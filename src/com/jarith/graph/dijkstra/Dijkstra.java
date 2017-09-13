package com.jarith.graph.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.jarith.graph.comm.IWeight;

/**
 * 迪杰特斯拉算法(有向图最短路径搜索)<br>
 * 算法步骤：<br>
 * a.初始时，S只包含源点，即S＝{v}，v的距离为0。U包含除v外的其他顶点，即:U={其余顶点}，若v与U中顶点u有边，则<u,v>正常有权值，若u不是v的出边邻接点，则<u,v>权值为∞<br>
 * b.从U中选取一个距离v最小的顶点k，把k，加入S中（该选定的距离就是v到k的最短路径长度）<br>
 * c.以k为新考虑的中间点，修改U中各顶点的距离；若从源点v到顶点u的距离（经过顶点k）比原来距离（不经过顶点k）短，则修改顶点u的距离值，修改后的距离值的顶点k的距离加上边上的权<br>
 * d.重复步骤b和c直到所有顶点都包含在S中<br>
 * 
 * @author liuz@aotian.com
 * @date 2017年9月6日 下午4:00:11
 */
public class Dijkstra<T extends IWeight> {
	private T[][] adjacentMatrix; // 邻接矩阵
	private boolean[] isInGatherS; // 定点S集合标志
	private int[] prevs; // 定点路径索引
	private IWeight[] minWeights; // 最短路径

	private static IWeight T_INFINITY;
	private static IWeight T_ZERO;
	private static int MATRIX_SIZE;

	private int status = 0; // 0：初始状态，1：初始化完成，2：所搜得到结果

	/**
	 * 用邻接矩阵初始化
	 * 
	 * @param adjacentMatrix
	 */
	public void init(T[][] adjacentMatrix) {
		if (adjacentMatrix == null) { // 邻接矩阵为 空
			new IllegalArgumentException("Adjacent matrix is empty");
		}

		if (adjacentMatrix.length == 0) { // 邻接矩阵，行数为0
			new IllegalArgumentException("Adjacent matrix row count is zero");
		}

		if (adjacentMatrix[0].length == 0) { // 邻接矩阵，列数为0
			new IllegalArgumentException("Adjacent matrix column count is zero");
		}

		if (adjacentMatrix[0].length != adjacentMatrix.length) { // 邻接矩阵，列数为0
			new IllegalArgumentException("Adjacent matrix row count not equals column count,rowCount="
					+ adjacentMatrix.length + ",columnCount=" + adjacentMatrix[0].length);
		}
		status = 0;

		T_INFINITY = adjacentMatrix[0][0].getInfinity();
		T_ZERO = adjacentMatrix[0][0].getZero();
		MATRIX_SIZE = adjacentMatrix.length;

		this.adjacentMatrix = adjacentMatrix;
		isInGatherS = new boolean[MATRIX_SIZE];
		Arrays.fill(isInGatherS, false); // 初始化，所有顶点都不在集合S中
		prevs = new int[MATRIX_SIZE];
		Arrays.fill(prevs, -1);
		minWeights = new IWeight[MATRIX_SIZE];
		Arrays.fill(minWeights, T_INFINITY);
		status = 1;
	}

	/**
	 * 核心搜索算法
	 * 
	 * @param v0
	 *            定点
	 * @param vd
	 *            目标点
	 */
	public void search(int v0) {
		isInGatherS[v0] = true; // 将v0放入集合S
		minWeights[v0] = T_ZERO;

		// 1. S集合中最大的，作为最短路径的起点,初始为v0
		int minWeightIndex = v0; // 最短权重(上一次确定的最短路径经过点)

		// 循环一次，搜索出一个顶点u(U集合中，距离v0最近的点),并加入S
		for (int i = 1; i < MATRIX_SIZE; i++) {
			IWeight minWeight = minWeights[minWeightIndex]; // 默认，无限大
			// 2. 找出U中，离上一个定点minWeightIndex之间最短的点
			for (int k = 0; k < MATRIX_SIZE; k++) {
				// k在U中，且k的值小于无穷大,且k的值与前一个点的距离和，小于当前最小距离
				if (!isInGatherS[k] && adjacentMatrix[minWeightIndex][k].compareTo(T_INFINITY) < 0
						&& minWeights[minWeightIndex].add(adjacentMatrix[minWeightIndex][k])
								.compareTo(minWeights[k]) < 0) {
					minWeights[k] = minWeights[minWeightIndex].add(adjacentMatrix[minWeightIndex][k]);
					prevs[k] = minWeightIndex;
				}
			}

			int nextMinWeightIndex = -1;
			minWeight = T_INFINITY;
			// 3. 搜索集合U中，最短路径最短的，加入S
			for (int k = 0; k < MATRIX_SIZE; k++) {
				if (!isInGatherS[k] && minWeights[k].compareTo(minWeight) < 0) {
					nextMinWeightIndex = k;
					minWeight = minWeights[k];
				}
			}

			// 4. 将最短距离加入集合S
			if (nextMinWeightIndex != -1) {
				minWeightIndex = nextMinWeightIndex;
				isInGatherS[nextMinWeightIndex] = true;
			}
		}
		status = 2;
	}

	/**
	 * 状态非法时抛出异常
	 */
	private void resultCheck() {
		String msg = null;
		if (status == 0) {
			msg = "Please call init(T[][] adjacentMatrix) methed";
		} else if (status == 1) {
			msg = "Please call search(int v0) methed";
		}
		if (msg != null) {
			throw new IllegalStateException(msg);
		}
	}

	/**
	 * 获取前驱定点数组
	 * 
	 * @return
	 */
	public int[] getPres() {
		resultCheck();
		return prevs;
	}

	/**
	 * 获取每个定点的最小路径权重结果
	 * 
	 * @return
	 */
	public IWeight[] getMinWeights() {
		resultCheck();
		return minWeights;
	}

	/**
	 * 获取v0到vd之间的最短路径信息
	 * @param vd
	 */
	public List<VResultInfor> getSearchResult(int vd) {
		if(vd >= MATRIX_SIZE || vd < 0) {
			new IllegalArgumentException("Target does not exist");
		}
		
		List<VResultInfor> list = new ArrayList<VResultInfor>();
		int t = vd;
		while(prevs[t] != -1){
			VResultInfor vr = new VResultInfor();
			vr.index = t;
			vr.minWeight = minWeights[t];
			list.add(vr);
			t = prevs[t];
		}
		VResultInfor vr = new VResultInfor();
		vr.index = t;
		vr.minWeight = minWeights[t];
		list.add(vr);
		Collections.reverse(list);
		return list;
	}

}
