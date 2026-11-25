package org.jetbrains.kotlin.resolve.calls.inference.components;

import java.util.Collection;
import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.resolve.calls.components.ClassicTypeSystemContextForCS;
import org.jetbrains.kotlin.resolve.calls.inference.ConstraintSystemBuilder;
import org.jetbrains.kotlin.resolve.calls.inference.DescriptorRelatedInferenceUtilsKt;
import org.jetbrains.kotlin.resolve.calls.inference.model.NewConstraintSystemImpl;
import org.jetbrains.kotlin.resolve.calls.inference.model.SimpleConstraintSystemConstraintPosition;
import org.jetbrains.kotlin.resolve.calls.inference.model.TypeVariableFromCallableDescriptor;
import org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructorSubstitution;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.types.model.TypeParameterMarker;
import org.jetbrains.kotlin.types.model.TypeSubstitutorMarker;
import org.jetbrains.kotlin.types.model.TypeSystemContextKt;
import org.jetbrains.kotlin.types.model.TypeSystemInferenceExtensionContext;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: SimpleConstraintSystemImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000b\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t¢\u0006\u0002\u0010\nJ\u0018\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001eH\u0016J\b\u0010 \u001a\u00020\fH\u0016J\u0016\u0010!\u001a\u00020\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020%0$H\u0016R\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0013\u001a\u00020\u0014¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\u0017\u001a\u00020\u0018¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001a¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/components/SimpleConstraintSystemImpl;", "Lorg/jetbrains/kotlin/resolve/calls/results/SimpleConstraintSystem;", "constraintInjector", "Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/resolve/calls/inference/components/ConstraintInjector;Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "captureFromArgument", "", "getCaptureFromArgument", "()Z", "context", "Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "getContext", "()Lorg/jetbrains/kotlin/types/model/TypeSystemInferenceExtensionContext;", "csBuilder", "Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "getCsBuilder", "()Lorg/jetbrains/kotlin/resolve/calls/inference/ConstraintSystemBuilder;", "system", "Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "getSystem", "()Lorg/jetbrains/kotlin/resolve/calls/inference/model/NewConstraintSystemImpl;", "addSubtypeConstraint", "", "subType", "Lorg/jetbrains/kotlin/types/model/KotlinTypeMarker;", "superType", "hasContradiction", "registerTypeVariables", "Lorg/jetbrains/kotlin/types/model/TypeSubstitutorMarker;", "typeParameters", "", "Lorg/jetbrains/kotlin/types/model/TypeParameterMarker;", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SimpleConstraintSystemImpl implements SimpleConstraintSystem {
    private final ConstraintSystemBuilder csBuilder;
    private final NewConstraintSystemImpl system;

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public boolean getCaptureFromArgument() {
        return true;
    }

    public SimpleConstraintSystemImpl(ConstraintInjector constraintInjector, KotlinBuiltIns builtIns, KotlinTypeRefiner kotlinTypeRefiner, LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(constraintInjector, "constraintInjector");
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        NewConstraintSystemImpl newConstraintSystemImpl = new NewConstraintSystemImpl(constraintInjector, new ClassicTypeSystemContextForCS(builtIns, kotlinTypeRefiner), languageVersionSettings);
        this.system = newConstraintSystemImpl;
        this.csBuilder = newConstraintSystemImpl.getBuilder();
    }

    public final NewConstraintSystemImpl getSystem() {
        return this.system;
    }

    public final ConstraintSystemBuilder getCsBuilder() {
        return this.csBuilder;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public TypeSubstitutorMarker registerTypeVariables(Collection<? extends TypeParameterMarker> typeParameters) {
        Intrinsics.checkNotNullParameter(typeParameters, "typeParameters");
        Collection<? extends TypeParameterMarker> collection = typeParameters;
        LinkedHashMap linkedHashMap = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(collection, 10)), 16));
        for (TypeParameterMarker typeParameterMarker : collection) {
            TypeSystemContextKt.requireOrDescribe(typeParameterMarker instanceof TypeParameterDescriptor, typeParameterMarker);
            TypeParameterDescriptor typeParameterDescriptor = (TypeParameterDescriptor) typeParameterMarker;
            TypeVariableFromCallableDescriptor typeVariableFromCallableDescriptor = new TypeVariableFromCallableDescriptor(typeParameterDescriptor);
            this.csBuilder.registerVariable(typeVariableFromCallableDescriptor);
            Pair pair = TuplesKt.to(typeParameterDescriptor.getDefaultType().getConstructor(), TypeUtilsKt.asTypeProjection(typeVariableFromCallableDescriptor.getDefaultType()));
            linkedHashMap.put(pair.getFirst(), pair.getSecond());
        }
        TypeSubstitutor typeSubstitutorBuildSubstitutor = TypeConstructorSubstitution.Companion.createByConstructorsMap$default(TypeConstructorSubstitution.INSTANCE, linkedHashMap, false, 2, null).buildSubstitutor();
        for (TypeParameterMarker typeParameterMarker2 : typeParameters) {
            TypeSystemContextKt.requireOrDescribe(typeParameterMarker2 instanceof TypeParameterDescriptor, typeParameterMarker2);
            TypeParameterDescriptor typeParameterDescriptor2 = (TypeParameterDescriptor) typeParameterMarker2;
            for (KotlinType kotlinType : typeParameterDescriptor2.getUpperBounds()) {
                SimpleType defaultType = typeParameterDescriptor2.getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                addSubtypeConstraint(DescriptorRelatedInferenceUtilsKt.substitute(typeSubstitutorBuildSubstitutor, defaultType), DescriptorRelatedInferenceUtilsKt.substitute(typeSubstitutorBuildSubstitutor, kotlinType.unwrap()));
            }
        }
        return typeSubstitutorBuildSubstitutor;
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public void addSubtypeConstraint(KotlinTypeMarker subType, KotlinTypeMarker superType) {
        Intrinsics.checkNotNullParameter(subType, "subType");
        Intrinsics.checkNotNullParameter(superType, "superType");
        this.csBuilder.addSubtypeConstraint(subType, superType, SimpleConstraintSystemConstraintPosition.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public boolean hasContradiction() {
        return this.csBuilder.getHasContradiction();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.results.SimpleConstraintSystem
    public TypeSystemInferenceExtensionContext getContext() {
        return this.system;
    }
}
