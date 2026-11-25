package org.jetbrains.kotlin.library.impl;

import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.konan.file.File;
import org.jetbrains.kotlin.library.IrKotlinLibraryLayout;
import org.jetbrains.kotlin.library.IrLibrary;

/* JADX INFO: compiled from: KotlinLibraryImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0012\n\u0002\b\u0005\b&\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001d\u0010\b\u001a\u0004\u0018\u00010\t8VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000b¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrLibraryImpl;", "Lorg/jetbrains/kotlin/library/IrLibrary;", "access", "Lorg/jetbrains/kotlin/library/impl/IrLibraryAccess;", "Lorg/jetbrains/kotlin/library/IrKotlinLibraryLayout;", "(Lorg/jetbrains/kotlin/library/impl/IrLibraryAccess;)V", "getAccess", "()Lorg/jetbrains/kotlin/library/impl/IrLibraryAccess;", "dataFlowGraph", "", "getDataFlowGraph", "()[B", "dataFlowGraph$delegate", "Lkotlin/Lazy;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public abstract class IrLibraryImpl implements IrLibrary {
    private final IrLibraryAccess<IrKotlinLibraryLayout> access;

    /* JADX INFO: renamed from: dataFlowGraph$delegate, reason: from kotlin metadata */
    private final Lazy dataFlowGraph;

    public IrLibraryImpl(IrLibraryAccess<IrKotlinLibraryLayout> access) {
        Intrinsics.checkNotNullParameter(access, "access");
        this.access = access;
        this.dataFlowGraph = LazyKt.lazy(new Function0<byte[]>() { // from class: org.jetbrains.kotlin.library.impl.IrLibraryImpl$dataFlowGraph$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final byte[] invoke() {
                return (byte[]) this.this$0.getAccess().inPlace(new Function1<IrKotlinLibraryLayout, byte[]>() { // from class: org.jetbrains.kotlin.library.impl.IrLibraryImpl$dataFlowGraph$2.1
                    @Override // kotlin.jvm.functions.Function1
                    public final byte[] invoke(IrKotlinLibraryLayout it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        File dataFlowGraphFile = it.getDataFlowGraphFile();
                        if (dataFlowGraphFile.getExists()) {
                            return dataFlowGraphFile.readBytes();
                        }
                        return null;
                    }
                });
            }
        });
    }

    public final IrLibraryAccess<IrKotlinLibraryLayout> getAccess() {
        return this.access;
    }

    @Override // org.jetbrains.kotlin.library.IrLibrary
    public byte[] getDataFlowGraph() {
        return (byte[]) this.dataFlowGraph.getValue();
    }
}
