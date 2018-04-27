package com.flt;

import java.io.IOException;

/**
 * @author Administrator
 * @date 2018/4/25 22:29
 */
public class App {


    public static void main(String[] args) {

        String tableName = "tb1";
        String[] family = {"fb1", "fb2", "fb3"};

      /*  try {
            HbaseDataSourceUtil.createTable(tableName, family);
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        HbaseDataSourceUtil.query("tb1");

        //   HbaseDataSourceUtil.addOneRecord("tb1", "key1", "fb1", "message", "validate");

        //HbaseDataSourceUtil.addOneRecord("tb1", "key1", "fb1", "message1", "validate");

    }
}
