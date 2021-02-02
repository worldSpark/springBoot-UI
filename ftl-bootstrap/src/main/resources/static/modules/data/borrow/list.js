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
    $table = $('#borrow_list_table').bootstrapTable('destroy').bootstrapTable({
        url: '/api/borrow/pages',                      //请求后台的URL（*）
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
        columns: [{
            field: 'book.img',
            title: '图书',
            align: 'center',
            valign: 'middle',
            minWidth:100,
            formatter:function (value,row,index) {
                if(value){
                    return '<img src="'+value+'"  alt="图片"  height="60" width="60"  />';
                }else{
                    return '<img src="/static/img/1.jpg"  alt="图片" height="60" width="60" />';
                }
            }
        },{
            field: 'book.bookName',
            title: '图书名称',
            align: 'center',
            valign: 'center',
            minWidth:160
        }, {
            field: 'book.bookCode',
            title: '图书编号',
            align: 'center',
            valign: 'middle',
            minWidth:100
        }, {
            field: 'book.author',
            title: '作者',
            align: 'center',
            valign: 'middle',
            minWidth:160
        }, {
            field: 'book.publish',
            title: '出版社',
            align: 'center',
            valign: 'middle',
            minWidth:100
        } ,{
            field: 'borrowTime',
            title: '借书日期',
            align: 'center',
            valign: 'middle',
            minWidth:100,
            formatter:datetimeFormat
        }, {
            field: 'departReturnTime',
            title: '到期日',
            align: 'center',
            valign: 'middle',
            minWidth:100,
            formatter:datetimeFormat
        }, {
            field: 'book.state',
            title: '状态',
            align: 'center',
            valign: 'middle',
            minWidth:100,
            formatter:function (value, row, index) {
                var d=new Date().setDate(new Date().getDate()+3);
                if(new Date(row.departReturnTime)<new Date()){
                    return '<span class="text-danger" title="已逾期，请及时归还，逾期计费3元/天。">已逾期</span>';
                }else if(new Date(row.departReturnTime)<d){
                    return '<span class="text-warning" title="离到日期不到3天，请及时归还书籍">还书预警</span>';
                }
                return value;
            }
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
    var book=row.book;
    var d=new Date().setDate(new Date().getDate()+3);
    var result = "";
    if(new Date(row.departReturnTime)<new Date()){
        result += "<a disabled='' style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' title='续借'><span class='glyphicon glyphicon-registration-mark'></span> 续借</a>";
    } else if(new Date(row.departReturnTime)<d){
        result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"continueBorrow('" + id + "','"+row.bookId+"')\" title='续借'><span class='glyphicon glyphicon-record'></span> 续借</a>";
    }else {
        result += "<a disabled='' style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' title='续借'><span class='glyphicon glyphicon-registration-mark'></span> 续借</a>";
    }
    result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"returnBook('" + id + "','"+row.bookId+"')\" title='归还'><span class='fa fa-reply'></span> 归还</a>";
    result += "<a style='margin: 3px;' href='javascript:;' class='btn btn-white btn-sm' onclick=\"showBorrowHistoryList('" + book.id + "','查看借阅记录')\" title='查看借阅记录'><span class='fa fa-search'></span> 查看借阅记录</a>";
    return result;
}



function reloadTable() {
    $table.bootstrapTable('refresh');
}



function showBorrowHistoryList(bookId) {
    bookFormLayer= layer.open({
        type: 2,
        title: "借阅记录预览",
        area: ['1200px', '700px'],
        maxmin: true,
        shade: 0.8,
        closeBtn: 1,
        shadeClose: true,
        anim:3,
        content: '/api/borrow/form',
        success: function(layero, index){
            var body=layer.getChildFrame('body',index);
            var $form=$(body).find("#vertical-timeline");
            $.ajax({
                type: "POST",
                dataType: "json",
                cache: false,
                async:false,
                url: "/api/borrow/showBorrowHistoryList/"+bookId,
                success: function (res) {
                    var result=res.data;
                    if(result && result.length>0){
                        for (var i=0;i<result.length;i++){
                            var borrow=result[i];
                            parseBorrowHistory(borrow,$form);
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

function parseBorrowHistory(borrow,$form) {
    var div1='<div class="vertical-timeline-block">\n' +
        '         <div class="vertical-timeline-icon navy-bg">\n' +
        '              <i class="glyphicon glyphicon-registration-mark"></i>\n' +
        '          </div>\n' +
        '          <div class="vertical-timeline-content">\n' +
        '              <h2 style="height: 20px;"><span style="padding-top: 9px;background-color: #80808075" class="vertical-timeline-icon">借</span><span style="margin-left: 25px;">《'+borrow.bookName+'》</span></h2>\n' +
        '              <p>'+borrow.operator+'</p>\n' +
        '              <span class="vertical-date">\n' +
        '                 <small>'+datetimeFormat(borrow.borrowTime)+'</small>\n' +
        '              </span>\n' +
        '          </div>\n' +
        '      </div>\n';
    $form.append(div1);
    if(borrow.returnTime){
        var div2=' <div class="vertical-timeline-block">\n' +
            '           <div class="vertical-timeline-icon lazur-bg">\n' +
            '                <i class="fa fa-reply"></i>\n' +
            '           </div>\n' +
            '           <div class="vertical-timeline-content">\n' +
            '                <h2 style="height: 20px;"><span style="padding-top: 9px;" class="vertical-timeline-icon yellow-bg">还</span><span style="margin-left: 25px;">《'+borrow.bookName+'》</span></h2>\n' +
            '                <p>'+borrow.operator+'</p>\n' +
            '              <span class="vertical-date">\n' +
            '                <small>'+datetimeFormat(borrow.returnTime)+'</small>' +
            '              </span>\n' +
            '          </div>\n' +
            '      </div>';
        $form.append(div2);
    }
}


function showBorrowHistoryListByUser() {
    bookFormLayer= layer.open({
        type: 2,
        title: "全部借阅记录",
        area: ['1200px', '700px'],
        maxmin: true,
        shade: 0.8,
        closeBtn: 1,
        shadeClose: true,
        anim:3,
        content: '/api/borrow/form',
        success: function(layero, index){
            var body=layer.getChildFrame('body',index);
            var $form=$(body).find("#vertical-timeline");
            $.ajax({
                type: "POST",
                dataType: "json",
                cache: false,
                async:false,
                url: "/api/borrow/showBorrowHistoryListByUser",
                success: function (res) {
                    var result=res.data;
                    if(result && result.length>0){
                        for (var i=0;i<result.length;i++){
                            var borrow=result[i];
                            parseBorrowHistory(borrow,$form);
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

/**
 * 续借
 * @param id
 * @param bookId
 */
function continueBorrow(id,bookId) {
    $.ajax({
        url: "/api/borrow/continueBorrow",
        data:{id:id,bookId:bookId},
        type: "post",
        success: function (result) {
            if (result.resultStat=="SUCCESS") {
                layer.msg("续借成功！", {
                    time: 2000 //20s后自动关闭
                });
                $('#borrow_list_table').bootstrapTable('refresh');
            } else {
                layer.alert(result.mess);
            }
        }
    });
}

/**
 * 归还
 * @param id
 * @param bookId
 */
function returnBook(id,bookId) {
    $.ajax({
        url: "/api/borrow/returnBook",
        data:{id:id,bookId:bookId},
        type: "post",
        success: function (result) {
            if (result.resultStat=="SUCCESS") {
                layer.msg("还书成功！", {
                    time: 2000 //20s后自动关闭
                });
                $('#borrow_list_table').bootstrapTable('refresh');
            } else {
                layer.alert(result.mess);
            }
        }
    });
}