package com.example.demo_service;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo_service.etc.Person;
import com.example.demo_service.etc.PersonService;
import com.example.demo_service.shop.Customer;
import com.example.demo_service.shop.CustomerService;
import com.example.demo_service.shop.DemoRepository;
import com.example.demo_service.shop.Sale;
import com.example.demo_service.shop.SaleService;
import com.example.demo_service.shop.SaleView;

// import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.ArrayList;
// import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
// import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

// 参考: SpringBoot3プログラミング入門等
// Cookie等を含むCORSリクエストを許可するにはallowCredentialsの指定が必要
// https://spring.pleiades.io/spring-framework/reference/web/webmvc-cors.html#mvc-cors-credentialed-requests
@RestController
@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true"
/*
 * , methods = { RequestMethod.OPTIONS,
 * RequestMethod.HEAD, RequestMethod.GET,
 * RequestMethod.POST, RequestMethod.PUT,
 * RequestMethod.DELETE }
 */)
@RequiredArgsConstructor
// logというフィールド名でロガーを自動生成するLombokのアノテーション
@Slf4j
public class DemoController {
        @Autowired
        private DemoRepository demoRepository;

        // @Autowired
        // customerService customerService;

        @Autowired
        private CustomerService customerService;

        // @Autowired
        // SaleRepository saleRepository;

        @Autowired
        private SaleService saleService;

        private final PersonService personService;

        @GetMapping("/")
        public List<DemoDto> findAll() {
                // DemoDto dd = new DemoDto(3, "abc");
                // new DemoDto();
                // System.out.println(dd);
                // dd.setStr("def");
                // System.out.println(dd.getStr());
                // return demoRepository.findAll();
                // List<String> strs = new ArrayList<>(List.of("a", "b", "c"));
                // return strs;
                // return new ArrayList<DemoDto>(List.of(new DemoDto(1, "abc"), new DemoDto(2,
                // "def"), new DemoDto(3, "ghi")));
                // フィールドインジェクションだと下記のように後から上書きできてしまう
                // customerService = new CustomerService();
                return demoRepository.findAll();
        }

        @GetMapping("/customers")
        public List<Customer> listCustomers() {
                List<Customer> customers = customerService.findAll();
                // https://docs.oracle.com/javase/jp/21/docs/api/java.base/java/util/stream/Stream.html
                return customers.stream()
                                .map(customer -> Customer.builder()
                                                .id(customer.getId())
                                                .name(customer.getName())
                                                // .name(null)
                                                .sales(customer.getSales()
                                                                .stream()
                                                                .map(sale -> Sale.builder()
                                                                                .id(sale.getId())
                                                                                .price(sale.getPrice())
                                                                                .build())
                                                                .toList())
                                                .build())
                                .toList();
        }

        @GetMapping("/customer")
        public Customer findCustomer(@RequestParam("id") int id) {
                Customer customer = customerService.findById(id);
                return Customer.builder()
                                .id(customer.getId())
                                .name(customer.getName())
                                .sales(customer.getSales()
                                                .stream()
                                                .map(sale -> Sale.builder()
                                                                .id(sale.getId())
                                                                .price(sale.getPrice())
                                                                .customer(null)
                                                                .build())
                                                .toList())
                                .build();
        }

        // ステータスコード参考: https://developer.mozilla.org/ja/docs/Web/HTTP/Reference/Status
        // curl -v http://localhost:8080/customers
        // 上記でリーズンフレーズが表示されないと悩んでいたのだが仕様ならしい。
        // 参考URL: https://qiita.com/5at00001040/items/413e1bdb07bbba2cf890,
        // 参考URL: https://gihyo.jp/admin/serial/01/http3/0001
        // ---
        // Content-Type: application/json
        // {"id": null, "name": "CRUDテスト"}
        // 上記のようにダブルクォートでJSONのプロパティを囲わないとエラーが出るので注意。
        // @RequestBodyにURLエンコードしたものを渡すとデコードされないので注意。(フォームは@ModelAttribute)
        // @RequestBody: JSONに対応している
        @PostMapping("/create-customer")
        public ResponseEntity<Customer> createCustomer(@RequestBody @Validated Customer customer,
                        HttpServletResponse res)
                        throws IOException {
                // 存在する場合は400 Bad Request
                // https://spring.pleiades.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html
                // https://spring.pleiades.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html
                if (customerService.existsById(customer.getId())) {
                        // https://spring.io/projects/spring-framework
                        // https://docs.spring.io/spring-framework/reference/web.html
                        // https://docs.spring.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods/responseentity.html
                        return ResponseEntity.badRequest().body(null);
                        // res.sendError(HttpStatus.BAD_REQUEST.value(),
                        // HttpStatus.BAD_REQUEST.getReasonPhrase());
                        // return null;
                }
                // idが-1のままで登録しようとすると"別のトランザクションで更新中"というようなエラーが出る
                if (customer.getId() == -1) {
                        customer.setId(null);
                }
                // IDを格納したエンティティを返す
                // ～デシリアライズ, JSONプロパティの不足～
                // デシリアライズ時にクラスに存在する属性がJSONに存在しない場合はデフォルト値が設定される
                // オブジェクトならnull, プリミティブだとintなら0といった対応するデフォルト値
                // ～デシリアライズ, JSONプロパティの過多～
                // @RequestBodyで余計なプロパティが無視されているのだろう
                System.out.println("req: " + customer);
                Customer ret = customerService.createCustomer(customer);
                // JpaRepositoryのsaveAndFlushはIDを設定したエンティティを返す
                System.out.println("res: " + ret);
                // 201 Created
                return ResponseEntity.created(URI.create("http://localhost:3000/shop")).body(ret);
        }

        // Content-Type: application/x-www-form-urlencoded
        // name=CRUD%20%E6%9B%B4%E6%96%B0%E3%83%86%E3%82%B9%E3%83%88&sales=
        // nameはJSのencodeURI("CRUD 更新テスト");
        // https://developer.mozilla.org/ja/docs/Web/JavaScript/Reference/Global_Objects/encodeURI
        // prop=と指定があれば空の配列等, 指定がなければnull
        // sales=&name=CRUD%20%E6%9B%B4%E6%96%B0%E3%83%86%E3%82%B9%E3%83%88&id=3
        // @ModelAttributeはslugにも対応している
        // ただし、フォームのデータの方が優先される
        // @MethodMappingにslugが存在する場合はないとエラーになる
        // 意図的にreturnしたNot Found等はボディを返さないが、Spring側のものはボディにタイムスタンプ等が含まれる
        // ---
        // http://localhost:8080/update-customer/3?name=テスト3&販売
        // name=テスト4&販売=
        // コンマ区切りで結合される
        // @ModelAttribute: クエリストリングとフォームとslugに対応している
        // ---
        @PutMapping("/update-customer/{id}")
        public ResponseEntity<Customer> updateCustomer(
                        // @RequestParam(name="customer") Customer customer
                        // @ModelAttribute Customer customer
                        @RequestBody @Validated Customer customer) {
                System.out.println("req: " + customer);
                // 存在しないなら404 Not Found
                if (!customerService.existsById(customer.getId())) {
                        return ResponseEntity.notFound().build();
                }
                // 更新後のエンティティを返す(200 OK)
                // 関連のあるエンティティを渡しても関連自体は更新されない(Salesは[]でもそのまま)
                // 関連も含めて渡して更新したい場合はバックエンド側でSalesの更新メソッドも別途呼び出す必要がある
                Customer ret = customerService.updateCustomer(customer);
                // ビューを返す代わり, 対応する販売情報が存在すると無限ループする
                ret.setSales(null);
                System.out.println("res: " + ret);
                return ResponseEntity.ok().body(ret);
        }

        // @RequestParam: クエリストリングとフォームに対応している
        // @PathVariable: slugに対応している
        // ---
        // @PutMapping("update-customer/{id}")
        // public Customer updateCustomer(@PathVariable(name = "id") int id,
        // @RequestParam(name = "name") String name,
        // @RequestParam(name = "販売") List<Sale> sales) {
        // System.out.println(String.format("req: {id: %d, name: %s, 販売: %s}", id, name,
        // sales));
        // Customer customer =
        // Customer.builder().id(id).name(name).sales(sales).build();
        // customerService.updateCustomer(customer);
        // System.out.println("res" + customer);
        // return customer;
        // }

        // @PathVariableもnameなしだとName for argument of type [xxx]とエラーが出る
        @DeleteMapping("/delete-customer/{id}")
        public ResponseEntity<Customer> deleteCustomer(@PathVariable(name = "id") int id) {
                // 存在しない場合は404 Not Found
                if (!customerService.existsById(id)) {
                        return ResponseEntity.notFound().build();
                }
                // 基本的にDELETEメソッドのレスポンスはボディを持たないようだ
                customerService.deleteCustomer(id);
                // 204 No Content
                return ResponseEntity.noContent().build();
        }

        // クエリストリングはブラウザ, フォームはform要素でURLエンコードされていそう
        @GetMapping("/customer-by-name")
        public List<Customer> getMethodName(@RequestParam(name = "name") String name) {
                return customerService.findByName(name);
        }

        @GetMapping("/sales")
        public Sale findSales(@RequestParam("id") int id) {
                Sale sale = saleService.findById(id);
                return Sale.builder()
                                .id(sale.getId())
                                .price(sale.getPrice())
                                .customer(Customer.builder()
                                                .id(sale.getCustomer().getId())
                                                .name(sale.getCustomer().getName())
                                                .build())
                                .build();
        }

        @GetMapping("/sales-view/{sales-view-id}")
        public SaleView findSalesView(@PathVariable("sales-view-id") int id) {
                Sale sale = saleService.findById(id);
                return SaleView.builder()
                                .id(sale.getId())
                                .price(sale.getPrice())
                                .customerName(sale.getCustomer().getName())
                                .build();
        }

        @GetMapping("/test")
        public ModelAndView testGet(ModelAndView mav,
                        @RequestParam(value = "testFilePath", required = false) String testFilePath) {
                System.out.println(testFilePath);
                mav.setViewName("test");
                mav.addObject("msg", "ファイルをアップロード");
                mav.addObject("testFilePath", testFilePath);
                return mav;
        }

        // https://spring.pleiades.io/spring-framework/docs/current/javadoc-api/org/springframework/web/multipart/MultipartFile.html
        @PostMapping("/test")
        public ModelAndView testPost(@RequestParam("test-file") MultipartFile testFile, ModelAndView mav)
                        throws IOException, InterruptedException {
                // String msg = "テストファイル送信成功";
                String fileName = testFile.getOriginalFilename();
                System.out.println(fileName);
                // String suffix = fileName == null ? "" :
                // fileName.substring(fileName.lastIndexOf("."));
                // SpringBootプロジェクトルートからの相対パスになる(このファイルからだとdemo-service)
                // https://docs.oracle.com/javase/jp/8/docs/api/java/nio/file/Paths.html
                testFile.transferTo(
                                // Paths.get("src/main/resources/static/test-file" + suffix)
                                Paths.get("src/main/resources/static/" + fileName));
                System.out.println(testFile);
                // return testGet(mav, fileName);
                mav.setViewName("redirect:/test?testFilePath=" + URLEncoder.encode(fileName, "utf-8"));
                // ちょっと待たないとファイルの保存がサーバに反映されない
                Thread.sleep(3000);
                return mav;
        }

        // curl http://localhost:8080/test-path/test-view -v -u guest:qwerty
        // ブラウザのDevツールはリーズンフレーズなくても補って表示している
        @GetMapping(path = "/test-path/test-view")
        public ModelAndView testView(@RequestParam(name = "msg", required = false) String msg,
                        // https://spring.pleiades.io/spring-framework/reference/web/webmvc/mvc-controller/ann-methods/requestheader.html
                        @RequestHeader("Accept-Language") String acceptLanguage,
                        @RequestParam(name = "num", required = false) int num) throws Exception {
                // @RestControllerだと文字列がそのまま返る
                // return "test-dir/test-view"
                // ServletResponseのsetHeader()がうまく動かない
                ModelAndView mav = new ModelAndView("test-dir/test", HttpStatusCode.valueOf(222));
                mav.addObject("isEven", num % 2 == 0);
                mav.addObject("arr", List.of(1, 2, 3, 99));
                mav.addObject("format", String.class.getMethod("format", String.class, Object[].class));
                return mav;
        }

        /*
         * BASIC認証
         * 注意: これ自体は暗号化でないのでJSのatob()でクレデンシャルを復元できる
         * 1.クライアントはAuthorizationヘッダーで認証方式とBASE64エンコードした"ユーザー名:パスワード"を送信
         * 2.サーバはログイン成功時にSet-CookieヘッダーでセッションIDを送信
         * 3.クライアントは以降のリクエストで毎回CookieヘッダーでセッションIDを送信、サーバが検証
         */
        // バックエンド側にログインページがある場合
        // @GetMapping("/login-page")
        // public ModelAndView getLoginPage(@RequestParam(name = "logout", required =
        // false) String logout,
        // @RequestParam(name = "error", required = false) String error,
        // ModelAndView mav) {
        // String msg = logout != null ? "ログアウトしました" : error != null ?
        // "ユーザー名かパスワードが誤っています" : "";
        // mav.addObject("msg", msg);
        // mav.setViewName("login-page");
        // return mav;
        // }

        // フロントエンド側にログインページがある場合
        @GetMapping("/login-page")
        public ResponseEntity<String> getLoginPage(@RequestParam(name = "logout", required = false) String logout,
                        @RequestParam(name = "error", required = false) String error) {
                String msg = logout != null ? "ログアウトしました" : error != null ? "ユーザー名かパスワードが誤っています" : "ログインに成功しました";

                // クライアントエラー:
                // https://developer.mozilla.org/ja/docs/Web/HTTP/Reference/Status#%E3%82%AF%E3%83%A9%E3%82%A4%E3%82%A2%E3%83%B3%E3%83%88%E3%82%A8%E3%83%A9%E3%83%BC%E3%83%AC%E3%82%B9%E3%83%9D%E3%83%B3%E3%82%B9
                // 401 Unauthorized: 未認証(認証失敗), 例えばログイン時にユーザー名かパスワードが誤っている
                // 403 Forbidden: 権限なし(認可失敗), 例えばユーザー権限で管理者権限が必要なページにアクセスしようとした
                if (error != null) {
                        // 401 Unauthorizedにするとブラウザがログインダイアログを表示してしまうので403 Forbiddenを返す
                        // HttpStatus:
                        // https://spring.pleiades.io/spring-framework/docs/current/javadoc-api/org/springframework/http/HttpStatus.html
                        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                                        // .header("WWW-Authenticate", "Basic realm=\"Realm\"")
                                        // ResponseEntityは中身がStringだとContent-Type: text/plain;charset=UTF-8になる
                                        .body(msg);
                }

                // 注意: ログインページがボディとして優先されるのでmsgが入らない
                return ResponseEntity.ok().body(msg);
        }

        // バリデーション検証(GET), この時点ではPersonが未設定なので@Validatedにしない
        @RequestMapping("/create-person")
        public ModelAndView getCreatePerson(@ModelAttribute("person") Person person, ModelAndView mav) {
                mav.addObject("persons", personService.list());
                mav.setViewName("create-person");
                return mav;
        }

        // バリデーション検証(POST), ここで@Validatedにして実際に検証する
        @RequestMapping(path = "/create-person", method = RequestMethod.POST)
        public ModelAndView postCreatePerson(@ModelAttribute("person") @Validated Person person,
                        // https://docs.spring.io/spring-framework/reference/core/validation.html
                        // ModelAndViewを先に書くとそこでエラーレスポンスが返ってしまうので注意
                        BindingResult bindingResult, ModelAndView mav) {
                if (bindingResult.hasErrors()) {
                        System.out.println("バリデーション検証します");
                        List<ObjectError> objectErrors = new ArrayList<ObjectError>(bindingResult.getErrorCount());
                        // Slf4jを使うとタイムスタンプとログ出力しているクラスの情報を出力できる
                        log.info("バリデーションエラー数: {}", bindingResult.getErrorCount());
                        // グローバルエラーとフィールドエラーがあり、getFieldErros()でもフィールドの全てのエラーを取得できる
                        for (ObjectError objectError : bindingResult.getFieldErrors()) {
                                // for (ObjectError objectError : bindingResult.getAllErrors()) {
                                objectErrors.add(objectError);
                        }
                        // そのまま表示するとデフォルトメッセージだけでなくコード等も含むかたちで出てくる
                        // バリデーションエラーの順番はランダム
                        // 注意: バックエンド側でエラーメッセージの{0}や{1}を解決できない、これは使わない方が良さそう
                        log.info("バリデーションエラーの内容: {}", objectErrors
                                        .stream()
                                        .map(objectError -> objectError.getDefaultMessage())
                                        .toList());
                        System.out.println();
                        return getCreatePerson(person, mav);
                }
                personService.save(person);
                return getCreatePerson(person, mav);
        }

        // RESTバリデーション検証(GET)
        @GetMapping("/rest-list-person")
        public List<Person> getRestListPerson() {
                return personService.list();
        }

        // APIテスト: CookieにJSESSIONID, Content-Type: application/json, bodyはdev-tool
        // RESTバリデーション検証(POST), ここで@Validatedにして実際に検証する
        @PostMapping("/rest-create-person")
        public ResponseEntity<List<String>> postRestCreatePerson(@RequestBody @Validated Person person,
                        BindingResult bindingResult) {

                // バリデーションエラーがあったらエラーメッセージを送信する
                if (bindingResult.hasErrors()) {
                        return ResponseEntity.badRequest().body(bindingResult
                                        .getFieldErrors()
                                        .stream()
                                        .map(fieldError -> fieldError.getDefaultMessage())
                                        .toList());
                }

                // バリデーションエラーがなければ作成
                personService.save(person);
                return ResponseEntity
                                .created(URI.create("http://localhost:3000/person"))
                                .body(List.of("個人情報の作成完了"));
        }

}
