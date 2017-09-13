package com.jarith.graph.comm;

/**
 * 图边的权重类-精确度float
 * 
 * @author liuz@aotian.com
 * @date 2017年9月7日 下午5:18:11
 */
public class FloatWeight implements IWeight {
	private Float value;

	public static final FloatWeight MAX_VALUE = new FloatWeight(Float.MAX_VALUE);
	public static final FloatWeight ZERO_VALUE = new FloatWeight(new Float(0));

	public FloatWeight(Float value) {
		if (null == value) {
			this.value = Float.MAX_VALUE;
			return;
		}
		this.value = value;
	}

	@Override
	public IWeight getInfinity() {
		return MAX_VALUE;
	}

	@Override
	public String strValue() {
		return String.valueOf(value);
	}

	@Override
	public IWeight getZero() {
		return ZERO_VALUE;
	}

	@Override
	public int compareTo(IWeight other) {
		FloatWeight k = (FloatWeight) other;
		return value.compareTo(k.value);
	}

	@Override
	public IWeight add(IWeight other) {
		FloatWeight k = (FloatWeight) other;
		return new FloatWeight(value + k.value);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		FloatWeight other = (FloatWeight) obj;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return strValue();
	}

}
