import Vue from 'vue'
import Vuex from 'vuex'
import axios from 'axios'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    pet: {},
    pets: [],
    reviews: []
  },
  getters: {
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
    async addUser({ commit }, data) {
      console.log(data);
      var options = {
        method: 'POST',
        url: 'http://localhost:8080/UserController/addUser',
        headers: {
          'Content-Type': 'application/json',
          'Access-Control-Request-Headers': 'Content-Type, Authorization'
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
        url: 'http://localhost:8080/PetController/listPets',
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
          url: 'http://localhost:8080/PetController/getPet/' + id,
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
        url: 'http://localhost:8080/demo/getReviews/' + id,
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
        url: 'http://localhost:8080/demo/addReview',
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