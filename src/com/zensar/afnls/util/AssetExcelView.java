package com.zensar.afnls.util;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractExcelView;
import com.zensar.afnls.beans.AssetBean;

public class AssetExcelView extends AbstractExcelView{

	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response)
					throws Exception {
		HSSFSheet sheet=workbook.createSheet("Asset Report");
		setExcelHeader(sheet);
		List<AssetBean> assetBeans=(List<AssetBean>) model.get("assetsOfEmployee");
		setExcelRows(sheet, assetBeans);
	}
	public void setExcelHeader(HSSFSheet excelSheet) {
		HSSFRow excelHeader = excelSheet.createRow(0);
		excelHeader.createCell(0).setCellValue("Project Name");
		excelHeader.createCell(1).setCellValue("Current Head Count");
		//excelHeader.createCell(2).setCellValue("Exptected Growth/Decline");
		excelHeader.createCell(2).setCellValue("Expected Growth");
		excelHeader.createCell(3).setCellValue("Growth/Decline");
		excelHeader.createCell(4).setCellValue("Cisco Manager Id");
		excelHeader.createCell(5).setCellValue("Cisco Manager Name");
		excelHeader.createCell(6).setCellValue("Quarter");
		excelHeader.createCell(7).setCellValue("Project Location");
		excelHeader.createCell(8).setCellValue("Project Manager");
		excelHeader.createCell(9).setCellValue("Program Manager");
		excelHeader.createCell(10).setCellValue("Delivery Head");
	}
	public void setExcelRows(HSSFSheet excelSheet, List<AssetBean> assetBeans){
		int record = 1;
		for (AssetBean assetBean : assetBeans) {
			HSSFRow excelRow = excelSheet.createRow(record++);
			excelRow.createCell(0).setCellValue(assetBean.getProjectName());
			excelRow.createCell(1).setCellValue(assetBean.getCurrentHeadCount());
			excelRow.createCell(2).setCellValue(assetBean.getGrowthCount());
			excelRow.createCell(3).setCellValue(assetBean.getGrowthStatus());
		//	excelRow.createCell(4).setCellValue(assetBean.getGrowthCount()+" "+assetBean.getGrowthStatus());
			excelRow.createCell(4).setCellValue(assetBean.getCiscoManagerId());
			excelRow.createCell(5).setCellValue(assetBean.getCiscoManagerName());
			excelRow.createCell(6).setCellValue(assetBean.getQuarter());
			excelRow.createCell(7).setCellValue(assetBean.getProjectLocation());
			excelRow.createCell(8).setCellValue(assetBean.getProjectManager());
			excelRow.createCell(9).setCellValue(assetBean.getProgramManager());
			excelRow.createCell(10).setCellValue(assetBean.getDeliveryHead());
		}
	}

}
