package com.apurv.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;
import com.apurv.model.Metadata;

public class FileMetadataRepository implements FileMetadataRepositoryDef{
	@PersistenceContext(unitName="serv1")
    public EntityManager em;
    private TransactionTemplate transactionTemplate;
    public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
            this.transactionTemplate = transactionTemplate;
        }
    public EntityManager getEm() {
		return em;
	}
	public void setEm(EntityManager em) {
		this.em = em;
	}

    @Override
	@Transactional
	public String addMetadata(Metadata m)
	{
		EntityManager em1 = getEm();
		//em1.getTransaction().begin();
   	   em1.persist(m);
	   return m.getFileId();
		
	}
    @Override
    public List<Metadata> getAllMetadata()
    {
    	TypedQuery<Metadata> tq=em.createNamedQuery("Metadata.findAll", Metadata.class);
		return tq.getResultList();
    }
}
