package lk.ijse.dep.pos;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@ComponentScan("lk.ijse.dep.pos")
@Configuration
@Import(HibernateConfig.class)
public class AppConfig {
}
