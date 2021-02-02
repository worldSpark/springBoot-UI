<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>角色管理</title>
    <#include "/common/base.ftl"/>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight">
    <!-- Panel Other -->
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5><i class="fa fa-list"></i> 角色列表</h5>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <!-- Example Events -->
                    <div class="example-wrap">
                        <div class="example">
                            <div class="btn-group hidden-xs" id="toolbar" role="group">
                                <a type="button" class="btn btn-outline btn-white" title="新增角色" onclick="addForm(null,'新增角色')">
                                    <i class="glyphicon glyphicon-plus" aria-hidden="true"></i>
                                </a>
                                <div class="input-group">
                                    <form id="user_search">
                                        <input type="text" name="keyword" class="form-control" style="width:60%;margin-left: 4px;" placeholder="请输入查询内容"  onkeydown="onKeyDown(event)"/>
                                        <button  title="查询" type="button" onclick="searchListener()" class="btn btn-outline btn-white">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                        <button title="清空" type="button" onclick="clearSearchListener()" class="btn btn-outline btn-white">
                                            <i class="fa fa-eraser"></i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                            <table  id="role_list_table"
                                    data-toggle="table"
                                    data-method="post"
                                    data-content-type="application/x-www-form-urlencoded; charset=UTF-8"
                                    data-click-to-select=true
                                    data-pagination-detail-h-align="left"
                                    data-pagination-show-page-go="true"
                                    data-page-size="8"
                                    data-page-list="[8,20,30,50,100]"
                                    data-pagination=true
                                    data-side-pagination="server"
                                    data-pagination-detail-h-align="left"
                                    data-query-params="searchParam"
                                    data-url="/sys/role"
                                    data-striped="true"
                            >
                                <thead>
                                <tr>
                                    <th data-field="id" data-visible="false" data-align="left" data-width="10">ID</th>
                                    <th data-field="roleName" data-align="center" data-width="100">角色名称</th>
                                    <th data-field="remark" data-align="center" data-width="120">备注</th>
                                    <th data-field="status" data-formatter="statusFormatter" data-align="center" data-width=10>状态</th>
                                    <th data-field="_operator" data-formatter="operateFormatter" data-align="left" data-width="140">操作</th>
                                </tr>
                                </thead>
                            </table>
                        </div>
                    </div>
                    <!-- End Example Events -->
                </div>
            </div>
        </div>
    </div>
    <!-- End Panel Other -->
</div>
<script type="text/javascript" src="/static/modules/sys/role/list.js"></script>

</body>

</html>
