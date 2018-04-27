package com.l9e.transaction.service.impl;

import com.l9e.transaction.aop.DS;
import com.l9e.transaction.aop.DatabaseType;
import com.l9e.transaction.bean.Account;
import com.l9e.transaction.mapper.AccountMapper;
import com.l9e.transaction.service.AccountService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author meizs
 * @create 2018-04-04 14:01
 **/
@Service("accountService")
public class AccountServiceImpl implements AccountService {

    //  @Autowired
    private AccountMapper accountMapper;




  /*  @Autowired
    private AccountRepository accountRepository;*/


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    @DS(DatabaseType.slaveDatasource)
    @Override
    public int addAccout(Account account) throws Exception {

        System.out.println(account);

        for (int i = 0; i < 5; i++) {
            // accountMapper2.insertSelective(account);
            //     accountMapper.insertSelective(account);
            //   Thread.sleep(2000);
        }
        //throw new Exception("msg");
        return 0;

    }

   /* @DS(DatabaseType.masterDatasource)
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class, isolation = Isolation.DEFAULT)
    @Override
    public int insertAccout(Account account) throws Exception {

        for (int i = 0; i < 5; i++) {
            accountRepository.save(account);
            System.out.println("i::" + i + account);
            //Thread.sleep(6000);
        }
        //  throw new Exception("msg");
        return 0;

    }*/

   /* @DS(DatabaseType.slaveDatasource)
    @Override
    public Account getAccountById(Integer id) {
        Account account = accountRepository.findOne(id);
        System.out.println("id: " + id);
        System.out.println("account: " + account);
        return account;
    }*/


}
