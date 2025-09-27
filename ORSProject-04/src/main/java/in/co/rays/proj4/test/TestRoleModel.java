package in.co.rays.proj4.test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import in.co.rays.proj4.bean.RoleBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.model.RoleModel;

public class TestRoleModel {
	   
	public static void main(String[] args) throws Exception {
		
		testAdd();
		//testUpdate();
		//testDelete();
		//testfindByPk();
	   //testFindByName();
		//testSearch();
		
//		testnextPk();
	}		
    private static void testnextPk() throws DatabaseException {
    	
    	RoleModel model = new RoleModel();
		int id = model.nextpk();
		System.out.println("next pk: " + id);

		
	}
	public static void testAdd() throws Exception {
    
    RoleBean bean = new RoleBean();
    
    bean.setName("faculty");
    bean.setDescription("faculty");
	bean.setCreatedBy("faculty");
	bean.setModifiedBy("faculty");
	bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
	bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
	
	 RoleModel model = new RoleModel();
        model.add(bean);
	}
    
    public static void testUpdate() throws Exception {
    	
    	RoleBean bean = new RoleBean();
    	bean.setId(1);
    	bean.setName("Admin");
    	bean.setDescription("admin");
    	bean.setCreatedBy("admin");
    	bean.setModifiedBy("admin");
    	bean.setCreatedDateTime(new Timestamp(new Date().getTime()));
    	bean.setModifiedDateTime(new Timestamp(new Date().getTime()));
    	
    	 RoleModel model = new RoleModel();
            model.update(bean);
    }
    
    public static void testDelete() throws Exception {

        RoleModel model = new RoleModel();
       RoleBean bean = new RoleBean();
         bean.setId(5);
    		model.delete(bean);
    }
    
    public static void testfindByPk() throws Exception {
		try {
			RoleModel model = new RoleModel();
			RoleBean bean = model.findbypk(1L);
			if (bean == null) {
				System.out.println("Test Find By PK fail");
			}
			System.out.println(bean.getId());
			System.out.println(bean.getName());
			System.out.println(bean.getDescription());
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
    public static void testFindByName() throws Exception {
        try {
        	RoleModel model = new RoleModel();
            RoleBean bean = new RoleBean();
            bean = model.findbyname("admin");
            if (bean == null) {
                System.out.println("Test Find By PK fail");
            }
            System.out.println(bean.getId());
            System.out.println(bean.getName());
            System.out.println(bean.getDescription());
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
    }
	public static void testSearch() {
		try {
			RoleModel model = new RoleModel();
			RoleBean bean = new RoleBean();
			List list = new ArrayList();
			
			bean.setName("student");
			list = model.search(bean, 0, 0);
			System.out.println(list);
			if (list.size() == 0) {
				System.out.println("Test Search fail");
			}
			Iterator it = list.iterator();
			while (it.hasNext()) {
				bean = (RoleBean) it.next();
				System.out.println(bean.getId());
				System.out.println(bean.getName());
				System.out.println(bean.getDescription());
			}
		} catch (ApplicationException e) {
			e.printStackTrace();
		}
	}
}

