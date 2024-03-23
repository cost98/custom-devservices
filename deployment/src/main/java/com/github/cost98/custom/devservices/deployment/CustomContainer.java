package com.github.cost98.custom.devservices.deployment;

import lombok.extern.slf4j.Slf4j;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.InternetProtocol;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;

@Slf4j
public class CustomContainer extends GenericContainer<CustomContainer> {
    private final Configuration.DevServicesConfig devServicesConfig;

    public CustomContainer(Configuration.DevServicesConfig devServicesConfig) {
        super(devServicesConfig.imageName());
        log.info("Create container " + getContainerId());
        this.devServicesConfig = devServicesConfig;
    }

    @Override
    protected void configure() {
        super.configure();
        log.info("Set env on container " + getContainerId());
        devServicesConfig.containerEnv().forEach(this::addEnv);
        devServicesConfig.mappingPort().forEach(this::mapPort);
    }

    private void mapPort(Configuration.DevServicesConfig.MappingPort mappingPort) {
        int port = mappingPort.portExposed().orElse(findRandomPort());
        InternetProtocol internetProtocol = InternetProtocol.valueOf(mappingPort.internetProtocol());
        addFixedExposedPort(port, mappingPort.portInternal(), internetProtocol);
    }

    private Integer findRandomPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public String toString() {
        return "ContainerId: " + super.getContainerId() + " image: " + getDockerImageName() + "\n"
                + "env: " + getEnv();
    }
}
