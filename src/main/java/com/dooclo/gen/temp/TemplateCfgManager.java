package com.dooclo.gen.temp;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModelException;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;

import java.io.File;
import java.io.IOException;

/**
 * Created by IvanMa on 2014/11/26.
 */
public class TemplateCfgManager {

    public static Configuration cfg;

    public static Configuration getConfiguration(){
        if(cfg != null){
            return cfg;
        }
        cfg = new Configuration();
        try {
            cfg.setDirectoryForTemplateLoading(new File("templates"));

            org.apache.commons.configuration.Configuration conf = new PropertiesConfiguration("conf/gen-conf.properties");
            cfg.setSharedVariable("basePackage",conf.getString("basePackage",""));
            cfg.setSharedVariable("targetDir",conf.getString("targetDir", System.getProperty("user.dir")));
            cfg.setSharedVariable("author",System.getProperty("user.name"));
            cfg.setObjectWrapper(new DefaultObjectWrapper());

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateModelException e) {
            e.printStackTrace();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        return cfg;
    }

}
