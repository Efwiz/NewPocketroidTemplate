package org.jetbrains.kotlin.modules;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: Module.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\n\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u001f\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/modules/JavaRootPath;", "", "path", "", ModuleXmlParser.JAVA_SOURCE_PACKAGE_PREFIX, "(Ljava/lang/String;Ljava/lang/String;)V", "getPackagePrefix", "()Ljava/lang/String;", "getPath", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JavaRootPath {
    private final String packagePrefix;
    private final String path;

    public static /* synthetic */ JavaRootPath copy$default(JavaRootPath javaRootPath, String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = javaRootPath.path;
        }
        if ((i & 2) != 0) {
            str2 = javaRootPath.packagePrefix;
        }
        return javaRootPath.copy(str, str2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getPath() {
        return this.path;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getPackagePrefix() {
        return this.packagePrefix;
    }

    public final JavaRootPath copy(String path, String packagePrefix) {
        Intrinsics.checkNotNullParameter(path, "path");
        return new JavaRootPath(path, packagePrefix);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JavaRootPath)) {
            return false;
        }
        JavaRootPath javaRootPath = (JavaRootPath) other;
        return Intrinsics.areEqual(this.path, javaRootPath.path) && Intrinsics.areEqual(this.packagePrefix, javaRootPath.packagePrefix);
    }

    public int hashCode() {
        int iHashCode = this.path.hashCode() * 31;
        String str = this.packagePrefix;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "JavaRootPath(path=" + this.path + ", packagePrefix=" + this.packagePrefix + ')';
    }

    public JavaRootPath(String path, String str) {
        Intrinsics.checkNotNullParameter(path, "path");
        this.path = path;
        this.packagePrefix = str;
    }

    public /* synthetic */ JavaRootPath(String str, String str2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2);
    }

    public final String getPackagePrefix() {
        return this.packagePrefix;
    }

    public final String getPath() {
        return this.path;
    }
}
