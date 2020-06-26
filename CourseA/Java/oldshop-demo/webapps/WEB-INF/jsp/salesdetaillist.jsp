<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="jp.flowershop.domain.SalesDetail" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">

  <title>Flower Shop</title>

  <!-- FONT AWESOME 4.7.0 -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

  <!-- BOOTSTRAP CSS 3.4.1 -->
  <link rel="stylesheet" href="css/bootstrap.min.css">
  <link rel="stylesheet" href="css/bootstrap-theme.min.css">

  <!-- BUTTER CAKE CSS 4.0.0 -->
  <link rel="stylesheet" href="css/butterCake.min.css">

</head>

<body>

  <jsp:include page="/navi" flush="true | false" />

  <div class="container text-center">
    <h4 class="display-4 text-primary">販売明細</h4>
  </div>

  <section class="main-hero">
    <div class="text-danger">${errorMessage}</div>

    <table class="table table-bordered">
      <tr>
        <th>販売伝票番号</th>
        <td>${salesNo}</td>
        <th>販売日</th>
        <td>${salesDate}</td>
        <th>合計金額</th>
        <td>${totalAmount}円</td>
      </tr>
    </table>
    <div style="text-align:right"><a class="btn primary" href="productselection?salesNo=${salesNo}">追加</a></div>
    <table class="table table-hover">
      <thead>
        <tr>
          <th>販売明細番号</th>
          <th>商品ID</th>
          <th>商品名</th>
          <th>単価</th>
          <th>数量</th>
          <th>小計金額</th>
          <th></th>
        </tr> 
      </thead>
      <tbody>
        <c:forEach var="item" items="${salesdetaillist}">
        <tr>
          <td>${item.salesDetaillNo}</td>
          <td>${item.productId}</td>
          <td>${item.productName}</td>
          <td>${item.unitPrice}</td>
          <td>${item.qty}</td>
          <td>${item.amount}</td>
          <td><a href="unselectproduct?salesNo=${item.salesNo}&productId=${item.productId}"><i class="fa fa-remove" style="font-size:36px"></i></a></td>
        </tr>
        </c:forEach>
      </tbody>
    </table>
  </section>

  <!-- JQUERY 3.3.1 -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

  <!-- BOOTSTRAP JS 3.4.1 -->
  <script src="css/bootstrap.min.js"></script>

  <!-- BUTTER CAKE JS 4.0.0 -->
  <script src="css/butterCake.min.js"></script>

</body>

</html>
