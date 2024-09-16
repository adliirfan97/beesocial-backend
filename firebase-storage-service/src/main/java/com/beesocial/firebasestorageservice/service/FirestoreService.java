package com.beesocial.firebasestorageservice.service;

import com.beesocial.firebasestorageservice.model.User;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Service
public class FirestoreService {

    public String saveData(String collectionName, Map<String, Object> data) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        ApiFuture<DocumentReference> result = db.collection(collectionName).add(data);
        return result.get().toString();
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

    public String saveUserData(String firstName, String email) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        User user = new User(firstName, email);

        // Firestore stores documents in collections
        ApiFuture<DocumentReference> result = db.collection("users").add(user);
        return result.get().toString();
    }

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
    public String editData(String collectionName, String documentId, Map<String, Object> data) throws ExecutionException, InterruptedException {
        Firestore db = FirestoreClient.getFirestore();
        DocumentReference documentReference = db.collection(collectionName)
                .document(documentId);
        ApiFuture<WriteResult> result = documentReference.update(data);
        return result.get().getUpdateTime().toString();
    }
//    public String saveUserData(String firstName, String email) throws ExecutionException, InterruptedException {
//        Firestore db = FirestoreClient.getFirestore();
//        User user = new User(firstName, email);
//
//        // Firestore stores documents in collections
//        ApiFuture<DocumentReference> result = db.collection("users").add(user);
//        return result.get().toString();
//    }
//
//    public User getUserByEmail(String email) throws ExecutionException, InterruptedException {
//        Firestore db = FirestoreClient.getFirestore();
//
//        // Query Firestore for documents where the 'email' field matches the given email
//        ApiFuture<QuerySnapshot> future = db.collection("users")
//                .whereEqualTo("email", email)
//                .get();
//
//        List<QueryDocumentSnapshot> documents = future.get().getDocuments();
//
//        // Check if any document is found
//        if (!documents.isEmpty()) {
//            // Convert the first document to a User object (assuming email is unique)
//            return documents.getFirst().toObject(User.class);
//        } else {
//            return null;  // Handle case where no user is found
//        }
//    }

}
