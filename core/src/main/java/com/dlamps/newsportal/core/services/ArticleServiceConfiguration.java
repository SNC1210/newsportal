package com.dlamps.newsportal.core.services;


import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
public @interface ArticleServiceConfiguration {
    @AttributeDefinition(name = "Article Rest API")
    public String articleApi() default "https://gorest.co.in/public/v2/users";

    @AttributeDefinition(name = "Status value")
    public boolean active() default true;

}
