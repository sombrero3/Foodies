package com.example.foodies.model;

import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.json.JSONException;
import org.w3c.dom.Document;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ModelFireBase {
    FirebaseAuth currentUser;
    String currentUserId;
    ;
    FirebaseDatabase dbRealTime = FirebaseDatabase.getInstance();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public ModelFireBase() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        db.setFirestoreSettings(settings);
    }

    private String getSignedUserId() {
        return Model.instance.getSignedUser().getId();
    }

    /**
     * User CRUD + Dao
     */
    //Create
    public void addUser(User user, Model.AddUserListener listener) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String userString = objectMapper.writeValueAsString(user);
        Map<String, Object> json = objectMapper.readValue(userString, Map.class);
        db.collection("users")
                .document(user.getId())
                .set(json)
                .addOnSuccessListener(u -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    //Create
    public void updateUser(User user, Model.AddUserListener listener) throws JsonProcessingException {
        addUser(user, new Model.AddUserListener() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }

    //Read
    public void getUserById(String id, Model.getUserByIdListener listener) {
        db.collection("users")
                //  .whereEqualTo("id",studentId)
                .document(id)
                .get()
                .addOnCompleteListener(task -> {
                    User user = null;
                    if (task.isSuccessful() && task.getResult() != null) {
                        try {
                            //  ObjectMapper map = new ObjectMapper();
                            user = User.create(task.getResult().getData());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    listener.onComplete(user);
                });
    }

    //Delete
//    public Task<Void> removeUser(User user){
//        return usersReference.child(user.getId()).removeValue();
//    }

    public void getAllUsers(Model.GetAllUsersListener listener) {

        db.collection("users")
                //      .whereEqualTo("deleted",false)
                //  .whereGreaterThanOrEqualTo("updateDate", new Timestamp(lastUpdateDate,0))
                .get()
                .addOnCompleteListener(task -> {
                    List<User> list = new LinkedList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            User user = null;
                            try {
                                user = User.create(doc.getData());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            if (user != null) {
                                list.add(user);
                            }
                        }
                    }
                    //       Log.d("TAG","Last update date = "+ lastUpdateDate);
                    listener.onComplete(list);
                });
    }

    public interface GetUsersListListener {
        void onComplete(List<User> users);
    }

    public void getUsersList(List<String> usersId, GetUsersListListener listener) {
        if (!usersId.isEmpty()) {
            db.collection("users")
                    .whereIn("id", usersId)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                List<User> users = new LinkedList<>();
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    try {
                                        User user = User.create(doc.getData());
                                        if (user != null) {
                                            users.add(user);
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                listener.onComplete(users);
                            }
                        }
                    });
        }
    }


    /**
     * Authentication
     */
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public boolean isSignedIn() {
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        return (currentUser != null);
    }

    public void signIn(String email, String password, String firstName, String lastName, ProgressBar progressBar) {
        Log.d("TAG", "signIn: " + email + " " + password + " " + mAuth);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "onComplete: succeed" + email + " " + password + " " + mAuth.getCurrentUser());
                        } else {
                            Toast.makeText(progressBar.getContext(), "Failed To Registered 2", Toast.LENGTH_LONG).show();

                            Log.d("TAG", "onComplete failed: " + email + " " + password + " " + mAuth + " " + task.getException().toString());
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    public void setCurrentUser(Model.setCurrentUserListener listener) {
        currentUser = FirebaseAuth.getInstance();
        String userUid = currentUser.getCurrentUser().getUid();
        getUserById(userUid, new Model.getUserByIdListener() {
            @Override
            public void onComplete(User user) {
                listener.onComplete(user);
            }
        });
    }

    /**
     * FriendshipDao
     */
    public interface AddFriendshipStatusListener {
        void onComplete();
    }

    public void addFriendshipStatus(FriendshipStatus fs, Model.VoidListener listener) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String fsString = objectMapper.writeValueAsString(fs);
        Map<String, Object> json = objectMapper.readValue(fsString, Map.class);
        db.collection("friendshipStatuses")
                .document(fs.getUser1Id() + fs.getUser2Id())
                .set(json)
                .addOnSuccessListener(u -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    public void addFriendRequest(String userId, Model.VoidListener listener) throws JsonProcessingException {
        String signedUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FriendshipStatus fs = new FriendshipStatus(userId, signedUserId, "pending");
        ObjectMapper objectMapper = new ObjectMapper();
        String fsString = objectMapper.writeValueAsString(fs);
        Map<String, Object> json = objectMapper.readValue(fsString, Map.class);
        db.collection("friendshipStatuses")
                .document(fs.getUser1Id() + fs.getUser2Id())
                .set(json)
                .addOnSuccessListener(u -> listener.onComplete())
                .addOnFailureListener(e -> listener.onComplete());
    }

    public void getFriendsRequests(String userId, Model.GetFriendsRequestsListener listener) {
        List<String> usersRequested = new LinkedList<>();
        db.collection("friendshipStatuses")
                //      .whereEqualTo("deleted",false)
                //.whereGreaterThanOrEqualTo("updateDate", new Timestamp(lastUpdateDate,0))
                .whereEqualTo("user1Id", userId)
                .whereEqualTo("status", "pending")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : task.getResult()) {
                            FriendshipStatus fs = FriendshipStatus.create(doc.getData());
                            if (fs != null) {
                                usersRequested.add(fs.getUser2Id());
                            }
                        }
                        getUsersList(usersRequested, new GetUsersListListener() {
                            @Override
                            public void onComplete(List<User> users) {
                                listener.onComplete(users);
                            }
                        });
                    }
                });
    }

    public void getFriendsList(String user1Id, Model.GetFriendListListener listener) {
        List<User> res = new LinkedList<>();
        List<String> usersId = new LinkedList<>();
        db.collection("friendshipStatuses")
                .whereEqualTo("user1Id", user1Id)
                .whereEqualTo("status", "friends")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        for (QueryDocumentSnapshot qs : task.getResult()) {
                            FriendshipStatus fs = FriendshipStatus.create(qs.getData());
                            usersId.add(fs.getUser2Id());
                        }
                        db.collection("friendshipStatuses")
                                .whereEqualTo("user2Id", user1Id)
                                .whereEqualTo("status", "friends")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        for (QueryDocumentSnapshot qs : task.getResult()) {
                                            FriendshipStatus fs = FriendshipStatus.create(qs.getData());
                                            usersId.add(fs.getUser1Id());
                                        }
                                        getUsersList(usersId, new GetUsersListListener() {
                                            @Override
                                            public void onComplete(List<User> users) {
                                                listener.onComplete(users);
                                            }
                                        });
                                    }
                                });
                    }
                });
    }

    public void friendRequestConfirmed(String userId, Model.FriendRequestConfirmedListener listener) throws JsonProcessingException {
        String signedUserId = Model.instance.getSignedUser().getId();
        FriendshipStatus fs = new FriendshipStatus(signedUserId, userId, "friends");
        addFriendshipStatus(fs, new Model.VoidListener() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }

    public void friendRequestUnConfirmed(User user, Model.FriendRequestUnConfirmed listener) throws JsonProcessingException {
        String signedUserId = Model.instance.getSignedUser().getId();
        FriendshipStatus fs = new FriendshipStatus(signedUserId, user.getId(), "pending");
        addFriendshipStatus(fs, new Model.VoidListener() {
            @Override
            public void onComplete() {
                listener.onComplete();
            }
        });
    }

    public void cancelFriendship(String userId, Model.CancelFriendshipListener listener) {
        String signedUserId = getSignedUserId();
        db.collection("friendshipStatuses")
                .whereEqualTo("user1Id", signedUserId)
                .whereEqualTo("user2Id", userId)
                .whereEqualTo("status", "friends")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                db.collection("friendshipStatuses")
                                        .whereEqualTo("user1Id", userId)
                                        .whereEqualTo("user2Id", signedUserId)
                                        .whereEqualTo("status", "friends")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    FriendshipStatus fs = new FriendshipStatus(userId, signedUserId, "canceled");
                                                    try {
                                                        addFriendshipStatus(fs, new Model.VoidListener() {
                                                            @Override
                                                            public void onComplete() {
                                                                listener.onComplete();
                                                            }
                                                        });
                                                    } catch (JsonProcessingException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        });
                            } else {
                                FriendshipStatus fs = new FriendshipStatus(signedUserId, userId, "canceled");
                                try {
                                    addFriendshipStatus(fs, new Model.VoidListener() {
                                        @Override
                                        public void onComplete() {
                                            listener.onComplete();
                                        }
                                    });
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                });

    }

    public void recoverFriendship(String userId, Model.VoidListener listener) {
        String signedUserId = getSignedUserId();
        db.collection("friendshipStatuses")
                .whereEqualTo("user1Id", signedUserId)
                .whereEqualTo("user2Id", userId)
                .whereEqualTo("status", "canceled")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (task.getResult().isEmpty()) {
                                db.collection("friendshipStatuses")
                                        .whereEqualTo("user1Id", userId)
                                        .whereEqualTo("user2Id", signedUserId)
                                        .whereEqualTo("status", "canceled")
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    FriendshipStatus fs = new FriendshipStatus(userId, signedUserId, "friends");
                                                    try {
                                                        addFriendshipStatus(fs, new Model.VoidListener() {
                                                            @Override
                                                            public void onComplete() {
                                                                listener.onComplete();
                                                            }
                                                        });
                                                    } catch (JsonProcessingException e) {
                                                        e.printStackTrace();
                                                    }
                                                }
                                            }
                                        });
                            } else {
                                FriendshipStatus fs = new FriendshipStatus(signedUserId, userId, "friends");
                                try {
                                    addFriendshipStatus(fs, new Model.VoidListener() {
                                        @Override
                                        public void onComplete() {
                                            listener.onComplete();
                                        }
                                    });
                                } catch (JsonProcessingException e) {
                                    e.printStackTrace();
                                }

                            }
                        }
                    }
                });
    }
}