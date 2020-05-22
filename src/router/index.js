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
      redirect: '/search',
      children: [
        {
          path: '/search',
          name: 'SearchView',
          component: SearchView,
          meta: {
            title: '选课界面'
          }
        },
        {
          path: '/program',
          name: 'ProgramView',
          component: ProgramView,
          meta: {
            title: '培养方案'
          }
        }
      ]
    },
    {
      path: '*',
      name: 'default',
      redirect: '/',
      meta: {
        title: '选课界面'
      }
    }
  ]
})
