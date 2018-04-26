package com.l9e.transaction.bean;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

//@Entity
//@Table(name = "account")
public class Account {
    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //  @Column(name = "account")
    private String account;
    //  @Column(name = "pwd")
    private String pwd;
    //  @Column(name = "idname")
    private String idname;
    // @Column(name = "idcard")
    private String idcard;
    //  @Column(name = "phone")
    private String phone;
    //  @Column(name = "channel")
    private String channel;
    // @Column(name = "status")
    private Byte status;
    //   @Column(name = "mail")
    private String mail;
    //  @Column(name = "mailPass")
    private String mailPass;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    //  @Column(name = "createTime")
    private Date createTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss:SSS")
    //  @Column(name = "updateTime")
    private Date updateTime;

    public Account() {
       
    }

    public Account(String account, String pwd) {
        this.account = account;
        this.pwd = pwd;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account == null ? null : account.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public String getIdname() {
        return idname;
    }

    public void setIdname(String idname) {
        this.idname = idname == null ? null : idname.trim();
    }

    public String getIdcard() {
        return idcard;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard == null ? null : idcard.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel == null ? null : channel.trim();
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail == null ? null : mail.trim();
    }

    public String getMailPass() {
        return mailPass;
    }

    public void setMailPass(String mailPass) {
        this.mailPass = mailPass == null ? null : mailPass.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", pwd='" + pwd + '\'' +
                ", idname='" + idname + '\'' +
                ", idcard='" + idcard + '\'' +
                ", phone='" + phone + '\'' +
                ", channel='" + channel + '\'' +
                ", status=" + status +
                ", mail='" + mail + '\'' +
                ", mailPass='" + mailPass + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}