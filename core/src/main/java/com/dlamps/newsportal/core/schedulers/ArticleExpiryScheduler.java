package com.dlamps.newsportal.core.schedulers;

import com.day.cq.replication.ReplicationActionType;
import com.day.cq.replication.ReplicationException;
import com.day.cq.replication.Replicator;
import com.day.cq.wcm.api.Page;
import com.day.cq.wcm.api.PageManager;
import com.dlamps.newsportal.core.services.NPUtilService;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.commons.scheduler.ScheduleOptions;
import org.apache.sling.commons.scheduler.Scheduler;
import org.osgi.service.component.annotations.*;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Session;
import javax.json.Json;
import javax.json.JsonObjectBuilder;
import java.util.Date;
import java.util.Iterator;

@Component(
        service = Runnable.class,
        immediate = true
)
@Designate(ocd= ArtilceExpiryConfiguration.class)
public class ArticleExpiryScheduler implements  Runnable {
    private final Logger LOG = LoggerFactory.getLogger(ArticleExpiryScheduler.class);

    @Reference
    NPUtilService npUtilService;

    @Reference
    Replicator replicator;// from this service we can activate and deactivate the page programatically

    @Reference
    Scheduler scheduler;

    @Activate
    @Modified
    public void activate(ArtilceExpiryConfiguration config) {
        if(config.enable())
        {
            ScheduleOptions options = scheduler.EXPR(config.cronExpression());
            options.name(config.schedulerName());
            options.canRunConcurrently(false);
            scheduler.schedule(this, options);
        }else {
            scheduler.unschedule(config.schedulerName());
        }
    }

    @Override
    public void run() {
        ResourceResolver resolver = npUtilService.getResourceResolver();
        PageManager pageManager = resolver.adaptTo(PageManager.class);
        Page articlePage = pageManager.getPage("/content/newsportal/us/en/article");
        if(articlePage != null) {
            Iterator<Page> childPages = articlePage.listChildren();
            while(childPages.hasNext()) {
                Page childPage = childPages.next();
                ValueMap pageProperties = childPage.getProperties();
                Date articleExpiryDate =  pageProperties.get("articleExpiry", Date.class);
                Date today = new Date();
                if(articleExpiryDate != null && articleExpiryDate.compareTo(today) < 0) {
                    //deactivate the page
                    try {
                        replicator.replicate(resolver.adaptTo(Session.class), ReplicationActionType.DEACTIVATE,childPage.getPath());
                    } catch (ReplicationException e) {
                        throw new RuntimeException(e);
                    }

                }
            }
        }
        LOG.info("ArticleExpiryScheduler started inside run method");
    }
}
