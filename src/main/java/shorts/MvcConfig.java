package shorts;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        //registry.addViewController("/short").setViewName("short");
        //registry.addViewController("/").setViewName("short");
        registry.addViewController("/table").setViewName("table");
        registry.addViewController("/login").setViewName("login");
    }

}