-- mkp_ig_exchange-积分兑换设置

-- mkp_ig_exchange	
-- 1.0-base
desc mkp_ig_exchange;


-- 1.1-base-index
show index from mkp_ig_exchange;-- 

-- 1.2-base-count
select count(*) from mkp_ig_exchange;-- 
-- 1.3-base-limit
select * from mkp_ig_exchange limit 1; -- 
select * from mkp_ig_exchange where rownum <101;
select * from mkp_ig_exchange limit 100;
select * from mkp_ig_exchange order by org_id limit 100;

-- 1.4-base-insert
insert into mkp_ig_exchange
		(field) 
values ('value');
commit;
-- 1.5-base-update
update mkp_ig_exchange set field1='value1' where field2 = 'value2';
commit;
-- 1.6-base-delete
delete from mkp_ig_exchange;
commit;

-- 6.0-busi

-- 7.0-nsql
-- 7.1.1-NSQL.sqlId
-- 7.1.1.0-NSQL.sqlId-count
-- 8.0-jira	


			-- data_create_time:20191013 08:02:59 605
			-- data_update_time:20191014 12:10:33 261
			-- data_version		 :2
