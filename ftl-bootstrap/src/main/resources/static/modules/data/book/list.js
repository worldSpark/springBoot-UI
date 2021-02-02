/**
 * 初始化事件
 */
$(function () {
        InitMainTable();
})

var $table;
//初始化bootstrap-table的内容
function InitMainTable () {
    //记录页面bootstrap-table全局变量$table，方便应用
    $table = $('#book_list_table').bootstrapTable('destroy').bootstrapTable({
        url: '/api/book/pages',                      //请求后台的URL（*）
        method: 'GET',                      //请求方式（*）
        toolbar: '#toolbar',              //工具按钮用哪个容器
        striped: true,                      //是否显示行间隔色
        cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
        pagination: true,                   //是否显示分页（*）
        sortable: true,                     //是否启用排序
        sortOrder: "asc",                   //排序方式
        sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
        pageNumber: 1,                      //初始化加载第一页，默认第一页,并记录
        pageSize: 5,                     //每页的记录行数（*）
        pageList: [5,10,20,50],        //可供选择的每页的行数（*）
        search: false,                      //是否显示表格搜索
        strictSearch: true,
        showColumns: true,                  //是否显示所有的列（选择显示的列）
        showRefresh: true,                  //是否显示刷新按钮
        minimumCountColumns: 2,             //最少允许的列数
        clickToSelect: true,                //是否启用点击选中行
        //height: 500,                      //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
        uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
        showToggle: true,                   //是否显示详细视图和列表视图的切换按钮
        cardView: false,                    //是否显示详细视图
        detailView: false,                  //是否显示父子表
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
        columns: [{
            field: 'img',
            title: '图书',
            align: 'center',
            valign: 'middle',
            minWidth:100,
            formatter:imgFormatter
        },{
            field: 'bookName',
            title: '图书名称',
            align: 'center',
            valign: 'center',
            minWidth:160
        }, {
            field: 'bookCode',
            title: '图书编号',
            align: 'center',
            valign: 'middle',
            minWidth:100
        }, {
            field: 'author',
            title: '作者',
            align: 'center',
            valign: 'middle',
            minWidth:160
        }, {
            field: 'publish',
            title: '出版社',
            align: 'center',
            valign: 'middle',
            minWidth:100
        }, {
            field: 'state',
            title: '状态',
            align: 'center',
            valign: 'middle',
            minWidth:100
        }, {
            field:'id',
            title: '操作',
            minWidth: 240,
            align: 'center',
            valign: 'middle',
            formatter:actionFormatter
        }, ]
    });
}

//操作栏的格式化
function actionFormatter(value, row, index) {
    var id = row.id;
    var result = "";
    if(row.state=="库存中"){
        result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"borrow('" + id + "')\" title='借阅'><span class='glyphicon glyphicon-record'></span> 借阅</a>";
    }else {
        result += "<a disabled='' style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' title='借阅'><span class='glyphicon glyphicon-registration-mark'></span> 借阅</a>";
    }
    result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"showBookDetail('" + id + "','预览图书')\" title='预览图书'><span class='fa fa-search'></span> 预览图书</a>";
    return result;
}

function imgFormatter(value,row,index) {
    if(value){
        return '<img src="'+value+'"  alt="图片"  height="60" width="60"  />';
    }else{
        return '<img src="/static/img/1.jpg"  alt="图片" height="60" width="60" />';
    }
}

function reloadTable() {
    $table.bootstrapTable('refresh');
}



function showBookDetail(id) {
    bookFormLayer= layer.open({
        type: 2,
        title: "图书预览",
        area: ['1200px', '700px'],
        maxmin: true,
        shade: 0.8,
        closeBtn: 1,
        shadeClose: true,
        anim:3,
        content: '/api/book/form',
        success: function(layero, index){
            var body=layer.getChildFrame('body',index);
            var $form=$(body).find("#book_form");
            $.ajax({
                type: "POST",
                dataType: "json",
                cache: false,
                async:false,
                url: "/api/book/showBookDetail/"+id,
                success: function (res) {
                    var result=res.data;
                    $form.find("input").attr("disabled",true);
                    if(result){
                        for (i in result){
                            if(i !='publishTime'&& i!='img'){
                                $form.find("input[name=\'"+i+"\']").val(result[i]);
                                $form.find("textarea[name=\'"+i+"\']").val(result[i]);
                            }else if(i=='publishTime'){
                                $form.find("input[name=\'"+i+"\']").val(datetimeFormat(result[i]));
                            }else if(i=='img' && result[i]){
                                $form.find("#img").attr("src",result[i]);
                            }
                        }
                    }
                },
                error: function (result) {
                    layer.alert("数据加载失败，"+result.mess);
                }
            });

        }
    });

    layer.full(bookFormLayer);
}

function borrow(id) {
    $.ajax({
        url: "/api/book/borrow/"+id,
        type: "post",
        success: function (result) {
            if (result.resultStat=="SUCCESS") {
                layer.msg("借阅成功！", {
                    time: 2000 //20s后自动关闭
                });
                $('#book_list_table').bootstrapTable('refresh');
            } else {
                layer.alert(result.mess);
            }
        }
    });
}