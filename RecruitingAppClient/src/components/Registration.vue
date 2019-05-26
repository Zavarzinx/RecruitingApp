<template>
    <!--
    <div class="wrapper fadeInDown">
        <div id="formContent">
            <form>
                <input type="text" id="username" class="fadeIn third" name="username" placeholder="username" v-model="user.username">
                <input type="password" id="password" class="fadeIn second" name="password" placeholder="password" v-model="user.password">
                <input type="email" id="email" class="fadeIn second" name="email" placeholder="email" v-model="user.email">
                <input type="text" id="firstName" class="fadeIn second" name="firstName" placeholder="firstName" v-model="user.firstName">
                <input type="text" id="lastName" class="fadeIn second" name="lastName" placeholder="lastName" v-model="user.lastName">
                <b-form-checkbox-group
                        id="checkbox-group-1"
                        v-model="user.roles"
                        :options="options"
                        name="flavour-1"
                ></b-form-checkbox-group>
                <input type="submit" @click.prevent="SignUp" class="fadeIn fourth"  value="Sign Up">
            </form>
        </div>
    </div>
 -->
<div>
    <div class="wrapper fadeInDown">

        <div id="formContentUser">
            <form>
                <div class="form-group">
                    <input type="text" id="username" class="form-control" v-validate="'required'" name="username" placeholder="username" required v-model="user.username" :class="{ 'is-invalid': submitted && vErrors.has('username') }"  />
                    <div v-if="submitted && vErrors.has('username') " class="invalid-feedback">{{ vErrors.first('username') }}</div>
                </div>

                <div class="form-group">
                    <input type="password" id="password" class="form-control" v-validate="{ required: true, min: 6 }" name="password" placeholder="password" v-model="user.password" :class="{ 'is-invalid': submitted && vErrors.has('password') }" />
                    <div v-if="submitted && vErrors.has('password')" class="invalid-feedback">{{ vErrors.first('password') }}</div>
                </div>

                <div class="form-group">
                    <input type="email" id="email" class="form-control"  v-validate="'required|email'" name="email" placeholder="email" v-model="user.email" :class="{ 'is-invalid': submitted && vErrors.has('email') }" />
                    <div v-if="submitted && vErrors.has('email')" class="invalid-feedback">{{ vErrors.first('email') }}</div>
                </div>
                <div class="form-group">
                    <input type="text" id="firstName" class="form-control"  v-validate="'required'" name="firstName" placeholder="firstName" v-model="user.firstName" :class="{ 'is-invalid': submitted && vErrors.has('firstName') }" />
                    <div v-if="submitted && vErrors.has('firstName')" class="invalid-feedback">{{ vErrors.first('firstName') }}</div>
                </div>
                <div class="form-group">
                    <input type="text" id="lastName" class="form-control"  v-validate="'required'" name="lastName" placeholder="lastName" v-model="user.lastName" :class="{ 'is-invalid': submitted && vErrors.has('lastName') }" />
                    <div v-if="submitted && vErrors.has('lastName')" class="invalid-feedback">{{ vErrors.first('lastName') }}</div>
                </div>
                <div class="form-group">
                    <input type="text" id="busyness" class="form-control"  v-validate="'required'" name="busyness" placeholder="company/busyness" v-model="user.busyness" :class="{ 'is-invalid': submitted && vErrors.has('busyness') }" />
                    <div v-if="submitted && vErrors.has('busyness')" class="invalid-feedback">{{ vErrors.first('busyness') }}</div>
                </div>
                <b-form-group label="Role">
                    <b-form-radio v-model="user.roles" name="user" value="USER">User</b-form-radio>
                    <b-form-radio v-model="user.roles" name="recruiter" value="RECRUITER">Recruiter</b-form-radio>
                </b-form-group>
                <input type="submit" @click.prevent="SignUp" class="fadeIn fourth"  value="Sign Up">
            </form>
        </div>
    </div>
</div>
</template>
<script>
import {AXIOS} from './http-common'
export default {
    name: 'registration',
    data() {
        return {
            user: {
                username: '',
                password: '',
                email:'',
                firstName: '',
                lastName: '',
                busyness:'',
                roles: []
            },
            submitted: false,
            options: [
                {text: 'Recruiter', value: 'RECRUITER'},
                {text: 'User', value: 'USER'}
            ]
        }
    },
    methods: {
        SignUp() {
            console.log(this.user)
            console.log(this.user.roles)
            const roles = [this.user.roles]
           this.submitted = true
            this.$validator.validate().then(valid => {
                if (valid) {
                    AXIOS.post('/registration',
                        {
                            username: this.user.username, email: this.user.email, password: this.user.password,
                            firstName: this.user.firstName, lastName: this.user.lastName, roles: roles,
                            busyness:this.user.busyness
                        })
                        .then(
                            (response) => {
                                console.log(response)
                                this.$router.push('/login')
                            }
                        )
                        .catch(
                            (error) => console.log(error)
                        );
                }
            })
        }
    }
}
</script>


<style scoped>
html {
 background-color: #56baed;
}

body {
 font-family: "Poppins", sans-serif;
 height: 100vh;
}

a {
 color: #92badd;
 display:inline-block;
 text-decoration: none;
 font-weight: 400;
}

h2 {
 text-align: center;
 font-size: 16px;
 font-weight: 600;
 text-transform: uppercase;
 display:inline-block;
 margin: 40px 8px 10px 8px;
 color: #cccccc;
}



/* STRUCTURE */

.wrapper {
 display: flex;
 align-items: center;
 flex-direction: column;
 justify-content: center;
 width: 100%;
 min-height: 100%;
 padding: 20px;
}

#formContent {
 -webkit-border-radius: 10px 10px 10px 10px;
 border-radius: 10px 10px 10px 10px;
 background: #fff;
 padding: 30px;
 width: 90%;
 max-width: 450px;
 position: relative;
 padding: 0px;
 -webkit-box-shadow: 0 30px 60px 0 rgba(0,0,0,0.3);
 box-shadow: 0 30px 60px 0 rgba(0,0,0,0.3);
 text-align: center;
}

#formFooter {
 background-color: #f6f6f6;
 border-top: 1px solid #dce8f1;
 padding: 25px;
 text-align: center;
 -webkit-border-radius: 0 0 10px 10px;
 border-radius: 0 0 10px 10px;
}



/* TABS */

h2.inactive {
 color: #cccccc;
}

h2.active {
 color: #0d0d0d;
 border-bottom: 2px solid #5fbae9;
}



/* FORM TYPOGRAPHY*/

input[type=button], input[type=submit], input[type=reset]  {
 background-color: #56baed;
 border: none;
 color: white;
 padding: 15px 80px;
 text-align: center;
 text-decoration: none;
 display: inline-block;
 text-transform: uppercase;
 font-size: 13px;
 -webkit-box-shadow: 0 10px 30px 0 rgba(95,186,233,0.4);
 box-shadow: 0 10px 30px 0 rgba(95,186,233,0.4);
 -webkit-border-radius: 5px 5px 5px 5px;
 border-radius: 5px 5px 5px 5px;
 margin: 5px 20px 40px 20px;
 -webkit-transition: all 0.3s ease-in-out;
 -moz-transition: all 0.3s ease-in-out;
 -ms-transition: all 0.3s ease-in-out;
 -o-transition: all 0.3s ease-in-out;
 transition: all 0.3s ease-in-out;
}

input[type=button]:hover, input[type=submit]:hover, input[type=reset]:hover  {
 background-color: #39ace7;
}

input[type=button]:active, input[type=submit]:active, input[type=reset]:active  {
 -moz-transform: scale(0.95);
 -webkit-transform: scale(0.95);
 -o-transform: scale(0.95);
 -ms-transform: scale(0.95);
 transform: scale(0.95);
}

input[type=text] {
 background-color: #f6f6f6;
 border: none;
 color: #0d0d0d;
 padding: 15px 32px;
 text-align: center;
 text-decoration: none;
 display: inline-block;
 font-size: 16px;
 margin: 5px;
 width: 85%;
 border: 2px solid #f6f6f6;
 -webkit-transition: all 0.5s ease-in-out;
 -moz-transition: all 0.5s ease-in-out;
 -ms-transition: all 0.5s ease-in-out;
 -o-transition: all 0.5s ease-in-out;
 transition: all 0.5s ease-in-out;
 -webkit-border-radius: 5px 5px 5px 5px;
 border-radius: 5px 5px 5px 5px;
}

input[type=text]:focus {
 background-color: #fff;
 border-bottom: 2px solid #5fbae9;
}

input[type=text]:placeholder {
 color: #cccccc;
}



input[type=email] {
    background-color: #f6f6f6;
    border: none;
    color: #0d0d0d;
    padding: 15px 32px;
    text-align: center;
    text-decoration: none;
    display: inline-block;
    font-size: 16px;
    margin: 5px;
    width: 85%;
    border: 2px solid #f6f6f6;
    -webkit-transition: all 0.5s ease-in-out;
    -moz-transition: all 0.5s ease-in-out;
    -ms-transition: all 0.5s ease-in-out;
    -o-transition: all 0.5s ease-in-out;
    transition: all 0.5s ease-in-out;
    -webkit-border-radius: 5px 5px 5px 5px;
    border-radius: 5px 5px 5px 5px;
}

input[type=email]:focus {
    background-color: #fff;
    border-bottom: 2px solid #5fbae9;
}

input[type=email]:placeholder {
    color: #cccccc;
}


input[type=password] {
 background-color: #f6f6f6;
 border: none;
 color: #0d0d0d;
 padding: 15px 32px;
 text-align: center;
 text-decoration: none;
 display: inline-block;
 font-size: 16px;
 margin: 5px;
 width: 85%;
 border: 2px solid #f6f6f6;
 -webkit-transition: all 0.5s ease-in-out;
 -moz-transition: all 0.5s ease-in-out;
 -ms-transition: all 0.5s ease-in-out;
 -o-transition: all 0.5s ease-in-out;
 transition: all 0.5s ease-in-out;
 -webkit-border-radius: 5px 5px 5px 5px;
 border-radius: 5px 5px 5px 5px;
}

input[type=password]:focus {
 background-color: #fff;
 border-bottom: 2px solid #5fbae9;
}

input[type=password]:placeholder {
 color: #cccccc;
}



/* ANIMATIONS */

/* Simple CSS3 Fade-in-down Animation */
.fadeInDown {
 -webkit-animation-name: fadeInDown;
 animation-name: fadeInDown;
 -webkit-animation-duration: 1s;
 animation-duration: 1s;
 -webkit-animation-fill-mode: both;
 animation-fill-mode: both;
}

@-webkit-keyframes fadeInDown {
 0% {
     opacity: 0;
     -webkit-transform: translate3d(0, -100%, 0);
     transform: translate3d(0, -100%, 0);
 }
 100% {
     opacity: 1;
     -webkit-transform: none;
     transform: none;
 }
}

@keyframes fadeInDown {
 0% {
     opacity: 0;
     -webkit-transform: translate3d(0, -100%, 0);
     transform: translate3d(0, -100%, 0);
 }
 100% {
     opacity: 1;
     -webkit-transform: none;
     transform: none;
 }
}

/* Simple CSS3 Fade-in Animation */
@-webkit-keyframes fadeIn { from { opacity:0; } to { opacity:1; } }
@-moz-keyframes fadeIn { from { opacity:0; } to { opacity:1; } }
@keyframes fadeIn { from { opacity:0; } to { opacity:1; } }

.fadeIn {
 opacity:0;
 -webkit-animation:fadeIn ease-in 1;
 -moz-animation:fadeIn ease-in 1;
 animation:fadeIn ease-in 1;

 -webkit-animation-fill-mode:forwards;
 -moz-animation-fill-mode:forwards;
 animation-fill-mode:forwards;

 -webkit-animation-duration:1s;
 -moz-animation-duration:1s;
 animation-duration:1s;
}

.fadeIn.first {
 -webkit-animation-delay: 0.4s;
 -moz-animation-delay: 0.4s;
 animation-delay: 0.4s;
}

.fadeIn.second {
 -webkit-animation-delay: 0.6s;
 -moz-animation-delay: 0.6s;
 animation-delay: 0.6s;
}

.fadeIn.third {
 -webkit-animation-delay: 0.8s;
 -moz-animation-delay: 0.8s;
 animation-delay: 0.8s;
}

.fadeIn.fourth {
 -webkit-animation-delay: 1s;
 -moz-animation-delay: 1s;
 animation-delay: 1s;
}

/* Simple CSS3 Fade-in Animation */
.underlineHover:after {
 display: block;
 left: 0;
 bottom: -10px;
 width: 0;
 height: 2px;
 background-color: #56baed;
 content: "";
 transition: width 0.2s;
}

.underlineHover:hover {
 color: #0d0d0d;
}

.underlineHover:hover:after{
 width: 100%;
}



/* OTHERS */

*:focus {
 outline: none;
}

#icon {
 width:60%;
}

</style>