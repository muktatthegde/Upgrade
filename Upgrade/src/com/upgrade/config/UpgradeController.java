package com.upgrade.config;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import io.swagger.jaxrs.config.BeanConfig;
import main.java.com.upgrade.service.UserService;


public class UpgradeController extends Application{

	HashSet<Object> singletons = new HashSet<Object>();

    public UpgradeController() {
          BeanConfig config = new BeanConfig();
          config.setConfigId("Upgrade API"); // Update your Code
          config.setTitle("Upgrade API"); // Update your Code
          config.setVersion("1.0"); // Update your Code
          config.setBasePath("/upgrade/v1"); // Update your Code
          config.setResourcePackage("main.java.com.upgrade"); // Update your Code
          config.setPrettyPrint(true);
          config.setScan(true);
         
    }
@Override
    public Set<Class<?>> getClasses() {
          HashSet<Class<?>> set = new HashSet<Class<?>>();

          set.add(ResourceTest.class); // Update your B2C Resource Code
          
          set.add(io.swagger.jaxrs.listing.ApiListingResource.class);
          set.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);
        
          return set;
    }
@Override
    public Set<Object> getSingletons() {
		    return singletons;
    }
}
