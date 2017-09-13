package com.jarith.graph.search;

import java.util.List;

import com.jarith.graph.g.Digraph;
import com.jarith.graph.g.Vertex;

/**
 * 图搜索接口
 * 
 * @author liuz@aotian.com
 * @date 2017年9月7日 下午6:28:34
 */
public interface IDigraphSearcher {
	/**
	 * 初始化搜索引擎
	 * @param digraph
	 */
	public void init(Digraph digraph);

	/**
	 * 全图搜索
	 * @param v0  顶点ID，以此点为起点
	 * @return
	 */
	public DigraphSearchResult search(String v0);
	
	/**
	 * 局部图搜索
	 * @param v0 顶点ID，以此点为起点
	 * @param vList 图中需要检索的顶点，必须包含v0
	 * @return
	 */
	public DigraphSearchResult searchRangeS(String v0,List<String> vList);
	
	/**
	 * 局部图搜索
	 * @param v0 顶点ID，以此点为起点
	 * @param vList 图中需要检索的顶点，必须包含v0
	 * @return
	 */
	public DigraphSearchResult searchRange(String v0,List<Vertex> vList);
}
