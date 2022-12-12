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
    path: '/bookings',
    name: 'booking',
    component: () => import(/* webpackChunkName: "booking" */ '../views/Bookings.vue')
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
