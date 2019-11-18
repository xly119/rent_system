package priv.xly.rentsys;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
//@MapperScan("com.wizz.demo.dao")//扫描mapper的路径
//@EnableWebSecurity
@EnableCaching
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
//@ComponentScan(basePackages = {"com.wizz.demo"})//因为Controller层有的为注解@Controller，需要这个注解来注入
public class RentsysApplication {

    public static void main(String[] args) {SpringApplication.run(RentsysApplication.class, args);}
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件最大
        factory.setMaxFileSize("102400KB"); //KB,MB
        /// 设置总上传数据总大小
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