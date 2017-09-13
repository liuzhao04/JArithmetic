package com.jarith.graph.g;

import java.util.Arrays;
import java.util.List;

import com.jarith.graph.comm.FloatWeight;

/**
 * ����·��
 * 
 * @author liuz@aotian.com
 * @date 2017��9��8�� ����2:56:38
 */
public class VertexRouter {
	private List<Vertex> vList; // ����·��˳�����кõĶ����б�
	private FloatWeight[] weight; // ����·��˳�����кõĸ�������Դ��ľ���

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
