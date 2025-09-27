package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.RoleModel;
import in.co.rays.proj4.model.UserModel;

public class TestUserModel {
	
	public static void main(String[] args) throws Exception {
		
	    testAdd();
	// testUpdate();
    // testDelete();
	 //testfindbypk();
	 //testfindbylogin();
	// testSearch();
}

	public static void testAdd() throws Exception {
		UserBean bean = new UserBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		bean.setFirstName("akshat");
		bean.setLastName("saxena");
		bean.setLogin("akshat123@gmail.com");
		bean.setPassword("Akshat@123");
		bean.setDob(sdf.parse("20-05-2025"));
		bean.setMobileNo("987653214");
		bean.setRoleId(2);
		bean.setGender("male");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		
		UserModel model = new UserModel();
		model.add(bean);
	}
	
	public static void testUpdate() throws Exception {
		
		UserBean bean = new UserBean();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		bean.setId(1);
		bean.setFirstName("ashok");
		bean.setLastName("dongre");
		bean.setLogin("malay@123");
		bean.setPassword("123");
		bean.setDob(sdf.parse("20-05-2025"));
		bean.setMobileNo("987653214");
		bean.setRoleId(1);
		bean.setGender("male");
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		
		UserModel model = new UserModel();
		model.update(bean);

	}
	
	 public static void testDelete() throws Exception {

	        RoleModel model = new RoleModel();
           
	    		//model.delete(1);
	    }

	 public static void testfindbypk() throws Exception {
		try { 
		 UserModel model = new UserModel();
		 UserBean bean = model.findbypk(1);
		 
		 if(bean == null) {
			 System.out.println("test find by pk fail");
		   }
		 System.out.println(bean.getId());
		 System.out.println(bean.getFirstName());
		 System.out.println(bean.getLastName());
		 System.out.println(bean.getLogin());
		 System.out.println(bean.getPassword());
		 System.out.println(bean.getDob());
		 System.out.println(bean.getMobileNo());
		 System.out.println(bean.getRoleId());
		 System.out.println(bean.getGender());
		 
		} catch (Exception e) {
			e.printStackTrace();
	}
}
	
	 public static void testfindbylogin() throws Exception  {
	        try {
	        	 UserModel model = new UserModel();
	            UserBean bean = new UserBean();
	            bean = model.findbylogin("malay@123");
	            if (bean == null) {
	                System.out.println("Test Find By name fail");
	            }
	         System.out.println(bean.getId());
	   		 System.out.println(bean.getFirstName());
	   		 System.out.println(bean.getLastName());
	   		 System.out.println(bean.getLogin());
	   		 System.out.println(bean.getPassword());
	   		 System.out.println(bean.getDob());
	   		 System.out.println(bean.getMobileNo());
	   		 System.out.println(bean.getRoleId());
	   		 System.out.println(bean.getGender());
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	 
	 public static void testSearch() throws Exception  {
			try {
				UserModel model = new UserModel();
				UserBean bean = new UserBean();
				List list = new ArrayList();
				
    			bean.setFirstName("student");
				list = model.search(bean, 1, 10);
		        list=model.list();
				System.out.println(list);
				if (list.size() == 0) {
					System.out.println("Test Search fail");
				}
				Iterator it = list.iterator();
				while (it.hasNext()) {
					bean = (UserBean) it.next();
					 System.out.print(bean.getId());
			   		 System.out.print("\t " +bean.getFirstName());
			   		 System.out.print("\t "+bean.getLastName());
			   		 System.out.print("\t "+bean.getLogin());
			   		 System.out.print("\t "+bean.getPassword());
			   		 System.out.print("\t "+bean.getDob());
			   		 System.out.print("\t "+bean.getMobileNo());
			   		 System.out.print("\t "+bean.getRoleId());
			   		 System.out.println("\t "+bean.getGender());
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
	 
	 }	 
	 
	 
	 
}