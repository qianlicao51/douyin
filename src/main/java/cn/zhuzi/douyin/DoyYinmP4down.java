package cn.zhuzi.douyin;

import java.util.List;
import cn.zhuzi.douyin.bean.FollPerson;
import cn.zhuzi.douyin.bean.MyCareBean;
import com.alibaba.fastjson.JSON;

/**
 * 抖音抓取入口
 * 
 * @author grq
 *
 */
public class DoyYinmP4down {
	public static void main(String[] args) {
		String carePersonStr = "[{'nickname':'大果粒','shDetails':[],'uid':'72722865756'},{'nickname':'一珺、','uid':'52616983119'},{'nickname':'Imperia_小然然','uid':'61141281259'},{'nickname':'光哥','uid':'58900737309'}]";
		// carePersonStr = "[{'nickname':'光哥','uid':'58900737309'}]";
		// carePersonStr="[{'nickname':'大果粒','shDetails':[],'uid':'72722865756'},{'nickname':'光哥','uid':'58900737309'}]";
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
