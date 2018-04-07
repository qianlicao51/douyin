package cn.zhuzi.douyin;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;

import cn.zhuzi.douyin.bean.FollPerson;
import cn.zhuzi.douyin.bean.MyCareBean;

import com.alibaba.fastjson.JSON;

/**
 * 抖音抓取入口
 */
public class DoyYinmP4down {
	public static void main(String[] args) {
		String carePersonStr = null;

		try {
			carePersonStr = FileUtils.readFileToString(new File(new File("").getAbsoluteFile(), "myCare.json"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		List<FollPerson> parseArray = JSON.parseArray(carePersonStr, FollPerson.class);
		MyCareBean myCareBean = new MyCareBean();

		FollPerson[] followings = new FollPerson[parseArray.size()];
		for (int i = 0; i < parseArray.size(); i++) {
			followings[i] = parseArray.get(i);
		}
		CarePerson carePerson = new CarePerson();
		myCareBean.setFollowings(followings);
		carePerson.setMyCareBean(myCareBean);

		List<FollPerson> pserWork = carePerson.getPserWork();

		DownFromDetailUrl.downByFollList(pserWork);

	}
}
