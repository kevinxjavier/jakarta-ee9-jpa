<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Using Directive include: Will not show the changes made in Header in this File unless changes we change something in this File --%>
<%-- 1. Using this JSP include will share the imports and all other variables inside the included variable --%>
<%@include file="layout/header.jsp" %>

		<h1>Iniciar Sesion</h1>
		<form action="/jakarta-ee9-webapp-cdi/login" method="post">
			<div class="row my-2">
				<label for="username" class="form-label">Username</label>
				<div>
					<input type="text" name="username" id="username" class="form-control" />
				</div>
			</div>
			<div class="row my-2">
				<label for="password" class="form-label">Password</label>
				<div>
					<input type="password" name="password" id="password"  class="form-control" />
				</div>
			</div>
			<div class="row my-2">
				<input type="submit" value="Login"  class="btn btn-primary" />
			</div>
			<a href="<%=request.getContextPath()%>/index.jsp">Volver</a>
		</form>

<%@include file="layout/footer.jsp" %>
