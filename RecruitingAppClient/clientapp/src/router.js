import Vue from 'vue';
import Router from 'vue-router';
import Resume from './components/Resume.vue'
import User from './components/User.vue'
import Login from './components/Login.vue'
import Registration from './components/Registration.vue'
import SingleResume from './components/SingleResume'
import Admin from './components/Admin'

Vue.use(Router);


export const router =  new Router({
  mode: 'history',
  routes: [
    {
      path: '/resume',
      name: 'Resume',
      component: Resume,
      meta: { requiresAuth: true, roles:['USER','ADMIN'] }
    },
    {
      path: '/resume/:id',
      name: 'SingleResume',
      component: SingleResume,
      meta: { requiresAuth: true,roles:['USER','ADMIN'] }
    },
    {
      path: '/registration',
      name: 'Registration',
      component: Registration,
    },
    {
      path: '/',
      name: 'Login',
      component: Login,
    },
    {
    path: '/user/:id',
    name: 'User',
    component: User,
    meta: { requiresAuth: true,roles:['USER','ADMIN'] }
    },
    {
      path: '/admin',
      name: 'Admin',
      component: Admin,
      meta: { requiresAuth: true,roles:['ADMIN'] }
    },
  ],

})

router.beforeEach((to, from, next) => {
  if (to.matched.some(record => record.meta.requiresAuth)) {
    let token = localStorage.getItem('token')
    if (token === null || token === '') {
      next({
        path: '/',
        query: {redirect: to.fullPath}
      })
    } else if(to.meta.roles.includes(JSON.parse(localStorage.getItem("roles")).find(x=>x === 'RECRUITER' || 'USER' || 'ADMIN'))) {
      next()
    }
  } else {
    next()
  }
})