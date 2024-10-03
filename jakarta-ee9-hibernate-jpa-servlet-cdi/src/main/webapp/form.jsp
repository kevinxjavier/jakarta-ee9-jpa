<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" import="java.time.format.DateTimeFormatter" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Using Directive include: Will not show the changes made in Header in this File unless changes we change something in this File --%>
<%-- 1. Using this JSP include will share the imports and all other variables inside the included variable --%>
<%@include file="layout/header.jsp" %>

	<h1>Product Form</h1>

	<form action="${pageContext.request.contextPath}/product/form" method="post">

		<div>
			<label for="name">Name</label>
			<div>
				<input type="text" name="name" id="name"
					value="${product != null ? product.name : ""}">
			</div>
			<%-- if (errors != null && errors.containsKey("name")) {	--%>
			<c:if test="${errors != null && errors.containsKey('name')}">
				<div style="color: red;">${errors.get("name")} - ${errors.name}</div>
				<%--	out.println("<div style=\"color: red;\">" + errors.get("name") + "</div>");	--%>
			</c:if>
			<%-- }	--%>
		</div>

		<div>
			<label for="price">Price</label>
			<div>
				<input type="number" name="price" id="price"
					value="${product != null ? product.price : ""}">
			</div>
			<c:if test="${errors != null && not empty errors.price}">
				<div style="color: red;">${errors.price}</div>
			</c:if>
		</div>

		<div>
			<label for="sku">Sku</label>
			<div>
				<input type="text" name="sku" id="sku"
					value="${product != null ? product.sku : ""}">
			</div>
			<c:if test="${errors != null && not empty errors.sku}">
				<div style="color: red;">${errors.sku}</div>
			</c:if>
		</div>

		<div>
			<label for="date">Date</label>
			<div>
				<input type="date" name="date" id="date" value="${product != null ? product.date != null ? product.date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) : "" : ""}">
			</div>
			<c:if test="${errors != null && not empty errors.date}">
				<div style="color: red;">${errors.date}</div>
			</c:if>
		</div>

		<div>
			<label for="category">Category</label>
			<div>
				<select name="category" id="category">
					<option value="">--- Select ---</option>
					<c:forEach items="${categories}" var="category">
						<option value="${category.id}" ${product != null ? (category.id.equals(product.category.id) ? "selected" : "") : ""}>${category.name}</option>
					</c:forEach>
				</select>
			</div>
			<c:if test="${errors != null && not empty errors.category}">
				<div style="color: red;">${errors.category}</div>
			</c:if>
		</div>

		<input type="hidden" name="id"
			value="${product != null ? product.id : ""}">

		<input type="submit"
			value="${product != null && product.id != null && product.id > 0 ? "Update" : "Create"}">
		<p>
			<a href="${pageContext.request.contextPath}/products">Back</a>
		</p>

	</form>

<%@include file="layout/footer.jsp" %>
