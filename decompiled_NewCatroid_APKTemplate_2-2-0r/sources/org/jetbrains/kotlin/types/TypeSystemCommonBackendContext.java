package org.jetbrains.kotlin.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;

/* JADX INFO: compiled from: TypeSystemCommonBackendContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\n\bf\u0018\u00002\u00020\u0001J\u0010\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J\b\u0010\u0006\u001a\u00020\u0003H&J\u0016\u0010\u0007\u001a\u0004\u0018\u00010\b*\u00020\u00052\u0006\u0010\t\u001a\u00020\nH&J\u000e\u0010\u000b\u001a\u0004\u0018\u00010\f*\u00020\rH&J\f\u0010\u000e\u001a\u00020\u000f*\u00020\u0010H&J\u000e\u0010\u0011\u001a\u0004\u0018\u00010\u0012*\u00020\rH&J\u000e\u0010\u0013\u001a\u0004\u0018\u00010\u0012*\u00020\rH&J\f\u0010\u0014\u001a\u00020\u0005*\u00020\u0010H&J\u000e\u0010\u0015\u001a\u0004\u0018\u00010\u0005*\u00020\u0005H&J\u000e\u0010\u0016\u001a\u0004\u0018\u00010\u0005*\u00020\u0005H&J \u0010\u0017\u001a\u0016\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00030\u0019\u0018\u00010\u0018*\u00020\rH&J\u0014\u0010\u001a\u001a\u00020\u001b*\u00020\u00052\u0006\u0010\t\u001a\u00020\nH&J\f\u0010\u001c\u001a\u00020\u001b*\u00020\u0005H&J\f\u0010\u001d\u001a\u00020\u001b*\u00020\rH&J\f\u0010\u001e\u001a\u00020\u001b*\u00020\rH&J\f\u0010\u001f\u001a\u00020\u001b*\u00020\rH&J\f\u0010 \u001a\u00020\u001b*\u00020\u0005H&J\f\u0010!\u001a\u00020\u001b*\u00020\rH&J\f\u0010\"\u001a\u00020\u001b*\u00020\u0010H&J\f\u0010#\u001a\u00020\u001b*\u00020\rH&J\f\u0010$\u001a\u00020\u0005*\u00020\u0005H\u0016ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006%À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/types/TypeSystemCommonBackendContext;", "Lorg/jetbrains/kotlin/types/model/TypeSystemContext;", "arrayType", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "componentType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "nullableAnyType", "getAnnotationFirstArgumentValue", "", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", "getClassFqNameUnsafe", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "getName", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "getPrimitiveArrayType", "Lorg/jetbrains/kotlin/builtins/PrimitiveType;", "getPrimitiveType", "getRepresentativeUpperBound", "getSubstitutedUnderlyingType", "getUnsubstitutedUnderlyingType", "getValueClassProperties", "", "Lkotlin/Pair;", "hasAnnotation", "", "isArrayOrNullableArray", "isFinalClassOrEnumEntryOrAnnotationClassConstructor", "isInlineClass", "isInnerClass", "isInterfaceOrAnnotationClass", "isMultiFieldValueClass", "isReified", "isUnderKotlinPackage", "makeNullable", "compiler.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeSystemCommonBackendContext extends TypeSystemContext {
    SimpleTypeMarker arrayType(KotlinTypeMarker componentType);

    Object getAnnotationFirstArgumentValue(KotlinTypeMarker kotlinTypeMarker, FqName fqName);

    FqNameUnsafe getClassFqNameUnsafe(TypeConstructorMarker typeConstructorMarker);

    Name getName(TypeParameterMarker typeParameterMarker);

    PrimitiveType getPrimitiveArrayType(TypeConstructorMarker typeConstructorMarker);

    PrimitiveType getPrimitiveType(TypeConstructorMarker typeConstructorMarker);

    KotlinTypeMarker getRepresentativeUpperBound(TypeParameterMarker typeParameterMarker);

    KotlinTypeMarker getSubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker);

    KotlinTypeMarker getUnsubstitutedUnderlyingType(KotlinTypeMarker kotlinTypeMarker);

    List<Pair<Name, SimpleTypeMarker>> getValueClassProperties(TypeConstructorMarker typeConstructorMarker);

    boolean hasAnnotation(KotlinTypeMarker kotlinTypeMarker, FqName fqName);

    boolean isArrayOrNullableArray(KotlinTypeMarker kotlinTypeMarker);

    boolean isFinalClassOrEnumEntryOrAnnotationClassConstructor(TypeConstructorMarker typeConstructorMarker);

    boolean isInlineClass(TypeConstructorMarker typeConstructorMarker);

    boolean isInnerClass(TypeConstructorMarker typeConstructorMarker);

    boolean isInterfaceOrAnnotationClass(KotlinTypeMarker kotlinTypeMarker);

    boolean isMultiFieldValueClass(TypeConstructorMarker typeConstructorMarker);

    boolean isReified(TypeParameterMarker typeParameterMarker);

    boolean isUnderKotlinPackage(TypeConstructorMarker typeConstructorMarker);

    SimpleTypeMarker nullableAnyType();

    default KotlinTypeMarker makeNullable(KotlinTypeMarker kotlinTypeMarker) {
        SimpleTypeMarker simpleTypeMarkerWithNullability;
        Intrinsics.checkNotNullParameter(kotlinTypeMarker, "<this>");
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = asSimpleType(kotlinTypeMarker);
        return (simpleTypeMarkerAsSimpleType == null || (simpleTypeMarkerWithNullability = withNullability(simpleTypeMarkerAsSimpleType, true)) == null) ? kotlinTypeMarker : simpleTypeMarkerWithNullability;
    }
}
