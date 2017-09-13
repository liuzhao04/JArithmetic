package com.jarith.graph.g;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.jarith.graph.comm.FloatWeight;

/**
 * 图的边
 * 
 * @author liuz@aotian.com
 * @date 2017年9月7日 下午12:13:17
 */
public class Edge {
	private Vertex va;
	private Vertex vb;
	private FloatWeight a2b;
	private FloatWeight b2a;

	public Vertex getVa() {
		return va;
	}

	public void setVa(Vertex va) {
		this.va = va;
	}

	public Vertex getVb() {
		return vb;
	}

	public void setVb(Vertex vb) {
		this.vb = vb;
	}

	public FloatWeight getA2b() {
		return a2b;
	}

	public void setA2b(FloatWeight a2b) {
		this.a2b = a2b;
	}

	public FloatWeight getB2a() {
		return b2a;
	}

	public void setB2a(FloatWeight b2a) {
		this.b2a = b2a;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((va == null) ? 0 : va.hashCode());
		result = prime * result + ((vb == null) ? 0 : vb.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Edge other = (Edge) obj;
		if (va == null) {
			if (other.va != null)
				return false;
		} else if (!va.equals(other.va))
			return false;
		if (vb == null) {
			if (other.vb != null)
				return false;
		} else if (!vb.equals(other.vb))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "E:<" + va.getId() + "," + vb.getId() + ">(" + a2b.strValue() + "," + b2a.strValue() + ")";
	}

	/**
	 * 边构造器
	 * 
	 * @author liuz@aotian.com
	 * @date 2017年9月7日 下午5:30:32
	 */
	public static class EdgeBuilder {
		private Map<String, Vertex> vertexs;
		private List<Edge> list;

		public void init(Map<String, Vertex> vertexs) {
			if (vertexs == null || vertexs.size() == 0) {
				new IllegalArgumentException("Vertex count is zero");
			}
			this.vertexs = vertexs;
			list = new ArrayList<Edge>();
		}

		public EdgeBuilder append(String va, String vb, Float a2b, Float b2a) {
			Vertex v1 = findVertexById(va);
			Vertex v2 = findVertexById(vb);
			if (v1 == null) {
				throw new IllegalArgumentException("Vertex va not exist :" + va);
			}
			if (v2 == null) {
				throw new IllegalArgumentException("Vertex va not exist :" + vb);
			}
			if(a2b == null && b2a == null){
				throw new IllegalArgumentException("Weight is null in the edge : <"+va+","+vb+">");
			}
			
			if (a2b != null && a2b < 0) {
				throw new IllegalArgumentException("Weight a2b must greater then 0 :" + a2b);
			}
			if (b2a != null && b2a < 0) {
				throw new IllegalArgumentException("Weight b2a must greater then 0 :" + b2a);
			}
			Edge e = new Edge();
			e.va = v1;
			e.vb = v2;
			e.a2b = new FloatWeight(a2b);
			e.b2a = new FloatWeight(b2a);
			if(list.contains(e)){
				throw new IllegalArgumentException("Edge is repeated : <" + va + "," + vb + ">");
			}
			list.add(e);
			return this;
		}

		public List<Edge> build() {
			return this.list;
		}

		private Vertex findVertexById(String id) {
			return vertexs.get(id);
		}
	}

}