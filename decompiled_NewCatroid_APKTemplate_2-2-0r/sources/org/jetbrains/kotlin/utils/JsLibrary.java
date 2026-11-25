package org.jetbrains.kotlin.utils;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JsLibraryUtils.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B)\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u0011\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\u0007HÆ\u0003J5\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\nR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\n¨\u0006\u001a"}, d2 = {"Lorg/jetbrains/kotlin/utils/JsLibrary;", "", FirebaseAnalytics.Param.CONTENT, "", "path", "sourceMapContent", "file", "Ljava/io/File;", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/io/File;)V", "getContent", "()Ljava/lang/String;", "getFile", "()Ljava/io/File;", "getPath", "getSourceMapContent", "component1", "component2", "component3", "component4", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "js.config"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JsLibrary {
    private final String content;
    private final File file;
    private final String path;
    private final String sourceMapContent;

    public static /* synthetic */ JsLibrary copy$default(JsLibrary jsLibrary, String str, String str2, String str3, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            str = jsLibrary.content;
        }
        if ((i & 2) != 0) {
            str2 = jsLibrary.path;
        }
        if ((i & 4) != 0) {
            str3 = jsLibrary.sourceMapContent;
        }
        if ((i & 8) != 0) {
            file = jsLibrary.file;
        }
        return jsLibrary.copy(str, str2, str3, file);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getContent() {
        return this.content;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getPath() {
        return this.path;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getSourceMapContent() {
        return this.sourceMapContent;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final File getFile() {
        return this.file;
    }

    public final JsLibrary copy(String content, String path, String sourceMapContent, File file) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(path, "path");
        return new JsLibrary(content, path, sourceMapContent, file);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JsLibrary)) {
            return false;
        }
        JsLibrary jsLibrary = (JsLibrary) other;
        return Intrinsics.areEqual(this.content, jsLibrary.content) && Intrinsics.areEqual(this.path, jsLibrary.path) && Intrinsics.areEqual(this.sourceMapContent, jsLibrary.sourceMapContent) && Intrinsics.areEqual(this.file, jsLibrary.file);
    }

    public int hashCode() {
        int iHashCode = ((this.content.hashCode() * 31) + this.path.hashCode()) * 31;
        String str = this.sourceMapContent;
        int iHashCode2 = (iHashCode + (str == null ? 0 : str.hashCode())) * 31;
        File file = this.file;
        return iHashCode2 + (file != null ? file.hashCode() : 0);
    }

    public String toString() {
        return "JsLibrary(content=" + this.content + ", path=" + this.path + ", sourceMapContent=" + this.sourceMapContent + ", file=" + this.file + ')';
    }

    public JsLibrary(String content, String path, String str, File file) {
        Intrinsics.checkNotNullParameter(content, "content");
        Intrinsics.checkNotNullParameter(path, "path");
        this.content = content;
        this.path = path;
        this.sourceMapContent = str;
        this.file = file;
    }

    public final String getContent() {
        return this.content;
    }

    public final File getFile() {
        return this.file;
    }

    public final String getPath() {
        return this.path;
    }

    public final String getSourceMapContent() {
        return this.sourceMapContent;
    }
}
