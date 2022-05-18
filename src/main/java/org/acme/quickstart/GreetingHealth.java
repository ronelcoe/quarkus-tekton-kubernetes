package org.acme.quickstart;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@Liveness
@ApplicationScoped
public class GreetingHealth {

	public HealthCheckResponse call() {
		return HealthCheckResponse.up("Liveness health check");
	}

}
