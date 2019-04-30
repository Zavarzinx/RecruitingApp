import Vue from 'vue';
import App from './App.vue'
import {router} from './router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import store from './store'
import VueRouter from "vue-router";


Vue.config.productionTip = false

// Bootstrap
Vue.use(BootstrapVue)
Vue.use(VueRouter)

const app = new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app');