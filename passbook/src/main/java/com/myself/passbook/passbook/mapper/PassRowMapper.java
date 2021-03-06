package com.myself.passbook.passbook.mapper;

import com.myself.passbook.passbook.constant.Constants;
import com.myself.passbook.passbook.vo.Pass;
import com.spring4all.spring.boot.starter.hbase.api.RowMapper;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

/**
 * <h1>HBase Pass Row To Pass Object</h1>
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 15:28 2018\8\23 0023
 */
public class PassRowMapper implements RowMapper<Pass> {

    private static byte[] FAMIL_I = Constants.PassTable.FAMILY_I.getBytes();
    private static byte[] USER_ID = Constants.PassTable.USER_ID.getBytes();
    private static byte[] TEMPLATE_ID = Constants.PassTable.TEMPLATE_ID.getBytes();
    private static byte[] TOKEN = Constants.PassTable.TOKEN.getBytes();
    private static byte[] ASSIGNED_DATE = Constants.PassTable.ASSIGNED_DATE.getBytes();
    private static byte[] CON_DATE = Constants.PassTable.CON_DATE.getBytes();


    @Override
    public Pass mapRow(Result result, int i) throws Exception {

        Pass pass = new Pass();

        pass.setUserId(Bytes.toLong(result.getValue(FAMIL_I,USER_ID)));
        pass.setTemplateId(Bytes.toString(result.getValue(FAMIL_I,TEMPLATE_ID)));
        pass.setToken(Bytes.toString(result.getValue(FAMIL_I,TOKEN)));

        String[] patterns = new String[]{"yyyy-MM-dd"};
        pass.setAssignedDate(DateUtils.parseDate(Bytes.toString(result.getValue(FAMIL_I,ASSIGNED_DATE)),patterns));

        String conDateStr = Bytes.toString(result.getValue(FAMIL_I,CON_DATE));
        if (conDateStr.equals("-1")){
            pass.setConDate(null);
        } else {
            pass.setConDate(DateUtils.parseDate(conDateStr,patterns));
        }

        return pass;
    }
}
