package p22_exception;
/*
import cn.sunline.ltts.aplt.tables.TabApRule.App_ruleDao;
import cn.sunline.ltts.aplt.tables.TabApRule.app_rule;
import cn.sunline.ltts.base.odb.OdbFactory;
import cn.sunline.ltts.core.api.exception.LttsBusinessException;
import cn.sunline.ltts.sys.dict.ApDict;
import cn.sunline.ltts.sys.errors.ApPubErr;
*/
public class LttsExceptionTest {

	public static void main(String[] args) {
		/*
		System.out.println("begin...");
		try{
			app_rule ruleInfo = null;//App_ruleDao.selectOne_odb2(ruleId, false);
			
			if (ruleInfo == null) {
				// ruleInfo.getException_rule_id();
				// throw 规则代码[ruleId]没有定义
				//throw ApPubErr.APPUB.E0005(OdbFactory.getTable(app_rule.class).getLongname(), ApDict.A.rule_id.getLongName(), ruleId);
				throw ApPubErr.APPUB.E0005("longname", ApDict.A.rule_id.getLongName(), "ruleId");
			}
		}catch(LttsBusinessException e){
			/*
			e.getMessage(); //[V096]Fail to retreive data from table [longname], record not found, [rule id-ruleId]
			e.getLocalMessage(); //[V096]Fail to retreive data from table [longname], record not found, [rule id-ruleId]
			e.getType(); //error
			e.getCode(); //V096
			e.getClass(); //cn.sunline.ltts.core.api.exception.LttsBusinessException
			*/
		/*
			if(ApPubErr.APPUB.F_E0005.equals(e.getCode()))
				System.out.println("catched");
			else throw e;
		}
		System.out.println("end...");
		*/
	}

}
