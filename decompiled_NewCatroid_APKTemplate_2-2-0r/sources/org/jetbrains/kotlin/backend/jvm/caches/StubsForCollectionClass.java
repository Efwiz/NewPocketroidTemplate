package org.jetbrains.kotlin.backend.jvm.caches;

import java.util.Collection;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;

/* JADX INFO: compiled from: CollectionStubComputer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\bf\u0018\u00002\u00020\u0001R\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0005\u0010\u0006R\u0012\u0010\u0007\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u000b\u001a\u00020\bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\nø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/caches/StubsForCollectionClass;", "", "candidatesForStubs", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getCandidatesForStubs", "()Ljava/util/Collection;", "mutableClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getMutableClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "readOnlyClass", "getReadOnlyClass", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface StubsForCollectionClass {
    Collection<IrSimpleFunction> getCandidatesForStubs();

    IrClassSymbol getMutableClass();

    IrClassSymbol getReadOnlyClass();
}
