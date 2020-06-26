<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="jp.flowershop.domain.Sales" %>

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

<script>
  function newSales(targetNo) {
    var result = window.confirm('販売を新しく登録します。よろしいですか。');
    if (result == false) return;
    document.newSalesForm.action = "newsales"; 
    document.newSalesForm.submit();
  }

  function cancelSales(targetNo) {
    var result = window.confirm('販売伝票:' + targetNo + 'をキャンセルします。よろしいですか。');
    if (result == false) return;
    document.cancelSalesForm.inputCancelSalesNo.value = targetNo;
    document.cancelSalesForm.action = "cancelsales"; 
    document.cancelSalesForm.submit();
  }
</script>

</head>

<body>

  <jsp:include page="/navi" flush="true | false" />

  <div class="container text-center">
    <h4 class="display-4 text-primary">販売一覧</h4>
  </div>

  <section class="main-hero">
    <div class="text-danger">${errorMessage}</div>
    <div style="text-align:right"><a class="btn primary" href="javascript:newSales()">新規販売</a></div>
    <table class="table table-hover">
      <thead>
        <tr>
          <th>販売日</th>
          <th>販売伝票番号</th>
          <th>販売日時</th>
          <th>販売点数</th>
          <th>合計金額</th>
          <th></th>
        </tr> 
      </thead>
      <tbody>
        <c:forEach var="item" items="${saleslist}">
        <tr>
          <td>${item.salesDate}</td>
          <td><a href="salesdetaillist?salesNo=${item.salesNo}">${item.salesNo}</a></td>
          <td>${item.salesDateTime}</td>
          <td>${item.totalQty}</td>
          <td>${item.totalAmount}</td>
          <td><a href="javascript:cancelSales('${item.salesNo}')"><i class="fa fa-remove" style="font-size:36px"></i></a></td>
        </tr>
        </c:forEach>
      </tbody>
    </table>
  </section>

  <form name="newSalesForm" method="post">
  </form>

  <form name="cancelSalesForm" method="post">
    <input type="hidden" id="inputCancelSalesNo" name="cancelSalesNo" value="">
  </form>

  <!-- JQUERY 3.3.1 -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

  <!-- BOOTSTRAP JS 3.4.1 -->
  <script src="css/bootstrap.min.js"></script>  

  <!-- BUTTER CAKE JS 4.0.0 -->
  <script src="css/butterCake.min.js"></script>

</body>

</html>
