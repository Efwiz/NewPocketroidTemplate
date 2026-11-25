package org.jetbrains.kotlin.backend.jvm;

import java.util.List;
import kotlin.Metadata;
import org.jetbrains.kotlin.ir.IrBuiltIns;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.linkage.IrProvider;
import org.jetbrains.kotlin.ir.util.SymbolTable;

/* JADX INFO: compiled from: JvmIrDeserializer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J6\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000b2\u0006\u0010\r\u001a\u00020\u000eH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u000fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmIrDeserializer;", "", "deserializeTopLevelClass", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "irBuiltIns", "Lorg/jetbrains/kotlin/ir/IrBuiltIns;", "symbolTable", "Lorg/jetbrains/kotlin/ir/util/SymbolTable;", "irProviders", "", "Lorg/jetbrains/kotlin/ir/linkage/IrProvider;", "extensions", "Lorg/jetbrains/kotlin/backend/jvm/JvmGeneratorExtensions;", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JvmIrDeserializer {
    boolean deserializeTopLevelClass(IrClass irClass, IrBuiltIns irBuiltIns, SymbolTable symbolTable, List<? extends IrProvider> irProviders, JvmGeneratorExtensions extensions);
}
