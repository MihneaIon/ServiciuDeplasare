import Vue from 'vue';
import path from 'path';

// THE ONE network
import Network from 'network'

const eventBus = function( network/*, lc, plc*/) {
    const plc = {
        addPropListener: (n,p,f) => n.addPropListener(p,f)
    }
    const lc = {
        addEventListener: (o,e,f) => o.addEventListener(e,f)
    }
    const nodes = network.network.Nodes;

    const bus = new Vue({
        data() {
            return {
                // all nodes
                N: Object.keys(nodes).reduce((akk,n) => {
                    const node = nodes[n];

                    // bind all properties
                    const props = Object.keys(nodes[n].props).reduce((pakk,p) => {
                        // set initial value
                        if( nodes[n].props[p]._type == "Trigger") {
                            pakk[p] = function() { nodes[n].setProp(p) };
                        } else {
                            pakk[p] = nodes[n].getProp(p);
                            // update value in data of bus when network changes
                            plc.addPropListener( nodes[n], p, function(p, val) { 
                                bus.N[n][p] = val; 
                            } );
                        }
                        return pakk;
                    }, {});

                    // now bind all outputs
                    const outputs = {};
                    Object.keys(node._outputs).reduce((oakk,o) => {
                        if( node._outputs[o]._type == "Render" ) 
                            return oakk;

                        var name = o;
                        if( props.hasOwnProperty(name) )
                            name = "out"+o;
                        
                        // i am sorry, there is a name collision that we give up on..
                        if( props.hasOwnProperty(name) )
                            return oakk;
                        
                        // get data once
                        outputs[name] = "";
                        node.getOutputData(o, (data) => {
                            outputs[name] = data; 
                        });

                        const handleData = () => {
                            node.getOutputData(o, (data) => {
                                bus.N[n][o] = data; 
                            });
                        }

                        // register handler
                        lc.addEventListener( node._outputs[o], "update", handleData );
                        return oakk;
                    }, {});

                    akk[n] = Object.assign(props, outputs);
                    return akk;
                }, {})
            }
        },
        // vue -> network
        watch: Object.keys(nodes).reduce((akk,n) => {
            // bind all properties
            const props = Object.keys(nodes[n].props).forEach((p) => {
                // triggers are functions and ignored here
                if( nodes[n].props[p]._type === "Trigger" ) {

                } else {
                    // if the value in data changes we set the prop as well
                    akk['N.'+n+'.'+p] = function(val, old) {
                        if (val !== old)
                            nodes[n].setProp(p, val);
                    };
                }
            });
            return akk;
        }, {})
    });
    
    return {
        // create bus wrapper around singular object
        // you can call this many times to generate a (relatively) lighweight mixin for components
        generator: () => ({
            computed: {
                N() {
                    return bus.N
                }
            }
        }),
        // call this to destroy the current event bus!
        $destroy: () =>	bus.$destroy()
    };
}

// compatibility mixins
const CompatPlugin = (opts) => ({
  install(Vue, options) {
      Vue.mixin(Object.assign({
          methods: {
              navigate(url) {
                  this.$router.push(url)
              }
          }
      }, opts.networkBinding ? eventBus(Network).generator() : {}));
  }
});

export default CompatPlugin;