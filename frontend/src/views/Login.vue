<template>
 <v-container fill-height fluid>
      <v-row>
        <v-col cols="12">
          <v-row justify="center">
            <v-col lg=3 sm=6 xs=12>
              <v-text-field
                v-model="email"
                type="email"
                label="Email"
                :rules="emailRules"
              />
            </v-col>
          </v-row>
          <v-row justify="center">
            <v-col lg=3 sm=6 xs=12>
              <v-text-field
                v-model="password"
                type="password"
                label="Password"
              />
            </v-col>
          </v-row>
          <v-row justify="center">
            <v-col cols="auto">
              <v-btn @click="login" color="blue" text> login </v-btn>
            </v-col>
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
  name: "login"
}
</script>

<style>

</style>