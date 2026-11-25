package org.jetbrains.kotlin.backend.common.linkage.partial;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: PartiallyLinkedIrTreePatcher.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\n\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u0006\u0010\u0003\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "Lorg/jetbrains/kotlin/backend/common/linkage/partial/PartiallyLinkedIrTreePatcher$ReturnTargetContext;", "R", "it", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class PartiallyLinkedIrTreePatcher$NonLocalReturnsPatcher$withContext$1 extends Lambda implements Function1<PartiallyLinkedIrTreePatcher.ReturnTargetContext, PartiallyLinkedIrTreePatcher.ReturnTargetContext> {
    public static final PartiallyLinkedIrTreePatcher$NonLocalReturnsPatcher$withContext$1 INSTANCE = new PartiallyLinkedIrTreePatcher$NonLocalReturnsPatcher$withContext$1();

    public PartiallyLinkedIrTreePatcher$NonLocalReturnsPatcher$withContext$1() {
        super(1);
    }

    @Override // kotlin.jvm.functions.Function1
    public final PartiallyLinkedIrTreePatcher.ReturnTargetContext invoke(PartiallyLinkedIrTreePatcher.ReturnTargetContext it) {
        Intrinsics.checkNotNullParameter(it, "it");
        return it;
    }
}
