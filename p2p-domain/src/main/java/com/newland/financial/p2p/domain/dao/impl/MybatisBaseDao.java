package com.newland.financial.p2p.domain.dao.impl;

import com.github.pagehelper.PageInfo;
import com.newland.financial.p2p.common.util.Constants;
import com.newland.financial.p2p.common.util.PageModel;
import com.newland.financial.p2p.domain.entity.BaseEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.dao.DataAccessException;

import javax.annotation.Resource;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@SuppressWarnings({ "unchecked", "unused" })
public class MybatisBaseDao<T extends BaseEntity> extends SqlSessionDaoSupport
		{

	private Class<T> entityClass;
	private String mapperNamespace;

	public MybatisBaseDao() {
		setEntityClass((Class<T>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0]);
	}

	@Resource
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		super.setSqlSessionFactory(sqlSessionFactory);
	}

	protected List<T> selectAll() throws DataAccessException {
		List<T> result = new ArrayList<T>();
		try {
			result = this.getSqlSession().selectList(
					getMapperNamespace() + "." + "selectAll");
		} catch (DataAccessException e) {
			throw e;
		}
		return result;
	}

	protected boolean insertSelective(T entity) throws DataAccessException {
		return insert("insertSelective", entity);
	}

	protected boolean deleteByPrimaryKey(Long pk) throws DataAccessException {
		boolean flag = false;
		try {
			flag = this.getSqlSession().delete(
					getMapperNamespace() + "." + "deleteByPrimaryKey", pk) > 0 ? true
					: false;
		} catch (DataAccessException e) {
			flag = false;
			throw e;
		}
		return flag;
	}
	
	protected T selectByPrimaryKey(Long pk) throws DataAccessException {
		T result = null;
		try {
			result = (T) this.getSqlSession().selectOne(
					getMapperNamespace() + "." + "selectByPrimaryKey", pk);
		} catch (DataAccessException e) {
			throw e;
		}
		return result;
	}

	protected T selectByPrimaryKey(String pk) throws DataAccessException {
				T result = null;
				try {
					result = (T) this.getSqlSession().selectOne(
							getMapperNamespace() + "." + "selectByPrimaryKey", pk);
				} catch (DataAccessException e) {
					throw e;
				}
				return result;
	}

	protected List<T> selectByEntity(T entity) throws DataAccessException {

		return select("selectByEntity", entity);
	}
	
	protected PageInfo selectByEntityAndPage(String listId,Object params,int offset,int limit) throws Exception {
		return selectByPage(listId,params,offset,limit);
	}
	
	protected PageModel selectByEntityAndPage(String listId, Object params, PageModel pageModel) throws Exception {
		PageInfo pageInfo = selectByPage(listId,params,pageModel.getCurrentPage(),pageModel.getPageSize());
		pageModel.setTotalRecord(new Long(pageInfo.getTotal()).intValue());
		if (pageModel.getTotalPage() > 0) {
			pageModel.setList(pageInfo.getList());
		}
		return pageModel;
	}
	
	protected PageModel selectByEntityAndPage(String listId,Map<String,String> params) throws Exception{
		String curPage = StringUtils.defaultIfEmpty((String)params.get(Constants.PAGED_CURPAGE), "1");// 当前页数
		String numPerPage = StringUtils.defaultIfEmpty((String)params.get(Constants.PAGED_NUM_PERPAGE), "10");// 每页条数
		PageModel pageModel = new PageModel(Integer.parseInt(curPage));
		pageModel.setPageSize(Integer.parseInt(numPerPage));
		return selectByEntityAndPage(listId,params,pageModel);
	}
	
	protected PageInfo selectByPage(String listId, Object params, int offset, int limit) throws Exception {
		List<T> result = new ArrayList<T>();
		result = this.getSqlSession().selectList(getMapperNamespace() + "." + listId, params, new RowBounds(offset, limit));
		PageInfo page = new PageInfo(result);
		return page;
	}


	protected boolean delete(String id, T entity) throws DataAccessException {
		boolean flag = false;
		try {
			flag = this.getSqlSession().delete(getMapperNamespace() + "." + id,
					entity) > 0 ? true : false;
		} catch (DataAccessException e) {
			flag = false;
			throw e;
		}
		return flag;
	}

	protected Long getTotalCount(String id, Object params)
			throws DataAccessException {
		return getSqlSession().selectOne(getMapperNamespace() + "." + id,
				params);
	}

	/*public PageModel getPage(String listId, String countId, Object params,
			PageModel pageModel) {

		// 得到数据记录总数
		Integer totalItem = (Integer) getSqlSession().selectOne(
				getMapperNamespace() + "." + countId, params);
		pageModel.setTotalRecord(totalItem);

		if (pageModel.getTotalPage() > 0) {
			pageModel.setList(this.getSqlSession().selectList(
					getMapperNamespace() + "." + listId,
					params,
					new PageBounds(pageModel.getCurrentPage(), pageModel
							.getPageSize())));
		}
		return pageModel;
	}*/
	

	protected boolean update(String id, T entity) throws DataAccessException {
		boolean flag = false;
		try {
			flag = this.getSqlSession().update(getMapperNamespace() + "." + id,
					entity) > 0 ? true : false;
		} catch (DataAccessException e) {
			flag = false;
			throw e;
		}
		return flag;
	}

	protected List<T> select(String id, Object params) throws DataAccessException {
		List<T> result = new ArrayList<T>();
		try {
			result = this.getSqlSession().selectList(
					getMapperNamespace() + "." + id, params);
		} catch (DataAccessException e) {
			throw e;
		}
		return result;
	}
	
	
	protected boolean updateByPrimaryKeySelective(T entity)
			throws DataAccessException {

		return update("updateByPrimaryKeySelective", entity);
	}

	protected Class<T> getEntityClass() {
		return entityClass;
	}

	protected void setEntityClass(Class<T> entityClass) {
		this.entityClass = entityClass;
		setMapperNamespace(entityClass.getName().substring(
				entityClass.getName().lastIndexOf(".") + 1));
	}

	protected String getMapperNamespace() {
		return mapperNamespace;
	}

	protected void setMapperNamespace(String className) {
		this.mapperNamespace = "com.newland.financial.p2p.mapping." + className + "Mapper";
	}

	protected boolean saveOrUpdate(T entity) throws DataAccessException {
		return saveOrUpdate("insertSelective", "updateByPrimaryKeySelective",
				entity);
	}

	protected boolean saveOrUpdate(String insertId, String updateId, T entity)
			throws DataAccessException {
		BeanWrapper beanWrapper = new BeanWrapperImpl(entity);
		Long id = (Long) beanWrapper.getPropertyValue("id");
		if (id == null || id <= 0) { // 添加
			return insert(insertId, entity);
		} else {
			return update(updateId, entity);
		}
	}

	protected boolean insert(String id, T entity) throws DataAccessException {
		return insertObj(id, entity);
	}

	/*@Override
	public PageModel getPage(String listId, String countId,
			Map paramsMap) {

		String curPage = StringUtils.defaultIfEmpty( // 当前页数
				(String)paramsMap.get(Constants.PAGED_CURPAGE), "1");

		String numPerPage = StringUtils.defaultIfEmpty( // 每页条数
				(String)paramsMap.get(Constants.PAGED_NUM_PERPAGE), "10");

		PageModel pageModel = new PageModel(Integer.parseInt(curPage));
		pageModel.setPageSize(Integer.parseInt(numPerPage));

		return getPage(listId, countId, paramsMap, pageModel);

	}*/

	protected boolean insertObj(String id, Object params)
			throws DataAccessException {
		boolean flag = false;
		try {
			flag = this.getSqlSession().insert(getMapperNamespace() + "." + id,
					params) > 0 ? true : false;
		} catch (DataAccessException e) {
			flag = false;
			throw e;
		}
		return flag;
	}

	protected T selectEntity(String id, Object params) throws DataAccessException {
		List<T> list = select(id, params);
		if (list != null && list.size() > 0)
			return (T) list.get(0);
		return null;
	}

	protected List selects(String id, Object params) throws DataAccessException {
		List result = new ArrayList();
		try {
			result = this.getSqlSession().selectList(
					getMapperNamespace() + "." + id, params);
		} catch (DataAccessException e) {
			throw e;
		}
		return result;
	}

	protected boolean deletes(String id, Object object) throws DataAccessException {
		boolean flag = false;
		try {
			flag = this.getSqlSession().delete(getMapperNamespace() + "." + id,
					object) > 0 ? true : false;
		} catch (DataAccessException e) {
			flag = false;
			throw e;
		}
		return flag;
	}

	protected Object getObject(String id, Object object) {
		// TODO Auto-generated method stub
		return this.getSqlSession().selectOne(getMapperNamespace() + "." + id, object);
	}

	public List<T> selectAll(int curPage, int pageNum)
			throws DataAccessException {
		
		List<T> result = new ArrayList<T>();
		PageInfo page = null;
		
		try {
			result = this.getSqlSession().selectList(
					getMapperNamespace() + "." + "selectAll",new RowBounds(curPage, pageNum));
			if(null != result || result.size() > 0){
				
				page = new PageInfo(result);
			}
			
		} catch (DataAccessException e) {
			throw e;
		}
		return  null==result? null : page.getList();
	}

}
