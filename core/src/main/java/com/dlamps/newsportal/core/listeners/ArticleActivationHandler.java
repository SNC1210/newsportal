package com.dlamps.newsportal.core.listeners;

import com.day.cq.replication.ReplicationAction;
import com.dlamps.newsportal.core.services.NPUtilService;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.PersistenceException;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventConstants;
import org.osgi.service.event.EventHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Component(service = EventHandler.class,
        property = {
                EventConstants.EVENT_TOPIC+"="+ReplicationAction.EVENT_TOPIC,
                EventConstants.EVENT_FILTER+"= (& (type=ACTIVATE) (paths=/content/newsportal/us/en/article/*))"
        },
        immediate=true

)
public class ArticleActivationHandler implements EventHandler {
    private final Logger LOG = LoggerFactory.getLogger(ArticleActivationHandler.class);

    @Reference
    NPUtilService npUtilService;


    @Override
    public void handleEvent(Event event) {
        String[] paths = (String[]) event.getProperty("paths");
        ResourceResolver resolver = npUtilService.getResourceResolver();//npUtilService wont work here as we don't have service/System user created

        for(String path : paths) {
            Resource contentResource = resolver.getResource(path+"/jcr:content");
            ModifiableValueMap mProp = contentResource.adaptTo(ModifiableValueMap.class);
            mProp.put("pageActivated", true);

        }
        try {
            resolver.commit();
        } catch (PersistenceException e) {
            throw new RuntimeException(e);
        }
        LOG.info("Inside Handle Event Method");
    }
}
