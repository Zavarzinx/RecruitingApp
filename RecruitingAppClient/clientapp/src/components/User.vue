<template>
    <div class="User">

 <div>User {{ $route.params.id }}</div>
 <b-form>
     <b-form-group id="input-group-2" label="User info:" label-for="input-2">
         <b-form-textarea
                 id="input-2"
                 v-model="user.username"
                 required
                 placeholder="Enter text"
         ></b-form-textarea>
         <b-form-textarea
                 id="input-2"
                 v-model="user.password"
                 required
                 placeholder="Enter text"
         ></b-form-textarea>
         <b-form-textarea
                 id="input-2"
                 v-model="user.email"
                 required
                 placeholder="Enter text"
         ></b-form-textarea>
         <b-form-textarea
                 id="input-2"
                 v-model="user.firstName"
                 required
                 placeholder="Enter text"
         ></b-form-textarea>
         <b-form-textarea
                 id="input-2"
                 v-model="user.lastName"
                 required
                 placeholder="Enter text"
         ></b-form-textarea>
         <b-form-textarea
                 id="input-2"
                 v-model="user.active"
                 required
                 placeholder="Enter text"
         ></b-form-textarea>
         <b-form-textarea
                 id="input-2"
                 v-model="user.roles"
                 required
                 placeholder="Enter text"
         ></b-form-textarea>

     </b-form-group>

     <b-button @click="getUser()"  variant="primary">Get user</b-button>
     <b-button @click="updateUser()"  variant="primary">Update user</b-button>
 </b-form>

</div>

</template>

<script>
import {AXIOS} from './http-common'
export default {
 name: 'User',
 data() {
     return {
         users:[],
         user:{
             id: 0,
             username:'',
             password:'',
             email:'',
             firstName:'',
             lastName:'',
             active:'',
             roles:[]
         }
     }
 },
 methods: {
     getUser() {
         console.log("route id " + this.$route.id)
         AXIOS.get('/admin/users/' + this.$route.params.id,
             {
                 headers: {
                     'Content-Type': 'application/json',
                     'Authorization': localStorage.token,
                     'Access-Control-Allow-Origin': "*"
                 }
             }).then(response => {
             this.user = response.data
             console.log(response.data)
                 .catch(e => {
                     this.errors.push(e)
                 })
         })
     },
     updateResume(){
         AXIOS.put('/resume/' + this.$route.params.id,
             {text: this.resume.text},
             {headers: {
                     'Content-Type': 'application/json',
                     'Authorization':localStorage.token,
                     'Access-Control-Allow-Origin': "*"
                 }}).then(response => {
             this.resume = response.data
             console.log(response.data)
                 .catch(e => {
                     this.errors.push(e)
                 })
         })
     }
 }
}
</script>

<style scoped>

</style>