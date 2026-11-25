package org.jetbrains.kotlin.backend.jvm.codegen;

import com.android.dx.rop.code.RegisterSpec;
import kotlin.Metadata;
import org.jetbrains.kotlin.backend.common.extensions.IrIntrinsicExtension;
import org.jetbrains.kotlin.backend.jvm.intrinsics.IntrinsicMethod;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;
import org.jetbrains.org.objectweb.asm.tree.InsnList;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JvmIrIntrinsicExtension.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&J(\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/codegen/JvmIrIntrinsicExtension;", "Lorg/jetbrains/kotlin/backend/common/extensions/IrIntrinsicExtension;", "getIntrinsic", "Lorg/jetbrains/kotlin/backend/jvm/intrinsics/IntrinsicMethod;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "rewritePluginDefinedOperationMarker", "", RegisterSpec.PREFIX, "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "reifiedInsn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "instructions", "Lorg/jetbrains/org/objectweb/asm/tree/InsnList;", ModuleXmlParser.TYPE, "Lorg/jetbrains/kotlin/ir/types/IrType;", "backend.jvm.codegen"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface JvmIrIntrinsicExtension extends IrIntrinsicExtension {
    IntrinsicMethod getIntrinsic(IrFunctionSymbol symbol);

    boolean rewritePluginDefinedOperationMarker(InstructionAdapter v, AbstractInsnNode reifiedInsn, InsnList instructions, IrType type);
}
