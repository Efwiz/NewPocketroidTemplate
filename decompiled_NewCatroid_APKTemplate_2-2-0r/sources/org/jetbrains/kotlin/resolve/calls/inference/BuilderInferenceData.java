package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.inference.constraintPosition.ConstraintPositionKind;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.TypeSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: BuilderInferenceUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J%\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\u0011\u001a\u00020\u0006H\u0000¢\u0006\u0002\b\u0012J\u0006\u0010\u0013\u001a\u00020\rJ\u000e\u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\tJ\u0006\u0010\u0016\u001a\u00020\rJ\u0018\u0010\u0017\u001a\u00020\u00062\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000fH\u0002J\u000e\u0010\u0018\u001a\u00020\r2\u0006\u0010\u0019\u001a\u00020\u001aJ\u000e\u0010\u001b\u001a\u00020\u000f2\u0006\u0010\u001c\u001a\u00020\u000fR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e¢\u0006\u0002\n\u0000R*\u0010\u0007\u001a\u001e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n0\bj\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\n`\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001d"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceData;", "", "()V", "csBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilderImpl;", "hereIsBadCall", "", "typeTemplates", "Ljava/util/HashMap;", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeTemplate;", "Lkotlin/collections/HashMap;", "addConstraint", "", "subType", "Lorg/jetbrains/kotlin/types/KotlinType;", "superType", "allowOnlyTrivialConstraints", "addConstraint$frontend", "badCallHappened", "getTypeTemplate", "typeVariable", "initSystem", "isTrivialConstraint", "reportInferenceResult", "externalCSBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystem$Builder;", "toNewVariableType", ModuleXmlParser.TYPE, "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BuilderInferenceData {
    private boolean hereIsBadCall;
    private final ConstraintSystemBuilderImpl csBuilder = new ConstraintSystemBuilderImpl(null, 1, null);
    private final HashMap<TypeVariable, TypeTemplate> typeTemplates = new HashMap<>();

    public final TypeTemplate getTypeTemplate(TypeVariable typeVariable) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        HashMap<TypeVariable, TypeTemplate> map = this.typeTemplates;
        TypeTemplate typeTemplate = map.get(typeVariable);
        if (typeTemplate == null) {
            typeTemplate = new TypeTemplate(typeVariable, this, false, 4, null);
            map.put(typeVariable, typeTemplate);
        }
        return typeTemplate;
    }

    public final void initSystem() {
        ConstraintSystemBuilderImpl constraintSystemBuilderImpl = this.csBuilder;
        CallHandle.NONE none = CallHandle.NONE.INSTANCE;
        Set<TypeVariable> setKeySet = this.typeTemplates.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "<get-keys>(...)");
        Set<TypeVariable> set = setKeySet;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        Iterator<T> it = set.iterator();
        while (it.hasNext()) {
            arrayList.add(((TypeVariable) it.next()).getFreshTypeParameter());
        }
        ConstraintSystem.Builder.registerTypeVariables$default(constraintSystemBuilderImpl, none, arrayList, false, 4, null);
    }

    public final KotlinType toNewVariableType(KotlinType type) {
        TypeVariable typeVariable;
        TypeParameterDescriptor freshTypeParameter;
        TypeSubstitution substitution;
        Intrinsics.checkNotNullParameter(type, "type");
        UnwrappedType unwrappedTypeUnwrap = type.unwrap();
        KotlinType nullableAnyType = null;
        TypeTemplate typeTemplate = unwrappedTypeUnwrap instanceof TypeTemplate ? (TypeTemplate) unwrappedTypeUnwrap : null;
        if (typeTemplate == null || (typeVariable = typeTemplate.getTypeVariable()) == null || (freshTypeParameter = typeVariable.getFreshTypeParameter()) == null) {
            return type;
        }
        TypeSubstitutor typeSubstitutor = this.csBuilder.getTypeVariableSubstitutors().get(CallHandle.NONE.INSTANCE);
        if (typeSubstitutor != null && (substitution = typeSubstitutor.getSubstitution()) != null) {
            SimpleType defaultType = freshTypeParameter.getDefaultType();
            Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
            TypeProjection typeProjectionMo7475get = substitution.mo7475get(defaultType);
            if (typeProjectionMo7475get != null) {
                nullableAnyType = typeProjectionMo7475get.getNullableAnyType();
            }
        }
        return nullableAnyType == null ? type : nullableAnyType;
    }

    public final void addConstraint$frontend(KotlinType subType, KotlinType superType, boolean allowOnlyTrivialConstraints) {
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        KotlinType newVariableType = toNewVariableType(subType);
        KotlinType newVariableType2 = toNewVariableType(superType);
        if (allowOnlyTrivialConstraints) {
            if (isTrivialConstraint(subType, superType)) {
                return;
            } else {
                badCallHappened();
            }
        }
        this.csBuilder.addSubtypeConstraint(newVariableType, newVariableType2, ConstraintPositionKind.SPECIAL.position());
    }

    private final boolean isTrivialConstraint(KotlinType subType, KotlinType superType) {
        return ((subType instanceof SimpleType) && TypeUtilsKt.isNothing(subType)) || ((superType instanceof SimpleType) && TypeUtilsKt.isNullableAny(superType));
    }

    public final void reportInferenceResult(ConstraintSystem.Builder externalCSBuilder) {
        Intrinsics.checkNotNullParameter(externalCSBuilder, "externalCSBuilder");
        if (this.hereIsBadCall) {
            return;
        }
        TypeSubstitution substitution = this.csBuilder.build().getResultingSubstitutor().getSubstitution();
        Intrinsics.checkNotNullExpressionValue(substitution, "getSubstitution(...)");
        Iterator<Map.Entry<TypeVariable, TypeTemplate>> it = this.typeTemplates.entrySet().iterator();
        while (it.hasNext()) {
            TypeVariable key = it.next().getKey();
            TypeProjection typeProjectionMo7475get = substitution.mo7475get(key.getType());
            KotlinType nullableAnyType = typeProjectionMo7475get != null ? typeProjectionMo7475get.getNullableAnyType() : null;
            externalCSBuilder.addSubtypeConstraint(key.getType(), nullableAnyType, ConstraintPositionKind.FROM_COMPLETER.position());
            externalCSBuilder.addSubtypeConstraint(nullableAnyType, key.getType(), ConstraintPositionKind.FROM_COMPLETER.position());
        }
    }

    public final void badCallHappened() {
        this.hereIsBadCall = true;
    }
}
