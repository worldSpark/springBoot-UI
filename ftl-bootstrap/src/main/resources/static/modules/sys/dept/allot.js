var tree=$ ("#using_json" );
var currentDeptId;
$ (document ).ready(function ()  {
        onloadTree();
    }
);

function onloadTree() {
    $.ajax({
        type: "POST",
        dataType: "json",
        async: false,
        url: "/sys/dept/treeList",
        success: function (result) {debugger
            if (result) {
                tree.jstree(result);
            }
            var len=result.core.data;
            if(len.length>0){
                var row=len[0];
                $('#orgName').text(row.text);
                InitMainTable_1 (row.id);
                InitMainTable_2 (row.id);
            }
        },
        error: function () {
            layer.alert("数据加载失败！");
        }
    });
}

$('#using_json').bind("activate_node.jstree", function (obj, e) {
    // 处理代码
    // 获取当前节点
    var currentNode = e.node;
    currentDeptId=currentNode.id;
    $('#orgName').text(currentNode.text);
    InitMainTable_1 ();
    InitMainTable_2 ();
});


function InitMainTable_1 (id) {
    if(id){
        currentDeptId=id;
    }
    console.log(currentDeptId);
    //记录页面bootstrap-table全局变量$table，方便应用
    $('#unAllot_list_table').bootstrapTable('destroy').bootstrapTable({
        url: '/sys/dept/isUnAllotUserList?deptId='+currentDeptId,                      //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        toolbar: 'false',              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 5,                     //每页的记录行数（*）
        pageList: [5,10,20],        //可供选择的每页的行数（*）
        search: false,                      //是否显示表格搜索
        strictSearch: true,
        showColumns: false,                  //是否显示所有的列（选择显示的列）
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                  //是否显示父子表
        showPaginationSwitch:false,
        //得到查询的参数
        queryParams : function (params) {
            //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
            var temp = {
                rows: params.limit,                         //页面大小
                page: (params.offset / params.limit) + 1,   //页码
                sort: params.sort,      //排序列名
                sortOrder: params.order, //排位命令（desc，asc）
                keyword:$('input[name="keyword"]').val()
            };
            return temp;
        },
        formatRecordsPerPage: function (pageNumber) {
            return '';
        },
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            //$('#total1').text();
            return  '共'+totalRows +'条数据';
        },
        columns: [{
            field: 'username',
            title: '用户名',
            sortable: true
        }, {
            field:'id',
            title: '操作',
            width: 180,
            align: 'center',
            valign: 'middle',
            formatter:function (value,row,index) {
                return "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"allotUser('" + row.id + "',0)\" title='分配'><span class='fa fa-arrow-left'></span></a>";
            }
        }, ]
    });
}

function InitMainTable_2 (id) {
    if(id){
        currentDeptId=id;
    }
    $('#masterName').text('暂无部长');
    $('#sex').attr("class","fa fa-user");
    //记录页面bootstrap-table全局变量$table，方便应用
    $('#allot_list_table').bootstrapTable('destroy').bootstrapTable({
        url: '/sys/dept/isAllotUserList?deptId='+currentDeptId,                      //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        toolbar: 'false',              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 5,                     //每页的记录行数（*）
        pageList: [5,10],        //可供选择的每页的行数（*）
        search: false,                      //是否显示表格搜索
        strictSearch: true,
        showColumns: false,                  //是否显示所有的列（选择显示的列）
        showRefresh: false,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
        showToggle: false,                   //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                  //是否显示父子表
        showPaginationSwitch:false,
        //得到查询的参数
        queryParams : function (params) {
            //这里的键的名字和控制器的变量名必须一致，这边改动，控制器也需要改成一样的
            var temp = {
                rows: params.limit,                         //页面大小
                page: (params.offset / params.limit) + 1,   //页码
                sort: params.sort,      //排序列名
                sortOrder: params.order, //排位命令（desc，asc）
                keyword:$('input[name="keyword"]').val()
            };
            return temp;
        },
        formatRecordsPerPage: function (pageNumber) {
            return '';
        },
        formatShowingRows: function (pageFrom, pageTo, totalRows) {
            $('#totalPer').html('<b style="font-size: 40px;color: #00abff;margin-top: 2px;margin-right: 4px;">'+totalRows +'</b>个组员');
            return '';
        },
        columns: [{
            field: 'username',
            title: '用户名',
            sortable: true
        }, {
            field:'id',
            title: '操作',
            width: 180,
            align: 'center',
            valign: 'middle',
            formatter:function (value,row,index) {
                var text="<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"setMasterUserByDept('" + row.id + "',1)\" title='设置管理员'><span class='fa fa-user-md'></span></a>";

                if(row.ismaster==0){
                    $('#masterName').text(row.username);
                    if(row.sex==1){
                        $('#sex').attr("class","fa fa-male");
                    }else{
                        $('#sex').attr("class","fa fa-female");
                    }
                    text="<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"setMasterUserByDept('" + row.id + "',0)\" title='取消管理员'><span style='color: #0a6aa1' class='fa fa-street-view'></span></a>";
                }
                return text+="<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"allotUser('" + row.id + "',1)\" title='移除'><span class='fa fa-arrow-right'></span></a>";


            }
        }, ]
    });
}

function allotUser(userId,type) {
    $.ajax({
        type: "POST",
        dataType: "json",
        async: false,
        data:{userId:userId,type:type,deptId:currentDeptId},
        url: "/sys/dept/allotUserByDept",
        success: function (result) {
            if (result.resultStat=="SUCCESS") {
                InitMainTable_1 ();
                InitMainTable_2 ();
            }
        },
        error: function () {
            layer.alert("操作失败！");
        }
    });
}

function setMasterUserByDept(userId,type) {
    $.ajax({
        type: "POST",
        dataType: "json",
        async: false,
        data:{userId:userId,deptId:currentDeptId,type:type},
        url: "/sys/dept/setMasterUserByDept",
        success: function (result) {
            if (result.resultStat=="SUCCESS") {
                InitMainTable_1 ();
                InitMainTable_2 ();
            }
        },
        error: function () {
            layer.alert("操作失败！");
        }
    });
}