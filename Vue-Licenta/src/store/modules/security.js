import * as types from '../types';
import axios from 'axios';
import store from '../../store';

const state = {
    auth: undefined,
    userInfo: undefined,
    specialGroups: ['bdo_administrators', 'bdo_developers'],
    isSpecialUser: false,
};

const getters = {
    auth: state => state.auth,
    userInfo: state => state.userInfo,
    isSpecialUser: state => state.isSpecialUser,
    // isAdmin: state =>{
    //     try{
    //         return state.auth.resourceAccess["fulfillment-api"].roles.indexOf("ff_admin")!= -1;
    //     }
    //     catch(ex){
    //         return false;
    //     }
    // }
};

const actions = {
    authLogin({ commit }, keycloakAuth) {
        store.commit(types.SECURITY_AUTH, keycloakAuth);
    },
    loadUserInfo({ commit, getters, rootGetters }, payload) {
        state.auth
            .loadUserInfo()
            .success(function(userInfo) {
                commit('setUserInfo', userInfo);
                // var special = state.specialGroups.some(function(v) {
                //     return userInfo.groups.indexOf(v) >= 0;
                // });
                //commit('setSpecialUser', special);
            })
            .error(function(error) {
                console.log(error);
            });
    },
    // consumeCar({commit}){
    //     return axios.get('http://localhost:8080/test')
    //     .then(response => {commit('getMappingLog',response.data);})
    //     .catch(()=>{commit('error')})
    // }
};

const mutations = {
    [types.SECURITY_AUTH](state, keycloakAuth) {
        state.auth = keycloakAuth;
        axios.defaults.headers.common = {
            Authorization: 'Bearer ' + keycloakAuth.token,
        };
    },
    setUserInfo(state, userInfo) {
        state.userInfo = userInfo;
    },
    setSpecialUser(state, specialUser) {
        state.isSpecialUser = specialUser;
    },
};

export default {
    state,
    actions,
    getters,
    mutations,
};
