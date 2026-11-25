package org.jetbrains.kotlin.cli.metadata;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.builtins.BuiltInsBinaryVersion;
import org.jetbrains.kotlin.serialization.ApproximatingStringTable;
import org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: MetadataSerializerExtension.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/cli/metadata/MetadataSerializerExtension;", "Lorg/jetbrains/kotlin/serialization/KotlinSerializerExtensionBase;", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;", "(Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;)V", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/builtins/BuiltInsBinaryVersion;", "stringTable", "Lorg/jetbrains/kotlin/serialization/ApproximatingStringTable;", "getStringTable", "()Lorg/jetbrains/kotlin/serialization/ApproximatingStringTable;", "shouldUseTypeTable", "", "cli"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class MetadataSerializerExtension extends KotlinSerializerExtensionBase {
    private final BuiltInsBinaryVersion metadataVersion;
    private final ApproximatingStringTable stringTable;

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public boolean shouldUseTypeTable() {
        return true;
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public BuiltInsBinaryVersion getMetadataVersion() {
        return this.metadataVersion;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MetadataSerializerExtension(BuiltInsBinaryVersion metadataVersion) {
        super(BuiltInSerializerProtocol.INSTANCE);
        Intrinsics.checkNotNullParameter(metadataVersion, "metadataVersion");
        this.metadataVersion = metadataVersion;
        this.stringTable = new ApproximatingStringTable();
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public ApproximatingStringTable getStringTable() {
        return this.stringTable;
    }
}
