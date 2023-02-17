package com.tanbenwang;

import javax.naming.OperationNotSupportedException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 计算器
 *
 * @author tanbenwang
 */
public class Calculator {

    /**
     * 保留小数点位数
     */
    private static final Integer SCALE = 2;
    /**
     * 计算节点map id自增 node对应一次计算过程
     */
    private static Map<Integer, Node> nodeMap = new HashMap<>();

    /**
     * 自增id 与计node.id对应
     */
    public static AtomicInteger id = new AtomicInteger(0);

    /**
     * 最新结果
     */
    private static BigDecimal result;

    /**
     * 操作次数
     */
    private static Integer count = 0;

    /**
     * 回撤操作
     */
    public static void redo() {
        if (count <= 0) {
            count = 1;
        }
        Node redoNode = nodeMap.get(count);
        if (null == redoNode) {
            System.out.println("不能进行redo");
            return;
        }
        BigDecimal oldResult = result;
        result = redoNode.getValue();
        count++;
        if (count > nodeMap.size()) {
            count = nodeMap.size();
        }
        System.out.println("redo操作，撤回的值：" + oldResult + "，最新结果：" + result);
    }

    /**
     * 回滚计算节点
     */
    public static void undo() {

        if (count <= 0) {
            System.out.println("不能再undo");
            return;
        }
        count--;
        BigDecimal preResult = result;
        if (count == 0) {
            result = BigDecimal.ZERO;
        } else {
            Node redoNode = nodeMap.get(count);
            result = redoNode.getValue();
            if (count < 0) {
                count = 0;
            }
        }

        System.out.println("undo操作，上一步的结果：" + preResult + "，最新结果：" + result);
    }

    /**
     * 开始计算
     *
     * @param node 计算节点
     */
    public static void calculator(Node node) throws OperationNotSupportedException {
        if (result != null) {
            node.setNum1(result);
        }
        node.setId(id.incrementAndGet());
        switch (node.getOperator()) {
            case "+":
                node.setValue(node.getNum1().add(node.getNum2()));
                break;
            case "-":
                node.setValue(node.getNum1().subtract(node.getNum2()).setScale(SCALE, RoundingMode.HALF_UP));
                break;
            case "*":
                node.setValue(node.getNum1().multiply(node.getNum2()).setScale(SCALE, RoundingMode.HALF_UP));
                break;
            case "/":
                node.setValue(node.getNum1().divide(node.getNum2().setScale(SCALE, RoundingMode.HALF_UP)));
                break;
            default:
                throw new OperationNotSupportedException("非法操作类型！");
        }
        nodeMap.put(node.getId(), new Node(node.getId(), node.getOperator(), node.getNum1(), node.getNum2(), node.getValue()));
        count++;
        result = node.getValue();
        System.out.println(node);
    }

    /**
     * 程序入口
     *
     * @param args
     */
    public static void main(String[] args) {
        Node node = new Node();
        BigDecimal num1 = new BigDecimal(1);
        BigDecimal num2 = new BigDecimal(2);
        BigDecimal num3 = new BigDecimal(3);
        BigDecimal num4 = new BigDecimal(4);
        node.setNum1(num1);
        try {
            node.setOperator("+");
            node.setNum2(num2);
            calculator(node);
            node.setNum2(num4);
            node.setOperator("*");
            calculator(node);
            undo();
            undo();
            undo();
            redo();
            redo();
        } catch (Exception exception) {
            System.out.println("捕获异常：" + exception);
        }

    }


}
