-- mkf_integral-积分产品定义

-- mkf_integral	
-- 1.0-base
desc mkf_integral;


-- 1.1-base-index
show index from mkf_integral;-- 

-- 1.2-base-count
select count(*) from mkf_integral;-- 
-- 1.3-base-limit
select * from mkf_integral limit 1; -- 
select * from mkf_integral where rownum <101;
select * from mkf_integral limit 100;
select * from mkf_integral order by org_id limit 100;

-- 1.4-base-insert
insert into mkf_integral
		(field) 
values ('value');
commit;
-- 1.5-base-update
update mkf_integral set field1='value1' where field2 = 'value2';
commit;
-- 1.6-base-delete
delete from mkf_integral;
commit;

-- 6.0-busi

-- 7.0-nsql
-- 7.1.1-NSQL.sqlId
-- 7.1.1.0-NSQL.sqlId-count
-- 8.0-jira	


			-- data_create_time:20191013 08:02:59 605
			-- data_update_time:20191014 12:10:33 261
			-- data_version		 :2
