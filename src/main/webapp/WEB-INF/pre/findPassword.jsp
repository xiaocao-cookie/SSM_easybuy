<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
    if(request.getSession().getAttribute("loginUser")!=null){
        response.sendRedirect(request.getContextPath()+"/Home?action=index");
    }
%>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <%@ include file="../common/pre/header.jsp" %>
    <script type="text/javascript" src="${ctx}/statics/js/login/login.js"></script>
    <title>易买网</title>
</head>
<body>  
<!--Begin Login Begin-->
<div class="log_bg">	
    <div class="top">
        <div class="logo"><a href="${ctx}/Home/index.html"><img src="${ctx}/statics/images/logo.png" /></a></div>
    </div>
	<div class="login">
    	<div class="log_img"><img src="${ctx}/statics/images/l_img.png" width="611" height="425" /></div>
		<div class="log_c">
        	<form method="post">
            <table border="0" style="width:370px; font-size:14px; margin-top:30px;" cellspacing="0" cellpadding="0">
              <tr height="50" valign="top">
              	<td width="55">&nbsp;</td>
                <td>
                	<span class="fl" style="font-size:24px;">找回密码</span>
                    <span class="fr">还没有商城账号，<a href="${ctx}/Register/toRegister" style="color:#ff4e00;">立即注册</a></span>
                </td>
              </tr>
              <tr height="70">
                <td>登录名</td>
                <td><input type="text" name="loginName" id="loginName" value="" class="l_user" /></td>
              </tr>
              <tr height="60">
              	<td>&nbsp;</td>
                <td><input type="button" value="找回密码" class="log_btn" onclick="findPassword();" /></td>
              </tr>
                <tr height="70" style="display: none">
                    <td>密&nbsp; &nbsp; 码</td>
                    <td><input type="text" name="password" id="password" value="" class="l_pwd" /></td>
                </tr>
            </table>
            </form>
        </div>
    </div>
</div>
<!--End Login End-->
<!--Begin Footer Begin-->
<div class="btmbg">
    <div class="btm">
        备案/许可证编号：蜀ICP备12009302号-1-www.dingguagua.com   Copyright © 2018-2028 尤洪商城网 All Rights Reserved. 复制必究 , Technical Support: Dgg Group <br />
        <img src="${ctx}/statics/images/b_1.gif" width="98" height="33" /><img src="${ctx}/statics/images/b_2.gif" width="98" height="33" /><img src="${ctx}/statics/images/b_3.gif" width="98" height="33" /><img src="${ctx}/statics/images/b_4.gif" width="98" height="33" /><img src="${ctx}/statics/images/b_5.gif" width="98" height="33" /><img src="${ctx}/statics/images/b_6.gif" width="98" height="33" />
    </div>    	
</div>
<!--End Footer End -->
</body>
</html>
