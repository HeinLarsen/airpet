<template>
  <v-container fill-height fluid>
    <v-form
      ref="form"
      style="width: 100%;"
      v-model="valid"
      lazy-validation
      @submit.prevent="validate"
    >
      <v-row justify="center">
        <v-col cols="12" lg="3">
          <v-row justify="center">
            <v-col cols="12">
              <v-text-field
                outlined
                v-model="email"
                type="email"
                label="Email"
                required
                :rules="emailRules"
              />
            </v-col>
            <v-col cols="12">
              <v-text-field
                v-model="password"
                required
                :rules="[(v) => !!v || 'Password is required']"
                outlined
                type="password"
                label="Password"
              />
            </v-col>
          </v-row>
          <v-row justify="center">
            <v-col cols="auto">
              <v-btn type="submit" class="blue--text text--accent-2" outlined>
                login
              </v-btn>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-form>
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
    validate() {
      if (this.$refs.form.validate()) {
        console.log("valid");
        this.login();
      }
    },
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