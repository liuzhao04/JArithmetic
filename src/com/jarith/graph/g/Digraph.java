package com.jarith.graph.g;

import java.util.List;
import java.util.Map;

/**
 * 有向图
 * 
 * @author liuz@aotian.com
 * @date 2017年9月7日 上午11:26:25
 */
public class Digraph {
	private List<Vertex> vList; // 顶点列表
	private List<Edge> eList; // 边列表
	private Map<String, Vertex> map; // 顶点ID与顶点之间的映射表
	private boolean isUndigraph = false;	// 是无向图,默认有向

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
