package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;

/* JADX INFO: compiled from: TypeVariable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\b\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableForLambdaReturnType;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewTypeVariable;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "name", "", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Ljava/lang/String;)V", "hasOnlyInputTypesAnnotation", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeVariableForLambdaReturnType extends NewTypeVariable {
    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.NewTypeVariable
    public boolean hasOnlyInputTypesAnnotation() {
        return false;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeVariableForLambdaReturnType(KotlinBuiltIns builtIns, String name2) {
        super(builtIns, name2, null, 4, null);
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(name2, "name");
    }
}
