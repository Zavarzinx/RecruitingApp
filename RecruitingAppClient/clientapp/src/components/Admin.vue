<template>
    <div class="Admin">
<div class="btn-toolbar">
    <b-button @click="getUsers()"  variant="primary">Get users</b-button>
</div>
<div class="well">
    <li v-for="item in users">
    <table class="table">
        <thead>
        <tr>
            <th>Profile</th>
            <th>#</th>
            <th>Username</th>
            <th>First name</th>
            <th>Last name</th>
            <th>Email</th>
            <th style="width: 36px;"></th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td><router-link :to="{ name: 'User', params: { id: item.id }}">Profile</router-link></td>
            <td>{{item.id}}</td>
            <td>{{item.username}}</td>
            <td>{{item.firstName}}</td>
            <td>{{item.lastName}}</td>
            <td>{{item.email}}</td>
        </tr>
        </tbody>
    </table>
    </li>

    </div>
    </div>
</template>

<script>
import {AXIOS} from './http-common'
export default {
name: "Admin",
data(){
    return{
        users:[],
        user:{
            id:'',
            username:'',
            password:'',
            email:'',
            firstName:'',
            lastName:'',
            active:''
        }
    }
},
methods: {
    getUsers() {
        AXIOS.get('/admin/users',
            {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': localStorage.token,
                    'Access-Control-Allow-Origin': "*"
                }
            })
            .then(response => {
                this.users = response.data
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
.custab{
border: 1px solid #ccc;
padding: 5px;
margin: 5% 0;
box-shadow: 3px 3px 2px #ccc;
transition: 0.5s;
}
.custab:hover{
box-shadow: 3px 3px 0px transparent;
transition: 0.5s;
}
</style>