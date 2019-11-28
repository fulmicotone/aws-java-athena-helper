package com.fulmicotone.aws.athena.helper.business;


import com.fulmicotone.aws.athena.helper.models.AthenaPageResults;
import software.amazon.awssdk.services.athena.model.*;
import software.amazon.awssdk.services.athena.paginators.GetQueryResultsIterable;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class MyAthenaResponse {

    private final static  int max_response_page_size=-1;

    private final MyAthena myAthena;

    private final StartQueryExecutionResponse response;

    private final StartQueryExecutionRequest request;

    private StartQueryExecutionResponse startQueryExecutionResponse;

    public MyAthenaResponse(MyAthena myAthena,
                            StartQueryExecutionRequest request,
                            StartQueryExecutionResponse response) {
        this.response = response;
        this.request=request;
        this.myAthena=myAthena;

    }


    public StartQueryExecutionRequest getRequest() { return request; }

    public StartQueryExecutionResponse getResponse() { return response; }

    public MyAthenaResponse waitForQueryToComplete() throws InterruptedException {

        GetQueryExecutionRequest getQueryExecutionRequest = GetQueryExecutionRequest
                .builder()
                .queryExecutionId(response.queryExecutionId())
                .build();

        GetQueryExecutionResponse getQueryExecutionResponse;
        boolean isQueryStillRunning = true;
        while (isQueryStillRunning) {
            getQueryExecutionResponse = myAthena.athenaClient.getQueryExecution(getQueryExecutionRequest);
            String queryState = getQueryExecutionResponse.queryExecution().status().state().toString();
            if (queryState.equals(QueryExecutionState.FAILED.toString())) {
                throw new RuntimeException("Query Failed to run with Error Message: " + getQueryExecutionResponse
                        .queryExecution().status().stateChangeReason());
            } else if (queryState.equals(QueryExecutionState.CANCELLED.toString())) {
                throw new RuntimeException("Query was cancelled.");
            } else if (queryState.equals(QueryExecutionState.SUCCEEDED.toString())) {
                isQueryStillRunning = false;
            } else {
                // Sleep an amount of time before retrying again.
                Thread.sleep(myAthena.retrySleepAmountMS);
            }
            System.out.println("Current Status is: " + queryState);
        }
          return  this;

    }

    public MyAthenaResponse processResultRows(){ return this.processResultRows(max_response_page_size,null); }

    public MyAthenaResponse processResultRows(int maxResultsPerPage, Consumer<
            AthenaPageResults> consumer){

        GetQueryResultsRequest.Builder queryRequestBuilder=null;

        GetQueryResultsRequest queryRequest;

        String nextToken=null;


        int page=0;

       queryRequestBuilder = GetQueryResultsRequest.builder().queryExecutionId(response.queryExecutionId());


             if(maxResultsPerPage!=max_response_page_size){
                 queryRequestBuilder=queryRequestBuilder.maxResults(maxResultsPerPage);
             }

            queryRequest= queryRequestBuilder.build();

            GetQueryResultsIterable getQueryResultsResults = myAthena.athenaClient
                    .getQueryResultsPaginator(queryRequest);



            for (GetQueryResultsResponse Resultresult : getQueryResultsResults) {

                List<ColumnInfo> columnInfoList = Resultresult.resultSet().resultSetMetadata().columnInfo();

                List<Row> results = Resultresult
                        .resultSet()
                        .rows();

                if(consumer!=null){ consumer.accept(new AthenaPageResults(page,columnInfoList,results));}

                page++;

                // processRow(results, columnInfoList);
            }





        return this;
    }





}
