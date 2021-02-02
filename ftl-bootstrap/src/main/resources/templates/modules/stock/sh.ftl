<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>沪股列表</title>
    <#include "/common/base.ftl"/>
    <style>
        .btn-default{
            color: grey;
            background-color: transparent;
        }
        .btn-default:hover{
            color: #767676;
            background-color: #eeeeee;
        }
    </style>
</head>

<body class="gray-bg">

<div class="wrapper wrapper-content animated" >
    <!-- Panel Other -->
    <div class="ibox float-e-margins" >
        <div class="ibox-title">
            <h5><i class="fa fa-list"></i> 沪股列表</h5>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <!-- Example Events -->
                    <div class="example-wrap">
                        <#--<h4 class="example-title">事件</h4>-->
                        <div class="example">
                            <table id="sh_list_table" data-height="650" data-mobile-responsive="true">
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

<!--模态框新增编辑-->

<script type="text/javascript" src="/static/modules/data/stock/sh.js"></script>
</body>

</html>
