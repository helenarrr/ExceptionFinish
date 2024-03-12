package org.example;

//Напишите приложение, которое будет запрашивать у пользователя следующие данные в произвольном порядке, разделенные пробелом:

//     --   Фамилия Имя Отчество дата рождения номер телефона пол
//        Форматы данных:
//        фамилия, имя, отчество - строки
//        дата_рождения - строка формата dd.mm.yyyy
//        номер_телефона - целое беззнаковое число без форматирования
//        пол - символ латиницей f или m.
//
//     --   Приложение должно проверить введенные данные по количеству. Если количество не совпадает с требуемым,
//        вернуть код ошибки, обработать его и показать пользователю сообщение, что он ввел меньше и больше данных, чем требуется.
//-------------------------------------------------------------------------------------------------------------------------------------
//      --  Приложение должно попытаться распарсить полученные значения и выделить из них требуемые параметры.
//        Если форматы данных не совпадают, нужно бросить исключение, соответствующее типу проблемы. Можно использовать встроенные типы java и создать свои.
//        Исключение должно быть корректно обработано, пользователю выведено сообщение с информацией, что именно неверно.
//
//      --  Если всё введено и обработано верно, должен создаться файл с названием, равным фамилии, в него в одну строку должны записаться полученные данные, вида
//
//             <Фамилия><Имя><Отчество><датарождения> <номертелефона><пол>
//
//              Однофамильцы должны записаться в один и тот же файл, в отдельные строки.
//
//              Не забудьте закрыть соединение с файлом.
//
//При возникновении проблемы с чтением-записью в файл, исключение должно быть корректно обработано, пользователь должен увидеть стектрейс ошибки.


import org.example.exception.FullNameExceptionException;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserDataApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Запрашиваем данные у пользователя
        System.out.println("Введите данные в формате: Фамилия Имя Отчество Дата_рождения Номер_телефона Пол");
        String userData = scanner.nextLine();

        // Разделяем введенные данные
        String[] dataParts = userData.split(" ");
        if (dataParts.length != 6) {
            System.out.println("Некорректное количество данных. Пожалуйста, введите все данные в требуемом формате.");
            return;
        }

        String lastName = dataParts[0];
        String firstName = dataParts[1];
        String middleName = dataParts[2];
        String birthDate = dataParts[3];
        String phoneNumber = dataParts[4];
        String gender = dataParts[5];


        try {

            if (!lastName.matches("[A-Za-z ]*"))
                throw new FullNameExceptionException(lastName, "Вы неправильно ввели фамилию " + lastName);
            if (!firstName.matches("[A-Za-z ]*"))
                throw new FullNameExceptionException(firstName, "Вы неправильно ввели имя " + firstName);
            if (!middleName.matches("[A-Za-z ]*"))
                throw new FullNameExceptionException(middleName, "Вы неправильно ввели отчество " + middleName);

            if (birthDate.matches("\\\\d{2}.\\\\d{2}.\\\\d{4}")) throw new RuntimeException("Неверная дата " + birthDate);
            if (phoneNumber.matches("\\\\d+")) throw new RuntimeException("Неверный номер " + phoneNumber);
            if (gender.matches("fm")) throw new RuntimeException("Неверный пол " + gender);

            // Создаем файл с именем фамилии
            FileWriter fileWriter = new FileWriter(lastName + ".txt", true);
            fileWriter.write(String.format("%s %s %s %s %s %s%n", lastName, firstName, middleName, birthDate, phoneNumber, gender));
            fileWriter.flush();
            fileWriter.close();
            System.out.println("Данные успешно записаны в файл.");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ошибка при записи данных в файл. Пожалуйста, попробуйте еще раз.");
        } catch (FullNameExceptionException lastnameException) {
            lastnameException.getMessage();
            lastnameException.getData();
        } finally {
            scanner.close();
        }
    }
}