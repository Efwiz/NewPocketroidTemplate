package org.jetbrains.kotlin.resolve.calls.inference.model;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.resolve.calls.model.PostponableKotlinCallArgument;

/* JADX INFO: compiled from: TypeVariable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/model/TypeVariableForLambdaParameterType;", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewTypeVariable;", "atom", "Lorg/jetbrains/kotlin/resolve/calls/model/PostponableKotlinCallArgument;", "index", "", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "name", "", "(Lorg/jetbrains/kotlin/resolve/calls/model/PostponableKotlinCallArgument;ILorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Ljava/lang/String;)V", "getAtom", "()Lorg/jetbrains/kotlin/resolve/calls/model/PostponableKotlinCallArgument;", "getIndex", "()I", "hasOnlyInputTypesAnnotation", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeVariableForLambdaParameterType extends NewTypeVariable {
    private final PostponableKotlinCallArgument atom;
    private final int index;

    @Override // org.jetbrains.kotlin.resolve.calls.inference.model.NewTypeVariable
    public boolean hasOnlyInputTypesAnnotation() {
        return false;
    }

    public final PostponableKotlinCallArgument getAtom() {
        return this.atom;
    }

    public final int getIndex() {
        return this.index;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeVariableForLambdaParameterType(PostponableKotlinCallArgument atom, int i, KotlinBuiltIns builtIns, String name2) {
        super(builtIns, name2, null, 4, null);
        Intrinsics.checkNotNullParameter(atom, "atom");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(name2, "name");
        this.atom = atom;
        this.index = i;
    }
}
