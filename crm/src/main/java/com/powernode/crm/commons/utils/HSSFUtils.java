package com.powernode.crm.commons.utils;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Excel文件解析获取工具类
 */
public class HSSFUtils {
    /**
     * 获取指定的HSSFCell的值
     * @param cell
     * @return
     */
    public static String getCellValueByStr(HSSFCell cell){
        String str = "";
        if(cell.getCellType() == HSSFCell.CELL_TYPE_STRING){
            str = cell.getStringCellValue();
        }else if (cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC){
            str = cell.getNumericCellValue() + "";
        }else if (cell.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN){
            str = cell.getBooleanCellValue() + "";
        }else if (cell.getCellType() == HSSFCell.CELL_TYPE_FORMULA){
            str = cell.getCellFormula() + "";
        }else {
            str = "";
        }
        return str;
    }
}
