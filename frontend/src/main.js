import Vue from 'vue'
import BootstrapVue from 'bootstrap-vue'
import VueSweetalert2 from 'vue-sweetalert2'
//import VueResource from 'vue-resource'
import App from './App.vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import 'expose-loader?$!expose-loader?jQuery!jquery'

// ajax
//import axios from 'axios'

Vue.config.productionTip = false

//Object.defineProperty(Vue.prototype, '$axios', {value: axios})

Vue.use(BootstrapVue)
//Vue.use(VueResource)

const options = {
  confirmButtonColor: '#41b882',
  cancelButtonColor: '#ff7674'
}

Vue.use(VueSweetalert2, options)

new Vue({
  render: h => h(App),
}).$mount('#app')
