package org.jetbrains.kotlin.fir;

import kotlin.Metadata;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirNameConflictsTrackerComponent.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J:\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\n\u0010\u0007\u001a\u0006\u0012\u0002\b\u00030\b2\u0006\u0010\t\u001a\u00020\n2\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\b2\b\u0010\f\u001a\u0004\u0018\u00010\nH&¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/FirNameConflictsTrackerComponent;", "Lorg/jetbrains/kotlin/fir/FirSessionComponent;", "()V", "registerClassifierRedeclaration", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "newSymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassLikeSymbol;", "newSymbolFile", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "prevSymbol", "prevSymbolFile", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class FirNameConflictsTrackerComponent implements FirSessionComponent {
    public abstract void registerClassifierRedeclaration(ClassId classId, FirClassLikeSymbol<?> newSymbol, FirFile newSymbolFile, FirClassLikeSymbol<?> prevSymbol, FirFile prevSymbolFile);
}
