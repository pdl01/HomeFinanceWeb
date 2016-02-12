<!DOCTYPE html>
<html>

    <head>
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
        	<meta name="_csrf" content="${_csrf.token}"/>
                <!-- default header name is X-CSRF-TOKEN -->
                <meta name="_csrf_header" content="${_csrf.headerName}"/>      

        <script src="/script/jquery-1.11.1.min.js"></script>
        <script src="/script/angular/1.2.26/angular.min.js"></script>
        <script src="/script/angular/1.2.26/angular-route.min.js"></script>
        <script src="/script/bootstrap/3.2.0/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href = "/style/bootstrap/3.2.0/bootstrap.min.css"/>
        <link rel="stylesheet" href = "/style/dashboard.css"/>
        <link rel="stylesheet" href = "/themes/${theme}/theme.css"/>
    </head>

    <body >
    <div id="exportFile">
        <input type="button" id="btnExport" value="Export">
    </div>
    <div>
        <input type="button" id="btnImport" value="Import">
    </div>
    <form id="exportForm" action="/servlet/fileDownload"></form>
    <script>
    $(document).ready(function(){
        $('#btnExport').click(function(){
            $('#exportForm').submit();
        });
    });

    </script>
    </body>
    
</html>
