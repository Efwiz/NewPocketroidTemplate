package org.jetbrains.kotlin.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.ArraysKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;

/* JADX INFO: compiled from: TypeSystemCommonBackendContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010 \n\u0000\bf\u0018\u00002\u00020\u0001J\b\u0010\u0002\u001a\u00020\u0003H&J\u0010\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0005\u001a\u00020\u0006H&J\f\u0010\u0007\u001a\u00020\b*\u00020\tH\u0016J\f\u0010\n\u001a\u00020\u000b*\u00020\u0003H&J\f\u0010\f\u001a\u00020\b*\u00020\u0003H&J\u000e\u0010\r\u001a\u0004\u0018\u00010\u000e*\u00020\bH&J\f\u0010\u000f\u001a\u00020\u0010*\u00020\u0011H&J\f\u0010\u0012\u001a\u00020\u0010*\u00020\u0003H&J\f\u0010\u0013\u001a\u00020\u0010*\u00020\u0011H&J\f\u0010\u0014\u001a\u00020\u0010*\u00020\u0003H&J\f\u0010\u0015\u001a\u00020\b*\u00020\u000bH&J%\u0010\u0016\u001a\u00020\u0011*\u00020\u00032\u0012\u0010\u0017\u001a\n\u0012\u0006\b\u0001\u0012\u00020\b0\u0018\"\u00020\bH\u0016¢\u0006\u0002\u0010\u0019J\u001a\u0010\u0016\u001a\u00020\u0011*\u00020\u00032\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\b0\u001aH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContextForTypeMapping;", "Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "continuationTypeConstructor", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "functionNTypeConstructor", "n", "", "adjustedType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "Lorg/jetbrains/kotlin/types/model/TypeArgumentMarker;", "asTypeParameter", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "defaultType", "getNameForErrorType", "", "isKClass", "", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "isScript", "isSuspendFunction", "isTypeParameter", "representativeUpperBound", "typeWithArguments", "arguments", "", "(Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;[Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeSystemCommonBackendContextForTypeMapping extends TypeSystemCommonBackendContext {
    TypeParameterMarker asTypeParameter(TypeConstructorMarker typeConstructorMarker);

    TypeConstructorMarker continuationTypeConstructor();

    KotlinTypeMarker defaultType(TypeConstructorMarker typeConstructorMarker);

    TypeConstructorMarker functionNTypeConstructor(int n);

    String getNameForErrorType(KotlinTypeMarker kotlinTypeMarker);

    boolean isKClass(SimpleTypeMarker simpleTypeMarker);

    boolean isScript(TypeConstructorMarker typeConstructorMarker);

    boolean isSuspendFunction(SimpleTypeMarker simpleTypeMarker);

    boolean isTypeParameter(TypeConstructorMarker typeConstructorMarker);

    KotlinTypeMarker representativeUpperBound(TypeParameterMarker typeParameterMarker);

    SimpleTypeMarker typeWithArguments(TypeConstructorMarker typeConstructorMarker, List<? extends KotlinTypeMarker> list);

    default SimpleTypeMarker typeWithArguments(TypeConstructorMarker typeConstructorMarker, KotlinTypeMarker... arguments) {
        Intrinsics.checkNotNullParameter(typeConstructorMarker, "<this>");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        return typeWithArguments(typeConstructorMarker, ArraysKt.toList(arguments));
    }

    default KotlinTypeMarker adjustedType(TypeArgumentMarker typeArgumentMarker) {
        Intrinsics.checkNotNullParameter(typeArgumentMarker, "<this>");
        return isStarProjection(typeArgumentMarker) ? nullableAnyType() : getType(typeArgumentMarker);
    }
}
