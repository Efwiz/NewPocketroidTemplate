package org.jetbrains.kotlin.fir.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.serialization.SerializableStringTable;

/* JADX INFO: compiled from: firKlibSerialization.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H\u0016¨\u0006\b"}, d2 = {"Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareSerializableStringTable;", "Lorg/jetbrains/kotlin/fir/serialization/FirElementAwareStringTable;", "Lorg/jetbrains/kotlin/serialization/SerializableStringTable;", "()V", "getLocalClassIdReplacement", "Lorg/jetbrains/kotlin/name/ClassId;", "firClass", "Lorg/jetbrains/kotlin/fir/declarations/FirClass;", "fir-serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirElementAwareSerializableStringTable extends SerializableStringTable implements FirElementAwareStringTable {
    @Override // org.jetbrains.kotlin.fir.serialization.FirElementAwareStringTable
    public ClassId getLocalClassIdReplacement(FirClass firClass) {
        Intrinsics.checkNotNullParameter(firClass, "firClass");
        ClassId classId = ClassId.topLevel(StandardNames.FqNames.any.toSafe());
        Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
        return classId;
    }
}
