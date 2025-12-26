<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.Iterator"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.HashMap"%>

<%@ page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page import="in.co.rays.proj4.controller.ItemInformationListCtl"%>

<%@ page import="in.co.rays.proj4.bean.ItemInformationBean"%>

<%@ page import="in.co.rays.proj4.util.DataUtility"%>
<%@ page import="in.co.rays.proj4.util.ServletUtility"%>
<%@ page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@ page import="in.co.rays.proj4.util.PropertyReader"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Item Information List</title>

<script>
function toggle(source) {
    const checkboxes = document.querySelectorAll('input[name="ids"]');
    for (let i = 0; i < checkboxes.length; i++) {
        checkboxes[i].checked = source.checked;
    }
}
</script>
</head>

<body>

<%@ include file="Header.jsp"%>

<div align="center">

<h1 style="color: navy">Item Information List</h1>

<h3><font color="red"><%=ServletUtility.getErrorMessage(request)%></font></h3>
<h3><font color="green"><%=ServletUtility.getSuccessMessage(request)%></font></h3>

<form action="<%=ORSView.ITEM_INFORMATION_LIST_CTL%>" method="post">

<%
    int pageNo = ServletUtility.getPageNo(request);
    int pageSize = ServletUtility.getPageSize(request);

    if (pageNo <= 0) pageNo = 1;
    if (pageSize <= 0) {
        pageSize = DataUtility.getInt(PropertyReader.getValue("page.size"));
    }

    int index = ((pageNo - 1) * pageSize) + 1;

    int nextListSize  = DataUtility.getInt(request.getAttribute("nextListSize").toString());
   

    List<ItemInformationBean> list =
        (List<ItemInformationBean>) ServletUtility.getList(request);
%>

<input type="hidden" name="pageNo" value="<%=pageNo%>">
<input type="hidden" name="pageSize" value="<%=pageSize%>">

<!-- Search Panel -->
<table width="100%">
<tr>
<td align="center">

<b>Title :</b>
<input type="text" name="title"
value="<%=ServletUtility.getParameter("title", request)%>">

&nbsp;&nbsp;

<b>Category :</b>
<%
    HashMap<String, String> map = new HashMap<>();
    map.put("Active", "Active");
    map.put("Inactive", "Inactive");
%>
<%=HTMLUtility.getList("category",
        ServletUtility.getParameter("category", request), map)%>

&nbsp;&nbsp;

<input type="submit" name="operation" value="<%=ItemInformationListCtl.OP_SEARCH%>">
<input type="submit" name="operation" value="<%=ItemInformationListCtl.OP_RESET%>">

</td>
</tr>
</table>

<br>

<!-- List Table -->
<table border="1" width="100%">
<tr style="background-color:#e1e6f1">
<th><input type="checkbox" onclick="toggle(this)"></th>
<th>S.No</th>
<th>Title</th>
<th>Overview</th>
<th>Cost</th>
<th>Purchase Date</th>
<th>Category</th>
<th>Edit</th>
</tr>

<%
if (list != null && !list.isEmpty()) {
    Iterator<ItemInformationBean> it = list.iterator();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    while (it.hasNext()) {
        ItemInformationBean bean = it.next();
%>

<tr>
<td align="center"><input type="checkbox" name="ids" value="<%=bean.getId()%>"></td>
<td align="center"><%=index++%></td>
<td align="center"><%=bean.getTitle()%></td>
<td align="center"><%=bean.getOverview()%></td>
<td align="center"><%=bean.getCost()%></td>
<td align="center"><%=bean.getPurchasedate()!=null?sdf.format(bean.getPurchasedate()):""%></td>
<td align="center"><%=bean.getCategory()%></td>
<td align="center">
<a href="<%=ORSView.ITEM_INFORMATION_CTL%>?id=<%=bean.getId()%>">Edit</a>
</td>
</tr>

<%
    }
} else {
%>

<tr>
<td colspan="8" align="center">No record found</td>
</tr>

<%
}
%>

</table>

<br>

<!-- Buttons -->
<table width="100%">
<tr>
<td>
<input type="submit" name="operation" value="<%=ItemInformationListCtl.OP_PREVIOUS%>"
<%=pageNo > 1 ? "" : "disabled"%>>
</td>

<td align="center">
<input type="submit" name="operation" value="<%=ItemInformationListCtl.OP_NEW%>">
</td>

<td align="center">
<input type="submit" name="operation" value="<%=ItemInformationListCtl.OP_DELETE%>">
</td>

<td align="right">
<input type="submit" name="operation" value="<%=ItemInformationListCtl.OP_NEXT%>"
<%=nextListSize != 0 ? "" : "disabled"%>>
</td>
</tr>
</table>

</form>
</div>

<%@ include file="Footer.jsp"%>

</body>
</html>
