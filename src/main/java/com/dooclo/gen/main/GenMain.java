package com.dooclo.gen.main;

import com.dooclo.gen.table.TableMetaInfo;
import com.dooclo.gen.temp.TemplateGen;

/**
 * Created by IvanMa on 2014/11/27.
 */
public class GenMain {

    public static void main(String args[]){
        TableMetaInfo metaInfo = new TableMetaInfo("wjods","HS_ASSET.BANKARG");
        TemplateGen tg = new TemplateGen();
        tg.batchGen(metaInfo);
    }

}
