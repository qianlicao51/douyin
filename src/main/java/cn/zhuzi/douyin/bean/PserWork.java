package cn.zhuzi.douyin.bean;

import java.util.List;

/**
 * 个人作品列表
 * 
 * @author grq
 *
 */
public class PserWork {

	/**
	 * 如果 has_more =1 表示还有下一页
	 */
	private String has_more;

	/**
	 * 如果 还有下一页 需要这个参数，初始值为0
	 */
	private String max_cursor = "0";

	private List<WorkList> aweme_list;

	public List<WorkList> getAweme_list() {
		return aweme_list;
	}

	public void setAweme_list(List<WorkList> aweme_list) {
		this.aweme_list = aweme_list;
	}

	public String getHas_more() {
		return has_more;
	}

	public void setHas_more(String has_more) {
		this.has_more = has_more;
	}

	public String getMax_cursor() {
		return max_cursor;
	}

	public void setMax_cursor(String max_cursor) {
		this.max_cursor = max_cursor;
	}

}
