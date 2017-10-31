package com.zensar.afnls.util;

public class Queries {
	public static String getallemployeeBean = "select * from EmployeeBean";
	public static String getEmployeeFromCredentials="select e.employeeName as employeeName, d.designationName as designation, e.userId as userId, e.managerId as managerId, e.managerId2Up as managerId2Up, "
			+ "e.status as status from EmployeeBean e,designation d where userId= ? and  e.designation = d.designationId";
	public static String getAssetsAsPerEmployee = "select * from AssetBean where userId=? and fiscalYear=? and quarter=? and deleteStatus=0 order by quarter desc";
	public static String addAssetForEmployee="replace into AssetBean(projectName,currentHeadCount,growthCount,growthStatus,CiscoManagerName,CiscoManagerId,quarter,projectLocation,projectManager,programManager,deliveryHead,userId,createdDate,fiscalYear) "
			+ "values(?,?,?,?,?,?,?,?,?,?,?,?,curdate(),?)";
	public static String getAssetsAsPerEmployeeRequest="select * from AssetBean where userId=? and quarter=? and fiscalYear=? and deleteStatus!=1";
	public static String getAssetsAsPerEmployeeRequestYearly="select * from AssetBean where userId=? and fiscalYear=? and deleteStatus!=1";
	public static String deleteAssetForEmployee="update AssetBean set deleteStatus=1,createdDate=curdate() where projectId=?";
	public static String updateAssetForEmployee="update AssetBean set projectName=?,currentHeadCount=?,growthCount=?,growthStatus=?,CiscoManagerName=?,CiscoManagerId=?,quarter=?,projectLocation=? where projectId=?";
	public static String getUpdatedEmployee="select e.employeeName as employeeName, d.designationName as designation, e.userId as userId, e.managerId as managerId, e.managerId2Up as managerId2Up, "
			+ "e.status as status from EmployeeBean e,designation d where userId=? and e.status='A' and  e.designation = d.designationId";
	public static String getCurrentQuarter="select quarter from quarters where curdate() between startDate and endDate";
	public static String getCurrentfiscalYear="select fiscalYear from quarters where curdate() between startDate and endDate";
	public static String getQuarterForYear="SELECT quarter FROM quarters where STR_TO_DATE(?,'%m/%d/%Y') between startDate and endDate";
	public static String updateFiscalCalender="update assetmanagement.quarters set year=?,STR_TO_DATE(?,'%m/%d/%Y') where quarter=?";
	public static String getEmployeesUnderManager="select employeeId where managerUserId=?";
	public static String getLaptopsPerEmployee="select a.laptopId as laptopId, a.ownerName as ownerName, a.reason as reason,  "
												+ " b.RequestName as requestType, a.createdDate as createdDate, a.status as status, a.userId as userId,"
												+ " a.projectName as projectName, a.requestStatus as requestStatus, a.fromuserId as fromuserId, "
												+ "a.stock as stock, a.RemarksFromLA as RemarksFromLA  , e.employeeName as Employee ,a.add_info as addInfo"
												+" from LaptopBean a , alptoprequesttype b , employeebean e where a.userId=? and "
												+ " a.requestType = b.requestType "
												+ " and a.requestStatus!='C'  and e.userId = a.userId "
												+ " order by a.createddate desc";
	public static String addLaptopRequestForEmployee="insert into LaptopBean(ownerName,reason,requestType,createdDate,status,userId,projectName,stock,fromuserId,touserId) values(?,?,?,curdate(),?,?,?,?,?,?)";
	public static String addLaptopRequestNewHireForEmployee="insert into LaptopBean(ownerName,reason,requestType,createdDate,status,userId,projectName,stock,fromuserId,touserId) values(?,?,?,curdate(),?,?,?,?,?,?)";
	public static String deleteLaptopForEmployee="delete  from LaptopBean where laptopId=?";
	public static String getEmployeesForEmployee="select e.employeeName as employeeName, d.designationName as designation, e.userId as userId, e.managerId as managerId, e.managerId2Up as managerId2Up, "
			+ "e.status as status from EmployeeBean e,designation d where managerId=? and e.status='A' and  e.designation = d.designationId";
	public static String getAllEmployeesForPCO="select e.employeeName as employeeName, d.designationName as designation, e.userId as userId, e.managerId as managerId,e.managerId2Up as managerId2Up, e.status as status from EmployeeBean e,designation d where  e.designation = d.designationId and e.designation in(1,2,3,4,5,6)";
	public static String insertEmployee="insert into employeeBean(employeeName,designation,userId,managerId,managerId2Up,status) values(?,?,?,?,?,'A')";
	public static String getAssetBeanById="select * from assetBean where projectId=?";
	public static String addFreezeTime="insert into systemFreezeMaster(freezeYear,freezeQuarter,freezeDate) values(?,?,STR_TO_DATE(?,'%m/%d/%Y'))";
	public static String checkFreezeTimeForQuarter="select freezeDate from systemfreezemaster where freezeYear=? and freezeQuarter=?";
	public static String checkQuartersForYear="select count(*) from quarters where fiscalYear=?";
	public static String setQuarter1ForYear="insert into quarters(startDate,endDate,fiscalYear,quarter) values(STR_TO_DATE(?,'%m/%d/%Y'),STR_TO_DATE(?,'%m/%d/%Y'),?,1)";
	public static String setQuarter2ForYear="insert into quarters(startDate,endDate,fiscalYear,quarter) values(STR_TO_DATE(?,'%m/%d/%Y'),STR_TO_DATE(?,'%m/%d/%Y'),?,2)";
	public static String setQuarter3ForYear="insert into quarters(startDate,endDate,fiscalYear,quarter) values(STR_TO_DATE(?,'%m/%d/%Y'),STR_TO_DATE(?,'%m/%d/%Y'),?,3)";
	public static String setQuarter4ForYear="insert into quarters(startDate,endDate,fiscalYear,quarter) values(STR_TO_DATE(?,'%m/%d/%Y'),STR_TO_DATE(?,'%m/%d/%Y'),?,4)";
	public static String retrieveEmployeeById="select e.employeeName as employeeName, d.designationName as designation, e.userId as userId, e.managerId as managerId,e.managerId2Up as managerId2Up, e.status as status from EmployeeBean e,designation d where e.userId=? and e.designation = d.designationId;";
	public static String updateEmployee="update employeeBean set employeeName=?,designation=?,managerId=?,managerId2Up=? where userId=?";
	public static String deactivateEmployee="update employeeBean set status='I' where userId=?";
	public static String updatelaptopstatus="update laptopbean set requestStatus=? where laptopId=?";
	public static String updatelaptopstatus_MoreInfo="update laptopbean set requestStatus=? , add_info = ? where laptopId=?";
	public static String savefeedback="insert into feedback(employeeId,feedback) values(?,?)";
	public static String savefeedbackapp="insert into feedback(employeeId,appreciation) values(?,?)";
	
	public static String UPDATEQUARTER="UPDATE QUARTERS set startDate=STR_TO_DATE(?,'%m/%d/%Y'),endDate=STR_TO_DATE(?,'%m/%d/%Y') where quarter=? and fiscalYear=?";

	/*
	 * 




SELECT * FROM quarterupdates q where q.userId in (
select d.userId from employeebean d where managerId in ((SELECT e.userId as userId FROM employeebean e where designation =2)) and d.designation=1);
	 */
	
	
	public static String Closelaptoprequest="update laptopbean set requestStatus=? , RemarksFromLA=? where laptopId=?";
	
	public static String getLaptopsbylaptopid="select a.laptopId as laptopId, a.ownerName as ownerName, a.reason as reason, b.RequestName as requestType, 	"
			+ "				a.createdDate as createdDate, a.status as status, a.userId as userId, a.projectName as projectName,"
			+ "			 a.requestStatus as requestStatus, a.fromuserId as fromuserId, a.stock as stock, a.RemarksFromLA as RemarksFromLA ,a.add_info as addInfo,"
			+ "                          e.employeeName as Employee "+
			"    from LaptopBean a , alptoprequesttype b ,employeebean e where a.laptopId=? and a.requestType = b.requestType and e.userId = a.userId ";
	
	
	public static String getEmployeeId = "SELECT userId FROM laptopbean  where laptopid=? ";
	
	public static String maxLaptopId="SELECT max(laptopid) FROM laptopbean ";
	public static String addLaptopRequestMails="insert into laptopsmail(laptopId,mailData,MailName) values(?,?,?)";
	public static String getAllLaptops="select a.laptopId as laptopId, a.ownerName as ownerName, a.reason as reason, "
			+ "						b.RequestName as requestType, a.createdDate as createdDate, a.status as status,"
			+ "						 a.userId as userId, a.projectName as projectName, a.requestStatus as requestStatus,a.add_info as addInfo, "
			+ "						a.fromuserId as fromuserId, a.stock as stock, a.RemarksFromLA as RemarksFromLA ,  e.employeeName as Employee "
										+"  from LaptopBean a , alptoprequesttype b ,employeebean e  "
										+ " where a.requestStatus=? and a.requestType = b.requestType "
										+ " and  a.createdDate >= ? and e.userId = a.userId "
										+ " order by createddate desc";
	public static String getlaptopsMailbylaptopId="select * from laptopsmail where laptopId=?";
	public static String getLaptopasPerDesg = 
			" select l.laptopId as laptopId, l.ownerName as ownerName , l.reason as reason,l.add_info as addInfo,"
			+ " re.RequestName as requestType, l.createdDate as createdDate, l.status as status, l.userId, "
			+ "l.projectName, l.requestStatus, l.fromuserId, l.stock,"
			+ " l.RemarksFromLA ,a.employeeName as Employee from LaptopBean l , employeebean a ,alptoprequesttype re where "
			+"		(a.managerId = ? or a.managerId2Up=? or a.userId=? ) and "
			+ "     a.designation <= ? and l.userId = a.userId "
			+ "    and re.requestType = l.requestType and l.requestStatus!='C' order by createddate desc";
	public static String getAllLaptopsbyfilter=" select a.laptopId as laptopId, a.ownerName as ownerName, a.reason as reason,a.add_info as addInfo, "
			+ " b.RequestName as requestType, a.createdDate as createdDate, a.status as status, a.userId as userId, a.projectName as projectName, "
			+ " a.requestStatus as requestStatus, a.fromuserId as fromuserId, a.stock as stock,"
			+ "  a.RemarksFromLA as RemarksFromLA ,e.employeeName as Employee     "
			+"          from LaptopBean a , alptoprequesttype b , employeebean e  "
			+ "  where a.requestStatus=? and a.requestType = b.requestType and "
			+ "  a.createdDate >= ? and e.userId = a.userId  order by createddate desc";
	
	public static String getProjectIdsForAsset="select projectId from assetBean where projectname=(select projectname from assetBean where projectId=?)";
	public static String updateAllDeletingAssets="update AssetBean set deleteStatus=? where projectId =?";
	public static String getAssetBeanByName="select count(projectId) from assetBean where projectname=?";


	public static String getAssetsForDeliveryHeadYearly="SELECT * FROM assetbean a where userId in (select userId from employeeBean where managerId in (select userId from employeeBean where managerId=?)) and fiscalYear=? and deleteStatus!=1";
	public static String getAssetsForDeliveryHead="SELECT * FROM assetbean a where userId in (select userId from employeeBean where managerId in (select userId from employeeBean where managerId=?)) and quarter=? and fiscalYear=? and deleteStatus!=1";
	public static String getAssetsForProgramManagerYearly="select * from assetBean where userId in(select userId from employeebean where managerId=?) and fiscalYear=? and deleteStatus!=1";
	public static String getAssetsForProgramManager="select * from assetBean where userId in(select userId from employeebean where managerId=?) and quarter=? and fiscalYear=? and deleteStatus!=1";
	public static String getAssetsForProgramManagerwithProjectManageYearly="select * from assetBean where userId in(select userId from employeebean where managerId=? and userId=?) and fiscalYear=? and and deleteStatus!=1";
	public static String getAssetsForProgramManagerwithProjectManager="select * from assetBean where userId in(select userId from employeebean where managerId=? and userId=?) and quarter=? and fiscalYear=? and  deleteStatus!=1";
	public static String getAssetsForDeliveryHeadWithPGMYearly="SELECT * FROM assetbean a where userId in (select userId from employeeBean where managerId = (select userId from employeeBean where managerId=? and userId=?)) and fiscalYear=? and deleteStatus!=1";
	public static String getAssetsForDeliveryHeadWithPGM="SELECT * FROM assetbean a where userId in (select userId from employeeBean where managerId = (select userId from employeeBean where managerId=? and userId=?)) and quarter=? and fiscalYear=? and deleteStatus!=1";
	public static String getAssetsForDeliveryHeadWithPGMPMYearly="SELECT * FROM assetbean a where userId in (select userId from employeeBean where userId=?) and fiscalYear=? and deleteStatus!=1";
	public static String getAssetsForDeliveryHeadWithPGMPM="SELECT * FROM assetbean a where userId in (select userId from employeeBean where userId=?) and quarter=? and fiscalYear=? and deleteStatus!=1";
	public static String getCurrentfreezeDate="SELECT freezeDate FROM systemfreezemaster where freezeYear=? and FreezeQuarter=?";
	public static String getUserIdOfProjectId="select userId from  assetBean where projectId=?";
	public static String getITOList="select * from ito_team_cc_list";
	public static String delegateProjectManager="update assetBean set userId=?,projectManager=? where projectId=?";
	public static String getODCUserId="select userId from employeeBean where designation=4 and status='A'";
	public static String delegateProgramManager="update assetBean set userId=?,projectManager=?,programManager=? where projectId=?";
	public static String delegateDeliveryhead="update assetBean set userId=?,projectManager=?,programManager=?,deliveryHead=? where projectId=?"; 
	public static String checkAssetsForProjectManager="select count(projectId) from assetBean where userId=?";
	public static String checkQuarterDetails="select count(userId) from quarterupdates where userId=? and year=? and quarter=?";
	public static String getQuarterDetails="select status from quarterupdates where userId=? and year=? and quarter=?";
	public static String updateQuarterlyUpdates="update quarterupdates set status=? where userId=? and year=? and quarter=?";
	public static String addQuarterlyDetails="replace into quarterupdates(userId,year,quarter,status) values(?,?,?,?)";
	public static String getQuarterlyDetails="select status from quarterupdates where userId=? and year=? and quarter=?";
	public static String getValidatedEmployees="select e.userId,e.employeeName from employeeBean e where (e.designation =1 ||e.designation=2)";
	public static String getValidatedEmployeesFromMaster="select e.userId,e.employeeName,q.status from employeeBean e,quarterupdates q where (e.designation =1 ||e.designation=2) and e.userId=q.userId and year=? and quarter=?";
	public static String checkCountFreezeTimeForQuarter="select count(freezeDate) from systemfreezemaster where freezeYear=? and FreezeQuarter=?";
	public static String activateEmployee="update employeeBean set status='A' where userId=?";
	public static String getAssetsForODCHeadYearly="SELECT * FROM assetbean a where userId in(select userId from employeeBean where managerId in(select userId from employeeBean where managerId in (select userId from employeeBean where managerId in (select userId from employeeBean where userId=?)))) and fiscalYear=? and deleteStatus!=1";
	public static String getAssetsForODCHead="SELECT * FROM assetbean a where userId in(select userId from employeeBean where managerId in(select userId from employeeBean where managerId in (select userId from employeeBean where managerId in (select userId from employeeBean where userId=?)))) and quarter=? and fiscalYear=? and deleteStatus!=1";
	public static String getAllAssetsForODCHeadQuarYearly="SELECT * FROM assetbean a where userId in(select userId from employeeBean where managerId in(select userId from employeeBean where managerId in (select userId from employeeBean where managerId in (select userId from employeeBean)))) and quarter=? and fiscalYear=? and deleteStatus!=1";
	public static String insertMailerAlias="insert into ito_team_cc_list (mailer_list) values(?)";
	public static String deleteMailerAlias="delete from  ito_team_cc_list  where mailer_list = ?";
	public static String getlaptopStatus =  "select requestStatus from LaptopBean where laptopId = ? ";
	public static String getQuartersForYear="select * from quarters where fiscalYear in (?,?)";
	public static String getQuarterByQuarterId="select * from quarters where quarterId=?";
	
	public static String updateAssetbyId = "update AssetBean set growthStatus=?,growthCount=? where projectId=? ";
	public static String getvalidatedStatusforPGM = "  select e.userid ,e.employeeName ,q.status,q.pm_status " 
						+"	from employeebean e LEFT OUTER JOIN quarterupdates q on (q.year=? and q.quarter=? and q.userid=e.userid) " 
						+"	 where e.managerid in (SELECT e.userId FROM employeebean  e where  e.userid=?) ";
	
	public static String updateStatusforPGM = "update quarterupdates q set q.pm_status='Yes', q.status='validated'  where q.userId in ( " +
			"	select d.userId from employeebean d where managerId in ((SELECT e.userId as userId FROM employeebean e "+
		 "		where designation =2 and userId=?)) and d.designation=1 and q.year=? and q.quarter=?)";
	
public static String checkQuarterlyUpdates="select count(userId) from quarterupdates where quarter=? and year=? and userId=?";
public static String addPGMQuarterlyUpdates="replace into quarterupdates(userId,year,quarter,status,pm_status) values(?,?,?,'validated','Yes')";
public static String addPGMQuarterlyUpdates_invalidated="replace into quarterupdates(userId,year,quarter,status,pm_status) values(?,?,?,'invalidated','N')";

public static String getpmforpgm = "select d.userId,d.employeeName,'' from employeebean d where managerId in ((SELECT e.userId as userId FROM employeebean e " +
		 		" where designation =2 and userId=?))";
public static String getValidatedEmployeesforPGM="select e.userId,e.employeeName from employeeBean e where e.designation =1  ";
public static String getValidatedEmployeesFromMasterforPGM="select e.userId,e.employeeName,q.status from employeeBean e,quarterupdates q where e.designation =1 "
		+ " and e.userId=q.userId and year=? and quarter=? and e.userId =?";

public static String getEmployeesByDesignation="select employeeName,userId,managerId from employeebean where designation=?";
public static String retrieveAATURLS="select * from AAT";
public static String updateAATURL="update AAT set url=? where urlName=?";
public static String checkLaptopRequestInOpenState="select count(*) from laptopbean where ownerName=? and requestStatus in ('I','A','M')";
public static String getLaptopsProjectManager="select a.laptopId as laptopId, a.ownerName as ownerName, a.reason as reason,  "
		+ " b.RequestName as requestType, a.createdDate as createdDate, a.status as status, a.userId as userId,"
		+ " a.projectName as projectName, a.requestStatus as requestStatus, a.fromuserId as fromuserId, "
		+ "a.stock as stock, a.RemarksFromLA as RemarksFromLA  , e.employeeName as Employee "
		+" from LaptopBean a , alptoprequesttype b , employeebean e where a.userId=? and "
		+ " a.requestType = b.requestType "
		+ " and e.userId = a.userId "
		+ " order by a.createddate desc";

public static String getLaptopsProgramManager="select a.laptopId as laptopId, a.ownerName as ownerName, a.reason as reason,  "
		+ " b.RequestName as requestType, a.createdDate as createdDate, a.status as status, a.userId as userId,"
		+ " a.projectName as projectName, a.requestStatus as requestStatus, a.fromuserId as fromuserId, "
		+ "a.stock as stock, a.RemarksFromLA as RemarksFromLA  , e.employeeName as Employee "
		+" from LaptopBean a , alptoprequesttype b , employeebean e where a.userId in (select userId from employeebean where userId=?) and "
		+ " a.requestType = b.requestType "
		+ " and e.userId = a.userId "
		+ " order by a.createddate desc";
public static String getLaptopsDeliveryHead="select a.laptopId as laptopId, a.ownerName as ownerName, a.reason as reason,  "
		+ " b.RequestName as requestType, a.createdDate as createdDate, a.status as status, a.userId as userId,"
		+ " a.projectName as projectName, a.requestStatus as requestStatus, a.fromuserId as fromuserId, "
		+ "a.stock as stock, a.RemarksFromLA as RemarksFromLA  , e.employeeName as Employee "
		+" from LaptopBean a , alptoprequesttype b , employeebean e where a.userId in (select userId from employeeBean where userId in (select userId from employeebean where userId=?)) and "
		+ " a.requestType = b.requestType "
		+ " and e.userId = a.userId "
		+ " order by a.createddate desc";
public static String getLaptopsODCHead="select a.laptopId as laptopId, a.ownerName as ownerName, a.reason as reason,  "
		+ " b.RequestName as requestType, a.createdDate as createdDate, a.status as status, a.userId as userId,"
		+ " a.projectName as projectName, a.requestStatus as requestStatus, a.fromuserId as fromuserId, "
		+ "a.stock as stock, a.RemarksFromLA as RemarksFromLA  , e.employeeName as Employee "
		+" from LaptopBean a , alptoprequesttype b , employeebean e where "
		+ " a.requestType = b.requestType "
		+ " and e.userId = a.userId "
		+ " order by a.createddate desc";
public static String getLaptopsLPT="select a.laptopId as laptopId, a.ownerName as ownerName, a.reason as reason,  "
		+ " b.RequestName as requestType, a.createdDate as createdDate, a.status as status, a.userId as userId,"
		+ " a.projectName as projectName, a.requestStatus as requestStatus, a.fromuserId as fromuserId, "
		+ "a.stock as stock, a.RemarksFromLA as RemarksFromLA  , e.employeeName as Employee "
		+" from LaptopBean a , alptoprequesttype b , employeebean e where "
		+ " a.requestType = b.requestType "
		+ " and e.userId = a.userId "
		+ " order by a.createddate desc";
public static String checkODCHead="select count(*) from employeeBean where designation=4 and status='A'";
public static String getFreezeDates="select * from systemfreezemaster where freezeYear=?";
public static String updateSystemFreezeDate="update systemfreezemaster set freezeDate=STR_TO_DATE(?,'%m/%d/%Y') where freezeId=?";
public static String getSystemFreezeDate="select * from systemfreezemaster where freezeId=?";
public static String updateLaptopBeanReason="update laptopBean set reason=?,requestStatus='I' where laptopId=?";
}
