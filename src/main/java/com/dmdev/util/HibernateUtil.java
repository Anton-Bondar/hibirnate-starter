package com.dmdev.util;

import com.dmdev.converter.BirthdayConverter;
import com.dmdev.entity.User;
import lombok.experimental.UtilityClass;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@UtilityClass
public class HibernateUtil {

    public static SessionFactory buildSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(User.class);
        //configuration.setPhysicalNamingStrategy(new CamelCaseToUnderscoresNamingStrategy())
        configuration.addAttributeConverter(new BirthdayConverter());
        configuration.configure();

        return configuration.buildSessionFactory();
    }
}
