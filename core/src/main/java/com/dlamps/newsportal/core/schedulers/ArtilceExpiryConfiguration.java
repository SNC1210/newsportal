package com.dlamps.newsportal.core.schedulers;

import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;

@ObjectClassDefinition
public @interface ArtilceExpiryConfiguration {

    @AttributeDefinition (name ="Cron Expression")
    public String cronExpression() default "*/10 * * ? * *";

    @AttributeDefinition (name = "Scheduler Name")
    public String schedulerName() default "article-expiry";

    @AttributeDefinition (name = " Enable/Disable Scheduler...")
    public boolean enable() default true;
}
