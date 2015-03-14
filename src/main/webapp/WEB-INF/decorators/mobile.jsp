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
        <script src="/HFW/script/jquery-1.11.1.min.js"></script>
        <script src="/HFW/script/angular/1.2.26/angular.min.js"></script>
        <script src="/HFW/script/angular/1.2.26/angular-route.min.js"></script>
        <script src="/HFW/script/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href = "/HFW/style/bootstrap/3.2.0/bootstrap.min.css"/>
        <link rel="stylesheet" href = "/HFW/style/dashboard.css"/>
        <link rel="stylesheet" href = "/HFW/themes/default/theme.css"/>

  </head>
  <body>
      <div id="header">
          <a href="/HFW/mobile" class="headerItem mobileHeader">Accounts</a>
        </div>
      
    <div id="hfMain">
      <decorator:body />
    </div>
  </body>
</html>
