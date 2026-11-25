package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.description.type.TypeDescription;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.SubtypingRepresentatives;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.error.ErrorScopeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;

/* JADX INFO: compiled from: CapturedTypeConstructor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B+\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010\"\u001a\u00020\u00002\u0006\u0010#\u001a\u00020\tH\u0016J\u0010\u0010$\u001a\u00020\u00002\u0006\u0010%\u001a\u00020&H\u0017J\u0010\u0010'\u001a\u00020\u00012\u0006\u0010(\u001a\u00020\u000bH\u0016J\u0018\u0010)\u001a\u00020\u001b2\u0006\u0010*\u001a\u00020+2\u0006\u0010,\u001a\u00020\u001bH\u0002J\u0010\u0010-\u001a\u00020\t2\u0006\u0010.\u001a\u00020\u001bH\u0016J\b\u0010/\u001a\u000200H\u0016R\u001a\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00050\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\n\u001a\u00020\u000bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0006\u001a\u00020\u0007X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0014\u0010\b\u001a\u00020\tX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019R\u0014\u0010\u001a\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u001e\u001a\u00020\u001b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u001f\u0010\u001dR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b \u0010!¨\u00061"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/CapturedType;", "Lorg/jetbrains/kotlin/types/SimpleType;", "Lorg/jetbrains/kotlin/types/SubtypingRepresentatives;", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "typeProjection", "Lorg/jetbrains/kotlin/types/TypeProjection;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/resolve/calls/inference/CapturedTypeConstructor;", "isMarkedNullable", "", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "(Lorg/jetbrains/kotlin/types/TypeProjection;Lorg/jetbrains/kotlin/resolve/calls/inference/CapturedTypeConstructor;ZLorg/jetbrains/kotlin/types/TypeAttributes;)V", "arguments", "", "getArguments", "()Ljava/util/List;", "getAttributes", "()Lorg/jetbrains/kotlin/types/TypeAttributes;", "getConstructor", "()Lorg/jetbrains/kotlin/resolve/calls/inference/CapturedTypeConstructor;", "()Z", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getMemberScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "subTypeRepresentative", "Lorg/jetbrains/kotlin/types/KotlinType;", "getSubTypeRepresentative", "()Lorg/jetbrains/kotlin/types/KotlinType;", "superTypeRepresentative", "getSuperTypeRepresentative", "getTypeProjection", "()Lorg/jetbrains/kotlin/types/TypeProjection;", "makeNullableAsSpecified", "newNullability", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "replaceAttributes", "newAttributes", "representative", "variance", "Lorg/jetbrains/kotlin/types/Variance;", "default", "sameTypeConstructor", ModuleXmlParser.TYPE, "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CapturedType extends SimpleType implements SubtypingRepresentatives, CapturedTypeMarker {
    private final TypeAttributes attributes;
    private final CapturedTypeConstructor constructor;
    private final boolean isMarkedNullable;
    private final TypeProjection typeProjection;

    public final TypeProjection getTypeProjection() {
        return this.typeProjection;
    }

    public /* synthetic */ CapturedType(TypeProjection typeProjection, CapturedTypeConstructorImpl capturedTypeConstructorImpl, boolean z, TypeAttributes typeAttributes, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(typeProjection, (i & 2) != 0 ? new CapturedTypeConstructorImpl(typeProjection) : capturedTypeConstructorImpl, (i & 4) != 0 ? false : z, (i & 8) != 0 ? TypeAttributes.INSTANCE.getEmpty() : typeAttributes);
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public CapturedTypeConstructor getConstructor() {
        return this.constructor;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    /* JADX INFO: renamed from: isMarkedNullable, reason: from getter */
    public boolean getIsMarkedNullable() {
        return this.isMarkedNullable;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public TypeAttributes getAttributes() {
        return this.attributes;
    }

    public CapturedType(TypeProjection typeProjection, CapturedTypeConstructor constructor, boolean z, TypeAttributes attributes) {
        Intrinsics.checkNotNullParameter(typeProjection, "typeProjection");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        this.typeProjection = typeProjection;
        this.constructor = constructor;
        this.isMarkedNullable = z;
        this.attributes = attributes;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public List<TypeProjection> getArguments() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public MemberScope getMemberScope() {
        return ErrorUtils.createErrorScope(ErrorScopeKind.CAPTURED_TYPE_SCOPE, true, new String[0]);
    }

    @Override // org.jetbrains.kotlin.types.SubtypingRepresentatives
    public KotlinType getSubTypeRepresentative() {
        Variance variance = Variance.OUT_VARIANCE;
        SimpleType nullableAnyType = TypeUtilsKt.getBuiltIns(this).getNullableAnyType();
        Intrinsics.checkNotNullExpressionValue(nullableAnyType, "getNullableAnyType(...)");
        return representative(variance, nullableAnyType);
    }

    @Override // org.jetbrains.kotlin.types.SubtypingRepresentatives
    public KotlinType getSuperTypeRepresentative() {
        Variance variance = Variance.IN_VARIANCE;
        SimpleType nothingType = TypeUtilsKt.getBuiltIns(this).getNothingType();
        Intrinsics.checkNotNullExpressionValue(nothingType, "getNothingType(...)");
        return representative(variance, nothingType);
    }

    private final KotlinType representative(Variance variance, KotlinType kotlinType) {
        if (this.typeProjection.getProjectionKind() == variance) {
            kotlinType = this.typeProjection.getNullableAnyType();
        }
        Intrinsics.checkNotNull(kotlinType);
        return kotlinType;
    }

    @Override // org.jetbrains.kotlin.types.SubtypingRepresentatives
    public boolean sameTypeConstructor(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return getConstructor() == type.getConstructor();
    }

    @Override // org.jetbrains.kotlin.types.SimpleType
    public String toString() {
        return "Captured(" + this.typeProjection + ')' + (getIsMarkedNullable() ? TypeDescription.Generic.OfWildcardType.SYMBOL : "");
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public CapturedType makeNullableAsSpecified(boolean newNullability) {
        return newNullability == getIsMarkedNullable() ? this : new CapturedType(this.typeProjection, getConstructor(), newNullability, getAttributes());
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public SimpleType replaceAttributes(TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return new CapturedType(this.typeProjection, getConstructor(), getIsMarkedNullable(), newAttributes);
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType, org.jetbrains.kotlin.types.KotlinType
    public CapturedType refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        TypeProjection typeProjectionRefine = this.typeProjection.refine(kotlinTypeRefiner);
        Intrinsics.checkNotNullExpressionValue(typeProjectionRefine, "refine(...)");
        return new CapturedType(typeProjectionRefine, getConstructor(), getIsMarkedNullable(), getAttributes());
    }
}
