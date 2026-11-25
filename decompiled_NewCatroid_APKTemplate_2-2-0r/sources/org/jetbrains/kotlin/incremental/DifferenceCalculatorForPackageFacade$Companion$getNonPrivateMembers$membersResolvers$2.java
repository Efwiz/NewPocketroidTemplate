package org.jetbrains.kotlin.incremental;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.metadata.ProtoBuf;

/* JADX INFO: compiled from: protoDifferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class DifferenceCalculatorForPackageFacade$Companion$getNonPrivateMembers$membersResolvers$2 extends FunctionReference implements Function1<ProtoBuf.Package, List<ProtoBuf.Property>> {
    public static final DifferenceCalculatorForPackageFacade$Companion$getNonPrivateMembers$membersResolvers$2 INSTANCE = new DifferenceCalculatorForPackageFacade$Companion$getNonPrivateMembers$membersResolvers$2();

    DifferenceCalculatorForPackageFacade$Companion$getNonPrivateMembers$membersResolvers$2() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "getPropertyList()Ljava/util/List;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "getPropertyList";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(ProtoBuf.Package.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final List<ProtoBuf.Property> invoke(ProtoBuf.Package p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return p0.getPropertyList();
    }
}
