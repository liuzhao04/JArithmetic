package com.jarith.graph;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

import com.jarith.graph.comm.IWeight;
import com.jarith.graph.g.Digraph;
import com.jarith.graph.g.DigraphLoader;
import com.jarith.graph.g.VertexRouter;
import com.jarith.graph.search.DigraphSearchResult;
import com.jarith.graph.search.DigraphSearchResultParser;
import com.jarith.graph.search.DijkstraDigraphSearcher;
import com.jarith.graph.search.IDigraphSearcher;

/**
 * 基础算法使用实例
 * 
 * @author liuz@aotian.com
 * @date 2017年9月7日 上午10:08:47
 */
public class DijkstraDemo {

	public static KTYPE val(int value){
		return new KTYPE(value);
	}
	
	public static void main(String[] args) {
		/* 1. Dijkstra 算法测试
		KTYPE[][] matrix =  new KTYPE[][]{
									{val(0),val(7),val(4),val(6)},
									{val(7),val(0),val(Integer.MAX_VALUE),val(2)},
									{val(4),val(Integer.MAX_VALUE),val(0),val(1)},
									{val(6),val(1),val(1),val(0)}
							  };
		Dijkstra<KTYPE> d = new Dijkstra<DijkstraDemo.KTYPE>();
		d.init(matrix);
		d.search(0);
		System.out.println(d.getSearchResult(2));
		//*/
		
		//* 2. 图加载测试
		try {
			String jsonConfig = FileUtils.readFileToString(new File("conf/graph.json"));
			Digraph g = DigraphLoader.loadDigraphFromJson(jsonConfig);
			System.out.println(g);
			
			//* 3. 创建邻接矩阵
			IDigraphSearcher searcher = new DijkstraDigraphSearcher();
			searcher.init(g);
			DigraphSearchResult rs = searcher.search("6");
			System.out.println(rs);
			//*/
			
			//* 4. 路由读取
			VertexRouter vr = DigraphSearchResultParser.parse(rs, "7");
			System.out.println(vr.toNameRouterString());
			
			System.out.println("-----------------------------");
			List<VertexRouter> vrs = DigraphSearchResultParser.parseAll(rs);
			for(VertexRouter r : vrs ){
				System.out.println(r.toNameRouterString());
			}
			//*/
			
			System.out.println("-----------------------------");
			
			//* 5. 区域搜索测试
			rs = searcher.searchRangeS("8",Arrays.asList("1","2","3","4","5","6","8"));
			vrs = DigraphSearchResultParser.parseAll(rs);
			for(VertexRouter r : vrs ){
				System.out.println(r.toNameRouterString());
			}
			//*/
		} catch (IOException e) {
			e.printStackTrace();
		}
		//*/
	}
	
	public static class KTYPE implements IWeight {
		public static KTYPE MAX_VALUE = new KTYPE(Integer.MAX_VALUE);
		public static KTYPE ZERO_VALUE = new KTYPE(0);

		private int value;

		public KTYPE(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		@Override
		public IWeight getInfinity() {
			return MAX_VALUE;
		}

		@Override
		public IWeight getZero() {
			return ZERO_VALUE;
		}

		@Override
		public int compareTo(IWeight other) {
			KTYPE k = (KTYPE) other;
			return value - k.value;
		}

		@Override
		public IWeight add(IWeight other) {
			KTYPE k = (KTYPE) other;
			return new KTYPE(value + k.value);
		}

		@Override
		public String toString() {
			return "KTYPE [value=" + value + "]";
		}

		@Override
		public String strValue() {
			return String.valueOf(value);
		}
		
	}
}
