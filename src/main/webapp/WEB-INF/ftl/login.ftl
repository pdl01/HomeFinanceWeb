<html>
<head>
        <meta name="_csrf" content="${_csrf.token}"/>
        <!-- default header name is X-CSRF-TOKEN -->
        <meta name="_csrf_header" content="${_csrf.headerName}"/>   
        <style>
body {
    margin:0px;
    font-family: 'arial', 'sans-serif';
}

#loginForm {
    margin-top:40px;
    margin-left:100px;
    border-style:solid;
    border-color:black;
    border-width: 1px;
    width:300px;
    display:inline-block;
}
.formLabel{
    width:30%;
    float:left;
    margin:10px;
    vertical-align: text-top;
    padding:2px;
}
.formInput {
    /*float:right;*/
margin:10px;
vertical-align: text-top;
    padding:2px;
}
.formControls{
    float:left;
    margin:10px;
}
        </style>
</head>
<body>
<form method="POST" action="/login">
<div id="loginForm">
    
    <div class="formLabel">Username:</div><div class="formInput"><input type="text" name="username"></div>
    <div class="formLabel">Password:</div><div class="formInput"><input type="text" name="password"></div>
    <input type="hidden" name="${_csrf.parameterName}" 	value="${_csrf.token}" />
    <div class="formControls"><input type="submit" value="Login"></div>

</div>
</form>
</body>
</html>