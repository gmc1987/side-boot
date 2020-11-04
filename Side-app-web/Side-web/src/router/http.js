/**
 * http请求全局拦截器。用于拦截所有http请求中是否带有token信息和token是否已经超时。
 * 如果为否，将跳转到登录界面进行登录重新校验。
 * 如果为是，将带上用户token信息进行请求
 */

import axios from 'axios'
import router from './index'
import store from '../store/store'
import qs from 'qs'
import * as types from '../store/types'

//axios 配置
axios.defaults.timeout = 6000
axios.defaults.baseURL = 'http://localhost:8801'
//axios.defaults.baseURL = this.HOST
// http request 拦截器
axios.interceptors.request.use(
  config => {
  	//当发起请求为post请求时，对数据进行格式化处理
  	var accessToken = window.localStorage.getItem("access_token")
  	if (accessToken != null) { //判断token是否存在
    		config.headers.Authorization = "Bearer " + accessToken  //将token设置成请求头
	} 
  	if(config.method=="post"){
  		
  		//当发起请求是登录请求时
	  	if(config.url == "/auth-center/oauth/token"){
	      	config.data.client_id="client"
	      	// config.data.client_secret="secret"
	      	config.data.grant_type="password"
	      	config.data = qs.stringify(config.data)
        		config.headers['Content-Type'] = 'application/x-www-form-urlencoded'
        		
	  	} else{
	  		config.data = JSON.stringify(config.data)
	  		config.headers.post['Content-Type'] = 'application/json'
	  	}
        
  	} 
  	if(config.method=="get"){
  		config.headers.get['Content-Type'] = 'application/json'
		for (var param in config.params) {
			let myParam = config.params[param]
			if(myParam instanceof Object){
				for(var field in myParam){
					if(myParam[field] != null && myParam[field] != undefined && myParam[field] != ""){
						config.params[field]=myParam[field]
					}
				}
				// config.params[param] = undefined
			}
		}
//		if(config.url.indexOf("?") != -1){
//			config.url = config.url
//		} else {
//			config.url = config.url
//		}
  	}
    return config
  },
  err => {
    return Promise.reject(err)
  }
)

// http response 拦截器
axios.interceptors.response.use(
  response => {
    return response
  },
  error => {
    if (error.response) {
      switch (error.response.status) {
        case 401:
          // 401 清除token信息并跳转到登录页面
          store.commit(types.LOGOUT)
          
          // 只有在当前路由不是登录页面才跳转
          router.currentRoute.path !== '/' &&
            router.replace({
              path: '/',
              query: { redirect: router.currentRoute.path },
            })
      }
    }
    // console.log(JSON.stringify(error))//console : Error: Request failed with status code 402
    return Promise.reject(error.response.data)
  },
)
export default axios
