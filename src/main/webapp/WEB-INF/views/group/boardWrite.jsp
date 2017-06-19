<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

	<!-- **********************************************************************************************************************************************************
    			  MAIN CONTENT
      *********************************************************************************************************************************************************** -->
	<!--main content start--> 
	<section id="main-content">
          <section class="wrapper">
          	<h3><i class="fa fa-angle-right"></i> 게시판</h3>
          	
          	<!-- BASIC FORM ELELEMNTS -->
          	<div class="row mt">
          		<div class="col-lg-12">
                  <div class="form-panel">
                  	  <h4 class="mb"><i class="fa fa-angle-right"></i> 게시글 작성</h4>
                      <form class="form-horizontal style-form" method="post" action='<c:url value="/group/${groupNo}/insertBoard"/>'>
                          <div class="form-group">
                              <label class="col-sm-2 col-sm-2 control-label">제목</label>
                              <div class="col-sm-10">
                                  <input type="text" class="form-control" name="title">
                              </div>
                          </div>
                          <div class="form-group">
                              <label class="col-lg-2 col-sm-2 control-label">작성자</label>
                              <div class="col-lg-10">
                                  <p class="form-control-static"><sec:authentication property="principal.username"/></p>
                              </div>
                          </div>
                          <div class="form-group">
                              <label class="col-sm-2 col-sm-2 control-label">내용</label>
                              <div class="col-sm-10">
                                  <textarea class="form-control" rows="15" maxlength="5000" name="contents" placeholder="최대 5000자 이하로 입력해주세요."></textarea>
                              </div>
                          </div>
                          <button type="submit" class="btn btn-primary btn-lg btn-block">작성 완료</button>
						  <button type="button" class="btn btn-default btn-lg btn-block" onclick="window.history.go(-1); return false;">취소</button>
                      </form>
                  </div>
          		</div><!-- col-lg-12-->      	
          	</div><!-- /row -->
		</section><!--/wrapper -->
      </section><!-- /MAIN CONTENT -->
