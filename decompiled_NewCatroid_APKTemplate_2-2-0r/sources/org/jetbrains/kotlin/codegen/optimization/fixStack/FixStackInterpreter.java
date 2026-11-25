package org.jetbrains.kotlin.codegen.optimization.fixStack;

import com.android.dx.rop.code.RegisterSpec;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.org.objectweb.asm.Handle;
import org.jetbrains.org.objectweb.asm.Type;
import org.jetbrains.org.objectweb.asm.tree.AbstractInsnNode;

/* JADX INFO: compiled from: FixStackInterpreter.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\r\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u00020\u00022\u0006\u0010\u0005\u001a\u00020\u0002H\u0014J\u0010\u0010\u0005\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J\b\u0010\b\u001a\u00020\u0002H\u0014J\b\u0010\t\u001a\u00020\u0002H\u0014J\b\u0010\n\u001a\u00020\u0002H\u0014J\u0018\u0010\u000b\u001a\u00020\u00022\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0002H\u0016J\b\u0010\u000f\u001a\u00020\u0002H\u0014J\b\u0010\u0010\u001a\u00020\u0002H\u0014J\u0010\u0010\u0011\u001a\u00020\u00022\u0006\u0010\u0012\u001a\u00020\u0013H\u0014J\b\u0010\u0014\u001a\u00020\u0002H\u0014J\b\u0010\u0015\u001a\u00020\u0002H\u0014J\u0018\u0010\u0016\u001a\u00020\u00022\u0006\u0010\u0017\u001a\u00020\u00022\u0006\u0010\u0018\u001a\u00020\u0002H\u0016J\u0010\u0010\u0019\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J\b\u0010\u001a\u001a\u00020\u0002H\u0014J\u0010\u0010\u001b\u001a\u00020\u00022\u0006\u0010\u0006\u001a\u00020\u0007H\u0014J\b\u0010\u001c\u001a\u00020\u0002H\u0014J\u0010\u0010\u001d\u001a\u00020\u00022\u0006\u0010\u001e\u001a\u00020\u0007H\u0014J\b\u0010\u001f\u001a\u00020\u0002H\u0014¨\u0006 "}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackInterpreter;", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/BasicTypeInterpreter;", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackValue;", "()V", "aaLoadValue", "arrayValue", ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "booleanValue", "byteValue", "charValue", "copyOperation", "insn", "Lorg/jetbrains/org/objectweb/asm/tree/AbstractInsnNode;", "value", "doubleValue", "floatValue", "handleValue", "handle", "Lorg/jetbrains/org/objectweb/asm/Handle;", "intValue", "longValue", "merge", RegisterSpec.PREFIX, "w", "methodValue", "nullValue", "objectValue", "shortValue", "typeConstValue", "typeConst", "uninitializedValue", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FixStackInterpreter extends BasicTypeInterpreter<FixStackValue> {
    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue uninitializedValue() {
        return FixStackValue.UNINITIALIZED;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue booleanValue() {
        return FixStackValue.INT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue charValue() {
        return FixStackValue.INT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue byteValue() {
        return FixStackValue.INT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue shortValue() {
        return FixStackValue.INT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue intValue() {
        return FixStackValue.INT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue longValue() {
        return FixStackValue.LONG;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue floatValue() {
        return FixStackValue.FLOAT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue doubleValue() {
        return FixStackValue.DOUBLE;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue nullValue() {
        return FixStackValue.OBJECT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue objectValue(Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return FixStackValue.OBJECT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue arrayValue(Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return FixStackValue.OBJECT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue methodValue(Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return FixStackValue.OBJECT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue handleValue(Handle handle) {
        Intrinsics.checkNotNullParameter(handle, "handle");
        return FixStackValue.OBJECT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue typeConstValue(Type typeConst) {
        Intrinsics.checkNotNullParameter(typeConst, "typeConst");
        return FixStackValue.OBJECT;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.codegen.optimization.fixStack.BasicTypeInterpreter
    public FixStackValue aaLoadValue(FixStackValue arrayValue) {
        Intrinsics.checkNotNullParameter(arrayValue, "arrayValue");
        return FixStackValue.OBJECT;
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public FixStackValue copyOperation(AbstractInsnNode insn, FixStackValue value) {
        Intrinsics.checkNotNullParameter(insn, "insn");
        Intrinsics.checkNotNullParameter(value, "value");
        switch (insn.getOpcode()) {
            case 21:
                return FixStackValue.INT;
            case 22:
                return FixStackValue.LONG;
            case 23:
                return FixStackValue.FLOAT;
            case 24:
                return FixStackValue.DOUBLE;
            case 25:
                return FixStackValue.OBJECT;
            default:
                return value;
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.tree.analysis.Interpreter
    public FixStackValue merge(FixStackValue v, FixStackValue w) {
        Intrinsics.checkNotNullParameter(v, "v");
        Intrinsics.checkNotNullParameter(w, "w");
        if (v == w) {
            return v;
        }
        throw new AssertionError("Mismatching value kinds: " + v + " != " + w);
    }
}
