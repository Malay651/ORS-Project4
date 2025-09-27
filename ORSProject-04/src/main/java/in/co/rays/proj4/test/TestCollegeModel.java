package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.CollegeModel;

public class TestCollegeModel {

	public static void main(String[] args) throws Exception {
		
		//testAdd();
		//testUpdate();
		//testDelete();
		//testfindbypk();
		//testfindbyname();
		testsearch();
	}
	 
	public static void testAdd() throws Exception {
		
		CollegeBean bean = new CollegeBean();
		
		bean.setName("svce");
		bean.setAddress("jaora compound");
		bean.setCity("indore");
		bean.setState("mp");
		bean.setPhoneNo("94576452");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		
		CollegeModel Model = new CollegeModel();
		Model.add(bean);
	}
	
	public static void testupdate() throws Exception {
		
		CollegeBean bean = new CollegeBean();
		
		bean.setName("svce");
		bean.setAddress("jaora compound");
		bean.setCity("indore");
		bean.setState("mp");
		bean.setPhoneNo("9457645233");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		bean.setId(1);
		
		CollegeModel Model = new CollegeModel();
		Model.Update(bean);
	}
	
	public static void testdelete() throws Exception {
		
		CollegeModel model = new CollegeModel();
		CollegeBean bean = new CollegeBean();
		bean.setId(5);
		model.Delete(bean);
	}
	
	public static void testfindbypk() throws Exception {
		
		try {
			CollegeModel model = new CollegeModel();
			CollegeBean bean = model.findbypk(1);
			
			if(bean == null) {
			System.out.println("test find by pk is fail");
		} 
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getCity());
			System.out.println(bean.getState());
			System.out.println(bean.getPhoneNo());
			
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testfindbyname() throws Exception {
		
		try {
			CollegeModel model = new CollegeModel();
			CollegeBean bean = model.findbypk(1);
			
			if(bean == null) {
			System.out.println("test find by pk is fail");
		} 
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getAddress());
			System.out.println(bean.getCity());
			System.out.println(bean.getState());
			System.out.println(bean.getPhoneNo());
			
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testsearch() throws Exception {
		
		try {
			CollegeModel model = new CollegeModel();
			CollegeBean bean = new CollegeBean();
			List list = new ArrayList();
			
			bean.setName("svce");
			list = model.search(bean, 0, 0);
			System.out.println(list);
			if (list.size() == 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (CollegeBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getAddress());
				System.out.println(bean.getCity());
				System.out.println(bean.getState());
				System.out.println(bean.getPhoneNo());
				
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
	}

