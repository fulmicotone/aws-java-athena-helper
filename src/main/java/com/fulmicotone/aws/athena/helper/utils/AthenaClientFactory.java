package com.fulmicotone.aws.athena.helper.utils;

import com.fulmicotone.aws.athena.helper.business.MyAthena;
import software.amazon.awssdk.auth.credentials.EnvironmentVariableCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.AthenaClientBuilder;

public class AthenaClientFactory {
    /**
     * AthenaClientClientBuilder to build Athena with the following properties:
     * - Set the region of the client
     * - Use the instance profile from the EC2 instance as the credentials provider
     * - Configure the client to increase the execution timeout.
     */
    private final AthenaClientBuilder builder = AthenaClient.builder()
            .region(Region.US_EAST_1)
            .credentialsProvider(EnvironmentVariableCredentialsProvider.create());

    public AthenaClient createClient(MyAthena athena) {
        return builder
                .build();
    }
}