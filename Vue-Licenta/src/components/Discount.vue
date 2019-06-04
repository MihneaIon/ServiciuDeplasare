<template>
  <modal name="discount" :width="300" :height="140" :clickToClose="true" :draggable="true">
    <div class="insideModal">
      <h3>Discount-ul este:</h3>
      <div>{{duration}}</div>
      <div></div>
      <div class="taxe">
        <img 
        class="img-card"
        
        src="../assets/icons/credit-card.png" alt="Eroare Credit card">
        <img
          class="img-taxe"
          src="https://www.freeiconspng.com/uploads/coins-money-icon-24.png"
        >
      </div>
      <!-- <div>
        <button @click="appendThePayementMethode">Platesti cu cardul ?</button>
      </div> -->
    </div>
  </modal>
</template>


<script>
import { mapGetters } from "vuex";
export default {
  name: "PriceModal",
  data() {
    return {
      time: 0,
      duration: 10000,
      clickToClose: true,
      taxe: 0
    };
  },
  methods: {
    
    
  }
};
</script>


<style>
.insideModal {
  width: 300px;
  height: 100px;
  margin: 0px auto;
  padding: 20px 20px;
  background-color: #fff;
  border-radius: 2px;
  box-shadow: 0 2px 8px rgba(0, 0, 0, 0.33);
  transition: all 0.3s ease;
  font-family: Helvetica, Arial, sans-serif;
}
h3 {
  margin-top: 0;
  color: #42b983;
}
.taxe {
  text-align: left;
  margin-top: 0px auto;
  margin-left: 20%;
  width: 10%; /* am modificat aici  era 10 % initial*/
  height: 10%;
  border: 3px solid #42b983;
  padding: 15px;
  float: inherit;
  padding: 20px 30px;
  background-color: #fff;
  /* border-radius: 8px solid #73ad21; */
  box-shadow: 0 2px 8px rgba(3, 10, 3, 0.33);
  transition: all 0.3s ease;
  font-family: Helvetica, Arial, sans-serif;
}
.img-taxe {
  margin-left: 100px;
  margin-top: -70px;
  width: 50px;
  height: 50px;
}
.img-card{
  margin-left: -90px;
  margin-top: -30px;
  width: 50px;
  height: 50px;
}
.price{
  margin-left: -10px;
  margin-top: -25px;
}
</style>

