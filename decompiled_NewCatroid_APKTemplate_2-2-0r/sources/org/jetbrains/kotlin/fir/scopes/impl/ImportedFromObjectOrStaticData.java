package org.jetbrains.kotlin.fir.scopes.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: FirAbstractImportingScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00028\u0000¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0006\u001a\u00028\u0000¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/ImportedFromObjectOrStaticData;", "D", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "", "objectClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "original", "(Lorg/jetbrains/kotlin/name/ClassId;Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;)V", "getObjectClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getOriginal", "()Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ImportedFromObjectOrStaticData<D extends FirCallableDeclaration> {
    private final ClassId objectClassId;
    private final D original;

    public ImportedFromObjectOrStaticData(ClassId objectClassId, D original) {
        Intrinsics.checkNotNullParameter(objectClassId, "objectClassId");
        Intrinsics.checkNotNullParameter(original, "original");
        this.objectClassId = objectClassId;
        this.original = original;
    }

    public final ClassId getObjectClassId() {
        return this.objectClassId;
    }

    public final D getOriginal() {
        return this.original;
    }
}
