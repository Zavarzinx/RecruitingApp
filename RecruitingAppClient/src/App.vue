<template>
  <div id="app">
    <Navigation></Navigation>
    <router-view></router-view>
  </div>
</template>

<script>
  import Navigation from './components/Navigation'
  export default {
    name: 'app',
    components: {
      'Navigation': Navigation
    }
  }
</script>

<style>
  #app {
    width: 100%;
  }
</style>