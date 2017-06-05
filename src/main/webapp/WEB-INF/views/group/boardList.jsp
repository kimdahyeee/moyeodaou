<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui" %>
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
							<c:if test="${not empty paginationInfo}">
						        <ui:pagination paginationInfo = "${paginationInfo}" type="text" jsFunction="fn_search"/>
						    </c:if>
					    </div>
						    <input type="hidden" id="currentPageNo" name="currentPageNo"/>
					</div>
					<!--/content-panel -->
				</div>
				
				<form id="commonForm" name="commonForm">
				</form>
				<!-- /col-md-12 -->
			</div>
			<!-- row -->
		</section>
	</section>
	
<script type="text/javascript">
	$(document).ready(function(){
		fn_search();
	});
	
	function gfn_isNull(str) {
	    if (str == null) return true;
	    if (str == "NaN") return true;
	    if (new String(str).valueOf() == "undefined") return true;   
	    var chkStr = new String(str);
	    if( chkStr.valueOf() == "undefined" ) return true;
	    if (chkStr == null) return true;   
	    if (chkStr.toString().length == 0 ) return true;  
	    return false;
	}
	
	function ComSubmit(opt_formId) {
		this.formId = gfn_isNull(opt_formId) == true ? "commonForm" : opt_formId;
		this.url = "";
		
		if(this.formId == "commonForm"){
			$('#commonForm input[type="hidden"]').val(""); 
		}
		
		this.setUrl = function setUrl(url){
			this.url = url;
		};
		
		this.addParam = function addParam(key, value){
			$("#"+this.formId).append($("<input type='hidden' name='"+key+"' id='"+key+"' value='"+value+"' >"));
			$("#"+this.formId).append($("<input type='hidden' name='${_csrf.parameterName}' value='${_csrf.token}' >"));
		};
		
		this.submit = function submit(){
			var frm = $("#"+this.formId)[0];
			frm.action = this.url;
			frm.method = "post";
			frm.submit();	
		};
	}
	
	function fn_search(pageNo){
        var comSubmit = new ComSubmit();
        comSubmit.setUrl("<c:url value='/group/${groupNo}/boardList' />");
        comSubmit.addParam("currentPageNo", pageNo);
        comSubmit.submit();
    }
</script>