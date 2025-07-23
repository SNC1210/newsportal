package com.dlamps.newsportal.core.models;

import com.dlamps.newsportal.core.services.ArticleService;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.Optional;
import org.apache.sling.models.annotations.Required;
import org.apache.sling.models.annotations.injectorspecific.*;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.inject.Named;
import java.util.Date;
import java.util.List;

@Model(
        adaptables = {Resource.class, SlingHttpServletRequest.class},
        defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
public class ArticlesDetailsModel {

    @ValueMapValue
    private String articleTitle;

    @ValueMapValue
    private String articleDesc;

    @ValueMapValue
    private String articleImage;

    @ValueMapValue(injectionStrategy = InjectionStrategy.OPTIONAL)
    //@Required
    //@Optional
    private Date articleExpiry;

    @ValueMapValue(name="sling:resourceType")
    //@Named("sling:resourceType") // this is also another way of mapping the name to java variable
    private String slingResourceType;//since we cannot use the sling:resourceType as java variable so we mapping the name here.

    private boolean articleIsExpired = false;

    @OSGiService
    ArticleService articleService;

    @SlingObject
    ResourceResolver resolver;

    @ScriptVariable
    ValueMap pageProperties;

    private String  articles;

    private String pageTitle;

    @ChildResource
    List<RelatedArticleItemModel> relatedArticles;

    @Self
    HelloWorldModel helloWorldModel;

    @RequestAttribute
    private String articleType;

    @ResourcePath(path = "/content/newsportal/us/en/articles/jcr:content")
    private Resource articleResource;


    @PostConstruct
    public void init() {
        Date Todaydate = new Date();
        if(articleExpiry != null && articleExpiry.compareTo(Todaydate) < 0) {
            articleIsExpired=true;
        }
        articles = articleService.getArticles();
        pageTitle=pageProperties.get("jcr:title", String.class);
    }

    public String getArticleTitle() {
        return articleTitle;
    }

    public Date getArticleExpiry() {
        return articleExpiry;
    }

    public String getArticleImage() {
        return articleImage;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public String getSlingResourceType() {
        return slingResourceType;
    }

    public boolean isArticleIsExpired() {
        return articleIsExpired;
    }

    public String getArticles() {
        return articles;
    }

    public String getPageTitle() {
        return pageTitle;
    }

    public List<RelatedArticleItemModel> getRelatedArticles() {
        return relatedArticles;
    }

    public String getArticleType() {
        return articleType;
    }

    public Resource getArticleResource() {
        return articleResource;
    }
}
