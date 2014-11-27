package ${basePackage!}.entity;

import java.io.Serializable;
<#list metaInfo.propertyTypeSet as propertyType>
import ${propertyType!};
</#list>

 /**
  *
  *	@author ${author!}
  * @since  ${.now}.
  */
public class ${metaInfo.className!}Entity implements Serializable {

<#list metaInfo.metaDataList as metaData>
    private ${metaData.classType[metaData.classType?last_index_of(".") + 1..]} ${metaData.columnClassPropertyName!};
</#list>

<#list metaInfo.metaDataList as metaData>
    public ${metaData.classType[metaData.classType?last_index_of(".") + 1..]} get${metaData.columnClassPropertyName?cap_first}() {
        return ${metaData.columnClassPropertyName!};
    }

    public void set${metaData.columnClassPropertyName?cap_first}(${metaData.classType[metaData.classType?last_index_of(".") + 1..]} ${metaData.columnClassPropertyName}) {
        this.${metaData.columnClassPropertyName!} = ${metaData.columnClassPropertyName!};
    }

</#list>

}
