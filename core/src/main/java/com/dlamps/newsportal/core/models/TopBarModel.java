package com.dlamps.newsportal.core.models;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import java.util.List;

@Model(adaptables = Resource.class)
public class TopBarModel {

    @ValueMapValue
    private String email;

    @ValueMapValue
    private String mobile;

    @ChildResource
    List<RightNavItemModel> rightNav;

    public List<RightNavItemModel> getRightNav() {
        return rightNav;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }
}
