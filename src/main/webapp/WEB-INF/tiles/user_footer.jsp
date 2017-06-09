<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!-- js placed at the end of the document so the pages load faster -->
	<script type="text/javascript" src="<c:url value='resources/js/jquery.js' />"></script>
	<script type="text/javascript" src="<c:url value='resources/js/jquery-1.8.3.min.js' />"></script>
	<script type="text/javascript" src="<c:url value='resources/js/bootstrap.min.js'/>"></script>

	<!-- 가입, 로그인화면 -->
    <script type="text/javascript" src="<c:url value='resources/js/jquery.backstretch.min.js'/>"></script>
    <script>
        $.backstretch("<c:url value='resources/img/login-bg.jpg'/>", {speed: 500});
    </script>
	
	<script type="text/javascript" src="<c:url value='resources/js/common-scripts.js'/>"></script>
	<script type="text/javascript" src="<c:url value='resources/js/jquery.validate.js' />"></script>
	<script type="text/javascript" src="<c:url value='resources/js/custom.js' />"></script>

</body>
</html>