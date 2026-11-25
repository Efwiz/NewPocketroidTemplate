package org.catrobat.catroid.ide;

import com.google.firebase.database.core.ServerValues;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.io.FilesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiTreeChangeEvent;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/LibraryTracker.class
  classes14.dex
 */
/* JADX INFO: compiled from: DependencyManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0005\bÆ\u0002\u0018\u00002\u00020\u0001:\u0001\u000fB\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u0016\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006J\u001e\u0010\r\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u00062\u0006\u0010\f\u001a\u00020\u0006¨\u0006\u0010"}, d2 = {"Lorg/catrobat/catroid/ide/LibraryTracker;", "", "()V", "getMetaFile", "Ljava/io/File;", Constants.EXTRA_PROJECT_PATH, "", "loadLibs", "", "Lorg/catrobat/catroid/ide/LibraryTracker$LibMeta;", "removeLib", "", PsiTreeChangeEvent.PROP_FILE_NAME, "saveLib", "mavenId", "LibMeta", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LibraryTracker {
    public static final LibraryTracker INSTANCE = new LibraryTracker();

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/LibraryTracker$LibMeta.class
      classes14.dex
     */
    /* JADX INFO: compiled from: DependencyManager.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J'\u0010\u0010\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u0017"}, d2 = {"Lorg/catrobat/catroid/ide/LibraryTracker$LibMeta;", "", "mavenId", "", PsiTreeChangeEvent.PROP_FILE_NAME, ServerValues.NAME_OP_TIMESTAMP, "", "(Ljava/lang/String;Ljava/lang/String;J)V", "getFileName", "()Ljava/lang/String;", "getMavenId", "getTimestamp", "()J", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class LibMeta {
        private final String fileName;
        private final String mavenId;
        private final long timestamp;

        public static /* synthetic */ LibMeta copy$default(LibMeta libMeta, String str, String str2, long j, int i, Object obj) {
            if ((i & 1) != 0) {
                str = libMeta.mavenId;
            }
            if ((i & 2) != 0) {
                str2 = libMeta.fileName;
            }
            if ((i & 4) != 0) {
                j = libMeta.timestamp;
            }
            return libMeta.copy(str, str2, j);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getMavenId() {
            return this.mavenId;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getFileName() {
            return this.fileName;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final long getTimestamp() {
            return this.timestamp;
        }

        public final LibMeta copy(String mavenId, String fileName, long timestamp) {
            Intrinsics.checkNotNullParameter(mavenId, "mavenId");
            Intrinsics.checkNotNullParameter(fileName, "fileName");
            return new LibMeta(mavenId, fileName, timestamp);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LibMeta)) {
                return false;
            }
            LibMeta libMeta = (LibMeta) other;
            return Intrinsics.areEqual(this.mavenId, libMeta.mavenId) && Intrinsics.areEqual(this.fileName, libMeta.fileName) && this.timestamp == libMeta.timestamp;
        }

        public int hashCode() {
            return (((this.mavenId.hashCode() * 31) + this.fileName.hashCode()) * 31) + Long.hashCode(this.timestamp);
        }

        public String toString() {
            return "LibMeta(mavenId=" + this.mavenId + ", fileName=" + this.fileName + ", timestamp=" + this.timestamp + Constant.AFTER_QUTO;
        }

        public LibMeta(String mavenId, String fileName, long j) {
            Intrinsics.checkNotNullParameter(mavenId, "mavenId");
            Intrinsics.checkNotNullParameter(fileName, "fileName");
            this.mavenId = mavenId;
            this.fileName = fileName;
            this.timestamp = j;
        }

        public final String getFileName() {
            return this.fileName;
        }

        public final String getMavenId() {
            return this.mavenId;
        }

        public final long getTimestamp() {
            return this.timestamp;
        }
    }

    private LibraryTracker() {
    }

    private final File getMetaFile(String projectPath) {
        return new File(projectPath, "libs/necat_libs.json");
    }

    public final void saveLib(String projectPath, final String mavenId, final String fileName) throws JSONException {
        Intrinsics.checkNotNullParameter(projectPath, "projectPath");
        Intrinsics.checkNotNullParameter(mavenId, "mavenId");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        File metaFile = getMetaFile(projectPath);
        List<LibMeta> mutableList = CollectionsKt.toMutableList((Collection) loadLibs(projectPath));
        final Function1<LibMeta, Boolean> function1 = new Function1<LibMeta, Boolean>() { // from class: org.catrobat.catroid.ide.LibraryTracker.saveLib.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(LibMeta it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return Boolean.valueOf(Intrinsics.areEqual(it.getFileName(), fileName) || Intrinsics.areEqual(it.getMavenId(), mavenId));
            }
        };
        mutableList.removeIf(new Predicate() { // from class: org.catrobat.catroid.ide.LibraryTracker$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return LibraryTracker.saveLib$lambda$0(function1, obj);
            }
        });
        mutableList.add(new LibMeta(mavenId, fileName, System.currentTimeMillis()));
        JSONArray jSONArray = new JSONArray();
        for (LibMeta libMeta : mutableList) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", libMeta.getMavenId());
            jSONObject.put("file", libMeta.getFileName());
            jSONObject.put("time", libMeta.getTimestamp());
            jSONArray.put(jSONObject);
        }
        if (!metaFile.getParentFile().exists()) {
            metaFile.getParentFile().mkdirs();
        }
        String string = jSONArray.toString(4);
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        FilesKt.writeText$default(metaFile, string, null, 2, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean saveLib$lambda$0(Function1 tmp0, Object obj) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Boolean) tmp0.invoke(obj)).booleanValue();
    }

    public final void removeLib(String projectPath, String fileName) throws JSONException {
        Intrinsics.checkNotNullParameter(projectPath, "projectPath");
        Intrinsics.checkNotNullParameter(fileName, "fileName");
        List<LibMeta> listLoadLibs = loadLibs(projectPath);
        ArrayList<LibMeta> arrayList = new ArrayList();
        for (Object obj : listLoadLibs) {
            if (!Intrinsics.areEqual(((LibMeta) obj).getFileName(), fileName)) {
                arrayList.add(obj);
            }
        }
        File metaFile = getMetaFile(projectPath);
        JSONArray jSONArray = new JSONArray();
        for (LibMeta libMeta : arrayList) {
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("id", libMeta.getMavenId());
            jSONObject.put("file", libMeta.getFileName());
            jSONObject.put("time", libMeta.getTimestamp());
            jSONArray.put(jSONObject);
        }
        String string = jSONArray.toString(4);
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        FilesKt.writeText$default(metaFile, string, null, 2, null);
    }

    public final List<LibMeta> loadLibs(String projectPath) {
        Intrinsics.checkNotNullParameter(projectPath, "projectPath");
        File metaFile = getMetaFile(projectPath);
        if (!metaFile.exists()) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        try {
            JSONArray jSONArray = new JSONArray(FilesKt.readText$default(metaFile, null, 1, null));
            int length = jSONArray.length();
            for (int i = 0; i < length; i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                String string = jSONObject.getString("id");
                Intrinsics.checkNotNullExpressionValue(string, "getString(...)");
                String string2 = jSONObject.getString("file");
                Intrinsics.checkNotNullExpressionValue(string2, "getString(...)");
                arrayList.add(new LibMeta(string, string2, jSONObject.optLong("time")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
