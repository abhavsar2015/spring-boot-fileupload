package com.apurv.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.apurv.model.Metadata;
import com.apurv.repository.FileMetadataRepositoryDef;
@Service("empSer")
public class FileMetadataServicesImpl implements FileMetadatServices{
@Autowired
FileMetadataRepositoryDef fileRepDef;
	
	public String addMetadata(Metadata m)
	 {
		 return fileRepDef.addMetadata(m);
	 }
	  public List<Metadata> getAllMetadata()
	  {
		  return fileRepDef.getAllMetadata();
	  }
}
