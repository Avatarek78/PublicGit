package cz.morosystems.morotestserver.dao;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(MessageForServer.class)
public abstract class MessageForServer_ {

	public static volatile SingularAttribute<MessageForServer, Date> dateTime;
	public static volatile SingularAttribute<MessageForServer, Integer> ID;
	public static volatile SingularAttribute<MessageForServer, String> message;

}

