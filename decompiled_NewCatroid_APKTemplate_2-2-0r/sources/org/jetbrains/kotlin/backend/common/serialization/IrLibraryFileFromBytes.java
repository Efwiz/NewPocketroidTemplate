package org.jetbrains.kotlin.backend.common.serialization;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrStatement;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.library.encodings.WobblyTF8;
import org.jetbrains.kotlin.protobuf.ExtensionRegistryLite;

/* JADX INFO: compiled from: IrFileDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\u0011\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0007\u001a\u00020\bH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFileFromBytes;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;", "bytesSource", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryBytesSource;", "(Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryBytesSource;)V", "debugInfo", "", "index", "", "declaration", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDeclaration;", "expressionBody", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrExpression;", "signature", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IdSignature;", "statementBody", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrStatement;", "string", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrType;", "Companion", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class IrLibraryFileFromBytes extends IrLibraryFile {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final ExtensionRegistryLite extensionRegistryLite;
    private final IrLibraryBytesSource bytesSource;

    public IrLibraryFileFromBytes(IrLibraryBytesSource bytesSource) {
        Intrinsics.checkNotNullParameter(bytesSource, "bytesSource");
        this.bytesSource = bytesSource;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public IrDeclaration declaration(int index) throws IOException {
        IrDeclaration from = IrDeclaration.parseFrom(BasicIrModuleDeserializerKt.getCodedInputStream(this.bytesSource.irDeclaration(index)), extensionRegistryLite);
        Intrinsics.checkNotNullExpressionValue(from, "parseFrom(...)");
        return from;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public IrType type(int index) {
        IrType from = IrType.parseFrom(BasicIrModuleDeserializerKt.getCodedInputStream(this.bytesSource.type(index)), extensionRegistryLite);
        Intrinsics.checkNotNullExpressionValue(from, "parseFrom(...)");
        return from;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public IdSignature signature(int index) throws IOException {
        IdSignature from = IdSignature.parseFrom(BasicIrModuleDeserializerKt.getCodedInputStream(this.bytesSource.signature(index)), extensionRegistryLite);
        Intrinsics.checkNotNullExpressionValue(from, "parseFrom(...)");
        return from;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public String string(int index) {
        return WobblyTF8.INSTANCE.decode(this.bytesSource.string(index));
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public IrExpression expressionBody(int index) throws IOException {
        IrExpression from = IrExpression.parseFrom(BasicIrModuleDeserializerKt.getCodedInputStream(this.bytesSource.body(index)), extensionRegistryLite);
        Intrinsics.checkNotNullExpressionValue(from, "parseFrom(...)");
        return from;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public IrStatement statementBody(int index) throws IOException {
        IrStatement from = IrStatement.parseFrom(BasicIrModuleDeserializerKt.getCodedInputStream(this.bytesSource.body(index)), extensionRegistryLite);
        Intrinsics.checkNotNullExpressionValue(from, "parseFrom(...)");
        return from;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public String debugInfo(int index) {
        byte[] bArrDebugInfo = this.bytesSource.debugInfo(index);
        if (bArrDebugInfo != null) {
            return WobblyTF8.INSTANCE.decode(bArrDebugInfo);
        }
        return null;
    }

    /* JADX INFO: compiled from: IrFileDeserializer.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFileFromBytes$Companion;", "", "()V", "extensionRegistryLite", "Lorg/jetbrains/kotlin/protobuf/ExtensionRegistryLite;", "getExtensionRegistryLite", "()Lorg/jetbrains/kotlin/protobuf/ExtensionRegistryLite;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final ExtensionRegistryLite getExtensionRegistryLite() {
            return IrLibraryFileFromBytes.extensionRegistryLite;
        }
    }

    static {
        ExtensionRegistryLite extensionRegistryLiteNewInstance = ExtensionRegistryLite.newInstance();
        Intrinsics.checkNotNullExpressionValue(extensionRegistryLiteNewInstance, "newInstance(...)");
        extensionRegistryLite = extensionRegistryLiteNewInstance;
    }
}
