package com.dmdev;

import com.dmdev.entity.Company;
import com.dmdev.entity.User;
import com.dmdev.util.HibernateUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import lombok.Cleanup;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;

class HibernateRunnerTest {

    @Test
    void checkReflectionApi() throws SQLException, IllegalAccessException {

        User user = User.builder()
                .username("ivan@gmail.com")
                .username("Ivanov")
                .build();

        String sql = """
                insert into %s
                (%s)
                values (%s)
                """;
        String tableName = ofNullable(user.getClass().getAnnotation(Table.class))
                .map(tableAnnotation -> tableAnnotation.schema() + "." + tableAnnotation.name())
                .orElse(user.getClass().getName());

        Field[] declaredFields = user.getClass().getDeclaredFields();

        String columnNames = stream(declaredFields)
                .map(field -> ofNullable(field.getAnnotation(Column.class))
                        .map(Column::name)
                        .orElse(field.getName()))
                .collect(Collectors.joining(", "));

        String columnValues = stream(declaredFields)
                .map(field -> "?")
                .collect(Collectors.joining(", "));

        String preparedSql = sql.formatted(tableName, columnNames, columnValues);
        System.out.println(preparedSql);

        Connection connection = createConnection("jdbc:postgresql://localhost:5432/postgres", "postgres", "");
        PreparedStatement preparedStatement = connection.prepareStatement(preparedSql);
        for (int i = 0; i < declaredFields.length; i++) {
            Field currentField = declaredFields[i];
            currentField.setAccessible(true);
            preparedStatement.setObject(i + 1, currentField.get(user));
        }
        System.out.println("prepareStatement:  " + preparedStatement);
    }

    private Connection createConnection(
            String url, String user, String password)
            throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }

    @Test
    void oneToMany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        session.get(Company.class, 1);


        session.getTransaction().commit();
    }

    @Test
    void addUserToNewCompany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        var company = Company.builder()
                .name("Facebook")
                .build();

        var user = User
                .builder()
                .username("anbo@gmail.com")
                .build();

//        user.setCompany(company);
//        company.getUsers().add(user);

        company.addUser(user);

        session.save(company);

        session.getTransaction().commit();
    }

    @Test
    void deleteCompany() {
        @Cleanup var sessionFactory = HibernateUtil.buildSessionFactory();
        @Cleanup var session = sessionFactory.openSession();

        session.beginTransaction();

        Company company = session.get(Company.class, 2);

        session.delete(company);


        session.getTransaction().commit();
    }
}