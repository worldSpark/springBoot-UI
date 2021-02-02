var $table = $('#menu_list_table');
function initTable(data) {

    $table.bootstrapTable('destroy').bootstrapTable({
        data:data,
        idField: 'id',
        dataType:'jsonp',
        columns: [
            { field: 'index',  title:'序号',align: 'center',width:60, formatter: function (value, row, index) {
                    /*if (row.check == true) {
                        // console.log(row.serverName);
                        //设置选中
                        return {  checked: true };
                    }*/
                    return index+1;
                }
            },
            { field: 'menuName',  title: '名称',minWidth:160,halign:"center",formatter:function (value,rows,index) {
                    return '<a><i class="'+rows.icon+'"></i> '+value+'</a>'
                } },
            {field: 'url', title: '请求路径',minWidth:120, align: 'center'},
            {field: 'menuType', title: '菜单类型', width:100, align: 'center', formatter: 'typeFormatter' },
            { field: 'isShow',  title: '是否显示', width:100, align: 'center', formatter: 'statusFormatter'  },
            { field: 'permission',align: 'center',minWidth:120, title: '权限值'  },
            { field: 'operate', title: '操作',minWidth:160, align: 'center', events : operateEvents, formatter: 'operateFormatter' },
        ],

        // bootstrap-table-treegrid.js 插件配置 -- start

        //在哪一列展开树形
        treeShowField: 'menuName',
        //指定父id列
        parentIdField: 'parentId',

        onResetView: function(data) {
            //console.log('load');
            $table.treegrid({
                initialState: 'collapsed',// 所有节点都折叠
                // initialState: 'expanded',// 所有节点都展开，默认展开
                treeColumn: 1,
                // expanderExpandedClass: 'glyphicon glyphicon-minus',  //图标样式
                // expanderCollapsedClass: 'glyphicon glyphicon-plus',
                onChange: function() {
                    $table.bootstrapTable('resetWidth');
                }
            });

            //只展开树形的第一级节点
            $table.treegrid('getRootNodes').treegrid('expand');

        },
       /* onCheck:function(row){
            var datas = $table.bootstrapTable('getData');
            // 勾选子类
            selectChilds(datas,row,"id","parentId",true);

            // 勾选父类
            selectParentChecked(datas,row,"id","parentId")

            // 刷新数据
            $table.bootstrapTable('load', datas);
        },*/

       /* onUncheck:function(row){
            var datas = $table.bootstrapTable('getData');
            selectChilds(datas,row,"id","parentId",false);
            $table.bootstrapTable('load', datas);
        },*/
        // bootstrap-table-treetreegrid.js 插件配置 -- end
    });
}

$(function() {
    loadTable();
});
function loadTable() {
    $.ajax({
        type: "post",
        url: "/sys/menu",
        dataType: "json",
        success: function (data) {
            if (data && data.length > 0) {
                initTable(data);
            }
        }
    });
}
// 格式化按钮
function operateFormatter(value, row, index) {
    return [
        //'<button type="button" class="btn btn-white btn-sm MenuOfadd" style="margin:3px;"><i class="fa fa-plus" ></i>&nbsp;新增</button>',
        '<button type="button" class="btn btn-white btn-sm MenuOfedit" style="margin:3px;"><i class="fa fa-pencil-square-o" ></i>&nbsp;修改</button>',
        '<button type="button" class="btn btn-white btn-sm MenuOfdelete" style="margin:3px;"><i class="fa fa-trash-o" ></i>&nbsp;删除</button>'
    ].join('');

}
// 格式化类型
function typeFormatter(value, row, index) {
    if (value === 'menu') {  return '菜单';  }
    if (value === 'button') {  return '按钮'; }
    if (value === 'api') {  return '接口'; }
    return '-';
}
// 格式化状态
function statusFormatter(value, row, index) {
    if (value === 0) {
        return '<span class="label label-success">显示</span>';
    } else {
        return '<span class="label label-warning">隐藏</span>';
    }
}

//初始化操作按钮的方法
window.operateEvents = {
    'click .MenuOfadd': function (e, value, row, index) {
        add(row.id);
    },
    'click .MenuOfdelete': function (e, value, row, index) {
        del(row.id);
    },
    'click .MenuOfedit': function (e, value, row, index) {
        update(row.id);
    }
};

/**
 * 选中父项时，同时选中子项
 * @param datas 所有的数据
 * @param row 当前数据
 * @param id id 字段名
 * @param parentId 父id字段名
 */
/*function selectChilds(datas,row,id,parentId,checked) {
    for(var i in datas){
        if(datas[i][parentId] == row[id]){
            datas[i].check=checked;
            selectChilds(datas,datas[i],id,parentId,checked);
        };
    }
}

function selectParentChecked(datas,row,id,parentId){
    for(var i in datas){
        if(datas[i][id] == row[parentId]){
            datas[i].check=true;
            selectParentChecked(datas,datas[i],id,parentId);
        };
    }
}*/



function del(id) {
    if(!id){
        layer.alert("请选择删除数据");
        return;
    }
    layer.confirm('你确定删除该菜单？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                dataType: "json",
                cache: false,
                url: "/sys/menu/delete/"+id,
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        layer.msg('删除成功！', {
                            time: 2000 //20s后自动关闭
                        });
                        layer.close(index);
                        loadTable();
                        parent.onloadMenuTree();
                    } else {
                        layer.alert("删除失败,"+result.mess);
                    }
                },
                error: function (result) {
                    layer.alert("删除失败，"+result.mess);
                }
            });
        }
    });

}

function update(id) {
    //alert("update 方法 , id = " + id);
    addForm(id,"编辑菜单");
}

function addForm(id,title) {
    $('#myModal').modal("show");
    resetForm('#sysMenuForm');
    onloadMenu('#sysMenuForm select[name="parentId"]');
    if(title=="编辑菜单"){
        backForm(id,'#sysMenuForm');
    }
    if(title=="编辑菜单"){
        title='<i class="fa fa-pencil-square-o"></i> '+title;
    }else{
        title='<i class="glyphicon glyphicon-plus"></i> '+title;
    }
    $('#myModalLabel').html(title);
}

function onloadMenu(sel) {
    $(sel).empty();
    $.ajax({
        type: "post",
        url: "/sys/menu",
        async:false,
        dataType: "json",
        success: function (data) {
            $(sel).append('<option value="">--请选择--</option>');
            if (data && data.length > 0) {
                for (var i=0;i<data.length;i++){
                    var row=data[i];
                    $(sel).append('<option value="'+row.id+'">'+row.menuName+'</option>')
                }
            }
        }
    });
}

function backForm(id,form) {
    $.ajax({
        type: "post",
        url: "/sys/menu/selectMenuById/"+id,
        async:false,
        dataType: "json",
        success: function (data) {
            if (data) {
               for (i in data){
                   $(form).find('input[name="'+i+'"]').val(data[i]);
                   $(form).find('select[name="'+i+'"]').find('option[value="'+data[i]+'"]').prop("selected",true);
               }
            }
        }
    });
}

var menuFormValidator;
menuFormValidator = $("#sysMenuForm").validate({
    // 定义校验规则
    rules: {
        menuName: {
            required: true
        }
    },
    messages: {
        menuName: {
            required: "该输入项为必输项！"
        }
    },
    // 提交表单
    submitHandler: function (form) {
        var params=$(form).serialize();
        // 异步保存
        $.ajax({
            type: "POST",
            dataType: "json",
            cache: false,
            url: "/sys/menu/saveOrUpdate",
            data: params,
            success: function (result) {
                if (result.resultStat=="SUCCESS") {
                    layer.msg('保存成功！', {
                        time: 2000 //20s后自动关闭
                    });
                    $('#myModal').modal("hide");
                    loadTable();
                    parent.onloadMenuTree();
                } else {
                    layer.alert("保存失败,"+result.mess);
                }
            },
            error: function (result) {
                layer.alert("保存失败，"+result.mess);
            }
        });
    }
});

function resetForm(form) {
    menuFormValidator.resetForm();
   $(form).find('input[type=text],select,input[type=hidden]').each(function() {
          $(this).val('');
   });

}