package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.ItemInformationBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class ItemInformationModel {

    /* ================= NEXT PK ================= */
    public long nextPK() throws ApplicationException {
        long pk = 0;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT MAX(id) FROM st_iteminformation");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                pk = rs.getLong(1);
            }
            rs.close();
            ps.close();
        } catch (Exception e) {
            throw new ApplicationException("Exception in getting next PK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk + 1;
    }

    /* ================= ADD ================= */
    public long add(ItemInformationBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;
        long pk = 0;

        ItemInformationBean duplicate = findByTitle(bean.getTitle());
        if (duplicate != null) {
            throw new DuplicateRecordException("Title already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            pk = nextPK();

            PreparedStatement ps = conn.prepareStatement(
                "INSERT INTO st_iteminformation VALUES (?,?,?,?,?,?,?,?,?,?)");

            ps.setLong(1, pk);
            ps.setString(2, bean.getTitle());
            ps.setString(3, bean.getOverview());
            ps.setLong(4, bean.getCost());
            ps.setDate(5, new java.sql.Date(bean.getPurchasedate().getTime()));
            ps.setString(6, bean.getCategory());
            ps.setString(7, bean.getCreatedBy());
            ps.setString(8, bean.getModifiedBy());
            ps.setTimestamp(9, bean.getCreatedDateTime());
            ps.setTimestamp(10, bean.getModifiedDateTime());

            ps.executeUpdate();
            conn.commit();
            ps.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new ApplicationException("Exception in adding ItemInformation");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return pk;
    }

    /* ================= UPDATE ================= */
    public void update(ItemInformationBean bean)
            throws ApplicationException, DuplicateRecordException {

        Connection conn = null;

        ItemInformationBean exist = findByTitle(bean.getTitle());
        if (exist != null && exist.getId() != bean.getId()) {
            throw new DuplicateRecordException("Item already exists");
        }

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(
                "UPDATE st_iteminformation SET title=?, overview=?, cost=?, purchasedate=?, " +
                "category=?, created_by=?, modified_by=?, created_datetime=?, modified_datetime=? " +
                "WHERE id=?");

            ps.setString(1, bean.getTitle());
            ps.setString(2, bean.getOverview());
            ps.setLong(3, bean.getCost());
            ps.setDate(4, new java.sql.Date(bean.getPurchasedate().getTime()));
            ps.setString(5, bean.getCategory());
            ps.setString(6, bean.getCreatedBy());
            ps.setString(7, bean.getModifiedBy());
            ps.setTimestamp(8, bean.getCreatedDateTime());
            ps.setTimestamp(9, bean.getModifiedDateTime());
            ps.setLong(10, bean.getId());

            ps.executeUpdate();
            conn.commit();
            ps.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new ApplicationException("Exception in updating ItemInformation");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /* ================= DELETE ================= */
    public void delete(long id) throws ApplicationException {
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            conn.setAutoCommit(false);

            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM st_iteminformation WHERE id=?");
            ps.setLong(1, id);
            ps.executeUpdate();

            conn.commit();
            ps.close();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            throw new ApplicationException("Exception in deleting ItemInformation");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
    }

    /* ================= FIND BY PK ================= */
    public ItemInformationBean findByPK(long pk) throws ApplicationException {
        ItemInformationBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM st_iteminformation WHERE id=?");
            ps.setLong(1, pk);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bean = populateBean(rs);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByPK");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /* ================= FIND BY TITLE ================= */
    public ItemInformationBean findByTitle(String title) throws ApplicationException {
        ItemInformationBean bean = null;
        Connection conn = null;

        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT * FROM st_iteminformation WHERE title=?");
            ps.setString(1, title);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                bean = populateBean(rs);
            }
            rs.close();
            ps.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in findByTitle");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return bean;
    }

    /* ================= SEARCH ================= */
    public List<ItemInformationBean> search(
            ItemInformationBean bean, int pageNo, int pageSize)
            throws ApplicationException {

        ArrayList<ItemInformationBean> list = new ArrayList<>();
        StringBuffer sql = new StringBuffer(
                "SELECT * FROM st_iteminformation WHERE 1=1");

        if (bean != null) {
            if (bean.getTitle() != null && bean.getTitle().length() > 0) {
                sql.append(" AND title LIKE '" + bean.getTitle() + "%'");
            }
            if (bean.getCategory() != null && bean.getCategory().length() > 0) {
                sql.append(" AND category = '" + bean.getCategory() + "'");
            }
        }

        if (pageSize > 0) {
            pageNo = (pageNo - 1) * pageSize;
            sql.append(" LIMIT " + pageNo + "," + pageSize);
        }

        Connection conn = null;
        try {
            conn = JDBCDataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                list.add(populateBean(rs));
            }

            rs.close();
            ps.close();

        } catch (Exception e) {
            throw new ApplicationException("Exception in search ItemInformation");
        } finally {
            JDBCDataSource.closeConnection(conn);
        }
        return list;
    }

    /* ================= POPULATE BEAN ================= */
    private ItemInformationBean populateBean(ResultSet rs) throws Exception {
        ItemInformationBean bean = new ItemInformationBean();
        bean.setId(rs.getLong(1));
        bean.setTitle(rs.getString(2));
        bean.setOverview(rs.getString(3));
        bean.setCost(rs.getLong(4));
        bean.setPurchasedate(rs.getDate(5));
        bean.setCategory(rs.getString(6));
        bean.setCreatedBy(rs.getString(7));
        bean.setModifiedBy(rs.getString(8));
        bean.setCreatedDateTime(rs.getTimestamp(9));
        bean.setModifiedDateTime(rs.getTimestamp(10));
        return bean;
    }
}
