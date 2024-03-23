package com.github.cost98.custom.devservices.deployment;

import io.quarkus.deployment.IsNormal;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.BuildSteps;
import io.quarkus.deployment.builditem.DevServicesResultBuildItem;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;


@Slf4j
@BuildSteps(onlyIfNot = IsNormal.class, onlyIf = {Configuration.Enabled.class})
class CustomDevServicesProcessor {

    private static final String FEATURE = "custom-devservices1";

    private static volatile DevServicesResultBuildItem.RunningDevService devService;


    @Inject
    Configuration configuration;


    @BuildStep
    DevServicesResultBuildItem feature() {
        log.info("Custom DevServices - cost98 all 1");
        Configuration.DevServicesConfig devServicesConfig = configuration.additionalDevServices().get(0);
        try (CustomContainer customContainer = new CustomContainer(devServicesConfig)) {
            customContainer.withStartupTimeout(Duration.ofSeconds(devServicesConfig.timeout().orElse(10)));
            customContainer.start();
            log.info(customContainer.toString());
            devService = new DevServicesResultBuildItem.RunningDevService(devServicesConfig.serviceName(), customContainer.getContainerId(), customContainer::close, getConfig(devServicesConfig, customContainer));
            return devService.toBuildItem();
        }
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
