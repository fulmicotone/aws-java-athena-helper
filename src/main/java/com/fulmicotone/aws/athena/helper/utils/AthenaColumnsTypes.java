package com.fulmicotone.aws.athena.helper.utils;

import java.util.stream.Stream;

public enum AthenaColumnsTypes {
    mytinyint("tinyint"),
    mysmallint("smallint"),
    myinteger("integer"),
    mybigint("bigint"),
    mydouble("double"),
    myboolean("boolean"),
    mydate("date"),
    mytimestamp("timestamp"),
    myunknown("");

    private final String label;
      AthenaColumnsTypes(String label) {
        this.label=label;
    }


public AthenaColumnsTypes getFromLabel(String label){

         return Stream.of(values())
                  .filter(item-> label.equals(item.label))
                  .findFirst().orElse(myunknown);
}


}
