import com.flt.commom.kyfwconst.CertType;
import com.flt.util.HttpUtil;

import java.util.Random;

/**
 * @author meizs
 * @create 2018-05-02 16:07
 **/
public class api {

    public static void main(String[] args) {

        //test1();
        //int[] demoArray = new int[Integer.MAX_VALUE];

        System.out.println(System.currentTimeMillis() + new Random().nextInt(10000));

        System.out.println(CertType.ed.getName() + "," + CertType.ed.getValue_12306() + "," + CertType.ed.getValue_19e());

    }

    private static void test1() {
        String url = "http://127.0.0.1:8080/kyfw/getOrder.do";

        String result = HttpUtil.sendByGet(url, "30000", "30000", "utf-8");

        System.out.println(result);
    }


}
