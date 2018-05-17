package com.mie.base.core.service;

import com.mie.base.core.entity.IExample;
import com.mie.base.core.entity.PageView;

import java.io.Serializable;
import java.util.List;

public interface BaseService<T extends Serializable, Example extends IExample<T>, Id extends Object> {
	
	/**
	 * 添加
	 * @param t
	 */
    public void addObj(T t);

    /**
     * 根据id删除
     * @param id
     */
    public void deleteObjById(Id id);

    /**
     * 修改，实体中必须带有id
     * @param t
     */
    public void modifyObj(T t);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    public T queryObjById(Id id);
    
    /**
     * 根据查询条件查询所有
     * @param example
     * @return
     */
    public List<T> queryAllObjByExample(Example example);

    /**
     * 根据查询条件分页查询 
     * @param example
     * @return
     */
    public PageView<T> queryObjByPage(Example example);
}
