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
    reviews: [],
    bookings: [],
    dates: [],
    species: [],
    breeds: [],
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
    },
    bookings(state) {
      return state.bookings
    },
    dates(state) {
      return state.dates
    },
    species(state) {
      return state.species
    },
    breeds(state) {
      return state.breeds
    },
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
    },
    setBookings(state, bookings) {
      state.bookings = bookings
    },
    setDates(state, dates) {
      state.dates = dates
    },
    setSpecies(state, species) {
      state.species = species
    },
    setBreeds(state, breeds) {
      state.breeds = breeds
    },
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
        var res = await axios(options)
        console.log(res);
        return res
      } catch (error) {
        console.log(error);
      }
    },
    async getPets(context, data) {
      var urlBuilder = 'http://localhost:8080/pet/listPets'
      if (data) {
        urlBuilder += '?breeds=' + data.breeds + '&species=' + data.species
      }
      var options = {
        method: 'GET',
        url: urlBuilder,
        headers: {
          'Content-Type': 'application/json'
        },
        data
      }
      const pets = await axios(options)
      console.log(pets);
      context.commit('setPets', pets.data)
      return pets.status

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
      let reviews = await axios(options)
      console.log(reviews);
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
        var res = await axios(options)
        context.dispatch('getReviews', data.pet)
        return res;
      } catch (error) {
        console.log(error);
      }
    },
    async createBooking(context, data) {
      console.log(data);
      var options = {
        method: 'POST',
        url: 'http://localhost:8080/booking/addBooking',
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
    async getBookings(context) {
      var options = {
        method: 'GET',
        url: 'http://localhost:8080/booking/listBookings/' + context.state.user.id,
        headers: {
          'Content-Type': 'application/json'
        }
      }
      const bookings = await axios(options)
      context.commit('setBookings', bookings.data)
    },
    async getDates(context, id) {
      var options = {
        method: 'GET',
        url: 'http://localhost:8080/booking/listBooking/' + id,
        headers: {
          'Content-Type': 'application/json'
        }
      }
      let dates = await axios(options)
      context.commit('setDates', dates.data)
    },
    async cancelBooking(context, id) {
      var options = {
        method: 'DELETE',
        url: 'http://localhost:8080/booking/cancelBooking/' + id,
        headers: {
          'Content-Type': 'application/json'
        }
      }
      try {
        await axios(options)
        context.dispatch('getBookings')
      } catch (error) {
        console.log(error);
      }
    },
    async getSpecies(context) {
      var options = {
        method: 'GET',
        url: 'http://localhost:8080/species/getSpecies',
        headers: {
          'Content-Type': 'application/json'
        }
      }
      let species = await axios(options)
      context.commit('setSpecies', species.data)
    },
    async getBreeds(context) {
      var options = {
        method: 'GET',
        url: 'http://localhost:8080/breed/getBreeds',
        headers: {
          'Content-Type': 'application/json'
        }
      }
      let breeds = await axios(options)
      context.commit('setBreeds', breeds.data)
    },
    async addPet(context, data) {
      console.log(data);
      var options = {
        method: 'POST',
        url: 'http://localhost:8080/pet/addPet',
        headers: {
          'Content-Type': 'application/json'
        },
        data: data
      }
      try {
        var res = await axios(options)
        console.log(res);
        return res.data;
      }
      catch (error) {
        console.log(error);
      }
    },
    async uploadImage(context, data) {
      var formData = new FormData();
      formData.append('file', data)
      axios.post('http://localhost:8080/uploadFile', formData, {
        headers: {
          'Content-Type': 'multipart/form-data'
        }
    })
    }
  },
  modules: {
  }
})