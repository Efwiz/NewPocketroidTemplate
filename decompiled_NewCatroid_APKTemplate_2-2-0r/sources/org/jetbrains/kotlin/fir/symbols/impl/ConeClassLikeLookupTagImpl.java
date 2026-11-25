package org.jetbrains.kotlin.fir.symbols.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.Constants;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.util.WeakPair;

/* JADX INFO: compiled from: ConeClassLikeLookupTagImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0096\u0002J\b\u0010\u0015\u001a\u00020\u0016H\u0016R8\u0010\u0005\u001a\u0016\u0012\u0004\u0012\u00020\u0007\u0012\n\u0012\b\u0012\u0002\b\u0003\u0018\u00010\b\u0018\u00010\u00068\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\t\u0010\n\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u0017"}, d2 = {"Lorg/jetbrains/kotlin/fir/symbols/impl/ConeClassLikeLookupTagImpl;", "Lorg/jetbrains/kotlin/fir/symbols/ConeClassLikeLookupTag;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "(Lorg/jetbrains/kotlin/name/ClassId;)V", "boundSymbol", "Lorg/jetbrains/kotlin/util/WeakPair;", "Lorg/jetbrains/kotlin/fir/FirSession;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "getBoundSymbol$annotations", "()V", "getBoundSymbol", "()Lorg/jetbrains/kotlin/util/WeakPair;", "setBoundSymbol", "(Lorg/jetbrains/kotlin/util/WeakPair;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", Constants.TYPE_TREE}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ConeClassLikeLookupTagImpl extends ConeClassLikeLookupTag {
    private WeakPair<FirSession, FirClassLikeSymbol<?>> boundSymbol;
    private final ClassId classId;

    @LookupTagInternals
    public static /* synthetic */ void getBoundSymbol$annotations() {
    }

    public ConeClassLikeLookupTagImpl(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        this.classId = classId;
        getClassId().isLocal();
    }

    @Override // org.jetbrains.kotlin.fir.symbols.ConeClassLikeLookupTag
    public ClassId getClassId() {
        return this.classId;
    }

    public final WeakPair<FirSession, FirClassLikeSymbol<?>> getBoundSymbol() {
        return this.boundSymbol;
    }

    public final void setBoundSymbol(WeakPair<FirSession, FirClassLikeSymbol<?>> weakPair) {
        this.boundSymbol = weakPair;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!Intrinsics.areEqual(getClass(), other != null ? other.getClass() : null)) {
            return false;
        }
        Intrinsics.checkNotNull(other, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.ConeClassLikeLookupTagImpl");
        return Intrinsics.areEqual(getClassId(), ((ConeClassLikeLookupTagImpl) other).getClassId());
    }

    public int hashCode() {
        return getClassId().hashCode();
    }
}
