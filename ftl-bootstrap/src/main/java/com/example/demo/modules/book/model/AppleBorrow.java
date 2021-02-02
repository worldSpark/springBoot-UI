package com.example.demo.modules.book.model;

import com.example.demo.core.entity.BaseEntity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "apple_borrow")
public class AppleBorrow extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 图书ID
     */
    @Column(name = "book_id")
    private Integer bookId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 借阅时间
     */
    @Column(name = "borrow_time")
    private Date borrowTime;

    /**
     * 实际归还时间
     */
    @Column(name = "return_time")
    private Date returnTime;

    /**
     * 计划归还时间
     */
    @Column(name = "depart_return_time")
    private Date departReturnTime;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作时间
     */
    @Column(name = "operator_time")
    private Date operatorTime;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取图书ID
     *
     * @return book_id - 图书ID
     */
    public Integer getBookId() {
        return bookId;
    }

    /**
     * 设置图书ID
     *
     * @param bookId 图书ID
     */
    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 获取借阅时间
     *
     * @return borrow_time - 借阅时间
     */
    public Date getBorrowTime() {
        return borrowTime;
    }

    /**
     * 设置借阅时间
     *
     * @param borrowTime 借阅时间
     */
    public void setBorrowTime(Date borrowTime) {
        this.borrowTime = borrowTime;
    }

    /**
     * 获取实际归还时间
     *
     * @return return_time - 实际归还时间
     */
    public Date getReturnTime() {
        return returnTime;
    }

    /**
     * 设置实际归还时间
     *
     * @param returnTime 实际归还时间
     */
    public void setReturnTime(Date returnTime) {
        this.returnTime = returnTime;
    }

    /**
     * 获取计划归还时间
     *
     * @return depart_return_time - 计划归还时间
     */
    public Date getDepartReturnTime() {
        return departReturnTime;
    }

    /**
     * 设置计划归还时间
     *
     * @param departReturnTime 计划归还时间
     */
    public void setDepartReturnTime(Date departReturnTime) {
        this.departReturnTime = departReturnTime;
    }

    /**
     * 获取操作人
     *
     * @return operator - 操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 设置操作人
     *
     * @param operator 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 获取操作时间
     *
     * @return operator_time - 操作时间
     */
    public Date getOperatorTime() {
        return operatorTime;
    }

    /**
     * 设置操作时间
     *
     * @param operatorTime 操作时间
     */
    public void setOperatorTime(Date operatorTime) {
        this.operatorTime = operatorTime;
    }
}