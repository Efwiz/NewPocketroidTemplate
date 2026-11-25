package org.jetbrains.kotlin.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.NewTypeVariableConstructor;
import org.jetbrains.kotlin.types.error.ErrorScopeKind;
import org.jetbrains.kotlin.types.error.ErrorTypeConstructor;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;

/* JADX INFO: compiled from: StubTypes.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0004\b&\u0018\u0000 \u001f2\u00020\u0001:\u0001\u001fB\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0017\u001a\u00020\u00012\u0006\u0010\u0018\u001a\u00020\u0005H\u0016J\u0010\u0010\u0019\u001a\u00020\u00002\u0006\u0010\u0018\u001a\u00020\u0005H&J\u0010\u0010\u001a\u001a\u00020\u00002\u0006\u0010\u001b\u001a\u00020\u001cH\u0017J\u0010\u0010\u001d\u001a\u00020\u00012\u0006\u0010\u001e\u001a\u00020\rH\u0016R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\r8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u0012X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractStubType;", "Lorg/jetbrains/kotlin/types/SimpleType;", "originalTypeVariable", "Lorg/jetbrains/kotlin/types/checker/NewTypeVariableConstructor;", "isMarkedNullable", "", "(Lorg/jetbrains/kotlin/types/checker/NewTypeVariableConstructor;Z)V", "arguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "getArguments", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/types/TypeAttributes;", "()Z", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getMemberScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "getOriginalTypeVariable", "()Lorg/jetbrains/kotlin/types/checker/NewTypeVariableConstructor;", "makeNullableAsSpecified", "newNullability", "materialize", "refine", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "replaceAttributes", "newAttributes", "Companion", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractStubType extends SimpleType {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final boolean isMarkedNullable;
    private final MemberScope memberScope;
    private final NewTypeVariableConstructor originalTypeVariable;

    public abstract AbstractStubType materialize(boolean newNullability);

    @Override // org.jetbrains.kotlin.types.UnwrappedType, org.jetbrains.kotlin.types.KotlinType
    public AbstractStubType refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return this;
    }

    public AbstractStubType(NewTypeVariableConstructor originalTypeVariable, boolean z) {
        Intrinsics.checkNotNullParameter(originalTypeVariable, "originalTypeVariable");
        this.originalTypeVariable = originalTypeVariable;
        this.isMarkedNullable = z;
        this.memberScope = org.jetbrains.kotlin.types.error.ErrorUtils.createErrorScope(ErrorScopeKind.STUB_TYPE_SCOPE, originalTypeVariable.toString());
    }

    public final NewTypeVariableConstructor getOriginalTypeVariable() {
        return this.originalTypeVariable;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    /* JADX INFO: renamed from: isMarkedNullable, reason: from getter */
    public boolean getIsMarkedNullable() {
        return this.isMarkedNullable;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public MemberScope getMemberScope() {
        return this.memberScope;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public List<TypeProjection> getArguments() {
        return CollectionsKt.emptyList();
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public TypeAttributes getAttributes() {
        return TypeAttributes.INSTANCE.getEmpty();
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public SimpleType replaceAttributes(TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        return this;
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        return newNullability == getIsMarkedNullable() ? this : materialize(newNullability);
    }

    /* JADX INFO: compiled from: StubTypes.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractStubType$Companion;", "", "()V", "createConstructor", "Lorg/jetbrains/kotlin/types/error/ErrorTypeConstructor;", "originalTypeVariable", "Lorg/jetbrains/kotlin/types/checker/NewTypeVariableConstructor;", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ErrorTypeConstructor createConstructor(NewTypeVariableConstructor originalTypeVariable) {
            Intrinsics.checkNotNullParameter(originalTypeVariable, "originalTypeVariable");
            return org.jetbrains.kotlin.types.error.ErrorUtils.INSTANCE.createErrorTypeConstructor(ErrorTypeKind.STUB_TYPE, originalTypeVariable.toString());
        }
    }
}
