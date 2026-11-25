package org.jetbrains.kotlin.ir.backend.js.dce;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;

/* JADX INFO: compiled from: UsefulDeclarationProcessor.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class UsefulDeclarationProcessor$collectDeclarations$stringify$2 extends FunctionReference implements Function2<List<? extends ReachabilityInfo>, DceDumpNameCache, String> {
    public static final UsefulDeclarationProcessor$collectDeclarations$stringify$2 INSTANCE = new UsefulDeclarationProcessor$collectDeclarations$stringify$2();

    UsefulDeclarationProcessor$collectDeclarations$stringify$2() {
        super(2);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "transformToJsConstDeclaration(Ljava/util/List;Lorg/jetbrains/kotlin/ir/backend/js/dce/DceDumpNameCache;)Ljava/lang/String;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "transformToJsConstDeclaration";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(UsefulDeclarationProcessorKt.class, "backend.js");
    }

    @Override // kotlin.jvm.functions.Function2
    public /* bridge */ /* synthetic */ String invoke(List<? extends ReachabilityInfo> list, DceDumpNameCache dceDumpNameCache) {
        return invoke2((List<ReachabilityInfo>) list, dceDumpNameCache);
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final String invoke2(List<ReachabilityInfo> p0, DceDumpNameCache p1) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        Intrinsics.checkNotNullParameter(p1, "p1");
        return UsefulDeclarationProcessorKt.transformToJsConstDeclaration(p0, p1);
    }
}
