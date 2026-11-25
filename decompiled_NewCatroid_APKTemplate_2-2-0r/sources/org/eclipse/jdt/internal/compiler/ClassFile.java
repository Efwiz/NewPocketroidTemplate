package org.eclipse.jdt.internal.compiler;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.jdt.core.compiler.CategorizedProblem;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ast.ASTNode;
import org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Annotation;
import org.eclipse.jdt.internal.compiler.ast.AnnotationMethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.Argument;
import org.eclipse.jdt.internal.compiler.ast.ArrayInitializer;
import org.eclipse.jdt.internal.compiler.ast.ClassLiteralAccess;
import org.eclipse.jdt.internal.compiler.ast.Expression;
import org.eclipse.jdt.internal.compiler.ast.FieldDeclaration;
import org.eclipse.jdt.internal.compiler.ast.FunctionalExpression;
import org.eclipse.jdt.internal.compiler.ast.LambdaExpression;
import org.eclipse.jdt.internal.compiler.ast.LocalDeclaration;
import org.eclipse.jdt.internal.compiler.ast.MemberValuePair;
import org.eclipse.jdt.internal.compiler.ast.MethodDeclaration;
import org.eclipse.jdt.internal.compiler.ast.NormalAnnotation;
import org.eclipse.jdt.internal.compiler.ast.QualifiedNameReference;
import org.eclipse.jdt.internal.compiler.ast.Receiver;
import org.eclipse.jdt.internal.compiler.ast.ReferenceExpression;
import org.eclipse.jdt.internal.compiler.ast.SingleMemberAnnotation;
import org.eclipse.jdt.internal.compiler.ast.SingleNameReference;
import org.eclipse.jdt.internal.compiler.ast.TypeDeclaration;
import org.eclipse.jdt.internal.compiler.ast.TypeParameter;
import org.eclipse.jdt.internal.compiler.ast.TypeReference;
import org.eclipse.jdt.internal.compiler.classfmt.ClassFileConstants;
import org.eclipse.jdt.internal.compiler.codegen.AnnotationContext;
import org.eclipse.jdt.internal.compiler.codegen.AttributeNamesConstants;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;
import org.eclipse.jdt.internal.compiler.codegen.ExceptionLabel;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.codegen.StackMapFrame;
import org.eclipse.jdt.internal.compiler.codegen.StackMapFrameCodeStream;
import org.eclipse.jdt.internal.compiler.codegen.TypeAnnotationCodeStream;
import org.eclipse.jdt.internal.compiler.codegen.VerificationTypeInfo;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jdt.internal.compiler.impl.Constant;
import org.eclipse.jdt.internal.compiler.impl.StringConstant;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.LocalVariableBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.ReferenceBinding;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticArgumentBinding;
import org.eclipse.jdt.internal.compiler.lookup.SyntheticMethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.TagBits;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.lookup.TypeIds;
import org.eclipse.jdt.internal.compiler.lookup.TypeVariableBinding;
import org.eclipse.jdt.internal.compiler.problem.AbortMethod;
import org.eclipse.jdt.internal.compiler.problem.AbortType;
import org.eclipse.jdt.internal.compiler.problem.ShouldNotImplement;
import org.eclipse.jdt.internal.compiler.util.Messages;
import org.eclipse.jdt.internal.compiler.util.Util;
import org.luaj.vm2.Lua;

/* JADX INFO: loaded from: classes14.dex */
public class ClassFile implements TypeConstants, TypeIds {
    public static final int INITIAL_CONTENTS_SIZE = 400;
    public static final int INITIAL_HEADER_SIZE = 1500;
    public static final int INNER_CLASSES_SIZE = 5;
    public List bootstrapMethods;
    private byte[] bytes;
    public CodeStream codeStream;
    public ConstantPool constantPool;
    public int constantPoolOffset;
    public byte[] contents;
    public int contentsOffset;
    protected boolean creatingProblemType;
    public ClassFile enclosingClassFile;
    public byte[] header;
    public int headerOffset;
    public Map<TypeBinding, Boolean> innerClassesBindings;
    public boolean isNestedType;
    boolean isShared;
    public int methodCount;
    public int methodCountOffset;
    public List<TypeBinding> missingTypes;
    public int produceAttributes;
    public SourceTypeBinding referenceBinding;
    public long targetJDK;
    public Set visitedTypes;

    public static void createProblemType(TypeDeclaration typeDeclaration, CompilationResult compilationResult) {
        boolean z;
        SourceTypeBinding sourceTypeBinding = typeDeclaration.binding;
        ClassFile newInstance = getNewInstance(sourceTypeBinding);
        newInstance.initialize(sourceTypeBinding, null, true);
        if (sourceTypeBinding.hasMemberTypes()) {
            for (ReferenceBinding referenceBinding : sourceTypeBinding.memberTypes) {
                newInstance.recordInnerClasses(referenceBinding);
            }
        }
        if (sourceTypeBinding.isNestedType()) {
            newInstance.recordInnerClasses(sourceTypeBinding);
        }
        for (TypeVariableBinding typeVariableBinding : sourceTypeBinding.typeVariables()) {
            if ((typeVariableBinding.tagBits & 2048) != 0) {
                Util.recordNestedType(newInstance, typeVariableBinding);
            }
        }
        FieldBinding[] fieldBindingArrFields = sourceTypeBinding.fields();
        if (fieldBindingArrFields != null && fieldBindingArrFields != Binding.NO_FIELDS) {
            newInstance.addFieldInfos();
        } else {
            byte[] bArr = newInstance.contents;
            int i = newInstance.contentsOffset;
            int i2 = i + 1;
            newInstance.contentsOffset = i2;
            bArr[i] = 0;
            newInstance.contentsOffset = i + 2;
            bArr[i2] = 0;
        }
        newInstance.setForMethodInfos();
        CategorizedProblem[] errors = compilationResult.getErrors();
        if (errors == null) {
            errors = new CategorizedProblem[0];
        }
        int length = errors.length;
        CategorizedProblem[] categorizedProblemArr = new CategorizedProblem[length];
        System.arraycopy(errors, 0, categorizedProblemArr, 0, length);
        AbstractMethodDeclaration[] abstractMethodDeclarationArr = typeDeclaration.methods;
        if (abstractMethodDeclarationArr != null) {
            if (sourceTypeBinding.isInterface()) {
                z = sourceTypeBinding.scope.compilerOptions().sourceLevel < ClassFileConstants.JDK1_8;
                newInstance.addProblemClinit(categorizedProblemArr);
            } else {
                z = false;
            }
            for (AbstractMethodDeclaration abstractMethodDeclaration : abstractMethodDeclarationArr) {
                MethodBinding methodBinding = abstractMethodDeclaration.binding;
                if (methodBinding != null) {
                    if (z) {
                        methodBinding.modifiers = 1025;
                    }
                    if (methodBinding.isConstructor()) {
                        if (!sourceTypeBinding.isInterface()) {
                            newInstance.addProblemConstructor(abstractMethodDeclaration, methodBinding, categorizedProblemArr);
                        }
                    } else if (methodBinding.isAbstract()) {
                        newInstance.addAbstractMethod(abstractMethodDeclaration, methodBinding);
                    } else {
                        newInstance.addProblemMethod(abstractMethodDeclaration, methodBinding, categorizedProblemArr);
                    }
                }
            }
            newInstance.addDefaultAbstractMethods();
        }
        if (typeDeclaration.memberTypes != null) {
            int length2 = typeDeclaration.memberTypes.length;
            for (int i3 = 0; i3 < length2; i3++) {
                TypeDeclaration typeDeclaration2 = typeDeclaration.memberTypes[i3];
                if (typeDeclaration2.binding != null) {
                    createProblemType(typeDeclaration2, compilationResult);
                }
            }
        }
        newInstance.addAttributes();
        compilationResult.record(sourceTypeBinding.constantPoolName(), newInstance);
    }

    public static ClassFile getNewInstance(SourceTypeBinding sourceTypeBinding) {
        return sourceTypeBinding.scope.environment().classFilePool.acquire(sourceTypeBinding);
    }

    protected ClassFile() {
        this.bootstrapMethods = null;
        this.isShared = false;
        this.missingTypes = null;
    }

    public ClassFile(SourceTypeBinding sourceTypeBinding) {
        this.bootstrapMethods = null;
        this.isShared = false;
        this.missingTypes = null;
        this.constantPool = new ConstantPool(this);
        CompilerOptions compilerOptions = sourceTypeBinding.scope.compilerOptions();
        this.targetJDK = compilerOptions.targetJDK;
        this.produceAttributes = compilerOptions.produceDebugAttributes;
        this.referenceBinding = sourceTypeBinding;
        this.isNestedType = sourceTypeBinding.isNestedType();
        long j = this.targetJDK;
        if (j >= ClassFileConstants.JDK1_6) {
            int i = this.produceAttributes;
            this.produceAttributes = i | 8;
            if (j >= ClassFileConstants.JDK1_8) {
                this.produceAttributes = i | 40;
                this.codeStream = new TypeAnnotationCodeStream(this);
                if (compilerOptions.produceMethodParameters) {
                    this.produceAttributes |= 64;
                }
            } else {
                this.codeStream = new StackMapFrameCodeStream(this);
            }
        } else if (j == ClassFileConstants.CLDC_1_1) {
            this.targetJDK = ClassFileConstants.JDK1_1;
            this.produceAttributes |= 16;
            this.codeStream = new StackMapFrameCodeStream(this);
        } else {
            this.codeStream = new CodeStream(this);
        }
        initByteArrays();
    }

    public void addAbstractMethod(AbstractMethodDeclaration abstractMethodDeclaration, MethodBinding methodBinding) {
        generateMethodInfoHeader(methodBinding);
        completeMethodInfo(methodBinding, this.contentsOffset, generateMethodInfoAttributes(methodBinding));
    }

    public void addAttributes() {
        int iGenerateInnerClassAttribute;
        TypeDeclaration typeDeclaration;
        Annotation[] annotationArr;
        long j;
        byte[] bArr = this.contents;
        int i = this.methodCountOffset;
        int i2 = i + 1;
        this.methodCountOffset = i2;
        int i3 = this.methodCount;
        bArr[i] = (byte) (i3 >> 8);
        bArr[i2] = (byte) i3;
        int i4 = this.contentsOffset;
        this.contentsOffset = i4 + 2;
        if ((this.produceAttributes & 1) != 0) {
            String strReplace = new String(this.referenceBinding.scope.referenceCompilationUnit().getFileName()).replace('\\', '/');
            int iLastIndexOf = strReplace.lastIndexOf(47);
            if (iLastIndexOf != -1) {
                strReplace = strReplace.substring(iLastIndexOf + 1, strReplace.length());
            }
            iGenerateInnerClassAttribute = generateSourceAttribute(strReplace);
        } else {
            iGenerateInnerClassAttribute = 0;
        }
        if (this.referenceBinding.isDeprecated()) {
            iGenerateInnerClassAttribute += generateDeprecatedAttribute();
        }
        char[] cArrGenericSignature = this.referenceBinding.genericSignature();
        if (cArrGenericSignature != null) {
            iGenerateInnerClassAttribute += generateSignatureAttribute(cArrGenericSignature);
        }
        if (this.targetJDK >= ClassFileConstants.JDK1_5 && this.referenceBinding.isNestedType() && !this.referenceBinding.isMemberType()) {
            iGenerateInnerClassAttribute += generateEnclosingMethodAttribute();
        }
        if (this.targetJDK >= ClassFileConstants.JDK1_4 && (typeDeclaration = this.referenceBinding.scope.referenceContext) != null && (annotationArr = typeDeclaration.annotations) != null) {
            if (typeDeclaration.isPackageInfo()) {
                j = 8796093022208L;
            } else {
                j = this.referenceBinding.isAnnotationType() ? 4466765987840L : 9007267974217728L;
            }
            iGenerateInnerClassAttribute += generateRuntimeAnnotations(annotationArr, j);
        }
        if (this.referenceBinding.isHierarchyInconsistent()) {
            ReferenceBinding referenceBinding = this.referenceBinding.superclass;
            if (referenceBinding != null) {
                this.missingTypes = referenceBinding.collectMissingTypes(this.missingTypes);
            }
            for (ReferenceBinding referenceBinding2 : this.referenceBinding.superInterfaces()) {
                this.missingTypes = referenceBinding2.collectMissingTypes(this.missingTypes);
            }
            iGenerateInnerClassAttribute += generateHierarchyInconsistentAttribute();
        }
        List list = this.bootstrapMethods;
        if (list != null && !list.isEmpty()) {
            iGenerateInnerClassAttribute += generateBootstrapMethods(this.bootstrapMethods);
        }
        Map<TypeBinding, Boolean> map = this.innerClassesBindings;
        int size = map != null ? map.size() : 0;
        if (size != 0) {
            ReferenceBinding[] referenceBindingArr = new ReferenceBinding[size];
            this.innerClassesBindings.keySet().toArray(referenceBindingArr);
            Arrays.sort(referenceBindingArr, new Comparator() { // from class: org.eclipse.jdt.internal.compiler.ClassFile.1
                @Override // java.util.Comparator
                public int compare(Object obj, Object obj2) {
                    TypeBinding typeBinding = (TypeBinding) obj;
                    TypeBinding typeBinding2 = (TypeBinding) obj2;
                    Boolean bool = ClassFile.this.innerClassesBindings.get(obj);
                    Boolean bool2 = ClassFile.this.innerClassesBindings.get(obj2);
                    if (bool.booleanValue()) {
                        if (!bool2.booleanValue()) {
                            return 1;
                        }
                    } else if (bool2.booleanValue()) {
                        return -1;
                    }
                    return CharOperation.compareTo(typeBinding.constantPoolName(), typeBinding2.constantPoolName());
                }
            });
            iGenerateInnerClassAttribute += generateInnerClassAttribute(size, referenceBindingArr);
        }
        if (this.missingTypes != null) {
            generateMissingTypesAttribute();
            iGenerateInnerClassAttribute++;
        }
        int iGenerateTypeAnnotationAttributeForTypeDeclaration = iGenerateInnerClassAttribute + generateTypeAnnotationAttributeForTypeDeclaration();
        if (i4 + 2 >= this.contents.length) {
            resizeContents(2);
        }
        byte[] bArr2 = this.contents;
        bArr2[i4] = (byte) (iGenerateTypeAnnotationAttributeForTypeDeclaration >> 8);
        bArr2[i4 + 1] = (byte) iGenerateTypeAnnotationAttributeForTypeDeclaration;
        this.header = this.constantPool.poolContent;
        this.headerOffset = this.constantPool.currentOffset;
        int i5 = this.constantPool.currentIndex;
        byte[] bArr3 = this.header;
        int i6 = this.constantPoolOffset;
        int i7 = i6 + 1;
        this.constantPoolOffset = i7;
        bArr3[i6] = (byte) (i5 >> 8);
        bArr3[i7] = (byte) i5;
    }

    public void addDefaultAbstractMethods() {
        for (MethodBinding methodBinding : this.referenceBinding.getDefaultAbstractMethods()) {
            generateMethodInfoHeader(methodBinding);
            completeMethodInfo(methodBinding, this.contentsOffset, generateMethodInfoAttributes(methodBinding));
        }
    }

    private int addFieldAttributes(FieldBinding fieldBinding, int i) {
        FieldDeclaration fieldDeclarationSourceField;
        Constant constant = fieldBinding.constant();
        int iGenerateConstantValueAttribute = constant != Constant.NotAConstant ? generateConstantValueAttribute(constant, fieldBinding, i) : 0;
        if (this.targetJDK < ClassFileConstants.JDK1_5 && fieldBinding.isSynthetic()) {
            iGenerateConstantValueAttribute += generateSyntheticAttribute();
        }
        if (fieldBinding.isDeprecated()) {
            iGenerateConstantValueAttribute += generateDeprecatedAttribute();
        }
        char[] cArrGenericSignature = fieldBinding.genericSignature();
        if (cArrGenericSignature != null) {
            iGenerateConstantValueAttribute += generateSignatureAttribute(cArrGenericSignature);
        }
        if (this.targetJDK >= ClassFileConstants.JDK1_4 && (fieldDeclarationSourceField = fieldBinding.sourceField()) != null) {
            Annotation[] annotationArr = fieldDeclarationSourceField.annotations;
            if (annotationArr != null) {
                iGenerateConstantValueAttribute += generateRuntimeAnnotations(annotationArr, 137438953472L);
            }
            if ((this.produceAttributes & 32) != 0) {
                ArrayList arrayList = new ArrayList();
                if (annotationArr != null && (fieldDeclarationSourceField.bits & 1048576) != 0) {
                    fieldDeclarationSourceField.getAllAnnotationContexts(19, arrayList);
                }
                TypeReference typeReference = fieldDeclarationSourceField.type;
                if (typeReference != null && (typeReference.bits & 1048576) != 0) {
                    typeReference.getAllAnnotationContexts(19, arrayList);
                }
                int size = arrayList.size();
                if (size != 0) {
                    AnnotationContext[] annotationContextArr = new AnnotationContext[size];
                    arrayList.toArray(annotationContextArr);
                    int i2 = 0;
                    int i3 = 0;
                    for (int i4 = 0; i4 < size; i4++) {
                        AnnotationContext annotationContext = annotationContextArr[i4];
                        if ((annotationContext.visibility & 2) != 0) {
                            i3++;
                            arrayList.add(annotationContext);
                        } else {
                            i2++;
                            arrayList.add(annotationContext);
                        }
                    }
                    iGenerateConstantValueAttribute += generateRuntimeTypeAnnotations(annotationContextArr, i2, i3);
                }
            }
        }
        if ((fieldBinding.tagBits & 128) != 0) {
            this.missingTypes = fieldBinding.type.collectMissingTypes(this.missingTypes);
        }
        return iGenerateConstantValueAttribute;
    }

    private void addFieldInfo(FieldBinding fieldBinding) {
        if (this.contentsOffset + 8 >= this.contents.length) {
            resizeContents(8);
        }
        int accessFlags = fieldBinding.getAccessFlags();
        if (this.targetJDK < ClassFileConstants.JDK1_5) {
            accessFlags &= -4097;
        }
        byte[] bArr = this.contents;
        int i = this.contentsOffset;
        int i2 = i + 1;
        this.contentsOffset = i2;
        bArr[i] = (byte) (accessFlags >> 8);
        this.contentsOffset = i + 2;
        bArr[i2] = (byte) accessFlags;
        int iLiteralIndex = this.constantPool.literalIndex(fieldBinding.f628name);
        byte[] bArr2 = this.contents;
        int i3 = this.contentsOffset;
        int i4 = i3 + 1;
        this.contentsOffset = i4;
        bArr2[i3] = (byte) (iLiteralIndex >> 8);
        this.contentsOffset = i3 + 2;
        bArr2[i4] = (byte) iLiteralIndex;
        int iLiteralIndex2 = this.constantPool.literalIndex(fieldBinding.type);
        byte[] bArr3 = this.contents;
        int i5 = this.contentsOffset;
        int i6 = i5 + 1;
        this.contentsOffset = i6;
        bArr3[i5] = (byte) (iLiteralIndex2 >> 8);
        int i7 = i5 + 2;
        this.contentsOffset = i7;
        bArr3[i6] = (byte) iLiteralIndex2;
        this.contentsOffset = i5 + 4;
        int iAddFieldAttributes = addFieldAttributes(fieldBinding, i7);
        if (this.contentsOffset + 2 >= this.contents.length) {
            resizeContents(2);
        }
        byte[] bArr4 = this.contents;
        bArr4[i7] = (byte) (iAddFieldAttributes >> 8);
        bArr4[i5 + 3] = (byte) iAddFieldAttributes;
    }

    public void addFieldInfos() {
        SourceTypeBinding sourceTypeBinding = this.referenceBinding;
        FieldBinding[] fieldBindingArrSyntheticFields = sourceTypeBinding.syntheticFields();
        int iFieldCount = sourceTypeBinding.fieldCount() + (fieldBindingArrSyntheticFields == null ? 0 : fieldBindingArrSyntheticFields.length);
        if (iFieldCount > 65535) {
            this.referenceBinding.scope.problemReporter().tooManyFields(this.referenceBinding.scope.referenceType());
        }
        byte[] bArr = this.contents;
        int i = this.contentsOffset;
        int i2 = i + 1;
        this.contentsOffset = i2;
        bArr[i] = (byte) (iFieldCount >> 8);
        this.contentsOffset = i + 2;
        bArr[i2] = (byte) iFieldCount;
        FieldDeclaration[] fieldDeclarationArr = sourceTypeBinding.scope.referenceContext.fields;
        int length = fieldDeclarationArr == null ? 0 : fieldDeclarationArr.length;
        for (int i3 = 0; i3 < length; i3++) {
            FieldDeclaration fieldDeclaration = fieldDeclarationArr[i3];
            if (fieldDeclaration.binding != null) {
                addFieldInfo(fieldDeclaration.binding);
            }
        }
        if (fieldBindingArrSyntheticFields != null) {
            for (FieldBinding fieldBinding : fieldBindingArrSyntheticFields) {
                addFieldInfo(fieldBinding);
            }
        }
    }

    private void addMissingAbstractProblemMethod(MethodDeclaration methodDeclaration, MethodBinding methodBinding, CategorizedProblem categorizedProblem, CompilationResult compilationResult) {
        generateMethodInfoHeader(methodBinding, methodBinding.modifiers & (-3329));
        int i = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(methodBinding) + 1;
        int i2 = this.contentsOffset;
        generateCodeAttributeHeader();
        StringBuffer stringBuffer = new StringBuffer(25);
        stringBuffer.append("\t" + categorizedProblem.getMessage() + "\n");
        stringBuffer.insert(0, Messages.compilation_unresolvedProblem);
        String string = stringBuffer.toString();
        this.codeStream.init(this);
        this.codeStream.preserveUnusedLocals = true;
        this.codeStream.initializeMaxLocals(methodBinding);
        this.codeStream.generateCodeAttributeForProblemMethod(string);
        completeCodeAttributeForMissingAbstractProblemMethod(methodBinding, i2, compilationResult.getLineSeparatorPositions(), categorizedProblem.getSourceLineNumber());
        completeMethodInfo(methodBinding, i, iGenerateMethodInfoAttributes);
    }

    public void addProblemClinit(CategorizedProblem[] categorizedProblemArr) {
        String string;
        int sourceLineNumber;
        generateMethodInfoHeaderForClinit();
        int i = this.contentsOffset;
        int i2 = i - 2;
        this.contentsOffset = i;
        generateCodeAttributeHeader();
        this.codeStream.resetForProblemClinit(this);
        if (categorizedProblemArr == null) {
            string = "";
            sourceLineNumber = 0;
        } else {
            int length = categorizedProblemArr.length;
            StringBuffer stringBuffer = new StringBuffer(25);
            int i3 = 0;
            sourceLineNumber = 0;
            for (int i4 = 0; i4 < length; i4++) {
                CategorizedProblem categorizedProblem = categorizedProblemArr[i4];
                if (categorizedProblem != null && categorizedProblem.isError()) {
                    stringBuffer.append("\t" + categorizedProblem.getMessage() + "\n");
                    i3++;
                    if (sourceLineNumber == 0) {
                        sourceLineNumber = categorizedProblem.getSourceLineNumber();
                    }
                    categorizedProblemArr[i4] = null;
                }
            }
            if (i3 > 1) {
                stringBuffer.insert(0, Messages.compilation_unresolvedProblems);
            } else {
                stringBuffer.insert(0, Messages.compilation_unresolvedProblem);
            }
            string = stringBuffer.toString();
        }
        this.codeStream.generateCodeAttributeForProblemMethod(string);
        completeCodeAttributeForClinit(i, sourceLineNumber);
        if (this.contentsOffset + 2 >= this.contents.length) {
            resizeContents(2);
        }
        byte[] bArr = this.contents;
        bArr[i2] = (byte) 0;
        bArr[i - 1] = (byte) 1;
    }

    public void addProblemConstructor(AbstractMethodDeclaration abstractMethodDeclaration, MethodBinding methodBinding, CategorizedProblem[] categorizedProblemArr) {
        String string;
        int i;
        if (methodBinding.declaringClass.isInterface()) {
            abstractMethodDeclaration.abort(8, null);
        }
        generateMethodInfoHeader(methodBinding, methodBinding.modifiers & (-3329));
        int i2 = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(methodBinding) + 1;
        int i3 = this.contentsOffset;
        generateCodeAttributeHeader();
        this.codeStream.reset(abstractMethodDeclaration, this);
        int i4 = 0;
        if (categorizedProblemArr == null) {
            string = "";
            i = 0;
        } else {
            int length = categorizedProblemArr.length;
            StringBuffer stringBuffer = new StringBuffer(25);
            int i5 = 0;
            int i6 = 0;
            int sourceLineNumber = 0;
            while (i5 < length) {
                CategorizedProblem categorizedProblem = categorizedProblemArr[i5];
                if (categorizedProblem != null && categorizedProblem.isError()) {
                    stringBuffer.append("\t" + categorizedProblem.getMessage() + "\n");
                    i6++;
                    if (sourceLineNumber == 0) {
                        sourceLineNumber = categorizedProblem.getSourceLineNumber();
                    }
                }
                i5++;
                i4 = 0;
            }
            if (i6 > 1) {
                stringBuffer.insert(i4, Messages.compilation_unresolvedProblems);
            } else {
                stringBuffer.insert(i4, Messages.compilation_unresolvedProblem);
            }
            string = stringBuffer.toString();
            i = sourceLineNumber;
        }
        this.codeStream.generateCodeAttributeForProblemMethod(string);
        completeCodeAttributeForProblemMethod(abstractMethodDeclaration, methodBinding, i3, ((SourceTypeBinding) methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions(), i);
        completeMethodInfo(methodBinding, i2, iGenerateMethodInfoAttributes);
    }

    public void addProblemConstructor(AbstractMethodDeclaration abstractMethodDeclaration, MethodBinding methodBinding, CategorizedProblem[] categorizedProblemArr, int i) {
        this.contentsOffset = i;
        this.methodCount--;
        addProblemConstructor(abstractMethodDeclaration, methodBinding, categorizedProblemArr);
    }

    public void addProblemMethod(AbstractMethodDeclaration abstractMethodDeclaration, MethodBinding methodBinding, CategorizedProblem[] categorizedProblemArr) {
        String string;
        int sourceLineNumber;
        if (methodBinding.isAbstract() && methodBinding.declaringClass.isInterface()) {
            abstractMethodDeclaration.abort(8, null);
        }
        generateMethodInfoHeader(methodBinding, methodBinding.modifiers & (-3329));
        int i = this.contentsOffset;
        int i2 = 1;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(methodBinding) + 1;
        int i3 = this.contentsOffset;
        generateCodeAttributeHeader();
        this.codeStream.reset(abstractMethodDeclaration, this);
        int i4 = 0;
        if (categorizedProblemArr == null) {
            string = "";
            sourceLineNumber = 0;
        } else {
            int length = categorizedProblemArr.length;
            StringBuffer stringBuffer = new StringBuffer(25);
            int i5 = 0;
            int i6 = 0;
            sourceLineNumber = 0;
            while (i5 < length) {
                CategorizedProblem categorizedProblem = categorizedProblemArr[i5];
                if (categorizedProblem != null && categorizedProblem.isError() && categorizedProblem.getSourceStart() >= abstractMethodDeclaration.declarationSourceStart && categorizedProblem.getSourceEnd() <= abstractMethodDeclaration.declarationSourceEnd) {
                    stringBuffer.append("\t" + categorizedProblem.getMessage() + "\n");
                    i6++;
                    if (sourceLineNumber == 0) {
                        sourceLineNumber = categorizedProblem.getSourceLineNumber();
                    }
                    categorizedProblemArr[i5] = null;
                }
                i5++;
                i4 = 0;
                i2 = 1;
            }
            if (i6 > i2) {
                stringBuffer.insert(i4, Messages.compilation_unresolvedProblems);
            } else {
                stringBuffer.insert(i4, Messages.compilation_unresolvedProblem);
            }
            string = stringBuffer.toString();
        }
        this.codeStream.generateCodeAttributeForProblemMethod(string);
        completeCodeAttributeForProblemMethod(abstractMethodDeclaration, methodBinding, i3, ((SourceTypeBinding) methodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions(), sourceLineNumber);
        completeMethodInfo(methodBinding, i, iGenerateMethodInfoAttributes);
    }

    public void addProblemMethod(AbstractMethodDeclaration abstractMethodDeclaration, MethodBinding methodBinding, CategorizedProblem[] categorizedProblemArr, int i) {
        this.contentsOffset = i;
        this.methodCount--;
        addProblemMethod(abstractMethodDeclaration, methodBinding, categorizedProblemArr);
    }

    public void addSpecialMethods() {
        boolean z;
        generateMissingAbstractMethods(this.referenceBinding.scope.referenceType().missingAbstractMethods, this.referenceBinding.scope.referenceCompilationUnit().compilationResult);
        for (MethodBinding methodBinding : this.referenceBinding.getDefaultAbstractMethods()) {
            generateMethodInfoHeader(methodBinding);
            completeMethodInfo(methodBinding, this.contentsOffset, generateMethodInfoAttributes(methodBinding));
        }
        SyntheticMethodBinding syntheticMethodBinding = null;
        int i = 0;
        boolean z2 = true;
        while (z2) {
            SyntheticMethodBinding[] syntheticMethodBindingArrSyntheticMethods = this.referenceBinding.syntheticMethods();
            int length = syntheticMethodBindingArrSyntheticMethods == null ? 0 : syntheticMethodBindingArrSyntheticMethods.length;
            if (i != length) {
                boolean z3 = false;
                for (int i2 = i; i2 < length; i2++) {
                    SyntheticMethodBinding syntheticMethodBinding2 = syntheticMethodBindingArrSyntheticMethods[i2];
                    switch (syntheticMethodBinding2.purpose) {
                        case 1:
                        case 3:
                            addSyntheticFieldReadAccessMethod(syntheticMethodBinding2);
                            break;
                        case 2:
                        case 4:
                            addSyntheticFieldWriteAccessMethod(syntheticMethodBinding2);
                            break;
                        case 5:
                        case 7:
                        case 8:
                            addSyntheticMethodAccessMethod(syntheticMethodBinding2);
                            break;
                        case 6:
                            addSyntheticConstructorAccessMethod(syntheticMethodBinding2);
                            break;
                        case 9:
                            addSyntheticEnumValuesMethod(syntheticMethodBinding2);
                            break;
                        case 10:
                            addSyntheticEnumValueOfMethod(syntheticMethodBinding2);
                            break;
                        case 11:
                            addSyntheticSwitchTable(syntheticMethodBinding2);
                            break;
                        case 12:
                            addSyntheticEnumInitializationMethod(syntheticMethodBinding2);
                            break;
                        case 13:
                            syntheticMethodBinding2.lambda.generateCode(this.referenceBinding.scope, this);
                            z3 = true;
                            break;
                        case 14:
                            addSyntheticArrayConstructor(syntheticMethodBinding2);
                            break;
                        case 15:
                            addSyntheticArrayClone(syntheticMethodBinding2);
                            break;
                        case 16:
                            addSyntheticFactoryMethod(syntheticMethodBinding2);
                            break;
                        case 17:
                            syntheticMethodBinding = syntheticMethodBinding2;
                            break;
                    }
                }
                z2 = z3;
                i = length;
            } else {
                z2 = false;
            }
        }
        if (syntheticMethodBinding != null) {
            this.codeStream.wideMode = false;
            int i3 = 0;
            do {
                try {
                    i3 = this.contentsOffset;
                    addSyntheticDeserializeLambda(syntheticMethodBinding, this.referenceBinding.syntheticMethods());
                    z = false;
                } catch (AbortMethod e) {
                    if (e.compilationResult == CodeStream.RESTART_IN_WIDE_MODE) {
                        this.contentsOffset = i3;
                        this.methodCount--;
                        this.codeStream.resetInWideMode();
                        z = true;
                    } else {
                        throw new AbortType(this.referenceBinding.scope.referenceContext.compilationResult, e.problem);
                    }
                }
            } while (z);
        }
    }

    public void addSyntheticArrayConstructor(SyntheticMethodBinding syntheticMethodBinding) {
        generateMethodInfoHeader(syntheticMethodBinding);
        int i = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(syntheticMethodBinding);
        int i2 = this.contentsOffset;
        int i3 = iGenerateMethodInfoAttributes + 1;
        generateCodeAttributeHeader();
        this.codeStream.init(this);
        this.codeStream.generateSyntheticBodyForArrayConstructor(syntheticMethodBinding);
        completeCodeAttributeForSyntheticMethod(syntheticMethodBinding, i2, ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
        byte[] bArr = this.contents;
        bArr[i] = (byte) (i3 >> 8);
        bArr[i + 1] = (byte) i3;
    }

    public void addSyntheticArrayClone(SyntheticMethodBinding syntheticMethodBinding) {
        generateMethodInfoHeader(syntheticMethodBinding);
        int i = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(syntheticMethodBinding);
        int i2 = this.contentsOffset;
        int i3 = iGenerateMethodInfoAttributes + 1;
        generateCodeAttributeHeader();
        this.codeStream.init(this);
        this.codeStream.generateSyntheticBodyForArrayClone(syntheticMethodBinding);
        completeCodeAttributeForSyntheticMethod(syntheticMethodBinding, i2, ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
        byte[] bArr = this.contents;
        bArr[i] = (byte) (i3 >> 8);
        bArr[i + 1] = (byte) i3;
    }

    public void addSyntheticFactoryMethod(SyntheticMethodBinding syntheticMethodBinding) {
        generateMethodInfoHeader(syntheticMethodBinding);
        int i = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(syntheticMethodBinding);
        int i2 = this.contentsOffset;
        int i3 = iGenerateMethodInfoAttributes + 1;
        generateCodeAttributeHeader();
        this.codeStream.init(this);
        this.codeStream.generateSyntheticBodyForFactoryMethod(syntheticMethodBinding);
        completeCodeAttributeForSyntheticMethod(syntheticMethodBinding, i2, ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
        byte[] bArr = this.contents;
        bArr[i] = (byte) (i3 >> 8);
        bArr[i + 1] = (byte) i3;
    }

    public void addSyntheticConstructorAccessMethod(SyntheticMethodBinding syntheticMethodBinding) {
        generateMethodInfoHeader(syntheticMethodBinding);
        int i = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(syntheticMethodBinding);
        int i2 = this.contentsOffset;
        int i3 = iGenerateMethodInfoAttributes + 1;
        generateCodeAttributeHeader();
        this.codeStream.init(this);
        this.codeStream.generateSyntheticBodyForConstructorAccess(syntheticMethodBinding);
        completeCodeAttributeForSyntheticMethod(syntheticMethodBinding, i2, ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
        byte[] bArr = this.contents;
        bArr[i] = (byte) (i3 >> 8);
        bArr[i + 1] = (byte) i3;
    }

    public void addSyntheticEnumValueOfMethod(SyntheticMethodBinding syntheticMethodBinding) {
        generateMethodInfoHeader(syntheticMethodBinding);
        int i = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(syntheticMethodBinding);
        int i2 = this.contentsOffset;
        int iGenerateMethodParameters = iGenerateMethodInfoAttributes + 1;
        generateCodeAttributeHeader();
        this.codeStream.init(this);
        this.codeStream.generateSyntheticBodyForEnumValueOf(syntheticMethodBinding);
        completeCodeAttributeForSyntheticMethod(syntheticMethodBinding, i2, ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
        if ((this.produceAttributes & 64) != 0) {
            iGenerateMethodParameters += generateMethodParameters(syntheticMethodBinding);
        }
        byte[] bArr = this.contents;
        bArr[i] = (byte) (iGenerateMethodParameters >> 8);
        bArr[i + 1] = (byte) iGenerateMethodParameters;
    }

    public void addSyntheticEnumValuesMethod(SyntheticMethodBinding syntheticMethodBinding) {
        generateMethodInfoHeader(syntheticMethodBinding);
        int i = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(syntheticMethodBinding);
        int i2 = this.contentsOffset;
        int i3 = iGenerateMethodInfoAttributes + 1;
        generateCodeAttributeHeader();
        this.codeStream.init(this);
        this.codeStream.generateSyntheticBodyForEnumValues(syntheticMethodBinding);
        completeCodeAttributeForSyntheticMethod(syntheticMethodBinding, i2, ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
        byte[] bArr = this.contents;
        bArr[i] = (byte) (i3 >> 8);
        bArr[i + 1] = (byte) i3;
    }

    public void addSyntheticEnumInitializationMethod(SyntheticMethodBinding syntheticMethodBinding) {
        generateMethodInfoHeader(syntheticMethodBinding);
        int i = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(syntheticMethodBinding);
        int i2 = this.contentsOffset;
        int i3 = iGenerateMethodInfoAttributes + 1;
        generateCodeAttributeHeader();
        this.codeStream.init(this);
        this.codeStream.generateSyntheticBodyForEnumInitializationMethod(syntheticMethodBinding);
        completeCodeAttributeForSyntheticMethod(syntheticMethodBinding, i2, ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
        byte[] bArr = this.contents;
        bArr[i] = (byte) (i3 >> 8);
        bArr[i + 1] = (byte) i3;
    }

    public void addSyntheticFieldReadAccessMethod(SyntheticMethodBinding syntheticMethodBinding) {
        generateMethodInfoHeader(syntheticMethodBinding);
        int i = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(syntheticMethodBinding);
        int i2 = this.contentsOffset;
        int i3 = iGenerateMethodInfoAttributes + 1;
        generateCodeAttributeHeader();
        this.codeStream.init(this);
        this.codeStream.generateSyntheticBodyForFieldReadAccess(syntheticMethodBinding);
        completeCodeAttributeForSyntheticMethod(syntheticMethodBinding, i2, ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
        byte[] bArr = this.contents;
        bArr[i] = (byte) (i3 >> 8);
        bArr[i + 1] = (byte) i3;
    }

    public void addSyntheticFieldWriteAccessMethod(SyntheticMethodBinding syntheticMethodBinding) {
        generateMethodInfoHeader(syntheticMethodBinding);
        int i = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(syntheticMethodBinding);
        int i2 = this.contentsOffset;
        int i3 = iGenerateMethodInfoAttributes + 1;
        generateCodeAttributeHeader();
        this.codeStream.init(this);
        this.codeStream.generateSyntheticBodyForFieldWriteAccess(syntheticMethodBinding);
        completeCodeAttributeForSyntheticMethod(syntheticMethodBinding, i2, ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
        byte[] bArr = this.contents;
        bArr[i] = (byte) (i3 >> 8);
        bArr[i + 1] = (byte) i3;
    }

    public void addSyntheticMethodAccessMethod(SyntheticMethodBinding syntheticMethodBinding) {
        generateMethodInfoHeader(syntheticMethodBinding);
        int i = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(syntheticMethodBinding);
        int i2 = this.contentsOffset;
        int i3 = iGenerateMethodInfoAttributes + 1;
        generateCodeAttributeHeader();
        this.codeStream.init(this);
        this.codeStream.generateSyntheticBodyForMethodAccess(syntheticMethodBinding);
        completeCodeAttributeForSyntheticMethod(syntheticMethodBinding, i2, ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
        byte[] bArr = this.contents;
        bArr[i] = (byte) (i3 >> 8);
        bArr[i + 1] = (byte) i3;
    }

    public void addSyntheticSwitchTable(SyntheticMethodBinding syntheticMethodBinding) {
        generateMethodInfoHeader(syntheticMethodBinding);
        int i = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(syntheticMethodBinding);
        int i2 = this.contentsOffset;
        int i3 = iGenerateMethodInfoAttributes + 1;
        generateCodeAttributeHeader();
        this.codeStream.init(this);
        this.codeStream.generateSyntheticBodyForSwitchTable(syntheticMethodBinding);
        completeCodeAttributeForSyntheticMethod(true, syntheticMethodBinding, i2, ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
        byte[] bArr = this.contents;
        bArr[i] = (byte) (i3 >> 8);
        bArr[i + 1] = (byte) i3;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v44 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6, types: [int] */
    /* JADX WARN: Type inference failed for: r11v10 */
    /* JADX WARN: Type inference failed for: r11v11, types: [int] */
    /* JADX WARN: Type inference failed for: r11v12, types: [int] */
    public void completeCodeAttribute(int i) {
        int i2;
        int iLiteralIndexForType;
        this.contents = this.codeStream.bCodeStream;
        int i3 = this.codeStream.classFileOffset;
        int i4 = this.codeStream.position;
        if (i4 > 65535) {
            if (this.codeStream.methodDeclaration != null) {
                this.codeStream.methodDeclaration.scope.problemReporter().bytecodeExceeds64KLimit(this.codeStream.methodDeclaration);
            } else {
                this.codeStream.lambdaExpression.scope.problemReporter().bytecodeExceeds64KLimit(this.codeStream.lambdaExpression);
            }
        }
        if (i3 + 20 >= this.contents.length) {
            resizeContents(20);
        }
        int i5 = this.codeStream.stackMax;
        byte[] bArr = this.contents;
        int i6 = i + 6;
        bArr[i6] = (byte) (i5 >> 8);
        bArr[i + 7] = (byte) i5;
        int i7 = this.codeStream.maxLocals;
        byte[] bArr2 = this.contents;
        bArr2[i + 8] = (byte) (i7 >> 8);
        bArr2[i + 9] = (byte) i7;
        bArr2[i + 10] = (byte) (i4 >> 24);
        bArr2[i + 11] = (byte) (i4 >> 16);
        bArr2[i + 12] = (byte) (i4 >> 8);
        bArr2[i + 13] = (byte) i4;
        boolean z = false;
        boolean z2 = (this.produceAttributes & 8) != 0;
        ExceptionLabel[] exceptionLabelArr = this.codeStream.exceptionLabels;
        int i8 = this.codeStream.exceptionLabelsCounter;
        int i9 = 0;
        int count = 0;
        while (true) {
            i2 = 2;
            if (i9 >= i8) {
                break;
            }
            count += this.codeStream.exceptionLabels[i9].getCount() / 2;
            i9++;
            exceptionLabelArr = exceptionLabelArr;
            z = false;
        }
        int i10 = (count * 8) + 2;
        if (i10 + i3 >= this.contents.length) {
            resizeContents(i10);
        }
        byte[] bArr3 = this.contents;
        bArr3[i3] = (byte) (count >> 8);
        bArr3[i3 + 1] = (byte) count;
        int i11 = this.codeStream.exceptionLabelsCounter;
        int i12 = i3 + 2;
        for (?? r0 = z; r0 < i11; r0++) {
            ExceptionLabel exceptionLabel = exceptionLabelArr[r0];
            if (exceptionLabel != null) {
                int count2 = exceptionLabel.getCount();
                if ((count2 & 1) != 0) {
                    if (this.codeStream.methodDeclaration != null) {
                        this.codeStream.methodDeclaration.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidExceptionAttribute, new String(this.codeStream.methodDeclaration.selector)), this.codeStream.methodDeclaration);
                    } else {
                        this.codeStream.lambdaExpression.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidExceptionAttribute, new String(this.codeStream.lambdaExpression.binding.selector)), this.codeStream.lambdaExpression);
                    }
                }
                ?? r11 = z;
                while (r11 < count2) {
                    int i13 = r11 + 1;
                    int i14 = exceptionLabel.ranges[r11];
                    byte[] bArr4 = this.contents;
                    bArr4[i12] = (byte) (i14 >> 8);
                    bArr4[i12 + 1] = (byte) i14;
                    r11 += 2;
                    int i15 = exceptionLabel.ranges[i13];
                    byte[] bArr5 = this.contents;
                    exceptionLabelArr = exceptionLabelArr;
                    bArr5[i12 + 2] = (byte) (i15 >> 8);
                    int i16 = i12 + 4;
                    bArr5[i12 + 3] = (byte) i15;
                    int i17 = exceptionLabel.position;
                    if (z2) {
                        ((StackMapFrameCodeStream) this.codeStream).addFramePosition(i17);
                    }
                    byte[] bArr6 = this.contents;
                    bArr6[i16] = (byte) (i17 >> 8);
                    int i18 = i12 + 6;
                    bArr6[i12 + 5] = (byte) i17;
                    if (exceptionLabel.exceptionType == null) {
                        byte[] bArr7 = this.contents;
                        int i19 = i12 + 7;
                        bArr7[i18] = 0;
                        i12 += 8;
                        bArr7[i19] = 0;
                    } else {
                        if (exceptionLabel.exceptionType == TypeBinding.NULL) {
                            iLiteralIndexForType = this.constantPool.literalIndexForType(ConstantPool.JavaLangClassNotFoundExceptionConstantPoolName);
                        } else {
                            iLiteralIndexForType = this.constantPool.literalIndexForType(exceptionLabel.exceptionType);
                        }
                        byte[] bArr8 = this.contents;
                        int i20 = i12 + 7;
                        bArr8[i18] = (byte) (iLiteralIndexForType >> 8);
                        i12 += 8;
                        bArr8[i20] = (byte) iLiteralIndexForType;
                    }
                }
            }
            exceptionLabelArr = exceptionLabelArr;
            z = false;
            i2 = 2;
        }
        int i21 = i12 + 2;
        if (i12 + 4 >= this.contents.length) {
            resizeContents(i2);
        }
        this.contentsOffset = i21;
        int iGenerateLineNumberAttribute = (this.produceAttributes & i2) != 0 ? generateLineNumberAttribute() : z;
        if ((this.produceAttributes & 4) != 0) {
            iGenerateLineNumberAttribute += generateLocalVariableTableAttribute(i4, this.codeStream.methodDeclaration != null ? this.codeStream.methodDeclaration.isStatic() : this.codeStream.lambdaExpression.binding.isStatic(), z);
        }
        int iGenerateTypeAnnotationsOnCodeAttribute = iGenerateLineNumberAttribute;
        if (z2) {
            iGenerateTypeAnnotationsOnCodeAttribute += generateStackMapTableAttribute(this.codeStream.methodDeclaration != null ? this.codeStream.methodDeclaration.binding : this.codeStream.lambdaExpression.binding, i4, i, i7, false);
        }
        if ((this.produceAttributes & 16) != 0) {
            iGenerateTypeAnnotationsOnCodeAttribute += generateStackMapAttribute(this.codeStream.methodDeclaration != null ? this.codeStream.methodDeclaration.binding : this.codeStream.lambdaExpression.binding, i4, i, i7, false);
        }
        if ((this.produceAttributes & 32) != 0) {
            iGenerateTypeAnnotationsOnCodeAttribute += generateTypeAnnotationsOnCodeAttribute();
        }
        byte[] bArr9 = this.contents;
        bArr9[i12] = (byte) (iGenerateTypeAnnotationsOnCodeAttribute >> 8);
        bArr9[i12 + 1] = (byte) iGenerateTypeAnnotationsOnCodeAttribute;
        int i22 = this.contentsOffset - i6;
        bArr9[i + 2] = (byte) (i22 >> 24);
        bArr9[i + 3] = (byte) (i22 >> 16);
        bArr9[i + 4] = (byte) (i22 >> 8);
        bArr9[i + 5] = (byte) i22;
    }

    public int generateTypeAnnotationsOnCodeAttribute() {
        LocalDeclaration localDeclaration;
        List list = ((TypeAnnotationCodeStream) this.codeStream).allTypeAnnotationContexts;
        int i = this.codeStream.allLocalsCounter;
        for (int i2 = 0; i2 < i; i2++) {
            LocalVariableBinding localVariableBinding = this.codeStream.locals[i2];
            if (!localVariableBinding.isCatchParameter() && (localDeclaration = localVariableBinding.declaration) != null && ((!localDeclaration.isArgument() || (localDeclaration.bits & 536870912) != 0) && localVariableBinding.initializationCount != 0 && (1048576 & localDeclaration.bits) != 0)) {
                localDeclaration.getAllAnnotationContexts((localVariableBinding.tagBits & 8192) == 0 ? 64 : 65, localVariableBinding, list);
            }
        }
        ExceptionLabel[] exceptionLabelArr = this.codeStream.exceptionLabels;
        int i3 = this.codeStream.exceptionLabelsCounter;
        for (int i4 = 0; i4 < i3; i4++) {
            ExceptionLabel exceptionLabel = exceptionLabelArr[i4];
            if (exceptionLabel.exceptionTypeReference != null && (exceptionLabel.exceptionTypeReference.bits & 1048576) != 0) {
                exceptionLabel.exceptionTypeReference.getAllAnnotationContexts(66, i4, list, exceptionLabel.se7Annotations);
            }
        }
        int size = list.size();
        if (size == 0) {
            return 0;
        }
        AnnotationContext[] annotationContextArr = new AnnotationContext[size];
        list.toArray(annotationContextArr);
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < size; i7++) {
            if ((annotationContextArr[i7].visibility & 2) != 0) {
                i6++;
            } else {
                i5++;
            }
        }
        return generateRuntimeTypeAnnotations(annotationContextArr, i5, i6);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v30 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6, types: [int] */
    /* JADX WARN: Type inference failed for: r16v1 */
    /* JADX WARN: Type inference failed for: r2v22 */
    /* JADX WARN: Type inference failed for: r2v23, types: [int] */
    public void completeCodeAttributeForClinit(int i) {
        int i2;
        int iLiteralIndexForType;
        this.contents = this.codeStream.bCodeStream;
        int i3 = this.codeStream.classFileOffset;
        int i4 = this.codeStream.position;
        if (i4 > 65535) {
            this.codeStream.methodDeclaration.scope.problemReporter().bytecodeExceeds64KLimit(this.codeStream.methodDeclaration.scope.referenceType());
        }
        if (i3 + 20 >= this.contents.length) {
            resizeContents(20);
        }
        int i5 = this.codeStream.stackMax;
        byte[] bArr = this.contents;
        int i6 = i + 6;
        bArr[i6] = (byte) (i5 >> 8);
        bArr[i + 7] = (byte) i5;
        int i7 = this.codeStream.maxLocals;
        byte[] bArr2 = this.contents;
        bArr2[i + 8] = (byte) (i7 >> 8);
        bArr2[i + 9] = (byte) i7;
        bArr2[i + 10] = (byte) (i4 >> 24);
        bArr2[i + 11] = (byte) (i4 >> 16);
        bArr2[i + 12] = (byte) (i4 >> 8);
        bArr2[i + 13] = (byte) i4;
        boolean z = true;
        boolean z2 = false;
        boolean z3 = (this.produceAttributes & 8) != 0;
        ExceptionLabel[] exceptionLabelArr = this.codeStream.exceptionLabels;
        int i8 = this.codeStream.exceptionLabelsCounter;
        int i9 = 0;
        int count = 0;
        while (true) {
            i2 = 2;
            if (i9 >= i8) {
                break;
            }
            count += this.codeStream.exceptionLabels[i9].getCount() / 2;
            i9++;
            z = true;
            z2 = false;
        }
        int i10 = (count * 8) + 2;
        if (i10 + i3 >= this.contents.length) {
            resizeContents(i10);
        }
        byte[] bArr3 = this.contents;
        bArr3[i3] = (byte) (count >> 8);
        bArr3[i3 + 1] = (byte) count;
        int i11 = this.codeStream.exceptionLabelsCounter;
        int i12 = i3 + 2;
        for (?? r0 = z2; r0 < i11; r0++) {
            ExceptionLabel exceptionLabel = exceptionLabelArr[r0];
            if (exceptionLabel != null) {
                int count2 = exceptionLabel.getCount();
                if ((count2 & 1) != 0) {
                    this.codeStream.methodDeclaration.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidExceptionAttribute, new String(this.codeStream.methodDeclaration.selector)), this.codeStream.methodDeclaration);
                }
                ?? r2 = z2;
                while (r2 < count2) {
                    int i13 = exceptionLabel.ranges[r2];
                    byte[] bArr4 = this.contents;
                    bArr4[i12] = (byte) (i13 >> 8);
                    bArr4[i12 + 1] = (byte) i13;
                    int i14 = exceptionLabel.ranges[r2 + 1];
                    byte[] bArr5 = this.contents;
                    r2 += 2;
                    bArr5[i12 + 2] = (byte) (i14 >> 8);
                    bArr5[i12 + 3] = (byte) i14;
                    int i15 = exceptionLabel.position;
                    byte[] bArr6 = this.contents;
                    bArr6[i12 + 4] = (byte) (i15 >> 8);
                    int i16 = i12 + 6;
                    bArr6[i12 + 5] = (byte) i15;
                    if (z3) {
                        ((StackMapFrameCodeStream) this.codeStream).addFramePosition(i15);
                    }
                    if (exceptionLabel.exceptionType == null) {
                        byte[] bArr7 = this.contents;
                        int i17 = i12 + 7;
                        bArr7[i16] = 0;
                        i12 += 8;
                        bArr7[i17] = 0;
                    } else {
                        if (exceptionLabel.exceptionType == TypeBinding.NULL) {
                            iLiteralIndexForType = this.constantPool.literalIndexForType(ConstantPool.JavaLangClassNotFoundExceptionConstantPoolName);
                        } else {
                            iLiteralIndexForType = this.constantPool.literalIndexForType(exceptionLabel.exceptionType);
                        }
                        byte[] bArr8 = this.contents;
                        int i18 = i12 + 7;
                        bArr8[i16] = (byte) (iLiteralIndexForType >> 8);
                        i12 += 8;
                        bArr8[i18] = (byte) iLiteralIndexForType;
                    }
                }
            }
            z = true;
            z2 = false;
            i2 = 2;
        }
        int i19 = i12 + 2;
        if (i12 + 4 >= this.contents.length) {
            resizeContents(i2);
        }
        this.contentsOffset = i19;
        int iGenerateLineNumberAttribute = (this.produceAttributes & i2) != 0 ? generateLineNumberAttribute() : z2;
        if ((this.produceAttributes & 4) != 0) {
            iGenerateLineNumberAttribute += generateLocalVariableTableAttribute(i4, z, z2);
        }
        int iGenerateTypeAnnotationsOnCodeAttribute = iGenerateLineNumberAttribute;
        if ((this.produceAttributes & 8) != 0) {
            iGenerateTypeAnnotationsOnCodeAttribute += generateStackMapTableAttribute(null, i4, i, i7, true);
        }
        if ((this.produceAttributes & 16) != 0) {
            iGenerateTypeAnnotationsOnCodeAttribute += generateStackMapAttribute(null, i4, i, i7, true);
        }
        if ((this.produceAttributes & 32) != 0) {
            iGenerateTypeAnnotationsOnCodeAttribute += generateTypeAnnotationsOnCodeAttribute();
        }
        if (i19 >= this.contents.length) {
            resizeContents(i2);
        }
        byte[] bArr9 = this.contents;
        bArr9[i12] = (byte) (iGenerateTypeAnnotationsOnCodeAttribute >> 8);
        bArr9[i12 + 1] = (byte) iGenerateTypeAnnotationsOnCodeAttribute;
        int i20 = this.contentsOffset - i6;
        bArr9[i + 2] = (byte) (i20 >> 24);
        bArr9[i + 3] = (byte) (i20 >> 16);
        bArr9[i + 4] = (byte) (i20 >> 8);
        bArr9[i + 5] = (byte) i20;
    }

    public void completeCodeAttributeForClinit(int i, int i2) {
        this.contents = this.codeStream.bCodeStream;
        int i3 = this.codeStream.classFileOffset;
        int i4 = this.codeStream.position;
        if (i4 > 65535) {
            this.codeStream.methodDeclaration.scope.problemReporter().bytecodeExceeds64KLimit(this.codeStream.methodDeclaration.scope.referenceType());
        }
        if (i3 + 20 >= this.contents.length) {
            resizeContents(20);
        }
        int i5 = this.codeStream.stackMax;
        byte[] bArr = this.contents;
        int i6 = i + 6;
        bArr[i6] = (byte) (i5 >> 8);
        bArr[i + 7] = (byte) i5;
        int i7 = this.codeStream.maxLocals;
        byte[] bArr2 = this.contents;
        bArr2[i + 8] = (byte) (i7 >> 8);
        bArr2[i + 9] = (byte) i7;
        bArr2[i + 10] = (byte) (i4 >> 24);
        bArr2[i + 11] = (byte) (i4 >> 16);
        bArr2[i + 12] = (byte) (i4 >> 8);
        bArr2[i + 13] = (byte) i4;
        bArr2[i3] = 0;
        int i8 = i3 + 2;
        bArr2[i3 + 1] = 0;
        int i9 = i3 + 4;
        if (i3 + 6 >= bArr2.length) {
            resizeContents(2);
        }
        this.contentsOffset = i9;
        int iGenerateLineNumberAttribute = (this.produceAttributes & 2) != 0 ? generateLineNumberAttribute(i2) : 0;
        int i10 = this.contentsOffset;
        if ((this.produceAttributes & 4) != 0) {
            int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.LocalVariableTableName);
            if (i10 + 8 >= this.contents.length) {
                resizeContents(8);
            }
            byte[] bArr3 = this.contents;
            bArr3[i10] = (byte) (iLiteralIndex >> 8);
            bArr3[i10 + 1] = (byte) iLiteralIndex;
            bArr3[i10 + 2] = 0;
            bArr3[i10 + 3] = 0;
            bArr3[i10 + 4] = 0;
            bArr3[i10 + 5] = 2;
            int i11 = i10 + 7;
            bArr3[i10 + 6] = 0;
            i10 += 8;
            bArr3[i11] = 0;
            iGenerateLineNumberAttribute++;
        }
        int iGenerateTypeAnnotationsOnCodeAttribute = iGenerateLineNumberAttribute;
        this.contentsOffset = i10;
        if ((this.produceAttributes & 8) != 0) {
            iGenerateTypeAnnotationsOnCodeAttribute += generateStackMapTableAttribute(null, i4, i, i7, true);
        }
        if ((this.produceAttributes & 16) != 0) {
            iGenerateTypeAnnotationsOnCodeAttribute += generateStackMapAttribute(null, i4, i, i7, true);
        }
        if ((this.produceAttributes & 32) != 0) {
            iGenerateTypeAnnotationsOnCodeAttribute += generateTypeAnnotationsOnCodeAttribute();
        }
        if (i9 >= this.contents.length) {
            resizeContents(2);
        }
        byte[] bArr4 = this.contents;
        bArr4[i8] = (byte) (iGenerateTypeAnnotationsOnCodeAttribute >> 8);
        bArr4[i3 + 3] = (byte) iGenerateTypeAnnotationsOnCodeAttribute;
        int i12 = this.contentsOffset - i6;
        bArr4[i + 2] = (byte) (i12 >> 24);
        bArr4[i + 3] = (byte) (i12 >> 16);
        bArr4[i + 4] = (byte) (i12 >> 8);
        bArr4[i + 5] = (byte) i12;
    }

    public void completeCodeAttributeForMissingAbstractProblemMethod(MethodBinding methodBinding, int i, int[] iArr, int i2) {
        this.contents = this.codeStream.bCodeStream;
        int i3 = this.codeStream.classFileOffset;
        int i4 = this.codeStream.stackMax;
        byte[] bArr = this.contents;
        int i5 = i + 6;
        bArr[i5] = (byte) (i4 >> 8);
        bArr[i + 7] = (byte) i4;
        int i6 = this.codeStream.maxLocals;
        byte[] bArr2 = this.contents;
        bArr2[i + 8] = (byte) (i6 >> 8);
        bArr2[i + 9] = (byte) i6;
        int i7 = this.codeStream.position;
        byte[] bArr3 = this.contents;
        bArr3[i + 10] = (byte) (i7 >> 24);
        bArr3[i + 11] = (byte) (i7 >> 16);
        bArr3[i + 12] = (byte) (i7 >> 8);
        bArr3[i + 13] = (byte) i7;
        if (i3 + 50 >= bArr3.length) {
            resizeContents(50);
        }
        byte[] bArr4 = this.contents;
        int iGenerateLineNumberAttribute = 0;
        bArr4[i3] = 0;
        int i8 = i3 + 2;
        bArr4[i3 + 1] = 0;
        int i9 = i3 + 4;
        if (i3 + 6 >= bArr4.length) {
            resizeContents(2);
        }
        this.contentsOffset = i9;
        if ((this.produceAttributes & 2) != 0) {
            iGenerateLineNumberAttribute = generateLineNumberAttribute(i2 == 0 ? Util.getLineNumber(methodBinding.sourceStart(), iArr, 0, iArr.length - 1) : i2);
        }
        int iGenerateStackMapAttribute = iGenerateLineNumberAttribute;
        if ((this.produceAttributes & 8) != 0) {
            iGenerateStackMapAttribute += generateStackMapTableAttribute(methodBinding, i7, i, i6, false);
        }
        if ((this.produceAttributes & 16) != 0) {
            iGenerateStackMapAttribute += generateStackMapAttribute(methodBinding, i7, i, i6, false);
        }
        if (i9 >= this.contents.length) {
            resizeContents(2);
        }
        byte[] bArr5 = this.contents;
        bArr5[i8] = (byte) (iGenerateStackMapAttribute >> 8);
        bArr5[i3 + 3] = (byte) iGenerateStackMapAttribute;
        int i10 = this.contentsOffset - i5;
        bArr5[i + 2] = (byte) (i10 >> 24);
        bArr5[i + 3] = (byte) (i10 >> 16);
        bArr5[i + 4] = (byte) (i10 >> 8);
        bArr5[i + 5] = (byte) i10;
    }

    public void completeCodeAttributeForProblemMethod(AbstractMethodDeclaration abstractMethodDeclaration, MethodBinding methodBinding, int i, int[] iArr, int i2) {
        int iGenerateLocalVariableTableAttribute;
        this.contents = this.codeStream.bCodeStream;
        int i3 = this.codeStream.classFileOffset;
        int i4 = this.codeStream.stackMax;
        byte[] bArr = this.contents;
        int i5 = i + 6;
        bArr[i5] = (byte) (i4 >> 8);
        bArr[i + 7] = (byte) i4;
        int i6 = this.codeStream.maxLocals;
        byte[] bArr2 = this.contents;
        bArr2[i + 8] = (byte) (i6 >> 8);
        bArr2[i + 9] = (byte) i6;
        int i7 = this.codeStream.position;
        byte[] bArr3 = this.contents;
        bArr3[i + 10] = (byte) (i7 >> 24);
        bArr3[i + 11] = (byte) (i7 >> 16);
        bArr3[i + 12] = (byte) (i7 >> 8);
        bArr3[i + 13] = (byte) i7;
        if (i3 + 50 >= bArr3.length) {
            resizeContents(50);
        }
        byte[] bArr4 = this.contents;
        bArr4[i3] = 0;
        int i8 = i3 + 2;
        bArr4[i3 + 1] = 0;
        int i9 = i3 + 4;
        if (i3 + 6 >= bArr4.length) {
            resizeContents(2);
        }
        this.contentsOffset = i9;
        if ((this.produceAttributes & 2) != 0) {
            iGenerateLocalVariableTableAttribute = generateLineNumberAttribute(i2 == 0 ? Util.getLineNumber(methodBinding.sourceStart(), iArr, 0, iArr.length - 1) : i2);
        } else {
            iGenerateLocalVariableTableAttribute = 0;
        }
        if ((this.produceAttributes & 4) != 0) {
            iGenerateLocalVariableTableAttribute += generateLocalVariableTableAttribute(i7, this.codeStream.methodDeclaration.isStatic(), false);
        }
        int iGenerateStackMapAttribute = iGenerateLocalVariableTableAttribute;
        if ((this.produceAttributes & 8) != 0) {
            iGenerateStackMapAttribute += generateStackMapTableAttribute(methodBinding, i7, i, i6, false);
        }
        if ((this.produceAttributes & 16) != 0) {
            iGenerateStackMapAttribute += generateStackMapAttribute(methodBinding, i7, i, i6, false);
        }
        if (i9 >= this.contents.length) {
            resizeContents(2);
        }
        byte[] bArr5 = this.contents;
        bArr5[i8] = (byte) (iGenerateStackMapAttribute >> 8);
        bArr5[i3 + 3] = (byte) iGenerateStackMapAttribute;
        int i10 = this.contentsOffset - i5;
        bArr5[i + 2] = (byte) (i10 >> 24);
        bArr5[i + 3] = (byte) (i10 >> 16);
        bArr5[i + 4] = (byte) (i10 >> 8);
        bArr5[i + 5] = (byte) i10;
    }

    public void completeCodeAttributeForSyntheticMethod(boolean z, SyntheticMethodBinding syntheticMethodBinding, int i, int[] iArr) {
        int i2;
        int i3;
        boolean z2;
        int iGenerateLocalVariableTableAttribute;
        int iLiteralIndexForType;
        this.contents = this.codeStream.bCodeStream;
        int i4 = this.codeStream.classFileOffset;
        int i5 = this.codeStream.stackMax;
        byte[] bArr = this.contents;
        int i6 = i + 6;
        bArr[i6] = (byte) (i5 >> 8);
        bArr[i + 7] = (byte) i5;
        int i7 = this.codeStream.maxLocals;
        byte[] bArr2 = this.contents;
        bArr2[i + 8] = (byte) (i7 >> 8);
        bArr2[i + 9] = (byte) i7;
        int i8 = this.codeStream.position;
        byte[] bArr3 = this.contents;
        bArr3[i + 10] = (byte) (i8 >> 24);
        bArr3[i + 11] = (byte) (i8 >> 16);
        bArr3[i + 12] = (byte) (i8 >> 8);
        bArr3[i + 13] = (byte) i8;
        if (i4 + 40 >= bArr3.length) {
            resizeContents(40);
        }
        boolean z3 = (this.produceAttributes & 8) != 0;
        int i9 = 2;
        if (z) {
            ExceptionLabel[] exceptionLabelArr = this.codeStream.exceptionLabels;
            int i10 = this.codeStream.exceptionLabelsCounter;
            int i11 = 0;
            int count = 0;
            while (i11 < i10) {
                count += this.codeStream.exceptionLabels[i11].getCount() / 2;
                i11++;
                i9 = 2;
            }
            int i12 = (count * 8) + i9;
            if (i12 + i4 >= this.contents.length) {
                resizeContents(i12);
            }
            byte[] bArr4 = this.contents;
            int i13 = i4 + 1;
            bArr4[i4] = (byte) (count >> 8);
            i3 = i4 + i9;
            bArr4[i13] = (byte) count;
            int i14 = this.codeStream.exceptionLabelsCounter;
            int i15 = 0;
            while (i15 < i14) {
                ExceptionLabel exceptionLabel = exceptionLabelArr[i15];
                if (exceptionLabel != null) {
                    int count2 = exceptionLabel.getCount();
                    if ((count2 & 1) != 0) {
                        this.referenceBinding.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidExceptionAttribute, new String(syntheticMethodBinding.selector), this.referenceBinding.scope.problemReporter().referenceContext));
                    }
                    i3 = i3;
                    int i16 = 0;
                    while (i16 < count2) {
                        int i17 = i16 + 1;
                        int i18 = exceptionLabel.ranges[i16];
                        ExceptionLabel[] exceptionLabelArr2 = exceptionLabelArr;
                        byte[] bArr5 = this.contents;
                        int i19 = count2;
                        bArr5[i3] = (byte) (i18 >> 8);
                        bArr5[i3 + 1] = (byte) i18;
                        int i20 = i16 + 2;
                        int i21 = exceptionLabel.ranges[i17];
                        byte[] bArr6 = this.contents;
                        bArr6[i3 + 2] = (byte) (i21 >> 8);
                        int i22 = i3 + 4;
                        bArr6[i3 + 3] = (byte) i21;
                        int i23 = exceptionLabel.position;
                        if (z3) {
                            ((StackMapFrameCodeStream) this.codeStream).addFramePosition(i23);
                        }
                        byte[] bArr7 = this.contents;
                        bArr7[i22] = (byte) (i23 >> 8);
                        int i24 = i3 + 6;
                        bArr7[i3 + 5] = (byte) i23;
                        if (exceptionLabel.exceptionType == null) {
                            byte[] bArr8 = this.contents;
                            int i25 = i3 + 7;
                            bArr8[i24] = 0;
                            i3 += 8;
                            bArr8[i25] = 0;
                        } else {
                            int i26 = exceptionLabel.exceptionType.f626id;
                            if (i26 == 7) {
                                iLiteralIndexForType = this.constantPool.literalIndexForType(ConstantPool.JavaLangNoSuchFieldErrorConstantPoolName);
                            } else if (i26 == 12) {
                                iLiteralIndexForType = this.constantPool.literalIndexForType(ConstantPool.JavaLangClassNotFoundExceptionConstantPoolName);
                            } else {
                                iLiteralIndexForType = this.constantPool.literalIndexForType(exceptionLabel.exceptionType);
                            }
                            byte[] bArr9 = this.contents;
                            int i27 = i3 + 7;
                            bArr9[i24] = (byte) (iLiteralIndexForType >> 8);
                            i3 += 8;
                            bArr9[i27] = (byte) iLiteralIndexForType;
                        }
                        i16 = i20;
                        exceptionLabelArr = exceptionLabelArr2;
                        count2 = i19;
                    }
                }
                i15++;
                exceptionLabelArr = exceptionLabelArr;
                i9 = 2;
            }
            i2 = i9;
        } else {
            i2 = 2;
            byte[] bArr10 = this.contents;
            int i28 = i4 + 1;
            bArr10[i4] = 0;
            i3 = i4 + 2;
            bArr10[i28] = 0;
        }
        int i29 = i3;
        int i30 = i29 + 2;
        if (i29 + 4 >= this.contents.length) {
            resizeContents(i2);
        }
        this.contentsOffset = i30;
        if ((this.produceAttributes & i2) != 0) {
            z2 = true;
            iGenerateLocalVariableTableAttribute = generateLineNumberAttribute(Util.getLineNumber(syntheticMethodBinding.sourceStart, iArr, 0, iArr.length - 1));
        } else {
            z2 = true;
            iGenerateLocalVariableTableAttribute = 0;
        }
        if ((this.produceAttributes & 4) != 0) {
            iGenerateLocalVariableTableAttribute += generateLocalVariableTableAttribute(i8, syntheticMethodBinding.isStatic(), z2);
        }
        int iGenerateStackMapAttribute = iGenerateLocalVariableTableAttribute;
        if (z3) {
            iGenerateStackMapAttribute += generateStackMapTableAttribute(syntheticMethodBinding, i8, i, i7, false);
        }
        if ((this.produceAttributes & 16) != 0) {
            iGenerateStackMapAttribute += generateStackMapAttribute(syntheticMethodBinding, i8, i, i7, false);
        }
        if (i30 >= this.contents.length) {
            resizeContents(2);
        }
        byte[] bArr11 = this.contents;
        bArr11[i29] = (byte) (iGenerateStackMapAttribute >> 8);
        bArr11[i29 + 1] = (byte) iGenerateStackMapAttribute;
        int i31 = this.contentsOffset - i6;
        bArr11[i + 2] = (byte) (i31 >> 24);
        bArr11[i + 3] = (byte) (i31 >> 16);
        bArr11[i + 4] = (byte) (i31 >> 8);
        bArr11[i + 5] = (byte) i31;
    }

    public void completeCodeAttributeForSyntheticMethod(SyntheticMethodBinding syntheticMethodBinding, int i, int[] iArr) {
        completeCodeAttributeForSyntheticMethod(false, syntheticMethodBinding, i, iArr);
    }

    private void completeArgumentAnnotationInfo(Argument[] argumentArr, List list) {
        int length = argumentArr.length;
        for (int i = 0; i < length; i++) {
            Argument argument = argumentArr[i];
            if ((argument.bits & 1048576) != 0) {
                argument.getAllAnnotationContexts(22, i, list);
            }
        }
    }

    public void completeMethodInfo(MethodBinding methodBinding, int i, int i2) {
        if ((this.produceAttributes & 32) != 0) {
            ArrayList arrayList = new ArrayList();
            AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = methodBinding.sourceMethod();
            if (abstractMethodDeclarationSourceMethod != null) {
                if ((abstractMethodDeclarationSourceMethod.bits & 1048576) != 0) {
                    Argument[] argumentArr = abstractMethodDeclarationSourceMethod.arguments;
                    if (argumentArr != null) {
                        completeArgumentAnnotationInfo(argumentArr, arrayList);
                    }
                    Receiver receiver = abstractMethodDeclarationSourceMethod.receiver;
                    if (receiver != null && (receiver.type.bits & 1048576) != 0) {
                        receiver.type.getAllAnnotationContexts(21, arrayList);
                    }
                }
                if (abstractMethodDeclarationSourceMethod.annotations != null && !abstractMethodDeclarationSourceMethod.isClinit() && (abstractMethodDeclarationSourceMethod.isConstructor() || methodBinding.returnType.f626id != 6)) {
                    abstractMethodDeclarationSourceMethod.getAllAnnotationContexts(20, arrayList);
                }
                if (!abstractMethodDeclarationSourceMethod.isConstructor() && !abstractMethodDeclarationSourceMethod.isClinit() && methodBinding.returnType.f626id != 6) {
                    TypeReference typeReference = ((MethodDeclaration) abstractMethodDeclarationSourceMethod).returnType;
                    if ((typeReference.bits & 1048576) != 0) {
                        typeReference.getAllAnnotationContexts(20, arrayList);
                    }
                }
                TypeReference[] typeReferenceArr = abstractMethodDeclarationSourceMethod.thrownExceptions;
                if (typeReferenceArr != null) {
                    int length = typeReferenceArr.length;
                    for (int i3 = 0; i3 < length; i3++) {
                        typeReferenceArr[i3].getAllAnnotationContexts(23, i3, arrayList);
                    }
                }
                TypeParameter[] typeParameterArrTypeParameters = abstractMethodDeclarationSourceMethod.typeParameters();
                if (typeParameterArrTypeParameters != null) {
                    int length2 = typeParameterArrTypeParameters.length;
                    for (int i4 = 0; i4 < length2; i4++) {
                        TypeParameter typeParameter = typeParameterArrTypeParameters[i4];
                        if ((typeParameter.bits & 1048576) != 0) {
                            typeParameter.getAllAnnotationContexts(1, i4, arrayList);
                        }
                    }
                }
            } else if (methodBinding.sourceLambda() != null) {
                LambdaExpression lambdaExpressionSourceLambda = methodBinding.sourceLambda();
                if ((1048576 & lambdaExpressionSourceLambda.bits) != 0 && lambdaExpressionSourceLambda.arguments != null) {
                    completeArgumentAnnotationInfo(lambdaExpressionSourceLambda.arguments, arrayList);
                }
            }
            int size = arrayList.size();
            if (size != 0) {
                AnnotationContext[] annotationContextArr = new AnnotationContext[size];
                arrayList.toArray(annotationContextArr);
                int i5 = 0;
                int i6 = 0;
                for (int i7 = 0; i7 < size; i7++) {
                    if ((annotationContextArr[i7].visibility & 2) != 0) {
                        i6++;
                    } else {
                        i5++;
                    }
                }
                i2 += generateRuntimeTypeAnnotations(annotationContextArr, i5, i6);
            }
        }
        if ((this.produceAttributes & 64) != 0) {
            i2 += generateMethodParameters(methodBinding);
        }
        byte[] bArr = this.contents;
        bArr[i] = (byte) (i2 >> 8);
        bArr[i + 1] = (byte) i2;
    }

    private void dumpLocations(int[] iArr) {
        if (iArr == null) {
            if (this.contentsOffset + 1 >= this.contents.length) {
                resizeContents(1);
            }
            byte[] bArr = this.contents;
            int i = this.contentsOffset;
            this.contentsOffset = i + 1;
            bArr[i] = 0;
            return;
        }
        int length = iArr.length;
        if (this.contentsOffset + length >= this.contents.length) {
            resizeContents(length + 1);
        }
        byte[] bArr2 = this.contents;
        int i2 = this.contentsOffset;
        this.contentsOffset = i2 + 1;
        bArr2[i2] = (byte) (iArr.length / 2);
        for (int i3 : iArr) {
            byte[] bArr3 = this.contents;
            int i4 = this.contentsOffset;
            this.contentsOffset = i4 + 1;
            bArr3[i4] = (byte) i3;
        }
    }

    private void dumpTargetTypeContents(int i, AnnotationContext annotationContext) {
        if (i == 0 || i == 1) {
            byte[] bArr = this.contents;
            int i2 = this.contentsOffset;
            this.contentsOffset = i2 + 1;
            bArr[i2] = (byte) annotationContext.info;
            return;
        }
        if (i == 22) {
            byte[] bArr2 = this.contents;
            int i3 = this.contentsOffset;
            this.contentsOffset = i3 + 1;
            bArr2[i3] = (byte) annotationContext.info;
            return;
        }
        if (i != 23) {
            switch (i) {
                case 16:
                    break;
                case 17:
                    byte[] bArr3 = this.contents;
                    int i4 = this.contentsOffset;
                    this.contentsOffset = i4 + 1;
                    bArr3[i4] = (byte) annotationContext.info;
                    byte[] bArr4 = this.contents;
                    int i5 = this.contentsOffset;
                    this.contentsOffset = i5 + 1;
                    bArr4[i5] = (byte) annotationContext.info2;
                    break;
                case 18:
                    byte[] bArr5 = this.contents;
                    int i6 = this.contentsOffset;
                    this.contentsOffset = i6 + 1;
                    bArr5[i6] = (byte) annotationContext.info;
                    byte[] bArr6 = this.contents;
                    int i7 = this.contentsOffset;
                    this.contentsOffset = i7 + 1;
                    bArr6[i7] = (byte) annotationContext.info2;
                    break;
                default:
                    switch (i) {
                        case 64:
                        case 65:
                            int i8 = this.contentsOffset;
                            LocalVariableBinding localVariableBinding = annotationContext.variableBinding;
                            int i9 = localVariableBinding.initializationCount;
                            int i10 = (i9 * 6) + 2;
                            if (this.contentsOffset + i10 >= this.contents.length) {
                                resizeContents(i10);
                            }
                            this.contentsOffset += 2;
                            int i11 = 0;
                            for (int i12 = 0; i12 < i9; i12++) {
                                int i13 = i12 << 1;
                                int i14 = localVariableBinding.initializationPCs[i13];
                                int i15 = localVariableBinding.initializationPCs[i13 + 1];
                                if (i14 != i15) {
                                    i11++;
                                    byte[] bArr7 = this.contents;
                                    int i16 = this.contentsOffset;
                                    int i17 = i16 + 1;
                                    this.contentsOffset = i17;
                                    bArr7[i16] = (byte) (i14 >> 8);
                                    int i18 = i16 + 2;
                                    this.contentsOffset = i18;
                                    bArr7[i17] = (byte) i14;
                                    int i19 = i15 - i14;
                                    int i20 = i16 + 3;
                                    this.contentsOffset = i20;
                                    bArr7[i18] = (byte) (i19 >> 8);
                                    this.contentsOffset = i16 + 4;
                                    bArr7[i20] = (byte) i19;
                                    int i21 = localVariableBinding.resolvedPosition;
                                    byte[] bArr8 = this.contents;
                                    int i22 = this.contentsOffset;
                                    int i23 = i22 + 1;
                                    this.contentsOffset = i23;
                                    bArr8[i22] = (byte) (i21 >> 8);
                                    this.contentsOffset = i22 + 2;
                                    bArr8[i23] = (byte) i21;
                                }
                            }
                            byte[] bArr9 = this.contents;
                            bArr9[i8] = (byte) (i11 >> 8);
                            bArr9[i8 + 1] = (byte) i11;
                            break;
                        case 66:
                        case 67:
                        case 68:
                        case 69:
                        case 70:
                            byte[] bArr10 = this.contents;
                            int i24 = this.contentsOffset;
                            this.contentsOffset = i24 + 1;
                            bArr10[i24] = (byte) (annotationContext.info >> 8);
                            byte[] bArr11 = this.contents;
                            int i25 = this.contentsOffset;
                            this.contentsOffset = i25 + 1;
                            bArr11[i25] = (byte) annotationContext.info;
                            break;
                        case 71:
                            byte[] bArr12 = this.contents;
                            int i26 = this.contentsOffset;
                            this.contentsOffset = i26 + 1;
                            bArr12[i26] = (byte) (annotationContext.info >> 8);
                            byte[] bArr13 = this.contents;
                            int i27 = this.contentsOffset;
                            this.contentsOffset = i27 + 1;
                            bArr13[i27] = (byte) annotationContext.info;
                            byte[] bArr14 = this.contents;
                            int i28 = this.contentsOffset;
                            this.contentsOffset = i28 + 1;
                            bArr14[i28] = (byte) annotationContext.info2;
                            break;
                        case 72:
                        case 73:
                        case 74:
                        case 75:
                            byte[] bArr15 = this.contents;
                            int i29 = this.contentsOffset;
                            this.contentsOffset = i29 + 1;
                            bArr15[i29] = (byte) (annotationContext.info >> 8);
                            byte[] bArr16 = this.contents;
                            int i30 = this.contentsOffset;
                            this.contentsOffset = i30 + 1;
                            bArr16[i30] = (byte) annotationContext.info;
                            byte[] bArr17 = this.contents;
                            int i31 = this.contentsOffset;
                            this.contentsOffset = i31 + 1;
                            bArr17[i31] = (byte) annotationContext.info2;
                            break;
                    }
                    break;
            }
            return;
        }
        byte[] bArr18 = this.contents;
        int i32 = this.contentsOffset;
        this.contentsOffset = i32 + 1;
        bArr18[i32] = (byte) (annotationContext.info >> 8);
        byte[] bArr19 = this.contents;
        int i33 = this.contentsOffset;
        this.contentsOffset = i33 + 1;
        bArr19[i33] = (byte) annotationContext.info;
    }

    public char[] fileName() {
        return this.constantPool.UTF8Cache.returnKeyFor(2);
    }

    private void generateAnnotation(Annotation annotation, int i) {
        if (this.contentsOffset + 4 >= this.contents.length) {
            resizeContents(4);
        }
        TypeBinding typeBinding = annotation.resolvedType;
        if (typeBinding == null) {
            this.contentsOffset = i;
            return;
        }
        if (typeBinding.isMemberType()) {
            recordInnerClasses(typeBinding);
        }
        int iLiteralIndex = this.constantPool.literalIndex(typeBinding.signature());
        byte[] bArr = this.contents;
        int i2 = this.contentsOffset;
        int i3 = i2 + 1;
        this.contentsOffset = i3;
        bArr[i2] = (byte) (iLiteralIndex >> 8);
        int i4 = i2 + 2;
        this.contentsOffset = i4;
        bArr[i3] = (byte) iLiteralIndex;
        if (annotation instanceof NormalAnnotation) {
            MemberValuePair[] memberValuePairArr = ((NormalAnnotation) annotation).memberValuePairs;
            int i5 = this.contentsOffset;
            if (memberValuePairArr != null) {
                int i6 = i5 + 2;
                this.contentsOffset = i6;
                int i7 = 0;
                for (MemberValuePair memberValuePair : memberValuePairArr) {
                    if (this.contentsOffset + 2 >= this.contents.length) {
                        resizeContents(2);
                    }
                    int iLiteralIndex2 = this.constantPool.literalIndex(memberValuePair.f618name);
                    byte[] bArr2 = this.contents;
                    int i8 = this.contentsOffset;
                    int i9 = i8 + 1;
                    this.contentsOffset = i9;
                    bArr2[i8] = (byte) (iLiteralIndex2 >> 8);
                    this.contentsOffset = i8 + 2;
                    bArr2[i9] = (byte) iLiteralIndex2;
                    MethodBinding methodBinding = memberValuePair.binding;
                    if (methodBinding == null) {
                        this.contentsOffset = i6;
                    } else {
                        try {
                            generateElementValue(memberValuePair.value, methodBinding.returnType, i5);
                            int i10 = this.contentsOffset;
                            if (i10 == i5) {
                                byte[] bArr3 = this.contents;
                                int i11 = i10 + 1;
                                this.contentsOffset = i11;
                                bArr3[i10] = 0;
                                this.contentsOffset = i10 + 2;
                                bArr3[i11] = 0;
                                break;
                            }
                            i7++;
                            i6 = i10;
                        } catch (ClassCastException unused) {
                            this.contentsOffset = i6;
                        } catch (ShouldNotImplement unused2) {
                            this.contentsOffset = i6;
                        }
                    }
                }
                byte[] bArr4 = this.contents;
                bArr4[i5] = (byte) (i7 >> 8);
                bArr4[i5 + 1] = (byte) i7;
                return;
            }
            byte[] bArr5 = this.contents;
            int i12 = i5 + 1;
            this.contentsOffset = i12;
            bArr5[i5] = 0;
            this.contentsOffset = i5 + 2;
            bArr5[i12] = 0;
            return;
        }
        if (annotation instanceof SingleMemberAnnotation) {
            SingleMemberAnnotation singleMemberAnnotation = (SingleMemberAnnotation) annotation;
            int i13 = i2 + 3;
            this.contentsOffset = i13;
            bArr[i4] = 0;
            this.contentsOffset = i2 + 4;
            bArr[i13] = 1;
            if (i2 + 6 >= bArr.length) {
                resizeContents(2);
            }
            int iLiteralIndex3 = this.constantPool.literalIndex(VALUE);
            byte[] bArr6 = this.contents;
            int i14 = this.contentsOffset;
            int i15 = i14 + 1;
            this.contentsOffset = i15;
            bArr6[i14] = (byte) (iLiteralIndex3 >> 8);
            this.contentsOffset = i14 + 2;
            bArr6[i15] = (byte) iLiteralIndex3;
            MethodBinding methodBinding2 = singleMemberAnnotation.memberValuePairs()[0].binding;
            if (methodBinding2 == null) {
                this.contentsOffset = i;
                return;
            }
            int i16 = this.contentsOffset;
            try {
                generateElementValue(singleMemberAnnotation.memberValue, methodBinding2.returnType, i16);
                if (this.contentsOffset == i16) {
                    this.contentsOffset = i;
                    return;
                }
                return;
            } catch (ClassCastException unused3) {
                this.contentsOffset = i;
                return;
            } catch (ShouldNotImplement unused4) {
                this.contentsOffset = i;
                return;
            }
        }
        int i17 = i2 + 3;
        this.contentsOffset = i17;
        bArr[i4] = 0;
        this.contentsOffset = i2 + 4;
        bArr[i17] = 0;
    }

    private int generateAnnotationDefaultAttribute(AnnotationMethodDeclaration annotationMethodDeclaration, int i) {
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.AnnotationDefaultName);
        if (this.contentsOffset + 6 >= this.contents.length) {
            resizeContents(6);
        }
        byte[] bArr = this.contents;
        int i2 = this.contentsOffset;
        int i3 = i2 + 1;
        this.contentsOffset = i3;
        bArr[i2] = (byte) (iLiteralIndex >> 8);
        int i4 = i2 + 2;
        this.contentsOffset = i4;
        bArr[i3] = (byte) iLiteralIndex;
        this.contentsOffset = i2 + 6;
        generateElementValue(annotationMethodDeclaration.defaultValue, annotationMethodDeclaration.binding.returnType, i);
        int i5 = this.contentsOffset;
        if (i5 == i) {
            return 0;
        }
        int i6 = (i5 - i4) - 4;
        byte[] bArr2 = this.contents;
        bArr2[i4] = (byte) (i6 >> 24);
        bArr2[i2 + 3] = (byte) (i6 >> 16);
        bArr2[i2 + 4] = (byte) (i6 >> 8);
        bArr2[i2 + 5] = (byte) i6;
        return 1;
    }

    public void generateCodeAttributeHeader() {
        if (this.contentsOffset + 20 >= this.contents.length) {
            resizeContents(20);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.CodeName);
        byte[] bArr = this.contents;
        int i = this.contentsOffset;
        int i2 = i + 1;
        this.contentsOffset = i2;
        bArr[i] = (byte) (iLiteralIndex >> 8);
        this.contentsOffset = i + 2;
        bArr[i2] = (byte) iLiteralIndex;
        this.contentsOffset = i + 14;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private int generateConstantValueAttribute(Constant constant, FieldBinding fieldBinding, int i) {
        int i2 = this.contentsOffset;
        if (i2 + 8 >= this.contents.length) {
            resizeContents(8);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.ConstantValueName);
        byte[] bArr = this.contents;
        bArr[i2] = (byte) (iLiteralIndex >> 8);
        bArr[i2 + 1] = (byte) iLiteralIndex;
        int i3 = 0;
        bArr[i2 + 2] = 0;
        bArr[i2 + 3] = 0;
        bArr[i2 + 4] = 0;
        int i4 = i2 + 6;
        bArr[i2 + 5] = 2;
        switch (constant.typeID()) {
            case 2:
            case 3:
            case 4:
            case 10:
                int iLiteralIndex2 = this.constantPool.literalIndex(constant.intValue());
                byte[] bArr2 = this.contents;
                bArr2[i4] = (byte) (iLiteralIndex2 >> 8);
                i4 = i2 + 8;
                bArr2[i2 + 7] = (byte) iLiteralIndex2;
                i3 = 1;
                break;
            case 5:
                int iLiteralIndex3 = this.constantPool.literalIndex(constant.booleanValue() ? 1 : 0);
                byte[] bArr3 = this.contents;
                bArr3[i4] = (byte) (iLiteralIndex3 >> 8);
                i4 = i2 + 8;
                bArr3[i2 + 7] = (byte) iLiteralIndex3;
                i3 = 1;
                break;
            case 6:
            default:
                i3 = 1;
                break;
            case 7:
                int iLiteralIndex4 = this.constantPool.literalIndex(constant.longValue());
                byte[] bArr4 = this.contents;
                bArr4[i4] = (byte) (iLiteralIndex4 >> 8);
                i4 = i2 + 8;
                bArr4[i2 + 7] = (byte) iLiteralIndex4;
                i3 = 1;
                break;
            case 8:
                int iLiteralIndex5 = this.constantPool.literalIndex(constant.doubleValue());
                byte[] bArr5 = this.contents;
                bArr5[i4] = (byte) (iLiteralIndex5 >> 8);
                i4 = i2 + 8;
                bArr5[i2 + 7] = (byte) iLiteralIndex5;
                i3 = 1;
                break;
            case 9:
                int iLiteralIndex6 = this.constantPool.literalIndex(constant.floatValue());
                byte[] bArr6 = this.contents;
                bArr6[i4] = (byte) (iLiteralIndex6 >> 8);
                i4 = i2 + 8;
                bArr6[i2 + 7] = (byte) iLiteralIndex6;
                i3 = 1;
                break;
            case 11:
                int iLiteralIndex7 = this.constantPool.literalIndex(((StringConstant) constant).stringValue());
                if (iLiteralIndex7 == -1) {
                    if (!this.creatingProblemType) {
                        TypeDeclaration typeDeclaration = this.referenceBinding.scope.referenceContext;
                        FieldDeclaration[] fieldDeclarationArr = typeDeclaration.fields;
                        int length = fieldDeclarationArr == null ? 0 : fieldDeclarationArr.length;
                        while (i3 < length) {
                            if (fieldDeclarationArr[i3].binding == fieldBinding) {
                                typeDeclaration.scope.problemReporter().stringConstantIsExceedingUtf8Limit(fieldDeclarationArr[i3]);
                            }
                            i3++;
                        }
                    } else {
                        this.contentsOffset = i;
                    }
                } else {
                    byte[] bArr7 = this.contents;
                    bArr7[i4] = (byte) (iLiteralIndex7 >> 8);
                    i4 = i2 + 8;
                    bArr7[i2 + 7] = (byte) iLiteralIndex7;
                }
                i3 = 1;
                break;
        }
        this.contentsOffset = i4;
        return i3;
    }

    private int generateDeprecatedAttribute() {
        int i = this.contentsOffset;
        if (i + 6 >= this.contents.length) {
            resizeContents(6);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.DeprecatedName);
        byte[] bArr = this.contents;
        bArr[i] = (byte) (iLiteralIndex >> 8);
        bArr[i + 1] = (byte) iLiteralIndex;
        bArr[i + 2] = 0;
        bArr[i + 3] = 0;
        bArr[i + 4] = 0;
        bArr[i + 5] = 0;
        this.contentsOffset = i + 6;
        return 1;
    }

    private void generateElementValue(Expression expression, TypeBinding typeBinding, int i) {
        Constant constant = expression.constant;
        TypeBinding typeBinding2 = expression.resolvedType;
        if (typeBinding2 == null) {
            this.contentsOffset = i;
            return;
        }
        if (typeBinding2.isMemberType()) {
            recordInnerClasses(typeBinding2);
        }
        if (typeBinding.isMemberType()) {
            recordInnerClasses(typeBinding);
        }
        if (typeBinding.isArrayType() && !typeBinding2.isArrayType()) {
            if (this.contentsOffset + 3 >= this.contents.length) {
                resizeContents(3);
            }
            byte[] bArr = this.contents;
            int i2 = this.contentsOffset;
            int i3 = i2 + 1;
            this.contentsOffset = i3;
            bArr[i2] = 91;
            int i4 = i2 + 2;
            this.contentsOffset = i4;
            bArr[i3] = 0;
            this.contentsOffset = i2 + 3;
            bArr[i4] = 1;
        }
        if (constant != null && constant != Constant.NotAConstant) {
            generateElementValue(i, expression, constant, typeBinding.leafComponentType());
        } else {
            generateElementValueForNonConstantExpression(expression, i, typeBinding2);
        }
    }

    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private void generateElementValue(int i, Expression expression, Constant constant, TypeBinding typeBinding) {
        if (this.contentsOffset + 3 >= this.contents.length) {
            resizeContents(3);
        }
        switch (typeBinding.f626id) {
            case 2:
                byte[] bArr = this.contents;
                int i2 = this.contentsOffset;
                this.contentsOffset = i2 + 1;
                bArr[i2] = 67;
                int iLiteralIndex = this.constantPool.literalIndex(constant.intValue());
                byte[] bArr2 = this.contents;
                int i3 = this.contentsOffset;
                int i4 = i3 + 1;
                this.contentsOffset = i4;
                bArr2[i3] = (byte) (iLiteralIndex >> 8);
                this.contentsOffset = i3 + 2;
                bArr2[i4] = (byte) iLiteralIndex;
                break;
            case 3:
                byte[] bArr3 = this.contents;
                int i5 = this.contentsOffset;
                this.contentsOffset = i5 + 1;
                bArr3[i5] = 66;
                int iLiteralIndex2 = this.constantPool.literalIndex(constant.intValue());
                byte[] bArr4 = this.contents;
                int i6 = this.contentsOffset;
                int i7 = i6 + 1;
                this.contentsOffset = i7;
                bArr4[i6] = (byte) (iLiteralIndex2 >> 8);
                this.contentsOffset = i6 + 2;
                bArr4[i7] = (byte) iLiteralIndex2;
                break;
            case 4:
                byte[] bArr5 = this.contents;
                int i8 = this.contentsOffset;
                this.contentsOffset = i8 + 1;
                bArr5[i8] = 83;
                int iLiteralIndex3 = this.constantPool.literalIndex(constant.intValue());
                byte[] bArr6 = this.contents;
                int i9 = this.contentsOffset;
                int i10 = i9 + 1;
                this.contentsOffset = i10;
                bArr6[i9] = (byte) (iLiteralIndex3 >> 8);
                this.contentsOffset = i9 + 2;
                bArr6[i10] = (byte) iLiteralIndex3;
                break;
            case 5:
                byte[] bArr7 = this.contents;
                int i11 = this.contentsOffset;
                this.contentsOffset = i11 + 1;
                bArr7[i11] = 90;
                int iLiteralIndex4 = this.constantPool.literalIndex(constant.booleanValue() ? 1 : 0);
                byte[] bArr8 = this.contents;
                int i12 = this.contentsOffset;
                int i13 = i12 + 1;
                this.contentsOffset = i13;
                bArr8[i12] = (byte) (iLiteralIndex4 >> 8);
                this.contentsOffset = i12 + 2;
                bArr8[i13] = (byte) iLiteralIndex4;
                break;
            case 7:
                byte[] bArr9 = this.contents;
                int i14 = this.contentsOffset;
                this.contentsOffset = i14 + 1;
                bArr9[i14] = 74;
                int iLiteralIndex5 = this.constantPool.literalIndex(constant.longValue());
                byte[] bArr10 = this.contents;
                int i15 = this.contentsOffset;
                int i16 = i15 + 1;
                this.contentsOffset = i16;
                bArr10[i15] = (byte) (iLiteralIndex5 >> 8);
                this.contentsOffset = i15 + 2;
                bArr10[i16] = (byte) iLiteralIndex5;
                break;
            case 8:
                byte[] bArr11 = this.contents;
                int i17 = this.contentsOffset;
                this.contentsOffset = i17 + 1;
                bArr11[i17] = 68;
                int iLiteralIndex6 = this.constantPool.literalIndex(constant.doubleValue());
                byte[] bArr12 = this.contents;
                int i18 = this.contentsOffset;
                int i19 = i18 + 1;
                this.contentsOffset = i19;
                bArr12[i18] = (byte) (iLiteralIndex6 >> 8);
                this.contentsOffset = i18 + 2;
                bArr12[i19] = (byte) iLiteralIndex6;
                break;
            case 9:
                byte[] bArr13 = this.contents;
                int i20 = this.contentsOffset;
                this.contentsOffset = i20 + 1;
                bArr13[i20] = 70;
                int iLiteralIndex7 = this.constantPool.literalIndex(constant.floatValue());
                byte[] bArr14 = this.contents;
                int i21 = this.contentsOffset;
                int i22 = i21 + 1;
                this.contentsOffset = i22;
                bArr14[i21] = (byte) (iLiteralIndex7 >> 8);
                this.contentsOffset = i21 + 2;
                bArr14[i22] = (byte) iLiteralIndex7;
                break;
            case 10:
                byte[] bArr15 = this.contents;
                int i23 = this.contentsOffset;
                this.contentsOffset = i23 + 1;
                bArr15[i23] = 73;
                int iLiteralIndex8 = this.constantPool.literalIndex(constant.intValue());
                byte[] bArr16 = this.contents;
                int i24 = this.contentsOffset;
                int i25 = i24 + 1;
                this.contentsOffset = i25;
                bArr16[i24] = (byte) (iLiteralIndex8 >> 8);
                this.contentsOffset = i24 + 2;
                bArr16[i25] = (byte) iLiteralIndex8;
                break;
            case 11:
                byte[] bArr17 = this.contents;
                int i26 = this.contentsOffset;
                this.contentsOffset = i26 + 1;
                bArr17[i26] = 115;
                int iLiteralIndex9 = this.constantPool.literalIndex(((StringConstant) constant).stringValue().toCharArray());
                if (iLiteralIndex9 == -1) {
                    if (!this.creatingProblemType) {
                        this.referenceBinding.scope.referenceContext.scope.problemReporter().stringConstantIsExceedingUtf8Limit(expression);
                    } else {
                        this.contentsOffset = i;
                    }
                } else {
                    byte[] bArr18 = this.contents;
                    int i27 = this.contentsOffset;
                    int i28 = i27 + 1;
                    this.contentsOffset = i28;
                    bArr18[i27] = (byte) (iLiteralIndex9 >> 8);
                    this.contentsOffset = i27 + 2;
                    bArr18[i28] = (byte) iLiteralIndex9;
                }
                break;
        }
    }

    private void generateElementValueForNonConstantExpression(Expression expression, int i, TypeBinding typeBinding) {
        FieldBinding fieldBinding;
        if (typeBinding != null) {
            if (typeBinding.isEnum()) {
                if (this.contentsOffset + 5 >= this.contents.length) {
                    resizeContents(5);
                }
                byte[] bArr = this.contents;
                int i2 = this.contentsOffset;
                this.contentsOffset = i2 + 1;
                bArr[i2] = 101;
                if (expression instanceof QualifiedNameReference) {
                    fieldBinding = (FieldBinding) ((QualifiedNameReference) expression).binding;
                } else if (expression instanceof SingleNameReference) {
                    fieldBinding = (FieldBinding) ((SingleNameReference) expression).binding;
                } else {
                    this.contentsOffset = i;
                    fieldBinding = null;
                }
                if (fieldBinding != null) {
                    int iLiteralIndex = this.constantPool.literalIndex(fieldBinding.type.signature());
                    int iLiteralIndex2 = this.constantPool.literalIndex(fieldBinding.f628name);
                    byte[] bArr2 = this.contents;
                    int i3 = this.contentsOffset;
                    int i4 = i3 + 1;
                    this.contentsOffset = i4;
                    bArr2[i3] = (byte) (iLiteralIndex >> 8);
                    int i5 = i3 + 2;
                    this.contentsOffset = i5;
                    bArr2[i4] = (byte) iLiteralIndex;
                    int i6 = i3 + 3;
                    this.contentsOffset = i6;
                    bArr2[i5] = (byte) (iLiteralIndex2 >> 8);
                    this.contentsOffset = i3 + 4;
                    bArr2[i6] = (byte) iLiteralIndex2;
                    return;
                }
                return;
            }
            if (typeBinding.isAnnotationType()) {
                if (this.contentsOffset + 1 >= this.contents.length) {
                    resizeContents(1);
                }
                byte[] bArr3 = this.contents;
                int i7 = this.contentsOffset;
                this.contentsOffset = i7 + 1;
                bArr3[i7] = 64;
                generateAnnotation((Annotation) expression, i);
                return;
            }
            if (typeBinding.isArrayType()) {
                if (this.contentsOffset + 3 >= this.contents.length) {
                    resizeContents(3);
                }
                byte[] bArr4 = this.contents;
                int i8 = this.contentsOffset;
                this.contentsOffset = i8 + 1;
                bArr4[i8] = 91;
                if (expression instanceof ArrayInitializer) {
                    ArrayInitializer arrayInitializer = (ArrayInitializer) expression;
                    int length = arrayInitializer.expressions != null ? arrayInitializer.expressions.length : 0;
                    byte[] bArr5 = this.contents;
                    int i9 = this.contentsOffset;
                    int i10 = i9 + 1;
                    this.contentsOffset = i10;
                    bArr5[i9] = (byte) (length >> 8);
                    this.contentsOffset = i9 + 2;
                    bArr5[i10] = (byte) length;
                    for (int i11 = 0; i11 < length; i11++) {
                        generateElementValue(arrayInitializer.expressions[i11], typeBinding.leafComponentType(), i);
                    }
                    return;
                }
                this.contentsOffset = i;
                return;
            }
            if (this.contentsOffset + 3 >= this.contents.length) {
                resizeContents(3);
            }
            byte[] bArr6 = this.contents;
            int i12 = this.contentsOffset;
            this.contentsOffset = i12 + 1;
            bArr6[i12] = 99;
            if (expression instanceof ClassLiteralAccess) {
                int iLiteralIndex3 = this.constantPool.literalIndex(((ClassLiteralAccess) expression).targetType.signature());
                byte[] bArr7 = this.contents;
                int i13 = this.contentsOffset;
                int i14 = i13 + 1;
                this.contentsOffset = i14;
                bArr7[i13] = (byte) (iLiteralIndex3 >> 8);
                this.contentsOffset = i13 + 2;
                bArr7[i14] = (byte) iLiteralIndex3;
                return;
            }
            this.contentsOffset = i;
            return;
        }
        this.contentsOffset = i;
    }

    private int generateEnclosingMethodAttribute() {
        byte b;
        MethodBinding methodBinding;
        int i = this.contentsOffset;
        if (i + 10 >= this.contents.length) {
            resizeContents(10);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.EnclosingMethodName);
        byte[] bArr = this.contents;
        bArr[i] = (byte) (iLiteralIndex >> 8);
        bArr[i + 1] = (byte) iLiteralIndex;
        byte b2 = 0;
        bArr[i + 2] = 0;
        bArr[i + 3] = 0;
        bArr[i + 4] = 0;
        bArr[i + 5] = 4;
        int iLiteralIndexForType = this.constantPool.literalIndexForType(this.referenceBinding.enclosingType().constantPoolName());
        byte[] bArr2 = this.contents;
        bArr2[i + 6] = (byte) (iLiteralIndexForType >> 8);
        int i2 = i + 8;
        bArr2[i + 7] = (byte) iLiteralIndexForType;
        SourceTypeBinding sourceTypeBinding = this.referenceBinding;
        if (!(sourceTypeBinding instanceof LocalTypeBinding) || (methodBinding = ((LocalTypeBinding) sourceTypeBinding).enclosingMethod) == null) {
            b = 0;
        } else {
            int iLiteralIndexForNameAndType = this.constantPool.literalIndexForNameAndType(methodBinding.selector, methodBinding.signature(this));
            b2 = (byte) (iLiteralIndexForNameAndType >> 8);
            b = (byte) iLiteralIndexForNameAndType;
        }
        byte[] bArr3 = this.contents;
        bArr3[i2] = b2;
        bArr3[i + 9] = b;
        this.contentsOffset = i + 10;
        return 1;
    }

    private int generateExceptionsAttribute(ReferenceBinding[] referenceBindingArr) {
        int i = this.contentsOffset;
        int length = referenceBindingArr.length;
        int i2 = length * 2;
        int i3 = i2 + 8;
        if (i3 + i >= this.contents.length) {
            resizeContents(i3);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.ExceptionsName);
        byte[] bArr = this.contents;
        bArr[i] = (byte) (iLiteralIndex >> 8);
        bArr[i + 1] = (byte) iLiteralIndex;
        int i4 = i2 + 2;
        bArr[i + 2] = (byte) (i4 >> 24);
        bArr[i + 3] = (byte) (i4 >> 16);
        bArr[i + 4] = (byte) (i4 >> 8);
        bArr[i + 5] = (byte) i4;
        int i5 = i + 7;
        bArr[i + 6] = (byte) (length >> 8);
        int i6 = i + 8;
        bArr[i5] = (byte) length;
        for (ReferenceBinding referenceBinding : referenceBindingArr) {
            int iLiteralIndexForType = this.constantPool.literalIndexForType(referenceBinding);
            byte[] bArr2 = this.contents;
            int i7 = i6 + 1;
            bArr2[i6] = (byte) (iLiteralIndexForType >> 8);
            i6 += 2;
            bArr2[i7] = (byte) iLiteralIndexForType;
        }
        this.contentsOffset = i6;
        return 1;
    }

    private int generateHierarchyInconsistentAttribute() {
        int i = this.contentsOffset;
        if (i + 6 >= this.contents.length) {
            resizeContents(6);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.InconsistentHierarchy);
        byte[] bArr = this.contents;
        bArr[i] = (byte) (iLiteralIndex >> 8);
        bArr[i + 1] = (byte) iLiteralIndex;
        bArr[i + 2] = 0;
        bArr[i + 3] = 0;
        bArr[i + 4] = 0;
        bArr[i + 5] = 0;
        this.contentsOffset = i + 6;
        return 1;
    }

    private int generateInnerClassAttribute(int i, ReferenceBinding[] referenceBindingArr) {
        int i2;
        int i3;
        int i4 = this.contentsOffset;
        int i5 = (i * 8) + 8;
        if (i5 + i4 >= this.contents.length) {
            resizeContents(i5);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.InnerClassName);
        byte[] bArr = this.contents;
        bArr[i4] = (byte) (iLiteralIndex >> 8);
        bArr[i4 + 1] = (byte) iLiteralIndex;
        int i6 = (i << 3) + 2;
        bArr[i4 + 2] = (byte) (i6 >> 24);
        bArr[i4 + 3] = (byte) (i6 >> 16);
        bArr[i4 + 4] = (byte) (i6 >> 8);
        bArr[i4 + 5] = (byte) i6;
        int i7 = i4 + 7;
        bArr[i4 + 6] = (byte) (i >> 8);
        int i8 = i4 + 8;
        bArr[i7] = (byte) i;
        for (int i9 = 0; i9 < i; i9++) {
            ReferenceBinding referenceBinding = referenceBindingArr[i9];
            int accessFlags = referenceBinding.getAccessFlags();
            int iLiteralIndexForType = this.constantPool.literalIndexForType(referenceBinding.constantPoolName());
            byte[] bArr2 = this.contents;
            bArr2[i8] = (byte) (iLiteralIndexForType >> 8);
            int i10 = i8 + 2;
            bArr2[i8 + 1] = (byte) iLiteralIndexForType;
            if (referenceBinding.isMemberType()) {
                int iLiteralIndexForType2 = this.constantPool.literalIndexForType(referenceBinding.enclosingType().constantPoolName());
                byte[] bArr3 = this.contents;
                int i11 = i8 + 3;
                bArr3[i10] = (byte) (iLiteralIndexForType2 >> 8);
                i2 = i8 + 4;
                bArr3[i11] = (byte) iLiteralIndexForType2;
            } else {
                byte[] bArr4 = this.contents;
                int i12 = i8 + 3;
                bArr4[i10] = 0;
                i2 = i8 + 4;
                bArr4[i12] = 0;
            }
            if (!referenceBinding.isAnonymousType()) {
                int iLiteralIndex2 = this.constantPool.literalIndex(referenceBinding.sourceName());
                byte[] bArr5 = this.contents;
                int i13 = i2 + 1;
                bArr5[i2] = (byte) (iLiteralIndex2 >> 8);
                i3 = i2 + 2;
                bArr5[i13] = (byte) iLiteralIndex2;
            } else {
                byte[] bArr6 = this.contents;
                int i14 = i2 + 1;
                bArr6[i2] = 0;
                i3 = i2 + 2;
                bArr6[i14] = 0;
            }
            if (referenceBinding.isAnonymousType()) {
                accessFlags &= -17;
            } else if (referenceBinding.isMemberType() && referenceBinding.isInterface()) {
                accessFlags |= 8;
            }
            byte[] bArr7 = this.contents;
            int i15 = i3 + 1;
            bArr7[i3] = (byte) (accessFlags >> 8);
            i8 = i3 + 2;
            bArr7[i15] = (byte) accessFlags;
        }
        this.contentsOffset = i8;
        return 1;
    }

    /* JADX WARN: Code duplicated, block: B:18:0x0097 A[PHI: r3
      0x0097: PHI (r3v27 org.eclipse.jdt.internal.compiler.lookup.TypeBinding[]) = 
      (r3v13 org.eclipse.jdt.internal.compiler.lookup.TypeBinding[])
      (r3v13 org.eclipse.jdt.internal.compiler.lookup.TypeBinding[])
      (r3v50 org.eclipse.jdt.internal.compiler.lookup.TypeBinding[])
     binds: [B:20:0x009b, B:22:0x009f, B:16:0x0094] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Code duplicated, block: B:33:0x0137  */
    /* JADX WARN: Code duplicated, block: B:34:0x013c  */
    /* JADX WARN: Code duplicated, block: B:36:0x013f  */
    /* JADX WARN: Code duplicated, block: B:39:0x014b  */
    /* JADX WARN: Code duplicated, block: B:41:0x0150  */
    /* JADX WARN: Code duplicated, block: B:42:0x016c  */
    /* JADX WARN: Code duplicated, block: B:45:0x0187  */
    /* JADX WARN: Code duplicated, block: B:46:0x0189  */
    /* JADX WARN: Code duplicated, block: B:49:0x0194  */
    /* JADX WARN: Code duplicated, block: B:50:0x0196  */
    /* JADX WARN: Code duplicated, block: B:53:0x01f3  */
    /* JADX WARN: Code duplicated, block: B:55:0x01f7  */
    /* JADX WARN: Code duplicated, block: B:58:0x020f  */
    /* JADX WARN: Code duplicated, block: B:61:0x022a A[LOOP:1: B:59:0x0227->B:61:0x022a, LOOP_END] */
    /* JADX WARN: Code duplicated, block: B:63:0x0245  */
    /* JADX WARN: Code duplicated, block: B:66:0x025f A[LOOP:2: B:64:0x025c->B:66:0x025f, LOOP_END] */
    /* JADX WARN: Instruction removed from duplicated block: B:58:0x020f, please report this as an issue */
    /* JADX WARN: Instruction removed from duplicated block: B:63:0x0245, please report this as an issue */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private int generateBootstrapMethods(List list) {
        TypeBinding[] markerInterfaces;
        TypeBinding[] typeBindingArr;
        int length;
        int i;
        FunctionalExpression functionalExpression;
        int length2;
        int length3;
        boolean z;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        ReferenceBinding javaLangInvokeMethodHandlesLookup = this.referenceBinding.scope.getJavaLangInvokeMethodHandlesLookup();
        byte b = 0;
        if (javaLangInvokeMethodHandlesLookup == null) {
            return 0;
        }
        recordInnerClasses(javaLangInvokeMethodHandlesLookup);
        ReferenceBinding javaLangInvokeLambdaMetafactory = this.referenceBinding.scope.getJavaLangInvokeLambdaMetafactory();
        int size = list.size();
        int i7 = this.contentsOffset;
        int i8 = (size * 10) + 8;
        if (i8 + i7 >= this.contents.length) {
            resizeContents(i8);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.BootstrapMethodsName);
        byte[] bArr = this.contents;
        bArr[i7] = (byte) (iLiteralIndex >> 8);
        int i9 = i7 + 2;
        bArr[i7 + 1] = (byte) iLiteralIndex;
        bArr[i7 + 6] = (byte) (size >> 8);
        bArr[i7 + 7] = (byte) size;
        int i10 = 0;
        int iLiteralIndexForMethodHandle = 0;
        int iLiteralIndexForMethodHandle2 = 0;
        int i11 = i7 + 8;
        while (i10 < size) {
            FunctionalExpression functionalExpression2 = (FunctionalExpression) list.get(i10);
            MethodBinding[] requiredBridges = functionalExpression2.getRequiredBridges();
            if (functionalExpression2 instanceof LambdaExpression) {
                markerInterfaces = ((LambdaExpression) functionalExpression2).getMarkerInterfaces();
                if (markerInterfaces != null) {
                    typeBindingArr = markerInterfaces;
                    if (typeBindingArr != null) {
                        length = (typeBindingArr.length * 2) + 4;
                    } else {
                        length = 2;
                    }
                    if (requiredBridges != null) {
                        length += (requiredBridges.length * 2) + 2;
                    }
                    i = length + 10;
                    if (i + i11 >= this.contents.length) {
                        resizeContents(i);
                    }
                    if (iLiteralIndexForMethodHandle2 == 0) {
                        iLiteralIndexForMethodHandle2 = this.constantPool.literalIndexForMethodHandle(6, javaLangInvokeLambdaMetafactory, ConstantPool.ALTMETAFACTORY, ConstantPool.JAVA_LANG_INVOKE_LAMBDAMETAFACTORY_ALTMETAFACTORY_SIGNATURE, false);
                        functionalExpression = functionalExpression2;
                    } else {
                        functionalExpression = functionalExpression2;
                    }
                    byte[] bArr2 = this.contents;
                    bArr2[i11] = (byte) (iLiteralIndexForMethodHandle2 >> 8);
                    bArr2[i11 + 1] = (byte) iLiteralIndexForMethodHandle2;
                    int i12 = i11 + 3;
                    bArr2[i11 + 2] = 0;
                    int i13 = i11 + 4;
                    if (typeBindingArr == 0) {
                        length2 = 0;
                    } else {
                        length2 = typeBindingArr.length + 1;
                    }
                    int i14 = length2 + 4;
                    if (requiredBridges == null) {
                        length3 = 0;
                    } else {
                        length3 = requiredBridges.length + 1;
                    }
                    bArr2[i12] = (byte) (i14 + length3);
                    int iLiteralIndexForMethodType = this.constantPool.literalIndexForMethodType(functionalExpression.descriptor.original().signature());
                    byte[] bArr3 = this.contents;
                    bArr3[i13] = (byte) (iLiteralIndexForMethodType >> 8);
                    bArr3[i11 + 5] = (byte) iLiteralIndexForMethodType;
                    int iLiteralIndexForMethodHandle3 = this.constantPool.literalIndexForMethodHandle(functionalExpression.binding.original());
                    byte[] bArr4 = this.contents;
                    bArr4[i11 + 6] = (byte) (iLiteralIndexForMethodHandle3 >> 8);
                    bArr4[i11 + 7] = (byte) iLiteralIndexForMethodHandle3;
                    int iLiteralIndexForMethodType2 = this.constantPool.literalIndexForMethodType(functionalExpression.descriptor.signature());
                    byte[] bArr5 = this.contents;
                    bArr5[i11 + 8] = (byte) (iLiteralIndexForMethodType2 >> 8);
                    int i15 = i11 + 10;
                    bArr5[i11 + 9] = (byte) iLiteralIndexForMethodType2;
                    z = functionalExpression.isSerializable;
                    i2 = z;
                    if (typeBindingArr != 0) {
                        i6 = (z ? 1 : 0) | 2;
                    }
                    if (requiredBridges != null) {
                        i2 = i6;
                        i2 = (i2 == true ? 1 : 0) | 4;
                    }
                    i2 = i6;
                    int iLiteralIndex2 = this.constantPool.literalIndex(i2);
                    byte[] bArr6 = this.contents;
                    bArr6[i15] = (byte) (iLiteralIndex2 >> 8);
                    i3 = i11 + 12;
                    bArr6[i11 + 11] = (byte) iLiteralIndex2;
                    if (typeBindingArr != 0) {
                        int iLiteralIndex3 = this.constantPool.literalIndex(typeBindingArr.length);
                        byte[] bArr7 = this.contents;
                        bArr7[i3] = (byte) (iLiteralIndex3 >> 8);
                        bArr7[i11 + 13] = (byte) iLiteralIndex3;
                        i3 = i11 + 14;
                        for (TypeBinding typeBinding : typeBindingArr) {
                            int iLiteralIndexForType = this.constantPool.literalIndexForType(typeBinding);
                            byte[] bArr8 = this.contents;
                            int i16 = i3 + 1;
                            bArr8[i3] = (byte) (iLiteralIndexForType >> 8);
                            i3 += 2;
                            bArr8[i16] = (byte) iLiteralIndexForType;
                        }
                    }
                    if (requiredBridges != null) {
                        int iLiteralIndex4 = this.constantPool.literalIndex(requiredBridges.length);
                        byte[] bArr9 = this.contents;
                        int i17 = i3 + 1;
                        bArr9[i3] = (byte) (iLiteralIndex4 >> 8);
                        i3 += 2;
                        bArr9[i17] = (byte) iLiteralIndex4;
                        for (MethodBinding methodBinding : requiredBridges) {
                            int iLiteralIndexForMethodType3 = this.constantPool.literalIndexForMethodType(methodBinding.signature());
                            byte[] bArr10 = this.contents;
                            int i18 = i3 + 1;
                            bArr10[i3] = (byte) (iLiteralIndexForMethodType3 >> 8);
                            i3 += 2;
                            bArr10[i18] = (byte) iLiteralIndexForMethodType3;
                        }
                    }
                    i11 = i3;
                }
                i10++;
                javaLangInvokeLambdaMetafactory = javaLangInvokeLambdaMetafactory;
                b = 0;
            } else {
                markerInterfaces = null;
            }
            if (requiredBridges != null || functionalExpression2.isSerializable) {
                typeBindingArr = markerInterfaces;
                if (typeBindingArr != null) {
                    length = (typeBindingArr.length * 2) + 4;
                } else {
                    length = 2;
                }
                if (requiredBridges != null) {
                    length += (requiredBridges.length * 2) + 2;
                }
                i = length + 10;
                if (i + i11 >= this.contents.length) {
                    resizeContents(i);
                }
                if (iLiteralIndexForMethodHandle2 == 0) {
                    iLiteralIndexForMethodHandle2 = this.constantPool.literalIndexForMethodHandle(6, javaLangInvokeLambdaMetafactory, ConstantPool.ALTMETAFACTORY, ConstantPool.JAVA_LANG_INVOKE_LAMBDAMETAFACTORY_ALTMETAFACTORY_SIGNATURE, false);
                    functionalExpression = functionalExpression2;
                } else {
                    functionalExpression = functionalExpression2;
                }
                byte[] bArr11 = this.contents;
                bArr11[i11] = (byte) (iLiteralIndexForMethodHandle2 >> 8);
                bArr11[i11 + 1] = (byte) iLiteralIndexForMethodHandle2;
                int i19 = i11 + 3;
                bArr11[i11 + 2] = 0;
                int i110 = i11 + 4;
                if (typeBindingArr == 0) {
                    length2 = 0;
                } else {
                    length2 = typeBindingArr.length + 1;
                }
                int i111 = length2 + 4;
                if (requiredBridges == null) {
                    length3 = 0;
                } else {
                    length3 = requiredBridges.length + 1;
                }
                bArr11[i19] = (byte) (i111 + length3);
                int iLiteralIndexForMethodType4 = this.constantPool.literalIndexForMethodType(functionalExpression.descriptor.original().signature());
                byte[] bArr12 = this.contents;
                bArr12[i110] = (byte) (iLiteralIndexForMethodType4 >> 8);
                bArr12[i11 + 5] = (byte) iLiteralIndexForMethodType4;
                int iLiteralIndexForMethodHandle4 = this.constantPool.literalIndexForMethodHandle(functionalExpression.binding.original());
                byte[] bArr13 = this.contents;
                bArr13[i11 + 6] = (byte) (iLiteralIndexForMethodHandle4 >> 8);
                bArr13[i11 + 7] = (byte) iLiteralIndexForMethodHandle4;
                int iLiteralIndexForMethodType5 = this.constantPool.literalIndexForMethodType(functionalExpression.descriptor.signature());
                byte[] bArr14 = this.contents;
                bArr14[i11 + 8] = (byte) (iLiteralIndexForMethodType5 >> 8);
                int i112 = i11 + 10;
                bArr14[i11 + 9] = (byte) iLiteralIndexForMethodType5;
                z = functionalExpression.isSerializable;
                i2 = z;
                if (typeBindingArr != 0) {
                    i6 = (z ? 1 : 0) | 2;
                }
                if (requiredBridges != null) {
                    i2 = i6;
                    i2 = (i2 == true ? 1 : 0) | 4;
                }
                i2 = i6;
                int iLiteralIndex5 = this.constantPool.literalIndex(i2);
                byte[] bArr15 = this.contents;
                bArr15[i112] = (byte) (iLiteralIndex5 >> 8);
                i3 = i11 + 12;
                bArr15[i11 + 11] = (byte) iLiteralIndex5;
                if (typeBindingArr != 0) {
                    int iLiteralIndex6 = this.constantPool.literalIndex(typeBindingArr.length);
                    byte[] bArr16 = this.contents;
                    bArr16[i3] = (byte) (iLiteralIndex6 >> 8);
                    bArr16[i11 + 13] = (byte) iLiteralIndex6;
                    i3 = i11 + 14;
                    while (i5 < r1) {
                        int iLiteralIndexForType2 = this.constantPool.literalIndexForType(typeBinding);
                        byte[] bArr17 = this.contents;
                        int i113 = i3 + 1;
                        bArr17[i3] = (byte) (iLiteralIndexForType2 >> 8);
                        i3 += 2;
                        bArr17[i113] = (byte) iLiteralIndexForType2;
                    }
                }
                if (requiredBridges != null) {
                    int iLiteralIndex7 = this.constantPool.literalIndex(requiredBridges.length);
                    byte[] bArr18 = this.contents;
                    int i114 = i3 + 1;
                    bArr18[i3] = (byte) (iLiteralIndex7 >> 8);
                    i3 += 2;
                    bArr18[i114] = (byte) iLiteralIndex7;
                    while (i4 < r1) {
                        int iLiteralIndexForMethodType6 = this.constantPool.literalIndexForMethodType(methodBinding.signature());
                        byte[] bArr19 = this.contents;
                        int i115 = i3 + 1;
                        bArr19[i3] = (byte) (iLiteralIndexForMethodType6 >> 8);
                        i3 += 2;
                        bArr19[i115] = (byte) iLiteralIndexForMethodType6;
                    }
                }
                i11 = i3;
            } else {
                if (i11 + 10 >= this.contents.length) {
                    resizeContents(10);
                }
                if (iLiteralIndexForMethodHandle == 0) {
                    iLiteralIndexForMethodHandle = this.constantPool.literalIndexForMethodHandle(6, javaLangInvokeLambdaMetafactory, ConstantPool.METAFACTORY, ConstantPool.JAVA_LANG_INVOKE_LAMBDAMETAFACTORY_METAFACTORY_SIGNATURE, false);
                }
                byte[] bArr20 = this.contents;
                bArr20[i11] = (byte) (iLiteralIndexForMethodHandle >> 8);
                bArr20[i11 + 1] = (byte) iLiteralIndexForMethodHandle;
                bArr20[i11 + 2] = b;
                bArr20[i11 + 3] = 3;
                int iLiteralIndexForMethodType7 = this.constantPool.literalIndexForMethodType(functionalExpression2.descriptor.original().signature());
                byte[] bArr21 = this.contents;
                bArr21[i11 + 4] = (byte) (iLiteralIndexForMethodType7 >> 8);
                bArr21[i11 + 5] = (byte) iLiteralIndexForMethodType7;
                int iLiteralIndexForMethodHandle5 = this.constantPool.literalIndexForMethodHandle(functionalExpression2.binding.original());
                byte[] bArr22 = this.contents;
                bArr22[i11 + 6] = (byte) (iLiteralIndexForMethodHandle5 >> 8);
                bArr22[i11 + 7] = (byte) iLiteralIndexForMethodHandle5;
                int iLiteralIndexForMethodType8 = this.constantPool.literalIndexForMethodType(functionalExpression2.descriptor.signature());
                byte[] bArr23 = this.contents;
                int i20 = i11 + 9;
                bArr23[i11 + 8] = (byte) (iLiteralIndexForMethodType8 >> 8);
                i11 += 10;
                bArr23[i20] = (byte) iLiteralIndexForMethodType8;
            }
            i10++;
            javaLangInvokeLambdaMetafactory = javaLangInvokeLambdaMetafactory;
            b = 0;
        }
        int i21 = (i11 - i9) - 4;
        byte[] bArr24 = this.contents;
        bArr24[i9] = (byte) (i21 >> 24);
        bArr24[i7 + 3] = (byte) (i21 >> 16);
        bArr24[i7 + 4] = (byte) (i21 >> 8);
        bArr24[i7 + 5] = (byte) i21;
        this.contentsOffset = i11;
        return 1;
    }

    private int generateLineNumberAttribute() {
        int i = this.contentsOffset;
        int[] iArr = this.codeStream.pcToSourceMap;
        int i2 = 0;
        if (iArr != null && this.codeStream.pcToSourceMapSize != 0) {
            int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.LineNumberTableName);
            if (i + 8 >= this.contents.length) {
                resizeContents(8);
            }
            byte[] bArr = this.contents;
            bArr[i] = (byte) (iLiteralIndex >> 8);
            int i3 = i + 2;
            bArr[i + 1] = (byte) iLiteralIndex;
            int i4 = this.codeStream.pcToSourceMapSize;
            int i5 = i + 8;
            int i6 = 0;
            while (i2 < i4) {
                if (i5 + 4 >= this.contents.length) {
                    resizeContents(4);
                }
                int i7 = i2 + 1;
                int i8 = iArr[i2];
                byte[] bArr2 = this.contents;
                bArr2[i5] = (byte) (i8 >> 8);
                bArr2[i5 + 1] = (byte) i8;
                i2 += 2;
                int i9 = iArr[i7];
                int i10 = i5 + 3;
                bArr2[i5 + 2] = (byte) (i9 >> 8);
                i5 += 4;
                bArr2[i10] = (byte) i9;
                i6++;
            }
            int i11 = (i6 * 4) + 2;
            byte[] bArr3 = this.contents;
            bArr3[i3] = (byte) (i11 >> 24);
            bArr3[i + 3] = (byte) (i11 >> 16);
            bArr3[i + 4] = (byte) (i11 >> 8);
            bArr3[i + 5] = (byte) i11;
            bArr3[i + 6] = (byte) (i6 >> 8);
            bArr3[i + 7] = (byte) i6;
            i2 = 1;
            i = i5;
        }
        this.contentsOffset = i;
        return i2;
    }

    private int generateLineNumberAttribute(int i) {
        int i2 = this.contentsOffset;
        if (i2 + 12 >= this.contents.length) {
            resizeContents(12);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.LineNumberTableName);
        byte[] bArr = this.contents;
        bArr[i2] = (byte) (iLiteralIndex >> 8);
        bArr[i2 + 1] = (byte) iLiteralIndex;
        bArr[i2 + 2] = 0;
        bArr[i2 + 3] = 0;
        bArr[i2 + 4] = 0;
        bArr[i2 + 5] = 6;
        bArr[i2 + 6] = 0;
        bArr[i2 + 7] = 1;
        bArr[i2 + 8] = 0;
        bArr[i2 + 9] = 0;
        bArr[i2 + 10] = (byte) (i >> 8);
        bArr[i2 + 11] = (byte) i;
        this.contentsOffset = i2 + 12;
        return 1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    private int generateLocalVariableTableAttribute(int i, boolean z, boolean z2) {
        int i2;
        SourceTypeBinding sourceTypeBinding;
        int i3;
        int i4;
        int i5 = this.contentsOffset;
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.LocalVariableTableName);
        int i6 = ((!z ? 1 : 0) * 10) + 8;
        byte b = 0;
        int i7 = 0;
        while (i7 < this.codeStream.allLocalsCounter) {
            i6 += this.codeStream.locals[i7].initializationCount * 10;
            i7++;
            b = 0;
        }
        if (i5 + i6 >= this.contents.length) {
            resizeContents(i6);
        }
        byte[] bArr = this.contents;
        bArr[i5] = (byte) (iLiteralIndex >> 8);
        int i8 = i5 + 2;
        bArr[i5 + 1] = (byte) iLiteralIndex;
        int i9 = i5 + 8;
        LocalVariableBinding[] localVariableBindingArr = null;
        if (z || z2) {
            i2 = b;
            sourceTypeBinding = null;
        } else {
            bArr[i9] = b;
            bArr[i5 + 9] = b;
            bArr[i5 + 10] = (byte) (i >> 8);
            bArr[i5 + 11] = (byte) i;
            int iLiteralIndex2 = this.constantPool.literalIndex(ConstantPool.This);
            byte[] bArr2 = this.contents;
            bArr2[i5 + 12] = (byte) (iLiteralIndex2 >> 8);
            int i10 = i5 + 14;
            bArr2[i5 + 13] = (byte) iLiteralIndex2;
            SourceTypeBinding sourceTypeBinding2 = (SourceTypeBinding) (this.codeStream.methodDeclaration != null ? this.codeStream.methodDeclaration.binding : this.codeStream.lambdaExpression.binding).declaringClass;
            int iLiteralIndex3 = this.constantPool.literalIndex(sourceTypeBinding2.signature());
            byte[] bArr3 = this.contents;
            bArr3[i10] = (byte) (iLiteralIndex3 >> 8);
            bArr3[i5 + 15] = (byte) iLiteralIndex3;
            bArr3[i5 + 16] = b;
            i9 = i5 + 18;
            bArr3[i5 + 17] = b;
            sourceTypeBinding = sourceTypeBinding2;
            i2 = 1;
        }
        int i11 = this.codeStream.allLocalsCounter;
        int i12 = b;
        int i13 = i12;
        int i14 = i13;
        while (i12 < i11) {
            LocalVariableBinding localVariableBinding = this.codeStream.locals[i12];
            int i15 = localVariableBinding.initializationCount;
            if (i15 != 0 && localVariableBinding.declaration != null) {
                TypeBinding typeBinding = localVariableBinding.type;
                byte b2 = (typeBinding.isParameterizedType() || typeBinding.isTypeVariable()) ? (byte) 1 : b;
                if (b2 != 0) {
                    if (localVariableBindingArr == null) {
                        localVariableBindingArr = new LocalVariableBinding[i11];
                    }
                    i4 = i13 + 1;
                    localVariableBindingArr[i13] = localVariableBinding;
                } else {
                    i4 = i13;
                }
                LocalVariableBinding[] localVariableBindingArr2 = localVariableBindingArr;
                int i16 = b;
                while (i16 < i15) {
                    int i17 = i16 << 1;
                    int i18 = localVariableBinding.initializationPCs[i17];
                    int i19 = localVariableBinding.initializationPCs[i17 + 1];
                    int i20 = i5;
                    if (i18 != i19) {
                        if (i19 == -1) {
                            localVariableBinding.declaringScope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidAttribute, new String(localVariableBinding.f628name)), (ASTNode) localVariableBinding.declaringScope.methodScope().referenceContext);
                        }
                        if (b2 != 0) {
                            i14++;
                        }
                        i2++;
                        byte[] bArr4 = this.contents;
                        bArr4[i9] = (byte) (i18 >> 8);
                        bArr4[i9 + 1] = (byte) i18;
                        int i21 = i19 - i18;
                        bArr4[i9 + 2] = (byte) (i21 >> 8);
                        bArr4[i9 + 3] = (byte) i21;
                        int iLiteralIndex4 = this.constantPool.literalIndex(localVariableBinding.f628name);
                        byte[] bArr5 = this.contents;
                        bArr5[i9 + 4] = (byte) (iLiteralIndex4 >> 8);
                        bArr5[i9 + 5] = (byte) iLiteralIndex4;
                        int iLiteralIndex5 = this.constantPool.literalIndex(typeBinding.signature());
                        byte[] bArr6 = this.contents;
                        bArr6[i9 + 6] = (byte) (iLiteralIndex5 >> 8);
                        bArr6[i9 + 7] = (byte) iLiteralIndex5;
                        int i22 = localVariableBinding.resolvedPosition;
                        byte[] bArr7 = this.contents;
                        int i23 = i9 + 9;
                        bArr7[i9 + 8] = (byte) (i22 >> 8);
                        i9 += 10;
                        bArr7[i23] = (byte) i22;
                    } else {
                        i8 = i8;
                        sourceTypeBinding = sourceTypeBinding;
                        i11 = i11;
                    }
                    i16++;
                    i = i;
                    i5 = i20;
                    i8 = i8;
                    sourceTypeBinding = sourceTypeBinding;
                    i11 = i11;
                    b = 0;
                }
                localVariableBindingArr = localVariableBindingArr2;
                i13 = i4;
            }
            i12++;
        }
        int i24 = (i2 * 10) + 2;
        byte[] bArr8 = this.contents;
        bArr8[i8] = (byte) (i24 >> 24);
        bArr8[i5 + 3] = (byte) (i24 >> 16);
        bArr8[i5 + 4] = (byte) (i24 >> 8);
        bArr8[i5 + 5] = (byte) i24;
        bArr8[i5 + 6] = (byte) (i2 >> 8);
        bArr8[i5 + 7] = (byte) i2;
        byte b3 = (z || sourceTypeBinding == null || sourceTypeBinding.typeVariables == Binding.NO_TYPE_VARIABLES) ? b : (byte) 1;
        if (i13 == 0 && b3 == 0) {
            i3 = 1;
        } else {
            int i25 = i14 + b3;
            int i26 = i25 * 10;
            int i27 = i26 + 8;
            if (i9 + i27 >= this.contents.length) {
                resizeContents(i27);
            }
            int iLiteralIndex6 = this.constantPool.literalIndex(AttributeNamesConstants.LocalVariableTypeTableName);
            byte[] bArr9 = this.contents;
            bArr9[i9] = (byte) (iLiteralIndex6 >> 8);
            bArr9[i9 + 1] = (byte) iLiteralIndex6;
            int i28 = i26 + 2;
            bArr9[i9 + 2] = (byte) (i28 >> 24);
            bArr9[i9 + 3] = (byte) (i28 >> 16);
            bArr9[i9 + 4] = (byte) (i28 >> 8);
            bArr9[i9 + 5] = (byte) i28;
            bArr9[i9 + 6] = (byte) (i25 >> 8);
            int i29 = i9 + 8;
            bArr9[i9 + 7] = (byte) i25;
            if (b3 != 0) {
                bArr9[i29] = b;
                bArr9[i9 + 9] = b;
                bArr9[i9 + 10] = (byte) (i >> 8);
                bArr9[i9 + 11] = (byte) i;
                int iLiteralIndex7 = this.constantPool.literalIndex(ConstantPool.This);
                byte[] bArr10 = this.contents;
                bArr10[i9 + 12] = (byte) (iLiteralIndex7 >> 8);
                bArr10[i9 + 13] = (byte) iLiteralIndex7;
                int iLiteralIndex8 = this.constantPool.literalIndex(sourceTypeBinding.genericTypeSignature());
                byte[] bArr11 = this.contents;
                bArr11[i9 + 14] = (byte) (iLiteralIndex8 >> 8);
                bArr11[i9 + 15] = (byte) iLiteralIndex8;
                bArr11[i9 + 16] = b;
                i29 = i9 + 18;
                bArr11[i9 + 17] = b;
            }
            i9 = i29;
            for (int i30 = b; i30 < i13; i30++) {
                LocalVariableBinding localVariableBinding2 = localVariableBindingArr[i30];
                for (int i31 = b; i31 < localVariableBinding2.initializationCount; i31++) {
                    int i32 = i31 << 1;
                    int i33 = localVariableBinding2.initializationPCs[i32];
                    int i34 = localVariableBinding2.initializationPCs[i32 + 1];
                    if (i33 != i34) {
                        byte[] bArr12 = this.contents;
                        bArr12[i9] = (byte) (i33 >> 8);
                        bArr12[i9 + 1] = (byte) i33;
                        int i35 = i34 - i33;
                        bArr12[i9 + 2] = (byte) (i35 >> 8);
                        bArr12[i9 + 3] = (byte) i35;
                        int iLiteralIndex9 = this.constantPool.literalIndex(localVariableBinding2.f628name);
                        byte[] bArr13 = this.contents;
                        bArr13[i9 + 4] = (byte) (iLiteralIndex9 >> 8);
                        bArr13[i9 + 5] = (byte) iLiteralIndex9;
                        int iLiteralIndex10 = this.constantPool.literalIndex(localVariableBinding2.type.genericTypeSignature());
                        byte[] bArr14 = this.contents;
                        bArr14[i9 + 6] = (byte) (iLiteralIndex10 >> 8);
                        bArr14[i9 + 7] = (byte) iLiteralIndex10;
                        int i36 = localVariableBinding2.resolvedPosition;
                        byte[] bArr15 = this.contents;
                        int i37 = i9 + 9;
                        bArr15[i9 + 8] = (byte) (i36 >> 8);
                        i9 += 10;
                        bArr15[i37] = (byte) i36;
                    }
                }
            }
            i3 = 2;
        }
        this.contentsOffset = i9;
        return i3;
    }

    public int generateMethodInfoAttributes(MethodBinding methodBinding) {
        Argument[] argumentArrArguments;
        int iGenerateRuntimeAnnotationsForParameters;
        Argument[] argumentArr;
        int i = this.contentsOffset;
        this.contentsOffset = i + 2;
        if (i + 4 >= this.contents.length) {
            resizeContents(2);
        }
        ReferenceBinding[] referenceBindingArr = methodBinding.thrownExceptions;
        int iGenerateExceptionsAttribute = referenceBindingArr != Binding.NO_EXCEPTIONS ? generateExceptionsAttribute(referenceBindingArr) : 0;
        if (methodBinding.isDeprecated()) {
            iGenerateExceptionsAttribute += generateDeprecatedAttribute();
        }
        if (this.targetJDK < ClassFileConstants.JDK1_5) {
            if (methodBinding.isSynthetic()) {
                iGenerateExceptionsAttribute += generateSyntheticAttribute();
            }
            if (methodBinding.isVarargs()) {
                iGenerateExceptionsAttribute += generateVarargsAttribute();
            }
        }
        char[] cArrGenericSignature = methodBinding.genericSignature();
        if (cArrGenericSignature != null) {
            iGenerateExceptionsAttribute += generateSignatureAttribute(cArrGenericSignature);
        }
        if (this.targetJDK >= ClassFileConstants.JDK1_4) {
            AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = methodBinding.sourceMethod();
            if (methodBinding instanceof SyntheticMethodBinding) {
                SyntheticMethodBinding syntheticMethodBinding = (SyntheticMethodBinding) methodBinding;
                if (syntheticMethodBinding.purpose == 7 && CharOperation.equals(syntheticMethodBinding.selector, syntheticMethodBinding.targetMethod.selector)) {
                    abstractMethodDeclarationSourceMethod = syntheticMethodBinding.targetMethod.sourceMethod();
                }
            }
            if (abstractMethodDeclarationSourceMethod != null) {
                Annotation[] annotationArr = abstractMethodDeclarationSourceMethod.annotations;
                if (annotationArr != null) {
                    iGenerateExceptionsAttribute += generateRuntimeAnnotations(annotationArr, methodBinding.isConstructor() ? 1099511627776L : 274877906944L);
                }
                if ((1024 & methodBinding.tagBits) != 0 && (argumentArr = abstractMethodDeclarationSourceMethod.arguments) != null) {
                    iGenerateRuntimeAnnotationsForParameters = generateRuntimeAnnotationsForParameters(argumentArr);
                    iGenerateExceptionsAttribute += iGenerateRuntimeAnnotationsForParameters;
                }
            } else {
                LambdaExpression lambdaExpressionSourceLambda = methodBinding.sourceLambda();
                if (lambdaExpressionSourceLambda != null && (1024 & methodBinding.tagBits) != 0 && (argumentArrArguments = lambdaExpressionSourceLambda.arguments()) != null) {
                    int length = methodBinding.parameters.length;
                    int length2 = argumentArrArguments.length;
                    if (length > length2) {
                        int i2 = length - length2;
                        Argument[] argumentArr2 = new Argument[length];
                        System.arraycopy(argumentArrArguments, 0, argumentArr2, i2, length2);
                        for (int i3 = 0; i3 < i2; i3++) {
                            argumentArr2[i3] = new Argument(CharOperation.NO_CHAR, 0L, null, 0);
                        }
                        argumentArrArguments = argumentArr2;
                    }
                    iGenerateRuntimeAnnotationsForParameters = generateRuntimeAnnotationsForParameters(argumentArrArguments);
                    iGenerateExceptionsAttribute += iGenerateRuntimeAnnotationsForParameters;
                }
            }
        }
        if ((methodBinding.tagBits & 128) != 0) {
            this.missingTypes = methodBinding.collectMissingTypes(this.missingTypes);
        }
        return iGenerateExceptionsAttribute;
    }

    public int generateMethodInfoAttributes(MethodBinding methodBinding, AnnotationMethodDeclaration annotationMethodDeclaration) {
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(methodBinding);
        return (annotationMethodDeclaration.modifiers & 131072) != 0 ? iGenerateMethodInfoAttributes + generateAnnotationDefaultAttribute(annotationMethodDeclaration, this.contentsOffset) : iGenerateMethodInfoAttributes;
    }

    public void generateMethodInfoHeader(MethodBinding methodBinding) {
        generateMethodInfoHeader(methodBinding, methodBinding.modifiers);
    }

    public void generateMethodInfoHeader(MethodBinding methodBinding, int i) {
        this.methodCount++;
        if (this.contentsOffset + 10 >= this.contents.length) {
            resizeContents(10);
        }
        if (this.targetJDK < ClassFileConstants.JDK1_5) {
            i &= -4225;
        }
        if ((methodBinding.tagBits & 512) != 0) {
            i &= -3;
        }
        byte[] bArr = this.contents;
        int i2 = this.contentsOffset;
        int i3 = i2 + 1;
        this.contentsOffset = i3;
        bArr[i2] = (byte) (i >> 8);
        this.contentsOffset = i2 + 2;
        bArr[i3] = (byte) i;
        int iLiteralIndex = this.constantPool.literalIndex(methodBinding.selector);
        byte[] bArr2 = this.contents;
        int i4 = this.contentsOffset;
        int i5 = i4 + 1;
        this.contentsOffset = i5;
        bArr2[i4] = (byte) (iLiteralIndex >> 8);
        this.contentsOffset = i4 + 2;
        bArr2[i5] = (byte) iLiteralIndex;
        int iLiteralIndex2 = this.constantPool.literalIndex(methodBinding.signature(this));
        byte[] bArr3 = this.contents;
        int i6 = this.contentsOffset;
        int i7 = i6 + 1;
        this.contentsOffset = i7;
        bArr3[i6] = (byte) (iLiteralIndex2 >> 8);
        this.contentsOffset = i6 + 2;
        bArr3[i7] = (byte) iLiteralIndex2;
    }

    public void addSyntheticDeserializeLambda(SyntheticMethodBinding syntheticMethodBinding, SyntheticMethodBinding[] syntheticMethodBindingArr) {
        generateMethodInfoHeader(syntheticMethodBinding);
        int i = this.contentsOffset;
        int iGenerateMethodInfoAttributes = generateMethodInfoAttributes(syntheticMethodBinding);
        int i2 = this.contentsOffset;
        int iGenerateMethodParameters = iGenerateMethodInfoAttributes + 1;
        generateCodeAttributeHeader();
        this.codeStream.init(this);
        this.codeStream.generateSyntheticBodyForDeserializeLambda(syntheticMethodBinding, syntheticMethodBindingArr);
        if (this.codeStream.position > 65535) {
            this.referenceBinding.scope.problemReporter().bytecodeExceeds64KLimit(syntheticMethodBinding, this.referenceBinding.sourceStart(), this.referenceBinding.sourceEnd());
        }
        completeCodeAttributeForSyntheticMethod(syntheticMethodBinding, i2, ((SourceTypeBinding) syntheticMethodBinding.declaringClass).scope.referenceCompilationUnit().compilationResult.getLineSeparatorPositions());
        if ((this.produceAttributes & 64) != 0) {
            iGenerateMethodParameters += generateMethodParameters(syntheticMethodBinding);
        }
        byte[] bArr = this.contents;
        bArr[i] = (byte) (iGenerateMethodParameters >> 8);
        bArr[i + 1] = (byte) iGenerateMethodParameters;
    }

    public void generateMethodInfoHeaderForClinit() {
        this.methodCount++;
        if (this.contentsOffset + 10 >= this.contents.length) {
            resizeContents(10);
        }
        byte[] bArr = this.contents;
        int i = this.contentsOffset;
        int i2 = i + 1;
        this.contentsOffset = i2;
        bArr[i] = 0;
        this.contentsOffset = i + 2;
        bArr[i2] = 8;
        int iLiteralIndex = this.constantPool.literalIndex(ConstantPool.Clinit);
        byte[] bArr2 = this.contents;
        int i3 = this.contentsOffset;
        int i4 = i3 + 1;
        this.contentsOffset = i4;
        bArr2[i3] = (byte) (iLiteralIndex >> 8);
        this.contentsOffset = i3 + 2;
        bArr2[i4] = (byte) iLiteralIndex;
        int iLiteralIndex2 = this.constantPool.literalIndex(ConstantPool.ClinitSignature);
        byte[] bArr3 = this.contents;
        int i5 = this.contentsOffset;
        int i6 = i5 + 1;
        this.contentsOffset = i6;
        bArr3[i5] = (byte) (iLiteralIndex2 >> 8);
        int i7 = i5 + 2;
        this.contentsOffset = i7;
        bArr3[i6] = (byte) iLiteralIndex2;
        int i8 = i5 + 3;
        this.contentsOffset = i8;
        bArr3[i7] = 0;
        this.contentsOffset = i5 + 4;
        bArr3[i8] = 1;
    }

    public void generateMissingAbstractMethods(MethodDeclaration[] methodDeclarationArr, CompilationResult compilationResult) {
        if (methodDeclarationArr != null) {
            TypeDeclaration typeDeclaration = this.referenceBinding.scope.referenceContext;
            int iSourceStart = typeDeclaration.sourceStart();
            int iSourceEnd = typeDeclaration.sourceEnd();
            for (MethodDeclaration methodDeclaration : methodDeclarationArr) {
                MethodBinding methodBinding = methodDeclaration.binding;
                String str = new String(methodBinding.readableName());
                CategorizedProblem[] categorizedProblemArr = compilationResult.problems;
                int i = compilationResult.problemCount;
                for (int i2 = 0; i2 < i; i2++) {
                    CategorizedProblem categorizedProblem = categorizedProblemArr[i2];
                    if (categorizedProblem != null && categorizedProblem.getID() == 67109264 && categorizedProblem.getMessage().indexOf(str) != -1 && categorizedProblem.getSourceStart() >= iSourceStart && categorizedProblem.getSourceEnd() <= iSourceEnd) {
                        addMissingAbstractProblemMethod(methodDeclaration, methodBinding, categorizedProblem, compilationResult);
                    }
                }
            }
        }
    }

    private void generateMissingTypesAttribute() {
        int size = this.missingTypes.size();
        int[] iArr = new int[size];
        if (size > 1) {
            Collections.sort(this.missingTypes, new Comparator() { // from class: org.eclipse.jdt.internal.compiler.ClassFile.2
                @Override // java.util.Comparator
                public int compare(Object obj, Object obj2) {
                    return CharOperation.compareTo(((TypeBinding) obj).constantPoolName(), ((TypeBinding) obj2).constantPoolName());
                }
            });
        }
        int i = 0;
        int i2 = 0;
        for (int i3 = 0; i3 < size; i3++) {
            int iLiteralIndexForType = this.constantPool.literalIndexForType(this.missingTypes.get(i3));
            if (i != iLiteralIndexForType) {
                iArr[i2] = iLiteralIndexForType;
                i2++;
                i = iLiteralIndexForType;
            }
        }
        int i4 = i2 * 2;
        int i5 = i4 + 2;
        if (this.contentsOffset + i5 + 6 >= this.contents.length) {
            resizeContents(i4 + 8);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.MissingTypesName);
        byte[] bArr = this.contents;
        int i6 = this.contentsOffset;
        int i7 = i6 + 1;
        this.contentsOffset = i7;
        bArr[i6] = (byte) (iLiteralIndex >> 8);
        int i8 = i6 + 2;
        this.contentsOffset = i8;
        bArr[i7] = (byte) iLiteralIndex;
        int i9 = i6 + 3;
        this.contentsOffset = i9;
        bArr[i8] = (byte) (i5 >> 24);
        int i10 = i6 + 4;
        this.contentsOffset = i10;
        bArr[i9] = (byte) (i5 >> 16);
        int i11 = i6 + 5;
        this.contentsOffset = i11;
        bArr[i10] = (byte) (i5 >> 8);
        int i12 = i6 + 6;
        this.contentsOffset = i12;
        bArr[i11] = (byte) i5;
        int i13 = i6 + 7;
        this.contentsOffset = i13;
        bArr[i12] = (byte) (i2 >> 8);
        this.contentsOffset = i6 + 8;
        bArr[i13] = (byte) i2;
        for (int i14 = 0; i14 < i2; i14++) {
            int i15 = iArr[i14];
            byte[] bArr2 = this.contents;
            int i16 = this.contentsOffset;
            int i17 = i16 + 1;
            this.contentsOffset = i17;
            bArr2[i16] = (byte) (i15 >> 8);
            this.contentsOffset = i16 + 2;
            bArr2[i17] = (byte) i15;
        }
    }

    private boolean jdk16packageInfoAnnotation(long j, long j2) {
        return this.targetJDK <= ClassFileConstants.JDK1_6 && j2 == 8796093022208L && j != 0 && (j & 8796093022208L) == 0;
    }

    private int generateRuntimeAnnotations(Annotation[] annotationArr, long j) {
        int i;
        int i2;
        long annotationTagBits;
        long j2;
        int i3;
        long annotationTagBits2;
        long j3;
        int length = annotationArr.length;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        while (i4 < length) {
            int i7 = length;
            Annotation persistibleAnnotation = annotationArr[i4].getPersistibleAnnotation();
            if (persistibleAnnotation != null) {
                long annotationTagBits3 = persistibleAnnotation.resolvedType != null ? persistibleAnnotation.resolvedType.getAnnotationTagBits() & TagBits.AnnotationTargetMASK : 0L;
                if (annotationTagBits3 == 0 || (annotationTagBits3 & j) != 0 || jdk16packageInfoAnnotation(annotationTagBits3, j)) {
                    if (persistibleAnnotation.isRuntimeInvisible() || persistibleAnnotation.isRuntimeTypeInvisible()) {
                        i5++;
                    } else if (persistibleAnnotation.isRuntimeVisible() || persistibleAnnotation.isRuntimeTypeVisible()) {
                        i6++;
                    }
                }
            }
            i4++;
            length = i7;
        }
        int i8 = this.contentsOffset;
        int i9 = this.constantPool.currentOffset;
        int i10 = this.constantPool.currentIndex;
        if (i5 == 0) {
            i = 0;
        } else {
            if (this.contentsOffset + 10 >= this.contents.length) {
                resizeContents(10);
            }
            int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.RuntimeInvisibleAnnotationsName);
            byte[] bArr = this.contents;
            int i11 = this.contentsOffset;
            int i12 = i11 + 1;
            this.contentsOffset = i12;
            bArr[i11] = (byte) (iLiteralIndex >> 8);
            int i13 = i11 + 2;
            this.contentsOffset = i13;
            bArr[i12] = (byte) iLiteralIndex;
            int i14 = i11 + 6;
            this.contentsOffset = i11 + 8;
            int i15 = 0;
            int i16 = 0;
            while (i15 < length && i5 != 0) {
                Annotation persistibleAnnotation2 = annotationArr[i15].getPersistibleAnnotation();
                if (persistibleAnnotation2 == null) {
                    i3 = i14;
                    i8 = i8;
                } else {
                    i3 = i14;
                    if (persistibleAnnotation2.resolvedType != null) {
                        j3 = 0;
                        annotationTagBits2 = persistibleAnnotation2.resolvedType.getAnnotationTagBits() & TagBits.AnnotationTargetMASK;
                    } else {
                        annotationTagBits2 = 0;
                        j3 = 0;
                    }
                    if ((annotationTagBits2 == j3 || (annotationTagBits2 & j) != j3 || jdk16packageInfoAnnotation(annotationTagBits2, j)) && (persistibleAnnotation2.isRuntimeInvisible() || persistibleAnnotation2.isRuntimeTypeInvisible())) {
                        int i17 = this.contentsOffset;
                        generateAnnotation(persistibleAnnotation2, i17);
                        i5--;
                        if (this.contentsOffset != i17) {
                            i16++;
                        }
                    }
                }
                i15++;
                i14 = i3;
                i8 = i8;
            }
            if (i16 != 0) {
                byte[] bArr2 = this.contents;
                bArr2[i14] = (byte) (i16 >> 8);
                bArr2[i11 + 7] = (byte) i16;
                int i18 = (this.contentsOffset - i13) - 4;
                bArr2[i13] = (byte) (i18 >> 24);
                bArr2[i11 + 3] = (byte) (i18 >> 16);
                bArr2[i11 + 4] = (byte) (i18 >> 8);
                bArr2[i11 + 5] = (byte) i18;
                i = 1;
            } else {
                this.contentsOffset = i8;
                this.constantPool.resetForAttributeName(AttributeNamesConstants.RuntimeInvisibleAnnotationsName, i10, i9);
                i = 0;
            }
        }
        int i19 = this.contentsOffset;
        int i20 = this.constantPool.currentOffset;
        int i21 = this.constantPool.currentIndex;
        if (i6 != 0) {
            if (this.contentsOffset + 10 >= this.contents.length) {
                resizeContents(10);
            }
            int iLiteralIndex2 = this.constantPool.literalIndex(AttributeNamesConstants.RuntimeVisibleAnnotationsName);
            byte[] bArr3 = this.contents;
            int i22 = this.contentsOffset;
            int i23 = i22 + 1;
            this.contentsOffset = i23;
            bArr3[i22] = (byte) (iLiteralIndex2 >> 8);
            int i24 = i22 + 2;
            this.contentsOffset = i24;
            bArr3[i23] = (byte) iLiteralIndex2;
            int i25 = i22 + 6;
            this.contentsOffset = i22 + 8;
            int i26 = i6;
            int i27 = 0;
            int i28 = 0;
            while (i27 < length && i26 != 0) {
                Annotation persistibleAnnotation3 = annotationArr[i27].getPersistibleAnnotation();
                if (persistibleAnnotation3 == null) {
                    i2 = length;
                    i = i;
                } else {
                    i2 = length;
                    if (persistibleAnnotation3.resolvedType != null) {
                        j2 = 0;
                        annotationTagBits = persistibleAnnotation3.resolvedType.getAnnotationTagBits() & TagBits.AnnotationTargetMASK;
                    } else {
                        annotationTagBits = 0;
                        j2 = 0;
                    }
                    if ((annotationTagBits == j2 || (annotationTagBits & j) != j2 || jdk16packageInfoAnnotation(annotationTagBits, j)) && (persistibleAnnotation3.isRuntimeVisible() || persistibleAnnotation3.isRuntimeTypeVisible())) {
                        i26--;
                        int i29 = this.contentsOffset;
                        generateAnnotation(persistibleAnnotation3, i29);
                        if (this.contentsOffset != i29) {
                            i28++;
                        }
                    }
                }
                i27++;
                length = i2;
                i = i;
            }
            if (i28 != 0) {
                byte[] bArr4 = this.contents;
                bArr4[i25] = (byte) (i28 >> 8);
                bArr4[i22 + 7] = (byte) i28;
                int i30 = (this.contentsOffset - i24) - 4;
                bArr4[i24] = (byte) (i30 >> 24);
                bArr4[i22 + 3] = (byte) (i30 >> 16);
                bArr4[i22 + 4] = (byte) (i30 >> 8);
                bArr4[i22 + 5] = (byte) i30;
                return i + 1;
            }
            this.contentsOffset = i19;
            this.constantPool.resetForAttributeName(AttributeNamesConstants.RuntimeVisibleAnnotationsName, i21, i20);
        }
        return i;
    }

    private int generateRuntimeAnnotationsForParameters(Argument[] argumentArr) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Annotation[] annotationArr;
        long annotationTagBits;
        long j;
        long annotationTagBits2;
        int length = argumentArr.length;
        int i6 = 2;
        int i7 = 1;
        byte b = 0;
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, length, 2);
        int i8 = 0;
        int i9 = 0;
        int i10 = 0;
        while (i8 < length) {
            int i11 = length;
            Annotation[] annotationArr2 = argumentArr[i8].annotations;
            if (annotationArr2 != null) {
                for (Annotation annotation : annotationArr2) {
                    Annotation persistibleAnnotation = annotation.getPersistibleAnnotation();
                    if (persistibleAnnotation != null) {
                        if (persistibleAnnotation.resolvedType != null) {
                            annotationTagBits2 = persistibleAnnotation.resolvedType.getAnnotationTagBits() & TagBits.AnnotationTargetMASK;
                            j = 0;
                        } else {
                            j = 0;
                            annotationTagBits2 = 0;
                        }
                        if (annotationTagBits2 == j || (annotationTagBits2 & 549755813888L) != j) {
                            if (persistibleAnnotation.isRuntimeInvisible()) {
                                int[] iArr2 = iArr[i8];
                                iArr2[1] = iArr2[1] + 1;
                                i9++;
                            } else if (persistibleAnnotation.isRuntimeVisible()) {
                                int[] iArr3 = iArr[i8];
                                iArr3[0] = iArr3[0] + 1;
                                i10++;
                            }
                        }
                    }
                }
            }
            i8++;
            i7 = 1;
            b = 0;
            length = i11;
            i6 = 2;
        }
        int i12 = this.contentsOffset;
        if (i9 == 0) {
            i7 = 0;
        } else {
            if (i12 + 7 >= this.contents.length) {
                resizeContents(7);
            }
            int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.RuntimeInvisibleParameterAnnotationsName);
            byte[] bArr = this.contents;
            int i13 = this.contentsOffset;
            int i14 = i13 + 1;
            this.contentsOffset = i14;
            bArr[i13] = (byte) (iLiteralIndex >> 8);
            int i15 = i13 + 2;
            this.contentsOffset = i15;
            bArr[i14] = (byte) iLiteralIndex;
            this.contentsOffset = i13 + 7;
            bArr[i13 + 6] = (byte) length;
            int i16 = b;
            int i17 = i16;
            byte b2 = b;
            while (i16 < length) {
                if (this.contentsOffset + i6 >= this.contents.length) {
                    resizeContents(i6);
                }
                if (i9 == 0) {
                    byte[] bArr2 = this.contents;
                    int i18 = this.contentsOffset;
                    int i19 = i18 + 1;
                    this.contentsOffset = i19;
                    bArr2[i18] = b2;
                    this.contentsOffset = i18 + i6;
                    bArr2[i19] = b2;
                } else {
                    int i20 = iArr[i16][1];
                    int i21 = this.contentsOffset;
                    this.contentsOffset = i21 + 2;
                    if (i20 != 0) {
                        Annotation[] annotationArr3 = argumentArr[i16].annotations;
                        int length2 = annotationArr3.length;
                        int i22 = b2;
                        while (i5 < length2) {
                            Annotation persistibleAnnotation2 = annotationArr3[i5].getPersistibleAnnotation();
                            if (persistibleAnnotation2 == null) {
                                i5 = b2;
                                annotationArr = annotationArr3;
                            } else {
                                annotationArr = annotationArr3;
                                if (persistibleAnnotation2.resolvedType != null) {
                                    i5 = b2;
                                    annotationTagBits = persistibleAnnotation2.resolvedType.getAnnotationTagBits() & TagBits.AnnotationTargetMASK;
                                } else {
                                    i5 = b2;
                                    annotationTagBits = 0;
                                }
                                if ((annotationTagBits == 0 || (annotationTagBits & 549755813888L) != 0) && persistibleAnnotation2.isRuntimeInvisible()) {
                                    int i23 = this.contentsOffset;
                                    generateAnnotation(persistibleAnnotation2, i23);
                                    if (this.contentsOffset != i23) {
                                        i22++;
                                        i17++;
                                    }
                                    i9--;
                                }
                            }
                            annotationArr3 = annotationArr;
                            i5++;
                        }
                        i5 = b2;
                        i4 = i22;
                    } else {
                        i4 = 0;
                    }
                    byte[] bArr3 = this.contents;
                    bArr3[i21] = (byte) (i4 >> 8);
                    bArr3[i21 + 1] = (byte) i4;
                }
                i16++;
                i6 = 2;
                i7 = 1;
                b2 = 0;
            }
            if (i17 != 0) {
                int i24 = (this.contentsOffset - i15) - 4;
                byte[] bArr4 = this.contents;
                bArr4[i15] = (byte) (i24 >> 24);
                bArr4[i13 + 3] = (byte) (i24 >> 16);
                bArr4[i13 + 4] = (byte) (i24 >> 8);
                bArr4[i13 + 5] = (byte) i24;
            } else {
                this.contentsOffset = i12;
                i7 = 0;
            }
        }
        if (i10 == 0) {
            return i7;
        }
        if (this.contentsOffset + 7 >= this.contents.length) {
            resizeContents(7);
        }
        int iLiteralIndex2 = this.constantPool.literalIndex(AttributeNamesConstants.RuntimeVisibleParameterAnnotationsName);
        byte[] bArr5 = this.contents;
        int i25 = this.contentsOffset;
        int i26 = i25 + 1;
        this.contentsOffset = i26;
        bArr5[i25] = (byte) (iLiteralIndex2 >> 8);
        int i27 = i25 + 2;
        this.contentsOffset = i27;
        bArr5[i26] = (byte) iLiteralIndex2;
        this.contentsOffset = i25 + 7;
        bArr5[i25 + 6] = (byte) length;
        int i28 = 0;
        int i29 = 0;
        while (i28 < length) {
            if (this.contentsOffset + 2 >= this.contents.length) {
                resizeContents(2);
            }
            if (i10 == 0) {
                byte[] bArr6 = this.contents;
                int i30 = this.contentsOffset;
                int i31 = i30 + 1;
                this.contentsOffset = i31;
                bArr6[i30] = 0;
                this.contentsOffset = i30 + 2;
                bArr6[i31] = 0;
                i = length;
            } else {
                int i32 = iArr[i28][0];
                int i33 = this.contentsOffset;
                this.contentsOffset = i33 + 2;
                if (i32 != 0) {
                    Annotation[] annotationArr4 = argumentArr[i28].annotations;
                    int length3 = annotationArr4.length;
                    int i34 = 0;
                    int i35 = 0;
                    while (i34 < length3) {
                        Annotation persistibleAnnotation3 = annotationArr4[i34].getPersistibleAnnotation();
                        if (persistibleAnnotation3 == null) {
                            i3 = length;
                        } else {
                            i3 = length;
                            long annotationTagBits3 = persistibleAnnotation3.resolvedType != null ? persistibleAnnotation3.resolvedType.getAnnotationTagBits() & TagBits.AnnotationTargetMASK : 0L;
                            if ((annotationTagBits3 == 0 || (annotationTagBits3 & 549755813888L) != 0) && persistibleAnnotation3.isRuntimeVisible()) {
                                int i36 = this.contentsOffset;
                                generateAnnotation(persistibleAnnotation3, i36);
                                if (this.contentsOffset != i36) {
                                    i35++;
                                    i29++;
                                }
                                i10--;
                            }
                        }
                        i34++;
                        length = i3;
                    }
                    i = length;
                    i2 = i35;
                } else {
                    i = length;
                    i2 = 0;
                }
                byte[] bArr7 = this.contents;
                bArr7[i33] = (byte) (i2 >> 8);
                bArr7[i33 + 1] = (byte) i2;
            }
            i28++;
            length = i;
        }
        if (i29 != 0) {
            int i37 = (this.contentsOffset - i27) - 4;
            byte[] bArr8 = this.contents;
            bArr8[i27] = (byte) (i37 >> 24);
            bArr8[i25 + 3] = (byte) (i37 >> 16);
            bArr8[i25 + 4] = (byte) (i37 >> 8);
            bArr8[i25 + 5] = (byte) i37;
            return i7 + 1;
        }
        this.contentsOffset = i12;
        return i7;
    }

    private int generateRuntimeTypeAnnotations(AnnotationContext[] annotationContextArr, int i, int i2) {
        int i3;
        int length = annotationContextArr.length;
        int i4 = this.contentsOffset;
        int i5 = this.constantPool.currentOffset;
        int i6 = this.constantPool.currentIndex;
        if (i2 == 0) {
            i3 = 0;
        } else {
            if (this.contentsOffset + 10 >= this.contents.length) {
                resizeContents(10);
            }
            int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.RuntimeInvisibleTypeAnnotationsName);
            byte[] bArr = this.contents;
            int i7 = this.contentsOffset;
            int i8 = i7 + 1;
            this.contentsOffset = i8;
            bArr[i7] = (byte) (iLiteralIndex >> 8);
            int i9 = i7 + 2;
            this.contentsOffset = i9;
            bArr[i8] = (byte) iLiteralIndex;
            int i10 = i7 + 6;
            this.contentsOffset = i7 + 8;
            int i11 = i2;
            int i12 = 0;
            for (int i13 = 0; i13 < length && i11 != 0; i13++) {
                AnnotationContext annotationContext = annotationContextArr[i13];
                if ((annotationContext.visibility & 2) != 0) {
                    int i14 = this.contentsOffset;
                    generateTypeAnnotation(annotationContext, i14);
                    i11--;
                    if (this.contentsOffset != i14) {
                        i12++;
                    }
                }
            }
            if (i12 != 0) {
                byte[] bArr2 = this.contents;
                bArr2[i10] = (byte) (i12 >> 8);
                bArr2[i7 + 7] = (byte) i12;
                int i15 = (this.contentsOffset - i9) - 4;
                bArr2[i9] = (byte) (i15 >> 24);
                bArr2[i7 + 3] = (byte) (i15 >> 16);
                bArr2[i7 + 4] = (byte) (i15 >> 8);
                bArr2[i7 + 5] = (byte) i15;
                i3 = 1;
            } else {
                this.contentsOffset = i4;
                this.constantPool.resetForAttributeName(AttributeNamesConstants.RuntimeInvisibleTypeAnnotationsName, i6, i5);
                i3 = 0;
            }
        }
        int i16 = this.contentsOffset;
        int i17 = this.constantPool.currentOffset;
        int i18 = this.constantPool.currentIndex;
        if (i == 0) {
            return i3;
        }
        if (this.contentsOffset + 10 >= this.contents.length) {
            resizeContents(10);
        }
        int iLiteralIndex2 = this.constantPool.literalIndex(AttributeNamesConstants.RuntimeVisibleTypeAnnotationsName);
        byte[] bArr3 = this.contents;
        int i19 = this.contentsOffset;
        int i20 = i19 + 1;
        this.contentsOffset = i20;
        bArr3[i19] = (byte) (iLiteralIndex2 >> 8);
        int i21 = i19 + 2;
        this.contentsOffset = i21;
        bArr3[i20] = (byte) iLiteralIndex2;
        int i22 = i19 + 6;
        this.contentsOffset = i19 + 8;
        int i23 = i;
        int i24 = 0;
        for (int i25 = 0; i25 < length && i23 != 0; i25++) {
            AnnotationContext annotationContext2 = annotationContextArr[i25];
            if ((annotationContext2.visibility & 1) != 0) {
                i23--;
                int i26 = this.contentsOffset;
                generateTypeAnnotation(annotationContext2, i26);
                if (this.contentsOffset != i26) {
                    i24++;
                }
            }
        }
        if (i24 != 0) {
            byte[] bArr4 = this.contents;
            bArr4[i22] = (byte) (i24 >> 8);
            bArr4[i19 + 7] = (byte) i24;
            int i27 = (this.contentsOffset - i21) - 4;
            bArr4[i21] = (byte) (i27 >> 24);
            bArr4[i19 + 3] = (byte) (i27 >> 16);
            bArr4[i19 + 4] = (byte) (i27 >> 8);
            bArr4[i19 + 5] = (byte) i27;
            return i3 + 1;
        }
        this.contentsOffset = i16;
        this.constantPool.resetForAttributeName(AttributeNamesConstants.RuntimeVisibleTypeAnnotationsName, i18, i17);
        return i3;
    }

    /* JADX WARN: Code duplicated, block: B:11:0x0044  */
    private int generateMethodParameters(MethodBinding methodBinding) {
        int iWriteArgumentName;
        Argument[] argumentArr;
        Argument argument;
        int i = this.contentsOffset;
        AbstractMethodDeclaration abstractMethodDeclarationSourceMethod = methodBinding.sourceMethod();
        boolean zIsConstructor = methodBinding.isConstructor();
        TypeBinding[] typeBindingArr = methodBinding.parameters;
        ReferenceBinding referenceBinding = methodBinding.declaringClass;
        int i2 = 4096;
        if (!referenceBinding.isEnum()) {
            iWriteArgumentName = 0;
        } else if (zIsConstructor) {
            iWriteArgumentName = writeArgumentName(ConstantPool.EnumOrdinal, 4096, writeArgumentName(ConstantPool.EnumName, 4096, 0));
        } else if ((methodBinding instanceof SyntheticMethodBinding) && CharOperation.equals(ConstantPool.ValueOf, methodBinding.selector)) {
            iWriteArgumentName = writeArgumentName(ConstantPool.Name, 32768, 0);
            typeBindingArr = Binding.NO_PARAMETERS;
        } else {
            iWriteArgumentName = 0;
        }
        boolean z = zIsConstructor && referenceBinding.isNestedType();
        if (z) {
            boolean z2 = referenceBinding.isAnonymousType() && referenceBinding.superclass().isLocalType();
            boolean z3 = referenceBinding.isAnonymousType() && referenceBinding.superclass().isNestedType();
            boolean z4 = (!referenceBinding.isPrivate() || referenceBinding.isAnonymousType()) && !z2;
            ReferenceBinding[] referenceBindingArrSyntheticEnclosingInstanceTypes = referenceBinding.syntheticEnclosingInstanceTypes();
            if (referenceBindingArrSyntheticEnclosingInstanceTypes != null) {
                int length = referenceBindingArrSyntheticEnclosingInstanceTypes.length;
                int i3 = 0;
                while (i3 < length) {
                    iWriteArgumentName = writeArgumentName(CharOperation.concat(TypeConstants.SYNTHETIC_ENCLOSING_INSTANCE_PREFIX, String.valueOf(i3).toCharArray()), (((z3 ? referenceBinding.superclass().enclosingType().equals(referenceBindingArrSyntheticEnclosingInstanceTypes[i3]) : true) && z4) ? 32768 : i2) | 16, iWriteArgumentName);
                    i3++;
                    i2 = 4096;
                }
            }
            if (methodBinding instanceof SyntheticMethodBinding) {
                SyntheticMethodBinding syntheticMethodBinding = (SyntheticMethodBinding) methodBinding;
                typeBindingArr = syntheticMethodBinding.targetMethod.parameters;
                abstractMethodDeclarationSourceMethod = syntheticMethodBinding.targetMethod.sourceMethod();
            }
        }
        if (typeBindingArr != Binding.NO_PARAMETERS) {
            if (abstractMethodDeclarationSourceMethod != null && abstractMethodDeclarationSourceMethod.arguments != null) {
                argumentArr = abstractMethodDeclarationSourceMethod.arguments;
            } else {
                argumentArr = methodBinding.sourceLambda() != null ? methodBinding.sourceLambda().arguments : null;
            }
            int length2 = typeBindingArr.length;
            int length3 = argumentArr != null ? argumentArr.length : 0;
            for (int i4 = 0; i4 < length2; i4++) {
                if (length3 > i4 && (argument = argumentArr[i4]) != null) {
                    iWriteArgumentName = writeArgumentName(argument.f617name, argument.binding.modifiers, iWriteArgumentName);
                } else {
                    iWriteArgumentName = writeArgumentName(null, 4096, iWriteArgumentName);
                }
            }
        }
        if (z) {
            SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticOuterLocalVariables = referenceBinding.syntheticOuterLocalVariables();
            int length4 = syntheticArgumentBindingArrSyntheticOuterLocalVariables == null ? 0 : syntheticArgumentBindingArrSyntheticOuterLocalVariables.length;
            for (int i5 = 0; i5 < length4; i5++) {
                iWriteArgumentName = writeArgumentName(syntheticArgumentBindingArrSyntheticOuterLocalVariables[i5].f628name, syntheticArgumentBindingArrSyntheticOuterLocalVariables[i5].modifiers | 4096, iWriteArgumentName);
            }
            int length5 = methodBinding.parameters.length;
            for (int length6 = typeBindingArr.length; length6 < length5; length6++) {
                iWriteArgumentName = writeArgumentName(methodBinding.parameters[length6].constantPoolName(), 4096, iWriteArgumentName);
            }
        }
        if (iWriteArgumentName <= 0) {
            return 0;
        }
        int i6 = iWriteArgumentName * 4;
        int i7 = i6 + 1;
        if (this.contentsOffset + 6 + i7 >= this.contents.length) {
            resizeContents(i6 + 7);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.MethodParametersName);
        byte[] bArr = this.contents;
        bArr[i] = (byte) (iLiteralIndex >> 8);
        bArr[i + 1] = (byte) iLiteralIndex;
        bArr[i + 2] = (byte) (i7 >> 24);
        bArr[i + 3] = (byte) (i7 >> 16);
        bArr[i + 4] = (byte) (i7 >> 8);
        bArr[i + 5] = (byte) i7;
        bArr[i + 6] = (byte) iWriteArgumentName;
        return 1;
    }

    private int writeArgumentName(char[] cArr, int i, int i2) {
        int i3;
        if (i2 == 0) {
            this.contentsOffset += 7;
            i3 = 11;
        } else {
            i3 = 4;
        }
        if (this.contentsOffset + i3 > this.contents.length) {
            resizeContents(i3);
        }
        int iLiteralIndex = cArr == null ? 0 : this.constantPool.literalIndex(cArr);
        byte[] bArr = this.contents;
        int i4 = this.contentsOffset;
        int i5 = i4 + 1;
        this.contentsOffset = i5;
        bArr[i4] = (byte) (iLiteralIndex >> 8);
        int i6 = i4 + 2;
        this.contentsOffset = i6;
        bArr[i5] = (byte) iLiteralIndex;
        int i7 = 36880 & i;
        int i8 = i4 + 3;
        this.contentsOffset = i8;
        bArr[i6] = (byte) (i7 >> 8);
        this.contentsOffset = i4 + 4;
        bArr[i8] = (byte) i7;
        return i2 + 1;
    }

    private int generateSignatureAttribute(char[] cArr) {
        int i = this.contentsOffset;
        if (i + 8 >= this.contents.length) {
            resizeContents(8);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.SignatureName);
        byte[] bArr = this.contents;
        bArr[i] = (byte) (iLiteralIndex >> 8);
        bArr[i + 1] = (byte) iLiteralIndex;
        bArr[i + 2] = 0;
        bArr[i + 3] = 0;
        bArr[i + 4] = 0;
        bArr[i + 5] = 2;
        int iLiteralIndex2 = this.constantPool.literalIndex(cArr);
        byte[] bArr2 = this.contents;
        bArr2[i + 6] = (byte) (iLiteralIndex2 >> 8);
        bArr2[i + 7] = (byte) iLiteralIndex2;
        this.contentsOffset = i + 8;
        return 1;
    }

    private int generateSourceAttribute(String str) {
        int i = this.contentsOffset;
        if (i + 8 >= this.contents.length) {
            resizeContents(8);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.SourceName);
        byte[] bArr = this.contents;
        bArr[i] = (byte) (iLiteralIndex >> 8);
        bArr[i + 1] = (byte) iLiteralIndex;
        bArr[i + 2] = 0;
        bArr[i + 3] = 0;
        bArr[i + 4] = 0;
        bArr[i + 5] = 2;
        int iLiteralIndex2 = this.constantPool.literalIndex(str.toCharArray());
        byte[] bArr2 = this.contents;
        bArr2[i + 6] = (byte) (iLiteralIndex2 >> 8);
        bArr2[i + 7] = (byte) iLiteralIndex2;
        this.contentsOffset = i + 8;
        return 1;
    }

    /* JADX WARN: Code duplicated, block: B:91:0x0281  */
    private int generateStackMapAttribute(MethodBinding methodBinding, int i, int i2, int i3, boolean z) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8 = this.contentsOffset;
        StackMapFrameCodeStream stackMapFrameCodeStream = (StackMapFrameCodeStream) this.codeStream;
        stackMapFrameCodeStream.removeFramePosition(i);
        int i9 = 0;
        if (stackMapFrameCodeStream.hasFramePositions()) {
            List listTraverse = traverse(z ? null : methodBinding, i3, this.contents, i2 + 14, i, new HashMap(), z);
            int size = listTraverse.size();
            int i10 = 1;
            if (size <= 1) {
                i4 = 0;
            } else {
                if (i8 + 8 >= this.contents.length) {
                    resizeContents(8);
                }
                int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.StackMapName);
                byte[] bArr = this.contents;
                bArr[i8] = (byte) (iLiteralIndex >> 8);
                int i11 = i8 + 2;
                bArr[i8 + 1] = (byte) iLiteralIndex;
                int i12 = i8 + 6;
                int i13 = 4;
                if (i8 + 10 >= bArr.length) {
                    resizeContents(4);
                }
                int i14 = i8 + 8;
                if (i8 + 10 >= this.contents.length) {
                    resizeContents(2);
                }
                int i15 = 1;
                while (i15 < size) {
                    StackMapFrame stackMapFrame = (StackMapFrame) listTraverse.get(i15);
                    int i16 = stackMapFrame.pc;
                    if (i14 + 5 >= this.contents.length) {
                        resizeContents(5);
                    }
                    byte[] bArr2 = this.contents;
                    bArr2[i14] = (byte) (i16 >> 8);
                    int i17 = i14 + 2;
                    bArr2[i14 + 1] = (byte) i16;
                    int i18 = i14 + 4;
                    int numberOfLocals = stackMapFrame.getNumberOfLocals();
                    int length = stackMapFrame.locals == null ? i9 : stackMapFrame.locals.length;
                    int i19 = i9;
                    int i20 = i19;
                    int i21 = i20;
                    while (i20 < length && i19 < numberOfLocals) {
                        List list = listTraverse;
                        if (i18 + 3 >= this.contents.length) {
                            resizeContents(3);
                        }
                        VerificationTypeInfo verificationTypeInfo = stackMapFrame.locals[i20];
                        if (verificationTypeInfo == null) {
                            i6 = i18 + 1;
                            this.contents[i18] = 0;
                            i7 = size;
                            i12 = i12;
                        } else {
                            switch (verificationTypeInfo.id()) {
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 10:
                                    i6 = i18 + 1;
                                    this.contents[i18] = 1;
                                    i7 = size;
                                    break;
                                case 6:
                                case 11:
                                default:
                                    int i22 = i18 + 1;
                                    i7 = size;
                                    this.contents[i18] = (byte) verificationTypeInfo.tag;
                                    int i23 = verificationTypeInfo.tag;
                                    if (i23 == 7) {
                                        int iLiteralIndexForType = this.constantPool.literalIndexForType(verificationTypeInfo.constantPoolName());
                                        byte[] bArr3 = this.contents;
                                        bArr3[i22] = (byte) (iLiteralIndexForType >> 8);
                                        i6 = i18 + 3;
                                        bArr3[i18 + 2] = (byte) iLiteralIndexForType;
                                    } else if (i23 != 8) {
                                        i6 = i22;
                                    } else {
                                        int i24 = verificationTypeInfo.offset;
                                        byte[] bArr4 = this.contents;
                                        bArr4[i22] = (byte) (i24 >> 8);
                                        bArr4[i18 + 2] = (byte) i24;
                                        i6 = i18 + 3;
                                    }
                                    break;
                                case 7:
                                    i6 = i18 + 1;
                                    this.contents[i18] = 4;
                                    i20++;
                                    i7 = size;
                                    break;
                                case 8:
                                    i6 = i18 + 1;
                                    this.contents[i18] = 3;
                                    i20++;
                                    i7 = size;
                                    break;
                                case 9:
                                    i6 = i18 + 1;
                                    this.contents[i18] = 2;
                                    i7 = size;
                                    break;
                                case 12:
                                    i6 = i18 + 1;
                                    this.contents[i18] = 5;
                                    i7 = size;
                                    break;
                            }
                            i19++;
                        }
                        i18 = i6;
                        i21++;
                        i20++;
                        listTraverse = list;
                        size = i7;
                        i12 = i12;
                    }
                    List list2 = listTraverse;
                    int i25 = size;
                    int i26 = i12;
                    int i27 = i21;
                    if (i18 + 4 >= this.contents.length) {
                        resizeContents(4);
                    }
                    byte[] bArr5 = this.contents;
                    bArr5[i17] = (byte) (i27 >> 8);
                    bArr5[i14 + 3] = (byte) i27;
                    int i28 = stackMapFrame.numberOfStackItems;
                    byte[] bArr6 = this.contents;
                    bArr6[i18] = (byte) (i28 >> 8);
                    bArr6[i18 + 1] = (byte) i28;
                    i14 = i18 + 2;
                    for (int i29 = 0; i29 < i28; i29++) {
                        if (i14 + 3 >= this.contents.length) {
                            resizeContents(3);
                        }
                        VerificationTypeInfo verificationTypeInfo2 = stackMapFrame.stackItems[i29];
                        if (verificationTypeInfo2 == null) {
                            i5 = i14 + 1;
                            this.contents[i14] = 0;
                        } else {
                            switch (verificationTypeInfo2.id()) {
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 10:
                                    this.contents[i14] = 1;
                                    i14++;
                                    break;
                                case 6:
                                case 11:
                                default:
                                    int i30 = i14 + 1;
                                    this.contents[i14] = (byte) verificationTypeInfo2.tag;
                                    int i31 = verificationTypeInfo2.tag;
                                    if (i31 == 7) {
                                        int iLiteralIndexForType2 = this.constantPool.literalIndexForType(verificationTypeInfo2.constantPoolName());
                                        byte[] bArr7 = this.contents;
                                        int i32 = i14 + 2;
                                        bArr7[i30] = (byte) (iLiteralIndexForType2 >> 8);
                                        i14 += 3;
                                        bArr7[i32] = (byte) iLiteralIndexForType2;
                                    } else if (i31 != 8) {
                                        i14 = i30;
                                    } else {
                                        int i33 = verificationTypeInfo2.offset;
                                        byte[] bArr8 = this.contents;
                                        int i34 = i14 + 2;
                                        bArr8[i30] = (byte) (i33 >> 8);
                                        i14 += 3;
                                        bArr8[i34] = (byte) i33;
                                    }
                                    break;
                                case 7:
                                    this.contents[i14] = 4;
                                    i14++;
                                    break;
                                case 8:
                                    this.contents[i14] = 3;
                                    i14++;
                                    break;
                                case 9:
                                    this.contents[i14] = 2;
                                    i14++;
                                    break;
                                case 12:
                                    i5 = i14 + 1;
                                    this.contents[i14] = 5;
                                    break;
                            }
                        }
                        i14 = i5;
                    }
                    i15++;
                    listTraverse = list2;
                    size = i25;
                    i12 = i26;
                    i10 = 1;
                    i9 = 0;
                    i13 = 4;
                }
                int i35 = size - 1;
                if (i35 != 0) {
                    byte[] bArr9 = this.contents;
                    bArr9[i12] = (byte) (i35 >> 8);
                    bArr9[i8 + 7] = (byte) i35;
                    int i36 = (i14 - i11) - i13;
                    bArr9[i11] = (byte) (i36 >> 24);
                    bArr9[i8 + 3] = (byte) (i36 >> 16);
                    bArr9[i8 + 4] = (byte) (i36 >> 8);
                    bArr9[i8 + 5] = (byte) i36;
                    i4 = i10;
                    i8 = i14;
                } else {
                    i4 = 0;
                }
            }
        } else {
            i4 = 0;
        }
        this.contentsOffset = i8;
        return i4;
    }

    /* JADX WARN: Code duplicated, block: B:194:0x0549  */
    /* JADX WARN: Failed to find 'out' block for switch in B:105:0x02af. Please report as an issue. */
    /* JADX WARN: Failed to find 'out' block for switch in B:125:0x0354. Please report as an issue. */
    private int generateStackMapTableAttribute(MethodBinding methodBinding, int i, int i2, int i3, boolean z) {
        int i4;
        int i5;
        char c;
        int i6;
        int i7;
        int i8;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int i14;
        int i15;
        int i16;
        int i17 = this.contentsOffset;
        StackMapFrameCodeStream stackMapFrameCodeStream = (StackMapFrameCodeStream) this.codeStream;
        stackMapFrameCodeStream.removeFramePosition(i);
        if (stackMapFrameCodeStream.hasFramePositions()) {
            List listTraverse = traverse(z ? null : methodBinding, i3, this.contents, i2 + 14, i, new HashMap(), z);
            int size = listTraverse.size();
            int i18 = 1;
            if (size <= 1) {
                i4 = 0;
            } else {
                if (i17 + 8 >= this.contents.length) {
                    resizeContents(8);
                }
                int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.StackMapTableName);
                byte[] bArr = this.contents;
                bArr[i17] = (byte) (iLiteralIndex >> 8);
                int i19 = i17 + 2;
                bArr[i17 + 1] = (byte) iLiteralIndex;
                int i20 = i17 + 6;
                int i21 = 4;
                if (i17 + 10 >= bArr.length) {
                    resizeContents(4);
                }
                int i22 = i17 + 8;
                int i23 = 2;
                if (i17 + 10 >= this.contents.length) {
                    resizeContents(2);
                }
                StackMapFrame stackMapFrame = (StackMapFrame) listTraverse.get(0);
                int i24 = 1;
                while (i24 < size) {
                    StackMapFrame stackMapFrame2 = (StackMapFrame) listTraverse.get(i24);
                    int offsetDelta = stackMapFrame2.getOffsetDelta(stackMapFrame);
                    int frameType = stackMapFrame2.getFrameType(stackMapFrame);
                    if (frameType != 0) {
                        if (frameType != i18) {
                            int i25 = 6;
                            if (frameType != i23) {
                                if (frameType == 3) {
                                    listTraverse = listTraverse;
                                    size = size;
                                    i20 = i20;
                                    if (i22 + 3 >= this.contents.length) {
                                        resizeContents(3);
                                    }
                                    byte[] bArr2 = this.contents;
                                    bArr2[i22] = -5;
                                    int i26 = i22 + 2;
                                    bArr2[i22 + 1] = (byte) (offsetDelta >> 8);
                                    i22 += 3;
                                    bArr2[i26] = (byte) offsetDelta;
                                } else if (frameType == 5) {
                                    listTraverse = listTraverse;
                                    size = size;
                                    i20 = i20;
                                    if (i22 + 4 >= this.contents.length) {
                                        resizeContents(4);
                                    }
                                    int i27 = i22 + 1;
                                    this.contents[i22] = (byte) (offsetDelta + 64);
                                    if (stackMapFrame2.stackItems[0] == null) {
                                        i22 += 2;
                                        this.contents[i27] = 0;
                                    } else {
                                        switch (stackMapFrame2.stackItems[0].id()) {
                                            case 2:
                                            case 3:
                                            case 4:
                                            case 5:
                                            case 10:
                                                i22 += 2;
                                                i10 = 1;
                                                this.contents[i27] = 1;
                                                i7 = i10;
                                                c = '\b';
                                                i5 = 2;
                                                i6 = 4;
                                                break;
                                            case 6:
                                            case 11:
                                            default:
                                                VerificationTypeInfo verificationTypeInfo = stackMapFrame2.stackItems[0];
                                                byte b = (byte) verificationTypeInfo.tag;
                                                i13 = i22 + 2;
                                                this.contents[i27] = b;
                                                if (b == 7) {
                                                    int iLiteralIndexForType = this.constantPool.literalIndexForType(verificationTypeInfo.constantPoolName());
                                                    byte[] bArr3 = this.contents;
                                                    bArr3[i13] = (byte) (iLiteralIndexForType >> 8);
                                                    i13 = i22 + 4;
                                                    bArr3[i22 + 3] = (byte) iLiteralIndexForType;
                                                } else if (b == 8) {
                                                    int i28 = verificationTypeInfo.offset;
                                                    byte[] bArr4 = this.contents;
                                                    int i29 = i22 + 3;
                                                    bArr4[i13] = (byte) (i28 >> 8);
                                                    i22 += 4;
                                                    bArr4[i29] = (byte) i28;
                                                    break;
                                                }
                                                i22 = i13;
                                                break;
                                            case 7:
                                                i22 += 2;
                                                i11 = 4;
                                                this.contents[i27] = 4;
                                                i6 = i11;
                                                i7 = 1;
                                                c = '\b';
                                                i5 = 2;
                                                break;
                                            case 8:
                                                i22 += 2;
                                                this.contents[i27] = 3;
                                                break;
                                            case 9:
                                                i22 += 2;
                                                i12 = 2;
                                                this.contents[i27] = 2;
                                                i5 = i12;
                                                i7 = 1;
                                                c = '\b';
                                                i6 = 4;
                                                break;
                                            case 12:
                                                i22 += 2;
                                                this.contents[i27] = 5;
                                                break;
                                        }
                                    }
                                } else if (frameType == 6) {
                                    listTraverse = listTraverse;
                                    size = size;
                                    i20 = i20;
                                    if (i22 + 6 >= this.contents.length) {
                                        resizeContents(6);
                                    }
                                    byte[] bArr5 = this.contents;
                                    bArr5[i22] = -9;
                                    bArr5[i22 + 1] = (byte) (offsetDelta >> 8);
                                    int i30 = i22 + 3;
                                    bArr5[i22 + 2] = (byte) offsetDelta;
                                    if (stackMapFrame2.stackItems[0] == null) {
                                        i22 += 4;
                                        this.contents[i30] = 0;
                                    } else {
                                        switch (stackMapFrame2.stackItems[0].id()) {
                                            case 2:
                                            case 3:
                                            case 4:
                                            case 5:
                                            case 10:
                                                i22 += 4;
                                                i10 = 1;
                                                this.contents[i30] = 1;
                                                i7 = i10;
                                                c = '\b';
                                                i5 = 2;
                                                i6 = 4;
                                                break;
                                            case 6:
                                            case 11:
                                            default:
                                                VerificationTypeInfo verificationTypeInfo2 = stackMapFrame2.stackItems[0];
                                                byte b2 = (byte) verificationTypeInfo2.tag;
                                                i13 = i22 + 4;
                                                this.contents[i30] = b2;
                                                if (b2 == 7) {
                                                    int iLiteralIndexForType2 = this.constantPool.literalIndexForType(verificationTypeInfo2.constantPoolName());
                                                    byte[] bArr6 = this.contents;
                                                    bArr6[i13] = (byte) (iLiteralIndexForType2 >> 8);
                                                    i13 = i22 + 6;
                                                    bArr6[i22 + 5] = (byte) iLiteralIndexForType2;
                                                } else if (b2 == 8) {
                                                    int i31 = verificationTypeInfo2.offset;
                                                    byte[] bArr7 = this.contents;
                                                    int i32 = i22 + 5;
                                                    bArr7[i13] = (byte) (i31 >> 8);
                                                    i22 += 6;
                                                    bArr7[i32] = (byte) i31;
                                                    break;
                                                }
                                                i22 = i13;
                                                break;
                                            case 7:
                                                i22 += 4;
                                                i11 = 4;
                                                this.contents[i30] = 4;
                                                i6 = i11;
                                                i7 = 1;
                                                c = '\b';
                                                i5 = 2;
                                                break;
                                            case 8:
                                                i22 += 4;
                                                this.contents[i30] = 3;
                                                break;
                                            case 9:
                                                i22 += 4;
                                                i12 = 2;
                                                this.contents[i30] = 2;
                                                i5 = i12;
                                                i7 = 1;
                                                c = '\b';
                                                i6 = 4;
                                                break;
                                            case 12:
                                                i22 += 4;
                                                this.contents[i30] = 5;
                                                break;
                                        }
                                    }
                                } else {
                                    if (i22 + 5 >= this.contents.length) {
                                        resizeContents(5);
                                    }
                                    byte[] bArr8 = this.contents;
                                    bArr8[i22] = -1;
                                    bArr8[i22 + 1] = (byte) (offsetDelta >> 8);
                                    int i33 = i22 + 3;
                                    bArr8[i22 + 2] = (byte) offsetDelta;
                                    int i34 = i22 + 5;
                                    int numberOfLocals = stackMapFrame2.getNumberOfLocals();
                                    int length = stackMapFrame2.locals == null ? 0 : stackMapFrame2.locals.length;
                                    int i35 = 0;
                                    int i36 = 0;
                                    int i37 = 0;
                                    while (i35 < length && i36 < numberOfLocals) {
                                        List list = listTraverse;
                                        if (i34 + 3 >= this.contents.length) {
                                            resizeContents(3);
                                        }
                                        VerificationTypeInfo verificationTypeInfo3 = stackMapFrame2.locals[i35];
                                        if (verificationTypeInfo3 == null) {
                                            i15 = i34 + 1;
                                            this.contents[i34] = 0;
                                            i16 = size;
                                            i20 = i20;
                                        } else {
                                            switch (verificationTypeInfo3.id()) {
                                                case 2:
                                                case 3:
                                                case 4:
                                                case 5:
                                                case 10:
                                                    i15 = i34 + 1;
                                                    this.contents[i34] = 1;
                                                    i16 = size;
                                                    break;
                                                case 6:
                                                case 11:
                                                default:
                                                    int i38 = i34 + 1;
                                                    i16 = size;
                                                    this.contents[i34] = (byte) verificationTypeInfo3.tag;
                                                    int i39 = verificationTypeInfo3.tag;
                                                    if (i39 == 7) {
                                                        int iLiteralIndexForType3 = this.constantPool.literalIndexForType(verificationTypeInfo3.constantPoolName());
                                                        byte[] bArr9 = this.contents;
                                                        bArr9[i38] = (byte) (iLiteralIndexForType3 >> 8);
                                                        i15 = i34 + 3;
                                                        bArr9[i34 + 2] = (byte) iLiteralIndexForType3;
                                                    } else if (i39 != 8) {
                                                        i15 = i38;
                                                    } else {
                                                        int i40 = verificationTypeInfo3.offset;
                                                        byte[] bArr10 = this.contents;
                                                        bArr10[i38] = (byte) (i40 >> 8);
                                                        bArr10[i34 + 2] = (byte) i40;
                                                        i15 = i34 + 3;
                                                    }
                                                    break;
                                                case 7:
                                                    i15 = i34 + 1;
                                                    this.contents[i34] = 4;
                                                    i35++;
                                                    i16 = size;
                                                    break;
                                                case 8:
                                                    i15 = i34 + 1;
                                                    this.contents[i34] = 3;
                                                    i35++;
                                                    i16 = size;
                                                    break;
                                                case 9:
                                                    i15 = i34 + 1;
                                                    this.contents[i34] = 2;
                                                    i16 = size;
                                                    break;
                                                case 12:
                                                    i15 = i34 + 1;
                                                    this.contents[i34] = 5;
                                                    i16 = size;
                                                    break;
                                            }
                                            i36++;
                                        }
                                        i34 = i15;
                                        i37++;
                                        i35++;
                                        listTraverse = list;
                                        size = i16;
                                        i20 = i20;
                                    }
                                    listTraverse = listTraverse;
                                    size = size;
                                    i20 = i20;
                                    int i41 = i37;
                                    if (i34 + 4 >= this.contents.length) {
                                        resizeContents(4);
                                    }
                                    byte[] bArr11 = this.contents;
                                    bArr11[i33] = (byte) (i41 >> 8);
                                    bArr11[i22 + 4] = (byte) i41;
                                    int i42 = stackMapFrame2.numberOfStackItems;
                                    byte[] bArr12 = this.contents;
                                    int i43 = i34 + 1;
                                    bArr12[i34] = (byte) (i42 >> 8);
                                    int i44 = i34 + 2;
                                    bArr12[i43] = (byte) i42;
                                    for (int i45 = 0; i45 < i42; i45++) {
                                        if (i44 + 3 >= this.contents.length) {
                                            resizeContents(3);
                                        }
                                        VerificationTypeInfo verificationTypeInfo4 = stackMapFrame2.stackItems[i45];
                                        if (verificationTypeInfo4 == null) {
                                            i14 = i44 + 1;
                                            this.contents[i44] = 0;
                                        } else {
                                            switch (verificationTypeInfo4.id()) {
                                                case 2:
                                                case 3:
                                                case 4:
                                                case 5:
                                                case 10:
                                                    i14 = i44 + 1;
                                                    this.contents[i44] = 1;
                                                    break;
                                                case 6:
                                                case 11:
                                                default:
                                                    int i46 = i44 + 1;
                                                    this.contents[i44] = (byte) verificationTypeInfo4.tag;
                                                    int i47 = verificationTypeInfo4.tag;
                                                    if (i47 == 7) {
                                                        int iLiteralIndexForType4 = this.constantPool.literalIndexForType(verificationTypeInfo4.constantPoolName());
                                                        byte[] bArr13 = this.contents;
                                                        int i48 = i44 + 2;
                                                        bArr13[i46] = (byte) (iLiteralIndexForType4 >> 8);
                                                        i44 += 3;
                                                        bArr13[i48] = (byte) iLiteralIndexForType4;
                                                    } else if (i47 != 8) {
                                                        i44 = i46;
                                                    } else {
                                                        int i49 = verificationTypeInfo4.offset;
                                                        byte[] bArr14 = this.contents;
                                                        int i50 = i44 + 2;
                                                        bArr14[i46] = (byte) (i49 >> 8);
                                                        i44 += 3;
                                                        bArr14[i50] = (byte) i49;
                                                    }
                                                    break;
                                                case 7:
                                                    i14 = i44 + 1;
                                                    this.contents[i44] = 4;
                                                    break;
                                                case 8:
                                                    i14 = i44 + 1;
                                                    this.contents[i44] = 3;
                                                    break;
                                                case 9:
                                                    i14 = i44 + 1;
                                                    this.contents[i44] = 2;
                                                    break;
                                                case 12:
                                                    i14 = i44 + 1;
                                                    this.contents[i44] = 5;
                                                    break;
                                            }
                                        }
                                        i44 = i14;
                                    }
                                    i22 = i44;
                                }
                                i7 = 1;
                                c = '\b';
                                i5 = 2;
                                i6 = 4;
                            } else {
                                if (i22 + 3 >= this.contents.length) {
                                    resizeContents(3);
                                }
                                int iNumberOfDifferentLocals = stackMapFrame2.numberOfDifferentLocals(stackMapFrame);
                                byte[] bArr15 = this.contents;
                                bArr15[i22] = (byte) (iNumberOfDifferentLocals + 251);
                                int i51 = i22 + 2;
                                bArr15[i22 + 1] = (byte) (offsetDelta >> 8);
                                i22 += 3;
                                bArr15[i51] = (byte) offsetDelta;
                                int indexOfDifferentLocals = stackMapFrame2.getIndexOfDifferentLocals(iNumberOfDifferentLocals);
                                stackMapFrame2.getNumberOfLocals();
                                while (indexOfDifferentLocals < stackMapFrame2.locals.length && iNumberOfDifferentLocals > 0) {
                                    if (i22 + 6 >= this.contents.length) {
                                        resizeContents(i25);
                                    }
                                    VerificationTypeInfo verificationTypeInfo5 = stackMapFrame2.locals[indexOfDifferentLocals];
                                    if (verificationTypeInfo5 == null) {
                                        this.contents[i22] = 0;
                                        i22++;
                                        i9 = 1;
                                    } else {
                                        switch (verificationTypeInfo5.id()) {
                                            case 2:
                                            case 3:
                                            case 4:
                                            case 5:
                                            case 10:
                                                i8 = i22 + 1;
                                                this.contents[i22] = 1;
                                                break;
                                            case 6:
                                            case 11:
                                            default:
                                                int i52 = i22 + 1;
                                                this.contents[i22] = (byte) verificationTypeInfo5.tag;
                                                int i53 = verificationTypeInfo5.tag;
                                                if (i53 == 7) {
                                                    int iLiteralIndexForType5 = this.constantPool.literalIndexForType(verificationTypeInfo5.constantPoolName());
                                                    byte[] bArr16 = this.contents;
                                                    bArr16[i52] = (byte) (iLiteralIndexForType5 >> 8);
                                                    bArr16[i22 + 2] = (byte) iLiteralIndexForType5;
                                                    i8 = i22 + 3;
                                                } else if (i53 != 8) {
                                                    i8 = i52;
                                                } else {
                                                    int i54 = verificationTypeInfo5.offset;
                                                    byte[] bArr17 = this.contents;
                                                    bArr17[i52] = (byte) (i54 >> 8);
                                                    i8 = i22 + 3;
                                                    bArr17[i22 + 2] = (byte) i54;
                                                }
                                                break;
                                            case 7:
                                                i8 = i22 + 1;
                                                this.contents[i22] = 4;
                                                indexOfDifferentLocals++;
                                                break;
                                            case 8:
                                                i8 = i22 + 1;
                                                this.contents[i22] = 3;
                                                indexOfDifferentLocals++;
                                                break;
                                            case 9:
                                                i8 = i22 + 1;
                                                this.contents[i22] = 2;
                                                break;
                                            case 12:
                                                i8 = i22 + 1;
                                                this.contents[i22] = 5;
                                                break;
                                        }
                                        iNumberOfDifferentLocals--;
                                        i22 = i8;
                                        i9 = 1;
                                    }
                                    indexOfDifferentLocals += i9;
                                    i25 = 6;
                                }
                                c = '\b';
                                i5 = 2;
                                i6 = 4;
                            }
                        } else {
                            i5 = i23;
                            c = '\b';
                            i6 = 4;
                            if (i22 + 3 >= this.contents.length) {
                                resizeContents(3);
                            }
                            int i55 = -stackMapFrame2.numberOfDifferentLocals(stackMapFrame);
                            byte[] bArr18 = this.contents;
                            bArr18[i22] = (byte) (251 - i55);
                            int i56 = i22 + 2;
                            bArr18[i22 + 1] = (byte) (offsetDelta >> 8);
                            i22 += 3;
                            bArr18[i56] = (byte) offsetDelta;
                        }
                        i7 = 1;
                    } else {
                        listTraverse = listTraverse;
                        size = size;
                        i20 = i20;
                        i5 = i23;
                        c = '\b';
                        i6 = 4;
                        int i57 = i22 + 1;
                        if (i57 >= this.contents.length) {
                            i7 = 1;
                            resizeContents(1);
                        } else {
                            i7 = 1;
                        }
                        this.contents[i22] = (byte) offsetDelta;
                        i22 = i57;
                    }
                    i24++;
                    i18 = i7;
                    stackMapFrame = stackMapFrame2;
                    listTraverse = listTraverse;
                    size = size;
                    i20 = i20;
                    int i58 = i6;
                    i23 = i5;
                    i21 = i58;
                }
                int i59 = size - 1;
                if (i59 != 0) {
                    byte[] bArr19 = this.contents;
                    bArr19[i20] = (byte) (i59 >> 8);
                    bArr19[i17 + 7] = (byte) i59;
                    int i60 = (i22 - i19) - i21;
                    bArr19[i19] = (byte) (i60 >> 24);
                    bArr19[i17 + 3] = (byte) (i60 >> 16);
                    bArr19[i17 + 4] = (byte) (i60 >> 8);
                    bArr19[i17 + 5] = (byte) i60;
                    i4 = i18;
                    i17 = i22;
                } else {
                    i4 = 0;
                }
            }
        } else {
            i4 = 0;
        }
        this.contentsOffset = i17;
        return i4;
    }

    private int generateSyntheticAttribute() {
        int i = this.contentsOffset;
        if (i + 6 >= this.contents.length) {
            resizeContents(6);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.SyntheticName);
        byte[] bArr = this.contents;
        bArr[i] = (byte) (iLiteralIndex >> 8);
        bArr[i + 1] = (byte) iLiteralIndex;
        bArr[i + 2] = 0;
        bArr[i + 3] = 0;
        bArr[i + 4] = 0;
        bArr[i + 5] = 0;
        this.contentsOffset = i + 6;
        return 1;
    }

    private void generateTypeAnnotation(AnnotationContext annotationContext, int i) {
        Annotation persistibleAnnotation = annotationContext.annotation.getPersistibleAnnotation();
        if (persistibleAnnotation == null || persistibleAnnotation.resolvedType == null) {
            return;
        }
        int i2 = annotationContext.targetType;
        int[] locations = Annotation.getLocations(annotationContext.typeReference, annotationContext.annotation);
        if (this.contentsOffset + 5 >= this.contents.length) {
            resizeContents(5);
        }
        byte[] bArr = this.contents;
        int i3 = this.contentsOffset;
        this.contentsOffset = i3 + 1;
        bArr[i3] = (byte) i2;
        dumpTargetTypeContents(i2, annotationContext);
        dumpLocations(locations);
        generateAnnotation(persistibleAnnotation, i);
    }

    private int generateTypeAnnotationAttributeForTypeDeclaration() {
        TypeDeclaration typeDeclaration = this.referenceBinding.scope.referenceContext;
        if ((typeDeclaration.bits & 1048576) == 0) {
            return 0;
        }
        TypeReference typeReference = typeDeclaration.superclass;
        ArrayList arrayList = new ArrayList();
        if (typeReference != null && (typeReference.bits & 1048576) != 0) {
            typeReference.getAllAnnotationContexts(16, -1, arrayList);
        }
        TypeReference[] typeReferenceArr = typeDeclaration.superInterfaces;
        if (typeReferenceArr != null) {
            for (int i = 0; i < typeReferenceArr.length; i++) {
                TypeReference typeReference2 = typeReferenceArr[i];
                if ((typeReference2.bits & 1048576) != 0) {
                    typeReference2.getAllAnnotationContexts(16, i, arrayList);
                }
            }
        }
        TypeParameter[] typeParameterArr = typeDeclaration.typeParameters;
        if (typeParameterArr != null) {
            int length = typeParameterArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                TypeParameter typeParameter = typeParameterArr[i2];
                if ((typeParameter.bits & 1048576) != 0) {
                    typeParameter.getAllAnnotationContexts(0, i2, arrayList);
                }
            }
        }
        int size = arrayList.size();
        if (size == 0) {
            return 0;
        }
        AnnotationContext[] annotationContextArr = new AnnotationContext[size];
        arrayList.toArray(annotationContextArr);
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < size; i5++) {
            AnnotationContext annotationContext = annotationContextArr[i5];
            if ((annotationContext.visibility & 2) != 0) {
                i4++;
                arrayList.add(annotationContext);
            } else {
                i3++;
                arrayList.add(annotationContext);
            }
        }
        return generateRuntimeTypeAnnotations(annotationContextArr, i3, i4);
    }

    private int generateVarargsAttribute() {
        int i = this.contentsOffset;
        if (i + 6 >= this.contents.length) {
            resizeContents(6);
        }
        int iLiteralIndex = this.constantPool.literalIndex(AttributeNamesConstants.VarargsName);
        byte[] bArr = this.contents;
        bArr[i] = (byte) (iLiteralIndex >> 8);
        bArr[i + 1] = (byte) iLiteralIndex;
        bArr[i + 2] = 0;
        bArr[i + 3] = 0;
        bArr[i + 4] = 0;
        bArr[i + 5] = 0;
        this.contentsOffset = i + 6;
        return 1;
    }

    public byte[] getBytes() {
        if (this.bytes == null) {
            int i = this.headerOffset;
            byte[] bArr = new byte[this.contentsOffset + i];
            this.bytes = bArr;
            System.arraycopy(this.header, 0, bArr, 0, i);
            System.arraycopy(this.contents, 0, this.bytes, this.headerOffset, this.contentsOffset);
        }
        return this.bytes;
    }

    public char[][] getCompoundName() {
        return CharOperation.splitOn('/', fileName());
    }

    private int getParametersCount(char[] cArr) {
        int iIndexOf = CharOperation.indexOf('(', cArr) + 1;
        int i = 0;
        if (cArr[iIndexOf] == ')') {
            return 0;
        }
        while (true) {
            char c = cArr[iIndexOf];
            if (c == ')') {
                return i;
            }
            if (c != 'F') {
                if (c == 'L') {
                    iIndexOf = CharOperation.indexOf(';', cArr, iIndexOf + 1);
                } else if (c != 'S' && c != 'I' && c != 'J' && c != 'Z') {
                    if (c == '[') {
                        iIndexOf = scanType(cArr, iIndexOf + 1);
                    } else {
                        switch (c) {
                            case 'B':
                            case 'C':
                            case 'D':
                                break;
                            default:
                                throw new IllegalArgumentException("Invalid starting type character : " + c);
                        }
                    }
                }
            }
            i++;
            iIndexOf++;
        }
    }

    private char[] getReturnType(char[] cArr) {
        return CharOperation.subarray(cArr, CharOperation.lastIndexOf(')', cArr) + 1, cArr.length);
    }

    private final int i4At(byte[] bArr, int i, int i2) {
        int i3 = i + i2;
        int i4 = i3 + 2;
        return ((bArr[i3] & 255) << 24) + ((bArr[i3 + 1] & 255) << 16) + ((bArr[i4] & 255) << 8) + (bArr[i3 + 3] & 255);
    }

    protected void initByteArrays() {
        int length = this.referenceBinding.methods().length + this.referenceBinding.fields().length;
        this.header = new byte[1500];
        this.contents = new byte[length < 15 ? 400 : 1500];
    }

    public void initialize(SourceTypeBinding sourceTypeBinding, ClassFile classFile, boolean z) {
        int iLiteralIndexForType;
        byte[] bArr = this.header;
        int i = this.headerOffset;
        int i2 = i + 1;
        this.headerOffset = i2;
        bArr[i] = -54;
        int i3 = i + 2;
        this.headerOffset = i3;
        bArr[i2] = -2;
        int i4 = i + 3;
        this.headerOffset = i4;
        bArr[i3] = Opcodes.OPC_invokedynamic;
        int i5 = i + 4;
        this.headerOffset = i5;
        bArr[i4] = Opcodes.OPC_arraylength;
        long j = this.targetJDK;
        int i6 = i + 5;
        this.headerOffset = i6;
        bArr[i5] = (byte) (j >> 8);
        int i7 = i + 6;
        this.headerOffset = i7;
        bArr[i6] = (byte) j;
        int i8 = i + 7;
        this.headerOffset = i8;
        bArr[i7] = (byte) (j >> 24);
        int i9 = i + 8;
        this.headerOffset = i9;
        bArr[i8] = (byte) (j >> 16);
        this.constantPoolOffset = i9;
        this.headerOffset = i + 10;
        this.constantPool.initialize(this);
        int accessFlags = sourceTypeBinding.getAccessFlags();
        if (sourceTypeBinding.isPrivate()) {
            accessFlags &= -2;
        }
        if (sourceTypeBinding.isProtected()) {
            accessFlags |= 1;
        }
        int i10 = accessFlags & (-2351);
        if (!sourceTypeBinding.isInterface()) {
            i10 |= 32;
        }
        if (sourceTypeBinding.isAnonymousType()) {
            i10 &= -17;
        }
        if ((i10 & 1040) == 1040) {
            i10 &= -1041;
        }
        this.enclosingClassFile = classFile;
        byte[] bArr2 = this.contents;
        int i11 = this.contentsOffset;
        int i12 = i11 + 1;
        this.contentsOffset = i12;
        bArr2[i11] = (byte) (i10 >> 8);
        this.contentsOffset = i11 + 2;
        bArr2[i12] = (byte) i10;
        int iLiteralIndexForType2 = this.constantPool.literalIndexForType(sourceTypeBinding);
        byte[] bArr3 = this.contents;
        int i13 = this.contentsOffset;
        int i14 = i13 + 1;
        this.contentsOffset = i14;
        bArr3[i13] = (byte) (iLiteralIndexForType2 >> 8);
        this.contentsOffset = i13 + 2;
        bArr3[i14] = (byte) iLiteralIndexForType2;
        long j2 = 0;
        int i15 = 0;
        if (sourceTypeBinding.isInterface()) {
            iLiteralIndexForType = this.constantPool.literalIndexForType(ConstantPool.JavaLangObjectConstantPoolName);
        } else if (sourceTypeBinding.superclass == null) {
            iLiteralIndexForType = 0;
        } else if ((sourceTypeBinding.superclass.tagBits & 128) != 0) {
            iLiteralIndexForType = this.constantPool.literalIndexForType(ConstantPool.JavaLangObjectConstantPoolName);
        } else {
            iLiteralIndexForType = this.constantPool.literalIndexForType(sourceTypeBinding.superclass);
        }
        byte[] bArr4 = this.contents;
        int i16 = this.contentsOffset;
        int i17 = i16 + 1;
        this.contentsOffset = i17;
        bArr4[i16] = (byte) (iLiteralIndexForType >> 8);
        this.contentsOffset = i16 + 2;
        bArr4[i17] = (byte) iLiteralIndexForType;
        ReferenceBinding[] referenceBindingArrSuperInterfaces = sourceTypeBinding.superInterfaces();
        int length = referenceBindingArrSuperInterfaces.length;
        int i18 = this.contentsOffset;
        this.contentsOffset = i18 + 2;
        int i19 = 0;
        while (i15 < length) {
            ReferenceBinding referenceBinding = referenceBindingArrSuperInterfaces[i15];
            if ((referenceBinding.tagBits & 128) == j2) {
                i19++;
                int iLiteralIndexForType3 = this.constantPool.literalIndexForType(referenceBinding);
                byte[] bArr5 = this.contents;
                int i20 = this.contentsOffset;
                int i21 = i20 + 1;
                this.contentsOffset = i21;
                bArr5[i20] = (byte) (iLiteralIndexForType3 >> 8);
                this.contentsOffset = i20 + 2;
                bArr5[i21] = (byte) iLiteralIndexForType3;
            }
            i15++;
            j2 = 0;
        }
        byte[] bArr6 = this.contents;
        bArr6[i18] = (byte) (i19 >> 8);
        bArr6[i18 + 1] = (byte) i19;
        this.creatingProblemType = z;
        this.codeStream.maxFieldCount = sourceTypeBinding.scope.outerMostClassScope().referenceType().maxFieldCount;
    }

    private void initializeDefaultLocals(StackMapFrame stackMapFrame, MethodBinding methodBinding, int i, int i2) {
        int i3;
        if (i != 0) {
            boolean zIsConstructor = methodBinding.isConstructor();
            if (zIsConstructor || !methodBinding.isStatic()) {
                LocalVariableBinding localVariableBinding = new LocalVariableBinding(ConstantPool.This, (TypeBinding) methodBinding.declaringClass, 0, false);
                localVariableBinding.resolvedPosition = 0;
                this.codeStream.record(localVariableBinding);
                localVariableBinding.recordInitializationStartPC(0);
                localVariableBinding.recordInitializationEndPC(i2);
                stackMapFrame.putLocal(0, new VerificationTypeInfo(zIsConstructor ? 6 : 7, methodBinding.declaringClass));
                i3 = 1;
            } else {
                i3 = 0;
            }
            if (zIsConstructor) {
                if (methodBinding.declaringClass.isEnum()) {
                    LocalVariableBinding localVariableBinding2 = new LocalVariableBinding(" name".toCharArray(), (TypeBinding) this.referenceBinding.scope.getJavaLangString(), 0, false);
                    localVariableBinding2.resolvedPosition = i3;
                    this.codeStream.record(localVariableBinding2);
                    localVariableBinding2.recordInitializationStartPC(0);
                    localVariableBinding2.recordInitializationEndPC(i2);
                    stackMapFrame.putLocal(i3, new VerificationTypeInfo(11, ConstantPool.JavaLangStringConstantPoolName));
                    int i4 = i3 + 1;
                    LocalVariableBinding localVariableBinding3 = new LocalVariableBinding(" ordinal".toCharArray(), (TypeBinding) TypeBinding.INT, 0, false);
                    localVariableBinding3.resolvedPosition = i4;
                    this.codeStream.record(localVariableBinding3);
                    localVariableBinding3.recordInitializationStartPC(0);
                    localVariableBinding3.recordInitializationEndPC(i2);
                    stackMapFrame.putLocal(i4, new VerificationTypeInfo(TypeBinding.INT));
                    i3 += 2;
                }
                if (methodBinding.declaringClass.isNestedType()) {
                    ReferenceBinding[] referenceBindingArrSyntheticEnclosingInstanceTypes = methodBinding.declaringClass.syntheticEnclosingInstanceTypes();
                    if (referenceBindingArrSyntheticEnclosingInstanceTypes != null) {
                        int length = referenceBindingArrSyntheticEnclosingInstanceTypes.length;
                        for (int i5 = 0; i5 < length; i5++) {
                            LocalVariableBinding localVariableBinding4 = new LocalVariableBinding((" enclosingType" + i5).toCharArray(), (TypeBinding) referenceBindingArrSyntheticEnclosingInstanceTypes[i5], 0, false);
                            localVariableBinding4.resolvedPosition = i3;
                            this.codeStream.record(localVariableBinding4);
                            localVariableBinding4.recordInitializationStartPC(0);
                            localVariableBinding4.recordInitializationEndPC(i2);
                            stackMapFrame.putLocal(i3, new VerificationTypeInfo(referenceBindingArrSyntheticEnclosingInstanceTypes[i5]));
                            i3++;
                        }
                    }
                    TypeBinding[] typeBindingArr = methodBinding.parameters;
                    if (typeBindingArr != null) {
                        for (TypeBinding typeBinding : typeBindingArr) {
                            stackMapFrame.putLocal(i3, new VerificationTypeInfo(typeBinding));
                            int i6 = typeBinding.f626id;
                            i3 = (i6 == 7 || i6 == 8) ? i3 + 2 : i3 + 1;
                        }
                    }
                    SyntheticArgumentBinding[] syntheticArgumentBindingArrSyntheticOuterLocalVariables = methodBinding.declaringClass.syntheticOuterLocalVariables();
                    if (syntheticArgumentBindingArrSyntheticOuterLocalVariables != null) {
                        int length2 = syntheticArgumentBindingArrSyntheticOuterLocalVariables.length;
                        for (int i7 = 0; i7 < length2; i7++) {
                            TypeBinding typeBinding2 = syntheticArgumentBindingArrSyntheticOuterLocalVariables[i7].type;
                            LocalVariableBinding localVariableBinding5 = new LocalVariableBinding((" synthetic" + i7).toCharArray(), typeBinding2, 0, false);
                            localVariableBinding5.resolvedPosition = i3;
                            this.codeStream.record(localVariableBinding5);
                            localVariableBinding5.recordInitializationStartPC(0);
                            localVariableBinding5.recordInitializationEndPC(i2);
                            stackMapFrame.putLocal(i3, new VerificationTypeInfo(typeBinding2));
                            int i8 = typeBinding2.f626id;
                            i3 = (i8 == 7 || i8 == 8) ? i3 + 2 : i3 + 1;
                        }
                        return;
                    }
                    return;
                }
                TypeBinding[] typeBindingArr2 = methodBinding.parameters;
                if (typeBindingArr2 != null) {
                    for (TypeBinding typeBinding3 : typeBindingArr2) {
                        stackMapFrame.putLocal(i3, new VerificationTypeInfo(typeBinding3));
                        int i9 = typeBinding3.f626id;
                        i3 = (i9 == 7 || i9 == 8) ? i3 + 2 : i3 + 1;
                    }
                    return;
                }
                return;
            }
            TypeBinding[] typeBindingArr3 = methodBinding.parameters;
            if (typeBindingArr3 != null) {
                int length3 = typeBindingArr3.length;
                for (int i10 = 0; i10 < length3; i10++) {
                    TypeBinding typeBinding4 = typeBindingArr3[i10];
                    LocalVariableBinding localVariableBinding6 = new LocalVariableBinding((" synthetic" + i10).toCharArray(), typeBinding4, 0, true);
                    localVariableBinding6.resolvedPosition = i10;
                    this.codeStream.record(localVariableBinding6);
                    localVariableBinding6.recordInitializationStartPC(0);
                    localVariableBinding6.recordInitializationEndPC(i2);
                    stackMapFrame.putLocal(i3, new VerificationTypeInfo(typeBinding4));
                    int i11 = typeBinding4.f626id;
                    i3 = (i11 == 7 || i11 == 8) ? i3 + 2 : i3 + 1;
                }
            }
        }
    }

    private void initializeLocals(boolean z, int i, StackMapFrame stackMapFrame) {
        VerificationTypeInfo[] verificationTypeInfoArr = stackMapFrame.locals;
        int length = verificationTypeInfoArr.length;
        for (int i2 = !z ? 1 : 0; i2 < length; i2++) {
            verificationTypeInfoArr[i2] = null;
        }
        int i3 = this.codeStream.allLocalsCounter;
        for (int i4 = 0; i4 < i3; i4++) {
            LocalVariableBinding localVariableBinding = this.codeStream.locals[i4];
            if (localVariableBinding != null) {
                int i5 = localVariableBinding.resolvedPosition;
                TypeBinding typeBinding = localVariableBinding.type;
                for (int i6 = 0; i6 < localVariableBinding.initializationCount; i6++) {
                    int i7 = i6 << 1;
                    int i8 = localVariableBinding.initializationPCs[i7];
                    int i9 = localVariableBinding.initializationPCs[i7 + 1];
                    if (i >= i8 && i < i9) {
                        if (stackMapFrame.locals[i5] != null) {
                            break;
                        }
                        stackMapFrame.locals[i5] = new VerificationTypeInfo(typeBinding);
                        break;
                    }
                }
            }
        }
    }

    public ClassFile outerMostEnclosingClassFile() {
        ClassFile classFile = this;
        while (true) {
            ClassFile classFile2 = classFile.enclosingClassFile;
            if (classFile2 == null) {
                return classFile;
            }
            classFile = classFile2;
        }
    }

    public void recordInnerClasses(TypeBinding typeBinding) {
        recordInnerClasses(typeBinding, false);
    }

    public void recordInnerClasses(TypeBinding typeBinding, boolean z) {
        if (this.innerClassesBindings == null) {
            this.innerClassesBindings = new HashMap(5);
        }
        ReferenceBinding referenceBinding = (ReferenceBinding) typeBinding;
        this.innerClassesBindings.put(referenceBinding.erasure().unannotated(), Boolean.valueOf(z));
        for (ReferenceBinding referenceBindingEnclosingType = referenceBinding.enclosingType(); referenceBindingEnclosingType != null && referenceBindingEnclosingType.isNestedType(); referenceBindingEnclosingType = referenceBindingEnclosingType.enclosingType()) {
            this.innerClassesBindings.put(referenceBindingEnclosingType.erasure().unannotated(), Boolean.valueOf(z));
        }
    }

    public int recordBootstrapMethod(FunctionalExpression functionalExpression) {
        if (this.bootstrapMethods == null) {
            this.bootstrapMethods = new ArrayList();
        }
        if (functionalExpression instanceof ReferenceExpression) {
            for (int i = 0; i < this.bootstrapMethods.size(); i++) {
                FunctionalExpression functionalExpression2 = (FunctionalExpression) this.bootstrapMethods.get(i);
                if (functionalExpression2.binding == functionalExpression.binding && TypeBinding.equalsEquals(functionalExpression2.expectedType(), functionalExpression.expectedType())) {
                    functionalExpression.bootstrapMethodNumber = i;
                    return i;
                }
            }
        }
        this.bootstrapMethods.add(functionalExpression);
        int size = this.bootstrapMethods.size() - 1;
        functionalExpression.bootstrapMethodNumber = size;
        return size;
    }

    public void reset(SourceTypeBinding sourceTypeBinding) {
        CompilerOptions compilerOptions = sourceTypeBinding.scope.compilerOptions();
        this.referenceBinding = sourceTypeBinding;
        this.isNestedType = sourceTypeBinding.isNestedType();
        this.targetJDK = compilerOptions.targetJDK;
        int i = compilerOptions.produceDebugAttributes;
        this.produceAttributes = i;
        long j = this.targetJDK;
        if (j >= ClassFileConstants.JDK1_6) {
            this.produceAttributes = i | 8;
            if (j >= ClassFileConstants.JDK1_8) {
                this.produceAttributes = i | 40;
                if (compilerOptions.produceMethodParameters) {
                    this.produceAttributes |= 64;
                }
            }
        } else if (j == ClassFileConstants.CLDC_1_1) {
            this.targetJDK = ClassFileConstants.JDK1_1;
            this.produceAttributes = i | 16;
        }
        this.bytes = null;
        this.constantPool.reset();
        this.codeStream.reset(this);
        this.constantPoolOffset = 0;
        this.contentsOffset = 0;
        this.creatingProblemType = false;
        this.enclosingClassFile = null;
        this.headerOffset = 0;
        this.methodCount = 0;
        this.methodCountOffset = 0;
        Map<TypeBinding, Boolean> map = this.innerClassesBindings;
        if (map != null) {
            map.clear();
        }
        List list = this.bootstrapMethods;
        if (list != null) {
            list.clear();
        }
        this.missingTypes = null;
        this.visitedTypes = null;
    }

    private final void resizeContents(int i) {
        byte[] bArr = this.contents;
        int length = bArr.length;
        if (length >= i) {
            i = length;
        }
        byte[] bArr2 = new byte[i + length];
        this.contents = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, length);
    }

    private VerificationTypeInfo retrieveLocal(int i, int i2) {
        int i3 = this.codeStream.allLocalsCounter;
        for (int i4 = 0; i4 < i3; i4++) {
            LocalVariableBinding localVariableBinding = this.codeStream.locals[i4];
            if (localVariableBinding != null && i2 == localVariableBinding.resolvedPosition) {
                for (int i5 = 0; i5 < localVariableBinding.initializationCount; i5++) {
                    int i6 = i5 << 1;
                    int i7 = localVariableBinding.initializationPCs[i6];
                    int i8 = localVariableBinding.initializationPCs[i6 + 1];
                    if (i >= i7 && i < i8) {
                        return new VerificationTypeInfo(localVariableBinding.type);
                    }
                }
            }
        }
        return null;
    }

    private int scanType(char[] cArr, int i) {
        char c = cArr[i];
        if (c != 'F') {
            if (c == 'L') {
                return CharOperation.indexOf(';', cArr, i + 1);
            }
            if (c != 'S' && c != 'I' && c != 'J' && c != 'Z') {
                if (c == '[') {
                    return scanType(cArr, i + 1);
                }
                switch (c) {
                    case 'B':
                    case 'C':
                    case 'D':
                        break;
                    default:
                        throw new IllegalArgumentException();
                }
            }
        }
        return i;
    }

    public void setForMethodInfos() {
        int i = this.contentsOffset;
        this.methodCountOffset = i;
        this.contentsOffset = i + 2;
    }

    private List filterFakeFrames(Set set, Map map, int i) {
        set.remove(Integer.valueOf(i));
        ArrayList arrayList = new ArrayList();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            StackMapFrame stackMapFrame = (StackMapFrame) map.get((Integer) it.next());
            if (stackMapFrame != null) {
                arrayList.add(stackMapFrame);
            }
        }
        Collections.sort(arrayList, new Comparator() { // from class: org.eclipse.jdt.internal.compiler.ClassFile.3
            @Override // java.util.Comparator
            public int compare(Object obj, Object obj2) {
                return ((StackMapFrame) obj).pc - ((StackMapFrame) obj2).pc;
            }
        });
        return arrayList;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code duplicated, block: B:104:0x0228  */
    /* JADX WARN: Failed to find 'out' block for switch in B:98:0x01af. Please report as an issue. */
    public List traverse(MethodBinding methodBinding, int i, byte[] bArr, int i2, int i3, Map map, boolean z) {
        int[] iArr;
        int i4;
        int i5;
        ClassFile classFile;
        int i6;
        Map map2;
        int i7;
        int i8;
        boolean z2;
        boolean z3;
        boolean z4;
        char[] cArr;
        int i9;
        char[] cArr2;
        boolean z5;
        boolean z6;
        boolean z7;
        boolean z8;
        ClassFile classFile2 = this;
        byte[] bArr2 = bArr;
        int i10 = i3;
        HashSet hashSet = new HashSet();
        StackMapFrameCodeStream stackMapFrameCodeStream = (StackMapFrameCodeStream) classFile2.codeStream;
        int[] framePositions = stackMapFrameCodeStream.getFramePositions();
        int[] iArr2 = classFile2.constantPool.offsets;
        byte[] bArr3 = classFile2.constantPool.poolContent;
        int length = framePositions.length;
        int i11 = framePositions[0];
        StackMapFrameCodeStream.StackDepthMarker[] stackDepthMarkers = stackMapFrameCodeStream.getStackDepthMarkers();
        int length2 = stackDepthMarkers == null ? 0 : stackDepthMarkers.length;
        boolean z9 = length2 != 0;
        StackMapFrameCodeStream.StackDepthMarker stackDepthMarker = z9 ? stackDepthMarkers[0] : null;
        StackMapFrameCodeStream.StackMarker[] stackMarkers = stackMapFrameCodeStream.getStackMarkers();
        int length3 = stackMarkers != null ? stackMarkers.length : 0;
        boolean z10 = length3 != 0;
        StackMapFrameCodeStream.StackMarker stackMarker = z10 ? stackMarkers[0] : null;
        StackMapFrameCodeStream.ExceptionMarker[] exceptionMarkers = stackMapFrameCodeStream.getExceptionMarkers();
        int length4 = exceptionMarkers == null ? 0 : exceptionMarkers.length;
        boolean z11 = length4 != 0;
        StackMapFrameCodeStream.ExceptionMarker exceptionMarker = z11 ? exceptionMarkers[0] : null;
        StackMapFrame stackMapFrame = new StackMapFrame(i);
        if (!z) {
            classFile2.initializeDefaultLocals(stackMapFrame, methodBinding, i, i10);
        }
        stackMapFrame.pc = -1;
        classFile2.add(map, stackMapFrame.duplicate());
        classFile2.addRealJumpTarget(hashSet, -1);
        int i12 = classFile2.codeStream.exceptionLabelsCounter;
        int i13 = 0;
        while (i13 < i12) {
            StackMapFrameCodeStream.ExceptionMarker[] exceptionMarkerArr = exceptionMarkers;
            int[] iArr3 = framePositions;
            int i14 = length;
            int i15 = length4;
            byte[] bArr4 = bArr3;
            HashSet hashSet2 = hashSet;
            int i16 = length3;
            int i17 = i10;
            byte[] bArr5 = bArr2;
            ClassFile classFile3 = classFile2;
            int i18 = i12;
            ExceptionLabel exceptionLabel = classFile3.codeStream.exceptionLabels[i13];
            if (exceptionLabel != null) {
                classFile3.addRealJumpTarget(hashSet2, exceptionLabel.position);
            }
            i13++;
            i10 = i17;
            hashSet = hashSet2;
            length3 = i16;
            length4 = i15;
            framePositions = iArr3;
            length = i14;
            bArr3 = bArr4;
            exceptionMarkers = exceptionMarkerArr;
            i12 = i18;
            classFile2 = classFile3;
            bArr2 = bArr5;
        }
        int i19 = i2;
        HashSet hashSet3 = hashSet;
        StackMapFrameCodeStream.StackDepthMarker stackDepthMarker2 = stackDepthMarker;
        StackMapFrameCodeStream.StackMarker stackMarker2 = stackMarker;
        int i20 = i11;
        StackMapFrameCodeStream.ExceptionMarker exceptionMarker2 = exceptionMarker;
        int i21 = 0;
        int i22 = 0;
        int i23 = 0;
        int i24 = 0;
        while (true) {
            int i25 = i19 - i2;
            int i26 = i19;
            if (z10 && stackMarker2.pc == i25) {
                VerificationTypeInfo[] verificationTypeInfoArr = stackMapFrame.stackItems;
                VerificationTypeInfo[] verificationTypeInfoArr2 = new VerificationTypeInfo[stackMapFrame.numberOfStackItems];
                iArr = framePositions;
                i4 = length;
                System.arraycopy(verificationTypeInfoArr, 0, verificationTypeInfoArr2, 0, stackMapFrame.numberOfStackItems);
                stackMarker2.setInfos(verificationTypeInfoArr2);
            } else {
                iArr = framePositions;
                i4 = length;
                if (z10 && stackMarker2.destinationPC == i25) {
                    VerificationTypeInfo[] verificationTypeInfoArr3 = stackMarker2.infos;
                    stackMapFrame.stackItems = verificationTypeInfoArr3;
                    stackMapFrame.numberOfStackItems = verificationTypeInfoArr3.length;
                    int i27 = i21 + 1;
                    if (i27 < length3) {
                        i21 = i27;
                        stackMarker2 = stackMarkers[i27];
                    } else {
                        i21 = i27;
                        z10 = false;
                    }
                }
            }
            if (z9 && stackDepthMarker2.pc == i25) {
                TypeBinding typeBinding = stackDepthMarker2.typeBinding;
                if (typeBinding != null) {
                    if (stackDepthMarker2.delta > 0) {
                        stackMapFrame.addStackItem(new VerificationTypeInfo(typeBinding));
                    } else {
                        stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(typeBinding);
                    }
                } else {
                    stackMapFrame.numberOfStackItems--;
                }
                int i28 = i22 + 1;
                if (i28 < length2) {
                    i22 = i28;
                    stackDepthMarker2 = stackDepthMarkers[i28];
                } else {
                    i22 = i28;
                    z9 = false;
                }
            }
            if (z11 && exceptionMarker2.pc == i25) {
                stackMapFrame.numberOfStackItems = 0;
                stackMapFrame.addStackItem(new VerificationTypeInfo(0, 7, exceptionMarker2.constantPoolName));
                int i29 = i23 + 1;
                if (i29 < length4) {
                    exceptionMarker2 = exceptionMarkers[i29];
                    i23 = i29;
                } else {
                    i23 = i29;
                    z11 = false;
                }
            }
            if (i20 < i25) {
                while (true) {
                    int i30 = i24 + 1;
                    i5 = i4;
                    i20 = i30 < i5 ? iArr[i30] : Integer.MAX_VALUE;
                    i24 = i30;
                    if (i20 < i25) {
                        i4 = i5;
                    }
                }
            } else {
                i5 = i4;
            }
            if (i20 == i25) {
                StackMapFrame stackMapFrameDuplicate = stackMapFrame.duplicate();
                stackMapFrameDuplicate.pc = i25;
                classFile = this;
                classFile.initializeLocals(z ? true : methodBinding.isStatic(), i25, stackMapFrameDuplicate);
                i6 = i25;
                map2 = map;
                classFile.add(map2, stackMapFrameDuplicate);
                int i31 = i24 + 1;
                if (i31 < i5) {
                    i7 = iArr[i31];
                    i8 = 0;
                    i24 = i31;
                } else {
                    i24 = i31;
                    i8 = 0;
                    i7 = Integer.MAX_VALUE;
                }
            } else {
                classFile = this;
                i6 = i25;
                map2 = map;
                i7 = i20;
                i8 = 0;
            }
            byte bU1At = (byte) classFile.u1At(bArr, i8, i26);
            switch (bU1At) {
                case -128:
                case -127:
                case -126:
                case -125:
                    exceptionMarkers = exceptionMarkers;
                    stackDepthMarker2 = stackDepthMarker2;
                    i5 = i5;
                    bArr3 = bArr3;
                    hashSet3 = hashSet3;
                    length3 = length3;
                    length4 = length4;
                    stackMapFrame.numberOfStackItems--;
                    i26++;
                    i3 = i3;
                    break;
                case -124:
                    hashSet3 = hashSet3;
                    i26 += 3;
                    i3 = i3;
                    break;
                case -123:
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.LONG);
                    i26++;
                    i3 = i3;
                    break;
                case -122:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.FLOAT);
                    i26++;
                    i3 = i3;
                    break;
                case -121:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.DOUBLE);
                    i26++;
                    i3 = i3;
                    break;
                case -120:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.INT);
                    i26++;
                    i3 = i3;
                    break;
                case -119:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.FLOAT);
                    i26++;
                    i3 = i3;
                    break;
                case -118:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.DOUBLE);
                    i26++;
                    i3 = i3;
                    break;
                case -117:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.INT);
                    i26++;
                    i3 = i3;
                    break;
                case -116:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.LONG);
                    i26++;
                    i3 = i3;
                    break;
                case -115:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.DOUBLE);
                    i26++;
                    i3 = i3;
                    break;
                case -114:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.INT);
                    i26++;
                    i3 = i3;
                    break;
                case -113:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.LONG);
                    i26++;
                    i3 = i3;
                    break;
                case -112:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.FLOAT);
                    i26++;
                    i3 = i3;
                    break;
                case -111:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.BYTE);
                    i26++;
                    i3 = i3;
                    break;
                case -110:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.CHAR);
                    i26++;
                    i3 = i3;
                    break;
                case -109:
                    z2 = true;
                    stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.SHORT);
                    i26++;
                    i3 = i3;
                    break;
                case -108:
                case -107:
                case -106:
                case -105:
                case -104:
                    stackMapFrame.numberOfStackItems -= 2;
                    stackMapFrame.addStackItem(TypeBinding.INT);
                    i26++;
                    i3 = i3;
                    break;
                case -103:
                case -102:
                case -101:
                case -100:
                case -99:
                case -98:
                    hashSet3 = hashSet3;
                    stackMapFrame.numberOfStackItems--;
                    classFile.addRealJumpTarget(hashSet3, classFile.i2At(bArr, 1, i26) + i6);
                    i26 += 3;
                    i3 = i3;
                    break;
                case -97:
                case -96:
                case -95:
                case -94:
                case -93:
                case -92:
                case -91:
                case -90:
                    exceptionMarkers = exceptionMarkers;
                    stackDepthMarker2 = stackDepthMarker2;
                    i5 = i5;
                    bArr3 = bArr3;
                    hashSet3 = hashSet3;
                    z2 = true;
                    length3 = length3;
                    length4 = length4;
                    stackMapFrame.numberOfStackItems -= 2;
                    classFile.addRealJumpTarget(hashSet3, classFile.i2At(bArr, 1, i26) + i6);
                    i26 += 3;
                    i3 = i3;
                    break;
                case -89:
                    exceptionMarkers = exceptionMarkers;
                    stackDepthMarker2 = stackDepthMarker2;
                    i5 = i5;
                    bArr3 = bArr3;
                    hashSet3 = hashSet3;
                    z2 = true;
                    length3 = length3;
                    length4 = length4;
                    classFile.addRealJumpTarget(hashSet3, classFile.i2At(bArr, 1, i26) + i6);
                    i26 += 3;
                    classFile.addRealJumpTarget(hashSet3, i26 - i2);
                    i3 = i3;
                    break;
                default:
                    switch (bU1At) {
                        case -86:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            length4 = length4;
                            do {
                                i26++;
                            } while (((i26 - i2) & 3) != 0);
                            classFile.addRealJumpTarget(hashSet3, classFile.i4At(bArr, 0, i26) + i6);
                            int iI4At = classFile.i4At(bArr, 0, i26 + 4);
                            int iI4At2 = classFile.i4At(bArr, 0, i26 + 8);
                            i26 += 12;
                            int i32 = iI4At2 - iI4At;
                            int i33 = 1;
                            int i34 = i32 + 1;
                            int i35 = 0;
                            while (i35 < i34) {
                                classFile.addRealJumpTarget(hashSet3, classFile.i4At(bArr, 0, i26) + i6);
                                i26 += 4;
                                i35++;
                                i33 = 1;
                            }
                            stackMapFrame.numberOfStackItems -= i33;
                            i3 = i3;
                            break;
                        case -85:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            length4 = length4;
                            do {
                                i26++;
                            } while (((i26 - i2) & 3) != 0);
                            classFile.addRealJumpTarget(hashSet3, classFile.i4At(bArr, 0, i26) + i6);
                            int iU4At = (int) classFile.u4At(bArr, 0, i26 + 4);
                            i26 += 8;
                            for (int i36 = 0; i36 < iU4At; i36++) {
                                classFile.addRealJumpTarget(hashSet3, classFile.i4At(bArr, 0, i26 + 4) + i6);
                                i26 += 8;
                            }
                            stackMapFrame.numberOfStackItems--;
                            i3 = i3;
                            break;
                        case -84:
                        case -83:
                        case -82:
                        case -81:
                        case -80:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            length4 = length4;
                            stackMapFrame.numberOfStackItems--;
                            i26++;
                            classFile.addRealJumpTarget(hashSet3, i26 - i2);
                            i3 = i3;
                            break;
                        case -79:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            length4 = length4;
                            i26++;
                            classFile.addRealJumpTarget(hashSet3, i26 - i2);
                            i3 = i3;
                            break;
                        case -78:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            length4 = length4;
                            int i37 = iArr2[classFile.u2At(bArr3, 3, iArr2[classFile.u2At(bArr3, 3, iArr2[classFile.u2At(bArr, 1, i26)])])];
                            char[] cArrUtf8At = classFile.utf8At(bArr3, i37 + 3, classFile.u2At(bArr3, 1, i37));
                            if (cArrUtf8At.length == 1) {
                                char c = cArrUtf8At[0];
                                if (c == 'F') {
                                    stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                } else if (c == 'S') {
                                    stackMapFrame.addStackItem(TypeBinding.SHORT);
                                } else if (c == 'Z') {
                                    stackMapFrame.addStackItem(TypeBinding.BOOLEAN);
                                } else if (c == 'I') {
                                    stackMapFrame.addStackItem(TypeBinding.INT);
                                } else if (c != 'J') {
                                    switch (c) {
                                        case 'B':
                                            stackMapFrame.addStackItem(TypeBinding.BYTE);
                                            break;
                                        case 'C':
                                            stackMapFrame.addStackItem(TypeBinding.CHAR);
                                            break;
                                        case 'D':
                                            stackMapFrame.addStackItem(TypeBinding.DOUBLE);
                                            break;
                                    }
                                } else {
                                    stackMapFrame.addStackItem(TypeBinding.LONG);
                                }
                            } else if (cArrUtf8At[0] == '[') {
                                stackMapFrame.addStackItem(new VerificationTypeInfo(0, cArrUtf8At));
                            } else {
                                stackMapFrame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray(cArrUtf8At, 1, cArrUtf8At.length - 1)));
                            }
                            i26 += 3;
                            i3 = i3;
                            break;
                        case -77:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            length4 = length4;
                            z3 = true;
                            stackMapFrame.numberOfStackItems--;
                            i26 += 3;
                            break;
                        case -76:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            length4 = length4;
                            int i38 = iArr2[classFile.u2At(bArr3, 3, iArr2[classFile.u2At(bArr3, 3, iArr2[classFile.u2At(bArr, 1, i26)])])];
                            char[] cArrUtf8At2 = classFile.utf8At(bArr3, i38 + 3, classFile.u2At(bArr3, 1, i38));
                            stackMapFrame.numberOfStackItems--;
                            if (cArrUtf8At2.length == 1) {
                                char c2 = cArrUtf8At2[0];
                                if (c2 == 'F') {
                                    stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                } else if (c2 == 'S') {
                                    stackMapFrame.addStackItem(TypeBinding.SHORT);
                                } else if (c2 == 'Z') {
                                    stackMapFrame.addStackItem(TypeBinding.BOOLEAN);
                                } else if (c2 == 'I') {
                                    stackMapFrame.addStackItem(TypeBinding.INT);
                                } else if (c2 != 'J') {
                                    switch (c2) {
                                        case 'B':
                                            stackMapFrame.addStackItem(TypeBinding.BYTE);
                                            break;
                                        case 'C':
                                            stackMapFrame.addStackItem(TypeBinding.CHAR);
                                            break;
                                        case 'D':
                                            stackMapFrame.addStackItem(TypeBinding.DOUBLE);
                                            break;
                                    }
                                } else {
                                    stackMapFrame.addStackItem(TypeBinding.LONG);
                                }
                            } else {
                                if (cArrUtf8At2[0] == '[') {
                                    stackMapFrame.addStackItem(new VerificationTypeInfo(0, cArrUtf8At2));
                                } else {
                                    z3 = true;
                                    stackMapFrame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray(cArrUtf8At2, 1, cArrUtf8At2.length - 1)));
                                }
                                i26 += 3;
                                break;
                            }
                            z3 = true;
                            i26 += 3;
                            break;
                        case -75:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            length4 = length4;
                            stackMapFrame.numberOfStackItems -= 2;
                            i26 += 3;
                            i3 = i3;
                            break;
                        case -74:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            length4 = length4;
                            int iU2At = classFile.u2At(bArr3, 3, iArr2[classFile.u2At(bArr, 1, i26)]);
                            int i39 = iArr2[classFile.u2At(bArr3, 3, iArr2[iU2At])];
                            char[] cArrUtf8At3 = classFile.utf8At(bArr3, i39 + 3, classFile.u2At(bArr3, 1, i39));
                            int i40 = iArr2[classFile.u2At(bArr3, 1, iArr2[iU2At])];
                            classFile.utf8At(bArr3, i40 + 3, classFile.u2At(bArr3, 1, i40));
                            stackMapFrame.numberOfStackItems -= classFile.getParametersCount(cArrUtf8At3) + 1;
                            char[] returnType = classFile.getReturnType(cArrUtf8At3);
                            if (returnType.length == 1) {
                                char c3 = returnType[0];
                                if (c3 == 'F') {
                                    stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                } else if (c3 == 'S') {
                                    stackMapFrame.addStackItem(TypeBinding.SHORT);
                                } else if (c3 == 'Z') {
                                    stackMapFrame.addStackItem(TypeBinding.BOOLEAN);
                                } else if (c3 == 'I') {
                                    stackMapFrame.addStackItem(TypeBinding.INT);
                                } else if (c3 != 'J') {
                                    switch (c3) {
                                        case 'B':
                                            stackMapFrame.addStackItem(TypeBinding.BYTE);
                                            break;
                                        case 'C':
                                            stackMapFrame.addStackItem(TypeBinding.CHAR);
                                            break;
                                        case 'D':
                                            stackMapFrame.addStackItem(TypeBinding.DOUBLE);
                                            break;
                                    }
                                } else {
                                    stackMapFrame.addStackItem(TypeBinding.LONG);
                                }
                            } else if (returnType[0] == '[') {
                                stackMapFrame.addStackItem(new VerificationTypeInfo(0, returnType));
                            } else {
                                stackMapFrame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray(returnType, 1, returnType.length - 1)));
                            }
                            i26 += 3;
                            i3 = i3;
                            break;
                        case -73:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            length4 = length4;
                            int iU2At2 = classFile.u2At(bArr3, 3, iArr2[classFile.u2At(bArr, 1, i26)]);
                            int i41 = iArr2[classFile.u2At(bArr3, 3, iArr2[iU2At2])];
                            char[] cArrUtf8At4 = classFile.utf8At(bArr3, i41 + 3, classFile.u2At(bArr3, 1, i41));
                            int i42 = iArr2[classFile.u2At(bArr3, 1, iArr2[iU2At2])];
                            char[] cArrUtf8At5 = classFile.utf8At(bArr3, i42 + 3, classFile.u2At(bArr3, 1, i42));
                            stackMapFrame.numberOfStackItems -= classFile.getParametersCount(cArrUtf8At4);
                            if (CharOperation.equals(ConstantPool.Init, cArrUtf8At5)) {
                                stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1].tag = 7;
                            }
                            stackMapFrame.numberOfStackItems--;
                            char[] returnType2 = classFile.getReturnType(cArrUtf8At4);
                            if (returnType2.length == 1) {
                                char c4 = returnType2[0];
                                if (c4 == 'F') {
                                    stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                } else if (c4 == 'S') {
                                    stackMapFrame.addStackItem(TypeBinding.SHORT);
                                } else if (c4 == 'Z') {
                                    stackMapFrame.addStackItem(TypeBinding.BOOLEAN);
                                } else if (c4 == 'I') {
                                    stackMapFrame.addStackItem(TypeBinding.INT);
                                } else if (c4 != 'J') {
                                    switch (c4) {
                                        case 'B':
                                            stackMapFrame.addStackItem(TypeBinding.BYTE);
                                            break;
                                        case 'C':
                                            stackMapFrame.addStackItem(TypeBinding.CHAR);
                                            break;
                                        case 'D':
                                            stackMapFrame.addStackItem(TypeBinding.DOUBLE);
                                            break;
                                    }
                                } else {
                                    stackMapFrame.addStackItem(TypeBinding.LONG);
                                }
                            } else {
                                if (returnType2[0] == '[') {
                                    stackMapFrame.addStackItem(new VerificationTypeInfo(0, returnType2));
                                } else {
                                    z3 = true;
                                    stackMapFrame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray(returnType2, 1, returnType2.length - 1)));
                                }
                                i26 += 3;
                                break;
                            }
                            z3 = true;
                            i26 += 3;
                            break;
                        case -72:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            length4 = length4;
                            int iU2At3 = classFile.u2At(bArr3, 3, iArr2[classFile.u2At(bArr, 1, i26)]);
                            int i43 = iArr2[classFile.u2At(bArr3, 3, iArr2[iU2At3])];
                            char[] cArrUtf8At6 = classFile.utf8At(bArr3, i43 + 3, classFile.u2At(bArr3, 1, i43));
                            int i44 = iArr2[classFile.u2At(bArr3, 1, iArr2[iU2At3])];
                            classFile.utf8At(bArr3, i44 + 3, classFile.u2At(bArr3, 1, i44));
                            stackMapFrame.numberOfStackItems -= classFile.getParametersCount(cArrUtf8At6);
                            char[] returnType3 = classFile.getReturnType(cArrUtf8At6);
                            if (returnType3.length == 1) {
                                char c5 = returnType3[0];
                                if (c5 == 'F') {
                                    stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                } else if (c5 == 'S') {
                                    stackMapFrame.addStackItem(TypeBinding.SHORT);
                                } else if (c5 == 'Z') {
                                    stackMapFrame.addStackItem(TypeBinding.BOOLEAN);
                                } else if (c5 == 'I') {
                                    stackMapFrame.addStackItem(TypeBinding.INT);
                                } else if (c5 != 'J') {
                                    switch (c5) {
                                        case 'B':
                                            stackMapFrame.addStackItem(TypeBinding.BYTE);
                                            break;
                                        case 'C':
                                            stackMapFrame.addStackItem(TypeBinding.CHAR);
                                            break;
                                        case 'D':
                                            stackMapFrame.addStackItem(TypeBinding.DOUBLE);
                                            break;
                                    }
                                } else {
                                    stackMapFrame.addStackItem(TypeBinding.LONG);
                                }
                            } else {
                                if (returnType3[0] == '[') {
                                    stackMapFrame.addStackItem(new VerificationTypeInfo(0, returnType3));
                                } else {
                                    z3 = true;
                                    stackMapFrame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray(returnType3, 1, returnType3.length - 1)));
                                }
                                i26 += 3;
                                break;
                            }
                            z3 = true;
                            i26 += 3;
                            break;
                        case -71:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            length4 = length4;
                            int iU2At4 = classFile.u2At(bArr3, 3, iArr2[classFile.u2At(bArr, 1, i26)]);
                            int i45 = iArr2[classFile.u2At(bArr3, 3, iArr2[iU2At4])];
                            char[] cArrUtf8At7 = classFile.utf8At(bArr3, i45 + 3, classFile.u2At(bArr3, 1, i45));
                            int i46 = iArr2[classFile.u2At(bArr3, 1, iArr2[iU2At4])];
                            classFile.utf8At(bArr3, i46 + 3, classFile.u2At(bArr3, 1, i46));
                            stackMapFrame.numberOfStackItems -= classFile.getParametersCount(cArrUtf8At7) + 1;
                            char[] returnType4 = classFile.getReturnType(cArrUtf8At7);
                            if (returnType4.length == 1) {
                                char c6 = returnType4[0];
                                if (c6 == 'F') {
                                    stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                } else if (c6 == 'S') {
                                    stackMapFrame.addStackItem(TypeBinding.SHORT);
                                } else if (c6 == 'Z') {
                                    stackMapFrame.addStackItem(TypeBinding.BOOLEAN);
                                } else if (c6 == 'I') {
                                    stackMapFrame.addStackItem(TypeBinding.INT);
                                } else if (c6 != 'J') {
                                    switch (c6) {
                                        case 'B':
                                            stackMapFrame.addStackItem(TypeBinding.BYTE);
                                            break;
                                        case 'C':
                                            stackMapFrame.addStackItem(TypeBinding.CHAR);
                                            break;
                                        case 'D':
                                            stackMapFrame.addStackItem(TypeBinding.DOUBLE);
                                            break;
                                    }
                                } else {
                                    stackMapFrame.addStackItem(TypeBinding.LONG);
                                }
                            } else {
                                if (returnType4[0] == '[') {
                                    stackMapFrame.addStackItem(new VerificationTypeInfo(0, returnType4));
                                } else {
                                    z3 = true;
                                    stackMapFrame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray(returnType4, 1, returnType4.length - 1)));
                                }
                                i26 += 5;
                                break;
                            }
                            z3 = true;
                            i26 += 5;
                            break;
                        case -70:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            length4 = length4;
                            int i47 = iArr2[classFile.u2At(bArr3, 3, iArr2[classFile.u2At(bArr3, 3, iArr2[classFile.u2At(bArr, 1, i26)])])];
                            char[] cArrUtf8At8 = classFile.utf8At(bArr3, i47 + 3, classFile.u2At(bArr3, 1, i47));
                            stackMapFrame.numberOfStackItems -= classFile.getParametersCount(cArrUtf8At8);
                            char[] returnType5 = classFile.getReturnType(cArrUtf8At8);
                            if (returnType5.length == 1) {
                                char c7 = returnType5[0];
                                if (c7 == 'F') {
                                    stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                } else if (c7 == 'S') {
                                    stackMapFrame.addStackItem(TypeBinding.SHORT);
                                } else if (c7 == 'Z') {
                                    stackMapFrame.addStackItem(TypeBinding.BOOLEAN);
                                } else if (c7 == 'I') {
                                    stackMapFrame.addStackItem(TypeBinding.INT);
                                } else if (c7 != 'J') {
                                    switch (c7) {
                                        case 'B':
                                            stackMapFrame.addStackItem(TypeBinding.BYTE);
                                            break;
                                        case 'C':
                                            stackMapFrame.addStackItem(TypeBinding.CHAR);
                                            break;
                                        case 'D':
                                            stackMapFrame.addStackItem(TypeBinding.DOUBLE);
                                            break;
                                    }
                                } else {
                                    stackMapFrame.addStackItem(TypeBinding.LONG);
                                }
                            } else {
                                if (returnType5[0] == '[') {
                                    stackMapFrame.addStackItem(new VerificationTypeInfo(0, returnType5));
                                } else {
                                    z3 = true;
                                    stackMapFrame.addStackItem(new VerificationTypeInfo(0, CharOperation.subarray(returnType5, 1, returnType5.length - 1)));
                                }
                                i26 += 5;
                                break;
                            }
                            z3 = true;
                            i26 += 5;
                            break;
                        case -69:
                            bArr3 = bArr3;
                            z4 = true;
                            int i48 = iArr2[classFile.u2At(bArr3, 1, iArr2[classFile.u2At(bArr, 1, i26)])];
                            VerificationTypeInfo verificationTypeInfo = new VerificationTypeInfo(0, 8, classFile.utf8At(bArr3, i48 + 3, classFile.u2At(bArr3, 1, i48)));
                            verificationTypeInfo.offset = i6;
                            stackMapFrame.addStackItem(verificationTypeInfo);
                            i26 += 3;
                            i3 = i3;
                            break;
                        case -68:
                            bArr3 = bArr3;
                            z4 = true;
                            switch (classFile.u1At(bArr, 1, i26)) {
                                case 4:
                                    cArr = new char[]{'[', 'Z'};
                                    break;
                                case 5:
                                    cArr = new char[]{'[', Util.C_CHAR};
                                    break;
                                case 6:
                                    cArr = new char[]{'[', Util.C_FLOAT};
                                    break;
                                case 7:
                                    cArr = new char[]{'[', Util.C_DOUBLE};
                                    break;
                                case 8:
                                    cArr = new char[]{'[', Util.C_BYTE};
                                    break;
                                case 9:
                                    cArr = new char[]{'[', Util.C_SHORT};
                                    break;
                                case 10:
                                    cArr = new char[]{'[', 'I'};
                                    break;
                                case 11:
                                    cArr = new char[]{'[', Util.C_LONG};
                                    break;
                                default:
                                    cArr = null;
                                    break;
                            }
                            stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(1, cArr);
                            i26 += 2;
                            i3 = i3;
                            break;
                        case -67:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            length3 = length3;
                            int i49 = iArr2[classFile.u2At(bArr3, 1, iArr2[classFile.u2At(bArr, 1, i26)])];
                            char[] cArrUtf8At9 = classFile.utf8At(bArr3, i49 + 3, classFile.u2At(bArr3, 1, i49));
                            int length5 = cArrUtf8At9.length;
                            if (cArrUtf8At9[0] != '[') {
                                cArr2 = new char[length5 + 3];
                                System.arraycopy(cArrUtf8At9, 0, cArr2, 2, length5);
                                cArr2[0] = '[';
                                i9 = 1;
                                cArr2[1] = 'L';
                                cArr2[length5 + 2] = ';';
                            } else {
                                i9 = 1;
                                cArr2 = new char[length5 + 1];
                                System.arraycopy(cArrUtf8At9, 0, cArr2, 1, length5);
                                cArr2[0] = '[';
                            }
                            stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - i9] = new VerificationTypeInfo(0, cArr2);
                            i26 += 3;
                            i3 = i3;
                            break;
                        case -66:
                            hashSet3 = hashSet3;
                            z5 = true;
                            stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.INT);
                            i26++;
                            length4 = length4;
                            i3 = i3;
                            break;
                        case -65:
                            hashSet3 = hashSet3;
                            z5 = true;
                            stackMapFrame.numberOfStackItems--;
                            i26++;
                            classFile.addRealJumpTarget(hashSet3, i26 - i2);
                            length4 = length4;
                            i3 = i3;
                            break;
                        case Lua.MASK_NOT_OP /* -64 */:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            int i50 = iArr2[classFile.u2At(bArr3, 1, iArr2[classFile.u2At(bArr, 1, i26)])];
                            stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(0, classFile.utf8At(bArr3, i50 + 3, classFile.u2At(bArr3, 1, i50)));
                            i26 += 3;
                            length4 = length4;
                            break;
                        case -63:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1] = new VerificationTypeInfo(TypeBinding.INT);
                            i26 += 3;
                            length4 = length4;
                            break;
                        case -62:
                        case -61:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            stackMapFrame.numberOfStackItems--;
                            i26++;
                            length3 = length3;
                            length4 = length4;
                            i3 = i3;
                            break;
                        case -60:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            byte bU1At2 = (byte) classFile.u1At(bArr, 1, i26);
                            if (bU1At2 == -124) {
                                i26 += 6;
                            } else {
                                int iU2At5 = classFile.u2At(bArr, 2, i26);
                                switch (bU1At2) {
                                    case 21:
                                        stackMapFrame.addStackItem(TypeBinding.INT);
                                        break;
                                    case 22:
                                        stackMapFrame.addStackItem(TypeBinding.LONG);
                                        break;
                                    case 23:
                                        stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                        break;
                                    case 24:
                                        stackMapFrame.addStackItem(TypeBinding.DOUBLE);
                                        break;
                                    case 25:
                                        VerificationTypeInfo verificationTypeInfoRetrieveLocal = stackMapFrame.locals[iU2At5];
                                        if (verificationTypeInfoRetrieveLocal == null) {
                                            verificationTypeInfoRetrieveLocal = classFile.retrieveLocal(i6, iU2At5);
                                        }
                                        stackMapFrame.addStackItem(verificationTypeInfoRetrieveLocal);
                                        break;
                                    default:
                                        switch (bU1At2) {
                                            case 54:
                                                stackMapFrame.numberOfStackItems--;
                                                break;
                                            case 55:
                                                stackMapFrame.numberOfStackItems--;
                                                break;
                                            case 56:
                                                stackMapFrame.numberOfStackItems--;
                                                break;
                                            case 57:
                                                stackMapFrame.numberOfStackItems--;
                                                break;
                                            case 58:
                                                stackMapFrame.locals[iU2At5] = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                                stackMapFrame.numberOfStackItems--;
                                                break;
                                        }
                                        break;
                                }
                                i26 += 4;
                            }
                            length4 = length4;
                            break;
                        case -59:
                            exceptionMarkers = exceptionMarkers;
                            stackDepthMarker2 = stackDepthMarker2;
                            i5 = i5;
                            bArr3 = bArr3;
                            hashSet3 = hashSet3;
                            int i51 = iArr2[classFile.u2At(bArr3, 1, iArr2[classFile.u2At(bArr, 1, i26)])];
                            char[] cArrUtf8At10 = classFile.utf8At(bArr3, i51 + 3, classFile.u2At(bArr3, 1, i51));
                            stackMapFrame.numberOfStackItems -= classFile.u1At(bArr, 3, i26);
                            int length6 = cArrUtf8At10.length;
                            char[] cArr3 = new char[length6];
                            System.arraycopy(cArrUtf8At10, 0, cArr3, 0, length6);
                            stackMapFrame.addStackItem(new VerificationTypeInfo(0, cArr3));
                            i26 += 4;
                            length4 = length4;
                            break;
                        case -58:
                        case -57:
                            hashSet3 = hashSet3;
                            z6 = true;
                            stackMapFrame.numberOfStackItems--;
                            classFile.addRealJumpTarget(hashSet3, classFile.i2At(bArr, 1, i26) + i6);
                            i26 += 3;
                            length3 = length3;
                            length4 = length4;
                            break;
                        case -56:
                            z6 = true;
                            hashSet3 = hashSet3;
                            classFile.addRealJumpTarget(hashSet3, classFile.i4At(bArr, 1, i26) + i6);
                            i26 += 5;
                            classFile.addRealJumpTarget(hashSet3, i26 - i2);
                            length3 = length3;
                            length4 = length4;
                            break;
                        default:
                            switch (bU1At) {
                                case 0:
                                    i26++;
                                    i3 = i3;
                                    hashSet3 = hashSet3;
                                    length4 = length4;
                                    break;
                                case 1:
                                    stackMapFrame.addStackItem(TypeBinding.NULL);
                                    i26++;
                                    i3 = i3;
                                    hashSet3 = hashSet3;
                                    length4 = length4;
                                    break;
                                case 2:
                                case 3:
                                case 4:
                                case 5:
                                case 6:
                                case 7:
                                case 8:
                                    stackMapFrame.addStackItem(TypeBinding.INT);
                                    i26++;
                                    i3 = i3;
                                    hashSet3 = hashSet3;
                                    length4 = length4;
                                    break;
                                case 9:
                                case 10:
                                    stackMapFrame.addStackItem(TypeBinding.LONG);
                                    i26++;
                                    i3 = i3;
                                    hashSet3 = hashSet3;
                                    length4 = length4;
                                    break;
                                case 11:
                                case 12:
                                case 13:
                                    stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                    i26++;
                                    i3 = i3;
                                    hashSet3 = hashSet3;
                                    length4 = length4;
                                    break;
                                case 14:
                                case 15:
                                    stackMapFrame.addStackItem(TypeBinding.DOUBLE);
                                    i26++;
                                    i3 = i3;
                                    hashSet3 = hashSet3;
                                    length4 = length4;
                                    break;
                                case 16:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    bArr3 = bArr3;
                                    stackMapFrame.addStackItem(TypeBinding.BYTE);
                                    i26 += 2;
                                    i3 = i3;
                                    hashSet3 = hashSet3;
                                    length4 = length4;
                                    break;
                                case 17:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    bArr3 = bArr3;
                                    stackMapFrame.addStackItem(TypeBinding.SHORT);
                                    i26 += 3;
                                    i3 = i3;
                                    hashSet3 = hashSet3;
                                    length4 = length4;
                                    break;
                                case 18:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    bArr3 = bArr3;
                                    int iU1At = classFile.u1At(bArr3, 0, iArr2[classFile.u1At(bArr, 1, i26)]);
                                    if (iU1At == 3) {
                                        stackMapFrame.addStackItem(TypeBinding.INT);
                                    } else if (iU1At == 4) {
                                        stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                    } else if (iU1At == 7) {
                                        stackMapFrame.addStackItem(new VerificationTypeInfo(16, ConstantPool.JavaLangClassConstantPoolName));
                                    } else if (iU1At == 8) {
                                        stackMapFrame.addStackItem(new VerificationTypeInfo(11, ConstantPool.JavaLangStringConstantPoolName));
                                    }
                                    i26 += 2;
                                    i3 = i3;
                                    hashSet3 = hashSet3;
                                    length4 = length4;
                                    break;
                                case 19:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    bArr3 = bArr3;
                                    int iU1At2 = classFile.u1At(bArr3, 0, iArr2[classFile.u2At(bArr, 1, i26)]);
                                    if (iU1At2 == 3) {
                                        stackMapFrame.addStackItem(TypeBinding.INT);
                                    } else if (iU1At2 == 4) {
                                        stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                    } else if (iU1At2 == 7) {
                                        stackMapFrame.addStackItem(new VerificationTypeInfo(16, ConstantPool.JavaLangClassConstantPoolName));
                                    } else if (iU1At2 == 8) {
                                        stackMapFrame.addStackItem(new VerificationTypeInfo(11, ConstantPool.JavaLangStringConstantPoolName));
                                    }
                                    i26 += 3;
                                    i3 = i3;
                                    hashSet3 = hashSet3;
                                    length4 = length4;
                                    break;
                                case 20:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    bArr3 = bArr3;
                                    int iU1At3 = classFile.u1At(bArr3, 0, iArr2[classFile.u2At(bArr, 1, i26)]);
                                    if (iU1At3 == 5) {
                                        stackMapFrame.addStackItem(TypeBinding.LONG);
                                    } else if (iU1At3 == 6) {
                                        stackMapFrame.addStackItem(TypeBinding.DOUBLE);
                                    }
                                    i26 += 3;
                                    i3 = i3;
                                    hashSet3 = hashSet3;
                                    length4 = length4;
                                    break;
                                case 21:
                                    stackMapFrame.addStackItem(TypeBinding.INT);
                                    i26 += 2;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 22:
                                    stackMapFrame.addStackItem(TypeBinding.LONG);
                                    i26 += 2;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 23:
                                    stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                    i26 += 2;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 24:
                                    stackMapFrame.addStackItem(TypeBinding.DOUBLE);
                                    i26 += 2;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 25:
                                    stackMapFrame.addStackItem(classFile.retrieveLocal(i6, classFile.u1At(bArr, 1, i26)));
                                    i26 += 2;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 26:
                                case 27:
                                case 28:
                                case 29:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.addStackItem(TypeBinding.INT);
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 30:
                                case 31:
                                case 32:
                                case 33:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.addStackItem(TypeBinding.LONG);
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 34:
                                case 35:
                                case 36:
                                case 37:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 38:
                                case 39:
                                case 40:
                                case 41:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.addStackItem(TypeBinding.DOUBLE);
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 42:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    VerificationTypeInfo verificationTypeInfoRetrieveLocal2 = stackMapFrame.locals[0];
                                    if (verificationTypeInfoRetrieveLocal2 == null || verificationTypeInfoRetrieveLocal2.tag != 6) {
                                        verificationTypeInfoRetrieveLocal2 = classFile.retrieveLocal(i6, 0);
                                    }
                                    stackMapFrame.addStackItem(verificationTypeInfoRetrieveLocal2);
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 43:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.addStackItem(classFile.retrieveLocal(i6, 1));
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 44:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.addStackItem(classFile.retrieveLocal(i6, 2));
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 45:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.addStackItem(classFile.retrieveLocal(i6, 3));
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 46:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.numberOfStackItems -= 2;
                                    stackMapFrame.addStackItem(TypeBinding.INT);
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 47:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.numberOfStackItems -= 2;
                                    stackMapFrame.addStackItem(TypeBinding.LONG);
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 48:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.numberOfStackItems -= 2;
                                    stackMapFrame.addStackItem(TypeBinding.FLOAT);
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 49:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.numberOfStackItems -= 2;
                                    stackMapFrame.addStackItem(TypeBinding.DOUBLE);
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 50:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.numberOfStackItems--;
                                    stackMapFrame.replaceWithElementType();
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 51:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.numberOfStackItems -= 2;
                                    stackMapFrame.addStackItem(TypeBinding.BYTE);
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 52:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.numberOfStackItems -= 2;
                                    stackMapFrame.addStackItem(TypeBinding.CHAR);
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 53:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.numberOfStackItems -= 2;
                                    stackMapFrame.addStackItem(TypeBinding.SHORT);
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 54:
                                case 55:
                                case 56:
                                case 57:
                                    stackMapFrame.numberOfStackItems--;
                                    i26 += 2;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 58:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    classFile.u1At(bArr, 1, i26);
                                    stackMapFrame.numberOfStackItems--;
                                    i26 += 2;
                                    length4 = length4;
                                    break;
                                case 59:
                                case 60:
                                case 61:
                                case 62:
                                case 63:
                                case 64:
                                case 65:
                                case 66:
                                case 67:
                                case 68:
                                case 69:
                                case 70:
                                case 71:
                                case 72:
                                case 73:
                                case 74:
                                case 76:
                                case 77:
                                case 78:
                                    stackMapFrame.numberOfStackItems--;
                                    i26++;
                                    length4 = length4;
                                    break;
                                case 75:
                                    stackMapFrame.locals[0] = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                    stackMapFrame.numberOfStackItems--;
                                    i26++;
                                    length4 = length4;
                                    break;
                                case 79:
                                case 80:
                                case 81:
                                case 82:
                                case 83:
                                case 84:
                                case 85:
                                case 86:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.numberOfStackItems -= 3;
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 87:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    stackMapFrame.numberOfStackItems--;
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 88:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    int iId = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1].id();
                                    if (iId == 7 || iId == 8) {
                                        z7 = true;
                                        stackMapFrame.numberOfStackItems--;
                                    } else {
                                        stackMapFrame.numberOfStackItems -= 2;
                                        z7 = true;
                                    }
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 89:
                                    z8 = true;
                                    stackMapFrame.addStackItem(stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1]);
                                    i26++;
                                    length4 = length4;
                                    break;
                                case 90:
                                    z8 = true;
                                    VerificationTypeInfo verificationTypeInfo2 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                    stackMapFrame.numberOfStackItems--;
                                    VerificationTypeInfo verificationTypeInfo3 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                    stackMapFrame.numberOfStackItems--;
                                    stackMapFrame.addStackItem(verificationTypeInfo2);
                                    stackMapFrame.addStackItem(verificationTypeInfo3);
                                    stackMapFrame.addStackItem(verificationTypeInfo2);
                                    i26++;
                                    length4 = length4;
                                    break;
                                case 91:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    VerificationTypeInfo verificationTypeInfo4 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                    stackMapFrame.numberOfStackItems--;
                                    VerificationTypeInfo verificationTypeInfo5 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                    stackMapFrame.numberOfStackItems--;
                                    int iId2 = verificationTypeInfo5.id();
                                    if (iId2 == 7 || iId2 == 8) {
                                        stackMapFrame.addStackItem(verificationTypeInfo4);
                                        stackMapFrame.addStackItem(verificationTypeInfo5);
                                        stackMapFrame.addStackItem(verificationTypeInfo4);
                                    } else {
                                        VerificationTypeInfo verificationTypeInfo6 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                        stackMapFrame.numberOfStackItems--;
                                        stackMapFrame.addStackItem(verificationTypeInfo4);
                                        stackMapFrame.addStackItem(verificationTypeInfo6);
                                        stackMapFrame.addStackItem(verificationTypeInfo5);
                                        stackMapFrame.addStackItem(verificationTypeInfo4);
                                    }
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 92:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    VerificationTypeInfo verificationTypeInfo7 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                    stackMapFrame.numberOfStackItems--;
                                    int iId3 = verificationTypeInfo7.id();
                                    if (iId3 == 7 || iId3 == 8) {
                                        stackMapFrame.addStackItem(verificationTypeInfo7);
                                        stackMapFrame.addStackItem(verificationTypeInfo7);
                                    } else {
                                        VerificationTypeInfo verificationTypeInfo8 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                        stackMapFrame.numberOfStackItems--;
                                        stackMapFrame.addStackItem(verificationTypeInfo8);
                                        stackMapFrame.addStackItem(verificationTypeInfo7);
                                        stackMapFrame.addStackItem(verificationTypeInfo8);
                                        stackMapFrame.addStackItem(verificationTypeInfo7);
                                    }
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 93:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    VerificationTypeInfo verificationTypeInfo9 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                    stackMapFrame.numberOfStackItems--;
                                    VerificationTypeInfo verificationTypeInfo10 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                    stackMapFrame.numberOfStackItems--;
                                    int iId4 = verificationTypeInfo9.id();
                                    if (iId4 == 7 || iId4 == 8) {
                                        stackMapFrame.addStackItem(verificationTypeInfo9);
                                        stackMapFrame.addStackItem(verificationTypeInfo10);
                                        stackMapFrame.addStackItem(verificationTypeInfo9);
                                    } else {
                                        VerificationTypeInfo verificationTypeInfo11 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                        stackMapFrame.numberOfStackItems--;
                                        stackMapFrame.addStackItem(verificationTypeInfo10);
                                        stackMapFrame.addStackItem(verificationTypeInfo9);
                                        stackMapFrame.addStackItem(verificationTypeInfo11);
                                        stackMapFrame.addStackItem(verificationTypeInfo10);
                                        stackMapFrame.addStackItem(verificationTypeInfo9);
                                    }
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 94:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    VerificationTypeInfo verificationTypeInfo12 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                    stackMapFrame.numberOfStackItems--;
                                    VerificationTypeInfo verificationTypeInfo13 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                    stackMapFrame.numberOfStackItems--;
                                    int iId5 = verificationTypeInfo12.id();
                                    if (iId5 == 7 || iId5 == 8) {
                                        int iId6 = verificationTypeInfo13.id();
                                        if (iId6 == 7 || iId6 == 8) {
                                            stackMapFrame.addStackItem(verificationTypeInfo12);
                                            stackMapFrame.addStackItem(verificationTypeInfo13);
                                            stackMapFrame.addStackItem(verificationTypeInfo12);
                                        } else {
                                            VerificationTypeInfo verificationTypeInfo14 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                            stackMapFrame.numberOfStackItems--;
                                            stackMapFrame.addStackItem(verificationTypeInfo12);
                                            stackMapFrame.addStackItem(verificationTypeInfo14);
                                            stackMapFrame.addStackItem(verificationTypeInfo13);
                                            stackMapFrame.addStackItem(verificationTypeInfo12);
                                        }
                                    } else {
                                        VerificationTypeInfo verificationTypeInfo15 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                        stackMapFrame.numberOfStackItems--;
                                        int iId7 = verificationTypeInfo15.id();
                                        if (iId7 == 7 || iId7 == 8) {
                                            stackMapFrame.addStackItem(verificationTypeInfo13);
                                            stackMapFrame.addStackItem(verificationTypeInfo12);
                                            stackMapFrame.addStackItem(verificationTypeInfo15);
                                            stackMapFrame.addStackItem(verificationTypeInfo13);
                                            stackMapFrame.addStackItem(verificationTypeInfo12);
                                        } else {
                                            VerificationTypeInfo verificationTypeInfo16 = stackMapFrame.stackItems[stackMapFrame.numberOfStackItems - 1];
                                            stackMapFrame.numberOfStackItems--;
                                            stackMapFrame.addStackItem(verificationTypeInfo13);
                                            stackMapFrame.addStackItem(verificationTypeInfo12);
                                            stackMapFrame.addStackItem(verificationTypeInfo16);
                                            stackMapFrame.addStackItem(verificationTypeInfo15);
                                            stackMapFrame.addStackItem(verificationTypeInfo13);
                                            stackMapFrame.addStackItem(verificationTypeInfo12);
                                        }
                                    }
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 95:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    int i52 = stackMapFrame.numberOfStackItems;
                                    int i53 = i52 - 1;
                                    VerificationTypeInfo verificationTypeInfo17 = stackMapFrame.stackItems[i53];
                                    int i54 = i52 - 2;
                                    stackMapFrame.stackItems[i53] = stackMapFrame.stackItems[i54];
                                    stackMapFrame.stackItems[i54] = verificationTypeInfo17;
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                case 96:
                                case 97:
                                case 98:
                                case 99:
                                case 100:
                                case 101:
                                case 102:
                                case 103:
                                case 104:
                                case 105:
                                case 106:
                                case 107:
                                case 108:
                                case 109:
                                case 110:
                                case 111:
                                case 112:
                                case 113:
                                case 114:
                                case 115:
                                case 120:
                                case 121:
                                case 122:
                                case 123:
                                case 124:
                                case 125:
                                case 126:
                                case 127:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    bArr3 = bArr3;
                                    hashSet3 = hashSet3;
                                    length3 = length3;
                                    length4 = length4;
                                    stackMapFrame.numberOfStackItems--;
                                    i26++;
                                    i3 = i3;
                                    break;
                                case 116:
                                case 117:
                                case 118:
                                case 119:
                                    exceptionMarkers = exceptionMarkers;
                                    stackDepthMarker2 = stackDepthMarker2;
                                    i5 = i5;
                                    i26++;
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                                default:
                                    if (classFile.codeStream.methodDeclaration != null) {
                                        exceptionMarkers = exceptionMarkers;
                                        stackDepthMarker2 = stackDepthMarker2;
                                        i5 = i5;
                                        classFile.codeStream.methodDeclaration.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidOpcode, new Object[]{Byte.valueOf(bU1At), Integer.valueOf(i26), new String(methodBinding.shortReadableName())}), classFile.codeStream.methodDeclaration);
                                    } else {
                                        exceptionMarkers = exceptionMarkers;
                                        stackDepthMarker2 = stackDepthMarker2;
                                        i5 = i5;
                                        classFile.codeStream.lambdaExpression.scope.problemReporter().abortDueToInternalError(Messages.bind(Messages.abort_invalidOpcode, new Object[]{Byte.valueOf(bU1At), Integer.valueOf(i26), new String(methodBinding.shortReadableName())}), classFile.codeStream.lambdaExpression);
                                    }
                                    i3 = i3;
                                    length4 = length4;
                                    break;
                            }
                            break;
                    }
                    break;
            }
            if (i26 >= i3 + i2) {
                return classFile.filterFakeFrames(hashSet3, map2, i3);
            }
            length3 = length3;
            length4 = length4;
            framePositions = iArr;
            i20 = i7;
            stackMarker2 = stackMarker2;
            exceptionMarker2 = exceptionMarker2;
            stackDepthMarker2 = stackDepthMarker2;
            bArr3 = bArr3;
            hashSet3 = hashSet3;
            exceptionMarkers = exceptionMarkers;
            length = i5;
            i19 = i26;
        }
    }

    private void addRealJumpTarget(Set set, int i) {
        set.add(Integer.valueOf(i));
    }

    private void add(Map map, StackMapFrame stackMapFrame) {
        map.put(Integer.valueOf(stackMapFrame.pc), stackMapFrame);
    }

    private final int u1At(byte[] bArr, int i, int i2) {
        return bArr[i + i2] & 255;
    }

    private final int u2At(byte[] bArr, int i, int i2) {
        int i3 = i + i2;
        return ((bArr[i3] & 255) << 8) + (bArr[i3 + 1] & 255);
    }

    private final long u4At(byte[] bArr, int i, int i2) {
        int i3 = i + i2;
        int i4 = i3 + 2;
        return ((((long) bArr[i3]) & 255) << 24) + ((long) ((bArr[i3 + 1] & 255) << 16)) + ((long) ((bArr[i4] & 255) << 8)) + ((long) (bArr[i3 + 3] & 255));
    }

    private final int i2At(byte[] bArr, int i, int i2) {
        int i3 = i + i2;
        return (bArr[i3] << 8) + (bArr[i3 + 1] & 255);
    }

    public char[] utf8At(byte[] bArr, int i, int i2) {
        char[] cArr = new char[i2];
        int i3 = i2;
        int i4 = 0;
        while (i3 != 0) {
            int i5 = i + 1;
            byte b = bArr[i];
            int i6 = b & 255;
            int i7 = i3 - 1;
            if ((b & 128) == 0) {
                i = i5;
                i3 = i7;
            } else if ((b & 32) != 0) {
                i3 -= 3;
                int i8 = i + 2;
                i += 3;
                i6 = ((bArr[i5] & 63) << 6) | ((b & 15) << 12) | (bArr[i8] & 63);
            } else {
                i3 -= 2;
                i += 2;
                i6 = ((b & 31) << 6) | (bArr[i5] & 63);
            }
            cArr[i4] = (char) i6;
            i4++;
        }
        if (i4 == i2) {
            return cArr;
        }
        char[] cArr2 = new char[i4];
        System.arraycopy(cArr, 0, cArr2, 0, i4);
        return cArr2;
    }
}
