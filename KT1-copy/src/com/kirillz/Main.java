package com.kirillz;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
    private static final ArrayList<CUser> users = new ArrayList<>();

    private static void loadUsersExcel()
    {
        File file = new File("./files/Магазин.xlsx");
        try (XSSFWorkbook wb = new XSSFWorkbook(file))
        {
            Sheet sheet = wb.getSheet("Пользователи");
            int rows = sheet.getLastRowNum();
            Row row;
            Cell cell;
            //переменные для данных пользователя
            UUID id;
            String temp;
            String login;
            String name;
            String gender;
            LocalDate dateOfBirth;
            for (int i = 1; i <= rows; i++)
            {
                row = sheet.getRow(i);
                if (row == null)
                    continue;
                //id
                cell = row.getCell(0);
                temp = cell.getStringCellValue();
                id = UUID.fromString(temp);
                //login
                cell = row.getCell(1);
                login = cell.getStringCellValue();
                //name
                cell = row.getCell(2);
                name = cell.getStringCellValue();
                //gender
                cell = row.getCell(3);
                gender = cell.getStringCellValue();
                //birth date
                cell = row.getCell(4);
                dateOfBirth = cell.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                users.add(new CUser(id, login, name, gender, dateOfBirth));
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    private static void loadInfo()
    {
        loadUsersExcel();
    }

    public static void main(String[] args) {
        // write your code here
        loadInfo();
        for (int i = 0; i < users.size(); i++)
        {
            System.out.println(users.get(i).getName() + users.get(i).getId() + users.get(i).getLogin() + users.get(i).getGender());
        }
    }
}
