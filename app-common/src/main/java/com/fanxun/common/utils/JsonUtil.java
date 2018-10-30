package com.fanxun.common.utils;

import java.io.Serializable;
import java.util.*;

import com.fanxun.common.pojo.FanXunResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * 处理json字符串与java对象之间的转换
 * @Author liu
 * @Date 2018-10-07 18:18
 */

public class JsonUtil {

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();
    /**
     * 将对象转换成json字符串。
     * @param data
     * @return
     */
    public static String objectToJson(Object data) {
    	try {
			String string = MAPPER.writeValueAsString(data);
			return string;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
    	return null;
    }
    
    /**
     * 将json结果集转化为对象
     * 
     * @param jsonData json数据
     * @param clazz 对象中的object类型
     * @return
     */
    public static <T> T jsonToPojo(String jsonData, Class<T> beanType) {
        try {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 将json数据转换成pojo对象list
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T>List<T> jsonToList(String jsonData, Class<T> beanType) {
    	JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
    	try {
    		List<T> list = MAPPER.readValue(jsonData, javaType);
    		return list;
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	return null;
    }

    public static void main(String[] args) throws Exception {
        Student student = new Student();
        student.setFlag(true);
        student.setId(1);
        student.setUsername("chen");
        student.setBirthday(new Date());
        Map<String,Integer> map = new HashMap<>();
        map.put("liu",22);
        map.put("chen",21);
        List<Object> list = new ArrayList<>();
        list.add(student);
        list.add(map);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("aaaa");
        arrayList.add("bbbb");
        list.add(arrayList);
        String json2 = JsonUtil.objectToJson(list);
        System.out.println(json2);
//        List<Object> list1 = JsonUtil.jsonToList(json2,Object.class);
//        for (Object object:list1){
//            System.out.println(object);
//
//        }


//        FanXunResult fanXunResult = new FanXunResult();
//        fanXunResult.setData("xxxxxxxxxx");
//        fanXunResult.setMsg("OK");
//        fanXunResult.setStatus(1000);
//        String json = JsonUtil.objectToJson(fanXunResult);
//        System.out.println(json);
//
//        FanXunResult fanXunResult1 = JsonUtil.jsonToPojo(json,FanXunResult.class);
//        System.out.println(fanXunResult1);

    }

}
class Student implements Serializable {
    private Integer id;
    private String username;
    private boolean flag;
    private Date birthday;

    public Student(){}

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isFlag() {
        return flag;
    }

    public Date getBirthday() {
        return birthday;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", flag=" + flag +
                ", birthday=" + birthday +
                '}';
    }
}