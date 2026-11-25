package org.jetbrains.kotlin.backend.common.serialization;

import kotlin.Metadata;
import org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrStatement;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrType;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: IrFileDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b&\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H&J\u0010\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0005\u001a\u00020\u0006H&¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;", "", "()V", "debugInfo", "", "index", "", "declaration", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDeclaration;", "expressionBody", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrExpression;", "signature", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IdSignature;", "statementBody", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrStatement;", "string", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrType;", "ir.serialization.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class IrLibraryFile {
    public abstract String debugInfo(int index);

    public abstract IrDeclaration declaration(int index);

    public abstract IrExpression expressionBody(int index);

    public abstract IdSignature signature(int index);

    public abstract IrStatement statementBody(int index);

    public abstract String string(int index);

    public abstract IrType type(int index);
}
