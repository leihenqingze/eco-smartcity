\#<font color='#D2691E'>添加时间：2019-06-08</font><br/>
\#<font color='#D2691E'>描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：充值记录表添加[充值方式]</font><br/>
\#<font color='#D2691E'>添&nbsp;&nbsp;加&nbsp;人：张凯</font><br/>
<strong>ALTER TABLE \`tb_recharge_record\` ADD COLUMN \`recharge_way\` tinyint(1) DEFAULT 1 COMMENT \'充值类型：1现金存款，2支票存款，3旧卡导入，4汇款\' AFTER \`amount\`;</strong>
