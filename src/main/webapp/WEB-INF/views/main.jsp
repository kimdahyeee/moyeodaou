<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

	<!-- **********************************************************************************************************************************************************
    			  MAIN CONTENT
      *********************************************************************************************************************************************************** -->
	<!--main content start--> 
	<section id="main-content-center">
              <div>
                  <div class="main-chart">
                  	<div class="row mtbox">
                  		<div class="col-lg-3 col-lg-offset-4 col-md-5 col-md-offset-3 col-sm-5 col-sm-offset-3 box0">
                  			<div class="box1" data-toggle="modal" data-target="#makeGroup">
					  			<span class="li_cloud"></span>
					  			<h3>+그룹 생성</h3>
                  			</div>
					  			<p>그룹을 자유롭게 생성해 주세요!</p>
                  		</div>
                  	</div><!-- /row mt -->	
                    <!-- 그룹 생성 modal -->
                      <div class="modal fade" id="makeGroup" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                          <div class="modal-dialog">
                            <div class="modal-content">
                              <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                                <h4 class="modal-title" id="myModalLabel">그룹 생성</h4>
                              </div>
                              <div class="modal-body">
                                <form class="form-horizontal style-form" method="post">
                                      <div class="form-group">
                                          <label class="col-sm-2 col-sm-2 control-label">그룹명</label>
                                          <div class="col-sm-10">
                                              <input type="text" class="form-control">
                                          </div>
                                      </div>
                                    <div class="form-group">
                                          <label class="col-sm-2 col-sm-2 control-label">그룹 소개</label>
                                          <div class="col-sm-10">
                                              <input type="text" class="form-control" placeholder="50자 내로 입력해주세요!">
                                          </div>
                                      </div>
                                    <div class="form-group">
                                          <label class="col-sm-2 col-sm-2 control-label">이미지</label>
                                          <div class="col-sm-10">
                                              <button type="button" class="btn btn-theme02"><i class="fa fa-check"></i> 이미지 업로드</button>
                                          </div>
                                      </div>
                                    <div class="centered">
                                      <button type="submit" class="btn btn-primary">생성</button>
                                      <button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
                                    </div>
                                  </form>
                              </div>
                            </div>
                          </div>
                        </div> 
                      
                      <div class="row mt">
                      <!-- SERVER STATUS PANELS -->
                      	<div class="col-md-4 col-sm-4 mb">
                            <a href="<c:url value='/groupMain'/>">
                                <div class="white-panel pn" >
                                    <div class="white-header">
                                        <h5>그룹명</h5>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-6 col-xs-6 goleft">
                                            <p><i class="fa fa-heart"></i> 인원 수</p>
                                        </div>
                                        <div class="col-sm-6 col-xs-6"></div>
                                    </div>
                                    <div class="centered">
                                            <img src="<c:url value='resources/img/product.png'/>" width="120">
                                    </div>
                                </div>
                            </a>
                      	</div><!-- /col-md-4-->
                      	<div class="col-md-4 col-sm-4 mb">
                            <a href="<c:url value='/groupMain'/>">
                                <div class="white-panel pn" >
                                    <div class="white-header">
                                        <h5>그룹명</h5>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-6 col-xs-6 goleft">
                                            <p><i class="fa fa-heart"></i> 인원 수</p>
                                        </div>
                                        <div class="col-sm-6 col-xs-6"></div>
                                    </div>
                                    <div class="centered">
                                            <img src="<c:url value='resources/img/product.png'/>" width="120">
                                    </div>
                                </div>
                            </a>
                      	</div><!-- /col-md-4-->
                          <div class="col-md-4 col-sm-4 mb">
                      		<a href="<c:url value='/groupMain'/>">
                                <div class="white-panel pn">
                                    <div class="white-header">
                                        <h5>그룹명</h5>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-6 col-xs-6 goleft">
                                            <p><i class="fa fa-heart"></i> 인원 수</p>
                                        </div>
                                        <div class="col-sm-6 col-xs-6"></div>
                                    </div>
                                    <div class="centered">
                                            <img src="<c:url value='resources/img/product.png'/>" width="120">
                                    </div>
                                </div>
                            </a>
                      	</div>
                        <div class="col-md-4 col-sm-4 mb">
                      		<a href="<c:url value='/groupMain'/>">
                                <div class="white-panel pn">
                                    <div class="white-header">
                                        <h5>그룹명</h5>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-6 col-xs-6 goleft">
                                            <p><i class="fa fa-heart"></i> 인원 수</p>
                                        </div>
                                        <div class="col-sm-6 col-xs-6"></div>
                                    </div>
                                    <div class="centered">
                                            <img src="<c:url value='resources/img/product.png'/>" width="120">
                                    </div>
                                </div>
                            </a>
                        </div><!-- /row -->
                  </div><!-- /col-lg-9 END SECTION MIDDLE -->
              </div>
          </div>
      </section><!-- /MAIN CONTENT -->
	