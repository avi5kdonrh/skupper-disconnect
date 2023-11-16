package com.example;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

@Path("/register")
public class RegisterResource {

    @Channel("out-channel")
    Emitter<Profile> producerChannel;
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String register(Profile profile) throws Exception {
        producerChannel.send(profile).toCompletableFuture().join();
        return "Success";
    }
}
