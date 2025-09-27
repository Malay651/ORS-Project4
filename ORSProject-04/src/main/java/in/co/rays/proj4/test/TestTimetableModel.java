package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.TimetableBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.TimetableModel;

public class TestTimetableModel {
    public static void main(String[] args) throws Exception {
		
    	testAdd();
    	//testUpdate();
    	//testDelete();
    	//testfindByPk();
    	//testSearch();
	}
    
    public static void testAdd() throws Exception {
        
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
        TimetableBean bean = new TimetableBean();
        
        bean.setSemester("4th");
        bean.setDescription("admin");
        bean.setExamDate(sdf.parse("2025-05-26"));
        bean.setExamTime("two hours");
        bean.setCourseId(1);
        bean.setSubjectId(1);
    	bean.setCreatedBy("admin");
    	bean.setModifiedBy("admin");
    	bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
    	bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
    	
    	 TimetableModel model = new TimetableModel();
            model.add(bean);
    	}
        
        public static void testUpdate() throws Exception {
        	
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
            TimetableBean bean = new TimetableBean();
            
            bean.setSemester("malay");
            bean.setDescription("admin");
            bean.setExamDate(sdf.parse("2025-05-26"));
            bean.setExamTime("two hours");
            bean.setCourseId(1);
            bean.setSubjectId(1);
        	bean.setCreatedBy("admin");
        	bean.setModifiedBy("admin");
        	bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
        	bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
        	
        	 TimetableModel model = new TimetableModel();
            
            model.update(bean);
        }
        
        public static void testDelete() throws Exception {

        	 TimetableModel model = new  TimetableModel();

        	 TimetableBean bean = new TimetableBean();
        	 bean.setId(1);
        		model.delete(bean);
        }
        
        public static void testfindByPk() throws Exception {
    		try {

           	 TimetableModel model = new  TimetableModel();


    			TimetableBean bean = model.findByPk(1L);
    			if (bean == null) {
    				System.out.println("Test Find By PK fail");
    			}
    			System.out.println(bean.getId());
    			System.out.println(bean.getSemester());
    			System.out.println(bean.getDescription());
    			System.out.println(bean.getExamDate());
    			System.out.println(bean.getExamTime());
    			System.out.println(bean.getCourseId());
    			System.out.println(bean.getSubjectId());
    		} catch (ApplicationException e) {
    			e.printStackTrace();
    		}
    	}
        public static void testFindByName() throws Exception {
            try {

           	 TimetableModel model = new  TimetableModel();


                TimetableBean bean = new TimetableBean();
                bean = model.findByPk(1);
                if (bean == null) {
                    System.out.println("Test Find By name fail");
                }
               
                System.out.println(bean.getId());
    			System.out.println(bean.getSemester());
    			System.out.println(bean.getDescription());
    			System.out.println(bean.getExamDate());
    			System.out.println(bean.getExamTime());
    			System.out.println(bean.getCourseId());
    			System.out.println(bean.getSubjectId());
    	
            } catch (ApplicationException e) {
                e.printStackTrace();
            }
        }
    	public static void testSearch() {
    		try {

           	 TimetableModel model = new  TimetableModel();


    			TimetableBean bean = new TimetableBean();
    			List list = new ArrayList();
    			
    			bean.setId(1);
    			list = model.search(bean, 0, 0);
    			System.out.println(list);
    			if (list.size() == 0) {
    				System.out.println("Test Search fail");
    			}
    			Iterator it = list.iterator();
    			while (it.hasNext()) {
    				bean = (TimetableBean) it.next();
    				System.out.println(bean.getId());
        			System.out.println(bean.getSemester());
        			System.out.println(bean.getDescription());
        			System.out.println(bean.getExamDate());
        			System.out.println(bean.getExamTime());
        			System.out.println(bean.getCourseId());
        			System.out.println(bean.getSubjectId());
        	
    			}
    		} catch (ApplicationException e) {
    			e.printStackTrace();
    		}
    	}
    }

