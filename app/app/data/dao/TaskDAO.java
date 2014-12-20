package app.data.dao;

import javax.inject.Inject;

import models.Task;

import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class TaskDAO extends SequencedDAO<Task> {

//	@Inject
//	private SequenceDAO seqDAO;
	
	@Inject
	public TaskDAO(Morphia morphia, MongoClient mongo) {
        super(morphia, mongo);
    }
	
//	@Override
//	public Key<Task> save(Task entity) {
//		entity.setTaskId(seqDAO.getSequence());
//		return super.save(entity);
//	}
}
