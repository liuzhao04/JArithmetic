package com.jarith.graph.g;

import java.util.List;
import java.util.Map;

/**
 * ����ͼ
 * 
 * @author liuz@aotian.com
 * @date 2017��9��7�� ����11:26:25
 */
public class Digraph {
	private List<Vertex> vList; // �����б�
	private List<Edge> eList; // ���б�
	private Map<String, Vertex> map; // ����ID�붥��֮���ӳ���
	private boolean isUndigraph = false;	// ������ͼ,Ĭ������

	public boolean isUndigraph() {
		return isUndigraph;
	}

	public void setUndigraph(boolean isUndigraph) {
		this.isUndigraph = isUndigraph;
	}

	public Map<String, Vertex> getMap() {
		return map;
	}

	public void setMap(Map<String, Vertex> map) {
		this.map = map;
	}

	@Override
	public String toString() {
		return "Digraph [vList=" + vList + ", eList=" + eList + ", map=" + map + "]";
	}

	public List<Vertex> getvList() {
		return vList;
	}

	public void setvList(List<Vertex> vList) {
		this.vList = vList;
	}

	public List<Edge> geteList() {
		return eList;
	}

	public void seteList(List<Edge> eList) {
		this.eList = eList;
	}

}
