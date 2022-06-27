package com.base.rest.utils;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.base.rest.constant.Constantes;
import com.base.rest.dtos.BaseDTO;
import com.base.rest.exceptions.POIException;

public final class POIUtils {
	
	private POIUtils() {
		
	}
	
	private static CellStyle getStyleDateTime(Workbook workbook, CreationHelper helper) {
		
		CellStyle cellStyleDateTime = workbook.createCellStyle();
		cellStyleDateTime.setDataFormat(helper.createDataFormat().getFormat(Constantes.DD_MM_YYYY_HH_MM_SS));
		return cellStyleDateTime;
	}
	
	@SuppressWarnings("rawtypes")
	public static byte[] getReportExcel(List<BaseDTO> listado, Enum[] tablaEnum, String titulo) {
		
		try {
			Workbook workbook = new XSSFWorkbook();
			CreationHelper helper = workbook.getCreationHelper();
			CellStyle cellStyleDateTime = getStyleDateTime(workbook, helper);

			Sheet sheet = workbook.createSheet(titulo);
			int contador = 0;
			Row header = sheet.createRow(contador);
			Cell headerCell = null;
			for (Enum e: tablaEnum) {
				Field f1 = e.getClass().getDeclaredField("titulo");
				f1.setAccessible(true);
				headerCell = header.createCell(contador++);
				headerCell.setCellValue((String) f1.get(e));
			}

			contador = 1;
			for (BaseDTO record: listado) {
				Row row = sheet.createRow(contador++);
				int contadorCell = 0;
				for (Enum e: tablaEnum) {
					Field nombreColumna = e.getClass().getDeclaredField("column");
					nombreColumna.setAccessible(true);
					Field valor = record.getClass().getDeclaredField((String) nombreColumna.get(e));
					valor.setAccessible(true);
					Cell cell = row.createCell(contadorCell++);
					setValorCelda(valor, cell, cellStyleDateTime, record);
				}
			}
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			workbook.write(baos);
			workbook.close();
			return baos.toByteArray();
		} catch (Exception e) {
			throw new POIException();
		}
		
	}

	private static void setValorCelda(Field valor, Cell cell, CellStyle cellStyleDateTime, BaseDTO record) throws IllegalArgumentException, IllegalAccessException {
		
		if (valor.getType() == Date.class) {
			cell.setCellStyle(cellStyleDateTime);
			cell.setCellValue((Date) valor.get(record));
		} else {
			cell.setCellValue((String) valor.get(record));
		}
	}
}
