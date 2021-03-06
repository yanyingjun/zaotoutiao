<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>早头条后台管理系统</title>
    <#assign ctx="${springMacroRequestContext.contextPath}" />
    <link href="${ctx}/static/css/base.css" rel="stylesheet">
    <link href="${ctx}/static/css/platform.css" rel="stylesheet">
    <link rel="stylesheet" href="${ctx}/static/uimaker/easyui.css">
</head>
<body>
<div class="container">
    <div id="pf-hd">
        <div class="pf-logo">
            <img src="${ctx}/static/images/main/main_logo.png" alt="logo">
        </div>

        <div class="pf-nav-wrap">
            <div class="pf-nav-ww">
                <ul class="pf-nav">
                    <!--<li class="pf-nav-item home current" data-menu="sys-manage">
                        <a href="javascript:;">
                            <span class="iconfont">&#xe63f;</span>
                            <span class="pf-nav-title">系统管理</span>
                        </a>
                    </li>-->
                </ul>
            </div>


            <a href="javascript:;" class="pf-nav-prev disabled iconfont">&#xe606;</a>
            <a href="javascript:;" class="pf-nav-next iconfont">&#xe607;</a>
        </div>



        <div class="pf-user">
            <div class="pf-user-photo">
                <img src="${ctx}/static/images/main/user.png" alt="">
            </div>
            <h4 class="pf-user-name ellipsis">uimaker</h4>
            <i class="iconfont xiala">&#xe607;</i>

            <div class="pf-user-panel">
                <ul class="pf-user-opt">
                    <li>
                        <a href="javascript:;">
                            <i class="iconfont">&#xe60d;</i>
                            <span class="pf-opt-name">用户信息</span>
                        </a>
                    </li>
                    <li class="pf-modify-pwd">
                        <a href="http://www.uimaker.com">
                            <i class="iconfont">&#xe634;</i>
                            <span class="pf-opt-name">修改密码</span>
                        </a>
                    </li>
                    <li class="pf-logout">
                        <a href="/login">
                            <i class="iconfont">&#xe60e;</i>
                            <span class="pf-opt-name">退出</span>
                        </a>
                    </li>
                </ul>
            </div>
        </div>

    </div>

    <div id="pf-bd">
        <div id="pf-sider">
            <h2 class="pf-model-name">
                <span class="iconfont">&#xe64a;</span>
                <span class="pf-name">导航管理</span>
                <span class="toggle-icon"></span>
            </h2>

            <ul class="sider-nav">
                <li class="current">
                    <a href="javascript:;">
                        <span class="iconfont sider-nav-icon">&#xe620;</span>
                        <span class="sider-nav-title">基础信息管理</span>
                        <i class="iconfont">&#xe642;</i>
                    </a>
                    <ul class="sider-nav-s">
                        <li class="active"><a href="javascript:void(0);" onclick="Open('导航管理', '/channel/manageView')">导航管理</a></li>
                        <li><a href="javascript:void(0);" onclick="Open('一级标签', '/first/tab')">一级标签</a></li>
                        <li><a href="javascript:void(0);" onclick="Open('二级标签', '/second/tab')">二级标签</a></li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="iconfont sider-nav-icon">&#xe625;</span>
                        <span class="sider-nav-title">内容管理</span>
                        <i class="iconfont">&#xe642;</i>
                    </a>
                    <ul class="sider-nav-s">
                        <li class="active"><a href="javascript:void(0);" onclick="Open('内容列表', '/content/list/view')">内容列表</a></li>
                        <li><a href="#">CP合作</a></li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="iconfont sider-nav-icon">&#xe62f;</span>
                        <span class="sider-nav-title">推荐管理</span>
                        <i class="iconfont">&#xe642;</i>
                    </a>
                    <ul class="sider-nav-s">
                        <li class="active"><a href="#">人为推荐</a></li>
                        <li><a href="javascript:void(0)">置顶设置</a></li>
                        <li><a href="#">推荐规则</a></li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="iconfont sider-nav-icon">&#xe647;</span>
                        <span class="sider-nav-title">TOP排行</span>
                        <i class="iconfont">&#xe642;</i>
                    </a>
                    <ul class="sider-nav-s">
                        <li class="active"><a href="javascript:void(0);" onclick="Open('导航排行', '/nav/rank')">导航排行</a></li>
                        <li><a href="javascript:void(0);" onclick="Open('一级标签排行', '/first/rank')">一级标签排行</a></li>
                        <li><a href="javascript:void(0);" onclick="Open('二级标签排行', '/second/rank')">二级标签排行</a></li>
                        <li><a href="javascript:void(0);" onclick="Open('关键词排行', '/keyword/rank')">关键词排行</a></li>
                        <li><a href="javascript:void(0);" onclick="Open('文章排行', '/article/rank')">文章排行</a></li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="iconfont sider-nav-icon">&#xe611;</span>
                        <span class="sider-nav-title">广告管理</span>
                        <i class="iconfont">&#xe642;</i>
                    </a>
                    <ul class="sider-nav-s">
                        <li class="active"><a href="#">广告位管理</a></li>
                        <li><a href="#">广告主管理</a></li>
                        <li><a href="#">广告库管理</a></li>
                        <li><a href="#">广告投放管理</a></li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="iconfont sider-nav-icon">&#xe633;</span>
                        <span class="sider-nav-title">系统设置</span>
                        <i class="iconfont">&#xe642;</i>
                    </a>
                    <ul class="sider-nav-s">
                        <li class="active"><a href="javascript:void(0);" onclick="Open('汇率设置', '/exchange/rate')">汇率设置</a></li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="iconfont sider-nav-icon">&#xe633;</span>
                        <span class="sider-nav-title">用户列表</span>
                        <i class="iconfont">&#xe642;</i>
                    </a>
                    <ul class="sider-nav-s">
                        <li class="active"><a href="#">阅读篇数</a></li>
                        <li><a href="#">金币来源</a></li>
                        <li><a href="#">零钱明细</a></li>
                        <li><a href="javascript:void(0)">徒弟列表</a></li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="iconfont sider-nav-icon">&#xe633;</span>
                        <span class="sider-nav-title">用户分析</span>
                        <i class="iconfont">&#xe642;</i>
                    </a>
                    <ul class="sider-nav-s">
                        <li class="active"><a href="javascript:void(0);" onclick="Open('下载量', '/behavior')">下载量</a></li>
                        <li><a href="javascript:void(0);" onclick="Open('用户数据', '/retention')">用户数据</a></li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="iconfont sider-nav-icon">&#xe633;</span>
                        <span class="sider-nav-title">推送管理</span>
                        <i class="iconfont">&#xe642;</i>
                    </a>
                    <ul class="sider-nav-s">
                        <li class="active"><a href="javascript:void(0);" onclick="Open('推送列表', '/push/manage')">推送列表</a></li>
                        <li><a href="#">违禁词库</a></li>
                        <li><a href="javascript:void(0);" onclick="Open('评论管理', '/comment/management')">评论管理</a></li>
                    </ul>
                </li>
                <li>
                    <a href="javascript:;">
                        <span class="iconfont sider-nav-icon">&#xe633;</span>
                        <span class="sider-nav-title">财务管理</span>
                        <i class="iconfont">&#xe642;</i>
                    </a>
                    <ul class="sider-nav-s">
                        <li class="active"><a href="javascript:void(0);" onclick="Open('提现申请', '/present/req')">提现申请</a></li>
                        <li><a href="javascript:void(0);" onclick="Open('金币预警', '/gold/earlyWarning')">金币预警</a></li>
                        <li><a href="javascript:void(0);" onclick="Open('金币统计', '/userGold')">金币统计</a></li>
                        <li><a href="javascript:void(0)" onclick="Open('零钱统计','/userMoney')">零钱统计</a></li>
                        <li><a href="javascript:void(0)" onclick="Open('可提现用户列表','/bePresentedUser')">可提现用户列表</a></li>
                    </ul>
                </li>
            </ul>
        </div>

        <div id="pf-page" region="center">
                <div class="easyui-tabs" fit="true" border="false" id="tabs">
                    <div title="首页" style="padding:10px 5px 5px 10px;">
                        <iframe class="page-iframe" src="/main" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>
                    </div>


                <!--<div title="采购组织" style="padding:10px 5px 5px 10px;" data-options="closable:true">
                    <iframe class="page-iframe" src="index.ftl" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>
                </div>
                <div title="基本信息" data-options="closable:true" style="padding:10px 5px 5px 10px;">
                    <iframe class="page-iframe" src="basic_info.html" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>
                </div>
                <div title="供应商" data-options="closable:true" style="padding:10px 5px 5px 10px;">
                    <iframe class="page-iframe" src="providers.html" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>
                </div>
                <div title="业务流程" data-options="closable:true" style="padding:10px 5px 5px 10px;">
                    <iframe class="page-iframe" src="process.html" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>
                </div>
                <div title="表单管理" data-options="closable:true" style="padding:10px 5px 5px 10px;">
                    <iframe class="page-iframe" src="providers1.html" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>
                </div>-->
            </div>
        </div>
    </div>

    <div id="pf-ft">
        <div class="system-name">
            <i class="iconfont">&#xe6fe;</i>
            <span>信息管理系统&nbsp;v1.0</span>
        </div>
        <div class="copyright-name">
            <span>CopyRight&nbsp;2016&nbsp;&nbsp;uimaker.com&nbsp;版权所有</span>
            <i class="iconfont" >&#xe6ff;</i>
        </div>
    </div>
</div>

<script type="text/javascript" src="${ctx}/static/js/jquery-3.3.1.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/main.js"></script>
<!--[if IE 7]>
<script type="text/javascript">
    $(window).resize(function(){
        $('#pf-bd').height($(window).height()-76);
    }).resize();

</script>
<![endif]-->


<script type="text/javascript">

    //在右边center区域打开菜单，新增tab
    function Open(text, url) {
        if ($('#tabs').tabs('exists', text)) {
            $('#tabs').tabs('select', text);
        } else {
            $('#tabs').tabs('add', {
                title : text,
                closable : true,
                content : '<iframe class="page-iframe" src="'+url+'" frameborder="no"   border="no" height="100%" width="100%" scrolling="auto"></iframe>'
            });
        }
    }

    /*$('.easyui-tabs1').tabs({
        tabHeight: 44,
        onSelect:function(title,index){
            var currentTab = $('.easyui-tabs1').tabs("getSelected");
            if(currentTab.find("iframe") && currentTab.find("iframe").size()){
                currentTab.find("iframe").attr("src",currentTab.find("iframe").attr("src"));
            }
        }
    })*/
    $(window).resize(function(){
        $('.tabs-panels').height($("#pf-page").height()-46);
        $('.panel-body').height($("#pf-page").height()-76)
    }).resize();

    var page = 0,
        pages = ($('.pf-nav').height() / 70) - 1;

    if(pages === 0){
        $('.pf-nav-prev,.pf-nav-next').hide();
    }
    $(document).on('click', '.pf-nav-prev,.pf-nav-next', function(){


        if($(this).hasClass('disabled')) return;
        if($(this).hasClass('pf-nav-next')){
            page++;
            $('.pf-nav').stop().animate({'margin-top': -70*page}, 200);
            if(page == pages){
                $(this).addClass('disabled');
                $('.pf-nav-prev').removeClass('disabled');
            }else{
                $('.pf-nav-prev').removeClass('disabled');
            }

        }else{
            page--;
            $('.pf-nav').stop().animate({'margin-top': -70*page}, 200);
            if(page == 0){
                $(this).addClass('disabled');
                $('.pf-nav-next').removeClass('disabled');
            }else{
                $('.pf-nav-next').removeClass('disabled');
            }

        }
    })

    // setTimeout(function(){
    //    $('.tabs-panels').height($("#pf-page").height()-46);
    //    $('.panel-body').height($("#pf-page").height()-76)
    // }, 200)
</script>
</body>
</html>
