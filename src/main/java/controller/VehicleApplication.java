package controller;

import java.util.*;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/Vehicle-1.0")
public class VehicleApplication extends Application {

	public Set<Class<?>> getClasses() {
        Set<Class<?>> s = new HashSet<Class<?>>();
        s.add(controller.VehicleController.class);

        return s;
    }
    public Set<Object> getSingletons() {
        Set<Object> s = new HashSet<Object>();
        s.add(new controller.VehicleController());
        return s;
    }

}
