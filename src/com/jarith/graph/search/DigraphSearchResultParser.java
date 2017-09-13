package com.jarith.graph.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.jarith.graph.comm.FloatWeight;
import com.jarith.graph.g.Vertex;
import com.jarith.graph.g.VertexRouter;
import com.jarith.graph.g.Vertex.VertexBuilder;

/**
 * ͼ���������������
 * 
 * @author liuz@aotian.com
 * @date 2017��9��8�� ����11:21:10
 */
public class DigraphSearchResultParser {
	
	/**
	 * ����������ж�ȡ·����Ϣ
	 * @param result
	 * @param vd
	 * @return
	 */
	public static VertexRouter parse(DigraphSearchResult result,String vd) {
		List<Vertex> vList = result.getvList();
		Vertex v = VertexBuilder.parse(vd, null);
		int index = vList.indexOf(v);
		
		if(index >= vList.size() || index < 0) {
			new IllegalArgumentException("Target does not exist");
		}
		
		int tmp = index;
		int preId = -1;
		
		VertexRouter vr = new VertexRouter();
		
		List<Vertex> rList = new ArrayList<Vertex>();
		List<FloatWeight> wList = new ArrayList<FloatWeight>();
		while((preId = result.getpArr()[tmp]) != -1){
			rList.add(result.getvList().get(tmp));
			wList.add(result.getwArr()[tmp]);
			tmp = preId;
		}
		rList.add(result.getvList().get(tmp));
		wList.add(result.getwArr()[tmp]);
		
		Collections.reverse(rList);
		Collections.reverse(wList);
		
		vr.setvList(rList);
		FloatWeight[] k = new FloatWeight[wList.size()];
		wList.toArray(k);
		vr.setWeight(k);
		return vr;
	}
	
	/**
	 * ����������ж�ȡ����·����Ϣ
	 * @param result
	 * @return
	 */
	public static List<VertexRouter> parseAll(DigraphSearchResult result) {
		List<VertexRouter> vrs = new ArrayList<VertexRouter>();
		result.getvList();
		for(int i = 0 ; i < result.getvList().size(); i++) {
			Vertex v = result.getvList().get(i);
			if(result.getpArr()[i] != -1) {
				vrs.add(parse(result, v.getId()));
			}
		}
		return vrs;
	}
}
