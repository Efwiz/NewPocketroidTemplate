package org.jetbrains.kotlin.backend.common.linkage.issues;

import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.utils.ResolvedDependencyId;

/* JADX INFO: compiled from: KotlinIrLinkerIssues.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\n\b\u0002\u0018\u00002\u00020\u0001B\u001f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0000\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u000eR\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0000¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0011\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\u0012\u0010\u000b¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/linkage/issues/Data;", "", "parent", "incomingDependencyId", "Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "isLast", "", "(Lorg/jetbrains/kotlin/backend/common/linkage/issues/Data;Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;Z)V", "errorLinePrefix", "", "getErrorLinePrefix", "()Ljava/lang/String;", "getIncomingDependencyId", "()Lorg/jetbrains/kotlin/utils/ResolvedDependencyId;", "()Z", "getParent", "()Lorg/jetbrains/kotlin/backend/common/linkage/issues/Data;", "regularLinePrefix", "getRegularLinePrefix", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class Data {
    private final ResolvedDependencyId incomingDependencyId;
    private final boolean isLast;
    private final Data parent;

    public Data(Data data, ResolvedDependencyId incomingDependencyId, boolean z) {
        Intrinsics.checkNotNullParameter(incomingDependencyId, "incomingDependencyId");
        this.parent = data;
        this.incomingDependencyId = incomingDependencyId;
        this.isLast = z;
    }

    public final ResolvedDependencyId getIncomingDependencyId() {
        return this.incomingDependencyId;
    }

    public final Data getParent() {
        return this.parent;
    }

    /* JADX INFO: renamed from: isLast, reason: from getter */
    public final boolean getIsLast() {
        return this.isLast;
    }

    public final String getRegularLinePrefix() {
        return CollectionsKt.joinToString$default(CollectionsKt.asReversed(SequencesKt.toList(SequencesKt.map(SequencesKt.generateSequence(this, new Function1<Data, Data>() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.Data$regularLinePrefix$1
            @Override // kotlin.jvm.functions.Function1
            public final Data invoke(Data it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getParent();
            }
        }), new Function1<Data, String>() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.Data$regularLinePrefix$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Data it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it == this.this$0) {
                    return it.getIsLast() ? "\\--- " : "+--- ";
                }
                return it.getIsLast() ? "     " : "|    ";
            }
        }))), "", null, null, 0, null, null, 62, null);
    }

    public final String getErrorLinePrefix() {
        return CollectionsKt.joinToString$default(CollectionsKt.asReversed(SequencesKt.toList(SequencesKt.map(SequencesKt.generateSequence(this, new Function1<Data, Data>() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.Data$errorLinePrefix$1
            @Override // kotlin.jvm.functions.Function1
            public final Data invoke(Data it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getParent();
            }
        }), new Function1<Data, String>() { // from class: org.jetbrains.kotlin.backend.common.linkage.issues.Data$errorLinePrefix$2
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(Data it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.getIsLast() ? "     " : "|    ";
            }
        }))), "", null, null, 0, null, null, 62, null);
    }
}
