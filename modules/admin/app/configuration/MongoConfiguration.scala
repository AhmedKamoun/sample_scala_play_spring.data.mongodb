package configuration

import java.util.Arrays

import com.mongodb.{WriteConcern, MongoClient, MongoCredential, ServerAddress}
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories


@Configuration
@EnableMongoRepositories(Array("com.core.dal"))
class MongoConfiguration {
  private var DATBASE_NAME: String = "database_test"

  @Bean(name = Array("mongoClient")) def mongoClient: MongoClient = {
    val mongoClient: MongoClient = new MongoClient(new ServerAddress("localhost:27017"), Arrays.asList(MongoCredential.createCredential("root", DATBASE_NAME, "root".toCharArray)))
    return mongoClient
  }

  @Bean(name = Array("mongoTemplate")) def mongoTemplate: MongoTemplate = {
    var template = new MongoTemplate(mongoClient, DATBASE_NAME)
    template.setWriteConcern(WriteConcern.ACKNOWLEDGED)
    return template
  }

}
