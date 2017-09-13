package com.jarith.graph.comm;

/**
 * 权重数据类型接口定义
 * 
 * @author liuz@aotian.com
 * @date 2017年9月6日 下午4:42:53
 */
public interface IWeight {
	/**
	 * 获取此类型的无穷大数
	 * 
	 * @return
	 */
	public IWeight getInfinity();

	public String strValue();

	/**
	 * 获取此类型的零
	 * 
	 * @return
	 */
	public IWeight getZero();

	/**
	 * 比较接口
	 * 
	 * @param other
	 * @return 比other大返回大于0，反之返回小于0，相等则返回0
	 */
	public int compareTo(IWeight other);

	/**
	 * 返回两者相加之和
	 * 
	 * @param other
	 * @return
	 */
	public IWeight add(IWeight other);
}
