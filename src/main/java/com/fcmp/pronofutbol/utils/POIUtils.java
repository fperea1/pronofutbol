package com.fcmp.pronofutbol.utils;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.fcmp.pronofutbol.constant.Constantes;
import com.fcmp.pronofutbol.dtos.BaseDTO;
import com.fcmp.pronofutbol.exceptions.POIException;

public final class POIUtils {
	
	private POIUtils() {}
	
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
				headerCell.setCellValue(I18nUtils.getMensaje((String) f1.get(e)));
				
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
		} else if (valor.getType() == Boolean.class) {
			Boolean bol = (Boolean)valor.get(record);
			cell.setCellValue((bol != null && bol) ? Constantes.SI : Constantes.NO);
		} else if (valor.getType() == Set.class) {
			@SuppressWarnings("unchecked")
			Set<BaseDTO> set = (HashSet<BaseDTO>)valor.get(record);
			String nombre = "";
			if (!set.isEmpty()) {
				for (BaseDTO b: set) {
					nombre += ", " +b.getNombre();
				}
			}
			nombre = nombre.replaceFirst(", ", "");
			cell.setCellValue(nombre);
		} else {
			cell.setCellValue((String) valor.get(record));
		}
	}
}
