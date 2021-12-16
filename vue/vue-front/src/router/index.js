import Vue from "vue"
import Router from 'vue-router'
import Calendar from "../components/Calendar.vue"
import HelloWorld from "../components/HelloWorld.vue"
import Login from "../components/Login.vue"
import SignUp from "../components/SignUp.vue"
import Home from "../components/Home.vue"

Vue.use(Router)
export default new Router({
    mode: 'history',
    base: process.env.BASE_URL,
    routes: [
        {
            path: '/calendar',
            name: 'Calendar',
            component: Calendar
        },
        {
            path: '/',
            name: 'HelloWorld',
            component: HelloWorld
        },
        {
            path: '/login',
            name: 'Login',
            component: Login
        },
        {
            path: '/signup',
            name: 'SignUp',
            component: SignUp
        },
        {
            path: '/:id',
            name: 'Home',
            component: Home,
            props: true
        }
    ]
})