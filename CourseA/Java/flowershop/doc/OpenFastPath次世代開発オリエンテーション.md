# OpenFastPath次世代開発オリエンテーション
ー　 version 2020.06.23　ー

## トレーニング環境の方針

### 目的

* Webアプリケーション開発に従事したことがない方にクイックに学ぶための実装環境を提供する。
* 受講者が異なる技術を学び続けられるように、１つの環境で複数の言語やミドルウェアを利用でき、かつ維持費がかからない学習環境を提供する。

### 基本構成

* 統合開発環境は、複数言語を１つの環境で実施でき、複数のOSにも対応し無償のMicrosoftのVisual Studio Code（以後VS Code）を標準開発環境とする。
* Webアプリケーションの開発及び実行環境はDockerで用意し、異なるバージョンのソフトウエア、ミドルウェアを１つのパソコン上で複数のコンテナを立てることで実現可能とする。（但し、利用PCが32bit版、BIOS仮想化不可の場合は例外とする。）
* VS CodeからDockerのコンテナを操作できるようにするためRemote DevelopmentおよびDocker for VS Codeを利用する。　
* OS環境はLinuxをベースにトレーニングする。Windowsでは仮想環境（WSL2、Hyper-V、VMware）を利用したUbuntuを利用、MacはベースがLinuxのためそのまま利用する。
* 将来、共同開発型トレーニングを実施する場合はクラウド上で環境、またはRaspberry PIなどエッジコンピューティング上での環境を用意し開発することが想定されるが、その場合も、環境はDockerで用意し同じ技術を用いて開発できるようにする。
* クラウド環境が違ってもアプリケーションを実現できるスキルを育成するため、原則クラウドロックされる技術は利用せず、汎用的な技術を利用する。

### 利用マシンスペックによる手順参照先

本トレーニングは利用するマシンのスペックにより環境構築およびトライアルトレーニングの手順が異なる。

* Windows
	* 64bit BIOS仮想化可能
		* Windows Update 2004 May 28以上適用可能（Home / Pro共通）
			* 「OpenFastPath環境構築WSL2セットアップ」
			* 「OpenFastPath環境構築Ubuntu版」
			* 「OpenFastPath環境構築Docker共通設定」
			* 「OpenFastPath環境構築VSCode共通設定」
		* Windows Update 2004 May 28以上適用不可
			* Windows Pro
				* 「OpenFastPath環境構築Hyper-Vセットアップ」
				* 「OpenFastPath環境構築Ubuntu版」
				* 「OpenFastPath環境構築Docker共通設定」
				* 「OpenFastPath環境構築VSCode共通設定」
			* Windows Home
				* 「OpenFastPath環境構築VMwareセットアップ」
				* 「OpenFastPath環境構築Ubuntu版」
				* 「OpenFastPath環境構築Docker共通設定」
				* 「OpenFastPath環境構築VSCode共通設定」
* 32bit またはBIOS仮想化不可
	* 「OpenFastPath環境構築Windows直接利用版」
	* 「OpenFastPath環境構築VSCode共通設定」
* Mac
	* 「OpenFastPath環境構築手順Mac版」
	* 「OpenFastPath環境構築Docker共通設定」
	* 「OpenFastPath環境構築VSCode共通設定」

### 環境利用上の心得

* 本資料に掲載されている手順やバージョンはある時点で有効なものを示しており、環境構築に必要な基礎知識が充足していなくても動作できるようにする道標となるものである。
* 今回の提供環境はあくまで特定の時期において有効であるが、次世代開発で使われる技術は数ヶ月で変化し、記載されている内容は未来永劫動作を保証するものではない。
* トレーニングに参加する皆さんは、「書かれているから正しい」ではなく、もし動作しないものがあればそれはなぜ動作しないのか、どうすれば動作するのかを自身で解決すること、採用している技術やバージョン、手順がそのままで良いのか、もっとより良い技術や手順はないのかを日々追求し取り入れることに挑戦することもトレーニングの一環であることをぜひ心得いただきたい。

#OpenFastPath/Environment/Fixed
