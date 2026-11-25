package org.jetbrains.kotlin.resolve.calls.inference;

import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.NewCapturedTypeConstructor;

/* JADX INFO: compiled from: CapturedTypeConstructor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\r\u001a\u00020\u000eH\u0016J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0016J\u000e\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00130\u0012H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u0015H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u0018H\u0016J\u0010\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u001cH\u0017J\b\u0010\u001d\u001a\u00020\u001eH\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\nR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\f¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/inference/CapturedTypeConstructorImpl;", "Lorg/jetbrains/kotlin/resolve/calls/inference/CapturedTypeConstructor;", "projection", "Lorg/jetbrains/kotlin/types/TypeProjection;", "(Lorg/jetbrains/kotlin/types/TypeProjection;)V", "newTypeConstructor", "Lorg/jetbrains/kotlin/types/checker/NewCapturedTypeConstructor;", "getNewTypeConstructor", "()Lorg/jetbrains/kotlin/types/checker/NewCapturedTypeConstructor;", "setNewTypeConstructor", "(Lorg/jetbrains/kotlin/types/checker/NewCapturedTypeConstructor;)V", "getProjection", "()Lorg/jetbrains/kotlin/types/TypeProjection;", "getBuiltIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getDeclarationDescriptor", "", "getParameters", "", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "isDenotable", "", "isFinal", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CapturedTypeConstructorImpl implements CapturedTypeConstructor {
    private NewCapturedTypeConstructor newTypeConstructor;
    private final TypeProjection projection;

    public Void getDeclarationDescriptor() {
        return null;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public boolean isDenotable() {
        return false;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public boolean isFinal() {
        return true;
    }

    public CapturedTypeConstructorImpl(TypeProjection projection) {
        Intrinsics.checkNotNullParameter(projection, "projection");
        this.projection = projection;
        getProjection().getProjectionKind();
        Variance variance = Variance.INVARIANT;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getDeclarationDescriptor, reason: collision with other method in class */
    public /* bridge */ /* synthetic */ ClassifierDescriptor getThis$0() {
        return (ClassifierDescriptor) getDeclarationDescriptor();
    }

    @Override // org.jetbrains.kotlin.resolve.calls.inference.CapturedTypeConstructor
    public TypeProjection getProjection() {
        return this.projection;
    }

    public final NewCapturedTypeConstructor getNewTypeConstructor() {
        return this.newTypeConstructor;
    }

    public final void setNewTypeConstructor(NewCapturedTypeConstructor newCapturedTypeConstructor) {
        this.newTypeConstructor = newCapturedTypeConstructor;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public List<TypeParameterDescriptor> getParameters() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getSupertypes */
    public Collection<KotlinType> mo7438getSupertypes() {
        SimpleType nullableAnyType;
        if (getProjection().getProjectionKind() == Variance.OUT_VARIANCE) {
            nullableAnyType = getProjection().getNullableAnyType();
        } else {
            nullableAnyType = getBuiltIns().getNullableAnyType();
        }
        Intrinsics.checkNotNull(nullableAnyType);
        return CollectionsKt.listOf(nullableAnyType);
    }

    public String toString() {
        return "CapturedTypeConstructor(" + getProjection() + ')';
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public KotlinBuiltIns getBuiltIns() {
        KotlinBuiltIns builtIns = getProjection().getNullableAnyType().getConstructor().getBuiltIns();
        Intrinsics.checkNotNullExpressionValue(builtIns, "getBuiltIns(...)");
        return builtIns;
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public CapturedTypeConstructorImpl refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        TypeProjection typeProjectionRefine = getProjection().refine(kotlinTypeRefiner);
        Intrinsics.checkNotNullExpressionValue(typeProjectionRefine, "refine(...)");
        return new CapturedTypeConstructorImpl(typeProjectionRefine);
    }
}
