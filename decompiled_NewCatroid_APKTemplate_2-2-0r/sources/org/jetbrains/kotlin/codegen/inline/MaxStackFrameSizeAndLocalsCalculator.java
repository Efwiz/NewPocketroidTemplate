package org.jetbrains.kotlin.codegen.inline;

import com.google.firebase.crashlytics.internal.metadata.UserMetadata;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.utils.SmartIdentityTable;
import org.jetbrains.kotlin.utils.SmartSet;
import org.jetbrains.org.objectweb.asm.Handle;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: loaded from: classes3.dex */
public class MaxStackFrameSizeAndLocalsCalculator extends MaxLocalsCalculator {
    private static final int[] FRAME_SIZE_CHANGE_BY_OPCODE;
    private LabelWrapper currentBlock;
    private final Collection<ExceptionHandler> exceptionHandlers;
    private final LabelWrapper firstLabel;
    private final SmartIdentityTable<Label, LabelWrapper> labelWrappersTable;
    private int maxStack;
    private int maxStackSize;
    private LabelWrapper previousBlock;
    private int stackSize;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "owner";
                break;
            case 2:
            case 4:
                objArr[0] = "name";
                break;
            case 3:
            case 5:
            case 16:
                objArr[0] = "desc";
                break;
            case 6:
                objArr[0] = "bsm";
                break;
            case 7:
                objArr[0] = "bsmArgs";
                break;
            case 8:
            case 9:
                objArr[0] = CoroutineCodegenUtilKt.COROUTINE_LABEL_FIELD_NAME;
                break;
            case 10:
                objArr[0] = "cst";
                break;
            case 11:
            case 13:
                objArr[0] = "dflt";
                break;
            case 12:
            case 15:
                objArr[0] = "labels";
                break;
            case 14:
                objArr[0] = UserMetadata.KEYDATA_FILENAME;
                break;
            case 17:
                objArr[0] = "start";
                break;
            case 18:
                objArr[0] = "end";
                break;
            case 19:
                objArr[0] = "handler";
                break;
            default:
                objArr[0] = ModuleXmlParser.TYPE;
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/inline/MaxStackFrameSizeAndLocalsCalculator";
        switch (i) {
            case 1:
            case 2:
            case 3:
                objArr[2] = "visitFieldInsn";
                break;
            case 4:
            case 5:
            case 6:
            case 7:
                objArr[2] = "visitInvokeDynamicInsn";
                break;
            case 8:
                objArr[2] = "visitJumpInsn";
                break;
            case 9:
                objArr[2] = "visitLabel";
                break;
            case 10:
                objArr[2] = "visitLdcInsn";
                break;
            case 11:
            case 12:
                objArr[2] = "visitTableSwitchInsn";
                break;
            case 13:
            case 14:
            case 15:
                objArr[2] = "visitLookupSwitchInsn";
                break;
            case 16:
                objArr[2] = "visitMultiANewArrayInsn";
                break;
            case 17:
            case 18:
            case 19:
                objArr[2] = "visitTryCatchBlock";
                break;
            default:
                objArr[2] = "visitTypeInsn";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    static {
        int[] iArr = new int[202];
        for (int i = 0; i < 202; i++) {
            iArr[i] = "EFFFFFFFFGGFFFGGFFFEEFGFGFEEEEEEEEEEEEEEEEEEEEDEDEDDDDDCDCDEEEEEEEEEEEEEEEEEEEEBABABBBBDCFFFGGGEDCDCDCDCDCDCDCDCDCDCEEEEDDDDDDDCDCDCEFEFDDEEFFDEDEEEBDDBBDDDDDDCCCCCCCCEFEDDDCDCDEEEEEEEEEEFEEEEEEDDEEDDEE".charAt(i) - 'E';
        }
        FRAME_SIZE_CHANGE_BY_OPCODE = iArr;
    }

    public MaxStackFrameSizeAndLocalsCalculator(int i, int i2, String str, MethodVisitor methodVisitor) {
        super(i, i2, str, methodVisitor);
        this.exceptionHandlers = new LinkedList();
        this.labelWrappersTable = new SmartIdentityTable<>();
        LabelWrapper labelWrapper = getLabelWrapper(new Label());
        this.firstLabel = labelWrapper;
        processLabel(labelWrapper.label);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitFrame(int i, int i2, Object[] objArr, int i3, Object[] objArr2) {
        throw new AssertionError("We don't support visitFrame because currently nobody needs");
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitInsn(int i) {
        increaseStackSize(FRAME_SIZE_CHANGE_BY_OPCODE[i]);
        if ((i >= 172 && i <= 177) || i == 191) {
            noSuccessor();
        }
        super.visitInsn(i);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitIntInsn(int i, int i2) {
        if (i != 188) {
            increaseStackSize(1);
        }
        super.visitIntInsn(i, i2);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.MaxLocalsCalculator, org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitVarInsn(int i, int i2) {
        increaseStackSize(FRAME_SIZE_CHANGE_BY_OPCODE[i]);
        super.visitVarInsn(i, i2);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitTypeInsn(int i, String str) {
        if (str == null) {
            $$$reportNull$$$0(0);
        }
        if (i == 187) {
            increaseStackSize(1);
        }
        super.visitTypeInsn(i, str);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitFieldInsn(int i, String str, String str2, String str3) {
        int i2 = 1;
        if (str == null) {
            $$$reportNull$$$0(1);
        }
        if (str2 == null) {
            $$$reportNull$$$0(2);
        }
        if (str3 == null) {
            $$$reportNull$$$0(3);
        }
        char cCharAt = str3.charAt(0);
        int i3 = -2;
        switch (i) {
            case 178:
                if (cCharAt == 'D' || cCharAt == 'J') {
                    i2 = 2;
                }
                break;
            case 179:
                if (cCharAt != 'D' && cCharAt != 'J') {
                    i3 = -1;
                }
                i2 = i3;
                break;
            case 180:
                if (cCharAt != 'D' && cCharAt != 'J') {
                    i2 = 0;
                }
                break;
            default:
                if (cCharAt == 'D' || cCharAt == 'J') {
                    i3 = -3;
                }
                i2 = i3;
                break;
        }
        increaseStackSize(i2);
        super.visitFieldInsn(i, str, str2, str3);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitMethodInsn(int i, String str, String str2, String str3, boolean z) {
        int argumentsAndReturnSizes = Type.getArgumentsAndReturnSizes(str3);
        increaseStackSize(i == 184 ? ((argumentsAndReturnSizes & 3) - (argumentsAndReturnSizes >> 2)) + 1 : (argumentsAndReturnSizes & 3) - (argumentsAndReturnSizes >> 2));
        super.visitMethodInsn(i, str, str2, str3, z);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitInvokeDynamicInsn(String str, String str2, Handle handle, Object... objArr) {
        if (str == null) {
            $$$reportNull$$$0(4);
        }
        if (str2 == null) {
            $$$reportNull$$$0(5);
        }
        if (handle == null) {
            $$$reportNull$$$0(6);
        }
        if (objArr == null) {
            $$$reportNull$$$0(7);
        }
        int argumentsAndReturnSizes = Type.getArgumentsAndReturnSizes(str2);
        increaseStackSize(((argumentsAndReturnSizes & 3) - (argumentsAndReturnSizes >> 2)) + 1);
        super.visitInvokeDynamicInsn(str, str2, handle, objArr);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitJumpInsn(int i, Label label) {
        if (label == null) {
            $$$reportNull$$$0(8);
        }
        if (this.currentBlock != null) {
            this.stackSize += FRAME_SIZE_CHANGE_BY_OPCODE[i];
            addSuccessor(getLabelWrapper(label), this.stackSize);
            if (i == 167) {
                noSuccessor();
            }
        }
        super.visitJumpInsn(i, label);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitLabel(Label label) {
        if (label == null) {
            $$$reportNull$$$0(9);
        }
        processLabel(label);
        super.visitLabel(label);
    }

    private void processLabel(Label label) {
        LabelWrapper labelWrapper = getLabelWrapper(label);
        LabelWrapper labelWrapper2 = this.currentBlock;
        if (labelWrapper2 != null) {
            labelWrapper2.outputStackMax = this.maxStackSize;
            addSuccessor(labelWrapper, this.stackSize);
        }
        this.currentBlock = labelWrapper;
        this.stackSize = 0;
        this.maxStackSize = 0;
        LabelWrapper labelWrapper3 = this.previousBlock;
        if (labelWrapper3 != null) {
            labelWrapper3.nextLabel = labelWrapper;
        }
        this.previousBlock = labelWrapper;
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitLdcInsn(Object obj) {
        if (obj == null) {
            $$$reportNull$$$0(10);
        }
        if ((obj instanceof Long) || (obj instanceof Double)) {
            increaseStackSize(2);
        } else {
            increaseStackSize(1);
        }
        super.visitLdcInsn(obj);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitTableSwitchInsn(int i, int i2, Label label, Label... labelArr) {
        if (label == null) {
            $$$reportNull$$$0(11);
        }
        if (labelArr == null) {
            $$$reportNull$$$0(12);
        }
        visitSwitchInsn(label, labelArr);
        super.visitTableSwitchInsn(i, i2, label, labelArr);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitLookupSwitchInsn(Label label, int[] iArr, Label[] labelArr) {
        if (label == null) {
            $$$reportNull$$$0(13);
        }
        if (iArr == null) {
            $$$reportNull$$$0(14);
        }
        if (labelArr == null) {
            $$$reportNull$$$0(15);
        }
        visitSwitchInsn(label, labelArr);
        super.visitLookupSwitchInsn(label, iArr, labelArr);
    }

    private void visitSwitchInsn(Label label, Label[] labelArr) {
        if (this.currentBlock != null) {
            this.stackSize--;
            addSuccessor(getLabelWrapper(label), this.stackSize);
            for (Label label2 : labelArr) {
                addSuccessor(getLabelWrapper(label2), this.stackSize);
            }
            noSuccessor();
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitMultiANewArrayInsn(String str, int i) {
        if (str == null) {
            $$$reportNull$$$0(16);
        }
        if (this.currentBlock != null) {
            increaseStackSize(i - 1);
        }
        super.visitMultiANewArrayInsn(str, i);
    }

    @Override // org.jetbrains.kotlin.codegen.inline.MaxLocalsCalculator, org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitMaxs(int i, int i2) {
        Iterator<ExceptionHandler> it = this.exceptionHandlers.iterator();
        while (true) {
            int i3 = 0;
            if (!it.hasNext()) {
                Stack stack = new Stack();
                SmartSet smartSetCreate = SmartSet.create();
                stack.push(this.firstLabel);
                smartSetCreate.add(this.firstLabel);
                while (!stack.empty()) {
                    LabelWrapper labelWrapper = (LabelWrapper) stack.pop();
                    int i4 = labelWrapper.inputStackSize;
                    int i5 = labelWrapper.outputStackMax + i4;
                    if (i5 > i3) {
                        i3 = i5;
                    }
                    for (ControlFlowEdge controlFlowEdge : labelWrapper.successors) {
                        LabelWrapper labelWrapper2 = controlFlowEdge.successor;
                        if (!smartSetCreate.contains(labelWrapper2)) {
                            labelWrapper2.inputStackSize = controlFlowEdge.isExceptional ? 1 : controlFlowEdge.outputStackSize + i4;
                            smartSetCreate.add(labelWrapper2);
                            stack.push(labelWrapper2);
                        }
                    }
                }
                int iMax = Math.max(this.maxStack, Math.max(i, i3));
                this.maxStack = iMax;
                super.visitMaxs(iMax, i2);
                return;
            }
            ExceptionHandler next = it.next();
            LabelWrapper labelWrapper3 = next.end;
            for (LabelWrapper labelWrapper4 = next.start; labelWrapper4 != labelWrapper3; labelWrapper4 = labelWrapper4.nextLabel) {
                if (labelWrapper4 == null) {
                    throw new IllegalStateException("Bad exception handler end");
                }
                labelWrapper4.addSuccessor(next.handlerLabel, 0, true);
            }
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitTryCatchBlock(Label label, Label label2, Label label3, String str) {
        if (label == null) {
            $$$reportNull$$$0(17);
        }
        if (label2 == null) {
            $$$reportNull$$$0(18);
        }
        if (label3 == null) {
            $$$reportNull$$$0(19);
        }
        this.exceptionHandlers.add(new ExceptionHandler(getLabelWrapper(label), getLabelWrapper(label2), getLabelWrapper(label3)));
        super.visitTryCatchBlock(label, label2, label3, str);
    }

    private static class ExceptionHandler {
        private final LabelWrapper end;
        private final LabelWrapper handlerLabel;
        private final LabelWrapper start;

        public ExceptionHandler(LabelWrapper labelWrapper, LabelWrapper labelWrapper2, LabelWrapper labelWrapper3) {
            this.start = labelWrapper;
            this.end = labelWrapper2;
            this.handlerLabel = labelWrapper3;
        }
    }

    private static class ControlFlowEdge {
        private final boolean isExceptional;
        private final int outputStackSize;
        private final LabelWrapper successor;

        public ControlFlowEdge(LabelWrapper labelWrapper, int i, boolean z) {
            this.successor = labelWrapper;
            this.outputStackSize = i;
            this.isExceptional = z;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class LabelWrapper {
        private final Label label;
        private LabelWrapper nextLabel = null;
        private final Collection<ControlFlowEdge> successors = new LinkedList();
        private int outputStackMax = 0;
        private int inputStackSize = 0;

        public LabelWrapper(Label label) {
            this.label = label;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void addSuccessor(LabelWrapper labelWrapper, int i, boolean z) {
            this.successors.add(new ControlFlowEdge(labelWrapper, i, z));
        }
    }

    private LabelWrapper getLabelWrapper(final Label label) {
        return this.labelWrappersTable.getOrCreate(label, new Function0() { // from class: org.jetbrains.kotlin.codegen.inline.MaxStackFrameSizeAndLocalsCalculator$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return MaxStackFrameSizeAndLocalsCalculator.lambda$getLabelWrapper$0(label);
            }
        });
    }

    static /* synthetic */ LabelWrapper lambda$getLabelWrapper$0(Label label) {
        return new LabelWrapper(label);
    }

    private void increaseStackSize(int i) {
        updateStackSize(this.stackSize + i);
    }

    private void updateStackSize(int i) {
        if (i > this.maxStackSize) {
            this.maxStackSize = i;
        }
        this.stackSize = i;
    }

    private void addSuccessor(LabelWrapper labelWrapper, int i) {
        this.currentBlock.addSuccessor(labelWrapper, i, false);
    }

    private void noSuccessor() {
        LabelWrapper labelWrapper = this.currentBlock;
        if (labelWrapper != null) {
            labelWrapper.outputStackMax = this.maxStackSize;
            this.currentBlock = null;
        }
    }
}
