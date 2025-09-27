package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.MarksheetBean;
import in.co.rays.proj4.model.MarksheetModel;



public class TestMarksheetModel {
	public static void main(String[] args) throws Exception {
		
	
	testadd();
	//testupdate();
	//testdelete();
    //testfindbypk();
	//testfindbyemail();
	//testsearch();

}

 public static void testadd() throws Exception {
	 
		MarksheetBean bean = new MarksheetBean();
		
		
		bean.setRollNo("0822cs121054");
		bean.setStudentId(1);
		bean.setChemistry(56);
		bean.setPhysics(75);
		bean.setMaths(85);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
		bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		
		MarksheetModel Model = new MarksheetModel();
		Model.add(bean);
	}
	
	public static void testupdate() throws Exception {
		
		MarksheetBean bean = new MarksheetBean();
		
		bean.setRollNo("0822cs121054");
		bean.setStudentId(1);
		bean.setChemistry(56);
		bean.setPhysics(75);
		bean.setMaths(85);
		bean.setCreatedBy("admin");
		bean.setModifiedBy("admin");
	    bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
		bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
		bean.setId(1);
		MarksheetModel Model = new MarksheetModel();
		Model.update(bean);
	}
	
	public static void testdelete() throws Exception {
		
		MarksheetModel Model = new MarksheetModel();
		
		MarksheetBean bean = new MarksheetBean();
		bean .setId(1);
		Model.delete(bean);
	}
	
	public static void testfindbypk() throws Exception {
		
		try {
			MarksheetModel Model = new MarksheetModel();
			MarksheetBean bean = Model.findByPk(1);
			
			if(bean == null) {
			System.out.println("test find by pk is fail");
		} 
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getStudentId());
			System.out.println(bean.getName());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getMaths());
	        
		
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testfindbyrollno() throws Exception {
		
		try {
			MarksheetModel Model = new MarksheetModel();
			MarksheetBean bean = Model.findByPk(1);
			
			if(bean == null) {
			System.out.println("test find by rollno is fail");
		} 
			System.out.println(bean.getId());
			System.out.println(bean.getRollNo());
			System.out.println(bean.getStudentId());
			System.out.println(bean.getName());
			System.out.println(bean.getChemistry());
			System.out.println(bean.getPhysics());
			System.out.println(bean.getMaths());
	        
	
		}	catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void testsearch() throws Exception {
		
		try {
			MarksheetModel Model = new MarksheetModel();
			MarksheetBean bean = new MarksheetBean();
			List list = new ArrayList();
			
			bean.setId(1);
			list = Model.search(bean, 0, 0);
			System.out.println(list);
			if (list.size() == 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (MarksheetBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getRollNo());
				System.out.println(bean.getStudentId());
				System.out.println(bean.getName());
				System.out.println(bean.getChemistry());
				System.out.println(bean.getPhysics());
				System.out.println(bean.getMaths());
		        
		
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}



 