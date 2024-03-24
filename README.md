[![Quarkus](https://design.jboss.org/quarkus/logo/final/PNG/quarkus_logo_horizontal_rgb_1280px_default.png#gh-light-mode-only)](https://quarkus.io/#gh-light-mode-only)
[![Quarkus](https://design.jboss.org/quarkus/logo/final/PNG/quarkus_logo_horizontal_rgb_1280px_reverse.png#gh-dark-mode-only)](https://quarkus.io/#gh-dark-mode-only)

# Custom Dev Services per Quarkus
![Version](https://img.shields.io/github/v/release/cost98/custom-devservices?style=for-the-badge)
[![Commits](https://img.shields.io/github/commit-activity/m/cost98/custom-devservices.svg?label=commits&style=for-the-badge&logo=git&logoColor=white)](https://github.com/cost98/custom-devservices/pulse)
[![License](https://img.shields.io/github/license/cost98/custom-devservices?style=for-the-badge&logo=apache)](https://www.apache.org/licenses/LICENSE-2.0)
[![Supported JVM Versions](https://img.shields.io/badge/JVM-17--21-brightgreen.svg?style=for-the-badge&logo=openjdk)](https://github.com/cost98/custom-devservices/actions/runs/113853915/)
[![GitHub Repo stars](https://img.shields.io/github/stars/cost98/custom-devservices?style=for-the-badge)](https://github.com/cost98/custom-devservices/stargazers)

Custom Dev Services in Quarkus are designed to simplify the development and testing of applications by integrating external services such as databases, queues, caches, etc., directly into the development flow. This documentation will provide an overview of the custom configuration module for Dev Services in Quarkus, allowing developers to easily integrate their custom services.

## Overview
The custom Dev Services configuration module in Quarkus allows developers to specify additional configurations for custom services, in addition to those automatically managed by Quarkus. This module offers the flexibility to integrate any type of custom service within the development or testing environment of the application.

## Configuration
The custom Dev Services configuration module is based on annotations provided by Quarkus and SmallRye Config. Configurations can be specified in the application.properties file or via environment variables.
### Enabling Custom Dev Services
Custom Dev Services can be explicitly enabled or disabled using the following property:
``` properties
quarkus.custom-dev-services.enabled=true
```

### Additional Services Configuration
To specify additional configurations for custom services, you can use the following syntax in the configuration file:
``` properties
quarkus.custom-dev-services.additional-dev-services.<service-name>.<property>=<value>
```

Where:
- **service-name** is the name of the custom service.
- **property** is the property to configure for the service.
- **value** is the value of the property.

### Configuration Example
Here's an example of configuring a custom Redis service within the development environment:
``` properties
quarkus.custom-dev-services.additional-dev-services.redis.image-name=redis/redis-stack:latest
quarkus.custom-dev-services.additional-dev-services.redis.mapping-port[0].port-internal=6379
quarkus.custom-dev-services.additional-dev-services.redis.mapping-port[0].name-config=redis
quarkus.custom-dev-services.additional-dev-services.redis.shared=true
quarkus.custom-dev-services.additional-dev-services.redis.container-env.REDIS_PASSWORD=secret
```

This example configures a Redis service with a specific Docker image, maps internal port 6379 to the configuration name "redis", enables container sharing, and sets an environment variable REDIS_PASSWORD with the value "secret".


## Conclusion
The custom Dev Services configuration module offers extensive flexibility to integrate external services within the development or testing environment of Quarkus applications. By following this guide, developers can easily configure and manage their custom services to enhance the development and testing process of Quarkus applications.
