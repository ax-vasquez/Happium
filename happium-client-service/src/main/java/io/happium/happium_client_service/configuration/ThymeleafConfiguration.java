package io.happium.happium_client_service.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration
public class ThymeleafConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * SpringResourceTemplateResolver is automatically integrated
     * with Spring's own resource resolution infrastructure
     *
     * @return      Initialized SpringResourceTemplateResolver
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {

        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext( this.applicationContext );
        templateResolver.setPrefix("classpath:templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode( TemplateResolver.DEFAULT_TEMPLATE_MODE ); // HTML

        // Cache-able by default - set to false if you need templates
        // to be automatically updated when modified
        templateResolver.setCacheable(true);
        return templateResolver;

    }

    @Bean
    public SpringTemplateEngine templateEngine() {

        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver( templateResolver() );
        return templateEngine;

    }

    @Bean
    public ThymeleafViewResolver viewResolver() {

        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine( templateEngine() );

        // OPTIONAL - sets this as first resolver to use
        viewResolver.setOrder(1);

        // OPTIONAL - sets the name patterns used to determine if a view is resolved by this resolver or not
        viewResolver.setViewNames(new String[] { ".html", ".xhtml" });

        return viewResolver;

    }


}
