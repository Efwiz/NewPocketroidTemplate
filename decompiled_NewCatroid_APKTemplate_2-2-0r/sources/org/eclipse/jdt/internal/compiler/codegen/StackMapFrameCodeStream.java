package org.eclipse.jdt.internal.compiler.codegen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.Scope;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.problem.AbortMethod;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;

/* JADX INFO: loaded from: classes14.dex */
public class StackMapFrameCodeStream extends CodeStream {
    public Set exceptionMarkers;
    private HashMap framePositions;
    public ArrayList stackDepthMarkers;
    public ArrayList stackMarkers;
    public int[] stateIndexes;
    public int stateIndexesCounter;

    public static class ExceptionMarker implements Comparable {
        public char[] constantPoolName;
        public int pc;

        public ExceptionMarker(int i, char[] cArr) {
            this.pc = i;
            this.constantPoolName = cArr;
        }

        @Override // java.lang.Comparable
        public int compareTo(Object obj) {
            if (obj instanceof ExceptionMarker) {
                return this.pc - ((ExceptionMarker) obj).pc;
            }
            return 0;
        }

        public boolean equals(Object obj) {
            if (obj instanceof ExceptionMarker) {
                ExceptionMarker exceptionMarker = (ExceptionMarker) obj;
                if (this.pc == exceptionMarker.pc && CharOperation.equals(this.constantPoolName, exceptionMarker.constantPoolName)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return this.pc + this.constantPoolName.hashCode();
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append('(').append(this.pc).append(',').append(this.constantPoolName).append(')');
            return String.valueOf(stringBuffer);
        }
    }

    public static class StackDepthMarker {
        public int delta;
        public int pc;
        public TypeBinding typeBinding;

        public StackDepthMarker(int i, int i2, TypeBinding typeBinding) {
            this.pc = i;
            this.typeBinding = typeBinding;
            this.delta = i2;
        }

        public StackDepthMarker(int i, int i2) {
            this.pc = i;
            this.delta = i2;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append('(').append(this.pc).append(',').append(this.delta);
            if (this.typeBinding != null) {
                stringBuffer.append(',').append(this.typeBinding.qualifiedPackageName()).append(this.typeBinding.qualifiedSourceName());
            }
            stringBuffer.append(')');
            return String.valueOf(stringBuffer);
        }
    }

    public static class StackMarker {
        public int destinationPC;
        public VerificationTypeInfo[] infos;
        public int pc;

        public StackMarker(int i, int i2) {
            this.pc = i;
            this.destinationPC = i2;
        }

        public void setInfos(VerificationTypeInfo[] verificationTypeInfoArr) {
            this.infos = verificationTypeInfoArr;
        }

        public String toString() {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append("[copy stack items from ").append(this.pc).append(" to ").append(this.destinationPC);
            VerificationTypeInfo[] verificationTypeInfoArr = this.infos;
            if (verificationTypeInfoArr != null) {
                int length = verificationTypeInfoArr.length;
                for (int i = 0; i < length; i++) {
                    if (i > 0) {
                        stringBuffer.append(',');
                    }
                    stringBuffer.append(this.infos[i]);
                }
            }
            stringBuffer.append(']');
            return String.valueOf(stringBuffer);
        }
    }

    static class FramePosition {
        int counter;

        FramePosition() {
        }
    }

    public StackMapFrameCodeStream(ClassFile classFile) {
        super(classFile);
        this.generateAttributes |= 16;
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void addDefinitelyAssignedVariables(Scope scope, int i) {
        for (int i2 = 0; i2 < this.visibleLocalsCount; i2++) {
            LocalVariableBinding localVariableBinding = this.visibleLocals[i2];
            if (localVariableBinding != null) {
                if (!isDefinitelyAssigned(scope, i, localVariableBinding)) {
                    if (this.stateIndexes != null) {
                        int i3 = this.stateIndexesCounter;
                        for (int i4 = 0; i4 < i3; i4++) {
                            if (isDefinitelyAssigned(scope, this.stateIndexes[i4], localVariableBinding)) {
                                if (localVariableBinding.initializationCount != 0 && localVariableBinding.initializationPCs[((localVariableBinding.initializationCount - 1) << 1) + 1] == -1) {
                                    break;
                                }
                                localVariableBinding.recordInitializationStartPC(this.position);
                                break;
                            }
                        }
                    }
                } else if (localVariableBinding.initializationCount == 0 || localVariableBinding.initializationPCs[((localVariableBinding.initializationCount - 1) << 1) + 1] != -1) {
                    localVariableBinding.recordInitializationStartPC(this.position);
                }
            }
        }
    }

    public void addExceptionMarker(int i, TypeBinding typeBinding) {
        if (this.exceptionMarkers == null) {
            this.exceptionMarkers = new HashSet();
        }
        if (typeBinding == null) {
            this.exceptionMarkers.add(new ExceptionMarker(i, ConstantPool.JavaLangThrowableConstantPoolName));
            return;
        }
        int i2 = typeBinding.f626id;
        if (i2 == 7) {
            this.exceptionMarkers.add(new ExceptionMarker(i, ConstantPool.JavaLangNoSuchFieldErrorConstantPoolName));
        } else if (i2 == 12) {
            this.exceptionMarkers.add(new ExceptionMarker(i, ConstantPool.JavaLangClassNotFoundExceptionConstantPoolName));
        } else {
            this.exceptionMarkers.add(new ExceptionMarker(i, typeBinding.constantPoolName()));
        }
    }

    public void addFramePosition(int i) {
        Integer numValueOf = Integer.valueOf(i);
        FramePosition framePosition = (FramePosition) this.framePositions.get(numValueOf);
        if (framePosition != null) {
            framePosition.counter++;
        } else {
            this.framePositions.put(numValueOf, new FramePosition());
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void optimizeBranch(int i, BranchLabel branchLabel) {
        super.optimizeBranch(i, branchLabel);
        removeFramePosition(i);
    }

    public void removeFramePosition(int i) {
        Integer numValueOf = Integer.valueOf(i);
        FramePosition framePosition = (FramePosition) this.framePositions.get(numValueOf);
        if (framePosition != null) {
            framePosition.counter--;
            if (framePosition.counter <= 0) {
                this.framePositions.remove(numValueOf);
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void addVariable(LocalVariableBinding localVariableBinding) {
        if (localVariableBinding.initializationPCs == null) {
            record(localVariableBinding);
        }
        localVariableBinding.recordInitializationStartPC(this.position);
    }

    private void addStackMarker(int i, int i2) {
        ArrayList arrayList = this.stackMarkers;
        if (arrayList == null) {
            ArrayList arrayList2 = new ArrayList();
            this.stackMarkers = arrayList2;
            arrayList2.add(new StackMarker(i, i2));
        } else {
            int size = arrayList.size();
            if (size == 0 || ((StackMarker) this.stackMarkers.get(size - 1)).pc != this.position) {
                this.stackMarkers.add(new StackMarker(i, i2));
            }
        }
    }

    private void addStackDepthMarker(int i, int i2, TypeBinding typeBinding) {
        ArrayList arrayList = this.stackDepthMarkers;
        if (arrayList == null) {
            ArrayList arrayList2 = new ArrayList();
            this.stackDepthMarkers = arrayList2;
            arrayList2.add(new StackDepthMarker(i, i2, typeBinding));
            return;
        }
        int size = arrayList.size();
        if (size == 0) {
            this.stackDepthMarkers.add(new StackDepthMarker(i, i2, typeBinding));
            return;
        }
        int i3 = size - 1;
        if (((StackDepthMarker) this.stackDepthMarkers.get(i3)).pc != this.position) {
            this.stackDepthMarkers.add(new StackDepthMarker(i, i2, typeBinding));
        } else {
            this.stackDepthMarkers.set(i3, new StackDepthMarker(i, i2, typeBinding));
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void decrStackSize(int i) {
        super.decrStackSize(i);
        addStackDepthMarker(this.position, -1, null);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void recordExpressionType(TypeBinding typeBinding) {
        addStackDepthMarker(this.position, 0, typeBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
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
        int i = this.position;
        goto_(branchLabel);
        int i2 = this.stackDepth;
        pushExceptionOnStack(TypeBinding.NULL);
        exceptionLabel.place();
        newNoClassDefFoundError();
        dup_x1();
        swap();
        invokeThrowableGetMessage();
        invokeNoClassDefFoundErrorStringConstructor();
        athrow();
        branchLabel.place();
        addStackMarker(i, this.position);
        this.stackDepth = i2;
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void generateOuterAccess(Object[] objArr, ASTNode aSTNode, Binding binding, Scope scope) {
        int i = this.position;
        super.generateOuterAccess(objArr, aSTNode, binding, scope);
        if (i == this.position) {
            throw new AbortMethod(scope.referenceCompilationUnit().compilationResult, null);
        }
    }

    public ExceptionMarker[] getExceptionMarkers() {
        Set set = this.exceptionMarkers;
        if (set == null) {
            return null;
        }
        ExceptionMarker[] exceptionMarkerArr = new ExceptionMarker[set.size()];
        Iterator it = set.iterator();
        int i = 0;
        while (it.hasNext()) {
            exceptionMarkerArr[i] = (ExceptionMarker) it.next();
            i++;
        }
        Arrays.sort(exceptionMarkerArr);
        return exceptionMarkerArr;
    }

    public int[] getFramePositions() {
        Set setKeySet = this.framePositions.keySet();
        int[] iArr = new int[setKeySet.size()];
        Iterator it = setKeySet.iterator();
        int i = 0;
        while (it.hasNext()) {
            iArr[i] = ((Integer) it.next()).intValue();
            i++;
        }
        Arrays.sort(iArr);
        return iArr;
    }

    public StackDepthMarker[] getStackDepthMarkers() {
        int size;
        ArrayList arrayList = this.stackDepthMarkers;
        if (arrayList == null || (size = arrayList.size()) == 0) {
            return null;
        }
        StackDepthMarker[] stackDepthMarkerArr = new StackDepthMarker[size];
        this.stackDepthMarkers.toArray(stackDepthMarkerArr);
        return stackDepthMarkerArr;
    }

    public StackMarker[] getStackMarkers() {
        int size;
        ArrayList arrayList = this.stackMarkers;
        if (arrayList == null || (size = arrayList.size()) == 0) {
            return null;
        }
        StackMarker[] stackMarkerArr = new StackMarker[size];
        this.stackMarkers.toArray(stackMarkerArr);
        return stackMarkerArr;
    }

    public boolean hasFramePositions() {
        return this.framePositions.size() != 0;
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void init(ClassFile classFile) {
        super.init(classFile);
        this.stateIndexesCounter = 0;
        HashMap map = this.framePositions;
        if (map != null) {
            map.clear();
        }
        Set set = this.exceptionMarkers;
        if (set != null) {
            set.clear();
        }
        ArrayList arrayList = this.stackDepthMarkers;
        if (arrayList != null) {
            arrayList.clear();
        }
        ArrayList arrayList2 = this.stackMarkers;
        if (arrayList2 != null) {
            arrayList2.clear();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void initializeMaxLocals(MethodBinding methodBinding) {
        super.initializeMaxLocals(methodBinding);
        HashMap map = this.framePositions;
        if (map == null) {
            this.framePositions = new HashMap();
        } else {
            map.clear();
        }
    }

    public void popStateIndex() {
        this.stateIndexesCounter--;
    }

    public void pushStateIndex(int i) {
        if (this.stateIndexes == null) {
            this.stateIndexes = new int[3];
        }
        int[] iArr = this.stateIndexes;
        int length = iArr.length;
        if (length == this.stateIndexesCounter) {
            int[] iArr2 = new int[length * 2];
            this.stateIndexes = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, length);
        }
        int[] iArr3 = this.stateIndexes;
        int i2 = this.stateIndexesCounter;
        this.stateIndexesCounter = i2 + 1;
        iArr3[i2] = i;
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void removeNotDefinitelyAssignedVariables(Scope scope, int i) {
        int i2 = this.visibleLocalsCount;
        for (int i3 = 0; i3 < i2; i3++) {
            LocalVariableBinding localVariableBinding = this.visibleLocals[i3];
            if (localVariableBinding != null && localVariableBinding.initializationCount > 0 && !isDefinitelyAssigned(scope, i, localVariableBinding)) {
                if (this.stateIndexes != null) {
                    int i4 = this.stateIndexesCounter;
                    int i5 = 0;
                    while (true) {
                        if (i5 < i4) {
                            if (isDefinitelyAssigned(scope, this.stateIndexes[i5], localVariableBinding)) {
                                break;
                            } else {
                                i5++;
                            }
                        } else {
                            localVariableBinding.recordInitializationEndPC(this.position);
                            break;
                        }
                    }
                } else {
                    localVariableBinding.recordInitializationEndPC(this.position);
                    break;
                    break;
                }
            }
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void reset(ClassFile classFile) {
        super.reset(classFile);
        this.stateIndexesCounter = 0;
        HashMap map = this.framePositions;
        if (map != null) {
            map.clear();
        }
        Set set = this.exceptionMarkers;
        if (set != null) {
            set.clear();
        }
        ArrayList arrayList = this.stackDepthMarkers;
        if (arrayList != null) {
            arrayList.clear();
        }
        ArrayList arrayList2 = this.stackMarkers;
        if (arrayList2 != null) {
            arrayList2.clear();
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    protected void writePosition(BranchLabel branchLabel) {
        super.writePosition(branchLabel);
        addFramePosition(branchLabel.position);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    protected void writePosition(BranchLabel branchLabel, int i) {
        super.writePosition(branchLabel, i);
        addFramePosition(branchLabel.position);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    protected void writeSignedWord(int i, int i2) {
        super.writeSignedWord(i, i2);
        addFramePosition(this.position);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    protected void writeWidePosition(BranchLabel branchLabel) {
        super.writeWidePosition(branchLabel);
        addFramePosition(branchLabel.position);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void areturn() {
        super.areturn();
        addFramePosition(this.position);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void ireturn() {
        super.ireturn();
        addFramePosition(this.position);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void lreturn() {
        super.lreturn();
        addFramePosition(this.position);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void freturn() {
        super.freturn();
        addFramePosition(this.position);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void dreturn() {
        super.dreturn();
        addFramePosition(this.position);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void return_() {
        super.return_();
        addFramePosition(this.position);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void athrow() {
        super.athrow();
        addFramePosition(this.position);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void pushOnStack(TypeBinding typeBinding) {
        super.pushOnStack(typeBinding);
        addStackDepthMarker(this.position, 1, typeBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void pushExceptionOnStack(TypeBinding typeBinding) {
        super.pushExceptionOnStack(typeBinding);
        addExceptionMarker(this.position, typeBinding);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void goto_(BranchLabel branchLabel) {
        super.goto_(branchLabel);
        addFramePosition(this.position);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void goto_w(BranchLabel branchLabel) {
        super.goto_w(branchLabel);
        addFramePosition(this.position);
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void resetInWideMode() {
        resetSecretLocals();
        super.resetInWideMode();
    }

    @Override // org.eclipse.jdt.internal.compiler.codegen.CodeStream
    public void resetForCodeGenUnusedLocals() {
        resetSecretLocals();
        super.resetForCodeGenUnusedLocals();
    }

    public void resetSecretLocals() {
        int length = this.locals.length;
        for (int i = 0; i < length; i++) {
            LocalVariableBinding localVariableBinding = this.locals[i];
            if (localVariableBinding != null && localVariableBinding.isSecret()) {
                localVariableBinding.resetInitializations();
            }
        }
    }
}
