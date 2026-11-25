package org.catrobat.catroid.content;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.catrobat.catroid.CatroidApplication;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/content/FireBaseManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: FireBaseManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bJ\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000bH\u0002J,\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0014\u0010\u0011\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\u0004\u0012\u00020\t0\u0012J\u001e\u0010\u0013\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\u0014\u001a\u00020\u000bR\u001b\u0010\u0003\u001a\u00020\u00048BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0003\u0010\u0005¨\u0006\u0015"}, d2 = {"Lorg/catrobat/catroid/content/FireBaseManager;", "", "()V", "isInitialized", "", "()Z", "isInitialized$delegate", "Lkotlin/Lazy;", "deleteFromDatabase", "", "databaseUrl", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "getDbRef", "Lcom/google/firebase/database/DatabaseReference;", "urlOrId", "readFromDatabase", "callback", "Lkotlin/Function1;", "writeToDatabase", "value", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FireBaseManager {
    public static final FireBaseManager INSTANCE = new FireBaseManager();

    /* JADX INFO: renamed from: isInitialized$delegate, reason: from kotlin metadata */
    private static final Lazy isInitialized = LazyKt.lazy(new Function0<Boolean>() { // from class: org.catrobat.catroid.content.FireBaseManager.isInitialized.2
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // kotlin.jvm.functions.Function0
        public final Boolean invoke() {
            Context appContext = CatroidApplication.getAppContext();
            if (appContext != null && FirebaseApp.getApps(appContext).isEmpty()) {
                try {
                    Resources resources = appContext.getResources();
                    String packageName = appContext.getPackageName();
                    int identifier = resources.getIdentifier("google_app_id", "string", packageName);
                    int identifier2 = resources.getIdentifier("google_api_key", "string", packageName);
                    int identifier3 = resources.getIdentifier("project_id", "string", packageName);
                    int identifier4 = resources.getIdentifier("firebase_database_url", "string", packageName);
                    if (identifier != 0 && identifier2 != 0) {
                        String string = resources.getString(identifier);
                        Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                        String string2 = resources.getString(identifier2);
                        Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                        String string3 = identifier3 != 0 ? resources.getString(identifier3) : null;
                        String string4 = identifier4 != 0 ? resources.getString(identifier4) : null;
                        FirebaseOptions.Builder apiKey = new FirebaseOptions.Builder().setApplicationId(string).setApiKey(string2);
                        Intrinsics.checkNotNullExpressionValue(apiKey, "setApiKey(...)");
                        if (string3 != null) {
                            apiKey.setProjectId(string3);
                        }
                        if (string4 != null) {
                            apiKey.setDatabaseUrl(string4);
                        }
                        FirebaseOptions firebaseOptionsBuild = apiKey.build();
                        Intrinsics.checkNotNullExpressionValue(firebaseOptionsBuild, "build(...)");
                        FirebaseApp.initializeApp(appContext, firebaseOptionsBuild);
                        Log.i("Firebase", "Firebase initialized dynamically with explicit options successfully!");
                    } else {
                        FirebaseOptions firebaseOptionsBuild2 = new FirebaseOptions.Builder().setApplicationId("1:1234567890:android:abcdef12345678").setApiKey("AIzaSyDummyApiKeyPlaceholder123456789").setProjectId("dummy-project-id").build();
                        Intrinsics.checkNotNullExpressionValue(firebaseOptionsBuild2, "build(...)");
                        FirebaseApp.initializeApp(appContext, firebaseOptionsBuild2);
                        Log.w("Firebase", "Firebase initialized dynamically with dummy options (no resources found).");
                    }
                } catch (Exception e) {
                    Log.e("Firebase", "Failed to initialize Firebase dynamically", e);
                    try {
                        FirebaseApp.initializeApp(appContext);
                    } catch (Exception unused) {
                    }
                }
            }
            return true;
        }
    });

    private FireBaseManager() {
    }

    private final boolean isInitialized() {
        return ((Boolean) isInitialized.getValue()).booleanValue();
    }

    private final DatabaseReference getDbRef(String urlOrId, String key) {
        isInitialized();
        String string = StringsKt.trim((CharSequence) urlOrId).toString();
        if (string.length() == 0) {
            throw new IllegalArgumentException("Database ID or URL cannot be empty");
        }
        if (!StringsKt.startsWith$default(string, "http://", false, 2, (Object) null) && !StringsKt.startsWith$default(string, "https://", false, 2, (Object) null)) {
            string = "https://" + string + "-default-rtdb.firebaseio.com/";
        }
        DatabaseReference databaseReferenceChild = FirebaseDatabase.getInstance(string).getReference().child(key);
        Intrinsics.checkNotNullExpressionValue(databaseReferenceChild, "child(...)");
        return databaseReferenceChild;
    }

    public final void readFromDatabase(String databaseUrl, String key, final Function1<? super String, Unit> callback) {
        Intrinsics.checkNotNullParameter(databaseUrl, "databaseUrl");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(callback, "callback");
        try {
            getDbRef(databaseUrl, key).addListenerForSingleValueEvent(new ValueEventListener() { // from class: org.catrobat.catroid.content.FireBaseManager.readFromDatabase.1
                @Override // com.google.firebase.database.ValueEventListener
                public void onDataChange(DataSnapshot snapshot) {
                    String string;
                    Intrinsics.checkNotNullParameter(snapshot, "snapshot");
                    Function1<String, Unit> function1 = callback;
                    Object value = snapshot.getValue();
                    if (value == null || (string = value.toString()) == null) {
                        string = "No data";
                    }
                    function1.invoke(string);
                }

                @Override // com.google.firebase.database.ValueEventListener
                public void onCancelled(DatabaseError error) {
                    Intrinsics.checkNotNullParameter(error, "error");
                    Log.e("Firebase", "Error reading data: " + error.getMessage(), error.toException());
                    callback.invoke(null);
                }
            });
        } catch (Exception e) {
            Log.e("Firebase", "Database read execution failed", e);
            callback.invoke(null);
        }
    }

    public final void writeToDatabase(String databaseUrl, String key, String value) {
        Intrinsics.checkNotNullParameter(databaseUrl, "databaseUrl");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(value, "value");
        try {
            getDbRef(databaseUrl, key).setValue(value).addOnFailureListener(new OnFailureListener() { // from class: org.catrobat.catroid.content.FireBaseManager$$ExternalSyntheticLambda0
                @Override // com.google.android.gms.tasks.OnFailureListener
                public final void onFailure(Exception exc) {
                    FireBaseManager.writeToDatabase$lambda$0(exc);
                }
            });
        } catch (Exception e) {
            Log.e("Firebase", "Database write execution failed", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void writeToDatabase$lambda$0(Exception error) {
        Intrinsics.checkNotNullParameter(error, "error");
        Log.e("Firebase", "Error writing data: " + error.getMessage(), error);
    }

    public final void deleteFromDatabase(String databaseUrl, String key) {
        Intrinsics.checkNotNullParameter(databaseUrl, "databaseUrl");
        Intrinsics.checkNotNullParameter(key, "key");
        try {
            getDbRef(databaseUrl, key).removeValue().addOnFailureListener(new OnFailureListener() { // from class: org.catrobat.catroid.content.FireBaseManager$$ExternalSyntheticLambda1
                @Override // com.google.android.gms.tasks.OnFailureListener
                public final void onFailure(Exception exc) {
                    FireBaseManager.deleteFromDatabase$lambda$1(exc);
                }
            });
        } catch (Exception e) {
            Log.e("Firebase", "Database delete execution failed", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void deleteFromDatabase$lambda$1(Exception error) {
        Intrinsics.checkNotNullParameter(error, "error");
        Log.e("Firebase", "Error deleting data: " + error.getMessage(), error);
    }
}
