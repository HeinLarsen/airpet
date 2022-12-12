<template>
  <v-app>
    <v-app-bar app>
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
          <v-btn to="/bookings" text> My bookings </v-btn>
          <v-btn to="/addPet" text> Add pet </v-btn>
          <v-btn to="/profile" text> profile </v-btn>
          <v-btn @click="logout" text> logout </v-btn>
        </template>
      </v-toolbar-items>
    </v-app-bar>

    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script>
export default {
  name: "App",
  data: () => ({}),
  created() {
    this.$store.dispatch("loadUser");
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
