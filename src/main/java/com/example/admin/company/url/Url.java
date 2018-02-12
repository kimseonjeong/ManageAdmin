package com.example.admin.company.url;

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
public class Url {
	@Id
	@GeneratedValue
	private Long seq;
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name="COMPANY_SEQ", nullable = false)
	private Company company;
	@Column(name="URL", nullable = false, length=4000)
	private String url;
	@Column(name="MODE", nullable=false)
	private String mode;
	@Column(name="IFRAME", nullable=false)
	private String iFrame;
	@Column(name="TESTID")
	private String testID;
	@Column(name="TESTPW")
	private String testPW;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REG_DATE", nullable=false, length=4000)
	private Calendar regDate;
	@PrePersist
	void preInsert(){
		regDate = Calendar.getInstance();
	}
	//복사 생성자
	public Url(Url source){
		BeanUtils.copyProperties(source, this);
	}
}
