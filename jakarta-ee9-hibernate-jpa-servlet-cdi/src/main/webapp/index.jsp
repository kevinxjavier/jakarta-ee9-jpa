<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Using Tag include: Will show the changes made in Header File in this file without changes in this file --%>
<%-- 1. Also the request.setAttribute in his Servlet won't work here after the redirect tp his jsp, better use then the Directive Include --%>
<%-- 2. With this Tag include won't put all variables and imports inside the jsp page included --%>
<jsp:include page="layout/header.jsp" />

		<h3>User Form</h3>
		<h6>
			<h5>Session HTTP</h5>
			<ul class="list-group">
			<li class="list-group-item active">Option Menu</li>
			<li class="list-group-item"><a href="/jakarta-ee9-webapp-cdi/products.html">Products</a></li>
			<li class="list-group-item"><a href="/jakarta-ee9-webapp-cdi/login.jsp">Login</a></li>
			<li class="list-group-item"><a href="/jakarta-ee9-webapp-cdi/logout">Logout</a></li>
			<li class="list-group-item"><a href="/jakarta-ee9-webapp-cdi/cart/view">View Cart</a></li>
			</ul> 
		</h6>
	</body>
</html>

<jsp:include page="layout/footer.jsp" />
