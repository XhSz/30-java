package p12_String;

public class VisaParserString {

	public static void main(String[] args) {
		fun050();
//		fun051(); 
	}
	public static void fun050() 
	{
		String tcr0 = 
//				"05004367412465161400000Z  74611979286940101565185000000001013000000022725608000000022725608ROB SUPERMARKET RP IMU   CAVITE       PH 541100000     1008N6839245 1 0592870";
//				"05004367418510708178000Z N24492159287637593269824100000561013000000010900608000000010900608ORDER.WISH.COM           WWW.WISH.COM US 531194104CA   1000N5210649 4 1092870"
		"05004367412928927868000B N74921649279000031329237000000001004000000026500608000000026500608STARBUCKS  CITY CENTER   TAGUIG CITY  PH 581401634     1008N0000Y15 1 079280B"
				;
		System.out.println(tcr0.substring(4		,20  )); //ACCOUNT_NUMBER		
		System.out.println(tcr0.substring(20	,23  )); //ACCOUNT_NUMBER_EXT	
		System.out.println(tcr0.substring(23	,24  )); //FLR_LIMIT_IND		
		System.out.println(tcr0.substring(24	,25  )); //CRB_IND				
		System.out.println(tcr0.substring(25	,26  )); //PCAS_IND				
		System.out.println(tcr0.substring(26	,49  )); //ARN					
		System.out.println(tcr0.substring(49	,57  )); //ACQ_BUS_ID			
		System.out.println(tcr0.substring(57	,61  )); //PURCH_DATE_MMDD		
		System.out.println(tcr0.substring(61	,73  )); //DEST_AMT				
		System.out.println(tcr0.substring(73	,76  )); //DEST_CURR_CD			
		System.out.println(tcr0.substring(76	,88  )); //SRC_AMT				
		System.out.println(tcr0.substring(88	,91  )); //SRC_CURR_CD			
		System.out.println(tcr0.substring(91	,116 )); //MER_NAME				
		System.out.println(tcr0.substring(116	,129 )); //MER_CITY				
		System.out.println(tcr0.substring(129	,132 )); //MER_CTRY_CD			
		System.out.println(tcr0.substring(132	,136 )); //MER_CAT_CD			
		System.out.println(tcr0.substring(136	,141 )); //MER_ZIP				
		System.out.println(tcr0.substring(141	,144 )); //MER_STATE_PROV_CD	
		System.out.println(tcr0.substring(144	,145 )); //REQ_PMT_SVC			
		System.out.println(tcr0.substring(145	,146 )); //NUM_PMT_FORMS		
		System.out.println(tcr0.substring(146	,147 )); //USAGE_CD				
		System.out.println(tcr0.substring(147	,149 )); //REASON_CD			
		System.out.println(tcr0.substring(149	,150 )); //SETT_FLAG			
		System.out.println(tcr0.substring(150	,151 )); //ACI					
		System.out.println(tcr0.substring(151	,157 )); //AUTH_CODE			
		System.out.println(tcr0.substring(157	,158 )); //POS_TERM_CAP			
		System.out.println(tcr0.substring(158	,159 )); //RESERVED				
		System.out.println(tcr0.substring(159	,160 )); //CARD_ID_METHOD		
		System.out.println(tcr0.substring(160	,161 )); //COLLECTION_ONLY_FG	
		System.out.println(tcr0.substring(161	,163 )); //PEM					
		System.out.println(tcr0.substring(163	,167 )); //CENTRAL_PROC_DATE_YDD
		System.out.println(tcr0.substring(167	,168 )); //REIMBURSE_ATTR	
	}
	public static void fun051() 
	{
		String tcr0 = "0501            000000                                                          00000608000245460852923000000000000  928700  0   0                           0000000000 ";
		System.out.println(tcr0.substring(4	  , 5   )); // BUS_FORMAT_CD		
		System.out.println(tcr0.substring(5	  , 7   )); // TOKEN_ASSURANCE_LVL	
		System.out.println(tcr0.substring(7	  , 16  )); // RESERVED			
		System.out.println(tcr0.substring(16  , 22  )); // CHBK_REF_NO			
		System.out.println(tcr0.substring(22  , 23  )); // DOC_IND				
		System.out.println(tcr0.substring(23  , 73  )); // MEMBER_MSG_TXT		
		System.out.println(tcr0.substring(73  , 75  )); // SP_COND_IND			
		System.out.println(tcr0.substring(75  , 78  )); // FEE_PRG_IND			
		System.out.println(tcr0.substring(78  , 79  )); // ISSUER_CHARGE		
		System.out.println(tcr0.substring(79  , 80  )); // RESERVED_1			
		System.out.println(tcr0.substring(80  , 95  )); // CARD_ACCEPTOR_ID	
		System.out.println(tcr0.substring(95  , 103 )); // TERMINAL_ID			
		System.out.println(tcr0.substring(103 , 115 )); // REIMBURSEMENT_FEE	
		System.out.println(tcr0.substring(115 , 116 )); // MOTO_ECOM_PMT_IND	
		System.out.println(tcr0.substring(116 , 117 )); // SP_CHBK_IND			
		System.out.println(tcr0.substring(117 , 123 )); // INTF_TRACE_NO		
		System.out.println(tcr0.substring(123 , 124 )); // ACCEPT_TERM_IND		
		System.out.println(tcr0.substring(124 , 125 )); // PREPAID_IND			
		System.out.println(tcr0.substring(125 , 126 )); // SVC_DEV_FLD			
		System.out.println(tcr0.substring(126 , 127 )); // AVS_RESP_CD			
		System.out.println(tcr0.substring(127 , 128 )); // AUTH_SRC_CD			
		System.out.println(tcr0.substring(128 , 129 )); // PURCH_ID_FMT		
		System.out.println(tcr0.substring(129 , 130 )); // ACCOUNT_SELECT		
		System.out.println(tcr0.substring(130 , 132 )); // INSTALLMENT_PMT_CNT	
		System.out.println(tcr0.substring(132 , 157 )); // PURCH_ID			
		System.out.println(tcr0.substring(157 , 166 )); // CASHBACK			
		System.out.println(tcr0.substring(166 , 167 )); // CHIP_COND_CD		
		System.out.println(tcr0.substring(167 , 168 )); // POS_ENV				
	}
}
