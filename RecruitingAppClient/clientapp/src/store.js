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
    isAdmin(){
     return state.Admin === JSON.parse(localStorage.getItem("roles")).find(x=>x === 'ADMIN')
    },
    isRecruiter(){
     return state.Recruiter === JSON.parse(localStorage.getItem("roles")).find(x=>x === 'RECRUITER')
    },
    isLoggedIn(){
     return state.User === JSON.parse(localStorage.getItem("roles")).find(x=>x === 'USER')
    }
  },
  getters:{
  }
})