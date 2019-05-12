import Vue from 'vue'
import Vuex from 'vuex'


Vue.use(Vuex)



export default new Vuex.Store({
  state: {
    Admin:'ADMIN',
    User:'USER',
    Recruiter:'RECRUITER',
    isLoggedIn:false,
    isAdmin:false,
    isRecruiter:false
  },
  mutations: {
    setAdmin (state, value) {
      state.isAdmin=value
    },
    setRecruiter(state, value){
      state.isRecruiter=value
    },
    setLoggedIn(state, value){
      state.isLoggedIn=value
    }
  },
  actions: {
    isAdmin() {
      console.log(localStorage.getItem("roles"))
      if (JSON.parse(localStorage.getItem("roles")) === null){
        return false
      }
      console.log("isadmin")
      console.log(JSON.parse(localStorage.getItem("roles")).find(x=>x === 'ADMIN') === 'ADMIN')
      return JSON.parse(localStorage.getItem("roles")).find(x=>x === 'ADMIN') === 'ADMIN'
    },
    isRecruiter() {
        console.log(localStorage.getItem("roles"))
      if (JSON.parse(localStorage.getItem("roles")) === null){
        return false
      }
      console.log("isrrec")
      console.log(JSON.parse(localStorage.getItem("roles")).find(x=>x === 'RECRUITER') === 'RECRUITER')
      return JSON.parse(localStorage.getItem("roles")).find(x=>x === 'RECRUITER') === 'RECRUITER'
          || JSON.parse(localStorage.getItem("roles")).find(x=>x === 'ADMIN') === 'ADMIN'
    },
    isLoggedIn() {
      console.log(localStorage.getItem("roles"))
      if (JSON.parse(localStorage.getItem("roles")) === null)
        return false
      console.log("isloggedin")
      console.log(JSON.parse(localStorage.getItem("roles")).find(x=>x === 'USER') === 'USER')
      return JSON.parse(localStorage.getItem("roles")).find(x=>x === 'USER') === 'USER'
    },
  },
  getters:{
    isAdmin() {
      console.log(localStorage.getItem("roles"))
      if (JSON.parse(localStorage.getItem("roles")) === null){
        return false
      }
      console.log(JSON.parse(localStorage.getItem("roles")).find(x=>x === 'ADMIN') === 'ADMIN')
      return JSON.parse(localStorage.getItem("roles")).find(x=>x === 'ADMIN') === 'ADMIN'
    },
    isRecruiter() {
      console.log(localStorage.getItem("roles"))
      if (JSON.parse(localStorage.getItem("roles")) === null){
        return false
      }
      console.log(JSON.parse(localStorage.getItem("roles")).find(x=>x === 'RECRUITER') === 'RECRUITER')
      return JSON.parse(localStorage.getItem("roles")).find(x=>x === 'RECRUITER') === 'RECRUITER'
          || JSON.parse(localStorage.getItem("roles")).find(x=>x === 'ADMIN') === 'ADMIN'
    },
    isUser() {
      console.log(localStorage.getItem("roles"))
      if (JSON.parse(localStorage.getItem("roles")) === null)
        return false
      console.log(JSON.parse(localStorage.getItem("roles")).find(x=>x === 'USER') === 'USER')
      return JSON.parse(localStorage.getItem("roles")).find(x=>x === 'USER') === 'USER'
    },
    getAdmin(){
      return this.state.Admin
    }
  }
})