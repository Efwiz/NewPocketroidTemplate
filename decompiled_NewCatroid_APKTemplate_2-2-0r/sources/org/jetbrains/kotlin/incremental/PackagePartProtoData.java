package org.jetbrains.kotlin.incremental;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: protoDifferenceUtils.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u000f\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0010\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0011\u001a\u00020\u0007HÆ\u0003J'\u0010\u0012\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u0007HÆ\u0001J\u0013\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u0016HÖ\u0003J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001J\t\u0010\u0019\u001a\u00020\u001aHÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/incremental/PackagePartProtoData;", "Lorg/jetbrains/kotlin/incremental/ProtoData;", "proto", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "packageFqName", "Lorg/jetbrains/kotlin/name/FqName;", "(Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;Lorg/jetbrains/kotlin/name/FqName;)V", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getPackageFqName", "()Lorg/jetbrains/kotlin/name/FqName;", "getProto", "()Lorg/jetbrains/kotlin/metadata/ProtoBuf$Package;", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "", "hashCode", "", "toString", "", "kotlin-build-common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final /* data */ class PackagePartProtoData extends ProtoData {
    private final NameResolver nameResolver;
    private final FqName packageFqName;
    private final ProtoBuf.Package proto;

    public static /* synthetic */ PackagePartProtoData copy$default(PackagePartProtoData packagePartProtoData, ProtoBuf.Package r1, NameResolver nameResolver, FqName fqName, int i, Object obj) {
        if ((i & 1) != 0) {
            r1 = packagePartProtoData.proto;
        }
        if ((i & 2) != 0) {
            nameResolver = packagePartProtoData.nameResolver;
        }
        if ((i & 4) != 0) {
            fqName = packagePartProtoData.packageFqName;
        }
        return packagePartProtoData.copy(r1, nameResolver, fqName);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final ProtoBuf.Package getProto() {
        return this.proto;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final NameResolver getNameResolver() {
        return this.nameResolver;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final FqName getPackageFqName() {
        return this.packageFqName;
    }

    public final PackagePartProtoData copy(ProtoBuf.Package proto, NameResolver nameResolver, FqName packageFqName) {
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        return new PackagePartProtoData(proto, nameResolver, packageFqName);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof PackagePartProtoData)) {
            return false;
        }
        PackagePartProtoData packagePartProtoData = (PackagePartProtoData) other;
        return Intrinsics.areEqual(this.proto, packagePartProtoData.proto) && Intrinsics.areEqual(this.nameResolver, packagePartProtoData.nameResolver) && Intrinsics.areEqual(this.packageFqName, packagePartProtoData.packageFqName);
    }

    public int hashCode() {
        return (((this.proto.hashCode() * 31) + this.nameResolver.hashCode()) * 31) + this.packageFqName.hashCode();
    }

    public String toString() {
        return "PackagePartProtoData(proto=" + this.proto + ", nameResolver=" + this.nameResolver + ", packageFqName=" + this.packageFqName + ')';
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PackagePartProtoData(ProtoBuf.Package proto, NameResolver nameResolver, FqName packageFqName) {
        super(null);
        Intrinsics.checkNotNullParameter(proto, "proto");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(packageFqName, "packageFqName");
        this.proto = proto;
        this.nameResolver = nameResolver;
        this.packageFqName = packageFqName;
    }

    public final NameResolver getNameResolver() {
        return this.nameResolver;
    }

    public final FqName getPackageFqName() {
        return this.packageFqName;
    }

    public final ProtoBuf.Package getProto() {
        return this.proto;
    }
}
