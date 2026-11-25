package org.jetbrains.kotlin.metadata.serialization;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.metadata.ProtoBuf;

/* JADX INFO: compiled from: MutableTable.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u0014\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0001B\u0005¢\u0006\u0002\u0010\u0005J\u0018\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u00042\u0006\u0010\t\u001a\u00020\u0002H\u0014J\b\u0010\n\u001a\u00020\u0004H\u0014¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/metadata/serialization/MutableTypeTable;", "Lorg/jetbrains/kotlin/metadata/serialization/MutableTable;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$Type$Builder;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeTable;", "Lorg/jetbrains/kotlin/metadata/ProtoBuf$TypeTable$Builder;", "()V", "addElement", "", "builder", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "createTableBuilder", "metadata"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MutableTypeTable extends MutableTable<ProtoBuf.Type.Builder, ProtoBuf.TypeTable, ProtoBuf.TypeTable.Builder> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.metadata.serialization.MutableTable
    public ProtoBuf.TypeTable.Builder createTableBuilder() {
        ProtoBuf.TypeTable.Builder builderNewBuilder = ProtoBuf.TypeTable.newBuilder();
        Intrinsics.checkNotNullExpressionValue(builderNewBuilder, "newBuilder(...)");
        return builderNewBuilder;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.metadata.serialization.MutableTable
    public void addElement(ProtoBuf.TypeTable.Builder builder, ProtoBuf.Type.Builder element) {
        Intrinsics.checkNotNullParameter(builder, "builder");
        Intrinsics.checkNotNullParameter(element, "element");
        builder.addType(element);
    }
}
