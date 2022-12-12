<template>
  <v-container>
    <v-row>
      <v-col cols="12" md="6">
        <v-row align="stretch">
          <template v-for="(p, index) in filteredPets">
            <v-slide-y-transition mode="out-in" :key="index">
              <v-col cols="6" lg="3" sm="4">
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
                        background-color="grey darken-3"
                        color="red"
                        full-icon="mdi-heart"
                        half-icon="mdi-heart-half-full"
                        empty-icon="mdi-heart-outline"
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
          :options="mapStyle"
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
import "@/plugins/googleMaps.js";
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
      mapStyle: {
        mapTypeControl: false,
        streetViewControl: false,
        fullscreenControlOptions: { position: 3 },
        zoomControlOptions: { position: 7 },
        // other properties...
        styles: [
  {
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#242f3e"
      }
    ]
  },
  {
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#746855"
      }
    ]
  },
  {
    "elementType": "labels.text.stroke",
    "stylers": [
      {
        "color": "#242f3e"
      }
    ]
  },
  {
    "featureType": "administrative.locality",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#d4d4d4"
      }
    ]
  },
  {
    "featureType": "poi",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#ffffff"
      }
    ]
  },
  {
    "featureType": "poi.business",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "poi.government",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#263c3f"
      }
    ]
  },
  {
    "featureType": "poi.park",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#6b9a76"
      }
    ]
  },
  {
    "featureType": "poi.place_of_worship",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "poi.school",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#38414e"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "geometry.stroke",
    "stylers": [
      {
        "color": "#212a37"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "labels",
    "stylers": [
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "road",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#9ca5b3"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#333333"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "geometry.stroke",
    "stylers": [
      {
        "color": "#1f2835"
      }
    ]
  },
  {
    "featureType": "road.highway",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#ffffff"
      },
      {
        "visibility": "off"
      }
    ]
  },
  {
    "featureType": "transit",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#2f3948"
      }
    ]
  },
  {
    "featureType": "transit",
    "elementType": "labels",
    "stylers": [
      {
        "visibility": "simplified"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "geometry",
    "stylers": [
      {
        "color": "#121212"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "labels.text.fill",
    "stylers": [
      {
        "color": "#515c6d"
      }
    ]
  },
  {
    "featureType": "water",
    "elementType": "labels.text.stroke",
    "stylers": [
      {
        "color": "#17263c"
      }
    ]
  }
]
      },
    };
  },

  mounted() {
    this.$store.dispatch("getPets");
  },
  methods: {
    getMark() {
      return require("@/assets/logo-small-b.png");
    },
    boundsChanged() {
      var mapRef = this.$refs.mapRef.$mapObject;
      this.filteredPets = this.pets.filter((pet) => {
        var result = mapRef.getBounds().contains(this.getPosition(pet));
        return result;
      });
    },
    getPosition: function (pet) {
      return {
        lat: parseFloat(pet.latitude),
        lng: parseFloat(pet.longitude),
      };
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