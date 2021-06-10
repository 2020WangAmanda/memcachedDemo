package com.example.memcacheddemo;

import com.google.code.ssm.CacheFactory;
import com.google.code.ssm.config.AbstractSSMConfiguration;
import com.google.code.ssm.config.DefaultAddressProvider;
import com.google.code.ssm.providers.xmemcached.MemcacheClientFactoryImpl;
import com.google.code.ssm.providers.xmemcached.XMemcachedConfiguration;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author $user$
 * @version 1.0.0
 * @Description TODO
 * @createTime 2021/06/03
 */
@Configuration
public class MemCachierConfig extends AbstractSSMConfiguration {
    @Bean
    @Override
    public CacheFactory defaultMemcachedClient() {
        String serverString = System.getenv("MEMCACHIER_SERVERS").replace(",", " ");
        List<InetSocketAddress> servers = AddrUtil.getAddresses(serverString);
        AuthInfo authInfo = AuthInfo.plain(System.getenv("MEMCACHIER_USERNAME"),
            System.getenv("MEMCACHIER_PASSWORD"));
        Map<InetSocketAddress, AuthInfo> authInfoMap =
            new HashMap<InetSocketAddress, AuthInfo>();
        for(InetSocketAddress server : servers) {
            authInfoMap.put(server, authInfo);
        }

        final XMemcachedConfiguration conf = new XMemcachedConfiguration();
        conf.setUseBinaryProtocol(true);
        conf.setAuthInfoMap(authInfoMap);

        final CacheFactory cf = new CacheFactory();
        cf.setCacheClientFactory(new MemcacheClientFactoryImpl());
        cf.setAddressProvider(new DefaultAddressProvider(serverString));
        cf.setConfiguration(conf);
        return cf;
    }
}
