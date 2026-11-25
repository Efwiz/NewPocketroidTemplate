package org.eclipse.jdt.internal.compiler;

import java.util.ArrayList;
import java.util.Map;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.AllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.AnnotationMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.ArrayAllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.jdt.internal.compiler.ast.ArrayReference;
import org.eclipse.jdt.internal.compiler.ast.Assignment;
import org.eclipse.jdt.internal.compiler.ast.ClassLiteralAccess;
import org.eclipse.jdt.internal.compiler.ast.CompilationUnitDeclaration;
import org.eclipse.jdt.internal.compiler.ast.ConstructorDeclaration;
import org.eclipse.jdt.internal.compiler.ast.ExplicitConstructorCall;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.ImportReference;
import org.eclipse.jdt.internal.compiler.ast.Initializer;
import org.eclipse.jdt.internal.compiler.ast.MessageSend;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.QualifiedAllocationExpression;
import org.eclipse.jdt.internal.compiler.ast.ThisReference;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.util.HashtableOfObjectToInt;

/* JADX INFO: loaded from: classes14.dex */
public class SourceElementNotifier {
    int eofPosition;
    int initialPosition;
    LocalDeclarationVisitor localDeclarationVisitor;
    int nestedTypeIndex;
    Map nodesToCategories;
    boolean reportReferenceInfo;
    ISourceElementRequestor requestor;
    HashtableOfObjectToInt sourceEnds;
    char[][] superTypeNames;
    char[][] typeNames;

    public class LocalDeclarationVisitor extends ASTVisitor {
        public ImportReference currentPackage;
        ArrayList declaringTypes;

        public LocalDeclarationVisitor() {
        }

        public void pushDeclaringType(TypeDeclaration typeDeclaration) {
            if (this.declaringTypes == null) {
                this.declaringTypes = new ArrayList();
            }
            this.declaringTypes.add(typeDeclaration);
        }

        public void popDeclaringType() {
            ArrayList arrayList = this.declaringTypes;
            arrayList.remove(arrayList.size() - 1);
        }

        public TypeDeclaration peekDeclaringType() {
            int size;
            ArrayList arrayList = this.declaringTypes;
            if (arrayList == null || (size = arrayList.size()) == 0) {
                return null;
            }
            return (TypeDeclaration) this.declaringTypes.get(size - 1);
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(TypeDeclaration typeDeclaration, BlockScope blockScope) {
            SourceElementNotifier.this.notifySourceElementRequestor(typeDeclaration, true, peekDeclaringType(), this.currentPackage);
            return false;
        }

        @Override // org.eclipse.jdt.internal.compiler.ASTVisitor
        public boolean visit(TypeDeclaration typeDeclaration, ClassScope classScope) {
            SourceElementNotifier.this.notifySourceElementRequestor(typeDeclaration, true, peekDeclaringType(), this.currentPackage);
            return false;
        }
    }

    public SourceElementNotifier(ISourceElementRequestor iSourceElementRequestor, boolean z) {
        this.localDeclarationVisitor = null;
        this.requestor = iSourceElementRequestor;
        if (z) {
            this.localDeclarationVisitor = new LocalDeclarationVisitor();
        }
        this.typeNames = new char[4][];
        this.superTypeNames = new char[4][];
        this.nestedTypeIndex = 0;
    }

    protected Object[][] getArgumentInfos(Argument[] argumentArr) {
        int length = argumentArr.length;
        char[][] cArr = new char[length][];
        char[][] cArr2 = new char[length][];
        ISourceElementRequestor.ParameterInfo[] parameterInfoArr = new ISourceElementRequestor.ParameterInfo[length];
        for (int i = 0; i < length; i++) {
            Argument argument = argumentArr[i];
            cArr[i] = CharOperation.concatWith(argument.type.getParameterizedTypeName(), '.');
            char[] cArr3 = argument.f617name;
            cArr2[i] = cArr3;
            ISourceElementRequestor.ParameterInfo parameterInfo = new ISourceElementRequestor.ParameterInfo();
            parameterInfo.declarationStart = argument.declarationSourceStart;
            parameterInfo.declarationEnd = argument.declarationSourceEnd;
            parameterInfo.nameSourceStart = argument.sourceStart;
            parameterInfo.nameSourceEnd = argument.sourceEnd;
            parameterInfo.modifiers = argument.modifiers;
            parameterInfo.name = cArr3;
            parameterInfoArr[i] = parameterInfo;
        }
        return new Object[][]{parameterInfoArr, new char[][][]{cArr, cArr2}};
    }

    protected char[][] getInterfaceNames(TypeDeclaration typeDeclaration) {
        char[][] cArr;
        int length;
        QualifiedAllocationExpression qualifiedAllocationExpression;
        TypeReference[] typeReferenceArr = typeDeclaration.superInterfaces;
        if (typeReferenceArr != null) {
            length = typeReferenceArr.length;
            cArr = new char[length][];
        } else if ((typeDeclaration.bits & 512) == 0 || (qualifiedAllocationExpression = typeDeclaration.allocation) == null || qualifiedAllocationExpression.type == null) {
            cArr = null;
            length = 0;
        } else {
            TypeReference[] typeReferenceArr2 = {qualifiedAllocationExpression.type};
            cArr = new char[1][];
            length = 1;
            typeReferenceArr = typeReferenceArr2;
        }
        if (typeReferenceArr != null) {
            for (int i = 0; i < length; i++) {
                cArr[i] = CharOperation.concatWith(typeReferenceArr[i].getParameterizedTypeName(), '.');
            }
        }
        return cArr;
    }

    protected char[] getSuperclassName(TypeDeclaration typeDeclaration) {
        TypeReference typeReference = typeDeclaration.superclass;
        if (typeReference != null) {
            return CharOperation.concatWith(typeReference.getParameterizedTypeName(), '.');
        }
        return null;
    }

    protected char[][] getThrownExceptions(AbstractMethodDeclaration abstractMethodDeclaration) {
        TypeReference[] typeReferenceArr = abstractMethodDeclaration.thrownExceptions;
        if (typeReferenceArr == null) {
            return null;
        }
        int length = typeReferenceArr.length;
        char[][] cArr = new char[length][];
        for (int i = 0; i < length; i++) {
            cArr[i] = CharOperation.concatWith(typeReferenceArr[i].getParameterizedTypeName(), '.');
        }
        return cArr;
    }

    protected char[][] getTypeParameterBounds(TypeParameter typeParameter) {
        TypeReference typeReference = typeParameter.type;
        TypeReference[] typeReferenceArr = typeParameter.bounds;
        if (typeReference == null) {
            return CharOperation.NO_CHAR_CHAR;
        }
        int i = 0;
        if (typeReferenceArr != null) {
            int length = typeReferenceArr.length;
            char[][] cArr = new char[length + 1][];
            cArr[0] = CharOperation.concatWith(typeReference.getParameterizedTypeName(), '.');
            while (i < length) {
                int i2 = i + 1;
                cArr[i2] = CharOperation.concatWith(typeReferenceArr[i].getParameterizedTypeName(), '.');
                i = i2;
            }
            return cArr;
        }
        return new char[][]{CharOperation.concatWith(typeReference.getParameterizedTypeName(), '.')};
    }

    private ISourceElementRequestor.TypeParameterInfo[] getTypeParameterInfos(TypeParameter[] typeParameterArr) {
        if (typeParameterArr == null) {
            return null;
        }
        int length = typeParameterArr.length;
        ISourceElementRequestor.TypeParameterInfo[] typeParameterInfoArr = new ISourceElementRequestor.TypeParameterInfo[length];
        for (int i = 0; i < length; i++) {
            TypeParameter typeParameter = typeParameterArr[i];
            char[][] typeParameterBounds = getTypeParameterBounds(typeParameter);
            ISourceElementRequestor.TypeParameterInfo typeParameterInfo = new ISourceElementRequestor.TypeParameterInfo();
            typeParameterInfo.typeAnnotated = (typeParameter.bits & 1048576) != 0;
            typeParameterInfo.declarationStart = typeParameter.declarationSourceStart;
            typeParameterInfo.declarationEnd = typeParameter.declarationSourceEnd;
            typeParameterInfo.name = typeParameter.f617name;
            typeParameterInfo.nameSourceStart = typeParameter.sourceStart;
            typeParameterInfo.nameSourceEnd = typeParameter.sourceEnd;
            typeParameterInfo.bounds = typeParameterBounds;
            typeParameterInfoArr[i] = typeParameterInfo;
        }
        return typeParameterInfoArr;
    }

    private boolean hasDeprecatedAnnotation(Annotation[] annotationArr) {
        if (annotationArr != null) {
            for (Annotation annotation : annotationArr) {
                if (CharOperation.equals(annotation.type.getLastToken(), TypeConstants.JAVA_LANG_DEPRECATED[2])) {
                    return true;
                }
            }
        }
        return false;
    }

    protected void notifySourceElementRequestor(AbstractMethodDeclaration abstractMethodDeclaration, TypeDeclaration typeDeclaration, ImportReference importReference) {
        boolean zIsVarArgs;
        char[][] cArr;
        ISourceElementRequestor.ParameterInfo[] parameterInfoArr;
        char[][] cArr2;
        Expression expression;
        ExplicitConstructorCall explicitConstructorCall;
        ExplicitConstructorCall explicitConstructorCall2;
        boolean z = this.initialPosition <= abstractMethodDeclaration.declarationSourceStart && this.eofPosition >= abstractMethodDeclaration.declarationSourceEnd;
        if (abstractMethodDeclaration.isClinit()) {
            visitIfNeeded(abstractMethodDeclaration);
            return;
        }
        if (abstractMethodDeclaration.isDefaultConstructor()) {
            if (!this.reportReferenceInfo || (explicitConstructorCall2 = ((ConstructorDeclaration) abstractMethodDeclaration).constructorCall) == null) {
                return;
            }
            int i = explicitConstructorCall2.accessMode;
            if (i == 1 || i == 2) {
                this.requestor.acceptConstructorReference(this.superTypeNames[this.nestedTypeIndex - 1], explicitConstructorCall2.arguments != null ? explicitConstructorCall2.arguments.length : 0, explicitConstructorCall2.sourceStart);
                return;
            } else {
                if (i != 3) {
                    return;
                }
                this.requestor.acceptConstructorReference(this.typeNames[this.nestedTypeIndex - 1], explicitConstructorCall2.arguments != null ? explicitConstructorCall2.arguments.length : 0, explicitConstructorCall2.sourceStart);
                return;
            }
        }
        Argument[] argumentArr = abstractMethodDeclaration.arguments;
        ISourceElementRequestor.MethodInfo methodInfo = new ISourceElementRequestor.MethodInfo();
        methodInfo.typeAnnotated = (abstractMethodDeclaration.bits & 1048576) != 0;
        if (argumentArr != null) {
            Object[][] argumentInfos = getArgumentInfos(argumentArr);
            parameterInfoArr = (ISourceElementRequestor.ParameterInfo[]) argumentInfos[0];
            Object[] objArr = argumentInfos[1];
            cArr2 = (char[][]) objArr[0];
            cArr = (char[][]) objArr[1];
            zIsVarArgs = argumentArr[argumentArr.length - 1].isVarArgs();
        } else {
            zIsVarArgs = false;
            cArr = null;
            parameterInfoArr = null;
            cArr2 = null;
        }
        char[][] thrownExceptions = getThrownExceptions(abstractMethodDeclaration);
        if (abstractMethodDeclaration.isConstructor()) {
            int i2 = this.sourceEnds.get(abstractMethodDeclaration);
            if (z) {
                int i3 = abstractMethodDeclaration.modifiers & 1114111;
                if (zIsVarArgs) {
                    i3 |= 128;
                }
                if (hasDeprecatedAnnotation(abstractMethodDeclaration.annotations)) {
                    i3 |= 1048576;
                }
                methodInfo.isConstructor = true;
                methodInfo.declarationStart = abstractMethodDeclaration.declarationSourceStart;
                methodInfo.modifiers = i3;
                methodInfo.name = abstractMethodDeclaration.selector;
                methodInfo.nameSourceStart = abstractMethodDeclaration.sourceStart;
                methodInfo.nameSourceEnd = i2;
                methodInfo.parameterTypes = cArr2;
                methodInfo.parameterNames = cArr;
                methodInfo.exceptionTypes = thrownExceptions;
                methodInfo.typeParameters = getTypeParameterInfos(abstractMethodDeclaration.typeParameters());
                methodInfo.parameterInfos = parameterInfoArr;
                methodInfo.categories = (char[][]) this.nodesToCategories.get(abstractMethodDeclaration);
                methodInfo.annotations = abstractMethodDeclaration.annotations;
                methodInfo.declaringPackageName = importReference == null ? CharOperation.NO_CHAR : CharOperation.concatWith(importReference.tokens, '.');
                methodInfo.declaringTypeModifiers = typeDeclaration.modifiers;
                methodInfo.extraFlags = ExtraFlags.getExtraFlags(typeDeclaration);
                methodInfo.node = abstractMethodDeclaration;
                this.requestor.enterConstructor(methodInfo);
            }
            if (this.reportReferenceInfo && (explicitConstructorCall = ((ConstructorDeclaration) abstractMethodDeclaration).constructorCall) != null) {
                int i4 = explicitConstructorCall.accessMode;
                if (i4 == 1 || i4 == 2) {
                    this.requestor.acceptConstructorReference(this.superTypeNames[this.nestedTypeIndex - 1], explicitConstructorCall.arguments == null ? 0 : explicitConstructorCall.arguments.length, explicitConstructorCall.sourceStart);
                } else if (i4 == 3) {
                    this.requestor.acceptConstructorReference(this.typeNames[this.nestedTypeIndex - 1], explicitConstructorCall.arguments == null ? 0 : explicitConstructorCall.arguments.length, explicitConstructorCall.sourceStart);
                }
            }
            visitIfNeeded(abstractMethodDeclaration);
            if (z) {
                this.requestor.exitConstructor(abstractMethodDeclaration.declarationSourceEnd);
                return;
            }
            return;
        }
        int i5 = this.sourceEnds.get(abstractMethodDeclaration);
        if (z) {
            int i6 = abstractMethodDeclaration.modifiers & 1310719;
            if (zIsVarArgs) {
                i6 |= 128;
            }
            if (hasDeprecatedAnnotation(abstractMethodDeclaration.annotations)) {
                i6 |= 1048576;
            }
            TypeReference typeReference = abstractMethodDeclaration instanceof MethodDeclaration ? ((MethodDeclaration) abstractMethodDeclaration).returnType : null;
            methodInfo.isAnnotation = abstractMethodDeclaration instanceof AnnotationMethodDeclaration;
            methodInfo.declarationStart = abstractMethodDeclaration.declarationSourceStart;
            methodInfo.modifiers = i6;
            methodInfo.returnType = typeReference == null ? null : CharOperation.concatWith(typeReference.getParameterizedTypeName(), '.');
            methodInfo.name = abstractMethodDeclaration.selector;
            methodInfo.nameSourceStart = abstractMethodDeclaration.sourceStart;
            methodInfo.nameSourceEnd = i5;
            methodInfo.parameterTypes = cArr2;
            methodInfo.parameterNames = cArr;
            methodInfo.exceptionTypes = thrownExceptions;
            methodInfo.typeParameters = getTypeParameterInfos(abstractMethodDeclaration.typeParameters());
            methodInfo.parameterInfos = parameterInfoArr;
            methodInfo.categories = (char[][]) this.nodesToCategories.get(abstractMethodDeclaration);
            methodInfo.annotations = abstractMethodDeclaration.annotations;
            methodInfo.node = abstractMethodDeclaration;
            methodInfo.enclosingType = typeDeclaration;
            methodInfo.declaringPackageName = importReference == null ? CharOperation.NO_CHAR : CharOperation.concatWith(importReference.tokens, '.');
            this.requestor.enterMethod(methodInfo);
        }
        visitIfNeeded(abstractMethodDeclaration);
        if (z) {
            if ((abstractMethodDeclaration instanceof AnnotationMethodDeclaration) && (expression = ((AnnotationMethodDeclaration) abstractMethodDeclaration).defaultValue) != null) {
                this.requestor.exitMethod(abstractMethodDeclaration.declarationSourceEnd, expression);
            } else {
                this.requestor.exitMethod(abstractMethodDeclaration.declarationSourceEnd, (Expression) null);
            }
        }
    }

    public void notifySourceElementRequestor(CompilationUnitDeclaration compilationUnitDeclaration, int i, int i2, boolean z, HashtableOfObjectToInt hashtableOfObjectToInt, Map map) {
        int i3;
        this.initialPosition = i;
        this.eofPosition = i2;
        this.reportReferenceInfo = z;
        this.sourceEnds = hashtableOfObjectToInt;
        this.nodesToCategories = map;
        try {
            boolean z2 = i <= compilationUnitDeclaration.sourceStart && this.eofPosition >= compilationUnitDeclaration.sourceEnd;
            if (z2) {
                this.requestor.enterCompilationUnit();
            }
            ImportReference importReference = compilationUnitDeclaration.currentPackage;
            LocalDeclarationVisitor localDeclarationVisitor = this.localDeclarationVisitor;
            if (localDeclarationVisitor != null) {
                localDeclarationVisitor.currentPackage = importReference;
            }
            ImportReference[] importReferenceArr = compilationUnitDeclaration.imports;
            TypeDeclaration[] typeDeclarationArr = compilationUnitDeclaration.types;
            int length = (importReference == null ? 0 : 1) + (importReferenceArr == null ? 0 : importReferenceArr.length) + (typeDeclarationArr == null ? 0 : typeDeclarationArr.length);
            ASTNode[] aSTNodeArr = new ASTNode[length];
            if (importReference != null) {
                aSTNodeArr[0] = importReference;
                i3 = 1;
            } else {
                i3 = 0;
            }
            if (importReferenceArr != null) {
                int length2 = importReferenceArr.length;
                int i4 = 0;
                while (i4 < length2) {
                    aSTNodeArr[i3] = importReferenceArr[i4];
                    i4++;
                    i3++;
                }
            }
            if (typeDeclarationArr != null) {
                int length3 = typeDeclarationArr.length;
                int i5 = 0;
                while (i5 < length3) {
                    aSTNodeArr[i3] = typeDeclarationArr[i5];
                    i5++;
                    i3++;
                }
            }
            if (length > 0) {
                quickSort(aSTNodeArr, 0, length - 1);
                for (int i6 = 0; i6 < length; i6++) {
                    ASTNode aSTNode = aSTNodeArr[i6];
                    if (aSTNode instanceof ImportReference) {
                        ImportReference importReference2 = (ImportReference) aSTNode;
                        if (aSTNode == compilationUnitDeclaration.currentPackage) {
                            notifySourceElementRequestor(importReference2, true);
                        } else {
                            notifySourceElementRequestor(importReference2, false);
                        }
                    } else {
                        notifySourceElementRequestor((TypeDeclaration) aSTNode, true, null, importReference);
                    }
                }
            }
            if (z2) {
                this.requestor.exitCompilationUnit(compilationUnitDeclaration.sourceEnd);
            }
        } finally {
            reset();
        }
    }

    protected void notifySourceElementRequestor(FieldDeclaration fieldDeclaration, TypeDeclaration typeDeclaration) {
        char[] cArrConcatWith;
        boolean z = this.initialPosition <= fieldDeclaration.declarationSourceStart && this.eofPosition >= fieldDeclaration.declarationSourceEnd;
        int kind = fieldDeclaration.getKind();
        if (kind != 1) {
            if (kind == 2) {
                if (z) {
                    this.requestor.enterInitializer(fieldDeclaration.declarationSourceStart, fieldDeclaration.modifiers);
                }
                visitIfNeeded((Initializer) fieldDeclaration);
                if (z) {
                    this.requestor.exitInitializer(fieldDeclaration.declarationSourceEnd);
                    return;
                }
                return;
            }
            if (kind != 3) {
                return;
            }
            if (this.reportReferenceInfo && (fieldDeclaration.initialization instanceof AllocationExpression)) {
                AllocationExpression allocationExpression = (AllocationExpression) fieldDeclaration.initialization;
                this.requestor.acceptConstructorReference(typeDeclaration.f619name, allocationExpression.arguments == null ? 0 : allocationExpression.arguments.length, allocationExpression.sourceStart);
            }
        }
        int i = this.sourceEnds.get(fieldDeclaration);
        int i2 = -1;
        if (i == -1) {
            i = fieldDeclaration.declarationSourceEnd;
        }
        if (z) {
            int i3 = fieldDeclaration.modifiers;
            boolean z2 = (i3 & 1048576) != 0 || hasDeprecatedAnnotation(fieldDeclaration.annotations);
            if (fieldDeclaration.type == null) {
                cArrConcatWith = typeDeclaration.f619name;
                i3 |= 16384;
            } else {
                cArrConcatWith = CharOperation.concatWith(fieldDeclaration.type.getParameterizedTypeName(), '.');
            }
            ISourceElementRequestor.FieldInfo fieldInfo = new ISourceElementRequestor.FieldInfo();
            fieldInfo.typeAnnotated = (fieldDeclaration.bits & 1048576) != 0;
            fieldInfo.declarationStart = fieldDeclaration.declarationSourceStart;
            fieldInfo.name = fieldDeclaration.f617name;
            int i4 = 65535 & i3;
            if (z2) {
                i4 |= 1048576;
            }
            fieldInfo.modifiers = i4;
            fieldInfo.type = cArrConcatWith;
            fieldInfo.nameSourceStart = fieldDeclaration.sourceStart;
            fieldInfo.nameSourceEnd = fieldDeclaration.sourceEnd;
            fieldInfo.categories = (char[][]) this.nodesToCategories.get(fieldDeclaration);
            fieldInfo.annotations = fieldDeclaration.annotations;
            fieldInfo.node = fieldDeclaration;
            this.requestor.enterField(fieldInfo);
        }
        visitIfNeeded(fieldDeclaration, typeDeclaration);
        if (z) {
            ISourceElementRequestor iSourceElementRequestor = this.requestor;
            if (fieldDeclaration.initialization != null && !(fieldDeclaration.initialization instanceof ArrayInitializer) && !(fieldDeclaration.initialization instanceof AllocationExpression) && !(fieldDeclaration.initialization instanceof ArrayAllocationExpression) && !(fieldDeclaration.initialization instanceof Assignment) && !(fieldDeclaration.initialization instanceof ClassLiteralAccess) && !(fieldDeclaration.initialization instanceof MessageSend) && !(fieldDeclaration.initialization instanceof ArrayReference) && !(fieldDeclaration.initialization instanceof ThisReference)) {
                i2 = fieldDeclaration.initialization.sourceStart;
            }
            iSourceElementRequestor.exitField(i2, i, fieldDeclaration.declarationSourceEnd);
        }
    }

    protected void notifySourceElementRequestor(ImportReference importReference, boolean z) {
        if (z) {
            this.requestor.acceptPackage(importReference);
        } else {
            boolean z2 = (importReference.bits & 131072) != 0;
            this.requestor.acceptImport(importReference.declarationSourceStart, importReference.declarationSourceEnd, importReference.sourceStart, z2 ? importReference.trailingStarPosition : importReference.sourceEnd, importReference.tokens, z2, importReference.modifiers);
        }
    }

    /* JADX WARN: Code duplicated, block: B:81:0x0131  */
    /* JADX WARN: Code duplicated, block: B:82:0x0149  */
    protected void notifySourceElementRequestor(TypeDeclaration typeDeclaration, boolean z, TypeDeclaration typeDeclaration2, ImportReference importReference) {
        TypeDeclaration[] typeDeclarationArr;
        int i;
        FieldDeclaration fieldDeclaration;
        AbstractMethodDeclaration abstractMethodDeclaration;
        boolean z2;
        TypeDeclaration typeDeclaration3;
        char[] cArr;
        int i2;
        char[][] cArr2;
        char[] superclassName;
        if (CharOperation.equals(TypeConstants.PACKAGE_INFO_NAME, typeDeclaration.f619name)) {
            return;
        }
        boolean z3 = this.initialPosition <= typeDeclaration.declarationSourceStart && this.eofPosition >= typeDeclaration.declarationSourceEnd;
        FieldDeclaration[] fieldDeclarationArr = typeDeclaration.fields;
        AbstractMethodDeclaration[] abstractMethodDeclarationArr = typeDeclaration.methods;
        TypeDeclaration[] typeDeclarationArr2 = typeDeclaration.memberTypes;
        int length = fieldDeclarationArr == null ? 0 : fieldDeclarationArr.length;
        int length2 = abstractMethodDeclarationArr == null ? 0 : abstractMethodDeclarationArr.length;
        int length3 = typeDeclarationArr2 == null ? 0 : typeDeclarationArr2.length;
        if (z) {
            char[][] interfaceNames = getInterfaceNames(typeDeclaration);
            int iKind = TypeDeclaration.kind(typeDeclaration.modifiers);
            char[] cArr3 = TypeConstants.CharArray_JAVA_LANG_OBJECT;
            ISourceElementRequestor.TypeInfo typeInfo = new ISourceElementRequestor.TypeInfo();
            typeInfo.typeAnnotated = (typeDeclaration.bits & 1048576) != 0;
            if (z3) {
                int i3 = typeDeclaration.modifiers;
                boolean z4 = (i3 & 1048576) != 0 || hasDeprecatedAnnotation(typeDeclaration.annotations);
                boolean z5 = (typeDeclaration.allocation == null || typeDeclaration.allocation.enumConstant == null) ? false : true;
                if (z5) {
                    superclassName = typeDeclaration2.f619name;
                    i3 |= 16384;
                } else {
                    superclassName = getSuperclassName(typeDeclaration);
                }
                typeDeclarationArr = typeDeclarationArr2;
                if (typeDeclaration.allocation == null) {
                    typeInfo.declarationStart = typeDeclaration.declarationSourceStart;
                } else if (z5) {
                    typeInfo.declarationStart = typeDeclaration.allocation.enumConstant.sourceStart;
                } else {
                    typeInfo.declarationStart = typeDeclaration.allocation.sourceStart;
                }
                typeInfo.modifiers = z4 ? (i3 & 65535) | 1048576 : i3 & 65535;
                typeInfo.name = typeDeclaration.f619name;
                typeInfo.nameSourceStart = z5 ? typeDeclaration.allocation.enumConstant.sourceStart : typeDeclaration.sourceStart;
                typeInfo.nameSourceEnd = sourceEnd(typeDeclaration);
                typeInfo.superclass = superclassName;
                typeInfo.superinterfaces = interfaceNames;
                typeInfo.typeParameters = getTypeParameterInfos(typeDeclaration.typeParameters);
                typeInfo.categories = (char[][]) this.nodesToCategories.get(typeDeclaration);
                typeInfo.secondary = typeDeclaration.isSecondary();
                typeInfo.anonymousMember = (typeDeclaration.allocation == null || typeDeclaration.allocation.enclosingInstance == null) ? false : true;
                typeInfo.annotations = typeDeclaration.annotations;
                typeInfo.extraFlags = ExtraFlags.getExtraFlags(typeDeclaration);
                typeInfo.node = typeDeclaration;
                this.requestor.enterType(typeInfo);
                if (iKind != 1) {
                    if (iKind == 2) {
                        cArr = TypeConstants.CharArray_JAVA_LANG_OBJECT;
                    } else if (iKind == 3) {
                        cArr = TypeConstants.CharArray_JAVA_LANG_ENUM;
                    } else if (iKind == 4) {
                        cArr = TypeConstants.CharArray_JAVA_LANG_ANNOTATION_ANNOTATION;
                    }
                } else if (superclassName != null) {
                    cArr = superclassName;
                }
                i2 = this.nestedTypeIndex;
                cArr2 = this.typeNames;
                if (i2 == cArr2.length) {
                    char[][] cArr4 = new char[i2 * 2][];
                    this.typeNames = cArr4;
                    i = 0;
                    System.arraycopy(cArr2, 0, cArr4, 0, i2);
                    char[][] cArr5 = this.superTypeNames;
                    int i4 = this.nestedTypeIndex;
                    char[][] cArr6 = new char[i4 * 2][];
                    this.superTypeNames = cArr6;
                    System.arraycopy(cArr5, 0, cArr6, 0, i4);
                } else {
                    i = 0;
                }
                this.typeNames[this.nestedTypeIndex] = typeDeclaration.f619name;
                char[][] cArr7 = this.superTypeNames;
                int i5 = this.nestedTypeIndex;
                this.nestedTypeIndex = i5 + 1;
                cArr7[i5] = cArr;
            } else {
                typeDeclarationArr = typeDeclarationArr2;
                cArr3 = cArr3;
            }
            cArr = cArr3;
            i2 = this.nestedTypeIndex;
            cArr2 = this.typeNames;
            if (i2 == cArr2.length) {
                char[][] cArr8 = new char[i2 * 2][];
                this.typeNames = cArr8;
                i = 0;
                System.arraycopy(cArr2, 0, cArr8, 0, i2);
                char[][] cArr9 = this.superTypeNames;
                int i6 = this.nestedTypeIndex;
                char[][] cArr10 = new char[i6 * 2][];
                this.superTypeNames = cArr10;
                System.arraycopy(cArr9, 0, cArr10, 0, i6);
            } else {
                i = 0;
            }
            this.typeNames[this.nestedTypeIndex] = typeDeclaration.f619name;
            char[][] cArr11 = this.superTypeNames;
            int i7 = this.nestedTypeIndex;
            this.nestedTypeIndex = i7 + 1;
            cArr11[i7] = cArr;
        } else {
            typeDeclarationArr = typeDeclarationArr2;
            i = 0;
        }
        int i8 = i;
        int i9 = i8;
        int i10 = i9;
        while (true) {
            if (i8 >= length && i9 >= length3 && i10 >= length2) {
                break;
            }
            int i11 = Integer.MAX_VALUE;
            byte b = -1;
            if (i8 < length) {
                fieldDeclaration = fieldDeclarationArr[i8];
                if (fieldDeclaration.declarationSourceStart < Integer.MAX_VALUE) {
                    i11 = fieldDeclaration.declarationSourceStart;
                    b = 0;
                }
            } else {
                fieldDeclaration = null;
            }
            if (i10 < length2) {
                abstractMethodDeclaration = abstractMethodDeclarationArr[i10];
                if (abstractMethodDeclaration.declarationSourceStart < i11) {
                    i11 = abstractMethodDeclaration.declarationSourceStart;
                    b = 1;
                }
            } else {
                abstractMethodDeclaration = null;
            }
            if (i9 < length3) {
                typeDeclaration3 = typeDeclarationArr[i9];
                z2 = z3;
                if (typeDeclaration3.declarationSourceStart < i11) {
                    int i12 = typeDeclaration3.declarationSourceStart;
                    b = 2;
                }
            } else {
                z2 = z3;
                typeDeclaration3 = null;
            }
            if (b == 0) {
                i8++;
                notifySourceElementRequestor(fieldDeclaration, typeDeclaration);
            } else if (b == 1) {
                i10++;
                notifySourceElementRequestor(abstractMethodDeclaration, typeDeclaration, importReference);
            } else if (b == 2) {
                i9++;
                notifySourceElementRequestor(typeDeclaration3, true, null, importReference);
            }
            z3 = z2;
        }
        if (z) {
            if (z3) {
                this.requestor.exitType(typeDeclaration.declarationSourceEnd);
            }
            this.nestedTypeIndex--;
        }
    }

    private static void quickSort(ASTNode[] aSTNodeArr, int i, int i2) {
        ASTNode aSTNode = aSTNodeArr[((i2 - i) / 2) + i];
        int i3 = i;
        int i4 = i2;
        while (true) {
            if (aSTNodeArr[i3].sourceStart >= aSTNode.sourceStart) {
                while (aSTNode.sourceStart < aSTNodeArr[i4].sourceStart) {
                    i4--;
                }
                if (i3 <= i4) {
                    ASTNode aSTNode2 = aSTNodeArr[i3];
                    aSTNodeArr[i3] = aSTNodeArr[i4];
                    aSTNodeArr[i4] = aSTNode2;
                    i3++;
                    i4--;
                }
                if (i3 > i4) {
                    break;
                }
            } else {
                i3++;
            }
        }
        if (i < i4) {
            quickSort(aSTNodeArr, i, i4);
        }
        if (i3 < i2) {
            quickSort(aSTNodeArr, i3, i2);
        }
    }

    private void reset() {
        this.typeNames = new char[4][];
        this.superTypeNames = new char[4][];
        this.nestedTypeIndex = 0;
        this.sourceEnds = null;
    }

    private int sourceEnd(TypeDeclaration typeDeclaration) {
        if ((typeDeclaration.bits & 512) != 0) {
            QualifiedAllocationExpression qualifiedAllocationExpression = typeDeclaration.allocation;
            if (qualifiedAllocationExpression.enumConstant != null) {
                return qualifiedAllocationExpression.enumConstant.sourceEnd;
            }
            return qualifiedAllocationExpression.type.sourceEnd;
        }
        return typeDeclaration.sourceEnd;
    }

    private void visitIfNeeded(AbstractMethodDeclaration abstractMethodDeclaration) {
        if (this.localDeclarationVisitor == null || (abstractMethodDeclaration.bits & 2) == 0) {
            return;
        }
        if (abstractMethodDeclaration instanceof ConstructorDeclaration) {
            ConstructorDeclaration constructorDeclaration = (ConstructorDeclaration) abstractMethodDeclaration;
            if (constructorDeclaration.constructorCall != null) {
                constructorDeclaration.constructorCall.traverse(this.localDeclarationVisitor, abstractMethodDeclaration.scope);
            }
        }
        if (abstractMethodDeclaration.statements != null) {
            int length = abstractMethodDeclaration.statements.length;
            for (int i = 0; i < length; i++) {
                abstractMethodDeclaration.statements[i].traverse(this.localDeclarationVisitor, abstractMethodDeclaration.scope);
            }
        }
    }

    private void visitIfNeeded(FieldDeclaration fieldDeclaration, TypeDeclaration typeDeclaration) {
        if (this.localDeclarationVisitor == null || (fieldDeclaration.bits & 2) == 0 || fieldDeclaration.initialization == null) {
            return;
        }
        try {
            this.localDeclarationVisitor.pushDeclaringType(typeDeclaration);
            fieldDeclaration.initialization.traverse(this.localDeclarationVisitor, (BlockScope) null);
        } finally {
            this.localDeclarationVisitor.popDeclaringType();
        }
    }

    private void visitIfNeeded(Initializer initializer) {
        if (this.localDeclarationVisitor == null || (initializer.bits & 2) == 0 || initializer.block == null) {
            return;
        }
        initializer.block.traverse(this.localDeclarationVisitor, null);
    }
}
