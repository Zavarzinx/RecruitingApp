<template>
        <div>
            <b-navbar toggleable="lg" type="dark" variant="dark">
                <b-navbar-brand href="#">NavBar</b-navbar-brand>

                <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

                <b-collapse id="nav-collapse" is-nav>
                    <b-navbar-nav>
                        <b-nav-item href="/resume">Resume</b-nav-item>
                        <b-nav-item v-if="!isLoggedIn" href="/registration">Registration</b-nav-item>
                        <b-nav-item v-if="isAdmin"  href="/admin">Admin</b-nav-item>
                    </b-navbar-nav>

                    <!-- Right aligned nav items -->
                    <b-navbar-nav class="ml-auto">
                        <b-nav-item v-if="!isLoggedIn" href="/">Login</b-nav-item>
                        <b-nav-item-dropdown right>
                            <!-- Using 'button-content' slot -->
                            <template slot="button-content"><em>User panel</em></template>
                            <b-dropdown-item v-if="isLoggedIn" href="#">Profile</b-dropdown-item>
                            <b-dropdown-item v-if="isLoggedIn" @click="SignOut">Sign Out</b-dropdown-item>
                        </b-nav-item-dropdown>
                    </b-navbar-nav>
                </b-collapse>
            </b-navbar>
        </div>


</template>
<script>
    export default {
        name: "Navigation",
        computed: {
            isAdmin() {
                if (JSON.parse(localStorage.getItem("roles")) === null){
                    return false
                }
                console.log(JSON.parse(localStorage.getItem("roles")).find(x=>x === 'ADMIN') === 'ADMIN')
              return JSON.parse(localStorage.getItem("roles")).find(x=>x === 'ADMIN') === 'ADMIN'
            },
            isRecruiter() {
                if (JSON.parse(localStorage.getItem("roles")) === null){
                    return false
                }
                console.log(JSON.parse(localStorage.getItem("roles")).find(x=>x === 'ADMIN') === 'ADMIN')
                return JSON.parse(localStorage.getItem("roles")).find(x=>x === 'RECRUITER') === 'RECRUITER'
            },
            isLoggedIn() {
                if (JSON.parse(localStorage.getItem("roles")) === null)
                    return false
                console.log(JSON.parse(localStorage.getItem("roles")).find(x=>x === 'USER') === 'USER')
                return JSON.parse(localStorage.getItem("roles")).find(x=>x === 'USER') === 'USER'
            },
        },
        methods: {
            SignOut(){
                localStorage.removeItem("token")
                localStorage.removeItem('roles')
                this.$router.push("/")
            }
        }
    }
</script>

<style scoped>

</style>