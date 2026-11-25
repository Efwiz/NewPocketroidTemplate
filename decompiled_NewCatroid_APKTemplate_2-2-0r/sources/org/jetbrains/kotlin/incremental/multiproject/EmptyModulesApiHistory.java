package org.jetbrains.kotlin.incremental.multiproject;

import java.io.File;
import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.incremental.util.Either;

/* JADX INFO: compiled from: ModulesApiHistory.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u0003\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00042\u0006\u0010\u0007\u001a\u00020\u0006H\u0016J\"\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00060\u00050\u00042\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u0016¨\u0006\n"}, d2 = {"Lorg/jetbrains/kotlin/incremental/multiproject/EmptyModulesApiHistory;", "Lorg/jetbrains/kotlin/incremental/multiproject/ModulesApiHistory;", "()V", "abiSnapshot", "Lorg/jetbrains/kotlin/incremental/util/Either;", "", "Ljava/io/File;", "jar", "historyFilesForChangedFiles", "changedFiles", "incremental-compilation-impl"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class EmptyModulesApiHistory implements ModulesApiHistory {
    public static final EmptyModulesApiHistory INSTANCE = new EmptyModulesApiHistory();

    private EmptyModulesApiHistory() {
    }

    @Override // org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistory
    public Either<Set<File>> historyFilesForChangedFiles(Set<? extends File> changedFiles) {
        Intrinsics.checkNotNullParameter(changedFiles, "changedFiles");
        return new Either.Error("Multi-module IC is not configured");
    }

    @Override // org.jetbrains.kotlin.incremental.multiproject.ModulesApiHistory
    public Either<Set<File>> abiSnapshot(File jar) {
        Intrinsics.checkNotNullParameter(jar, "jar");
        return new Either.Error("Not supported");
    }
}
