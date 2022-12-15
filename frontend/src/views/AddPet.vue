<template>
  <v-container>
    <v-dialog v-model="modalShow" width="50%">
      <v-card>
        <vue-cropper
          class="cropper"
          ref="cropper"
          :src="this.previewImage"
          alt="Source Image"
          :viewMode="1"
          :aspect-ratio="16 / 9"
          :zoomable="false"
        >
        </vue-cropper>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="primary" text @click="(modalShow = false), cropImage()">
            crop
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-row justify="center">
      <v-col md="12" lg="8">
        <v-row>
          <v-col cols="12" md="6" class="pb-0">
            <v-row>
              <v-col cols="12" class="pb-0">
                <v-text-field v-model="pet.name" outlined label="name">
                </v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  v-model="pet.age"
                  outlined
                  label="age"
                  type="number"
                >
                </v-text-field>
              </v-col>
              <v-col cols="12" class="pb-0">
                <v-select
                  outlined
                  :items="species"
                  v-model="pet.species"
                  item-text="species"
                  item-value="id"
                  label="species"
                ></v-select>
              </v-col>
              <v-col cols="12" class="pb-0">
                <v-select
                  outlined
                  :items="breeds"
                  v-model="pet.breed"
                  item-text="breed"
                  item-value="id"
                  label="breed"
                ></v-select>
              </v-col>
            </v-row>
          </v-col>
          <v-col cols="6" align-self="end">
            <v-img :src="imageCropped" v-if="imageCropped" />
            <v-file-input
              show-size
              label="Select Image"
              accept="image/*"
              @change="selectImage"
            ></v-file-input>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12">
            <v-textarea
              outlined
              v-model="pet.description"
              label="beskrivelse"
            ></v-textarea>
          </v-col>
        </v-row>
        <v-row justify="center">
          <v-col cols="auto">
            <v-btn class="green--text text--accent-3" outlined @click="addPet">
              add
            </v-btn>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import VueCropper from "vue-cropperjs";
import "cropperjs/dist/cropper.css";
import conf from "@/conf.json";
import axios from "axios";
export default {
  name: "addpet",
  data() {
    return {
      currentImage: undefined,
      imageCropped: undefined,
      modalShow: false,
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
  components: {
    VueCropper,
  },
  mounted() {
    this.$store.dispatch("getSpecies");
    this.$store.dispatch("getBreeds");
  },
  methods: {
    cropImage() {
      console.log("image cropped");
      // get image data for post processing, e.g. upload or setting image src
      this.imageCropped = this.$refs.cropper.getCroppedCanvas().toDataURL();
    },
    selectImage(image) {
      this.currentImage = image;
      this.previewImage = URL.createObjectURL(this.currentImage);
      this.modalShow = true;
    },
    resizeImage(base64Str) {
      var img = new Image();
      img.src = base64Str;
      var canvas = document.createElement("canvas");
      var MAX_WIDTH = 384;
      var MAX_HEIGHT = 216;

      canvas.width = MAX_WIDTH;
      canvas.height = MAX_HEIGHT;
      var ctx = canvas.getContext("2d");
      ctx.drawImage(img, 0, 0, MAX_WIDTH, MAX_HEIGHT);
      return canvas.toDataURL("image/jpeg");
    },
    async uploadImage(id) {
      var resizedImage = this.resizeImage(this.imageCropped);
      var file = this.dataURLtoFile(resizedImage, id +".jpg");
      this.$store.dispatch("uploadImage", file);
    },
    async geolocation() {
      var address =
        this.user.street +
        " " +
        this.user.streetNumber +
        " " +
        this.user.zip +
        " " +
        this.user.city;
      var url =
        "https://maps.googleapis.com/maps/api/geocode/json?address=" +
        address +
        "&key=" +
        conf.key;
      var options = {
        url: url,
        method: "GET",
      };
      var result = await axios(options).catch((error) => {
        console.log(error.response);
        throw new Error(error.response.data.message);
      });
      var geopoint = result.data.results[0].geometry.location;
      this.pet.latitude = geopoint.lat;
      this.pet.longitude = geopoint.lng;
    },
    dataURLtoFile(dataurl, filename) {
      var arr = dataurl.split(","),
        mime = arr[0].match(/:(.*?);/)[1],
        bstr = atob(arr[1]),
        n = bstr.length,
        u8arr = new Uint8Array(n);
      while (n--) {
        u8arr[n] = bstr.charCodeAt(n);
      }
      return new File([u8arr], filename, { type: mime });
    },
    async addPet() {
      await this.geolocation();
      console.log(this.pet);
      var pet = await this.$store.dispatch("addPet", this.pet);
      this.uploadImage(pet);
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

<style lang="scss" scoped>
.cropper {
  max-height: 70vh;

  .cropper-container,
  .cropper-bg {
    height: 100%;
    width: 100%;
  }
}
</style>