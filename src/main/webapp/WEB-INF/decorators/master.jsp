<%@ taglib prefix="decorator" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
  <head>    
    <title>
    Home Finance: <decorator:title /> 
    </title>
  	<script>
  	</script>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <script src="script/jquery-1.11.1.min.js"></script>
        <script src="script/angular/1.2.26/angular.min.js"></script>
        <script src="script/angular/1.2.26/angular-route.min.js"></script>
        <script src="script/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href = "style/bootstrap/3.2.0/bootstrap.min.css"/>
        <link rel="stylesheet" href = "style/dashboard.css"/>
        <link rel="stylesheet" href = "themes/default/theme.css"/>

  </head>
  <body>
      <div id="header">
            <ul id="left_header">
                <li class="headerItem"><a class="headerItem" href="/HFW/index.jsp">Accounts</a></li>
                <li class="headerItem"><a class="headerItem" href="/HFW/budget.jsp">Budget</a></li>
            </ul>
          <ul>
               <ul id="right_header">
                <li class="headerItem"><a class="headerItem" href="/HFW/settings.jsp">Settings</a></li>
            </ul>
          </ul>
        </div>
    <div id="hfMain">
      <decorator:body />
    </div>
  </body>
</html>
