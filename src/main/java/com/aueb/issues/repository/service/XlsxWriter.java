package com.aueb.issues.repository.service;

import com.aueb.issues.model.entity.ApplicationEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@Slf4j
public class XlsxWriter {

    public void writeStatistics(List<ApplicationEntity> applications){
        //TODO: GET the applications from the base, and create an excell file that shows everything about them
        //TODO: PASS THE FILTERS BY PARAMETERS
        //style
        Workbook workbook = new XSSFWorkbook();

        Sheet sheet = workbook.createSheet("Persons");
        sheet.setColumnWidth(0, 6000);





        sheet.setColumnWidth(1, 4000);
        Row header = sheet.createRow(0);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setFontHeightInPoints((short) 16);
        font.setBold(true);
        headerStyle.setFont(font);

        //set header cells
        Cell headerCell = header.createCell(0);
        headerCell.setCellValue("Name");
        headerCell.setCellStyle(headerStyle);

        headerCell = header.createCell(1);
        headerCell.setCellValue("Age");
        headerCell.setCellStyle(headerStyle);


    }
    private void printFile(Workbook workbook){
        //TODO: Steve (me) will research za Best of way to return the file.
        //it's either the Output stram (see comment) or the Resource class.


        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(fileLocation);
        }catch (FileNotFoundException e) {
            log.error("Error finding outputStream");
            throw new RuntimeException(e);
        }
        try {
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            log.error("Error writing file/ closing stream");
            throw new RuntimeException(e);
        }
//        public class ExcelDownloadServlet extends HttpServlet {
//            protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//                response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
//                response.setHeader("Content-Disposition", "attachment; filename=example.xlsx");
//
//                try (OutputStream outputStream = response.getOutputStream()) {
//                    Workbook workbook = createExcelWorkbook(); // Your method to create the Excel workbook
//
//                    workbook.write(outputStream);
//                    workbook.close();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
    }
}
