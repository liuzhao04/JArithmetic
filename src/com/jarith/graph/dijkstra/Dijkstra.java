package com.jarith.graph.dijkstra;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.jarith.graph.comm.IWeight;

/**
 * �Ͻ���˹���㷨(����ͼ���·������)<br>
 * �㷨���裺<br>
 * a.��ʼʱ��Sֻ����Դ�㣬��S��{v}��v�ľ���Ϊ0��U������v����������㣬��:U={���ඥ��}����v��U�ж���u�бߣ���<u,v>������Ȩֵ����u����v�ĳ����ڽӵ㣬��<u,v>ȨֵΪ��<br>
 * b.��U��ѡȡһ������v��С�Ķ���k����k������S�У���ѡ���ľ������v��k�����·�����ȣ�<br>
 * c.��kΪ�¿��ǵ��м�㣬�޸�U�и�����ľ��룻����Դ��v������u�ľ��루��������k����ԭ�����루����������k���̣����޸Ķ���u�ľ���ֵ���޸ĺ�ľ���ֵ�Ķ���k�ľ�����ϱ��ϵ�Ȩ<br>
 * d.�ظ�����b��cֱ�����ж��㶼������S��<br>
 * 
 * @author liuz@aotian.com
 * @date 2017��9��6�� ����4:00:11
 */
public class Dijkstra<T extends IWeight> {
	private T[][] adjacentMatrix; // �ڽӾ���
	private boolean[] isInGatherS; // ����S���ϱ�־
	private int[] prevs; // ����·������
	private IWeight[] minWeights; // ���·��

	private static IWeight T_INFINITY;
	private static IWeight T_ZERO;
	private static int MATRIX_SIZE;

	private int status = 0; // 0����ʼ״̬��1����ʼ����ɣ�2�����ѵõ����

	/**
	 * ���ڽӾ����ʼ��
	 * 
	 * @param adjacentMatrix
	 */
	public void init(T[][] adjacentMatrix) {
		if (adjacentMatrix == null) { // �ڽӾ���Ϊ ��
			new IllegalArgumentException("Adjacent matrix is empty");
		}

		if (adjacentMatrix.length == 0) { // �ڽӾ�������Ϊ0
			new IllegalArgumentException("Adjacent matrix row count is zero");
		}

		if (adjacentMatrix[0].length == 0) { // �ڽӾ�������Ϊ0
			new IllegalArgumentException("Adjacent matrix column count is zero");
		}

		if (adjacentMatrix[0].length != adjacentMatrix.length) { // �ڽӾ�������Ϊ0
			new IllegalArgumentException("Adjacent matrix row count not equals column count,rowCount="
					+ adjacentMatrix.length + ",columnCount=" + adjacentMatrix[0].length);
		}
		status = 0;

		T_INFINITY = adjacentMatrix[0][0].getInfinity();
		T_ZERO = adjacentMatrix[0][0].getZero();
		MATRIX_SIZE = adjacentMatrix.length;

		this.adjacentMatrix = adjacentMatrix;
		isInGatherS = new boolean[MATRIX_SIZE];
		Arrays.fill(isInGatherS, false); // ��ʼ�������ж��㶼���ڼ���S��
		prevs = new int[MATRIX_SIZE];
		Arrays.fill(prevs, -1);
		minWeights = new IWeight[MATRIX_SIZE];
		Arrays.fill(minWeights, T_INFINITY);
		status = 1;
	}

	/**
	 * ���������㷨
	 * 
	 * @param v0
	 *            ����
	 * @param vd
	 *            Ŀ���
	 */
	public void search(int v0) {
		isInGatherS[v0] = true; // ��v0���뼯��S
		minWeights[v0] = T_ZERO;

		// 1. S���������ģ���Ϊ���·�������,��ʼΪv0
		int minWeightIndex = v0; // ���Ȩ��(��һ��ȷ�������·��������)

		// ѭ��һ�Σ�������һ������u(U�����У�����v0����ĵ�),������S
		for (int i = 1; i < MATRIX_SIZE; i++) {
			IWeight minWeight = minWeights[minWeightIndex]; // Ĭ�ϣ����޴�
			// 2. �ҳ�U�У�����һ������minWeightIndex֮����̵ĵ�
			for (int k = 0; k < MATRIX_SIZE; k++) {
				// k��U�У���k��ֵС�������,��k��ֵ��ǰһ����ľ���ͣ�С�ڵ�ǰ��С����
				if (!isInGatherS[k] && adjacentMatrix[minWeightIndex][k].compareTo(T_INFINITY) < 0
						&& minWeights[minWeightIndex].add(adjacentMatrix[minWeightIndex][k])
								.compareTo(minWeights[k]) < 0) {
					minWeights[k] = minWeights[minWeightIndex].add(adjacentMatrix[minWeightIndex][k]);
					prevs[k] = minWeightIndex;
				}
			}

			int nextMinWeightIndex = -1;
			minWeight = T_INFINITY;
			// 3. ��������U�У����·����̵ģ�����S
			for (int k = 0; k < MATRIX_SIZE; k++) {
				if (!isInGatherS[k] && minWeights[k].compareTo(minWeight) < 0) {
					nextMinWeightIndex = k;
					minWeight = minWeights[k];
				}
			}

			// 4. ����̾�����뼯��S
			if (nextMinWeightIndex != -1) {
				minWeightIndex = nextMinWeightIndex;
				isInGatherS[nextMinWeightIndex] = true;
			}
		}
		status = 2;
	}

	/**
	 * ״̬�Ƿ�ʱ�׳��쳣
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
	 * ��ȡǰ����������
	 * 
	 * @return
	 */
	public int[] getPres() {
		resultCheck();
		return prevs;
	}

	/**
	 * ��ȡÿ���������С·��Ȩ�ؽ��
	 * 
	 * @return
	 */
	public IWeight[] getMinWeights() {
		resultCheck();
		return minWeights;
	}

	/**
	 * ��ȡv0��vd֮������·����Ϣ
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
