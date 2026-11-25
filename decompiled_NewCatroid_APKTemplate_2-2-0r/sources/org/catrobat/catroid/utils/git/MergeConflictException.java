package org.catrobat.catroid.utils.git;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/utils/git/MergeConflictException.class
  classes14.dex
 */
/* JADX INFO: compiled from: GitResult.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00060\u0001j\u0002`\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\u0002\u0010\u0006R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\t"}, d2 = {"Lorg/catrobat/catroid/utils/git/MergeConflictException;", "Ljava/lang/Exception;", "Lkotlin/Exception;", "conflicts", "", "Lorg/catrobat/catroid/utils/git/Conflict;", "(Ljava/util/List;)V", "getConflicts", "()Ljava/util/List;", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MergeConflictException extends Exception {
    private final List<Conflict> conflicts;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MergeConflictException(List<Conflict> conflicts) {
        super("Merge conflicts detected");
        Intrinsics.checkNotNullParameter(conflicts, "conflicts");
        this.conflicts = conflicts;
    }

    public final List<Conflict> getConflicts() {
        return this.conflicts;
    }
}
