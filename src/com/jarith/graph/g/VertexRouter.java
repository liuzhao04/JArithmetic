package com.jarith.graph.g;

import java.util.Arrays;
import java.util.List;

import com.jarith.graph.comm.FloatWeight;

/**
 * 顶点路由
 * 
 * @author liuz@aotian.com
 * @date 2017年9月8日 下午2:56:38
 */
public class VertexRouter {
	private List<Vertex> vList; // 按照路由顺序排列好的顶点列表
	private FloatWeight[] weight; // 按照路由顺序排列好的各顶点与源点的距离

	public List<Vertex> getvList() {
		return vList;
	}

	public void setvList(List<Vertex> vList) {
		this.vList = vList;
	}

	public FloatWeight[] getWeight() {
		return weight;
	}

	public void setWeight(FloatWeight[] weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "VertexRouter [vList=" + vList + ", weight=" + Arrays.toString(weight) + "]";
	}

	public String toRouterString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < vList.size(); i++){
			if(i > 0){
				sb.append("->");
			}
			Vertex v =  vList.get(i);
			sb.append(v.getId()).append("(").append(weight[i].strValue()).append(")");
		}
		return sb.toString();
	}
	
	public String toNameRouterString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < vList.size(); i++){
			if(i > 0){
				sb.append("->");
			}
			Vertex v =  vList.get(i);
			sb.append(v.getName()).append("(").append(weight[i].strValue()).append(")");
		}
		return sb.toString();
	}
	
}
