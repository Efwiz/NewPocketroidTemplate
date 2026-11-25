package org.jetbrains.kotlin.resolve.calls.inference.components;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;

/* JADX INFO: compiled from: VariableFixationFinder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000&\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\u001a\u001a\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u001a\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u0004\u001a,\u0010\n\u001a\u00020\u0001*\u00020\u00022\u0006\u0010\u0003\u001a\u00020\u00042\u0012\u0010\u000b\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00010\fH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\r"}, d2 = {"containsTypeVariable", "", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", "extractProjectionsForAllCapturedTypes", "", "baseType", "isProperTypeForFixation", "isProper", "Lkotlin/Function1;", "resolution.common"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class VariableFixationFinderKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final boolean isProperTypeForFixation(TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, KotlinTypeMarker type, Function1<? super KotlinTypeMarker, Boolean> isProper) {
        Intrinsics.checkNotNullParameter(typeSystemInferenceExtensionContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(isProper, "isProper");
        if (isProper.invoke(type).booleanValue()) {
            Set<KotlinTypeMarker> setExtractProjectionsForAllCapturedTypes = extractProjectionsForAllCapturedTypes(typeSystemInferenceExtensionContext, type);
            if (!(setExtractProjectionsForAllCapturedTypes instanceof Collection) || !setExtractProjectionsForAllCapturedTypes.isEmpty()) {
                Iterator<T> it = setExtractProjectionsForAllCapturedTypes.iterator();
                while (it.hasNext()) {
                    if (!isProper.invoke(it.next()).booleanValue()) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x007d  */
    /* JADX WARN: Code duplicated, block: B:24:0x0080  */
    /* JADX WARN: Code duplicated, block: B:26:0x0087  */
    /* JADX WARN: Code duplicated, block: B:29:0x0092  */
    /* JADX WARN: Code duplicated, block: B:32:0x0096  */
    /* JADX WARN: Code duplicated, block: B:38:0x00a3 A[SYNTHETIC] */
    public static final Set<KotlinTypeMarker> extractProjectionsForAllCapturedTypes(TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, KotlinTypeMarker baseType) {
        Integer numValueOf;
        int iIntValue;
        int i;
        TypeArgumentMarker argument;
        Intrinsics.checkNotNullParameter(typeSystemInferenceExtensionContext, "<this>");
        Intrinsics.checkNotNullParameter(baseType, "baseType");
        if (typeSystemInferenceExtensionContext.isFlexible(baseType)) {
            FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType = typeSystemInferenceExtensionContext.asFlexibleType(baseType);
            Intrinsics.checkNotNull(flexibleTypeMarkerAsFlexibleType);
            Set setCreateSetBuilder = SetsKt.createSetBuilder();
            setCreateSetBuilder.addAll(extractProjectionsForAllCapturedTypes(typeSystemInferenceExtensionContext, typeSystemInferenceExtensionContext.lowerBound(flexibleTypeMarkerAsFlexibleType)));
            setCreateSetBuilder.addAll(extractProjectionsForAllCapturedTypes(typeSystemInferenceExtensionContext, typeSystemInferenceExtensionContext.upperBound(flexibleTypeMarkerAsFlexibleType)));
            return SetsKt.build(setCreateSetBuilder);
        }
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = typeSystemInferenceExtensionContext.asSimpleType(baseType);
        SimpleTypeMarker simpleTypeMarkerOriginalIfDefinitelyNotNullable = simpleTypeMarkerAsSimpleType != null ? typeSystemInferenceExtensionContext.originalIfDefinitelyNotNullable(simpleTypeMarkerAsSimpleType) : null;
        Set setCreateSetBuilder2 = SetsKt.createSetBuilder();
        if (simpleTypeMarkerOriginalIfDefinitelyNotNullable instanceof CapturedTypeMarker) {
            TypeArgumentMarker typeArgumentMarkerTypeConstructorProjection = typeSystemInferenceExtensionContext.typeConstructorProjection((CapturedTypeMarker) simpleTypeMarkerOriginalIfDefinitelyNotNullable);
            if (typeSystemInferenceExtensionContext.isStarProjection(typeArgumentMarkerTypeConstructorProjection)) {
                typeArgumentMarkerTypeConstructorProjection = null;
            }
            if (typeArgumentMarkerTypeConstructorProjection != null) {
                baseType = typeSystemInferenceExtensionContext.getType(typeArgumentMarkerTypeConstructorProjection);
                setCreateSetBuilder2.add(baseType);
                numValueOf = Integer.valueOf(typeSystemInferenceExtensionContext.argumentsCount(baseType));
                if (numValueOf.intValue() == 0) {
                    numValueOf = null;
                }
                if (numValueOf != null) {
                    iIntValue = numValueOf.intValue();
                    for (i = 0; i < iIntValue; i++) {
                        argument = typeSystemInferenceExtensionContext.getArgument(baseType, i);
                        if (typeSystemInferenceExtensionContext.isStarProjection(argument)) {
                            argument = null;
                        }
                        if (argument == null) {
                            setCreateSetBuilder2.addAll(extractProjectionsForAllCapturedTypes(typeSystemInferenceExtensionContext, typeSystemInferenceExtensionContext.getType(argument)));
                        }
                    }
                }
            }
        } else {
            numValueOf = Integer.valueOf(typeSystemInferenceExtensionContext.argumentsCount(baseType));
            if (numValueOf.intValue() == 0) {
                numValueOf = null;
            }
            if (numValueOf != null) {
                iIntValue = numValueOf.intValue();
                while (i < iIntValue) {
                    argument = typeSystemInferenceExtensionContext.getArgument(baseType, i);
                    if (typeSystemInferenceExtensionContext.isStarProjection(argument)) {
                        argument = null;
                    }
                    if (argument == null) {
                        setCreateSetBuilder2.addAll(extractProjectionsForAllCapturedTypes(typeSystemInferenceExtensionContext, typeSystemInferenceExtensionContext.getType(argument)));
                    }
                }
            }
        }
        return SetsKt.build(setCreateSetBuilder2);
    }

    public static final boolean containsTypeVariable(final TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext, KotlinTypeMarker type, final TypeConstructorMarker typeVariable) {
        Intrinsics.checkNotNullParameter(typeSystemInferenceExtensionContext, "<this>");
        Intrinsics.checkNotNullParameter(type, "type");
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        if (typeSystemInferenceExtensionContext.contains(type, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinderKt.containsTypeVariable.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(KotlinTypeMarker it) {
                Intrinsics.checkNotNullParameter(it, "it");
                TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext2 = typeSystemInferenceExtensionContext;
                return Boolean.valueOf(Intrinsics.areEqual(typeSystemInferenceExtensionContext2.unwrapStubTypeVariableConstructor(typeSystemInferenceExtensionContext2.typeConstructor(it)), typeVariable));
            }
        })) {
            return true;
        }
        Set<KotlinTypeMarker> setExtractProjectionsForAllCapturedTypes = extractProjectionsForAllCapturedTypes(typeSystemInferenceExtensionContext, type);
        if (!(setExtractProjectionsForAllCapturedTypes instanceof Collection) || !setExtractProjectionsForAllCapturedTypes.isEmpty()) {
            Iterator<T> it = setExtractProjectionsForAllCapturedTypes.iterator();
            while (it.hasNext()) {
                if (typeSystemInferenceExtensionContext.contains((KotlinTypeMarker) it.next(), new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.VariableFixationFinderKt$containsTypeVariable$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(KotlinTypeMarker it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext2 = typeSystemInferenceExtensionContext;
                        return Boolean.valueOf(Intrinsics.areEqual(typeSystemInferenceExtensionContext2.unwrapStubTypeVariableConstructor(typeSystemInferenceExtensionContext2.typeConstructor(it2)), typeVariable));
                    }
                })) {
                    return true;
                }
            }
        }
        return false;
    }
}
