import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'
import router from '@/router'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    auth: false,
    user: {},
    pet: {},
    pets: [],
    reviews: []
  },
  getters: {
    user(state) {
      return state.user
    },
    pet(state) {
      return state.pet
    },
    pets(state) {
      return state.pets
    },
    reviews(state) {
      return state.reviews
    }
  },
  mutations: {
    setUser(state, user) {
      state.user = user
    },
    setPet(state, pet) {
      state.pet = pet
    },
    setPets(state, pets) {
      state.pets = pets
    },
    setReviews(state, reviews) {
      state.reviews = reviews
    }
  },
  actions: {
    async loadUser(context) {
      let user = JSON.parse(localStorage.getItem('user'))
      if (user) {
        context.commit('setUser', user)
        context.state.auth = true
      }
    },
    async login(context, data) {
      console.log(data);
      var options = {
        method: 'POST',
        url: 'http://localhost:8080/user/login',
        headers: {
          'Content-Type': 'application/json',
        },
        data: data
      }
      try {
        const response = await axios(options)
        localStorage.setItem('user', JSON.stringify(response.data))
        context.commit('setUser', response.data)
        context.state.auth = true
        router.push('/')
      } catch (error) {
        console.log(error);
      }
    },
    async logout(context) {
      localStorage.removeItem('user')
      context.commit('setUser', {})
      context.state.auth = false
      router.push('/')
    },
    async addUser({ commit }, data) {
      console.log(data);
      var options = {
        method: 'POST',
        url: 'http://localhost:8080/user/addUser',
        headers: {
          'Content-Type': 'application/json',
        },
        data: data
      }
      try {
        await axios(options)
      } catch (error) {
        console.log(error);
      }
    },
    async getPets(context) {
      var options = {
        method: 'GET',
        url: 'http://localhost:8080/pet/listPets',
        headers: {
          'Content-Type': 'application/json'
        }
      }
      const pets = await axios(options)
      context.commit('setPets', pets.data)

    },
    async getPet(context, id) {
        var options = {
          method: 'GET',
          url: 'http://localhost:8080/pet/getPet/' + id,
          headers: {
            'Content-Type': 'application/json'
          }
        }
        const pet = await axios(options)
        console.log(pet);
        context.commit('setPet', pet.data)
    },
    async getReviews(context, id) {
      var options = {
        method: 'GET',
        url: 'http://localhost:8080/review/getReviews/' + id,
        headers: {
          'Content-Type': 'application/json'
        }
      }
      const reviews = await axios(options)
      context.commit('setReviews', reviews.data)
    },
    async createReview(context, data) {
      console.log(data);
      var options = {
        method: 'POST',
        url: 'http://localhost:8080/review/addReview',
        headers: {
          'Content-Type': 'application/json'
        },
        data: data
      }
      try {
        await axios(options)
        context.dispatch('getReviews', data.pet)
      } catch (error) {
        console.log(error);
      }
    }
  },
  modules: {
  }
})