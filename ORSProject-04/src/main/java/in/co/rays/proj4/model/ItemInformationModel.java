package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.ItemInformationBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

 public class ItemInformationModel {
	 
		public Integer nextpk() {
			
			Connection conn = null;
			int pk = 0;
			
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_iteminformation");
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					pk = rs.getInt(1);
			    } 
				rs.close();
				pstmt.close();
			} catch (Exception e) {
				
				} finally {
					JDBCDataSource.closeConnection(conn);
				}
				return pk + 1;
			
		}
		
		/**
		 * 
		 * @param bean
		 * @return
		 * @throws ApplicationException
		 * @throws sDuplicateRecordException
		 */
		public Long add(ItemInformationBean bean) throws ApplicationException, DuplicateRecordException   {
			
			Connection conn = null;
			long pk = 0;

			ItemInformationBean DuplicateIteminformation = findbyTitle(bean.getTitle());

			if (DuplicateIteminformation != null) {
				throw new DuplicateRecordException(" title already exists");
			}
			try {
				conn = JDBCDataSource.getConnection();
				pk = nextpk();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement("insert into st_iteminformation values (?,?,?,?,?,?,?,?,?,?)");
				pstmt.setLong(1, pk);
				pstmt.setString(2, bean.getTitle());
				pstmt.setString(3, bean.getOverview());
				pstmt.setDouble(4, bean.getCost());
				pstmt.setDate(5,new java.sql.Date(bean.getPurchasedate().getTime()));
				pstmt.setString(6,bean.getCategory());
				pstmt.setString(7,bean.getCreatedBy());
				pstmt.setString(8, bean.getModifiedBy());
				pstmt.setTimestamp(9, bean.getCreatedDateTime());
				pstmt.setTimestamp(10, bean.getModifiedDateTime());
				
				pstmt.executeUpdate();
				conn.commit();
				pstmt.close();
				
			} catch (Exception e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (Exception ex) {
					throw new ApplicationException("Exception : add rollback Exception" + ex.getMessage());
				}
				throw new ApplicationException("Exception : Exception in add role");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			return pk;
		}
		
		
		public void Update(ItemInformationBean bean) throws Exception {
			
			Connection conn = null;

			ItemInformationBean beanExist = findbyTitle(bean.getTitle());

			if (beanExist != null && beanExist.getId() != bean.getId()) {
				throw new DuplicateRecordException("iteminformation is already exist");
			}
			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement(
						"update st_iteminformation set title= ?,overview= ?,cost = ?,purchasedate = ?,category = ?,created_by = ?,modified_by = ?,created_datetime = ?,modified_dateTime = ? where id = ?");

				pstmt.setLong(1, bean.getId());
				pstmt.setString(2, bean.getTitle());
				pstmt.setString(3, bean.getOverview());
				pstmt.setDouble(4, bean.getCost());
				pstmt.setDate(5,new java.sql.Date(bean.getPurchasedate().getTime()));
				pstmt.setString(6,bean.getCategory());
				pstmt.setString(7,bean.getCreatedBy());
				pstmt.setString(8, bean.getModifiedBy());
				pstmt.setTimestamp(9, bean.getCreatedDateTime());
				pstmt.setTimestamp(10, bean.getModifiedDateTime());
			
			    pstmt.executeUpdate();
				conn.commit();
				pstmt.close();
				System.out.println("Data Updated");
			} catch (Exception e) {
				e.printStackTrace();

				try {
					conn.rollback();
				} catch (Exception ex) {
					// TODO: handle exception

					throw new ApplicationException("Exception : deleting rollback Exception" + ex.getMessage());
				}
				throw new ApplicationException("Exception in updating iteminformation");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}

		}
		
		public void Delete(ItemInformationBean bean) throws ApplicationException {
			Connection conn = null;

			try {
				conn = JDBCDataSource.getConnection();
				conn.setAutoCommit(false);
				PreparedStatement pstmt = conn.prepareStatement("delete from st_iteminformation where id = ?");

				pstmt.setLong(1,bean.getId());

				int i = pstmt.executeUpdate();

				System.out.println("data deleted => " + i);

				pstmt.executeUpdate();
				conn.commit();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					conn.rollback();
				} catch (Exception ex) {
					throw new ApplicationException("Exception : delete rollback Exception" + ex.getMessage());
				}
				throw new ApplicationException("Exception : Exception in deleting iteminformation");

			} finally {
				JDBCDataSource.closeConnection(conn);
			}

		}
		
		public ItemInformationBean findbypk(long pk) throws ApplicationException {
			
			ItemInformationBean bean = null;
			Connection conn = null;

			StringBuffer sql = new StringBuffer("select * from st_iteminformation where id = ?");
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				pstmt.setLong(1, pk);
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new ItemInformationBean();
					bean.setId(rs.getLong(1));
					bean.setTitle(rs.getString(2));
					bean.setOverview(rs.getString(3));
					bean.setCost(rs.getDouble(4));
					bean.setPurchasedate(rs.getDate(5));
					bean.setCategory(rs.getString(6));
					bean.setCreatedBy(rs.getString(7));
					bean.setModifiedBy(rs.getString(8));
					bean.setCreatedDateTime(rs.getTimestamp(9));
					bean.setModifiedDateTime(rs.getTimestamp(10));
				
				}
				rs.close();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
					throw new ApplicationException("Exception : Exception in getting iteminformation by pk");
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			return bean;

		}
		
		public ItemInformationBean findbyTitle(String title) throws ApplicationException  {
			
			ItemInformationBean bean = null;
			Connection conn = null;

			StringBuffer sql = new StringBuffer("select * from iteminformation where title = ?");
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				pstmt.setString(1,title);
				ResultSet rs = pstmt.executeQuery();
				
				while (rs.next()) {
					bean = new ItemInformationBean();
					bean.setId(rs.getLong(1));
					bean.setTitle(rs.getString(2));
					bean.setOverview(rs.getString(3));
					bean.setCost(rs.getDouble(4));
					bean.setPurchasedate(rs.getDate(5));
					bean.setCategory(rs.getString(6));
					bean.setCreatedBy(rs.getString(7));
					bean.setModifiedBy(rs.getString(8));
					bean.setCreatedDateTime(rs.getTimestamp(9));
					bean.setModifiedDateTime(rs.getTimestamp(10));
								}
				rs.close();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
					throw new ApplicationException("Exception : Exception in getting iteminformation by title");
				
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			return bean;

		}
		public List<UserBean> list() throws ApplicationException {
			return search(null, 0, 0);
		}


		public List search(ItemInformationBean bean , int pageNo , int pageSize) throws ApplicationException {
			
			StringBuffer sql = new StringBuffer("select * from st_iteminformation where 1=1");
			ArrayList list = new ArrayList();
			Connection conn = null;
			
			if (bean != null) {
				if (bean.getId() > 0) {
					sql.append(" and id = " + bean.getId());
				}
				if (bean.getTitle() != null && bean.getTitle().length() > 0) {
					sql.append(" and title like '" + bean.getTitle() + "%'");
				}
				if (bean.getOverview() != null && bean.getOverview().length() > 0) {
					sql.append(" and overview like '" + bean.getOverview() + "%'");
				}
				if (bean.getCost() > 0.0) {
					sql.append(" and cost like '" + bean.getCost() + "%'");
				}
				if (bean.getPurchasedate() != null && bean.getPurchasedate().getTime() > 0) {
					sql.append(" and purchasedate like '" + bean.getPurchasedate() + "%'");
				}
				if (bean.getCategory() != null && bean.getCategory().length() > 0) {
					sql.append(" and category = " + bean.getCategory());
				}
			}

				if (pageSize > 0) {
					pageNo = (pageNo - 1) * pageSize;
					sql.append(" limit " + pageNo + "," + pageSize);
				}
			
			
			try {
				conn = JDBCDataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql.toString());
				ResultSet rs = pstmt.executeQuery();
				while (rs.next()) {
					bean = new ItemInformationBean();
					bean.setId(rs.getLong(1));
					bean.setTitle(rs.getString(2));
					bean.setOverview(rs.getString(3));
					bean.setCost(rs.getDouble(4));
					bean.setPurchasedate(rs.getDate(5));
					bean.setCategory(rs.getString(6));
					bean.setCreatedBy(rs.getString(7));
					bean.setModifiedBy(rs.getString(8));
					bean.setCreatedDateTime(rs.getTimestamp(9));
					bean.setModifiedDateTime(rs.getTimestamp(10));
								}
			
					list.add(bean);
				
				rs.close();
				pstmt.close();
			} catch (Exception e) {
				e.printStackTrace();
				throw new ApplicationException("Exception : Exception in search iteminformation");
				
			} finally {
				JDBCDataSource.closeConnection(conn);
			}
			
			return list;

		}

		}

		 
		