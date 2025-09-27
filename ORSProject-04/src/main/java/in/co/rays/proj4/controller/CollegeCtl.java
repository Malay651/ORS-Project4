package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.CollegeModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet (name = "CollegeCtl" , urlPatterns = {"/ctl/CollegeCtl"})
public class CollegeCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(CollegeCtl.class);
	
	@Override
	protected boolean validate(HttpServletRequest request) {
			
		log.debug("CollegeCtl Method validate Started");
		
		Boolean pass = true;
		
		if(DataValidator.isNull(request.getParameter("name"))) {
			request.setAttribute("name",PropertyReader.getValue("error.require","name"));
			pass = false;
		} else if(!DataValidator.isName(request.getParameter("name"))) {
			request.setAttribute("name","Invalid Name");
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("address"))) {
			request.setAttribute("address",PropertyReader.getValue("error.require","address"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("state"))) {
			request.setAttribute("state",PropertyReader.getValue("error.require","state"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("city"))) {
			request.setAttribute("state",PropertyReader.getValue("error.require","city"));
			pass = false;
		}
		if(DataValidator.isNull(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo",PropertyReader.getValue("error.require","phoneNo"));
			pass = false;
		} else if(!DataValidator.isPhoneLength(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo","Phone No must have 10 digits");
			pass = false;
		} else if(!DataValidator.isPhoneNo(request.getParameter("phoneNo"))) {
			request.setAttribute("phoneNo","Invalid Phone No");
			pass = false;
		}
		
		log.debug("CollegeCtl Method validate Ended");
		
		return pass;
	}
	@Override
	protected BaseBean populateBean(HttpServletRequest request) {
		
		log.debug("CollegeCtl Method populate Started");
		
		CollegeBean bean = new CollegeBean();
		
		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setName(DataUtility.getString(request.getParameter("name")));
		bean.setAddress(DataUtility.getString(request.getParameter("address")));
		bean.setState(DataUtility.getString(request.getParameter("state")));
		bean.setCity(DataUtility.getString(request.getParameter("city")));
		bean.setPhoneNo(DataUtility.getString(request.getParameter("phoneNo")));
		
	    populateDTO (bean, request);
	    
	    log.debug("CollegeCtl Method populate Ended");
	    
	    return bean;
	}
	@Override
	protected void doGet(HttpServletRequest request , HttpServletResponse response) throws ServletException, IOException {
	
		
		long id = DataUtility.getLong(request.getParameter("id"));
		
		CollegeModel model = new CollegeModel();
		
		if(id > 0) {
			try {
				CollegeBean bean = model.findbypk(id);

				ServletUtility.setBean(bean, request);
				
			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			}
			
		}
		ServletUtility.forward(getView(), request, response);
		
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		log.debug("CollegeCtl Method doPost Started");
		
		String op = DataUtility.getString(request.getParameter("operation"));
		
		CollegeModel model = new CollegeModel();
		
		long id = DataUtility.getLong(request.getParameter("id"));
		
		if(OP_SAVE.equalsIgnoreCase(op)) {
			CollegeBean bean = (CollegeBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is Successfully saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("College Name alredy exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			CollegeBean bean = (CollegeBean) populateBean(request);
			
			try {
			    if(id > 0) {
			    	model.Update(bean);
			    }
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is Successfully saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("College Name alredy exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			}catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			  ServletUtility.redirect(ORSView.COLLEGE_LIST_CTL, request, response);
			  return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			  ServletUtility.redirect(ORSView.COLLEGE_CTL, request, response);
			  return;
		}
		ServletUtility.forward(getView(), request, response);
		
		log.debug("CollegeCtl Method doPost Started");
	}
	@Override
	protected String getView() {
		return ORSView.COLLEGE_VIEW;
	}

}
