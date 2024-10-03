<%@page contentType="text/html; pageEncoding=UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%-- Using Tag include: Will show the changes made in Header File in this file without changes in this file --%>
<%-- Also the request.setAttribute in his Servlet won't work here after the redirect tp his jsp, better use then the Directive Include --%>
<%-- 2. With this Tag include won't put all variables and imports inside the jsp page included --%>
<jsp:include page="layout/header.jsp" />

<%
	// List<Product> products = (List<Product>) request.getAttribute("products");
	// Optional<String> usernameOptional = (Optional<String>) request.getAttribute("usernameOptional");

	// String messageApplication = (String) request.getAttribute("message");
	// String messageRequest = (String) getServletContext().getAttribute("message");
%>

	<h1>List Products</h1>

	<%
	//if (usernameOptional.isPresent()) {
	%>
	<c:if test="${usernameOptional.isPresent()}">
		<div>
			Hi Welcome
			<%--=usernameOptional.get()--%>
			<c:out value="${usernameOptional.get()}" />
		</div>
			<a href="<c:out value="${pageContext.request.contextPath}" />/product/form">Create [+]</a>
	</c:if>
	<%
	// }
	%>

	<table class="table">
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Type</th>

			<%
			// if (usernameOptional.isPresent()) {
			%>
			<!-- ${usernameOptional.isPresent()} is the same as the next line only that when is a boolean we can treated as an attribute-->
			<c:if test="${requestScope.usernameOptional.present}"><!-- we can omit requestScope because it is in only one scope so there is no confusion as the attribute message attribute down -->
				<th>Price</th>
				<th>Add</th>
				<th>Edit</th>
				<th>Delete</th>
			</c:if>
			<%
			// }
			%>
		</tr>
		<%
		// for (Product product : products) {
		%>
		<c:forEach items="${products}" var="product">
			<tr>
				<td><c:out value="${product.id}" /></td><%-- using JSTL <td><%=product.getId()%></td> --%>
				<td>${product.name}</td><%-- using Expression Language EL <td><%=product.getName()%></td> --%>
				<td><c:out value="${product.category.name}" /></td><%-- <td><%=product.getCategory().getName()%></td> --%>

				<%
				// if (usernameOptional.isPresent()) {
				%>
				<c:if test="${usernameOptional.present}">
					<td><c:out value="${product.price}" /></td><%-- <td><%=product.getPrice()%></td> --%>
					<td><a
						href="${pageContext.request.contextPath}/cart/add?id=${product.id}">Add</a></td>
					<td><a
						href="<c:out value="${pageContext.request.contextPath}" />/product/form?id=<c:out value="${product.id}" />">Edit</a></td>
					<td><a
						href="<c:out value="${pageContext.request.contextPath}" />/product/delete?id=<c:out value="${product.id}" />"
						onclick="return confirm('Are you sure to delete this product?');">Delete</a></td>
					<%-- <td><a --%>
					<%--href="<%=request.getContextPath()%>/product/delete?id=<%=product.getId()"%> --%>
					<%--onclick="return confirm('Are you sure to delete this product?');"">Delte</a></td> --%>
					<%
					//}
					%>
				</c:if>

			</tr>
		</c:forEach>
		<%
		// }
		%>
	</table>

	<p><b>Message Application: </b>${applicationScope.message}<%-- <%=messageApplication%> --%></p>
	<p><b>Message Request: </b>${requestScope.message} <%-- <%=messageRequest%> --%></p>
	<p>
		Si el atributo message no estuviera repetido en <b>applicationScope</b> y <b>requestScope</b> podemos obviar el scope y colocar &#36;&#123;message&#125;<br/>
		lo mismo aplica si estuviera en el <b>sessionScope</b>.<br/>
		<b>applicationScope</b><br/>
		<b>requestScope</b><br/>
		<b>sessionScope</b>
	</p>
	<p>
		<a href="<%=request.getContextPath()%>/index.jsp">Back</a>
	</p>

<jsp:include page="layout/footer.jsp" />
