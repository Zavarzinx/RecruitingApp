<template>
    <div class="resume">

<div class="container">
    <div class="row">
        <form class="col-md-9 go-right">
            <h2>Submit new resume</h2>

            <div class="form-group">
                <input id="title" name="title"  v-validate="'required'" type="text" class="form-control" required v-model="resume.title" :class="{ 'is-invalid': submitted && vErrors.has('title') }"/>
                <label for="title">Resume Title</label>
                <div v-if="submitted && vErrors.has('title') " class="invalid-feedback">{{ vErrors.first('title') }}</div>
            </div>
            <div class="form-group">
                <input id="region" name="region"  v-validate="'required'" type="text" class="form-control" required v-model="resume.region" :class="{ 'is-invalid': submitted && vErrors.has('region') }"/>
                <label for="region">Location region</label>
                <div v-if="submitted && vErrors.has('region') " class="invalid-feedback">{{ vErrors.first('region') }}</div>
            </div>
            <div class="form-group">
                <textarea id="text" name="text" rows="10"  v-validate="'required'" class="form-control" required v-model="resume.text" :class="{ 'is-invalid': submitted && vErrors.has('text') }"/>
                <label for="title">Message</label>
                <div v-if="submitted && vErrors.has('text') " class="invalid-feedback">{{ vErrors.first('text') }}</div>
            </div>
            <div class="form-group">
                <input id="email" name="email" v-validate="'required|email'" class="form-control" required v-model="resume.email" :class="{ 'is-invalid': submitted && vErrors.has('email') }"/>
                <label for="email">Email</label>
                <div v-if="submitted && vErrors.has('email') " class="invalid-feedback">{{ vErrors.first('email') }}</div>
            </div>
            <div class="form-group">
                <input id="phone" name="phone"  v-validate="'required'" class="form-control" required v-model="resume.phone" :class="{ 'is-invalid': submitted && vErrors.has('phone') }"/>
                <label for="phone">Phone number</label>
                <div v-if="submitted && vErrors.has('phone') " class="invalid-feedback">{{ vErrors.first('phone') }}</div>
            </div>
        </form>
    </div>

    <b-button @click="createResume()" variant="primary">Submit</b-button>
    <div  v-for="list in resumes" :key="list.id" class="single-resume">
        <h2> <router-link :to="{ name: 'SingleResume', params: { id: list.id }}">{{ list.title }}</router-link></h2>
        <article>{{ list.text }}</article>
    </div>
</div>

</div>

</template>
<script>
import {AXIOS} from './http-common'
export default {
name: 'resume',
data() {
    return {
        response: [],
        errors: [],
        resumes: [],
        resume: {
            title:'',
            text: '',
            author: '',
            phone:'',
            email:'',
            region:'',
            id: 0
        },
        submitted: false,
    }
},
    methods: {
        createResume() {
            this.submitted = true
            this.$validator.validate().then(valid => {
                console.log('errors' + valid)
                if (valid) {
                    console.log(this.resume.text + "resume")
                    console.log(localStorage.token + " token")
                    AXIOS.post('/resume',
                        {
                            text: this.resume.text, title: this.resume.title, phone: this.resume.phone,
                            email: this.resume.email,region:this.resume.region
                        },
                        {
                            headers: {
                                'Content-Type': 'application/json',
                                'Authorization': localStorage.token,
                                'Access-Control-Allow-Origin': "*"
                            }
                        })
                        .then(this.getResumes(),
                            (response) => console.log(response),
                            this.getResumes()
                        )
                        .catch(
                            (error) => console.log(error)
                        );
                }
            })
        },
        getResumes(){
            AXIOS.get('/resume/all/user',
                {headers: {
                        'Content-Type': 'application/json',
                        'Authorization':localStorage.token,
                        'Access-Control-Allow-Origin': "*"
                    }})
                .then(response => {
                    this.resumes = response.data
                    console.log(response.data)
                        .catch(
                            (error) => console.log(error)
                        );
                })

        }
    },
    beforeMount(){
    this.getResumes()
    },
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
    .single-resume{
        padding: 20px;
        margin: 20px 0;
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