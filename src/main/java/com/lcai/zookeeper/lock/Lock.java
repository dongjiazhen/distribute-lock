/**   
* @Title:：Lock.java 
* @Package ：com.lcai.zookeeper.lock 
* @Description： TODO
* @author： lcai   
* @date： 2018年10月17日 下午4:03:06 
* @version ： 1.0   
*/
package com.lcai.zookeeper.lock;

public interface Lock {

	//加锁
	public void lock();
	//解锁
	public void unLock();
}
