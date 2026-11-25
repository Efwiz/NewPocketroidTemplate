package org.jetbrains.kotlin.backend.jvm.serialization;

import com.reandroid.arsc.container.SpecTypePair;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile;
import org.jetbrains.kotlin.backend.common.serialization.proto.IdSignature;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrDeclaration;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrExpression;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrStatement;
import org.jetbrains.kotlin.backend.common.serialization.proto.IrType;
import org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;

/* JADX INFO: compiled from: deserializeLazyDeclarations.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0002\u0018\u00002\u00020\u0001BK\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003\u0012\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u0003¢\u0006\u0002\u0010\fJ\u0010\u0010\u000b\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u0013\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u0014\u001a\u00020\u00152\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u0016\u001a\u00020\b2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0010\u0010\u0017\u001a\u00020\u00042\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/serialization/IrLibraryFileFromAnnotation;", "Lorg/jetbrains/kotlin/backend/common/serialization/IrLibraryFile;", SpecTypePair.NAME_types, "", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrType;", "signatures", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IdSignature;", "strings", "", "bodies", "Lorg/jetbrains/kotlin/backend/jvm/serialization/proto/JvmIr$XStatementOrExpression;", "debugInfo", "(Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "index", "", "declaration", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrDeclaration;", "expressionBody", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrExpression;", "signature", "statementBody", "Lorg/jetbrains/kotlin/backend/common/serialization/proto/IrStatement;", "string", ModuleXmlParser.TYPE, "ir.serialization.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class IrLibraryFileFromAnnotation extends IrLibraryFile {
    private final List<JvmIr.XStatementOrExpression> bodies;
    private final List<String> debugInfo;
    private final List<IdSignature> signatures;
    private final List<String> strings;
    private final List<IrType> types;

    public IrLibraryFileFromAnnotation(List<IrType> types, List<IdSignature> signatures, List<String> strings, List<JvmIr.XStatementOrExpression> bodies, List<String> debugInfo) {
        Intrinsics.checkNotNullParameter(types, "types");
        Intrinsics.checkNotNullParameter(signatures, "signatures");
        Intrinsics.checkNotNullParameter(strings, "strings");
        Intrinsics.checkNotNullParameter(bodies, "bodies");
        Intrinsics.checkNotNullParameter(debugInfo, "debugInfo");
        this.types = types;
        this.signatures = signatures;
        this.strings = strings;
        this.bodies = bodies;
        this.debugInfo = debugInfo;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public IrDeclaration declaration(int index) {
        throw new IllegalStateException("This method is never supposed to be called".toString());
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public IrType type(int index) {
        return this.types.get(index);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public IdSignature signature(int index) {
        return this.signatures.get(index);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public String string(int index) {
        return this.strings.get(index);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public String debugInfo(int index) {
        return this.debugInfo.get(index);
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public IrExpression expressionBody(int index) {
        JvmIr.XStatementOrExpression xStatementOrExpression = this.bodies.get(index);
        if (!xStatementOrExpression.hasExpression()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        IrExpression expression = xStatementOrExpression.getExpression();
        Intrinsics.checkNotNullExpressionValue(expression, "getExpression(...)");
        return expression;
    }

    @Override // org.jetbrains.kotlin.backend.common.serialization.IrLibraryFile
    public IrStatement statementBody(int index) {
        JvmIr.XStatementOrExpression xStatementOrExpression = this.bodies.get(index);
        if (!xStatementOrExpression.hasStatement()) {
            throw new IllegalArgumentException("Failed requirement.".toString());
        }
        IrStatement statement = xStatementOrExpression.getStatement();
        Intrinsics.checkNotNullExpressionValue(statement, "getStatement(...)");
        return statement;
    }
}
