package in.co.rays.proj4.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.ItemInformationBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.model.ItemInformationModel;
import in.co.rays.proj4.util.DataUtility;
import in.co.rays.proj4.util.DataValidator;
import in.co.rays.proj4.util.PropertyReader;
import in.co.rays.proj4.util.ServletUtility;

@WebServlet(name = "ItemInformationCtl", urlPatterns = { "/ctl/ItemInformationCtl" })
public class ItemInformationCtl extends BaseCtl {

	private static Logger log = Logger.getLogger(ItemInformationCtl.class);

	@Override
	protected boolean validate(HttpServletRequest request) {

		log.debug("ItemInformationCtl Method validate Started");

		Boolean pass = true;

		if (DataValidator.isNull(request.getParameter("title"))) {
			request.setAttribute("title", PropertyReader.getValue("error.require", "title"));
			pass = false;
		} else if (!DataValidator.isName(request.getParameter("title"))) {
			request.setAttribute("title", "Invalid title");
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("overview"))) {
			request.setAttribute("overview", PropertyReader.getValue("error.require", "overview"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("cost"))) {
			request.setAttribute("cost", PropertyReader.getValue("error.require", "cost"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("purchasedate"))) {
			request.setAttribute("purchasedate", PropertyReader.getValue("error.require", "purchasedate"));
			pass = false;
		}
		if (DataValidator.isNull(request.getParameter("category"))) {
			request.setAttribute("category", PropertyReader.getValue("error.require", "category"));
			pass = false;
		}

		log.debug("ItemInformationCtl Method validate Ended");

		return pass;
	}

	@Override
	protected BaseBean populateBean(HttpServletRequest request) {

		log.debug("Ctl Method populate Started");

		ItemInformationBean bean = new ItemInformationBean();

		bean.setId(DataUtility.getLong(request.getParameter("id")));
		bean.setTitle(DataUtility.getString(request.getParameter("title")));
		bean.setOverview(DataUtility.getString(request.getParameter("overview")));
		bean.setCost(DataUtility.getdouble(request.getParameter("cost")));
		bean.setPurchasedate(DataUtility.getDate(request.getParameter("purchasedate")));
		bean.setCategory(DataUtility.getString(request.getParameter("category")));

		populateDTO(bean, request);

		log.debug("InformationItemCtl Method populate Ended");

		return bean;
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		long id = DataUtility.getLong(request.getParameter("id"));

		ItemInformationModel model = new ItemInformationModel();

		if (id > 0) {
			try {
				ItemInformationBean bean = model.findbypk(id);

				ServletUtility.setBean(bean, request);

			} catch (Exception e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			}

		}
		ServletUtility.forward(getView(), request, response);

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		log.debug("ItemInformationCtl Method doPost Started");

		String op = DataUtility.getString(request.getParameter("operation"));

		ItemInformationModel model = new ItemInformationModel();

		long id = DataUtility.getLong(request.getParameter("id"));

		if (OP_SAVE.equalsIgnoreCase(op)) {
			ItemInformationBean bean = (ItemInformationBean) populateBean(request);

			try {
				model.add(bean);
				ServletUtility.setBean(bean, request);
				ServletUtility.setSuccessMessage("Data is Successfully saved", request);
			} catch (DuplicateRecordException e) {
				ServletUtility.setBean(bean, request);
				ServletUtility.setErrorMessage("Item information is already exists", request);
			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (OP_UPDATE.equalsIgnoreCase(op)) {
			ItemInformationBean bean = (ItemInformationBean) populateBean(request);

			try {
				if (id > 0) {
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
			} catch (Exception e) {
				e.printStackTrace();
				return;
			}
		} else if (OP_CANCEL.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ITEM_INFORMATION_CTL, request, response);
			return;
		} else if (OP_RESET.equalsIgnoreCase(op)) {
			ServletUtility.redirect(ORSView.ITEM_INFORMATION_CTL, request, response);
			return;
		}
		ServletUtility.forward(getView(), request, response);

		log.debug("ItemInformationCtl Method doPost Started");
	}

	@Override
	protected String getView() {
		return ORSView.ITEM_INFORMATION_VIEW;
	}

}
