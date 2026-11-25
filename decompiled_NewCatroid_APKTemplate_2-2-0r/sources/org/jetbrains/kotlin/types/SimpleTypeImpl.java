package org.jetbrains.kotlin.types;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.error.ErrorScope;
import org.jetbrains.kotlin.types.error.ThrowingScope;

/* JADX INFO: compiled from: KotlinTypeFactory.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0012\b\u0002\u0018\u00002\u00020\u0001BE\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n\u0012\u0018\u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u00010\fj\u0002`\u000e¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\bH\u0016J\u0010\u0010!\u001a\u00020\u00012\u0006\u0010\"\u001a\u00020\rH\u0017J\u0010\u0010#\u001a\u00020\u00012\u0006\u0010$\u001a\u00020\u0013H\u0016R\u001a\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0014\u0010\u0012\u001a\u00020\u00138VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001a\u0010\u0018\u001a\u00020\b8VX\u0097\u0004¢\u0006\f\u0012\u0004\b\u0019\u0010\u001a\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u001cR\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u001eR \u0010\u000b\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u00010\fj\u0002`\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006%"}, d2 = {"Lorg/jetbrains/kotlin/types/SimpleTypeImpl;", "Lorg/jetbrains/kotlin/types/SimpleType;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/types/TypeConstructor;", "arguments", "", "Lorg/jetbrains/kotlin/types/TypeProjection;", "isMarkedNullable", "", "memberScope", "Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "refinedTypeFactory", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "Lorg/jetbrains/kotlin/types/RefinedTypeFactory;", "(Lorg/jetbrains/kotlin/types/TypeConstructor;Ljava/util/List;ZLorg/jetbrains/kotlin/resolve/scopes/MemberScope;Lkotlin/jvm/functions/Function1;)V", "getArguments", "()Ljava/util/List;", "attributes", "Lorg/jetbrains/kotlin/types/TypeAttributes;", "getAttributes", "()Lorg/jetbrains/kotlin/types/TypeAttributes;", "getConstructor", "()Lorg/jetbrains/kotlin/types/TypeConstructor;", "hasNotTrivialRefinementFactory", "getHasNotTrivialRefinementFactory$annotations", "()V", "getHasNotTrivialRefinementFactory", "()Z", "getMemberScope", "()Lorg/jetbrains/kotlin/resolve/scopes/MemberScope;", "makeNullableAsSpecified", "newNullability", "refine", "kotlinTypeRefiner", "replaceAttributes", "newAttributes", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class SimpleTypeImpl extends SimpleType {
    private final List<TypeProjection> arguments;
    private final TypeConstructor constructor;
    private final boolean isMarkedNullable;
    private final MemberScope memberScope;
    private final Function1<KotlinTypeRefiner, SimpleType> refinedTypeFactory;

    public static /* synthetic */ void getHasNotTrivialRefinementFactory$annotations() {
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public boolean getHasNotTrivialRefinementFactory() {
        return true;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public TypeConstructor getConstructor() {
        return this.constructor;
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public List<TypeProjection> getArguments() {
        return this.arguments;
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

    /* JADX WARN: Multi-variable type inference failed */
    public SimpleTypeImpl(TypeConstructor constructor, List<? extends TypeProjection> arguments, boolean z, MemberScope memberScope, Function1<? super KotlinTypeRefiner, ? extends SimpleType> refinedTypeFactory) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(arguments, "arguments");
        Intrinsics.checkNotNullParameter(memberScope, "memberScope");
        Intrinsics.checkNotNullParameter(refinedTypeFactory, "refinedTypeFactory");
        this.constructor = constructor;
        this.arguments = arguments;
        this.isMarkedNullable = z;
        this.memberScope = memberScope;
        this.refinedTypeFactory = refinedTypeFactory;
        if ((getMemberScope() instanceof ErrorScope) && !(getMemberScope() instanceof ThrowingScope)) {
            throw new IllegalStateException("SimpleTypeImpl should not be created for error type: " + getMemberScope() + '\n' + getConstructor());
        }
    }

    @Override // org.jetbrains.kotlin.types.KotlinType
    public TypeAttributes getAttributes() {
        return TypeAttributes.INSTANCE.getEmpty();
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public SimpleType replaceAttributes(TypeAttributes newAttributes) {
        Intrinsics.checkNotNullParameter(newAttributes, "newAttributes");
        if (newAttributes.isEmpty()) {
            return this;
        }
        return new SimpleTypeWithAttributes(this, newAttributes);
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType
    public SimpleType makeNullableAsSpecified(boolean newNullability) {
        if (newNullability == getIsMarkedNullable()) {
            return this;
        }
        if (newNullability) {
            return new NullableSimpleType(this);
        }
        return new NotNullSimpleType(this);
    }

    @Override // org.jetbrains.kotlin.types.UnwrappedType, org.jetbrains.kotlin.types.KotlinType
    public SimpleType refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        SimpleType simpleTypeInvoke = this.refinedTypeFactory.invoke(kotlinTypeRefiner);
        return simpleTypeInvoke == null ? this : simpleTypeInvoke;
    }
}
