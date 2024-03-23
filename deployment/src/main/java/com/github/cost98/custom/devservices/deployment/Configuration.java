package com.github.cost98.custom.devservices.deployment;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;
import io.smallrye.config.WithParentName;

import java.util.List;
import java.util.Map;
import java.util.OptionalInt;

@ConfigMapping(prefix = "quarkus.custom-dev-services")
@ConfigRoot(phase = ConfigPhase.BUILD_TIME)
public interface Configuration {




    /**
     * Whether a health check is published in case the smallrye-health extension is present.
     */
    @WithName("health.enabled")
    @WithDefault("true")
    boolean healthEnabled();


    /**
     * Additional dev services configurations
     */
    @WithParentName
    List<DevServicesConfig> additionalDevServices();


    @ConfigGroup
    public interface DevServicesConfig {

        /**
         * If DevServices has been explicitly enabled or disabled. DevServices is generally enabled
         * by default, unless there is an existing configuration present.
         * <p>
         * When DevServices is enabled Quarkus will attempt to automatically configure and start
         * a database when running in Dev or Test mode and when Docker is running.
         */
        @WithDefault("true")
        boolean enabled();

        /**
         * The container image name to use, for container based DevServices providers.
         * If you want to use Redis Stack modules (bloom, graph, search...), use:
         * {@code redis/redis-stack:latest}.
         */
        String imageName();

        /**
         * Optional fixed port the dev service will listen to.
         * <p>
         * If not defined, the port will be chosen randomly.
         */
        OptionalInt port();


        /**
         * TODO
         */
        List<MappingPort> mappingPort();

        /**
         * Indicates if the Redis server managed by Quarkus Dev Services is shared.
         * When shared, Quarkus looks for running containers using label-based service discovery.
         * If a matching container is found, it is used, and so a second one is not started.
         * Otherwise, Dev Services for Redis starts a new container.
         * <p>
         * The discovery uses the {@code quarkus-dev-service-redis} label.
         * The value is configured using the {@code service-name} property.
         * <p>
         * Container sharing is only used in dev mode.
         */
        @WithDefault("true")
        boolean shared();

        /**
         * The value of the {@code quarkus-dev-service-redis} label attached to the started container.
         * This property is used when {@code shared} is set to {@code true}.
         * In this case, before starting a container, Dev Services for Redis looks for a container with the
         * {@code quarkus-dev-service-redis} label
         * set to the configured value. If found, it will use this container instead of starting a new one. Otherwise, it
         * starts a new container with the {@code quarkus-dev-service-redis} label set to the specified value.
         * <p>
         * This property is used when you need multiple shared Redis servers.
         */
        String serviceName();

        /**
         * Environment variables that are passed to the container.
         */
        Map<String, String> containerEnv();


        @ConfigGroup
        interface MappingPort {
            /**
             * TODO
             */
            int portInternal();

            /**
             * TODO
             */
            OptionalInt portExposed();

            /**
             * TODO
             */
            String internetProtocol();

        }
    }
}
