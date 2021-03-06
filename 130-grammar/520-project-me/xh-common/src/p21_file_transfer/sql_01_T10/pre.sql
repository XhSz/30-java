

select a.cust_no,a.acct_no,a.prod_id,b.cust_name,a.sub_acct_no from dpa_sub_account a left join cfb_person_base b 
on a.cust_no = b.cust_no
where b.cust_no in(
	'100000000106',
	'100000000107'
);
100000000106
100000000107

/*
100000000106	20867500001043	FA02	MULTICIFFND MULTICIFFND MULTICIFFND	1000000107
100000000107	20867500001050	GS02	MULTICIFFND MULTICIFFND MULTICIFFND	1000000108


										MULTICIFFNB MULTICIFFNB MULTICIFFNB	100000000103
										MULTICIFFNB MULTICIFFNB MULTICIFFNB	100000000105


100000000227	20860700001111		GS02	multiciffn multicifmn multicifln	1000000127
100000000307	20867000001014		SA01	multiciffn multicifmn multicifln	1000000206
*/

	select b.acct_name,b.cust_no from dpa_account b where  b.cust_no in(
	'100000000105',
	'100000000103'
);

update cfb_person_base set cust_name = 'MULTICIFFNB MULTICIFMNB MULTICIFLNB',cust_first_name = 'MULTICIFFNB', cust_middle_name='MULTICIFMNB' , cust_last_name = 'MULTICIFLNB'
where cust_no in(
	'100000000105',
	'100000000103'
);
update dpa_account set acct_name = 'MULTICIFFNB MULTICIFMNB MULTICIFLNB'
where cust_no in(
	'100000000105',
	'100000000103'
);
commit;