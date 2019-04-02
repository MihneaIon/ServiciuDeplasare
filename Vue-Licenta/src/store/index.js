import Vue from 'vue';
import Vuex from 'vuex';

import security from './modules/security';
import users from './modules/users'
import GoogleMap from '../components/GoogleMap'
import Drivers from '../components/Drivers'
// import Car from '../components/Car'
import tasks from './modules/tasks'

Vue.use(Vuex);

const debug = process.env.NODE_ENV !== 'production';

export default new Vuex.Store({
  modules: {
    security,
    users,
    tasks
  },
  strict: debug,
  plugins: [],
  components: {
    'googleMap': GoogleMap,
    'drivers':Drivers,
    // 'car': Car
},
});
