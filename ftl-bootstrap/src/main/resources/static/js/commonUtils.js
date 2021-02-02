var APPLE_QJP = function(){};
APPLE_QJP.utils = {
   
     /**
     * 查询数据字典列表
     * @param typeCode 
     * @return List<BscDicCodeItem>
     */
    getBscDicCodeItemListByTypeCode:function (typeCode) {
        $.ajax({
            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                url: "/data/dic/getBscDicCodeItemListByTypeCode/"+typeCode ,//url
                //data: $('#form1').serialize(),
                success: function (result) {
                    return result;
                },
                error : function() {
                    alert("异常！");
                }
            });
    },
     /**
     * 查询数据字典列表
     * @param typeCode 
     * @param sel select选择器
     * @return List<BscDicCodeItem>
     */
      getBscDicCodeItemListByTypeCode:function (typeCode,sel) {
        $(sel).empty();
        $.ajax({
            //几个参数需要注意一下
                type: "POST",//方法类型
                dataType: "json",//预期服务器返回的数据类型
                url: "/data/dic/getBscDicCodeItemListByTypeCode/"+typeCode ,//url
                //data: $('#form1').serialize(),
                success: function (result) {
                    var options='<option value="">--请选择--</option>';
                    if(result && result.length>0){
                        for(var i=0;i<result.length;i++){
                            var row=result[i];
                            options+='<option value="'+row.itemCode+'">'+row.itemName+'</option>';
                        }
                    }
                    $(sel).append(options);
                },
                error : function() {
                    alert("异常！");
                }
            });
    }
};