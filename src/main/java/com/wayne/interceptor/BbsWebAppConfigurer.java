package com.wayne.interceptor;

import com.google.common.collect.Lists;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.freemarker.FreeMarkerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * @Author TAO
 * @Date 2017/3/27 16:10
 */
@Configuration
public class BbsWebAppConfigurer extends WebMvcConfigurerAdapter{

    /**
     * 拿到spring boot中的fm自动化配置
     */
    @Autowired
    private FreeMarkerProperties properties;

    private static final String FAVICON_URL = "/favicon.ico";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 多个拦截器组成一个拦截器链
        // addPathPatterns 用于添加拦截规则
        // excludePathPatterns 用户排除拦截
        registry.addInterceptor(new UserInterceptorHandler()).addPathPatterns("/**").excludePathPatterns(FAVICON_URL);
        super.addInterceptors(registry);
    }

    /**
     * 覆盖spring boot中的freemarker配置
     *
     * @return
     */
    @Bean
    public FreeMarkerConfigurer freeMarkerConfigurer() {
        //写入配置
        FreeMarkerConfigurer factory = new FreeMarkerConfigurer();
        writerProperties(factory);

        //创建fm的配置，并且将factory中的信息写入到configuration中
        freemarker.template.Configuration configuration = null;
        try {
            configuration = factory.createConfiguration();
            //和spring boot不同的部分，这部分是用来写入我们需要的freemarker configuration
            List<String> autoIncludes = Lists.newArrayListWithCapacity(1);
            //注意macro\macro.ftl这个路径是和在spring.freemarker.template-loader-path下
            autoIncludes.add("common/global.ftl");
            configuration.setAutoIncludes(autoIncludes);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        factory.setConfiguration(configuration);
        return factory;
    }

    private void writerProperties(FreeMarkerConfigurer factory){
        factory.setTemplateLoaderPaths(this.properties.getTemplateLoaderPath());
        factory.setPreferFileSystemAccess(this.properties.isPreferFileSystemAccess());
        factory.setDefaultEncoding(this.properties.getCharsetName());
        Properties settings = new Properties();
        settings.putAll(this.properties.getSettings());
        factory.setFreemarkerSettings(settings);
    }

}
