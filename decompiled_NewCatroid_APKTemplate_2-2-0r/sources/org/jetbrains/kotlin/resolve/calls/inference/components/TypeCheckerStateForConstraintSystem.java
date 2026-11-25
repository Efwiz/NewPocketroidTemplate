package org.jetbrains.kotlin.resolve.calls.inference.components;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.types.AbstractNullabilityChecker;
import org.jetbrains.kotlin.types.AbstractTypeChecker;
import org.jetbrains.kotlin.types.AbstractTypePreparator;
import org.jetbrains.kotlin.types.AbstractTypeRefiner;
import org.jetbrains.kotlin.types.TypeCheckerState;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.SimpleTypeMarker;
import org.jetbrains.kotlin.types.model.TypeArgumentMarker;
import org.jetbrains.kotlin.types.model.TypeCheckerProviderContext;
import org.jetbrains.kotlin.types.model.TypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContext;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.model.TypeVariableTypeConstructorMarker;
import org.jetbrains.kotlin.types.model.TypeVariance;

/* JADX INFO: compiled from: TypeCheckerStateForConstraintSystem.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H&J\"\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\fH&J%\u0010\u0017\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\f¢\u0006\u0002\u0010\u0019J\u0018\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0018\u001a\u00020\u0013H&J\u0010\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u001cH\u0002J\u0018\u0010\u001d\u001a\u00020\u000f2\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0013H\u0002J\u001a\u0010\u001e\u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u001f\u001a\u00020\fH\u0002J\u001a\u0010 \u001a\u0004\u0018\u00010\u00132\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0013H\u0002J\u0018\u0010!\u001a\u00020\"2\u0006\u0010\u0015\u001a\u00020#2\u0006\u0010\u0018\u001a\u00020$H\u0016J'\u0010%\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0016\u001a\u00020\fH\u0002¢\u0006\u0002\u0010\u0019J\u0010\u0010&\u001a\u00020\f2\u0006\u0010\u0012\u001a\u00020#H&J\u0018\u0010'\u001a\u00020\f2\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0013H\u0002J\u001f\u0010(\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0015\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0013H\u0002¢\u0006\u0002\u0010)J\"\u0010*\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00132\u0006\u0010\u0015\u001a\u00020\u00132\b\b\u0002\u0010\u0016\u001a\u00020\fH\u0002J\u0018\u0010+\u001a\u00020\f2\u0006\u0010\u0010\u001a\u00020\u00132\u0006\u0010\u0018\u001a\u00020\u0013H\u0002J!\u0010,\u001a\u00020\f*\u00020\u00132\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\f0.H\u0082\bJ!\u0010/\u001a\u00020\f*\u00020\u00132\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\f0.H\u0082\bJ\f\u00100\u001a\u00020\f*\u00020\u0013H\u0002J\f\u00101\u001a\u00020\f*\u00020\u0013H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\fX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\r¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/TypeCheckerStateForConstraintSystem;", "Lorg/jetbrains/kotlin/types/TypeCheckerState;", "extensionTypeContext", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "kotlinTypePreparator", "Lorg/jetbrains/kotlin/types/AbstractTypePreparator;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/AbstractTypeRefiner;", "(Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;Lorg/jetbrains/kotlin/types/AbstractTypePreparator;Lorg/jetbrains/kotlin/types/AbstractTypeRefiner;)V", "getExtensionTypeContext", "()Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "isInferenceCompatibilityEnabled", "", "()Z", "addEqualityConstraint", "", "typeVariable", "Lorg/jetbrains/kotlin/types/model/TypeConstructorMarker;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "addLowerConstraint", "subType", "isFromNullabilityConstraint", "addSubtypeConstraint", "superType", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Z)Ljava/lang/Boolean;", "addUpperConstraint", "assertFlexibleTypeVariable", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "assertInputTypes", "extractTypeForProjectedType", "out", "extractTypeVariableForSubtype", "getLowerCapturedTypePolicy", "Lorg/jetbrains/kotlin/types/TypeCheckerState$LowerCapturedTypePolicy;", "Lorg/jetbrains/kotlin/types/model/SimpleTypeMarker;", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "internalAddSubtypeConstraint", "isMyTypeVariable", "isSubtypeOfByTypeChecker", "simplifyConstraintForPossibleIntersectionSubType", "(Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;)Ljava/lang/Boolean;", "simplifyLowerConstraint", "simplifyUpperConstraint", "anyBound", "f", "Lkotlin/Function1;", "bothBounds", "isTypeVariableWithExact", "isTypeVariableWithNoInfer", "resolution.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class TypeCheckerStateForConstraintSystem extends TypeCheckerState {
    private final TypeSystemInferenceExtensionContext extensionTypeContext;

    /* JADX INFO: compiled from: TypeCheckerStateForConstraintSystem.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TypeVariance.values().length];
            try {
                iArr[TypeVariance.IN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[TypeVariance.OUT.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[TypeVariance.INV.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public abstract void addEqualityConstraint(TypeConstructorMarker typeVariable, KotlinTypeMarker type);

    public abstract void addLowerConstraint(TypeConstructorMarker typeVariable, KotlinTypeMarker subType, boolean isFromNullabilityConstraint);

    public abstract void addUpperConstraint(TypeConstructorMarker typeVariable, KotlinTypeMarker superType);

    /* JADX INFO: renamed from: isInferenceCompatibilityEnabled */
    public abstract boolean getIsInferenceCompatibilityEnabled();

    public abstract boolean isMyTypeVariable(SimpleTypeMarker type);

    public final TypeSystemInferenceExtensionContext getExtensionTypeContext() {
        return this.extensionTypeContext;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public TypeCheckerStateForConstraintSystem(TypeSystemInferenceExtensionContext extensionTypeContext, AbstractTypePreparator kotlinTypePreparator, AbstractTypeRefiner kotlinTypeRefiner) {
        super(true, true, false, extensionTypeContext, kotlinTypePreparator, kotlinTypeRefiner);
        Intrinsics.checkNotNullParameter(extensionTypeContext, "extensionTypeContext");
        Intrinsics.checkNotNullParameter(kotlinTypePreparator, "kotlinTypePreparator");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.extensionTypeContext = extensionTypeContext;
    }

    public static /* synthetic */ void addLowerConstraint$default(TypeCheckerStateForConstraintSystem typeCheckerStateForConstraintSystem, TypeConstructorMarker typeConstructorMarker, KotlinTypeMarker kotlinTypeMarker, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addLowerConstraint");
        }
        if ((i & 4) != 0) {
            z = false;
        }
        typeCheckerStateForConstraintSystem.addLowerConstraint(typeConstructorMarker, kotlinTypeMarker, z);
    }

    @Override // org.jetbrains.kotlin.types.TypeCheckerState
    public TypeCheckerState.LowerCapturedTypePolicy getLowerCapturedTypePolicy(SimpleTypeMarker subType, CapturedTypeMarker superType) {
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = this.extensionTypeContext;
        if (!isMyTypeVariable(subType)) {
            return typeSystemInferenceExtensionContext.contains(subType, new Function1<KotlinTypeMarker, Boolean>() { // from class: org.jetbrains.kotlin.resolve.calls.inference.components.TypeCheckerStateForConstraintSystem$getLowerCapturedTypePolicy$1$1
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(KotlinTypeMarker it) {
                    boolean zIsMyTypeVariable;
                    Intrinsics.checkNotNullParameter(it, "it");
                    TypeCheckerStateForConstraintSystem typeCheckerStateForConstraintSystem = this.this$0;
                    if (it instanceof SimpleTypeMarker) {
                        zIsMyTypeVariable = typeCheckerStateForConstraintSystem.isMyTypeVariable((SimpleTypeMarker) it);
                    } else {
                        if (!(it instanceof FlexibleTypeMarker)) {
                            throw new IllegalStateException("sealed".toString());
                        }
                        TypeSystemContext typeSystemContext = typeCheckerStateForConstraintSystem.getTypeSystemContext();
                        FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) it;
                        zIsMyTypeVariable = typeCheckerStateForConstraintSystem.isMyTypeVariable(typeSystemContext.lowerBound(flexibleTypeMarker)) || typeCheckerStateForConstraintSystem.isMyTypeVariable(typeSystemContext.upperBound(flexibleTypeMarker));
                    }
                    return Boolean.valueOf(zIsMyTypeVariable);
                }
            }) ? TypeCheckerState.LowerCapturedTypePolicy.CHECK_ONLY_LOWER : TypeCheckerState.LowerCapturedTypePolicy.CHECK_SUBTYPE_AND_LOWER;
        }
        TypeArgumentMarker typeArgumentMarkerTypeConstructorProjection = typeSystemInferenceExtensionContext.typeConstructorProjection(superType);
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = typeSystemInferenceExtensionContext.asSimpleType(typeSystemInferenceExtensionContext.getType(typeArgumentMarkerTypeConstructorProjection));
        if (typeSystemInferenceExtensionContext.getVariance(typeArgumentMarkerTypeConstructorProjection) == TypeVariance.IN && simpleTypeMarkerAsSimpleType != null && isMyTypeVariable(simpleTypeMarkerAsSimpleType)) {
            return TypeCheckerState.LowerCapturedTypePolicy.CHECK_ONLY_LOWER;
        }
        return TypeCheckerState.LowerCapturedTypePolicy.SKIP_LOWER;
    }

    /* JADX WARN: Code duplicated, block: B:41:0x0070  */
    @Override // org.jetbrains.kotlin.types.TypeCheckerState
    public final Boolean addSubtypeConstraint(KotlinTypeMarker subType, KotlinTypeMarker superType, boolean isFromNullabilityConstraint) {
        boolean z;
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        if (isTypeVariableWithNoInfer(subType) || isTypeVariableWithNoInfer(superType)) {
            return true;
        }
        boolean z2 = isTypeVariableWithExact(subType) || isTypeVariableWithExact(superType);
        if (z2) {
            KotlinTypeMarker kotlinTypeMarkerExtractTypeForProjectedType = extractTypeForProjectedType(subType, true);
            subType = kotlinTypeMarkerExtractTypeForProjectedType == null ? this.extensionTypeContext.removeExactAnnotation(subType) : kotlinTypeMarkerExtractTypeForProjectedType;
        }
        if (z2) {
            KotlinTypeMarker kotlinTypeMarkerExtractTypeForProjectedType2 = extractTypeForProjectedType(superType, false);
            superType = kotlinTypeMarkerExtractTypeForProjectedType2 == null ? this.extensionTypeContext.removeExactAnnotation(superType) : kotlinTypeMarkerExtractTypeForProjectedType2;
        }
        Boolean boolInternalAddSubtypeConstraint = internalAddSubtypeConstraint(subType, superType, isFromNullabilityConstraint);
        if (!z2) {
            return boolInternalAddSubtypeConstraint;
        }
        Boolean boolInternalAddSubtypeConstraint2 = internalAddSubtypeConstraint(superType, subType, isFromNullabilityConstraint);
        if (boolInternalAddSubtypeConstraint == null && boolInternalAddSubtypeConstraint2 == null) {
            return null;
        }
        if (boolInternalAddSubtypeConstraint != null ? boolInternalAddSubtypeConstraint.booleanValue() : true) {
            z = boolInternalAddSubtypeConstraint2 != null ? boolInternalAddSubtypeConstraint2.booleanValue() : true;
        }
        return Boolean.valueOf(z);
    }

    private final KotlinTypeMarker extractTypeForProjectedType(KotlinTypeMarker type, boolean out) {
        CapturedTypeMarker capturedTypeMarkerAsCapturedType;
        TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = this.extensionTypeContext;
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = typeSystemInferenceExtensionContext.asSimpleType(type);
        if (simpleTypeMarkerAsSimpleType == null || (capturedTypeMarkerAsCapturedType = typeSystemInferenceExtensionContext.asCapturedType(simpleTypeMarkerAsSimpleType)) == null) {
            return null;
        }
        TypeArgumentMarker typeArgumentMarkerTypeConstructorProjection = typeSystemInferenceExtensionContext.typeConstructorProjection(capturedTypeMarkerAsCapturedType);
        if (typeSystemInferenceExtensionContext.isStarProjection(typeArgumentMarkerTypeConstructorProjection)) {
            if (!out) {
                if (out) {
                    throw new NoWhenBranchMatchedException();
                }
                return typeSystemInferenceExtensionContext.lowerType(capturedTypeMarkerAsCapturedType);
            }
            Collection<KotlinTypeMarker> collectionSupertypes = typeSystemInferenceExtensionContext.supertypes(typeSystemInferenceExtensionContext.typeConstructor(simpleTypeMarkerAsSimpleType));
            if (collectionSupertypes.isEmpty()) {
                return typeSystemInferenceExtensionContext.nullableAnyType();
            }
            return typeSystemInferenceExtensionContext.intersectTypes(CollectionsKt.toList(collectionSupertypes));
        }
        int i = WhenMappings.$EnumSwitchMapping$0[typeSystemInferenceExtensionContext.getVariance(typeArgumentMarkerTypeConstructorProjection).ordinal()];
        if (i == 1) {
            if (out) {
                return null;
            }
            KotlinTypeMarker kotlinTypeMarkerLowerType = typeSystemInferenceExtensionContext.lowerType(capturedTypeMarkerAsCapturedType);
            return kotlinTypeMarkerLowerType == null ? typeSystemInferenceExtensionContext.getType(typeArgumentMarkerTypeConstructorProjection) : kotlinTypeMarkerLowerType;
        }
        if (i != 2) {
            if (i == 3) {
                return null;
            }
            throw new NoWhenBranchMatchedException();
        }
        if (out) {
            return typeSystemInferenceExtensionContext.getType(typeArgumentMarkerTypeConstructorProjection);
        }
        return null;
    }

    private final boolean isTypeVariableWithExact(KotlinTypeMarker kotlinTypeMarker) {
        boolean zIsMyTypeVariable;
        if (!this.extensionTypeContext.hasExactAnnotation(kotlinTypeMarker)) {
            return false;
        }
        if (kotlinTypeMarker instanceof SimpleTypeMarker) {
            zIsMyTypeVariable = isMyTypeVariable((SimpleTypeMarker) kotlinTypeMarker);
        } else {
            if (!(kotlinTypeMarker instanceof FlexibleTypeMarker)) {
                throw new IllegalStateException("sealed".toString());
            }
            TypeSystemContext typeSystemContext = getTypeSystemContext();
            FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) kotlinTypeMarker;
            zIsMyTypeVariable = isMyTypeVariable(typeSystemContext.lowerBound(flexibleTypeMarker)) || isMyTypeVariable(typeSystemContext.upperBound(flexibleTypeMarker));
        }
        return zIsMyTypeVariable;
    }

    private final boolean isTypeVariableWithNoInfer(KotlinTypeMarker kotlinTypeMarker) {
        boolean zIsMyTypeVariable;
        if (!this.extensionTypeContext.hasNoInferAnnotation(kotlinTypeMarker)) {
            return false;
        }
        if (kotlinTypeMarker instanceof SimpleTypeMarker) {
            zIsMyTypeVariable = isMyTypeVariable((SimpleTypeMarker) kotlinTypeMarker);
        } else {
            if (!(kotlinTypeMarker instanceof FlexibleTypeMarker)) {
                throw new IllegalStateException("sealed".toString());
            }
            TypeSystemContext typeSystemContext = getTypeSystemContext();
            FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) kotlinTypeMarker;
            zIsMyTypeVariable = isMyTypeVariable(typeSystemContext.lowerBound(flexibleTypeMarker)) || isMyTypeVariable(typeSystemContext.upperBound(flexibleTypeMarker));
        }
        return zIsMyTypeVariable;
    }

    private final Boolean internalAddSubtypeConstraint(KotlinTypeMarker subType, KotlinTypeMarker superType, boolean isFromNullabilityConstraint) {
        boolean zIsMyTypeVariable;
        boolean zIsMyTypeVariable2;
        assertInputTypes(subType, superType);
        boolean z = false;
        if (superType instanceof SimpleTypeMarker) {
            zIsMyTypeVariable = isMyTypeVariable((SimpleTypeMarker) superType);
        } else if (superType instanceof FlexibleTypeMarker) {
            TypeSystemContext typeSystemContext = getTypeSystemContext();
            FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) superType;
            zIsMyTypeVariable = isMyTypeVariable(typeSystemContext.lowerBound(flexibleTypeMarker)) || isMyTypeVariable(typeSystemContext.upperBound(flexibleTypeMarker));
        } else {
            throw new IllegalStateException("sealed".toString());
        }
        Boolean boolValueOf = zIsMyTypeVariable ? Boolean.valueOf(simplifyLowerConstraint(superType, subType, isFromNullabilityConstraint)) : null;
        if (subType instanceof SimpleTypeMarker) {
            zIsMyTypeVariable2 = isMyTypeVariable((SimpleTypeMarker) subType);
        } else if (subType instanceof FlexibleTypeMarker) {
            TypeSystemContext typeSystemContext2 = getTypeSystemContext();
            FlexibleTypeMarker flexibleTypeMarker2 = (FlexibleTypeMarker) subType;
            zIsMyTypeVariable2 = isMyTypeVariable(typeSystemContext2.lowerBound(flexibleTypeMarker2)) || isMyTypeVariable(typeSystemContext2.upperBound(flexibleTypeMarker2));
        } else {
            throw new IllegalStateException("sealed".toString());
        }
        if (zIsMyTypeVariable2) {
            if (simplifyUpperConstraint(subType, superType)) {
                if (boolValueOf != null ? boolValueOf.booleanValue() : true) {
                    z = true;
                }
            }
            return Boolean.valueOf(z);
        }
        KotlinTypeMarker kotlinTypeMarkerExtractTypeVariableForSubtype = extractTypeVariableForSubtype(subType, superType);
        if (kotlinTypeMarkerExtractTypeVariableForSubtype != null) {
            if (simplifyUpperConstraint(kotlinTypeMarkerExtractTypeVariableForSubtype, superType)) {
                if (boolValueOf != null ? boolValueOf.booleanValue() : true) {
                    z = true;
                }
            }
            return Boolean.valueOf(z);
        }
        Boolean boolSimplifyConstraintForPossibleIntersectionSubType = simplifyConstraintForPossibleIntersectionSubType(subType, superType);
        return boolSimplifyConstraintForPossibleIntersectionSubType == null ? boolValueOf : boolSimplifyConstraintForPossibleIntersectionSubType;
    }

    private final KotlinTypeMarker extractTypeVariableForSubtype(KotlinTypeMarker subType, KotlinTypeMarker superType) {
        CapturedTypeMarker capturedTypeMarkerAsCapturedType;
        FlexibleTypeMarker flexibleTypeMarkerAsFlexibleType;
        TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = this.extensionTypeContext;
        SimpleTypeMarker simpleTypeMarkerAsSimpleType = typeSystemInferenceExtensionContext.asSimpleType(subType);
        SimpleTypeMarker simpleTypeMarkerLowerBound = null;
        if (simpleTypeMarkerAsSimpleType == null || (capturedTypeMarkerAsCapturedType = typeSystemInferenceExtensionContext.asCapturedType(simpleTypeMarkerAsSimpleType)) == null) {
            return null;
        }
        TypeArgumentMarker typeArgumentMarkerTypeConstructorProjection = typeSystemInferenceExtensionContext.typeConstructorProjection(capturedTypeMarkerAsCapturedType);
        if (typeSystemInferenceExtensionContext.isStarProjection(typeArgumentMarkerTypeConstructorProjection)) {
            return null;
        }
        if (typeSystemInferenceExtensionContext.getVariance(typeArgumentMarkerTypeConstructorProjection) == TypeVariance.IN) {
            SimpleTypeMarker simpleTypeMarkerAsSimpleType2 = typeSystemInferenceExtensionContext.asSimpleType(typeSystemInferenceExtensionContext.getType(typeArgumentMarkerTypeConstructorProjection));
            if (simpleTypeMarkerAsSimpleType2 != null && isMyTypeVariable(simpleTypeMarkerAsSimpleType2)) {
                simplifyLowerConstraint$default(this, simpleTypeMarkerAsSimpleType2, superType, false, 4, null);
                SimpleTypeMarker simpleTypeMarkerAsSimpleType3 = typeSystemInferenceExtensionContext.asSimpleType(superType);
                if (simpleTypeMarkerAsSimpleType3 != null && isMyTypeVariable(simpleTypeMarkerAsSimpleType3)) {
                    addLowerConstraint$default(this, typeSystemInferenceExtensionContext.typeConstructor(superType), typeSystemInferenceExtensionContext.nullableAnyType(), false, 4, null);
                }
            }
            return null;
        }
        if (typeSystemInferenceExtensionContext.getVariance(typeArgumentMarkerTypeConstructorProjection) != TypeVariance.OUT) {
            return null;
        }
        KotlinTypeMarker type = typeSystemInferenceExtensionContext.getType(typeArgumentMarkerTypeConstructorProjection);
        if ((type instanceof SimpleTypeMarker) && isMyTypeVariable((SimpleTypeMarker) type)) {
            simpleTypeMarkerLowerBound = typeSystemInferenceExtensionContext.asSimpleType(type);
        } else if ((type instanceof FlexibleTypeMarker) && isMyTypeVariable(typeSystemInferenceExtensionContext.lowerBound((FlexibleTypeMarker) type)) && (flexibleTypeMarkerAsFlexibleType = typeSystemInferenceExtensionContext.asFlexibleType(type)) != null) {
            simpleTypeMarkerLowerBound = typeSystemInferenceExtensionContext.lowerBound(flexibleTypeMarkerAsFlexibleType);
        }
        return simpleTypeMarkerLowerBound;
    }

    static /* synthetic */ boolean simplifyLowerConstraint$default(TypeCheckerStateForConstraintSystem typeCheckerStateForConstraintSystem, KotlinTypeMarker kotlinTypeMarker, KotlinTypeMarker kotlinTypeMarker2, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: simplifyLowerConstraint");
        }
        if ((i & 4) != 0) {
            z = false;
        }
        return typeCheckerStateForConstraintSystem.simplifyLowerConstraint(kotlinTypeMarker, kotlinTypeMarker2, z);
    }

    private final boolean simplifyLowerConstraint(KotlinTypeMarker typeVariable, KotlinTypeMarker subType, boolean isFromNullabilityConstraint) {
        TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = this.extensionTypeContext;
        TypeConstructorMarker typeConstructorMarkerTypeConstructor = typeSystemInferenceExtensionContext.typeConstructor(subType);
        if (typeVariable instanceof SimpleTypeMarker) {
            if (typeSystemInferenceExtensionContext.isK2() && typeSystemInferenceExtensionContext.isDefinitelyNotNullType(typeVariable) && !typeSystemInferenceExtensionContext.isTypeVariable(typeConstructorMarkerTypeConstructor) && !AbstractNullabilityChecker.INSTANCE.isSubtypeOfAny(this.extensionTypeContext, subType)) {
                return false;
            }
            SimpleTypeMarker simpleTypeMarker = (SimpleTypeMarker) typeVariable;
            if (typeSystemInferenceExtensionContext.isMarkedNullable(simpleTypeMarker)) {
                TypeConstructorMarker typeConstructorMarkerTypeConstructor2 = typeSystemInferenceExtensionContext.typeConstructor(simpleTypeMarker);
                if (typeSystemInferenceExtensionContext.isTypeVariable(typeConstructorMarkerTypeConstructor) || !(typeConstructorMarkerTypeConstructor2 instanceof TypeVariableTypeConstructorMarker) || !typeSystemInferenceExtensionContext.isContainedInInvariantOrContravariantPositions((TypeVariableTypeConstructorMarker) typeConstructorMarkerTypeConstructor2)) {
                    subType = typeSystemInferenceExtensionContext.makeDefinitelyNotNullOrNotNull(subType);
                } else if (!getIsInferenceCompatibilityEnabled() && (subType instanceof CapturedTypeMarker)) {
                    subType = typeSystemInferenceExtensionContext.withNotNullProjection((CapturedTypeMarker) subType);
                } else {
                    subType = typeSystemInferenceExtensionContext.withNullability(subType, false);
                }
                if (getIsInferenceCompatibilityEnabled() && (subType instanceof CapturedTypeMarker)) {
                    subType = typeSystemInferenceExtensionContext.withNotNullProjection((CapturedTypeMarker) subType);
                }
            }
        } else if (typeVariable instanceof FlexibleTypeMarker) {
            assertFlexibleTypeVariable((FlexibleTypeMarker) typeVariable);
            if (subType instanceof SimpleTypeMarker) {
                if (typeSystemInferenceExtensionContext.useRefinedBoundsForTypeVariableInFlexiblePosition()) {
                    SimpleTypeMarker simpleTypeMarker2 = (SimpleTypeMarker) subType;
                    subType = typeSystemInferenceExtensionContext.createFlexibleType(typeSystemInferenceExtensionContext.makeSimpleTypeDefinitelyNotNullOrNotNull(simpleTypeMarker2), typeSystemInferenceExtensionContext.withNullability(simpleTypeMarker2, true));
                } else {
                    SimpleTypeMarker simpleTypeMarker3 = (SimpleTypeMarker) subType;
                    if (!typeSystemInferenceExtensionContext.isMarkedNullable(simpleTypeMarker3)) {
                        subType = typeSystemInferenceExtensionContext.createFlexibleType(simpleTypeMarker3, typeSystemInferenceExtensionContext.withNullability(simpleTypeMarker3, true));
                    }
                }
            } else if (subType instanceof FlexibleTypeMarker) {
                if (typeSystemInferenceExtensionContext.useRefinedBoundsForTypeVariableInFlexiblePosition()) {
                    FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) subType;
                    subType = typeSystemInferenceExtensionContext.createFlexibleType(typeSystemInferenceExtensionContext.makeSimpleTypeDefinitelyNotNullOrNotNull(typeSystemInferenceExtensionContext.lowerBound(flexibleTypeMarker)), typeSystemInferenceExtensionContext.withNullability(typeSystemInferenceExtensionContext.upperBound(flexibleTypeMarker), true));
                } else {
                    FlexibleTypeMarker flexibleTypeMarker2 = (FlexibleTypeMarker) subType;
                    subType = typeSystemInferenceExtensionContext.createFlexibleType(typeSystemInferenceExtensionContext.makeSimpleTypeDefinitelyNotNullOrNotNull(typeSystemInferenceExtensionContext.lowerBound(flexibleTypeMarker2)), typeSystemInferenceExtensionContext.upperBound(flexibleTypeMarker2));
                }
            } else {
                throw new IllegalStateException("sealed".toString());
            }
        } else {
            throw new IllegalStateException("sealed".toString());
        }
        addLowerConstraint(typeSystemInferenceExtensionContext.typeConstructor(typeVariable), subType, isFromNullabilityConstraint);
        return true;
    }

    private final void assertFlexibleTypeVariable(FlexibleTypeMarker typeVariable) {
        TypeSystemContext typeSystemContext = getTypeSystemContext();
        Intrinsics.areEqual(typeSystemContext.typeConstructor(typeSystemContext.lowerBound(typeVariable)), typeSystemContext.typeConstructor(typeSystemContext.upperBound(typeVariable)));
    }

    private final boolean simplifyUpperConstraint(KotlinTypeMarker typeVariable, KotlinTypeMarker superType) {
        boolean zIsMyTypeVariable;
        TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = this.extensionTypeContext;
        SimpleTypeMarker simpleTypeMarkerLowerBoundIfFlexible = typeSystemInferenceExtensionContext.lowerBoundIfFlexible(typeVariable);
        if (typeSystemInferenceExtensionContext.isFlexible(typeVariable) && typeSystemInferenceExtensionContext.useRefinedBoundsForTypeVariableInFlexiblePosition()) {
            superType = typeSystemInferenceExtensionContext.createFlexibleType(typeSystemInferenceExtensionContext.makeSimpleTypeDefinitelyNotNullOrNotNull(typeSystemInferenceExtensionContext.lowerBoundIfFlexible(superType)), typeSystemInferenceExtensionContext.withNullability(typeSystemInferenceExtensionContext.upperBoundIfFlexible(superType), true));
        } else if (typeSystemInferenceExtensionContext.isDefinitelyNotNullType(simpleTypeMarkerLowerBoundIfFlexible)) {
            superType = typeSystemInferenceExtensionContext.withNullability(superType, true);
        } else if (typeSystemInferenceExtensionContext.isFlexible(typeVariable) && (superType instanceof SimpleTypeMarker)) {
            SimpleTypeMarker simpleTypeMarker = (SimpleTypeMarker) superType;
            superType = typeSystemInferenceExtensionContext.createFlexibleType(simpleTypeMarker, typeSystemInferenceExtensionContext.withNullability(simpleTypeMarker, true));
        }
        addUpperConstraint(typeSystemInferenceExtensionContext.typeConstructor(simpleTypeMarkerLowerBoundIfFlexible), superType);
        if (!typeSystemInferenceExtensionContext.isMarkedNullable(simpleTypeMarkerLowerBoundIfFlexible)) {
            return true;
        }
        if (superType instanceof SimpleTypeMarker) {
            zIsMyTypeVariable = isMyTypeVariable((SimpleTypeMarker) superType);
        } else if (superType instanceof FlexibleTypeMarker) {
            TypeSystemContext typeSystemContext = getTypeSystemContext();
            FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) superType;
            zIsMyTypeVariable = isMyTypeVariable(typeSystemContext.lowerBound(flexibleTypeMarker)) || isMyTypeVariable(typeSystemContext.upperBound(flexibleTypeMarker));
        } else {
            throw new IllegalStateException("sealed".toString());
        }
        return zIsMyTypeVariable || isSubtypeOfByTypeChecker(typeSystemInferenceExtensionContext.nullableNothingType(), superType);
    }

    private final Boolean simplifyConstraintForPossibleIntersectionSubType(KotlinTypeMarker subType, KotlinTypeMarker superType) {
        TypeSystemInferenceExtensionContext typeSystemInferenceExtensionContext = this.extensionTypeContext;
        SimpleTypeMarker simpleTypeMarkerLowerBoundIfFlexible = typeSystemInferenceExtensionContext.lowerBoundIfFlexible(subType);
        if (!typeSystemInferenceExtensionContext.isIntersection(typeSystemInferenceExtensionContext.typeConstructor(simpleTypeMarkerLowerBoundIfFlexible))) {
            return null;
        }
        typeSystemInferenceExtensionContext.isMarkedNullable(simpleTypeMarkerLowerBoundIfFlexible);
        Collection<KotlinTypeMarker> collectionSupertypes = typeSystemInferenceExtensionContext.supertypes(typeSystemInferenceExtensionContext.typeConstructor(simpleTypeMarkerLowerBoundIfFlexible));
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collectionSupertypes, 10));
        Iterator<T> it = collectionSupertypes.iterator();
        while (it.hasNext()) {
            arrayList.add(typeSystemInferenceExtensionContext.lowerBoundIfFlexible((KotlinTypeMarker) it.next()));
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj : arrayList2) {
            if (isMyTypeVariable((SimpleTypeMarker) obj)) {
                arrayList3.add(obj);
            }
        }
        ArrayList arrayList4 = arrayList3;
        if (arrayList4.isEmpty()) {
            arrayList4 = null;
        }
        if (arrayList4 == null) {
            return null;
        }
        ArrayList arrayList5 = new ArrayList();
        for (Object obj2 : arrayList2) {
            if (!isMyTypeVariable((SimpleTypeMarker) obj2)) {
                arrayList5.add(obj2);
            }
        }
        ArrayList arrayList6 = arrayList5;
        boolean z = true;
        if (!arrayList6.isEmpty()) {
            AbstractTypeChecker abstractTypeChecker = AbstractTypeChecker.INSTANCE;
            Intrinsics.checkNotNull(typeSystemInferenceExtensionContext, "null cannot be cast to non-null type org.jetbrains.kotlin.types.model.TypeCheckerProviderContext");
            if (AbstractTypeChecker.isSubtypeOf$default(abstractTypeChecker, (TypeCheckerProviderContext) typeSystemInferenceExtensionContext, (KotlinTypeMarker) typeSystemInferenceExtensionContext.intersectTypes((List<? extends KotlinTypeMarker>) arrayList6), superType, false, 8, (Object) null)) {
                return true;
            }
        }
        ArrayList<SimpleTypeMarker> arrayList7 = arrayList6;
        if (!(arrayList7 instanceof Collection) || !arrayList7.isEmpty()) {
            for (SimpleTypeMarker simpleTypeMarker : arrayList7) {
                AbstractNullabilityChecker abstractNullabilityChecker = AbstractNullabilityChecker.INSTANCE;
                Intrinsics.checkNotNull(typeSystemInferenceExtensionContext, "null cannot be cast to non-null type org.jetbrains.kotlin.types.model.TypeCheckerProviderContext");
                if (abstractNullabilityChecker.isSubtypeOfAny(typeSystemInferenceExtensionContext, simpleTypeMarker)) {
                    ArrayList arrayList8 = arrayList4;
                    if (!(arrayList8 instanceof Collection) || !arrayList8.isEmpty()) {
                        Iterator it2 = arrayList8.iterator();
                        while (it2.hasNext()) {
                            if (!simplifyUpperConstraint((SimpleTypeMarker) it2.next(), typeSystemInferenceExtensionContext.withNullability(superType, true))) {
                                z = false;
                                break;
                            }
                        }
                    }
                    return Boolean.valueOf(z);
                }
            }
        }
        ArrayList arrayList9 = arrayList4;
        if (!(arrayList9 instanceof Collection) || !arrayList9.isEmpty()) {
            Iterator it3 = arrayList9.iterator();
            while (it3.hasNext()) {
                if (!simplifyUpperConstraint((SimpleTypeMarker) it3.next(), superType)) {
                    z = false;
                    break;
                }
            }
        }
        return Boolean.valueOf(z);
    }

    private final boolean isSubtypeOfByTypeChecker(KotlinTypeMarker subType, KotlinTypeMarker superType) {
        AbstractTypeChecker abstractTypeChecker = AbstractTypeChecker.INSTANCE;
        Intrinsics.checkNotNull(this, "null cannot be cast to non-null type org.jetbrains.kotlin.types.TypeCheckerState");
        return AbstractTypeChecker.isSubtypeOf$default(abstractTypeChecker, (TypeCheckerState) this, subType, superType, false, 8, (Object) null);
    }

    private final void assertInputTypes(KotlinTypeMarker subType, KotlinTypeMarker superType) {
        TypeSystemContext typeSystemContext = getTypeSystemContext();
        if (AbstractTypeChecker.RUN_SLOW_ASSERTIONS) {
            if (subType instanceof SimpleTypeMarker) {
                assertInputTypes$lambda$30$correctSubType(typeSystemContext, this, (SimpleTypeMarker) subType);
            } else if (subType instanceof FlexibleTypeMarker) {
                TypeSystemContext typeSystemContext2 = getTypeSystemContext();
                FlexibleTypeMarker flexibleTypeMarker = (FlexibleTypeMarker) subType;
                if (assertInputTypes$lambda$30$correctSubType(typeSystemContext, this, typeSystemContext2.lowerBound(flexibleTypeMarker))) {
                    assertInputTypes$lambda$30$correctSubType(typeSystemContext, this, typeSystemContext2.upperBound(flexibleTypeMarker));
                }
            } else {
                throw new IllegalStateException("sealed".toString());
            }
            if (superType instanceof SimpleTypeMarker) {
                assertInputTypes$lambda$30$correctSuperType(typeSystemContext, this, (SimpleTypeMarker) superType);
                return;
            }
            if (superType instanceof FlexibleTypeMarker) {
                TypeSystemContext typeSystemContext3 = getTypeSystemContext();
                FlexibleTypeMarker flexibleTypeMarker2 = (FlexibleTypeMarker) superType;
                if (assertInputTypes$lambda$30$correctSuperType(typeSystemContext, this, typeSystemContext3.lowerBound(flexibleTypeMarker2))) {
                    assertInputTypes$lambda$30$correctSuperType(typeSystemContext, this, typeSystemContext3.upperBound(flexibleTypeMarker2));
                    return;
                }
                return;
            }
            throw new IllegalStateException("sealed".toString());
        }
    }

    private static final boolean assertInputTypes$lambda$30$correctSubType(TypeSystemContext typeSystemContext, TypeCheckerStateForConstraintSystem typeCheckerStateForConstraintSystem, SimpleTypeMarker simpleTypeMarker) {
        return typeSystemContext.isSingleClassifierType(simpleTypeMarker) || typeSystemContext.isIntersection(typeSystemContext.typeConstructor(simpleTypeMarker)) || typeCheckerStateForConstraintSystem.isMyTypeVariable(simpleTypeMarker) || typeSystemContext.isError(simpleTypeMarker) || typeSystemContext.isIntegerLiteralType(simpleTypeMarker);
    }

    private static final boolean assertInputTypes$lambda$30$correctSuperType(TypeSystemContext typeSystemContext, TypeCheckerStateForConstraintSystem typeCheckerStateForConstraintSystem, SimpleTypeMarker simpleTypeMarker) {
        return typeSystemContext.isSingleClassifierType(simpleTypeMarker) || typeSystemContext.isIntersection(typeSystemContext.typeConstructor(simpleTypeMarker)) || typeCheckerStateForConstraintSystem.isMyTypeVariable(simpleTypeMarker) || typeSystemContext.isError(simpleTypeMarker) || typeSystemContext.isIntegerLiteralType(simpleTypeMarker);
    }
}
