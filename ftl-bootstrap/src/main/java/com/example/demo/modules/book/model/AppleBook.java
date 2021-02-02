package com.example.demo.modules.book.model;

import com.example.demo.core.entity.BaseEntity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.*;

@Table(name = "apple_book")
public class AppleBook extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 图书编号
     */
    @Column(name = "book_code")
    private String bookCode;

    /**
     * 图书名称
     */
    @Column(name = "book_name")
    private String bookName;

    /**
     * 作者
     */
    private String author;

    /**
     * 出版社
     */
    private String publish;

    /**
     * 图书类型
     */
    @Column(name = "book_type")
    private String bookType;

    /**
     * 语种
     */
    @Column(name = "language_classification")
    private String languageClassification;

    /**
     * 所属行业
     */
    private String industry;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 出版日期
     */
    @Column(name = "publish_time")
    private Date publishTime;

    /**
     * 标准ISBN
     */
    @Column(name = "ISBN")
    private String isbn;

    /**
     * 借阅状态
     */
    private String state;

    /**
     * 封面图片
     */
    private String img;

    /**
     * 内容简介
     */
    private String content;

    /**
     * 目录
     */
    private String catalog;

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
     * 获取图书编号
     *
     * @return book_code - 图书编号
     */
    public String getBookCode() {
        return bookCode;
    }

    /**
     * 设置图书编号
     *
     * @param bookCode 图书编号
     */
    public void setBookCode(String bookCode) {
        this.bookCode = bookCode;
    }

    /**
     * 获取图书名称
     *
     * @return book_name - 图书名称
     */
    public String getBookName() {
        return bookName;
    }

    /**
     * 设置图书名称
     *
     * @param bookName 图书名称
     */
    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    /**
     * 获取作者
     *
     * @return author - 作者
     */
    public String getAuthor() {
        return author;
    }

    /**
     * 设置作者
     *
     * @param author 作者
     */
    public void setAuthor(String author) {
        this.author = author;
    }

    /**
     * 获取出版社
     *
     * @return publish - 出版社
     */
    public String getPublish() {
        return publish;
    }

    /**
     * 设置出版社
     *
     * @param publish 出版社
     */
    public void setPublish(String publish) {
        this.publish = publish;
    }

    /**
     * 获取图书类型
     *
     * @return book_type - 图书类型
     */
    public String getBookType() {
        return bookType;
    }

    /**
     * 设置图书类型
     *
     * @param bookType 图书类型
     */
    public void setBookType(String bookType) {
        this.bookType = bookType;
    }

    /**
     * 获取语种
     *
     * @return language_classification - 语种
     */
    public String getLanguageClassification() {
        return languageClassification;
    }

    /**
     * 设置语种
     *
     * @param languageClassification 语种
     */
    public void setLanguageClassification(String languageClassification) {
        this.languageClassification = languageClassification;
    }

    /**
     * 获取所属行业
     *
     * @return industry - 所属行业
     */
    public String getIndustry() {
        return industry;
    }

    /**
     * 设置所属行业
     *
     * @param industry 所属行业
     */
    public void setIndustry(String industry) {
        this.industry = industry;
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    /**
     * 获取出版日期
     *
     * @return publish_time - 出版日期
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置出版日期
     *
     * @param publishTime 出版日期
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }

    /**
     * 获取标准ISBN
     *
     * @return ISBN - 标准ISBN
     */
    public String getIsbn() {
        return isbn;
    }

    /**
     * 设置标准ISBN
     *
     * @param isbn 标准ISBN
     */
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    /**
     * 获取借阅状态
     *
     * @return state - 借阅状态
     */
    public String getState() {
        return state;
    }

    /**
     * 设置借阅状态
     *
     * @param state 借阅状态
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * 获取封面图片
     *
     * @return img - 封面图片
     */
    public String getImg() {
        return img;
    }

    /**
     * 设置封面图片
     *
     * @param img 封面图片
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取内容简介
     *
     * @return content - 内容简介
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容简介
     *
     * @param content 内容简介
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取目录
     *
     * @return catalog - 目录
     */
    public String getCatalog() {
        return catalog;
    }

    /**
     * 设置目录
     *
     * @param catalog 目录
     */
    public void setCatalog(String catalog) {
        this.catalog = catalog;
    }
}