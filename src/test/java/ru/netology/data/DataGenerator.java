package ru.netology.data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import lombok.Value;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class DataGenerator {
    static Faker faker = new Faker();

    public static String generateCreditCard() {
        return faker.finance().creditCard(CreditCardType.MASTERCARD);
    }

    public static String generateShortCardNumber() {
        return faker.number().digits(8);
    }

    public static String getApprovedCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getDeclinedCardNumber() {
        return ("4444 4444 4444 4442");
    }
    public static String getEmptyCArd() {
        return ("");
    }

    public static String currentMonth = LocalDate.now().format(DateTimeFormatter.ofPattern("MM"));
    public static String previousMonth = LocalDate.now().
            minusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    public static String nextMonth = LocalDate.now().
            plusMonths(1).format(DateTimeFormatter.ofPattern("MM"));
    public static String zeroMonth = "00";
    public static String emptyMonth = "";
    public static String currentYear = LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));

    public static String previousYear = LocalDate.now().
            minusYears(1).format(DateTimeFormatter.ofPattern("yy"));

    public static String nextYear = LocalDate.now().
            plusYears(1).format(DateTimeFormatter.ofPattern("yy"));
    public static String emptyYear = "";
    public static String emptyCardholder = "";
    public static String emptyCvv = "";

    public static String generateInvalidYearFuture = LocalDate.now().
            plusYears(6).format(DateTimeFormatter.ofPattern("yy"));

    public static String generateMonthNumberGreaterThanTwelve() {
        int greaterTwelve = (int) ((Math.random()*86)+13);
        return (String.valueOf(greaterTwelve));
    }

    public static String generateValidName() {
        Faker faker = new Faker(new Locale("en"));
        String name = faker.name().firstName() + " " + faker.name().lastName();
        return name;
    }

    public static String generateFirstName() {
        return faker.name().firstName();
    }

    public static String generateCVC() {
        return faker.number().digits(3);
    }

    public static String generateInvalidCVC() {
        return faker.number().digits(2);
    }

    public static String generateSpecialCharactersOnly(int length) {
        String specialCharacters = "!@#$%^&*()_+-=[]{}|;:,.<>?";
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = (int) (Math.random() * specialCharacters.length());
            result.append(specialCharacters.charAt(randomIndex));
        }
        return result.toString();
    }

     public static String generateCyrillicName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().fullName();
    }

    @Value
    public static class CardInfo  {
        private String cardNumber;
        private String month;
        private String year;
        private String owner;
        private String cardCVV;
    }

}
