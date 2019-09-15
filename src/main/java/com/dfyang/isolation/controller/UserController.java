package com.dfyang.isolation.controller;

import com.dfyang.isolation.util.ConnectionFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@RestController
public class UserController {

    /**
     * 读未提交事务1
     * @return
     * @throws Exception
     */
    @GetMapping("/transaction1")
    public String readUncommitted1() throws Exception {
        Connection connection = startTransaction(Connection.TRANSACTION_READ_COMMITTED);
        try {
            ResultSet resultSet = executeQuery(connection, "select id,name from t_user where id = 1");
            System.out.println("[事务1]查询当前id为1的name：" + resultSet.getString("name"));
            System.out.println("[事务1]等待2s");
            Thread.sleep(2000);
            executeUpdate(connection, "update t_user set name = '长大后的张三' where id = 1");
            System.out.println("[事务1]将id为1的name为张三修改为长大后的张三，等待10s");
            Thread.sleep(10000);
            connection.commit();
            System.out.println("[事务1]提交事务");
        } catch (Exception e) {
            connection.rollback();
        } finally {
            connection.close();
        }
        return "成功";
    }

    /**
     * 读未提交事务2
     * @return
     * @throws Exception
     */
    @GetMapping("/transaction2")
    public String readUncommitted2() throws Exception {
        Connection connection = startTransaction(Connection.TRANSACTION_READ_UNCOMMITTED);
        try {
            ResultSet resultSet = executeQuery(connection, "select id,name from t_user where id = 1");
            System.out.println("[事务2]查询当前id为1的name：" + resultSet.getString("name"));
            System.out.println("[事务2]等待5s");
            Thread.sleep(5000);
            resultSet = executeQuery(connection, "select id,name from t_user where id = 1");
            System.out.println("[事务2]等待3s后查询到的结果：" + resultSet.getString("name"));
            connection.commit();
            System.out.println("[事务2]提交事务");
        } catch (Exception e) {
            connection.rollback();
        } finally {
            connection.close();
        }
        return "成功";
    }

    public Connection startTransaction(int isolation) throws Exception {
        Connection connection = ConnectionFactory.getConnection();
        connection.setTransactionIsolation(isolation);
        connection.setAutoCommit(false);
        return connection;
    }

    public void executeUpdate(Connection connection, String sql) throws Exception {
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.executeUpdate();
    }

    public ResultSet executeQuery(Connection connection, String sql) throws Exception {
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        resultSet.next();
        return resultSet;
    }
}
