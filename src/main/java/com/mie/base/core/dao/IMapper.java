package com.mie.base.core.dao;

import com.mie.base.core.entity.IExample;
import org.apache.ibatis.annotations.Param;

import java.io.Serializable;
import java.util.List;

/**
 * 提供一些基本的操作方法
 * @param <T>
 * @param <Example>
 */
public interface IMapper<T extends Serializable, Example extends IExample<T>> {
    public int countByExample(Example example);

    public int deleteByExample(Example example);

    public int deleteByPrimaryKey(Integer id);

    public int insert(T record);

    public int insertSelective(T record);

    public List<T> selectByExample(Example example);

    public T selectByPrimaryKey(Integer id);

    public int updateByExampleSelective(@Param("record") T record, @Param("example") Example example);

    public int updateByExample(@Param("record") T record, @Param("example") Example example);

    public int updateByPrimaryKeySelective(T record);

    public int updateByPrimaryKey(T record);

    public List<T> selectByExampleByPage(Example example);
}
