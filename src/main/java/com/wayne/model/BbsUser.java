package com.wayne.model;


import javax.persistence.*;

/**
 *
 * @Author TAO
 * @Date 2017/3/23 18:01
 */
@Entity
@Table(name = "bbs_user")
public class BbsUser{

	@Id
	@GeneratedValue
	private Integer id ;
	@Column(name = "level")
	private Integer level ;
	@Column(name = "score")
	private Integer score ;
	@Column(name = "balance")
	private Integer balance;
	@Column(name = "password")
	private String password;
	@Column(name = "email")
	private String email ;
	@Column(name = "userName")
	private String userName ;
	@Column(name = "corp")
	private String corp;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Integer getBalance() {
		return balance;
	}
	public void setBalance(Integer balance) {
		this.balance = balance;
	}
	public String getCorp() {
		return corp;
	}
	public void setCorp(String corp) {
		this.corp = corp;
	}
	
	
}
