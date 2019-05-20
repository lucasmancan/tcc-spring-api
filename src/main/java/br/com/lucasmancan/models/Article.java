package br.com.lucasmancan.models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table(name="articles")
@EqualsAndHashCode(callSuper=false, exclude = {"account", "creationAppUser", "category"})
@ToString(callSuper=false, exclude = {"account", "creationAppUser", "category"})
@AllArgsConstructor
@NoArgsConstructor
public class Article {

	@Id
	@GeneratedValue( strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="creation_user_id")
	private AppUser creationAppUser;

	@Column(name="title")
	private String title;
	
	@Column(name="subtitle")
	private String subtitle;
	
	@Column(name="content")
	private String content;
		
	@Column(name="image")
	private String image;
	
  	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn( name ="category_id")
	private ArticleCategory category;  
    
	@Column(name="created_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdAt;
	
	@Column(name="updated_at")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedAt;

}
