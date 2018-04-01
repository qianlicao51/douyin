package cn.zhuzi.douyin.downthread;

import java.io.File;

import cn.zhuzi.douyin.DouYinConStant;
import cn.zhuzi.douyin.bean.FollPerson;
import cn.zhuzi.douyin.bean.WorkList;
import cn.zhuzi.douyin.utils.DouYinUtils;

/**
 * 下载第一步
 * 
 * @author grq
 *
 */
public class ThreadDownDouyInStep1 extends Thread {

	public FollPerson follPerson;

	public ThreadDownDouyInStep1(FollPerson follPerson) {
		this.follPerson = follPerson;
	}

	@Override
	public void run() {
		File save = new File(DouYinConStant.SAVE_FILE, follPerson.getUid());
		if (!save.exists()) {
			save.mkdirs();
		}
		for (WorkList wlist : follPerson.getShDetails()) {
			DouYinUtils.sleepForColl(10000);
			String share_url = wlist.getShare_info().getShare_url();
			new ThreadDownDouyInStep2(save, share_url).start();
		}
	}
}
