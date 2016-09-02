package zj.test.util;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import zj.util.PoiUtils;

/**
 * 测试poiUtils .
 *
 * @author lzj 2014年12月19日
 */

public class TestMyPoiUtils {

    /**
     * 测试获取Workbook .
     */
    @Test
    public void testGetWorkbook() {
        Workbook book = PoiUtils.getClassPathWorkbook("123.xls");
        if (book == null) {
            System.out.println("null");
        } else {
            System.out.println("Not null");
            Sheet sheet = book.getSheetAt(0);
            String username = (String) PoiUtils.readCellValue(sheet, "A2");
            System.out.println(username);
        }
    }

}
