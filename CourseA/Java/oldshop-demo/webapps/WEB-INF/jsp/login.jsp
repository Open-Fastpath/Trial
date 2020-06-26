<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="ja">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">

  <title>Flower Shop</title>

  <!-- FONT AWESOME 4.7.0 -->
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

  <!-- BUTTER CAKE CSS 4.0.0 -->
  <link rel="stylesheet" href="css/butterCake.min.css">

</head>

<body>

  <!-- LOGIN CONTAINER -->
  <section class="login-page flex-center-center py-5 bg-light">

    <!-- FORM -->
    <div class="w-100 mx-auto px-2" style="max-width: 400px">
      <form action="login" method=POST>

        <div class="text-center text-gray">
          <h2 class="weight-500 mb-1">Login</h2>
          <p class="h4 mb-2 weight-300">
           ログインしてください 
          </p>
        </div>

        <div class="card overflow-unset mt-9 mb-1">
          <div class="card-body">

            <!-- AVATAR -->
            <div class="avatar-icon text-center">
              <!--<img src="https://placehold.it/128x128" alt="Avatar"-->
              <img src="images/flowershop.ico" alt="Avatar"
              class="img-circle img-cover card mb-2 ml-auto mr-auto p-1">
            </div>

            <div class="text-danger">${errorMessage}</div>

            <!-- USERID -->
            <div class="group">
              <input type="text" class="input" name="userId" placeholder="ユーザIDを入力してください。">
            </div>

            <!-- PASSWORD -->
            <div class="group">
              <input type="password" class="input" name="password" placeholder="パスワードを入力してください">
            </div>

            <!-- LOGIN -->
            <div class="group">
              <button class="btn primary block btn-lg weight-500" type="submit">Login</button>
            </div>

          </div>
        </div>

        <!-- LINKS -->
        <div class="text-center weight-600 text-gray">
          <a href="" class="text-gray">Sign Up</a> · <a href="" class="text-gray">Forgot Password</a> · <a href=""
            class="text-gray">Need Help?</a>
        </div>
      </form>
    </div>

  </section>

  <!-- STYLE -->
  <style>
    .login-page {
      min-height: 100vh;
    }

    .login-page .avatar-icon img {
      margin-top: -80px;
      width: 128px;
      height: 128px;
    }
  </style>


  <!-- JQUERY 3.3.1 -->
  <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>

  <!-- BUTTER CAKE JS 4.0.0 -->
  <script src="css/butterCake.min.js"></script>

</body>

</html>
