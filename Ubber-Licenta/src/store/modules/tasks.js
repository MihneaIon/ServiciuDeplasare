import axios from 'axios'
import Vuex from 'vuex'
import * as types from '../types'
import Vue from 'vue';

Vue.use(Vuex)

const state = {
    tasks: [],
    cars: [],
    selectedCar: {},
    currentTask: {},
    showAddTaskDialog: false,
    savedQueryObj: {},
    socket: {
        isConnected: false,
        message: '',
        reconnectError: false,
    },
    driverSelected:false, // variabila care ma va asigura ca am selectat un sofer
    thePrice:{},
    driverContract:{},
    clientsContract: [],
    balance: '',
    value:{},
    mesajContract: ''
}

const getters = {
    tasks: state => state.tasks,
    currentTask: state => state.currentTask,
    showAddTaskDialog: state => state.showAddTaskDialog,
    savedQueryObj: state => state.savedQueryObj,
    cars: (state) => state.cars,
    selectedCar: (state) => state.selectedCar,
    takeClientPosition: (stat) => state.clientPosition,
    driverSelected: (state) => state.driverSelected,
    thePrice: (state) => state.thePrice,
    driverContract: (state) => state.driverContract,
    clientsContract : (state) => state.clientsContract,
    balance: (state) => state.balance,
    mesajContract:(state) => state.mesajContract
    
}

const mutations = {
    [types.SET_CURRENT_TASK](state, task) {
        state.currentTask = task;
    },
    [types.SET_TASKS](state, tasks) {
        state.tasks = tasks;
    },
    [types.RESET_CURRENT_TASK](state) {
        state.currentTask = undefined;
    },
    setShowAddTaskDialog(state, results) {
        state.showAddTaskDialog = results;
    },
    setSavedQueryObj(state,results) {
        state.savedQueryObj = results
    },
    setCar(state,car){
        state.cars=car;
    },
    selectCar(state,car){
        state.selectedCar= car
        console.log("this is my car", state.selectedCar)
    },
    activeDriverSelected(state,value){
        console.log('ce trebuie sa se intample',state.driverSelected)
        return state.driverSelected = value
    },
    setThePrice(state,value){
        return state.thePrice = value
    },
    SOCKET_ONOPEN(state, event) {
        state.socket.isConnected = true
        console.log("socket opened")
    },
    SOCKET_ONCLOSE(state, event) {
        console.log("socket closed")
        state.socket.isConnected = false
    },
    SOCKET_ONMESSAGE(state, message) {
        console.log('!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!',message)
        state.pozitiiIntermediare = message
        console.log('mesajul este',message)
        state.socket.message = 'message'
    },
    SOCKET_ONERROR(state, event){
        console.error(state, event)
    },
    SOCKET_RECONNECT(state,count){
        console.info(state, count)
    },
    SOCKET_RECONNECT_ERROR(state){
        state.socket.reconnectError = true;
    },
    sendDriverContract(state,value){
        return state.driverContract = value
    },
    sendClientsContract(state,value){
        return state.clientsContract = value
    },
    sendBalance(state, value){
        return state.balance = value
    },
    setTheContractMessage(state, value){
        return state.mesajContract = value;
    }
    
}

const actions = {
    setCurretTask(state, currentTask) {

        state.commit(types.SET_CURRENT_TASK, currentTask);
    },
    resetCurrentTask(state) {
        state.commit(types.RESET_CURRENT_TASK);
    },
    getAllCars: ({commit }) =>{
        return axios.get('https://192.168.43.165:8082/cars')
        .then((response) =>{
            if(response.status==200){
                commit('setCar',response.data)
                console.log('am intrat in action')
            }
        })
        .catch((error)=> console.error(error))
    },
}

export default {
    state,
    getters,
    actions,
    mutations
}