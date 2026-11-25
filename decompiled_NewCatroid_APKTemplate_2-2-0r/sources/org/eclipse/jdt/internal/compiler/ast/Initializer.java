package org.eclipse.jdt.internal.compiler.ast;

import io.netty.util.internal.StringUtil;
import org.eclipse.jdt.core.compiler.CharOperation;
import org.eclipse.jdt.internal.compiler.ASTVisitor;
import org.eclipse.jdt.internal.compiler.codegen.CodeStream;
import org.eclipse.jdt.internal.compiler.flow.FlowContext;
import org.eclipse.jdt.internal.compiler.flow.FlowInfo;
import org.eclipse.jdt.internal.compiler.lookup.Binding;
import org.eclipse.jdt.internal.compiler.lookup.BlockScope;
import org.eclipse.jdt.internal.compiler.lookup.FieldBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodBinding;
import org.eclipse.jdt.internal.compiler.lookup.MethodScope;
import org.eclipse.jdt.internal.compiler.lookup.SourceTypeBinding;
import org.eclipse.jdt.internal.compiler.lookup.TypeBinding;
import org.eclipse.jdt.internal.compiler.parser.Parser;

/* JADX INFO: loaded from: classes14.dex */
public class Initializer extends FieldDeclaration {
    public Block block;
    public int bodyEnd;
    public int bodyStart;
    public int lastVisibleFieldID;
    private MethodBinding methodBinding;

    @Override // org.eclipse.jdt.internal.compiler.ast.FieldDeclaration, org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration
    public int getKind() {
        return 2;
    }

    public Initializer(Block block, int i) {
        this.block = block;
        this.modifiers = i;
        if (block != null) {
            int i2 = block.sourceStart;
            this.sourceStart = i2;
            this.declarationSourceStart = i2;
        }
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FieldDeclaration
    public FlowInfo analyseCode(MethodScope methodScope, FlowContext flowContext, FlowInfo flowInfo) {
        Block block = this.block;
        return block != null ? block.analyseCode(methodScope, flowContext, flowInfo) : flowInfo;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FieldDeclaration, org.eclipse.jdt.internal.compiler.ast.Statement
    public void generateCode(BlockScope blockScope, CodeStream codeStream) {
        if ((this.bits & Integer.MIN_VALUE) == 0) {
            return;
        }
        int i = codeStream.position;
        Block block = this.block;
        if (block != null) {
            block.generateCode(blockScope, codeStream);
        }
        codeStream.recordPositionsFrom(i, this.sourceStart);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FieldDeclaration
    public boolean isStatic() {
        return (this.modifiers & 8) != 0;
    }

    public void parseStatements(Parser parser, TypeDeclaration typeDeclaration, CompilationUnitDeclaration compilationUnitDeclaration) {
        parser.parse(this, typeDeclaration, compilationUnitDeclaration);
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FieldDeclaration, org.eclipse.jdt.internal.compiler.ast.AbstractVariableDeclaration, org.eclipse.jdt.internal.compiler.ast.Statement
    public StringBuffer printStatement(int i, StringBuffer stringBuffer) {
        if (this.modifiers != 0) {
            printIndent(i, stringBuffer);
            printModifiers(this.modifiers, stringBuffer);
            if (this.annotations != null) {
                printAnnotations(this.annotations, stringBuffer);
                stringBuffer.append(StringUtil.SPACE);
            }
            stringBuffer.append("{\n");
            Block block = this.block;
            if (block != null) {
                block.printBody(i, stringBuffer);
            }
            printIndent(i, stringBuffer).append('}');
            return stringBuffer;
        }
        Block block2 = this.block;
        if (block2 != null) {
            block2.printStatement(i, stringBuffer);
        } else {
            printIndent(i, stringBuffer).append("{}");
        }
        return stringBuffer;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FieldDeclaration
    public void resolve(MethodScope methodScope) {
        FieldBinding fieldBinding = methodScope.initializedField;
        int i = methodScope.lastVisibleFieldID;
        try {
            methodScope.initializedField = null;
            methodScope.lastVisibleFieldID = this.lastVisibleFieldID;
            if (isStatic()) {
                SourceTypeBinding sourceTypeBindingEnclosingSourceType = methodScope.enclosingSourceType();
                if (sourceTypeBindingEnclosingSourceType.isNestedType() && !sourceTypeBindingEnclosingSourceType.isStatic()) {
                    methodScope.problemReporter().innerTypesCannotDeclareStaticInitializers(sourceTypeBindingEnclosingSourceType, this);
                }
            }
            Block block = this.block;
            if (block != null) {
                block.resolve(methodScope);
            }
        } finally {
            methodScope.initializedField = fieldBinding;
            methodScope.lastVisibleFieldID = i;
        }
    }

    public MethodBinding getMethodBinding() {
        MethodBinding methodBinding;
        if (this.methodBinding == null) {
            BlockScope blockScope = this.block.scope;
            if (isStatic()) {
                methodBinding = new MethodBinding(8, CharOperation.NO_CHAR, TypeBinding.VOID, Binding.NO_PARAMETERS, Binding.NO_EXCEPTIONS, blockScope.enclosingSourceType());
            } else {
                methodBinding = new MethodBinding(0, CharOperation.NO_CHAR, TypeBinding.VOID, Binding.NO_PARAMETERS, Binding.NO_EXCEPTIONS, blockScope.enclosingSourceType());
            }
            this.methodBinding = methodBinding;
        }
        return this.methodBinding;
    }

    @Override // org.eclipse.jdt.internal.compiler.ast.FieldDeclaration
    public void traverse(ASTVisitor aSTVisitor, MethodScope methodScope) {
        Block block;
        if (aSTVisitor.visit(this, methodScope) && (block = this.block) != null) {
            block.traverse(aSTVisitor, methodScope);
        }
        aSTVisitor.endVisit(this, methodScope);
    }
}
