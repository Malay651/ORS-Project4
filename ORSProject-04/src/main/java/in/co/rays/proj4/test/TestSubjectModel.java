package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.SubjectBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.SubjectModel;

public class TestSubjectModel {

	public static void main(String[] args) throws Exception {
		
		//testadd();
		testupdate();
		//testdelete();
		//testfindbypk();
		//testfindbyname();
		//testsearch();
	}
		public static void testadd() throws Exception {
			
			SubjectBean bean = new SubjectBean();
			
			bean.setName("MATHS");
			bean.setCourseId(1);
			bean.setDescription("admin");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
			
			SubjectModel Model = new SubjectModel();
			Model.add(bean);
		}
		
		public static void testupdate() throws Exception {
			
			SubjectBean bean = new SubjectBean();
			
			bean.setName("JDBC");
			bean.setCourseId(1);
			bean.setDescription("admin");
			bean.setCreatedBy("admin");
			bean.setModifiedBy("admin");
			bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
			bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
			bean.setId(1);
			
			SubjectModel Model = new SubjectModel();
			Model.update(bean);
		}
		
		public static void testdelete() throws Exception {
			
			SubjectModel model = new SubjectModel();
			
			SubjectBean bean = new SubjectBean();
			bean.setId(1);
			model.delete(bean);
		}
		
		public static void testfindbypk() throws Exception {
			
			try {
				SubjectModel model = new SubjectModel();
				SubjectBean bean = model.findByPk(1);
				
				if(bean == null) {
				System.out.println("test find by pk is fail");
			} 
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getDescription());
				
			}	catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void testfindbyname() throws Exception {
			
			try {
				SubjectModel model = new SubjectModel();
				SubjectBean bean = model.findByPk(1);
				
				if(bean == null) {
				System.out.println("test find by pk is fail");
			} 
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getCourseId());
				System.out.println(bean.getCourseName());
				System.out.println(bean.getDescription());
			}	catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public static void testsearch() throws Exception {
			
			try {
				SubjectModel model = new SubjectModel();
				SubjectBean bean = new SubjectBean();
				List list = new ArrayList();
				
				bean.setName("svce");
				list = model.search(bean, 0, 0);
				System.out.println(list);
				if (list.size() == 0) {
					System.out.println("Test Search fail");
				}
				Iterator it = list.iterator();
				while (it.hasNext()) {
					bean = (SubjectBean) it.next();
					System.out.println(bean.getId());
					System.out.println(bean.getName());
					System.out.println(bean.getCourseId());
					System.out.println(bean.getCourseName());
					System.out.println(bean.getDescription());
				}
			} catch (ApplicationException e) {
				e.printStackTrace();
			}
		}


	}

