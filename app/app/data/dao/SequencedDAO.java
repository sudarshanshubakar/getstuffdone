package app.data.dao;

import javax.inject.Inject;

import models.SequencedModel;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.MongoClient;

public abstract  class  SequencedDAO<T extends SequencedModel> extends BasicDAO<T, ObjectId>{

	@Inject
	private SequenceDAO seqDAO;
	protected SequencedDAO(Morphia morphia, MongoClient mongo) {
        super(mongo, morphia, "get_work_done");
    }
	@Override
	public Key<T> save(T entity) {
		entity.setEntityId(seqDAO.getSequence());
		return super.save(entity);
	}
}
