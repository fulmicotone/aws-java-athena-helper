package com.fulmicotone.aws.athena.helper.business;

import com.fulmicotone.aws.athena.helper.utils.AthenaClientFactory;
import software.amazon.awssdk.services.athena.AthenaClient;
import software.amazon.awssdk.services.athena.model.QueryExecutionContext;
import software.amazon.awssdk.services.athena.model.ResultConfiguration;
import software.amazon.awssdk.services.athena.model.StartQueryExecutionRequest;

import java.util.Objects;


public class MyAthena {

    protected final  QueryExecutionContext ctx;

    protected final  ResultConfiguration resultConfiguration;

    protected final AthenaClient athenaClient;

    protected final long retrySleepAmountMS =1000;

    protected final long clientExecutionAmountMS =100_000;

    private MyAthena(QueryExecutionContext ctx,
                     ResultConfiguration resultConfiguration,
                     AthenaClient athenaClient){
        this.ctx=ctx;
        this.resultConfiguration=resultConfiguration;
        this.athenaClient=athenaClient==null? new AthenaClientFactory()
                .createClient(this):athenaClient;

    }

   public  MyAthenaQuery newQuery(String query,QueryExecutionContext ctx,ResultConfiguration resultConfig){

        return new MyAthenaQuery(this,
                        StartQueryExecutionRequest
                        .builder()
                        .queryString(query)
                        .queryExecutionContext(ctx)
                        .resultConfiguration(resultConfig).build());
    }

    public MyAthenaQuery newQuery(String query){

        Objects.requireNonNull(this.ctx, "context is required");
        Objects.requireNonNull(this.resultConfiguration, "result configuration is required");
        return newQuery(query,this.ctx,this.resultConfiguration);}






    public static final class Builder {

        protected QueryExecutionContext ctx;

        protected ResultConfiguration resultConfiguration;

        protected AthenaClient athenaClient;

        private Builder() { }


        public static Builder standard(String athenaDatabase,String athenaOutputBucket) {

            return new Builder()
                    .withCtx(QueryExecutionContext.builder()
                            .database(athenaDatabase).build())
                    .withResultConfiguration(   ResultConfiguration.builder()
                    // You can provide encryption options for the output that is written.
                    // .withEncryptionConfiguration(encryptionConfiguration)
                    .outputLocation(athenaOutputBucket)
                    .build()); }

        public static Builder newOne() { return new Builder(); }

        public Builder withCtx(QueryExecutionContext ctx) {
            this.ctx = ctx;
            return this;
        }

        public Builder withResultConfiguration(ResultConfiguration resultConfiguration) {
            this.resultConfiguration = resultConfiguration;
            return this;
        }

        public Builder withAthenaClient(AthenaClient athenaClient) {
            this.athenaClient = athenaClient;
            return this;
        }

        public MyAthena build() {
            return new MyAthena(
                    ctx,
                    resultConfiguration,
                    athenaClient);
        }
    }


}
