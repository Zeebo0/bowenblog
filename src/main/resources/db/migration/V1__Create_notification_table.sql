CREATE TABLE notification (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  notifier bigint(20) NOT NULL COMMENT '发起通知的人的id',
  receiver bigint(20) NOT NULL COMMENT '接收通知人的id',
  outer_id bigint(20) NOT NULL COMMENT '通知的id',
  type int(11) NOT NULL,
  gmt_create bigint(20) NOT NULL,
  gmt_modify bigint(20) NOT NULL,
  status int(11) NOT NULL DEFAULT 0 COMMENT '状态，0为未读，1为已读',
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;