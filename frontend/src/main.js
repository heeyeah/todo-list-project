import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
// import VueSweetalert2 from 'vue-sweetalert2'
//import VueResource from 'vue-resource'
import App from './App.vue'
import 'expose-loader?$!expose-loader?jQuery!jquery'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'


// ajax
import axios from 'axios'

// window.Vue = require('vue')
//import VueTags from 'vue-tags'

Vue.config.productionTip = false

Vue.prototype.$http=axios // ?
Object.defineProperty(Vue.prototype, '$axios', {value: axios})

// Vue.component('input-tags', VueTags)
Vue.use(BootstrapVue)
//Vue.use(VueResource)

const options = {
  confirmButtonColor: '#41b882',
  cancelButtonColor: '#ff7674'
}

// Vue.use(VueSweetalert2, options)

new Vue({
  render: h => h(App),
}).$mount('#app')
