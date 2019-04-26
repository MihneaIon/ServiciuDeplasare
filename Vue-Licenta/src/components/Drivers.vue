<template>
  <div>
    <table >
      <thead></thead>
      <tbody>
        <th scope="col">Driver position</th>
        <td>{{selectedCar.position}}</td>
      </tbody>
      <tbody>
        <th scope="col">Color</th>
        <td>{{selectedCar.color}}</td>
      </tbody>
      <tbody>
        <th scope="col">Driver Name</th>
        <td>{{selectedCar.driverName}}</td>
      </tbody>
      <label>
        <span></span>
        <button @click="trimiteSoferulSelectat(selectedCar)">Il aleg</button>
      </label>
    </table>
  </div>
</template>

<script>
import { mapGetters, mapMutations } from "vuex";
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
      car: {}
    };
  },
  methods: {
    trimiteSoferulSelectat(selectedCar) {
      this.$store.commit("selectCar", selectedCar);
      console.log('am intrat in lista cu cei mai apropiati soferi')
      this.$store.commit("activeDriverSelected", true);
      return this.$store.commit("selectCar", selectedCar);
    },
  }
};
</script>

<style>
</style>