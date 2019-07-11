**\#<font color='#D2691E'>添加时间：2019-06-08</font><br/>
\#<font color='#D2691E'>描&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;述：充值记录表添加[充值方式]</font><br/>
\#<font color='#D2691E'>添&nbsp;&nbsp;加&nbsp;人：张凯</font><br/>
<strong>ALTER TABLE \`tb_recharge_record\` ADD COLUMN \`recharge_way\` tinyint(1) DEFAULT 1 COMMENT \'充值类型：1现金存款，2支票存款，3旧卡导入，4汇款\' AFTER \`amount\`;</strong>


ALTER TABLE `tb_consume_record` 
ADD COLUMN `recharge_ago_amount` decimal(12, 2) NULL DEFAULT NULL COMMENT '消费前充值总金额' AFTER `recharge_amount`,
ADD COLUMN `recharge_ADD COLUMN `recharge_after_amount` decimal(12, 2) NULL DEFAULT NULL COMMENT '消费后after_amount` decimal(12, 2) NULL DEFAULT NULL COMMENT '消费后充值总金额' AFTER `recharge_ago_amount`,
ADD COLUMN `subsidy_ago_amount` decimal(12, 2) NULL DEFAULT NULL COMMENT '消费前补助总金额' AFTER `subsidy_amount`,
ADD COLUMN `subsidy_after_amount` decimal(12, 2) NULL DEFAULT NULL COMMENT '消费后补助总金额' AFTER `subsidy_ago_amount`;

ALTER TABLE `tb_subsidy_record` 
ADD COLUMN `subsidy_ago_amount` decimal(12, 2) NULL DEFAULT NULL COMMENT '消费前补助总金额' AFTER `amount`,
ADD COLUMN `subsidy_after_amount` decimal(12, 2) NULL DEFAULT NULL COMMENT '消费后补助总金额' AFTER `subsidy_ago_amount`;

ALTER TABLE `tb_user`
ADD COLUMN `identity` tinyint(1) NOT NULL DEFAULT 0 COMMENT '身份: 0:训练局职工，外租户，保安保洁等 . 1:运动员' AFTER `dept_id`,
ADD COLUMN `item_id` varchar(50) NOT NULL DEFAULT '' COMMENT '运动员记录ID' AFTER `identity`;


ADD COLUMN `identity` tinyint(1) NOT NULL DEFAULT 0 COMMENT '身份: 0:训练局职工，外租户，保安保洁等 . 1:运动员',` AFTER `dept_id`;

ALTER TABLE `tb_recharge_record` 
ADD COLUMN `recharge_ago_amount` decimal(12, 2) NULL DEFAULT NULL COMMENT '充值前总金额' AFTER `amount`,
ADD COLUMN `recharge_after_amount` decimal(12, 2) NULL DEFAULT NULL COMMENT '充值后总金额' AFTER `recharge_ago_amount`;

**