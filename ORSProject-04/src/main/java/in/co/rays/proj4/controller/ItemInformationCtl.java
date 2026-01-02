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

    /* ================= VALIDATION ================= */
    @Override
    protected boolean validate(HttpServletRequest request) {

        log.debug("ItemInformationCtl validate started");

        boolean pass = true;

        if (DataValidator.isNull(request.getParameter("title"))) {
            request.setAttribute("title",
                    PropertyReader.getValue("error.require", "Title"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("overview"))) {
            request.setAttribute("overview",
                    PropertyReader.getValue("error.require", "Overview"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("cost"))) {
            request.setAttribute("cost",
                    PropertyReader.getValue("error.require", "Cost"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("purchasedate"))) {
            request.setAttribute("purchasedate",
                    PropertyReader.getValue("error.require", "Purchase Date"));
            pass = false;
        }

        if (DataValidator.isNull(request.getParameter("category"))) {
            request.setAttribute("category",
                    PropertyReader.getValue("error.require", "Category"));
            pass = false;
        }

        log.debug("ItemInformationCtl validate ended");

        return pass;
    }

    /* ================= POPULATE BEAN ================= */
    @Override
    protected BaseBean populateBean(HttpServletRequest request) {

        log.debug("ItemInformationCtl populateBean started");

        ItemInformationBean bean = new ItemInformationBean();

        bean.setId(DataUtility.getLong(request.getParameter("id")));
        bean.setTitle(DataUtility.getString(request.getParameter("title")));
        bean.setOverview(DataUtility.getString(request.getParameter("overview")));
        bean.setCost(DataUtility.getLong(request.getParameter("cost")));
        bean.setPurchasedate(
                DataUtility.getDate(request.getParameter("purchasedate")));
        bean.setCategory(
                DataUtility.getString(request.getParameter("category")));

        populateDTO(bean, request);

        log.debug("ItemInformationCtl populateBean ended");
        return bean;
    }

    /* ================= DO GET ================= */
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long id = DataUtility.getLong(request.getParameter("id"));
        ItemInformationModel model = new ItemInformationModel();

        if (id > 0) {
            try {
                ItemInformationBean bean = model.findByPK(id);
                ServletUtility.setBean(bean, request);
            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }
        }
        ServletUtility.forward(getView(), request, response);
    }

    /* ================= DO POST ================= */
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        log.debug("ItemInformationCtl doPost started");

        String op = DataUtility.getString(request.getParameter("operation"));
        long id = DataUtility.getLong(request.getParameter("id"));

        ItemInformationModel model = new ItemInformationModel();

        if (OP_SAVE.equalsIgnoreCase(op)) {

            ItemInformationBean bean =
                    (ItemInformationBean) populateBean(request);
            System.out.println(bean);
            try {
                model.add(bean);
                ServletUtility.setBean(bean, request);
                ServletUtility.setSuccessMessage(
                        "Data saved successfully", request);

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage(
                        "Title already exists", request);

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_UPDATE.equalsIgnoreCase(op)) {

            ItemInformationBean bean =
                    (ItemInformationBean) populateBean(request);

            try {
                if (id > 0) {
                    model.update(bean);
                    ServletUtility.setBean(bean, request);
                    ServletUtility.setSuccessMessage(
                            "Data updated successfully", request);
                }

            } catch (DuplicateRecordException e) {
                ServletUtility.setBean(bean, request);
                ServletUtility.setErrorMessage(
                        "Title already exists", request);

            } catch (ApplicationException e) {
                log.error(e);
                ServletUtility.handleException(e, request, response);
                return;
            }

        } else if (OP_CANCEL.equalsIgnoreCase(op)) {
            ServletUtility.redirect(
                    ORSView.ITEM_INFORMATION_LIST_CTL,
                    request, response);
            return;

        } else if (OP_RESET.equalsIgnoreCase(op)) {
            ServletUtility.redirect(
                    ORSView.ITEM_INFORMATION_CTL,
                    request, response);
            return;
        }

        ServletUtility.forward(getView(), request, response);
        log.debug("ItemInformationCtl doPost ended");
    }

    /* ================= VIEW ================= */
    @Override
    protected String getView() {
        return ORSView.ITEM_INFORMATION_VIEW;
    }
}
