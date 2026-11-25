package org.jetbrains.kotlin.cli.jvm.config;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JvmContentRoots.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u00012\u00020\u0002B\u000f\b\u0016\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\f\u001a\u00020\u0004HÆ\u0003J\t\u0010\r\u001a\u00020\u0007HÆ\u0003J\u001d\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00072\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/config/JvmClasspathRoot;", "Lorg/jetbrains/kotlin/cli/jvm/config/JvmContentRoot;", "Lorg/jetbrains/kotlin/cli/jvm/config/JvmClasspathRootBase;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "isSdkRoot", "", "(Ljava/io/File;Z)V", "getFile", "()Ljava/io/File;", "()Z", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JvmClasspathRoot implements JvmClasspathRootBase, JvmContentRoot {
    private final File file;
    private final boolean isSdkRoot;

    public static /* synthetic */ JvmClasspathRoot copy$default(JvmClasspathRoot jvmClasspathRoot, File file, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            file = jvmClasspathRoot.file;
        }
        if ((i & 2) != 0) {
            z = jvmClasspathRoot.isSdkRoot;
        }
        return jvmClasspathRoot.copy(file, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final File getFile() {
        return this.file;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsSdkRoot() {
        return this.isSdkRoot;
    }

    public final JvmClasspathRoot copy(File file, boolean isSdkRoot) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new JvmClasspathRoot(file, isSdkRoot);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JvmClasspathRoot)) {
            return false;
        }
        JvmClasspathRoot jvmClasspathRoot = (JvmClasspathRoot) other;
        return Intrinsics.areEqual(this.file, jvmClasspathRoot.file) && this.isSdkRoot == jvmClasspathRoot.isSdkRoot;
    }

    public int hashCode() {
        return (this.file.hashCode() * 31) + Boolean.hashCode(this.isSdkRoot);
    }

    public String toString() {
        return "JvmClasspathRoot(file=" + this.file + ", isSdkRoot=" + this.isSdkRoot + ')';
    }

    public JvmClasspathRoot(File file, boolean z) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.file = file;
        this.isSdkRoot = z;
    }

    @Override // org.jetbrains.kotlin.cli.jvm.config.JvmContentRoot
    public File getFile() {
        return this.file;
    }

    @Override // org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRootBase
    public boolean isSdkRoot() {
        return this.isSdkRoot;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public JvmClasspathRoot(File file) {
        this(file, false);
        Intrinsics.checkNotNullParameter(file, "file");
    }
}
