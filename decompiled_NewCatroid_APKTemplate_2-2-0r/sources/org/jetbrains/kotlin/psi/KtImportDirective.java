package org.jetbrains.kotlin.psi;

import org.jetbrains.kotlin.com.intellij.lang.ASTNode;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.lexer.KtTokens;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.stubs.KotlinImportDirectiveStub;
import org.jetbrains.kotlin.psi.stubs.elements.KtStubElementTypes;
import org.jetbrains.kotlin.psi.stubs.elements.KtTokenSets;
import org.jetbrains.kotlin.resolve.ImportPath;

/* JADX INFO: loaded from: classes6.dex */
public class KtImportDirective extends KtElementImplStub<KotlinImportDirectiveStub> implements KtImportInfo {
    private volatile FqName importedFqName;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        Object[] objArr = new Object[3];
        if (i == 1) {
            objArr[0] = "stub";
        } else if (i != 2) {
            objArr[0] = "node";
        } else {
            objArr[0] = "visitor";
        }
        objArr[1] = "org/jetbrains/kotlin/psi/KtImportDirective";
        if (i != 2) {
            objArr[2] = "<init>";
        } else {
            objArr[2] = "accept";
        }
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtImportDirective(ASTNode aSTNode) {
        super(aSTNode);
        if (aSTNode == null) {
            $$$reportNull$$$0(0);
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public KtImportDirective(KotlinImportDirectiveStub kotlinImportDirectiveStub) {
        super(kotlinImportDirectiveStub, KtStubElementTypes.IMPORT_DIRECTIVE);
        if (kotlinImportDirectiveStub == null) {
            $$$reportNull$$$0(1);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtElementImplStub, org.jetbrains.kotlin.psi.KtElement, org.jetbrains.kotlin.psi.KtExpression
    public <R, D> R accept(KtVisitor<R, D> ktVisitor, D d) {
        if (ktVisitor == null) {
            $$$reportNull$$$0(2);
        }
        return ktVisitor.visitImportDirective(this, d);
    }

    @IfNotParsed
    public KtExpression getImportedReference() {
        KtExpression[] ktExpressionArr = (KtExpression[]) getStubOrPsiChildren(KtTokenSets.INSIDE_DIRECTIVE_EXPRESSIONS, KtExpression.ARRAY_FACTORY);
        if (ktExpressionArr.length > 0) {
            return ktExpressionArr[0];
        }
        return null;
    }

    public KtImportAlias getAlias() {
        return (KtImportAlias) getStubOrPsiChild(KtStubElementTypes.IMPORT_ALIAS);
    }

    @Override // org.jetbrains.kotlin.psi.KtImportInfo
    public String getAliasName() {
        KtImportAlias alias = getAlias();
        if (alias != null) {
            return alias.getName();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.psi.KtImportInfo
    public boolean isAllUnder() {
        KotlinImportDirectiveStub kotlinImportDirectiveStub = (KotlinImportDirectiveStub) getStub();
        if (kotlinImportDirectiveStub != null) {
            return kotlinImportDirectiveStub.isAllUnder();
        }
        return getNode().findChildByType(KtTokens.MUL) != null;
    }

    @Override // org.jetbrains.kotlin.psi.KtImportInfo
    public KtImportInfo.ImportContent getImportContent() {
        KtExpression importedReference = getImportedReference();
        if (importedReference == null) {
            return null;
        }
        return new KtImportInfo.ImportContent.ExpressionBased(importedReference);
    }

    @Override // org.jetbrains.kotlin.psi.KtImportInfo
    @IfNotParsed
    public FqName getImportedFqName() {
        KotlinImportDirectiveStub kotlinImportDirectiveStub = (KotlinImportDirectiveStub) getStub();
        if (kotlinImportDirectiveStub != null) {
            return kotlinImportDirectiveStub.getImportedFqName();
        }
        FqName fqName = this.importedFqName;
        if (fqName != null) {
            return fqName;
        }
        KtExpression importedReference = getImportedReference();
        if (importedReference == null) {
            return null;
        }
        FqName fqNameFqNameFromExpression = fqNameFromExpression(importedReference);
        this.importedFqName = fqNameFqNameFromExpression;
        return fqNameFqNameFromExpression;
    }

    @IfNotParsed
    public ImportPath getImportPath() {
        FqName importedFqName = getImportedFqName();
        if (importedFqName == null) {
            return null;
        }
        String aliasName = getAliasName();
        return new ImportPath(importedFqName, isAllUnder(), aliasName != null ? Name.identifier(aliasName) : null);
    }

    public boolean isValidImport() {
        KotlinImportDirectiveStub kotlinImportDirectiveStub = (KotlinImportDirectiveStub) getStub();
        if (kotlinImportDirectiveStub != null) {
            return kotlinImportDirectiveStub.isValid();
        }
        return !PsiTreeUtil.hasErrorElements(this);
    }

    @Override // org.jetbrains.kotlin.com.intellij.extapi.psi.ASTDelegatePsiElement
    public void subtreeChanged() {
        super.subtreeChanged();
        this.importedFqName = null;
    }

    private static FqName fqNameFromExpression(KtExpression ktExpression) {
        if (ktExpression == null) {
            return null;
        }
        if (ktExpression instanceof KtDotQualifiedExpression) {
            KtDotQualifiedExpression ktDotQualifiedExpression = (KtDotQualifiedExpression) ktExpression;
            FqName fqNameFqNameFromExpression = fqNameFromExpression(ktDotQualifiedExpression.getReceiverExpression());
            Name nameNameFromExpression = nameFromExpression(ktDotQualifiedExpression.getSelectorExpression());
            if (nameNameFromExpression == null) {
                return fqNameFqNameFromExpression;
            }
            if (fqNameFqNameFromExpression != null) {
                return fqNameFqNameFromExpression.child(nameNameFromExpression);
            }
            return null;
        }
        if (ktExpression instanceof KtSimpleNameExpression) {
            return FqName.topLevel(((KtSimpleNameExpression) ktExpression).getReferencedNameAsName());
        }
        throw new IllegalArgumentException("Can't construct fqn for: " + ktExpression.getClass().toString());
    }

    private static Name nameFromExpression(KtExpression ktExpression) {
        if (ktExpression == null) {
            return null;
        }
        if (ktExpression instanceof KtSimpleNameExpression) {
            return ((KtSimpleNameExpression) ktExpression).getReferencedNameAsName();
        }
        throw new IllegalArgumentException("Can't construct name for: " + ktExpression.getClass().toString());
    }
}
