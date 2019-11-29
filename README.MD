**AWS ATHENA SDK JAVA API**

            MyAthena.Builder.standard(ATHENA_DEFAULT_DATABASE,ATHENA_OUTPUT_BUCKET)
         .build()
         .newQuery(ATHENA_SAMPLE_QUERY)
         .submit()
         .waitForQueryToComplete()
         .processResultRows(5, new Consumer<AthenaPageResults>() {
             @Override
             public void accept(AthenaPageResults athenaPageResults) {
  
                 System.out.println("page:"+athenaPageResults.getPage());
 
             }
         });
         
        
Right now is only a simply way to makes query to Athena through amazon sdk
every improvement is welcome
thanks         

[![](https://jitpack.io/v/fulmicotone/aws-java-athena-helper.svg)](https://jitpack.io/#fulmicotone/aws-java-athena-helper)