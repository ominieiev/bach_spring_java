package edu.ntu.sau.javasb.service;

import edu.ntu.sau.javasb.model.User;
import lombok.NonNull;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public class Transforming {
    public static final double CIVIL_TAX = 0.18;
    public static final double MILITARY_TAX = 0.05;


    public byte[] transformExcel(InputStream inputStream) throws IOException {

        List<User> users = readUsersFromExcell(inputStream);

        Workbook resultBook = createBookWithUsers(users);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        resultBook.write(byteArrayOutputStream);

        return byteArrayOutputStream.toByteArray();

    }

    private List<User> readUsersFromExcell(InputStream inputStream) throws IOException {
        Workbook workbook = WorkbookFactory.create(inputStream);
        Sheet sheet = workbook.getSheetAt(0);
        List<User> users = new ArrayList<>();

        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            User user = new User(row.getCell(0).getStringCellValue(), (int)(row.getCell(1).getNumericCellValue()));
            users.add(user);
        }

        workbook.close();
        return users;
    }

    private Workbook createBookWithUsers(List<User> users) {
        Workbook resultBook = new XSSFWorkbook();
        Sheet resultSheet = resultBook.createSheet("users with taxes");

        createHederRow(resultSheet);

        int i = 1;
        for (User user : users) {
            Row row = resultSheet.createRow(i);
            row.createCell(0).setCellValue(user.getName());
            row.createCell(1).setCellValue(user.getSalary());
            row.createCell(2).setCellValue(Double.toString(user.getSalary() * CIVIL_TAX));
            row.createCell(3).setCellValue(Double.toString(user.getSalary() * MILITARY_TAX));
            i++;
        }
        return resultBook;
    }

    private void createHederRow(Sheet resultSheet) {
        Row headerRow = resultSheet.createRow(0);
        headerRow.createCell(0).setCellValue("name");
        headerRow.createCell(1).setCellValue("salary");
        headerRow.createCell(2).setCellValue("civil tax");
        headerRow.createCell(3).setCellValue("military tax");
    }

}
