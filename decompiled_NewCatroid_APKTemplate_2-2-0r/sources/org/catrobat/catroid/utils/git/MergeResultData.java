package org.catrobat.catroid.utils.git;

import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.catrobat.catroid.content.Project;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/MergeResultData.class
  classes14.dex
 */
/* JADX INFO: compiled from: GitResult.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0003J#\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\u0016"}, d2 = {"Lorg/catrobat/catroid/utils/git/MergeResultData;", "", "mergedProject", "Lorg/catrobat/catroid/content/Project;", "conflicts", "", "Lorg/catrobat/catroid/utils/git/Conflict;", "(Lorg/catrobat/catroid/content/Project;Ljava/util/List;)V", "getConflicts", "()Ljava/util/List;", "getMergedProject", "()Lorg/catrobat/catroid/content/Project;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class MergeResultData {
    private final List<Conflict> conflicts;
    private final Project mergedProject;

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ MergeResultData copy$default(MergeResultData mergeResultData, Project project, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            project = mergeResultData.mergedProject;
        }
        if ((i & 2) != 0) {
            list = mergeResultData.conflicts;
        }
        return mergeResultData.copy(project, list);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final Project getMergedProject() {
        return this.mergedProject;
    }

    public final List<Conflict> component2() {
        return this.conflicts;
    }

    public final MergeResultData copy(Project mergedProject, List<Conflict> conflicts) {
        Intrinsics.checkNotNullParameter(mergedProject, "mergedProject");
        Intrinsics.checkNotNullParameter(conflicts, "conflicts");
        return new MergeResultData(mergedProject, conflicts);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MergeResultData)) {
            return false;
        }
        MergeResultData mergeResultData = (MergeResultData) other;
        return Intrinsics.areEqual(this.mergedProject, mergeResultData.mergedProject) && Intrinsics.areEqual(this.conflicts, mergeResultData.conflicts);
    }

    public int hashCode() {
        return (this.mergedProject.hashCode() * 31) + this.conflicts.hashCode();
    }

    public String toString() {
        return "MergeResultData(mergedProject=" + this.mergedProject + ", conflicts=" + this.conflicts + Constant.AFTER_QUTO;
    }

    public MergeResultData(Project mergedProject, List<Conflict> conflicts) {
        Intrinsics.checkNotNullParameter(mergedProject, "mergedProject");
        Intrinsics.checkNotNullParameter(conflicts, "conflicts");
        this.mergedProject = mergedProject;
        this.conflicts = conflicts;
    }

    public final Project getMergedProject() {
        return this.mergedProject;
    }

    public final List<Conflict> getConflicts() {
        return this.conflicts;
    }
}
