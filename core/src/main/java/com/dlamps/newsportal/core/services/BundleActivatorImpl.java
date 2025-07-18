package com.dlamps.newsportal.core.services;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class BundleActivatorImpl implements BundleActivator {
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        System.out.println("🔥 Bundle Started: " + bundleContext.getBundle().getSymbolicName());
    }

    @Override
    public void stop(BundleContext bundleContext) throws Exception {
        System.out.println("🛑 Bundle Stopped: " + bundleContext.getBundle().getSymbolicName());
    }
}
