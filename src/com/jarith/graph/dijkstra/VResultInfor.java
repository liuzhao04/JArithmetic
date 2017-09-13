package com.jarith.graph.dijkstra;

import com.jarith.graph.comm.IWeight;

/**
 * ��������Ϣ
 * 
 * @author liuz@aotian.com
 * @date 2017��9��7�� ����9:44:44
 */
public class VResultInfor {
	protected int index;
	protected IWeight minWeight;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public IWeight getMinWeight() {
		return minWeight;
	}

	public void setMinWeight(IWeight minWeight) {
		this.minWeight = minWeight;
	}

	@Override
	public String toString() {
		return "V"+index+"("+minWeight.strValue()+")";
	}

}
