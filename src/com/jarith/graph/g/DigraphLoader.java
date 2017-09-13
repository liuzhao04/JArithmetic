package com.jarith.graph.g;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jarith.graph.g.Edge.EdgeBuilder;
import com.jarith.graph.g.Vertex.VertexBuilder;

/**
 * 图加载器
 * 
 * @author liuz@aotian.com
 * @date 2017年9月7日 下午6:28:52
 */
public class DigraphLoader {
	/**
	 * 有向图加载-json配置
	 * 
	 * @return
	 */
	public static Digraph loadDigraphFromJson(String json) {
		Gson g = new Gson();
		JsonObject obj = g.fromJson(json, JsonObject.class);
		JsonArray vList = (JsonArray) obj.get("vertexs");
		JsonArray eList = (JsonArray) obj.get("edges");
		Map<String, Vertex> map = new HashMap<String, Vertex>();
		List<Vertex> vvList = new ArrayList<Vertex>();
		for (int i = 0; i < vList.size(); i++) {
			JsonObject e = vList.get(i).getAsJsonObject();
			String id = e.get("id").getAsString();
			Vertex v = VertexBuilder.parse(id, e.get("name").getAsString());
			if (map.containsKey(id)) {
				throw new IllegalArgumentException("Vertex is repeated : " + id);
			}
			map.put(id, v);
			vvList.add(v);
		}
		EdgeBuilder eb = new EdgeBuilder();
		eb.init(map);
		for (int i = 0; i < eList.size(); i++) {
			JsonObject e = eList.get(i).getAsJsonObject();
			String va = e.get("va").getAsString();
			String vb = e.get("vb").getAsString();
			JsonElement f1 = e.get("a2b");
			JsonElement f2 = e.get("b2a");
			Float a2b = f1 == null ? null : f1.getAsFloat();
			Float b2a = f2 == null ? null : f2.getAsFloat();
			eb.append(va, vb, a2b, b2a);
		}
		List<Edge> eeList = eb.build();
		Digraph di = new Digraph();
		di.seteList(eeList);
		di.setMap(map);
		di.setvList(vvList);
		return di;
	}

	/**
	 * 无向图加载-json配置
	 * 
	 * @return
	 */
	public static Digraph loadUnDigraphFromJson(String json) {
		Gson g = new Gson();
		JsonObject obj = g.fromJson(json, JsonObject.class);
		JsonArray vList = (JsonArray) obj.get("vertexs");
		JsonArray eList = (JsonArray) obj.get("edges");
		Map<String, Vertex> map = new HashMap<String, Vertex>();
		List<Vertex> vvList = new ArrayList<Vertex>();
		for (int i = 0; i < vList.size(); i++) {
			JsonObject e = vList.get(i).getAsJsonObject();
			String id = e.get("id").getAsString();
			Vertex v = VertexBuilder.parse(id, e.get("name").getAsString());
			if (map.containsKey(id)) {
				throw new IllegalArgumentException("Vertex is repeated : " + id);
			}
			map.put(id, v);
			vvList.add(v);
		}
		EdgeBuilder eb = new EdgeBuilder();
		eb.init(map);
		for (int i = 0; i < eList.size(); i++) {
			JsonObject e = eList.get(i).getAsJsonObject();
			String va = e.get("va").getAsString();
			String vb = e.get("vb").getAsString();
			JsonElement f1 = e.get("a2b");
			JsonElement f2 = e.get("b2a");
			Float a2b = f1 == null ? null : f1.getAsFloat();
			Float b2a = f2 == null ? null : f2.getAsFloat();

			// 无向图，两边权重相等检查
			if (a2b != null && b2a != null && !a2b.equals(b2a)) {
				throw new IllegalArgumentException(
						"Undigraph request a2b's weight equals b2a's : a2b=" + a2b + ",b2a=" + b2a);
			}
			// 无向图，从一边考值到另外一边，保证两边不出现无穷大
			if (a2b == null && b2a != null) {
				a2b = b2a;
			} else if (f2 == null && f1 != null) {
				b2a = a2b;
			}

			eb.append(va, vb, a2b, b2a);
		}
		List<Edge> eeList = eb.build();
		Digraph di = new Digraph();
		di.seteList(eeList);
		di.setMap(map);
		di.setvList(vvList);
		di.setUndigraph(true);
		return di;
	}
}
