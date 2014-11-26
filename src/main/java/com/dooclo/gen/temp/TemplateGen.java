package com.dooclo.gen.temp;

import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Created by IvanMa on 2014/11/26.
 */
public class TemplateGen {

    public void gen(){
        try {
            Template template = TemplateCfgManager.getConfiguration().getTemplate("entity.ftl");
            Writer out = new OutputStreamWriter(System.out);
            template.process(null,out);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]){
        new TemplateGen().gen();
    }
}
