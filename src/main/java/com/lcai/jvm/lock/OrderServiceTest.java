/**   
* @Title:：OrderService.java 
* @Package ：com.lcai.jvm.lock 
* @Description： TODO
* @author： lcai   
* @date： 2018年10月17日 下午12:36:43 
* @version ： 1.0   
*/
package com.lcai.jvm.lock;
import java.util.concurrent.CountDownLatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 生成订单服务（单体架构中）
 *
 */
public class OrderServiceTest implements Runnable{

	private static Logger logger = LoggerFactory.getLogger(OrderServiceTest.class);
	//订单号工厂
	private OrderNumFactory onf = new OrderNumFactory();
	//计数 100 
	private static int count = 100;
	//信号量：它允许多个任务同一个时刻操作共享资源，但是必须限制同一时刻允许的最多的线程数目。
	private static CountDownLatch cdl = new CountDownLatch(count);
	//互斥对象
	private static Object obj = new Object();
	
	public void run() {
		/****************重要的代码块*********************/
		//通过互斥对象锁住共享资源
		synchronized (obj) {
			creadOrderNum();
		}
		//方法二
		//creadOrderNum();
	}
    /**
     * 预期实现生成唯一的订单号
     * @Title：creadOrderNum 
     * @Description：TODO
     * @param ： 
     * @return ：void 
     * @throws
     */
	public  void creadOrderNum() {
		String orderNum = onf.createOrderNum();
		logger.info(Thread.currentThread().getName()+"创建了订单号:【"+orderNum+"】");
		//生成100个订单号，那么同步锁就成功了。解决了并发的问题。
	}
	//测试--生成100个订单号
	public static void main(String[] args) {
		for(int i = 0 ;i<count;i++) {
			new Thread(new OrderServiceTest()).start();
			/****************重要的代码块*********************/
			//发令枪里面的数字减1
			cdl.countDown();
		}
	}
}
