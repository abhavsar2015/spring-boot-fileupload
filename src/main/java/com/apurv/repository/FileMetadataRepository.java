package com.apurv.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.transaction.support.TransactionTemplate;
import com.apurv.model.Metadata;

public class FileMetadataRepository implements FileMetadataRepositoryDef{
	@PersistenceContext(unitName="serv1")
    public EntityManager em;
    private TransactionTemplate transactionTemplate;
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
            this.transactionTemplate = transactionTemplate;
        }

	public String addMetadata(Metadata m)
	{
		
		return "";
	}
    public List<Metadata> getAllMetadata()
    {
    	TypedQuery<Metadata> tq=em.createNamedQuery("Movie.findAll", Metadata.class);
		return tq.getResultList();
    }
}
