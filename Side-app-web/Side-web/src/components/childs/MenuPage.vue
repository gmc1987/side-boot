<template>
	<div id="contianer" class="dashboard-wrapper">
	<div class="left-sidebar">
		<div class="row-fluid">
			<div class="span5">
				<div class="widget">
					<div class="widget-header">
						<div class="title"><span>一级菜单</span></div>
					</div>
					<div class="widget-body">
						<div id="dt_example" class="example_alt_pagination">
							<div class="dataTables_wrapper" id="data-table_wrapper" role="gird">
								<div class="dataTables_length">
									<button type="button" class="btn btn-small btn-success bottom-margin" v-on:click="addMenu">新增</button>
									<button type="button" class="btn btn-small btn-warning2 bottom-margin" v-on:click="editMenu">修改</button>
									<button type="button" class="btn btn-small btn-danger bottom-margin" v-on:click="delMenu">删除</button>
								</div>
								<!-- 查询条件 -->
								<div class="dataTables_filter" id="data-table_filter">
									<label>
										查询:
										<input type="text" v-model="parent_search" v-on:keyup="parentSelect" aria-controls="data-table">
									</label>
								</div>
								<!-- 表格主题 -->
								<table style="margin-bottom: 20px;" class="table table-condensed table-striped table-bordered table-hover no-margin">
								<thead>
									<tr>
										<th style="display: none;">
											菜单ID
										</th>
										<th>菜单编码</th>
										<th>菜单名称</th>
									</tr>
								</thead>
								<tbody>
									<tr v-on:click="select(menu.menuId, $event)" v-for="menu in menuList">
										<td style="display: none;">
											<input type="hidden" ref="pid" v-model="menu.menuId">
										</td>
										<td style="width: 120px;">
											{{menu.menuCode}}
										</td>
										<td>
											{{menu.menuName}}
										</td>
									</tr>
								</tbody>
								</table>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
			</div>
			<div class="span7">
				<div class="widget">
					<div class="widget-header">
						<div class="title"><span>二级菜单</span></div>
					</div>
					<div class="widget-body">
						<div id="dt_example" class="example_alt_pagination">
							<div class="dataTables_wrapper" id="data-table_wrapper" role="gird">
								<div class="dataTables_length">
									<button type="button" class="btn btn-small btn-success bottom-margin" v-on:click="addChild">新增</button>
									<button type="button" class="btn btn-small btn-warning2 bottom-margin" v-on:click="editChild">修改</button>
									<button type="button" class="btn btn-small btn-danger bottom-margin" v-on:click="delChild">删除</button>
								</div>
								<!-- 查询条件 -->
								<div class="dataTables_filter" id="data-table_filter">
									<label>
										查询:
										<input type="text" v-model="child_search" v-on:keyup="childSelect" aria-controls="data-table">
									</label>
								</div>
								<!-- 表格主题 -->
								<table style="margin-bottom: 20px;" class="table table-condensed table-striped table-bordered table-hover no-margin">
								<thead>
									<tr>
										<th style="width: 80px;">菜单编码</th>
										<th style="width: 100px;">菜单名称</th>
										<th>请求路径</th>
										<th>顺序</th>
									</tr>
								</thead>
								<tbody>
									<tr v-on:click="childChoose(child.menuId)" v-if="0 < childList.length" v-for="child in childList">
										<td style="display: none;">
											<input type="hidden" v-model="child.menuId">
										</td>
										<td style="width: 120px;">
											{{child.menuCode}}
										</td>
										<td>
											{{child.menuName}}
										</td>
										<td>
											{{child.menuPath}}
										</td>
										<td>
											{{child.menuSort}}
										</td>
									</tr>
								</tbody>
								</table>
							</div>
						</div>
						<div class="clearfix"></div>
					</div>
				</div>
				<div class="clearfix"></div>
			</div>
		</div>

		<!-- 新增编辑弹出框 -->
		<div class="modal hide fade" id="parentModal" tabindex="-1" style="display: none;" role="dialog" aria-labelledby="parentModalLabel">
			<div class="widget no-margin" role="document">
				<div class="widget-header">
					<div class="title">菜单编辑</div>
					<div class="tools">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					</div>
				</div>
				<div class="widget-body">
					<form id="editForm" class="form-horizontal no-margin" action="">
						<div id="first_control" class="control-group warning" style="display: none;">
							<label class="control-label">上级菜单</label>
							<div class="controls">
								<input type="text" disabled="disabled" name="parentMenu" id="parentMenu">
							</div>
						</div>
						<div class="control-group warning">
							<label class="control-label">菜单编码</label>
							<div class="controls">
								<input type="text" name="menuCode" id="menuCode" v-model="menuObject.menuCode">
							</div>
						</div>
						<div class="control-group warning">
							<label class="control-label">菜单名称</label>
							<div class="controls">
								<input type="text" name="menuName" id="menuName" v-model="menuObject.menuName">
							</div>
						</div>
						<div class="control-group warning">
							<label class="control-label">菜单类型</label>
							<div class="controls">
								<select name="menuType" id="menuType" v-model="menuObject.menuType">
									<option value="0">菜单</option>
									<option value="1">功能按钮</option>
								</select>
							</div>
						</div>
						<div class="control-group warning">
							<label class="control-label">菜单地址</label>
							<div class="controls">
								<input type="text" name="menuPath" id="menuPath" v-model="menuObject.menuPath">
							</div>
						</div>
						<div class="control-group warning">
							<label class="control-label">排序</label>
							<div class="controls">
								<select name="menuSort" id="menuSort" v-model="menuObject.menuSort">
									<option value="1">1</option>
									<option value="2">2</option>
									<option value="3">3</option>
									<option value="4">4</option>
									<option value="5">5</option>
									<option value="6">6</option>
									<option value="7">7</option>
									<option value="8">8</option>
									<option value="9">9</option>
									<option value="10">10</option>
								</select>
							</div>
						</div>
						<div id="last_control" class="control-group warning">
							<label class="control-label">菜单图标</label>
							<div class="controls">
								<input type="text" name="menuIcon" id="menuIcon" placeholder="选择图标" v-model="menuObject.menuIcon" data-target="#iconModal" data-toggle="modal">
								<ul>
									<li>
										<span id="exemple" class="fs1" aria-hidden="true" :data-icon="icon"></span>
									</li>
								</ul>
							</div>
						</div>
						<hr>
						<div class="next-prev-btn-container pull-right">
							<button type="button" class="button" v-on:click="menuSave">提交</button>
							<button type="button" class="button" data-dismiss="modal">取消</button>
						</div>
						<div class="clearfix"></div>
					</form>
				</div>
			</div>
		</div>

		<!-- 菜单图标选择框 -->
		<div class="modal-max hide fade" id="iconModal" style="display: none; height: 600px;overflow:auto;" tabindex="-1" role="dialog" aria-labelledby="iconModalLabel">
			<div class="widget no-margin" role="document">
				<div class="widget-header">
					<div class="title">Icons</div>
					<div class="tools">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					</div>
				</div>
				<div class="widget-body" style="overflow-y: scroll;">
					<ul id="icons" class="icomoon-icons-container">
                      <li v-for="icon in iconArray" v-on:click="iconSelect($event)">
                        <span class="fs1" aria-hidden="true" :data-icon="icon"></span>
                      </li>
                    </ul>
                    <div class="clearfix">
                      &nbsp;
                    </div>
				</div>
			</div>
		</div>
	</div>
	</div>
</template>

<script>
	export default{
		name: "menuPage",
		data : function(){
			return{
				menuList : [],
				childList : [],
				parent_search : "",
				child_search : "",
				parentId : "", //用于获取当前选择的一级菜单id
				childId : "",  //用户获取当前选择的二级菜单id
				menuName : "",
				icon : "",
				menuObject : {
					menuId : "",
					menuCode : "",
					menuName : "",
					menuPath : "",
					menuSort : "",
					menuType : "",
					icon : "",
					parentMenu : "",
					isParent : 0,
					key : ""
				},
				menu : {
					menuId : "",
					menuCode : "",
					menuName : "",
					menuPath : "",
					menuSort : "",
					menuType : "",
					icon : "",
					parentMenu : "",
					isParent : 0
				},
				iconArray : ["\ue000","\ue001","\ue002","\ue003","\ue004","\ue005","\ue006","\ue007","\ue008","\ue009","\ue00a","\ue00b","\ue00c","\ue00d","\ue00e","\ue00f","\ue010","\ue011","\ue012","\ue013","\ue014","\ue015","\ue016","\ue017","\ue018","\ue019","\ue01a","\ue01b","\ue01c","\ue01d","\ue01e","\ue01f","\ue020","\ue021","\ue022","\ue023","\ue024","\ue025","\ue026","\ue027","\ue028","\ue029","\ue02a","\ue02b","\ue02c","\ue02d","\ue02e","\ue02f","\ue030","\ue031","\ue032","\ue033","\ue034","\ue035","\ue036","\ue037","\ue038","\ue039","\ue03a","\ue03b","\ue03c","\ue03d","\ue03e","\ue03f","\ue040","\ue041","\ue042","\ue043","\ue044","\ue045","\ue046","\ue047","\ue048","\ue049","\ue04a","\ue04b","\ue04c","\ue04d","\ue053","\ue054","\ue052","\ue051","\ue050","\ue14e","\ue14f","\ue054","\ue055","\ue056","\ue057","\ue058","\ue059","\ue05a","\ue05b","\ue05c","\ue05d","\ue05e","\ue05f","\ue060","\ue060","\ue061","\ue062","\ue063","\ue064","\ue065","\ue066","\ue067","\ue068","\ue069","\ue06a","\ue06b","\ue06c","\ue06d","\ue06e","\ue06f","\ue070","\ue070","\ue071","\ue072","\ue073","\ue074","\ue075","\ue076","\ue077","\ue078","\ue079","\ue07a","\ue07b","\ue07c","\ue07d","\ue07e","\ue07f","\ue080","\ue080","\ue081","\ue082","\ue083","\ue084","\ue085","\ue086","\ue087","\ue088","\ue089","\ue08a","\ue08b","\ue08c","\ue08d","\ue08e","\ue08f","\ue090","\ue090","\ue091","\ue092","\ue093","\ue094","\ue095","\ue096","\ue097","\ue098","\ue099","\ue09a","\ue09b","\ue09c","\ue09d","\ue09e","\ue09f","\ue0a0","\ue0a1","\ue0a2","\ue0a3","\ue0a4","\ue0a6","\ue0a7","\ue0a8","\ue0a9","\ue0aa","\ue0ab","\ue0ac","\ue0ad","\ue0ae","\ue0af","\ue0b0","\ue0b1","\ue0b2","\ue0b3","\ue0b4","\ue0b5","\ue0b6","\ue0b7","\ue0b8","\ue0b9","\ue0ba","\ue0bb","\ue0bc","\ue0bd","\ue0be","\ue0bf","\ue0c0","\ue0c1","\ue0c2","\ue0c3","\ue0c4","\ue0c5","\ue0c6","\ue0c7","\ue0c8","\ue0c9","\ue0ca","\ue0cd","\ue0cc","\ue0cd","\ue0ce","\ue0ce","\ue0cf","\ue0d0","\ue0d1","\ue0d2","\ue0d3","\ue0d4","\ue0d5","\ue0d6","\ue0d7","\ue0d8","\ue0d9","\ue0da","\ue0db","\ue0dc","\ue0dd","\ue0de","\ue0df","\ue0e0","\ue0e1","\ue0e2","\ue0e3","\ue0e4","\ue0e5","\ue0e6","\ue0e7","\ue0e8","\ue0e9","\ue0ea","\ue0eb","\ue0ec","\ue0ed","\ue0ee","\ue0ef","\ue0f0","\ue0f1","\ue0f2","\ue0f3","\ue0f4","\ue0f5","\ue0f6","\ue0f7","\ue0f8","\ue0f9","\ue0fa","\ue0fb","\ue0fc","\ue0fd","\ue0fe","\ue0ff","\ue100","\ue101","\ue102","\ue103","\ue104","\ue105","\ue106","\ue107","\ue108","\ue109","\ue10a","\ue10b","\ue10c","\ue10d","\ue10e","\ue10f","\ue110","\ue111","\ue112","\ue113","\ue114","\ue115","\ue116","\ue117","\ue118","\ue119","\ue11a","\ue11b","\ue11c","\ue11d","\ue11e","\ue11f","\ue120","\ue121","\ue122","\ue123","\ue124","\ue125","\ue126","\ue127","\ue128","\ue129","\ue12a","\ue12b","\ue12c","\ue12d","\ue12e","\ue12f","\ue130","\ue131","\ue132","\ue133","\ue134","\ue135","\ue136","\ue137","\ue138","\ue139","\ue13a","\ue13b","\ue13c","\ue13d","\ue13e","\ue13f","\ue140","\ue141","\ue142","\ue143","\ue144","\ue145","\ue146","\ue147","\ue148","\ue149","\ue14a","\ue14b","\ue14c","\ue14d","\ue14e","\ue14f","\ue150","\ue151","\ue152","\ue153","\ue154","\ue155","\ue156","\ue157","\ue158","\ue159","\ue15a","\ue15b","\ue15c","\ue15d","\ue15e","\ue15f","\ue160","\ue161","\ue162","\ue163","\ue164","\ue165","\ue166","\ue167","\ue168","\ue169","\ue16a","\ue16b","\ue16c","\ue16d","\ue16e","\ue16f","\ue170","\ue171","\ue172","\ue173","\ue174","\ue175","\ue176","\ue177","\ue178","\ue179","\ue17a","\ue17b","\ue17c","\ue17d","\ue17e","\ue17f","\ue180","\ue181","\ue182","\ue183","\ue184","\ue185","\ue186","\ue187","\ue188","\ue189","\ue18a","\ue18b","\ue18c","\ue18d","\ue18e","\ue18f","\ue190","\ue191","\ue192","\ue193","\ue194","\ue195","\ue196","\ue197","\ue198","\ue199","\ue19a","\ue19b","\ue19c","\ue19d","\ue19e","\ue19f","\ue1a0","\ue1a1","\ue1a2","\ue1a3","\ue1a4","\ue1a5","\ue1a6","\ue1a7","\ue1a8","\ue1a9","\ue1aa","\ue1ab","\ue1ac","\ue1ad","\ue1ae","\ue1af","\ue1b0","\ue1b1","\ue1b2","\ue1b3","\ue1b4","\ue1b5","\ue1b6","\ue1b7","\ue1b8","\ue1b9","\ue1ba","\ue1bb","\ue1bc","\ue1bd","\ue1be","\ue1bf","\ue1c0","\ue1c1","\ue1c2","\ue1c3","\ue1c4","\ue1c5","\ue1c6","\ue1c7","\ue1c8","\ue1c9","\ue1ca","\ue1cb","\ue1cc","\ue1cd","\ue1ce","\ue1cf","\ue1d0","\ue1d1","\ue1d2","\ue1d3","\ue1d4","\ue1d5","\ue1d6","\ue1d7","\ue1d8","\ue1d9","\ue1da","\ue1db","\ue1dc","\ue1ad"]
			}
		},
		created : function(){
			let _this = this;
			this.axios({
				method : 'get',
				url : '/side/menu/search',
				params : {key : _this.menuObject.key,
						isParent : _this.menuObject.isParent
				}
			}).then(function(response){
				if(response.data.success){
					if(response.data.data != null){
						_this.$data.menuList = JSON.parse(response.data.data);
					}
				}
			}).catch(response => {
				this.$alertify.error("查询发送异常，请联系管理员");
			});
		},
		methods : {
			parentSelect : function(){
				let _this = this;
				this.menuObject.key = _this.parent_search;
				this.menuObject.isParent = 0;
				this.axios({
					method : 'get',
					url : '/side/menu/search',
					params : {key : this.menuObject.key,
							isParent : this.menuObject.isParent
					}
				}).then(function(response){
					if(response.data.success){
						if(response.data.data != null){
							_this.$data.menuList = JSON.parse(response.data.data);
						}
					}
				}).catch(response => {
					this.$alertify.error("查询发送异常，请联系管理员");
				});

			},
			childSelect : function(){
				let _this = this;
				this.menuObject.key = _this.child_search;
				this.menuObject.isParent = 1;
				this.axios({
					method : 'get',
					url : '/side/menu/search',
					params : {key : this.menuObject.key,
							  isParent : this.menuObject.isParent,
							  parentId : _this.parentId
					}
				}).then(function(response){
					if(response.data.success){
						if(response.data.data != null){
							_this.$data.childList = JSON.parse(response.data.data);
						}
					}
				}).catch(response => {
					this.$alertify.error("查询发送异常，请联系管理员");
				});
			},
			select : function(menuId, event){//父级菜单选择触发事件
				let _this = this;
				_this.parentId = menuId;
				_this.menuName = event.currentTarget.lastChild.innerText;
				this.axios({
					method : 'get',
					url : '/side/menu/search',
					params : {parentId : menuId}
				}).then(function(response){
					if(response.data.success){
						if(response.data.data != null){
							_this.$data.childList = JSON.parse(response.data.data);
						}
					}
				}).catch(response => {
					this.$alertify.error("查询发送异常，请联系管理员");
				});
			},
			childChoose : function(menuId){
				this.childId = menuId;
			},
			addMenu : function(){
				let _this = this;
				_this.menuObject = {};
				$("#first_control").hide();
				$("#last_control").show();
				$("#parentModal").modal('show');
				_this.menuObject.isParent = 0;
				_this.childId = '';
				_this.menuObject.parentMenu = '';
				_this.parentId = '';
			},
			editMenu : function(){
				let _this = this;
				if(_this.parentId == undefined || _this.parentId == ''){
					this.$alertify.alert("请选择需要编辑的记录");
					return;
				} else {
					// update init form data
					for(var i = 0; i < _this.menuList.length; i ++){
						var menu = _this.menuList[i];
						if(menu.menuId == _this.parentId){
							_this.menuObject = copy(menu);
							break;
						}
					}

					$("#first_control").hide();
					$("#last_control").show();
					$("#parentModal").modal('show');
					$("#exemple").attr("data-icon", _this.menuObject.icon)
					_this.menuObject.isParent = 0;
					_this.childId = '';
					_this.menuObject.parentMenu = _this.parentId;
				}
			},
			delMenu : function(){
				let _this = this;
				if(_this.parentId == undefined || _this.parentId == ''){
					this.$alertify.alert("请选择需要删除的记录");
					return;
				} else {
					_this.menuObject.menuId = _this.parentId;
					_this.menuObject.isParent = 0;
					this.$alertify.confirmWithTitle(
						'提示!',
						'你确定删除该菜单吗？',
						() => {
							$(".alertify").css("display", "none");
							//发送请求
							this.axios({
								method : 'delete',
								url : '/side/menu/delMenuService',
								params : _this.menuObject
							}).then(response => {
								if(response.data.success){
									if(response.data.msg != null){
										_this.$alertify.success(response.data.msg);
										_this.parentSelect();
									}
								}
							}).catch(response => {
								this.$alertify.error("查询发送异常，请联系管理员");
							});
						},
						() => {
							$(".alertify").css("display", "none");
						}
					);
				}
			},
			addChild : function(){
				let _this = this;
				if(_this.parentId != null && _this.parentId != undefined){
					_this.menuObject = {};
					$("#first_control").show();
					_this.parentMenu = _this.parentId;
					$("#last_control").hide();
					$("#parentModal").modal('show');
					$("#parentMenu").val(_this.menuName)
					_this.menuObject.isParent = 1;
					_this.childId = '';
				} else {
					this.$alertify.alert("未选定需要添加二级菜单的一级菜单");
					return;
				}
			},
			editChild : function(){
				let _this = this;
				if(_this.childId == undefined || _this.childId == ''){
					this.$alertify.alert("请选择需要编辑的记录");
					return;
				} else {

					for(var i = 0; i < _this.childList.length; i ++){
						var menu = _this.childList[i];
						if(menu.menuId == _this.childId){
							_this.menuObject = copy(menu);
							break;
						}
					}

					$("#parentModal").modal('show');
					$("#first_control").show();
					_this.parentMenu = _this.parentId;
					$("#last_control").hide();
					$("#parentMenu").val(_this.menuName);
					_this.menuObject.isParent = 1;
				}
			},
			delChild: function(){
				let _this = this;
				if(_this.childId == undefined || _this.childId == ''){
					this.$alertify.alert("请选择需要删除的记录");
					return;
				} else {
					_this.menuObject.menuId = _this.childId;
					_this.menuObject.isParent = 1;
					this.$alertify.confirmWithTitle(
						'提示!',
						'你确定删除该菜单吗？',
						() => {
							$(".alertify").css("display", "none");
							//发送请求
							this.axios({
								method : 'delete',
								url : '/side/menu/delMenuService',
								params : _this.menuObject
							}).then(response => {
								if(response.data.success){
									if(response.data.msg != null){
										_this.$alertify.success(response.data.msg);
										_this.childSelect();
									}
								}
							}).catch(response => {
								this.$alertify.error("查询发送异常，请联系管理员");
							});
						},
						() => {
							$(".alertify").css("display", "none");
						}
					);
				}
			},
			menuSave: function(){
				let _this = this;
				if(_this.parentId != undefined && _this.parentId != '' && (_this.childId != undefined && _this.childId != '')){
					this.menuObject.parentMenu = _this.parentId;
					this.menuObject.menuId = _this.childId;
				} else if(_this.parentId != undefined && _this.parentId != '' && (_this.childId == undefined || _this.childId == '')){
					this.menuObject.parentMenu = _this.parentId;
				}else {
					this.menuObject.menuId = _this.parentId;
				}
				//发送请求
				this.axios({
					method : 'put',
					url : '/side/menu/editMenuService',
					params : this.menuObject
				}).then(function(response){
					if(response.data.success){
						if(response.data.data != null){
							if(_this.parentId != undefined && _this.parentId != '' && (_this.childId != undefined && _this.childId != '')){//二级菜单修改
								_this.childList = new Array();
								_this.childList = eval(response.data.data);
								$("#parentModal").modal('hide');
							} else if(_this.parentId != undefined && _this.parentId != '' && (_this.childId == undefined || _this.childId == '')){//二级菜单新增
								_this.childList = new Array();
								_this.childList = eval(response.data.data);
								$("#parentModal").modal('hide');
							}else { //父级菜单新增修改
								_this.menuList = new Array();
								_this.menuList = eval(response.data.data);
								$("#parentModal").modal('hide');
							}
						}
					}
				}).catch(response => {
					this.$alertify.error("查询发送异常，请联系管理员");
				});

			},
			//图标选择事件
			iconSelect: function(e){
				let _this = this;
				_this.$data.icon = e.target.dataset.icon;
				$("#iconModal").modal("hide");
			}
		}
	}
	//对象复制
	function copy(obj){
		var newobj = {};
		for ( var attr in obj) {
			newobj[attr] = obj[attr];
		}
		return newobj;
	}
</script>

<style>
</style>
