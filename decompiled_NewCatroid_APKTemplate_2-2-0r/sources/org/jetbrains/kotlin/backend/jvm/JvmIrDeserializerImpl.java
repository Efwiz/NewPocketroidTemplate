package org.jetbrains.kotlin.backend.jvm;

import java.io.IOException;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.jvm.lower.SingletonObjectJvmStaticTransformer;
import org.jetbrains.kotlin.backend.jvm.serialization.DeserializeLazyDeclarationsKt;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.util.SymbolTable;
import org.jetbrains.kotlin.load.kotlin.JvmPackagePartSource;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinaryClass;
import org.jetbrains.kotlin.load.kotlin.KotlinJvmBinarySourceElement;
import org.jetbrains.kotlin.load.kotlin.header.KotlinClassHeader;

/* JADX INFO: compiled from: JvmIrDeserializerImpl.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J6\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\f2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmIrDeserializerImpl;", "Lorg/jetbrains/kotlin/backend/jvm/JvmIrDeserializer;", "()V", "deserializeTopLevelClass", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "extensions", "Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;", "backend.jvm.entrypoint"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmIrDeserializerImpl implements JvmIrDeserializer {
    /* JADX WARN: Code duplicated, block: B:15:0x004c A[RETURN] */
    /* JADX WARN: Code duplicated, block: B:17:0x004e  */
    @Override // org.jetbrains.kotlin.backend.jvm.JvmIrDeserializer
    public boolean deserializeTopLevelClass(IrClass irClass, IrBuiltIns irBuiltIns, SymbolTable symbolTable, List<? extends IrProvider> irProviders, JvmGeneratorExtensions extensions) throws IOException {
        byte[] bArr;
        KotlinJvmBinaryClass knownJvmBinaryClass;
        KotlinClassHeader classHeader;
        byte[] serializedIr;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        Intrinsics.checkNotNullParameter(irBuiltIns, "irBuiltIns");
        Intrinsics.checkNotNullParameter(symbolTable, "symbolTable");
        Intrinsics.checkNotNullParameter(irProviders, "irProviders");
        Intrinsics.checkNotNullParameter(extensions, "extensions");
        SourceElement source = irClass.getSource();
        if (!(source instanceof KotlinJvmBinarySourceElement)) {
            if (!(source instanceof JvmPackagePartSource) || (knownJvmBinaryClass = ((JvmPackagePartSource) source).getKnownJvmBinaryClass()) == null || (classHeader = knownJvmBinaryClass.getClassHeader()) == null) {
                bArr = null;
            } else {
                serializedIr = classHeader.getSerializedIr();
            }
            if (bArr == null) {
                return false;
            }
            DeserializeLazyDeclarationsKt.deserializeFromByteArray(bArr, irBuiltIns, symbolTable, irProviders, irClass, new JvmIrTypeSystemContext(irBuiltIns));
            irClass.transform(new SingletonObjectJvmStaticTransformer(irBuiltIns, extensions.getCachedFields()), null);
            return true;
        }
        serializedIr = ((KotlinJvmBinarySourceElement) source).getBinaryClass().getClassHeader().getSerializedIr();
        bArr = serializedIr;
        if (bArr == null) {
            return false;
        }
        DeserializeLazyDeclarationsKt.deserializeFromByteArray(bArr, irBuiltIns, symbolTable, irProviders, irClass, new JvmIrTypeSystemContext(irBuiltIns));
        irClass.transform(new SingletonObjectJvmStaticTransformer(irBuiltIns, extensions.getCachedFields()), null);
        return true;
    }
}
