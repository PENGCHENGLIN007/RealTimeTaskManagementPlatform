package com.zk.graduation.flink.util;

import com.zk.graduation.metadata.common.Column;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.common.typeinfo.Types;

import java.util.List;

/**
 * 数据类型工具
 *
 * @author pengchenglin
 * @create 2020-05-15 17:35
 */
public class DataTypeUtil {

    /**
     * 根据字段列表获取类型信息
     * @param list
     * @return
     */
    public static TypeInformation[] getDataTypes(List list){
        TypeInformation[] typeInformations =  new TypeInformation[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Column str = (Column) list.get(i);
            switch (str.getDataType()) {
                case VARCHAR:
                    typeInformations[i] = Types.STRING;
                    break;
                case INT:
                    typeInformations[i] = Types.INT;
                    break;
                case DOUBLE:
                    typeInformations[i] = Types.DOUBLE;
                    break;
                case LONG:
                    typeInformations[i] = Types.LONG;
                    break;
                case DATE:
                    typeInformations[i] = Types.SQL_DATE;
                    break;
                case TIMESTAMP:
                    typeInformations[i] = Types.SQL_TIMESTAMP;
                    break;
                default:
                    break;
            }
        }
        return typeInformations;
    }
}
