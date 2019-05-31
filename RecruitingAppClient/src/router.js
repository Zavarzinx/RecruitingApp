import Vue from 'vue';
import Router from 'vue-router';
import Resume from './components/Resume.vue'
import User from './components/User.vue'
import Login from './components/Login.vue'
import Registration from './components/Registration.vue'
import SingleResume from './components/SingleResume'
import Admin from './components/Admin'
import Vacancy from './components/Vacancy'
import SingleVacancy from './components/SingleVacancy'
import Search from './components/Search'
import VSearch from './components/VSearch'
import Home from './components/Home'
import Profile from './components/Profile'
import SingleProfile from './components/SingleProfile.vue'
import ConfirmAcc from './components/ConfirmAcc'
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
      meta: { requiresAuth: true,roles:['USER','ADMIN','RECRUITER'] }
    },
    {
      path: '/registration',
      name: 'Registration',
      component: Registration,
    },
    {
      path: '/login',
      name: 'Login',
      component: Login,
    },
    {
      path: '/profile',
      name: 'Profile',
      component: Profile,
      meta: { requiresAuth: true,roles:['USER','ADMIN','RECRUITER'] }
    },
    {
      path: '/profile/:id',
      name: 'SingleProfile',
      component: SingleProfile,
      meta: { requiresAuth: true,roles:['USER','ADMIN','RECRUITER'] }
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
    {
      path: '/vacancy',
      name: 'Vacancy',
      component: Vacancy,
      meta: { requiresAuth: true,roles:['ADMIN','RECRUITER'] }
    },
    {
      path: '/vacancy/:id',
      name: 'SingleVacancy',
      component: SingleVacancy,
      meta: { requiresAuth: true,roles:['ADMIN','RECRUITER','USER'] }
    },
    {
      path: '/search/resume',
      name: 'Search',
      component: Search,
      meta: { requiresAuth: true,roles:['ADMIN','RECRUITER'] }
    },
    {
      path: '/search/vacancy',
      name: 'VSearch',
      component: VSearch,
      meta: { requiresAuth: true,roles:['ADMIN','USER'] }
    },
    {
      path: '/',
      name: 'Home',
      component: Home,
    },
    {
      path:'/confirm-account',
      name:ConfirmAcc,
      component:ConfirmAcc
    }
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
    } else if(to.meta.roles.includes(JSON.parse(localStorage.getItem("roles")).find(x=>x === 'ADMIN' || x === 'RECRUITER')) || to.meta.roles.includes(JSON.parse(localStorage.getItem("roles")).find(x=>x==='USER'))) {
      next()
    }
  } else {
    next()
  }
})
