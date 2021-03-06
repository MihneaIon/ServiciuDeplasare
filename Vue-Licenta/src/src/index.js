// get vue
import Vue from 'vue';
// import XComps from '../engine/x-components';
import store from './store';
import router from './router-config';
import axios from 'axios';
import VueAxios from 'vue-axios';
import * as VueGoogleMaps from 'vue2-google-maps'
import DrawerLayout from 'vue-drawer-layout'



// this method will delete vue resources when app is relaunched
// it is used at the end of this file.
import cleanAfter from 'helpers/clean-after';

import GoogleMap from './components/GoogleMap';
import Drivers from '@/components/Drivers';
import PercentBar from '@/components/PercentBar';
import VModal from 'vue-js-modal';
import VueMaterial from 'vue-material'
import { MdIcon, MdAvatar, MdAccent} from 'vue-material/dist/components'


Vue.use(VueAxios, axios);
Vue.use(DrawerLayout)

Vue.component('Drivers',Drivers)
Vue.component('GoogleMap',GoogleMap)
Vue.component('PercentBar',PercentBar)
Vue.use(VueGoogleMaps, {
    load: {
      key: 'AIzaSyAuH_VblA7ViIJUFqSfVsLnSs7LS1fdEA0',
      libraries: "places"
    },
})
Vue.use(VModal)
Vue.use(MdIcon)
Vue.use(MdAvatar)
Vue.use(VueMaterial)




// launch app in div with id 'app'
const appInstance = new Vue({
    el: document.getElementById('app'),
    // adding the network here is important to be able to use x-viewer in your app!
    //_network: Network,
    router,
    store,
});

// cleanAfter( appInstance );
