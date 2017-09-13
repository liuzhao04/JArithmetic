package com.jarith.graph.g;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 图的顶点
 * 
 * @author liuz@aotian.com
 * @date 2017年9月7日 上午11:28:46
 */
public class Vertex {
	private String id;
	private String name;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Vertex [id=" + id + ", name=" + name + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Vertex other = (Vertex) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}



	/**
	 * 顶点构造工具
	 * 
	 * @author liuz@aotian.com
	 * @date 2017年9月7日 下午12:08:17
	 */
	public static class VertexBuilder {
		private List<Vertex> list;

		public VertexBuilder() {
			reset();
		}

		public static List<Vertex> parse(Object... ids) {
			if (ids == null || ids.length == 0) {
				return Collections.emptyList();
			}

			List<Vertex> vs = new ArrayList<Vertex>();
			for (Object ob : ids) {
				Vertex v = new Vertex();
				v.setId(String.valueOf(ob));
				vs.add(v);
			}
			return vs;
		}
		
		public static Vertex parse(Object id,String name) {
			Vertex v = new Vertex();
			v.setId(String.valueOf(id));
			v.setName(name);
			return v;
		}

		public void reset() {
			list = new ArrayList<Vertex>();
		}

		public VertexBuilder append(Object id, String name) {
			if (id == null) {
				return this;
			}
			Vertex v = new Vertex();
			v.setId(String.valueOf(id));
			v.setName(name);
			list.add(v);
			return this;
		}
		
		public VertexBuilder append(Object id) {
			append(id,null);
			return this;
		}

		public List<Vertex> build() {
			return list;
		}
	}
}
