package com.example.android.basicpermissions.model;

import org.json.JSONObject;

import java.io.Serializable;

public abstract class BaseVo implements Serializable {

	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -2364937401902365995L;
	private boolean isVoVaild = true;

	/**
	 * <b>构造方法。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 根据JSON对象构造Vo
	 * 
	 * @param json
	 */
	public BaseVo(JSONObject json) {
		init(json);
	}

	public BaseVo() {
	}

	protected int id;

	protected int returnCode;

	public int getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(int returnCode) {
		this.returnCode = returnCode;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * 
	 * <b>init。</b>
	 * <p>
	 * <b>详细说明：</b>
	 * </p>
	 * <!-- 在此添加详细说明 --> 无。
	 */
	protected abstract void init(JSONObject jsonOfVo);

	public boolean isVoVaild() {
		return isVoVaild;
	}

	public void setVoVaild(boolean isVoVaild) {
		this.isVoVaild = isVoVaild;
	}

}
