<!DOCTYPE html>
<html lang="en">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
	crossorigin="anonymous"></script>
	<head>
		<meta charset="UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1.0">
		<title>${title}</title>
	</head>
	<body>
	
	<!-- shoppingCart es un elemento unico en los scopes así que podemos obviar sessionScope 
		solo que ahora no es un atributo de la session sino que es un Bean CDI @SessionScope 
		con nombre @Name("shoppingCart").
		ver ramas: feature/12_webapp_session y feature/13_webapp_shopping_cart 	
	-->
	
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
	  <div class="container-fluid">
	    <a class="navbar-brand" href="#">Enterprise</a>
	    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
	      <span class="navbar-toggler-icon"></span>
	    </button>
	    <div class="collapse navbar-collapse" id="navbarSupportedContent">
	      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
	        <li class="nav-item">
	          <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}/index.jsp">Home</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="${pageContext.request.contextPath}/products">Products</a>
	        </li>
	        <li class="nav-item">
	          <a class="nav-link" href="${pageContext.request.contextPath}/cart/view">Cart (${shoppingCart.itemsCart.size()})</a>
	          <%-- <a class="nav-link" href="${pageContext.request.contextPath}/cart/view">Cart (${sessionScope.shoppingCart.itemsCart.size()})</a> --%>
	        </li>
	        <li class="nav-item dropdown">
	          <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
	            ${not empty sessionScope.username ? sessionScope.username : "Account"}
	          </a>
	          <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
	            <li><a class="dropdown-item" href="${pageContext.request.contextPath}/${not empty sessionScope.username ? "logout" : "login"}">${not empty sessionScope.username ? "Logout" : "Login"}</a></li>
	          </ul>
	        </li>
	      </ul>
	    </div>
	  </div>
	</nav>

	<div class="container">