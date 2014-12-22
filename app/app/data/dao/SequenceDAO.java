package app.data.dao;

import java.util.List;

import javax.inject.Inject;

import models.sequence.Sequence;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.MongoClient;

public class SequenceDAO extends BasicDAO<Sequence, ObjectId> {

	@Inject
	protected SequenceDAO(Morphia morphia, MongoClient mongo) {
        super(mongo, morphia, "get_work_done");
	}

	public long getSequence() {
		List<Sequence> sequences = this.find().asList();
		Sequence sequence = null;
		if (sequences.size() == 0) {
			sequence = new Sequence();
			this.save(sequence);
		} else {
			sequence = sequences.get(0);
		}
		long id = sequence.current;
		this.save(sequence.increment());
		return id;
	}
}
