<template>
  <v-container fill-height fluid>
    <v-form
      ref="form"
      v-model="valid"
      lazy-validation
      @submit.prevent="validate"
    >
      <v-row justify="center">
        <v-col cols="12" lg="6">
          <v-row justify="center">
            <v-col sm="6" xs="12">
              <v-text-field
                outlined
                required
                v-model="user.email"
                type="email"
                label="Email"
                :rules="emailRules"
              />
            </v-col>
            <v-col sm="6" xs="12">
              <v-text-field
                outlined
                required
                v-model="user.password"
                type="Password"
                label="Password"
                :rules="[(v) => !!v || 'Password is required']"
              />
            </v-col>
            <v-col sm="6" xs="12">
              <v-text-field
                outlined
                required
                v-model="user.firstName"
                label="First name"
                :rules="[(v) => !!v || 'First name is required']"
              />
            </v-col>
            <v-col sm="6" xs="12">
              <v-text-field
                outlined
                required
                v-model="user.lastName"
                label="Last name"
                :rules="[(v) => !!v || 'Last name is required']"
              />
            </v-col>
            <v-col sm="6" xs="12">
              <v-text-field
                outlined
                required
                v-model="user.street"
                label="Street name"
                :rules="[(v) => !!v || 'Street name is required']"
              />
            </v-col>
            <v-col sm="6" xs="12">
              <v-text-field
                outlined
                required
                v-model="user.streetNumber"
                label="Street number"
                type="number"
                :rules="[(v) => !!v || 'Street number is required']"
              />
            </v-col>
            <v-col sm="6" xs="12">
              <v-text-field
                outlined
                required
                v-model="user.city"
                label="City"
                :rules="[(v) => !!v || 'City is required']"
              />
            </v-col>
            <v-col sm="6" xs="12">
              <v-text-field
                outlined
                required
                v-model="user.zip"
                label="Zip code"
                type="number"
                :rules="[(v) => !!v || 'Zip code is required']"
              />
            </v-col>
          </v-row>

          <v-row justify="center">
            <v-col cols="auto">
              <v-btn type="submit" class="green--text text--accent-3" outlined>
                signup
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
  name: "signup",
  data: () => ({
    valid: true,
    user: {
      email: "",
      firstName: "",
      lastName: "",
      password: "",
      street: "",
      streetNumber: null,
      city: "",
      zip: null,
    },
    emailRules: [
      (v) => !!v || "E-mail is required",
      (v) => /.+@.+\..+/.test(v) || "E-mail must be valid",
    ],
  }),
  methods: {
    validate() {
      if (this.$refs.form.validate()) {
        console.log("valid");
        this.signup();
      }
    },
    async signup() {
      var res = await this.$store.dispatch("addUser", this.user);
      if (res.status == 200) {
        this.$root.vtoast.show({
          message: res.data,
          color: "green",
          icon: "mdi-check",
          timer: 3000,
        });
        this.$router.push("/login");
      } else {
        this.$root.vtoast.show({
          message: res.data,
          color: "error",
          icon: "mdi-alert",
          timer: 3000,
        })
      }
    },
  },
};
</script>

<style>
</style>