1. デモアプリケーションの動き

```PlantUML
actor ユーザ
boundary Servlet
boundary View
entity ViewModel
control Controller
control Application
entity DomainModel
boundary JDBC
boundary Repository
database DB

ユーザ->Servlet : ユースケースの依頼
Servlet->Servlet : web.xml\nURI→Servlet判定
Servlet->Controller : FlowerConfig URI->ユースケースを担うController判定
Controller->Application : ユースケースを\nビジネス機能に\n役割分担し依頼
Application->JDBC : トランザクション開始
JDBC->DB : begin trans
Application->Repository : データ読込依頼:find()
DB->Repository : SELECT
Repository->DomainModel : 読込データの設定:DomainModel.set()
Repository->Application : データ読込結果を反映した\nDomainModel(ビジネスルール・情報)の提供
Application->DomainModel : ビジネスルールと情報の利用
DomainModel->Application : ルール判定・計算結果の提供
Application->Repository : データ保管依頼:save()
DomainModel->Repository : 保管データの取得:DomainModel.get()
Repository->DB : INSERT\nUPDATE\nDELETE
Repository->Application : データ保管結果
Application->JDBC : トランザクション終了
JDBC->DB : commit end trans
Application->Controller : ビジネス機能\n実行結果
Controller->ViewModel : UI用データ\nの編集依頼
Controller->Servlet : View(JSP)または\n遷移先URIの指定
Servlet->View : web.xml\nViewからJSPの選定
ViewModel->View : 表示データの\n取得と反映
View->ユーザ : ユースケースの結果

```

