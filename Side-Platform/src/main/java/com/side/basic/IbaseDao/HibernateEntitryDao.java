/**
 * 
 */
package com.side.basic.IbaseDao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.side.basic.common.utils.DetachedCriteriaTS;
import com.side.basic.common.utils.PageMode;

/**
 * @author gmc
 * @see 数据库实体操作实现类，提供通用查询，新增，修改，删除接口
 */
public interface HibernateEntitryDao {

	/**
	 * 查询单个实体
	 * @param entityClass
	 * @param id
	 * @return
	 */
	public <T> T get(Class<T> entityClass, Serializable id);
	
	/**
	 * 返回单条数据
	 * 
	 * @see DetachedCriteriaWrapper
	 */
	<T> T find(final DetachedCriteriaTS<T> criteria);
	
	/**
	 * 分页查询
	 * @param criteria
	 * @param first 第一条记录
	 * @param maxResult 最后一条记录
	 * @return
	 */
	public <T> List<T> find(DetachedCriteriaTS<T> detachedCriteria, int first, int maxResult);
	
	/**
	 * 查询总数
	 * @param criteria
	 * @param maxResult
	 * @return
	 */
	public <T> List<T> find(DetachedCriteriaTS<T> detachedCriteria, int maxResult);
	
	/**
	 * 查询所有符合条件记录
	 * @param detachedCriteria
	 * @return
	 */
	public <T> List<T> findAll(DetachedCriteriaTS<T> detachedCriteria);
	
	/**
	 * 保存一个对象
	 * @param obj
	 */
	public void save(Object entity);
	
	/**
	 * 更新指定对象
	 * @param entity 对象
	 */
	public void saveOrUpdate(Object entity);
	
	/**
	 * 更新指定集合对象
	 * @param entities	对象集合
	 */
	public <T> void saveOrUpdateAll(Collection<T> entities) ;
	
	/**
	 * 删除指定对象
	 * @param entity 实体对象
	 */
	public void delete(Object entity);
	
	/**
	 * 删除指定对象集合
	 * @param entities	对象集合
	 */
	public <T> void deleteAll(Collection<T> entities);
	
	/**
	 * 查询总记录数
	 * @param criteria
	 * @return
	 */
	public <T> int count(final DetachedCriteriaTS<T> criteria);
	
	/**
	 * 分页查询
	 * @param criteria 参数对象
	 * @param pi
	 * @param ps
	 * @return
	 */
	public <T> PageMode<T> findForPage(DetachedCriteriaTS<T> criteria, int pageNumber, int pageSize);
	
	/**
	 * 根据sql查询数据
	 * @param sql
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @param clazz
	 * @return
	 */
	public <T> PageMode<T> findBySQL(String sql, Map<String, String> params, int pageNumber, int pageSize, Class<T> clazz);
	
	/**
	 * 根据sql查询对象
	 * @param sql
	 * @param params
	 * @param clazz
	 * @return
	 */
	public <T> T findObjBySQL(String sql, Map<String, String> params, Class<T> clazz);
	
	/**
	 * 根据sql查询数据集合
	 * @param sql
	 * @param params
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 */
	public PageMode findBySQL(String sql, Map<String, String> params, int pageNumber, int pageSize);
	
	/**
	 * 根据sql查询对象
	 * @param sql
	 * @param params
	 * @return
	 */
	public Object findObjBySQL(String sql, Map<String, String> params);
	
	/**
	 * 根据sql操作数据库
	 * @param sql
	 * @param params
	 */
	public void executeSql(String sql, Map<String, String> params);
	
}
