<%-- <%@ page import = "java.util.*, web.jdbc.*" %>--%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>

<!DOCTYPE>
<html>
<head>
	<title>Student Tracker App</title> 
	<link type = "text/css" rel = "stylesheet" href = "css/style.css">
</head>
<%--List<Student> theStudents = (List<Student>) request.getAttribute("STUDENT_LIST"); --%>
<body>
	<div id = "wrapper">
		<div id = "header">
		<h2>Stevens Institute of Technology</h2>
		</div>
	</div>
	<div id = "container">	
		<div id = "content">
			<input type = "button" value = "Add Student"  onclick = "window.location.href ='add-student-form.jsp';
			return false;" class="add-student-button"/>
			<form action = "StudentControllerServlet" method = "GET"> 
				<input type = "hidden" name = "command" value = "SEARCH" />
				Search student: <input type = "text" name = "theSearchName" />
				<input type = "submit" class = "add-student-button" value = "Search" />
			</form>
			<table>
				<tr>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Email</th>
					<th>Action</th>
				</tr>
				<c:forEach var="tempStudent" items="${STUDENT_LIST}">
					<c:url var = "templink" value = "StudentControllerServlet">
						<c:param name = "command" value = "LOAD" />
						<c:param name = "studentId" value = "${tempStudent.id}" />
					</c:url>
					<c:url var = "deletelink" value = "StudentControllerServlet">
						<c:param name = "command" value = "DELETE" />
						<c:param name = "studentId" value = "${tempStudent.id}" />
					</c:url>
					<tr>
						<td>${tempStudent.firstName}</td>
						<td>${tempStudent.lastName}</td>
						<td>${tempStudent.email}</td>
						<td>
							<a href= ${templink}>Update</a>
							|
							<a href = ${deletelink} onclick = "if(!(confirm('Are you sure you want to delete this student?'))) return false">Delete</td>
						</td>
						
					</tr>
				</c:forEach>
					
				<%-- for (Student tempStudent : theStudents) { %>
				<tr>
					<td><%= tempStudent.getFirstName()%></td>
					<td><%= tempStudent.getLastName()%></td>
					<td><%= tempStudent.getEmail()%></td>
				</tr>
				<% }--%>
			</table>
		</div>
	</div>
</body>
</html>