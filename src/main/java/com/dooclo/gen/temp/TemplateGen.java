package com.dooclo.gen.temp;

import com.dooclo.gen.table.TableMetaInfo;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by IvanMa on 2014/11/26.
 */
public class TemplateGen {

    public void genCode(TableMetaInfo metaInfo,String freemarkerName,String targetFile){
        try {
            Template template = TemplateCfgManager.getConfiguration().getTemplate(freemarkerName);
            FileOutputStream fos = new FileOutputStream(new File(targetFile));
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("metaInfo",metaInfo);
            template.process(map,new OutputStreamWriter(fos,"utf-8"));
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    public void batchGen(TableMetaInfo metaInfo){
        try {
            Configuration conf = new PropertiesConfiguration("conf/gen-conf.properties");
            String targetDir = conf.getString("targetDir") + File.separator + "src" + File.separator + "main";
            String srcBaseDir = targetDir + File.separator + "java";
            String basePackage = conf.getString("basePackage");
            String str = File.separator;
            String packageDir = "";
            if(str.equals("\\")){
                packageDir = basePackage.replaceAll("\\.","\\" + File.separator);
            }else{
                packageDir = basePackage.replaceAll("\\.", File.separator);
            }

            String entityDir = srcBaseDir + File.separator + packageDir  + File.separator + "entity";
            File entityDirFile = new File(entityDir);
            if(!entityDirFile.exists()){
                entityDirFile.mkdirs();
            }
            genCode(metaInfo, "entity.ftl",entityDir + File.separator + metaInfo.getClassName() + "Entity.java");
            String classMapperDir = srcBaseDir + File.separator + packageDir + File.separator + "model" + File.separator + "mapper";

            File classMapperDirFile = new File(classMapperDir);
            if(!classMapperDirFile.exists()){
                classMapperDirFile.mkdirs();
            }

            genCode(metaInfo, "class-mapper.ftl",classMapperDir + File.separator + metaInfo.getClassName() + "Mapper.java");
            String resourceBaseDir = targetDir + File.separator + "resources";

            File resourceBaseDirFile = new File(resourceBaseDir);
            if(!resourceBaseDirFile.exists()){
                resourceBaseDirFile.mkdirs();
            }

            genCode(metaInfo, "mybatis-mapper.ftl",resourceBaseDir + File.separator + "mapper-" + metaInfo.getClassName().toLowerCase() + ".java");
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){

        TableMetaInfo metaInfo = new TableMetaInfo("wjods","HS_ASSET.BANKARG");
        TemplateGen tg = new TemplateGen();
        tg.batchGen(metaInfo);
    }
}
