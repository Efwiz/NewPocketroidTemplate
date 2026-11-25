package org.catrobat.catroid.ide;

import android.content.Context;
import com.beust.jcommander.Parameters;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.io.ByteStreamsKt;
import kotlin.io.CloseableKt;
import kotlin.io.FilesKt;
import kotlin.io.TextStreamsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.Charsets;
import kotlin.text.StringsKt;
import org.apache.xerces.impl.xs.SchemaSymbols;
import org.catrobat.catroid.common.Constants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/DependencyManager.class
  classes14.dex
 */
/* JADX INFO: compiled from: DependencyManager.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010#\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\bÆ\u0002\u0018\u00002\u00020\u0001:\u0002&'B\u0007\b\u0002¢\u0006\u0002\u0010\u0002JH\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0018\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fH\u0002J^\u0010\u0012\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00050\u00142\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\u0006\u0010\u0015\u001a\u00020\u00072\u0018\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fH\u0002JR\u0010\u0016\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u0017\u001a\u00020\u00052\u000e\b\u0002\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00050\u00042\b\b\u0002\u0010\u0015\u001a\u00020\u00072\u0018\u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110\u000fJ \u0010\u0019\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004H\u0002J\u0014\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001b0\u00042\u0006\u0010\n\u001a\u00020\u0005J\u0016\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00042\u0006\u0010\u001e\u001a\u00020\u0005H\u0002J\u0010\u0010\u001f\u001a\u0004\u0018\u00010\f2\u0006\u0010 \u001a\u00020\u0005J,\u0010!\u001a\u00020\u00072\u0006\u0010\"\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u001b2\u0012\u0010$\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00110%H\u0002R\u0014\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/catrobat/catroid/ide/DependencyManager;", "", "()V", "defaultRepos", "", "", "downloadFile", "", "context", "Landroid/content/Context;", Constants.EXTRA_PROJECT_PATH, "lib", "Lorg/catrobat/catroid/ide/DependencyManager$LibInfo;", "repos", "onProgressUpdate", "Lkotlin/Function2;", "Lorg/catrobat/catroid/ide/DownloadStatus;", "", "downloadLibInternal", "visited", "", "recursive", "downloadLibraryRecursive", "libString", "customRepos", "downloadPom", "getLibraries", "Ljava/io/File;", "parseDependenciesFromPom", "Lorg/catrobat/catroid/ide/DependencyManager$Dep;", "pomXml", "parseLib", "str", "tryDownload", "urlStr", FirebaseAnalytics.Param.DESTINATION, "onProgress", "Lkotlin/Function1;", "Dep", "LibInfo", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DependencyManager {
    public static final DependencyManager INSTANCE = new DependencyManager();
    private static final List<String> defaultRepos = CollectionsKt.listOf((Object[]) new String[]{"https://repo1.maven.org/maven2", "https://maven.google.com"});

    private DependencyManager() {
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/DependencyManager$LibInfo.class
      classes14.dex
     */
    /* JADX INFO: compiled from: DependencyManager.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0007J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0003HÆ\u0003J3\u0010\u0011\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\b\u0010\u0017\u001a\u00020\u0003H\u0016R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\u0018"}, d2 = {"Lorg/catrobat/catroid/ide/DependencyManager$LibInfo;", "", "group", "", "artifact", "version", "classifier", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getArtifact", "()Ljava/lang/String;", "getClassifier", "getGroup", "getVersion", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class LibInfo {
        private final String artifact;
        private final String classifier;
        private final String group;
        private final String version;

        public static /* synthetic */ LibInfo copy$default(LibInfo libInfo, String str, String str2, String str3, String str4, int i, Object obj) {
            if ((i & 1) != 0) {
                str = libInfo.group;
            }
            if ((i & 2) != 0) {
                str2 = libInfo.artifact;
            }
            if ((i & 4) != 0) {
                str3 = libInfo.version;
            }
            if ((i & 8) != 0) {
                str4 = libInfo.classifier;
            }
            return libInfo.copy(str, str2, str3, str4);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getGroup() {
            return this.group;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getArtifact() {
            return this.artifact;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final String getVersion() {
            return this.version;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final String getClassifier() {
            return this.classifier;
        }

        public final LibInfo copy(String group, String artifact, String version, String classifier) {
            Intrinsics.checkNotNullParameter(group, "group");
            Intrinsics.checkNotNullParameter(artifact, "artifact");
            Intrinsics.checkNotNullParameter(version, "version");
            return new LibInfo(group, artifact, version, classifier);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof LibInfo)) {
                return false;
            }
            LibInfo libInfo = (LibInfo) other;
            return Intrinsics.areEqual(this.group, libInfo.group) && Intrinsics.areEqual(this.artifact, libInfo.artifact) && Intrinsics.areEqual(this.version, libInfo.version) && Intrinsics.areEqual(this.classifier, libInfo.classifier);
        }

        public int hashCode() {
            int iHashCode = ((((this.group.hashCode() * 31) + this.artifact.hashCode()) * 31) + this.version.hashCode()) * 31;
            String str = this.classifier;
            return iHashCode + (str == null ? 0 : str.hashCode());
        }

        public LibInfo(String group, String artifact, String version, String str) {
            Intrinsics.checkNotNullParameter(group, "group");
            Intrinsics.checkNotNullParameter(artifact, "artifact");
            Intrinsics.checkNotNullParameter(version, "version");
            this.group = group;
            this.artifact = artifact;
            this.version = version;
            this.classifier = str;
        }

        public /* synthetic */ LibInfo(String str, String str2, String str3, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, str3, (i & 8) != 0 ? null : str4);
        }

        public final String getArtifact() {
            return this.artifact;
        }

        public final String getClassifier() {
            return this.classifier;
        }

        public final String getGroup() {
            return this.group;
        }

        public final String getVersion() {
            return this.version;
        }

        public String toString() {
            String str = this.classifier;
            if (str != null) {
                return this.group + ":" + this.artifact + ":" + this.version + ":" + str;
            }
            return this.group + ":" + this.artifact + ":" + this.version;
        }
    }

    public final LibInfo parseLib(String str) {
        Intrinsics.checkNotNullParameter(str, "str");
        List listSplit$default = StringsKt.split$default((CharSequence) str, new String[]{":"}, false, 0, 6, (Object) null);
        int size = listSplit$default.size();
        if (size == 3) {
            return new LibInfo((String) listSplit$default.get(0), (String) listSplit$default.get(1), (String) listSplit$default.get(2), null);
        }
        if (size != 4) {
            return null;
        }
        return new LibInfo((String) listSplit$default.get(0), (String) listSplit$default.get(1), (String) listSplit$default.get(2), (String) listSplit$default.get(3));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ boolean downloadLibraryRecursive$default(DependencyManager dependencyManager, Context context, String str, String str2, List list, boolean z, Function2 function2, int i, Object obj) {
        if ((i & 8) != 0) {
            list = CollectionsKt.emptyList();
        }
        List list2 = list;
        if ((i & 16) != 0) {
            z = true;
        }
        return dependencyManager.downloadLibraryRecursive(context, str, str2, list2, z, function2);
    }

    public final boolean downloadLibraryRecursive(Context context, String projectPath, String libString, List<String> customRepos, boolean recursive, Function2<? super String, ? super DownloadStatus, Unit> onProgressUpdate) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(projectPath, "projectPath");
        Intrinsics.checkNotNullParameter(libString, "libString");
        Intrinsics.checkNotNullParameter(customRepos, "customRepos");
        Intrinsics.checkNotNullParameter(onProgressUpdate, "onProgressUpdate");
        LibInfo lib = parseLib(libString);
        if (lib == null) {
            onProgressUpdate.invoke(libString, new DownloadStatus.Error("Неверный формат ID"));
            return false;
        }
        return downloadLibInternal(context, projectPath, lib, new HashSet(), CollectionsKt.distinct(CollectionsKt.plus((Collection) customRepos, (Iterable) defaultRepos)), recursive, onProgressUpdate);
    }

    private final boolean downloadLibInternal(Context context, String projectPath, LibInfo lib, Set<String> visited, List<String> repos, boolean recursive, Function2<? super String, ? super DownloadStatus, Unit> onProgressUpdate) {
        String strDownloadPom;
        String group = lib.getGroup();
        String artifact = lib.getArtifact();
        String classifier = lib.getClassifier();
        if (classifier == null) {
            classifier = "";
        }
        String str = group + ":" + artifact + ":" + classifier;
        if (visited.contains(str)) {
            return true;
        }
        visited.add(str);
        if (!downloadFile(context, projectPath, lib, repos, onProgressUpdate)) {
            return false;
        }
        if (recursive && (strDownloadPom = downloadPom(lib, repos)) != null && lib.getClassifier() == null) {
            for (Dep dep : parseDependenciesFromPom(strDownloadPom)) {
                if (!Intrinsics.areEqual(dep.getScope(), "test") && !Intrinsics.areEqual(dep.getScope(), "provided") && !dep.getOptional() && !StringsKt.contains$default((CharSequence) dep.getVersion(), (CharSequence) "$", false, 2, (Object) null)) {
                    downloadLibInternal(context, projectPath, new LibInfo(dep.getGroup(), dep.getArtifact(), dep.getVersion(), null, 8, null), visited, repos, true, onProgressUpdate);
                }
            }
        }
        return true;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [org.catrobat.catroid.ide.DependencyManager] */
    /* JADX WARN: Type inference failed for: r1v10 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v12 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v16 */
    /* JADX WARN: Type inference failed for: r1v17 */
    /* JADX WARN: Type inference failed for: r1v18 */
    /* JADX WARN: Type inference failed for: r1v2, types: [org.catrobat.catroid.ide.DependencyManager] */
    /* JADX WARN: Type inference failed for: r1v5 */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v8 */
    /* JADX WARN: Type inference failed for: r1v9 */
    /* JADX WARN: Type inference failed for: r20v0, types: [org.catrobat.catroid.ide.DependencyManager] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v2, types: [int] */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    private final boolean downloadFile(Context context, String projectPath, LibInfo lib, List<String> repos, final Function2<? super String, ? super DownloadStatus, Unit> onProgressUpdate) {
        Throwable th;
        this = this;
        File file = new File(projectPath, "libs");
        if (!file.exists()) {
            file.mkdirs();
        }
        final String str = lib.getClassifier() != null ? lib.getArtifact() + Parameters.DEFAULT_OPTION_PREFIXES + lib.getVersion() + Parameters.DEFAULT_OPTION_PREFIXES + lib.getClassifier() + ".jar" : lib.getArtifact() + Parameters.DEFAULT_OPTION_PREFIXES + lib.getVersion() + ".jar";
        File file2 = new File(file, str);
        int i = 1;
        if (file2.exists() && file2.length() > 0) {
            onProgressUpdate.invoke(str, DownloadStatus.Success.INSTANCE);
            return true;
        }
        Iterator<String> it = repos.iterator();
        do {
            char c = '/';
            if (!it.hasNext()) {
                if (lib.getClassifier() == null) {
                    File file3 = new File(context.getCacheDir(), "temp_" + lib.getArtifact() + ".aar");
                    for (String str2 : repos) {
                        char[] cArr = new char[i];
                        cArr[0] = c;
                        String strTrimEnd = StringsKt.trimEnd(str2, cArr);
                        final String str3 = lib.getArtifact() + Parameters.DEFAULT_OPTION_PREFIXES + lib.getVersion() + ".aar";
                        String str4 = strTrimEnd + "/" + StringsKt.replace$default(lib.getGroup(), '.', '/', false, 4, (Object) null) + "/" + lib.getArtifact() + "/" + lib.getVersion() + "/" + str3;
                        onProgressUpdate.invoke(str3, DownloadStatus.Connecting.INSTANCE);
                        if (this.tryDownload(str4, file3, new Function1<DownloadStatus, Unit>() { // from class: org.catrobat.catroid.ide.DependencyManager$downloadFile$downloaded$2
                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                            /* JADX WARN: Multi-variable type inference failed */
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public /* bridge */ /* synthetic */ Unit invoke(DownloadStatus downloadStatus) {
                                invoke2(downloadStatus);
                                return Unit.INSTANCE;
                            }

                            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                            public final void invoke2(DownloadStatus status) {
                                Intrinsics.checkNotNullParameter(status, "status");
                                onProgressUpdate.invoke(str3, status);
                            }
                        })) {
                            try {
                                onProgressUpdate.invoke(str, DownloadStatus.Connecting.INSTANCE);
                                ZipFile zipFile = new ZipFile(file3);
                                try {
                                    ZipFile zipFile2 = zipFile;
                                    ZipEntry entry = zipFile2.getEntry("classes.jar");
                                    if (entry != null) {
                                        InputStream inputStream = zipFile2.getInputStream(entry);
                                        try {
                                            InputStream inputStream2 = inputStream;
                                            FileOutputStream fileOutputStream = new FileOutputStream(file2);
                                            try {
                                                Intrinsics.checkNotNull(inputStream2);
                                                ByteStreamsKt.copyTo$default(inputStream2, fileOutputStream, 0, 2, null);
                                                CloseableKt.closeFinally(fileOutputStream, null);
                                                CloseableKt.closeFinally(inputStream, null);
                                                onProgressUpdate.invoke(str, DownloadStatus.Success.INSTANCE);
                                                file3.delete();
                                                CloseableKt.closeFinally(zipFile, null);
                                                return true;
                                            } catch (Throwable th2) {
                                                this = 1;
                                                try {
                                                    throw th2;
                                                } catch (Throwable th3) {
                                                    try {
                                                        CloseableKt.closeFinally(fileOutputStream, th2);
                                                        throw th3;
                                                    } catch (Throwable th4) {
                                                        th = th4;
                                                        Throwable th5 = th;
                                                        try {
                                                            throw th5;
                                                        } catch (Throwable th6) {
                                                            CloseableKt.closeFinally(inputStream, th5);
                                                            throw th6;
                                                        }
                                                    }
                                                }
                                            }
                                        } catch (Throwable th7) {
                                            th = th7;
                                            this = 1;
                                        }
                                    } else {
                                        try {
                                            this = 1;
                                            ?? r1 = 1;
                                            Unit unit = Unit.INSTANCE;
                                            try {
                                                CloseableKt.closeFinally(zipFile, null);
                                            } catch (Exception unused) {
                                                onProgressUpdate.invoke(str, new DownloadStatus.Error("Ошибка распаковки AAR"));
                                                r1 = this;
                                            }
                                            file3.delete();
                                            i = r1;
                                            c = '/';
                                        } catch (Throwable th8) {
                                            th = th8;
                                            th = th;
                                            throw th;
                                        }
                                    }
                                    th = th8;
                                } catch (Throwable th9) {
                                    th = th9;
                                    this = 1;
                                }
                                th = th;
                                try {
                                    throw th;
                                } catch (Throwable th10) {
                                    CloseableKt.closeFinally(zipFile, th);
                                    throw th10;
                                }
                            } catch (Exception unused2) {
                                this = 1;
                            }
                        } else {
                            i = 1;
                            c = '/';
                        }
                    }
                }
                onProgressUpdate.invoke(str, new DownloadStatus.Error("Файл не найден"));
                return false;
            }
        } while (!this.tryDownload(StringsKt.trimEnd(it.next(), '/') + "/" + StringsKt.replace$default(lib.getGroup(), '.', '/', false, 4, (Object) null) + "/" + lib.getArtifact() + "/" + lib.getVersion() + "/" + (lib.getClassifier() != null ? lib.getArtifact() + Parameters.DEFAULT_OPTION_PREFIXES + lib.getVersion() + Parameters.DEFAULT_OPTION_PREFIXES + lib.getClassifier() + ".jar" : lib.getArtifact() + Parameters.DEFAULT_OPTION_PREFIXES + lib.getVersion() + ".jar"), file2, new Function1<DownloadStatus, Unit>() { // from class: org.catrobat.catroid.ide.DependencyManager$downloadFile$downloaded$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(DownloadStatus downloadStatus) {
                invoke2(downloadStatus);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(DownloadStatus status) {
                Intrinsics.checkNotNullParameter(status, "status");
                onProgressUpdate.invoke(str, status);
            }
        }));
        return true;
    }

    private final boolean tryDownload(String urlStr, File destination, Function1<? super DownloadStatus, Unit> onProgress) {
        try {
            onProgress.invoke(DownloadStatus.Connecting.INSTANCE);
            URLConnection uRLConnectionOpenConnection = new URL(urlStr).openConnection();
            Intrinsics.checkNotNull(uRLConnectionOpenConnection, "null cannot be cast to non-null type java.net.HttpURLConnection");
            HttpURLConnection httpURLConnection = (HttpURLConnection) uRLConnectionOpenConnection;
            httpURLConnection.setConnectTimeout(5000);
            httpURLConnection.setReadTimeout(5000);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() == 200) {
                int contentLength = httpURLConnection.getContentLength();
                InputStream inputStream = httpURLConnection.getInputStream();
                try {
                    InputStream inputStream2 = inputStream;
                    FileOutputStream fileOutputStream = new FileOutputStream(destination);
                    try {
                        FileOutputStream fileOutputStream2 = fileOutputStream;
                        byte[] bArr = new byte[8192];
                        long j = 0;
                        while (true) {
                            int i = inputStream2.read(bArr);
                            if (i != -1) {
                                fileOutputStream2.write(bArr, 0, i);
                                j += (long) i;
                                if (contentLength > 0) {
                                    onProgress.invoke(new DownloadStatus.Downloading(j / contentLength));
                                } else {
                                    onProgress.invoke(DownloadStatus.Connecting.INSTANCE);
                                }
                            } else {
                                Unit unit = Unit.INSTANCE;
                                CloseableKt.closeFinally(fileOutputStream, null);
                                Unit unit2 = Unit.INSTANCE;
                                CloseableKt.closeFinally(inputStream, null);
                                onProgress.invoke(DownloadStatus.Success.INSTANCE);
                                return true;
                            }
                            try {
                                throw th;
                            } catch (Throwable th) {
                                CloseableKt.closeFinally(inputStream, th);
                                throw th;
                            }
                        }
                    } catch (Throwable th2) {
                        try {
                            throw th2;
                        } catch (Throwable th3) {
                            CloseableKt.closeFinally(fileOutputStream, th2);
                            throw th3;
                        }
                    }
                } catch (Throwable th4) {
                    throw th4;
                }
            }
        } catch (Exception e) {
            String message = e.getMessage();
            if (message == null) {
                message = "Unknown error";
            }
            onProgress.invoke(new DownloadStatus.Error(message));
        }
        return false;
    }

    private final String downloadPom(LibInfo lib, List<String> repos) {
        Iterator<String> it = repos.iterator();
        while (it.hasNext()) {
            try {
                String str = new String(TextStreamsKt.readBytes(new URL(StringsKt.trimEnd(it.next(), '/') + "/" + StringsKt.replace$default(lib.getGroup(), '.', '/', false, 4, (Object) null) + "/" + lib.getArtifact() + "/" + lib.getVersion() + "/" + lib.getArtifact() + Parameters.DEFAULT_OPTION_PREFIXES + lib.getVersion() + ".pom")), Charsets.UTF_8);
                if (str.length() > 0) {
                    return str;
                }
            } catch (Exception unused) {
            }
        }
        return null;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/DependencyManager$Dep.class
      classes14.dex
     */
    /* JADX INFO: compiled from: DependencyManager.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0011\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B1\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\t\u0010\u0011\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0012\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0014\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0015\u001a\u00020\bHÆ\u0003J;\u0010\u0016\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\bHÆ\u0001J\u0013\u0010\u0017\u001a\u00020\b2\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001J\t\u0010\u001b\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u001c"}, d2 = {"Lorg/catrobat/catroid/ide/DependencyManager$Dep;", "", "group", "", "artifact", "version", "scope", SchemaSymbols.ATTVAL_OPTIONAL, "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)V", "getArtifact", "()Ljava/lang/String;", "getGroup", "getOptional", "()Z", "getScope", "getVersion", "component1", "component2", "component3", "component4", "component5", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class Dep {
        private final String artifact;
        private final String group;
        private final boolean optional;
        private final String scope;
        private final String version;

        public static /* synthetic */ Dep copy$default(Dep dep, String str, String str2, String str3, String str4, boolean z, int i, Object obj) {
            if ((i & 1) != 0) {
                str = dep.group;
            }
            if ((i & 2) != 0) {
                str2 = dep.artifact;
            }
            String str5 = str2;
            if ((i & 4) != 0) {
                str3 = dep.version;
            }
            String str6 = str3;
            if ((i & 8) != 0) {
                str4 = dep.scope;
            }
            String str7 = str4;
            if ((i & 16) != 0) {
                z = dep.optional;
            }
            return dep.copy(str, str5, str6, str7, z);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getGroup() {
            return this.group;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getArtifact() {
            return this.artifact;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final String getVersion() {
            return this.version;
        }

        /* JADX INFO: renamed from: component4, reason: from getter */
        public final String getScope() {
            return this.scope;
        }

        /* JADX INFO: renamed from: component5, reason: from getter */
        public final boolean getOptional() {
            return this.optional;
        }

        public final Dep copy(String group, String artifact, String version, String scope, boolean optional) {
            Intrinsics.checkNotNullParameter(group, "group");
            Intrinsics.checkNotNullParameter(artifact, "artifact");
            Intrinsics.checkNotNullParameter(version, "version");
            Intrinsics.checkNotNullParameter(scope, "scope");
            return new Dep(group, artifact, version, scope, optional);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Dep)) {
                return false;
            }
            Dep dep = (Dep) other;
            return Intrinsics.areEqual(this.group, dep.group) && Intrinsics.areEqual(this.artifact, dep.artifact) && Intrinsics.areEqual(this.version, dep.version) && Intrinsics.areEqual(this.scope, dep.scope) && this.optional == dep.optional;
        }

        public int hashCode() {
            return (((((((this.group.hashCode() * 31) + this.artifact.hashCode()) * 31) + this.version.hashCode()) * 31) + this.scope.hashCode()) * 31) + Boolean.hashCode(this.optional);
        }

        public String toString() {
            return "Dep(group=" + this.group + ", artifact=" + this.artifact + ", version=" + this.version + ", scope=" + this.scope + ", optional=" + this.optional + Constant.AFTER_QUTO;
        }

        public Dep(String group, String artifact, String version, String scope, boolean z) {
            Intrinsics.checkNotNullParameter(group, "group");
            Intrinsics.checkNotNullParameter(artifact, "artifact");
            Intrinsics.checkNotNullParameter(version, "version");
            Intrinsics.checkNotNullParameter(scope, "scope");
            this.group = group;
            this.artifact = artifact;
            this.version = version;
            this.scope = scope;
            this.optional = z;
        }

        public /* synthetic */ Dep(String str, String str2, String str3, String str4, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(str, str2, str3, (i & 8) != 0 ? "compile" : str4, (i & 16) != 0 ? false : z);
        }

        public final String getArtifact() {
            return this.artifact;
        }

        public final String getGroup() {
            return this.group;
        }

        public final boolean getOptional() {
            return this.optional;
        }

        public final String getScope() {
            return this.scope;
        }

        public final String getVersion() {
            return this.version;
        }
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    private final List<Dep> parseDependenciesFromPom(String pomXml) {
        ArrayList arrayList = new ArrayList();
        try {
            XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
            xmlPullParserNewPullParser.setInput(new StringReader(pomXml));
            String strNextText = "";
            String strNextText2 = strNextText;
            String strNextText3 = strNextText2;
            String strNextText4 = strNextText3;
            String strNextText5 = strNextText4;
            boolean z = false;
            boolean z2 = false;
            for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
                String name2 = xmlPullParserNewPullParser.getName();
                if (eventType == 2) {
                    if (Intrinsics.areEqual(name2, "dependencies")) {
                        z = true;
                    }
                    if (z && Intrinsics.areEqual(name2, "dependency")) {
                        z2 = true;
                    }
                    if (z2 && name2 != null) {
                        switch (name2.hashCode()) {
                            case -79017120:
                                if (name2.equals(SchemaSymbols.ATTVAL_OPTIONAL)) {
                                    strNextText = xmlPullParserNewPullParser.nextText();
                                    Intrinsics.checkNotNullExpressionValue(strNextText, "nextText(...)");
                                }
                                break;
                            case 109264468:
                                if (name2.equals("scope")) {
                                    strNextText5 = xmlPullParserNewPullParser.nextText();
                                    Intrinsics.checkNotNullExpressionValue(strNextText5, "nextText(...)");
                                }
                                break;
                            case 240640653:
                                if (name2.equals("artifactId")) {
                                    strNextText3 = xmlPullParserNewPullParser.nextText();
                                    Intrinsics.checkNotNullExpressionValue(strNextText3, "nextText(...)");
                                }
                                break;
                            case 293428218:
                                if (name2.equals("groupId")) {
                                    strNextText2 = xmlPullParserNewPullParser.nextText();
                                    Intrinsics.checkNotNullExpressionValue(strNextText2, "nextText(...)");
                                }
                                break;
                            case 351608024:
                                if (name2.equals("version")) {
                                    strNextText4 = xmlPullParserNewPullParser.nextText();
                                    Intrinsics.checkNotNullExpressionValue(strNextText4, "nextText(...)");
                                }
                                break;
                        }
                    }
                } else if (eventType == 3) {
                    if (Intrinsics.areEqual(name2, "dependencies")) {
                        z = false;
                    }
                    if (Intrinsics.areEqual(name2, "dependency") && z) {
                        if (strNextText2.length() > 0 && strNextText3.length() > 0 && strNextText4.length() > 0) {
                            arrayList.add(new Dep(strNextText2, strNextText3, strNextText4, strNextText5, Intrinsics.areEqual(strNextText, "true")));
                        }
                        strNextText = "";
                        strNextText2 = strNextText;
                        strNextText3 = strNextText2;
                        strNextText4 = strNextText3;
                        strNextText5 = strNextText4;
                        z2 = false;
                    }
                }
            }
        } catch (Exception unused) {
        }
        return arrayList;
    }

    public final List<File> getLibraries(String projectPath) {
        Intrinsics.checkNotNullParameter(projectPath, "projectPath");
        File[] fileArrListFiles = new File(projectPath, "libs").listFiles();
        if (fileArrListFiles != null) {
            ArrayList arrayList = new ArrayList();
            for (File file : fileArrListFiles) {
                Intrinsics.checkNotNull(file);
                if (Intrinsics.areEqual(FilesKt.getExtension(file), "jar")) {
                    arrayList.add(file);
                }
            }
            List<File> list = CollectionsKt.toList(arrayList);
            if (list != null) {
                return list;
            }
        }
        return CollectionsKt.emptyList();
    }
}
