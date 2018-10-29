/**   
* @Title:：ZkAbstractLock.java 
* @Package ：com.lcai.zookeeper.lock 
* @Description： TODO
* @author： lcai   
* @date： 2018年10月17日 下午4:04:49 
* @version ： 1.0   
*/
package com.lcai.zookeeper.lock;
import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
public abstract class ZkAbstractLock implements Lock{

   private static Logger logger = LoggerFactory.getLogger(ZkAbstractLock.class);
   //主机
   private static String host = "127.0.0.1";
   //端口
   private static String port = "2181";
   //znode节点
   protected static String path = "/lock";
   //创建ZkClient实例
   protected ZkClient client = new ZkClient(host+":"+port);
   /**
    * 
    * (非 Javadoc) 
    * <p>Title：lock</p> 
    * <p>
    *    Description：对我们的资源进行加锁，这里指的是创建订单工厂
    *    加锁就代表已经占用共享资源了
    *  </p>  
    */
    public void lock() {
	   
    	if(tryLock()) {
    		logger.info(Thread.currentThread().getName()+" get lock success!");
    	}else {
    		waitForLock();
    	}
    }
	/** 
	* @Title：waitForLock 
	* @Description：TODO
	* @param ： 
	* @return ：void 
	* @throws 
	*/
    protected abstract void waitForLock();
	/** 
	* @Title：tryLock 
	* @Description：TODO
	* @param ：@return 
	* @return ：boolean 
	* @throws 
	*/
	protected abstract boolean tryLock();
	/**
	 * 
	 * <p>Title：unLock</p> 
	 * <p>Description：
	 *    解锁(非 Javadoc) 断开ZkClient
	 *  </p>  
	 * @see com.lcai.zookeeper.lock.Lock#unLock()
	 */
	public void unLock() {
		//zk关闭之后，自动删除临时节点
		client.close();
	}
}
