package org.jetbrains.kotlin.resolve.calls.inference.model;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinTypeFactory;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: TypeVariable.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\n\u0010\u0000\u001a\u00020\u0001*\u00020\u0002¨\u0006\u0003"}, d2 = {"typeForTypeVariable", "Lorg/jetbrains/kotlin/types/SimpleType;", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "resolution"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TypeVariableKt {
    public static final SimpleType typeForTypeVariable(TypeConstructor typeConstructor) {
        Intrinsics.checkNotNullParameter(typeConstructor, "<this>");
        if (!(typeConstructor instanceof TypeVariableTypeConstructor)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        TypeAttributes empty = TypeAttributes.INSTANCE.getEmpty();
        List listEmptyList = CollectionsKt.emptyList();
        MemberScope unsubstitutedMemberScope = ((TypeVariableTypeConstructor) typeConstructor).getBuiltIns().getAny().getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        return KotlinTypeFactory.simpleTypeWithNonTrivialMemberScope(empty, typeConstructor, listEmptyList, false, unsubstitutedMemberScope);
    }
}
