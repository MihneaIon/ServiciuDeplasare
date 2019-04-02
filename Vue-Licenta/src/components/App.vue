<template>
  <div class="jan">
    <router-view></router-view>

    <div>
      <div class="avatar">
        <md-avatar class="md-large">
          <img src="../assets/icons/avatar.png" alt>
        </md-avatar>
      </div>
      <label class="title">The current user is :</label>
      <p style="margin-left: 30px;">{{userInfo}}</p>

    <GoogleMap/>

    </div>
    
  </div>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import GoogleMap from "./GoogleMap.vue";
import index from "../index.js";
import axios from "axios";
import Drivers from "./Drivers.vue";
import VueNativeWebSock from 'vue-native-websocket'
//import Car from './Car.vue'

module.exports = {
  name: "Regular",
  computed: {
    ...mapGetters({
      auth: "auth",
      userInfo: "userInfo",
      // grantAccessDialog: 'grantAccessDialog',
      notificationItem: "notificationItem",
    })
  },
  components: {
    "app-googleMap": GoogleMap
    //GoogleMap, Car
  },
  watch: {
    currentPage() {
      this.$router.push("/googlemap");
    },
    auth(val) {
      if (val) {
        this.profileOpts[0].title = this.$store.state.security.auth.idTokenParsed.name;
        console.log(
          "----------------------------------",
          this.$store.state.security.auth.idTokenParsed.name
        );
        this.loadUserInfo();
        this.loadUsers();
        //this.devVersionMessage();
      }
    }
  },
  methods: {
    ...mapActions(["loadUserInfo", "loadUsers"]),
    ...mapMutations(["setGrantAccessDialog", "setNotificationItem"]),
    addTask() {
      this.setGrantAccessDialog(true);
    },
    setCurrentPage() {
      const path = window.location.pathname;
      switch (path) {
        case "":
        case "/":
          this.currentPage = 0;
          break;
        case "/governance":
          this.currentPage = 1;
          break;
        default:
          break;
      }
    },
    setCookie(name, value, days) {
      var expires = "";
      if (days) {
        var date = new Date();
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        expires = "; expires=" + date.toUTCString();
      }
      document.cookie = name + "=" + (value || "") + expires + "; path=/";
    },
    getCookie(name) {
      var nameEQ = name + "=";
      var ca = document.cookie.split(";");
      for (var i = 0; i < ca.length; i++) {
        var c = ca[i];
        while (c.charAt(0) == " ") c = c.substring(1, c.length);
        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
      }
      return null;
    },
    devVersionMessage() {
      this.$message({
        showClose: true,
        duration: 3500,
        message:
          "This is the development version of AI Flow, downtimes can occur."
      });
    },
    handleAction: function(item) {
      if (item === "Log Out") {
        this.logout();
      }
      if (item === this.$store.state.security.auth.idTokenParsed.name) {
        this.$router.push("/userProfile");
      }
      if (item === "Switch theme") {
        if (this.theme === "bright") {
          this.theme = "dark";
          this.setCookie("theme", this.theme, 99);
        } else {
          this.theme = "bright";
          this.setCookie("theme", this.theme, 99);
        }
      }
    },
    logout() {
      this.$store.state.security.auth.logout();
    }
  },
  data() {
    return {
      specialGroups: ["bdo_administrators", "bdo_developers"],
      userItem: ["Uber-app"],
      fullName: "",
      profileOpts: [
        {
          title: "User profile",
          icon: "x-icon-avatar"
        }
      ],
      theme: "bright"
    };
  }
};
</script>

<style>
div {
  margin-top: 5px;
}

label.title {
  margin-left: 30px;
}

body {
  height: 1000px;
  width: 100%;
}
.md-large {
  margin-left: 50px;
}
div.jan {
  margin-top: 15px;
  margin-left: 15px;
  margin-right: 15px;
}
.avatar {
  margin-top: 25px;
  margin-left: 15px;
  margin-right: 15px;
}
</style>
