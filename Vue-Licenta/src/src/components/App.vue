<template>
  <div class="main-class">
    <router-view></router-view>

    <div class="navbar navbar-dark bg-dark">
      <div class="avatar" style="margin:0 auto;">
        <div class="inainteDePoza">
          <div class="aranjezaFrumos">
            <img src="../assets/icons/avatar.png" alt="Error" height="28" width="28">
            </div>
          <div class="userName">
            <label align="center" class="title">
              <strong>
                <font color="red">The current user is : {{userInfo}}</font>
              </strong>
            </label>
          </div>
        </div>
        <PercentBar style="float: left; display:block; width:65%"/>
        <br>
      </div>
    </div>
    <GoogleMap/>
  </div>
</template>

<script>
import { mapGetters, mapActions, mapMutations } from "vuex";
import GoogleMap from "./GoogleMap.vue";
import index from "../index.js";
import axios from "axios";
import Drivers from "./Drivers.vue";
import Discount from "./Discount.vue";
import VueNativeWebSock from "vue-native-websocket";

module.exports = {
  name: "Regular",
  computed: {
    ...mapGetters({
      auth: "auth",
      userInfo: "userInfo",
      // grantAccessDialog: 'grantAccessDialog',
      notificationItem: "notificationItem"
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
 /* @import url("vue-material/dist/vue-material.min.css");
 @import url("vue-material/dist/theme/default.css"); */
div {
  margin-top: 5px;
}


body {
  height: 1000px;
  width: 100%;
}
.main-class {
  background-image: url("http://pm1.narvii.com/7066/2067b61f2fc4a700953c1087ed5995fa423c8cf5r5-480-854_00.jpg")
    center no-repet;
}
.md-large {
  margin-left: 10%;
  width: 1%;
}
div.main-class {
  margin-top: 1%;
  margin-left: 15px;
  margin-right: 15px;
}
.avatar {
  margin-left: 15px;
  margin-right: 15px;
}
.inainteDePoza {
  width: 35%;
  display: block;
  float: left;
  height: 20px;
}
.md-avatar img{
  width: 40%;
  height: 40%;
}
.md-avatar.md-large{
   width: 0%;
  height: 0%;
}
.md-avatar md-large md-theme-default{
  width: 0%;
  height: 0%;
}
.userName {
  margin-top: 0%;
}
/* .navbar navbar-dark bg-dark {
  float: left;
} */
.aranjezaFrumos{
  margin-left: 10%;
}
/* .navbar navbar-dark bg-dark {
  width: 100%;
} */
</style>
