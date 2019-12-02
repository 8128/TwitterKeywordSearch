package com.tty.twsearch.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.tty.twsearch.mapper.TwitterMapper;
import com.tty.twsearch.pojo.TwitterData;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * @author :   Tianyi Tang
 * @date :   Created in 2019-11-30 18:01
 */
public class EasyExcelUtil {

    @Autowired
    private TwitterMapper twitterMapper;

    public String csvToXLSX(String fileName) {
        try {
            String xlsxFileAddress = "test.xlsx";
            XSSFWorkbook workBook = new XSSFWorkbook();
            XSSFSheet sheet = workBook.createSheet("sheet1");
            String currentLine=null;
            int RowNum=0;
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            while ((currentLine = br.readLine()) != null) {
                String str[] = currentLine.split(",");
                RowNum++;
                XSSFRow currentRow=sheet.createRow(RowNum);
                for(int i=0;i<str.length;i++){
                    currentRow.createCell(i).setCellValue(str[i]);
                }
            }

            FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
            workBook.write(fileOutputStream);
            fileOutputStream.close();
            return xlsxFileAddress;
        } catch (Exception ex) {
            System.out.println(ex.getMessage()+"Exception in try");
        }
        return null;
    }

    public void readExcel (String fileName) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        ExcelReader excelReader = EasyExcel.read(fileName, TwitterData.class, new TwitterDataListener(twitterMapper)).build();
        ReadSheet readSheet = EasyExcel.readSheet(0).build();
        excelReader.read(readSheet);
        // 这里千万别忘记关闭，读的时候会创建临时文件，到时磁盘会崩的
        excelReader.finish();
    }
}
