package com.l9e.transaction.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

/**
 * @author meizs
 * @create 2018-04-09 15:36
 **/
@Repository
@Qualifier("accountRepository")
@SuppressWarnings("unchecked")
public interface AccountRepository /*extends CrudRepository<Account, Integer>*/ {

   /* @Override
    public Account findOne(Integer id);

    @Override
    public Account save(Account account);

    @Query("select t from Account t where t.account=:name")
    public Account findAccountByName(@Param("name") String name);*/


}
