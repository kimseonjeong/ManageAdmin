package com.example.admin.company.manage;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.beans.BeanUtils;

import com.example.admin.company.Company;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = "company")
@ToString(exclude = "company")
@NoArgsConstructor
@Entity
public class Manage {
	@Id
	@GeneratedValue
	private Long seq;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="COMPANY_SEQ", nullable = false)
	private Company company;
	@Column(name="SELLERKEY", nullable = false, length=4000)
	private String sellerkey;
	@Column(name="CPID", nullable=false)
	private String cpid;
	@Column(name="PRODUCTID", nullable=false)
	private String productid;
	@Column(name="DELIVERYID", nullable=true)
	private String deliveryid;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REG_DATE", nullable=false, length=4000)
	private Calendar regDate;
	@PrePersist
	void preInsert(){
		regDate = Calendar.getInstance();
	}
	//복사 생성자
	public Manage(Manage source){
		BeanUtils.copyProperties(source, this);
	}
}
