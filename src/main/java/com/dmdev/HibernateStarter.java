package com.dmdev;


import com.dmdev.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.time.LocalDate;

public class HibernateStarter {
    public static void main(String[] args) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        //configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy())
        configuration.configure();

        try (SessionFactory sessionFactory = configuration.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = User.builder()
                    .username("ivan@gmail.com")
                    .firstname("Ivan")
                    .username("Ivanov")
                    .birthDate(LocalDate.of(2000, 1, 19))
                    .build();

            session.persist(user);

            session.getTransaction().commit();
        }
    }
}