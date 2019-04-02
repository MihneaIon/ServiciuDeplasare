<template>
  <modal name="price-modal" :width="300" :height="140" :clickToClose="true" :draggable="true">
    <div class="insideModal">
      <h3>Pretul este:</h3>
      <div></div>
      <div></div>
      <div class="taxe">{{this.displayThePrice()+ "lei"}}
        <img class="img-taxe" src="https://www.freeiconspng.com/uploads/coins-money-icon-24.png" />
      </div>
      
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
    displayThePrice() {
      console.log("vulturul ia prada ");
      this.taxe = this.$store.state.tasks.thePrice;
      if (this.taxe)
        return this.taxe;
    }
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
  margin-top:  0px auto;
  margin-left: 20%;
  width: 10%;
  height: 10%;
  border: 3px solid #73AD21;
  padding: 15px;
  float: inherit;
  padding: 20px 30px;
  background-color: #fff;
  border-radius: 8px solid #73AD21;
  box-shadow: 0 2px 8px rgba(3, 10, 3, 0.33);
  transition: all .3s ease;
  font-family: Helvetica, Arial, sans-serif;
}
.img-taxe {
      margin-left: 70px;
      margin-top: -65px;
      width:50px;
      height:50px;

}
</style>

