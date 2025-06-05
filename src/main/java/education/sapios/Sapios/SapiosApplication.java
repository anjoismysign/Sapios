package education.sapios.Sapios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories({"education.sapios.Sapios.repository"})
public class SapiosApplication {
    public static void main(String[] args) {
        SpringApplication.run(SapiosApplication.class, args);
    }
}
