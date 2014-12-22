package app.data.dao;

import javax.inject.Inject;

import models.Sprint;

import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;

public class SprintDAO extends SequencedDAO<Sprint>{

	@Inject
	protected SprintDAO(Morphia morphia, MongoClient mongo) {
		super(morphia, mongo);
	}
}
