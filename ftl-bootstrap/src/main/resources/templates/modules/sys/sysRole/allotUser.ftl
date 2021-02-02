<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>分配用户</title>
    <#include "/common/base.ftl"/>
    <style type="text/css">
    </style>
</head>

<body class="gray-bg">
<div class="wrapper wrapper-content animated fadeInRight" style="position: relative">
    <input type="text" id="roleId" value="" hidden/>
    <!-- Panel Other -->
    <div class="ibox float-e-margins" style="position:absolute;left:10px;top:8px;width:45%;height: 420px;">
        <div class="ibox-title">
            <h5>未分配用户</h5>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <!-- Example Events -->
                    <div class="example-wrap">
                        <div class="example">
                            <div class="btn-group hidden-xs" id="toolbar" role="group">
                                <div class="input-group">
                                    <form id="isNotOwner_search">
                                        <input type="text" name="keyword" class="form-control" style="width:60%;" placeholder="请输入查询内容"/>
                                        <button  title="查询" type="button" onclick="searchListener(1)" class="btn btn-outline btn-white">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                        <button title="清空" type="button" onclick="clearSearchListener(1)" class="btn btn-outline btn-white">
                                            <i class="fa fa-eraser"></i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                            <div style="height: 300px;overflow-x:hidden;overflow-y: scroll;">
                                <table  id="isNotOwner_list_table"></table>
                            </div>
                        </div>
                    </div>
                    <!-- End Example Events -->
                </div>
            </div>
        </div>
    </div>
    <div class="ibox float-e-margins" style="position:absolute;right:10px;top:8px;width:45%;height: 420px;">
        <div class="ibox-title">
            <h5>已分配用户</h5>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <!-- Example Events -->
                    <div class="example-wrap">
                        <div class="example">
                            <div class="btn-group hidden-xs" id="toolbar" role="group">
                                <div class="input-group">
                                    <form id="isOwner_search">
                                        <input type="text" name="keyword" class="form-control" style="width:60%;" placeholder="请输入查询内容"/>
                                        <button  title="查询" type="button" onclick="searchListener(0)" class="btn btn-outline btn-white">
                                            <i class="glyphicon glyphicon-search"></i>
                                        </button>
                                        <button title="清空" type="button" onclick="clearSearchListener(0)" class="btn btn-outline btn-white">
                                            <i class="fa fa-eraser"></i>
                                        </button>
                                    </form>
                                </div>
                            </div>
                            <div style="height: 300px;overflow-x:hidden;overflow-y: scroll;">
                                <table  id="isOwner_list_table"></table>
                            </div>
                        </div>
                    </div>
                    <!-- End Example Events -->
                </div>
            </div>
        </div>
    </div>
    <!-- End Panel Other -->
</div>
<script type="text/javascript">
    //var $table;
    function allotUserById(userId,roleId,type) {
        $.ajax({
            type: "POST",
            dataType: "json",
            cache: false,
            url: "/sys/user/allotUserById",
            data:{
                userId:userId,
                roleId:roleId,
                type:type
            },
            success: function (result) {
                if (result.resultStat=="SUCCESS") {
                    if(type==0){
                        layer.msg('移除成功！', {
                            time: 2000 //20s后自动关闭
                        });
                    }else{
                        layer.msg('分配成功！', {
                            time: 2000 //20s后自动关闭
                        });
                    }
                    searchListener(0);
                    searchListener(1);
                } else {
                    layer.alert("操作失败");
                }
            },
            error: function (result) {
                layer.alert("操作失败，"+result.mess);
            }
        });
    }
    
    function searchListener(type) {
        var $search;
        var $tb;
        var roleId=$('#roleId').val();
        var url;
        if(type==1){
            $search=$('#isNotOwner_search');
            $tb=$('#isNotOwner_list_table');
            var keyword=$search.find("input[name='keyword']").val()==''?"null":$search.find("input[name='keyword']").val();
            url="/sys/user/allotUser/getIsNotOwner/"+roleId+"/"+keyword;
        }else{
            $search=$('#isOwner_search');
            $tb=$('#isOwner_list_table');
            var keyword=$search.find("input[name='keyword']").val()==''?"null":$search.find("input[name='keyword']").val();
            url="/sys/user/allotUser/getIsOwner/"+roleId+"/"+keyword;
        }

        parent.InitMainTable ($tb,roleId,url,type);
    }


    function clearSearchListener(type) {
        var $search;
        if(type==1){
            $search=$('#isNotOwner_search');
        }else{
            $search=$('#isOwner_search');
        }

        $search.find("input[name='keyword']").val('');
        searchListener(type);
    }
</script>

</body>

</html>
