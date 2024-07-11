 package com.yuji.system.preference;

/**
 * 用户偏好 配置项
 * 
 * @author Liguoqiang
 */
public interface IUserPreference {
	
	/**
	 * 唯一标识
	 * 
	 * @return
	 */
	public String getId();

	/**
	 * 显示名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 校验数据
	 * 
	 * @param config
	 */
	public boolean validate(Object config);
	
	/**
	 * 默认值
	 * 
	 * @return
	 */
	public Object getDefaultValue();
}
