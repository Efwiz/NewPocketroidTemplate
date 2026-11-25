package org.jetbrains.kotlin.resolve.calls.inference;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.FlexibleType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;

/* JADX INFO: compiled from: BuilderInferenceUtil.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001f\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u0007H\u0016J\u0010\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u0015H\u0017J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/TypeTemplate;", "Lorg/jetbrains/kotlin/types/FlexibleType;", "typeVariable", "Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "builderInferenceData", "Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceData;", "nullable", "", "(Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceData;Z)V", "getBuilderInferenceData", "()Lorg/jetbrains/kotlin/resolve/calls/inference/BuilderInferenceData;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/types/SimpleType;", "getDelegate", "()Lorg/jetbrains/kotlin/types/SimpleType;", "getTypeVariable", "()Lorg/jetbrains/kotlin/resolve/calls/inference/TypeVariable;", "makeNullableAsSpecified", "newNullability", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "render", "", "renderer", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "options", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptions;", "replaceAttributes", "newAttributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeTemplate extends FlexibleType {
    private final BuilderInferenceData builderInferenceData;
    private final TypeVariable typeVariable;

    @Override // org.jetbrains.kotlin.types.UnwrappedType, org.jetbrains.kotlin.types.KotlinType
    public TypeTemplate refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public TypeTemplate replaceAttributes(TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return this;
    }

    public /* synthetic */ TypeTemplate(TypeVariable typeVariable, BuilderInferenceData builderInferenceData, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeVariable, builderInferenceData, (i & 4) != 0 ? true : z);
    }

    public final TypeVariable getTypeVariable() {
        return this.typeVariable;
    }

    public final BuilderInferenceData getBuilderInferenceData() {
        return this.builderInferenceData;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public TypeTemplate(TypeVariable typeVariable, BuilderInferenceData builderInferenceData, boolean z) {
        Intrinsics.checkNotNullParameter(typeVariable, "typeVariable");
        Intrinsics.checkNotNullParameter(builderInferenceData, "builderInferenceData");
        SimpleType nothingType = DescriptorUtilsKt.getBuiltIns(typeVariable.getOriginalTypeParameter()).getNothingType();
        Intrinsics.checkNotNullExpressionValue(nothingType, "getNothingType(...)");
        super(nothingType, DescriptorUtilsKt.getBuiltIns(typeVariable.getOriginalTypeParameter()).getAnyType().makeNullableAsSpecified(z));
        this.typeVariable = typeVariable;
        this.builderInferenceData = builderInferenceData;
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public TypeTemplate makeNullableAsSpecified(boolean newNullability) {
        return new TypeTemplate(this.typeVariable, this.builderInferenceData, newNullability);
    }

    @Override // org.jetbrains.kotlin.types.FlexibleType
    public SimpleType getDelegate() {
        return getUpperBound();
    }

    @Override // org.jetbrains.kotlin.types.FlexibleType
    public String render(DescriptorRenderer renderer, DescriptorRendererOptions options) {
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        Intrinsics.checkNotNullParameter(options, "options");
        return "~" + renderer.renderType(this.typeVariable.getType());
    }
}
