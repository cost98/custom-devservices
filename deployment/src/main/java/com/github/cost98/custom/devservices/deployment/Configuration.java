package com.github.cost98.custom.devservices.deployment;

import io.quarkus.runtime.annotations.ConfigGroup;
import io.quarkus.runtime.annotations.ConfigPhase;
import io.quarkus.runtime.annotations.ConfigRoot;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithParentName;
import jakarta.inject.Inject;

import java.util.*;
import java.util.function.BooleanSupplier;

/**
 * This interface represents the configuration of custom Dev Services for Quarkus.
 * Dev Services are integrated services to simplify application development and testing,
 * such as databases, queues, caches, etc.
 * This interface defines configurations to enable/disable Dev Services and specify additional configurations
 * for custom services.
 */
@ConfigMapping(prefix = "quarkus.custom-dev-services")
@ConfigRoot(phase = ConfigPhase.BUILD_TIME)
public interface Configuration {

    /**
     * Indicates whether Dev Services are explicitly enabled.
     * Dev Services are generally enabled by default unless there is an existing configuration.
     *
     * @return true if Dev Services are enabled, otherwise false.
     */
    @WithDefault("true")
    boolean enabled();

    /**
     * Returns additional configurations for custom Dev Services.
     *
     * @return A map of additional configurations for custom services.
     */
    @WithParentName
    HashMap<String, DevServicesConfig> additionalDevServices();

    /**
     * This interface represents the configuration for a custom Dev Services.
     */
    @ConfigGroup
    interface DevServicesConfig {

        /**
         * Returns the timeout of the service if configured.
         *
         * @return An OptionalInt instance containing the timeout if specified.
         */
        OptionalInt timeout();

        /**
         * Returns the container image name to use for the service.
         *
         * @return The container image name.
         */
        String imageName();

        /**
         * Returns the list of mapped ports for the service.
         *
         * @return The list of mapped ports.
         */
        List<MappingPort> mappingPort();

        /**
         * Indicates whether the Redis server managed by Dev Services is shared.
         *
         * @return true if the Redis server is shared, otherwise false.
         */
        @WithDefault("true")
        boolean shared();

        /**
         * Returns the environment variables passed to the service's container.
         *
         * @return A map of environment variables.
         */
        Map<String, String> containerEnv();

        /**
         * This interface represents a mapped port for the service.
         */
        @ConfigGroup
        interface MappingPort {
            /**
             * Returns the internal port of the service.
             *
             * @return The internal port.
             */
            int portInternal();

            /**
             * Returns the name of the configuration if specified.
             *
             * @return An Optional instance containing the name of the configuration if specified.
             */
            Optional<String> nameConfig();

            /**
             * Returns the format of the configuration if specified.
             *
             * @return An Optional instance containing the format of the configuration if specified.
             */
            Optional<String> formatConfig();
        }
    }

    /**
     * This class provides a boolean evaluation to determine if Dev Services are enabled.
     */
    class Enabled implements BooleanSupplier {
        @Inject
        Configuration configuration;

        /**
         * Evaluates if Dev Services are enabled.
         *
         * @return true if Dev Services are enabled and additional configurations are present, otherwise false.
         */
        public boolean getAsBoolean() {
            return configuration.enabled() && !configuration.additionalDevServices().isEmpty();
        }
    }
}

