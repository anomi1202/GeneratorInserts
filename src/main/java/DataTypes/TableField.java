package DataTypes;

public class TableField {
    private static String spuAccField = "ACC_ID, ACC_DSC, ACC_STS, ACC_DT_REG, ACC_SPEC, ACC_REG, OP_UNI, TS_MIGR, ACC_DT_DTH";
    private static String blcField = "SP_OP_BLC_ID, SP_OP_BLC_DSC, ACC_ID, SP_OP_BLC_ACNT, PSF_KND, SP_OP_BLC_SUM, SP_OP_ID, ACC_TS, BSN_TS_BEG, BSN_TS_END, TS_MIGR";
    private static String accSpaField = "SP_ACC_SPA_ID, SP_ACC_SPA_DSC, ACC_ID, PNS_RL_ID, CLS_OPER_CD_ID, ACNT_FROM, ACNT_TO, CLS_ORG_MNG_ID_MIGR_FROM, CLS_ORG_MNG_ID_MIGR_TO, SP_ACC_SPA_DT_REG, SP_ACC_SPA_DEC_DT, CLS_OP_STS_ID, OP_UNI, ACC_TS, BSN_TS_BEG, BSN_TS_END, TS_MIGR";
    private static String deadinfnpf = "INSNMB, DINDAT";
    private static String decPns = "SP_DEC_PNS_ID, SP_DEC_PNS_DSC, ACC_ID, SP_DEC_PNS_DT_REG, SP_DEC_PNS_DT, SP_DEC_PNS_DT_REP, CLS_DEC_ID, CLS_PAY_KND_ID, SP_DEC_PNS_SUM, SP_DEC_PNS_SUM_MON, SP_DEC_PNS_SUM_MON1, SP_DEC_PNS_SUM_DT, SP_DEC_PNS_SUM_RSP_GC, SP_DEC_PNS_DT_STP_PAY, SP_DEC_PNS_MON_AWAIT, CLS_RSN_DEC_ID, SP_DEC_PNS_IS_ABRD, CLS_OP_STS_ID, ACC_TS, OP_UNI, BSN_TS_BEG, BSN_TS_END, TS_MIGR, SP_DEC_PNS_DT_MNT_BEG, SP_DEC_PNS_NO";
    private static String decPnsIpf = "SP_DEC_PNS_IPF_ID, SP_DEC_PNS_IPF_DSC, ACC_ID, CLS_ORG_MNG_ID, SP_DEC_PNS_IPF_DT_REG, SP_DEC_PNS_IPF_DT_REP, SP_DEC_PNS_IPF_DT, CLS_DEC_ID, CLS_PAY_KND_ID, CLS_RSN_DEC_ID, CLS_OP_STS_ID, SP_DEC_PNS_IPF_SUM_BS, SP_DEC_PNS_IPF_SUM_MON, SP_DEC_PNS_IPF_SUM_MON1, SP_DEC_PNS_IPF_SUM, SP_DEC_PNS_IPF_SUM_PAY, SP_DEC_PNS_IPF_DT_MNT_BEG, SP_DEC_PNS_IPF_DT_PAY_END, SP_DEC_PNS_IPF_MON_AWAIT, ACC_TS, OP_UNI, BSN_TS_BEG, BSN_TS_END, TS_MIGR, SP_DEC_PNS_IPF_NO";

    public static String getField(ShortTableName table) {
        String field = "";
        switch (table) {
            case SPU_ACC:
                field = spuAccField;
                break;
            case SPU_SP_ACC_SPA:
                field = accSpaField;
                break;
            case SPU_SP_OP_BLC:
                field = blcField;
                break;
            case DEADINFNPF:
                field = deadinfnpf;
                break;
            case SPU_SP_DEC_PNS:
                field = decPns;
                break;
            case SPU_SP_DEC_PNS_IPF:
                field = decPnsIpf;
                break;
        }

        return field;
    }
}
