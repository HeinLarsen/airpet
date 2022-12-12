<template>
  <v-container fill-height fluid>
    <v-row>
      <v-col cols="12">
        <v-row justify="center">
          <v-col lg="3" sm="6" xs="12">
            <v-text-field outlined
              v-model="email"
              type="email"
              label="Email"
              :rules="emailRules"
            />
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col lg="3" sm="6" xs="12">
            <v-text-field v-model="password" outlined type="password" label="Password" />
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col cols="auto">
            <v-btn @click="login" class="blue--text text--accent-2" outlined> login </v-btn>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "login",
  data() {
    return {
      email: "",
      password: "",
      emailRules: [
        (v) => !!v || "E-mail is required",
        (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
      ],
    };
  },

  methods: {
    login() {
      this.$store.dispatch("login", {
        email: this.email,
        password: this.password,
      });
    },
  },
};
</script>

<style>
</style>