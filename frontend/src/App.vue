<template>
  <v-app>
    <v-app-bar app dense>
      <v-toolbar-title>
        <router-link to="/" tag="span" style="cursor: pointer">
          <v-icon large>mdi-paw</v-icon>
          Airpets
        </router-link>
      </v-toolbar-title>

      <v-spacer></v-spacer>

      <v-toolbar-items>
        <template v-if="!auth">
          <v-btn to="/login" text> login </v-btn>
          <v-btn to="/signup" text> signup </v-btn>
        </template>
        <template v-else>
          <v-hover v-slot="{ hover }">
            <v-btn to="/bookings" text :color="hover ? 'blue accent-2' : 'grey'"> my bookings
          </v-btn>
          </v-hover>
          <v-hover v-slot="{ hover }">
            <v-btn to="/addPet" text :color="hover ? 'green accent-3' : 'grey'"> add pet </v-btn>
          </v-hover>

          <v-hover v-slot="{ hover }">
            <v-btn @click="logout" icon>
            <v-icon :color="hover ? 'red accent-3' : 'grey'">mdi-logout</v-icon>
          </v-btn>
          </v-hover>
        </template>
      </v-toolbar-items>
    </v-app-bar>
    <v-main>
    <v-slide-y-transition mode="out-in">
      <router-view />
       </v-slide-y-transition>
    </v-main>
    <vtoast ref="vtoast"/>
  </v-app>
</template>

<script>
import vtoast from '@/components/vtoast.vue'

export default {
  name: "App",
  components:{
        vtoast
    },
  data: () => ({}),
  created() {
    this.$store.dispatch("loadUser");
  },
  mounted() {
      this.$root.vtoast = this.$refs.vtoast
    },
  methods: {
    logout() {
      this.$store.dispatch("logout");
    },
  },
  computed: {
    auth() {
      return this.$store.state.auth;
    },
  },
};
</script>


