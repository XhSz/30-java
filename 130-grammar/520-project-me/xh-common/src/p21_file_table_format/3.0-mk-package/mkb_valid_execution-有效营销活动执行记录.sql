-- mkb_valid_execution-有效营销活动执行记录

-- mkb_valid_execution	
-- 1.0-base
desc mkb_valid_execution;


-- 1.1-base-index
show index from mkb_valid_execution;-- 

-- 1.2-base-count
select count(*) from mkb_valid_execution;-- 
-- 1.3-base-limit
select * from mkb_valid_execution limit 1; -- 
select * from mkb_valid_execution where rownum <101;
select * from mkb_valid_execution limit 100;
select * from mkb_valid_execution order by org_id limit 100;

-- 1.4-base-insert
insert into mkb_valid_execution
		(field) 
values ('value');
commit;
-- 1.5-base-update
update mkb_valid_execution set field1='value1' where field2 = 'value2';
commit;
-- 1.6-base-delete
delete from mkb_valid_execution;
commit;

-- 6.0-busi

-- 7.0-nsql
-- 7.1.1-NSQL.sqlId
-- 7.1.1.0-NSQL.sqlId-count
-- 8.0-jira	


			-- data_create_time:20191013 08:02:59 605
			-- data_update_time:20191014 12:10:33 261
			-- data_version		 :2
