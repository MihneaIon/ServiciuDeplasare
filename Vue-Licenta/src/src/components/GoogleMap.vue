<template>
  <div id="google-app">
    <!-- drawer options -->
    <vue-drawer-layout
      class="drawer-layout"
      style=" background-color:#aaa; margin-top:7%"
      ref="drawer"
      :drawer-width="350"
      :enable="true"
      :animatable="true"
      :z-index="0"
      :drawable-distance="Math.floor(20/3)"
      :content-drawable="true"
      :backdrop="true"
      :backdrop-opacity-range="[0,0.5]"
      @mask-click="handleMaskClick"
    >
      <div class="drawer-mask" @click="handleMaskClick" :style="{'opacity':backdropOpacity}"></div>
      <div class="drawer-content" slot="drawer" v-if="drowerVisibility">
        <Drivers></Drivers>
        <!-- drawer-content -->
      </div>
      <div class="harta" slot="content">
        <!--Here I color the div  -->
        <!-- main-content -->
        <div v-show="isTrue" id="content">
          <!-- atentie aici !!!! -->
          <div class="continatorDeButoane">
            <div class="aspirina">
              <gmap-autocomplete @place_changed="setPlace"></gmap-autocomplete>
              <button
                class="button-location-client"
                @click="addMarkerForACustomDestination"
              >Set Leaving</button>
              <button class="curentPosition" @click="addMarkerForCurrentPosition"></button>
              <br>
              <gmap-autocomplete @place_changed="setPlace"></gmap-autocomplete>
              <button class="button-location-destination" @click="addMarker">My Destination</button>
            </div>
            <div class="butoaneDreapta">
              <!-- <label style="margin-left: 0%"> -->
              <button class="button-utility" @click="displayClosestDrivers">See Drivers</button>
              <!-- </label> -->
              <!-- <label style="margin-left: 0%"> -->
              <span></span>
              <button class="button-utility" @click="getRoute">Set the Road</button>
              <!-- </label> -->
            </div>
          </div>
        </div>
        <div id="map">
          <modalprice style="width:300px; height: 100px"></modalprice>
          <gmap-map
            class="MyMap"
            ref="mapRef"
            v-bind:center="center"
            v-bind:zoom="14"
            style="width:100%; height:100%; margin-top: 20px"
          >
            <!-- desenez linia client -> destinatie -->
            <gmap-polyline
              v-bind:path="auxLatLngs"
              v-bind:options="{ strokeColor:'#008000'}"
              v-bind:type="type"
              ref="mapRef"
            ></gmap-polyline>
            <!-- desenez linia sofer -> client -->
            <gmap-polyline
              v-bind:path="driverLatLngs"
              v-bind:options="{ strokeColor:'#3104B4'}"
              v-bind:type="type"
              ref="taxiRef"
            ></gmap-polyline>

            <!-- marker pentru pozitia clientului -->
            <gmap-marker
              ref="myMarker"
              v-bind:key="cars.color"
              v-for="(m, cars) in markers"
              v-bind:position="m.position"
              v-bind:icon="{url:'https://www.freeiconspng.com/uploads/pic--home-address-symbol-png-29.png',scaledSize: { width: 20, height: 30 } }"
              @click="clickOnTheCarForMoreDetails(m)"
              v-bind::clickable="true"
              v-bind::draggable="true"
            />

            <!-- marker pentru destinatie -->
            <gmap-marker
              v-bind:key="cars.driverName"
              v-for="(m, cars) in destinationMarker"
              v-bind:position="m.position"
              @click="clickOnTheCarForMoreDetails(m)"
              v-bind::clickable="true"
              v-bind::draggable="true"
            />
            <!-- markerele pentru soferi -->
            <gmap-marker
              v-bind:key="idx"
              v-for="(m, idx) in carMarker"
              v-bind:position="m.position"
              v-bind:icon="{url: 'https://www.freeiconspng.com/uploads/transport-car-icon--android-iconset--icons8-8.png', scaledSize: { width: 20, height: 25 }}"
              @click="clickOnTheCarForMoreDetails(m)"
              v-bind::clickable="true"
              v-bind::draggable="true"
            ></gmap-marker>
            <!-- drumul de la client la destinatie -->
            <gmap-marker
              v-bind:key="cars.lng"
              v-for="(m, cars) in vectorDePozitiiIntermediare"
              v-bind:position="m.position"
              v-bind:icon="{url: 'https://www.freeiconspng.com/uploads/auto-car-coupe-sport-top-view-icon-0.png', scaledSize: { width: 20, height: 25 }}"
              v-bind::clickable="true"
              v-bind::draggable="true"
            ></gmap-marker>
          </gmap-map>
        </div>
      </div>
    </vue-drawer-layout>
  </div>
</template>
     
<script>
import { mapGetters, mapMutations, mapActions } from "vuex";
import axios from "axios";
import { gmapApi } from "vue2-google-maps";
import Drivers from "./Drivers.vue";
import invoice from "../invoice";
import web3 from "../web3";
export default {
  components: {
    Drivers
  },
  props: {
    drawerWidth: {
      type: Number
    },
    drawableDistance: {
      type: Number
    },
    contentDrawable: {
      type: Boolean,
      default: false
    },
    backdrop: {
      type: Boolean,
      default: true
    },
    backdropOpacityRange: {
      type: Array,
      default: function() {
        return [0, 0.4];
      },
      validator: function(value) {
        let [min, max] = value;
        return min < max && min >= 0 && max <= 1;
      }
    },
    enable: {
      type: Boolean,
      default: true
    },
    animatable: {
      type: Boolean,
      default: true
    },
    reverse: {
      type: Boolean,
      default: false
    }
  },
  data() {
    return {
      map: null,
      center: { lat: 45.6524567, lng: 25.5264229 }, // Brasov
      markers: [], // pun markerul pentru locatie clientului
      destinationMarker: [], // pun marker-ul pentru destinatia unde vreau sa ajung
      places: [], // pun locul pe care il setez ca si origine, pentru a imi recalcula harta
      // !!!!!!!!!!!!!!!!!!!!!!!!!!!!!
      // intermediateRoad: [],
      vectorDePozitiiIntermediare: [],
      errors: [],
      currentPlace: null,
      carMarker: [], // pun markere-le pentru masini
      comutator: false,
      menuVisible: false,
      comutatorPentruRuta: false,
      masina: [],
      type: "roadmap",
      optiuneSoferi: false,
      directionService: null,
      directionsDisplay: null,
      auxLatLngs: [],
      driverLatLngs: [],
      drumSoferClient: [],
      origin: {},
      destination: {},
      startLocation: {
        latlng: {},
        address: {}
      },
      endLocation: {
        latlng: {},
        address: {}
      },
      theClientClickedOnTheSetRoute: true,
      amountOfEtherum: {},
      displayTheMouvementOfTheChoosenCar: 0,
      // variabile pentru drawer
      isTrue: true,
      width: this.drawerWidth,
      distance: this.drawableDistance,
      pos: 0,
      visible: false,
      moving: false,
      willChange: false,
      esckey: false,
      drowerVisibility: false
    };
  },
  created() {},
  mounted() {
    this.geolocate();
    this.loadingTheContractBetweenDriverAndCustomer();
    this.displayTheMoveOfTheDriver();
  },
  computed: {
    ...mapGetters({ cars: "cars" }),
    backdropOpacity() {
      const { backdropOpacityRange, pos, width } = this,
        [min, max] = backdropOpacityRange;
      return min + max * (pos / width) || 0;
    }
  },
  methods: {
    sendDriverPosition: function() {
      // let pozitiiFin = this.carMarker.map(
      //       pozitieIntermediara => {
      //         pozitieIntermediara.position = {
      //           lat: pozitieIntermediara.lat,
      //           lng: pozitieIntermediara.lng,
      //         },
      //         pozitieIntermediara.type= 'client'
      //       }
      //     );
      
      const pozitie = {
        position: {
          lat: this.carMarker[0].position.lat,
          lng: this.carMarker[0].position.lng
        },
        type: "client"
      };
      console.log("acesata este mirifica pozitie", pozitie);
      //this.$socket.send(this.carMarker);
      this.$socket.sendObj({ data: pozitie });
    },
    setPlace(place) {
      this.currentPlace = place;
    },
    removeUnselectedDrivers(index) {
      this.$delete(this.carMarker, index);
    },
    // cele 2 metode de mai jos le folosesc pentru a imi insera valori in  array-uri
    insertIntoAuxLatVenth(value) {
      this.auxLatLngs.push(value);
    },
    insertIntoDriverLatVenth(value) {
      this.driverLatLngs.push(value);
    },
    show() {
      this.$modal.show("price-modal");
    },
    hide() {
      this.$modal.hide("price-modal");
    },
    deleteUnselectedDriversMarkers(index) {
      // sterg din vector si nu mai am dimensiunea totala mai testez functia
      let numeberOfCars = this.carMarker.length;
      let i = 0;
      if (this.carMarker.length > 1) {
        while (this.carMarker.length !== 1 && i === 0) {
          if (
            index.lat !== this.carMarker[i].position.lat &&
            index.lng !== this.carMarker[i].position.lng
          ) {
            this.removeUnselectedDrivers(i);
          } else {
            if (this.carMarker.length === 2) {
              i = 1;
              this.removeUnselectedDrivers(i);
            } else {
              this.removeUnselectedDrivers(1);
              this.removeUnselectedDrivers(2);
            }
          }
        }
      }
    },
    displayClosestDrivers() {
      this.$store.dispatch("getAllCars");
      this.carMarker = this.cars;
      this.optiuneSoferi = true;
      console.log(this.carMarker);
      return this.carMarker;
    },
    selectYourFavouriteDriver() {
      var aux;
      aux = this.$store.state.tasks.selectedCar;
    },
    // getRoute(){
    //   var scope = this
    //   scope.makeAroute(scope.markers,scope.destinationMarker);
    //   this.$store.dispatch("getAllCars");
    //   actualContext.$modal.show("price-modal");
    // },
    getRoute: function() {
      if (this.theClientClickedOnTheSetRoute) {
        this.$refs.mapRef.$mapObject;
        console.log(" afisez mapObject", this.$refs.mapRef.$mapObject);
        this.directionService = new google.maps.DirectionsService();
        this.directionsDisplay = new google.maps.DirectionsRenderer();
        this.directionsDisplay.setMap(this.$mapObject);
        var actualContext = this;
        if (this.markers === undefined) {
          window.alert("no location selected");
        }
        console.log(this.markers);
        if (this.markers.length != 0) {
          this.origin = {
            lat: this.markers[0].position.lat,
            lng: this.markers[0].position.lng
          };
        }
        if (this.destinationMarker === undefined) {
          window.alert("destination not selected");
        }
        if (this.destinationMarker.length != 0) {
          this.destination = {
            lat: this.destinationMarker[0].position.lat,
            lng: this.destinationMarker[0].position.lng
          };
        }
        actualContext.directionService.route(
          {
            // call back asteapta raspuns de la functie
            origin: {
              lat: this.markers[0].position.lat,
              lng: this.markers[0].position.lng
            },
            destination: {
              lat: this.destinationMarker[0].position.lat,
              lng: this.destinationMarker[0].position.lng
            },
            travelMode: "DRIVING"
          },
          async function(response, status) {
            if (status === "OK") {
              actualContext.directionsDisplay.setDirections(response),
                console.log("raspunsul:", response);
              // desenez traseul
              var legs = response.routes[0].legs;
              for (var i = 0; i < legs.length; i++) {
                if (i == 0) {
                  actualContext.startLocation.latlng = legs[i].start_location;
                  actualContext.startLocation.address = legs[i].start_address;
                }
                actualContext.endLocation.latlng = legs[i].end_location;
                actualContext.endLocation.address = legs[i].end_adress;
                var steps = legs[i].steps;
                for (var j = 0; j < steps.length; j++) {
                  var nextSegment = steps[j].path;
                  for (var k = 0; k < nextSegment.length; k++) {
                    actualContext.insertIntoAuxLatVenth(nextSegment[k]);
                  }
                }
              }
              await actualContext.getTheCost();
              // await actualContext.checkForEtherumMoney();
              actualContext.theClientClickedOnTheSetRoute = false;
              await actualContext.$modal.show("price-modal");
              await actualContext.$store.dispatch("getAllCars");
            } else {
              console.log("Directions request failed due to " + status);
            }
          }
        );
        // actualContext.$modal.show("price-modal");
      }
    },
    async loadingTheContractBetweenDriverAndCustomer() {
      const driverAccount = await invoice.methods.driver().call();
      const clients = await invoice.methods.getClients().call();
      const balance = await web3.eth.getBalance(invoice.options.address);
      console.log("e bine");
      this.$store.commit("sendDriverContract", driverAccount);
      this.$store.commit("sendBalance", balance);
      this.$store.commit("sendClientsContract", clients);
    },
    payForTheInvoice() {
      this.$store.state.tasks.value = this.$store.state.tasks.thePrice;
    },
    // async checkForEtherumMoney() {
    //   window.ethereum.enable();
    //   const clients = await invoice.methods.getClients().call();
    //   const balance = await web3.eth.getBalance(invoice.options.address);
    //   const accounts = await web3.eth.getAccounts();

    //   console.log(this.$store.state.tasks.thePrice,"sdfkdsfsdkskd")
    //   console.log(this.$store.state.tasks.thePrice/720,"boom")
    //   await invoice.methods.callACab().send({
    //     from: accounts[0],
    //     value: web3.utils.toWei((this.$store.state.tasks.thePrice/720)+"", "ether"),
    //   });
    //   this.$store.commit("sendBalance", balance);
    //   this.$store.commit("sendClientsContract", clients);
    //   this.amountOfEtherum = this.$store.state.tasks.thePrice/720;
    // },
    // async payForTrevel(){
    //   window.ethereum.enable();
    //   const accounts = await web3.eth.getAccounts();
    //   console.log(accounts, "aici avem conturi");
    //   // const variabila=  web3.utils.toWei("0.011", "ether");
    //   // console.log(variabila);
    //   await invoice.methods.payForACab().send({
    //     to: accounts[1],
    //   })
    // },
    addMarkerForACustomDestination() {
      if (this.currentPlace) {
        const marker = {
          lat: this.currentPlace.geometry.location.lat(),
          lng: this.currentPlace.geometry.location.lng()
        };
        this.markers.push({
          position: marker
        });
        this.places.push(this.currentPlace);
        this.center = marker;
        this.postMyLocation();
        this.currentPlace = null;
      }
    },
    addMarker() {
      if (this.currentPlace) {
        const marker = {
          lat: this.currentPlace.geometry.location.lat(),
          lng: this.currentPlace.geometry.location.lng()
        };
        this.destinationMarker.push({
          position: marker
        });
        this.places.push(this.currentPlace);
        this.center = marker;
        this.postMyDestination();
        this.currentPlace = null;
      }
    },
    addMarkerForCurrentPosition: function() {
      var aux = this.putAMarkerOnTheCurrentLocation();
      const marker = {
        lat: aux.lat,
        lng: aux.lng
      };
      console.log("Marker", marker);
      this.markers.push({ position: marker });
      this.places.push(this.currentPlace);
      this.center = marker;
      this.postMyLocation();
      this.currentPlace = null;
    },
    // Functie care imi ia locatia curenta
    geolocate: function() {
      navigator.geolocation.getCurrentPosition(position => {
        this.center = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };
        console.log(this.center.lat, "sdfd", this.center.lng);
      });
    },
    // getTaxiRoute(){
    //   var scope = this
    //   this.makeAroute(scope.carMarker, scope.markers);
    // },
    getTaxiRoute: function() {
      this.$refs.taxiRef.$mapObject;
      console.log(" afisez mapObject", this.$refs.taxiRef.$mapObject);
      this.directionService = new google.maps.DirectionsService();
      this.directionsDisplay = new google.maps.DirectionsRenderer();
      this.directionsDisplay.setMap(this.$mapObject);
      var actualContext = this;
      if (this.carMarker === undefined) {
        window.alert("no taxi selected");
      }
      console.log(this.markers);
      if (this.carMarker.length != 0) {
        this.origin = {
          lat: this.carMarker[0].position.lat,
          lng: this.carMarker[0].position.lng
        };
      }
      if (this.markers === undefined) {
        window.alert("destination not selected");
      }
      if (this.markers.length != 0) {
        this.destination = {
          lat: this.markers[0].position.lat,
          lng: this.markers[0].position.lng
        };
      }
      actualContext.directionService.route(
        {
          // call back asteapta raspuns de la functie
          origin: {
            lat: this.carMarker[0].position.lat,
            lng: this.carMarker[0].position.lng
          },
          destination: {
            lat: this.markers[0].position.lat,
            lng: this.markers[0].position.lng
          },
          travelMode: "DRIVING"
        },
        function(response, status) {
          if (status === "OK") {
            actualContext.directionsDisplay.setDirections(response);
            // desenez traseul
            var legs = response.routes[0].legs;
            for (var i = 0; i < legs.length; i++) {
              if (i == 0) {
                actualContext.startLocation.latlng = legs[i].start_location;
                actualContext.startLocation.address = legs[i].start_address;
              }
              actualContext.endLocation.latlng = legs[i].end_location;
              actualContext.endLocation.address = legs[i].end_adress;
              var steps = legs[i].steps;
              for (var j = 0; j < steps.length; j++) {
                var nextSegment = steps[j].path;
                for (var k = 0; k < nextSegment.length; k++) {
                  actualContext.insertIntoDriverLatVenth(nextSegment[k]);
                }
              }
            }
            actualContext.displayTheMouvementOfTheChoosenCar = 1;
            console.log('acesta este variabila pentru a imi simula miscarea', actualContext.displayTheMouvementOfTheChoosenCar)
          } else {
            console.log("Directions request failed due to " + status);
          }
        }
      );
    },
    putAMarkerOnTheCurrentLocation: function() {
      navigator.geolocation.getCurrentPosition(position => {
        this.center = {
          lat: position.coords.latitude,
          lng: position.coords.longitude
        };
        this.map = this.center;
      });
      return this.center;
    },
    toggle() {
      if (visible === undefined) visible = !this.visible;
      this.visible = visible;
      this.pos = visible ? this.width : 0;
      this.moving = true;
    },
    handleMaskClick() {
      //console.log("Salltare Jupane");
      if (this.moving) return;
      this.$emit("mask-click");
    },
    postMyLocation() {
      var scop = this;
      // const url = "https://ec2-18-196-29-101.eu-central-1.compute.amazonaws.com:8082/position/takeClientPosition";
      const url = "https://localhost:8082/position/takeClientPosition";
      const clientPosition = {
        lat: scop.markers[0].position.lat,
        lng: scop.markers[0].position.lng
      };
      // this.$store.state.tasks.socket.message = clientPosition => console.log(clientPosition)
      axios
        .post(url, clientPosition)
        .then(response => {})
        .catch(e => {
          this.errors.push(e);
        });
    },
    getMyCloserCars: function() {
      this.$store.dispatch("getAllCars");
      this.carMarker = this.cars;
      this.optiuneSoferi = true;
      console.log("asa trebuie trimis in backend", this.carMarker);
      // this.$socket.send(this.carMarker,'saltare jupane');
      console.log(this.carMarker);
      return this.carMarker;
    },
    postMyDestination() {
      var scop = this;
      // const url =
      //    "https://ec2-18-196-29-101.eu-central-1.compute.amazonaws.com:8082/position/takeDestinationPosition";
      const url = "https://localhost:8082/position/takeDestinationPosition";
      const clientDestinationPosition = {
        lat: scop.destinationMarker[0].position.lat,
        lng: scop.destinationMarker[0].position.lng
      };
      axios
        .post(url, clientDestinationPosition)
        .then(response => {
          console.log("destinatia", response);
        })
        .catch(e => {
          this.errors.push(e);
        });
    },
    getMyIntermdiateRoad() {
      var scop = this;
      // const url = "https://ec2-18-196-29-101.eu-central-1.compute.amazonaws.com:8082:8082/distanta/getIntermediateRoad";
      const url = "https://localhost:8082/distanta/getIntermediateRoad";
      axios
        .get(url)
        .then(response => {
          console.log(response.data);
          console.log("raspunsul", scop.vectorDePozitiiIntermediare);
          console.log(
            "Acesta este vectorul de pozitii intermediare",
            scop.vectorDePozitiiIntermediare
          );
        })
        .catch(e => {
          this.errors.push(e);
          console.log("Eroare!!!!!!!!!!!!!!!!!!!");
        });
    },
    getTheCost() {
      // const url = "https://ec2-18-196-29-101.eu-central-1.compute.amazonaws.com:8082/price/settleThePrice";
      const url = "https://localhost:8082/price/settleThePrice";
      axios
        .get(url)
        .then(response => {
          this.$store.commit("setThePrice", response.data);
          console.log(this.$store.state.tasks.thePrice, "alabama slam");
        })
        .catch(e => {
          this.errors.push(e);
          console.log("Eroare!!!!!!!!!!!!!!!!!!!");
        });
    },
    clickOnTheCarForMoreDetails(masina) {
      this.$store.dispatch("getAllCars");
      console.log("culoarea masinii", this.masina);
      if (!this.esckey) {
        this.$refs.drawer.toggle();
      }
      this.esckey = true;
      this.$store.commit("setCar", masina);
      this.$store.commit("selectCar", masina);
      this.comutator = true;
      this.drowerVisibility = true;
      const that = this;
      if (this.comutatorPentruRuta === false) {
        this.comutatorPentruRuta = true;
      }
      document.addEventListener("keyup", function() {
        if (that.esckey == true) {
          that.$refs.drawer.toggle();
          that.esckey = false;
          that.$store.dispatch("getAllCars");
          that.drowerVisibility = false;
          that.comutator = false;
          that.getMyIntermdiateRoad();
          //that.sendDriverPosition(); //// Atentie aici!!
          if (that.$store.state.tasks.driverSelected === true) {
            console.log("iuyyy", that.$store.state.tasks.driverSelected);
            that.getTaxiRoute();
          }
        }
      });
      this.deleteUnselectedDriversMarkers(
        this.$store.state.tasks.selectedCar.position
      );
      // this.genereazaDrumIntermediar()
      let destinatie = this.markers;
      console.log("destinatia", destinatie);
      let drumFragmentat;
      drumFragmentat = this.$store.state.tasks.pozitiiIntermediare;
      console.log("drum fragmentat", drumFragmentat);
      

      // while (destinatie[0].position.lat !== drumFragmentat.lat) {
      //   console.log("am intrat in while", drumFragmentat);
      //   this.carMarker.push(drumFragmentat);
      //   console.log("markerul", this.carMarker);
      // }
    },
    displayTheMoveOfTheDriver() {
      if (this.displayTheMouvementOfTheChoosenCar === 1) {
        console.log(this.carMarker,'acesta este marker bosssssss')
        console.log('se misaca :)))))))))')
        this.$nextTick(function() {
          window.setInterval(() => {
            this.carMarker.position = this.$store.state.tasks.pozitiiIntermediare;
          }, 5000);
        });
      }
      //this.cars.position = this.$store.state.tasks.pozitiiIntermediare
    },
    // afisazaOptiunile() {
    //   var optiuni = this.displayClosestDrivers();
    //   this.optiuneSoferi = true;
    // },
    // calculateAndDisplayRoute() {}
  },
  // async genereazaDrumIntermediar(){
  //     let destinatie = this.markers;
  //     console.log('destinatia',destinatie)
  //     let drumFragmentat;
  //     drumFragmentat = this.$store.state.tasks.pozitiiIntermediare;
  //     console.log('drum fragmentat',drumFragmentat)
  //     let auxDrumFragmentat = drumFragmentat
  //     if(destinatie[0].position.lat !== car){
  //       console.log('si aici am intrat')
  //       this.carMarker.push(drumFragmentat)
  //     }

  // },
  watch: {
    moving() {
      if (!this.animatable) this.moving = false;
    },
    willChange() {
      if (!this.animatable) this.willChange = false;
    },
    esckey() {}
  },
  ...mapActions(["getAllCars"]),
  ...mapMutations({ alegeMasina: "tasks/selectCar" }),
  moveRate() {
    const { width, distance } = this;
    return distance / width;
  }
};
</script>

<style>
/* Set the size of the div element that contains the map */
#map {
  height: 100%; /* The height is 400 pixels  Am modificat  */
  width: 100%; /* The width is the width of the web page */
}
#google-app {
  margin-top: -5px;
  height: 100%; /* The height is 400 pixels  Am modificat  */
  width: 100%;
}
body {
  height: 900px;
  width: 100%;
}
.page-container {
  min-height: 300px;
  overflow: hidden;
  position: relative;
  border: 1px solid rgba(#000, 0.12);
}

.md-drawer {
  width: 230px;
  max-width: calc(100vw - 125px);
}

.md-content {
  padding: 16px;
}
.drawer-layout {
  margin-top: 4%;
}
.drawer-mask {
  position: inherit;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  background-color: #000;
  z-index: 818;
}
h2 {
  font-weight: normal;
}
ul {
  list-style-type: none;
  padding: 20;
  width: 70%;
  margin: auto;
}
li {
  display: inline-block;
  margin: 0 10px;
}
a {
  color: #aaaaaa;
}
p {
  font-size: 15px;
  font-family: "Lucida Sans", "Lucida Sans Regular", "Lucida Grande",
    "Lucida Sans Unicode", Geneva, Verdana, sans-serif;
}
label {
  vertical-align: middle;
  /* margin-left: 15%; */
  margin-top: 20%;
  height: 30px;
  width: 70px;
}
.text-left {
  text-align: left;
  font-weight: bold;
  text-align: left;
  float: left;
}
.text-right {
  text-align: right;
  font-weight: bold;
  text-align: left;
  float: right;
}
.jan {
  margin-top: 55px;
  margin-left: 15px;
  margin-right: 15px;
}
.harta {
  margin-top: 2%;
  margin-right: 5%;
  margin-left: 5%;
  height: 100%;
}
.drawer-wrap {
  margin-top: 75px;
}
.content-wrap {
  margin-top: 2%;
  margin-left: 15px;
  margin-right: 15px;
  padding: 10px;
  margin: 3px;
  background-image: url("../assets/icons/background.png");
}
.vue-drawer-layout {
  margin-top: 5%;
}
.curentPosition {
  background-image: url("../assets/icons/target.svg");
  background-position: center;
  background-repeat: no-repeat;
  font-size: 10px;
  border-radius: 16px;
  padding: 14px;
  height: 20px;
}

.button-utility {
  background-color: #309cf59c; /* Green */
  border: none;
  color: black;
  padding: 10px;
  text-align: center;
  text-decoration: none;
  display: inline-block;
  font-size: 14px;
  margin: 2px 2px;
  border: 1px solid #0000009c;
  cursor: pointer;
  width: 100%;
  height: 100%;
}
.button-utility:hover {
  background-color: #0570d4;
}
.button-location-client {
  background-color: #309cf59c; /* Green */
  border: 1px solid #0000009c;
  padding: 5px;
  color: black;
  cursor: pointer;
  width: 35%;
}
.button-location-client:hover {
  background-color: #0570d4;
}

.button-utility:active {
  background-color: #90dddd9c;
  box-shadow: 0 5px #666;
  transform: translateY(4px);
}
.button-location-destination {
  background-color: #309cf59c; /* Green */
  border: 1px solid #0000009c;
  padding: 5px;
  color: black;
  cursor: pointer;
  width: 35%;
}
.button-location-destination:hover {
  background-color: #0570d4;
}
.gmap-autocomplete {
  width: 25%;
}

.MyMap {
  height: 100%;
}
.butoaneDreapta {
  float: left;
  display: block;
  width: 50%;
}
.aspirina {
  width: 50%;
  height: 100px;
  display: block;
  float: left;
}
.continatorDeButoane {
  height: 100px;
  width: 100%;
}

.gmap-autocomplete {
  height: 15%;
}

@media screen and (max-width: 900px) {
}

@media screen and (max-width: 500px) {
  .harta {
    margin-top: 10%;
    margin-left: 0%;
    margin-right: 0%;
  }
  .content-wrap {
    margin-top: 0%;
    margin-left: 5px;
    margin-right: 5px;
    padding: 10px;
    margin: 3px;
    width: 90%;
    background-image: url("../assets/icons/background.png");
  }
  .button-location-client {
    width: 20%;
    height: 10%;
  }
  .button-location-destination {
    width: 20%;
    height: 10%;
  }
  .button-utility {
    width: 20%;
    height: 20%;
  }
}
</style>
