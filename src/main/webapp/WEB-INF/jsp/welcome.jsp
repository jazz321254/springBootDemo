<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html lang="en">
<head>
	<%@ include file="parts/meta.jsp" %>  
	<title>Home Page</title>
	<%@ include file="parts/header.jsp" %>  
</head>
<body>
	<%@ include file="parts/navbar.jsp" %>  
	
	<main role="main" class="container">
      <div class="jumbotron">
        <h1>${message}</h1>
        <p class="lead">${description}</p>
        <a class="btn btn-lg btn-primary" href="https://spring.io/" role="button">View Spring Official website »</a>
      </div>
    </main>
    
    <%@ include file="parts/footer.jsp" %>  
</body>
</html>
