<template>
	<div class="container-fluid" align="center" id="login">
		<div style="width:620px;height:320px;">
		<div align="center" style="width:600px;height:250px;"></div>
		<div style="padding:10px 10px 10px 10px" align="center" class="sign-in-container">
			<form id="loginForm" action="login" method="post" class="form-horizontal login-wrapper">
				<div class="header">
                  <div class="row-fluid">
                    <div class="span12">
                     	<div align="center" ><img style="width:600px;height:80px;" src="../assets/logo/logo.png" /></div>
                    </div>
                  </div>
				</div>

				<div class="content">
					<div class="row-fluid">
					    <div class="span12">
					      <input type="text" class="input span12 text" id="userCode" name="userCode" v-model="usercode" placeholder="User ID">
					    </div>
					</div>
					<div style="padding:10px 10px 10px 10px" align="center"></div>
					<div class="row-fluid">
					    <div class="span12">
					      <input type="password" class="input span12 password" id="password" name="password" v-model="pwd" placeholder="Password">
					    </div>
					</div>
				</div>

				<div class="actions">
					<button type="button" class="btn btn-warning2" v-on:click="dologin">Sign in</button>
			      	<a class="link" href="#">Forgot Password?</a>
			      	<div class="clearfix"></div>
  				</div>
			</form>
		</div>
		</div>
	</div>
</template>

<script>
	export default{
		data : function() {
				return{
					usercode : "",
					pwd : ""
				}
		},
		methods : {
			dologin : function(){
				let _this = this;
//				let encryptor = new JSEncrypt();
				this.jsEncrypt.setPublicKey(this.publicKey);
				if(!this.usercode || !this.pwd){
					this.$alertify.error("请输入账号密码!");
				}
				_this.axios({
					method : 'post',
					data : {
						username : this.usercode,
						password : _this.jsEncrypt.encrypt(this.pwd),
					    client_secret :_this.jsEncrypt.encrypt("secret")
					},
					url : '/auth-center/oauth/token'
				}).then(function(response){
					if(response.data != null){
						localStorage.setItem('access_token', response.data.access_token);
						_this.router.push({
							path: 'main'
						});
					}
				}).catch(response => {
					this.$alertify.error("查询发送异常，请联系管理员");
				});
			}
		}
	}
</script>

<style>
</style>
