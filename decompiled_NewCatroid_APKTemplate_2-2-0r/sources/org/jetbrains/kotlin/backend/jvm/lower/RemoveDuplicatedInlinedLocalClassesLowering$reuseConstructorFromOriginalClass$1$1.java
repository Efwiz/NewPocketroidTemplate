package org.jetbrains.kotlin.backend.jvm.lower;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.name.NameUtils;

/* JADX INFO: compiled from: RemoveDuplicatedInlinedLocalClassesLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class RemoveDuplicatedInlinedLocalClassesLowering$reuseConstructorFromOriginalClass$1$1 extends FunctionReference implements Function1<String, String> {
    RemoveDuplicatedInlinedLocalClassesLowering$reuseConstructorFromOriginalClass$1$1(Object obj) {
        super(1, obj);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "sanitizeAsJavaIdentifier(Ljava/lang/String;)Ljava/lang/String;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "sanitizeAsJavaIdentifier";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(NameUtils.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final String invoke(String p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return NameUtils.sanitizeAsJavaIdentifier(p0);
    }
}
