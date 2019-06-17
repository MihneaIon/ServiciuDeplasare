<template>
  <div>
    <div class="seteazaTextul">
      <strong style="text-align: center">discount</strong>
    </div>
    <div class="bar">
      <div
        class="progress"
        v-bind:style="{'background':color ,
     'width':amount+'%'}"
      >{{amount}} %</div>
    </div>
  </div>
</template>

<script src="https://cdn.jsdelivr.net/npm/vue"></script>

<script>
import { mapGetters, mapMutations, mapActions } from "vuex";
import axios from "axios";
import store from "../store";
import tokenDeAcces from "../keycloak/header";
export default {
  name: "PercentBar",
  data: () => ({
    amount: 0,
    color: "#00cc00"
  }),
  mounted() {
    setTimeout(() => this.sendClientNameToServer(), 3000);
    setTimeout(() => this.getMyClientFidelity(), 3000);
    setTimeout(() => this.postMyClientName(), 4000);
  },
  created() {
    // this.getMyClientFidelity();
  },
  methods: {
    sendClientNameToServer: async function() {
      console.log(
        typeof this.$store.state.security.auth.idTokenParsed,
        "tipul"
      );
      if (this.$store.state.security.auth.idTokenParsed !== undefined) {
        console.log(
          "acesta este numele",
          this.$store.state.security.auth.idTokenParsed.given_name
        );
        const clientName = {
          position: {
            nameOfClient: this.$store.state.security.auth.idTokenParsed
              .given_name
          },
          type: "authenticated"
        };
        this.$socket.sendObj({ data: clientName });
      } else {
        setTimeout(() => this.postMyClientName(), 4000);
        console.log("Hopa");
        //sendClientNameToServer();
      }
    },

    //Authorization Bearer <aceess_token>

    async postMyClientName() {
      var keycloakAuth = tokenDeAcces;
      console.log(keycloakAuth, ".................");
      var config = keycloakAuth;
      var scop = this;
      // const url =
      //   "https://ec2-18-196-29-101.eu-central-1.compute.amazonaws.com:8082/fidelity/fidelity";
      const url = "https://localhost:8082/fidelity/fidelity";
      const clientFidelity = {
        // numberOfRoads: 0,
        clientName: this.$store.state.security.auth.idTokenParsed.given_name
      };
      axios
        .post(url, clientFidelity, config)
        .then(response => {})
        .catch(e => {
          this.errors.push(e);
        });
    },
    async getMyClientFidelity() {
      // const url = "https://ec2-18-196-29-101.eu-central-1.compute.amazonaws.com:8082/fidelity/getFidelity";
      const url = "https://localhost:8082/fidelity/getFidelity";
      axios
        .get(url)
        .then(response => {
          console.log("acestea sunt datele", response.data);
          this.amount = response.data.numbersOfRoads;
        })
        .catch(e => {
          console.log('Exceptie , tabela era goala')
          this.amount = 0;
        });
    },
    // getMyClientFidelity() {
    //   axios
    //     .get("http://localhost", {
    //       withCredentials: true
    //     })
    //     .then(res => {
    //       console.log(res);
    //     })
    //     .catch(error => {
    //       console.log("erro", error);
    //     });
    // },
    show() {
      this.$modal.show("discount");
    },
    hide() {
      this.$modal.hide("discount");
    }
  }
};
</script>

<style>
.seteazaTextul {
  text-align: center;
}
.box {
  height: 3px;
  width: 50%;
  padding: 20%;
  margin: 20px auto;
  background: #f3f3f3;
  box-shadow: 3px 3px 3px 3px rgba(0, 0, 0, 0.1);
}
.bar {
  width: 100%;
  background: #dfdfdf;
  overflow: hidden;
}
.progress {
  float: left;
  padding: 5px;
}
</style>
