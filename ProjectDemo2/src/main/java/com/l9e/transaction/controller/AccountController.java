package com.l9e.transaction.controller;

import com.l9e.transaction.bean.Account;
import com.l9e.transaction.service.AccountService;
import com.l9e.transaction.service.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author meizs
 * @create 2018-04-04 11:40
 **/
@RestController
//@Controller
@RequestMapping("/account")
public class AccountController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private RedisService redisService;

   /* @Autowired
    private ArticleSearchRepository articleSearchRepository;*/

    @ResponseBody
    @RequestMapping(value = "/add.do")
    public String addAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Account account = new Account();
        account.setAccount(request.getParameter("account"));
        account.setPwd(request.getParameter("pwd"));
        account.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS").parse(request.getParameter("createTime")));
        logger.info(account.toString());

        try {
            accountService.addAccout(account);
            //    accountService.insertAccout(account);
        } catch (Exception e) {
            logger.info("", e);
            throw e;
        }

        return "SUCCESS";
    }

    /*@RequestMapping("/get/id/{id}")
    @ResponseBody
    public String getUserById(@PathVariable("id") String id) {
        logger.info("request /user/get/id/{id}, parameter is " + id);
        Account account = accountService.getAccountById(Integer.valueOf(id));
        return JSONObject.toJSONString(account);
    }
*/
/*
    @RequestMapping(value = "/getAccountById.do")
    public String getAccountById(HttpServletRequest request, HttpServletResponse response, @RequestParam String id, Account account_1) {
        logger.info("account_1::" + account_1);
        logger.info("id_1::" + id);
        String ids = request.getParameter("id");
        logger.info(ids);
        try {
            Account account = accountService.getAccountById(Integer.valueOf(ids));
            logger.info(account.toString());
        } catch (Exception e) {
            logger.info("", e);
        }

        return "SUCCESS";
    }*/


    @RequestMapping(value = "/index.html")
    public String index(Model model) {
        Account single = new Account();
        List<Account> people = new ArrayList<Account>();
        Account p1 = new Account();
        Account p2 = new Account();
        Account p3 = new Account();
        people.add(p1);
        people.add(p2);
        people.add(p3);
        model.addAttribute("singlePerson", single);
        model.addAttribute("people", people);
        return "index";
    }


    @RequestMapping(value = "/redis/setget")
    public String redisSet(@RequestParam("value") String value) {
        logger.info(value);
        boolean isOk = redisService.set("name", value);
        logger.info("isOK{}", isOk);
        String values = redisService.get("name");
        logger.info(values);
        return values;
    }


   /* @RequestMapping(value = "/es/add.do")
    public void testSaveArticleIndex() {
        Author author = new Author();
        author.setId(1L);
        author.setName("tianshouzhi");
        author.setRemark("java developer");

        Tutorial tutorial = new Tutorial();
        tutorial.setId(1L);
        tutorial.setName("elastic search");

        Article article = new Article();
        article.setId(1L);
        article.setTitle("springboot integreate elasticsearch");
        article.setAbstracts("springboot integreate elasticsearch is very easy");
        article.setTutorial(tutorial);
        article.setAuthor(author);
        article.setContent("elasticsearch based on lucene,"
                + "spring-data-elastichsearch based on elaticsearch"
                + ",this tutorial tell you how to integrete springboot with spring-data-elasticsearch");
        article.setPostTime(new Date());
        article.setClickCount(1L);

        articleSearchRepository.save(article);
    }*/
/*
    @RequestMapping(value = "/es/query.do")
    public void testSearch() {
        String queryString = "springboot";//搜索关键字
        QueryStringQueryBuilder builder = new QueryStringQueryBuilder(queryString);
        Iterable<Article> searchResult = articleSearchRepository.search(builder);
        Iterator<Article> iterator = searchResult.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }*/




}
