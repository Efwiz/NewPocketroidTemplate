package org.jetbrains.kotlin.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;

/* JADX INFO: compiled from: FunctionPlaceholders.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u001c\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\t\u001a\u00020\nR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/types/FunctionPlaceholders;", "", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;)V", "createFunctionPlaceholderType", "Lorg/jetbrains/kotlin/types/KotlinType;", "argumentTypes", "", "hasDeclaredArguments", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FunctionPlaceholders {
    private final KotlinBuiltIns builtIns;

    public FunctionPlaceholders(KotlinBuiltIns builtIns) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        this.builtIns = builtIns;
    }

    public final KotlinType createFunctionPlaceholderType(List<? extends KotlinType> argumentTypes, boolean hasDeclaredArguments) {
        Intrinsics.checkNotNullParameter(argumentTypes, "argumentTypes");
        return org.jetbrains.kotlin.types.error.ErrorUtils.INSTANCE.createErrorType(ErrorTypeKind.FUNCTION_PLACEHOLDER_TYPE, new FunctionPlaceholderTypeConstructor(argumentTypes, hasDeclaredArguments, this.builtIns), argumentTypes.toString());
    }
}
