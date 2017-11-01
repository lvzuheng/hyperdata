package com.lzh.hyperdata.cache.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Transactional
@SuppressWarnings("unchecked")
@Service
public class CacheServiceImpl  extends CacheService {
	@Autowired
	private static CacheServiceImpl cacheDataImpl;

	public static CacheServiceImpl getInstance(){
		if(cacheDataImpl==null)
			cacheDataImpl = new CacheServiceImpl();
		return cacheDataImpl;
	}
	public void setInstance(CacheServiceImpl cacheDataImpl){
		this.cacheDataImpl = cacheDataImpl;
	}

	//插入键值对String-string
	public void save(String key,String field) {
		getRedisTemplate().opsForValue().append(key, field);
	}
	//插入键值对string-map<string,string>
	public void save(String key,Map<String, String> map) {
		getRedisTemplate().opsForHash().putAll(key, map);

	}

	public void save(String key,String field,String value) {
		getRedisTemplate().opsForHash().put(key, field,value);
	}


	//插入键值对string-List
	public void saveList(String key,String... field) {
		getRedisTemplate().opsForList().leftPushAll(key, field);
	}
	public String get(String key){
		return (String) getRedisTemplate().opsForValue().get(key);
	}
	public List<String> getList(String key,long start,long end){
		return getRedisTemplate().opsForList().range(key, start, end);
	}
	//通过key拿到map的key
	public Set<String> getField(String key){
		return getRedisTemplate().opsForHash().keys(key);
	}
	//通过value拿到map的key,最好别用，太慢。。。
	public List<String> getField(String key,String value){
		List<String> list = new ArrayList<String>();
		Set<String> set =  getRedisTemplate().opsForHash().keys(key);
		Iterator<String> sIterator = set.iterator();
		String setkey;
		while (sIterator.hasNext()) {
			if(getRedisTemplate().opsForHash().get(key,setkey = sIterator.next()).equals(value)){
				list.add(setkey);
			}
		}
		return list;
	}

	public List<String> getMapAllValue(String key){
		return getRedisTemplate().opsForHash().values(key);
	}
	public String getValue(String key,String field) {
		return (String) getRedisTemplate().opsForHash().get(key, field);

	}

	public void del(String key) {
		getRedisTemplate().delete(key);
	}
	public void del(String key,String... field) {
		getRedisTemplate().opsForHash().delete(key, field);
	}

	//删除List
	public void delList(String key,String value){
		getRedisTemplate().opsForList().remove(key, 0, value);
	}

	public boolean isExist(String key,String field) {
		if(getRedisTemplate().hasKey(key))
			return getRedisTemplate().opsForHash().hasKey(key, field);
		return false;
	}
	public boolean isExist(String key) {
		return getRedisTemplate().hasKey(key);
	}
	public boolean flush(){
//		System.out.println(getRedisTemplate().keys(""));
		Iterator<String> iterator = getRedisTemplate().keys("*").iterator();
		while (iterator.hasNext()) {
			getRedisTemplate().delete(iterator.next());
		}
		return false;
	}

}
