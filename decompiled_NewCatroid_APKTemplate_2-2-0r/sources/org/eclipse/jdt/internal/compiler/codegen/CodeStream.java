package org.eclipse.jdt.internal.compiler.codegen;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.ExplicitConstructorCall;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FunctionalExpression;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.ReferenceExpression;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.lookup.ArrayBinding;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.IntersectionTypeBinding18;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.NestedTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticArgumentBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.problem.AbortMethod;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.jetbrains.kotlin.backend.common.serialization.mangle.MangleConstant;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: loaded from: classes14.dex */
public class CodeStream {
    public static final int LABELS_INCREMENT = 5;
    public static final int LOCALS_INCREMENT = 10;
    static final int L_CANNOT_OPTIMIZE = 4;
    static final int L_OPTIMIZABLE = 2;
    static final int L_UNKNOWN = 0;
    public int allLocalsCounter;
    public byte[] bCodeStream;
    public ClassFile classFile;
    public int classFileOffset;
    public ConstantPool constantPool;
    public int countLabels;
    public int exceptionLabelsCounter;
    public int generateAttributes;
    public LambdaExpression lambdaExpression;
    public int lastAbruptCompletion;
    public int lastEntryPC;
    public int lineNumberEnd;
    public int lineNumberStart;
    public int[] lineSeparatorPositions;
    public int maxFieldCount;
    public int maxLocals;
    public AbstractMethodDeclaration methodDeclaration;
    public int pcToSourceMapSize;
    public int position;
    public boolean preserveUnusedLocals;
    public int stackDepth;
    public int stackMax;
    public int startingClassFileOffset;
    protected long targetLevel;
    int visibleLocalsCount;
    public static FieldBinding[] ImplicitThis = new FieldBinding[0];
    public static final CompilationResult RESTART_IN_WIDE_MODE = new CompilationResult((char[]) null, 0, 0, 0);
    public static final CompilationResult RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE = new CompilationResult((char[]) null, 0, 0, 0);
    public ExceptionLabel[] exceptionLabels = new ExceptionLabel[5];
    public BranchLabel[] labels = new BranchLabel[5];
    public LocalVariableBinding[] locals = new LocalVariableBinding[10];
    public int[] pcToSourceMap = new int[24];
    public LocalVariableBinding[] visibleLocals = new LocalVariableBinding[10];
    public boolean wideMode = false;

    public void addVariable(LocalVariableBinding localVariableBinding) {
    }

    public void recordExpressionType(TypeBinding typeBinding) {
    }

    public void resetForCodeGenUnusedLocals() {
    }

    public CodeStream(ClassFile classFile) {
        this.targetLevel = classFile.targetJDK;
        this.generateAttributes = classFile.produceAttributes;
        if ((classFile.produceAttributes & 2) != 0) {
            this.lineSeparatorPositions = classFile.referenceBinding.scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions();
        }
    }

    public static int insertionIndex(int[] iArr, int i, int i2) {
        int i3 = i - 2;
        int i4 = 0;
        int i5 = 0;
        while (i4 <= i3) {
            i5 = (i4 + i3) / 2;
            if ((i5 & 1) != 0) {
                i5--;
            }
            int i6 = iArr[i5];
            if (i2 < i6) {
                i3 = i5 - 2;
            } else {
                if (i2 <= i6) {
                    return -1;
                }
                i4 = i5 + 2;
            }
        }
        return i2 < iArr[i5] ? i5 : i5 + 2;
    }

    public static final void sort(int[] iArr, int i, int i2, int[] iArr2) {
        if (i2 > i) {
            int i3 = iArr[((i2 - i) / 2) + i];
            int i4 = i;
            int i5 = i2;
            while (i4 <= i5) {
                while (i4 < i2 && iArr[i4] < i3) {
                    i4++;
                }
                while (i5 > i && iArr[i5] > i3) {
                    i5--;
                }
                if (i4 <= i5) {
                    swap(iArr, i4, i5, iArr2);
                    i4++;
                    i5--;
                }
            }
            if (i < i5) {
                sort(iArr, i, i5, iArr2);
            }
            if (i4 < i2) {
                sort(iArr, i4, i2, iArr2);
            }
        }
    }

    private static final void swap(int[] iArr, int i, int i2, int[] iArr2) {
        int i3 = iArr[i];
        iArr[i] = iArr[i2];
        iArr[i2] = i3;
        int i4 = iArr2[i2];
        iArr2[i2] = iArr2[i];
        iArr2[i] = i4;
    }

    public void aaload() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 50;
    }

    public void aastore() {
        this.countLabels = 0;
        this.stackDepth -= 3;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 83;
    }

    public void aconst_null() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 1;
    }

    public void addDefinitelyAssignedVariables(Scope scope, int i) {
        if ((this.generateAttributes & 28) == 0) {
            return;
        }
        for (int i2 = 0; i2 < this.visibleLocalsCount; i2++) {
            LocalVariableBinding localVariableBinding = this.visibleLocals[i2];
            if (localVariableBinding != null && isDefinitelyAssigned(scope, i, localVariableBinding) && (localVariableBinding.initializationCount == 0 || localVariableBinding.initializationPCs[((localVariableBinding.initializationCount - 1) << 1) + 1] != -1)) {
                localVariableBinding.recordInitializationStartPC(this.position);
            }
        }
    }

    public void addLabel(BranchLabel branchLabel) {
        int i = this.countLabels;
        BranchLabel[] branchLabelArr = this.labels;
        if (i == branchLabelArr.length) {
            BranchLabel[] branchLabelArr2 = new BranchLabel[i + 5];
            this.labels = branchLabelArr2;
            System.arraycopy(branchLabelArr, 0, branchLabelArr2, 0, i);
        }
        BranchLabel[] branchLabelArr3 = this.labels;
        int i2 = this.countLabels;
        this.countLabels = i2 + 1;
        branchLabelArr3[i2] = branchLabel;
    }

    public void addVisibleLocalVariable(LocalVariableBinding localVariableBinding) {
        if ((this.generateAttributes & 28) == 0) {
            return;
        }
        int i = this.visibleLocalsCount;
        LocalVariableBinding[] localVariableBindingArr = this.visibleLocals;
        if (i >= localVariableBindingArr.length) {
            LocalVariableBinding[] localVariableBindingArr2 = new LocalVariableBinding[i * 2];
            this.visibleLocals = localVariableBindingArr2;
            System.arraycopy(localVariableBindingArr, 0, localVariableBindingArr2, 0, i);
        }
        LocalVariableBinding[] localVariableBindingArr3 = this.visibleLocals;
        int i2 = this.visibleLocalsCount;
        this.visibleLocalsCount = i2 + 1;
        localVariableBindingArr3[i2] = localVariableBinding;
    }

    public void aload(int i) {
        this.countLabels = 0;
        int i2 = this.stackDepth + 1;
        this.stackDepth = i2;
        if (i2 > this.stackMax) {
            this.stackMax = i2;
        }
        if (this.maxLocals <= i) {
            this.maxLocals = i + 1;
        }
        if (i > 255) {
            if (this.classFileOffset + 3 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position += 2;
            byte[] bArr = this.bCodeStream;
            int i3 = this.classFileOffset;
            int i4 = i3 + 1;
            this.classFileOffset = i4;
            bArr[i3] = Opcodes.OPC_wide;
            this.classFileOffset = i3 + 2;
            bArr[i4] = 25;
            writeUnsignedShort(i);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i5 = this.classFileOffset;
        int i6 = i5 + 1;
        this.classFileOffset = i6;
        bArr2[i5] = 25;
        this.classFileOffset = i5 + 2;
        bArr2[i6] = (byte) i;
    }

    public void aload_0() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.maxLocals == 0) {
            this.maxLocals = 1;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 42;
    }

    public void aload_1() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.maxLocals <= 1) {
            this.maxLocals = 2;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 43;
    }

    public void aload_2() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.maxLocals <= 2) {
            this.maxLocals = 3;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 44;
    }

    public void aload_3() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.maxLocals <= 3) {
            this.maxLocals = 4;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 45;
    }

    public void anewarray(TypeBinding typeBinding) {
        this.countLabels = 0;
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_anewarray;
        writeUnsignedShort(this.constantPool.literalIndexForType(typeBinding));
    }

    public void areturn() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        int i = this.position + 1;
        this.position = i;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = -80;
        this.lastAbruptCompletion = i;
    }

    public void arrayAt(int i) {
        switch (i) {
            case 2:
                caload();
                break;
            case 3:
            case 5:
                baload();
                break;
            case 4:
                saload();
                break;
            case 6:
            default:
                aaload();
                break;
            case 7:
                laload();
                break;
            case 8:
                daload();
                break;
            case 9:
                faload();
                break;
            case 10:
                iaload();
                break;
        }
    }

    public void arrayAtPut(int i, boolean z) {
        switch (i) {
            case 2:
                if (z) {
                    dup_x2();
                }
                castore();
                break;
            case 3:
            case 5:
                if (z) {
                    dup_x2();
                }
                bastore();
                break;
            case 4:
                if (z) {
                    dup_x2();
                }
                sastore();
                break;
            case 6:
            default:
                if (z) {
                    dup_x2();
                }
                aastore();
                break;
            case 7:
                if (z) {
                    dup2_x2();
                }
                lastore();
                break;
            case 8:
                if (z) {
                    dup2_x2();
                }
                dastore();
                break;
            case 9:
                if (z) {
                    dup_x2();
                }
                fastore();
                break;
            case 10:
                if (z) {
                    dup_x2();
                }
                iastore();
                break;
        }
    }

    public void arraylength() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_arraylength;
    }

    public void astore(int i) {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals <= i) {
            this.maxLocals = i + 1;
        }
        if (i > 255) {
            if (this.classFileOffset + 3 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position += 2;
            byte[] bArr = this.bCodeStream;
            int i2 = this.classFileOffset;
            int i3 = i2 + 1;
            this.classFileOffset = i3;
            bArr[i2] = Opcodes.OPC_wide;
            this.classFileOffset = i2 + 2;
            bArr[i3] = 58;
            writeUnsignedShort(i);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i4 = this.classFileOffset;
        int i5 = i4 + 1;
        this.classFileOffset = i5;
        bArr2[i4] = 58;
        this.classFileOffset = i4 + 2;
        bArr2[i5] = (byte) i;
    }

    public void astore_0() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals == 0) {
            this.maxLocals = 1;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 75;
    }

    public void astore_1() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals <= 1) {
            this.maxLocals = 2;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 76;
    }

    public void astore_2() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals <= 2) {
            this.maxLocals = 3;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 77;
    }

    public void astore_3() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals <= 3) {
            this.maxLocals = 4;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 78;
    }

    public void athrow() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        int i = this.position + 1;
        this.position = i;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = -65;
        this.lastAbruptCompletion = i;
    }

    public void baload() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 51;
    }

    public void bastore() {
        this.countLabels = 0;
        this.stackDepth -= 3;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 84;
    }

    public void bipush(byte b) {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        int i3 = i2 + 1;
        this.classFileOffset = i3;
        bArr[i2] = 16;
        this.classFileOffset = i2 + 2;
        bArr[i3] = b;
    }

    public void caload() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 52;
    }

    public void castore() {
        this.countLabels = 0;
        this.stackDepth -= 3;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 85;
    }

    public void checkcast(int i) {
        this.countLabels = 0;
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = -64;
        switch (i) {
            case 2:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangCharacterConstantPoolName));
                break;
            case 3:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangByteConstantPoolName));
                break;
            case 4:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangShortConstantPoolName));
                break;
            case 5:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangBooleanConstantPoolName));
                break;
            case 7:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangLongConstantPoolName));
                break;
            case 8:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangDoubleConstantPoolName));
                break;
            case 9:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangFloatConstantPoolName));
                break;
            case 10:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangIntegerConstantPoolName));
                break;
        }
    }

    public void checkcast(TypeBinding typeBinding) {
        checkcast(null, typeBinding, -1);
    }

    public void checkcast(TypeReference typeReference, TypeBinding typeBinding, int i) {
        this.countLabels = 0;
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = -64;
        writeUnsignedShort(this.constantPool.literalIndexForType(typeBinding));
    }

    public void d2f() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = -112;
    }

    public void d2i() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_d2i;
    }

    public void d2l() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_d2l;
    }

    public void dadd() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 99;
    }

    public void daload() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 49;
    }

    public void dastore() {
        this.countLabels = 0;
        this.stackDepth -= 4;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 82;
    }

    public void dcmpg() {
        this.countLabels = 0;
        this.stackDepth -= 3;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_dcmpg;
    }

    public void dcmpl() {
        this.countLabels = 0;
        this.stackDepth -= 3;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_dcmpl;
    }

    public void dconst_0() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 14;
    }

    public void dconst_1() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 15;
    }

    public void ddiv() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 111;
    }

    public void decrStackSize(int i) {
        this.stackDepth -= i;
    }

    public void dload(int i) {
        this.countLabels = 0;
        int i2 = this.stackDepth + 2;
        this.stackDepth = i2;
        if (i2 > this.stackMax) {
            this.stackMax = i2;
        }
        int i3 = i + 2;
        if (this.maxLocals < i3) {
            this.maxLocals = i3;
        }
        if (i > 255) {
            if (this.classFileOffset + 3 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position += 2;
            byte[] bArr = this.bCodeStream;
            int i4 = this.classFileOffset;
            int i5 = i4 + 1;
            this.classFileOffset = i5;
            bArr[i4] = Opcodes.OPC_wide;
            this.classFileOffset = i4 + 2;
            bArr[i5] = 24;
            writeUnsignedShort(i);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i6 = this.classFileOffset;
        int i7 = i6 + 1;
        this.classFileOffset = i7;
        bArr2[i6] = 24;
        this.classFileOffset = i6 + 2;
        bArr2[i7] = (byte) i;
    }

    public void dload_0() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.maxLocals < 2) {
            this.maxLocals = 2;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 38;
    }

    public void dload_1() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.maxLocals < 3) {
            this.maxLocals = 3;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 39;
    }

    public void dload_2() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.maxLocals < 4) {
            this.maxLocals = 4;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 40;
    }

    public void dload_3() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.maxLocals < 5) {
            this.maxLocals = 5;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 41;
    }

    public void dmul() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 107;
    }

    public void dneg() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 119;
    }

    public void drem() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 115;
    }

    public void dreturn() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        int i = this.position + 1;
        this.position = i;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = Opcodes.OPC_dreturn;
        this.lastAbruptCompletion = i;
    }

    public void dstore(int i) {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.maxLocals <= i + 1) {
            this.maxLocals = i + 2;
        }
        if (i > 255) {
            if (this.classFileOffset + 3 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position += 2;
            byte[] bArr = this.bCodeStream;
            int i2 = this.classFileOffset;
            int i3 = i2 + 1;
            this.classFileOffset = i3;
            bArr[i2] = Opcodes.OPC_wide;
            this.classFileOffset = i2 + 2;
            bArr[i3] = 57;
            writeUnsignedShort(i);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i4 = this.classFileOffset;
        int i5 = i4 + 1;
        this.classFileOffset = i5;
        bArr2[i4] = 57;
        this.classFileOffset = i4 + 2;
        bArr2[i5] = (byte) i;
    }

    public void dstore_0() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.maxLocals < 2) {
            this.maxLocals = 2;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 71;
    }

    public void dstore_1() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.maxLocals < 3) {
            this.maxLocals = 3;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 72;
    }

    public void dstore_2() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.maxLocals < 4) {
            this.maxLocals = 4;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 73;
    }

    public void dstore_3() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.maxLocals < 5) {
            this.maxLocals = 5;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 74;
    }

    public void dsub() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 103;
    }

    public void dup() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 89;
    }

    public void dup_x1() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 90;
    }

    public void dup_x2() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 91;
    }

    public void dup2() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 92;
    }

    public void dup2_x1() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 93;
    }

    public void dup2_x2() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 94;
    }

    public void exitUserScope(BlockScope blockScope) {
        if ((this.generateAttributes & 28) == 0) {
            return;
        }
        int i = this.visibleLocalsCount - 1;
        while (i >= 0) {
            LocalVariableBinding localVariableBinding = this.visibleLocals[i];
            if (localVariableBinding == null || localVariableBinding.declaringScope != blockScope) {
                i--;
            } else {
                if (localVariableBinding.initializationCount > 0) {
                    localVariableBinding.recordInitializationEndPC(this.position);
                }
                this.visibleLocals[i] = null;
                i--;
            }
        }
    }

    public void exitUserScope(BlockScope blockScope, LocalVariableBinding localVariableBinding) {
        if ((this.generateAttributes & 28) == 0) {
            return;
        }
        int i = this.visibleLocalsCount - 1;
        while (i >= 0) {
            LocalVariableBinding localVariableBinding2 = this.visibleLocals[i];
            if (localVariableBinding2 == null || localVariableBinding2.declaringScope != blockScope || localVariableBinding2 == localVariableBinding) {
                i--;
            } else {
                if (localVariableBinding2.initializationCount > 0) {
                    localVariableBinding2.recordInitializationEndPC(this.position);
                }
                this.visibleLocals[i] = null;
                i--;
            }
        }
    }

    public void f2d() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = Opcodes.OPC_f2d;
    }

    public void f2i() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_f2i;
    }

    public void f2l() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = Opcodes.OPC_f2l;
    }

    public void fadd() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 98;
    }

    public void faload() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 48;
    }

    public void fastore() {
        this.countLabels = 0;
        this.stackDepth -= 3;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 81;
    }

    public void fcmpg() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_fcmpg;
    }

    public void fcmpl() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_fcmpl;
    }

    public void fconst_0() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 11;
    }

    public void fconst_1() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 12;
    }

    public void fconst_2() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 13;
    }

    public void fdiv() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 110;
    }

    public void fieldAccess(byte b, FieldBinding fieldBinding, TypeBinding typeBinding) {
        if (typeBinding == null) {
            typeBinding = fieldBinding.declaringClass;
        }
        if ((typeBinding.tagBits & 2048) != 0) {
            Util.recordNestedType(this.classFile, typeBinding);
        }
        TypeBinding typeBinding2 = fieldBinding.type;
        int i = typeBinding2.f626id;
        fieldAccess(b, (i == 7 || i == 8) ? 2 : 1, typeBinding.constantPoolName(), fieldBinding.f628name, typeBinding2.signature());
    }

    private void fieldAccess(byte b, int i, char[] cArr, char[] cArr2, char[] cArr3) {
        this.countLabels = 0;
        switch (b) {
            case -78:
                if (i == 2) {
                    this.stackDepth += 2;
                } else {
                    this.stackDepth++;
                }
                break;
            case -77:
                if (i == 2) {
                    this.stackDepth -= 2;
                } else {
                    this.stackDepth--;
                }
                break;
            case -76:
                if (i == 2) {
                    this.stackDepth++;
                }
                break;
            case -75:
                if (i == 2) {
                    this.stackDepth -= 3;
                } else {
                    this.stackDepth -= 2;
                }
                break;
        }
        int i2 = this.stackDepth;
        if (i2 > this.stackMax) {
            this.stackMax = i2;
        }
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i3 = this.classFileOffset;
        this.classFileOffset = i3 + 1;
        bArr[i3] = b;
        writeUnsignedShort(this.constantPool.literalIndexForField(cArr, cArr2, cArr3));
    }

    public void fload(int i) {
        this.countLabels = 0;
        int i2 = this.stackDepth + 1;
        this.stackDepth = i2;
        if (this.maxLocals <= i) {
            this.maxLocals = i + 1;
        }
        if (i2 > this.stackMax) {
            this.stackMax = i2;
        }
        if (i > 255) {
            if (this.classFileOffset + 3 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position += 2;
            byte[] bArr = this.bCodeStream;
            int i3 = this.classFileOffset;
            int i4 = i3 + 1;
            this.classFileOffset = i4;
            bArr[i3] = Opcodes.OPC_wide;
            this.classFileOffset = i3 + 2;
            bArr[i4] = 23;
            writeUnsignedShort(i);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i5 = this.classFileOffset;
        int i6 = i5 + 1;
        this.classFileOffset = i6;
        bArr2[i5] = 23;
        this.classFileOffset = i5 + 2;
        bArr2[i6] = (byte) i;
    }

    public void fload_0() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (this.maxLocals == 0) {
            this.maxLocals = 1;
        }
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 34;
    }

    public void fload_1() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (this.maxLocals <= 1) {
            this.maxLocals = 2;
        }
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 35;
    }

    public void fload_2() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (this.maxLocals <= 2) {
            this.maxLocals = 3;
        }
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 36;
    }

    public void fload_3() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (this.maxLocals <= 3) {
            this.maxLocals = 4;
        }
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 37;
    }

    public void fmul() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 106;
    }

    public void fneg() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 118;
    }

    public void frem() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 114;
    }

    public void freturn() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        int i = this.position + 1;
        this.position = i;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = Opcodes.OPC_freturn;
        this.lastAbruptCompletion = i;
    }

    public void fstore(int i) {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals <= i) {
            this.maxLocals = i + 1;
        }
        if (i > 255) {
            if (this.classFileOffset + 3 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position += 2;
            byte[] bArr = this.bCodeStream;
            int i2 = this.classFileOffset;
            int i3 = i2 + 1;
            this.classFileOffset = i3;
            bArr[i2] = Opcodes.OPC_wide;
            this.classFileOffset = i2 + 2;
            bArr[i3] = 56;
            writeUnsignedShort(i);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i4 = this.classFileOffset;
        int i5 = i4 + 1;
        this.classFileOffset = i5;
        bArr2[i4] = 56;
        this.classFileOffset = i4 + 2;
        bArr2[i5] = (byte) i;
    }

    public void fstore_0() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals == 0) {
            this.maxLocals = 1;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 67;
    }

    public void fstore_1() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals <= 1) {
            this.maxLocals = 2;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 68;
    }

    public void fstore_2() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals <= 2) {
            this.maxLocals = 3;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 69;
    }

    public void fstore_3() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals <= 3) {
            this.maxLocals = 4;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 70;
    }

    public void fsub() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 102;
    }

    public void generateBoxingConversion(int i) {
        switch (i) {
            case 2:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    invoke(Opcodes.OPC_invokestatic, 1, 1, ConstantPool.JavaLangCharacterConstantPoolName, ConstantPool.ValueOf, ConstantPool.charCharacterSignature);
                } else {
                    newWrapperFor(i);
                    dup_x1();
                    swap();
                    invoke(Opcodes.OPC_invokespecial, 2, 0, ConstantPool.JavaLangCharacterConstantPoolName, ConstantPool.Init, ConstantPool.CharConstrSignature);
                }
                break;
            case 3:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    invoke(Opcodes.OPC_invokestatic, 1, 1, ConstantPool.JavaLangByteConstantPoolName, ConstantPool.ValueOf, ConstantPool.byteByteSignature);
                } else {
                    newWrapperFor(i);
                    dup_x1();
                    swap();
                    invoke(Opcodes.OPC_invokespecial, 2, 0, ConstantPool.JavaLangByteConstantPoolName, ConstantPool.Init, ConstantPool.ByteConstrSignature);
                }
                break;
            case 4:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    invoke(Opcodes.OPC_invokestatic, 1, 1, ConstantPool.JavaLangShortConstantPoolName, ConstantPool.ValueOf, ConstantPool.shortShortSignature);
                } else {
                    newWrapperFor(i);
                    dup_x1();
                    swap();
                    invoke(Opcodes.OPC_invokespecial, 2, 0, ConstantPool.JavaLangShortConstantPoolName, ConstantPool.Init, ConstantPool.ShortConstrSignature);
                }
                break;
            case 5:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    invoke(Opcodes.OPC_invokestatic, 1, 1, ConstantPool.JavaLangBooleanConstantPoolName, ConstantPool.ValueOf, ConstantPool.booleanBooleanSignature);
                } else {
                    newWrapperFor(i);
                    dup_x1();
                    swap();
                    invoke(Opcodes.OPC_invokespecial, 2, 0, ConstantPool.JavaLangBooleanConstantPoolName, ConstantPool.Init, ConstantPool.BooleanConstrSignature);
                }
                break;
            case 7:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    invoke(Opcodes.OPC_invokestatic, 2, 1, ConstantPool.JavaLangLongConstantPoolName, ConstantPool.ValueOf, ConstantPool.longLongSignature);
                } else {
                    newWrapperFor(i);
                    dup_x2();
                    dup_x2();
                    pop();
                    invoke(Opcodes.OPC_invokespecial, 3, 0, ConstantPool.JavaLangLongConstantPoolName, ConstantPool.Init, ConstantPool.LongConstrSignature);
                }
                break;
            case 8:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    invoke(Opcodes.OPC_invokestatic, 2, 1, ConstantPool.JavaLangDoubleConstantPoolName, ConstantPool.ValueOf, ConstantPool.doubleDoubleSignature);
                } else {
                    newWrapperFor(i);
                    dup_x2();
                    dup_x2();
                    pop();
                    invoke(Opcodes.OPC_invokespecial, 3, 0, ConstantPool.JavaLangDoubleConstantPoolName, ConstantPool.Init, ConstantPool.DoubleConstrSignature);
                }
                break;
            case 9:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    invoke(Opcodes.OPC_invokestatic, 1, 1, ConstantPool.JavaLangFloatConstantPoolName, ConstantPool.ValueOf, ConstantPool.floatFloatSignature);
                } else {
                    newWrapperFor(i);
                    dup_x1();
                    swap();
                    invoke(Opcodes.OPC_invokespecial, 2, 0, ConstantPool.JavaLangFloatConstantPoolName, ConstantPool.Init, ConstantPool.FloatConstrSignature);
                }
                break;
            case 10:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    invoke(Opcodes.OPC_invokestatic, 1, 1, ConstantPool.JavaLangIntegerConstantPoolName, ConstantPool.ValueOf, ConstantPool.IntIntegerSignature);
                } else {
                    newWrapperFor(i);
                    dup_x1();
                    swap();
                    invoke(Opcodes.OPC_invokespecial, 2, 0, ConstantPool.JavaLangIntegerConstantPoolName, ConstantPool.Init, ConstantPool.IntConstrSignature);
                }
                break;
        }
    }

    public void generateClassLiteralAccessForType(TypeBinding typeBinding, FieldBinding fieldBinding) {
        if (typeBinding.isBaseType() && typeBinding != TypeBinding.NULL) {
            getTYPE(typeBinding.f626id);
            return;
        }
        if (this.targetLevel >= ClassFileConstants.JDK1_5) {
            ldc(typeBinding);
            return;
        }
        BranchLabel branchLabel = new BranchLabel(this);
        if (fieldBinding != null) {
            fieldAccess(Opcodes.OPC_getstatic, fieldBinding, null);
            dup();
            ifnonnull(branchLabel);
            pop();
        }
        ExceptionLabel exceptionLabel = new ExceptionLabel(this, TypeBinding.NULL);
        exceptionLabel.placeStart();
        ldc(typeBinding == TypeBinding.NULL ? CommonClassNames.JAVA_LANG_OBJECT : String.valueOf(typeBinding.constantPoolName()).replace('/', '.'));
        invokeClassForName();
        exceptionLabel.placeEnd();
        if (fieldBinding != null) {
            dup();
            fieldAccess(Opcodes.OPC_putstatic, fieldBinding, null);
        }
        goto_(branchLabel);
        int i = this.stackDepth;
        pushExceptionOnStack(TypeBinding.NULL);
        exceptionLabel.place();
        newNoClassDefFoundError();
        dup_x1();
        swap();
        invokeThrowableGetMessage();
        invokeNoClassDefFoundErrorStringConstructor();
        athrow();
        branchLabel.place();
        this.stackDepth = i;
    }

    public final void generateCodeAttributeForProblemMethod(String str) {
        newJavaLangError();
        dup();
        ldc(str);
        invokeJavaLangErrorConstructor();
        athrow();
    }

    public void generateConstant(Constant constant, int i) {
        int iTypeID = (i & 255) >> 4;
        if (iTypeID == 0) {
            iTypeID = constant.typeID();
        }
        switch (iTypeID) {
            case 2:
                generateInlinedValue(constant.charValue());
                break;
            case 3:
                generateInlinedValue(constant.byteValue());
                break;
            case 4:
                generateInlinedValue(constant.shortValue());
                break;
            case 5:
                generateInlinedValue(constant.booleanValue());
                break;
            case 7:
                generateInlinedValue(constant.longValue());
                break;
            case 8:
                generateInlinedValue(constant.doubleValue());
                break;
            case 9:
                generateInlinedValue(constant.floatValue());
                break;
            case 10:
                generateInlinedValue(constant.intValue());
                break;
            case 11:
                ldc(constant.stringValue());
                break;
        }
        if ((i & 512) != 0) {
            generateBoxingConversion(iTypeID);
        }
    }

    public void generateEmulatedReadAccessForField(FieldBinding fieldBinding) {
        generateEmulationForField(fieldBinding);
        swap();
        invokeJavaLangReflectFieldGetter(fieldBinding.type.f626id);
        if (fieldBinding.type.isBaseType()) {
            return;
        }
        checkcast(fieldBinding.type);
    }

    public void generateEmulatedWriteAccessForField(FieldBinding fieldBinding) {
        invokeJavaLangReflectFieldSetter(fieldBinding.type.f626id);
    }

    public void generateEmulationForConstructor(Scope scope, MethodBinding methodBinding) {
        ldc(String.valueOf(methodBinding.declaringClass.constantPoolName()).replace('/', '.'));
        invokeClassForName();
        int length = methodBinding.parameters.length;
        generateInlinedValue(length);
        newArray(scope.createArrayType(scope.getType(TypeConstants.JAVA_LANG_CLASS, 3), 1));
        if (length > 0) {
            dup();
            for (int i = 0; i < length; i++) {
                generateInlinedValue(i);
                TypeBinding typeBinding = methodBinding.parameters[i];
                if (typeBinding.isBaseType()) {
                    getTYPE(typeBinding.f626id);
                } else if (typeBinding.isArrayType()) {
                    ArrayBinding arrayBinding = (ArrayBinding) typeBinding;
                    if (arrayBinding.leafComponentType.isBaseType()) {
                        getTYPE(arrayBinding.leafComponentType.f626id);
                    } else {
                        ldc(String.valueOf(arrayBinding.leafComponentType.constantPoolName()).replace('/', '.'));
                        invokeClassForName();
                    }
                    generateInlinedValue(arrayBinding.dimensions);
                    newarray(10);
                    invokeArrayNewInstance();
                    invokeObjectGetClass();
                } else {
                    ldc(String.valueOf(methodBinding.declaringClass.constantPoolName()).replace('/', '.'));
                    invokeClassForName();
                }
                aastore();
                if (i < length - 1) {
                    dup();
                }
            }
        }
        invokeClassGetDeclaredConstructor();
        dup();
        iconst_1();
        invokeAccessibleObjectSetAccessible();
    }

    public void generateEmulationForField(FieldBinding fieldBinding) {
        ldc(String.valueOf(fieldBinding.declaringClass.constantPoolName()).replace('/', '.'));
        invokeClassForName();
        ldc(String.valueOf(fieldBinding.f628name));
        invokeClassGetDeclaredField();
        dup();
        iconst_1();
        invokeAccessibleObjectSetAccessible();
    }

    public void generateEmulationForMethod(Scope scope, MethodBinding methodBinding) {
        ldc(String.valueOf(methodBinding.declaringClass.constantPoolName()).replace('/', '.'));
        invokeClassForName();
        ldc(String.valueOf(methodBinding.selector));
        int length = methodBinding.parameters.length;
        generateInlinedValue(length);
        newArray(scope.createArrayType(scope.getType(TypeConstants.JAVA_LANG_CLASS, 3), 1));
        if (length > 0) {
            dup();
            for (int i = 0; i < length; i++) {
                generateInlinedValue(i);
                TypeBinding typeBinding = methodBinding.parameters[i];
                if (typeBinding.isBaseType()) {
                    getTYPE(typeBinding.f626id);
                } else if (typeBinding.isArrayType()) {
                    ArrayBinding arrayBinding = (ArrayBinding) typeBinding;
                    if (arrayBinding.leafComponentType.isBaseType()) {
                        getTYPE(arrayBinding.leafComponentType.f626id);
                    } else {
                        ldc(String.valueOf(arrayBinding.leafComponentType.constantPoolName()).replace('/', '.'));
                        invokeClassForName();
                    }
                    generateInlinedValue(arrayBinding.dimensions);
                    newarray(10);
                    invokeArrayNewInstance();
                    invokeObjectGetClass();
                } else {
                    ldc(String.valueOf(methodBinding.declaringClass.constantPoolName()).replace('/', '.'));
                    invokeClassForName();
                }
                aastore();
                if (i < length - 1) {
                    dup();
                }
            }
        }
        invokeClassGetDeclaredMethod();
        dup();
        iconst_1();
        invokeAccessibleObjectSetAccessible();
    }

    /* JADX WARN: Code duplicated, block: B:27:0x0044  */
    /* JADX WARN: Code duplicated, block: B:31:0x0061  */
    /* JADX WARN: Code duplicated, block: B:45:0x00ac  */
    /* JADX WARN: Code duplicated, block: B:47:0x00b4  */
    /* JADX WARN: Code duplicated, block: B:48:0x00b8  */
    /* JADX WARN: Code duplicated, block: B:49:0x00c1  */
    public void generateImplicitConversion(int i) {
        if ((i & 1024) != 0) {
            generateUnboxingConversion(i & 15);
        }
        int i2 = i & 255;
        if (i2 == 35 || i2 == 36) {
            i2c();
        } else if (i2 == 49) {
            int i3 = i2 >> 4;
            checkcast(i3);
            generateUnboxingConversion(i3);
        } else if (i2 == 50) {
            i2b();
        } else if (i2 == 137) {
            f2d();
        } else if (i2 == 138) {
            i2d();
        } else {
            switch (i2) {
                case 33:
                case 81:
                case 145:
                case 161:
                    int i4 = i2 >> 4;
                    checkcast(i4);
                    generateUnboxingConversion(i4);
                    break;
                case 39:
                    l2i();
                    i2c();
                    break;
                case 40:
                    d2i();
                    i2c();
                    break;
                case 41:
                    f2i();
                    i2c();
                    break;
                case 42:
                    i2c();
                    break;
                case 52:
                    i2b();
                    break;
                case 135:
                    l2d();
                    break;
                case 146:
                case 147:
                case 148:
                case 154:
                    i2f();
                    break;
                case 151:
                    l2f();
                    break;
                case 152:
                    d2f();
                    break;
                case 167:
                    l2i();
                    break;
                case 168:
                    d2i();
                    break;
                case 169:
                    f2i();
                    break;
                default:
                    switch (i2) {
                        case 55:
                            l2i();
                            i2b();
                            break;
                        case 56:
                            d2i();
                            i2b();
                            break;
                        case 57:
                            f2i();
                            i2b();
                            break;
                        case 58:
                            i2b();
                            break;
                        default:
                            switch (i2) {
                                case 65:
                                    int i5 = i2 >> 4;
                                    checkcast(i5);
                                    generateUnboxingConversion(i5);
                                    break;
                                case 66:
                                case 67:
                                    i2s();
                                    break;
                                default:
                                    switch (i2) {
                                        case 71:
                                            l2i();
                                            i2s();
                                            break;
                                        case 72:
                                            d2i();
                                            i2s();
                                            break;
                                        case 73:
                                            f2i();
                                            i2s();
                                            break;
                                        case 74:
                                            i2s();
                                            break;
                                        default:
                                            switch (i2) {
                                                case 113:
                                                    int i6 = i2 >> 4;
                                                    checkcast(i6);
                                                    generateUnboxingConversion(i6);
                                                    break;
                                                case 114:
                                                case 115:
                                                case 116:
                                                    i2l();
                                                    break;
                                                default:
                                                    switch (i2) {
                                                        case 120:
                                                            d2l();
                                                            break;
                                                        case 121:
                                                            f2l();
                                                            break;
                                                        case 122:
                                                            i2l();
                                                            break;
                                                        default:
                                                            switch (i2) {
                                                                case 129:
                                                                    int i7 = i2 >> 4;
                                                                    checkcast(i7);
                                                                    generateUnboxingConversion(i7);
                                                                    break;
                                                                case 130:
                                                                case 131:
                                                                case 132:
                                                                    i2d();
                                                                    break;
                                                            }
                                                            break;
                                                    }
                                                    break;
                                            }
                                            break;
                                    }
                                    break;
                            }
                            break;
                    }
                    break;
            }
        }
        if ((i & 512) != 0) {
            generateBoxingConversion(i2 >> 4);
        }
    }

    public void generateInlinedValue(boolean z) {
        if (z) {
            iconst_1();
        } else {
            iconst_0();
        }
    }

    public void generateInlinedValue(byte b) {
        switch (b) {
            case -1:
                iconst_m1();
                break;
            case 0:
                iconst_0();
                break;
            case 1:
                iconst_1();
                break;
            case 2:
                iconst_2();
                break;
            case 3:
                iconst_3();
                break;
            case 4:
                iconst_4();
                break;
            case 5:
                iconst_5();
                break;
            default:
                if (-128 <= b && b <= 127) {
                    bipush(b);
                    break;
                }
                break;
        }
    }

    public void generateInlinedValue(char c) {
        if (c == 0) {
            iconst_0();
            return;
        }
        if (c == 1) {
            iconst_1();
            return;
        }
        if (c == 2) {
            iconst_2();
            return;
        }
        if (c == 3) {
            iconst_3();
            return;
        }
        if (c == 4) {
            iconst_4();
            return;
        }
        if (c == 5) {
            iconst_5();
            return;
        }
        if (6 <= c && c <= 127) {
            bipush((byte) c);
        } else if (128 <= c && c <= 32767) {
            sipush(c);
        } else {
            ldc((int) c);
        }
    }

    public void generateInlinedValue(double d) {
        if (d == 0.0d) {
            if (Double.doubleToLongBits(d) != 0) {
                ldc2_w(d);
                return;
            } else {
                dconst_0();
                return;
            }
        }
        if (d == 1.0d) {
            dconst_1();
        } else {
            ldc2_w(d);
        }
    }

    public void generateInlinedValue(float f) {
        if (f == 0.0f) {
            if (Float.floatToIntBits(f) != 0) {
                ldc(f);
                return;
            } else {
                fconst_0();
                return;
            }
        }
        if (f == 1.0f) {
            fconst_1();
        } else if (f == 2.0f) {
            fconst_2();
        } else {
            ldc(f);
        }
    }

    public void generateInlinedValue(int i) {
        switch (i) {
            case -1:
                iconst_m1();
                break;
            case 0:
                iconst_0();
                break;
            case 1:
                iconst_1();
                break;
            case 2:
                iconst_2();
                break;
            case 3:
                iconst_3();
                break;
            case 4:
                iconst_4();
                break;
            case 5:
                iconst_5();
                break;
            default:
                if (-128 <= i && i <= 127) {
                    bipush((byte) i);
                } else if (-32768 <= i && i <= 32767) {
                    sipush(i);
                } else {
                    ldc(i);
                }
                break;
        }
    }

    public void generateInlinedValue(long j) {
        if (j == 0) {
            lconst_0();
        } else if (j == 1) {
            lconst_1();
        } else {
            ldc2_w(j);
        }
    }

    public void generateInlinedValue(short s) {
        switch (s) {
            case -1:
                iconst_m1();
                break;
            case 0:
                iconst_0();
                break;
            case 1:
                iconst_1();
                break;
            case 2:
                iconst_2();
                break;
            case 3:
                iconst_3();
                break;
            case 4:
                iconst_4();
                break;
            case 5:
                iconst_5();
                break;
            default:
                if (-128 <= s && s <= 127) {
                    bipush((byte) s);
                } else {
                    sipush(s);
                }
                break;
        }
    }

    public void generateOuterAccess(Object[] objArr, ASTNode aSTNode, Binding binding, Scope scope) {
        if (objArr == null) {
            if (binding instanceof LocalVariableBinding) {
                scope.problemReporter().needImplementation(aSTNode);
                return;
            } else {
                scope.problemReporter().noSuchEnclosingInstance((ReferenceBinding) binding, aSTNode, false);
                return;
            }
        }
        if (objArr == BlockScope.NoEnclosingInstanceInConstructorCall) {
            scope.problemReporter().noSuchEnclosingInstance((ReferenceBinding) binding, aSTNode, true);
            return;
        }
        if (objArr == BlockScope.NoEnclosingInstanceInStaticContext) {
            scope.problemReporter().noSuchEnclosingInstance((ReferenceBinding) binding, aSTNode, false);
            return;
        }
        if (objArr == BlockScope.EmulationPathToImplicitThis) {
            aload_0();
            return;
        }
        Object obj = objArr[0];
        if (obj instanceof FieldBinding) {
            aload_0();
            fieldAccess(Opcodes.OPC_getfield, (FieldBinding) obj, null);
        } else {
            load((LocalVariableBinding) obj);
        }
        int length = objArr.length;
        for (int i = 1; i < length; i++) {
            Object obj2 = objArr[i];
            if (obj2 instanceof FieldBinding) {
                fieldAccess(Opcodes.OPC_getfield, (FieldBinding) obj2, null);
            } else {
                invoke(Opcodes.OPC_invokestatic, (MethodBinding) obj2, null);
            }
        }
    }

    public void generateReturnBytecode(Expression expression) {
        if (expression == null) {
            return_();
            return;
        }
        int i = expression.implicitConversion;
        if ((i & 512) != 0) {
            areturn();
            return;
        }
        switch ((i & 255) >> 4) {
            case 5:
            case 10:
                ireturn();
                break;
            case 6:
            default:
                areturn();
                break;
            case 7:
                lreturn();
                break;
            case 8:
                dreturn();
                break;
            case 9:
                freturn();
                break;
        }
    }

    public void generateStringConcatenationAppend(BlockScope blockScope, Expression expression, Expression expression2) {
        if (expression == null) {
            newStringContatenation();
            dup_x1();
            swap();
            invokeStringValueOf(1);
            invokeStringConcatenationStringConstructor();
        } else {
            int i = this.position;
            expression.generateOptimizedStringConcatenationCreation(blockScope, this, expression.implicitConversion & 15);
            recordPositionsFrom(i, expression.sourceStart);
        }
        int i2 = this.position;
        expression2.generateOptimizedStringConcatenation(blockScope, this, expression2.implicitConversion & 15);
        recordPositionsFrom(i2, expression2.sourceStart);
        invokeStringConcatenationToString();
    }

    public void generateSyntheticBodyForConstructorAccess(SyntheticMethodBinding syntheticMethodBinding) {
        int i;
        initializeMaxLocals(syntheticMethodBinding);
        MethodBinding methodBinding = syntheticMethodBinding.targetMethod;
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        aload_0();
        ReferenceBinding referenceBinding = methodBinding.declaringClass;
        if (referenceBinding.erasure().f626id == 41 || referenceBinding.isEnum()) {
            aload_1();
            iload_2();
            i = 3;
        } else {
            i = 1;
        }
        if (referenceBinding.isNestedType()) {
            SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticEnclosingInstances = ((NestedTypeBinding) referenceBinding).syntheticEnclosingInstances();
            int i2 = 0;
            while (true) {
                if (i2 >= (syntheticArgumentBindingArrSyntheticEnclosingInstances == null ? 0 : syntheticArgumentBindingArrSyntheticEnclosingInstances.length)) {
                    break;
                }
                TypeBinding typeBinding = syntheticArgumentBindingArrSyntheticEnclosingInstances[i2].type;
                load(typeBinding, i);
                int i3 = typeBinding.f626id;
                i = (i3 == 7 || i3 == 8) ? i + 2 : i + 1;
                i2++;
            }
        }
        for (TypeBinding typeBinding2 : typeBindingArr) {
            load(typeBinding2, i);
            int i4 = typeBinding2.f626id;
            i = (i4 == 7 || i4 == 8) ? i + 2 : i + 1;
        }
        if (referenceBinding.isNestedType()) {
            SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticOuterLocalVariables = ((NestedTypeBinding) referenceBinding).syntheticOuterLocalVariables();
            int i5 = 0;
            while (true) {
                if (i5 >= (syntheticArgumentBindingArrSyntheticOuterLocalVariables == null ? 0 : syntheticArgumentBindingArrSyntheticOuterLocalVariables.length)) {
                    break;
                }
                TypeBinding typeBinding3 = syntheticArgumentBindingArrSyntheticOuterLocalVariables[i5].type;
                load(typeBinding3, i);
                int i6 = typeBinding3.f626id;
                i = (i6 == 7 || i6 == 8) ? i + 2 : i + 1;
                i5++;
            }
        }
        invoke(Opcodes.OPC_invokespecial, methodBinding, null);
        return_();
    }

    public void generateSyntheticBodyForArrayConstructor(SyntheticMethodBinding syntheticMethodBinding) {
        initializeMaxLocals(syntheticMethodBinding);
        iload_0();
        newArray(null, null, (ArrayBinding) syntheticMethodBinding.returnType);
        areturn();
    }

    public void generateSyntheticBodyForArrayClone(SyntheticMethodBinding syntheticMethodBinding) {
        initializeMaxLocals(syntheticMethodBinding);
        TypeBinding typeBinding = syntheticMethodBinding.parameters[0];
        aload_0();
        invoke(Opcodes.OPC_invokevirtual, 1, 1, typeBinding.signature(), ConstantPool.Clone, ConstantPool.CloneSignature);
        checkcast(typeBinding);
        areturn();
    }

    public void generateSyntheticBodyForFactoryMethod(SyntheticMethodBinding syntheticMethodBinding) {
        initializeMaxLocals(syntheticMethodBinding);
        MethodBinding methodBinding = syntheticMethodBinding.targetMethod;
        TypeBinding[] typeBindingArr = syntheticMethodBinding.parameters;
        new_(methodBinding.declaringClass);
        dup();
        int i = 0;
        for (TypeBinding typeBinding : typeBindingArr) {
            load(typeBinding, i);
            int i2 = typeBinding.f626id;
            i = (i2 == 7 || i2 == 8) ? i + 2 : i + 1;
        }
        for (int i3 = 0; i3 < syntheticMethodBinding.fakePaddedParameters; i3++) {
            aconst_null();
        }
        invoke(Opcodes.OPC_invokespecial, methodBinding, null);
        areturn();
    }

    public void generateSyntheticBodyForEnumValueOf(SyntheticMethodBinding syntheticMethodBinding) {
        initializeMaxLocals(syntheticMethodBinding);
        ReferenceBinding referenceBinding = syntheticMethodBinding.declaringClass;
        generateClassLiteralAccessForType(referenceBinding, null);
        aload_0();
        invokeJavaLangEnumvalueOf(referenceBinding);
        checkcast(referenceBinding);
        areturn();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14, types: [int] */
    /* JADX WARN: Type inference failed for: r0v22 */
    /* JADX WARN: Type inference failed for: r1v7, types: [int[], java.lang.Object] */
    /* JADX WARN: Type inference failed for: r28v3 */
    /* JADX WARN: Type inference failed for: r28v4 */
    /* JADX WARN: Type inference failed for: r2v30 */
    /* JADX WARN: Type inference failed for: r2v5 */
    /* JADX WARN: Type inference failed for: r2v6, types: [int] */
    /* JADX WARN: Type inference failed for: r31v0, types: [org.eclipse.jdt.internal.compiler.codegen.CodeStream] */
    /* JADX WARN: Type inference failed for: r6v3, types: [int[]] */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v25 */
    /* JADX WARN: Type inference failed for: r9v26 */
    /* JADX WARN: Type inference failed for: r9v27 */
    /* JADX WARN: Type inference failed for: r9v28 */
    /* JADX WARN: Type inference failed for: r9v3, types: [int] */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v7 */
    public void generateSyntheticBodyForDeserializeLambda(SyntheticMethodBinding syntheticMethodBinding, SyntheticMethodBinding[] syntheticMethodBindingArr) {
        byte b;
        String str;
        MethodBinding methodBinding;
        SyntheticArgumentBinding[] syntheticArgumentBindingArr;
        int i;
        StringBuffer stringBuffer;
        SyntheticArgumentBinding[] syntheticArgumentBindingArr2;
        int i2;
        TypeBinding typeBinding;
        int i3;
        initializeMaxLocals(syntheticMethodBinding);
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        int length = syntheticMethodBindingArr.length;
        boolean z = false;
        int i4 = 0;
        while (i4 < length) {
            boolean z2 = z ? 1 : 0;
            SyntheticMethodBinding syntheticMethodBinding2 = syntheticMethodBindingArr[i4];
            if ((syntheticMethodBinding2.lambda != null && syntheticMethodBinding2.lambda.isSerializable) || syntheticMethodBinding2.serializableMethodRef != null) {
                Integer numValueOf = Integer.valueOf(new String(syntheticMethodBinding2.selector).hashCode());
                List arrayList = (List) linkedHashMap.get(numValueOf);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    linkedHashMap.put(numValueOf, arrayList);
                }
                arrayList.add(syntheticMethodBinding2);
            }
            i4++;
            z = z2 ? 1 : 0;
        }
        ClassScope classScope = ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope;
        aload_0();
        invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetImplMethodName, ConstantPool.GetImplMethodNameSignature);
        astore_1();
        LocalVariableBinding localVariableBinding = new LocalVariableBinding("hashcode".toCharArray(), classScope.getJavaLangString(), z ? 1 : 0, z);
        int i5 = 1;
        localVariableBinding.resolvedPosition = 1;
        addVariable(localVariableBinding);
        iconst_m1();
        istore_2();
        LocalVariableBinding localVariableBinding2 = new LocalVariableBinding("id".toCharArray(), TypeBinding.INT, z ? 1 : 0, z);
        localVariableBinding2.resolvedPosition = 2;
        addVariable(localVariableBinding2);
        aload_1();
        invokeStringHashCode();
        BranchLabel branchLabel = new BranchLabel(this);
        CaseLabel caseLabel = new CaseLabel(this);
        int size = linkedHashMap.size();
        CaseLabel[] caseLabelArr = new CaseLabel[size];
        int[] iArr = new int[size];
        int[] iArr2 = new int[size];
        Set<Integer> setKeySet = linkedHashMap.keySet();
        int i6 = z ? 1 : 0;
        boolean z3 = z;
        for (Integer num : setKeySet) {
            boolean z4 = z3 ? 1 : 0;
            caseLabelArr[i6] = new CaseLabel(this);
            iArr[i6] = num.intValue();
            iArr2[i6] = i6;
            i6++;
            i5 = 1;
            z3 = z4 ? 1 : 0;
        }
        int[] iArr3 = new int[size];
        System.arraycopy(iArr, z3 ? 1 : 0, iArr3, z3 ? 1 : 0, size);
        sort(iArr3, z3 ? 1 : 0, size - i5, iArr2);
        lookupswitch(caseLabel, iArr, iArr2, caseLabelArr);
        Iterator it = setKeySet.iterator();
        int i7 = z3 ? 1 : 0;
        ?? r9 = z3;
        while (it.hasNext()) {
            ?? r28 = r9;
            LocalVariableBinding localVariableBinding3 = localVariableBinding;
            LocalVariableBinding localVariableBinding4 = localVariableBinding2;
            List list = (List) linkedHashMap.get((Integer) it.next());
            caseLabelArr[i7].place();
            BranchLabel branchLabel2 = new BranchLabel(this);
            int size2 = list.size();
            for (int i8 = r28 == true ? 1 : 0; i8 < size2; i8++) {
                SyntheticMethodBinding syntheticMethodBinding3 = (SyntheticMethodBinding) list.get(i8);
                aload_1();
                ldc(new String(syntheticMethodBinding3.selector));
                invokeStringEquals();
                ifeq(branchLabel2);
                loadInt(i7);
                istore_2();
                goto_(branchLabel);
                branchLabel2.place();
                branchLabel2 = new BranchLabel(this);
            }
            i7++;
            goto_(branchLabel);
            localVariableBinding = localVariableBinding3;
            localVariableBinding2 = localVariableBinding4;
            r9 = r28 == true ? 1 : 0;
            i5 = 1;
        }
        caseLabel.place();
        branchLabel.place();
        int size3 = setKeySet.size();
        CaseLabel[] caseLabelArr2 = new CaseLabel[size3];
        ?? r1 = new int[size3];
        ?? r6 = new int[size3];
        BranchLabel branchLabel3 = new BranchLabel(this);
        CaseLabel caseLabel2 = new CaseLabel(this);
        iload_2();
        for (?? r2 = r9; r2 < size3; r2++) {
            caseLabelArr2[r2] = new CaseLabel(this);
            r1[r2] = r2;
            r6[r2] = r2;
            i5 = 1;
        }
        int[] iArr4 = new int[size3];
        System.arraycopy(r1, r9, iArr4, r9, size3);
        sort(iArr4, r9, size3 - i5, r6);
        lookupswitch(caseLabel2, r1, r6, caseLabelArr2);
        Iterator it2 = setKeySet.iterator();
        ?? r0 = r9;
        ?? r10 = r9;
        while (it2.hasNext()) {
            List list2 = (List) linkedHashMap.get((Integer) it2.next());
            int i9 = r0 + 1;
            caseLabelArr2[r0].place();
            BranchLabel branchLabel4 = list2.size() > i5 ? new BranchLabel(this) : branchLabel3;
            int size4 = list2.size();
            BranchLabel branchLabel5 = branchLabel4;
            int i10 = r10 == true ? 1 : 0;
            ?? r11 = r10;
            while (i10 < size4) {
                SyntheticMethodBinding syntheticMethodBinding4 = (SyntheticMethodBinding) list2.get(i10);
                aload_0();
                FunctionalExpression functionalExpression = syntheticMethodBinding4.lambda != null ? syntheticMethodBinding4.lambda : syntheticMethodBinding4.serializableMethodRef;
                MethodBinding methodBinding2 = functionalExpression.binding;
                int i11 = i10;
                BranchLabel branchLabel6 = branchLabel5;
                int i12 = size4;
                CaseLabel caseLabel3 = caseLabel2;
                List list3 = list2;
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetImplMethodKind, ConstantPool.GetImplMethodKindSignature);
                if (methodBinding2.isStatic()) {
                    b = 6;
                } else if (methodBinding2.isPrivate()) {
                    b = 7;
                } else {
                    b = methodBinding2.isConstructor() ? (byte) 8 : (byte) 5;
                }
                bipush(b);
                if_icmpne(branchLabel6);
                aload_0();
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetFunctionalInterfaceClass, ConstantPool.GetFunctionalInterfaceClassSignature);
                TypeBinding typeBindingExpectedType = functionalExpression.expectedType();
                if (typeBindingExpectedType instanceof IntersectionTypeBinding18) {
                    str = new String(((IntersectionTypeBinding18) typeBindingExpectedType).getSAMType(classScope).constantPoolName());
                } else {
                    str = new String(typeBindingExpectedType.constantPoolName());
                }
                ldc(str);
                invokeObjectEquals();
                ifeq(branchLabel6);
                aload_0();
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetFunctionalInterfaceMethodName, ConstantPool.GetFunctionalInterfaceMethodNameSignature);
                ldc(new String(functionalExpression.descriptor.selector));
                invokeObjectEquals();
                ifeq(branchLabel6);
                aload_0();
                LocalVariableBinding localVariableBinding5 = localVariableBinding;
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetFunctionalInterfaceMethodSignature, ConstantPool.GetFunctionalInterfaceMethodSignatureSignature);
                ldc(new String(functionalExpression.descriptor.original().signature()));
                invokeObjectEquals();
                ifeq(branchLabel6);
                aload_0();
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetImplClass, ConstantPool.GetImplClassSignature);
                ldc(new String(methodBinding2.declaringClass.constantPoolName()));
                invokeObjectEquals();
                ifeq(branchLabel6);
                aload_0();
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetImplMethodSignature, ConstantPool.GetImplMethodSignatureSignature);
                ldc(new String(methodBinding2.signature()));
                invokeObjectEquals();
                ifeq(branchLabel6);
                StringBuffer stringBuffer2 = new StringBuffer("(");
                boolean z5 = functionalExpression instanceof LambdaExpression;
                TypeBinding typeBinding2 = null;
                if (z5) {
                    LambdaExpression lambdaExpression = (LambdaExpression) functionalExpression;
                    methodBinding = methodBinding2;
                    typeBinding2 = lambdaExpression.shouldCaptureInstance ? methodBinding.declaringClass : null;
                    syntheticArgumentBindingArr = lambdaExpression.outerLocalVariables;
                } else {
                    methodBinding = methodBinding2;
                    ReferenceExpression referenceExpression = (ReferenceExpression) functionalExpression;
                    if (referenceExpression.haveReceiver) {
                        syntheticArgumentBindingArr = null;
                        typeBinding2 = referenceExpression.receiverType;
                    } else {
                        syntheticArgumentBindingArr = null;
                    }
                }
                if (typeBinding2 != null) {
                    aload_0();
                    loadInt(0);
                    i = 0;
                    SyntheticArgumentBinding[] syntheticArgumentBindingArr3 = syntheticArgumentBindingArr;
                    MethodBinding methodBinding3 = methodBinding;
                    stringBuffer = stringBuffer2;
                    invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetCapturedArg, ConstantPool.GetCapturedArgSignature);
                    checkcast(z5 ? methodBinding3.declaringClass : ((ReferenceExpression) functionalExpression).receiverType);
                    stringBuffer.append(methodBinding3.declaringClass.signature());
                    syntheticArgumentBindingArr2 = syntheticArgumentBindingArr3;
                    i2 = 1;
                } else {
                    i = 0;
                    stringBuffer = stringBuffer2;
                    syntheticArgumentBindingArr2 = syntheticArgumentBindingArr;
                    i2 = 0;
                }
                int length2 = syntheticArgumentBindingArr2 == null ? i : syntheticArgumentBindingArr2.length;
                int i13 = i2;
                int i14 = i;
                while (i14 < length2) {
                    TypeBinding typeBinding3 = syntheticArgumentBindingArr2[i14].type;
                    aload_0();
                    loadInt(i13);
                    int i15 = i13;
                    int i16 = i14;
                    int i17 = length2;
                    SyntheticArgumentBinding[] syntheticArgumentBindingArr4 = syntheticArgumentBindingArr2;
                    invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangInvokeSerializedLambdaConstantPoolName, ConstantPool.GetCapturedArg, ConstantPool.GetCapturedArgSignature);
                    if (typeBinding3.isBaseType()) {
                        typeBinding = typeBinding3;
                        checkcast(classScope.boxing(typeBinding));
                        generateUnboxingConversion(typeBinding.f626id);
                        if (typeBinding.f626id == 30 || typeBinding.f626id == 32) {
                            i3 = i15 + 1;
                        }
                        i13 = i3 + 1;
                        stringBuffer.append(typeBinding.signature());
                        i14 = i16 + 1;
                        syntheticArgumentBindingArr2 = syntheticArgumentBindingArr4;
                        length2 = i17;
                    } else {
                        typeBinding = typeBinding3;
                        checkcast(typeBinding);
                    }
                    i3 = i15;
                    i13 = i3 + 1;
                    stringBuffer.append(typeBinding.signature());
                    i14 = i16 + 1;
                    syntheticArgumentBindingArr2 = syntheticArgumentBindingArr4;
                    length2 = i17;
                }
                stringBuffer.append(com.huawei.hms.ml.language.common.utils.Constant.AFTER_QUTO);
                if (functionalExpression.resolvedType instanceof IntersectionTypeBinding18) {
                    stringBuffer.append(((IntersectionTypeBinding18) functionalExpression.resolvedType).getSAMType(classScope).signature());
                } else {
                    stringBuffer.append(functionalExpression.resolvedType.signature());
                }
                invokeDynamic(functionalExpression.bootstrapMethodNumber, i13, 1, functionalExpression.descriptor.selector, stringBuffer.toString().toCharArray());
                areturn();
                if (i11 < i12 - 1) {
                    branchLabel6.place();
                    branchLabel5 = i11 < i12 + (-2) ? new BranchLabel(this) : branchLabel3;
                } else {
                    branchLabel5 = branchLabel6;
                }
                i10 = (i11 == true ? 1 : 0) + 1;
                list2 = list3;
                localVariableBinding = localVariableBinding5;
                localVariableBinding2 = localVariableBinding2;
                size4 = i12;
                caseLabel2 = caseLabel3;
                r11 = i;
                i5 = 1;
            }
            r0 = i9;
            r10 = r11;
        }
        removeVariable(localVariableBinding);
        removeVariable(localVariableBinding2);
        caseLabel2.place();
        branchLabel3.place();
        new_(classScope.getJavaLangIllegalArgumentException());
        dup();
        ldc("Invalid lambda deserialization");
        invoke(Opcodes.OPC_invokespecial, 2, 0, ConstantPool.JavaLangIllegalArgumentExceptionConstantPoolName, ConstantPool.Init, ConstantPool.IllegalArgumentExceptionConstructorSignature);
        athrow();
    }

    public void loadInt(int i) {
        if (i >= 6) {
            if (i < 128) {
                bipush((byte) i);
                return;
            } else {
                ldc(i);
                return;
            }
        }
        if (i == 0) {
            iconst_0();
            return;
        }
        if (i == 1) {
            iconst_1();
            return;
        }
        if (i == 2) {
            iconst_2();
            return;
        }
        if (i == 3) {
            iconst_3();
        } else if (i == 4) {
            iconst_4();
        } else if (i == 5) {
            iconst_5();
        }
    }

    public void generateSyntheticBodyForEnumValues(SyntheticMethodBinding syntheticMethodBinding) {
        ClassScope classScope = ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope;
        initializeMaxLocals(syntheticMethodBinding);
        TypeBinding typeBinding = syntheticMethodBinding.returnType;
        fieldAccess(Opcodes.OPC_getstatic, classScope.referenceContext.enumValuesSyntheticfield, null);
        dup();
        astore_0();
        iconst_0();
        aload_0();
        arraylength();
        dup();
        istore_1();
        newArray((ArrayBinding) typeBinding);
        dup();
        astore_2();
        iconst_0();
        iload_1();
        invokeSystemArraycopy();
        aload_2();
        areturn();
    }

    public void generateSyntheticBodyForEnumInitializationMethod(SyntheticMethodBinding syntheticMethodBinding) {
        this.maxLocals = 0;
        TypeDeclaration typeDeclaration = ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope.referenceContext;
        MethodScope methodScope = typeDeclaration.staticInitializerScope;
        FieldDeclaration[] fieldDeclarationArr = typeDeclaration.fields;
        int i = syntheticMethodBinding.endIndex;
        for (int i2 = syntheticMethodBinding.startIndex; i2 < i; i2++) {
            FieldDeclaration fieldDeclaration = fieldDeclarationArr[i2];
            if (fieldDeclaration.isStatic() && fieldDeclaration.getKind() == 3) {
                fieldDeclaration.generateCode(methodScope, this);
            }
        }
        return_();
    }

    public void generateSyntheticBodyForFieldReadAccess(SyntheticMethodBinding syntheticMethodBinding) {
        ReferenceBinding referenceBindingSuperclass;
        initializeMaxLocals(syntheticMethodBinding);
        FieldBinding fieldBinding = syntheticMethodBinding.targetReadField;
        if (syntheticMethodBinding.purpose == 3) {
            referenceBindingSuperclass = syntheticMethodBinding.declaringClass.superclass();
        } else {
            referenceBindingSuperclass = syntheticMethodBinding.declaringClass;
        }
        if (fieldBinding.isStatic()) {
            fieldAccess(Opcodes.OPC_getstatic, fieldBinding, referenceBindingSuperclass);
        } else {
            aload_0();
            fieldAccess(Opcodes.OPC_getfield, fieldBinding, referenceBindingSuperclass);
        }
        switch (fieldBinding.type.f626id) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
                ireturn();
                break;
            case 6:
            default:
                areturn();
                break;
            case 7:
                lreturn();
                break;
            case 8:
                dreturn();
                break;
            case 9:
                freturn();
                break;
        }
    }

    public void generateSyntheticBodyForFieldWriteAccess(SyntheticMethodBinding syntheticMethodBinding) {
        ReferenceBinding referenceBindingSuperclass;
        initializeMaxLocals(syntheticMethodBinding);
        FieldBinding fieldBinding = syntheticMethodBinding.targetWriteField;
        if (syntheticMethodBinding.purpose == 4) {
            referenceBindingSuperclass = syntheticMethodBinding.declaringClass.superclass();
        } else {
            referenceBindingSuperclass = syntheticMethodBinding.declaringClass;
        }
        if (fieldBinding.isStatic()) {
            load(fieldBinding.type, 0);
            fieldAccess(Opcodes.OPC_putstatic, fieldBinding, referenceBindingSuperclass);
        } else {
            aload_0();
            load(fieldBinding.type, 1);
            fieldAccess(Opcodes.OPC_putfield, fieldBinding, referenceBindingSuperclass);
        }
        return_();
    }

    public void generateSyntheticBodyForMethodAccess(SyntheticMethodBinding syntheticMethodBinding) {
        int i;
        ReferenceBinding referenceBindingFindDirectSuperTypeTowards;
        initializeMaxLocals(syntheticMethodBinding);
        MethodBinding methodBinding = syntheticMethodBinding.targetMethod;
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        int length = typeBindingArr.length;
        TypeBinding[] typeBindingArr2 = syntheticMethodBinding.purpose == 8 ? syntheticMethodBinding.parameters : null;
        if (methodBinding.isStatic()) {
            i = 0;
        } else {
            aload_0();
            i = 1;
        }
        for (int i2 = 0; i2 < length; i2++) {
            TypeBinding typeBinding = typeBindingArr[i2];
            if (typeBindingArr2 != null) {
                TypeBinding typeBinding2 = typeBindingArr2[i2];
                load(typeBinding2, i);
                if (TypeBinding.notEquals(typeBinding2, typeBinding)) {
                    checkcast(typeBinding);
                }
            } else {
                load(typeBinding, i);
            }
            int i3 = typeBinding.f626id;
            i = (i3 == 7 || i3 == 8) ? i + 2 : i + 1;
        }
        if (methodBinding.isStatic()) {
            invoke(Opcodes.OPC_invokestatic, methodBinding, syntheticMethodBinding.declaringClass);
        } else if (methodBinding.isConstructor() || methodBinding.isPrivate() || syntheticMethodBinding.purpose == 7) {
            if (syntheticMethodBinding.purpose == 7) {
                referenceBindingFindDirectSuperTypeTowards = findDirectSuperTypeTowards(syntheticMethodBinding, methodBinding);
            } else {
                referenceBindingFindDirectSuperTypeTowards = syntheticMethodBinding.declaringClass;
            }
            invoke(Opcodes.OPC_invokespecial, methodBinding, referenceBindingFindDirectSuperTypeTowards);
        } else if (methodBinding.declaringClass.isInterface()) {
            invoke(Opcodes.OPC_invokeinterface, methodBinding, null);
        } else {
            invoke(Opcodes.OPC_invokevirtual, methodBinding, syntheticMethodBinding.declaringClass);
        }
        switch (methodBinding.returnType.f626id) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
                ireturn();
                break;
            case 6:
                return_();
                break;
            case 7:
                lreturn();
                break;
            case 8:
                dreturn();
                break;
            case 9:
                freturn();
                break;
            default:
                TypeBinding typeBindingErasure = syntheticMethodBinding.returnType.erasure();
                if (methodBinding.returnType.findSuperTypeOriginatingFrom(typeBindingErasure) == null) {
                    checkcast(typeBindingErasure);
                }
                areturn();
                break;
        }
    }

    ReferenceBinding findDirectSuperTypeTowards(SyntheticMethodBinding syntheticMethodBinding, MethodBinding methodBinding) {
        ReferenceBinding referenceBinding = syntheticMethodBinding.declaringClass;
        ReferenceBinding referenceBindingSuperclass = referenceBinding.superclass();
        if (!methodBinding.isDefaultMethod()) {
            return referenceBindingSuperclass;
        }
        ReferenceBinding referenceBinding2 = methodBinding.declaringClass;
        if (referenceBindingSuperclass.isCompatibleWith(referenceBinding2)) {
            return referenceBindingSuperclass;
        }
        ReferenceBinding[] referenceBindingArrSuperInterfaces = referenceBinding.superInterfaces();
        if (referenceBindingArrSuperInterfaces != null) {
            for (ReferenceBinding referenceBinding3 : referenceBindingArrSuperInterfaces) {
                if (referenceBinding3.isCompatibleWith(referenceBinding2)) {
                    return referenceBinding3;
                }
            }
        }
        throw new RuntimeException("Assumption violated: some super type must be conform to the declaring class of a super method");
    }

    public void generateSyntheticBodyForSwitchTable(SyntheticMethodBinding syntheticMethodBinding) {
        ClassScope classScope = ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope;
        initializeMaxLocals(syntheticMethodBinding);
        BranchLabel branchLabel = new BranchLabel(this);
        FieldBinding fieldBinding = syntheticMethodBinding.targetReadField;
        fieldAccess(Opcodes.OPC_getstatic, fieldBinding, null);
        dup();
        ifnull(branchLabel);
        areturn();
        pushOnStack(fieldBinding.type);
        branchLabel.place();
        pop();
        ReferenceBinding referenceBinding = (ReferenceBinding) syntheticMethodBinding.targetEnumType;
        invokeJavaLangEnumValues(referenceBinding, classScope.createArrayType(referenceBinding, 1));
        arraylength();
        newarray(10);
        astore_0();
        LocalVariableBinding localVariableBinding = new LocalVariableBinding(" tab".toCharArray(), (TypeBinding) classScope.createArrayType(TypeBinding.INT, 1), 0, false);
        addVariable(localVariableBinding);
        FieldBinding[] fieldBindingArrFields = referenceBinding.fields();
        if (fieldBindingArrFields != null) {
            for (FieldBinding fieldBinding2 : fieldBindingArrFields) {
                if ((fieldBinding2.getAccessFlags() & 16384) != 0) {
                    BranchLabel branchLabel2 = new BranchLabel(this);
                    ExceptionLabel exceptionLabel = new ExceptionLabel(this, TypeBinding.LONG);
                    exceptionLabel.placeStart();
                    aload_0();
                    fieldAccess(Opcodes.OPC_getstatic, fieldBinding2, null);
                    invokeEnumOrdinal(referenceBinding.constantPoolName());
                    generateInlinedValue(fieldBinding2.f627id + 1);
                    iastore();
                    exceptionLabel.placeEnd();
                    goto_(branchLabel2);
                    pushExceptionOnStack(TypeBinding.LONG);
                    exceptionLabel.place();
                    pop();
                    branchLabel2.place();
                }
            }
        }
        aload_0();
        dup();
        fieldAccess(Opcodes.OPC_putstatic, fieldBinding, null);
        areturn();
        removeVariable(localVariableBinding);
    }

    public void generateSyntheticEnclosingInstanceValues(BlockScope blockScope, ReferenceBinding referenceBinding, Expression expression, ASTNode aSTNode) {
        boolean z;
        ReferenceBinding referenceBinding2 = referenceBinding.isAnonymousType() ? (ReferenceBinding) referenceBinding.superclass().erasure() : referenceBinding;
        boolean z2 = expression != null;
        if (z2 && (!referenceBinding2.isNestedType() || referenceBinding2.isStatic())) {
            blockScope.problemReporter().unnecessaryEnclosingInstanceSpecification(expression, referenceBinding2);
            return;
        }
        ReferenceBinding[] referenceBindingArrSyntheticEnclosingInstanceTypes = referenceBinding.syntheticEnclosingInstanceTypes();
        if (referenceBindingArrSyntheticEnclosingInstanceTypes != null) {
            ReferenceBinding referenceBindingEnclosingType = referenceBinding2.enclosingType();
            long j = blockScope.compilerOptions().complianceLevel;
            if (j <= ClassFileConstants.JDK1_3) {
                z = aSTNode instanceof AllocationExpression;
            } else {
                z = j != ClassFileConstants.JDK1_4 ? ((aSTNode instanceof AllocationExpression) || ((aSTNode instanceof ExplicitConstructorCall) && ((ExplicitConstructorCall) aSTNode).isSuperAccess())) && !referenceBinding.isLocalType() : (aSTNode instanceof AllocationExpression) || ((aSTNode instanceof ExplicitConstructorCall) && ((ExplicitConstructorCall) aSTNode).isSuperAccess());
            }
            boolean z3 = j >= ClassFileConstants.JDK1_4;
            for (ReferenceBinding referenceBinding3 : referenceBindingArrSyntheticEnclosingInstanceTypes) {
                if (z2 && TypeBinding.equalsEquals(referenceBinding3, referenceBindingEnclosingType)) {
                    expression.generateCode(blockScope, this, true);
                    if (z3) {
                        dup();
                        invokeObjectGetClass();
                        pop();
                    }
                    z2 = false;
                } else {
                    generateOuterAccess(blockScope.getEmulationPath(referenceBinding3, false, z), aSTNode, referenceBinding3, blockScope);
                }
            }
            if (z2) {
                blockScope.problemReporter().unnecessaryEnclosingInstanceSpecification(expression, referenceBinding2);
            }
        }
    }

    public void generateSyntheticOuterArgumentValues(BlockScope blockScope, ReferenceBinding referenceBinding, ASTNode aSTNode) {
        SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticOuterLocalVariables = referenceBinding.syntheticOuterLocalVariables();
        if (syntheticArgumentBindingArrSyntheticOuterLocalVariables != null) {
            for (SyntheticArgumentBinding syntheticArgumentBinding : syntheticArgumentBindingArrSyntheticOuterLocalVariables) {
                LocalVariableBinding localVariableBinding = syntheticArgumentBinding.actualOuterLocalVariable;
                generateOuterAccess(blockScope.getEmulationPath(localVariableBinding), aSTNode, localVariableBinding, blockScope);
            }
        }
    }

    public void generateUnboxingConversion(int i) {
        switch (i) {
            case 2:
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangCharacterConstantPoolName, ConstantPool.CHARVALUE_CHARACTER_METHOD_NAME, ConstantPool.CHARVALUE_CHARACTER_METHOD_SIGNATURE);
                break;
            case 3:
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangByteConstantPoolName, ConstantPool.BYTEVALUE_BYTE_METHOD_NAME, ConstantPool.BYTEVALUE_BYTE_METHOD_SIGNATURE);
                break;
            case 4:
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangShortConstantPoolName, ConstantPool.SHORTVALUE_SHORT_METHOD_NAME, ConstantPool.SHORTVALUE_SHORT_METHOD_SIGNATURE);
                break;
            case 5:
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangBooleanConstantPoolName, ConstantPool.BOOLEANVALUE_BOOLEAN_METHOD_NAME, ConstantPool.BOOLEANVALUE_BOOLEAN_METHOD_SIGNATURE);
                break;
            case 7:
                invoke(Opcodes.OPC_invokevirtual, 1, 2, ConstantPool.JavaLangLongConstantPoolName, ConstantPool.LONGVALUE_LONG_METHOD_NAME, ConstantPool.LONGVALUE_LONG_METHOD_SIGNATURE);
                break;
            case 8:
                invoke(Opcodes.OPC_invokevirtual, 1, 2, ConstantPool.JavaLangDoubleConstantPoolName, ConstantPool.DOUBLEVALUE_DOUBLE_METHOD_NAME, ConstantPool.DOUBLEVALUE_DOUBLE_METHOD_SIGNATURE);
                break;
            case 9:
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangFloatConstantPoolName, ConstantPool.FLOATVALUE_FLOAT_METHOD_NAME, ConstantPool.FLOATVALUE_FLOAT_METHOD_SIGNATURE);
                break;
            case 10:
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangIntegerConstantPoolName, ConstantPool.INTVALUE_INTEGER_METHOD_NAME, ConstantPool.INTVALUE_INTEGER_METHOD_SIGNATURE);
                break;
        }
    }

    public void generateWideRevertedConditionalBranch(byte b, BranchLabel branchLabel) {
        BranchLabel branchLabel2 = new BranchLabel(this);
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = b;
        branchLabel2.branch();
        goto_w(branchLabel);
        branchLabel2.place();
    }

    public void getBaseTypeValue(int i) {
        switch (i) {
            case 2:
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangCharacterConstantPoolName, ConstantPool.CHARVALUE_CHARACTER_METHOD_NAME, ConstantPool.CHARVALUE_CHARACTER_METHOD_SIGNATURE);
                break;
            case 3:
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangByteConstantPoolName, ConstantPool.BYTEVALUE_BYTE_METHOD_NAME, ConstantPool.BYTEVALUE_BYTE_METHOD_SIGNATURE);
                break;
            case 4:
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangShortConstantPoolName, ConstantPool.SHORTVALUE_SHORT_METHOD_NAME, ConstantPool.SHORTVALUE_SHORT_METHOD_SIGNATURE);
                break;
            case 5:
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangBooleanConstantPoolName, ConstantPool.BOOLEANVALUE_BOOLEAN_METHOD_NAME, ConstantPool.BOOLEANVALUE_BOOLEAN_METHOD_SIGNATURE);
                break;
            case 7:
                invoke(Opcodes.OPC_invokevirtual, 1, 2, ConstantPool.JavaLangLongConstantPoolName, ConstantPool.LONGVALUE_LONG_METHOD_NAME, ConstantPool.LONGVALUE_LONG_METHOD_SIGNATURE);
                break;
            case 8:
                invoke(Opcodes.OPC_invokevirtual, 1, 2, ConstantPool.JavaLangDoubleConstantPoolName, ConstantPool.DOUBLEVALUE_DOUBLE_METHOD_NAME, ConstantPool.DOUBLEVALUE_DOUBLE_METHOD_SIGNATURE);
                break;
            case 9:
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangFloatConstantPoolName, ConstantPool.FLOATVALUE_FLOAT_METHOD_NAME, ConstantPool.FLOATVALUE_FLOAT_METHOD_SIGNATURE);
                break;
            case 10:
                invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangIntegerConstantPoolName, ConstantPool.INTVALUE_INTEGER_METHOD_NAME, ConstantPool.INTVALUE_INTEGER_METHOD_SIGNATURE);
                break;
        }
    }

    public final byte[] getContents() {
        byte[] bArr = this.bCodeStream;
        int i = this.position;
        byte[] bArr2 = new byte[i];
        System.arraycopy(bArr, 0, bArr2, 0, i);
        return bArr2;
    }

    public static TypeBinding getConstantPoolDeclaringClass(Scope scope, FieldBinding fieldBinding, TypeBinding typeBinding, boolean z) {
        ReferenceBinding referenceBinding = fieldBinding.declaringClass;
        if (TypeBinding.notEquals(referenceBinding, typeBinding.erasure()) && !typeBinding.isArrayType() && referenceBinding != null && fieldBinding.constant() == Constant.NotAConstant) {
            CompilerOptions compilerOptions = scope.compilerOptions();
            if ((compilerOptions.targetJDK >= ClassFileConstants.JDK1_2 && ((compilerOptions.complianceLevel >= ClassFileConstants.JDK1_4 || !z || !fieldBinding.isStatic()) && referenceBinding.f626id != 1)) || !referenceBinding.canBeSeenBy(scope)) {
                return typeBinding.erasure();
            }
        }
        return referenceBinding;
    }

    public static TypeBinding getConstantPoolDeclaringClass(Scope scope, MethodBinding methodBinding, TypeBinding typeBinding, boolean z) {
        ReferenceBinding referenceBinding = methodBinding.declaringClass;
        if (methodBinding == scope.environment().arrayClone) {
            return scope.compilerOptions().sourceLevel > ClassFileConstants.JDK1_4 ? typeBinding.erasure() : referenceBinding;
        }
        if (!TypeBinding.notEquals(referenceBinding, typeBinding.erasure()) || typeBinding.isArrayType()) {
            return referenceBinding;
        }
        CompilerOptions compilerOptions = scope.compilerOptions();
        if ((compilerOptions.targetJDK < ClassFileConstants.JDK1_2 || ((compilerOptions.complianceLevel < ClassFileConstants.JDK1_4 && z && methodBinding.isStatic()) || methodBinding.declaringClass.f626id == 1)) && methodBinding.declaringClass.canBeSeenBy(scope)) {
            return referenceBinding;
        }
        if (typeBinding.isIntersectionType18()) {
            ReferenceBinding[] intersectingTypes = ((IntersectionTypeBinding18) typeBinding).getIntersectingTypes();
            for (int i = 0; i < intersectingTypes.length; i++) {
                if (intersectingTypes[i].findSuperTypeOriginatingFrom(referenceBinding) != null) {
                    return intersectingTypes[i];
                }
            }
            return referenceBinding;
        }
        return typeBinding.erasure();
    }

    protected int getPosition() {
        return this.position;
    }

    public void getTYPE(int i) {
        this.countLabels = 0;
        switch (i) {
            case 2:
                fieldAccess(Opcodes.OPC_getstatic, 1, ConstantPool.JavaLangCharacterConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
                break;
            case 3:
                fieldAccess(Opcodes.OPC_getstatic, 1, ConstantPool.JavaLangByteConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
                break;
            case 4:
                fieldAccess(Opcodes.OPC_getstatic, 1, ConstantPool.JavaLangShortConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
                break;
            case 5:
                fieldAccess(Opcodes.OPC_getstatic, 1, ConstantPool.JavaLangBooleanConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
                break;
            case 6:
                fieldAccess(Opcodes.OPC_getstatic, 1, ConstantPool.JavaLangVoidConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
                break;
            case 7:
                fieldAccess(Opcodes.OPC_getstatic, 1, ConstantPool.JavaLangLongConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
                break;
            case 8:
                fieldAccess(Opcodes.OPC_getstatic, 1, ConstantPool.JavaLangDoubleConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
                break;
            case 9:
                fieldAccess(Opcodes.OPC_getstatic, 1, ConstantPool.JavaLangFloatConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
                break;
            case 10:
                fieldAccess(Opcodes.OPC_getstatic, 1, ConstantPool.JavaLangIntegerConstantPoolName, ConstantPool.TYPE, ConstantPool.JavaLangClassSignature);
                break;
        }
    }

    public void goto_(BranchLabel branchLabel) {
        if (this.wideMode) {
            goto_w(branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        if (inlineForwardReferencesFromLabelsTargeting(branchLabel, this.position) && this.lastAbruptCompletion == this.position) {
            if (branchLabel.position != -1) {
                int[] iArrForwardReferences = branchLabel.forwardReferences();
                int iForwardReferenceCount = branchLabel.forwardReferenceCount();
                for (int i = 0; i < iForwardReferenceCount; i++) {
                    writePosition(branchLabel, iArrForwardReferences[i]);
                }
                this.countLabels = 0;
                return;
            }
            return;
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = Opcodes.OPC_goto;
        branchLabel.branch();
        this.lastAbruptCompletion = this.position;
    }

    public void goto_w(BranchLabel branchLabel) {
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_goto_w;
        branchLabel.branchWide();
        this.lastAbruptCompletion = this.position;
    }

    public void i2b() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_i2b;
    }

    public void i2c() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_i2c;
    }

    public void i2d() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = Opcodes.OPC_i2d;
    }

    public void i2f() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_i2f;
    }

    public void i2l() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = Opcodes.OPC_i2l;
    }

    public void i2s() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_i2s;
    }

    public void iadd() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 96;
    }

    public void iaload() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 46;
    }

    public void iand() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 126;
    }

    public void iastore() {
        this.countLabels = 0;
        this.stackDepth -= 3;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 79;
    }

    public void iconst_0() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 3;
    }

    public void iconst_1() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 4;
    }

    public void iconst_2() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 5;
    }

    public void iconst_3() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 6;
    }

    public void iconst_4() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 7;
    }

    public void iconst_5() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 8;
    }

    public void iconst_m1() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 2;
    }

    public void idiv() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 108;
    }

    public void if_acmpeq(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_if_acmpne, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_if_acmpeq;
        branchLabel.branch();
    }

    public void if_acmpne(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_if_acmpeq, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_if_acmpne;
        branchLabel.branch();
    }

    public void if_icmpeq(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch((byte) -96, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_if_icmpeq;
        branchLabel.branch();
    }

    public void if_icmpge(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_if_icmplt, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_if_icmpge;
        branchLabel.branch();
    }

    public void if_icmpgt(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_if_icmple, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_if_icmpgt;
        branchLabel.branch();
    }

    public void if_icmple(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_if_icmpgt, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_if_icmple;
        branchLabel.branch();
    }

    public void if_icmplt(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_if_icmpge, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_if_icmplt;
        branchLabel.branch();
    }

    public void if_icmpne(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_if_icmpeq, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = -96;
        branchLabel.branch();
    }

    public void ifeq(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_ifne, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_ifeq;
        branchLabel.branch();
    }

    public void ifge(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_iflt, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_ifge;
        branchLabel.branch();
    }

    public void ifgt(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_ifle, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_ifgt;
        branchLabel.branch();
    }

    public void ifle(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_ifgt, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_ifle;
        branchLabel.branch();
    }

    public void iflt(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_ifge, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_iflt;
        branchLabel.branch();
    }

    public void ifne(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_ifeq, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_ifne;
        branchLabel.branch();
    }

    public void ifnonnull(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_ifnull, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_ifnonnull;
        branchLabel.branch();
    }

    public void ifnull(BranchLabel branchLabel) {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.wideMode) {
            generateWideRevertedConditionalBranch(Opcodes.OPC_ifnonnull, branchLabel);
            return;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_ifnull;
        branchLabel.branch();
    }

    public final void iinc(int i, int i2) {
        this.countLabels = 0;
        if (i > 255 || i2 < -128 || i2 > 127) {
            if (this.classFileOffset + 3 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position += 2;
            byte[] bArr = this.bCodeStream;
            int i3 = this.classFileOffset;
            int i4 = i3 + 1;
            this.classFileOffset = i4;
            bArr[i3] = Opcodes.OPC_wide;
            this.classFileOffset = i3 + 2;
            bArr[i4] = Opcodes.OPC_iinc;
            writeUnsignedShort(i);
            writeSignedShort(i2);
            return;
        }
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 3;
        byte[] bArr2 = this.bCodeStream;
        int i5 = this.classFileOffset;
        int i6 = i5 + 1;
        this.classFileOffset = i6;
        bArr2[i5] = Opcodes.OPC_iinc;
        int i7 = i5 + 2;
        this.classFileOffset = i7;
        bArr2[i6] = (byte) i;
        this.classFileOffset = i5 + 3;
        bArr2[i7] = (byte) i2;
    }

    public void iload(int i) {
        this.countLabels = 0;
        int i2 = this.stackDepth + 1;
        this.stackDepth = i2;
        if (this.maxLocals <= i) {
            this.maxLocals = i + 1;
        }
        if (i2 > this.stackMax) {
            this.stackMax = i2;
        }
        if (i > 255) {
            if (this.classFileOffset + 3 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position += 2;
            byte[] bArr = this.bCodeStream;
            int i3 = this.classFileOffset;
            int i4 = i3 + 1;
            this.classFileOffset = i4;
            bArr[i3] = Opcodes.OPC_wide;
            this.classFileOffset = i3 + 2;
            bArr[i4] = 21;
            writeUnsignedShort(i);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i5 = this.classFileOffset;
        int i6 = i5 + 1;
        this.classFileOffset = i6;
        bArr2[i5] = 21;
        this.classFileOffset = i5 + 2;
        bArr2[i6] = (byte) i;
    }

    public void iload_0() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (this.maxLocals <= 0) {
            this.maxLocals = 1;
        }
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 26;
    }

    public void iload_1() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (this.maxLocals <= 1) {
            this.maxLocals = 2;
        }
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 27;
    }

    public void iload_2() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (this.maxLocals <= 2) {
            this.maxLocals = 3;
        }
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 28;
    }

    public void iload_3() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (this.maxLocals <= 3) {
            this.maxLocals = 4;
        }
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 29;
    }

    public void imul() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 104;
    }

    public int indexOfSameLineEntrySincePC(int i, int i2) {
        int i3 = this.pcToSourceMapSize;
        while (i < i3) {
            if (this.pcToSourceMap[i + 1] == i2) {
                return i;
            }
            i += 2;
        }
        return -1;
    }

    public void ineg() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 116;
    }

    public void init(ClassFile classFile) {
        this.classFile = classFile;
        this.constantPool = classFile.constantPool;
        this.bCodeStream = classFile.contents;
        int i = classFile.contentsOffset;
        this.classFileOffset = i;
        this.startingClassFileOffset = i;
        this.pcToSourceMapSize = 0;
        this.lastEntryPC = 0;
        this.visibleLocalsCount = 0;
        this.allLocalsCounter = 0;
        this.exceptionLabelsCounter = 0;
        this.countLabels = 0;
        this.lastAbruptCompletion = -1;
        this.stackMax = 0;
        this.stackDepth = 0;
        this.maxLocals = 0;
        this.position = 0;
    }

    public void initializeMaxLocals(MethodBinding methodBinding) {
        if (methodBinding == null) {
            this.maxLocals = 0;
            return;
        }
        this.maxLocals = !methodBinding.isStatic() ? 1 : 0;
        ReferenceBinding referenceBinding = methodBinding.declaringClass;
        if (methodBinding.isConstructor() && referenceBinding.isEnum()) {
            this.maxLocals += 2;
        }
        if (methodBinding.isConstructor() && referenceBinding.isNestedType()) {
            int enclosingInstancesSlotSize = this.maxLocals + referenceBinding.getEnclosingInstancesSlotSize();
            this.maxLocals = enclosingInstancesSlotSize;
            this.maxLocals = enclosingInstancesSlotSize + referenceBinding.getOuterLocalVariablesSlotSize();
        }
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        if (typeBindingArr != null) {
            for (TypeBinding typeBinding : typeBindingArr) {
                int i = typeBinding.f626id;
                if (i == 7 || i == 8) {
                    this.maxLocals += 2;
                } else {
                    this.maxLocals++;
                }
            }
        }
    }

    public boolean inlineForwardReferencesFromLabelsTargeting(BranchLabel branchLabel, int i) {
        if (branchLabel.delegate != null) {
            return false;
        }
        int i2 = 0;
        for (int i3 = this.countLabels - 1; i3 >= 0; i3--) {
            BranchLabel branchLabel2 = this.labels[i3];
            if (branchLabel2.position != i) {
                break;
            }
            if (branchLabel2 == branchLabel || !branchLabel2.isStandardLabel()) {
                i2 |= 4;
            } else if (branchLabel2.delegate == null) {
                branchLabel.becomeDelegateFor(branchLabel2);
                i2 |= 2;
            }
        }
        return (i2 & 6) == 2;
    }

    public void instance_of(TypeBinding typeBinding) {
        instance_of(null, typeBinding);
    }

    public void instance_of(TypeReference typeReference, TypeBinding typeBinding) {
        this.countLabels = 0;
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_instanceof;
        writeUnsignedShort(this.constantPool.literalIndexForType(typeBinding));
    }

    protected void invoke(byte b, int i, int i2, char[] cArr, char[] cArr2, char[] cArr3) {
        invoke18(b, i, i2, cArr, b == -71, cArr2, cArr3);
    }

    private void invoke18(byte b, int i, int i2, char[] cArr, boolean z, char[] cArr2, char[] cArr3) {
        this.countLabels = 0;
        if (b == -71) {
            if (this.classFileOffset + 4 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position += 3;
            byte[] bArr = this.bCodeStream;
            int i3 = this.classFileOffset;
            this.classFileOffset = i3 + 1;
            bArr[i3] = b;
            writeUnsignedShort(this.constantPool.literalIndexForMethod(cArr, cArr2, cArr3, true));
            byte[] bArr2 = this.bCodeStream;
            int i4 = this.classFileOffset;
            int i5 = i4 + 1;
            this.classFileOffset = i5;
            bArr2[i4] = (byte) i;
            this.classFileOffset = i4 + 2;
            bArr2[i5] = 0;
        } else {
            if (this.classFileOffset + 2 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position++;
            byte[] bArr3 = this.bCodeStream;
            int i6 = this.classFileOffset;
            this.classFileOffset = i6 + 1;
            bArr3[i6] = b;
            writeUnsignedShort(this.constantPool.literalIndexForMethod(cArr, cArr2, cArr3, z));
        }
        int i7 = this.stackDepth + (i2 - i);
        this.stackDepth = i7;
        if (i7 > this.stackMax) {
            this.stackMax = i7;
        }
    }

    public void invokeDynamic(int i, int i2, int i3, char[] cArr, char[] cArr2) {
        invokeDynamic(i, i2, i3, cArr, cArr2, false, null, null);
    }

    public void invokeDynamic(int i, int i2, int i3, char[] cArr, char[] cArr2, boolean z, TypeReference typeReference, TypeReference[] typeReferenceArr) {
        if (this.classFileOffset + 4 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        int iLiteralIndexForInvokeDynamic = this.constantPool.literalIndexForInvokeDynamic(i, cArr, cArr2);
        this.position += 3;
        byte[] bArr = this.bCodeStream;
        int i4 = this.classFileOffset;
        this.classFileOffset = i4 + 1;
        bArr[i4] = Opcodes.OPC_invokedynamic;
        writeUnsignedShort(iLiteralIndexForInvokeDynamic);
        byte[] bArr2 = this.bCodeStream;
        int i5 = this.classFileOffset;
        int i6 = i5 + 1;
        this.classFileOffset = i6;
        bArr2[i5] = 0;
        this.classFileOffset = i5 + 2;
        bArr2[i6] = 0;
        int i7 = this.stackDepth + (i3 - i2);
        this.stackDepth = i7;
        if (i7 > this.stackMax) {
            this.stackMax = i7;
        }
    }

    public void invoke(byte b, MethodBinding methodBinding, TypeBinding typeBinding) {
        invoke(b, methodBinding, typeBinding, null);
    }

    /* JADX WARN: Code duplicated, block: B:37:0x006a  */
    /* JADX WARN: Code duplicated, block: B:38:0x006c A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:40:0x0070  */
    /* JADX WARN: Code duplicated, block: B:41:0x0073  */
    /* JADX WARN: Code duplicated, block: B:44:0x008c  */
    /* JADX WARN: Code duplicated, block: B:46:0x0094 A[ADDED_TO_REGION] */
    /* JADX WARN: Code duplicated, block: B:48:0x0099  */
    public void invoke(byte b, MethodBinding methodBinding, TypeBinding typeBinding, TypeReference[] typeReferenceArr) {
        int enclosingInstancesSlotSize;
        int length;
        int i;
        int i2;
        int i3;
        TypeBinding typeBinding2 = typeBinding == null ? methodBinding.declaringClass : typeBinding;
        if ((typeBinding2.tagBits & 2048) != 0) {
            Util.recordNestedType(this.classFile, typeBinding2);
        }
        switch (b) {
            case -73:
                if (methodBinding.isConstructor()) {
                    if (typeBinding2.isNestedType()) {
                        ReferenceBinding referenceBinding = (ReferenceBinding) typeBinding2;
                        enclosingInstancesSlotSize = referenceBinding.getEnclosingInstancesSlotSize() + 1;
                        SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticOuterLocalVariables = referenceBinding.syntheticOuterLocalVariables();
                        if (syntheticArgumentBindingArrSyntheticOuterLocalVariables != null) {
                            for (SyntheticArgumentBinding syntheticArgumentBinding : syntheticArgumentBindingArrSyntheticOuterLocalVariables) {
                                int i4 = syntheticArgumentBinding.f627id;
                                enclosingInstancesSlotSize = (i4 == 7 || i4 == 8) ? enclosingInstancesSlotSize + 2 : enclosingInstancesSlotSize + 1;
                            }
                        }
                    } else {
                        enclosingInstancesSlotSize = 1;
                    }
                    if (typeBinding2.isEnum()) {
                        enclosingInstancesSlotSize += 2;
                    }
                }
                for (length = methodBinding.parameters.length - 1; length >= 0; length--) {
                    i3 = methodBinding.parameters[length].f626id;
                    if (i3 != 7 || i3 == 8) {
                        enclosingInstancesSlotSize += 2;
                    } else {
                        enclosingInstancesSlotSize++;
                    }
                }
                i = methodBinding.returnType.f626id;
                if (i == 6) {
                    i2 = 0;
                } else if (i != 7 || i == 8) {
                    i2 = 2;
                } else {
                    i2 = 1;
                }
                invoke18(b, enclosingInstancesSlotSize, i2, typeBinding2.constantPoolName(), typeBinding2.isInterface(), methodBinding.selector, methodBinding.signature(this.classFile));
            case -74:
            case -71:
                enclosingInstancesSlotSize = 1;
                while (length >= 0) {
                    i3 = methodBinding.parameters[length].f626id;
                    if (i3 != 7) {
                        enclosingInstancesSlotSize += 2;
                    } else {
                        enclosingInstancesSlotSize += 2;
                    }
                }
                i = methodBinding.returnType.f626id;
                if (i == 6) {
                    i2 = 0;
                } else if (i != 7) {
                    i2 = 2;
                } else {
                    i2 = 2;
                }
                invoke18(b, enclosingInstancesSlotSize, i2, typeBinding2.constantPoolName(), typeBinding2.isInterface(), methodBinding.selector, methodBinding.signature(this.classFile));
                break;
            case -72:
                enclosingInstancesSlotSize = 0;
                while (length >= 0) {
                    i3 = methodBinding.parameters[length].f626id;
                    if (i3 != 7) {
                        enclosingInstancesSlotSize += 2;
                    } else {
                        enclosingInstancesSlotSize += 2;
                    }
                }
                i = methodBinding.returnType.f626id;
                if (i == 6) {
                    i2 = 0;
                } else if (i != 7) {
                    i2 = 2;
                } else {
                    i2 = 2;
                }
                invoke18(b, enclosingInstancesSlotSize, i2, typeBinding2.constantPoolName(), typeBinding2.isInterface(), methodBinding.selector, methodBinding.signature(this.classFile));
                break;
        }
    }

    protected void invokeAccessibleObjectSetAccessible() {
        invoke(Opcodes.OPC_invokevirtual, 2, 0, ConstantPool.JAVALANGREFLECTACCESSIBLEOBJECT_CONSTANTPOOLNAME, ConstantPool.SETACCESSIBLE_NAME, ConstantPool.SETACCESSIBLE_SIGNATURE);
    }

    protected void invokeArrayNewInstance() {
        invoke(Opcodes.OPC_invokestatic, 2, 1, ConstantPool.JAVALANGREFLECTARRAY_CONSTANTPOOLNAME, ConstantPool.NewInstance, ConstantPool.NewInstanceSignature);
    }

    public void invokeClassForName() {
        invoke(Opcodes.OPC_invokestatic, 1, 1, ConstantPool.JavaLangClassConstantPoolName, ConstantPool.ForName, ConstantPool.ForNameSignature);
    }

    protected void invokeClassGetDeclaredConstructor() {
        invoke(Opcodes.OPC_invokevirtual, 2, 1, ConstantPool.JavaLangClassConstantPoolName, ConstantPool.GETDECLAREDCONSTRUCTOR_NAME, ConstantPool.GETDECLAREDCONSTRUCTOR_SIGNATURE);
    }

    protected void invokeClassGetDeclaredField() {
        invoke(Opcodes.OPC_invokevirtual, 2, 1, ConstantPool.JavaLangClassConstantPoolName, ConstantPool.GETDECLAREDFIELD_NAME, ConstantPool.GETDECLAREDFIELD_SIGNATURE);
    }

    protected void invokeClassGetDeclaredMethod() {
        invoke(Opcodes.OPC_invokevirtual, 3, 1, ConstantPool.JavaLangClassConstantPoolName, ConstantPool.GETDECLAREDMETHOD_NAME, ConstantPool.GETDECLAREDMETHOD_SIGNATURE);
    }

    public void invokeEnumOrdinal(char[] cArr) {
        invoke(Opcodes.OPC_invokevirtual, 1, 1, cArr, ConstantPool.Ordinal, ConstantPool.OrdinalSignature);
    }

    public void invokeIterableIterator(TypeBinding typeBinding) {
        if ((typeBinding.tagBits & 2048) != 0) {
            Util.recordNestedType(this.classFile, typeBinding);
        }
        invoke(typeBinding.isInterface() ? Opcodes.OPC_invokeinterface : Opcodes.OPC_invokevirtual, 1, 1, typeBinding.constantPoolName(), ConstantPool.ITERATOR_NAME, ConstantPool.ITERATOR_SIGNATURE);
    }

    public void invokeAutoCloseableClose(TypeBinding typeBinding) {
        invoke(typeBinding.erasure().isInterface() ? Opcodes.OPC_invokeinterface : Opcodes.OPC_invokevirtual, 1, 0, typeBinding.constantPoolName(), ConstantPool.Close, ConstantPool.CloseSignature);
    }

    public void invokeThrowableAddSuppressed() {
        invoke(Opcodes.OPC_invokevirtual, 2, 0, ConstantPool.JavaLangThrowableConstantPoolName, ConstantPool.AddSuppressed, ConstantPool.AddSuppressedSignature);
    }

    public void invokeJavaLangAssertionErrorConstructor(int i) {
        char[] cArr;
        char[] cArr2;
        char[] cArr3;
        int i2;
        switch (i) {
            case 1:
            case 11:
            case 12:
                cArr = ConstantPool.ObjectConstrSignature;
                cArr3 = cArr;
                i2 = 2;
                invoke(Opcodes.OPC_invokespecial, i2, 0, ConstantPool.JavaLangAssertionErrorConstantPoolName, ConstantPool.Init, cArr3);
                break;
            case 2:
                cArr = ConstantPool.CharConstrSignature;
                cArr3 = cArr;
                i2 = 2;
                invoke(Opcodes.OPC_invokespecial, i2, 0, ConstantPool.JavaLangAssertionErrorConstantPoolName, ConstantPool.Init, cArr3);
                break;
            case 3:
            case 4:
            case 10:
                cArr = ConstantPool.IntConstrSignature;
                cArr3 = cArr;
                i2 = 2;
                invoke(Opcodes.OPC_invokespecial, i2, 0, ConstantPool.JavaLangAssertionErrorConstantPoolName, ConstantPool.Init, cArr3);
                break;
            case 5:
                cArr = ConstantPool.BooleanConstrSignature;
                cArr3 = cArr;
                i2 = 2;
                invoke(Opcodes.OPC_invokespecial, i2, 0, ConstantPool.JavaLangAssertionErrorConstantPoolName, ConstantPool.Init, cArr3);
                break;
            case 7:
                cArr2 = ConstantPool.LongConstrSignature;
                cArr3 = cArr2;
                i2 = 3;
                invoke(Opcodes.OPC_invokespecial, i2, 0, ConstantPool.JavaLangAssertionErrorConstantPoolName, ConstantPool.Init, cArr3);
                break;
            case 8:
                cArr2 = ConstantPool.DoubleConstrSignature;
                cArr3 = cArr2;
                i2 = 3;
                invoke(Opcodes.OPC_invokespecial, i2, 0, ConstantPool.JavaLangAssertionErrorConstantPoolName, ConstantPool.Init, cArr3);
                break;
            case 9:
                cArr = ConstantPool.FloatConstrSignature;
                cArr3 = cArr;
                i2 = 2;
                invoke(Opcodes.OPC_invokespecial, i2, 0, ConstantPool.JavaLangAssertionErrorConstantPoolName, ConstantPool.Init, cArr3);
                break;
        }
    }

    public void invokeJavaLangAssertionErrorDefaultConstructor() {
        invoke(Opcodes.OPC_invokespecial, 1, 0, ConstantPool.JavaLangAssertionErrorConstantPoolName, ConstantPool.Init, ConstantPool.DefaultConstructorSignature);
    }

    public void invokeJavaLangClassDesiredAssertionStatus() {
        invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangClassConstantPoolName, ConstantPool.DesiredAssertionStatus, ConstantPool.DesiredAssertionStatusSignature);
    }

    public void invokeJavaLangEnumvalueOf(ReferenceBinding referenceBinding) {
        invoke(Opcodes.OPC_invokestatic, 2, 1, ConstantPool.JavaLangEnumConstantPoolName, ConstantPool.ValueOf, ConstantPool.ValueOfStringClassSignature);
    }

    public void invokeJavaLangEnumValues(TypeBinding typeBinding, ArrayBinding arrayBinding) {
        invoke(Opcodes.OPC_invokestatic, 0, 1, typeBinding.constantPoolName(), TypeConstants.VALUES, CharOperation.concat("()".toCharArray(), arrayBinding.constantPoolName()));
    }

    public void invokeJavaLangErrorConstructor() {
        invoke(Opcodes.OPC_invokespecial, 2, 0, ConstantPool.JavaLangErrorConstantPoolName, ConstantPool.Init, ConstantPool.StringConstructorSignature);
    }

    public void invokeJavaLangReflectConstructorNewInstance() {
        invoke(Opcodes.OPC_invokevirtual, 2, 1, ConstantPool.JavaLangReflectConstructorConstantPoolName, ConstantPool.NewInstance, ConstantPool.JavaLangReflectConstructorNewInstanceSignature);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:3:0x0002. Please report as an issue. */
    protected void invokeJavaLangReflectFieldGetter(int i) {
        char[] cArr;
        char[] cArr2;
        char[] cArr3;
        char[] cArr4;
        char[] cArr5;
        int i2;
        char[] cArr6;
        switch (i) {
            case 2:
                cArr = ConstantPool.GET_CHAR_METHOD_NAME;
                cArr2 = ConstantPool.GET_CHAR_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 1;
                break;
            case 3:
                cArr = ConstantPool.GET_BYTE_METHOD_NAME;
                cArr2 = ConstantPool.GET_BYTE_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 1;
                break;
            case 4:
                cArr = ConstantPool.GET_SHORT_METHOD_NAME;
                cArr2 = ConstantPool.GET_SHORT_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 1;
                break;
            case 5:
                cArr = ConstantPool.GET_BOOLEAN_METHOD_NAME;
                cArr2 = ConstantPool.GET_BOOLEAN_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 1;
                break;
            case 6:
            default:
                cArr = ConstantPool.GET_OBJECT_METHOD_NAME;
                cArr2 = ConstantPool.GET_OBJECT_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 1;
                break;
            case 7:
                cArr3 = ConstantPool.GET_LONG_METHOD_NAME;
                cArr4 = ConstantPool.GET_LONG_METHOD_SIGNATURE;
                cArr5 = cArr3;
                i2 = 2;
                cArr6 = cArr4;
                break;
            case 8:
                cArr3 = ConstantPool.GET_DOUBLE_METHOD_NAME;
                cArr4 = ConstantPool.GET_DOUBLE_METHOD_SIGNATURE;
                cArr5 = cArr3;
                i2 = 2;
                cArr6 = cArr4;
                break;
            case 9:
                cArr = ConstantPool.GET_FLOAT_METHOD_NAME;
                cArr2 = ConstantPool.GET_FLOAT_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 1;
                break;
            case 10:
                cArr = ConstantPool.GET_INT_METHOD_NAME;
                cArr2 = ConstantPool.GET_INT_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 1;
                break;
        }
        invoke(Opcodes.OPC_invokevirtual, 2, i2, ConstantPool.JAVALANGREFLECTFIELD_CONSTANTPOOLNAME, cArr5, cArr6);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:3:0x0002. Please report as an issue. */
    protected void invokeJavaLangReflectFieldSetter(int i) {
        char[] cArr;
        char[] cArr2;
        char[] cArr3;
        char[] cArr4;
        char[] cArr5;
        int i2;
        char[] cArr6;
        switch (i) {
            case 2:
                cArr = ConstantPool.SET_CHAR_METHOD_NAME;
                cArr2 = ConstantPool.SET_CHAR_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 3;
                break;
            case 3:
                cArr = ConstantPool.SET_BYTE_METHOD_NAME;
                cArr2 = ConstantPool.SET_BYTE_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 3;
                break;
            case 4:
                cArr = ConstantPool.SET_SHORT_METHOD_NAME;
                cArr2 = ConstantPool.SET_SHORT_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 3;
                break;
            case 5:
                cArr = ConstantPool.SET_BOOLEAN_METHOD_NAME;
                cArr2 = ConstantPool.SET_BOOLEAN_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 3;
                break;
            case 6:
            default:
                cArr = ConstantPool.SET_OBJECT_METHOD_NAME;
                cArr2 = ConstantPool.SET_OBJECT_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 3;
                break;
            case 7:
                cArr3 = ConstantPool.SET_LONG_METHOD_NAME;
                cArr4 = ConstantPool.SET_LONG_METHOD_SIGNATURE;
                cArr5 = cArr3;
                i2 = 4;
                cArr6 = cArr4;
                break;
            case 8:
                cArr3 = ConstantPool.SET_DOUBLE_METHOD_NAME;
                cArr4 = ConstantPool.SET_DOUBLE_METHOD_SIGNATURE;
                cArr5 = cArr3;
                i2 = 4;
                cArr6 = cArr4;
                break;
            case 9:
                cArr = ConstantPool.SET_FLOAT_METHOD_NAME;
                cArr2 = ConstantPool.SET_FLOAT_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 3;
                break;
            case 10:
                cArr = ConstantPool.SET_INT_METHOD_NAME;
                cArr2 = ConstantPool.SET_INT_METHOD_SIGNATURE;
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 3;
                break;
        }
        invoke(Opcodes.OPC_invokevirtual, i2, 0, ConstantPool.JAVALANGREFLECTFIELD_CONSTANTPOOLNAME, cArr5, cArr6);
    }

    public void invokeJavaLangReflectMethodInvoke() {
        invoke(Opcodes.OPC_invokevirtual, 3, 1, ConstantPool.JAVALANGREFLECTMETHOD_CONSTANTPOOLNAME, ConstantPool.INVOKE_METHOD_METHOD_NAME, ConstantPool.INVOKE_METHOD_METHOD_SIGNATURE);
    }

    public void invokeJavaUtilIteratorHasNext() {
        invoke(Opcodes.OPC_invokeinterface, 1, 1, ConstantPool.JavaUtilIteratorConstantPoolName, ConstantPool.HasNext, ConstantPool.HasNextSignature);
    }

    public void invokeJavaUtilIteratorNext() {
        invoke(Opcodes.OPC_invokeinterface, 1, 1, ConstantPool.JavaUtilIteratorConstantPoolName, ConstantPool.Next, ConstantPool.NextSignature);
    }

    public void invokeNoClassDefFoundErrorStringConstructor() {
        invoke(Opcodes.OPC_invokespecial, 2, 0, ConstantPool.JavaLangNoClassDefFoundErrorConstantPoolName, ConstantPool.Init, ConstantPool.StringConstructorSignature);
    }

    public void invokeObjectGetClass() {
        invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangObjectConstantPoolName, ConstantPool.GetClass, ConstantPool.GetClassSignature);
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:3:0x0007. Please report as an issue. */
    public void invokeStringConcatenationAppendForType(int i) {
        char[] cArr;
        char[] cArr2;
        char[] cArr3;
        char[] cArr4;
        char[] cArr5;
        int i2;
        char[] cArr6;
        char[] cArr7 = ConstantPool.Append;
        switch (i) {
            case 2:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    cArr = ConstantPool.JavaLangStringBuilderConstantPoolName;
                    cArr2 = ConstantPool.StringBuilderAppendCharSignature;
                } else {
                    cArr = ConstantPool.JavaLangStringBufferConstantPoolName;
                    cArr2 = ConstantPool.StringBufferAppendCharSignature;
                }
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 2;
                break;
            case 3:
            case 4:
            case 10:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    cArr = ConstantPool.JavaLangStringBuilderConstantPoolName;
                    cArr2 = ConstantPool.StringBuilderAppendIntSignature;
                } else {
                    cArr = ConstantPool.JavaLangStringBufferConstantPoolName;
                    cArr2 = ConstantPool.StringBufferAppendIntSignature;
                }
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 2;
                break;
            case 5:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    cArr = ConstantPool.JavaLangStringBuilderConstantPoolName;
                    cArr2 = ConstantPool.StringBuilderAppendBooleanSignature;
                } else {
                    cArr = ConstantPool.JavaLangStringBufferConstantPoolName;
                    cArr2 = ConstantPool.StringBufferAppendBooleanSignature;
                }
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 2;
                break;
            case 6:
            default:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    cArr = ConstantPool.JavaLangStringBuilderConstantPoolName;
                    cArr2 = ConstantPool.StringBuilderAppendObjectSignature;
                } else {
                    cArr = ConstantPool.JavaLangStringBufferConstantPoolName;
                    cArr2 = ConstantPool.StringBufferAppendObjectSignature;
                }
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 2;
                break;
            case 7:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    cArr3 = ConstantPool.JavaLangStringBuilderConstantPoolName;
                    cArr4 = ConstantPool.StringBuilderAppendLongSignature;
                } else {
                    cArr3 = ConstantPool.JavaLangStringBufferConstantPoolName;
                    cArr4 = ConstantPool.StringBufferAppendLongSignature;
                }
                cArr5 = cArr3;
                i2 = 3;
                cArr6 = cArr4;
                break;
            case 8:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    cArr3 = ConstantPool.JavaLangStringBuilderConstantPoolName;
                    cArr4 = ConstantPool.StringBuilderAppendDoubleSignature;
                } else {
                    cArr3 = ConstantPool.JavaLangStringBufferConstantPoolName;
                    cArr4 = ConstantPool.StringBufferAppendDoubleSignature;
                }
                cArr5 = cArr3;
                i2 = 3;
                cArr6 = cArr4;
                break;
            case 9:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    cArr = ConstantPool.JavaLangStringBuilderConstantPoolName;
                    cArr2 = ConstantPool.StringBuilderAppendFloatSignature;
                } else {
                    cArr = ConstantPool.JavaLangStringBufferConstantPoolName;
                    cArr2 = ConstantPool.StringBufferAppendFloatSignature;
                }
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 2;
                break;
            case 11:
                if (this.targetLevel >= ClassFileConstants.JDK1_5) {
                    cArr = ConstantPool.JavaLangStringBuilderConstantPoolName;
                    cArr2 = ConstantPool.StringBuilderAppendStringSignature;
                } else {
                    cArr = ConstantPool.JavaLangStringBufferConstantPoolName;
                    cArr2 = ConstantPool.StringBufferAppendStringSignature;
                }
                cArr5 = cArr;
                cArr6 = cArr2;
                i2 = 2;
                break;
        }
        invoke(Opcodes.OPC_invokevirtual, i2, 1, cArr5, cArr7, cArr6);
    }

    public void invokeStringConcatenationDefaultConstructor() {
        char[] cArr;
        if (this.targetLevel < ClassFileConstants.JDK1_5) {
            cArr = ConstantPool.JavaLangStringBufferConstantPoolName;
        } else {
            cArr = ConstantPool.JavaLangStringBuilderConstantPoolName;
        }
        invoke(Opcodes.OPC_invokespecial, 1, 0, cArr, ConstantPool.Init, ConstantPool.DefaultConstructorSignature);
    }

    public void invokeStringConcatenationStringConstructor() {
        char[] cArr;
        if (this.targetLevel < ClassFileConstants.JDK1_5) {
            cArr = ConstantPool.JavaLangStringBufferConstantPoolName;
        } else {
            cArr = ConstantPool.JavaLangStringBuilderConstantPoolName;
        }
        invoke(Opcodes.OPC_invokespecial, 2, 0, cArr, ConstantPool.Init, ConstantPool.StringConstructorSignature);
    }

    public void invokeStringConcatenationToString() {
        char[] cArr;
        if (this.targetLevel < ClassFileConstants.JDK1_5) {
            cArr = ConstantPool.JavaLangStringBufferConstantPoolName;
        } else {
            cArr = ConstantPool.JavaLangStringBuilderConstantPoolName;
        }
        invoke(Opcodes.OPC_invokevirtual, 1, 1, cArr, ConstantPool.ToString, ConstantPool.ToStringSignature);
    }

    public void invokeStringEquals() {
        invoke(Opcodes.OPC_invokevirtual, 2, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.Equals, ConstantPool.EqualsSignature);
    }

    public void invokeObjectEquals() {
        invoke(Opcodes.OPC_invokevirtual, 2, 1, ConstantPool.JavaLangObjectConstantPoolName, ConstantPool.Equals, ConstantPool.EqualsSignature);
    }

    public void invokeStringHashCode() {
        invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.HashCode, ConstantPool.HashCodeSignature);
    }

    public void invokeStringIntern() {
        invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.Intern, ConstantPool.InternSignature);
    }

    public void invokeStringValueOf(int i) {
        char[] cArr;
        char[] cArr2;
        char[] cArr3;
        int i2;
        switch (i) {
            case 0:
            case 1:
            case 11:
            case 12:
                cArr = ConstantPool.ValueOfObjectSignature;
                cArr3 = cArr;
                i2 = 1;
                invoke(Opcodes.OPC_invokestatic, i2, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.ValueOf, cArr3);
                break;
            case 2:
                cArr = ConstantPool.ValueOfCharSignature;
                cArr3 = cArr;
                i2 = 1;
                invoke(Opcodes.OPC_invokestatic, i2, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.ValueOf, cArr3);
                break;
            case 3:
            case 4:
            case 10:
                cArr = ConstantPool.ValueOfIntSignature;
                cArr3 = cArr;
                i2 = 1;
                invoke(Opcodes.OPC_invokestatic, i2, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.ValueOf, cArr3);
                break;
            case 5:
                cArr = ConstantPool.ValueOfBooleanSignature;
                cArr3 = cArr;
                i2 = 1;
                invoke(Opcodes.OPC_invokestatic, i2, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.ValueOf, cArr3);
                break;
            case 7:
                cArr2 = ConstantPool.ValueOfLongSignature;
                cArr3 = cArr2;
                i2 = 2;
                invoke(Opcodes.OPC_invokestatic, i2, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.ValueOf, cArr3);
                break;
            case 8:
                cArr2 = ConstantPool.ValueOfDoubleSignature;
                cArr3 = cArr2;
                i2 = 2;
                invoke(Opcodes.OPC_invokestatic, i2, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.ValueOf, cArr3);
                break;
            case 9:
                cArr = ConstantPool.ValueOfFloatSignature;
                cArr3 = cArr;
                i2 = 1;
                invoke(Opcodes.OPC_invokestatic, i2, 1, ConstantPool.JavaLangStringConstantPoolName, ConstantPool.ValueOf, cArr3);
                break;
        }
    }

    public void invokeSystemArraycopy() {
        invoke(Opcodes.OPC_invokestatic, 5, 0, ConstantPool.JavaLangSystemConstantPoolName, ConstantPool.ArrayCopy, ConstantPool.ArrayCopySignature);
    }

    public void invokeThrowableGetMessage() {
        invoke(Opcodes.OPC_invokevirtual, 1, 1, ConstantPool.JavaLangThrowableConstantPoolName, ConstantPool.GetMessage, ConstantPool.GetMessageSignature);
    }

    public void ior() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = -128;
    }

    public void irem() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 112;
    }

    public void ireturn() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        int i = this.position + 1;
        this.position = i;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = Opcodes.OPC_ireturn;
        this.lastAbruptCompletion = i;
    }

    public boolean isDefinitelyAssigned(Scope scope, int i, LocalVariableBinding localVariableBinding) {
        int i2;
        if ((localVariableBinding.tagBits & 1024) != 0) {
            return true;
        }
        if (i == -1) {
            return false;
        }
        int i3 = localVariableBinding.f627id + this.maxFieldCount;
        MethodScope methodScope = scope.methodScope();
        if (i3 < 64) {
            return ((1 << i3) & methodScope.definiteInits[i]) != 0;
        }
        long[] jArr = methodScope.extraDefiniteInits[i];
        if (jArr != null && (i2 = (i3 / 64) - 1) < jArr.length) {
            return ((1 << (i3 % 64)) & jArr[i2]) != 0;
        }
        return false;
    }

    public void ishl() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 120;
    }

    public void ishr() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 122;
    }

    public void istore(int i) {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals <= i) {
            this.maxLocals = i + 1;
        }
        if (i > 255) {
            if (this.classFileOffset + 3 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position += 2;
            byte[] bArr = this.bCodeStream;
            int i2 = this.classFileOffset;
            int i3 = i2 + 1;
            this.classFileOffset = i3;
            bArr[i2] = Opcodes.OPC_wide;
            this.classFileOffset = i2 + 2;
            bArr[i3] = 54;
            writeUnsignedShort(i);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i4 = this.classFileOffset;
        int i5 = i4 + 1;
        this.classFileOffset = i5;
        bArr2[i4] = 54;
        this.classFileOffset = i4 + 2;
        bArr2[i5] = (byte) i;
    }

    public void istore_0() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals == 0) {
            this.maxLocals = 1;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 59;
    }

    public void istore_1() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals <= 1) {
            this.maxLocals = 2;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 60;
    }

    public void istore_2() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals <= 2) {
            this.maxLocals = 3;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 61;
    }

    public void istore_3() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.maxLocals <= 3) {
            this.maxLocals = 4;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 62;
    }

    public void isub() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 100;
    }

    public void iushr() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 124;
    }

    public void ixor() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_ixor;
    }

    public final void jsr(BranchLabel branchLabel) {
        if (this.wideMode) {
            jsr_w(branchLabel);
            return;
        }
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_jsr;
        branchLabel.branch();
    }

    public final void jsr_w(BranchLabel branchLabel) {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_jsr_w;
        branchLabel.branchWide();
    }

    public void l2d() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_l2d;
    }

    public void l2f() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_l2f;
    }

    public void l2i() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_l2i;
    }

    public void ladd() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 97;
    }

    public void laload() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 47;
    }

    public void land() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 127;
    }

    public void lastore() {
        this.countLabels = 0;
        this.stackDepth -= 4;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 80;
    }

    public void lcmp() {
        this.countLabels = 0;
        this.stackDepth -= 3;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_lcmp;
    }

    public void lconst_0() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 9;
    }

    public void lconst_1() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 10;
    }

    public void ldc(float f) {
        this.countLabels = 0;
        int iLiteralIndex = this.constantPool.literalIndex(f);
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (iLiteralIndex > 255) {
            if (this.classFileOffset + 2 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position++;
            byte[] bArr = this.bCodeStream;
            int i2 = this.classFileOffset;
            this.classFileOffset = i2 + 1;
            bArr[i2] = 19;
            writeUnsignedShort(iLiteralIndex);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i3 = this.classFileOffset;
        int i4 = i3 + 1;
        this.classFileOffset = i4;
        bArr2[i3] = 18;
        this.classFileOffset = i3 + 2;
        bArr2[i4] = (byte) iLiteralIndex;
    }

    public void ldc(int i) {
        this.countLabels = 0;
        int iLiteralIndex = this.constantPool.literalIndex(i);
        int i2 = this.stackDepth + 1;
        this.stackDepth = i2;
        if (i2 > this.stackMax) {
            this.stackMax = i2;
        }
        if (iLiteralIndex > 255) {
            if (this.classFileOffset + 2 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position++;
            byte[] bArr = this.bCodeStream;
            int i3 = this.classFileOffset;
            this.classFileOffset = i3 + 1;
            bArr[i3] = 19;
            writeUnsignedShort(iLiteralIndex);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i4 = this.classFileOffset;
        int i5 = i4 + 1;
        this.classFileOffset = i5;
        bArr2[i4] = 18;
        this.classFileOffset = i4 + 2;
        bArr2[i5] = (byte) iLiteralIndex;
    }

    public void ldc(String str) {
        this.countLabels = 0;
        int i = this.position;
        char[] charArray = str.toCharArray();
        int iLiteralIndexForLdc = this.constantPool.literalIndexForLdc(charArray);
        if (iLiteralIndexForLdc > 0) {
            ldcForIndex(iLiteralIndexForLdc);
            return;
        }
        this.position = i;
        int length = str.length();
        int i2 = 65535;
        byte[] bArr = new byte[Math.min(length + 100, 65535)];
        int i3 = 0;
        int i4 = 0;
        while (i3 < 65532 && i4 < length) {
            char c = charArray[i4];
            int i5 = i3 + 3;
            int length2 = bArr.length;
            if (i5 > length2) {
                byte[] bArr2 = new byte[Math.min(length2 + 100, 65535)];
                System.arraycopy(bArr, 0, bArr2, 0, i3);
                bArr = bArr2;
            }
            if (c >= 1 && c <= 127) {
                bArr[i3] = (byte) c;
                i3++;
            } else if (c > 2047) {
                bArr[i3] = (byte) (((c >> '\f') & 15) | 224);
                int i6 = i3 + 2;
                bArr[i3 + 1] = (byte) (((c >> 6) & 63) | 128);
                i3 += 3;
                bArr[i6] = (byte) ((c & MangleConstant.Q_MARK) | 128);
            } else {
                int i7 = i3 + 1;
                bArr[i3] = (byte) (((c >> 6) & 31) | 192);
                i3 += 2;
                bArr[i7] = (byte) ((c & MangleConstant.Q_MARK) | 128);
            }
            i4++;
        }
        newStringContatenation();
        dup();
        char[] cArr = new char[i4];
        System.arraycopy(charArray, 0, cArr, 0, i4);
        byte[] bArr3 = new byte[i3];
        System.arraycopy(bArr, 0, bArr3, 0, i3);
        ldcForIndex(this.constantPool.literalIndex(cArr, bArr3));
        invokeStringConcatenationStringConstructor();
        while (i4 < length) {
            byte[] bArr4 = new byte[Math.min((length - i4) + 100, i2)];
            int i8 = 0;
            int i9 = i4;
            while (i8 < 65532 && i9 < length) {
                char c2 = charArray[i9];
                int i10 = i8 + 3;
                int length3 = bArr4.length;
                if (i10 > length3) {
                    byte[] bArr5 = new byte[Math.min(length3 + 100, i2)];
                    System.arraycopy(bArr4, 0, bArr5, 0, i8);
                    bArr4 = bArr5;
                }
                if (c2 >= 1 && c2 <= 127) {
                    bArr4[i8] = (byte) c2;
                    i8++;
                } else if (c2 > 2047) {
                    bArr4[i8] = (byte) (((c2 >> '\f') & 15) | 224);
                    int i11 = i8 + 2;
                    bArr4[i8 + 1] = (byte) (((c2 >> 6) & 63) | 128);
                    i8 += 3;
                    bArr4[i11] = (byte) ((c2 & MangleConstant.Q_MARK) | 128);
                } else {
                    int i12 = i8 + 1;
                    bArr4[i8] = (byte) (((c2 >> 6) & 31) | 192);
                    i8 += 2;
                    bArr4[i12] = (byte) ((c2 & MangleConstant.Q_MARK) | 128);
                }
                i9++;
                i2 = 65535;
            }
            int i13 = i9 - i4;
            char[] cArr2 = new char[i13];
            System.arraycopy(charArray, i4, cArr2, 0, i13);
            byte[] bArr6 = new byte[i8];
            System.arraycopy(bArr4, 0, bArr6, 0, i8);
            ldcForIndex(this.constantPool.literalIndex(cArr2, bArr6));
            invokeStringConcatenationAppendForType(11);
            i4 = i9;
            i2 = 65535;
        }
        invokeStringConcatenationToString();
        invokeStringIntern();
    }

    public void ldc(TypeBinding typeBinding) {
        this.countLabels = 0;
        int iLiteralIndexForType = this.constantPool.literalIndexForType(typeBinding);
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (iLiteralIndexForType > 255) {
            if (this.classFileOffset + 2 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position++;
            byte[] bArr = this.bCodeStream;
            int i2 = this.classFileOffset;
            this.classFileOffset = i2 + 1;
            bArr[i2] = 19;
            writeUnsignedShort(iLiteralIndexForType);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i3 = this.classFileOffset;
        int i4 = i3 + 1;
        this.classFileOffset = i4;
        bArr2[i3] = 18;
        this.classFileOffset = i3 + 2;
        bArr2[i4] = (byte) iLiteralIndexForType;
    }

    public void ldc2_w(double d) {
        this.countLabels = 0;
        int iLiteralIndex = this.constantPool.literalIndex(d);
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 20;
        writeUnsignedShort(iLiteralIndex);
    }

    public void ldc2_w(long j) {
        this.countLabels = 0;
        int iLiteralIndex = this.constantPool.literalIndex(j);
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 20;
        writeUnsignedShort(iLiteralIndex);
    }

    public void ldcForIndex(int i) {
        int i2 = this.stackDepth + 1;
        this.stackDepth = i2;
        if (i2 > this.stackMax) {
            this.stackMax = i2;
        }
        if (i > 255) {
            if (this.classFileOffset + 2 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position++;
            byte[] bArr = this.bCodeStream;
            int i3 = this.classFileOffset;
            this.classFileOffset = i3 + 1;
            bArr[i3] = 19;
            writeUnsignedShort(i);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i4 = this.classFileOffset;
        int i5 = i4 + 1;
        this.classFileOffset = i5;
        bArr2[i4] = 18;
        this.classFileOffset = i4 + 2;
        bArr2[i5] = (byte) i;
    }

    public void ldiv() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 109;
    }

    public void lload(int i) {
        this.countLabels = 0;
        int i2 = this.stackDepth + 2;
        this.stackDepth = i2;
        if (this.maxLocals <= i + 1) {
            this.maxLocals = i + 2;
        }
        if (i2 > this.stackMax) {
            this.stackMax = i2;
        }
        if (i > 255) {
            if (this.classFileOffset + 3 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position += 2;
            byte[] bArr = this.bCodeStream;
            int i3 = this.classFileOffset;
            int i4 = i3 + 1;
            this.classFileOffset = i4;
            bArr[i3] = Opcodes.OPC_wide;
            this.classFileOffset = i3 + 2;
            bArr[i4] = 22;
            writeUnsignedShort(i);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i5 = this.classFileOffset;
        int i6 = i5 + 1;
        this.classFileOffset = i6;
        bArr2[i5] = 22;
        this.classFileOffset = i5 + 2;
        bArr2[i6] = (byte) i;
    }

    public void lload_0() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (this.maxLocals < 2) {
            this.maxLocals = 2;
        }
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 30;
    }

    public void lload_1() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (this.maxLocals < 3) {
            this.maxLocals = 3;
        }
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 31;
    }

    public void lload_2() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (this.maxLocals < 4) {
            this.maxLocals = 4;
        }
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 32;
    }

    public void lload_3() {
        this.countLabels = 0;
        int i = this.stackDepth + 2;
        this.stackDepth = i;
        if (this.maxLocals < 5) {
            this.maxLocals = 5;
        }
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = 33;
    }

    public void lmul() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 105;
    }

    public void lneg() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 117;
    }

    public final void load(LocalVariableBinding localVariableBinding) {
        load(localVariableBinding.type, localVariableBinding.resolvedPosition);
    }

    protected final void load(TypeBinding typeBinding, int i) {
        this.countLabels = 0;
        switch (typeBinding.f626id) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
                if (i == 0) {
                    iload_0();
                } else if (i == 1) {
                    iload_1();
                } else if (i == 2) {
                    iload_2();
                } else if (i == 3) {
                    iload_3();
                } else {
                    iload(i);
                }
                break;
            case 6:
            default:
                if (i == 0) {
                    aload_0();
                } else if (i == 1) {
                    aload_1();
                } else if (i == 2) {
                    aload_2();
                } else if (i == 3) {
                    aload_3();
                } else {
                    aload(i);
                }
                break;
            case 7:
                if (i == 0) {
                    lload_0();
                } else if (i == 1) {
                    lload_1();
                } else if (i == 2) {
                    lload_2();
                } else if (i == 3) {
                    lload_3();
                } else {
                    lload(i);
                }
                break;
            case 8:
                if (i == 0) {
                    dload_0();
                } else if (i == 1) {
                    dload_1();
                } else if (i == 2) {
                    dload_2();
                } else if (i == 3) {
                    dload_3();
                } else {
                    dload(i);
                }
                break;
            case 9:
                if (i == 0) {
                    fload_0();
                } else if (i == 1) {
                    fload_1();
                } else if (i == 2) {
                    fload_2();
                } else if (i == 3) {
                    fload_3();
                } else {
                    fload(i);
                }
                break;
        }
    }

    public void lookupswitch(CaseLabel caseLabel, int[] iArr, int[] iArr2, CaseLabel[] caseLabelArr) {
        this.countLabels = 0;
        this.stackDepth--;
        int length = iArr.length;
        int i = this.position;
        caseLabel.placeInstruction();
        for (int i2 = 0; i2 < length; i2++) {
            caseLabelArr[i2].placeInstruction();
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i3 = this.classFileOffset;
        this.classFileOffset = i3 + 1;
        bArr[i3] = Opcodes.OPC_lookupswitch;
        for (int i4 = 3 - (i & 3); i4 > 0; i4--) {
            if (this.classFileOffset >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position++;
            byte[] bArr2 = this.bCodeStream;
            int i5 = this.classFileOffset;
            this.classFileOffset = i5 + 1;
            bArr2[i5] = 0;
        }
        caseLabel.branch();
        writeSignedWord(length);
        for (int i6 = 0; i6 < length; i6++) {
            writeSignedWord(iArr[iArr2[i6]]);
            caseLabelArr[iArr2[i6]].branch();
        }
    }

    public void lor() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_lor;
    }

    public void lrem() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 113;
    }

    public void lreturn() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        int i = this.position + 1;
        this.position = i;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = Opcodes.OPC_lreturn;
        this.lastAbruptCompletion = i;
    }

    public void lshl() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 121;
    }

    public void lshr() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 123;
    }

    public void lstore(int i) {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.maxLocals <= i + 1) {
            this.maxLocals = i + 2;
        }
        if (i > 255) {
            if (this.classFileOffset + 3 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position += 2;
            byte[] bArr = this.bCodeStream;
            int i2 = this.classFileOffset;
            int i3 = i2 + 1;
            this.classFileOffset = i3;
            bArr[i2] = Opcodes.OPC_wide;
            this.classFileOffset = i2 + 2;
            bArr[i3] = 55;
            writeUnsignedShort(i);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i4 = this.classFileOffset;
        int i5 = i4 + 1;
        this.classFileOffset = i5;
        bArr2[i4] = 55;
        this.classFileOffset = i4 + 2;
        bArr2[i5] = (byte) i;
    }

    public void lstore_0() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.maxLocals < 2) {
            this.maxLocals = 2;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 63;
    }

    public void lstore_1() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.maxLocals < 3) {
            this.maxLocals = 3;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 64;
    }

    public void lstore_2() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.maxLocals < 4) {
            this.maxLocals = 4;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 65;
    }

    public void lstore_3() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.maxLocals < 5) {
            this.maxLocals = 5;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 66;
    }

    public void lsub() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 101;
    }

    public void lushr() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 125;
    }

    public void lxor() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_lxor;
    }

    public void monitorenter() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_monitorenter;
    }

    public void monitorexit() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = Opcodes.OPC_monitorexit;
    }

    public void multianewarray(TypeReference typeReference, TypeBinding typeBinding, int i, ArrayAllocationExpression arrayAllocationExpression) {
        this.countLabels = 0;
        this.stackDepth += 1 - i;
        if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = Opcodes.OPC_multianewarray;
        writeUnsignedShort(this.constantPool.literalIndexForType(typeBinding));
        byte[] bArr2 = this.bCodeStream;
        int i3 = this.classFileOffset;
        this.classFileOffset = i3 + 1;
        bArr2[i3] = (byte) i;
    }

    public void new_(TypeBinding typeBinding) {
        new_(null, typeBinding);
    }

    public void new_(TypeReference typeReference, TypeBinding typeBinding) {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = -69;
        writeUnsignedShort(this.constantPool.literalIndexForType(typeBinding));
    }

    public void newarray(int i) {
        this.countLabels = 0;
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        int i3 = i2 + 1;
        this.classFileOffset = i3;
        bArr[i2] = -68;
        this.classFileOffset = i2 + 2;
        bArr[i3] = (byte) i;
    }

    public void newArray(ArrayBinding arrayBinding) {
        newArray(null, null, arrayBinding);
    }

    public void newArray(TypeReference typeReference, ArrayAllocationExpression arrayAllocationExpression, ArrayBinding arrayBinding) {
        TypeBinding typeBindingElementsType = arrayBinding.elementsType();
        switch (typeBindingElementsType.f626id) {
            case 2:
                newarray(5);
                break;
            case 3:
                newarray(8);
                break;
            case 4:
                newarray(9);
                break;
            case 5:
                newarray(4);
                break;
            case 6:
            default:
                anewarray(typeBindingElementsType);
                break;
            case 7:
                newarray(11);
                break;
            case 8:
                newarray(7);
                break;
            case 9:
                newarray(6);
                break;
            case 10:
                newarray(10);
                break;
        }
    }

    public void newJavaLangAssertionError() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = -69;
        writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangAssertionErrorConstantPoolName));
    }

    public void newJavaLangError() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = -69;
        writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangErrorConstantPoolName));
    }

    public void newNoClassDefFoundError() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = -69;
        writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangNoClassDefFoundErrorConstantPoolName));
    }

    public void newStringContatenation() {
        this.countLabels = 0;
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = -69;
        if (this.targetLevel >= ClassFileConstants.JDK1_5) {
            writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangStringBuilderConstantPoolName));
        } else {
            writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangStringBufferConstantPoolName));
        }
    }

    public void newWrapperFor(int i) {
        this.countLabels = 0;
        int i2 = this.stackDepth + 1;
        this.stackDepth = i2;
        if (i2 > this.stackMax) {
            this.stackMax = i2;
        }
        if (this.classFileOffset + 2 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i3 = this.classFileOffset;
        this.classFileOffset = i3 + 1;
        bArr[i3] = -69;
        switch (i) {
            case 2:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangCharacterConstantPoolName));
                break;
            case 3:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangByteConstantPoolName));
                break;
            case 4:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangShortConstantPoolName));
                break;
            case 5:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangBooleanConstantPoolName));
                break;
            case 6:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangVoidConstantPoolName));
                break;
            case 7:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangLongConstantPoolName));
                break;
            case 8:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangDoubleConstantPoolName));
                break;
            case 9:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangFloatConstantPoolName));
                break;
            case 10:
                writeUnsignedShort(this.constantPool.literalIndexForType(ConstantPool.JavaLangIntegerConstantPoolName));
                break;
        }
    }

    public void nop() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 0;
    }

    public void optimizeBranch(int i, BranchLabel branchLabel) {
        for (int i2 = 0; i2 < this.countLabels; i2++) {
            BranchLabel branchLabel2 = this.labels[i2];
            if (i == branchLabel2.position) {
                branchLabel2.position = this.position;
                if (branchLabel2 instanceof CaseLabel) {
                    int i3 = this.position - ((CaseLabel) branchLabel2).instructionPosition;
                    int[] iArrForwardReferences = branchLabel2.forwardReferences();
                    int iForwardReferenceCount = branchLabel2.forwardReferenceCount();
                    for (int i4 = 0; i4 < iForwardReferenceCount; i4++) {
                        writeSignedWord(iArrForwardReferences[i4], i3);
                    }
                } else {
                    int[] iArrForwardReferences2 = branchLabel2.forwardReferences();
                    int iForwardReferenceCount2 = branchLabel2.forwardReferenceCount();
                    for (int i5 = 0; i5 < iForwardReferenceCount2; i5++) {
                        writePosition(branchLabel, iArrForwardReferences2[i5]);
                    }
                }
            }
        }
    }

    public void pop() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 87;
    }

    public void pop2() {
        this.countLabels = 0;
        this.stackDepth -= 2;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 88;
    }

    public void pushExceptionOnStack(TypeBinding typeBinding) {
        this.stackDepth = 1;
        if (1 > this.stackMax) {
            this.stackMax = 1;
        }
    }

    public void pushOnStack(TypeBinding typeBinding) {
        int i = this.stackDepth + 1;
        this.stackDepth = i;
        if (i > this.stackMax) {
            this.stackMax = i;
        }
    }

    public void record(LocalVariableBinding localVariableBinding) {
        if ((this.generateAttributes & 28) == 0) {
            return;
        }
        int i = this.allLocalsCounter;
        LocalVariableBinding[] localVariableBindingArr = this.locals;
        if (i == localVariableBindingArr.length) {
            LocalVariableBinding[] localVariableBindingArr2 = new LocalVariableBinding[i + 10];
            this.locals = localVariableBindingArr2;
            System.arraycopy(localVariableBindingArr, 0, localVariableBindingArr2, 0, i);
        }
        LocalVariableBinding[] localVariableBindingArr3 = this.locals;
        int i2 = this.allLocalsCounter;
        this.allLocalsCounter = i2 + 1;
        localVariableBindingArr3[i2] = localVariableBinding;
        localVariableBinding.initializationPCs = new int[4];
        localVariableBinding.initializationCount = 0;
    }

    public void recordPositionsFrom(int i, int i2) {
        recordPositionsFrom(i, i2, false);
    }

    /* JADX WARN: Code duplicated, block: B:108:0x01b2  */
    /* JADX WARN: Code duplicated, block: B:138:0x0249  */
    /* JADX WARN: Code duplicated, block: B:166:0x02d6  */
    /* JADX WARN: Code duplicated, block: B:184:0x0334  */
    /* JADX WARN: Code duplicated, block: B:34:0x0075  */
    /* JADX WARN: Code duplicated, block: B:60:0x00e5  */
    /* JADX WARN: Code duplicated, block: B:85:0x014d  */
    public void recordPositionsFrom(int i, int i2, boolean z) {
        int iInsertionIndex;
        int iInsertionIndex2;
        int iInsertionIndex3;
        if ((this.generateAttributes & 2) == 0 || i2 == 0) {
            return;
        }
        int i3 = this.position;
        if ((i != i3 || z) && i <= i3) {
            int i4 = this.pcToSourceMapSize;
            int i5 = i4 + 4;
            int[] iArr = this.pcToSourceMap;
            if (i5 > iArr.length) {
                int[] iArr2 = new int[i4 << 1];
                this.pcToSourceMap = iArr2;
                System.arraycopy(iArr, 0, iArr2, 0, i4);
            }
            int i6 = this.pcToSourceMapSize;
            if (i6 > 0) {
                int[] iArr3 = this.pcToSourceMap;
                int i7 = iArr3[i6 - 1];
                int lineNumber = this.lineNumberStart;
                int i8 = this.lineNumberEnd;
                if (lineNumber != i8) {
                    int[] iArr4 = this.lineSeparatorPositions;
                    int length = iArr4.length;
                    if (i7 == 1) {
                        if (i2 < iArr4[0]) {
                            if (i < iArr3[i6 - 2] && (iInsertionIndex3 = insertionIndex(iArr3, i6, i)) != -1 && (iInsertionIndex3 <= 1 || this.pcToSourceMap[iInsertionIndex3 - 1] != 1)) {
                                int i9 = this.pcToSourceMapSize;
                                if (i9 > 4) {
                                    int[] iArr5 = this.pcToSourceMap;
                                    if (iArr5[i9 - 4] > i) {
                                        System.arraycopy(iArr5, iInsertionIndex3, iArr5, iInsertionIndex3 + 2, (i9 - 2) - iInsertionIndex3);
                                        int[] iArr6 = this.pcToSourceMap;
                                        iArr6[iInsertionIndex3] = i;
                                        iArr6[iInsertionIndex3 + 1] = 1;
                                    } else {
                                        this.pcToSourceMap[i9 - 2] = i;
                                    }
                                } else {
                                    this.pcToSourceMap[i9 - 2] = i;
                                }
                            }
                            this.lastEntryPC = this.position;
                            return;
                        }
                        if (length == 1 || i2 < iArr4[1]) {
                            if (i <= this.lastEntryPC) {
                                int iInsertionIndex4 = insertionIndex(iArr3, i6, i);
                                if (iInsertionIndex4 != -1) {
                                    int iIndexOfSameLineEntrySincePC = indexOfSameLineEntrySincePC(i, 2);
                                    if (iIndexOfSameLineEntrySincePC != -1) {
                                        this.pcToSourceMap[iIndexOfSameLineEntrySincePC] = i;
                                    } else if (iInsertionIndex4 < 1 || this.pcToSourceMap[iInsertionIndex4 - 1] != 2) {
                                        int[] iArr7 = this.pcToSourceMap;
                                        System.arraycopy(iArr7, iInsertionIndex4, iArr7, iInsertionIndex4 + 2, this.pcToSourceMapSize - iInsertionIndex4);
                                        int[] iArr8 = this.pcToSourceMap;
                                        iArr8[iInsertionIndex4] = i;
                                        iArr8[iInsertionIndex4 + 1] = 2;
                                        this.pcToSourceMapSize += 2;
                                    }
                                } else {
                                    int i10 = this.position;
                                    int i11 = this.lastEntryPC;
                                    if (i10 == i11) {
                                        int[] iArr9 = this.pcToSourceMap;
                                        int i12 = this.pcToSourceMapSize;
                                        if (iArr9[i12 - 1] < 2 && z) {
                                            iArr9[i12 - 1] = 2;
                                        }
                                    } else if (i11 != i) {
                                        int[] iArr10 = this.pcToSourceMap;
                                        int i13 = this.pcToSourceMapSize;
                                        if (i11 == iArr10[i13 - 2]) {
                                            this.pcToSourceMap[this.pcToSourceMapSize - 1] = 2;
                                        } else {
                                            int i14 = i13 + 1;
                                            this.pcToSourceMapSize = i14;
                                            iArr10[i13] = i11;
                                            this.pcToSourceMapSize = i13 + 2;
                                            iArr10[i14] = 2;
                                        }
                                    } else {
                                        this.pcToSourceMap[this.pcToSourceMapSize - 1] = 2;
                                    }
                                }
                            } else {
                                int i15 = i6 + 1;
                                this.pcToSourceMapSize = i15;
                                iArr3[i6] = i;
                                this.pcToSourceMapSize = i6 + 2;
                                iArr3[i15] = 2;
                            }
                            this.lastEntryPC = this.position;
                            return;
                        }
                        lineNumber = Util.getLineNumber(i2, iArr4, lineNumber - 1, i8 - 1);
                    } else if (i7 < length) {
                        if (iArr4[i7 - 2] < i2) {
                            if (i2 < iArr4[i7 - 1]) {
                                if (i < iArr3[i6 - 2] && (iInsertionIndex2 = insertionIndex(iArr3, i6, i)) != -1 && (iInsertionIndex2 <= 1 || this.pcToSourceMap[iInsertionIndex2 - 1] != i7)) {
                                    int i16 = this.pcToSourceMapSize;
                                    if (i16 > 4) {
                                        int[] iArr11 = this.pcToSourceMap;
                                        if (iArr11[i16 - 4] > i) {
                                            System.arraycopy(iArr11, iInsertionIndex2, iArr11, iInsertionIndex2 + 2, (i16 - 2) - iInsertionIndex2);
                                            int[] iArr12 = this.pcToSourceMap;
                                            iArr12[iInsertionIndex2] = i;
                                            iArr12[iInsertionIndex2 + 1] = i7;
                                        } else {
                                            this.pcToSourceMap[i16 - 2] = i;
                                        }
                                    } else {
                                        this.pcToSourceMap[i16 - 2] = i;
                                    }
                                }
                                this.lastEntryPC = this.position;
                                return;
                            }
                            if (i2 < iArr4[i7]) {
                                int i17 = i7 + 1;
                                if (i <= this.lastEntryPC) {
                                    int iInsertionIndex5 = insertionIndex(iArr3, i6, i);
                                    if (iInsertionIndex5 != -1) {
                                        int iIndexOfSameLineEntrySincePC2 = indexOfSameLineEntrySincePC(i, i17);
                                        if (iIndexOfSameLineEntrySincePC2 != -1) {
                                            this.pcToSourceMap[iIndexOfSameLineEntrySincePC2] = i;
                                        } else if (iInsertionIndex5 < 1 || this.pcToSourceMap[iInsertionIndex5 - 1] != i17) {
                                            int[] iArr13 = this.pcToSourceMap;
                                            System.arraycopy(iArr13, iInsertionIndex5, iArr13, iInsertionIndex5 + 2, this.pcToSourceMapSize - iInsertionIndex5);
                                            int[] iArr14 = this.pcToSourceMap;
                                            iArr14[iInsertionIndex5] = i;
                                            iArr14[iInsertionIndex5 + 1] = i17;
                                            this.pcToSourceMapSize += 2;
                                        }
                                    } else {
                                        int i18 = this.position;
                                        int i19 = this.lastEntryPC;
                                        if (i18 == i19) {
                                            int[] iArr15 = this.pcToSourceMap;
                                            int i20 = this.pcToSourceMapSize;
                                            if (iArr15[i20 - 1] < i17 && z) {
                                                iArr15[i20 - 1] = i17;
                                            }
                                        } else if (i19 != i) {
                                            int[] iArr16 = this.pcToSourceMap;
                                            int i21 = this.pcToSourceMapSize;
                                            if (i19 == iArr16[i21 - 2]) {
                                                this.pcToSourceMap[this.pcToSourceMapSize - 1] = i17;
                                            } else {
                                                int i22 = i21 + 1;
                                                this.pcToSourceMapSize = i22;
                                                iArr16[i21] = i19;
                                                this.pcToSourceMapSize = i21 + 2;
                                                iArr16[i22] = i17;
                                            }
                                        } else {
                                            this.pcToSourceMap[this.pcToSourceMapSize - 1] = i17;
                                        }
                                    }
                                } else {
                                    int i23 = i6 + 1;
                                    this.pcToSourceMapSize = i23;
                                    iArr3[i6] = i;
                                    this.pcToSourceMapSize = i6 + 2;
                                    iArr3[i23] = i17;
                                }
                                this.lastEntryPC = this.position;
                                return;
                            }
                            lineNumber = Util.getLineNumber(i2, iArr4, lineNumber - 1, i8 - 1);
                        } else {
                            lineNumber = Util.getLineNumber(i2, iArr4, lineNumber - 1, i8 - 1);
                        }
                    } else {
                        if (iArr4[length - 1] < i2) {
                            int i24 = length + 1;
                            if (i <= this.lastEntryPC) {
                                int iInsertionIndex6 = insertionIndex(iArr3, i6, i);
                                if (iInsertionIndex6 != -1) {
                                    int iIndexOfSameLineEntrySincePC3 = indexOfSameLineEntrySincePC(i, i24);
                                    if (iIndexOfSameLineEntrySincePC3 != -1) {
                                        this.pcToSourceMap[iIndexOfSameLineEntrySincePC3] = i;
                                    } else if (iInsertionIndex6 < 1 || this.pcToSourceMap[iInsertionIndex6 - 1] != i24) {
                                        int[] iArr17 = this.pcToSourceMap;
                                        System.arraycopy(iArr17, iInsertionIndex6, iArr17, iInsertionIndex6 + 2, this.pcToSourceMapSize - iInsertionIndex6);
                                        int[] iArr18 = this.pcToSourceMap;
                                        iArr18[iInsertionIndex6] = i;
                                        iArr18[iInsertionIndex6 + 1] = i24;
                                        this.pcToSourceMapSize += 2;
                                    }
                                } else {
                                    int i25 = this.position;
                                    int i26 = this.lastEntryPC;
                                    if (i25 == i26) {
                                        int[] iArr19 = this.pcToSourceMap;
                                        int i27 = this.pcToSourceMapSize;
                                        if (iArr19[i27 - 1] < i24 && z) {
                                            iArr19[i27 - 1] = i24;
                                        }
                                    } else if (i26 != i) {
                                        int[] iArr20 = this.pcToSourceMap;
                                        int i28 = this.pcToSourceMapSize;
                                        if (i26 == iArr20[i28 - 2]) {
                                            this.pcToSourceMap[this.pcToSourceMapSize - 1] = i24;
                                        } else {
                                            int i29 = i28 + 1;
                                            this.pcToSourceMapSize = i29;
                                            iArr20[i28] = i26;
                                            this.pcToSourceMapSize = i28 + 2;
                                            iArr20[i29] = i24;
                                        }
                                    } else {
                                        this.pcToSourceMap[this.pcToSourceMapSize - 1] = i24;
                                    }
                                }
                            } else {
                                int i30 = i6 + 1;
                                this.pcToSourceMapSize = i30;
                                iArr3[i6] = i;
                                this.pcToSourceMapSize = i6 + 2;
                                iArr3[i30] = i24;
                            }
                            this.lastEntryPC = this.position;
                            return;
                        }
                        lineNumber = Util.getLineNumber(i2, iArr4, lineNumber - 1, i8 - 1);
                    }
                }
                if (i7 != lineNumber) {
                    if (i <= this.lastEntryPC) {
                        int iInsertionIndex7 = insertionIndex(this.pcToSourceMap, this.pcToSourceMapSize, i);
                        if (iInsertionIndex7 != -1) {
                            int iIndexOfSameLineEntrySincePC4 = indexOfSameLineEntrySincePC(i, lineNumber);
                            if (iIndexOfSameLineEntrySincePC4 != -1) {
                                this.pcToSourceMap[iIndexOfSameLineEntrySincePC4] = i;
                            } else if (iInsertionIndex7 < 1 || this.pcToSourceMap[iInsertionIndex7 - 1] != lineNumber) {
                                int[] iArr21 = this.pcToSourceMap;
                                System.arraycopy(iArr21, iInsertionIndex7, iArr21, iInsertionIndex7 + 2, this.pcToSourceMapSize - iInsertionIndex7);
                                int[] iArr22 = this.pcToSourceMap;
                                iArr22[iInsertionIndex7] = i;
                                iArr22[iInsertionIndex7 + 1] = lineNumber;
                                this.pcToSourceMapSize += 2;
                            }
                        } else {
                            int i31 = this.position;
                            int i32 = this.lastEntryPC;
                            if (i31 == i32) {
                                int[] iArr23 = this.pcToSourceMap;
                                int i33 = this.pcToSourceMapSize;
                                if (iArr23[i33 - 1] < lineNumber && z) {
                                    iArr23[i33 - 1] = lineNumber;
                                }
                            } else if (i32 != i) {
                                int[] iArr24 = this.pcToSourceMap;
                                int i34 = this.pcToSourceMapSize;
                                if (i32 == iArr24[i34 - 2]) {
                                    this.pcToSourceMap[this.pcToSourceMapSize - 1] = lineNumber;
                                } else {
                                    int i35 = i34 + 1;
                                    this.pcToSourceMapSize = i35;
                                    iArr24[i34] = i32;
                                    this.pcToSourceMapSize = i34 + 2;
                                    iArr24[i35] = lineNumber;
                                }
                            } else {
                                this.pcToSourceMap[this.pcToSourceMapSize - 1] = lineNumber;
                            }
                        }
                    } else {
                        int[] iArr25 = this.pcToSourceMap;
                        int i36 = this.pcToSourceMapSize;
                        int i37 = i36 + 1;
                        this.pcToSourceMapSize = i37;
                        iArr25[i36] = i;
                        this.pcToSourceMapSize = i36 + 2;
                        iArr25[i37] = lineNumber;
                    }
                } else {
                    int[] iArr26 = this.pcToSourceMap;
                    int i38 = this.pcToSourceMapSize;
                    if (i < iArr26[i38 - 2] && (iInsertionIndex = insertionIndex(iArr26, i38, i)) != -1 && (iInsertionIndex <= 1 || this.pcToSourceMap[iInsertionIndex - 1] != lineNumber)) {
                        int i39 = this.pcToSourceMapSize;
                        if (i39 > 4) {
                            int[] iArr27 = this.pcToSourceMap;
                            if (iArr27[i39 - 4] > i) {
                                System.arraycopy(iArr27, iInsertionIndex, iArr27, iInsertionIndex + 2, (i39 - 2) - iInsertionIndex);
                                int[] iArr28 = this.pcToSourceMap;
                                iArr28[iInsertionIndex] = i;
                                iArr28[iInsertionIndex + 1] = lineNumber;
                            } else {
                                this.pcToSourceMap[i39 - 2] = i;
                            }
                        } else {
                            this.pcToSourceMap[i39 - 2] = i;
                        }
                    }
                }
                this.lastEntryPC = this.position;
                return;
            }
            int lineNumber2 = this.lineNumberStart;
            int i40 = this.lineNumberEnd;
            if (lineNumber2 != i40) {
                lineNumber2 = Util.getLineNumber(i2, this.lineSeparatorPositions, lineNumber2 - 1, i40 - 1);
            }
            int[] iArr29 = this.pcToSourceMap;
            int i41 = this.pcToSourceMapSize;
            int i42 = i41 + 1;
            this.pcToSourceMapSize = i42;
            iArr29[i41] = i;
            this.pcToSourceMapSize = i41 + 2;
            iArr29[i42] = lineNumber2;
            this.lastEntryPC = this.position;
        }
    }

    public void registerExceptionHandler(ExceptionLabel exceptionLabel) {
        int i = this.exceptionLabelsCounter;
        ExceptionLabel[] exceptionLabelArr = this.exceptionLabels;
        int length = exceptionLabelArr.length;
        if (i == length) {
            ExceptionLabel[] exceptionLabelArr2 = new ExceptionLabel[length + 5];
            this.exceptionLabels = exceptionLabelArr2;
            System.arraycopy(exceptionLabelArr, 0, exceptionLabelArr2, 0, length);
        }
        ExceptionLabel[] exceptionLabelArr3 = this.exceptionLabels;
        int i2 = this.exceptionLabelsCounter;
        this.exceptionLabelsCounter = i2 + 1;
        exceptionLabelArr3[i2] = exceptionLabel;
    }

    public void removeNotDefinitelyAssignedVariables(Scope scope, int i) {
        if ((this.generateAttributes & 28) == 0) {
            return;
        }
        for (int i2 = 0; i2 < this.visibleLocalsCount; i2++) {
            LocalVariableBinding localVariableBinding = this.visibleLocals[i2];
            if (localVariableBinding != null && !isDefinitelyAssigned(scope, i, localVariableBinding) && localVariableBinding.initializationCount > 0) {
                localVariableBinding.recordInitializationEndPC(this.position);
            }
        }
    }

    public void removeUnusedPcToSourceMapEntries() {
        if (this.pcToSourceMapSize == 0) {
            return;
        }
        while (true) {
            int i = this.pcToSourceMapSize;
            if (i < 2 || this.pcToSourceMap[i - 2] <= this.position) {
                return;
            } else {
                this.pcToSourceMapSize = i - 2;
            }
        }
    }

    public void removeVariable(LocalVariableBinding localVariableBinding) {
        if (localVariableBinding == null) {
            return;
        }
        if (localVariableBinding.initializationCount > 0) {
            localVariableBinding.recordInitializationEndPC(this.position);
        }
        for (int i = this.visibleLocalsCount - 1; i >= 0; i--) {
            LocalVariableBinding[] localVariableBindingArr = this.visibleLocals;
            if (localVariableBindingArr[i] == localVariableBinding) {
                localVariableBindingArr[i] = null;
                return;
            }
        }
    }

    public void reset(AbstractMethodDeclaration abstractMethodDeclaration, ClassFile classFile) {
        init(classFile);
        this.methodDeclaration = abstractMethodDeclaration;
        this.lambdaExpression = null;
        int[] iArr = this.lineSeparatorPositions;
        if (iArr != null) {
            int length = iArr.length;
            int i = length - 1;
            if (abstractMethodDeclaration.isClinit() || abstractMethodDeclaration.isConstructor()) {
                this.lineNumberStart = 1;
                if (length == 0) {
                    length = 1;
                }
                this.lineNumberEnd = length;
            } else {
                int lineNumber = Util.getLineNumber(abstractMethodDeclaration.bodyStart, iArr, 0, i);
                this.lineNumberStart = lineNumber;
                if (lineNumber > i) {
                    this.lineNumberEnd = lineNumber;
                } else {
                    int lineNumber2 = Util.getLineNumber(abstractMethodDeclaration.bodyEnd, iArr, lineNumber - 1, i);
                    if (lineNumber2 < i) {
                        length = lineNumber2;
                    }
                    this.lineNumberEnd = length != 0 ? length : 1;
                }
            }
        }
        this.preserveUnusedLocals = abstractMethodDeclaration.scope.compilerOptions().preserveAllLocalVariables;
        initializeMaxLocals(abstractMethodDeclaration.binding);
    }

    public void reset(LambdaExpression lambdaExpression, ClassFile classFile) {
        init(classFile);
        this.lambdaExpression = lambdaExpression;
        this.methodDeclaration = null;
        int[] iArr = this.lineSeparatorPositions;
        if (iArr != null) {
            int length = iArr.length;
            int i = length - 1;
            int lineNumber = Util.getLineNumber(lambdaExpression.body().sourceStart, iArr, 0, i);
            this.lineNumberStart = lineNumber;
            if (lineNumber > i) {
                this.lineNumberEnd = lineNumber;
            } else {
                int lineNumber2 = Util.getLineNumber(lambdaExpression.body().sourceEnd, iArr, lineNumber - 1, i);
                if (lineNumber2 < i) {
                    length = lineNumber2;
                }
                this.lineNumberEnd = length != 0 ? length : 1;
            }
        }
        this.preserveUnusedLocals = lambdaExpression.scope.compilerOptions().preserveAllLocalVariables;
        initializeMaxLocals(lambdaExpression.binding);
    }

    public void reset(ClassFile classFile) {
        this.targetLevel = classFile.targetJDK;
        int i = classFile.produceAttributes;
        this.generateAttributes = i;
        if ((i & 2) != 0) {
            this.lineSeparatorPositions = classFile.referenceBinding.scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions();
        } else {
            this.lineSeparatorPositions = null;
        }
    }

    public void resetForProblemClinit(ClassFile classFile) {
        init(classFile);
        initializeMaxLocals(null);
    }

    public void resetInWideMode() {
        this.wideMode = true;
    }

    private final void resizeByteArray() {
        byte[] bArr = this.bCodeStream;
        int length = bArr.length;
        int i = length + length;
        int i2 = this.classFileOffset;
        if (i2 >= i) {
            i = i2 + length;
        }
        byte[] bArr2 = new byte[i];
        this.bCodeStream = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, length);
    }

    public final void ret(int i) {
        this.countLabels = 0;
        if (i > 255) {
            if (this.classFileOffset + 3 >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position += 2;
            byte[] bArr = this.bCodeStream;
            int i2 = this.classFileOffset;
            int i3 = i2 + 1;
            this.classFileOffset = i3;
            bArr[i2] = Opcodes.OPC_wide;
            this.classFileOffset = i2 + 2;
            bArr[i3] = Opcodes.OPC_ret;
            writeUnsignedShort(i);
            return;
        }
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr2 = this.bCodeStream;
        int i4 = this.classFileOffset;
        int i5 = i4 + 1;
        this.classFileOffset = i5;
        bArr2[i4] = Opcodes.OPC_ret;
        this.classFileOffset = i4 + 2;
        bArr2[i5] = (byte) i;
    }

    public void return_() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        int i = this.position + 1;
        this.position = i;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        this.classFileOffset = i2 + 1;
        bArr[i2] = Opcodes.OPC_return;
        this.lastAbruptCompletion = i;
    }

    public void saload() {
        this.countLabels = 0;
        this.stackDepth--;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 53;
    }

    public void sastore() {
        this.countLabels = 0;
        this.stackDepth -= 3;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 86;
    }

    public void sendOperator(int i, int i2) {
        switch (i2) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
                if (i == 2) {
                    iand();
                    break;
                } else if (i == 3) {
                    ior();
                    break;
                } else if (i == 19) {
                    iushr();
                    break;
                } else {
                    switch (i) {
                        case 8:
                            ixor();
                            break;
                        case 9:
                            idiv();
                            break;
                        case 10:
                            ishl();
                            break;
                        default:
                            switch (i) {
                                case 13:
                                    isub();
                                    break;
                                case 14:
                                    iadd();
                                    break;
                                case 15:
                                    imul();
                                    break;
                                case 16:
                                    irem();
                                    break;
                                case 17:
                                    ishr();
                                    break;
                            }
                            break;
                    }
                }
                break;
            case 7:
                if (i == 2) {
                    land();
                    break;
                } else if (i == 3) {
                    lor();
                    break;
                } else if (i == 19) {
                    lushr();
                    break;
                } else {
                    switch (i) {
                        case 8:
                            lxor();
                            break;
                        case 9:
                            ldiv();
                            break;
                        case 10:
                            lshl();
                            break;
                        default:
                            switch (i) {
                                case 13:
                                    lsub();
                                    break;
                                case 14:
                                    ladd();
                                    break;
                                case 15:
                                    lmul();
                                    break;
                                case 16:
                                    lrem();
                                    break;
                                case 17:
                                    lshr();
                                    break;
                            }
                            break;
                    }
                }
                break;
            case 8:
                if (i != 9) {
                    switch (i) {
                        case 13:
                            dsub();
                            break;
                        case 14:
                            dadd();
                            break;
                        case 15:
                            dmul();
                            break;
                        case 16:
                            drem();
                            break;
                    }
                } else {
                    ddiv();
                    break;
                }
                break;
            case 9:
                if (i != 9) {
                    switch (i) {
                        case 13:
                            fsub();
                            break;
                        case 14:
                            fadd();
                            break;
                        case 15:
                            fmul();
                            break;
                        case 16:
                            frem();
                            break;
                    }
                } else {
                    fdiv();
                    break;
                }
                break;
        }
    }

    public void sipush(int i) {
        this.countLabels = 0;
        int i2 = this.stackDepth + 1;
        this.stackDepth = i2;
        if (i2 > this.stackMax) {
            this.stackMax = i2;
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i3 = this.classFileOffset;
        this.classFileOffset = i3 + 1;
        bArr[i3] = 17;
        writeSignedShort(i);
    }

    public void store(LocalVariableBinding localVariableBinding, boolean z) {
        int i = localVariableBinding.resolvedPosition;
        switch (localVariableBinding.type.f626id) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 10:
                if (z) {
                    dup();
                }
                if (i == 0) {
                    istore_0();
                } else if (i == 1) {
                    istore_1();
                } else if (i == 2) {
                    istore_2();
                } else if (i == 3) {
                    istore_3();
                } else {
                    istore(i);
                }
                break;
            case 6:
            default:
                if (z) {
                    dup();
                }
                if (i == 0) {
                    astore_0();
                } else if (i == 1) {
                    astore_1();
                } else if (i == 2) {
                    astore_2();
                } else if (i == 3) {
                    astore_3();
                } else {
                    astore(i);
                }
                break;
            case 7:
                if (z) {
                    dup2();
                }
                if (i == 0) {
                    lstore_0();
                } else if (i == 1) {
                    lstore_1();
                } else if (i == 2) {
                    lstore_2();
                } else if (i == 3) {
                    lstore_3();
                } else {
                    lstore(i);
                }
                break;
            case 8:
                if (z) {
                    dup2();
                }
                if (i == 0) {
                    dstore_0();
                } else if (i == 1) {
                    dstore_1();
                } else if (i == 2) {
                    dstore_2();
                } else if (i == 3) {
                    dstore_3();
                } else {
                    dstore(i);
                }
                break;
            case 9:
                if (z) {
                    dup();
                }
                if (i == 0) {
                    fstore_0();
                } else if (i == 1) {
                    fstore_1();
                } else if (i == 2) {
                    fstore_2();
                } else if (i == 3) {
                    fstore_3();
                } else {
                    fstore(i);
                }
                break;
        }
    }

    public void swap() {
        this.countLabels = 0;
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i = this.classFileOffset;
        this.classFileOffset = i + 1;
        bArr[i] = 95;
    }

    public void tableswitch(CaseLabel caseLabel, int i, int i2, int[] iArr, int[] iArr2, CaseLabel[] caseLabelArr) {
        this.countLabels = 0;
        this.stackDepth--;
        int i3 = this.position;
        caseLabel.placeInstruction();
        for (CaseLabel caseLabel2 : caseLabelArr) {
            caseLabel2.placeInstruction();
        }
        if (this.classFileOffset >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position++;
        byte[] bArr = this.bCodeStream;
        int i4 = this.classFileOffset;
        this.classFileOffset = i4 + 1;
        bArr[i4] = Opcodes.OPC_tableswitch;
        for (int i5 = 3 - (i3 & 3); i5 > 0; i5--) {
            if (this.classFileOffset >= this.bCodeStream.length) {
                resizeByteArray();
            }
            this.position++;
            byte[] bArr2 = this.bCodeStream;
            int i6 = this.classFileOffset;
            this.classFileOffset = i6 + 1;
            bArr2[i6] = 0;
        }
        caseLabel.branch();
        writeSignedWord(i);
        writeSignedWord(i2);
        int i7 = i;
        int i8 = i7;
        while (true) {
            int i9 = iArr2[i7 - i];
            if (iArr[i9] == i8) {
                caseLabelArr[i9].branch();
                i7++;
                if (i8 == i2) {
                    return;
                }
            } else {
                caseLabel.branch();
            }
            i8++;
        }
    }

    public void throwAnyException(LocalVariableBinding localVariableBinding) {
        load(localVariableBinding);
        athrow();
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer("( position:");
        stringBuffer.append(this.position);
        stringBuffer.append(",\nstackDepth:");
        stringBuffer.append(this.stackDepth);
        stringBuffer.append(",\nmaxStack:");
        stringBuffer.append(this.stackMax);
        stringBuffer.append(",\nmaxLocals:");
        stringBuffer.append(this.maxLocals);
        stringBuffer.append(com.huawei.hms.ml.language.common.utils.Constant.AFTER_QUTO);
        return stringBuffer.toString();
    }

    protected void writePosition(BranchLabel branchLabel) {
        int i = (branchLabel.position - this.position) + 1;
        if (Math.abs(i) > 32767 && !this.wideMode) {
            throw new AbortMethod(RESTART_IN_WIDE_MODE, null);
        }
        writeSignedShort(i);
        int[] iArrForwardReferences = branchLabel.forwardReferences();
        int iForwardReferenceCount = branchLabel.forwardReferenceCount();
        for (int i2 = 0; i2 < iForwardReferenceCount; i2++) {
            writePosition(branchLabel, iArrForwardReferences[i2]);
        }
    }

    protected void writePosition(BranchLabel branchLabel, int i) {
        int i2 = (branchLabel.position - i) + 1;
        if (Math.abs(i2) > 32767 && !this.wideMode) {
            throw new AbortMethod(RESTART_IN_WIDE_MODE, null);
        }
        if (this.wideMode) {
            if ((branchLabel.tagBits & 1) != 0) {
                writeSignedWord(i, i2);
                return;
            } else {
                writeSignedShort(i, i2);
                return;
            }
        }
        writeSignedShort(i, i2);
    }

    private final void writeSignedShort(int i) {
        if (this.classFileOffset + 1 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 2;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        int i3 = i2 + 1;
        this.classFileOffset = i3;
        bArr[i2] = (byte) (i >> 8);
        this.classFileOffset = i2 + 2;
        bArr[i3] = (byte) i;
    }

    private final void writeSignedShort(int i, int i2) {
        int i3 = this.startingClassFileOffset + i;
        int i4 = i3 + 1;
        if (i4 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        byte[] bArr = this.bCodeStream;
        bArr[i3] = (byte) (i2 >> 8);
        bArr[i4] = (byte) i2;
    }

    protected final void writeSignedWord(int i) {
        if (this.classFileOffset + 3 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        this.position += 4;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        int i3 = i2 + 1;
        this.classFileOffset = i3;
        bArr[i2] = (byte) (((-16777216) & i) >> 24);
        int i4 = i2 + 2;
        this.classFileOffset = i4;
        bArr[i3] = (byte) ((16711680 & i) >> 16);
        int i5 = i2 + 3;
        this.classFileOffset = i5;
        bArr[i4] = (byte) ((65280 & i) >> 8);
        this.classFileOffset = i2 + 4;
        bArr[i5] = (byte) (i & 255);
    }

    protected void writeSignedWord(int i, int i2) {
        int i3 = this.startingClassFileOffset + i;
        if (i3 + 3 >= this.bCodeStream.length) {
            resizeByteArray();
        }
        byte[] bArr = this.bCodeStream;
        bArr[i3] = (byte) (((-16777216) & i2) >> 24);
        bArr[i3 + 1] = (byte) ((16711680 & i2) >> 16);
        bArr[i3 + 2] = (byte) ((65280 & i2) >> 8);
        bArr[i3 + 3] = (byte) (i2 & 255);
    }

    private final void writeUnsignedShort(int i) {
        this.position += 2;
        byte[] bArr = this.bCodeStream;
        int i2 = this.classFileOffset;
        int i3 = i2 + 1;
        this.classFileOffset = i3;
        bArr[i2] = (byte) (i >>> 8);
        this.classFileOffset = i2 + 2;
        bArr[i3] = (byte) i;
    }

    protected void writeWidePosition(BranchLabel branchLabel) {
        int i = branchLabel.position;
        writeSignedWord((i - this.position) + 1);
        int[] iArrForwardReferences = branchLabel.forwardReferences();
        int iForwardReferenceCount = branchLabel.forwardReferenceCount();
        for (int i2 = 0; i2 < iForwardReferenceCount; i2++) {
            int i3 = iArrForwardReferences[i2];
            writeSignedWord(i3, (i - i3) + 1);
        }
    }
}
