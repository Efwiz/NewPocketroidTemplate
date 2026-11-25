package org.jetbrains.kotlin.types;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: TypeWithEnhancement.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0014\u001a\u00020\u0015H\u0017J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\u00102\u0006\u0010\u001d\u001a\u00020\u001eH\u0016J\b\u0010\u001f\u001a\u00020\u0017H\u0016R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\u0003\u001a\u00020\u0001X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/types/FlexibleTypeWithEnhancement;", "Lorg/jetbrains/kotlin/types/FlexibleType;", "Lorg/jetbrains/kotlin/types/TypeWithEnhancement;", "origin", "enhancement", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Lorg/jetbrains/kotlin/types/FlexibleType;Lorg/jetbrains/kotlin/types/KotlinType;)V", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "Lorg/jetbrains/kotlin/types/SimpleType;", "getDelegate", "()Lorg/jetbrains/kotlin/types/SimpleType;", "getEnhancement", "()Lorg/jetbrains/kotlin/types/KotlinType;", "getOrigin", "()Lorg/jetbrains/kotlin/types/FlexibleType;", "makeNullableAsSpecified", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "newNullability", "", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "render", "", "renderer", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "options", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptions;", "replaceAttributes", "newAttributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "toString", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FlexibleTypeWithEnhancement extends FlexibleType implements TypeWithEnhancement {
    private final KotlinType enhancement;
    private final FlexibleType origin;

    @Override // org.jetbrains.kotlin.types.TypeWithEnhancement
    public FlexibleType getOrigin() {
        return this.origin;
    }

    @Override // org.jetbrains.kotlin.types.TypeWithEnhancement
    public KotlinType getEnhancement() {
        return this.enhancement;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlexibleTypeWithEnhancement(FlexibleType origin, KotlinType enhancement) {
        super(origin.getLowerBound(), origin.getUpperBound());
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(enhancement, "enhancement");
        this.origin = origin;
        this.enhancement = enhancement;
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public UnwrappedType replaceAttributes(TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return TypeWithEnhancementKt.wrapEnhancement(getOrigin().replaceAttributes(newAttributes), getEnhancement());
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public UnwrappedType makeNullableAsSpecified(boolean newNullability) {
        return TypeWithEnhancementKt.wrapEnhancement(getOrigin().makeNullableAsSpecified(newNullability), getEnhancement().unwrap().makeNullableAsSpecified(newNullability));
    }

    @Override // org.jetbrains.kotlin.types.FlexibleType
    public String render(DescriptorRenderer renderer, DescriptorRendererOptions options) {
        Intrinsics.checkNotNullParameter(renderer, "renderer");
        Intrinsics.checkNotNullParameter(options, "options");
        if (options.getEnhancedTypes()) {
            return renderer.renderType(getEnhancement());
        }
        return getOrigin().render(renderer, options);
    }

    @Override // org.jetbrains.kotlin.types.FlexibleType
    public SimpleType getDelegate() {
        return getOrigin().getDelegate();
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType, org.jetbrains.kotlin.types.KotlinType
    public FlexibleTypeWithEnhancement refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        KotlinType kotlinTypeRefineType = kotlinTypeRefiner.refineType((KotlinTypeMarker) getOrigin());
        Intrinsics.checkNotNull(kotlinTypeRefineType, "null cannot be cast to non-null type org.jetbrains.kotlin.types.FlexibleType");
        return new FlexibleTypeWithEnhancement((FlexibleType) kotlinTypeRefineType, kotlinTypeRefiner.refineType((KotlinTypeMarker) getEnhancement()));
    }

    @Override // org.jetbrains.kotlin.types.FlexibleType
    public String toString() {
        return "[@EnhancedForWarnings(" + getEnhancement() + ")] " + getOrigin();
    }
}
