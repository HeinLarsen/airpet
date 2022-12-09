<template>
  <v-container>
    <v-row justify="center">
      <v-col cols="8">
        <v-row>
          <v-col cols="12">
            <h1>pet name</h1>
          </v-col>
          <v-col cols="12" md="6">
            Lorem ipsum dolor sit amet consectetur adipisicing elit. Dicta
            provident temporibus in excepturi quae sit iure assumenda, vitae
            beatae nemo voluptatum! Quasi minus architecto, saepe harum laborum
            ipsam quibusdam illum. Lorem, ipsum dolor sit amet consectetur
            adipisicing elit. Ad dolor modi sequi beatae. Facilis quibusdam
            veniam excepturi perspiciatis dicta natus quis, corporis quidem qui
            debitis inventore! Sed eum doloremque delectus.
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
                    <div class="grey--text mr-auto">user name</div>
                    <v-rating
                      :value="4.5"
                      color="amber"
                      dense
                      half-increments
                      readonly
                      size="14"
                    ></v-rating>
                    <div class="grey--text ms-1">4.5</div>
                  </v-card-title>
                  <v-card-text>
                    <div>review</div>
                  </v-card-text>
                </v-card>
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
  data: () => ({
      date: (new Date(Date.now() - (new Date()).getTimezoneOffset() * 60000)).toISOString().substr(0, 10),
      menu: false,
      modal: false,
      menu2: false,
    }),
};
</script>

<style>
</style>