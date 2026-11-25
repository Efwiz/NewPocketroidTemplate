package org.jetbrains.kotlin.cli.jvm.config;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JvmContentRoots.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\t\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0006HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0006HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u00062\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\n¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/config/VirtualJvmClasspathRoot;", "Lorg/jetbrains/kotlin/cli/jvm/config/JvmClasspathRootBase;", "file", "Lorg/jetbrains/kotlin/com/intellij/openapi/vfs/VirtualFile;", "(Lcom/intellij/openapi/vfs/VirtualFile;)V", "isSdkRoot", "", "(Lcom/intellij/openapi/vfs/VirtualFile;Z)V", "getFile", "()Lcom/intellij/openapi/vfs/VirtualFile;", "()Z", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "", "hashCode", "", "toString", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class VirtualJvmClasspathRoot implements JvmClasspathRootBase {
    private final VirtualFile file;
    private final boolean isSdkRoot;

    public static /* synthetic */ VirtualJvmClasspathRoot copy$default(VirtualJvmClasspathRoot virtualJvmClasspathRoot, VirtualFile virtualFile, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            virtualFile = virtualJvmClasspathRoot.file;
        }
        if ((i & 2) != 0) {
            z = virtualJvmClasspathRoot.isSdkRoot;
        }
        return virtualJvmClasspathRoot.copy(virtualFile, z);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final VirtualFile getFile() {
        return this.file;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final boolean getIsSdkRoot() {
        return this.isSdkRoot;
    }

    public final VirtualJvmClasspathRoot copy(VirtualFile file, boolean isSdkRoot) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new VirtualJvmClasspathRoot(file, isSdkRoot);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof VirtualJvmClasspathRoot)) {
            return false;
        }
        VirtualJvmClasspathRoot virtualJvmClasspathRoot = (VirtualJvmClasspathRoot) other;
        return Intrinsics.areEqual(this.file, virtualJvmClasspathRoot.file) && this.isSdkRoot == virtualJvmClasspathRoot.isSdkRoot;
    }

    public int hashCode() {
        return (this.file.hashCode() * 31) + Boolean.hashCode(this.isSdkRoot);
    }

    public String toString() {
        return "VirtualJvmClasspathRoot(file=" + this.file + ", isSdkRoot=" + this.isSdkRoot + ')';
    }

    public VirtualJvmClasspathRoot(VirtualFile file, boolean z) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.file = file;
        this.isSdkRoot = z;
    }

    public final VirtualFile getFile() {
        return this.file;
    }

    @Override // org.jetbrains.kotlin.cli.jvm.config.JvmClasspathRootBase
    public boolean isSdkRoot() {
        return this.isSdkRoot;
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public VirtualJvmClasspathRoot(VirtualFile file) {
        this(file, false);
        Intrinsics.checkNotNullParameter(file, "file");
    }
}
