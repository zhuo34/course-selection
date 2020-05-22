import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/components/Home'
import SearchView from '@/components/SearchView'
import ProgramView from '@/components/ProgramView'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'Home',
      component: Home,
      children: [
        {
          path: '/search',
          name: 'SearchView',
          component: SearchView
        },
        {
          path: '/program',
          name: 'ProgramView',
          component: ProgramView
        }
      ]
    }
  ]
})
