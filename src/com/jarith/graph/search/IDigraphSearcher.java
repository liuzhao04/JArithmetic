package com.jarith.graph.search;

import java.util.List;

import com.jarith.graph.g.Digraph;
import com.jarith.graph.g.Vertex;

/**
 * ͼ�����ӿ�
 * 
 * @author liuz@aotian.com
 * @date 2017��9��7�� ����6:28:34
 */
public interface IDigraphSearcher {
	/**
	 * ��ʼ����������
	 * @param digraph
	 */
	public void init(Digraph digraph);

	/**
	 * ȫͼ����
	 * @param v0  ����ID���Դ˵�Ϊ���
	 * @return
	 */
	public DigraphSearchResult search(String v0);
	
	/**
	 * �ֲ�ͼ����
	 * @param v0 ����ID���Դ˵�Ϊ���
	 * @param vList ͼ����Ҫ�����Ķ��㣬�������v0
	 * @return
	 */
	public DigraphSearchResult searchRangeS(String v0,List<String> vList);
	
	/**
	 * �ֲ�ͼ����
	 * @param v0 ����ID���Դ˵�Ϊ���
	 * @param vList ͼ����Ҫ�����Ķ��㣬�������v0
	 * @return
	 */
	public DigraphSearchResult searchRange(String v0,List<Vertex> vList);
}
