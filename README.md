# demo-app 自分用メモ
- Ctrl+Shift+Vでプレビュー(VSCode)

## ディレクトリ構成
- vsc_workspaceを開く
- demo-app: リポジトリ
- .vscode: VSCodeの設定
- apis/xxx-service: 各サービスのSpringBootプロジェクト, マイクロサービスアーキテクチャ(サービスごとに@SpringBootApplicationがある感じ)
- jar/xxx: 解凍したJarファイルの置き場所, ex) Gradleのjar: C:\Users\81802\.gradle\caches\modules-2, [参考URL](https://qiita.com/opengl-8080/items/4c1aa85b4737bd362d9e)
- sql: demo-db.mv.dbがDB本体, その他SQLファイル(.sql)
- ui-vue3: Vuetifyプロジェクト、ビルド(コンパイル等)後の静的な状態をデプロイすればSSGできそう？

## 起動手順
- sql: MySQL Serverをコマンドプロンプトで起動, タスクマネージャで確認
- apis/xxx-service: VSCode上でXxxApplication.javaをRunで実行
- ui-vue3: npm run devコマンドで実行

## H2/MySQL
### H2
- H2はSpringInitializrの依存関係でインストールできるのでok(H2個別でインストールと異なりコンソールが使えない...)
- H2コンソールを使うためにサーバーモードでH2に接続する(ファイルモードでもコネクション1つだけどできる) [参考URL](https://it-jog.com/java/springdatajpa/h2savefile)
- DBには3つのモードがあるようだ
  - インメモリモード: 指定なしまたはmem:のJDBC URL, アプリ停止でデータ消滅, ファイルが存在しないのでコンソールからは操作できない
  - ファイルモード: FILE:のJDBC URL, DBファイルにデータ書込, コンソールから操作できるが1つしかコネクションをはれないのでアプリとコンソールを切り替えながらやる感じになる
  - サーバーモード: TCP:のJDBC URL, DBファイルにデータ書込, 複数のコネクションをはれるのだがSpring BootとH2の組合せでは非対応？(これやるならMySQLをインストールして使う必要ありそう)
### MySQL
- たまに動かなくなるのでパソコンごと再起動(タスクマネージャで再起動してもダメ)
- [MySQLダウンロード](https://dev.mysql.com/downloads/mysql/): ここからダウンロードした(MySQL Community Server)
- MySQLツール
  - MySQL Server: DBサーバ
  - MySQL Shell: SQLクライアント(CLI)
  - MySQL Workbench: SQLクライアント(GUI)
  - MySQL Router: ロードバランサ?, [参考URL](https://gihyo.jp/dev/serial/01/mysql-road-construction-news/0118)
- MySQLの設定ファイル(データディレクトリ等): "C:\Program Files\MySQL\MySQL Server 8.0\mysql.ini"
- 起動ログに一時パスワードが出るのでログイン後rootユーザーのパスワードを変更
- 起動: mysqld, ログイン: mysql -u <ユーザー> -p <パスワード>, 停止: mysqladmin -u root -p shutdown, [参考URL](https://www.javadrive.jp/mysql/install/index10.html)

## SpringBoot
- https://spring.io/quickstart
- JDKは別でインストール(Oracle JDK LTS21)
- VSCodeのアドオン入れる(内部的にSpringInitializr使っているようなので同じ)
- Ctrl+Shift+PでSpringBootプロジェクト作成

## Vuetify
- Node.jsをインストール(Node v22.17.0)
- Vuetifyプロジェクト作成(Vue自体も入った状態なのでok)
- npm create vuetify@latest
- tsconfig.app.jsonを書き換え("extends": "@vue/tsconfig/tsconfig.json")
- cd Vuetifyプロジェクトのディレクトリ
- npm run dev

## GitHub
### 設定
- Git Bashを使う(現在のブランチを表示してくれる)
- Settings > Password and authentication > Authenticator app, Google AuthenticatorをスマホでダウンロードしてアカウントのMFAを設定
- Settings > Developer Settings > Personal access tokens(classic), repo権限のトークンを作成(プライベートリポジトリのアクセスに必要)
- [ユーザー名とメルアドの設定, リポジトリのクローン](https://docs.github.com/ja/get-started/git-basics/set-up-git), clone・pull・pushでダイアログ出るがブラウザログインで認証できる
- .git: GitHubの設定(リモートoriginとブランチmain等), クローンすると取得できる
- .gitignore: Git側で無視するファイルの設定, [参考URL](https://qiita.com/anqooqie/items/110957797b3d5280c44f)
### 操作
- みえているファイルは現在のブランチの状態
- チケットごとにブランチを切って開発
- マージの手順
  - baseブランチにマージしたいcompareブランチのPull Requestを作成
  - Pull Requestに含まれる差分を確認
  - 問題ないならマージする, クローズすればマージされない
- git config --list: 設定の一覧
- git config <設定> <値>: 設定の更新
- git checkout -b <ローカルブランチ>: ローカルブランチの作成と移動
  - git branch <ローカルブランチ>: ローカルブランチの作成
  - git checkout <ローカルブランチ>: ローカルブランチの切替
- git branch -vv: ブランチの詳細表示
  - フォーマット: <ローカルブランチ> [リモートブランチ] 最新コミットメッセージ
- git branch --set-upstream-to=<リモート/リモートブランチ> <ローカルブランチ>: リモートブランチをローカルブランチに設定
- git branch -d <ローカルブランチ>: ローカルブランチの削除
- git pull: リモートのブランチの変更をローカルのブランチに取得
  - git pull <リモート> <ブランチ>: デフォルトだとリモートがorigin, ブランチが現在のブランチ
- git add <ファイル>: 変更をステージング(差分確認(git status, git diff))
  - git add -A: 全ての変更をステージング
- git commit -m"<コミットメッセージ>": ステージングされた変更をローカルにコミット(コミット履歴確認(git log))
- git push: コミットされた変更をリモートにプッシュ, [参考URL](https://qiita.com/shin4488/items/0bc36878eab39d5e499d)
  - git push <リモート> <ブランチ>: デフォルトだとリモートがorigin, ブランチが現在のブランチ
  - git push --delete <リモート> <ブランチ>: リモートのブランチを削除, [参考URL](https://qiita.com/yuu_ta/items/519ea47ac2c1ded032d9)

## Docker Desktop
- TODO

## AWS
- TODO
