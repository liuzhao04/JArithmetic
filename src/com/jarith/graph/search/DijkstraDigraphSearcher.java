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
 * ͼ���� - �Ͻ���˹���㷨
 * 
 * @author liuz@aotian.com
 * @date 2017��9��7�� ����6:27:44
 */
public class DijkstraDigraphSearcher implements IDigraphSearcher {
	private Digraph digraph;
	private Dijkstra<FloatWeight> dijkstra; // �㷨����

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
		// 1. ����һ��dijkstra�ڽӾ���
		createSearchArea(null); // ������������
		createMatrix(); // �����ڽӾ���

		// 2. ��ѯ������·��
		this.dijkstra.search(findVertex(v0));

		// 3. ����ͼ���������
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
		// 1. ����һ��dijkstra�ڽӾ���
		createSearchAreaS(vList);
		createMatrix(); // �����ڽӾ���

		// 2. ��ѯ������·��
		this.dijkstra.search(findVertex(v0));

		// 3. ����ͼ���������
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
		// 1. ����һ��dijkstra�ڽӾ���
		createSearchArea(vList);
		createMatrix(); // �����ڽӾ���

		// 2. ��ѯ������·��
		this.dijkstra.search(findVertex(v0));

		// 3. ����ͼ���������
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
	 * ������������
	 * 
	 * @param vListMin
	 *            ����ID�б�
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
	 * ������������
	 * 
	 * @param vListMin
	 *            �����б�
	 */
	private void createSearchArea(List<Vertex> vListMin) {
		if (vListMin == null || vListMin.size() == 0) {
			this.vListTmp = this.digraph.getvList();
			this.eListTmp = this.digraph.geteList();
			return;
		}
		// ȷ�����㷶Χ
		List<Vertex> vList = new ArrayList<Vertex>();
		for (Vertex vId : vListMin) {
			Vertex v = this.digraph.getMap().get(vId.getId());
			if (v != null) {
				vList.add(v);
			}
		}
		this.vListTmp = vList;

		// ȷ�����䷶Χ
		List<Edge> eList = new ArrayList<Edge>();
		for (Edge e : this.digraph.geteList()) {
			// �����������������򶥵㼯����
			if (this.vListTmp.contains(e.getVa()) && this.vListTmp.contains(e.getVb())) {
				eList.add(e);
			}
		}
		this.eListTmp = eList;
	}

	/**
	 * �����ڽӾ���
	 */
	private void createMatrix() {
		int vCount = this.vListTmp.size();
		FloatWeight[][] adjacentMatrix = new FloatWeight[vCount][vCount];
		for (int i = 0; i < vCount; i++) {
			// ������ʼ��Ϊ�������������������䣩
			Arrays.fill(adjacentMatrix[i], FloatWeight.MAX_VALUE);
			// �������Լ��ļ��Ϊ0
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
