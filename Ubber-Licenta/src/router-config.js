import Vue from 'vue';
import VueRouter from 'vue-router';
// import drower from "vue-drawer-layout";

import store from './store';
import security from './keycloak';
import VueNativeSocket from 'vue-native-websocket'
import web3 from './web3'
import invoice from './invoice'

// the next line will register ALL local vue Components
// if you omit it, you need to register every Components
// locally wherever you need it
import Components from 'helpers/register-all-components';

Vue.use(web3);
Vue.use(invoice);
Vue.use(VueRouter);
Vue.use(VueNativeSocket, 'wss://192.168.43.165:8082/ws-masini', {
    store: store,
    format: 'json',
    reconnection: true, // (Boolean) whether to reconnect automatically (false)
    reconnectionAttempts: 5, // (Number) number of reconnection attempts before giving up (Infinity),
    reconnectionDelay: 3000, // (Number) how long to initially wait before attempting a new (1000)
})

const routes = [
    {
        path: '/',
        component: Components.Search,
        name: 'root',
        meta: {
            requiresAuth: true,
        },
    },
    {
        path: '/googlemap',
        component: Components.Search,
        name: 'GoogleMap',
        meta: {
            requiresAuth: true,
        }
    },
];

const router = new VueRouter({
    mode: 'history',
    scrollBehavior: () => ({ y: 0 }),
    routes,
});

router.beforeEach((to, from, next) => {
    if (to.meta.requiresAuth) {
        const auth = store.state.security.auth;
        if (typeof auth === 'undefined') {
            security.init(next);
        } else {
            next();
        }
    } else {
        next();
    }
});

router.onReady((cb, errCb) => {
    history.pushState(
        '',
        document.title,
        window.location.pathname + window.location.search
    );
});

export default router;
