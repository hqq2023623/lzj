package zj.util;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.IOException;

/**
 * poi工具类--基于poi3.11 .
 *
 * @author lzj 2015年1月14日
 */
public class PoiUtils {

    /**
     * 获取类路径的WorkBook .
     */
    public static Workbook getClassPathWorkbook(String path) {
        InputStream is;
        Workbook book = null;
        try {
            is = PoiUtils.class.getClassLoader().getResourceAsStream(path);
            if (path.endsWith(".xls")) {
                book = new HSSFWorkbook(is);
            } else {
                book = new SXSSFWorkbook(new XSSFWorkbook(is));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;
    }

    /**
     * 获取硬盘指定路径的WorkBook .
     */
    public static Workbook getFileSystemWorkbook(String path) {
        InputStream is;
        Workbook book = null;
        File file = new File(path);
        // 使用f.createNewFile();创建的文件无法被POI识别
        if (!file.exists() || file.length() < 512) {
            return null;
        }
        try {
            is = new FileInputStream(file);
            if (path.endsWith(".xls")) {
                book = new HSSFWorkbook(is);
            } else {
                book = new SXSSFWorkbook(new XSSFWorkbook(is));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return book;
    }

    /**
     * 将单元格的行列位置转换为行号和列号 .
     *
     * @param cellPosition 单元格位置,如:C2
     * @return 长度为2的数组，第1位为列号，第2位为行号
     */
    private static int[] convertToRowNumColumnNum(String cellPosition) {
        cellPosition = cellPosition.toUpperCase();
        char[] chars = cellPosition.toCharArray();
        int rowNum = 0;
        int columnNum = 0;
        for (char c : chars) {
            if ((c >= 'A' && c <= 'Z')) {
                columnNum = columnNum * 26 + ((int) c - 64);
            } else {
                rowNum = rowNum * 10 + new Integer(c + "");
            }
        }
        return new int[]{columnNum - 1, rowNum - 1};
    }

    /**
     * 读取一个单元格的值 .
     *
     * @param sheet        sheet
     * @param cellPosition 单元格位置,如:C2
     */
    public static Object readCellValue(Sheet sheet, String cellPosition) {
        int[] rowNumColumnNum = convertToRowNumColumnNum(cellPosition);
        int columnNum = rowNumColumnNum[0];
        int rowNum = rowNumColumnNum[1];
        Row row = sheet.getRow(rowNum);
        if (row != null) {
            Cell cell = row.getCell(columnNum);
            if (cell != null) {
                return getCellValue(cell);
            }
        }
        return null;
    }

    /**
     * 获取单元格中的值 .
     *
     * @param cell 单元格
     * @return 单元格中的值
     */
    private static Object getCellValue(Cell cell) {
        int type = cell.getCellType();
        switch (type) {
            case Cell.CELL_TYPE_STRING:
                return (Object) cell.getStringCellValue();
            case Cell.CELL_TYPE_NUMERIC:
                Double value = cell.getNumericCellValue();
                return (Object) (value.intValue());
            case Cell.CELL_TYPE_BOOLEAN:
                return (Object) cell.getBooleanCellValue();
            case Cell.CELL_TYPE_FORMULA:
                return (Object) cell.getArrayFormulaRange().formatAsString();
            case Cell.CELL_TYPE_BLANK:
                return (Object) "";
            default:
                return null;
        }
    }

    /**
     * 重命名一个sheet .
     *
     * @param sheetNum sheet的序号
     * @param newName  新的名称
     */
    public static void renameSheet(Workbook book, int sheetNum, String newName) {
        book.setSheetName(sheetNum, newName);
    }

    /**
     * 重命名一个sheet .
     *
     * @param oldName 旧的名称
     * @param newName 新的名称
     */
    public static void renameSheet(Workbook book, String oldName, String newName) {
        int sheetNum = book.getSheetIndex(oldName);
        renameSheet(book, sheetNum, newName);
    }

    /**
     * 删除一个sheet .
     *
     * @param sheetName sheet的名称
     */
    public static void removeSheet(Workbook book, String sheetName) {
        book.removeSheetAt(book.getSheetIndex(sheetName));
    }

    /**
     * 写入Excel文件并关闭 .
     */
    public static void writeAndClose(Workbook book, String path) {
        if (book != null) {
            try {
                FileOutputStream fileOutStream = new FileOutputStream(path);
                book.write(fileOutStream);
                if (fileOutStream != null) {
                    fileOutStream.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据sheeName写入一组值 .
     *
     * @param sheetName      写入的sheet的名称
     * @param fillRow        是写入行还是写入列
     * @param startRowNum    开始行号
     * @param startColumnNum 开始列号
     * @param contents       写入的内容数组
     */
    public static void writeArrayToExcel(Workbook book, String sheetName,
                                         boolean fillRow, int startRowNum, int startColumnNum, Object[] contents)
            throws Exception {
        Sheet sheet = book.getSheet(sheetName);
        doWriteArrayToExcel(sheet, fillRow, startRowNum, startColumnNum, contents);
    }

    /**
     * 根据指定sheet的下标写入一组值 .
     *
     * @param sheetNum       写入的sheet的编号
     * @param fillRow        是写入行还是写入列
     * @param startRowNum    开始行号
     * @param startColumnNum 开始列号
     * @param contents       写入的内容数组
     */
    public static void writeArrayToExcel(Workbook book, int sheetNum,
                                         boolean fillRow, int startRowNum, int startColumnNum, Object[] contents)
            throws Exception {
        Sheet sheet = book.getSheetAt(sheetNum);
        doWriteArrayToExcel(sheet, fillRow, startRowNum, startColumnNum, contents);
    }

    /**
     * 真正执行写入值 .
     */
    private static void doWriteArrayToExcel(Sheet sheet, boolean fillRow,
                                            int startRowNum, int startColumnNum, Object[] contents) throws Exception {
        for (int i = 0, length = contents.length; i < length; i++) {
            int rowNum;
            int columnNum;
            // 以行为单位写入
            if (fillRow) {
                rowNum = startRowNum;
                columnNum = startColumnNum + i;
            } else {
                // 　以列为单位写入
                rowNum = startRowNum + i;
                columnNum = startColumnNum;
            }
            doWriteToCell(sheet, rowNum, columnNum, convertString(contents[i]));
        }
    }

    /**
     * 向一个单元格写入值 .
     *
     * @param sheetNum  sheet的编号
     * @param rowNum    行号
     * @param columnNum 列号
     * @param value     写入的值
     */
    public static void writeToExcel(Workbook book, int sheetNum, int rowNum,
                                    int columnNum, Object value) throws Exception {
        Sheet sheet = book.getSheetAt(sheetNum);
        doWriteToCell(sheet, rowNum, columnNum, value);
    }

    /**
     * 向一个单元格写入值 .
     *
     * @param sheetName sheet的名称
     * @param columnNum 单元格的位置
     * @param value     写入的值
     */
    public static void writeToExcel(Workbook book, String sheetName, int rowNum,
                                    int columnNum, Object value) throws Exception {
        Sheet sheet = book.getSheet(sheetName);
        doWriteToCell(sheet, rowNum, columnNum, value);
    }

    /**
     * 向一个单元格写入值 .
     *
     * @param sheetNum     sheet的编号
     * @param columnRowNum 单元格的位置
     * @param value        写入的值
     */
    public static void writeToExcel(Workbook book, int sheetNum,
                                    String columnRowNum, Object value) throws Exception {
        Sheet sheet = book.getSheetAt(sheetNum);
        writeToCell(sheet, columnRowNum, value);
    }

    /**
     * 向一个单元格写入值 .
     *
     * @param sheetName    sheet的名称
     * @param columnRowNum 单元格的位置
     * @param value        写入的值
     */
    public static void writeToExcel(Workbook book, String sheetName,
                                    String columnRowNum, Object value) throws Exception {
        Sheet sheet = book.getSheet(sheetName);
        writeToCell(sheet, columnRowNum, value);
    }

    private static void writeToCell(Sheet sheet, String columnRowNum, Object value)
            throws Exception {
        int[] rowNumColumnNum = convertToRowNumColumnNum(columnRowNum);
        int rowNum = rowNumColumnNum[0];
        int columnNum = rowNumColumnNum[1];
        doWriteToCell(sheet, rowNum, columnNum, value);
    }

    /**
     * 执行写入值到指定单元格 .
     */
    private static void doWriteToCell(Sheet sheet, int rowNum, int columnNum,
                                      Object value) throws Exception {
        Row row = sheet.getRow(rowNum);
        Cell cell = row.getCell(columnNum);
        if (cell == null) {
            cell = row.createCell(columnNum);
        }
        cell.setCellValue(convertString(value));
    }

    /**
     * 把NULL转换为"" .
     */
    private static String convertString(Object value) {
        if (value == null) {
            return "";
        } else {
            return value.toString();
        }
    }

}
