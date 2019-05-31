<template>
    <div class="Admin">
<div class="well">
    <div  v-for="list in slicedUsers" :key="list.id" class="single-user">
        <h2> <router-link :to="{ name: 'SingleProfile', params: { id: list.id }}">{{ list.username }}</router-link></h2>
        <article>Email: {{ list.email }}</article>
        <article>First name: {{ list.firstName }}</article>
        <article>Last name: {{ list.lastName }}</article>
    </div>
    <b-pagination
            :total-rows="rows"
            v-model="currentPage"
            :per-page="perPage"
    ></b-pagination>
    </div>
    </div>

</template>

<script>
import {AXIOS} from './http-common'
export default {
name: "Admin",
data(){
    return{
        perPage: 5,
        currentPage: 1,
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
    computed:{
        rows() {
            return this.users.length
        },
        slicedUsers(){
            return this.users.slice((this.currentPage - 1) * this.perPage,
                this.currentPage * this.perPage)
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
},
    beforeMount(){
        this.getUsers()
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
 .form-control{
     background: transparent;
 }
form {
    width: 320px;
    margin: 20px;
}
form > div {
    position: relative;
    overflow: hidden;
}
form input, form textarea {
    width: 100%;
    border: 2px solid gray;
    background: none;
    position: relative;
    top: 0;
    left: 0;
    z-index: 1;
    padding: 8px 12px;
    outline: 0;
}
form input:valid, form textarea:valid {
    background: white;
}
form input:focus, form textarea:focus {
    border-color: #357EBD;
}
form input:focus + label, form textarea:focus + label {
    background: #357EBD;
    color: white;
    font-size: 70%;
    padding: 1px 6px;
    z-index: 2;
    text-transform: uppercase;
}
form label {
    -webkit-transition: background 0.2s, color 0.2s, top 0.2s, bottom 0.2s, right 0.2s, left 0.2s;
    transition: background 0.2s, color 0.2s, top 0.2s, bottom 0.2s, right 0.2s, left 0.2s;
    position: absolute;
    color: #999;
    padding: 7px 6px;
    font-weight: normal;
}
form textarea {
    display: block;
    resize: vertical;
}
form.go-bottom input, form.go-bottom textarea {
    padding: 12px 12px 12px 12px;
}
form.go-bottom label {
    top: 0;
    bottom: 0;
    left: 0;
    width: 100%;
}
form.go-bottom input:focus, form.go-bottom textarea:focus {
    padding: 4px 6px 20px 6px;
}
form.go-bottom input:focus + label, form.go-bottom textarea:focus + label {
    top: 100%;
    margin-top: -16px;
}
form.go-right label {
    border-radius: 0 5px 5px 0;
    height: 100%;
    top: 0;
    right: 100%;
    width: 100%;
    margin-right: -100%;
}
form.go-right input:focus + label, form.go-right textarea:focus + label {
    right: 0;
    margin-right: 0;
    width: 40%;
    padding-top: 5px;
}
#show-resumes{
    max-width: 800px;
    margin: 0px auto;
}
.single-user{
    padding: 20px;
    margin: 20px 0;
    width: 500px;
    box-sizing: border-box;
    background: #eee;
}
.form-control{
    background: transparent;
}
form {
    width: 320px;
    margin: 20px;
}
form > div {
    position: relative;
    overflow: hidden;
}
form input, form textarea {
    width: 100%;
    border: 2px solid gray;
    background: none;
    position: relative;
    top: 0;
    left: 0;
    z-index: 1;
    padding: 8px 12px;
    outline: 0;
}
form input:valid, form textarea:valid {
    background: white;
}
form input:focus, form textarea:focus {
    border-color: #357EBD;
}
form input:focus + label, form textarea:focus + label {
    background: #357EBD;
    color: white;
    font-size: 70%;
    padding: 1px 6px;
    z-index: 2;
    text-transform: uppercase;
}
form label {
    -webkit-transition: background 0.2s, color 0.2s, top 0.2s, bottom 0.2s, right 0.2s, left 0.2s;
    transition: background 0.2s, color 0.2s, top 0.2s, bottom 0.2s, right 0.2s, left 0.2s;
    position: absolute;
    color: #999;
    padding: 7px 6px;
    font-weight: normal;
}
form textarea {
    display: block;
    resize: vertical;
}
form.go-bottom input, form.go-bottom textarea {
    padding: 12px 12px 12px 12px;
}
form.go-bottom label {
    top: 0;
    bottom: 0;
    left: 0;
    width: 100%;
}
form.go-bottom input:focus, form.go-bottom textarea:focus {
    padding: 4px 6px 20px 6px;
}
form.go-bottom input:focus + label, form.go-bottom textarea:focus + label {
    top: 100%;
    margin-top: -16px;
}
form.go-right label {
    border-radius: 0 5px 5px 0;
    height: 100%;
    top: 0;
    right: 100%;
    width: 100%;
    margin-right: -100%;
}
form.go-right input:focus + label, form.go-right textarea:focus + label {
    right: 0;
    margin-right: 0;
    width: 40%;
    padding-top: 5px;
}
</style>