package org.jetbrains.kotlin.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.name.ClassId;

/* JADX INFO: compiled from: StringTableImpl.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0016\u0018\u00002\u00020\u00012\u00020\u0002B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0004\u001a\u00020\u00058VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0006¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/serialization/StringTableImpl;", "Lorg/jetbrains/kotlin/serialization/DescriptorAwareStringTable;", "Lorg/jetbrains/kotlin/serialization/SerializableStringTable;", "()V", "isLocalClassIdReplacementKeptGeneric", "", "()Z", "getQualifiedClassNameIndex", "", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "serialization"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class StringTableImpl extends SerializableStringTable implements DescriptorAwareStringTable {
    public boolean isLocalClassIdReplacementKeptGeneric() {
        return false;
    }

    @Override // org.jetbrains.kotlin.serialization.DescriptorAwareStringTable
    public int getQualifiedClassNameIndex(ClassId classId) {
        Intrinsics.checkNotNullParameter(classId, "classId");
        return getClassIdIndex(classId);
    }
}
