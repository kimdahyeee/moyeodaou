<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %> 

	<!-- **********************************************************************************************************************************************************
    			  MAIN CONTENT
      *********************************************************************************************************************************************************** -->
	<!--main content start-->
	<section id="main-content">
		<section class="wrapper">
			<!-- 세개가 한 줄!!-->
			<div class="row">
				<div class="col-md-12 mt">
					<div class="content-panel">
						<table class="table table-hover">
							<h4>
								<i class="fa fa-angle-right"></i> 게시판<i
									class="fa fa-angle-right more"><a href="<c:url value='/group/${groupNo}/boardWrite'/>">
										게시글 쓰기</a></i>
							</h4>
							<hr>
							<thead>
								<tr>
									<th>#</th>
									<th>제목</th>
									<th>작성자</th>
									<th>게시일</th>
								</tr>
							</thead>
							<tbody>
								<c:if test="${fn:length(allBoardList) > 0 }">
									<c:forEach items="${allBoardList}" var="allBoardLists"  begin="0" end="${fn:length(allBoardList)}" step="1" varStatus="status">
										<tr>
											<td>${allBoardList[status.index].rNum}</td>
											<td><a href="<c:url value='/group/${groupNo}/boardDetail/${allBoardList[status.index].board_no}'/>">${allBoardList[status.index].title}</a></td>
											<td>${allBoardList[status.index].member_name}</td>
											<td>${allBoardList[status.index].created_date}</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
							<div class="centered">
							<nav aria-label="Page navigation example">
								<ul class="pagination">
								<c:if test="${paginationInfo.firstPageNoOnPageList > 1}">
									<li class="page-item"><a class="page-link" href="<c:url value='/group/${groupNo}/boardList?page=${paginationInfo.firstPageNoOnPageList - 1}'/>">Previous</a></li>
								</c:if>
								<c:forEach var="i" begin="${paginationInfo.firstPageNoOnPageList}" end="${paginationInfo.lastPageNoOnPageList > paginationInfo.totalPageCount ? paginationInfo.totalPageCount : paginationInfo.lastPageNoOnPageList }" varStatus="status">
									<li class="page-item"><a class="page-link" href="<c:url value='/group/${groupNo}/boardList?page=${i}'/>">${i}</a></li>
								</c:forEach>
								<c:if test="${paginationInfo.totalPageCount > paginationInfo.lastPageNoOnPageList}">
									<li class="page-item"><a class="page-link" href="<c:url value='/group/${groupNo}/boardList?page=${paginationInfo.lastPageNoOnPageList + 1}'/>">Next</a></li>
								</c:if>
								</ul>
							</nav>
					    </div>
					</div>
					<!--/content-panel -->
				</div>
				<!-- /col-md-12 -->
			</div>
			<!-- row -->
		</section>
	</section>