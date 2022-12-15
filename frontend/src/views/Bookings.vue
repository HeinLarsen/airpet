<template>
  <v-container>
    <v-row >
      <template v-for="(b, index) in bookings">
        <v-col md="6" lg="4" :key="index" >
          <v-card
              dark
            >
              <div class="d-flex flex-no-wrap justify-space-between">
                <div>
                  <v-card-title
                    class="text-h5"
                    v-text="b.petName"
                  ></v-card-title>
  
                  <v-card-subtitle>{{ b.start }} - {{ b.end }} </v-card-subtitle>
  
                  <v-card-text>
                    {{ b.petDescription }}
                  </v-card-text>
  
                  
                </div>
  
                <v-avatar
                  class="ma-3 rounded"
                  size="125"
                  tile
                >
                  <v-img src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"></v-img>
                </v-avatar>
              </div>
              <v-card-actions>
                    <v-btn
                      class="ml-auto red--text text--accent-3"
                      outlined
                      @click="cancelBooking(b.id)"
                    >
                      cancel
                    </v-btn>
                  </v-card-actions>
            </v-card>
        </v-col>
      </template>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "booking",
  mounted() {
    this.$store.dispatch("getBookings");
  },
  methods: {
    cancelBooking(id) {
      this.$store.dispatch("cancelBooking", id);
    },
  },
  computed: {
    bookings() {
      var b = this.$store.state.bookings;
      // invert the array
      return b.reverse();
    },
  },
};
</script>

<style>
</style>