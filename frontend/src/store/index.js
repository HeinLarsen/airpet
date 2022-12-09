import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
  },
  getters: {
  },
  mutations: {
  },
  actions: {
    async addUser({ commit }, data) {
      console.log(data);
      var options = {
        method: 'POST',
        url: 'http://localhost:8080/demo/addUser',
        headers: {
          'Content-Type': 'application/json'
        },
        data: data
      }
      try {
        await axios(options)
      } catch (error) {
        console.log(error);
      }
    },
  },
  modules: {
  }
})