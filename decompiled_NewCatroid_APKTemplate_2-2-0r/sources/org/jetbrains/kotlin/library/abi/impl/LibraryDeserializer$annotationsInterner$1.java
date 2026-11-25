package org.jetbrains.kotlin.library.abi.impl;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import org.jetbrains.kotlin.library.abi.AbiQualifiedName;

/* JADX INFO: compiled from: LibraryAbiReaderImpl.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\u0017\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u000e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"org/jetbrains/kotlin/library/abi/impl/LibraryDeserializer$annotationsInterner$1", "", "uniqueAnnotationClassNames", "Lorg/jetbrains/kotlin/it/unimi/dsi/fastutil/objects/ObjectOpenHashSet;", "Lorg/jetbrains/kotlin/library/abi/AbiQualifiedName;", "intern", "annotationClassName", "kotlin-util-klib-abi"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LibraryDeserializer$annotationsInterner$1 {
    private final ObjectOpenHashSet<AbiQualifiedName> uniqueAnnotationClassNames = new ObjectOpenHashSet<>();

    LibraryDeserializer$annotationsInterner$1() {
    }

    public final AbiQualifiedName intern(AbiQualifiedName annotationClassName) {
        Intrinsics.checkNotNullParameter(annotationClassName, "annotationClassName");
        AbiQualifiedName abiQualifiedNameAddOrGet = this.uniqueAnnotationClassNames.addOrGet(annotationClassName);
        Intrinsics.checkNotNullExpressionValue(abiQualifiedNameAddOrGet, "addOrGet(...)");
        return abiQualifiedNameAddOrGet;
    }
}
