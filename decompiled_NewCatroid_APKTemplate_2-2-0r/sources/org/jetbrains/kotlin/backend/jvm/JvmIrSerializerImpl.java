package org.jetbrains.kotlin.backend.jvm;

import java.util.LinkedHashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.serialization.DeclarationTable;
import org.jetbrains.kotlin.backend.jvm.lower.FileClassLoweringKt;
import org.jetbrains.kotlin.backend.jvm.serialization.JvmGlobalDeclarationTable;
import org.jetbrains.kotlin.backend.jvm.serialization.JvmIrSerializerSession;
import org.jetbrains.kotlin.backend.jvm.serialization.proto.JvmIr;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.CompilerConfiguration;
import org.jetbrains.kotlin.config.JVMConfigurationKeys;
import org.jetbrains.kotlin.config.JvmSerializeIrMode;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.util.IrMessageLogger;
import org.jetbrains.kotlin.ir.util.IrMessageLoggerKt;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: JvmIrSerializerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u0012\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0012\u0010\u000f\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmIrSerializerImpl;", "Lorg/jetbrains/kotlin/backend/jvm/JvmIrSerializer;", "configuration", "Lorg/jetbrains/kotlin/config/CompilerConfiguration;", "(Lorg/jetbrains/kotlin/config/CompilerConfiguration;)V", "declarationTable", "Lorg/jetbrains/kotlin/backend/common/serialization/DeclarationTable;", "makeSerializerSession", "Lorg/jetbrains/kotlin/backend/jvm/serialization/JvmIrSerializerSession;", "fileClassFqName", "Lorg/jetbrains/kotlin/name/FqName;", "serializeIrFile", "", "irFile", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "serializeTopLevelIrClass", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmIrSerializerImpl implements JvmIrSerializer {
    private final CompilerConfiguration configuration;
    private final DeclarationTable declarationTable;

    public JvmIrSerializerImpl(CompilerConfiguration configuration) {
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        this.configuration = configuration;
        this.declarationTable = new DeclarationTable(new JvmGlobalDeclarationTable());
    }

    @Override // org.jetbrains.kotlin.backend.jvm.JvmIrSerializer
    public byte[] serializeIrFile(IrFile irFile) {
        Intrinsics.checkNotNullParameter(irFile, "irFile");
        JvmIr.ClassOrFile classOrFileSerializeJvmIrFile = makeSerializerSession(FileClassLoweringKt.getFileClassInfo(irFile).getFileClassFqName()).serializeJvmIrFile(irFile);
        if (classOrFileSerializeJvmIrFile != null) {
            return classOrFileSerializeJvmIrFile.toByteArray();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.backend.jvm.JvmIrSerializer
    public byte[] serializeTopLevelIrClass(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        boolean z = irClass.getParent() instanceof IrFile;
        IrDeclarationParent parent = irClass.getParent();
        Intrinsics.checkNotNull(parent, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.declarations.IrFile");
        JvmIr.ClassOrFile classOrFileSerializeTopLevelClass = makeSerializerSession(FileClassLoweringKt.getFileClassInfo((IrFile) parent).getFileClassFqName()).serializeTopLevelClass(irClass);
        if (classOrFileSerializeTopLevelClass != null) {
            return classOrFileSerializeTopLevelClass.toByteArray();
        }
        return null;
    }

    private final JvmIrSerializerSession makeSerializerSession(FqName fileClassFqName) {
        IrMessageLogger irMessageLogger = IrMessageLoggerKt.getIrMessageLogger(this.configuration);
        DeclarationTable declarationTable = this.declarationTable;
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        JvmSerializeIrMode jvmSerializeIrMode = (JvmSerializeIrMode) this.configuration.get(JVMConfigurationKeys.SERIALIZE_IR);
        if (jvmSerializeIrMode == null) {
            jvmSerializeIrMode = JvmSerializeIrMode.NONE;
        }
        JvmSerializeIrMode jvmSerializeIrMode2 = jvmSerializeIrMode;
        Intrinsics.checkNotNull(jvmSerializeIrMode2);
        return new JvmIrSerializerSession(irMessageLogger, declarationTable, linkedHashMap, jvmSerializeIrMode2, fileClassFqName, CommonConfigurationKeysKt.getLanguageVersionSettings(this.configuration), false, 64, null);
    }
}
