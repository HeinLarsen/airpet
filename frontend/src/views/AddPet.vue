<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="8">
        <v-row>
          <v-col cols="6">
            <v-row>
              <v-col cols="12">
                <v-text-field v-model="pet.name" label="name"> </v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field v-model="pet.age" label="age" type="number"> </v-text-field>
              </v-col>
              <v-col cols="12">
                <v-select
                  :items="species"
                  v-model="pet.species"
                  item-text="species"
                  item-value="id"
                  label="species"
                ></v-select>
              </v-col>
              <v-col cols="12">
                <v-select
                  :items="breeds"
                  v-model="pet.breed"
                  item-text="breed"
                  item-value="id"
                  label="breed"
                ></v-select>
              </v-col>
            </v-row>
          </v-col>
          <v-col cols="6"> img </v-col>
        </v-row>
        <v-row>
          <v-col cols="12">
            <v-textarea
              v-model="pet.description"
              label="beskrivelse"
            ></v-textarea>
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col cols="auto">
            <v-btn color="green" @click="addPet" text> add </v-btn>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import conf from "@/conf.json";
import axios from "axios";
export default {
  name: "addpet",
  data() {
    return {
      disabled: true,
      pet: {
        name: "",
        breed: 1,
        species: 1,
        owner: this.$store.state.user.id,
        age: null,
        description: "",
        latitude: null,
        longitude: null,
      },
    };
  },
  mounted() {
    this.$store.dispatch("getSpecies");
    this.$store.dispatch("getBreeds");
  },
  methods: {
   async geolocation() {
      var address = this.user.street + " " + this.user.streetNumber + " " + this.user.zip + " " + this.user.city;
      var url = 'https://maps.googleapis.com/maps/api/geocode/json?address=' + address + '&key=' + conf.key
			var options = {
				url: url,
				method: 'GET',
			}
			var result = await axios(options).catch((error) => {
				console.log(error.response)
				throw new Error(error.response.data.message)
			})
				var geopoint = result.data.results[0].geometry.location
				this.pet.latitude = geopoint.lat
				this.pet.longitude = geopoint.lng
    },
    async addPet() {
      await this.geolocation();
      console.log(this.pet);
      this.$store.dispatch("addPet", this.pet);
    },
  },
  computed: {
    user() {
      return this.$store.state.user;
    },
    species() {
      return this.$store.getters.species;
    },
    breeds() {
      var breeds = this.$store.getters.breeds;
      return breeds.filter((breed) => breed.species == this.pet.species);
    },
  },
};
</script>

<style>
</style>