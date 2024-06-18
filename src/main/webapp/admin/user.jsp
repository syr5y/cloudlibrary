<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>云借阅-图书管理系统</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/webbase.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/pages-login-manage.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
</head>
<style type="text/css">
    .image{
        width: 100%;
        height: auto;
        display: flex;
        justify-content: center;
    }
    img{
        width: 60px;
        height: 60px;
        border-radius: 50%;
        border: 1px solid red;
    }
    #mytitle{
        width: 100%;
        display: flex;
        justify-content: center;
    }
    /*头像*/
    img[alt="头像"] {
        width: 80px;
        height: 80px;
        border-radius: 50%;
        overflow: hidden;
        border: 1px solid red;
    }

    #fileform {
        width: 300px;
        height: 150px;
        margin-top: 20px;
        font-size: 15px;
    }

    button,
    #dialog {
        margin-top: 50px;
        margin-left: 10px;
        width: 80px;
        height: 30px;
        background-color: aqua;
        border: none;
    }
    #opens {
        width: 300px;
        height: 260px;
        background-color: aliceblue;
        border: 1px solid gray;
    }
    #opens::backdrop {
        background-color: rgba(0, 0, 0, 0.5);
        backdrop-filter: blur(1px);
    }
</style>
<body>
<div class="loginmanage" >
    <div class="py-container">
        <h4 class="manage-title">云借阅-图书管理系统</h4>
        <div class="loginform" >
            <ul class="sui-nav nav-tabs tab-wraped" id="mytitle">
                <li class="active">
                    <h3>账户信息</h3>
                </li>
            </ul>
            <div class="tab-content tab-wraped" >
                <div id="profile" class="tab-pane  active">
                    <div class="image" onclick="opens.showModal()">
                        <img id="primg" src="${pageContext.request.contextPath}/${USER_IMGPATH}"/>
                    </div>
                    <%--登录提示信息--%>
                    <span style="color: red">${msg}</span>
                    <form id="loginform" class="sui-form" action="${pageContext.request.contextPath}/update" method="post">
                        <div class="input-prepend">
                            <span class="add-on loginname">用户名</span>
                            <input type="text" class="span2 input-xfat" value="${USER_SESSION.name}" name="name"/>
                        </div>
                        <div class="input-prepend"><span class="add-on loginpwd">密码</span>
                            <input type="text" class="span2 input-xfat" value="${USER_SESSION.password}" name="password"/>
                        </div>
                        <div class="input-prepend"><span class="add-on loginpwd">邮箱</span>
                            <input type="text" class="span2 input-xfat" value="${USER_SESSION.email}" name="email"/>
                        </div>
                        <div class="input-prepend"><span class="add-on loginpwd">权限</span>
                            <input type="text" value="${USER_SESSION.role}" class="span2 input-xfat" name="role"/>
                        </div>
                        <div class="logined" id="updateuser">
                            <a class="sui-btn btn-block btn-xlarge btn-danger"
                               href='javascript:document:loginform.submit();' target="_self">修&nbsp;&nbsp;改</a>
                        </div>
                    </form>
                    <div class="logined">
                        <a href="${pageContext.request.contextPath}/logout">
                            <span class="hidden-xs">注销</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/delectuser">
                            <span class="hidden-xs">删除</span>
                        </a>
                        <a href="/tomain">
                            <span class="hidden-xs">首页</span>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<dialog id="opens">
    <div style="display: flex;align-items: center;flex-direction: column;">
        <img src="${pageContext.request.contextPath}/${USER_IMGPATH}" alt="头像">
        <form action="${pageContext.request.contextPath}/file" method="post"
              enctype="multipart/form-data" id="fileform">
            <label for="file">选择头像：</label>
            <input type="file" style="width: 150px;" id="file" name="file"/>
            <div class="btn">
                <button onclick="opens.close()" id="btnc">关闭</button>
                <input type="submit" value="提交" id="dialog" />
            </div>
        </form>
    </div>
</dialog>
</body>
<script type="text/javascript">
    /**
     * 登录超时 展示区跳出iframe
     */
    var _topWin = window;
    while (_topWin != _topWin.parent.window) {
        _topWin = _topWin.parent.window;
    }
    if (window != _topWin)
        _topWin.document.location.href = '${pageContext.request.contextPath}/admin/login.jsp';
//         禁止修改
    let submitCount = 0;
    $(document).ready(function() {
        $("form").find("#btnc").on("click",function (event){
            event.preventDefault();
        })
        //头像
        $("#file").change(function() {
            readURL(this);
        });
        //禁止修改
        $("input").prop("readonly",true)//只读
        var href=$("#updateuser").find("a").attr("href");//原始href
        $('#updateuser').find("a").on('click', function(event) {
            event.preventDefault(); // 阻止链接的默认行为
            submitCount++;
            if (submitCount === 1) {
                // 第一次点击时执行的代码
                $('input').prop("readonly",false);
                // 在这里执行你想要的任何操作，例如Ajax请求、显示模态框等
                // 重置表单或其他需要的操作
                // 例如，你可以使用 jQuery 的 reset() 方法来重置表单：
                // $('#loginform')[0].reset();
            }else{
                //重定向href
                window.location=href;
            }
        });
    });
    //头像
    function readURL(input) {
        var imgsrc=$("#opens").find("img").prop("src")
        const imageRegex = /^(image\/(jpg|jpeg|png|gif|bmp|webp))$/i;

        if (imageRegex.test(input.files[0].type)) {
            if(input.files){
                var reader = new FileReader();
                reader.onload = function(e) {
                    $("img").prop("src",e.target.result)
                };
                reader.readAsDataURL(input.files[0]);
            }
        }else{
            $("img").prop("src",imgsrc)
        }
    }
</script>
</html>