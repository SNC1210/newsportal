package com.dlamps.newsportal.core.models;

import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import javax.annotation.Resource;

@Model(adaptables = Resource.class,
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class RelatedArticleItemModel {

    @ValueMapValue
    private String articleTitle;

    @ValueMapValue
    private String articlePath;

    public String getArticlePath() {
        return articlePath;
    }

    public String getArticleTitle() {
        return articleTitle;
    }
}
