package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.metadata.deserialization.BinaryVersion;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmMetadataVersion;
import org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase;
import org.jetbrains.kotlin.serialization.StringTableImpl;
import org.jetbrains.kotlin.serialization.deserialization.builtins.BuiltInSerializerProtocol;

/* JADX INFO: compiled from: classFileUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\b\u0010\u000b\u001a\u00020\fH\u0016R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/codegen/JvmOptionalAnnotationSerializerExtension;", "Lorg/jetbrains/kotlin/serialization/KotlinSerializerExtensionBase;", "stringTable", "Lorg/jetbrains/kotlin/serialization/StringTableImpl;", "(Lorg/jetbrains/kotlin/serialization/StringTableImpl;)V", "metadataVersion", "Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "getMetadataVersion", "()Lorg/jetbrains/kotlin/metadata/deserialization/BinaryVersion;", "getStringTable", "()Lorg/jetbrains/kotlin/serialization/StringTableImpl;", "shouldUseTypeTable", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmOptionalAnnotationSerializerExtension extends KotlinSerializerExtensionBase {
    private final StringTableImpl stringTable;

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public boolean shouldUseTypeTable() {
        return true;
    }

    @Override // org.jetbrains.kotlin.serialization.KotlinSerializerExtensionBase, org.jetbrains.kotlin.serialization.SerializerExtension
    public StringTableImpl getStringTable() {
        return this.stringTable;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public JvmOptionalAnnotationSerializerExtension(StringTableImpl stringTable) {
        super(BuiltInSerializerProtocol.INSTANCE);
        Intrinsics.checkNotNullParameter(stringTable, "stringTable");
        this.stringTable = stringTable;
    }

    @Override // org.jetbrains.kotlin.serialization.SerializerExtension
    public BinaryVersion getMetadataVersion() {
        return JvmMetadataVersion.INSTANCE;
    }
}
