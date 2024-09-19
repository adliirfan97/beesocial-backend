package com.beesocial.firebasestorageservice.service;

import com.beesocial.firebasestorageservice.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {

    public String saveData(String collectionName, Map<String, Object> data) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        // Generate a new document reference (ID)
        DocumentReference docRef = db.collection(collectionName).document(); // This generates a new document with a unique ID

        // Add the generated document ID to the data map
        data.put("documentId", docRef.getId());

        // Save the document with the generated ID
        ApiFuture<WriteResult> result = docRef.set(data);

        // Return the document ID as the result
        return docRef.getId();
    }


    public Map<String, Object> getData(String collectionName, String documentId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<DocumentSnapshot> future = db.collection(collectionName).document(documentId).get();
        DocumentSnapshot document = future.get();
        return document.exists() ? document.getData() : null;
    }

    public void deleteData(String collectionName, String documentId) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        db.collection(collectionName).document(documentId).delete().get();
    }

    public List<Map<String, Object>> getAllData(String collectionName) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        // Retrieve all documents in the collection
        ApiFuture<QuerySnapshot> future = db.collection(collectionName).get();

        // Get the results
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        // Create a list to hold the data
        List<Map<String, Object>> dataList = new ArrayList<>();

        // Loop through the documents and add their data to the list
        for (QueryDocumentSnapshot document : documents) {
            dataList.add(document.getData());
        }

        return dataList;
    }

//    public String saveUserData(String firstName, String email) throws ExecutionException, InterruptedException {
//        Firestore db = FirestoreClient.getFirestore();
//        User user = new User(firstName, email);
//
//        // Firestore stores documents in collections
//        ApiFuture<DocumentReference> result = db.collection("users").add(user);
//        return result.get().toString();
//    }

    public User getUserByEmail(String email) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();

        // Query Firestore for documents where the 'email' field matches the given email
        ApiFuture<QuerySnapshot> future = db.collection("users")
                .whereEqualTo("email", email)
                .get();

        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        // Check if any document is found
        if (!documents.isEmpty()) {
            // Convert the first document to a User object (assuming email is unique)
            return documents.getFirst().toObject(User.class);
        } else {
            return null;  // Handle case where no user is found
        }
    }

}
