package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.DynamicTypeMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: dynamicTypes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0010\u0010\u0011\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u000fH\u0016J\u0010\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u0015H\u0017J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00002\u0006\u0010\u001d\u001a\u00020\u0006H\u0016R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0014\u0010\u000e\u001a\u00020\u000f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u0010¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/types/DynamicType;", "Lorg/jetbrains/kotlin/types/FlexibleType;", "Lorg/jetbrains/kotlin/types/model/DynamicTypeMarker;", "builtIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "(Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;Lorg/jetbrains/kotlin/types/TypeAttributes;)V", "getAttributes", "()Lorg/jetbrains/kotlin/types/TypeAttributes;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/types/SimpleType;", "getDelegate", "()Lorg/jetbrains/kotlin/types/SimpleType;", "isMarkedNullable", "", "()Z", "makeNullableAsSpecified", "newNullability", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "render", "", "renderer", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "options", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptions;", "replaceAttributes", "newAttributes", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DynamicType extends FlexibleType implements DynamicTypeMarker {
    private final TypeAttributes attributes;

    @Override // org.jetbrains.kotlin.types.FlexibleType, org.jetbrains.kotlin.types.KotlinType
    /* JADX INFO: renamed from: isMarkedNullable */
    public boolean getIsMarkedNullable() {
        return false;
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public DynamicType makeNullableAsSpecified(boolean newNullability) {
        return this;
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType, org.jetbrains.kotlin.types.KotlinType
    public DynamicType refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    @Override // org.jetbrains.kotlin.types.FlexibleType, org.jetbrains.kotlin.types.KotlinType
    public TypeAttributes getAttributes() {
        return this.attributes;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public DynamicType(KotlinBuiltIns builtIns, TypeAttributes attributes) {
        Intrinsics.checkNotNullParameter(builtIns, "builtIns");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        SimpleType nothingType = builtIns.getNothingType();
        Intrinsics.checkNotNullExpressionValue(nothingType, "getNothingType(...)");
        SimpleType nullableAnyType = builtIns.getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getNullableAnyType(...)");
        super(nothingType, nullableAnyType);
        this.attributes = attributes;
    }

    @Override // org.jetbrains.kotlin.types.FlexibleType
    public SimpleType getDelegate() {
        return getUpperBound();
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public DynamicType replaceAttributes(TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return new DynamicType(TypeUtilsKt.getBuiltIns(getDelegate()), newAttributes);
    }

    @Override // org.jetbrains.kotlin.types.FlexibleType
    public String render(DescriptorRenderer renderer, DescriptorRendererOptions options) {
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        Intrinsics.checkNotNullParameter(options, "options");
        return "dynamic";
    }
}
