<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Using Directive include: Will not show the changes made in Header in this File unless changes we change something in this File --%>
<%-- 1. Using this JSP include will share the imports and all other variables inside the included variable --%>
<%@include file="layout/header.jsp" %>

		<h1>Shopping Cart</h1>
		
		<!-- shoppingCart es un elemento unico en los scopes así que podemos obviar sessionScope 
				solo que ahora no es un atributo de la session sino que es un Bean CDI @SessionScope 
				con nombre @Name("shoppingCart").
				ver ramas: feature/12_webapp_session y feature/13_webapp_shopping_cart 	
			-->
		<c:choose>
			<%-- <c:when test ="${sessionScope.shoppingCart == null || shoppingCart.itemsCart.isEmpty()}"> --%>
			<c:when test ="${shoppingCart == null || shoppingCart.itemsCart.isEmpty()}">
				<p>We are sorry! there is no products in the shopping cart</p>
			</c:when>
			<c:otherwise>
				<table class="table">
					<tr>
						<th>Id</th>
						<th>Name</th>
						<th>Price</th>
						<th>quantity</th>
						<th>Total</th>
					</tr>
					<%-- <c:forEach items="${sessionScope.shoppingCart.itemsCart}" var="itemCart"> --%>
					<c:forEach items="${shoppingCart.itemsCart}" var="itemCart">
						<tr>
							<td>${itemCart.product.id}</td>
							<td>${itemCart.product.name}</td>
							<td>${itemCart.product.price}</td>
							<td>${itemCart.quantity}</td>
							<td>${itemCart.amount}</td>
						</tr>
					</c:forEach>
					<tr>
						<td colspan="4" style="text-align: right">Total</td>
						<td>${shoppingCart.total}</td>
						<%-- <td>${sessionScope.shoppingCart.total}</td> --%>
					</tr>
				</table>
			</c:otherwise>
		</c:choose>
		<p><a href="${pageContext.request.contextPath}/products">Continue buying</a></p>
		<p><a href="<%=request.getContextPath()%>/index.jsp">Back</a></p>

<%@include file="layout/footer.jsp" %>
