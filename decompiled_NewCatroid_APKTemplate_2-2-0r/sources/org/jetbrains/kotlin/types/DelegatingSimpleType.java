package org.jetbrains.kotlin.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;

/* JADX INFO: compiled from: SpecialTypes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u001cH\u0017J\u0010\u0010\u001d\u001a\u00020\u00002\u0006\u0010\u0010\u001a\u00020\u0001H'R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u00048VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0006\u0010\u0007R\u0014\u0010\b\u001a\u00020\t8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0012\u0010\u0010\u001a\u00020\u0001X¤\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u0014\u0010\u0013\u001a\u00020\u00148VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0015R\u0014\u0010\u0016\u001a\u00020\u00178VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0018\u0010\u0019¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/types/DelegatingSimpleType;", "Lorg/jetbrains/kotlin/types/SimpleType;", "()V", "arguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "getArguments", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/types/TypeAttributes;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "getConstructor", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "getDelegate", "()Lorg/jetbrains/kotlin/types/SimpleType;", "isMarkedNullable", "", "()Z", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getMemberScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "replaceDelegate", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DelegatingSimpleType extends SimpleType {
    protected abstract SimpleType getDelegate();

    public abstract DelegatingSimpleType replaceDelegate(SimpleType delegate);

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

    @Override // org.jetbrains.kotlin.types.KotlinType
    public TypeAttributes getAttributes() {
        return getDelegate().getAttributes();
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType, org.jetbrains.kotlin.types.KotlinType
    public SimpleType refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        KotlinType kotlinTypeRefineType = kotlinTypeRefiner.refineType((KotlinTypeMarker) getDelegate());
        Intrinsics.checkNotNull(kotlinTypeRefineType, "null cannot be cast to non-null type org.jetbrains.kotlin.types.SimpleType");
        return replaceDelegate((SimpleType) kotlinTypeRefineType);
    }
}
