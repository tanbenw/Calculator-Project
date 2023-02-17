package com.tanbenwang;


import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * 计算节点
 *
 * @author tanbenwang
 */
public class Node {
    private Integer id;

    /**
     * 操作符：+ - * /
     */

    private String operator;

    /**
     * 第一个数
     */
    private BigDecimal num1;

    /**
     * 第二个数
     */
    private BigDecimal num2;


    /**
     * 节点计算结果
     */
    private BigDecimal value;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }


    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public BigDecimal getNum1() {
        return num1;
    }

    public void setNum1(BigDecimal num1) {
        this.num1 = num1;
    }

    public BigDecimal getNum2() {
        return num2;
    }

    public void setNum2(BigDecimal num2) {
        this.num2 = num2;
    }


    public Node() {

    }

    public Node(Integer id, String operator, BigDecimal num1, BigDecimal num2, BigDecimal value) {
        this.id = id;
        this.operator = operator;
        this.num1 = num1;
        this.num2 = num2;
        this.value = value;
    }

    @Override
    public String toString() {


        return num1 + " " + operator + " " + num2 + " = " + value;
    }
}