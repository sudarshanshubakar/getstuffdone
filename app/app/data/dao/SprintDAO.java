package app.data.dao;

import javax.inject.Inject;

import models.Sprint;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;

import com.mongodb.MongoClient;

public class SprintDAO extends BasicDAO<Sprint, ObjectId>{

	@Inject
	public SprintDAO(Morphia morphia, MongoClient mongo) {
        super(mongo, morphia, "get_work_done");
    }
}
