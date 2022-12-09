<template>
   <v-container fill-height fluid>
      <v-row justify="center">
        <v-col cols="12" lg="6">
          <v-row justify="center">
            <v-col sm=6 xs=12>
              <v-text-field
                v-model="user.email"
                type="email"
                label="Email"
                :rules="emailRules"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field
                v-model="user.password"
                type="password"
                label="Password"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field
                v-model="user.firstName"
                label="Fornavn"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field
                v-model="user.lastName"
                label="Efternavn"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field
                v-model="user.street"
                label="Vejnavn"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field
                v-model="user.streetNumber"
                label="Husnummer"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field
                v-model="user.city"
                label="By"
              />
            </v-col>
            <v-col sm=6 xs=12>
              <v-text-field
                v-model="user.zip"
                label="Post kode"
              />
            </v-col>
          </v-row>
          <v-row justify="center">
            <v-col cols="auto">
              <v-btn @click="signup" color="green" text> signup </v-btn>
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
      streetNumber: '',
      city: '',
      zip: '',  
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