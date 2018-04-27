package com.flt.bl.dao;

import com.flt.bl.vo.Employee;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Component;

/**
 * @author Administrator
 * @date 2018/4/18 19:48
 */
@Component
public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {

    Employee queryEmployeeById(String id);

}
