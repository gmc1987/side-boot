<template>
<div>
	<header>
      <a href="#" class="logo">
        <img src="../assets/logo/logo.png" alt="Logo"/>
      </a>
      <div class="btn-group">
        <button id="userBtn" class="btn btn-primary" v-model="user_info" >{{user_info.userName}}</button>
        <button data-toggle="dropdown" class="btn btn-primary dropdown-toggle">
          <span class="caret">
          </span>
        </button>
        <ul class="dropdown-menu pull-right">
          <li>
            <a href="#">
              编辑个人信息
            </a>
          </li>
          <li>
            <a href="#">
              账号设置
            </a>
          </li>
          <li>
            <a href="#">
              Logout
            </a>
          </li>
        </ul>
      </div>
      <ul class="mini-nav">
        <li>
          <a href="#">
            <div class="fs1" aria-hidden="true" :data-icon="icon1"></div>

          </a>
        </li>
        <li>
          <a href="#">
            <div class="fs1" aria-hidden="true" :data-icon="icon2"></div>

          </a>
        </li>
      </ul>
    </header>
    <div class="container-fluid">
    	<div class="dashboard-container">
    		<div class="top-nav">
    			<ul>
    				<li v-for="(parentMenu, index) in parentMenuArray">
    					<a href="#" v-bind:class="[parentNoSelectClass, parentSelectIndex==index ? parentSelectClass : parentNoSelectClass]" v-on:click="navigationBarSelect(parentMenu, index)">
    						<div class="fs1" aria-hidden="true" :data-icon="parentMenu.icon"></div>
    						{{parentMenu.menuName}}
    					</a>
    				</li>
    			</ul>
    			<div class="clearfix"></div>
    		</div>
    		<div class="sub-nav">
    			<ul>
    				<li v-for="(child, index) in childMenuArray">
    					<a href="#" v-bind:class="[subNoSelectClass, childSelectIndex==index ? subSelectClass : subNoSelectClass]" v-on:click="subNavigationBarSelect(index)">{{child.menuName}}</a>
    				</li>
    			</ul>
    		</div>
    		<div>
    			<router-view></router-view>
    		</div>
    	</div>
    </div>

    <footer>
      <p>
        &copy; gmc 2018
      </p>
    </footer>
</div>
</template>

<script>
	export default{
		name : 'mainpage',
		data : function() {
			return {
				user_info : null,
				parentMenuArray : null,
				childMenuArray : null,
				icon1 : '\ue040',
				icon2 : '\ue04c',
				parentSelectIndex: 0,
				childSelectIndex: 0,
				parentSelectClass: 'selected',
				parentNoSelectClass: '',
				subSelectClass: 'heading',
				subNoSelectClass: ''
			}
		},
		created : function(){
			let _this = this;
			this.axios({
				method : 'get',
				url : '/side/dologin'
			}).then(function(response) {
				if(response.data.user_info != null){
					//把用户信息赋值到user_info对象中
					_this.$data.user_info = response.data.user_info;
				}
				if(response.data.parents != null && response.data.parents.length > 0){
					_this.$data.parentMenuArray = response.data.parents;
				}
				if(response.data.firstChilds != null && response.data.firstChilds.length > 0){
					_this.$data.childMenuArray = response.data.firstChilds;
				}
				_this.router.replace({
					path: _this.$data.childMenuArray[0].menuPath
				});
			}).catch(response => {
				this.$alertify.error("查询发送异常，请联系管理员");
			});
		},
		methods : {
				navigationBarSelect: function(menu, index){
            this.$data.parentSelectIndex = index;
            let _this = this;
            this.axios({
            	method : 'get',
              params : {'parentId' : menu.menuId},
            	url : '/side/getChildParentMenus'
            }).then(function(response){
            	if(response.data.retCode == "0000"){
            		if(response.data.record != null){
            			_this.$data.childMenuArray = response.data.record;
                  _this.subNavigationBarSelect(0);
            		}
            	}
            }).catch(function(response){
            	_this.$alertify.error("查询发送异常，请联系管理员");
            });
				},
				subNavigationBarSelect: function(index){
					this.$data.childSelectIndex = index;
					if(this.$data.childMenuArray.length > 0){
						var menu = this.$data.childMenuArray[index];
						this.router.replace({
							path: menu.menuPath
						});

					} else {
						this.$alertify.error("菜单数据异常");
						return;
					}

				}
		}
	}
</script>

<style>
</style>
