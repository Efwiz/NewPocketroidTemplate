package org.eclipse.jdt.internal.compiler.lookup;

import com.beust.jcommander.Parameters;
import net.bytebuddy.description.type.TypeDescription;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.FakedTrackingVariable;
import org.eclipse.jdt.internal.compiler.ast.Initializer;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.impl.ReferenceContext;

/* JADX INFO: loaded from: classes14.dex */
public class LocalVariableBinding extends VariableBinding {
    public static final int FAKE_USED = 2;
    public static final int UNUSED = 0;
    public static final int USED = 1;
    public FakedTrackingVariable closeTracker;
    public LocalDeclaration declaration;
    public BlockScope declaringScope;
    public int initializationCount;
    public int[] initializationPCs;
    public int resolvedPosition;
    public int useFlag;

    public boolean isCatchParameter() {
        return false;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public final int kind() {
        return 2;
    }

    public LocalVariableBinding(char[] cArr, TypeBinding typeBinding, int i, boolean z) {
        super(cArr, typeBinding, i, z ? Constant.NotAConstant : null);
        this.initializationCount = 0;
        if (z) {
            this.tagBits |= 1024;
        }
        this.tagBits |= 2048;
    }

    public LocalVariableBinding(LocalDeclaration localDeclaration, TypeBinding typeBinding, int i, boolean z) {
        this(localDeclaration.f617name, typeBinding, i, z);
        this.declaration = localDeclaration;
    }

    public LocalVariableBinding(LocalDeclaration localDeclaration, TypeBinding typeBinding, int i, MethodScope methodScope) {
        this(localDeclaration, typeBinding, i, true);
        this.declaringScope = methodScope;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public char[] computeUniqueKey(boolean z) {
        int i;
        MethodBinding methodBinding;
        StringBuffer stringBuffer = new StringBuffer();
        BlockScope blockScope = this.declaringScope;
        if (blockScope != null) {
            ReferenceContext referenceContext = (blockScope instanceof MethodScope ? (MethodScope) blockScope : blockScope.enclosingMethodScope()).referenceContext;
            if (referenceContext instanceof AbstractMethodDeclaration) {
                MethodBinding methodBinding2 = ((AbstractMethodDeclaration) referenceContext).binding;
                if (methodBinding2 != null) {
                    stringBuffer.append(methodBinding2.computeUniqueKey(false));
                }
            } else if (referenceContext instanceof TypeDeclaration) {
                SourceTypeBinding sourceTypeBinding = ((TypeDeclaration) referenceContext).binding;
                if (sourceTypeBinding != null) {
                    stringBuffer.append(sourceTypeBinding.computeUniqueKey(false));
                }
            } else if ((referenceContext instanceof LambdaExpression) && (methodBinding = ((LambdaExpression) referenceContext).binding) != null) {
                stringBuffer.append(methodBinding.computeUniqueKey(false));
            }
            getScopeKey(blockScope, stringBuffer);
            LocalVariableBinding[] localVariableBindingArr = blockScope.locals;
            i = 0;
            for (int i2 = 0; i2 < blockScope.localIndex; i2++) {
                LocalVariableBinding localVariableBinding = localVariableBindingArr[i2];
                if (CharOperation.equals(this.f628name, localVariableBinding.f628name)) {
                    if (this == localVariableBinding) {
                        break;
                    }
                    i++;
                }
            }
        } else {
            i = 0;
        }
        stringBuffer.append('#');
        stringBuffer.append(this.f628name);
        boolean z2 = isParameter() && this.declaringScope != null;
        if (i > 0 || z2) {
            stringBuffer.append('#');
            stringBuffer.append(i);
            if (z2) {
                LocalVariableBinding[] localVariableBindingArr2 = this.declaringScope.locals;
                int i3 = 0;
                while (true) {
                    if (i3 >= localVariableBindingArr2.length) {
                        i3 = -1;
                        break;
                    }
                    if (localVariableBindingArr2[i3] == this) {
                        break;
                    }
                    i3++;
                }
                if (i3 > -1) {
                    stringBuffer.append('#');
                    stringBuffer.append(i3);
                }
            }
        }
        int length = stringBuffer.length();
        char[] cArr = new char[length];
        stringBuffer.getChars(0, length, cArr, 0);
        return cArr;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.VariableBinding, org.eclipse.jdt.internal.compiler.lookup.Binding
    public AnnotationBinding[] getAnnotations() {
        LocalDeclaration localDeclaration;
        Annotation[] annotationArr;
        BlockScope blockScope = this.declaringScope;
        if (blockScope == null) {
            if ((this.tagBits & 8589934592L) != 0) {
                LocalDeclaration localDeclaration2 = this.declaration;
                if (localDeclaration2 == null) {
                    return Binding.NO_ANNOTATIONS;
                }
                Annotation[] annotationArr2 = localDeclaration2.annotations;
                if (annotationArr2 != null) {
                    int length = annotationArr2.length;
                    AnnotationBinding[] annotationBindingArr = new AnnotationBinding[length];
                    for (int i = 0; i < length; i++) {
                        AnnotationBinding compilerAnnotation = annotationArr2[i].getCompilerAnnotation();
                        if (compilerAnnotation == null) {
                            return Binding.NO_ANNOTATIONS;
                        }
                        annotationBindingArr[i] = compilerAnnotation;
                    }
                    return annotationBindingArr;
                }
            }
            return Binding.NO_ANNOTATIONS;
        }
        SourceTypeBinding sourceTypeBindingEnclosingSourceType = blockScope.enclosingSourceType();
        if (sourceTypeBindingEnclosingSourceType == null) {
            return Binding.NO_ANNOTATIONS;
        }
        if ((8589934592L & this.tagBits) == 0 && (this.tagBits & 1024) != 0 && (localDeclaration = this.declaration) != null && (annotationArr = localDeclaration.annotations) != null) {
            ASTNode.resolveAnnotations(this.declaringScope, annotationArr, this, true);
        }
        return sourceTypeBindingEnclosingSourceType.retrieveAnnotations(this);
    }

    private void getScopeKey(BlockScope blockScope, StringBuffer stringBuffer) {
        int iScopeIndex = blockScope.scopeIndex();
        if (iScopeIndex != -1) {
            getScopeKey((BlockScope) blockScope.parent, stringBuffer);
            stringBuffer.append('#');
            stringBuffer.append(iScopeIndex);
        }
    }

    public boolean isSecret() {
        return this.declaration == null && (this.tagBits & 1024) == 0;
    }

    public void recordInitializationEndPC(int i) {
        int[] iArr = this.initializationPCs;
        int i2 = this.initializationCount;
        if (iArr[((i2 - 1) << 1) + 1] == -1) {
            iArr[((i2 - 1) << 1) + 1] = i;
        }
    }

    public void recordInitializationStartPC(int i) {
        int[] iArr = this.initializationPCs;
        if (iArr == null) {
            return;
        }
        int i2 = this.initializationCount;
        if (i2 > 0) {
            int i3 = iArr[((i2 - 1) << 1) + 1];
            if (i3 == -1) {
                return;
            }
            if (i3 == i) {
                iArr[((i2 - 1) << 1) + 1] = -1;
                return;
            }
        }
        int i4 = i2 << 1;
        if (i4 == iArr.length) {
            int[] iArr2 = new int[i2 << 2];
            this.initializationPCs = iArr2;
            System.arraycopy(iArr, 0, iArr2, 0, i4);
        }
        int[] iArr3 = this.initializationPCs;
        iArr3[i4] = i;
        iArr3[i4 + 1] = -1;
        this.initializationCount++;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public void setAnnotations(AnnotationBinding[] annotationBindingArr, Scope scope) {
        SourceTypeBinding sourceTypeBindingEnclosingSourceType;
        if (scope == null || (sourceTypeBindingEnclosingSourceType = scope.enclosingSourceType()) == null) {
            return;
        }
        sourceTypeBindingEnclosingSourceType.storeAnnotations(this, annotationBindingArr);
    }

    public void resetInitializations() {
        this.initializationCount = 0;
        this.initializationPCs = null;
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.VariableBinding
    public String toString() {
        String string = super.toString();
        int i = this.useFlag;
        if (i == 0) {
            string = String.valueOf(string) + "[pos: unused]";
        } else if (i == 1) {
            string = String.valueOf(string) + "[pos: " + String.valueOf(this.resolvedPosition) + "]";
        } else if (i == 2) {
            string = String.valueOf(string) + "[pos: fake_used]";
        }
        String str = String.valueOf(string) + "[id:" + String.valueOf(this.f627id) + "]";
        if (this.initializationCount <= 0) {
            return str;
        }
        String string2 = String.valueOf(str) + "[pc: ";
        for (int i2 = 0; i2 < this.initializationCount; i2++) {
            if (i2 > 0) {
                string2 = String.valueOf(string2) + ", ";
            }
            int i3 = i2 << 1;
            StringBuilder sbAppend = new StringBuilder(String.valueOf(string2)).append(String.valueOf(this.initializationPCs[i3])).append(Parameters.DEFAULT_OPTION_PREFIXES);
            int i4 = this.initializationPCs[i3 + 1];
            string2 = sbAppend.append(i4 == -1 ? TypeDescription.Generic.OfWildcardType.SYMBOL : String.valueOf(i4)).toString();
        }
        return String.valueOf(string2) + "]";
    }

    @Override // org.eclipse.jdt.internal.compiler.lookup.Binding
    public boolean isParameter() {
        return (this.tagBits & 1024) != 0;
    }

    public MethodBinding getEnclosingMethod() {
        BlockScope blockScope = this.declaringScope;
        if (blockScope != null) {
            ReferenceContext referenceContext = blockScope.referenceContext();
            if (!(referenceContext instanceof Initializer) && (referenceContext instanceof AbstractMethodDeclaration)) {
                return ((AbstractMethodDeclaration) referenceContext).binding;
            }
        }
        return null;
    }
}
