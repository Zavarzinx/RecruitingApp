<template>
    <div class="container emp-profile">
        <div class="row">
            <div class="col-md-6">
                <div class="profile-head">
                    <h5 >
                        {{user.firstName}} {{user.lastName}}
                    </h5>
                    <h6>
                        {{user.busyness}}
                    </h6>
                    <h6>
                        {{user.roles}}
                    </h6>
                </div>
            </div>
            <div class="col-md-2">
                <b-button v-if="isAdmin" v-b-modal.modal-1>Edit profile</b-button>
                <b-modal id="modal-1" title="Edit profile">
                    <form>
                        <div class="form-group">
                            <label>Username
                                <h6>You need to relogin after username change</h6></label>
                            <input type="text" id="username" class="form-control" v-validate="'required'" name="username" placeholder="username" required v-model="user.username" :class="{ 'is-invalid': submitted && vErrors.has('username') }"  />
                            <div v-if="submitted && vErrors.has('username') " class="invalid-feedback">{{ vErrors.first('username') }}</div>
                        </div>
                        <div class="form-group">
                            <label>Email
                                <h6>Comfirm new email after change</h6></label>
                            <input type="email" id="email" class="form-control"  v-validate="'required|email'" name="email" placeholder="email" v-model="user.email" :class="{ 'is-invalid': submitted && vErrors.has('email') }" />
                            <div v-if="submitted && vErrors.has('email')" class="invalid-feedback">{{ vErrors.first('email') }}</div>
                        </div>
                        <div class="form-group">
                            <label>First name</label>
                            <input type="text" id="firstName" class="form-control"  v-validate="'required'" name="firstName" placeholder="firstName" v-model="user.firstName" :class="{ 'is-invalid': submitted && vErrors.has('firstName') }" />
                            <div v-if="submitted && vErrors.has('firstName')" class="invalid-feedback">{{ vErrors.first('firstName') }}</div>
                        </div>
                        <div class="form-group">
                            <label>Last name</label>
                            <input type="text" id="lastName" class="form-control"  v-validate="'required'" name="lastName" placeholder="lastName" v-model="user.lastName" :class="{ 'is-invalid': submitted && vErrors.has('lastName') }" />
                            <div v-if="submitted && vErrors.has('lastName')" class="invalid-feedback">{{ vErrors.first('lastName') }}</div>
                        </div>
                        <div class="form-group">
                            <label>Busyness</label>
                            <input type="text" id="busyness" class="form-control"  v-validate="'required'" name="busyness" placeholder="company/busyness" v-model="user.busyness" :class="{ 'is-invalid': submitted && vErrors.has('busyness') }" />
                            <div v-if="submitted && vErrors.has('busyness')" class="invalid-feedback">{{ vErrors.first('busyness') }}</div>
                        </div>
                        <b-button type="submit" @click.prevent="updateUser()">Edit</b-button>
                    </form>
                </b-modal>
            </div>
        </div>
        <div class="row">
            <div class="col-md-8">
                <div class="tab-content profile-tab" id="myTabContent">
                    <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                        <div class="row">
                            <div class="col-md-6">
                                <label>Username</label>
                            </div>
                            <div class="col-md-6">
                                <p>{{user.username}}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Name</label>
                            </div>
                            <div class="col-md-6">
                                <p>{{user.firstName}} {{user.lastName}}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Email</label>
                            </div>
                            <div class="col-md-6">
                                <p>{{user.email}}</p>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <label>Profession</label>
                            </div>
                            <div class="col-md-6">
                                <p>{{user.busyness}}</p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>

</template>

<script>
    import {AXIOS} from './http-common'
    export default {
        name: "Profile",
        data() {
            return {
                user:{
                    id: 0,
                    username:'',
                    password:'',
                    email:'',
                    firstName:'',
                    lastName:'',
                    active:'',
                    roles:[],
                    busyness:''
                },
                submitted: false
            }
        },
        methods: {
            getUser() {
                AXIOS.get('/user/' + this.$route.params.id,
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
            updateUser() {
                this.submitted = true;
                const oldUsername = this.user.username;
                console.log("update user" + this.user)
                this.$validator.validate().then(valid => {
                    if (valid) {
                        AXIOS.put('/admin/users/' + this.$route.params.id,
                            {
                                username: this.user.username,
                                password: this.user.password,
                                email: this.user.email,
                                firstName: this.user.firstName,
                                lastName: this.user.lastName,
                                busyness: this.user.busyness
                            },
                            {
                                headers: {
                                    'Content-Type': 'application/json',
                                    'Authorization': localStorage.token,
                                    'Access-Control-Allow-Origin': "*"
                                }
                            }).then(response => {
                            console.log(oldUsername + "oldusername")
                            this.user = response.data
                            console.log(response.data)
                            if (!(oldUsername === this.user.username)){
                                localStorage.removeItem("token")
                                localStorage.removeItem('roles')
                                this.$router.push("/login")
                                location.reload();

                            }
                        }).catch(e => {
                            this.errors.push(e)
                        })
                    }
                })
            }
        },
        computed:{
            isAdmin() {
                return this.$store.getters.isAdmin
            }
            },
        beforeMount(){
            this.getUser()
        }
    }
</script>

<style scoped>
    body{
        background: -webkit-linear-gradient(left, #3931af, #00c6ff);
    }
    .emp-profile{
        padding: 3%;
        margin-top: 3%;
        margin-bottom: 3%;
        border-radius: 0.5rem;
        background: #fff;
    }
    .profile-img{
        text-align: center;
    }
    .profile-img img{
        width: 70%;
        height: 100%;
    }
    .profile-img .file {
        position: relative;
        overflow: hidden;
        margin-top: -20%;
        width: 70%;
        border: none;
        border-radius: 0;
        font-size: 15px;
        background: #212529b8;
    }
    .profile-img .file input {
        position: absolute;
        opacity: 0;
        right: 0;
        top: 0;
    }
    .profile-head h5{
        color: #333;
    }
    .profile-head h6{
        color: #0062cc;
    }
    .profile-edit-btn{
        border: none;
        border-radius: 1.5rem;
        width: 70%;
        padding: 2%;
        font-weight: 600;
        color: #6c757d;
        cursor: pointer;
    }
    .proile-rating{
        font-size: 12px;
        color: #818182;
        margin-top: 5%;
    }
    .proile-rating span{
        color: #495057;
        font-size: 15px;
        font-weight: 600;
    }
    .profile-head .nav-tabs{
        margin-bottom:5%;
    }
    .profile-head .nav-tabs .nav-link{
        font-weight:600;
        border: none;
    }
    .profile-head .nav-tabs .nav-link.active{
        border: none;
        border-bottom:2px solid #0062cc;
    }
    .profile-work{
        padding: 14%;
        margin-top: -15%;
    }
    .profile-work p{
        font-size: 12px;
        color: #818182;
        font-weight: 600;
        margin-top: 10%;
    }
    .profile-work a{
        text-decoration: none;
        color: #495057;
        font-weight: 600;
        font-size: 14px;
    }
    .profile-work ul{
        list-style: none;
    }
    .profile-tab label{
        font-weight: 600;
    }
</style>