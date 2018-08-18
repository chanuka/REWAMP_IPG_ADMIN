/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.epic.epay.ipg.util.common;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;

/**
 *
 * @author badrika
 */
public class ExcelCommon {
    

    /**
     * gets XSSFWorkbook and return XSSFCellStyle cell object, witch formated to
     * bold font.
     *
     * @param workbook
     * @return XSSFCellStyle
     */
    public static CellStyle getFontBoldedCell(SXSSFWorkbook workbook) {
        Font font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    /**
     * gets XSSFWorkbook and return XSSFCellStyle cell object, witch formated to
     * underlined bold font.
     *
     * @param workbook
     * @return XSSFCellStyle
     */
    public static CellStyle getFontBoldedUnderlinedCell(SXSSFWorkbook workbook) {
        Font font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        font.setUnderline(XSSFFont.U_SINGLE);
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        return style;
    }

    /**
     * gets XSSFWorkbook and return XSSFCellStyle cell object, witch formated to
     * thin border(top, right, left, bottom) and bold font.
     *
     * @param workbook
     * @return XSSFCellStyle
     */
    public static CellStyle getColumnHeadeCell(SXSSFWorkbook workbook) {
        Font font = workbook.createFont();
        font.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);
        CellStyle style = workbook.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
        style.setFillPattern(XSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        return style;
    }

    /**
     * gets XSSFWorkbook and return XSSFCellStyle cell object, witch formated to
     * thin border(top, right, left, bottom).
     *
     * @param workbook
     * @return XSSFCellStyle
     */
    public static CellStyle getRowColumnCell(SXSSFWorkbook workbook) {
        CellStyle style = workbook.createCellStyle();
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        style.setBorderTop(XSSFCellStyle.BORDER_THIN);
        style.setBorderRight(XSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        style.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        return style;
    }

    /**
     * return XSSFCellSytyle witch contains the alignment according to the
     * parameter value ' short alignment ' contain and if XSSFCellStyle
     * parameter is null then it create new style with the alignment that came
     * with the alignment parameter. Parameter cellStyel is not mandatory and if
     * cellStyle not null then it clone and set the alignment. Both workbook and
     * alignment is mandatory.
     *
     * @param workbook
     * @param cellStyle
     * @param alignment
     * @return XSSFCellStyle
     */
    public static CellStyle getAligneCell(SXSSFWorkbook workbook, XSSFCellStyle cellStyle, short alignment) {
        CellStyle style = workbook.createCellStyle();
        if (cellStyle != null) {
            style.cloneStyleFrom(cellStyle);
        }
        style.setAlignment(alignment);
        return style;
    }
    
}

