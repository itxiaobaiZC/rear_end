import store from '@/store'
import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

const routes = [
  {
    path: "/login",
    name: "Login",
    component: () => import('../views/Login.vue')
  },
  {
    path: "/sgin",
    name: "Sgin",
    component: () => import('../views/Sgin.vue')
  },
  {
    path: "/404",
    name: "404",
    component: () => import('../views/404.vue')
  },
  {
    path: "/front",
    name: "Front",
    component: () => import('../views/front/Front.vue'),
    children:[
    {
      path:'fhome',
      name:'FrontHome',
      component: () => import('../views/front/Home.vue'),
    },
    {
      path:'garbage',
      name:'FrontGarbage',
      component: () => import('../views/front/FrontGarbage.vue'),
    },
    {
      path:'article',
      name:'FrontArticle',
      component: () => import('../views/front/FrontArticle.vue'),
    },
    {
      path:'articleDetail',
      name:'FrontArticleDetail',
      component: () => import('../views/front/FrontArticleDetail.vue'),
    },
    {
      path:'policy',
      name:'Policy',
      component: () => import('../views/front/Policy.vue'),
    },
    {
      path:'policyDetail',
      name:'policyDetail',
      component: () => import('../views/front/PolicyDetail.vue'),
    }
    ,
    {
      path:'test',
      name:'FrontGarbageTest',
      component: () => import('../views/front/FrontGarbageTest.vue'),
    }
    ,
    {
      path:'hello',
      name:'Hello',
      component: () => import('../views/front/Hello.vue'),
    }

  ]
  }

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export const resetRouter= ()=>{
  router.matcher=new VueRouter({
    mode:'history',
    base:process.env.BASE_URL,
    routes
  })
}


export const setRouter = () => {
  const storeMenus = localStorage.getItem("menus");
  if (storeMenus) {
    //拼装动态路由
    const manageRoute = { path: '/',name:'Manage' ,component: () => import('../views/ManageView.vue'), redirect: "/home", children: [
      {path:'person', name:'个人信息',component:()=>import('../views/Person.vue')},
      //
      {path:'password', name:'修改密码',component:()=>import('../views/Password.vue')},
    ]}
    const menus = JSON.parse(storeMenus)
    menus.forEach(item => {
      if (item.path) { //path不为空的时候
        let itemMenu = { path: item.path.replace("/", ""), name: item.name, component: () => import('../views/' + item.pagePath + '.vue') }
        manageRoute.children.push(itemMenu)
      } else if (item.children.length) {
        item.children.forEach(item => {
          if (item.path) {
            let itemMenu = { path: item.path.replace("/", ""), name: item.name, component: () => import('../views/' + item.pagePath + '.vue') }
            manageRoute.children.push(itemMenu)
          }
        })
      }
    })
    //获取当前的路由对象名称数组
  const currentRouteNames=router.getRoutes().map(v=>v.name)
  if(!currentRouteNames.includes('Manage')){
      //动态添加到现在的路由对象
      router.addRoute(manageRoute)
  }

  
  }
}

//重置路由
setRouter();

router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)
  store.commit("setPath")


  if(!to.matched.length){
    const storeMenus=localStorage.getItem("menus")
    if(storeMenus){
      next("/404")
    }else{
      next("/login")
    }
  }
  next()

})

export default router
