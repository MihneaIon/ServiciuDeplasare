import axios from 'axios'
import * as types from '../types'
const state = {
    users: [],
}

const getters = {
    users: state => state.users,
}

const mutations = {
    setUsers(state, results) {
        state.users = results;
    },
}

const actions = {

    loadUsers({
        commit,
        getters,
        rootGetters
    }, payload) {
        axios
            .request({
                method: "get",
                url: "http://localhost:8080/*" + 'users',
            })
            .then(response => {
                commit('setUsers', response.data);
            })
            .catch(error => {
                console.log(error);
            });
    },
}

export default {
    state,
    getters,
    actions,
    mutations
}