alter table notification
	add notifier_name varchar(100) null comment '发起通知的人的名字';

alter table notification
	add outer_title varchar(256) null;