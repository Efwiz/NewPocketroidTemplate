package org.jetbrains.kotlin.cli.jvm.config;

import java.io.File;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: JvmContentRoots.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\fHÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/config/JvmModulePathRoot;", "Lorg/jetbrains/kotlin/cli/jvm/config/JvmContentRoot;", "file", "Ljava/io/File;", "(Ljava/io/File;)V", "getFile", "()Ljava/io/File;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class JvmModulePathRoot implements JvmContentRoot {
    private final File file;

    public static /* synthetic */ JvmModulePathRoot copy$default(JvmModulePathRoot jvmModulePathRoot, File file, int i, Object obj) {
        if ((i & 1) != 0) {
            file = jvmModulePathRoot.file;
        }
        return jvmModulePathRoot.copy(file);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final File getFile() {
        return this.file;
    }

    public final JvmModulePathRoot copy(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        return new JvmModulePathRoot(file);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        return (other instanceof JvmModulePathRoot) && Intrinsics.areEqual(this.file, ((JvmModulePathRoot) other).file);
    }

    public int hashCode() {
        return this.file.hashCode();
    }

    public String toString() {
        return "JvmModulePathRoot(file=" + this.file + ')';
    }

    public JvmModulePathRoot(File file) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.file = file;
    }

    @Override // org.jetbrains.kotlin.cli.jvm.config.JvmContentRoot
    public File getFile() {
        return this.file;
    }
}
