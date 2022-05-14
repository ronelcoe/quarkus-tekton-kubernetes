package org.acme.quickstart;

import javax.enterprise.event.Observes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@Path("/hello")
public class GreetingResource {
	
	private static final Logger log = Logger.getLogger(GreetingResource.class);
	
	@ConfigProperty(name = "cm.name")
	String cmName;

    void onStart(@Observes StartupEvent ev) {               
		log.info("GreetingResource started");
	}

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello Quarkus";
    }
    
	@GET
	@Path("/{name}")
	@Produces(MediaType.TEXT_PLAIN)
	public String helloParam(@PathParam("name") String name) {
		return "Hello " + name;
	}
	
	@GET
	@Path("/cm")
	@Produces(MediaType.TEXT_PLAIN)
	public String cm() {
		return "Hello " + cmName;
	}
    
	void onStop(@Observes ShutdownEvent ev) {               
		log.info("GreetingResource Service stopped");
	}
}