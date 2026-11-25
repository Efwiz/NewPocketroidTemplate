package org.jetbrains.kotlin.codegen.inline;

import androidx.core.app.NotificationCompat;
import com.google.firebase.database.core.ServerValues;
import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: compiled from: LocalVarRemapper.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001:\u0002!\"B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0005H\u0002J\u000e\u0010\u000f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0005J\u001e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015J@\u0010\u0016\u001a\u00020\u00112\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\b\u0010\u001a\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u001b\u001a\u00020\u001c2\u0006\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u000e\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020\u0015J\u001e\u0010\u001e\u001a\u00020\u00112\u0006\u0010\u001f\u001a\u00020\u00052\u0006\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0014\u001a\u00020 R\u000e\u0010\u0007\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\b\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\n0\tX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000b¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/LocalVarRemapper;", "", "params", "Lorg/jetbrains/kotlin/codegen/inline/Parameters;", "additionalShift", "", "(Lorg/jetbrains/kotlin/codegen/inline/Parameters;I)V", "actualParamsSize", "remapValues", "", "Lorg/jetbrains/kotlin/codegen/StackValue;", "[Lorg/jetbrains/kotlin/codegen/StackValue;", "doRemap", "Lorg/jetbrains/kotlin/codegen/inline/LocalVarRemapper$RemapInfo;", "index", "remap", "visitIincInsn", "", PsiKeyword.VAR, ServerValues.NAME_OP_INCREMENT, JvmAnnotationNames.METADATA_VERSION_FIELD_NAME, "Lorg/jetbrains/org/objectweb/asm/MethodVisitor;", "visitLocalVariable", "name", "", "desc", "signature", "start", "Lorg/jetbrains/org/objectweb/asm/Label;", "end", "visitVarInsn", "opcode", "Lorg/jetbrains/org/objectweb/asm/commons/InstructionAdapter;", "RemapInfo", "RemapStatus", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LocalVarRemapper {
    private final int actualParamsSize;
    private final int additionalShift;
    private final Parameters params;
    private final StackValue[] remapValues;

    /* JADX INFO: compiled from: LocalVarRemapper.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/LocalVarRemapper$RemapStatus;", "", "(Ljava/lang/String;I)V", "SHIFT", "REMAPPED", "FAIL", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public enum RemapStatus {
        SHIFT,
        REMAPPED,
        FAIL;

        private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());

        public static EnumEntries<RemapStatus> getEntries() {
            return $ENTRIES;
        }
    }

    public LocalVarRemapper(Parameters params, int i) {
        int size;
        Intrinsics.checkNotNullParameter(params, "params");
        this.params = params;
        this.additionalShift = i;
        this.remapValues = new StackValue[params.getArgsSizeOnStack()];
        int i2 = 0;
        for (ParameterInfo parameterInfo : params) {
            int declarationSlot = this.params.getDeclarationSlot(parameterInfo);
            if (!parameterInfo.isSkippedOrRemapped()) {
                this.remapValues[declarationSlot] = StackValue.local(i2, AsmTypes.OBJECT_TYPE);
                size = parameterInfo.getType().getSize();
            } else {
                this.remapValues[declarationSlot] = parameterInfo.isRemapped() ? parameterInfo.getRemapValue() : null;
                if (CapturedParamInfo.INSTANCE.isSynthetic(parameterInfo)) {
                    size = parameterInfo.getType().getSize();
                }
            }
            i2 += size;
        }
        this.actualParamsSize = i2;
    }

    private final RemapInfo doRemap(int index) {
        int argsSizeOnStack;
        if (index < this.params.getArgsSizeOnStack()) {
            ParameterInfo parameterByDeclarationSlot = this.params.getParameterByDeclarationSlot(index);
            StackValue stackValue = this.remapValues[index];
            if (parameterByDeclarationSlot.getIsSkipped() || stackValue == null) {
                return new RemapInfo(parameterByDeclarationSlot, null, null, 6, null);
            }
            if (parameterByDeclarationSlot.isRemapped()) {
                return new RemapInfo(parameterByDeclarationSlot, stackValue, RemapStatus.REMAPPED);
            }
            argsSizeOnStack = ((StackValue.Local) stackValue).index;
        } else {
            argsSizeOnStack = index + (this.actualParamsSize - this.params.getArgsSizeOnStack());
        }
        return new RemapInfo(null, StackValue.local(argsSizeOnStack + this.additionalShift, AsmTypes.OBJECT_TYPE), RemapStatus.SHIFT);
    }

    public final RemapInfo remap(int index) {
        RemapInfo remapInfoDoRemap = doRemap(index);
        if (RemapStatus.FAIL != remapInfoDoRemap.status) {
            return remapInfoDoRemap;
        }
        ParameterInfo parameterInfo = remapInfoDoRemap.parameterInfo;
        StringBuilder sb = new StringBuilder("Trying to access skipped parameter: ");
        ParameterInfo parameterInfo2 = remapInfoDoRemap.parameterInfo;
        Intrinsics.checkNotNull(parameterInfo2);
        throw new RuntimeException(sb.append(parameterInfo2.getType()).append(" at ").append(index).toString());
    }

    public final void visitIincInsn(int var, int increment, MethodVisitor mv) {
        Intrinsics.checkNotNullParameter(mv, "mv");
        RemapInfo remapInfoRemap = remap(var);
        if (!(remapInfoRemap.value instanceof StackValue.Local)) {
            throw new AssertionError("Remapped value should be a local: " + remapInfoRemap.value);
        }
        mv.visitIincInsn(((StackValue.Local) remapInfoRemap.value).index, increment);
    }

    public final void visitLocalVariable(String name2, String desc, String signature, Label start, Label end, int index, MethodVisitor mv) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(desc, "desc");
        Intrinsics.checkNotNullParameter(start, "start");
        Intrinsics.checkNotNullParameter(end, "end");
        Intrinsics.checkNotNullParameter(mv, "mv");
        RemapInfo remapInfoDoRemap = doRemap(index);
        if (RemapStatus.SHIFT == remapInfoDoRemap.status) {
            StackValue stackValue = remapInfoDoRemap.value;
            Intrinsics.checkNotNull(stackValue, "null cannot be cast to non-null type org.jetbrains.kotlin.codegen.StackValue.Local");
            mv.visitLocalVariable(name2, desc, signature, start, end, ((StackValue.Local) stackValue).index);
        }
    }

    public final void visitVarInsn(int opcode, int var, InstructionAdapter mv) {
        Intrinsics.checkNotNullParameter(mv, "mv");
        RemapInfo remapInfoRemap = remap(var);
        StackValue stackValue = remapInfoRemap.value;
        if (stackValue instanceof StackValue.Local) {
            boolean zIsStoreInstruction = InlineCodegenUtilsKt.isStoreInstruction(opcode);
            if (remapInfoRemap.parameterInfo != null) {
                opcode = ((StackValue.Local) stackValue).type.getOpcode(zIsStoreInstruction ? 54 : 21);
            }
            StackValue.Local local = (StackValue.Local) stackValue;
            mv.visitVarInsn(opcode, local.index);
            if (remapInfoRemap.parameterInfo == null || zIsStoreInstruction) {
                return;
            }
            StackValue.coerce(local.type, remapInfoRemap.parameterInfo.getType(), mv);
            return;
        }
        ParameterInfo parameterInfo = remapInfoRemap.parameterInfo;
        Intrinsics.checkNotNull(stackValue);
        ParameterInfo parameterInfo2 = remapInfoRemap.parameterInfo;
        Intrinsics.checkNotNull(parameterInfo2);
        stackValue.put(parameterInfo2.getType(), mv);
    }

    /* JADX INFO: compiled from: LocalVarRemapper.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B%\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00038\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0087\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004¢\u0006\u0002\n\u0000¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/LocalVarRemapper$RemapInfo;", "", "parameterInfo", "Lorg/jetbrains/kotlin/codegen/inline/ParameterInfo;", "value", "Lorg/jetbrains/kotlin/codegen/StackValue;", NotificationCompat.CATEGORY_STATUS, "Lorg/jetbrains/kotlin/codegen/inline/LocalVarRemapper$RemapStatus;", "(Lorg/jetbrains/kotlin/codegen/inline/ParameterInfo;Lorg/jetbrains/kotlin/codegen/StackValue;Lorg/jetbrains/kotlin/codegen/inline/LocalVarRemapper$RemapStatus;)V", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class RemapInfo {
        public final ParameterInfo parameterInfo;
        public final RemapStatus status;
        public final StackValue value;

        public RemapInfo(ParameterInfo parameterInfo, StackValue stackValue, RemapStatus status) {
            Intrinsics.checkNotNullParameter(status, "status");
            this.parameterInfo = parameterInfo;
            this.value = stackValue;
            this.status = status;
        }

        public /* synthetic */ RemapInfo(ParameterInfo parameterInfo, StackValue stackValue, RemapStatus remapStatus, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(parameterInfo, (i & 2) != 0 ? null : stackValue, (i & 4) != 0 ? RemapStatus.FAIL : remapStatus);
        }
    }
}
