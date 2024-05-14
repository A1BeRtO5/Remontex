package com.example.programm;


import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.Date;

import static com.example.programm.HelloController.idFromDB;

public class Connect {
    String url = "jdbc:postgresql://localhost:5432/lala";
    String user = "postgres";
    String password = "root";

    public void writeToDb(String phoneNumber, String date, String groupp, String conditions, String client, String color, String serialNumber, String giveMyMoney) {

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "INSERT INTO orders (phoneNumber, date,groupp,conditions,client,color,serialNumber,giveMyMoney) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, phoneNumber);
            statement.setString(2, date);
            statement.setString(3, groupp);
            statement.setString(4, conditions);
            statement.setString(5, client);
            statement.setString(6, color);
            statement.setString(7, serialNumber);
            statement.setString(8, giveMyMoney);
            statement.executeUpdate();
            System.out.println("Запис додано до таблиці orders");
        } catch (SQLException e) {
            System.err.println("Помилка з'єднання з базою даних: " + e.getMessage());
        }
    }
    public void saveChangesOrdersBToDb(int id, String phoneNumber, String date, String groupp, String conditions, String client, String color, String serialNumber, String giveMyMoney) {
        String sql = "UPDATE orders SET phonenumber = ?, date = ?, groupp = ?, conditions = ?, client = ?, color = ?, serialnumber = ?, givemymoney = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, phoneNumber);
            pstmt.setString(2, date);
            pstmt.setString(3, groupp);
            pstmt.setString(4, conditions);
            pstmt.setString(5, client);
            pstmt.setString(6, color);
            pstmt.setString(7, serialNumber);
            pstmt.setString(8, giveMyMoney);
            pstmt.setInt(9, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }


    public String selectFromDb() {
        StringBuilder stringBuilder = new StringBuilder();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String phoneNumber = resultSet.getString("phoneNumber");
                String date = resultSet.getString("date");
                String groupp = resultSet.getString("groupp");
                String conditions = resultSet.getString("conditions");
                String client = resultSet.getString("client");
                String color = resultSet.getString("color");
                String serialNumber = resultSet.getString("serialNumber");
                String giveMyMoney = resultSet.getString("giveMyMoney");

                String dateString = date;
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date2 = format.parse(dateString);
                LocalDate localDate = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = LocalDate.now();
                long daysUntil = ChronoUnit.DAYS.between(currentDate, localDate);

                stringBuilder.append(id + "    |    " + phoneNumber +"    |    " + date +"    |    " + groupp +"    |    " + conditions  +"    |    " + client +"    |    " + color  +"    |    " + serialNumber +"    |    " + giveMyMoney+ "    |    " + "До видачі замовлення: " +daysUntil +" днів"+ "\n" );
                stringBuilder.append("__________________________________________________________________________________________________________________________________________________________________________"+ "\n");
            }


        } catch (SQLException e) {
            System.err.println("Помилка з'єднання з базою даних: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String s = String.valueOf(stringBuilder);

        return s;
    }

    public String selectAllOrdersFromDb() {
        int counter = 0;

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM  orders");
            while (resultSet.next()) {
                counter++;
            }

        } catch (SQLException e) {
            System.err.println("Помилка з'єднання з базою даних: " + e.getMessage());
        }
        String s = String.valueOf(counter);
        return s;
    }

    public String selectDataiff () {
        int counter = 0;
        String s = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT date, conditions FROM  orders");
            while (resultSet.next()) {

                String date = resultSet.getString("date");
                String conditions = resultSet.getString("conditions");

                String dateString = date;
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date2 = format.parse(dateString);
                LocalDate localDate = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = LocalDate.now();
                long daysUntil = ChronoUnit.DAYS.between(currentDate, localDate);

                String condition1 = "Issued";
                String condition2 = "issued";
                String condition3 = "I";
                String condition4 = "i";
                String condition5 = "Виданий";
                String condition6 = "Видано";
                String condition7 = "виданий";
                String condition8 = "видано";
                String condition9 = "в";
                String condition10 = "В";
                String condition11 = "Видана";
                String condition12 = "видана";


                if (daysUntil <= 0 && (!(conditions.equals(condition1) || conditions.equals(condition2) || conditions.equals(condition3) || conditions.equals(condition4) || conditions.equals(condition5) || conditions.equals(condition6) || conditions.equals(condition7) || conditions.equals(condition8) || conditions.equals(condition9) || conditions.equals(condition10) || conditions.equals(condition11) || conditions.equals(condition12)))) {
                    counter++;
                }

            }
        } catch (SQLException e) {
            System.err.println("Помилка з'єднання з базою даних: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        s = String.valueOf(counter);
        return s;
    }
    public String selectCondition () { //термінові
        int counter = 0;
        String s = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT conditions FROM  orders");
            while (resultSet.next()) {

                String conditions = resultSet.getString("conditions");
                String condition1 = "Терміново";
                String condition2 = "Терміновий";
                String condition3 = "терміново";
                String condition4 = "терміновий";
                String condition5 = "Т";
                String condition6 = "т";
                String condition7 = "T";
                String condition8 = "t";

                if (conditions.equals(condition1) || conditions.equals(condition2) || conditions.equals(condition3) || conditions.equals(condition4) || conditions.equals(condition5) || conditions.equals(condition6) || conditions.equals(condition7) || conditions.equals(condition8)) {
                    counter++;
                }
            }
        } catch (SQLException e) {
            System.err.println("Помилка з'єднання з базою даних: " + e.getMessage());
        }
        s = String.valueOf(counter);
        return s;
    }

    public ArrayList selectPhoneNumberromDb( String phoneNumber) {
        ArrayList<String> arrayList = new ArrayList<>();
        String strTrim;

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM  orders");
            arrayList.add(0, "");
            arrayList.add(1, "");
            arrayList.add(2, "");
            arrayList.add(3, "");
            arrayList.add(4, "");
            arrayList.add(5, "");
            arrayList.add(6, "");
            arrayList.add(7, "");
            arrayList.add(8, "");

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String phoneNumberFromDB = resultSet.getString("phoneNumber");
                strTrim = phoneNumberFromDB.trim();
                String date = resultSet.getString("date");
                String groupp = resultSet.getString("groupp");
                String conditions = resultSet.getString("conditions");
                String client = resultSet.getString("client");
                String color = resultSet.getString("color");
                String serialNumber = resultSet.getString("serialNumber");
                String giveMyMoney = resultSet.getString("giveMyMoney");

//                System.out.println("phoneNumberFromDB:" + strTrim);
//                System.out.println("phoneNumber:" + phoneNumber);
//                System.out.println(strTrim.equals(phoneNumber));

                if (phoneNumber.equals(strTrim)) {
                    arrayList.set(0, phoneNumberFromDB);
                    arrayList.set(1, date);
                    arrayList.set(2, groupp);
                    arrayList.set(3, conditions);
                    arrayList.set(4, client);
                    arrayList.set(5, color);
                    arrayList.set(6, serialNumber);
                    arrayList.set(7, giveMyMoney);
                    arrayList.set(8, id);
                }
            }
        } catch (SQLException e) {
            System.err.println("Помилка з'єднання з базою даних: " + e.getMessage());
        }
        return arrayList;
    }

    public String selectDoneOrders () {
        int counter = 0;
        String s = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT conditions FROM  orders");
            while (resultSet.next()) {

                String conditions = resultSet.getString("conditions");
                String condition1 = "Готово";
                String condition2 = "готово";
                String condition3 = "Г";
                String condition4 = "г";
                String condition5 = "Done";
                String condition6 = "done";
                String condition7 = "D";
                String condition8 = "d";
                String condition9 = "Complete";
                String condition10 = "complete";
                String condition11 = "C";
                String condition12 = "c";
                String condition13 = "С";
                String condition14 = "с";

                if (conditions.equals(condition1) || conditions.equals(condition2) || conditions.equals(condition3) || conditions.equals(condition4) || conditions.equals(condition5) || conditions.equals(condition6) || conditions.equals(condition7) || conditions.equals(condition8) || conditions.equals(condition9) || conditions.equals(condition10) || conditions.equals(condition11) || conditions.equals(condition12) || conditions.equals(condition13) || conditions.equals(condition14)) {
                    counter++;
                }
            }
        } catch (SQLException e) {
            System.err.println("Помилка з'єднання з базою даних: " + e.getMessage());
        }
        s = String.valueOf(counter);
        return s;
    }

    public String selectIssuedOrders () {
        String s = "";
        int counter = 0;

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT conditions FROM  orders");
            while (resultSet.next()) {

                String conditions = resultSet.getString("conditions");
                String condition1 = "Issued";
                String condition2 = "issued";
                String condition3 = "I";
                String condition4 = "i";
                String condition5 = "Виданий";
                String condition6 = "Видано";
                String condition7 = "виданий";
                String condition8 = "видано";
                String condition9 = "в";
                String condition10 = "В";
                String condition11 = "Видана";
                String condition12 = "видана";

                if (conditions.equals(condition1) || conditions.equals(condition2) || conditions.equals(condition3) || conditions.equals(condition4) || conditions.equals(condition5) || conditions.equals(condition6) || conditions.equals(condition7) || conditions.equals(condition8) || conditions.equals(condition9) || conditions.equals(condition10) || conditions.equals(condition11) || conditions.equals(condition12)) {
                    counter++;
                }
            }
        } catch (SQLException e) {
            System.err.println("Помилка з'єднання з базою даних: " + e.getMessage());
        }
        s = String.valueOf(counter);
        return s;
    }

    public void DeleteOrder () {

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            String sql = "DELETE FROM orders WHERE id = ?";

            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                int id1 = Integer.parseInt(idFromDB);
                statement.setInt(1, id1);
                int rowsUpdated = statement.executeUpdate();
                System.out.println("Rows updated: " + rowsUpdated);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public String selectSearchDoneOrders () {
        StringBuilder stringBuilder = new StringBuilder();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");
            while (resultSet.next()) {

                String id = resultSet.getString("id");
                String phoneNumber = resultSet.getString("phoneNumber");
                String date = resultSet.getString("date");
                String groupp = resultSet.getString("groupp");
                String conditions = resultSet.getString("conditions");
                String client = resultSet.getString("client");
                String color = resultSet.getString("color");
                String serialNumber = resultSet.getString("serialNumber");
                String giveMyMoney = resultSet.getString("giveMyMoney");

                String dateString = date;
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date2 = format.parse(dateString);
                LocalDate localDate = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = LocalDate.now();
                long daysUntil = ChronoUnit.DAYS.between(currentDate, localDate);

                String condition1 = "Готово";
                String condition2 = "готово";
                String condition3 = "Г";
                String condition4 = "г";
                String condition5 = "Done";
                String condition6 = "done";
                String condition7 = "D";
                String condition8 = "d";
                String condition9 = "Complete";
                String condition10 = "complete";
                String condition11 = "C";
                String condition12 = "c";
                String condition13 = "С";
                String condition14 = "с";


                if (conditions.equals(condition1) || conditions.equals(condition2) || conditions.equals(condition3) || conditions.equals(condition4) || conditions.equals(condition5) || conditions.equals(condition6) || conditions.equals(condition7) || conditions.equals(condition8) || conditions.equals(condition9) || conditions.equals(condition10) || conditions.equals(condition11) || conditions.equals(condition12) || conditions.equals(condition13) || conditions.equals(condition14)) {
                    stringBuilder.append(id + "    |    " + phoneNumber + "    |    " + date + "    |    " + groupp + "    |    " + conditions + "    |    " + client + "    |    " + color + "    |    " + serialNumber + "    |    " + giveMyMoney + "    |    " + "До видачі замовлення: " + daysUntil + " днів" + "\n");
                    stringBuilder.append("__________________________________________________________________________________________________________________________________________________________________________" + "\n");
                }
            }
        } catch (SQLException e) {
            System.err.println("Помилка з'єднання з базою даних: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String s = String.valueOf(stringBuilder);
        System.out.println("______________________________");
        System.out.println(s);
        System.out.println("------------------------------");
        return s;

    }

    public String selectOrdinarydOrders () {
        StringBuilder stringBuilder = new StringBuilder();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");
            while (resultSet.next()) {

                String id = resultSet.getString("id");
                String phoneNumber = resultSet.getString("phoneNumber");
                String date = resultSet.getString("date");
                String groupp = resultSet.getString("groupp");
                String conditions = resultSet.getString("conditions");
                String client = resultSet.getString("client");
                String color = resultSet.getString("color");
                String serialNumber = resultSet.getString("serialNumber");
                String giveMyMoney = resultSet.getString("giveMyMoney");

                String dateString = date;
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date2 = format.parse(dateString);
                LocalDate localDate = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = LocalDate.now();
                long daysUntil = ChronoUnit.DAYS.between(currentDate, localDate);

                String condition1 = "Issued";
                String condition2 = "issued";
                String condition3 = "I";
                String condition4 = "i";
                String condition5 = "Виданий";
                String condition6 = "Видано";
                String condition7 = "виданий";
                String condition8 = "видано";
                String condition9 = "в";
                String condition10 = "В";
                String condition11 = "Видана";
                String condition12 = "видана";

                if (conditions.equals(condition1) || conditions.equals(condition2) || conditions.equals(condition3) || conditions.equals(condition4) || conditions.equals(condition5) || conditions.equals(condition6) || conditions.equals(condition7) || conditions.equals(condition8) || conditions.equals(condition9) || conditions.equals(condition10) || conditions.equals(condition11) || conditions.equals(condition12)) {
                    stringBuilder.append(id + "    |    " + phoneNumber + "    |    " + date + "    |    " + groupp + "    |    " + conditions + "    |    " + client + "    |    " + color + "    |    " + serialNumber + "    |    " + giveMyMoney + "    |    " + "Видано клієнту"+ "\n");
                    stringBuilder.append("__________________________________________________________________________________________________________________________________________________________________________" + "\n");
                }
            }
        } catch (SQLException e) {
            System.err.println("Помилка з'єднання з базою даних: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String s = String.valueOf(stringBuilder);
        System.out.println("______________________________");
        System.out.println(s);
        System.out.println("------------------------------");
        return s;

    }
    public String selectUrgentOrders () {
        StringBuilder stringBuilder = new StringBuilder();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");
            while (resultSet.next()) {

                String id = resultSet.getString("id");
                String phoneNumber = resultSet.getString("phoneNumber");
                String date = resultSet.getString("date");
                String groupp = resultSet.getString("groupp");
                String conditions = resultSet.getString("conditions");
                String client = resultSet.getString("client");
                String color = resultSet.getString("color");
                String serialNumber = resultSet.getString("serialNumber");
                String giveMyMoney = resultSet.getString("giveMyMoney");

                String dateString = date;
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date2 = format.parse(dateString);
                LocalDate localDate = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = LocalDate.now();
                long daysUntil = ChronoUnit.DAYS.between(currentDate, localDate);

                String condition1 = "Терміново";
                String condition2 = "Терміновий";
                String condition3 = "терміново";
                String condition4 = "терміновий";
                String condition5 = "Т";
                String condition6 = "т";
                String condition7 = "T";
                String condition8 = "t";

                if (conditions.equals(condition1) || conditions.equals(condition2) || conditions.equals(condition3) || conditions.equals(condition4) || conditions.equals(condition5) || conditions.equals(condition6) || conditions.equals(condition7) || conditions.equals(condition8) ) {
                    stringBuilder.append(id + "    |    " + phoneNumber + "    |    " + date + "    |    " + groupp + "    |    " + conditions + "    |    " + client + "    |    " + color + "    |    " + serialNumber + "    |    " + giveMyMoney + "    |    " + "До видачі замовлення: " + daysUntil + " днів" + "\n");
                    stringBuilder.append("__________________________________________________________________________________________________________________________________________________________________________" + "\n");
                }
            }
        } catch (SQLException e) {
            System.err.println("Помилка з'єднання з базою даних: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String s = String.valueOf(stringBuilder);
        System.out.println("______________________________");
        System.out.println(s);
        System.out.println("------------------------------");
        return s;

    }
    public String selectOverdueOrders () {// prostrok
        StringBuilder stringBuilder = new StringBuilder();

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");
            while (resultSet.next()) {

                String id = resultSet.getString("id");
                String phoneNumber = resultSet.getString("phoneNumber");
                String date = resultSet.getString("date");
                String groupp = resultSet.getString("groupp");
                String conditions = resultSet.getString("conditions");
                String client = resultSet.getString("client");
                String color = resultSet.getString("color");
                String serialNumber = resultSet.getString("serialNumber");
                String giveMyMoney = resultSet.getString("giveMyMoney");

                String dateString = date;
                SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
                Date date2 = format.parse(dateString);
                LocalDate localDate = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                LocalDate currentDate = LocalDate.now();
                long daysUntil = ChronoUnit.DAYS.between(currentDate, localDate);

                String condition1 = "Issued";
                String condition2 = "issued";
                String condition3 = "I";
                String condition4 = "i";
                String condition5 = "Виданий";
                String condition6 = "Видано";
                String condition7 = "виданий";
                String condition8 = "видано";
                String condition9 = "в";
                String condition10 = "В";
                String condition11 = "Видана";
                String condition12 = "видана";

                if (daysUntil < 0 && (!(conditions.equals(condition1) || conditions.equals(condition2) || conditions.equals(condition3) || conditions.equals(condition4) || conditions.equals(condition5) || conditions.equals(condition6) || conditions.equals(condition7) || conditions.equals(condition8) || conditions.equals(condition9) || conditions.equals(condition10) || conditions.equals(condition11) || conditions.equals(condition12))) ) {

                    stringBuilder.append(id + "    |    " + phoneNumber + "    |    " + date + "    |    " + groupp + "    |    " + conditions + "    |    " + client + "    |    " + color + "    |    " + serialNumber + "    |    " + giveMyMoney + "    |    " + "Замовлення прострочене " + daysUntil + " днів" + "\n");
                    stringBuilder.append("__________________________________________________________________________________________________________________________________________________________________________" + "\n");
                }
            }
        } catch (SQLException e) {
            System.err.println("Помилка з'єднання з базою даних: " + e.getMessage());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        String s = String.valueOf(stringBuilder);
        System.out.println("______________________________");
        System.out.println(s);
        System.out.println("------------------------------");
        return s;

    }



}

