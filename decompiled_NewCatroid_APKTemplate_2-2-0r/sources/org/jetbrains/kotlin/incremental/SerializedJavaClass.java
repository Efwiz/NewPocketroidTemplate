package org.jetbrains.kotlin.incremental;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolverImpl;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.serialization.deserialization.NameResolverUtilKt;

/* JADX INFO: compiled from: JavaClassesTrackerImpl.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/incremental/SerializedJavaClass;", "", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "stringTable", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$StringTable;", "qualifiedNameTable", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$QualifiedNameTable;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;Lorg/jetbrains/kotlin/metadata/ProtoBuf$StringTable;Lorg/jetbrains/kotlin/metadata/ProtoBuf$QualifiedNameTable;)V", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "getClassId", "()Lorg/jetbrains/kotlin/name/ClassId;", "getProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "getQualifiedNameTable", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$QualifiedNameTable;", "getStringTable", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$StringTable;", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SerializedJavaClass {
    private final ProtoBuf.Class proto;
    private final ProtoBuf.QualifiedNameTable qualifiedNameTable;
    private final ProtoBuf.StringTable stringTable;

    public SerializedJavaClass(ProtoBuf.Class proto, ProtoBuf.StringTable stringTable, ProtoBuf.QualifiedNameTable qualifiedNameTable) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
        Intrinsics.checkNotNullParameter(qualifiedNameTable, "qualifiedNameTable");
        this.proto = proto;
        this.stringTable = stringTable;
        this.qualifiedNameTable = qualifiedNameTable;
    }

    public final ProtoBuf.Class getProto() {
        return this.proto;
    }

    public final ProtoBuf.StringTable getStringTable() {
        return this.stringTable;
    }

    public final ProtoBuf.QualifiedNameTable getQualifiedNameTable() {
        return this.qualifiedNameTable;
    }

    public final ClassId getClassId() {
        return NameResolverUtilKt.getClassId(new NameResolverImpl(this.stringTable, this.qualifiedNameTable), this.proto.getFqName());
    }
}
