package com.fulmicotone.aws.athena.helper.models;

import software.amazon.awssdk.services.athena.model.ColumnInfo;
import software.amazon.awssdk.services.athena.model.Row;

import java.util.List;

public class AthenaPageResults {

    private int page;
    private List<ColumnInfo> columnInfoList;
    private List<Row> rows;

    public AthenaPageResults(int page, List<ColumnInfo> columnInfoList, List<Row> rows) {
        this.page = page;
        this.columnInfoList = columnInfoList;
        this.rows = rows;
    }

    public AthenaPageResults(){}


    public int getPage() { return page; }

    public List<ColumnInfo> getColumnInfoList() { return columnInfoList; }

    public List<Row> getRows() { return rows; }


}
