package com.apurv.repository;

import java.util.List;

import com.apurv.model.Metadata;

public interface FileMetadataRepositoryDef {
    public String addMetadata(Metadata m);
    public List<Metadata> getAllMetadata();
}
