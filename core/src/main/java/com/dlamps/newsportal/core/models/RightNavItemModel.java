package com.dlamps.newsportal.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

@Model(adaptables = Resource.class)
public class RightNavItemModel {

    @ValueMapValue
    private String pageTitle;

    @ValueMapValue
    private String pagePath;

    public String getPageTitle() {
        return pageTitle;
    }

    public String getPagePath() {
        return pagePath;
    }
}
