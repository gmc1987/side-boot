import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/components/Login'
import Main from '@/components/MainPage'
import MenuPage from '@/components/childs/MenuPage'
import RolePage from '@/components/childs/RolePage'
import AuthorizationPage from '@/components/childs/AuthorizationPage'
import UserPage from '@/components/childs/UserPage'
import TenantPage from '@/components/childs/TenantPage'
import TenantPageAudit from '@/components/childs/TenantPageAudit'
import store from '../store/store'
import * as types from '../store/types'


Vue.use(Router)

//配置路由地址
const routes = [
    {
        path: '/',
        name: 'Login',
        component: Login
    },
    {
    		path: '/main',
    		name: 'main',
    		component: Main,
    		children : [
	    		{
	    			path: 'menus',
	    			name: 'menus',
    				component : MenuPage
	    		},
	    		{
	    			path: 'roles',
	    			name: 'roles',
	    			component : RolePage
	    		},
	    		{
	    			path: 'authorization',
	    			name: 'authorization',
	    			component: AuthorizationPage
	    		},
	    		{
	    			path: 'users',
	    			name: 'users',
	    			component: UserPage
	    		},
				{
					path: 'tenanies/list',
					name: 'tenanies/list',
					component: TenantPage
				},
        {
        	path: 'tenant/audit/list',
        	name: 'tenant/audit/list',
        	component: TenantPageAudit
        }
    		]
    }
];

// 页面刷新时，重新赋值token
if (window.localStorage.getItem('token')) {
    store.commit(types.LOGIN, window.localStorage.getItem('token'))
}

const router = new Router({
	mode:'history',
    routes
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(r => r.meta.requireAuth)) {
        if (store.state.token) {
            next();
        }
        else {
            next({
                path: '/',
                query: {redirect: 'http://localhost:8080/'}
            })
        }
    }
    else {
        next();
    }
});

export default router;
