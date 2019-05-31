<template>
    <div class="row">
        <div class="col-sm-12">
            <div class="text-center">
                <b-button class="btn btn-primary center-block" @click="confirm()">Click here to confirm your account</b-button>
            </div>
        </div>
    </div>
</template>

<script>
    import {AXIOS} from './http-common'
    export default {

        name: "ConfirmAcc",

        methods:{
            confirm(){
                AXIOS.get('http://localhost:8088/api/registration/confirm-account?token=' + this.$route.query.token)
                    .then(response => {
                        if(response.status === 200){
                            this.$router.push('/login')
                        }
                        else {
                            this.$router.push('/')
                        }
                        }
                    )
            }
        },

        beforeMount(){
        }
    }
</script>

<style scoped>

</style>