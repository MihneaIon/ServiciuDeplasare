// get vue
import Vue from 'vue';
// import XComps from '../engine/x-components';
import store from './store';
import router from './router-config';
import axios from 'axios';
import VueAxios from 'vue-axios';
import * as VueGoogleMaps from 'vue2-google-maps';
import DrawerLayout from 'vue-drawer-layout'


// this method will delete vue resources when app is relaunched
// it is used at the end of this file.
import cleanAfter from 'helpers/clean-after';

import {MdButton ,MdContent, MdToolbar, MdTabs, MdDrawer, MdList,
     MdIcon, MdApp, MdAvatar, MdLayoutColumn, MdTitle, MdToolbarSectionEnd,
    MdListItem, MdAppDrawer, MdAppToolbar} from 'vue-material/dist/components'

import GoogleMap from './components/GoogleMap';
import Drivers from '@/components/Drivers';
import VModal from 'vue-js-modal';
// import Car from './components/Car';

Vue.use(VueAxios, axios);
Vue.use(DrawerLayout)

Vue.component('Drivers',Drivers)
Vue.component('GoogleMap',GoogleMap)
 // Vue.component('Car',Car)
Vue.use(VueGoogleMaps, {
    load: {
      key: '',
      libraries: "places"
    },
})
Vue.use(MdIcon)
Vue.use(MdAvatar)
Vue.use(MdApp)
Vue.use(MdContent)
Vue.use(MdButton)
Vue.use(MdDrawer)
Vue.use(MdList)
Vue.use(MdToolbar)
Vue.use(VModal)



// launch app in div with id 'app'
const appInstance = new Vue({
    el: document.getElementById('app'),
    // adding the network here is important to be able to use x-viewer in your app!
    //_network: Network,
    router,
    store,
});

// cleanAfter( appInstance );