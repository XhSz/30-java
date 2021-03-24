package p41_http;

//用于英文翻译成其他，其中的text进行了编码
import java.io.*;
import java.net.*;

public class GoogleTranslationEngine1 {
	// 定义互译语言对常数变量，符合google页面相关对译语言对的值
	public static final String LANGPAIR_CN_EN = "zh-CN|en"; // 汉语到英语
	public static final String LANGPAIR_EN_CN = "en|zh-CN"; // 英语到汉语
	public static final String LANGPAIR_EN_JA = "en|ja"; // 英语到日语
	// 定义编码常数
	public static final String CHARSET_CN = "GBK";
	public static final String CHARSET_JA = "Shift_JIS";
	// google在线翻译引擎url
	static final String engineUrl = "http://translate.google.com/translate_t";

	/** */
	/**
	 * 利用google在线翻译引擎实现翻译，并获取翻译内容
	 * 
	 * @param translateText
	 *            要翻译的文本内容
	 * @param langpair
	 *            对译语言的值对，如en|ja是由英语翻译到日语
	 * @throws Exception
	 */
	public String translate(String translateText, String langpair) {
		// text是google翻译页面提交时对于欲翻译文字的变量名
		// langpair是google翻译页面提交时对于采用何种互对语言的变量名
		String urlstr = "";
		try {
			urlstr = engineUrl + "?text=" + encodeText(translateText) + "&langpair=" + langpair;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		URL url = null;
		try {
			url = new URL(urlstr);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(url);
		URLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		connection.setRequestProperty("User-agent", "IE/9.0"); // 必须，否则报错，到于FF的怎么写，没做过测试
		try {
			connection.connect();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String charset = getCharsetFromLangpair(langpair); // 自动获取目标语言的编码
		BufferedReader in = null;
		try {
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), charset));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // 使用指定编码接收数据
		String line = null;
		StringBuilder sb = new StringBuilder();
		try {
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String translation = getContent(sb.toString());
		return translation;
	}

	/** */
	/**
	 * 从获得的源文件中剥取翻译内容 分析google翻译生成的html源码来看 翻译内容被置于 和 标签之间
	 * 
	 * @param htmltext
	 *            获得的网页源代码
	 */
	private String getContent(String htmltext) {
		String ss = "";
		String se = "";

		String[] info = htmltext.split("</span>")[7].split(">");

		return info[info.length - 1];
		/*
		 * int ssidx = htmltext.indexOf(ss); int seidx = htmltext.indexOf(se,ssidx);
		 * String restr = htmltext.substring(ssidx + ss.length(),seidx); return restr;
		 */
	}

	// 将文本进行URL编码
	private static String encodeText(String text) throws Exception {
		String str = java.net.URLEncoder.encode(text);
		return str;
	}

	private String getCharsetFromLangpair(String langpair) {
		// 当翻译的目标语言为日语时，采用Shift+JIS编码接收数据
		if (langpair.equals(LANGPAIR_EN_JA))
			return CHARSET_JA;
		else
			return CHARSET_CN;
	}

	public static void main(String[] args) throws Exception {
		GoogleTranslationEngine1 engine = new GoogleTranslationEngine1();
		// String text = " Lady Charlotte Bronte, Emily Bronte, Anne Bronte Browning
		// constitutes a perfect trinity of the highest honors of that era British
		// women." ;
		String langpair = "zh-CN|en";
		String text = "逻辑学";

		// System.out.println(encodeText(text));

		String rst = engine.translate(text, langpair);

		System.out.println(" 翻译内容：" + text);
		System.out.println(" Google翻译结果：" + rst);
	}
}