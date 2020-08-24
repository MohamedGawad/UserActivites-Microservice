package bsu.chatbot.useractivites.helper;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ApplicationHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse
                .named("application-check")
                .up()
                .withData("CPU Available", Runtime.getRuntime().availableProcessors())
                .withData( "Memory Free", Runtime.getRuntime().freeMemory())
                .withData("Total Memory", Runtime.getRuntime().totalMemory())
                .build();
    }

}