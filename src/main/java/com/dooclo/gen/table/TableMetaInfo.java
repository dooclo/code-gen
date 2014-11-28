package com.dooclo.gen.table;

import com.dooclo.gen.jdbc.ConnectionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by IvanMa on 2014/11/25.
 */
public class TableMetaInfo {

    private String dbName;
    private String tableName;
    private List<MetaData> metaDataList;
    private String className;
    private Set<String> propertyTypeSet;

    public Set<String> getPropertyTypeSet() {
        return propertyTypeSet;
    }

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
            className = className.substring(tableName.lastIndexOf(".") + 1,tableName.length());
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
        setMetaDataList();
    }

    public void setMetaDataList() {
        String sql = "select * from " + this.tableName + " where 1=0";
        Connection conn = ConnectionManager.getConnection(this.dbName);
        try {
            DatabaseMetaData dmd = conn.getMetaData();
            Set<String> keys = primaryKeys(dmd,this.tableName);
            if(keys == null || keys.size() < 1){
                System.out.println("该表无主键");
                keys = uniqueIndex(dmd,this.tableName);
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            if(columnCount > 0){
                metaDataList = new ArrayList<MetaData>();
                propertyTypeSet = new HashSet<String>();
            }
            for(int i = 1 ; i <= columnCount ; i++){
                MetaData md = new MetaData();
                String columnName = rsmd.getColumnName(i);
                md.setColumnName(columnName);
                String columnClassName = rsmd.getColumnClassName(i);
                md.setClassType(columnClassName);
                String columnTypeName = rsmd.getColumnTypeName(i);
                md.setDataType(columnTypeName);
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
                md.setKeyFlag(keys.contains(columnName));
                metaDataList.add(md);
                propertyTypeSet.add(columnClassName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Set<String> uniqueIndex(DatabaseMetaData dmd, String tableName){
        Set<String> uniqueIndexs = new HashSet<String>();
        try {
            String schameName = null;
            String tableNameReal = tableName;
            if(tableName.indexOf(".") > 0){
                String [] tableNameSplit = tableName.split("\\.");
                tableNameReal = tableNameSplit[tableNameReal.length() - 1];
                schameName = tableNameSplit[tableNameReal.length() - 2];
            }
            ResultSet indexRs = dmd.getIndexInfo(null, schameName, tableNameReal, true, false);
            while(indexRs.next()){
                Object columnObj = indexRs.getObject(9);
                if(columnObj != null){
                   uniqueIndexs.add(columnObj.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return uniqueIndexs;
    }

    public Set<String> primaryKeys(DatabaseMetaData dmd, String tableName){
        Set<String> primaryKeys = new HashSet<String>();
        try {
            String schameName = null;
            String tableNameReal = tableName;
            if(tableName.indexOf(".") > 0){
                String [] tableNameSplit = tableName.split("\\.");
                tableNameReal = tableNameSplit[tableNameReal.length() - 1];
                schameName = tableNameSplit[tableNameReal.length() - 2];
            }
            ResultSet indexRs = dmd.getPrimaryKeys(null, schameName, tableNameReal);
            while(indexRs.next()){
                Object columnObj = indexRs.getObject(4);
                if(columnObj != null){
                    primaryKeys.add(columnObj.toString());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return primaryKeys;
    }

}
