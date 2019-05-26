<template>
        <div>
            <b-navbar toggleable="lg" type="dark" variant="dark">
                <b-navbar-brand href="#">NavBar</b-navbar-brand>

                <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

                <b-collapse id="nav-collapse" is-nav>
                    <b-navbar-nav>
                        <b-nav-item  v-if="isUser" href="/resume">Resume</b-nav-item>
                        <b-nav-item v-if="!isLoggedIn" href="/registration">Registration</b-nav-item>
                        <b-nav-item v-if="isAdmin"  href="/admin">Admin</b-nav-item>
                        <b-nav-item v-if="isRecruiter"  href="/vacancy">Vacancy</b-nav-item>
                        <b-nav-item-dropdown v-if="isLoggedIn"  text="Search">
                            <b-dropdown-item v-if="isRecruiter" href="/search/resume">Search for resume</b-dropdown-item>
                            <b-dropdown-item v-if="isUser" href="/search/vacancy">Search for vacancy</b-dropdown-item>
                        </b-nav-item-dropdown>
                    </b-navbar-nav>

                    <!-- Right aligned nav items -->
                    <b-navbar-nav class="ml-auto">
                        <b-nav-item v-if="!isLoggedIn" href="/login">Login</b-nav-item>
                        <b-nav-item-dropdown  v-if="isLoggedIn" right>
                            <!-- Using 'button-content' slot -->
                            <template slot="button-content"><em>User panel</em></template>
                            <b-dropdown-item v-if="isLoggedIn" href="/profile">Profile</b-dropdown-item>
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
               return this.$store.getters.isAdmin
            },
            isRecruiter() {
                return this.$store.getters.isRecruiter
            },
            isLoggedIn() {
                return this.$store.getters.isUser || this.$store.getters.isRecruiter || this.$store.getters.isAdmin
            },
            isUser(){
                return this.$store.getters.isUser
            }
        },
        methods: {
            SignOut(){
                localStorage.removeItem("token")
                localStorage.removeItem('roles')
                this.$router.push("/login")
                location.reload();
            }
        }
    }
</script>

<style scoped>

</style>