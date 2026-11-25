package org.catrobat.catroid.ide;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.io.File;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ide/CompilationResult.class
  classes14.dex
 */
/* JADX INFO: compiled from: ScriptExecutor.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B-\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\u0002\u0010\bJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0007HÆ\u0003J5\u0010\u0011\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0007HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/catrobat/catroid/ide/CompilationResult;", "", "dexFiles", "", "Ljava/io/File;", "extraAssets", "error", "", "(Ljava/util/List;Ljava/util/List;Ljava/lang/Throwable;)V", "getDexFiles", "()Ljava/util/List;", "getError", "()Ljava/lang/Throwable;", "getExtraAssets", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class CompilationResult {
    private final List<File> dexFiles;
    private final Throwable error;
    private final List<File> extraAssets;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ CompilationResult copy$default(CompilationResult compilationResult, List list, List list2, Throwable th, int i, Object obj) {
        if ((i & 1) != 0) {
            list = compilationResult.dexFiles;
        }
        if ((i & 2) != 0) {
            list2 = compilationResult.extraAssets;
        }
        if ((i & 4) != 0) {
            th = compilationResult.error;
        }
        return compilationResult.copy(list, list2, th);
    }

    public final List<File> component1() {
        return this.dexFiles;
    }

    public final List<File> component2() {
        return this.extraAssets;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final Throwable getError() {
        return this.error;
    }

    public final CompilationResult copy(List<? extends File> dexFiles, List<? extends File> extraAssets, Throwable error) {
        Intrinsics.checkNotNullParameter(dexFiles, "dexFiles");
        Intrinsics.checkNotNullParameter(extraAssets, "extraAssets");
        return new CompilationResult(dexFiles, extraAssets, error);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof CompilationResult)) {
            return false;
        }
        CompilationResult compilationResult = (CompilationResult) other;
        return Intrinsics.areEqual(this.dexFiles, compilationResult.dexFiles) && Intrinsics.areEqual(this.extraAssets, compilationResult.extraAssets) && Intrinsics.areEqual(this.error, compilationResult.error);
    }

    public int hashCode() {
        int iHashCode = ((this.dexFiles.hashCode() * 31) + this.extraAssets.hashCode()) * 31;
        Throwable th = this.error;
        return iHashCode + (th == null ? 0 : th.hashCode());
    }

    public String toString() {
        return "CompilationResult(dexFiles=" + this.dexFiles + ", extraAssets=" + this.extraAssets + ", error=" + this.error + Constant.AFTER_QUTO;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public CompilationResult(List<? extends File> dexFiles, List<? extends File> extraAssets, Throwable th) {
        Intrinsics.checkNotNullParameter(dexFiles, "dexFiles");
        Intrinsics.checkNotNullParameter(extraAssets, "extraAssets");
        this.dexFiles = dexFiles;
        this.extraAssets = extraAssets;
        this.error = th;
    }

    public /* synthetic */ CompilationResult(List list, List list2, Throwable th, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(list, list2, (i & 4) != 0 ? null : th);
    }

    public final List<File> getDexFiles() {
        return this.dexFiles;
    }

    public final List<File> getExtraAssets() {
        return this.extraAssets;
    }

    public final Throwable getError() {
        return this.error;
    }
}
