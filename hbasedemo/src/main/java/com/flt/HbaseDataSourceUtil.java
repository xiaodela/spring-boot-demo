package com.flt;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.Map;

/**
 * @author Administrator
 * @date 2018/4/25 22:25
 */
public class HbaseDataSourceUtil {
    private static Configuration conf = null;
    private static Admin admin = null;
    private static Connection conn = null;

    /**
     * 初始化连接
     *
     * @throws IOException
     */
    public static void init() throws IOException {
        System.setProperty("hadoop.home.dir", "E:\\workspace\\app_server\\hadoop-2.7.4\\hadoop-2.7.4");   //必备条件之一
        conf = HBaseConfiguration.create();
        conf.set("hbase.zookeeper.quorum", "localhost");   //hadoop14,hadoop15,hadoop16为hostname
        conf.set("hbase.zookeeper.property.clientPort", "2181");
        conn = ConnectionFactory.createConnection(conf);
        admin = conn.getAdmin();
    }

    /**
     * 建表
     *
     * @param tableName
     * @throws IOException
     */
    public static void createTable(String tableName, String[] families) throws IOException {
        init();
        if (admin.tableExists(TableName.valueOf(tableName))) {
            System.out.println(tableName + "已存在");
        } else {
            HTableDescriptor tableDesc = new HTableDescriptor(TableName.valueOf(tableName));
            for (String family : families) {
                tableDesc.addFamily(new HColumnDescriptor(family));
            }
            admin.createTable(tableDesc);
            System.out.println("Table created");
        }
    }

    /**
     * 新增列簇
     *
     * @param tableName
     * @param family
     */
    public static void addFamily(String tableName, String family) {
        try {
            init();
            HColumnDescriptor columnDesc = new HColumnDescriptor(family);
            admin.addColumn(TableName.valueOf(tableName), columnDesc);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            destroy();
        }
    }


    /**
     * 查询表信息
     *
     * @param tableName
     */
    public static void query(String tableName) {
        HTable hTable = null;
        ResultScanner scann = null;
        try {
            init();
            hTable = (HTable) conn.getTable(TableName.valueOf(tableName));
            scann = hTable.getScanner(new Scan());
            for (Result rs : scann) {
                System.out.println("RowKey为：" + new String(rs.getRow()));
//按cell进行循环
                for (Cell cell : rs.rawCells()) {
                    System.out.println("列簇为：" + new String(CellUtil.cloneFamily(cell)));
                    System.out.println("列修饰符为：" + new String(CellUtil.cloneQualifier(cell)));
                    System.out.println("值为：" + new String(CellUtil.cloneValue(cell)));
                }
                System.out.println("=============================================");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (scann != null) {
                scann.close();
            }
            if (hTable != null) {
                try {
                    hTable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            destroy();
        }
    }

    /**
     * 根据rowkey查询单行
     *
     * @param key
     * @param tableName
     */
    public static void queryByRowKey(String key, String tableName) {
        HTable hTable = null;
        try {
            init();
            hTable = (HTable) conn.getTable(TableName.valueOf(tableName));
            Result rs = hTable.get(new Get(Bytes.toBytes(key)));
            System.out.println(tableName + "表RowKey为" + key + "的行数据如下：");
            for (Cell cell : rs.rawCells()) {
                System.out.println("列簇为：" + new String(CellUtil.cloneFamily(cell)));
                System.out.println("值为：" + new String(CellUtil.cloneValue(cell)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (hTable != null) {
                try {
                    hTable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            destroy();
        }
    }

    /**
     * 插入单行单列簇单列修饰符数据
     *
     * @param tableName
     * @param key
     * @param family
     * @param col
     * @param val
     */
    public static void addOneRecord(String tableName, String key,
                                    String family, String col, String val) {
        HTable hTable = null;
        try {
            init();
            hTable = (HTable) conn.getTable(TableName.valueOf(tableName));
            Put p = new Put(Bytes.toBytes(key));
            p.addColumn(Bytes.toBytes(family), Bytes.toBytes(col), Bytes.toBytes(val));
            if (p.isEmpty()) {
                System.out.println("数据插入异常，请确认数据完整性，稍候重试");
            } else {
                hTable.put(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (hTable != null) {
                try {
                    hTable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            destroy();
        }
    }


    /**
     * 插入单行单列簇多列修饰符数据
     *
     *
     * @param tableName
     * @param key
     * @param family
     */
    public static void addMoreRecord(String tableName, String key,
                                     String family, Map<String, String> colVal) {
        HTable hTable = null;
        try {
            init();
            hTable = (HTable) conn.getTable(TableName.valueOf(tableName));
            Put p = new Put(Bytes.toBytes(key));
            for (String col : colVal.keySet()) {
                String val = colVal.get(col);
                if (StringUtils.isNotBlank(val)) {
                    p.addColumn(Bytes.toBytes(family), Bytes.toBytes(col), Bytes.toBytes(val));
                } else {
                    System.out.println("列值为空，请确认数据完整性");
                }
            }
//当put对象没有成功插入数据时，此时调用hTable.put(p)方法会报错：java.lang.IllegalArgumentException:No columns to insert
            if (p.isEmpty()) {
                System.out.println("数据插入异常，请确认数据完整性，稍候重试");
            } else {
                hTable.put(p);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (hTable != null) {
                try {
                    hTable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            destroy();
        }
    }


    /**
     * 删除指定名称的列簇
     *
     * @param family
     * @param tableName
     */
    public static void deleteFamily(String family, String tableName) {
        try {
            init();
            admin.deleteColumn(TableName.valueOf(tableName), Bytes.toBytes(family));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            destroy();
        }
    }


    /**
     * 删除指定行
     *
     * @param key
     * @param tableName
     */
    public static void deleteRow(String key, String tableName) {
        HTable hTable = null;
        try {
            init();
            hTable = (HTable) conn.getTable(TableName.valueOf(tableName));
            hTable.delete(new Delete(Bytes.toBytes(key)));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (hTable != null) {
                try {
                    hTable.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            destroy();
        }
    }


    /**
     * 删除指定表名
     *
     * @param tableName
     */
    public static void deleteTable(String tableName) {
        try {
            init();
//在删除一张表前，必须先使其失效
            admin.disableTable(TableName.valueOf(tableName));
            admin.deleteTable(TableName.valueOf(tableName));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            destroy();
        }
    }

    //关闭连接
    private static void destroy() {
        if (admin != null) {
            try {
                admin.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
/*if(conf!=null){
conf.clear();
}*/
    }
}
