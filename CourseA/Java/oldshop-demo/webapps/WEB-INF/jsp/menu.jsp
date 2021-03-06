<%@ page language="java" contentType="text/html; charset=UTF-8"                                                                                                          
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ page import="java.util.Enumeration"%>
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

  <!-- HERO BANNER -->
  <section class="main-hero">
    <img src="images/flowertitle.png" th:src="@{/images/flowertitle.png}">
    <div class="container text-center">
      <p><span>${userName}</span>さん、おはようございます。</p>
      <p>ただいまの時刻は<span>${currDate}</span>です。</p>
    </div>
  </section>

  <div class="py-2 sample row">
    <div class="col-sm-6 col-md-4 mb-2">
      <div class="card">
        <img alt="Thumbnail [100%x150]" src="images/thumbnail.png" th:src="@{/images/thumbnail.png}" 
          class="img-cover page_speed_786242883">
        <div class="card-body">
          <a class="h4 mb-1 d-block" href="saleslist">販売業務</a>
          <p class="text-muted">
            <small>お店での販売を記録し、販売の実績をご確認いただけます。</small>
          </p>
          <a class="btn primary" href="saleslist">こちらから開始</a>
        </div>
      </div>
    </div>

    <div class="col-sm-6 col-md-4 mb-2">
      <div class="card">
        <img alt="Thumbnail [100%x150]" src="images/thumbnail.png" th:src="@{/images/thumbnail.png}" 
          class="img-cover page_speed_786242883">
        <div class="card-body">
          <a class="h4 mb-1 d-block" href="#">発注業務</a>
          <p class="text-muted">
            <small>準備中ー近日公開予定です</small>
          </p>
          <a class="btn gray" href="#">準備中</a>
        </div>
      </div>
    </div>

    <div class="col-sm-6 col-md-4 mb-2">
      <div class="card">
        <img alt="Thumbnail [100%x150]" src="images/thumbnail.png" th:src="@{/images/thumbnail.png}" 
          class="img-cover page_speed_786242883">
        <div class="card-body">
          <a class="h4 mb-1 d-block" href="#">販売分析</a>
          <p class="text-muted">
            <small>準備中ー近日公開予定です</small>
          </p>
          <a class="btn gray" href="#">準備中</a>
        </div>
      </div>
    </div>
  </div>

  <div class="py-2 sample row">
    <div class="col-sm-6 col-md-4 mb-2">
      <div class="card">
        <img alt="Thumbnail [100%x150]" src="images/thumbnail.png" th:src="@{/images/thumbnail.png}" 
          class="img-cover page_speed_786242883">
        <div class="card-body">
          <a class="h4 mb-1 d-block" href="#">在庫管理</a>
          <p class="text-muted">
            <small>準備中ー近日公開予定です</small>
          </p>
          <a class="btn gray" href="#">準備中</a>
        </div>
      </div>
    </div>

    <div class="col-sm-6 col-md-4 mb-2">
      <div class="card">
        <img alt="Thumbnail [100%x150]" src="images/thumbnail.png" th:src="@{/images/thumbnail.png}" 
          class="img-cover page_speed_786242883">
        <div class="card-body">
          <a class="h4 mb-1 d-block" href="#">商品管理</a>
          <p class="text-muted">
            <small>準備中ーまもなく公開予定です</small>
          </p>
          <a class="btn gray" href="#">準備中</a>
        </div>
      </div>
    </div>

    <div class="col-sm-6 col-md-4 mb-2">
      <div class="card">
        <img alt="Thumbnail [100%x150]" src="images/thumbnail.png" th:src="@{/images/thumbnail.png}" 
          class="img-cover page_speed_786242883">
        <div class="card-body">
          <a class="h4 mb-1 d-block" href="#">財務会計</a>
          <p class="text-muted">
            <small>準備中ー来年公開予定です</small>
          </p>
          <a class="btn gray" href="#">準備中</a>
        </div>
      </div>
    </div>
  </div>

  <div class="py-2 sample row">
    <div class="col-sm-6 col-md-4 mb-2">
      <div class="card">
        <img alt="Thumbnail [100%x150]" src="images/thumbnail.png" th:src="@{/images/thumbnail.png}" 
          class="img-cover page_speed_786242883">
        <div class="card-body">
          <a class="h4 mb-1 d-block" href="#">ユーザ管理</a>
          <p class="text-muted">
            <small>準備中ー利益が増えて店員を増やすことになったら開発します。</small>
          </p>
          <a class="btn gray" href="#">準備中</a>
        </div>
      </div>
    </div>
  </div>


  <!-- JQUERY 3.3.1 -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

  <!-- BOOTSTRAP JS 3.4.1 -->
  <script src="css/bootstrap.min.js"></script>

  <!-- BUTTER CAKE JS 4.0.0 -->
  <script src="css/butterCake.min.js"></script>

</body>

</html>
