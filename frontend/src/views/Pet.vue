<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="8">
        <v-row>
          <v-col cols="auto" class="mr-auto">
            <h1>{{ pet.name }}</h1>
            <i class="grey--text">{{ pet.ownerName }}</i>
          </v-col>
          <v-col cols="2">
            <v-rating
              class="mt-10"
              :value="pet.rating"
              color="amber"
              dense
              half-increments
              readonly
              size="24"
            ></v-rating>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12" md="6">
            {{ pet.description }}
          </v-col>
          <v-col cols="12" md="6">
            <v-img
              class="rounded-lg"
              src="https://cdn.vuetifyjs.com/images/cards/desert.jpg"
            />
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="6">
            <v-row>
              <v-col cols="12">
                <v-card>
                  <v-card-title>
                    <div class="grey--text mr-auto">New review</div>
                  </v-card-title>
                  <v-card-text>
                    <v-textarea outlined v-model="review.description">
                    </v-textarea>
                  </v-card-text>
                  <v-card-actions>
                    <v-rating
                      color="amber"
                      v-model="review.rating"
                      dense
                      hover
                      half-increments
                      size="24"
                    ></v-rating>
                    <v-spacer></v-spacer>
                    <v-btn color="green lighten-2" text @click="submitReview">
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
                        color="amber"
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
              <v-card-title>Book</v-card-title>
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
                  <v-text-field
                    v-model="date"
                    prepend-icon="mdi-calendar"
                    readonly
                    v-bind="attrs"
                    v-on="on"
                  ></v-text-field>
                </template>
                <v-date-picker v-model="date" no-title scrollable range>
                  <v-spacer></v-spacer>
                  <v-btn text color="primary" @click="menu = false">
                    Cancel
                  </v-btn>
                  <v-btn text color="primary" @click="$refs.menu.save(date)">
                    OK
                  </v-btn>
                </v-date-picker>
              </v-menu>
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
      date: new Date(Date.now() - new Date().getTimezoneOffset() * 60000)
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
  },
  methods: {
    submitReview() {
      this.$store.dispatch("createReview", this.review);
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
  },
};
</script>

<style>
</style>