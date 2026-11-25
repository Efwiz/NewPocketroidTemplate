package org.eclipse.jdt.internal.compiler.ast;

import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.ClassFile;
import org.eclipse.jdt.internal.compiler.CompilationResult;
import org.eclipse.jdt.internal.compiler.codegen.BranchLabel;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.codegen.ConstantPool;
import org.eclipse.jdt.internal.compiler.codegen.Opcodes;
import org.eclipse.jdt.internal.compiler.flow.ExceptionHandlingFlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.flow.InitializationFlowContext;
import org.eclipse.jdt.internal.compiler.flow.UnconditionalFlowInfo;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.ClassScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeConstants;
import org.eclipse.jdt.internal.compiler.parser.Parser;
import org.eclipse.jdt.internal.compiler.problem.AbortMethod;

/* JADX INFO: loaded from: classes14.dex */
public class Clinit extends AbstractMethodDeclaration {
    private static int ENUM_CONSTANTS_THRESHOLD = 2000;
    private FieldBinding assertionSyntheticFieldBinding;
    private FieldBinding classLiteralSyntheticField;

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public boolean isClinit() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public boolean isInitializationMethod() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public boolean isStatic() {
        return true;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void parseStatements(Parser parser, CompilationUnitDeclaration compilationUnitDeclaration) {
    }

    public Clinit(CompilationResult compilationResult) {
        super(compilationResult);
        this.assertionSyntheticFieldBinding = null;
        this.classLiteralSyntheticField = null;
        this.modifiers = 0;
        this.selector = TypeConstants.CLINIT;
    }

    public void analyseCode(ClassScope classScope, InitializationFlowContext initializationFlowContext, FlowInfo flowInfo) {
        if (this.ignoreFurtherInvestigation) {
            return;
        }
        try {
            ExceptionHandlingFlowContext exceptionHandlingFlowContext = new ExceptionHandlingFlowContext(initializationFlowContext.parent, this, Binding.NO_EXCEPTIONS, initializationFlowContext, this.scope, FlowInfo.DEAD_END);
            if ((flowInfo.tagBits & 1) == 0) {
                this.bits |= 64;
            }
            UnconditionalFlowInfo unconditionalFlowInfoMergedWith = flowInfo.mergedWith(initializationFlowContext.initsOnReturn);
            for (FieldBinding fieldBinding : this.scope.enclosingSourceType().fields()) {
                if (fieldBinding.isStatic() && !unconditionalFlowInfoMergedWith.isDefinitelyAssigned(fieldBinding)) {
                    if (fieldBinding.isFinal()) {
                        this.scope.problemReporter().uninitializedBlankFinalField(fieldBinding, this.scope.referenceType().declarationOf(fieldBinding.original()));
                    } else if (fieldBinding.isNonNull()) {
                        this.scope.problemReporter().uninitializedNonNullField(fieldBinding, this.scope.referenceType().declarationOf(fieldBinding.original()));
                    }
                }
            }
            initializationFlowContext.checkInitializerExceptions(this.scope, exceptionHandlingFlowContext, unconditionalFlowInfoMergedWith);
        } catch (AbortMethod unused) {
            this.ignoreFurtherInvestigation = true;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void generateCode(ClassScope classScope, ClassFile classFile) {
        CompilationResult compilationResult;
        int i;
        boolean z;
        TypeDeclaration typeDeclaration;
        if (this.ignoreFurtherInvestigation) {
            return;
        }
        if (classScope == null || (typeDeclaration = classScope.referenceContext) == null) {
            compilationResult = null;
            i = 0;
        } else {
            compilationResult = typeDeclaration.compilationResult();
            i = compilationResult.problemCount;
        }
        int i2 = 0;
        do {
            try {
                i2 = classFile.contentsOffset;
                generateCode(classScope, classFile, i2);
            } catch (AbortMethod e) {
                z = true;
                if (e.compilationResult == CodeStream.RESTART_IN_WIDE_MODE) {
                    classFile.contentsOffset = i2;
                    classFile.methodCount--;
                    classFile.codeStream.resetInWideMode();
                    if (compilationResult != null) {
                        compilationResult.problemCount = i;
                    }
                } else if (e.compilationResult == CodeStream.RESTART_CODE_GEN_FOR_UNUSED_LOCALS_MODE) {
                    classFile.contentsOffset = i2;
                    classFile.methodCount--;
                    classFile.codeStream.resetForCodeGenUnusedLocals();
                    if (compilationResult != null) {
                        compilationResult.problemCount = i;
                    }
                } else {
                    classFile.contentsOffset = i2;
                    classFile.methodCount--;
                }
            }
            z = false;
        } while (z);
    }

    /* JADX WARN: Code duplicated, block: B:39:0x00dd  */
    /* JADX WARN: Code duplicated, block: B:89:0x0193  */
    private void generateCode(ClassScope classScope, ClassFile classFile, int i) {
        int i2;
        int i3;
        int i4;
        ConstantPool constantPool = classFile.constantPool;
        int i5 = constantPool.currentOffset;
        int i6 = constantPool.currentIndex;
        classFile.generateMethodInfoHeaderForClinit();
        int i7 = classFile.contentsOffset;
        classFile.generateCodeAttributeHeader();
        CodeStream codeStream = classFile.codeStream;
        resolve(classScope);
        codeStream.reset(this, classFile);
        TypeDeclaration typeDeclaration = classScope.referenceContext;
        MethodScope methodScope = typeDeclaration.staticInitializerScope;
        methodScope.computeLocalVariablePositions(0, codeStream);
        TypeBinding typeBinding = null;
        if (this.assertionSyntheticFieldBinding != null) {
            codeStream.generateClassLiteralAccessForType(classScope.outerMostClassScope().enclosingSourceType(), this.classLiteralSyntheticField);
            codeStream.invokeJavaLangClassDesiredAssertionStatus();
            BranchLabel branchLabel = new BranchLabel(codeStream);
            codeStream.ifne(branchLabel);
            codeStream.iconst_1();
            BranchLabel branchLabel2 = new BranchLabel(codeStream);
            codeStream.decrStackSize(1);
            codeStream.goto_(branchLabel2);
            branchLabel.place();
            codeStream.iconst_0();
            branchLabel2.place();
            codeStream.fieldAccess(Opcodes.OPC_putstatic, this.assertionSyntheticFieldBinding, null);
        }
        FieldDeclaration[] fieldDeclarationArr = typeDeclaration.fields;
        if (TypeDeclaration.kind(typeDeclaration.modifiers) == 3) {
            int i8 = typeDeclaration.enumConstantsCounter;
            if (i8 > ENUM_CONSTANTS_THRESHOLD) {
                if (fieldDeclarationArr != null) {
                    int length = fieldDeclarationArr.length;
                    int i9 = 0;
                    int i10 = -1;
                    int i11 = 0;
                    i4 = 0;
                    while (i9 < length) {
                        FieldDeclaration fieldDeclaration = fieldDeclarationArr[i9];
                        if (fieldDeclaration.isStatic()) {
                            if (fieldDeclaration.getKind() == 3) {
                                if (i10 == -1) {
                                    i10 = i9;
                                }
                                int i12 = i11 + 1;
                                if (i12 > ENUM_CONSTANTS_THRESHOLD) {
                                    codeStream.invoke(Opcodes.OPC_invokestatic, typeDeclaration.binding.addSyntheticMethodForEnumInitialization(i10, i9), null);
                                    i10 = i9;
                                    i11 = 1;
                                } else {
                                    i11 = i12;
                                }
                            } else {
                                i4++;
                            }
                        }
                        i9++;
                        typeBinding = null;
                    }
                    if (i11 != 0) {
                        codeStream.invoke(Opcodes.OPC_invokestatic, typeDeclaration.binding.addSyntheticMethodForEnumInitialization(i10, length), typeBinding);
                    }
                } else {
                    i4 = 0;
                }
            } else if (fieldDeclarationArr != null) {
                i4 = 0;
                for (FieldDeclaration fieldDeclaration2 : fieldDeclarationArr) {
                    if (fieldDeclaration2.isStatic()) {
                        if (fieldDeclaration2.getKind() == 3) {
                            fieldDeclaration2.generateCode(methodScope, codeStream);
                        } else {
                            i4++;
                        }
                    }
                }
            } else {
                i4 = 0;
            }
            codeStream.generateInlinedValue(i8);
            codeStream.anewarray(typeDeclaration.binding);
            if (i8 > 0 && fieldDeclarationArr != null) {
                for (FieldDeclaration fieldDeclaration3 : fieldDeclarationArr) {
                    if (fieldDeclaration3.getKind() == 3) {
                        codeStream.dup();
                        codeStream.generateInlinedValue(fieldDeclaration3.binding.f627id);
                        codeStream.fieldAccess(Opcodes.OPC_getstatic, fieldDeclaration3.binding, null);
                        codeStream.aastore();
                    }
                }
            }
            codeStream.fieldAccess(Opcodes.OPC_putstatic, typeDeclaration.enumValuesSyntheticfield, null);
            if (i4 != 0) {
                int length2 = fieldDeclarationArr.length;
                i2 = -1;
                for (int i13 = 0; i13 < length2 && i4 >= 0; i13++) {
                    FieldDeclaration fieldDeclaration4 = fieldDeclarationArr[i13];
                    int kind = fieldDeclaration4.getKind();
                    if (kind != 1) {
                        if (kind == 2 && fieldDeclaration4.isStatic()) {
                            i4--;
                            i2 = ((Initializer) fieldDeclaration4).block.sourceEnd;
                            fieldDeclaration4.generateCode(methodScope, codeStream);
                        }
                    } else if (fieldDeclaration4.binding.isStatic()) {
                        i4--;
                        i2 = fieldDeclaration4.declarationEnd;
                        fieldDeclaration4.generateCode(methodScope, codeStream);
                    }
                }
                i3 = i2;
            } else {
                i3 = -1;
            }
        } else if (fieldDeclarationArr != null) {
            i2 = -1;
            for (FieldDeclaration fieldDeclaration5 : fieldDeclarationArr) {
                int kind2 = fieldDeclaration5.getKind();
                if (kind2 != 1) {
                    if (kind2 == 2 && fieldDeclaration5.isStatic()) {
                        i2 = ((Initializer) fieldDeclaration5).block.sourceEnd;
                        fieldDeclaration5.generateCode(methodScope, codeStream);
                    }
                } else if (fieldDeclaration5.binding.isStatic()) {
                    i2 = fieldDeclaration5.declarationEnd;
                    fieldDeclaration5.generateCode(methodScope, codeStream);
                }
            }
            i3 = i2;
        } else {
            i3 = -1;
        }
        if (codeStream.position == 0) {
            classFile.contentsOffset = i;
            classFile.methodCount--;
            constantPool.resetForClinit(i6, i5);
            return;
        }
        if ((this.bits & 64) != 0) {
            int i14 = codeStream.position;
            codeStream.return_();
            if (i3 != -1) {
                codeStream.recordPositionsFrom(i14, i3);
            }
        }
        codeStream.recordPositionsFrom(0, typeDeclaration.sourceStart);
        classFile.completeCodeAttributeForClinit(i7);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration, org.eclipse.jdt.internal.compiler.ast.ASTNode
    public StringBuffer print(int i, StringBuffer stringBuffer) {
        printIndent(i, stringBuffer).append("<clinit>()");
        printBody(i + 1, stringBuffer);
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void resolve(ClassScope classScope) {
        this.scope = new MethodScope(classScope, classScope.referenceContext, true);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.AbstractMethodDeclaration
    public void traverse(ASTVisitor aSTVisitor, ClassScope classScope) {
        aSTVisitor.visit(this, classScope);
        aSTVisitor.endVisit(this, classScope);
    }

    public void setAssertionSupport(FieldBinding fieldBinding, boolean z) {
        this.assertionSyntheticFieldBinding = fieldBinding;
        if (z) {
            SourceTypeBinding sourceTypeBindingEnclosingSourceType = this.scope.outerMostClassScope().enclosingSourceType();
            if (sourceTypeBindingEnclosingSourceType.isInterface() || sourceTypeBindingEnclosingSourceType.isBaseType()) {
                return;
            }
            this.classLiteralSyntheticField = sourceTypeBindingEnclosingSourceType.addSyntheticFieldForClassLiteral(sourceTypeBindingEnclosingSourceType, this.scope);
        }
    }
}
