package com.google.firebase.database;

import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.interop.InteropAppCheckTokenProvider;
import com.google.firebase.auth.internal.InternalAuthProvider;
import com.google.firebase.database.android.AndroidAppCheckTokenProvider;
import com.google.firebase.database.android.AndroidAuthTokenProvider;
import com.google.firebase.database.core.DatabaseConfig;
import com.google.firebase.database.core.RepoInfo;
import com.google.firebase.database.core.TokenProvider;
import com.google.firebase.inject.Deferred;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes10.dex */
class FirebaseDatabaseComponent {

    /* JADX INFO: renamed from: app, reason: collision with root package name */
    private final FirebaseApp f176app;
    private final TokenProvider appCheckProvider;
    private final TokenProvider authProvider;
    private final Map<RepoInfo, FirebaseDatabase> instances = new HashMap();

    FirebaseDatabaseComponent(FirebaseApp firebaseApp, Deferred<InternalAuthProvider> deferred, Deferred<InteropAppCheckTokenProvider> deferred2) {
        this.f176app = firebaseApp;
        this.authProvider = new AndroidAuthTokenProvider(deferred);
        this.appCheckProvider = new AndroidAppCheckTokenProvider(deferred2);
    }

    synchronized FirebaseDatabase get(RepoInfo repoInfo) {
        FirebaseDatabase firebaseDatabase;
        firebaseDatabase = this.instances.get(repoInfo);
        if (firebaseDatabase == null) {
            DatabaseConfig databaseConfig = new DatabaseConfig();
            if (!this.f176app.isDefaultApp()) {
                databaseConfig.setSessionPersistenceKey(this.f176app.getName());
            }
            databaseConfig.setFirebaseApp(this.f176app);
            databaseConfig.setAuthTokenProvider(this.authProvider);
            databaseConfig.setAppCheckTokenProvider(this.appCheckProvider);
            FirebaseDatabase firebaseDatabase2 = new FirebaseDatabase(this.f176app, repoInfo, databaseConfig);
            this.instances.put(repoInfo, firebaseDatabase2);
            firebaseDatabase = firebaseDatabase2;
        }
        return firebaseDatabase;
    }
}
