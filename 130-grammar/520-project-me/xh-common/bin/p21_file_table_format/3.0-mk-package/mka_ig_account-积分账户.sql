-- mka_ig_account-积分账户

-- mka_ig_account	
-- 1.0-base
desc mka_ig_account;


-- 1.1-base-index
show index from mka_ig_account;-- 

-- 1.2-base-count
select count(*) from mka_ig_account;-- 
-- 1.3-base-limit
select * from mka_ig_account limit 1; -- 
select * from mka_ig_account where rownum <101;
select * from mka_ig_account limit 100;
select * from mka_ig_account order by org_id limit 100;

-- 1.4-base-insert
insert into mka_ig_account
		(field) 
values ('value');
commit;
-- 1.5-base-update
update mka_ig_account set field1='value1' where field2 = 'value2';
commit;
-- 1.6-base-delete
delete from mka_ig_account;
commit;

-- 6.0-busi

-- 7.0-nsql
-- 7.1.1-NSQL.sqlId
-- 7.1.1.0-NSQL.sqlId-count
-- 8.0-jira	


			-- data_create_time:20191013 08:02:59 605
			-- data_update_time:20191014 12:10:33 261
			-- data_version		 :2
