<template>
  <div>
    <table>
      <thead></thead>
      <tbody>
        <th scope="col">Driver position</th>
        <td>{{car.position}}</td>
      </tbody>
      <tbody>
        <th scope="col">Color</th>
        <td>{{car.color}}</td>
      </tbody>
      <tbody>
        <th scope="col">Driver Name</th>
        <td>{{car.driverName}}</td>
      </tbody>
      <label>
        <span></span>
        <button @click="trimiteSoferulSelectat(selectedCar)">Il aleg</button>
      </label>
    </table>
    <div class="taxe">
      <img
        class="img-card"
        @click="appendThePayementMethode"
        src="../assets/icons/credit-card.png"
        alt="Eroare Credit card"
      >
    </div>
  </div>
</template>

<script>
import { mapGetters, mapMutations } from "vuex";
import invoice from "../invoice";
import web3 from "../web3";
export default {
  name: "Drivers",
  computed: {
    ...mapGetters({
      selectedCar: "cars"
    })
  },
  data() {
    return {
      driver: [],
      car: this.$store.state.tasks.selectedCar
    };
  },
  methods: {
    trimiteSoferulSelectat(selectedCar) {
      this.$store.commit("selectCar", selectedCar);
      console.log("am intrat in lista cu cei mai apropiati soferi");
      this.$store.commit("activeDriverSelected", true);
      this.appendThePayementMethode()
      return this.$store.commit("selectCar", selectedCar);
    },
    async checkForEtherumMoney() {
      window.ethereum.enable();
      const clients = await invoice.methods.getClients().call();
      const balance = await web3.eth.getBalance(invoice.options.address);
      const accounts = await web3.eth.getAccounts();

      console.log(this.$store.state.tasks.thePrice, "sdfkdsfsdkskd");
      console.log(this.$store.state.tasks.thePrice / 720, "boom");
      await invoice.methods.callACab().send({
        from: accounts[0],
        value: web3.utils.toWei(
          this.$store.state.tasks.thePrice / 720 + "",
          "ether"
        )
      });
      this.$store.commit("sendBalance", balance);
      this.$store.commit("sendClientsContract", clients);
      this.amountOfEtherum = this.$store.state.tasks.thePrice / 720;
    },
    async payForTrevel() {
      window.ethereum.enable();
      const accounts = await web3.eth.getAccounts();
      console.log(accounts, "aici avem conturi");
      // const variabila=  web3.utils.toWei("0.011", "ether");
      // console.log(variabila);
      await invoice.methods.payForACab().send({
        to: accounts[1]
      });
    },
    appendThePayementMethode() {
      this.checkForEtherumMoney();
      this.payForTrevel();
    }
  }
};
</script>

<style>

.taxe {
  text-align: left;
  margin-top: 0px auto;
  margin-left: 20%;
  width: 10%;
  height: 10%;
  border: 3px solid #73ad21;
  padding: 15px;
  float: inherit;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 8px solid #73ad21;
  box-shadow: 0 2px 8px rgba(3, 10, 3, 0.33);
  transition: all 0.3s ease;
  font-family: Helvetica, Arial, sans-serif;
}
.img-card{
  margin-left: -90px;
  margin-top: -30px;
  width: 50px;
  height: 50px;
}

</style>