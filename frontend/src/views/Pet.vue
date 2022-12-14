<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="8">
        <v-row>
          <v-col cols="auto">
            <h1>{{ pet.name }}</h1>
            <i class="grey--text">{{ pet.age }} years</i>
          </v-col>
          <v-col cols="auto" class="ml-auto">
            <v-rating
              class="mt-4"
              :value="pet.rating"
              background-color="grey darken-3"
              color="red"
              full-icon="mdi-heart"
              half-icon="mdi-heart-half-full"
              empty-icon="mdi-heart-outline"
              dense
              half-increments
              readonly
              size="24"
            ></v-rating>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12" md="6">
            <v-card class="fill-height">
              <v-card-text>
                {{ pet.description }}
              </v-card-text>
            </v-card>
          </v-col>
          <v-col cols="12" md="6">
            <v-img
              class="rounded"
              :src="'http://localhost:8080/downloadFile/' + pet.id"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="6">
            <v-row>
              <v-col cols="12">
                <v-card>
                  <v-card-title>
                    <div class="grey--text mr-auto">Write a review!</div>
                  </v-card-title>
                  <v-card-text>
                    <v-textarea
                      rows="3"
                      class="mb-2"
                      outlined
                      v-model="review.description"
                    >
                    </v-textarea>
                  </v-card-text>
                  <v-card-actions>
                    <v-rating
                      background-color="grey darken-3"
                      color="red"
                      full-icon="mdi-heart"
                      half-icon="mdi-heart-half-full"
                      empty-icon="mdi-heart-outline"
                      v-model="review.rating"
                      dense
                      hover
                      half-increments
                      size="24"
                    ></v-rating>
                    <v-spacer></v-spacer>
                    <v-btn
                      class="green--text text--accent-3"
                      outlined
                      @click="submitReview"
                    >
                      Submit
                    </v-btn>
                  </v-card-actions>
                </v-card>
              </v-col>
              <v-col cols="12">
                <template v-for="(r, index) in reviews">
                  <v-card :key="index" class="mb-5">
                    <v-card-title>
                      <div class="grey--text mr-auto">{{ r.fullName }}</div>
                      <v-rating
                        :value="r.rating"
                        background-color="grey darken-3"
                        color="red"
                        full-icon="mdi-heart"
                        half-icon="mdi-heart-half-full"
                        empty-icon="mdi-heart-outline"
                        dense
                        half-increments
                        readonly
                        size="18"
                      ></v-rating>
                      <div class="grey--text ms-1">{{ r.rating }}</div>
                    </v-card-title>
                    <v-card-subtitle>
                      {{ r.date }}
                    </v-card-subtitle>
                    <v-card-text>
                      <div>{{ r.description }}</div>
                    </v-card-text>
                  </v-card>
                </template>
              </v-col>
            </v-row>
          </v-col>
          <v-col cols="6">
            <v-card>
              <v-card-title class="grey--text"
                >Book a day with {{ pet.name }}</v-card-title
              >
              <v-card-text>
                <v-menu
                  ref="menu"
                  v-model="menu"
                  :close-on-content-click="false"
                  :return-value.sync="date"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field outlined
                      v-model="date"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="date"
                    :min="currentDate"
                    :allowed-dates="allowedDates"
                    no-title
                    scrollable
                  >
                    <v-spacer></v-spacer>
                    <v-btn text color="primary" @click="menu = false">
                      Cancel
                    </v-btn>
                    <v-btn text color="primary" @click="$refs.menu.save(date)">
                      OK
                    </v-btn>
                  </v-date-picker>
                </v-menu>
                <v-menu
                  ref="menu2"
                  v-model="menu2"
                  :close-on-content-click="false"
                  :return-value.sync="date2"
                  transition="scale-transition"
                  offset-y
                  min-width="auto"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <v-text-field outlined
                      v-model="date2"
                      prepend-icon="mdi-calendar"
                      readonly
                      v-bind="attrs"
                      v-on="on"
                    ></v-text-field>
                  </template>
                  <v-date-picker
                    v-model="date2"
                    :min="date"
                    :allowed-dates="allowedDates"
                    no-title
                    scrollable
                  >
                    <v-spacer></v-spacer>
                    <v-btn text color="primary" @click="menu2 = false">
                      Cancel
                    </v-btn>
                    <v-btn
                      text
                      color="primary"
                      @click="$refs.menu2.save(date2)"
                    >
                      OK
                    </v-btn>
                  </v-date-picker>
                </v-menu>
              </v-card-text>
              <v-card-actions>
                <v-btn
                  class="ml-auto green--text text--accent-3"
                  outlined
                  @click="book()"
                >
                  book now
                </v-btn>
              </v-card-actions>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "pet",
  data() {
    return {
      el: [],
      currentDate: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
        .toISOString()
        .substr(0, 10),
      date: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
        .toISOString()
        .substr(0, 10),
      date2: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
        .toISOString()
        .substr(0, 10),
      menu: false,
      modal: false,
      menu2: false,
      review: {
        rating: 0,
        description: "",
        reviewer: this.$store.state.user.id,
        pet: this.$route.params.id,
        date: new Date(Date.now()).toISOString().split("T")[0],
      },
    };
  },
  mounted() {
    this.$store.dispatch("getPet", this.$route.params.id);
    this.$store.dispatch("getReviews", this.$route.params.id);
    this.$store.dispatch("getDates", this.$route.params.id);
  },
  methods: {
    allowedDates(val) {
      return !this.blockedDates.includes(val);
    },
    submitReview() {
      this.$store.dispatch("createReview", this.review);
    },
    book() {
      this.$store.dispatch("createBooking", {
        pet: this.$route.params.id,
        bookee: this.$store.state.user.id,
        start: this.date,
        end: this.date2,
      });
    },
  },
  computed: {
    pet() {
      return this.$store.state.pet;
    },
    reviews() {
      var reviews = this.$store.state.reviews;
      reviews.sort((a, b) => (a.rating > b.rating ? -1 : 1));
      return reviews;
    },
    blockedDates() {
      var blockedDates = this.$store.state.dates;
      this.el = blockedDates;
      return blockedDates;
    }
  },
};
</script>

<style>
</style>