<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="jp.flowershop.domain.Product" %>

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

  <script type='text/javascript'>
  var unitPriceList = {
    <c:forEach var="item" items="${productlist}">
    '${item.productId}':'${item.unitPrice}',
    </c:forEach>
  };

  function selProduct() {
      const productId = document.getElementById('selectProductId').value;
      document.getElementById('inputUnitPrice').value = unitPriceList[productId];
      calcAmount();
  }

  function calcAmount() {
    const qty = document.getElementById('inputQty').value;
    const price = document.getElementById('inputUnitPrice').value;
    document.getElementById('inputAmount').value = qty * price;
  }

  </script>

</head>

<body>

  <jsp:include page="/navi" flush="true | false" />

  <div class="container text-center">
    <h4 class="display-4 text-primary">販売 商品選択・追加</h4>
  </div>

  <!-- LOGIN CONTAINER -->
  <section class="flex-center-center py-5 bg-light">

    <!-- FORM -->
    <div class="w-100 mx-auto px-2" style="max-width: 400px">

    <div class="text-danger">${errorMessage}</div>

    <form id="formSelectProduct" action="selectproduct" method="POST">
      <div class="group">
        <label for="inputSalesNo">販売伝票番号</label>
        <input type="text" class="form-control input" id="inputSalesNo" name="salesNo" value="${salesNo}" >
      </div>
      <div class="group">
        <label for="selectProductId"></label>
        <select class="form-control" id="selectProductId" name="productId" onchange="selProduct()">
          <option value="${item.productId}">商品を選んでください。</option>
        <c:forEach var="item" items="${productlist}">
          <option value="${item.productId}">${item.productName}(商品ID:${item.productId})</option>
        </c:forEach>
        </select>
      </div>
      <div class="group">
        <label for="inputUnitPrice">販売単価</label>
        <input type="text" class="form-control input" id="inputUnitPrice" name="unitPrice" placeholder="販売単価は変更できます" value="${unitPrice}" onchange="calcAmount()">
      </div>
      <div class="group">
        <label for="inputQty">数量</label>
        <input type="text" class="form-control input" id="inputQty" name="qty" placeholder="数量を入れてください" value="${qty}" onchange="calcAmount()">
      </div>
      <div class="group">
        <label for="inputAmount">小計</label>
        <input type="text" class="form-control" id="inputAmount" name="amount" value="" >
      </div>
      <div class="group">
        <button class="btn primary block btn-lg weight-500" type="submit">選択</button>
      </div>
    </form>

    </div>

  </section>

  <!-- JQUERY 3.3.1 -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

  <!-- BOOTSTRAP JS 3.4.1 -->
  <script src="css/bootstrap.min.js"></script>

  <!-- BUTTER CAKE JS 4.0.0 -->
  <script src="css/butterCake.min.js"></script>

</body>

</html>
