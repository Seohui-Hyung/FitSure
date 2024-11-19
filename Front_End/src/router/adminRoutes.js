
export default [
  {
    path: "/admin",
    name: "AdminLoginForm",
    component: () => import('@/components/forms/AdminLoginForm.vue')
  },{
    path: "/admin/home",
    name: "AdminHome",
    component: () => import('@/components/admin/UserList.vue')
  }

]
