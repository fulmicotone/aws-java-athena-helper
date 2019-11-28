import com.fulmicotone.aws.athena.helper.business.MyAthena;
import com.fulmicotone.aws.athena.helper.models.AthenaPageResults;

import java.util.function.Consumer;

public class Test {


    String ATHENA_SAMPLE_QUERY = "SELECT \"client id\",\"product\""
            + " FROM \"mytable\""
            +" limit 10;";

    String ATHENA_DEFAULT_DATABASE = "MYDATABASE";

    String ATHENA_OUTPUT_BUCKET = "MYDATABASE";


    /**
     * remember to use
     * AWS_ACCESS_KEY_ID=x;AWS_SECRET_ACCESS_KEY=y
     * as env variables
     * @throws InterruptedException
     */
    // @org.junit.Test
    public void test1() throws InterruptedException {



        MyAthena.Builder
                .standard(ATHENA_DEFAULT_DATABASE,
                        ATHENA_OUTPUT_BUCKET)
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

    }


}





