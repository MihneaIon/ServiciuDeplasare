import Vue from 'vue';
import path from 'path';

const components = (() => {

  const dictionary = {};
  const register = (scriptNname, comp) => {
    name = scriptNname.replace('.vue','');
    try {
      dictionary[name] = Vue.component( name.toLowerCase(), comp.default);
    }	catch (e) {
      console.error(e);
    }
  }

  // register all components in the app automatically
  var context = require.context("../src", true, /\.vue$/);
  context.keys().forEach(function (key) {
    register(path.basename(key), context(key));
  });

  return dictionary
})()

export default components