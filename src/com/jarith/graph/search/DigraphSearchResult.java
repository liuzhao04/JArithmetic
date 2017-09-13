package com.jarith.graph.search;

import java.util.Arrays;
import java.util.List;

import com.jarith.graph.comm.FloatWeight;
import com.jarith.graph.g.Vertex;

/**
 * 图搜索结果
 * 
 * @author liuz@aotian.com
 * @date 2017年9月8日 上午8:44:23
 */
public class DigraphSearchResult {
	private FloatWeight[] wArr;
	private int[] pArr;
	private List<Vertex> vList;

	public FloatWeight[] getwArr() {
		return wArr;
	}

	public void setwArr(FloatWeight[] wArr) {
		this.wArr = wArr;
	}

	public int[] getpArr() {
		return pArr;
	}

	public void setpArr(int[] pArr) {
		this.pArr = pArr;
	}

	public List<Vertex> getvList() {
		return vList;
	}

	public void setvList(List<Vertex> vList) {
		this.vList = vList;
	}

	@Override
	public String toString() {
		return "DigraphSearchResult [wArr=" + Arrays.toString(wArr) + ", pArr=" + Arrays.toString(pArr) + ", vList="
				+ vList + "]";
	}

}
