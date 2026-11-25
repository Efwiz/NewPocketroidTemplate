package org.jetbrains.kotlin.codegen.inline;

import org.jetbrains.kotlin.codegen.StackValue;
import org.jetbrains.kotlin.load.java.JvmAnnotationNames;
import org.jetbrains.org.objectweb.asm.Label;
import org.jetbrains.org.objectweb.asm.MethodVisitor;
import org.jetbrains.org.objectweb.asm.commons.InstructionAdapter;
import org.jetbrains.org.objectweb.asm.tree.FieldInsnNode;

/* JADX INFO: loaded from: classes3.dex */
public class RemapVisitor extends SkipMaxAndEndVisitor {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final InstructionAdapter instructionAdapter;
    private final FieldRemapper nodeRemapper;
    private final LocalVarRemapper remapper;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        switch (i) {
            case 1:
                objArr[0] = "remapper";
                break;
            case 2:
                objArr[0] = "nodeRemapper";
                break;
            case 3:
            case 8:
                objArr[0] = "name";
                break;
            case 4:
            case 9:
                objArr[0] = "desc";
                break;
            case 5:
                objArr[0] = "start";
                break;
            case 6:
                objArr[0] = "end";
                break;
            case 7:
                objArr[0] = "owner";
                break;
            default:
                objArr[0] = JvmAnnotationNames.METADATA_VERSION_FIELD_NAME;
                break;
        }
        objArr[1] = "org/jetbrains/kotlin/codegen/inline/RemapVisitor";
        switch (i) {
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[2] = "visitLocalVariable";
                break;
            case 7:
            case 8:
            case 9:
                objArr[2] = "visitFieldInsn";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RemapVisitor(MethodVisitor methodVisitor, LocalVarRemapper localVarRemapper, FieldRemapper fieldRemapper) {
        super(methodVisitor);
        if (methodVisitor == null) {
            $$$reportNull$$$0(0);
        }
        if (localVarRemapper == null) {
            $$$reportNull$$$0(1);
        }
        if (fieldRemapper == null) {
            $$$reportNull$$$0(2);
        }
        this.instructionAdapter = new InstructionAdapter(methodVisitor);
        this.remapper = localVarRemapper;
        this.nodeRemapper = fieldRemapper;
    }

    @Override // org.jetbrains.org.objectweb.asm.commons.InstructionAdapter, org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitIincInsn(int i, int i2) {
        this.remapper.visitIincInsn(i, i2, this.mv);
    }

    @Override // org.jetbrains.org.objectweb.asm.commons.InstructionAdapter, org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitVarInsn(int i, int i2) {
        this.remapper.visitVarInsn(i, i2, this.instructionAdapter);
    }

    @Override // org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitLocalVariable(String str, String str2, String str3, Label label, Label label2, int i) {
        if (str == null) {
            $$$reportNull$$$0(3);
        }
        if (str2 == null) {
            $$$reportNull$$$0(4);
        }
        if (label == null) {
            $$$reportNull$$$0(5);
        }
        if (label2 == null) {
            $$$reportNull$$$0(6);
        }
        this.remapper.visitLocalVariable(str, str2, str3, label, label2, i, this.mv);
    }

    @Override // org.jetbrains.org.objectweb.asm.commons.InstructionAdapter, org.jetbrains.org.objectweb.asm.MethodVisitor
    public void visitFieldInsn(int i, String str, String str2, String str3) {
        if (str == null) {
            $$$reportNull$$$0(7);
        }
        if (str2 == null) {
            $$$reportNull$$$0(8);
        }
        if (str3 == null) {
            $$$reportNull$$$0(9);
        }
        if (str2.startsWith(InlineCodegenUtilsKt.CAPTURED_FIELD_FOLD_PREFIX)) {
            FieldRemapper fieldRemapper = this.nodeRemapper;
            if ((fieldRemapper instanceof RegeneratedLambdaFieldRemapper) || fieldRemapper.getIsRoot()) {
                StackValue fieldForInline = this.nodeRemapper.getFieldForInline(new FieldInsnNode(i, str, str2, str3), null);
                if (179 == i) {
                    fieldForInline.store(StackValue.onStack(fieldForInline.type, fieldForInline.kotlinType), this);
                    return;
                } else {
                    fieldForInline.put(fieldForInline.type, fieldForInline.kotlinType, this);
                    return;
                }
            }
        }
        super.visitFieldInsn(i, str, str2, str3);
    }
}
