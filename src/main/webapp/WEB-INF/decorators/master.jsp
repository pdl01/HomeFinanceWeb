<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>    
	<meta name="_csrf" content="${_csrf.token}"/>
	<!-- default header name is X-CSRF-TOKEN -->
	<meta name="_csrf_header" content="${_csrf.headerName}"/>      
    <title>
    Home Finance: <decorator:title /> 
    </title>
  	<script>
  	</script>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="/HFW/script/jquery-1.11.1.min.js"></script>
        <script src="/HFW/script/angular/1.2.26/angular.min.js"></script>
        <script src="/HFW/script/angular/1.2.26/angular-route.min.js"></script>
        <script src="/HFW/script/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href = "/HFW/style/bootstrap/3.2.0/bootstrap.min.css"/>
        <link rel="stylesheet" href = "/HFW/style/dashboard.css"/>
        <link rel="stylesheet" href = "/HFW/themes/<c:out value="${theme}"/>/theme.css"/>

  </head>
  <body>
      <div id="header">
            <ul id="left_header">
                <li class="headerItem"><a class="headerItem" href="/HFW/app/dashboard">Accounts</a></li>
                <li class="headerItem"><a class="headerItem" href="/HFW/app/budget">Budget</a></li>
            </ul>
          <ul>
               <ul id="right_header">
                <li class="headerItem"><a class="headerItem" href="/HFW/app/settings">Settings</a></li>
            </ul>
          </ul>
        </div>
    <div id="hfMain">
      <decorator:body />
    </div>
  </body>
</html>
