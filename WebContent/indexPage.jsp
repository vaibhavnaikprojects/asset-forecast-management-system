
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>AFMS | Welcome</title>
    <link href="<c:url value="/resources/css/bootstrap.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/fonts/css/font-awesome.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/animate.min.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/custom.css"/>" rel="stylesheet">
    <link href="<c:url value="/resources/css/icheck/flat/green.css"/>" rel="stylesheet">
    <script src="<c:url value="/resources/js/jquery.min.js"/>"></script>
</head>

<body >
    
    
        <div id="wrapper">
            <div id="login" class="animate form">
                <section class="login_content" style="background-color: buttonface;padding: 4%;opacity:0.97;margin-top: -10%;border-radius:18px ">
                <h1> Asset Forecast<br>&<br> Management System</h1>
                      <h4>${message} ${success}</h4>
                  <form action="login.html" method="post" class="form-signin">
                        <div>
                            <input type="text" class="form-control" name="cecid" id="cecid" placeholder="CEC ID" autofocus="autofocus" required="required"  style="border-radius:8px"/>
                        </div>
                        <div>
                            <input class="form-control" placeholder="Password" required="required" type="password" name="password" id="password" style="border-radius:8px"/>
                        </div>
                        <div>
                           <button class="btn btn-lg btn-primary btn-block" type="submit" style="border-radius:8px">Login</button>
                        </div>
                        </form>
                        <div class="clearfix"></div>
                        <div class="separator">
							<div class="clearfix"></div>
                            <br />
                           <!--  <div style="margin: -3%">
                                <h2>AFMS</h2>
                                <p style="margin: -1%">Copyright © 2015 Cisco Systems @ Zensar Technologies Inc. All right reserved.</p>
                            </div> -->
                        </div>
                    <!-- form -->
                </section>
                <!-- content -->
            </div>
          
        </div>
    <script src="resources/js/jquery.js"></script>
    <script src="resources/js/bootstrap.min.js"></script>

    <!--BACKSTRETCH-->
    <!-- You can use an image of whatever size. This script will stretch to fit in any screen size.-->
    <script type="text/javascript" src="resources/js/jquery.backstretch.min.js"></script>
    <script>
        $.backstretch("resources/images/professional.jpg", {speed: 500});
    </script>

</body>
</html>