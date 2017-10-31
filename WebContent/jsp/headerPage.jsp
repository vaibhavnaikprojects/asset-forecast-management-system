<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<div class="col-md-3 left_col">
	<div class="left_col scroll-view">
		<div class="navbar nav_title" style="border: 0;">
			<span class="site_title" id="afmsSpan">AFMS</span>
		</div>
		<div class="clearfix"></div>
		<!-- menu prile quick info -->
		<div class="profile">
			<div class="profile_pic">
				<img
					src="<c:url value="http://wwwin.cisco.com/dir/photo/std/${sessionScope.employee.userId}.jpg"/>"
					alt="..." class="img-circle profile_img">
			</div>
			<div class="profile_info">
				<span>Welcome,</span>
				<h2>${sessionScope.employee.employeeName}</h2>
				<c:choose>
					<c:when test="${sessionScope.employee.designation == 'LPT'}">
						<h5>(IT PMO)</h5>
					</c:when>
					<c:otherwise>
						<h5>(${ sessionScope.employee.designation})</h5>
					</c:otherwise>
				</c:choose>
			</div>
		</div>
		<br> <br> <br>
		<!-- /menu prile quick info -->
		<br />
		<!-- sidebar menu -->
		<div id="sidebar-menu" class="main_menu_side hidden-print main_menu">
			<div class="menu_section">
				<ul class="nav side-menu">
					<!--  <li><a><i class="fa fa-home"></i> Home</a> </li> -->
					<c:if test="${employee.designation ne 'LPT'}">
						<li><a href="asset.html"><i class="fa fa-thumb-tack"></i>
								Project Track </a></li>
					</c:if>
					<c:if test="${employee.designation ne 'PCO Team'}">
						<li><a href="laptop.html"><i class="fa fa-laptop"></i>
								Laptop Requests</a></li>
					</c:if>
					<c:if
						test="${employee.designation ne 'Project Manager' && employee.designation ne 'LPT'}">
						<li><a><i class="fa fa-user"></i> User Management
								<span class="fa fa-chevron-down"></span></a>
							<ul class="nav child_menu" style="display: none">
								<c:if test="${employee.designation ne 'PCO Team'}">
									<li><a href="userManagement.html?key=mantainUsers">Maintain
											Users</a></li>
									<li><a href="userManagement.html?key=delegateAssets">Delegate
											Projects</a></li>

								</c:if>
								<c:if test="${employee.designation eq 'PCO Team'}">
									<li><a href="userManagement.html?key=mantainUsers">Maintain
											Users</a></li>
									<li><a href="userManagement.html?key=delegateAssets">Delegate
											Projects</a></li>
									<li><a href="userManagement.html?key=deleteAssets">Delete
											Projects</a></li>
									<li><a
										href="userManagement.html?key=quarterlyAssetUpdates">Quarterly
											Project Updates</a></li>
									<li><a
										href="userManagement.html?key=maintaindeactivatedUsers">Maintain
											Deactivated Users</a></li>
									<li><a href="userManagement.html?key=uploadProjectTrack">Upload
											Project Track</a></li>
								</c:if>
							</ul></li>
					</c:if>
					<li><a href="aat.html"><i class="fa fa-bar-chart-o"></i>
							AAT</a></li>
					<c:if test="${employee.designation eq 'LPT'}">
						<li><a href="manageitolist.html"><i
								class="fa fa-user"></i>Manage ITO List</a></li>
					</c:if>
					<c:if test="${employee.designation eq 'PCO Team' }">
						<li><a><i class="fa fa-bar-chart-o"></i> Tools
								<span class="fa fa-chevron-down"></span></a>
							<ul class="nav child_menu" style="display: none">
								<li><a href="tools.html?key=maintainQuarters">Maintain Quarters</a></li>
								<li><a href="tools.html?key=maintainSystemFreezeDate">Maintain System Freeze-Date</a></li>
								<li><a href="tools.html?key=maintainAATURLS">Maintain AAT URLS</a></li>
							</ul></li>
					</c:if>
				</ul>
			</div>
			<div class="menu_section">

				<ul class="nav side-menu">
					<li><a href="feedback.html"><i class="fa fa-comment"></i>
							Feedback</a></li>
					<li><a><i class="fa fa-info fa-lg"></i>Extras <span
							class="fa fa-chevron-down"></span></a>
						<ul class="nav child_menu" style="display: none">
							<li><a href="help.html">Help</a></li>
							<li><a href="about.html">About</a></li>

						</ul></li>
				</ul>
			</div>


			<div class="menu_section">
				<ul class="nav side-menu">
					<li><a href="http://10.76.168.35:8080/" target="_blank"
						data-toggle="tooltip" data-placement="bottom"
						title="Redirects You To PCO Connect For VPN Process "><i
							class="fa fa-forward"></i>Goto PCO Connect</a></li>

					<li><a href="http://wwwin-tools.cisco.com/it/gdm/index.html"
						target="_blank" data-toggle="tooltip" data-placement="bottom"
						title="Redirects You To GDM Page For Cisco Related Approvals "><i
							class="fa fa-forward"></i>Goto GDM</a></li>
				</ul>
			</div>


		</div>
		<!-- /sidebar menu -->

		<!-- /menu footer buttons -->

		<!-- /menu footer buttons -->
	</div>
</div>

<!-- top navigation -->
<div class="top_nav">

	<div class="nav_menu">
		<nav class="" role="navigation">
			<div class="nav toggle">
				<a id="menu_toggle"><i class="fa fa-bars"></i></a>

			</div>
			<div class="nav title">
				<a style="position: absolute;">Asset Forecast And Management
					System</a> <br>
			</div>
			<ul class="nav navbar-nav navbar-right">
				<li class=""><a href="logout.html" data-toggle="tooltip"
					data-placement="top" title="Logout"> <span
						class="glyphicon glyphicon-off" aria-hidden="true"></span>
				</a></li>
				<!-- <li role="presentation" class="dropdown">
                                <a href="javascript:;" class="dropdown-toggle info-number" data-toggle="dropdown" aria-expanded="false">
                                    <i class="fa fa-envelope-o"></i>
                                    <span class="badge bg-green">6</span>
                                </a>
                                <ul id="menu1" class="dropdown-menu list-unstyled msg_list animated fadeInDown" role="menu">
                                    <li>
                                        <a>
                                            <span class="image">
                                        <img src="images/img.jpg" alt="Profile Image" />
                                    </span>
                                            <span>
                                        <span>John Smith</span>
                                            <span class="time">3 mins ago</span>
                                            </span>
                                            <span class="message">
                                        Film festivals used to be do-or-die moments for movie makers. They were where... 
                                    </span>
                                        </a>
                                    </li>
                                    <li>
                                        <div class="text-center">
                                            <a>
                                                <strong><a href="inbox.html">See All Alerts</strong>
                                                <i class="fa fa-angle-right"></i>
                                            </a>
                                        </div>
                                    </li>
                                </ul>
                            </li> -->

			</ul>
		</nav>
	</div>
</div>
<!-- /top navigation -->



