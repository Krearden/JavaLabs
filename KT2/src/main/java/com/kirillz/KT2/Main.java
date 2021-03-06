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
                if (Objects.equals(gender, "м"))
                    gender = "Male";
                else if (Objects.equals(gender, "ж"))
                    gender = "Female";
                //birth date
                cell = row.getCell(4);
                dateOfBirth = cell.getDateCellValue().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate();
                List<COrder> ord = Arrays.asList(new COrder());
                users.add(new CUser(id, login, name, gender, dateOfBirth, ord));
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
                List<COrder> ord = Arrays.asList(new COrder());
                products.add(new CProduct(product_id, product_name, price, category, ord));
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
            LocalDateTime purchase_date_time;
            LocalDate purchase_date;
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
                purchase_date_time = cell.getLocalDateTimeCellValue();
                purchase_date = purchase_date_time.toLocalDate();
                List<CProduct> prod = Arrays.asList(new CProduct());
                orders.add(new COrder(null, getCUser_by_id(user_id), purchase_date, prod));
            }
        } catch (Exception e) {e.printStackTrace();}
    }

    //заполнить продукты у заказов
    private static void loadProductsInOrders()
    {
        for (COrder order : orders)
        {
            UUID owners_uuid = order.getOwner().getId();
            List<CProduct> local_products = new ArrayList<>();
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
                    if (user_id.equals(owners_uuid))
                    {
                        //находится объект класса CProduct по его UUID и добавляется в список
                        local_products.add(getCProductById(product_id));
                    }
                }
            } catch (Exception e) {e.printStackTrace();}
            //заполененный список присваевается агрументу класса COrder
            //теперь конкретный заказ "понимает" какие в нем находятся товары
            order.setProducts(local_products);
        }
    }

    //beta заполнить заказы у продуктов
    private static void loadOrdersInProducts()
    {
        for (CProduct product : products)
        {
            UUID products_uuid = product.getId();
            List<COrder> local_orders = new ArrayList<>();
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
                    if (products_uuid.equals(product_id))
                    {
                        local_orders.add(getCOrderByUserId(user_id));
                    }
                }
            } catch (Exception e) {e.printStackTrace();}
            product.setOrders(local_orders);

        }
    }

    //beta заполнить заказы у пользователей
    private static void loadOrdersInUsers()
    {
        for (CUser user : users)
        {
            UUID user_uuid = user.getId();
            List<COrder> local_orders = new ArrayList<>();
            for (COrder order : orders)
            {
                UUID owners_uuid = order.getOwner().getId();
                if (user_uuid.equals(owners_uuid))
                {
                    local_orders.add(order);
                }
            }
            user.setOrders(local_orders);

        }
    }
    //загрузка информации из excel и заполнение аргументов классов, отвечающих за связь между ними
    public static void loadInfo(boolean load_2_db)
    {
        //заполняем списки, определенные в начале файла. аргументы представленных классов, ссылающиеся друг на друга, пока пусты.
        loadProductsExcel();
        loadUsersExcel();
        loadOrdersExcel();
        //заполняем аргументы классов в списках, которые ссылаются друг на друга (чтобы hibernate сам расставил все ссылки в БД)
        loadProductsInOrders();
        loadOrdersInProducts();
        loadOrdersInUsers();
        //производим загрузку информации в базу данных
        //cdao
        if (load_2_db)
        {
            CDAOUsers cdaoUsers = new CDAOUsers(CHibernateConfig.getSessionFactory());
            CDAOProducts cdaoProducts = new CDAOProducts(CHibernateConfig.getSessionFactory());
            CDAOOrders cdaoOrders = new CDAOOrders(CHibernateConfig.getSessionFactory());
            cdaoUsers.saveList(users);
            cdaoProducts.saveList(products);
            cdaoOrders.saveList(orders);
        }
    }

    //проверка, является ли дата пятницой
    private static boolean fridayCheck(LocalDate purchase_date)
    {
        DayOfWeek day = purchase_date.getDayOfWeek();
        return day == DayOfWeek.FRIDAY;
    }
    //функция для нахождения имени пользователя по id
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
    //возвращает пользователя по его UUID
    private static CUser getCUser_by_id(UUID user_id)
    {
        CUser answer_cUser = new CUser();
        for (int i = 0; i < users.size(); i++)
        {
            if (users.get(i).getId().equals(user_id))
                answer_cUser = users.get(i);
        }
        return answer_cUser;
    }
    //возвращает продукт по его UUID
    private  static CProduct getCProductById(UUID product_id)
    {
        CProduct answer_cProduct = new CProduct();
        for (CProduct product : products)
        {
            if (product.getId().equals(product_id))
            {
                answer_cProduct = product;
            }
        }
        return answer_cProduct;
    }
    //возвращает COrder по id его владельца
    private static COrder getCOrderByUserId(UUID user_id)
    {
        COrder answer_cOrder = new COrder();
        for (COrder order : orders)
        {
            if (order.getOwner().getId().equals(user_id))
            {
                answer_cOrder = order;
            }
        }
        return answer_cOrder;
    }
    //функция нахождения наименования товара по id
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

    //функция записи результатов работы программы в WORD файл
    public static void createWord()
    {
        CDAOOrders daoOrders = new CDAOOrders(CHibernateConfig.getSessionFactory());
        List<COrder> orders;
        orders = daoOrders.getAll();
        try (XWPFDocument document = new XWPFDocument())
        {
            //хедер
            XWPFHeader head = document.createHeader(HeaderFooterType.DEFAULT);
            head.createParagraph()
                    .createRun()
                    .setText("КТ-2 Технологии Java; Выполнил Кирилл Запорожченко (2 курс, Физфак, ПМИ, ФЗ-12)");
            //заголовок документа
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            run.setBold(true);
            run.setFontFamily("Courier");
            run.setFontSize(15);
            run.setText("\nНиже представлены пользователи, которые совершили покупки в пятницу\n");
            //создаем табцицу
            XWPFTable table = document.createTable();
            //первая строка таблицы с заголовками столбцов
            XWPFTableRow tableRowOne = table.getRow(0);
            tableRowOne.getCell(0).setText("ID");
            tableRowOne.addNewTableCell().setText("Date_of_birth");
            tableRowOne.addNewTableCell().setText("Gender");
            tableRowOne.addNewTableCell().setText("Login");
            tableRowOne.addNewTableCell().setText("Name");
            for (COrder order : orders)
            {
                if (fridayCheck(order.getPurchase_date()))
                {
                    CUser friday_user = order.getOwner();
                    XWPFTableRow tableRow = table.createRow();
                    tableRow.getCell(0).setText(friday_user.getId().toString());
                    tableRow.addNewTableCell().setText(friday_user.getDateOfBirth().toString());
                    tableRow.addNewTableCell().setText(friday_user.getGender());
                    tableRow.addNewTableCell().setText(friday_user.getLogin());
                    tableRow.addNewTableCell().setText(friday_user.getName());
                }
            }
            try (FileOutputStream out = new FileOutputStream("KT2_friday_users_report.docx")) {
                document.write(out);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        // write you r code here
//        loadInfo(true);

//        //пользователи, совершившие покупки(ку) в пятницу
//        List<CUser> users_from_database = cdaoUsers.getAll();
//        List<CUser> friday_users = new ArrayList<>();
//        for (CUser user : users_from_database)
//        {
//            for (COrder order : user.getOrders())
//            {
//                if (fridayCheck(order.getPurchase_date()))
//                {
//                    friday_users.add(user);
//                    break;
//                }
//            }
//        }


        CDAOOrders daoOrders = new CDAOOrders(CHibernateConfig.getSessionFactory());
        CDAOProducts daoProducts = new CDAOProducts(CHibernateConfig.getSessionFactory());

        CProduct testp = daoProducts.get(UUID.fromString("c5ae6430-e706-4128-91d4-84dd164f9d57"));

        //реализация удаления товара
        List<COrder> getorders = daoOrders.getAll();
        COrder getord = daoOrders.get(UUID.fromString("543f8f1c-7733-4f8a-9a1a-67bdab5ea5fd"));
        List<CProduct> getproducts = daoProducts.getAll();


        int x = 0;


    }
}
