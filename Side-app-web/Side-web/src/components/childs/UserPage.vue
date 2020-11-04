<template>
	<div id="contianer" class="dashboard-wrapper">
		<div class="left-sidebar">
			<div class="row-fluid">
				<div class="span12">
					<div class="widget">
						<div class="widget-header">
							<div class="title"><span>用户列表</span></div>
						</div>
						<div class="widget-body">
							<div id="dt_example" class="example_alt_pagination">
		                      <div id="data-table_wrapper" class="dataTables_wrapper" role="grid">
			                      <div id="data-table_length" class="dataTables_length">
			                      	<button type="button" class="btn btn-small btn-success bottom-margin" v-on:click="openUserEdit('add')">新增</button>
									<button type="button" class="btn btn-small btn-warning2 bottom-margin" v-on:click="openUserEdit('edit')">修改</button>
									<button type="button" class="btn btn-small btn-danger bottom-margin" v-on:click="deleteUser">删除</button>
									<button type="button" class="btn btn-small btn-info bottom-margin" v-on:click="userRoleSetting">角色绑定</button>
			                      </div>
			                      <div class="dataTables_filter" id="data-table_filter">
			                      	<label>Search: <input type="text" v-model="userInfo.searchKey" v-on:keyup="searchUser" aria-controls="data-table"></label>
			                      </div>
			                      <table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table" aria-describedby="data-table_info">
								    <thead>
								      <tr>
								        <th style="width:17%">
								          用户编码
								        </th>
								        <th style="width:20%">
								          用户名称
								        </th>
								        <th style="width:16%; display: none;">
								          角色id
								        </th>
								        <th style="width:16%">
								          用户角色
								        </th>
								        <th style="width:16%">
								          用户状态
								        </th>
								        <th style="width:16%">
								          创建人
								        </th>
								        <th style="width:16%">
								          创建日期
								        </th>
								      </tr>
								    </thead>
								    <tbody>
								      <tr class="gradeA even" v-for="user in userList" v-on:click="selectRow(user.userId)">
								      	<td style="display: none;">
											<input type="hidden" ref="uid" v-model="user.userId">
										</td>
								        <td>
								          {{user.userCode}}
								        </td>
								        <td>
								          {{user.userName}}
								        </td>
								        <td style="display: none;">
								          {{user.roleId}}
								        </td>
								        <td>
								          {{user.roleName}}
								        </td>
								        <td>
								          {{user.formatStatus}}
								        </td>
								        <td class="hidden-phone">
								          {{user.createBy}}
								        </td>
								        <td class="hidden-phone">
								          {{user.formatDate}}
								        </td>
								      </tr>
								    </tbody>
								 </table>
								 <div class="dataTables_info" id="data-table_info">{{pageInfo}}</div>
								<div class="dataTables_paginate paging_full_numbers" id="data-table_paginate">
									<a tabindex="0" class="first paginate_button paginate_button_disabled" id="data-table_first" v-on:click="toFirst">First</a><a tabindex="0" class="previous paginate_button paginate_button_disabled" id="data-table_previous" v-on:click="toPrevious">Previous</a><span><a tabindex="0" class="paginate_active" v-for="index in pageCount" v-on:click="pageLoad(index)">{{index}}</a></span><a tabindex="0" class="next paginate_button" id="data-table_next" v-on:click="toNext">Next</a><a tabindex="0" class="last paginate_button" id="data-table_last" v-on:click="toLast">Last</a>
								</div>
		                      </div>
		                      <div class="clearfix">
		                      </div>
		                    </div>
						</div>
					</div>
				</div>
			</div>

			<!-- 新增编辑弹出框 -->
			<div class="modal hide fade" id="userModal" tabindex="-1" style="display: none; " role="dialog" aria-labelledby="userModalLabel">
				<div class="widget no-margin" role="document">
					<div class="widget-header">
						<div class="title">用户编辑</div>
						<div class="tools">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						</div>
					</div>
					<div class="widget-body">
						<form class="form-horizontal no-margin">
	                      <div class="tab-content" id="myTabContent">
	                        <div id="step1" class="tab-pane fade active in">

	                          <div class="control-group">
	                            <label class="control-label" for="userCode">
	                              User Code
	                            </label>
	                            <div class="controls">
	                              <input type="text" id="userCode" placeholder="User Code" v-model="sideUser.userCode">
	                            </div>
	                          </div>
	                          <div class="control-group">
	                            <label class="control-label" for="userName">
	                              User Name
	                            </label>
	                            <div class="controls">
	                              <input type="text" id="userName" placeholder="User Name" v-model="sideUser.userName">
	                            </div>
	                          </div>
	                          <div class="control-group">
	                            <label class="control-label" for="userStatus">
	                              User Status
	                            </label>
	                            <div class="controls">
	                            	 <select id="userStatus" v-model="sideUser.userStatus">
	                            	 	<option value="0">无效</option>
	                            	 	<option value="1" selected>正常</option>
	                            	 	<option value="2">冻结</option>
	                            	 </select>
	                            </div>
	                          </div>

	                          <hr>
	                        </div>
	                        <div id="step2" class="tab-pane fade">
	                          <div class="control-group">
	                            <label class="control-label" for="accCode">
	                              Account Number
	                            </label>
	                            <div class="controls">
	                              <input type="text" id="accCode" placeholder="Account Number" v-model="sideUser.account.accCode">
	                            </div>
	                          </div>
	                          <div class="control-group">
	                            <label class="control-label" for="accName">
	                              Account Name
	                            </label>
	                            <div class="controls">
	                              <input type="text" id="accName" placeholder="Account Name" v-model="sideUser.account.accName">
	                            </div>
	                          </div>
	                          <div class="control-group">
	                            <label class="control-label" for="accPassword">
	                              Password
	                            </label>
	                            <div class="controls">
	                              <input type="password" id="accPassword" placeholder="Account Password" v-model="sideUser.account.accPassword">
	                            </div>
	                          </div>
	                          <div class="control-group">
	                            <label class="control-label">
	                              Gender
	                            </label>
	                            <div class="controls">
	                              <label class="radio inline">
	                                <input type="radio" name="accSex" value="M" v-model="sideUser.account.accSex" checked>
	                                Male
	                              </label>
	                              <label class="radio inline">
	                                <input type="radio" name="accSex" value="F" v-model="sideUser.account.accSex">
	                                Female
	                              </label>
	                            </div>
	                          </div>

	                          <div class="control-group">
	                            <label class="control-label" for="birthday">
	                               Date of birth
	                            </label>
	                            <div class="controls">
	                            		<div class="input-append">
	                            			<!--<date-picker :date="birthday" :option="option" :limit="limit" id="birthday" class="date_picker"></date-picker>-->
			                             <input type="text" id="birthday" name="accBirthday" placeholder="birthday" class="date_picker" v-model="sideUser.account.accBirthday"/>
			                             <span class="add-on btn date_picker" v-on:click="openDatePicker">
				                              <i class="icon-calendar"></i>
				                         </span>
	                            		</div>
	                            </div>
	                          </div>
	                          <div class="control-group">
	                          	<label class="control-label" for="tel">
	                              TEL
	                            </label>
	                            <div class="controls">
	                              <input type="text" id="tel" placeholder="tel" v-model="sideUser.account.tel">
	                            </div>
	                          </div>
	                          <div class="control-group">
	                            <label class="control-label" for="userStatus">
	                              Account Status
	                            </label>
	                            <div class="controls">
	                            	 <select id="accStatus" v-model="sideUser.account.accStatus">
	                            	 	<option value="0">无效</option>
	                            	 	<option value="1" selected>正常</option>
	                            	 	<option value="2">冻结</option>
	                            	 </select>
	                            </div>

	                          </div>
                            <div>
                                <label class="control-label" for="userStatus">
                                  Is System User
                                </label>
                                <div class="controls">
                                	 <select id="accStatus" v-model="sideUser.isSystemUser">
                                	 	<option value="0">是</option>
                                	 	<option value="1" selected>否</option>
                                	 </select>
                                </div>
                            </div>

	                          <hr>
	                        </div>
	                        <div class="next-prev-btn-container pull-right" style="margin-right: 10px;">
	                          <a data-toggle="tab" href="#step1" class="button prev">
	                            First
	                          </a>
	                          <a data-toggle="tab" href="#step2" class="button">
	                            Next
	                          </a>
	                          <a data-toggle="tab" href="#" class="button next" v-on:click="save">
	                            Finish
	                          </a>
	                          <div class="clearfix">
	                          </div>
	                        </div>
	                      </div>
	                    </form>
					</div>
				</div>
			</div>

			<!--
            	作者：Cat_870223@126.com
            	时间：2019-08-23
            	描述：绑定角色
            -->
            <div class="modal hide fade" id="userRoleModal" tabindex="-1" style="display: none; " role="dialog" aria-labelledby="userRoleModalLabel">
            		<div class="widget no-margin" role="document">
            			<div class="widget-header">
						<div class="title">角色绑定</div>
						<div class="tools">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						</div>
					</div>
					<div class="widget-body">
						<form class="form-horizontal no-margin">
	                     	<div class="control-group">
	                            <label class="control-label" for="userRole">
	                              角色
	                            </label>
	                            <div class="controls controls-row">
	                              <button type="button" class="span1 input-left-top-margins btn" :class="{'btn-info' : role.ischoose, 'btn-default' : !role.ischoose}" style="width: 120px; margin-top: 10px;" v-for="(role, index) in roleList" v-on:click="choose(role.id, index)">{{role.roleName}}</button>
	                            </div>
	                          </div>
	                          <hr>
							 <div class="next-prev-btn-container pull-right">
								<button type="button" class="button" v-on:click="roleSet">提交</button>
								<button type="button" class="button" data-dismiss="modal">取消</button>
							 </div>
							 <div class="clearfix"></div>
	                    </form>
					</div>
            		</div>
            </div>

		</div>
	</div>
</template>

<script>
	import '../../assets/js/template/js/date-picker/daterangepicker.js'
	import '../../assets/js/template/js/date-picker/date.js'
	export default{
		name : "userPage",
		data : function(){
			return {
				userList : [],
				roleList : [],
				userRoleList : [],
				userInfo : {
					userId : "",
					userName : "",
					userCode : "",
					userStatus : "",
					searchKey : ""
				},
				sideUser : {
					userId : "",
					userName : "",
					userCode	 : "",
					userStatus : "",
					account : {
						accountId : "",
						accCode : "",
						accName : "",
						accSex : "",
						accPassword : "",
						accStatus : "",
						accBirthday : "",
						tel : ""
					}
				},
				pageInfo : "Showing $1 to $2 of $3 entries",
				pageCount : 1,
				pageNumber : 1,
				pageSize : 10,
				hasNextPage : false,
				hasPreviousPage : false
			}
		},
		created : function(){
			let _this = this;
			this.axios({
				method : "get",
				url : '/side/users/getUserList',
				params : {dto : _this.userInfo, pageNumber : _this.pageNumber, pageSize : _this.pageSize}
			}).then(response => {
				_this.$data.userList = response.data.pageMode.records;
				_this.$data.pageCount = response.data.pageMode.pageCount;
				_this.$data.pageInfo = _this.$data.pageInfo.replace("$1",response.data.pageMode.firstIndex)
														  .replace("$2",response.data.pageMode.lastIndex)
														  .replace("$3",response.data.pageMode.count);
				_this.$data.pageNumber = response.data.pageMode.pageNumber;
				_this.$data.pageSize = response.data.pageMode.pageSize;
				_this.$data.hasPreviousPage = response.data.pageMode.hasPreviousPage;
				_this.$data.hasNextPage = response.data.pageMode.hasNextPage;
			}).catch(response => {
				if (response.data != null && response.data != undefined){
					this.$alertify.error(response.data.retMsg);
				} else {
					this.$alertify.error("执行时发生异常，请联系管理员");
				}
			})
		},
		methods : {
			searchUser : function(){
				let _this = this;
				this.axios({
					method : "get",
					url : '/side/users/getUserList',
					params : {dto : _this.userInfo, pageNumber :  _this.pageNumber, pageSize :  _this.pageSize}
				}).then(response => {
					_this.$data.userList = response.data.pageMode.records;
					_this.$data.pageCount = response.data.pageMode.pageCount;
					_this.$data.pageInfo = _this.$data.pageInfo.replace("$1",response.data.pageMode.firstIndex)
															  .replace("$2",response.data.pageMode.lastIndex)
															  .replace("$3",response.data.pageMode.count);
					_this.$data.pageNumber = response.data.pageMode.pageNumber;
					_this.$data.pageSize = response.data.pageMode.pageSize;
					_this.$data.hasPreviousPage = response.data.pageMode.hasPreviousPage;
					_this.$data.hasNextPage = response.data.pageMode.hasNextPage;
				}).catch(response => {
					if (response.data != null && response.data != undefined){
						this.$alertify.error(response.data.retMsg);
					} else {
						this.$alertify.error("执行时发生异常，请联系管理员");
					}
				})
			},
			selectRow : function(userId){
				if(userId != null && userId != undefined){
					this.$data.sideUser.userId = userId;
				}
			},
			openUserEdit : function(btnType){
				let _this = this;
				if(btnType == 'add'){
					_this.$data.sideUser = {
											userId : "",
											userName : "",
											userCode	 : "",
											userStatus : "",
											account : {
												accountId : "",
												accCode : "",
												accName : "",
												accSex : "",
												accPassword : "",
												accStatus : "",
												accBirthday : "",
												tel : ""
											}};
					$("#userModal").modal('show');
				} else {
					if(_this.$data.sideUser.userId == "" || _this.$data.sideUser.userId == undefined){
						this.$alertify.error("请选择需要编辑的用户");
						return;
					} else {
						_this.axios({
							method: 'get',
							url: '/side/users/userInfo',
							params : {userId : _this.$data.sideUser.userId}
						}).then(response=>{
							if(response.data.retCode=="0000" && (response.data.record != null || response.data.record.length > 0)){
								let user = response.data.record[0];
								_this.$data.sideUser = user;
								$("#userModal").modal('show');
							} else {
								this.$alertify.error(response.data.retMsg);
								return;
							}
						}).catch(response=>{
							this.$alertify.error("获取用户信息异常");
							return;
						});
					}
				}
			},
			userRoleSetting : function(){
				if(this.sideUser.userId == "" || this.sideUser.userId == undefined){
					this.$alertify.error("请选择用户");
					return;
				} else {
					let _this = this;
					//获取全部角色
					this.axios({
						method : 'get',
						url : '/side/role/list'
					}).then(function(response){
						if(response.data.retCode == "0000"){
							if(response.data.record != null){
								for(let i=0; i < response.data.record.length; i++){
									response.data.record[i].ischoose=false;
								}
								_this.$data.roleList = response.data.record;
							}
						}
					}).catch(function(response){
						_this.$alertify.error("查询发送异常，请联系管理员");
					});
					//获取用户角色
					this.axios({
						method : 'get',
						url : '/side/userRole/getUserRole',
						params : {userId : _this.$data.sideUser.userId}
					}).then(function(response){
						if(response.data.retCode == "0000"){
							if(response.data.record != null){
								for(let i=0; i < response.data.record.length; i++){
									if(response.data.record.length == 0) break;
									let userRole = response.data.record[i];
									for(let j=0; j < _this.$data.roleList.length; j++){
										let currRole = _this.$data.roleList[j];
										if(userRole.roleId.roleId == currRole.roleId){
											_this.$data.roleList[j].ischoose=true;
										}
									}
								}
							}
						}
					}).catch(function(response){
						_this.$alertify.error("查询发送异常，请联系管理员");
					});

					$("#userRoleModal").modal("show");
				}
			},
			choose : function(roleId,index){
				if(this.roleList[index].ischoose){
					this.roleList[index].ischoose = false;
				} else {
					this.roleList[index].ischoose = true;
				}

			},
			roleSet : function(){
				let _this = this;
				let userRoleList = new Array();
				for(let i = 0; i < _this.$data.roleList.length; i++){
					let userRoleSet = {};
					let role = _this.$data.roleList[i];
					if(role.ischoose){
						userRoleSet.userRoleId=null;
						userRoleSet.userId=_this.$data.sideUser.userId;
						userRoleSet.roleId=role.roleId
						userRoleList.push(userRoleSet);
					}
				}
				this.axios({
					method : 'post',
					url : '/side/userRole/saveRoleUser',
					data : userRoleList
				}).then(function(response){
					if(response.data.retCode == "0000"){
						$("#userRoleModal").modal("hide");
						_this.searchUser();
					}
				}).catch(function(response){
					_this.$alertify.error("查询发送异常，请联系管理员");
				});

			},
			save : function(){
				let _this = this;
				if(_this.sideUser.userName == "" || _this.sideUser.userCode == ""){
					this.$alertify.error("用户名或用户编码不能为空");
					return;
				}
				if(_this.sideUser.account.accCode == "" || _this.sideUser.account.accName == "" || _this.sideUser.account.accPassword == ""){
					this.$alertify.error("账户名、账户编码、账户密码不能为空");
					return;
				}
				_this.axios({
					method: "post",
					url: "/side/users/userEditer",
					data:_this.sideUser
				}).then(response => {
					if(response.data.retCode == "0000"){
						$("#userModal").modal('hide');
						this.$alertify.success(response.data.retMsg);
						if(response.data.pageMode != null){
							_this.$data.userList = response.data.pageMode.records;
							_this.$data.pageCount = response.data.pageMode.pageCount;
							_this.$data.pageInfo = _this.$data.pageInfo.replace("$1",response.data.pageMode.firstIndex)
																	  .replace("$2",response.data.pageMode.lastIndex)
																	  .replace("$3",response.data.pageMode.count);
							_this.$data.pageNumber = response.data.pageMode.pageNumber;
							_this.$data.pageSize = response.data.pageMode.pageSize;
						}
					}
				}).catch(response => {
					this.$alertify.error("执行时发生异常！请联系系统管理员");
				})
			},
			deleteUser : function(){
				let _this = this;
				if(_this.$data.sideUser.userId == "" || _this.$data.sideUser.userId == undefined){
					this.$alertify.error("请选择用户");
					return;
				} else {
					this.$alertify.confirmWithTitle(
						'提示!',
						'你确定删除该用户吗？',
						() => {
							$(".alertify").css("display", "none");
							//发送请求
							this.axios({
								method : 'delete',
								url : "/side/users/delete",
								params : {userId : _this.$data.sideUser.userId}
							}).then(response => {
								if(response.data.retCode == "0000"){
									this.$alertify.success(response.data.retMsg);
									_this.searchUser();
								}
							}).catch(response => {
								if (response.data.retMsg != null){
									this.$alertify.error(response.data.retMsg);
								} else {
									this.$alertify.error("执行时发生异常，请联系管理员");
								}
							});
						},
						() => {
							$(".alertify").css("display", "none");
						}
					);
				}
			},
			pageLoad : function(index){
				this.$data.pageNumber = index;
				this.searchUser();
			},
			toFirst : function(){
				if(this.hasPreviousPage){
					this.$data.pageNumber = 1;
					this.searchUser();
				} else {
					this.$alertify.success("已经是第一页");
				}

			},
			toLast : function(){
				if(this.hasNextPage){
					this.$data.pageNumber = this.pageCount;
					this.searchUser();
				} else {
					this.$alertify.success("已经是最后一页");
				}

			},
			toNext : function(){
				if(this.hasNextPage){
					this.$data.pageNumber = this.pageNumber+1;
					this.searchUser();
				} else {
					this.$alertify.success("没有下一页");
				}
			},
			toPrevious : function(){
				if(this.hasPreviousPage){
					this.$data.pageNumber = this.pageNumber-1;
					this.searchUser();
				} else {
					this.$alertify.success("没有上一页");
				}
			},
			openDatePicker : function(){
				let _this = this;
				$('.date_picker').daterangepicker({
				    singleDatePicker: true,//设置为单个的datepicker，而不是有区间的datepicker 默认false
                    showDropdowns: true,//当设置值为true的时候，允许年份和月份通过下拉框的形式选择 默认false
			        opens: 'right',
			        format: 'yyyy-MM-dd',
			        separator: ' to ',
			        startDate: Date.today(),
			        endDate: Date.today(),
			        locale: {
			          applyLabel: 'Submit',
			          fromLabel: 'From',
			          toLabel: 'To',
			          customRangeLabel: 'Custom Range',
			          daysOfWeek: ['Su', 'Mo', 'Tu', 'We', 'Th', 'Fr', 'Sa'],
			          monthNames: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
			          firstDay: 1
			        },
			        showWeekNumbers: true,
			        buttonClasses: ['btn-danger']
				}, function(start, end, label) {
				  _this.$data.sideUser.account.accBirthday = start;
				});
			}
		}
	}
</script>

<style>
	.daterangepicker{
		z-index: 9999;
	}
</style>
