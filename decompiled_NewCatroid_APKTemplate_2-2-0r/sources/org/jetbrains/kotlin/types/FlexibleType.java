package org.jetbrains.kotlin.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.renderer.DescriptorRenderer;
import org.jetbrains.kotlin.renderer.DescriptorRendererOptions;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.FlexibleTypeMarker;

/* JADX INFO: compiled from: KotlinType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000h\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005¢\u0006\u0002\u0010\u0007J\u0010\u0010'\u001a\u00020\u00002\u0006\u0010(\u001a\u00020)H'J\u0018\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020-2\u0006\u0010.\u001a\u00020/H&J\u0010\u00100\u001a\u00020\u00192\u0006\u00101\u001a\u00020!H\u0016J\b\u00102\u001a\u00020+H\u0016R\u001a\u0010\b\u001a\b\u0012\u0004\u0012\u00020\n0\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0012\u0010\u0015\u001a\u00020\u0005X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u0018\u001a\u00020\u00198VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u001aR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0017R\u0014\u0010\u001c\u001a\u00020\u001d8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001e\u0010\u001fR\u0014\u0010 \u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\"\u0010#R\u0014\u0010$\u001a\u00020!8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b%\u0010#R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b&\u0010\u0017¨\u00063"}, d2 = {"Lorg/jetbrains/kotlin/types/FlexibleType;", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "Lorg/jetbrains/kotlin/types/SubtypingRepresentatives;", "Lorg/jetbrains/kotlin/types/model/FlexibleTypeMarker;", "lowerBound", "Lorg/jetbrains/kotlin/types/SimpleType;", "upperBound", "(Lorg/jetbrains/kotlin/types/SimpleType;Lorg/jetbrains/kotlin/types/SimpleType;)V", "arguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "getArguments", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/types/TypeAttributes;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "getConstructor", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate", "()Lorg/jetbrains/kotlin/types/SimpleType;", "isMarkedNullable", "", "()Z", "getLowerBound", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getMemberScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "subTypeRepresentative", "Lorg/jetbrains/kotlin/types/KotlinType;", "getSubTypeRepresentative", "()Lorg/jetbrains/kotlin/types/KotlinType;", "superTypeRepresentative", "getSuperTypeRepresentative", "getUpperBound", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "render", "", "renderer", "Lorg/jetbrains/kotlin/renderer/DescriptorRenderer;", "options", "Lorg/jetbrains/kotlin/renderer/DescriptorRendererOptions;", "sameTypeConstructor", ModuleXmlParser.TYPE, "toString", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FlexibleType extends UnwrappedType implements SubtypingRepresentatives, FlexibleTypeMarker {
    private final SimpleType lowerBound;
    private final SimpleType upperBound;

    public abstract SimpleType getDelegate();

    @Override // org.jetbrains.kotlin.types.UnwrappedType, org.jetbrains.kotlin.types.KotlinType
    public abstract FlexibleType refine(KotlinTypeRefiner kotlinTypeRefiner);

    public abstract String render(DescriptorRenderer renderer, DescriptorRendererOptions options);

    @Override // org.jetbrains.kotlin.types.SubtypingRepresentatives
    public boolean sameTypeConstructor(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return false;
    }

    public final SimpleType getLowerBound() {
        return this.lowerBound;
    }

    public final SimpleType getUpperBound() {
        return this.upperBound;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FlexibleType(SimpleType lowerBound, SimpleType upperBound) {
        super(null);
        Intrinsics.checkNotNullParameter(lowerBound, "lowerBound");
        Intrinsics.checkNotNullParameter(upperBound, "upperBound");
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override // org.jetbrains.kotlin.types.SubtypingRepresentatives
    public KotlinType getSubTypeRepresentative() {
        return this.lowerBound;
    }

    @Override // org.jetbrains.kotlin.types.SubtypingRepresentatives
    public KotlinType getSuperTypeRepresentative() {
        return this.upperBound;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public TypeAttributes getAttributes() {
        return getDelegate().getAttributes();
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public TypeConstructor getConstructor() {
        return getDelegate().getConstructor();
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public List<TypeProjection> getArguments() {
        return getDelegate().getArguments();
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    /* JADX INFO: renamed from: isMarkedNullable */
    public boolean getIsMarkedNullable() {
        return getDelegate().getIsMarkedNullable();
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public MemberScope getMemberScope() {
        return getDelegate().getMemberScope();
    }

    public String toString() {
        return DescriptorRenderer.DEBUG_TEXT.renderType(this);
    }
}
