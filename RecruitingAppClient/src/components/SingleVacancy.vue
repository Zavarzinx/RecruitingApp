<template>
    <div class="SingleVacancy">
        <div class="container">
            <div class="row">
                <form role="form" class="col-md-9 go-right">
                    <h2>Vacancy info</h2>
                    <router-link :to="{ name: 'SingleProfile', params: {id: authorId }}">
                        <b-button>Author profile</b-button>
                    </router-link>
                    <div>{{vacancy.createdAt}} Creation time</div>
                    <div>{{vacancy.lastUpdated}} Last updated</div>
                    <div class="form-group">
                        <input id="title" name="title"  v-validate="'required'" type="text" class="form-control" required v-model="vacancy.title" :class="{ 'is-invalid': submitted && vErrors.has('title') }"/>
                        <label for="title">Vacancy Title</label>
                        <div v-if="submitted && vErrors.has('title') " class="invalid-feedback">{{ vErrors.first('title') }}</div>
                    </div>
                    <div class="form-group">
                        <input id="region" name="region"  v-validate="'required'" type="text" class="form-control" required v-model="vacancy.region" :class="{ 'is-invalid': submitted && vErrors.has('region') }"/>
                        <label for="region">Location region</label>
                        <div v-if="submitted && vErrors.has('region') " class="invalid-feedback">{{ vErrors.first('region') }}</div>
                    </div>
                    <div class="form-group">
                        <textarea id="text" name="text"  v-validate="'required'" class="form-control" required v-model="vacancy.text" :class="{ 'is-invalid': submitted && vErrors.has('text') }"/>
                        <label for="title">Message</label>
                        <div v-if="submitted && vErrors.has('text') " class="invalid-feedback">{{ vErrors.first('text') }}</div>
                    </div>
                    <div class="form-group">
                        <input id="email" name="email" v-validate="'required|email'" class="form-control" required v-model="vacancy.email" :class="{ 'is-invalid': submitted && vErrors.has('email') }"/>
                        <label for="email">Email</label>
                        <div v-if="submitted && vErrors.has('email') " class="invalid-feedback">{{ vErrors.first('email') }}</div>
                    </div>
                    <div class="form-group">
                        <input id="phone" name="phone"  v-validate="'required'" class="form-control" required v-model="vacancy.phone" :class="{ 'is-invalid': submitted && vErrors.has('phone') }"/>
                        <label for="phone">Phone number</label>
                        <div v-if="submitted && vErrors.has('phone') " class="invalid-feedback">{{ vErrors.first('phone') }}</div>
                    </div>
                </form>
            </div>
            <b-button @click="updateVacancy()" v-if="isRecruiter" variant="primary">Update vacancy</b-button>
            <b-button @click="deleteVacancy()" v-if="isRecruiter" variant="primary">Delete vacancy</b-button>
        </div>
    </div>

</template>

<script>
    import {AXIOS} from './http-common'
    export default {
        name: "SingleVacancy",
        data() {
            return {
                response: [],
                errors: [],
                vacancies: [],
                vacancy: {
                    title: '',
                    text: '',
                    author: '',
                    email:'',
                    phone:'',
                    createdAt:'',
                    lastUpdated:'',
                    region:'',
                    id: 0
                },
                authorId:0,
                submitted:false
            }
        },
        computed: {
            isAdmin() {
                return this.$store.getters.isAdmin
            },
            isRecruiter() {
                return this.$store.getters.isRecruiter
            },
            isLoggedIn() {
                return this.$store.getters.isUser
            },
        },
        methods: {
            getVacancy() {
                console.log("route id " + this.$route.id)
                AXIOS.get('/recruiter/' + this.$route.params.id,
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': localStorage.token,
                            'Access-Control-Allow-Origin': "*"
                        }
                    }).then(response => {
                    this.vacancy = response.data
                    console.log(response.data)
                        .catch(e => {
                            this.errors.push(e)
                        })
                })
            },
            getAuthor(){
                AXIOS.get('/recruiter/author/' + this.$route.params.id,
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': localStorage.token,
                            'Access-Control-Allow-Origin': "*"
                        }
                    }).then(response => {
                    this.authorId = response.data
                    console.log(response.data)
                    console.log("getAuthor")
                        .catch(e => {
                            this.errors.push(e)
                        })
                })
            },
            updateVacancy() {
                this.submitted = true
                this.$validator.validate().then(valid => {
                    if(valid){

                AXIOS.put('/recruiter/' + this.$route.params.id,
                    {text: this.vacancy.text, title: this.vacancy.title,phone:this.vacancy.phone,
                        email:this.vacancy.email,region:this.vacancy.region},
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': localStorage.token,
                            'Access-Control-Allow-Origin': "*"
                        }
                    }).then(response => {
                    this.vacancy = response.data
                    console.log(response.data)
                        .catch(e => {
                            this.errors.push(e)
                        })
                })}
                })
            },
            deleteVacancy() {
                AXIOS.delete('/recruiter/' + this.$route.params.id,
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': localStorage.token,
                            'Access-Control-Allow-Origin': "*"
                        }
                    }).then(response => {
                    console.log(response.data)
                    this.$router.push('/vacancy')
                        .catch(e => {
                            this.errors.push(e)
                        })
                })
            }
        },
        beforeMount(){
            this.getVacancy()
            this.getAuthor()
        }
    }
</script>

<style scoped>
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
    .profile-edit-btn{
        border: none;
        border-radius: 1.5rem;
        width: 70%;
        padding: 2%;
        font-weight: 600;
        color: #6c757d;
        cursor: pointer;
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