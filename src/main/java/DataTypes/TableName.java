package DataTypes;

import java.util.HashMap;

import static DataTypes.ShortTableName.*;

public class TableName {
    private static HashMap<ShortTableName, String> mapTable = new HashMap<>();
    static {
        mapTable.put(SPU_ACC, "SPUMST.SPU_ACC");
        mapTable.put(SPU_SP_ACC_SPA, "SPUMST.SPU_SP_ACC_SPA");
        mapTable.put(SPU_SP_OP_BLC, "SPUMST.SPU_SP_OP_BLC");
        mapTable.put(SPU_SP_DEC_PNS, "SPUMST.SPU_SP_DEC_PNS");
        mapTable.put(SPU_SP_DEC_PNS_IPF, "SPUMST.SPU_SP_DEC_PNS_IPF");
        mapTable.put(DEADINFNPF, "USPN.DEADINFNPF");

    }

    public static String getFullTableName(ShortTableName table){
        return mapTable.get(table);
    }
}
