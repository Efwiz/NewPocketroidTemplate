package org.jetbrains.kotlin.types.checker;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeAttributes;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.types.error.ErrorScopeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.model.CaptureStatus;
import org.jetbrains.kotlin.types.model.CapturedTypeMarker;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: NewCapturedType.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002B)\b\u0010\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bB=\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\f\u001a\u00020\r\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u0011\u0012\b\b\u0002\u0010\u0012\u001a\u00020\u0011¢\u0006\u0002\u0010\u0013J\u0010\u0010%\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0011H\u0016J\u0010\u0010'\u001a\u00020\u00002\u0006\u0010(\u001a\u00020)H\u0017J\u0010\u0010*\u001a\u00020\u00012\u0006\u0010+\u001a\u00020\u000fH\u0016R\u001a\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u00158VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0017R\u0014\u0010\u000e\u001a\u00020\u000fX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u001bR\u0014\u0010\f\u001a\u00020\rX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0014\u0010\u0010\u001a\u00020\u0011X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u001eR\u0011\u0010\u0012\u001a\u00020\u0011¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u001eR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u001f\u0010 R\u0014\u0010!\u001a\u00020\"8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b#\u0010$¨\u0006,"}, d2 = {"Lorg/jetbrains/kotlin/types/checker/NewCapturedType;", "Lorg/jetbrains/kotlin/types/SimpleType;", "Lorg/jetbrains/kotlin/types/model/CapturedTypeMarker;", "captureStatus", "Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "lowerType", "Lorg/jetbrains/kotlin/types/UnwrappedType;", "projection", "Lorg/jetbrains/kotlin/types/TypeProjection;", "typeParameter", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "(Lorg/jetbrains/kotlin/types/model/CaptureStatus;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/types/TypeProjection;Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;)V", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/checker/NewCapturedTypeConstructor;", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "isMarkedNullable", "", "isProjectionNotNull", "(Lorg/jetbrains/kotlin/types/model/CaptureStatus;Lorg/jetbrains/kotlin/types/checker/NewCapturedTypeConstructor;Lorg/jetbrains/kotlin/types/UnwrappedType;Lorg/jetbrains/kotlin/types/TypeAttributes;ZZ)V", "arguments", "", "getArguments", "()Ljava/util/List;", "getAttributes", "()Lorg/jetbrains/kotlin/types/TypeAttributes;", "getCaptureStatus", "()Lorg/jetbrains/kotlin/types/model/CaptureStatus;", "getConstructor", "()Lorg/jetbrains/kotlin/types/checker/NewCapturedTypeConstructor;", "()Z", "getLowerType", "()Lorg/jetbrains/kotlin/types/UnwrappedType;", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getMemberScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "makeNullableAsSpecified", "newNullability", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "replaceAttributes", "newAttributes", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class NewCapturedType extends SimpleType implements CapturedTypeMarker {
    private final TypeAttributes attributes;
    private final CaptureStatus captureStatus;
    private final NewCapturedTypeConstructor constructor;
    private final boolean isMarkedNullable;
    private final boolean isProjectionNotNull;
    private final UnwrappedType lowerType;

    public final CaptureStatus getCaptureStatus() {
        return this.captureStatus;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public NewCapturedTypeConstructor getConstructor() {
        return this.constructor;
    }

    public final UnwrappedType getLowerType() {
        return this.lowerType;
    }

    public /* synthetic */ NewCapturedType(CaptureStatus captureStatus, NewCapturedTypeConstructor newCapturedTypeConstructor, UnwrappedType unwrappedType, TypeAttributes typeAttributes, boolean z, boolean z2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(captureStatus, newCapturedTypeConstructor, unwrappedType, (i & 8) != 0 ? TypeAttributes.INSTANCE.getEmpty() : typeAttributes, (i & 16) != 0 ? false : z, (i & 32) != 0 ? false : z2);
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public TypeAttributes getAttributes() {
        return this.attributes;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    /* JADX INFO: renamed from: isMarkedNullable, reason: from getter */
    public boolean getIsMarkedNullable() {
        return this.isMarkedNullable;
    }

    /* JADX INFO: renamed from: isProjectionNotNull, reason: from getter */
    public final boolean getIsProjectionNotNull() {
        return this.isProjectionNotNull;
    }

    public NewCapturedType(CaptureStatus captureStatus, NewCapturedTypeConstructor constructor, UnwrappedType unwrappedType, TypeAttributes attributes, boolean z, boolean z2) {
        Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(attributes, "attributes");
        this.captureStatus = captureStatus;
        this.constructor = constructor;
        this.lowerType = unwrappedType;
        this.attributes = attributes;
        this.isMarkedNullable = z;
        this.isProjectionNotNull = z2;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public NewCapturedType(CaptureStatus captureStatus, UnwrappedType unwrappedType, TypeProjection projection, TypeParameterDescriptor typeParameter) {
        Intrinsics.checkNotNullParameter(captureStatus, "captureStatus");
        Intrinsics.checkNotNullParameter(projection, "projection");
        Intrinsics.checkNotNullParameter(typeParameter, "typeParameter");
        TypeAttributes typeAttributes = null;
        boolean z = false;
        boolean z2 = false;
        this(captureStatus, new NewCapturedTypeConstructor(projection, null, null, typeParameter, 6, null), unwrappedType, typeAttributes, z, z2, 56, null);
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public List<TypeProjection> getArguments() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public MemberScope getMemberScope() {
        return ErrorUtils.createErrorScope(ErrorScopeKind.CAPTURED_TYPE_SCOPE, true, new String[0]);
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public SimpleType replaceAttributes(TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return new NewCapturedType(this.captureStatus, getConstructor(), this.lowerType, newAttributes, getIsMarkedNullable(), this.isProjectionNotNull);
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public NewCapturedType makeNullableAsSpecified(boolean newNullability) {
        return new NewCapturedType(this.captureStatus, getConstructor(), this.lowerType, getAttributes(), newNullability, false, 32, null);
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType, org.jetbrains.kotlin.types.KotlinType
    public NewCapturedType refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        CaptureStatus captureStatus = this.captureStatus;
        NewCapturedTypeConstructor newCapturedTypeConstructorRefine = getConstructor().refine(kotlinTypeRefiner);
        UnwrappedType unwrappedType = this.lowerType;
        return new NewCapturedType(captureStatus, newCapturedTypeConstructorRefine, unwrappedType != null ? kotlinTypeRefiner.refineType((KotlinTypeMarker) unwrappedType).unwrap() : null, getAttributes(), getIsMarkedNullable(), false, 32, null);
    }
}
