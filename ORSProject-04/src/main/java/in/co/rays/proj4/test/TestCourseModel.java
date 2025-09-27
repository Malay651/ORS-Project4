package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CourseBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CourseModel;

public class TestCourseModel {

public static void main(String[] args) throws Exception {
		
		testadd();
		//testUpdate();
		//testDelete();
		//testfindbypk();
		//testfindbyname();
		//testsearch();
	}
	 
	public static void testadd() throws Exception {
		
		CourseBean bean = new CourseBean();
		
		bean.setName("java");
		bean.setDuration("six months");
		bean.setDescription("admin");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		
		CourseModel Model = new CourseModel();
		Model.Add(bean);
	}
	
	public static void testupdate() throws Exception {
		
		CourseBean bean = new CourseBean();
		
		bean.setName("python");
		bean.setDuration("six months");
		bean.setDescription("admin");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		bean.setId(1);
		
		CourseModel Model = new CourseModel();
		Model.Update(bean);
	}
	
	public static void testdelete() throws Exception {
		
		CourseModel model = new CourseModel();
		CourseBean bean = new CourseBean();
		bean.setId(1);
		model.Delete(bean);
	}
	
	public static void testfindbypk() throws Exception {
		
		try {
			CourseModel model = new CourseModel();
			CourseBean bean = model.findbypk(1);
			
			if(bean == null) {
			System.out.println("test find by pk is fail");
		} 
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDuration());
			System.out.println(bean.getDescription());
		
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testfindbyname() throws Exception {
		
		try {
			CourseModel model = new CourseModel();
			CourseBean bean = model.findbypk(1);
			
			if(bean == null) {
			System.out.println("test find by pk is fail");
		} 
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDuration());
			System.out.println(bean.getDescription());
			
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testsearch() throws Exception {
		
		try {
			CourseModel model = new CourseModel();
			CourseBean bean = new CourseBean();
			List list = new ArrayList();
			
			bean.setName("java");
			list = model.search(bean, 0, 0);
			System.out.println(list);
			if (list.size() == 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CourseBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDuration());
				System.out.println(bean.getDescription());
				
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	}

