package org.jetbrains.kotlin.metadata.jvm.deserialization;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;

/* JADX INFO: compiled from: BinaryModuleData.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B)\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0007\u001a\u00020\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/metadata/jvm/deserialization/BinaryModuleData;", "", "annotations", "", "", "optionalAnnotations", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Class;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "(Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;)V", "getAnnotations", "()Ljava/util/List;", "getNameResolver", "()Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "getOptionalAnnotations", "metadata.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class BinaryModuleData {
    private final List<String> annotations;
    private final NameResolver nameResolver;
    private final List<ProtoBuf.Class> optionalAnnotations;

    public BinaryModuleData(List<String> annotations, List<ProtoBuf.Class> optionalAnnotations, NameResolver nameResolver) {
        Intrinsics.checkNotNullParameter(annotations, "annotations");
        Intrinsics.checkNotNullParameter(optionalAnnotations, "optionalAnnotations");
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        this.annotations = annotations;
        this.optionalAnnotations = optionalAnnotations;
        this.nameResolver = nameResolver;
    }

    public final List<String> getAnnotations() {
        return this.annotations;
    }

    public final List<ProtoBuf.Class> getOptionalAnnotations() {
        return this.optionalAnnotations;
    }

    public final NameResolver getNameResolver() {
        return this.nameResolver;
    }
}
