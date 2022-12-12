<template>
   <v-container fill-height fluid>
      <v-row justify="center">
        <v-col cols="12" lg="6">
          <v-row justify="center">
            <v-col sm=6 xs=12>
              <v-text-field outlined
                v-model="user.email"
                type="email"
                label="Email"
                :rules="emailRules"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field outlined
                v-model="user.password"
                type="Password"
                label="Password"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field outlined
                v-model="user.firstName"
                label="First name"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field outlined
                v-model="user.lastName"
                label="Last name"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field outlined
                v-model="user.street"
                label="Street name"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field outlined
                v-model="user.streetNumber"
                label="Street number"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field outlined
                v-model="user.city"
                label="City"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field outlined
                v-model="user.zip"
                label="Zip code"
              />
            </v-col>
          </v-row>
          <v-row justify="center">
            <v-col cols="auto">
              <v-btn @click="signup" class="green--text text--accent-3" outlined> signup </v-btn>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
</template>

<script>
export default {
  name: "signup",
  data: () => ({
    user: {
      email: '',
      firstName: '',
      lastName: '',
      password: '',
      street: '',
      streetNumber: null,
      city: '',
      zip: null,  
    },
    emailRules: [
      v => !!v || 'E-mail is required',
      v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
    ],
  }),
  methods: {
    signup() {
        this.$store.dispatch('addUser', this.user)
      },
    },
}
</script>

<style>
</style>