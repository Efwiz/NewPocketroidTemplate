package org.catrobat.catroid.libraryeditor.data;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import kotlin.Metadata;
import kotlin.io.FilesKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/libraryeditor/data/LibraryStorageManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: LibraryStorageManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\n\u001a\u00020\u000bH\u0002J\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bJ\u0016\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/libraryeditor/data/LibraryStorageManager;", "", "()V", "DRAFTS_DIR", "", "LAST_SESSION_FILENAME", "gson", "Lcom/google/gson/Gson;", "clearSession", "", "context", "Landroid/content/Context;", "getSessionFile", "Ljava/io/File;", "loadLastSession", "Lorg/catrobat/catroid/libraryeditor/data/LibraryDraft;", "saveSession", "draft", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LibraryStorageManager {
    private static final String DRAFTS_DIR = "library_drafts";
    public static final LibraryStorageManager INSTANCE = new LibraryStorageManager();
    private static final String LAST_SESSION_FILENAME = "last_session.json";
    private static final Gson gson;

    private LibraryStorageManager() {
    }

    static {
        Gson gsonCreate = new GsonBuilder().setPrettyPrinting().create();
        Intrinsics.checkNotNullExpressionValue(gsonCreate, "create(...)");
        gson = gsonCreate;
    }

    private final File getSessionFile(Context context) {
        File file = new File(context.getFilesDir(), DRAFTS_DIR);
        file.mkdirs();
        return new File(file, LAST_SESSION_FILENAME);
    }

    public final LibraryDraft loadLastSession(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        File sessionFile = getSessionFile(context);
        if (!sessionFile.exists()) {
            return new LibraryDraft("last_session", null, null, null, null, null, 62, null);
        }
        try {
            Object objFromJson = gson.fromJson(FilesKt.readText$default(sessionFile, null, 1, null), (Class<Object>) LibraryDraft.class);
            Intrinsics.checkNotNull(objFromJson);
            return (LibraryDraft) objFromJson;
        } catch (Exception unused) {
            return new LibraryDraft("last_session", null, null, null, null, null, 62, null);
        }
    }

    public final void saveSession(Context context, LibraryDraft draft) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(draft, "draft");
        File sessionFile = getSessionFile(context);
        String json = gson.toJson(draft);
        Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
        FilesKt.writeText$default(sessionFile, json, null, 2, null);
    }

    public final void clearSession(Context context) {
        Intrinsics.checkNotNullParameter(context, "context");
        File sessionFile = getSessionFile(context);
        if (sessionFile.exists()) {
            sessionFile.delete();
        }
    }
}
