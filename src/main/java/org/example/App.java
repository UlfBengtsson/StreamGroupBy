package org.example;

import org.example.models.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class App 
{

    public static void main( String[] args )
    {
        // Connecting to World sample database and retrieve the CountryLanguage table as a list
        List<Language> countryLanguages = getLang();

        //byCountry(countryLanguages);

        //byLanguage(countryLanguages);

        //byOfficial(countryLanguages);

        byLanguageThenByOfficial(countryLanguages);
    }
    //Grouping methods
    private static void byLanguage(List<Language> countryLanguages) {

        Map<String, List<Language>> byLangName = countryLanguages.stream()
                .collect(Collectors.groupingBy(Language::getName));

        for (String key : byLangName.keySet()) {
            System.out.println(key);
            byLangName.get(key).forEach(System.out::println);
            System.out.println("-------------------------------------");
        }
    }

    private static void byCountry(List<Language> countryLanguages) {

        Map<String, List<Language>> byCountry = countryLanguages.stream()
                .collect(Collectors.groupingBy(Language::getCountryCode));

        for (String key : byCountry.keySet()) {
            System.out.println(key);
            byCountry.get(key).forEach(System.out::println);
            System.out.println("-------------------------------------");
        }
    }

    private static void byOfficial(List<Language> countryLanguages) {

        Map<Boolean, List<Language>> byOfficial = countryLanguages.stream()
                .collect(Collectors.groupingBy(Language::isOfficial));

        for (Boolean key : byOfficial.keySet()) {
            System.out.println((key ? "Official" : "Unofficial"));
            byOfficial.get(key).forEach(System.out::println);
            System.out.println("-------------------------------------");
        }
    }

    private static void byLanguageThenByOfficial(List<Language> countryLanguages) {

        Map<String, Map<Boolean, List<Language>>> byLanguageThenByOfficial = countryLanguages.stream()
                .collect(
                        Collectors.groupingBy(Language::getName,
                                Collectors.groupingBy(Language::isOfficial))
                );

        for (String lang : byLanguageThenByOfficial.keySet()) {

            System.out.println(lang + ':');

            for (Boolean official : byLanguageThenByOfficial.get(lang).keySet()) {
                System.out.println((official ? "Official" : "Unofficial"));
                byLanguageThenByOfficial.get(lang).get(official).forEach(System.out::println);
                System.out.println("-------------------------------------");
            }

        }

    }

    public static List<Language> getLang() {
        String connectionString = "jdbc:mysql://localhost:3306/?&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=Europe/Berlin";
        String userName = "root";
        String userPassword = "root";


        Connection connection = null;
        Statement statement = null;
        ResultSet rs = null;

        List<Language> countryLanguages = new ArrayList<>();

        try {
            connection = DriverManager.getConnection(connectionString, userName, userPassword);
            statement = connection.createStatement();

            rs = statement.executeQuery("SELECT * FROM world.countrylanguage;");

            while (rs.next()) {
                countryLanguages.add(
                        new Language(
                                rs.getString(1),
                                rs.getString(2),
                                (rs.getString(3).equals("T") ? true : false),
                                rs.getFloat(4)
                        ));
            }

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } finally {
            try {
                if (rs != null)
                    rs.close();
                if (statement != null)
                    statement.close();
                if (connection != null)
                    connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return countryLanguages;
    }
}
