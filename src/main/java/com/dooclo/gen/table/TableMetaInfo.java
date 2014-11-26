package com.dooclo.gen.table;

import com.dooclo.gen.base.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IvanMa on 2014/11/25.
 */
public class TableMetaInfo {

    private String dbName;
    private String tableName;
    private List<MetaData> metaDataList;
    private String className;

    public String getDbName() {
        return dbName;
    }

    public String getTableName() {
        return tableName;
    }

    public List<MetaData> getMetaDataList() {
        return metaDataList;
    }

    public String getClassName() {
        return className;
    }

    public TableMetaInfo() {
    }

    public TableMetaInfo(String dbName, String tableName) {
        this.dbName = dbName;
        this.tableName = tableName;
        className = tableName;
        if(tableName.indexOf(".") > 0){
            className = className.substring(tableName.indexOf(".") + 1,tableName.length());
        }
        className = className.toLowerCase();
        String[] classNameSplit = className.split("_");
        StringBuffer classNameBuffer = new StringBuffer("");
        for(int j = 0; j < classNameSplit.length; j++){
            String split = classNameSplit[j];
            split = split.replaceFirst(split.substring(0, 1), split.substring(0, 1).toUpperCase());
            classNameBuffer.append(split);
        }
        this.className = classNameBuffer.toString();
        System.out.println(className);
    }

    public void setMetaDataList() {
        String sql = "select * from " + this.tableName + " where 1=0";
        Connection conn = ConnectionManager.getConnection(this.dbName);
        try {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            int columnCount = rsmd.getColumnCount();
            if(columnCount > 0){
                metaDataList = new ArrayList<MetaData>();
            }
            for(int i = 1 ; i <= columnCount ; i++){
                MetaData md = new MetaData();
                String columnName = rsmd.getColumnName(i);
                md.setColumnName(columnName);
                String columnClassName = rsmd.getColumnClassName(i);
                md.setClassType(columnClassName);
                String columnTypeName = rsmd.getColumnTypeName(i);
                md.setClassType(columnTypeName);
                columnName = columnName.toLowerCase();
                String[] columnSplit = columnName.split("_");
                StringBuffer columnClassPropertyNameBuffer = new StringBuffer("");
                for(int j = 0; j < columnSplit.length; j++){
                    String split = columnSplit[j];
                    if(j > 0 ){
                        split = split.replaceFirst(split.substring(0, 1), split.substring(0, 1).toUpperCase());
                    }
                    columnClassPropertyNameBuffer.append(split);
                }
                md.setColumnClassPropertyName(columnClassPropertyNameBuffer.toString());
                System.out.println(columnClassPropertyNameBuffer.toString());
                metaDataList.add(md);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        TableMetaInfo tmi = new TableMetaInfo("wjods","HS_ASSET.BANKARG");
        tmi.setMetaDataList();
    }
}
