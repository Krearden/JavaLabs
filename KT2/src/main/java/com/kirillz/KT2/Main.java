package com.kirillz.KT2;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

import com.kirillz.KT2.dao.CDAOOrders;
import com.kirillz.KT2.dao.CDAOProducts;
import com.kirillz.KT2.dao.CDAOUsers;
import com.kirillz.KT2.model.COrder;
import com.kirillz.KT2.model.CProduct;
import com.kirillz.KT2.model.CUser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.wp.usermodel.HeaderFooterType;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;
import java.util.List;
import com.kirillz.KT2.config.CHibernateConfig;
import org.hibernate.Session;

public class Main {
    private static final ArrayList<CUser> users = new ArrayList<>();
    private static final ArrayList<CProduct> products = new ArrayList<>();
    private static final ArrayList<COrder> orders = new ArrayList<>();

    private List<COrder> local_orders;
    private List<CProduct> local_products;

//
//
//    //чтение пользователей
//    private static void loadUsersExcel()
//    {
//        File file = new File("./files/Магазин.xlsx");
//        try (XSSFWorkbook wb = new XSSFWorkbook(file))
//        {
//            Sheet sheet = wb.getSheet("Пользователи");
//            int rows = sheet.getLastRowNum();
//            Row row;
//            Cell cell;
//            //переменные для данных пользователя
//            UUID id;
//            String temp;
//            String login;
//            String name;
//            String gender;
//            LocalDate dateOfBirth;
//            for (int i = 1; i <= rows; i++)
//            {
//                row = sheet.getRow(i);
//                if (row == null)
//                    continue;
//                //id
//                cell = row.getCell(0);
//                temp = cell.getStringCellValue();
//                id = UUID.fromString(temp);
//                //login
//                cell = row.getCell(1);
//                login = cell.getStringCellValue();
//                //name
//                cell = row.getCell(2);
//                name = cell.getStringCellValue();
//                //gender
//                cell = row.getCell(3);
//                gender = cell.getStringCellValue();
//                if (Objects.equals(gender, "м"))
//                    gender = "Male";
//                else if (Objects.equals(gender, "ж"))
//                    gender = "Female";
//                //birth date
//                cell = row.getCell(4);
//                dateOfBirth = cell.getDateCellValue().toInstant()
//                        .atZone(ZoneId.systemDefault())
//                        .toLocalDate();
//                users.add(new CUser(id, login, name, gender, dateOfBirth));
//            }
//        } catch (Exception e) {e.printStackTrace();}
//    }
//
//    //чтение товаров
//    private static void loadProductsExcel()
//    {
//        File file = new File("./files/Магазин.xlsx");
//        try (XSSFWorkbook wb = new XSSFWorkbook(file))
//        {
//            Sheet sheet = wb.getSheet("Товары");
//            int rows = sheet.getLastRowNum();
//            Row row;
//            Cell cell;
//            //переменные для данных товара
//            UUID product_id;
//            String temp;
//            String product_name;
//            int price;
//            String category;
//            for (int i = 1; i <= rows; i++)
//            {
//                row = sheet.getRow(i);
//                if (row == null)
//                    continue;
//                //product_id
//                cell = row.getCell(0);
//                temp = cell.getStringCellValue();
//                product_id = UUID.fromString(temp);
//                //product_name
//                cell = row.getCell(1);
//                product_name = cell.getStringCellValue();
//                //price
//                cell = row.getCell(2);
//                price = (int)cell.getNumericCellValue();
//                //category
//                cell = row.getCell(3);
//                category = cell.getStringCellValue();
//                products.add(new CProduct(product_id, product_name, price, category));
//            }
//        } catch (Exception e) {e.printStackTrace();}
//    }
//
//    //чтение заказов
//    private static void loadOrdersExcel()
//    {
//        File file = new File("./files/Магазин.xlsx");
//        try (XSSFWorkbook wb = new XSSFWorkbook(file))
//        {
//            Sheet sheet = wb.getSheet("Покупки");
//            int rows = sheet.getLastRowNum();
//            Row row;
//            Cell cell;
//            //переменные для данных заказа
//            UUID user_id;
//            UUID product_id;
//            String temp;
//            LocalDateTime purchase_date_time;
//            LocalDate purchase_date;
//            for (int i = 1; i <= rows; i++)
//            {
//                row = sheet.getRow(i);
//                if (row == null)
//                    continue;
//                //user_id
//                cell = row.getCell(0);
//                temp = cell.getStringCellValue();
//                user_id = UUID.fromString(temp);
//                //product_id
//                cell = row.getCell(1);
//                temp = cell.getStringCellValue();
//                product_id = UUID.fromString(temp);
//                //purchase date and time
//                cell = row.getCell(2);
//                purchase_date_time = cell.getLocalDateTimeCellValue();
//                purchase_date = purchase_date_time.toLocalDate();
//                orders.add(new COrder(product_id, getCUser_by_id(user_id), purchase_date));
//            }
//        } catch (Exception e) {e.printStackTrace();}
//    }
//
//    //загрузка информации из excel
//    private static void loadInfo()
//    {
//        loadUsersExcel();
//        loadProductsExcel();
//        loadOrdersExcel();
//    }
//
//    //проверка, является ли дата пятницой
//    private static boolean fridayCheck(LocalDate purchase_date)
//    {
//        DayOfWeek day = purchase_date.getDayOfWeek();
//        return day == DayOfWeek.FRIDAY;
//    }
//    //функция для нахождения имени пользователя по id
//    private static String getNameById(UUID user_id)
//    {
//        String answer_name = null;
//       for (int i = 0; i < users.size(); i++)
//       {
//           if (users.get(i).getId().equals(user_id))
//               answer_name = users.get(i).getName();
//       }
//       return answer_name;
//    }
//    //возвращает пользователя по его UUID
//    private static CUser getCUser_by_id(UUID user_id)
//    {
//        CUser answer_cUser = new CUser();
//        for (int i = 0; i < users.size(); i++)
//        {
//            if (users.get(i).getId().equals(user_id))
//                answer_cUser = users.get(i);
//        }
//        return answer_cUser;
//    }
//    //функция нахождения наименования товара по id
//    private static String getProductNameById(UUID product_id)
//    {
//        String answer_product_name = null;
//        for (int i = 0; i < products.size(); i++)
//        {
//            if (products.get(i).getId().equals(product_id))
//            {
//                answer_product_name = products.get(i).getProduct_name();
//            }
//        }
//        return answer_product_name;
//    }
//
//    //функция записи результатов работы программы в WORD файл
////    private static void createWord()
////    {
////        boolean friday;
////        UUID user_id;
////        UUID product_id;
////        String username;
////        String product_name;
////        try (XWPFDocument document = new XWPFDocument())
////        {
////            //хедер
////            XWPFHeader head = document.createHeader(HeaderFooterType.DEFAULT);
////            head.createParagraph()
////                    .createRun()
////                    .setText("КТ-1 Java; Выполнил Кирилл Запорожченко (2 курс, Физфак, ПМИ, ФЗ-12)");
////            //заголовок документа
////            XWPFParagraph paragraph = document.createParagraph();
////            paragraph.setAlignment(ParagraphAlignment.CENTER);
////            XWPFRun run = paragraph.createRun();
////            run.setBold(true);
////            run.setFontFamily("Courier");
////            run.setFontSize(15);
////            run.setText("\nНиже представлены пользователи, которые совершили покупки в пятницу\n");
////            //создаем табцицу
////            XWPFTable table = document.createTable();
////            //первая строка таблицы с заголовками столбцов
////            XWPFTableRow tableRowOne = table.getRow(0);
////            tableRowOne.getCell(0).setText("Имя");
////            tableRowOne.addNewTableCell().setText("Пользователь ИД");
////            tableRowOne.addNewTableCell().setText("Наименование");
////            tableRowOne.addNewTableCell().setText("Продукт ИД");
////            //цикл - проходимся по заказам и записываем в таблицу те, которые были совершены в ПТ
////            for (int i = 0; i < orders.size(); i++)
////            {
////                LocalDate purchase_date = orders.get(i).getPurchase_date();
////                friday = fridayCheck(purchase_date);
////                if (friday)
////                {
////                    user_id = orders.get(i).getUser_id();
////                    product_id = orders.get(i).getProduct_id();
////                    username = getNameById(user_id);
////                    product_name = getProductNameById(product_id);
////                    System.out.println("FRIDAY" + " " + user_id + " " + username + " " + product_name);
////                    //создание и запись информации в новую строку таблицы
////                    XWPFTableRow tableRow = table.createRow();
////                    tableRow.getCell(0).setText(username);
////                    tableRow.addNewTableCell().setText(user_id.toString());
////                    tableRow.addNewTableCell().setText(product_name);
////                    tableRow.addNewTableCell().setText(orders.get(i).getProduct_id().toString());
////                }
////            }
////
////            try (FileOutputStream out = new FileOutputStream("friday_users_report.docx")) {
////                document.write(out);
////            } catch (FileNotFoundException e) {
////                e.printStackTrace();
////            } catch (IOException e) {
////                e.printStackTrace();
////            }
////        } catch (IOException e) {
////            e.printStackTrace();
////        }
////    }


    public static void main(String[] args) {
        // write you r code here
//        loadInfo();

//        for (CUser user : users)
//        {
//            cdaoUsers.save(user);
//        }
//        for (CProduct product : products)
//        {
//            cdaoProducts.save(product);
//        }
//        for (COrder order : orders)
//        {
//            cdaoOrders.save(order);
//        }
        String user_uuid = "6720a44c-af02-41e9-9d19-c4bb1c38c9a9";
        String product_uuid = "c5ae6430-e706-4128-91d4-84dd164f9d57";
        String product2_uuid = "27a23a88-7961-4ec5-9391-516b1f30b6b6";

        int count_products = 2;
        int count_orders = 1;
        for (int i = 0; i < count_products; i++)
        {
            products.add(new CProduct());
        }
        for (int i = 0; i < count_orders; i++)
        {
            orders.add(new COrder());
        }

        CProduct product = new CProduct(UUID.fromString(product_uuid), "Велосипед", 10, "Спорт", orders);
        CProduct product2 = new CProduct(UUID.fromString(product2_uuid), "Турник", 20, "Спорт", orders);
        CUser user = new CUser(UUID.fromString(user_uuid), "Alexander", "Александр", "Male", LocalDate.now(), orders);
        COrder order = new COrder(null, user, LocalDate.now(), products);

        orders.set(0, order);
        products.set(0, product);
        products.set(1, product2);
        user.setOrders(orders);
        product.setOrders(orders);
        order.setProducts(products);


//        CDAOUsers cdaoUsers = new CDAOUsers(CHibernateConfig.getSessionFactory());
//        CDAOProducts cdaoProducts = new CDAOProducts(CHibernateConfig.getSessionFactory());
//        CDAOOrders cdaoOrders = new CDAOOrders(CHibernateConfig.getSessionFactory());
//        cdaoProducts.save(product);
//        cdaoProducts.save(product2);
//        cdaoUsers.save(user);
//        cdaoOrders.save(order);


    }
}
