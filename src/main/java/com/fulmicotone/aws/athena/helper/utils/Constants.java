package com.fulmicotone.aws.athena.helper.utils;

public class Constants {

    public static final int CLIENT_EXECUTION_TIMEOUT = 100000;
    public static final String ATHENA_OUTPUT_BUCKET = "s3://aws-athena-query-results-886735004220-us-east-1/audiencerate/google/revenues";
    // This example demonstrates how to query a table created by the "Getting Started" tutorial in Athena
    public static final String ATHENA_SAMPLE_QUERY = "SELECT \"client id\",\"product\""
            + " FROM \"crawler-rt\".\"rev_revenues\""
            +" limit 10;";
    public static final long SLEEP_AMOUNT_IN_MS = 1000;
    public static final String ATHENA_DEFAULT_DATABASE = "crawler-rt";

}