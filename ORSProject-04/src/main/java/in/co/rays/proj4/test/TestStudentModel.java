package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.StudentBean;
import in.co.rays.proj4.model.StudentModel;


public class TestStudentModel {
	
	public static void main(String[] args) throws Exception {
		
	
	testadd();
   // testupdate();
	//testdelete();
    //testfindbypk();
	//testfindbyemail();
	// testsearch();
}

   public static void testadd() throws Exception {
	   
     SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StudentBean bean = new StudentBean();
		
		bean.setFirstName("rahul");
		bean.setLastName("singh");
		bean.setDob(sdf.parse("15-05-2025"));
		bean.setMobileNo("9425354715");
		bean.setGender("male");
		bean.setEmail("rahul@12");
		bean.setCollegeId(1);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		
		StudentModel Model = new StudentModel();
		Model.add(bean);
	}
	
	public static void testupdate() throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		StudentBean bean = new StudentBean();
		
		bean.setFirstName("rahul");
		bean.setLastName("singh");
		bean.setDob(sdf.parse("15-05-2025"));
		bean.setMobileNo("9425354715");	
		bean.setEmail("rahul@12");
		bean.setGender("male");
		bean.setCollegeId(1);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
	    bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		bean.setId(1);
		
		StudentModel Model = new StudentModel();
		Model.update(bean);
	}
	
	public static void testdelete() throws Exception {
		
		StudentModel model = new StudentModel();
		StudentBean bean = new StudentBean();
		bean.setId(1);
		model.delete(bean);
	}
	
	public static void testfindbypk() throws Exception {
		
		try {
			StudentModel model = new StudentModel();
			StudentBean bean = model.findByPk(1);
			
			if(bean == null) {
			System.out.println("test find by pk is fail");
		} 
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
	        
		
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testfindbyemail() throws Exception {
		
		try {
			StudentModel model = new StudentModel();
			StudentBean bean = model.findByPk(1);
			
			if(bean == null) {
			System.out.println("test find by pk is fail");
		} 
			System.out.println(bean.getId());
			System.out.println(bean.getFirstName());
			System.out.println(bean.getLastName());
			System.out.println(bean.getDob());
			System.out.println(bean.getMobileNo());
			System.out.println(bean.getEmail());
	
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testsearch() throws Exception {
		
		try {
			StudentModel model = new StudentModel();
			StudentBean bean = new StudentBean();
			List list = new ArrayList();
			
			bean.setFirstName("rahul");
			list = model.search(bean, 0, 0);
			System.out.println(list);
			if (list.size() == 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (StudentBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getFirstName());
				System.out.println(bean.getLastName());
				System.out.println(bean.getDob());
				System.out.println(bean.getMobileNo());
				System.out.println(bean.getEmail());
		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}


