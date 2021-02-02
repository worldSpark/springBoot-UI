var tree=$ ("#using_json" );
$(function () {
    onloadParentDeptList();
})
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
        success: function (result) {
            if (result) {
                tree.jstree(result);
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
    $.ajax({
        type: "get",
        dataType: "json",
        async: true,
        url: "/sys/dept/getDeptById/"+currentNode.id,
        success: function (result) {
            if (result) {
                for (i in result){
                    $('#dept_form').find('input[name="'+i+'"]').val(result[i]);
                }
                $('#dept_form').find('select[name="parentId"]').find('option[value="'+result.parentId+'"]').prop("selected",true);
            }
        },
        error: function () {
            layer.alert("数据加载失败！");
        }
    });
});

function resetDeptInfo() {
    $("#dept_form").find('input[type=text],select,input[type=hidden]').each(function() {
        $(this).val('');
    });
}

var userFormValidator;
userFormValidator = $("#dept_form").validate({
    // 定义校验规则
    rules: {
        deptName: {
            required: true
        }
    },
    messages: {
        deptName: {
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
            url: "/sys/dept/saveOrUpdate",
            data: params,
            success: function (result) {
                if (result.resultStat=="SUCCESS") {
                    layer.msg('保存成功！', {
                        time: 2000 //20s后自动关闭
                    }).then(window.location.reload());
                    /*$.ajax.get("/sys/dept/treeList",null,"json",function(json) {
                        tree.jstree(true).settings.core.data = json;
                        tree.jstree(true).refresh();
                    })
                    onloadParentDeptList();*/
                } else {
                    layer.alert("保存失败，"+result.mess);
                }
            },
            error: function () {
                layer.alert("保存失败，"+result.mess);
            }
        });
    }
});

function onloadParentDeptList() {
    $('#dept_form #parentId').empty();
    $.ajax({
        type: "POST",
        dataType: "json",
        cache: false,
        url: "/sys/dept",
        success: function (result) {
            if (result && result.length>0) {
                var option='<option value="null">--请选择--</option>';
                for (var i=0;i<result.length;i++){
                    var row=result[i];
                    option+='<option value="'+row.id+'">'+row.deptName+'</option>';
                }
                $('#dept_form #parentId').append(option);
            }

        },
        error: function () {
            layer.alert("数据加载失败！");
        }
    });
}
function DeleteByIds() {
    var id= $('#dept_form #id').val();
    if(!id){
        layer.alert("请选择删除数据");
        return;
    }
    layer.confirm('你确定删除该部门？', {
        time: 0 //不自动关闭
        ,btn: ['确定', '取消']
        ,yes: function(index){
            $.ajax({
                type: "POST",
                dataType: "json",
                cache: false,
                url: "/sys/dept/delete/"+id,
                success: function (result) {
                    if (result.resultStat=="SUCCESS") {
                        layer.msg('删除成功！', {
                            time: 2000 //20s后自动关闭
                        }).then(window.location.reload());

                    } else {
                        layer.alert("删除失败，"+result.mess);
                    }
                },
                error: function (result) {
                    layer.alert("删除失败，"+result.mess);
                }
            });
        }
    });

}

function showAllot() {
    layer.open({
        type: 2,
        title: "分配用户",
        area: ['1200px', '600px'],
        maxmin: true,
        shade: 0.8,
        closeBtn: 1,
        //shadeClose: true,
        content: '/sys/dept/showAllot',
        btn:["关闭"]
    });
}