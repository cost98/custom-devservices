package com.github.cost98.custom.devservices.deployment;

import io.quarkus.deployment.IsNormal;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.BuildSteps;
import io.quarkus.deployment.builditem.DevServicesResultBuildItem;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;


@Slf4j
@BuildSteps(onlyIfNot = IsNormal.class, onlyIf = Configuration.Enabled.class)
class CustomDevServicesProcessor {

    private static volatile DevServicesResultBuildItem.RunningDevService devService;

    @Inject
    Configuration configuration;

    @BuildStep
    List<DevServicesResultBuildItem> feature() {
        log.info("Custom DevServices - cost98");
        return configuration.additionalDevServices()
                .stream()
                .map(this::getDevBuildItem)
                .toList();
    }

    private DevServicesResultBuildItem getDevBuildItem(Configuration.DevServicesConfig devServicesConfig){
        CustomContainer customContainer = new CustomContainer(devServicesConfig);
        customContainer.withStartupTimeout(Duration.ofSeconds(10));
        customContainer.start();
        log.info(customContainer.toString());
        devService = new DevServicesResultBuildItem.RunningDevService(devServicesConfig.serviceName(), customContainer.getContainerId(), customContainer::close, getConfig(devServicesConfig, customContainer));
        return devService.toBuildItem();
    }

    private HashMap<String, String> getConfig(Configuration.DevServicesConfig devServicesConfig, CustomContainer customContainer) {
        HashMap<String, String> configMap = new HashMap<>();
        devServicesConfig.mappingPort().forEach(mappingPort -> {
            if (mappingPort.nameConfig().isPresent()) {
                String format = mappingPort.formatConfig().orElse("%s:%s");
                String valueConfig = String.format(format, customContainer.getHost(), customContainer.getContainerId());
                configMap.put(mappingPort.nameConfig().get(), valueConfig);
            }
        });
        return configMap;
    }


}