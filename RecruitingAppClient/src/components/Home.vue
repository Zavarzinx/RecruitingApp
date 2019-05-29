<template>
    <div class="view"  style="background-image:url('https://mdbootstrap.com/img/Photos/Others/background.jpg');  background-repeat: no-repeat; background-size: cover; background-position: center center;">
            <!-- Mask & flexbox options-->
            <div class="mask rgba-gradient align-items-center">
                <!-- Content -->
                <div class="container">
                    <!--Grid row-->
                    <div class="row">
                        <!--Grid column-->
                        <div class="col-md-6 white-text text-center text-md-left mt-xl-5 mb-5 wow fadeInLeft" data-wow-delay="0.3s">
                            <h1 class="h1-responsive font-weight-bold mt-sm-5">Find your dream with our website</h1>
                            <h1 class="h1-responsive font-weight-bold mt-sm-5">Recruit new people with our website</h1>
                            <hr class="hr-light">
                        </div>
                        <!--Grid column-->
                        <!--Grid column-->
                        <div>
                            <img src="https://i.imgur.com/TOBWZlN.png" alt="" class="img-fluid">
                        </div>
                        <!--Grid column-->
                    </div>
                    <!--Grid row-->
                </div>
                <!-- Content -->
            </div>
            <!-- Mask & flexbox options-->
        </div>
        <!-- Full Page Intro -->
</template>

<script>
    export default {
        name: "Home"
    }
</script>

<style scoped>
    html,
    body,
    header,
    .view {
        height: 100%;
    }

    @media (max-width: 740px) {
        html,
        body,
        header,
        .view {
            height: 1000px;
        }
    }
    @media (min-width: 800px) and (max-width: 850px) {
        html,
        body,
        header,
        .view {
            height: 600px;
        }
    }

    .btn .fa {
        margin-left: 3px;
    }

    .top-nav-collapse {
        background-color: #424f95 !important;
    }

    .navbar:not(.top-nav-collapse) {
        background: transparent !important;
    }

    @media (max-width: 991px) {
        .navbar:not(.top-nav-collapse) {
            background: #424f95 !important;
        }
    }

    .btn-white {
        color: black !important;
    }

    h6 {
        line-height: 1.7;
    }

    .rgba-gradient {
        background: -moz-linear-gradient(45deg, rgba(42, 27, 161, 0.7), rgba(29, 210, 177, 0.7) 100%);
        background: -webkit-linear-gradient(45deg, rgba(42, 27, 161, 0.7), rgba(29, 210, 177, 0.7) 100%);
        background: -webkit-gradient(linear, 45deg, from(rgba(42, 27, 161, 0.7)), to(rgba(29, 210, 177, 0.7)));
        background: -o-linear-gradient(45deg, rgba(42, 27, 161, 0.7), rgba(29, 210, 177, 0.7) 100%);
        background: linear-gradient(to 45deg, rgba(42, 27, 161, 0.7), rgba(29, 210, 177, 0.7) 100%);
    }
</style>