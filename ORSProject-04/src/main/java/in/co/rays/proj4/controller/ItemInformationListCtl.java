

	package in.co.rays.proj4.controller;

	import java.io.IOException;
	import java.util.List;

	import javax.servlet.ServletException;
	import javax.servlet.annotation.WebServlet;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;

	import in.co.rays.proj4.bean.BaseBean;
import in.co.rays.proj4.bean.ItemInformationBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.model.ItemInformationModel;
import in.co.rays.proj4.model.RoleModel;
	import in.co.rays.proj4.model.UserModel;
	import in.co.rays.proj4.util.DataUtility;
	import in.co.rays.proj4.util.PropertyReader;
	import in.co.rays.proj4.util.ServletUtility;

	@WebServlet(name = "ItemInformationListCtl", urlPatterns = { "/ctl/ItemInformationListCtl" })
	public class ItemInformationListCtl extends BaseCtl {


		@Override
		protected BaseBean populateBean(HttpServletRequest request) {

			ItemInformationBean bean = new ItemInformationBean();

			bean.setTitle(DataUtility.getString(request.getParameter("title")));
			bean.setPurchasedate(DataUtility.getDate(request.getParameter("purchasedate")));
			bean.setCategory(DataUtility.getString(request.getParameter("category")));

			return bean;

		}

		protected void doGet(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			int pageNo = 1;
			int pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));

			ItemInformationBean bean = (ItemInformationBean) populateBean(request);
			ItemInformationModel model = new ItemInformationModel();

			try {
				List<ItemInformationBean> list = model.search(bean, pageNo, pageSize);
				List<ItemInformationBean> next = model.search(bean, pageNo + 1, pageSize);

				if (list == null || list.isEmpty()) {
					ServletUtility.setErrorMessage("No record found", request);
				}

				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				ServletUtility.setBean(bean, request);
				request.setAttribute("nextListSize", next.size());

				ServletUtility.forward(getView(), request, response);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {

			List list = null;
			List next = null;

			int pageNo = DataUtility.getInt(request.getParameter("pageNo"));
			int pageSize = DataUtility.getInt(request.getParameter("pageSize"));

			pageNo = (pageNo == 0) ? 1 : pageNo;
			pageSize = (pageSize == 0) ? DataUtility.getInt(PropertyReader.getValue("page.size")) : pageSize;

			ItemInformationBean bean = (ItemInformationBean) populateBean(request);
			ItemInformationModel model = new ItemInformationModel();

			String op = DataUtility.getString(request.getParameter("operation"));
			String[] ids = request.getParameterValues("ids");

			try {

				if (OP_SEARCH.equalsIgnoreCase(op) || "Next".equalsIgnoreCase(op) || "Previous".equalsIgnoreCase(op)) {

					if (OP_SEARCH.equalsIgnoreCase(op)) {
						pageNo = 1;
					} else if (OP_NEXT.equalsIgnoreCase(op)) {
						pageNo++;
					} else if (OP_PREVIOUS.equalsIgnoreCase(op) && pageNo > 1) {
						pageNo--;
					}

				} else if (OP_NEW.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.ITEM_INFORMATION_CTL, request, response);
					return;
					
				} else if (OP_DELETE.equalsIgnoreCase(op)) {
					pageNo = 1;
					if (ids != null && ids.length > 0) {
						ItemInformationBean deletebean = new ItemInformationBean();
						for (String id : ids) {
							deletebean.setId(DataUtility.getInt(id));
							model.Delete(deletebean);
							ServletUtility.setSuccessMessage("User deleted successfully", request);
						}
					} else {
						ServletUtility.setErrorMessage("Select at least one record", request);
					}
					
				} else if (OP_RESET.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.ITEM_INFORMATION_LIST_CTL, request, response);
					return;
					
				} else if (OP_BACK.equalsIgnoreCase(op)) {
					ServletUtility.redirect(ORSView.ITEM_INFORMATION_LIST_CTL, request, response);
					return;
				}

				list = model.search(bean, pageNo, pageSize);
				next = model.search(bean, pageNo + 1, pageSize);

				if (list == null || list.size() == 0) {
					ServletUtility.setErrorMessage("No record found ", request);
				}

				ServletUtility.setList(list, request);
				ServletUtility.setPageNo(pageNo, request);
				ServletUtility.setPageSize(pageSize, request);
				ServletUtility.setBean(bean, request);
				request.setAttribute("nextListSize", next.size());

				ServletUtility.forward(getView(), request, response);

			} catch (ApplicationException e) {
				e.printStackTrace();
				ServletUtility.handleException(e, request, response);
				return;
			}
		}

		@Override
		protected String getView() {
			return ORSView.ITEM_INFORMATION_LIST_CTL;
		}
	}

