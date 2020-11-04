<template>
	<div id="contianer" class="dashboard-wrapper">
		<div class="left-sidebar">
			<div class="row-fluid">
				<div class="span12">
					<div class="widget">
						<div class="widget-header">
							<div class="title"><span>租户列表</span></div>
						</div>
						<div class="widget-body">
							<div id="dt_example" class="example_alt_pagination">
		                      <div id="data-table_wrapper" class="dataTables_wrapper" role="grid">
			                      <div id="data-table_length" class="dataTables_length">
			                      	<button type="button" class="btn btn-small btn-info bottom-margin" v-on:click="openTenantInfo()">详情</button>
			                      </div>
			                      <div class="dataTables_filter" id="data-table_filter">
			                      	<label>Search: <input type="text" v-model="tenantInfo.searchKey" v-on:keyup="searchUser" aria-controls="data-table"></label>
			                      </div>
			                      <table class="table table-condensed table-striped table-hover table-bordered pull-left" id="data-table" aria-describedby="data-table_info">
								    <thead>
								      <tr>
								        <th style="width:10%">
								          租户编码
								        </th>
								        <th style="width:10%">
								          租户姓名
								        </th>
								        <th style="width:10%;">
								          联系电话
								        </th>
								        <th style="width:10%">
								          性别
								        </th>
                        <th style="width: 10%;">
                          信用分
                        </th>
								        <th style="width:10%">
								         审核状态
								        </th>
								        <th style="width:10%">
								          创建人
								        </th>
								        <th style="width:10%">
								          创建日期
								        </th>
								      </tr>
								    </thead>
								    <tbody>
								      <tr class="gradeA even" v-for="(tenant, index) in tenantList" v-on:click="selectRow(tenant.tenantId, index)">
								      	<td style="display: none;">
                          <input type="hidden" ref="uid" v-model="tenant.tenantId">
                        </td>
								        <td>
								          {{tenant.tenantCode}}
								        </td>
								        <td>
								          {{tenant.user.userName}}
								        </td>
								        <td>
								          {{tenant.user.account.tel}}
								        </td>
								        <td>
								          {{tenant.user.account.isFemale}}
								        </td>
                        <td>
                           {{tenant.trustNum}}
                        </td>
								        <td>
								          {{tenant.isAudited}}
								        </td>
								        <td class="hidden-phone">
								          {{tenant.createBy}}
								        </td>
								        <td class="hidden-phone">
								          {{tenant.formatCreateDate}}
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
			<div class="modal hide fade" id="tenantModal" tabindex="-1" style="display: none; " role="dialog" aria-labelledby="tenantModalLabel">
				<div class="widget no-margin" role="document">
					<div class="widget-header">
						<div class="title">租户编辑</div>
						<div class="tools">
							<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						</div>
					</div>
					<div class="widget-body">
						<form class="form-horizontal no-margin">
	                      <div class="tab-content" id="myTabContent">
	                        <div id="step1" class="tab-pane fade active in">

	                          <div class="control-group">
	                            <label class="control-label" for="tenantCode">
	                              租户编码
	                            </label>
	                            <div class="controls">
	                              <input type="text" id="tenantCode" placeholder="租户编码" v-model="tenant.tenantCode">
	                            </div>
	                          </div>
	                          <div class="control-group">
	                            <label class="control-label" for="userName">
	                              租户名称
	                            </label>
	                            <div class="controls">
	                              <input type="text" id="userName" placeholder="租户名称" v-model="tenant.user.userName">
	                            </div>
	                          </div>
                            <div class="control-group">
                              <label class="control-label">
                                性别
                              </label>
                              <div class="controls">
                                <label class="radio inline">
                                  <input type="radio" name="accSex" value="M" v-model="tenant.user.account.accSex" checked>
                                  Male
                                </label>
                                <label class="radio inline">
                                  <input type="radio" name="accSex" value="F" v-model="tenant.user.account.accSex">
                                  Female
                                </label>
                              </div>
                            </div>
                            <div class="control-group">
                            	<label class="control-label" for="tel">
                                电话
                              </label>
                              <div class="controls">
                                <input type="text" id="tel" placeholder="tel" v-model="tenant.user.account.tel">
                              </div>
                            </div>
	                          <div class="control-group">
	                            <label class="control-label" for="auditFlag">
	                              审核状态
	                            </label>
	                            <div class="controls">
	                            	 <select id="auditFlag" v-model="tenant.auditFlag">
	                            	 	<option value="0">未审核</option>
	                            	 	<option value="1" selected>已审核</option>
                                  <option value="2">审核失败</option>
	                            	 </select>
	                            </div>
	                          </div>
	                          <hr>
	                        </div>
	                        <div id="step2" class="tab-pane fade">
	                          <div class="control-group">
	                            <label class="control-label" for="idCardType">
	                              证件类型
	                            </label>
	                            <div class="controls">
	                            	 <select id="idCardType" v-model="tenant.idCardType">
	                            	 	<option value="0" selected>身份证</option>
	                            	 	<option value="1" >军官证</option>
	                            	 	<option value="2">回乡证</option>
	                            	 </select>
	                            </div>
	                          </div>
                            <div class="control-group">
                              <label class="control-label" for="idCard">
                                证件号码
                              </label>
                              <div class="controls">
                              	 <input type="text" id="idCard" placeholder="证件号码" v-model="tenant.idCard">
                              </div>
                            </div>
                            <div class="control-group">
                              <label class="control-label" for="cardPhotofirst">
                                证件照（正面）
                              </label>
                              <div class="controls">
                              	 <img id="cardPhotofirst" style="width: 280px; height: 150px;" src=""/>
                              </div>
                            </div>
                            <div class="control-group">
                              <label class="control-label" for="cardPhotosecond">
                                证件照（反面）
                              </label>
                              <div class="controls">
                              	 <img id="cardPhotosecond" style="width: 280px; height: 150px;" src=""/>
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

		</div>
	</div>
</template>

<script>
	import '../../assets/js/template/js/date-picker/daterangepicker.js'
	import '../../assets/js/template/js/date-picker/date.js'
	export default{
		name : "tenantPage",
		data : function(){
			return {
				tenantList : [],
				tenantInfo : {
					tenantId : "",
					tenantCode : "",
          trustNum : "",
          idCard : "",
          idCardType: "",
          cardPhotofirst: null,
          cardPhotosecond: null,
					auditFlag : "",
          userId: "",
					searchKey : ""
				},
				tenant : {
					tenantId : "",
					tenantCode : "",
					user	 : {
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
					trustNum : "",
					idCard : "",
          idCardType: "",
          cardPhotofirst: "",
          cardPhotosecond: "",
          auditFlag: ""
				},
        selectIndex : "",
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
      _this.tenantInfo.auditFlag = 1;
			this.axios({
				method : "get",
				url : '/side/tenant/service/list',
				params : {dto : _this.tenantInfo, pageNumber : _this.pageNumber, pageSize : _this.pageSize}
			}).then(response => {
				_this.$data.tenantList = response.data.pageMode.records;
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
        _this.tenantInfo.auditFlag = 1;
				this.axios({
					method : "get",
					url : '/side/tenant/service/list',
					params : {dto : _this.tenantInfo, pageNumber :  _this.pageNumber, pageSize :  _this.pageSize}
				}).then(response => {
					_this.$data.tenantList = response.data.pageMode.records;
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
			selectRow : function(tenantId, index){
				if(tenantId != null && tenantId != undefined && index != null && index != undefined){
					this.$data.tenant.tenantId = tenantId;
          this.$data.selectIndex = index;
				}
			},

      openTenantInfo : function(){
      	let _this = this;
        if((_this.$data.tenant.tenantId == "" || _this.$data.tenant.tenantId == undefined) && (_this.$data.selectIndex == "" || _this.$data.selectedIndex == undefined)) {
          this.$alertify.error("请选择需要编辑的用户");
          return;
        } else {
          this.$data.tenant =  this.$data.tenantList[this.selectIndex];
          if(this.tenant != null && this.tenant != undefined){
                if(this.tenant.cardPhotofirst != null && this.tenant.cardPhotofirst.length > 0){
                      $("#cardPhotofirst").src=window.URL.createObjectURL(this.tenant.cardPhotofirst);
                }
                if(this.tenant.cardPhotosecond != null && this.tenant.cardPhotosecond.length > 0){
                      $("#cardPhotosecond").src=window.URL.createObjectURL(this.tenant.cardPhotosecond);
                }
          }
          $("#tenantModal").modal('show');
          // _this.axios({
          // 	method: 'get',
          // 	url: '/side/users/userInfo',
          // 	params : {userId : _this.$data.tenant.tenantId}
          // }).then(response=>{
          // 	if(response.data.retCode=="0000" && (response.data.record != null || response.data.record.length > 0)){
          // 		let user = response.data.record[0];
          // 		_this.$data.tenant = user;
          // 		$("#userModal").modal('show');
          // 	} else {
          // 		this.$alertify.error(response.data.retMsg);
          // 		return;
          // 	}
          // }).catch(response=>{
          // 	this.$alertify.error("获取用户信息异常");
          // 	return;
          // });
        }
      },

			save : function(){
            $("#tenantModal").modal('hide');
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

		}
	}
</script>

<style>
	.daterangepicker{
		z-index: 9999;
	}
</style>
