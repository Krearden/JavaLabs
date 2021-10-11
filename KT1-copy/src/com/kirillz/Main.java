package com.kirillz;

import java.io.File;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Main {
    private static final ArrayList<CUser> users = new ArrayList<>();
    private static final ArrayList<CProduct> products = new ArrayList<>();
    private static final ArrayList<COrder> orders = new ArrayList<>();

    //чтение пользователей
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

    //чтение товаров
    private static void loadProductsExcel()
    {
        File file = new File("./files/Магазин.xlsx");
        try (XSSFWorkbook wb = new XSSFWorkbook(file))
        {
            Sheet sheet = wb.getSheet("Товары");
            int rows = sheet.getLastRowNum();
            Row row;
            Cell cell;
            //переменные для данных товара
            UUID product_id;
            String temp;
            String product_name;
            int price;
            String category;
            for (int i = 1; i <= rows; i++)
            {
                row = sheet.getRow(i);
                if (row == null)
                    continue;
                //product_id
                cell = row.getCell(0);
                temp = cell.getStringCellValue();
                product_id = UUID.fromString(temp);
                //product_name
                cell = row.getCell(1);
                product_name = cell.getStringCellValue();
                //price
                cell = row.getCell(2);
                price = (int)cell.getNumericCellValue();
                //category
                cell = row.getCell(3);
                category = cell.getStringCellValue();
                products.add(new CProduct(product_id, product_name, price, category));
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    //чтение заказов
    private static void loadOrdersExcel()
    {
        File file = new File("./files/Магазин.xlsx");
        try (XSSFWorkbook wb = new XSSFWorkbook(file))
        {
            Sheet sheet = wb.getSheet("Покупки");
            int rows = sheet.getLastRowNum();
            Row row;
            Cell cell;
            //переменные для данных заказа
            UUID user_id;
            UUID product_id;
            String temp;
            LocalDateTime purchase_date;
            for (int i = 1; i <= rows; i++)
            {
                row = sheet.getRow(i);
                if (row == null)
                    continue;
                //user_id
                cell = row.getCell(0);
                temp = cell.getStringCellValue();
                user_id = UUID.fromString(temp);
                //product_id
                cell = row.getCell(1);
                temp = cell.getStringCellValue();
                product_id = UUID.fromString(temp);
                //purchase date and time
                cell = row.getCell(2);
                purchase_date = cell.getLocalDateTimeCellValue();
                orders.add(new COrder(user_id, product_id, purchase_date));
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    //загрузка информации из excel
    private static void loadInfo()
    {
        loadUsersExcel();
        loadProductsExcel();
        loadOrdersExcel();
    }

    //проверка, является ли дата пятницой
    private static boolean fridayCheck(LocalDateTime purchase_date)
    {
        DayOfWeek day = purchase_date.getDayOfWeek();
        return day == DayOfWeek.FRIDAY;
    }
    //функция для нахождения имени пользователя по id -- NEED FIX
    private static String getNameById(UUID user_id)
    {
        String answer_name = null;
       for (int i = 0; i < users.size(); i++)
       {
           if (users.get(i).getId().equals(user_id))
               answer_name = users.get(i).getName();
       }
       return answer_name;
    }
    //функция нахождения наименования товара по id -- NEED FIX
    private static String getProductNameById(UUID product_id)
    {
        String answer_product_name = null;
        for (int i = 0; i < products.size(); i++)
        {
            if (products.get(i).getId().equals(product_id))
            {
                answer_product_name = products.get(i).getProduct_name();
            }
        }
        return answer_product_name;
    }

    public static void main(String[] args) {
        // write your code here
        loadInfo();
        //проходимся по всем покупкам, находим те, которые были совершены в пт.
        boolean friday;
        UUID user_id;
        UUID product_id;
        String username;
        String product_name;
        for (int i = 0; i < orders.size(); i++)
        {
            LocalDateTime purchase_date = orders.get(i).getPurchase_date_time();
            friday = fridayCheck(purchase_date);
            if (friday)
            {
                user_id = orders.get(i).getUser_id();
                product_id = orders.get(i).getProduct_id();
                username = getNameById(user_id);
                product_name = getProductNameById(product_id);
                System.out.println("FRIDAY" + " " + user_id + " " + username + " " + product_name);
            }
        }
    }
}
