/**   
* @Title:：OrderNumFactory.java 
* @Package ：com.lcai.jvm.lock 
* @Description： TODO
* @author： lcai   
* @date： 2018年10月17日 下午12:32:20 
* @version ： 1.0   
*/
package com.lcai.jvm.lock;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 临界资源
 */
public class OrderNumFactory {

	/**
	 * 创建订单
	* @Title：createOrderNum 
	* @Description：TODO
	* @param ：@return 
	* @return ：String 
	* @throws
	 */
	private static int i = 0 ;
	//方法一  互斥对象----对同步代码块同步锁synchronized
	public  static String createOrderNum() {
	//方法二  临界区 -----对同步java方法进行同步锁synchronized，不推荐使用
	//public synchronized static String createOrderNum() {
	//**************方法一比方法二的性能要好，推荐使用方法一：互斥量同步代码块*******************
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss-");
		
		return sdf.format(new Date())+ ++i;
	}
}
