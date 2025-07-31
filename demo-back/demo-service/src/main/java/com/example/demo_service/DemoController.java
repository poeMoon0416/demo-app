package com.example.demo_service;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo_service.shop.Customer;
import com.example.demo_service.shop.CustomerService;
import com.example.demo_service.shop.DemoRepository;
import com.example.demo_service.shop.Sale;
import com.example.demo_service.shop.SaleService;
import com.example.demo_service.shop.SaleView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.file.Paths;
// import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
// import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.bind.annotation.PutMapping;

// 参考: SpringBoot3プログラミング入門等
@RestController
@CrossOrigin(origins = "http://localhost:3000")
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
                        @ModelAttribute Customer customer) {
                System.out.println("req: " + customer);
                // 存在しないなら404 Not Found
                if (!customerService.existsById(customer.getId())) {
                        return ResponseEntity.notFound().build();
                }
                // 更新後のエンティティを返す(200 OK)
                Customer ret = customerService.updateCustomer(customer);
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
                        @RequestHeader("Accept-Language") String acceptLanguage) {
                // @RestControllerだと文字列がそのまま返る
                // return "test-dir/test-view"
                // ServletResponseのsetHeader()がうまく動かない
                return new ModelAndView("test-dir/test", HttpStatusCode.valueOf(222));
        }
}
