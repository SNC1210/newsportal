package com.dlamps.newsportal.core.services;


import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.metatype.annotations.Designate;

import java.util.HashMap;
import java.util.Map;

@Component(service = NPUtilService.class)
public class NPUtilService {

    @Reference
    private ResourceResolverFactory factory;

    public ResourceResolver getResourceResolver() {
        ResourceResolver resolver = null;
        Map<String, Object> props = new HashMap<String, Object>();
        props.put(ResourceResolverFactory.SUBSERVICE, "npsubservice");//we have created yet the npservice system user
        try {
            resolver = factory.getServiceResourceResolver(props);
        } catch (LoginException e) {
            throw new RuntimeException(e);
        }
        return resolver;
    }
}
