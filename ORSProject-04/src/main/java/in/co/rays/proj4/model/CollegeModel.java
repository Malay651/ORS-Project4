package in.co.rays.proj4.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import in.co.rays.proj4.bean.CollegeBean;
import in.co.rays.proj4.bean.UserBean;
import in.co.rays.proj4.exception.ApplicationException;
import in.co.rays.proj4.exception.DatabaseException;
import in.co.rays.proj4.exception.DuplicateRecordException;
import in.co.rays.proj4.util.JDBCDataSource;

public class CollegeModel {
 
	public Integer nextpk() {
		
		Connection conn = null;
		int pk = 0;
		
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_college");
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
	
	public Long add(CollegeBean bean) throws ApplicationException, DuplicateRecordException   {
		
		Connection conn = null;
		long pk = 0;

		CollegeBean DuplicateCollegeName = findbyName(bean.getName());

		if (DuplicateCollegeName != null) {
			throw new DuplicateRecordException("college name already exists");
		}
		try {
			conn = JDBCDataSource.getConnection();
			pk = nextpk();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("insert into st_college values (?,?,?,?,?,?,?,?,?,?)");
			pstmt.setLong(1, pk);
			pstmt.setString(2, bean.getName());
			pstmt.setString(3, bean.getAddress());
			pstmt.setString(4, bean.getState());
			pstmt.setString(5,bean.getCity());
			pstmt.setString(6,bean.getPhoneNo());
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
	
	
	public void Update(CollegeBean bean) throws Exception {
		
		Connection conn = null;

		CollegeBean beanExist = findbyName(bean.getName());

		if (beanExist != null && beanExist.getId() != bean.getId()) {
			throw new DuplicateRecordException("College is already exist");
		}
		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement(
					"update st_college set name = ?,address= ?,state = ?,city = ?,phone_no = ?,created_by = ?,modified_by = ?,created_datetime = ?,modified_dateTime = ? where id = ?");

			pstmt.setString(1, bean.getName());
			pstmt.setString(2, bean.getAddress());
			pstmt.setString(3, bean.getState());
			pstmt.setString(4,bean.getCity());
			pstmt.setString(5,bean.getPhoneNo());
		    pstmt.setString(6, bean.getCreatedBy());
			pstmt.setString(7, bean.getModifiedBy());
			pstmt.setTimestamp(8, bean.getCreatedDateTime());
			pstmt.setTimestamp(9, bean.getModifiedDateTime());
			pstmt.setLong(10,bean.getId());
			
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
			throw new ApplicationException("Exception in updating college");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
	
	public void Delete(CollegeBean bean) throws ApplicationException {
		Connection conn = null;

		try {
			conn = JDBCDataSource.getConnection();
			conn.setAutoCommit(false);
			PreparedStatement pstmt = conn.prepareStatement("delete from st_college where id = ?");

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
			throw new ApplicationException("Exception : Exception in deleting college");

		} finally {
			JDBCDataSource.closeConnection(conn);
		}

	}
	
	public CollegeBean findbypk(long pk) throws ApplicationException {
		
		CollegeBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_college where id = ?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setLong(1, pk);
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
			
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
				throw new ApplicationException("Exception : Exception in getting college by pk");
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;

	}
	
	public CollegeBean findbyName(String name) throws ApplicationException  {
		
		CollegeBean bean = null;
		Connection conn = null;

		StringBuffer sql = new StringBuffer("select * from st_college where name = ?");
		try {
			conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString(1,name);
			ResultSet rs = pstmt.executeQuery();
			
			while (rs.next()) {
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
				throw new ApplicationException("Exception : Exception in getting college by name");
			
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		return bean;

	}
	public List<UserBean> list() throws ApplicationException {
		return search(null, 0, 0);
	}


	public List search(CollegeBean bean , int pageNo , int pageSize) throws ApplicationException {
		
		StringBuffer sql = new StringBuffer("select * from st_college where 1=1");
		ArrayList list = new ArrayList();
		Connection conn = null;
		
		if (bean != null) {
			if (bean.getId() > 0) {
				sql.append(" and id = " + bean.getId());
			}
			if (bean.getName() != null && bean.getName().length() > 0) {
				sql.append(" and name like '" + bean.getName() + "%'");
			}
			if (bean.getAddress() != null && bean.getAddress().length() > 0) {
				sql.append(" and address like '" + bean.getAddress() + "%'");
			}
			if (bean.getState() != null && bean.getState().length() > 0) {
				sql.append(" and state like '" + bean.getState() + "%'");
			}
			if (bean.getCity() != null && bean.getCity().length() > 0) {
				sql.append(" and city like '" + bean.getCity() + "%'");
			}
			if (bean.getPhoneNo() != null && bean.getPhoneNo().length() > 0) {
				sql.append(" and phone_no = " + bean.getPhoneNo());
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
				bean = new CollegeBean();
				bean.setId(rs.getLong(1));
				bean.setName(rs.getString(2));
				bean.setAddress(rs.getString(3));
				bean.setState(rs.getString(4));
				bean.setCity(rs.getString(5));
				bean.setPhoneNo(rs.getString(6));
				bean.setCreatedBy(rs.getString(7));
				bean.setModifiedBy(rs.getString(8));
				bean.setCreatedDateTime(rs.getTimestamp(9));
				bean.setModifiedDateTime(rs.getTimestamp(10));
				list.add(bean);
			}
			rs.close();
			pstmt.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ApplicationException("Exception : Exception in search college");
			
		} finally {
			JDBCDataSource.closeConnection(conn);
		}
		
		return list;

	}

	}

