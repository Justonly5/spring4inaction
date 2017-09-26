<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf" %>
<%@ page session="false" %>
<html>
  <head>
    <title>Spitter</title>
    <link rel="stylesheet" type="text/css" 
          href="<c:url value="/resources/style.css" />" >
  </head>
  <body>
    <h1>Register</h1>
    <form method="POST">
      First Name: <input type="text" name="firstName" /><br/>
      Last Name: <input type="text" name="lastName" /><br/>
      Email: <input type="email" name="email" /><br/>
      Username: <input type="text" name="username" /><br/>
      Password: <input type="password" name="password" /><br/>
      <input type="submit" value="Register" />
    </form>
    <%--
     Spring 标签实现.Model中必须有和commandName一致的对象
    	相对于标准的HTML标签，使用Spring的表单绑定标签能够带来一定的功能提升，在校验失败后，表单中会预先填充之前输入的值
     --%>
    <sf:form commandName="spitter" method="POST">
    	<sf:errors path="*" cssClass="errors"></sf:errors><br/>
    	First Name: <sf:input path="firstName"></sf:input>
    				<sf:errors path="firstName"></sf:errors><br/>
    	Last Name: <sf:input path="lastName"></sf:input>
    				<sf:errors path="lastName"></sf:errors><br/>
    	Email: <sf:input path="email" type="email"></sf:input>
    			<sf:errors path="email"></sf:errors><br/>
    	UserName: <sf:input path="username"></sf:input>
    			<sf:errors path="username"></sf:errors><br/>
    	Password: <sf:password path="password"></sf:password>
    			<sf:errors path="password"></sf:errors><br/>
    	<input type="submit" value="Register" />
    </sf:form>
  </body>
</html>
