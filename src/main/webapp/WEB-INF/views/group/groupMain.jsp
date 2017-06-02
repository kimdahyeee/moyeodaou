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
				<div class="col-md-9 mt">
					<div class="content-panel">
						<table class="table table-hover">
							<h4>
								<i class="fa fa-angle-right"></i> 게시판
								<i class="fa fa-angle-right more"><a href="<c:url value='/boardWrite'/>">게시글 쓰기</a></i> 
								<i class="fa fa-angle-right more"><a href="<c:url value='/boardList'/>"> 더보기</a></i>
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
								<c:if test="${fn:length(allMainBoardList) > 0 }">
									<c:forEach var="allMainBoardLists" items="${allMainBoardList}" begin="0" end="${fn:length(allMainBoardList)}" step="1" varStatus="status">
										<tr>
											<td>${allMainBoardList[status.index].rNum}</td>
											<td><a href="<c:url value='/boardDetail/${allMainBoardList[status.index].board_no}'/>">${allMainBoardList[status.index].title}</a></td>
											<td>${allMainBoardList[status.index].member_name}</td>
											<td>${allMainBoardList[status.index].created_date}</td>
										</tr>
									</c:forEach>
								</c:if>
							</tbody>
						</table>
					</div>

					<div class="row mt">
						<!-- SERVER STATUS PANELS -->
						<!-- /col-md-4-->
						<div class="col-lg-4 col-md-4 col-sm-4 desc">
							<div class="content-panel pn">
								<div id="profile-01">
									<h3></h3>
								</div>
								<div class="profile-01 centered">
									<!--  <p>추가</p>	-->
									<a data-toggle="modal" data-target="#fileUpload" href="#">
					                    	<span>추가</span>
					                </a>
								</div>
								<div class="centered">
									<h6>
										<i class="fa fa-heart"></i><br />게시물을 추가해 주세요!
									</h6>
								</div>
							</div>
							<!--/content-panel -->
						</div>
						<!--/col-md-4 -->
						<c:if test="${fn:length(sharing_list) > 0}"> 
							<c:forEach items="${sharing_list}" var="list" step="1" varStatus="status">
							<!--  <form action="<c:url value='/fileDownload?${_csrf.parameterName}=${_csrf.token}'/>" id='fileDownload' name="fileDownload" method="post">	-->
									<div class="col-lg-4 col-md-4 col-sm-4 col-xs-12 desc">
										<div class="project-wrapper" data-toggle="dropdown">
											<div class="project">
												<div class="photo-wrapper">
													<div class="white-header">
														<h5>${sharing_list[status.index].file_name}</h5>
														<h5>${sharing_list[status.index].member_no}</h5>
														<h5>${sharing_list[status.index].group_file_no}</h5>
													</div>
													<div class="photo">
														<a class="fancybox" href="">
															<img class="img-responsive" src="<c:url value='resources/img/gongU.jpg' />" alt="">
														</a>
													</div>
													<div class="overlay"></div>
												</div>
											</div>
										</div>
										<input type="hidden" id="fno" name="fno" value="${sharing_list[status.index].group_file_no}">	
										<ul class="dropdown-menu" role="menu">
											<li><a href="#">미리보기</a></li>								
											<li><a href="<c:url value='/fileDownload?fno=${sharing_list[status.index].group_file_no}&=${_csrf.parameterName}&=${_csrf.token}'/>">다운로드</a></li>
											<li><a href="#">삭제</a></li>
										</ul>
									</div>
									<br/>
								<!--  </form> -->
							</c:forEach>
						</c:if>
						<!-- col-lg-4 -->
					</div>
					<!-- 파일 업로드 모달 창 -->
					<div class="modal fade" id="fileUpload" tabindex="-1"
					role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content팀">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
								<h4 class="modal-title" id="myModalLabel">파일 업로드</h4>
							</div>
							<div class="modal-body">
							</div>
							<div class="modal-footer">
								<!--  
								<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
								<button type="button" class="btn btn-primary">확인
								</button>	-->
									<a href="#this"	id="fileUpload2"></a>
									<form action="<c:url value='/fileUpload?${_csrf.parameterName}=${_csrf.token}'/>" id="fileUpload" name="fileUpload" method="post" enctype="multipart/form-data">
										<input type="file" name="testFile" required="required">		
										<input type="submit" value="완료">
									</form>
									<script>
										console.log($("form#fileUpload"))
									</script>
									
							</div>
						</div>
					</div>
				</div>
					
					<!-- /row -->

				</div>
				<!-- /col-lg-9 END SECTION MIDDLE -->


				<!-- **********************************************************************************************************************************************************
     RIGHT SIDEBAR CONTENT
     *********************************************************************************************************************************************************** -->
				<div class="col-lg-3 ds">

					<!--COMPLETED ACTIONS DONUTS CHART-->
					<h3>회의 가능 시간</h3>
					<div class="mb">
						<div class="white-panel desc donut-chart">
							<div class="row">
								<div class="col-sm-6 col-xs-6 goleft">
									<p>
										<i class="fa fa-database"></i> 50%
									</p>
								</div>
							</div>
							<canvas id="serverstatus01" height="120" width="120"></canvas>
							<script>
								var doughnutData = [
										{
											value: 50,
											color:"#68dff0"
										},
										{
											value : 50,
											color : "#fdfdfd"
										}
									];
									var myDoughnut = new Chart(document.getElementById("serverstatus01").getContext("2d")).Doughnut(doughnutData);
							</script>
						</div>
						<!--/grey-panel -->
					</div>

					<!-- USERS ONLINE SECTION -->
					<h3>채팅</h3>
					<!-- First Member -->
					<div class="desc">
						<div class="thumb">
							<img class="img-circle" src="<c:url value='resources/img/ui-divya.jpg'/>" width="35px" height="35px" align="">
						</div>
						<div class="details">
							<p>
								<a href="#">DIVYA MANIAN</a><br />
								<muted>Available</muted>
							</p>
						</div>
					</div>
					<!-- Second Member -->
					<div class="desc">
						<div class="thumb">
							<img class="img-circle" src="<c:url value='resources/img/ui-sherman.jpg'/>" width="35px" height="35px" align="">
						</div>
						<div class="details">
							<p>
								<a href="#">DJ SHERMAN</a><br />
								<muted>I am Busy</muted>
							</p>
						</div>
					</div>
					<!-- Third Member -->
					<div class="desc">
						<div class="thumb">
							<img class="img-circle" src="<c:url value='resources/img/ui-danro.jpg'/>" width="35px" height="35px" align="">		
						</div>
						<div class="details">
							<p>
								<a href="#">DAN ROGERS</a><br />
								<muted>Available</muted>
							</p>
						</div>
					</div>
					<!-- Fourth Member -->
					<div class="desc">
						<div class="thumb">
							<img class="img-circle" src="<c:url value='resources/img/ui-zac.jpg'/>" width="35px" height="35px" align="">		
						</div>
						<div class="details">
							<p>
								<a href="#">Zac Sniders</a><br />
								<muted>Available</muted>
							</p>
						</div>
					</div>
					<!-- Fifth Member -->
					<div class="desc">
						<div class="thumb">
							<img class="img-circle" src="<c:url value='resources/img/ui-sam.jpg'/>" width="35px" height="35px" align="">		
						</div>
						<div class="details">
							<p>
								<a href="#">Marcel Newman</a><br />
								<muted>Available</muted>
							</p>
						</div>
					</div>
				</div>
				<!-- /col-lg-3 -->
			</div>
			<!-- /row -->
		</section>
	</section>
