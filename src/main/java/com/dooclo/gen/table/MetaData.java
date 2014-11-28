package com.dooclo.gen.table;

/**
 * Created by IvanMa on 2014/11/26.
 */
public class MetaData {

    private String columnName;
    private String dataType;
    private String classType;
    private String columnClassPropertyName;
    private boolean keyFlag;

    public boolean isKeyFlag() {
        return keyFlag;
    }

    public void setKeyFlag(boolean keyFlag) {
        this.keyFlag = keyFlag;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getClassType() {
        return classType;
    }

    public void setClassType(String classType) {
        this.classType = classType;
    }

    public String getColumnClassPropertyName() {
        return columnClassPropertyName;
    }

    public void setColumnClassPropertyName(String columnClassPropertyName) {
        this.columnClassPropertyName = columnClassPropertyName;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MetaData{");
        sb.append("columnName='").append(columnName).append('\'');
        sb.append(", dataType='").append(dataType).append('\'');
        sb.append(", classType='").append(classType).append('\'');
        sb.append(", columnClassPropertyName='").append(columnClassPropertyName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
