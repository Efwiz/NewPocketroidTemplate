package org.jetbrains.kotlin.fir.extensions;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirClassDeclaredMemberScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirNestedClassifierScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassSymbol;
import org.jetbrains.kotlin.js.translate.context.Namer;

/* JADX INFO: compiled from: FirDeclarationGenerationExtension.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003:\u0002\u0015\u0016B\u001d\b\u0004\u0012\n\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u0010\u0007J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0003H\u0096\u0002J\b\u0010\u0014\u001a\u00020\fH\u0016R\u0015\u0010\u0006\u001a\u0004\u0018\u00018\u0000¢\u0006\n\n\u0002\u0010\n\u001a\u0004\b\b\u0010\tR\u0012\u0010\u000b\u001a\u00020\fX¤\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0015\u0010\u0004\u001a\u0006\u0012\u0002\b\u00030\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010\u0082\u0001\u0002\u0017\u0018¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "declaredScope", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;)V", "getDeclaredScope", "()Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", Namer.METADATA_CLASS_KIND, "", "getKind", "()I", "getOwner", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "Member", "Nested", "Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Member;", "Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Nested;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class DeclarationGenerationContext<T extends FirContainingNamesAwareScope> {
    private final T declaredScope;
    private final FirClassSymbol<?> owner;

    public /* synthetic */ DeclarationGenerationContext(FirClassSymbol firClassSymbol, FirContainingNamesAwareScope firContainingNamesAwareScope, DefaultConstructorMarker defaultConstructorMarker) {
        this(firClassSymbol, firContainingNamesAwareScope);
    }

    protected abstract int getKind();

    private DeclarationGenerationContext(FirClassSymbol<?> firClassSymbol, T t) {
        this.owner = firClassSymbol;
        this.declaredScope = t;
    }

    public final FirClassSymbol<?> getOwner() {
        return this.owner;
    }

    public final T getDeclaredScope() {
        return this.declaredScope;
    }

    /* JADX INFO: compiled from: FirDeclarationGenerationExtension.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001b\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Member;", "Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassDeclaredMemberScope;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "declaredScope", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Lorg/jetbrains/kotlin/fir/scopes/impl/FirClassDeclaredMemberScope;)V", Namer.METADATA_CLASS_KIND, "", "getKind", "()I", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Member extends DeclarationGenerationContext<FirClassDeclaredMemberScope> {
        @Override // org.jetbrains.kotlin.fir.extensions.DeclarationGenerationContext
        protected int getKind() {
            return 1;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Member(FirClassSymbol<?> owner, FirClassDeclaredMemberScope firClassDeclaredMemberScope) {
            super(owner, firClassDeclaredMemberScope, null);
            Intrinsics.checkNotNullParameter(owner, "owner");
        }
    }

    /* JADX INFO: compiled from: FirDeclarationGenerationExtension.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001b\u0012\n\u0010\u0003\u001a\u0006\u0012\u0002\b\u00030\u0004\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0002¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8TX\u0094\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext$Nested;", "Lorg/jetbrains/kotlin/fir/extensions/DeclarationGenerationContext;", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScope;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;", "declaredScope", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassSymbol;Lorg/jetbrains/kotlin/fir/scopes/impl/FirNestedClassifierScope;)V", Namer.METADATA_CLASS_KIND, "", "getKind", "()I", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Nested extends DeclarationGenerationContext<FirNestedClassifierScope> {
        @Override // org.jetbrains.kotlin.fir.extensions.DeclarationGenerationContext
        protected int getKind() {
            return 2;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public Nested(FirClassSymbol<?> owner, FirNestedClassifierScope firNestedClassifierScope) {
            super(owner, firNestedClassifierScope, null);
            Intrinsics.checkNotNullParameter(owner, "owner");
        }
    }

    public boolean equals(Object other) {
        if (getClass() != (other != null ? other.getClass() : null)) {
            return false;
        }
        if (!(other instanceof DeclarationGenerationContext)) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        return Intrinsics.areEqual(this.owner, ((DeclarationGenerationContext) other).owner);
    }

    public int hashCode() {
        return this.owner.hashCode() + getKind();
    }
}
