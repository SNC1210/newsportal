package com.dlamps.newsportal.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import javax.json.Json;
import javax.json.JsonObjectBuilder;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component(service = Servlet.class)
@SlingServletPaths(value="/newsportal/services/recent-articles-new")
public class RecentArticleServletNew extends SlingAllMethodsServlet {

    @Override
    protected void doGet( SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {

        ResourceResolver resolver = request.getResourceResolver();
        Resource recentArticleResource = resolver.getResource("/content/newsportal/recent-articles");

        ValueMap props = recentArticleResource.getValueMap();
        String resourceType = props.get("sling:resourceType",String.class);
        String type = (String) props.get("type");
        String limit = props.get("limit","5");

        JsonObjectBuilder nodeProps = Json.createObjectBuilder();
        nodeProps.add("resourceType", resourceType);
        nodeProps.add("type", type);
        nodeProps.add("limit", limit);

        response.getWriter().write(nodeProps.build().toString());



    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {

        ResourceResolver resolver = request.getResourceResolver();
        Resource userResource = resolver.getResource("/content/newsportal/users");

        String userId = request.getParameter("userId");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String password = request.getParameter("password");

        if(userResource != null) {
            Map<String,Object> userProps = new HashMap<>();
            userProps.put("userId", userId);
            userProps.put("email", email);
            userProps.put("firstName", firstName);
            userProps.put("lastName", lastName);
            userProps.put("password", password);
            resolver.create(userResource,userId,userProps);
            resolver.commit();
        }

        response.getWriter().write("User Created Successfully");

    }

    @Override
    protected void doPut(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resolver = request.getResourceResolver();
        Resource userResource = resolver.getResource("/content/newsportal/users");

        String userId = request.getParameter("userId");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");

        Resource userIdResource = resolver.getResource("/content/newsportal/users/"+userId);
        if(userIdResource != null && userResource != null) {
            ModifiableValueMap mProp = userIdResource.adaptTo(ModifiableValueMap.class);
            if(firstName != null)
                mProp.put("firstName", firstName);
            if(lastName != null)
            mProp.put("lastName", lastName);

            //mProp.remove("email");
            resolver.commit();
            response.getWriter().write("User Updated Successfully");
        }
        else {
            response.getWriter().write("user does not exist");
        }
        response.getWriter().write("Unable to update user");

    }

    @Override
    protected void doDelete(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resolver = request.getResourceResolver();
        Resource userResource = resolver.getResource("/content/newsportal/users");
        String userId = request.getParameter("userId");

        Resource userIdResource = resolver.getResource("/content/newsportal/users/"+userId);
        if(userIdResource != null) {
            resolver.delete(userIdResource);
            resolver.commit();
            response.getWriter().write("User Deleted Successfully");
        }
        else {
            response.getWriter().write("user does not exist");
        }

        response.getWriter().write("Unable to delete user");
    }
}
