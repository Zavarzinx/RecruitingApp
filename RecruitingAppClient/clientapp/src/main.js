import Vue from 'vue';
import App from './App.vue'
import {router} from './router'
import BootstrapVue from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import store from './store'
import VueRouter from "vue-router";
import InstantSearch from 'vue-instantsearch';
import VeeValidate from "vee-validate";
import {Vuelidate} from "vuelidate";

Vue.config.productionTip = false

// Bootstrap
Vue.use(VeeValidate);
Vue.use(BootstrapVue)
Vue.use(VueRouter)
Vue.use(Vuelidate);
Vue.use(InstantSearch);

const app = new Vue({
  store,
  router,
  render: h => h(App),
}).$mount('#app');