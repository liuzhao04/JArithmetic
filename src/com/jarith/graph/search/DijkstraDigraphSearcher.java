package com.jarith.graph.search;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.jarith.graph.comm.FloatWeight;
import com.jarith.graph.comm.IWeight;
import com.jarith.graph.dijkstra.Dijkstra;
import com.jarith.graph.g.Digraph;
import com.jarith.graph.g.Edge;
import com.jarith.graph.g.Vertex;
import com.jarith.graph.g.Vertex.VertexBuilder;

/**
 * 图搜索 - 迪杰特斯拉算法
 * 
 * @author liuz@aotian.com
 * @date 2017年9月7日 下午6:27:44
 */
public class DijkstraDigraphSearcher implements IDigraphSearcher {
	private Digraph digraph;
	private Dijkstra<FloatWeight> dijkstra; // 算法工具

	private List<Vertex> vListTmp;
	private List<Edge> eListTmp;

	@Override
	public void init(Digraph digraph) {
		this.digraph = digraph;
		this.dijkstra = new Dijkstra<FloatWeight>();
	}

	@Override
	public DigraphSearchResult search(String v0) {
		searchParamsCheckS(v0, null);
		// 1. 构造一个dijkstra邻接矩阵
		createSearchArea(null); // 创建搜索区间
		createMatrix(); // 创建邻接矩阵

		// 2. 查询出所有路径
		this.dijkstra.search(findVertex(v0));

		// 3. 构造图的搜索结果
		FloatWeight[] wArr = cast(this.dijkstra.getMinWeights());
		int[] pArr = this.dijkstra.getPres();
		DigraphSearchResult rs = new DigraphSearchResult();
		rs.setwArr(wArr);
		rs.setpArr(pArr);
		rs.setvList(vListTmp);
		return rs;
	}

	private FloatWeight[] cast(IWeight[] wArr) {
		FloatWeight[] wArr_ = new FloatWeight[wArr.length];
		int index = 0;
		for (IWeight w : wArr) {
			wArr_[index++] = (FloatWeight) w;
		}
		return wArr_;
	}

	@Override
	public DigraphSearchResult searchRangeS(String v0, List<String> vList) {
		searchParamsCheckS(v0, vList);
		// 1. 构造一个dijkstra邻接矩阵
		createSearchAreaS(vList);
		createMatrix(); // 创建邻接矩阵

		// 2. 查询出所有路径
		this.dijkstra.search(findVertex(v0));

		// 3. 构造图的搜索结果
		FloatWeight[] wArr = cast(this.dijkstra.getMinWeights());
		int[] pArr = this.dijkstra.getPres();
		DigraphSearchResult rs = new DigraphSearchResult();
		rs.setwArr(wArr);
		rs.setpArr(pArr);
		rs.setvList(vListTmp);
		return rs;
	}

	@Override
	public DigraphSearchResult searchRange(String v0, List<Vertex> vList) {
		searchParamsCheck(v0, vList);
		// 1. 构造一个dijkstra邻接矩阵
		createSearchArea(vList);
		createMatrix(); // 创建邻接矩阵

		// 2. 查询出所有路径
		this.dijkstra.search(findVertex(v0));

		// 3. 构造图的搜索结果
		FloatWeight[] wArr = cast(this.dijkstra.getMinWeights());
		int[] pArr = this.dijkstra.getPres();
		DigraphSearchResult rs = new DigraphSearchResult();
		rs.setwArr(wArr);
		rs.setpArr(pArr);
		rs.setvList(vListTmp);
		return rs;
	}

	private int findVertex(String v0) {
		Vertex v = VertexBuilder.parse(v0, null);
		return this.vListTmp.indexOf(v);
	}

	/**
	 * 创建搜索区域
	 * 
	 * @param vListMin
	 *            顶点ID列表
	 */
	private void createSearchAreaS(List<String> vListMin) {
		if (vListMin == null || vListMin.size() == 0) {
			createSearchArea(null);
		}
		List<Vertex> vList = new ArrayList<Vertex>();
		for (String vId : vListMin) {
			Vertex v = this.digraph.getMap().get(vId);
			if (v != null) {
				vList.add(v);
			}
		}
		createSearchArea(vList);
	}

	/**
	 * 创建搜索区间
	 * 
	 * @param vListMin
	 *            顶点列表
	 */
	private void createSearchArea(List<Vertex> vListMin) {
		if (vListMin == null || vListMin.size() == 0) {
			this.vListTmp = this.digraph.getvList();
			this.eListTmp = this.digraph.geteList();
			return;
		}
		// 确定顶点范围
		List<Vertex> vList = new ArrayList<Vertex>();
		for (Vertex vId : vListMin) {
			Vertex v = this.digraph.getMap().get(vId.getId());
			if (v != null) {
				vList.add(v);
			}
		}
		this.vListTmp = vList;

		// 确定区间范围
		List<Edge> eList = new ArrayList<Edge>();
		for (Edge e : this.digraph.geteList()) {
			// 必须两个顶点在区域顶点集合内
			if (this.vListTmp.contains(e.getVa()) && this.vListTmp.contains(e.getVb())) {
				eList.add(e);
			}
		}
		this.eListTmp = eList;
	}

	/**
	 * 创建邻接矩阵
	 */
	private void createMatrix() {
		int vCount = this.vListTmp.size();
		FloatWeight[][] adjacentMatrix = new FloatWeight[vCount][vCount];
		for (int i = 0; i < vCount; i++) {
			// 个点间初始化为互不相连（用无穷大填充）
			Arrays.fill(adjacentMatrix[i], FloatWeight.MAX_VALUE);
			// 个点与自己的间距为0
			adjacentMatrix[i][i] = FloatWeight.ZERO_VALUE;
		}

		for (Edge e : this.eListTmp) {
			Vertex va = e.getVa();
			Vertex vb = e.getVb();
			int vaIndex = this.vListTmp.indexOf(va);
			int vbIndex = this.vListTmp.indexOf(vb);
			adjacentMatrix[vaIndex][vbIndex] = e.getA2b();
			adjacentMatrix[vbIndex][vaIndex] = e.getB2a();
		}

		this.dijkstra.init(adjacentMatrix);
	}

	private void searchParamsCheckS(String v0, List<String> vertexs) {
		if (StringUtils.isBlank(v0)) {
			throw new IllegalArgumentException("The starting point is null");
		}
		if (vertexs != null && vertexs.size() > 0) {
			if (!vertexs.contains(v0)) {
				throw new IllegalArgumentException("The starting point is not in the assigned vertexs : " + v0);
			}
		}
	}

	private void searchParamsCheck(String v0, List<Vertex> vertexs) {
		if (StringUtils.isBlank(v0)) {
			throw new IllegalArgumentException("The starting point is null");
		}
		Vertex vv0 = VertexBuilder.parse(v0, null);
		if (vertexs != null && vertexs.size() > 0) {
			if (!vertexs.contains(vv0)) {
				throw new IllegalArgumentException("The starting point is not in the assigned vertexs : " + v0);
			}
		}
	}

}
