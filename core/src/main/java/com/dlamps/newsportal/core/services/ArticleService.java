package com.dlamps.newsportal.core.services;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.Designate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component(service = ArticleService.class)
@Designate(ocd = ArticleServiceConfiguration.class)
public class ArticleService {
private static final Logger LOG = LoggerFactory.getLogger(ArticleService.class);

@Activate
public void init(ArticleServiceConfiguration config) {
    LOG.info("Inside Article service activated");
}

@Deactivate
public void deactivate(ArticleServiceConfiguration config) {
    LOG.info("Inside Article service deactivated");
}

@Modified
public void update(ArticleServiceConfiguration config) {
    LOG.info("updated Configuration: {}",config.articleApi());
    LOG.info("Inside Article service modified");
}
public String getArticles() {
    //https://gorest.co.in/public/v2/users
        return "Articles from REST API";
}
}
