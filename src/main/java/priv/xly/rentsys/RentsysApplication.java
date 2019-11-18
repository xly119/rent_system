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
//@MapperScan("com.wizz.demo.dao")//ɨ��mapper��·��
//@EnableWebSecurity
@EnableCaching
@EnableAutoConfiguration(exclude = {SecurityAutoConfiguration.class})
//@ComponentScan(basePackages = {"com.wizz.demo"})//��ΪController���е�Ϊע��@Controller����Ҫ���ע����ע��
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