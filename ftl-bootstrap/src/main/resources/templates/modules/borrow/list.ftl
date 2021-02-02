<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>借阅管理</title>
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
<div class="wrapper wrapper-content animated fadeInRight">
    <!-- Panel Other -->
    <div class="ibox float-e-margins">
        <div class="ibox-title">
            <h5><i class="fa fa-list"></i> 借阅列表</h5>
        </div>
        <div class="ibox-content">
            <div class="row row-lg">
                <div class="col-sm-12">
                    <!-- Example Events -->
                    <div class="example-wrap">
                        <#--<h4 class="example-title">事件</h4>-->
                            <div class="btn-group hidden-xs" id="toolbar" role="group">
                                <button type="button" class="btn btn-outline btn-white" title="全部借阅记录" onclick="showBorrowHistoryListByUser()">
                                    <i class="fa fa-clock-o" aria-hidden="true"> </i> 全部借阅记录
                                </button>
                            </div>
                        <div class="example">
                            <table id="borrow_list_table" data-height="520" data-mobile-responsive="true">
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
<script type="text/javascript" src="/static/modules/data/borrow/list.js"></script>

</body>

</html>
