package org.jetbrains.kotlin.codegen.inline;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;

/* JADX INFO: loaded from: classes3.dex */
public class InlineAdapter extends InstructionAdapter {
    private final List<CatchBlock> blocks;
    private boolean isLambdaInlining;
    private int nextLocalIndex;
    private int nextLocalIndexBeforeInline;
    private final SourceMapCopier sourceMapper;

    /* JADX WARN: Code duplicated, block: B:11:0x0021  */
    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "sourceMapper";
        } else if (i == 2) {
            objArr[0] = "start";
        } else if (i == 3) {
            objArr[0] = "end";
        } else if (i == 4) {
            objArr[0] = "handler";
        } else if (i != 5) {
            objArr[0] = JvmAnnotationNames.METADATA_VERSION_FIELD_NAME;
        } else {
            objArr[0] = "start";
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/inline/InlineAdapter";
        if (i == 2 || i == 3 || i == 4) {
            objArr[2] = "visitTryCatchBlock";
        } else if (i != 5) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "visitLineNumber";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public InlineAdapter(MethodVisitor methodVisitor, int i, SourceMapCopier sourceMapCopier) {
        super(589824, methodVisitor);
        if (methodVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (sourceMapCopier == null) {
            $$$reportNull$$$0(1);
        }
        this.blocks = new ArrayList();
        this.isLambdaInlining = false;
        this.nextLocalIndexBeforeInline = -1;
        this.nextLocalIndex = i;
        this.sourceMapper = sourceMapCopier;
    }

    @Override // org.jetbrains.org.objectweb.asm.commons.InstructionAdapter, org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitIincInsn(int i, int i2) {
        super.visitIincInsn(i, i2);
        updateIndex(i, 1);
    }

    @Override // org.jetbrains.org.objectweb.asm.commons.InstructionAdapter, org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitVarInsn(int i, int i2) {
        super.visitVarInsn(i, i2);
        updateIndex(i2, InlineCodegenUtilsKt.getLoadStoreArgSize(i));
    }

    private void updateIndex(int i, int i2) {
        int i3 = i + i2;
        if (i3 > this.nextLocalIndex) {
            this.nextLocalIndex = i3;
        }
    }

    public int getNextLocalIndex() {
        return this.nextLocalIndex;
    }

    public void setLambdaInlining(boolean z) {
        this.isLambdaInlining = z;
        if (z) {
            this.nextLocalIndexBeforeInline = this.nextLocalIndex;
        } else {
            this.nextLocalIndex = this.nextLocalIndexBeforeInline;
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitTryCatchBlock(Label label, Label label2, Label label3, String str) {
        if (label == null) {
            $$$reportNull$$$0(2);
        }
        if (label2 == null) {
            $$$reportNull$$$0(3);
        }
        if (label3 == null) {
            $$$reportNull$$$0(4);
        }
        if (!this.isLambdaInlining) {
            this.blocks.add(new CatchBlock(label, label2, label3, str));
        } else {
            super.visitTryCatchBlock(label, label2, label3, str);
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitLineNumber(int i, Label label) {
        if (label == null) {
            $$$reportNull$$$0(5);
        }
        int iMapLineNumber = this.sourceMapper.mapLineNumber(i);
        if (iMapLineNumber >= 0) {
            super.visitLineNumber(iMapLineNumber, label);
        }
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitMaxs(int i, int i2) {
        for (CatchBlock catchBlock : this.blocks) {
            super.visitTryCatchBlock(catchBlock.start, catchBlock.end, catchBlock.handler, catchBlock.type);
        }
        super.visitMaxs(i, i2);
    }

    private static class CatchBlock {
        private final Label end;
        private final Label handler;
        private final Label start;
        private final String type;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "end";
            } else if (i != 2) {
                objArr[0] = "start";
            } else {
                objArr[0] = "handler";
            }
            objArr[1] = "org/jetbrains/kotlin/codegen/inline/InlineAdapter$CatchBlock";
            objArr[2] = "<init>";
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        public CatchBlock(Label label, Label label2, Label label3, String str) {
            if (label == null) {
                $$$reportNull$$$0(0);
            }
            if (label2 == null) {
                $$$reportNull$$$0(1);
            }
            if (label3 == null) {
                $$$reportNull$$$0(2);
            }
            this.start = label;
            this.end = label2;
            this.handler = label3;
            this.type = str;
        }
    }
}
