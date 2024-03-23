package com.github.cost98.custom.devservices.deployment;

import io.quarkus.deployment.IsNormal;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.annotations.BuildSteps;
import io.quarkus.deployment.builditem.DevServicesResultBuildItem;
import io.quarkus.deployment.builditem.LaunchModeBuildItem;
import io.quarkus.deployment.dev.devservices.GlobalDevServicesConfig;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.util.HashMap;


@Slf4j
@BuildSteps(onlyIfNot = IsNormal.class, onlyIf = GlobalDevServicesConfig.Enabled.class)
class CustomDevservicesProcessor {

    private static final String FEATURE = "custom-devservices1";

    private static volatile DevServicesResultBuildItem.RunningDevService devService;

    @BuildStep
    DevServicesResultBuildItem feature(LaunchModeBuildItem launchMode) {
        log.info("Custom DevServices - cost98");

        CustomContainer customContainer = new CustomContainer("postgres:11",false);

        customContainer.withStartupTimeout(Duration.ofSeconds(10));
        customContainer.start();
        devService = new DevServicesResultBuildItem.RunningDevService("container_name", customContainer.getContainerId(),
                customContainer::close, new HashMap<>());
        return devService.toBuildItem();
    }




}
