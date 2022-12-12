<template>
  <v-container>
    <v-row>
      <v-col cols="12" md="6">
        <v-row align="stretch">
          <template v-for="(p, index) in filteredPets">
          <v-slide-y-transition mode="out-in" :key="index">
            <v-col cols="6" lg="3" sm="4" >
              <v-card class="fill-height" :to="'/pet/' + p.id">
                <v-img
                  class="white--text align-end"
                  height="200px"
                  src="https://cdn.vuetifyjs.com/images/cards/docks.jpg"
                >
                  <v-card-title>{{ p.name }}</v-card-title>
                </v-img>
                <v-card-text>
                  <v-row align="center" class="mb-1">
                    <v-rating
                      :value="p.rating"
                      color="amber"
                      dense
                      half-increments
                      readonly
                      size="20"
                    ></v-rating>

                    <div class="grey--text ml-auto">
                      {{ p.rating }} ({{ p.ratingCount }})
                    </div>
                  </v-row>
                  <template v-if="p.description.length > 50">
                    <p>{{ p.description.substring(0, 50) + ".." }}</p>
                  </template>
                  <template v-else>
                    {{ p.description }}
                  </template>
                </v-card-text>
              </v-card>
            </v-col>
      </v-slide-y-transition>
          </template>
        </v-row>
      </v-col>
      <v-col cols="12" md="6">
        <GmapMap
          style="width: 100%; height: 90vh"
          @idle="boundsChanged($event)"
          :center="center"
          :zoom="zoom"
          :options="{
            streetViewControl: false,
            fullscreenControlOptions: { position: 3 },
            zoomControlOptions: { position: 7 },
          }"
          ref="mapRef"
        >
          <GmapCluster :zoomOnClick="true" :maxZoom="10">
            <gmap-marker
              v-for="marker in pets"
              :key="marker.id"
              :position="marker.position"
              :clickable="true"
              :draggable="false"
              :icon="{
                url: getMark(),
              }"
              @click="center = marker.position"
            />
          </GmapCluster>
        </GmapMap>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import '@/plugins/googleMaps.js'
import { gmapApi } from "vue2-google-maps";
export default {
  name: "home",
  data() {
    return {
      center: {
        lat: 55.8,
        lng: 11,
      },
      zoom: 7,
      filteredPets: {},
    };
  },

  mounted() {
    this.$store.dispatch("getPets");
  },
  methods: {
    getMark() {
      return require("@/assets/logo-small.png");
    },
    boundsChanged() {
			var mapRef = this.$refs.mapRef.$mapObject
			this.filteredPets = this.pets.filter((pet) => {
				var result = mapRef.getBounds().contains(this.getPosition(pet))
				return result
			})
		},
    getPosition: function (pet) {
			return {
				lat: parseFloat(pet.latitude),
				lng: parseFloat(pet.longitude),
			}
		},
  },
  computed: {
    pets() {
      var pets = this.$store.state.pets;
      for (let i = 0; i < pets.length; i++) {
        pets[i].position = {
          lat: pets[i].latitude,
          lng: pets[i].longitude,
        };
      }
      return pets;
    },
  },
};
</script>

<style>
</style>