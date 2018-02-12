package com.example.admin.company;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.admin.company.manage.Manage;
import com.example.admin.company.memo.Memo;
import com.example.admin.company.url.Url;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(exclude = {"url", "memo","manage"})
@ToString(exclude = {"url", "memo","manage"})
@Entity
public class Company {

	@Id
	@GeneratedValue
	private Long seq;
	@NotEmpty
	private String companyName;
	@NotEmpty
	private String serviceName;
	@NotEmpty
	private String serviceStatus;
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="REG_DATE", nullable = false, updatable = false)
	private Calendar regDate;
	@PrePersist
	void preInsert(){
		this.regDate = Calendar.getInstance();
	}
	@Lob
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar companyOpenDate;
	private String tax;
	private String serviceMethod;
	private String payMode;
	private String paycoKinds;
	private String language;
	private String managerMain;
	private String managerVice;
	private String managerNE;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	private List<Url> url;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	private List<Manage> manage;
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "company")
	private List<Memo> memo;
}
