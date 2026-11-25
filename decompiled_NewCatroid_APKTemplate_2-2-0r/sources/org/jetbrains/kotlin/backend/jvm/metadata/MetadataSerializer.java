package org.jetbrains.kotlin.backend.jvm.metadata;

import kotlin.Metadata;
import kotlin.Pair;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.MetadataSource;
import org.jetbrains.kotlin.metadata.jvm.serialization.JvmStringTable;
import org.jetbrains.kotlin.protobuf.MessageLite;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.commons.Method;

/* JADX INFO: compiled from: MetadataSerializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J$\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007H&J\u0018\u0010\n\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u000b2\u0006\u0010\u0006\u001a\u00020\fH&J \u0010\r\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\f2\u0006\u0010\u000e\u001a\u00020\u000fH&J\u001e\u0010\u0010\u001a\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u0012\u0018\u00010\u00072\u0006\u0010\u0004\u001a\u00020\u0013H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0014À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/metadata/MetadataSerializer;", "", "bindFieldMetadata", "", "metadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Property;", "signature", "Lkotlin/Pair;", "Lorg/jetbrains/org/objectweb/asm/Type;", "", "bindMethodMetadata", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource$Function;", "Lorg/jetbrains/org/objectweb/asm/commons/Method;", "bindPropertyMetadata", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "serialize", "Lorg/jetbrains/kotlin/protobuf/MessageLite;", "Lorg/jetbrains/kotlin/metadata/jvm/serialization/JvmStringTable;", "Lorg/jetbrains/kotlin/ir/declarations/MetadataSource;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface MetadataSerializer {
    void bindFieldMetadata(MetadataSource.Property metadata, Pair<Type, String> signature);

    void bindMethodMetadata(MetadataSource.Function metadata, Method signature);

    void bindPropertyMetadata(MetadataSource.Property metadata, Method signature, IrDeclarationOrigin origin);

    Pair<MessageLite, JvmStringTable> serialize(MetadataSource metadata);
}
