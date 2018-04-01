package cn.zhuzi.douyin.bean;

import java.util.ArrayList;
import java.util.List;

public class FollPerson {
	/**
	 * 用户id
	 */
	private String uid;
	/**
	 * 用户昵称
	 */
	private String nickname;

	/**
	 * 作品集合
	 */
	private List<WorkList> shDetails = new ArrayList<WorkList>();

	public List<WorkList> getShDetails() {
		return shDetails;
	}

	public void setShDetails(List<WorkList> shDetails) {
		this.shDetails = shDetails;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
