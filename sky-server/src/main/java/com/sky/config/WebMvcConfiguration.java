package com.sky.config;

import com.sky.interceptor.JwtTokenAdminInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/admin/employee/login");
    }

    /**
     * 通过knife4j生成接口文档
     * @return
     */
    @Bean
   // 创建docket对象
    public Docket docket() {
        // 创建ApiInfo对象
        ApiInfo apiInfo = new ApiInfoBuilder()
                // 设置文档标题
                .title("苍穹外卖项目接口文档")
                // 设置文档版本
                .version("2.0")
                // 设置文档描述
                .description("苍穹外卖项目接口文档")
                // 创建ApiInfo对象
                .build();
        // 创建Docket对象
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                // 设置ApiInfo对象
                .apiInfo(apiInfo)
                // 设置扫描路径
                .select()
                // 设置扫描包路径
                .apis(RequestHandlerSelectors.basePackage("com.sky.controller"))
                // 设置扫描路径
                .paths(PathSelectors.any())
                // 创建Docket对象
                .build();
        // 返回Docket对象
        return docket;
    }
    /**
     * 设置静态资源映射
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 把/doc.html映射到classpath:/META-INF/resources/
        log.info("开始设置静态资源映射...");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
