package com.github.cost98.custom.devservices.deployment;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.utility.DockerImageName;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.net.ServerSocket;

public class CustomContainer extends GenericContainer<CustomContainer> {
    private DockerImageName dockerImageName;
    private boolean useSharedNetwork;
    public CustomContainer(String imageName, boolean useSharedNetwork){
        super(imageName);
        this.dockerImageName = DockerImageName.parse(imageName);
        this.useSharedNetwork = useSharedNetwork;

    }

    @Override
    protected void configure() {
        super.configure();

        //addEnv

        //addPort
    }

    private Integer findRandomPort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
