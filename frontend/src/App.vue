<template>
  <v-app>
    <v-app-bar
      app
    >
      <div class="d-flex align-center">
        <h1>Airpets</h1>
      </div>

      <v-spacer></v-spacer>

      <v-toolbar-items>
        <v-btn to="/login" text>
          login
        </v-btn>
        <v-btn to="/signup" text>
          signup
        </v-btn>
      </v-toolbar-items>
      
    </v-app-bar>

    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script>

export default {
  name: 'App',

  data: () => ({
    //
  }),
};
</script>
