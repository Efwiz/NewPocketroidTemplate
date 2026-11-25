package org.jetbrains.kotlin.analysis.decompiler.stub;

import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.ProtoBuf;
import org.jetbrains.kotlin.metadata.deserialization.NameResolver;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: ClsStubBuilderContext.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\u0016J\u0011\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH¦\u0002ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\rÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeParameters;", "", "child", "Lorg/jetbrains/kotlin/analysis/decompiler/stub/TypeParametersImpl;", "nameResolver", "Lorg/jetbrains/kotlin/metadata/deserialization/NameResolver;", "innerTypeParameters", "", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeParameter;", "get", "Lorg/jetbrains/kotlin/name/Name;", "id", "", "decompiler-to-stubs"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface TypeParameters {
    Name get(int id2);

    default TypeParametersImpl child(NameResolver nameResolver, List<ProtoBuf.TypeParameter> innerTypeParameters) {
        Intrinsics.checkNotNullParameter(nameResolver, "nameResolver");
        Intrinsics.checkNotNullParameter(innerTypeParameters, "innerTypeParameters");
        return new TypeParametersImpl(nameResolver, innerTypeParameters, this);
    }
}
