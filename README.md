### 轻量级代码生成器（基于 java 开发）

#### 使用指南

1. 配置

 + src/main/resource/conf 
 
    - jdbc.properties 数据源配置文件(可配置多个) key命名格式约定
    
    ```
    ${dbName}.jdbc.driver=driverClassName
    ${dbName}.jdbc.url=ip:port/db
    ${dbName}.jdbc.username=uname
    ${dbName}.jdbc.password=pword
    ```
    
    - gen-conf.properties 代码生成目标对应配置 （详见文件内说明）
  
 + src/main/resource/templates 模板文件放置目录，该目录可放置在任何地方，但需与 gen-conf.properties 中 templateDir 值一致。
 
    - 目前里面有mybatis相关模板
 
2. 运行
  
  com.dooclo.gen.main.GenMain.main()
  TableMetaInfo metaInfo = new TableMetaInfo("${dbName}","${tableName}");
  
  以 ${dbName} 开头的相关数据源配置需在 jdbc.properties 中存在。
  
