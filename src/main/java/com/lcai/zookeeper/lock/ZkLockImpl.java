/**   
* @Title:：ZkLockImpl.java 
* @Package ：com.lcai.zookeeper.lock 
* @Description： TODO
* @author： lcai   
* @date： 2018年10月17日 下午4:45:00 
* @version ： 1.0   
*/
package com.lcai.zookeeper.lock;
import java.util.concurrent.CountDownLatch;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.exception.ZkException;
public class ZkLockImpl extends ZkAbstractLock{
	
	private CountDownLatch cdl = null;
	
	@Override
	protected void waitForLock() {

		IZkDataListener listener = new IZkDataListener(){

			public void handleDataChange(String dataPath, Object data) throws Exception {
				
			}

			public void handleDataDeleted(String dataPath) throws Exception {
				if(cdl!=null) {
					cdl.countDown();
				}
			}
				
		};
		//注册watcher事件
		client.subscribeDataChanges(path,listener);
		if(client.exists(path)) {
			//如果存在该节点等待
			cdl = new CountDownLatch(1);
			try {
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		client.unsubscribeDataChanges(path, listener);
	}

	
	@Override
	protected boolean tryLock() {
		try {
			//创建一个临时节点znode
			client.createEphemeral(path);
			return true;
		} catch (ZkException e) {
			//若报异常，说明已经有线程占用该节点或者说已经存在该节点
			return false;
		} 
	}
	
}
