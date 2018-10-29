/**   
* @Title:：OrderService.java 
* @Package ：com.lcai.jvm.lock 
* @Description： TODO
* @author： lcai   
* @date： 2018年10月17日 下午12:36:43 
* @version ： 1.0   
*/
package com.lcai.zookeeper.lock;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.lcai.jvm.lock.OrderNumFactory;
/**
 * 生成订单服务（单体架构中）分布式锁生成唯一的订单号。
 * 实现分布式高可用需要zookeeper集群
 *
 */
public class ZookeeperOrderServiceTest implements Runnable{
	//日志
	private static Logger logger = LoggerFactory.getLogger(ZookeeperOrderServiceTest.class);
	//生成订单号工厂
	private OrderNumFactory onf = new OrderNumFactory();
	//计数 100 
	private static int count = 100;
	//信号量：它允许多个任务同一个时刻操作共享资源，但是必须限制同一时刻允许的最多的线程数目。
	private static CountDownLatch cdl = new CountDownLatch(count);
	//实例化
	Lock lock = new ZkLockImpl();
	
	public void run() {
		
		creadOrderNum();
	}
    /**
     * 预期实现生成唯一的订单号
     * @Title：creadOrderNum 
     * @Description：TODO
     * @param ： 
     * @return ：void 
     * @throws
     */
	public void creadOrderNum() {
		//首先对共享资源枷锁
		lock.lock();
		String orderNum = onf.createOrderNum();
		logger.info(Thread.currentThread().getName()+"创建了订单号:【"+orderNum+"】");
		//解锁--释放共享资源
		lock.unLock();
		//生成100个订单号，那么分布式锁就成功了。解决了并发的问题。
	}
	public static void main(String[] args) {
		for(int i = 0 ;i<count;i++) {
			new Thread(new ZookeeperOrderServiceTest()).start();
			/****************重要的代码块*********************/
			//发令枪里面的数字减1
			cdl.countDown();
		}
	}
}
