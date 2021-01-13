//登录的方法
function login(){
    var loginName=$("#loginName").val();
    var password=$("#password").val();
    $.ajax({
        url:contextPath+"/Login",
        method:"post",
        data:{loginName:loginName,password:password,action:"login"},
        success:function(jsonStr){
            var result=eval("("+jsonStr+")");
            if(result.status==1){
                window.location.href=contextPath+"/Home?action=index";
            }else{
                showMessage(result.message)
                window.location.reload();
            }
        }
    });
}

//找回密码
function findPassword() {
    var loginName = $("#loginName").val();
    $("#password").show();
    $.ajax({
        url:contextPath+"/Login/findPassword",
        method: "post",
        data:{loginName:loginName},
        success:function (jsonStr) {
            var result=eval("("+jsonStr+")");
            //无该用户
            if(result.status == 1){
                showMessage(result.message);
                window.location.reload();
            }else{
                showMessage("您的密码为:"+result.message);
                window.location.reload();
            }
        }
    });
}