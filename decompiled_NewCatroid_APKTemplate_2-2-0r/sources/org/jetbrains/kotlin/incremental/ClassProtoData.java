package org.jetbrains.kotlin.incremental;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;

/* JADX INFO: compiled from: protoDifferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0005HÆ\u0003J\u001d\u0010\r\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/incremental/ClassProtoData;", "Lorg/jetbrains/kotlin/incremental/ProtoData;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;)V", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class ClassProtoData extends ProtoData {
    private final NameResolver nameResolver;
    private final ProtoBuf.Class proto;

    public static /* synthetic */ ClassProtoData copy$default(ClassProtoData classProtoData, ProtoBuf.Class r1, NameResolver nameResolver, int i, Object obj) {
        if ((i & 1) != 0) {
            r1 = classProtoData.proto;
        }
        if ((i & 2) != 0) {
            nameResolver = classProtoData.nameResolver;
        }
        return classProtoData.copy(r1, nameResolver);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ProtoBuf.Class getProto() {
        return this.proto;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final NameResolver getNameResolver() {
        return this.nameResolver;
    }

    public final ClassProtoData copy(ProtoBuf.Class proto, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        return new ClassProtoData(proto, nameResolver);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ClassProtoData)) {
            return false;
        }
        ClassProtoData classProtoData = (ClassProtoData) other;
        return Intrinsics.areEqual(this.proto, classProtoData.proto) && Intrinsics.areEqual(this.nameResolver, classProtoData.nameResolver);
    }

    public int hashCode() {
        return (this.proto.hashCode() * 31) + this.nameResolver.hashCode();
    }

    public String toString() {
        return "ClassProtoData(proto=" + this.proto + ", nameResolver=" + this.nameResolver + ')';
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ClassProtoData(ProtoBuf.Class proto, NameResolver nameResolver) {
        super(null);
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        this.proto = proto;
        this.nameResolver = nameResolver;
    }

    public final NameResolver getNameResolver() {
        return this.nameResolver;
    }

    public final ProtoBuf.Class getProto() {
        return this.proto;
    }
}
