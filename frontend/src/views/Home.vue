<template>
  <v-container>
    <v-row>
      <v-col cols="12" md="6">
        <v-row align="stretch">
          <template v-for="(p, index) in pets">
            <v-col cols="6" lg="3" sm="4" :key="index">
              <v-card class="fill-height" :to="'/pet/' + p.id">
                <v-img
                  class="white--text align-end"
                  height="200px"
                  src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
                >
                  <v-card-title>{{p.name}}</v-card-title>
                </v-img>
                <v-card-text>
                  <v-row align="center" class="mx-0 mb-1">
                    <v-rating
                      :value="p.rating"
                      color="amber"
                      dense
                      half-increments
                      readonly
                      size="14"
                    ></v-rating>

                    <div class="grey--text ms-4">{{ p.rating }} ({{ p.ratingCount }})</div>
                  </v-row>
                  <template v-if="p.description.length > 50">
                    <div>{{ p.description.substring(0,50)+".." }}</div>
                  </template>
                  <template v-else>
                    {{ p.description }}
                  </template>
                </v-card-text>
              </v-card>
            </v-col>
          </template>
        </v-row>
      </v-col>
      <v-col cols="12" md="6"> google maps </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "home",

  mounted() {
    this.$store.dispatch("getPets");
  },
  computed: {
    pets() {
      return this.$store.state.pets;
    },
  },
};
</script>

<style>
</style>