package com.dlamps.newsportal.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.osgi.service.component.annotations.Component;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component(service = Servlet.class)
@SlingServletPaths(value = "/newsportal/recent-articles")
public class RecentArticlePathBasedServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {
        ResourceResolver resolver = request.getResourceResolver();
        Resource usersResource = resolver.getResource("/content/users/");

        JsonArrayBuilder userJsonList = Json.createArrayBuilder();//[]
        if (usersResource != null) {
            Iterator users = usersResource.listChildren();
            while (users.hasNext()) {
                Resource userResource = (Resource) users.next();
                ValueMap props = userResource.getValueMap();

                JsonObjectBuilder userJson = Json.createObjectBuilder();//{}
                userJson.add("firstName",props.get("firstName",String.class));
                userJson.add("lastName",props.get("lastName",String.class));
                userJson.add("email",props.get("email",String.class));
                userJson.add("phoneNumber",props.get("phoneNumber",String.class));
                userJsonList.add(userJson);
            }
        }

        response.setContentType("application/json");
        response.getWriter().write(userJsonList.build().toString());
    }

    @Override
    protected void doPost(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userID");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String password =  request.getParameter("password");



        ResourceResolver resolver = request.getResourceResolver();
        Resource usersResource = resolver.getResource("/content/users/");
        Resource userResource = resolver.getResource("/content/users/"+userID);

        if(usersResource != null && userResource == null) {
            Map<String,Object> props = new HashMap<>();
            props.put("firstName", firstName);
            props.put("lastName", lastName);
            props.put("email", email);
            props.put("phoneNumber", phoneNumber);
            props.put("password", password);

            resolver.create(usersResource,userID,props);
            resolver.commit();
            response.getWriter().write("User Creation Completed");
        }
        //response.getWriter().write("Message from doPOST RecentArticlePathBased Servlet");
        //response.getWriter().write("User was not created as the content path is not available");
    }

    @Override
    protected void doPut(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userID");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String phoneNumber = request.getParameter("phoneNumber");
        String password =  request.getParameter("password");



        ResourceResolver resolver = request.getResourceResolver();
        //Resource usersResource = resolver.getResource("/content/users");
        Resource userResource = resolver.getResource("/content/users/"+userID);


        if(userResource != null) {
           ModifiableValueMap mprop = userResource.adaptTo(ModifiableValueMap.class);
           if(firstName !=null)
               mprop.put("firstName", firstName);
           if(lastName !=null)
               mprop.put("lastName", lastName);
           if(email !=null)
               mprop.put("email", email);
           if(phoneNumber !=null)
               mprop.put("phoneNumber", phoneNumber);

           //mprop.remove("phoneNumber"); // using this we can remove the properties
           resolver.commit();
           response.getWriter().write("User Update Completed");
        }
        //response.getWriter().write("Message from doPUT RecentArticlePathBased Servlet");
    }

    @Override
    protected void doDelete(SlingHttpServletRequest request,SlingHttpServletResponse response) throws ServletException, IOException {

        ResourceResolver resolver = request.getResourceResolver();
        String userID = request.getParameter("userID");
        Resource userResource = resolver.getResource("/content/users/"+userID);

        if(userResource != null) {
            resolver.delete(userResource);
            resolver.commit();

        }
        response.getWriter().write("User Deleted Completed");
       // response.getWriter().write("User cannot be deleted ");
    }
}
