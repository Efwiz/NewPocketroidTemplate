package org.jetbrains.kotlin.library.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.library.IrKotlinLibraryLayout;
import org.jetbrains.kotlin.library.KotlinLibraryLayoutKt;
import org.jetbrains.kotlin.library.SerializedIrFile;
import org.jetbrains.kotlin.library.SerializedIrModule;

/* JADX INFO: compiled from: IrWriterImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/library/impl/IrMonoliticWriterImpl;", "Lorg/jetbrains/kotlin/library/impl/IrWriterImpl;", "_irLayout", "Lorg/jetbrains/kotlin/library/IrKotlinLibraryLayout;", "(Lorg/jetbrains/kotlin/library/IrKotlinLibraryLayout;)V", "addIr", "", KotlinLibraryLayoutKt.KLIB_IR_FOLDER_NAME, "Lorg/jetbrains/kotlin/library/SerializedIrModule;", "kotlin-util-klib"}, k = 1, mv = {1, 5, 1}, xi = 48)
public final class IrMonoliticWriterImpl extends IrWriterImpl {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public IrMonoliticWriterImpl(IrKotlinLibraryLayout _irLayout) {
        super(_irLayout);
        Intrinsics.checkNotNullParameter(_irLayout, "_irLayout");
    }

    @Override // org.jetbrains.kotlin.library.IrWriter
    public void addIr(SerializedIrModule ir) {
        Intrinsics.checkNotNullParameter(ir, "ir");
        getIrLayout().getIrDir().mkdirs();
        List listSortedWith = CollectionsKt.sortedWith(ir.getFiles(), new Comparator() { // from class: org.jetbrains.kotlin.library.impl.IrMonoliticWriterImpl$addIr$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((SerializedIrFile) t).getPath(), ((SerializedIrFile) t2).getPath());
            }
        });
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSortedWith, 10));
        Iterator it = listSortedWith.iterator();
        while (it.hasNext()) {
            arrayList.add(((SerializedIrFile) it.next()).getFileData());
        }
        new IrArrayWriter(arrayList).writeIntoFile(getIrLayout().getIrFiles().getAbsolutePath());
        ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSortedWith, 10));
        Iterator it2 = listSortedWith.iterator();
        while (it2.hasNext()) {
            arrayList2.add(((SerializedIrFile) it2.next()).getDeclarations());
        }
        new IrArrayWriter(arrayList2).writeIntoFile(getIrLayout().getIrDeclarations().getAbsolutePath());
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSortedWith, 10));
        Iterator it3 = listSortedWith.iterator();
        while (it3.hasNext()) {
            arrayList3.add(((SerializedIrFile) it3.next()).getTypes());
        }
        new IrArrayWriter(arrayList3).writeIntoFile(getIrLayout().getIrTypes().getAbsolutePath());
        ArrayList arrayList4 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSortedWith, 10));
        Iterator it4 = listSortedWith.iterator();
        while (it4.hasNext()) {
            arrayList4.add(((SerializedIrFile) it4.next()).getSignatures());
        }
        new IrArrayWriter(arrayList4).writeIntoFile(getIrLayout().getIrSignatures().getAbsolutePath());
        ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSortedWith, 10));
        Iterator it5 = listSortedWith.iterator();
        while (it5.hasNext()) {
            arrayList5.add(((SerializedIrFile) it5.next()).getStrings());
        }
        new IrArrayWriter(arrayList5).writeIntoFile(getIrLayout().getIrStrings().getAbsolutePath());
        ArrayList arrayList6 = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSortedWith, 10));
        Iterator it6 = listSortedWith.iterator();
        while (it6.hasNext()) {
            arrayList6.add(((SerializedIrFile) it6.next()).getBodies());
        }
        new IrArrayWriter(arrayList6).writeIntoFile(getIrLayout().getIrBodies().getAbsolutePath());
        ArrayList arrayList7 = new ArrayList();
        Iterator it7 = listSortedWith.iterator();
        while (it7.hasNext()) {
            byte[] debugInfo = ((SerializedIrFile) it7.next()).getDebugInfo();
            if (debugInfo != null) {
                arrayList7.add(debugInfo);
            }
        }
        new IrArrayWriter(arrayList7).writeIntoFile(getIrLayout().getIrDebugInfo().getAbsolutePath());
    }
}
