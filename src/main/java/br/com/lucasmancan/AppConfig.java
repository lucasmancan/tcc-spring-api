package br.com.lucasmancan;
import net.sf.ehcache.config.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.PreDestroy;

@Configuration
@EnableCaching
@ComponentScan({ "br.com.lucasmancan.*" })
public class AppConfig {

    private final Logger log = LoggerFactory.getLogger(CacheConfiguration.class);

    private net.sf.ehcache.CacheManager cacheManager;

//    @PreDestroy
//    public void destroy() {
//        cacheManager.shutdown();
//    }
//
//    @Bean
//    public CacheManager cacheManager() {
//        log.debug("Starting Ehcache");
//        cacheManager = net.sf.ehcache.CacheManager.create();
//        cacheManager.getConfiguration().setMaxBytesLocalHeap("16M");
//        EhCacheCacheManager ehCacheManager = new EhCacheCacheManager();
//        ehCacheManager.setCacheManager(cacheManager);
//        return ehCacheManager;
//    }

    @Bean
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheCacheManager().getObject());
    }

    @Bean
    public EhCacheManagerFactoryBean ehCacheCacheManager() {
        EhCacheManagerFactoryBean cmfb = new EhCacheManagerFactoryBean();
        cmfb.setConfigLocation(new ClassPathResource("ehcache.xml"));
        cmfb.setShared(true);
        return cmfb;
    }
}