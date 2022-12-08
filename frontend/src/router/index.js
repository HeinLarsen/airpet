import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: () => import(/* webpackChunkName: "home" */ '../views/Home.vue')
  },
  {
    path: '/dashboard',
    name: 'dashboard',
    component: () => import(/* webpackChunkName: "dashboard" */ '../views/Dashboard.vue')
  },
  {
    path: '/login',
    name: 'login',
    component: () => import(/* webpackChunkName: "login" */ '../views/Login.vue')
  },
  {
    path: '/signup',
    name: 'signup',
    component: () => import(/* webpackChunkName: "signup" */ '../views/Signup.vue')
  },
  {
    path: '/addPet',
    name: 'addPet',
    component: () => import(/* webpackChunkName: "addPet" */ '../views/AddPet.vue')
  },
  {
    path: '/pet/:id',
    name: 'pet',
    component: () => import(/* webpackChunkName: "pet" */ '../views/Pet.vue')
  },
  {
    path: '/addBooking',
    name: 'addBooking',
    component: () => import(/* webpackChunkName: "addBooking" */ '../views/AddBooking.vue')
  },
  {
    path: '/booking/:id',
    name: 'booking',
    component: () => import(/* webpackChunkName: "booking" */ '../views/Booking.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
