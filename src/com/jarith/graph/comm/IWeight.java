package com.jarith.graph.comm;

/**
 * Ȩ���������ͽӿڶ���
 * 
 * @author liuz@aotian.com
 * @date 2017��9��6�� ����4:42:53
 */
public interface IWeight {
	/**
	 * ��ȡ�����͵��������
	 * 
	 * @return
	 */
	public IWeight getInfinity();

	public String strValue();

	/**
	 * ��ȡ�����͵���
	 * 
	 * @return
	 */
	public IWeight getZero();

	/**
	 * �ȽϽӿ�
	 * 
	 * @param other
	 * @return ��other�󷵻ش���0����֮����С��0������򷵻�0
	 */
	public int compareTo(IWeight other);

	/**
	 * �����������֮��
	 * 
	 * @param other
	 * @return
	 */
	public IWeight add(IWeight other);
}
