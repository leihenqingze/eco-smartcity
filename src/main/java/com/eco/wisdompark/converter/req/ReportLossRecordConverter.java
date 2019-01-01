package com.eco.wisdompark.converter.req;

import com.eco.wisdompark.domain.model.ReportLossRecord;

import java.time.LocalDateTime;

/**
 * 挂失记录类型构造转换
 */
public class ReportLossRecordConverter {

    /**
     * 挂失记录类 构造
     * @param userId
     * @param oldCardId
     * @param newCardId
     * @param newCardSerialNo
     * @return
     */
    public static ReportLossRecord create(Integer userId, String oldCardId, String newCardId, String newCardSerialNo, String oldCardSerialNo){
        ReportLossRecord reportLossRecord = new ReportLossRecord();
        reportLossRecord.setUserId(userId);
        reportLossRecord.setOldCardId(oldCardId);
        reportLossRecord.setNewCardId(newCardId);
        reportLossRecord.setNewCardSerialNo(newCardSerialNo);
        reportLossRecord.setOldCardSerialNo(oldCardSerialNo);
        reportLossRecord.setReportLossTime(LocalDateTime.now());
        return reportLossRecord;
    }

}
