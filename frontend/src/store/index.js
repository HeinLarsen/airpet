import Vue from 'vue'
import Vuex from 'vuex'
import http from '@/plugins/http'

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
      return http.post("/addUser", data)
    },
  },
  modules: {
  }
})
