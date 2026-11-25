package org.jetbrains.kotlin.fir.scopes.impl;

import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.resolve.providers.FirSymbolProvider;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassLikeSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirLazyNestedClassifierScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u000e\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00060\rH\u0016J\u000e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00060\rH\u0016J.\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00062\u001c\u0010\u0012\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0014\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00100\u0013H\u0016R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirLazyNestedClassifierScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirContainingNamesAwareScope;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "existingNames", "", "Lorg/jetbrains/kotlin/name/Name;", "symbolProvider", "Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;", "(Lorg/jetbrains/kotlin/name/ClassId;Ljava/util/List;Lorg/jetbrains/kotlin/fir/resolve/providers/FirSymbolProvider;)V", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getCallableNames", "", "getClassifierNames", "processClassifiersByNameWithSubstitution", "", "name", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirLazyNestedClassifierScope extends FirContainingNamesAwareScope {
    private final ClassId classId;
    private final List<Name> existingNames;
    private final FirSymbolProvider symbolProvider;

    public final ClassId getClassId() {
        return this.classId;
    }

    public FirLazyNestedClassifierScope(ClassId classId, List<Name> existingNames, FirSymbolProvider symbolProvider) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        Intrinsics.checkNotNullParameter(existingNames, "existingNames");
        Intrinsics.checkNotNullParameter(symbolProvider, "symbolProvider");
        this.classId = classId;
        this.existingNames = existingNames;
        this.symbolProvider = symbolProvider;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (this.existingNames.contains(name2)) {
            ClassId classIdCreateNestedClassId = this.classId.createNestedClassId(name2);
            Intrinsics.checkNotNullExpressionValue(classIdCreateNestedClassId, "createNestedClassId(...)");
            FirClassLikeSymbol<?> classLikeSymbolByClassId = this.symbolProvider.getClassLikeSymbolByClassId(classIdCreateNestedClassId);
            if (classLikeSymbolByClassId == null) {
                return;
            }
            processor.invoke(classLikeSymbolByClassId, ConeSubstitutor.Empty.INSTANCE);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return CollectionsKt.toSet(this.existingNames);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return SetsKt.emptySet();
    }
}
