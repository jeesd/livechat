package org.mylivedata.app.configuration;

import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.SearchAttribute;
import net.sf.ehcache.config.Searchable;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableCaching
public class CachingConfigConfiguration implements CachingConfigurer {
    
	@Bean(destroyMethod="shutdown")
    public net.sf.ehcache.CacheManager ehCacheManager() {
        CacheConfiguration usersCache = new CacheConfiguration();
        usersCache.setName("usersCache");
        usersCache.setMemoryStoreEvictionPolicy("LRU");
        usersCache.setMaxEntriesLocalHeap(1000);
        usersCache.setTransactionalMode("local");
        Searchable userSearch = new Searchable();
        
        SearchAttribute accountId = new SearchAttribute();
        accountId.setName("accountId");
        accountId.setExpression("value.getAccountId()");
        userSearch.addSearchAttribute(accountId);
        
        SearchAttribute isOperator = new SearchAttribute();
        isOperator.setName("isOperator");
        isOperator.setExpression("value.isOperator()");
        userSearch.addSearchAttribute(isOperator);
        
        SearchAttribute isChatOnline = new SearchAttribute();
        isChatOnline.setName("isChatOnline");
        isChatOnline.setExpression("value.isChatOnline()");
        userSearch.addSearchAttribute(isChatOnline);
        
        usersCache.searchable(userSearch);
        
        CacheConfiguration connectionsCache = new CacheConfiguration();
        connectionsCache.setName("connectionsCache");
        connectionsCache.setMemoryStoreEvictionPolicy("LRU");
        connectionsCache.setMaxEntriesLocalHeap(1000);
        connectionsCache.setTransactionalMode("local");
        
        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();
        config.addCache(usersCache);
        config.addCache(connectionsCache);

        return net.sf.ehcache.CacheManager.newInstance(config);
    }

    @Bean
    @Override
    public CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }

    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

	@Override
	public CacheResolver cacheResolver() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CacheErrorHandler errorHandler() {
		// TODO Auto-generated method stub
		return null;
	}
}
