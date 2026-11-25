package org.jetbrains.kotlin.backend.common;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;

/* JADX INFO: compiled from: FunctionsFromAnyGenerator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
/* synthetic */ class FunctionsFromAnyGenerator$generateToStringIfNeeded$function$2 extends FunctionReference implements Function1<List<? extends ValueParameterDescriptor>, Boolean> {
    public static final FunctionsFromAnyGenerator$generateToStringIfNeeded$function$2 INSTANCE = new FunctionsFromAnyGenerator$generateToStringIfNeeded$function$2();

    FunctionsFromAnyGenerator$generateToStringIfNeeded$function$2() {
        super(1);
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final String getSignature() {
        return "isEmpty()Z";
    }

    @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
    public final String getName() {
        return "isEmpty";
    }

    @Override // kotlin.jvm.internal.CallableReference
    public final KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(List.class);
    }

    @Override // kotlin.jvm.functions.Function1
    public final Boolean invoke(List<? extends ValueParameterDescriptor> p0) {
        Intrinsics.checkNotNullParameter(p0, "p0");
        return Boolean.valueOf(p0.isEmpty());
    }
}
