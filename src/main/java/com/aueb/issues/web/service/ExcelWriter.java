package com.aueb.issues.web.service;

import com.aueb.issues.model.entity.ApplicationEntity;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.*;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Service
public class ExcelWriter {
    public Resource generateMassActionFile(List<ApplicationEntity> dataRows) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet1");
        //style
        sheet.setColumnWidth(0, 6000);
        ((XSSFSheet) sheet).setColumnWidth(1, 4000);
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName("Arial");
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setFontHeightInPoints((short) 12);
        font.setBold(false);
        headerStyle.setFont(font);
        CellStyle style = workbook.createCellStyle();
        style.setWrapText(true);
        List<String> headers=getMassActionHeaders();
        //cell style for date formats
        CellStyle dateCellStyle = workbook.createCellStyle();
        CreationHelper creationHelper = workbook.getCreationHelper();
        dateCellStyle.setDataFormat(creationHelper.createDataFormat().getFormat("yyyy-MM-dd"));
        //header
        int i=0;
        Row header = ((XSSFSheet) sheet).createRow(0);
        for (String headerString:headers)
        {
            Cell headerCell = header.createCell(i);
            headerCell.setCellValue(headerString);
            headerCell.setCellStyle(headerStyle);
            i++;
        }
        //rows
        i=1;
        Cell cell;
        XSSFCell xssfCell;
        ZoneId zoneId = ZoneId.systemDefault();
        for(ApplicationEntity dataRow :dataRows) {
            Row row = ((XSSFSheet) sheet).createRow(i);


            cell = row.createCell(0);
            cell.setCellValue(dataRow.getId());
            cell.setCellStyle(style);

            cell = row.createCell(1);
            cell.setCellValue(dataRow.getTitle());
            cell.setCellStyle(style);

            xssfCell= (XSSFCell) row.createCell(2);
            xssfCell.setCellValue(dataRow.getCompletionDate()==null?null:
                    Date.from(dataRow.getCompletionDate().atZone(zoneId).toInstant()));
            xssfCell.setCellStyle(dateCellStyle);

            xssfCell= (XSSFCell) row.createCell(3);
            xssfCell.setCellValue(Date.from(dataRow.getCreateDate().atZone(zoneId).toInstant()));
            xssfCell.setCellStyle(dateCellStyle);

            cell = row.createCell(4);
            cell.setCellValue(dataRow.getDescription());
            cell.setCellStyle(style);

            xssfCell= (XSSFCell) row.createCell(5);
            xssfCell.setCellValue(dataRow.getDueDate()==null?null:
                    Date.from(dataRow.getDueDate().atZone(zoneId).toInstant()));
            xssfCell.setCellStyle(dateCellStyle);


            cell = row.createCell(6);
            cell.setCellValue(dataRow.getIssueType()==null?null:
                    dataRow.getIssueType().name());
            cell.setCellStyle(style);

            cell = row.createCell(7);
            cell.setCellValue(dataRow.getPriority().name());
            cell.setCellStyle(style);

            cell = row.createCell(8);
            cell.setCellValue(dataRow.getStatus().name());
            cell.setCellStyle(style);

            cell = row.createCell(9);
            cell.setCellValue(dataRow.getAssigneeTech()==null?null:
                    dataRow.getAssigneeTech().getFirstname()+ " "+
                    dataRow.getAssigneeTech().getLastname());
            cell.setCellStyle(style);

            cell = row.createCell(10);
            cell.setCellValue(dataRow.getCreationUser().getFirstname()+ " "+
                    dataRow.getCreationUser().getLastname());
            cell.setCellStyle(style);


            cell = row.createCell(11);
            cell.setCellValue(dataRow.getSite().getName());
            cell.setCellStyle(style);

            cell = row.createCell(12);
            cell.setCellValue(dataRow.getSite().getBuilding().getName());
            cell.setCellStyle(style);

            i++;
        }
//        return writeFile(workbook);
        return getInputStream(workbook);
    }
    private Resource getInputStream(Workbook workbook) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        ByteArrayInputStream inputStream = new ByteArrayInputStream(outputStream.toByteArray());
        return new InputStreamResource(inputStream);
    }
    private String writeFile(Workbook workbook) throws IOException {

        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

        FileOutputStream outputStream = new FileOutputStream(fileLocation);
        workbook.write(outputStream);
        workbook.close();

        return fileLocation;
    }
    private List<String> getMassActionHeaders(){
        List<String> headers=new ArrayList<>();
        headers.add("Αναγνωριστικό");
        headers.add("Τίτλος");
        headers.add("Ημερομηνία Ολοκλήρωσης");
        headers.add("Ημερομηνία Δημιουργίας");
        headers.add("Περιγραφή");
        headers.add("Ολοκήρωση Μέχρι");
        headers.add("Τύπος βλάβης");
        headers.add("Προτεραιότητα");
        headers.add("Κατάσταση");
        headers.add("Όνομα εντολοδόχου");
        headers.add("Όνομα δημιουργού");
        headers.add("Όνομα αίθουσας");
        headers.add("Όνομα κτηρίου");
        return headers;
    }
}
