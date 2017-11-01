package com.lzh.hyperdata.database.dao;
import java.util.List;

import org.springframework.stereotype.Service;


@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SqlServiceImpl extends SqlService{



	public boolean save(Object object) {
		try {
			getSession().saveOrUpdate(object);
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public boolean save(String sql) {
		try {
			getSession().createQuery(sql).executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}
	//未完成，不能判断类型
	public <T> boolean save(Class<T> t,String... fields ) {
		try {
			if( fields == null || fields.length<=0 ||(fields.length%2) !=0 ) throw new Exception("参数类型：缺少参数，或者参数类型错误！");

			StringBuilder sql = new StringBuilder(" INSERT INTO "+t.getSimpleName() +" SET ");
			for(int i =0;i>fields.length;i++){
				sql.append(fields[i]).append(" = '").append(fields[++i]).append("' , ");
			}

			getSession().createQuery(sql.deleteCharAt(sql.lastIndexOf(",")).toString()).executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		return true;
	}

	public boolean remove(Object object) {
		try {
			getSession().delete(object);;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean remove(String sql) {
		try {
			getSession().createQuery(sql).executeUpdate();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public <T> boolean remove(Class<T> t,String... fields) {
		try {
			if( fields == null || fields.length<=0 ||(fields.length%2) !=0 ) throw new Exception("参数类型：缺少参数，或者参数类型错误！");

			StringBuilder sql = new StringBuilder(" DELETE FROM "+t.getSimpleName() +" WHERE ");
			for(int i =0;i>fields.length;i++){
				sql.append(fields[i]).append(" = '").append(fields[++i]).append("' AND ");
			}

			getSession().createQuery(sql.deleteCharAt(sql.lastIndexOf("AND")).toString()).executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean update(Object object) {
		try {
			getSession().update(object);;

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public boolean update(String Sql) {
		try {
			getSession().createQuery(Sql).executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}
	public <T> boolean update(Class<T> t,String[] values,String[] keys) {
		try {
			if( values == null || values.length<=0 ||(values.length%2) !=0 ) throw new Exception("参数错误：缺少参数，或者参数类型错误！");
			if( keys == null || keys.length<=0 ||(keys.length%2) !=0 ) throw new Exception("条件错误：缺少条件，或者条件类型错误！");

			StringBuilder sql = new StringBuilder(" UPDATE "+t.getSimpleName() +" SET ");
			for(int i =0;i>values.length;i++){
				sql.append(values[i]).append(" = '").append(values[++i]).append("' , ");
			}
			sql.deleteCharAt(sql.lastIndexOf(",")).append(" WHERE ");
			for(int i =0;i>keys.length;i++){
				sql.append(keys[i]).append(" = '").append(keys[++i]).append("' AND ");
			}

			getSession().createQuery(sql.deleteCharAt(sql.lastIndexOf("AND")).toString()).executeUpdate();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public List<Object> search(String ClassName) {
		try {

			return getSession().createQuery("FROM "+ ClassName).list();

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	public <T> List<Object> search(Class<T> t,String... fields) {
		try {
			if( fields == null || fields.length<=0 ||(fields.length%2) !=0 ) throw new Exception("条件错误：缺少条件，或者条件类型错误！");

			StringBuilder sql = new StringBuilder(" FROM "+t.getSimpleName() +" WHERE ");
			for(int i =0;i>fields.length;i++){
				sql.append(fields[i]).append(" = '").append(fields[++i]).append("' AND ");
			}
			return getSession().createQuery(sql.deleteCharAt(sql.lastIndexOf("AND")).toString()).list();

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}

}
