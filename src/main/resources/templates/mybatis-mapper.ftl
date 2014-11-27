<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<mapper namespace="${basePackage!}.model.mapper.${metaInfo.className!}Mapper">

	<resultMap id="RM.${metaInfo.className!}" type="${basePackage!}.entity.${metaInfo.className!}Entity">
	<#list metaInfo.metaDataList as metaData>
		<result column="${metaData.columnName!}" property="${metaData.columnClassPropertyName!}"/>
	</#list>
	</resultMap>

	<sql id="${metaInfo.className!}.columns">
	<#list metaInfo.metaDataList as metaData>
        ${metaData.columnName!}<#if !metaData_has_next><#else>,</#if>
    </#list>
	</sql>

	<sql id="${metaInfo.className!}.where">
		<where>
		<#list metaInfo.metaDataList as metaData>
            <if test="${metaData.columnClassPropertyName!} != null">
                and ${metaData.columnName!} = ${r"#"}{${metaData.columnClassPropertyName!}}
            </if>
        </#list>
		</where>
	</sql>

	<select id="queryByMap" resultMap="RM.${metaInfo.className!}" parameterType="java.util.Map">
		select <include refid="${metaInfo.className!}.columns"/>
		from ${metaInfo.tableName}
		<include refid="${metaInfo.className!}.where"/>
	</select>

	<insert id="save" parameterType="${basePackage!}.entity.${metaInfo.className!}Entity">
        insert into ${metaInfo.tableName} (
        <#list metaInfo.metaDataList as metaData>
        ${metaData.columnName!}<#if !metaData_has_next><#else>,</#if>
        </#list>
        ) values (
        <#list metaInfo.metaDataList as metaData>
        ${r"#"}{${metaData.columnClassPropertyName!}}<#if !metaData_has_next><#else>,</#if>
        </#list>
        )
    </insert>

</mapper>