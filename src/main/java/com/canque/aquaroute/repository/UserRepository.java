package com.canque.aquaroute.repository;

import org.springframework.data.mongodb.repository.MongoRepository;


import com.canque.aquaroute.model.User;

public interface UserRepository extends MongoRepository<User, Integer> {
    User findByEmail(String email);
}

// @Component
// class UserRepositoryImplement implements UserRepository {

//     @Autowired
//     MongoClient client;

//     @Autowired
//     MongoConverter converter;

//     public List<User> findByEmail(String text){

//         final List<User> accounts = new ArrayList<>();

//         MongoDatabase database = client.getDatabase("sysarchDemo");
//         MongoCollection<Document> collection = database.getCollection("accounts");
//         AggregateIterable<Document> result = collection.aggregate(Arrays.asList(new Document("$search", 
//         new Document("index", "accountsDefault")
//             .append("text", 
//         new Document("query", text)
//                 .append("path", "email"))), 
//         new Document("$limit", 1L)));

//         result.forEach(doc -> accounts.add(converter.read(User.class, doc)));

//         return accounts;
//     }
// }