package priv.xly.rentsys;

import javax.servlet.MultipartConfigElement;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@MapperScan("priv.xly.rentsys.dao")
//@EnableWebSecurity
@EnableCaching
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
@ComponentScan(basePackages = {"priv.xly.rentsys"})
public class RentsysApplication {

    public static void main(String[] args) {SpringApplication.run(RentsysApplication.class, args);}
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //�����ļ����
        factory.setMaxFileSize("102400KB"); //KB,MB
        /// �������ϴ������ܴ�С
        factory.setMaxRequestSize("1024000KB");
        return factory.createMultipartConfig();
    }
  /**   @Configuration
   public class MyConfiguration {

     @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**");
               }
            };
        }
  }**/ 

}