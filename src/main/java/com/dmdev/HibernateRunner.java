package com.dmdev;


import com.dmdev.entity.Birthday;
import com.dmdev.entity.Company;
import com.dmdev.entity.PersonalInfo;
import com.dmdev.entity.User;
import com.dmdev.util.HibernateUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.time.LocalDate;

@Slf4j
public class HibernateRunner {

    public static void main(String[] args) {
        var company = Company.builder()
                .name("Google")
                .build();

        User user = User.builder()
                .username("ivan@gmail.com")
                .personalInfo(PersonalInfo.builder()
                        .lastname("Ivanov")
                        .firstname("Ivan")
                        .birthDate(new Birthday(LocalDate.of(2000, 1, 2)))
                        .build())
                .company(company)
                .build();

        log.info("User entity is in transient state, object: {}" , user);

        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory()) {
            Session session1 = sessionFactory.openSession();
            try (session1) {
                Transaction transaction = session1.beginTransaction();

                session1.persist(company);
                session1.persist(user);

                session1.getTransaction().commit();
            }
        } catch (Exception e) {
            log.error("Exception occured", e);
            throw e;
        }
    }
}