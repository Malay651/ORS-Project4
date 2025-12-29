<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="in.co.rays.proj4.controller.ItemInformationCtl"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.ItemInformationBean"%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Item Information</title>
<style>
footer {
    position: fixed;
    bottom: 0;
    left: 0;
    width: 100%;
}
</style>

</head>

<body>

<form action="<%=ORSView.ITEM_INFORMATION_CTL%>" method="post">

	<%@ include file="Header.jsp"%>

	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.ItemInformationBean"
		scope="request" />

	<!-- Hidden ID for Update -->
	<input type="hidden" name="id" value="<%=bean.getId()%>">

	<div align="center">

		<h1 style="color: navy">
			<% if (bean != null && bean.getId() > 0) { %>
				Update
			<% } else { %>
				Add
			<% } %>
			Item
		</h1>

		<!-- Messages -->
		<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
		<h3><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>

		<table>

			<tr>
				<th align="left">Title <span style="color:red">*</span></th>
				<td>
					<input type="text" name="title" placeholder="Enter Title"
						value="<%=DataUtility.getStringData(bean.getTitle())%>">
				</td>
				<td><font color="red"><%=ServletUtility.getErrorMessage("title", request)%></font></td>
			</tr>

			<tr>
				<th align="left">Overview <span style="color:red">*</span></th>
				<td>
					<input type="text" name="overview" placeholder="Enter Overview"
						value="<%=DataUtility.getStringData(bean.getOverview())%>">
				</td>
				<td><font color="red"><%=ServletUtility.getErrorMessage("overview", request)%></font></td>
			</tr>

			<tr>
				<th align="left">Cost <span style="color:red">*</span></th>
				<td>
					<input type="text" name="cost" placeholder="Enter Cost"
						value="<%=DataUtility.getStringData(bean.getCost())%>">
				</td>
				<td><font color="red"><%=ServletUtility.getErrorMessage("cost", request)%></font></td>
			</tr>

			<tr>
				<th align="left">Purchase Date <span style="color:red">*</span></th>
				<td>
					<input type="text" id="itemDate" name="purchasedate"
						placeholder="Select Purchase Date"
						value="<%=DataUtility.getDateString(bean.getPurchasedate())%>">
				</td>
				<td><font color="red"><%=ServletUtility.getErrorMessage("purchasedate", request)%></font></td>
			</tr>

			<tr>
				<th align="left">Category <span style="color:red">*</span></th>
				<td>
					<%
						HashMap<String, String> map = new HashMap<>();
						map.put("Active", "Active");
						map.put("Inactive", "Inactive");

						String htmlList = HTMLUtility.getList("category", bean.getCategory(), map);
					%>
					<%=htmlList%>
				</td>
				<td><font color="red"><%=ServletUtility.getErrorMessage("category", request)%></font></td>
			</tr>

			<tr>
				<th></th>
				<td colspan="2">
					<% if (bean != null && bean.getId() > 0) { %>
						<input type="submit" name="operation" value="<%=ItemInformationCtl.OP_UPDATE%>">
						<input type="submit" name="operation" value="<%=ItemInformationCtl.OP_CANCEL%>">
					<% } else { %>
						<input type="submit" name="operation" value="<%=ItemInformationCtl.OP_SAVE%>">
						<input type="submit" name="operation" value="<%=ItemInformationCtl.OP_RESET%>">
					<% } %>
				</td>
			</tr>

		</table>
	</div>
</form>

<footer>
<%@ include file="Footer.jsp"%>
</footer>

</body>
</html>
