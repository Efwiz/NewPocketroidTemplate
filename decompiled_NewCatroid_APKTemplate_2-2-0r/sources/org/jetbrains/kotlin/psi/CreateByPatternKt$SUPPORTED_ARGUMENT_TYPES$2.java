package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.renderer.RenderingUtilsKt;

/* JADX INFO: compiled from: createByPattern.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class CreateByPatternKt$SUPPORTED_ARGUMENT_TYPES$2 extends FunctionReference implements Function1<Name, String> {
    public static final CreateByPatternKt$SUPPORTED_ARGUMENT_TYPES$2 INSTANCE = new CreateByPatternKt$SUPPORTED_ARGUMENT_TYPES$2();

    CreateByPatternKt$SUPPORTED_ARGUMENT_TYPES$2() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "render(Lorg/jetbrains/kotlin/name/Name;)Ljava/lang/String;";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "render";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinPackage(RenderingUtilsKt.class, "psi");
    }

    @Override // kotlin.jvm.functions.Function1
    public final String invoke(Name p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return RenderingUtilsKt.render(p0);
    }
}
