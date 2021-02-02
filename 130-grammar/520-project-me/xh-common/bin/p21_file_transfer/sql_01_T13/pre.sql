

select a.cust_no,a.acct_no,a.prod_id,b.cust_name,a.sub_acct_no from dpa_sub_account a left join cfb_person_base b 
on a.cust_no = b.cust_no
where b.cust_no in(
	'100000000110',
	'100000000111'
);
/*
100000000102	1000000102	60849.55
100000000004	1000000103	9669.06

100000000110	20860700001020	GS01	MULTICIFFNG MULTICIFFNG MULTICIFFNG	1000000109
100000000111	20867500001068	GS02	MULTICIFFNG MULTICIFFNG MULTICIFFNG	1000000110


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