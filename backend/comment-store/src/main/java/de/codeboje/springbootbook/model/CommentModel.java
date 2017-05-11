package de.codeboje.springbootbook.model;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import de.codeboje.springbootbook.model.utils.UtcCalendarType;

@Entity
@Table(name = "comments_model", 
	   indexes = {
			   @Index(name = "idx_pageId", 
					  columnList = "pageId"
					 ) 
		}
)
@TypeDefs({ 
	@TypeDef(name = "calendarUTC", 
			typeClass = UtcCalendarType.class, 
			defaultForType = Calendar.class) 
	})
public class CommentModel implements Serializable {

	private static final long serialVersionUID = 8926987149780391093L;

	@Id
	@Column(length = 36)
	private String id;

	@Version
	private Integer version;

	@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "calendarUTC")
	private Calendar lastModificationDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Type(type = "calendarUTC")
	private Calendar creationDate;

	@Column(length = 32)
	private String pageId;

	@Column(length = 32)
	private String username;

	@Column(length = 32)
	private String emailAddress;

	@Column
	private boolean spam;

	public boolean isSpam() {
		return spam;
	}

	public void setSpam(boolean spam) {
		this.spam = spam;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	@Column(columnDefinition = "TEXT")
	private String comment;

	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (!(o instanceof CommentModel)) {

			return false;
		}

		CommentModel other = (CommentModel) o;

		// if the id is missing, return false
		if (id == null) {
			return false;
		}

		// equivalence by id
		return id.equals(other.getId());
	}

	public String getComment() {
		return comment;
	}

	public Calendar getCreationDate() {
		return creationDate;
	}

	public String getId() {
		return id;
	}

	public Calendar getLastModificationDate() {
		return lastModificationDate;
	}

	public String getPageId() {
		return pageId;
	}

	public String getUsername() {
		return username;
	}

	public Integer getVersion() {
		return version;
	}

	public int hashCode() {
		if (id != null) {
			return id.hashCode();
		} else {
			return super.hashCode();
		}
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public void setCreationDate(Calendar creationDate) {
		this.creationDate = creationDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLastModificationDate(Calendar lastModificationDate) {
		this.lastModificationDate = lastModificationDate;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
