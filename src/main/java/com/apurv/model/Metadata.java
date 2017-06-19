package com.apurv.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.Arrays;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table
@NamedQueries({
	   @NamedQuery(name="Metadata.findAll",query="SELECT e FROM Metadata e ORDER BY e.title ASC")}) 
public class Metadata implements Serializable {
	@Id
	@GenericGenerator(name="customUUID", strategy="uuid2")
	@GeneratedValue(generator="customUUID")
    private String FileId;
	private String extraField;
    private String files;
    public Metadata(){}
    
    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
    }
    
    public String getFileId() {
		return FileId;
	}
    
	public void setFileId(String fileId) {
		FileId = fileId;
	}
    public String getFiles() {
        return files;
    }

    public void setFiles(String files) {
        this.files = files;
    }

    
}
