package com.dlamps.newsportal.core.servlets;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.apache.sling.api.servlets.SlingSafeMethodsServlet;
import org.apache.sling.servlets.annotations.SlingServletPaths;
import org.apache.sling.servlets.annotations.SlingServletResourceTypes;
import org.osgi.service.component.annotations.Component;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import java.io.IOException;

@Component(service = Servlet.class)
//@SlingServletPaths(value = {"/newsportal/recent-articles","/newsportal/feature-articles"})
@SlingServletResourceTypes(
        resourceTypes = "newsportal/services/recent-articles",
        methods= {HttpConstants.METHOD_GET,HttpConstants.METHOD_POST},
        extensions = {"json","xml"},
        selectors = {"recent","popular","featured"}
)
public class RecentArticleServlet extends SlingAllMethodsServlet {

    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("Message from doGet Method .... ");
    }

    @Override
    protected void doPost(SlingHttpServletRequest request, SlingHttpServletResponse response) throws ServletException, IOException {
        response.getWriter().write("Message from doPost Method .... ");
    }
}
