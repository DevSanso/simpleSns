package com.github.devsanso.sns.configure;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.HashMap;


@Configuration
public class BeanConfigure {
    @Bean(name = "excludeHttpMethod")
    @Scope("singleton")
    public HashMap<String, String[]> excludeHttpMethod() {
        HashMap<String, String[]> map = new HashMap<String,String[]>();
        map.put("/user", new String[]{"GET"});
        return map;
    }
}
