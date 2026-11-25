package org.jetbrains.kotlin.backend.common;

import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: FunctionsFromAnyGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FunctionsFromAnyGenerator$generateHashCodeIfNeeded$function$1 extends FunctionReference implements Function1<KotlinType, Boolean> {
    public static final FunctionsFromAnyGenerator$generateHashCodeIfNeeded$function$1 INSTANCE = new FunctionsFromAnyGenerator$generateHashCodeIfNeeded$function$1();

    FunctionsFromAnyGenerator$generateHashCodeIfNeeded$function$1() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "isInt(Lorg/jetbrains/kotlin/types/KotlinType;)Z";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "isInt";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(KotlinBuiltIns.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(KotlinType p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Boolean.valueOf(KotlinBuiltIns.isInt(p0));
    }
}
