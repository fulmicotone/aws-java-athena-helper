package com.fulmicotone.aws.athena.helper.business;

import software.amazon.awssdk.services.athena.model.StartQueryExecutionRequest;

public class MyAthenaQuery {

    private final StartQueryExecutionRequest request;
    private final MyAthena myAthenaClient;


    public MyAthenaQuery(MyAthena myAthenaClient, StartQueryExecutionRequest request) {
        this.request = request;
        this.myAthenaClient=myAthenaClient;
    }

    public MyAthenaResponse submit(){
          return  new MyAthenaResponse(
                  myAthenaClient,
                  request,
                  myAthenaClient.athenaClient.startQueryExecution(request));
    }





}
