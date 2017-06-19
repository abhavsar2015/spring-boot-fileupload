package com.apurv.service;

import java.util.List;

import com.apurv.model.Metadata;

public interface FileMetadatServices {
	  public String addMetadata(Metadata m);
	  public List<Metadata> getAllMetadata();
}
