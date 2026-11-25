package org.jetbrains.kotlin.com.intellij.psi;

import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocComment;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTag;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocTagValue;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiDocToken;
import org.jetbrains.kotlin.com.intellij.psi.javadoc.PsiInlineDocTag;

/* JADX INFO: loaded from: classes3.dex */
public abstract class JavaElementVisitor extends PsiElementVisitor {
    public void visitReferenceExpression(PsiReferenceExpression psiReferenceExpression) {
    }

    public void visitAnonymousClass(PsiAnonymousClass psiAnonymousClass) {
        visitClass(psiAnonymousClass);
    }

    public void visitArrayAccessExpression(PsiArrayAccessExpression psiArrayAccessExpression) {
        visitExpression(psiArrayAccessExpression);
    }

    public void visitArrayInitializerExpression(PsiArrayInitializerExpression psiArrayInitializerExpression) {
        visitExpression(psiArrayInitializerExpression);
    }

    public void visitAssertStatement(PsiAssertStatement psiAssertStatement) {
        visitStatement(psiAssertStatement);
    }

    public void visitAssignmentExpression(PsiAssignmentExpression psiAssignmentExpression) {
        visitExpression(psiAssignmentExpression);
    }

    public void visitBinaryExpression(PsiBinaryExpression psiBinaryExpression) {
        visitPolyadicExpression(psiBinaryExpression);
    }

    public void visitBlockStatement(PsiBlockStatement psiBlockStatement) {
        visitStatement(psiBlockStatement);
    }

    public void visitBreakStatement(PsiBreakStatement psiBreakStatement) {
        visitStatement(psiBreakStatement);
    }

    public void visitYieldStatement(PsiYieldStatement psiYieldStatement) {
        visitStatement(psiYieldStatement);
    }

    public void visitClass(PsiClass psiClass) {
        visitElement(psiClass);
    }

    public void visitClassInitializer(PsiClassInitializer psiClassInitializer) {
        visitElement(psiClassInitializer);
    }

    public void visitClassObjectAccessExpression(PsiClassObjectAccessExpression psiClassObjectAccessExpression) {
        visitExpression(psiClassObjectAccessExpression);
    }

    public void visitCodeBlock(PsiCodeBlock psiCodeBlock) {
        visitElement(psiCodeBlock);
    }

    public void visitConditionalExpression(PsiConditionalExpression psiConditionalExpression) {
        visitExpression(psiConditionalExpression);
    }

    public void visitContinueStatement(PsiContinueStatement psiContinueStatement) {
        visitStatement(psiContinueStatement);
    }

    public void visitDeclarationStatement(PsiDeclarationStatement psiDeclarationStatement) {
        visitStatement(psiDeclarationStatement);
    }

    public void visitDocComment(PsiDocComment psiDocComment) {
        visitComment(psiDocComment);
    }

    public void visitDocTag(PsiDocTag psiDocTag) {
        visitElement(psiDocTag);
    }

    public void visitDocTagValue(PsiDocTagValue psiDocTagValue) {
        visitElement(psiDocTagValue);
    }

    public void visitDoWhileStatement(PsiDoWhileStatement psiDoWhileStatement) {
        visitStatement(psiDoWhileStatement);
    }

    public void visitEmptyStatement(PsiEmptyStatement psiEmptyStatement) {
        visitStatement(psiEmptyStatement);
    }

    public void visitExpression(PsiExpression psiExpression) {
        visitElement(psiExpression);
    }

    public void visitExpressionList(PsiExpressionList psiExpressionList) {
        visitElement(psiExpressionList);
    }

    public void visitExpressionListStatement(PsiExpressionListStatement psiExpressionListStatement) {
        visitStatement(psiExpressionListStatement);
    }

    public void visitExpressionStatement(PsiExpressionStatement psiExpressionStatement) {
        visitStatement(psiExpressionStatement);
    }

    public void visitField(PsiField psiField) {
        visitVariable(psiField);
    }

    public void visitForStatement(PsiForStatement psiForStatement) {
        visitStatement(psiForStatement);
    }

    public void visitForeachStatement(PsiForeachStatement psiForeachStatement) {
        visitStatement(psiForeachStatement);
    }

    public void visitIdentifier(PsiIdentifier psiIdentifier) {
        visitJavaToken(psiIdentifier);
    }

    public void visitIfStatement(PsiIfStatement psiIfStatement) {
        visitStatement(psiIfStatement);
    }

    public void visitImportList(PsiImportList psiImportList) {
        visitElement(psiImportList);
    }

    public void visitImportStatement(PsiImportStatement psiImportStatement) {
        visitElement(psiImportStatement);
    }

    public void visitImportStaticStatement(PsiImportStaticStatement psiImportStaticStatement) {
        visitElement(psiImportStaticStatement);
    }

    public void visitInlineDocTag(PsiInlineDocTag psiInlineDocTag) {
        visitDocTag(psiInlineDocTag);
    }

    public void visitInstanceOfExpression(PsiInstanceOfExpression psiInstanceOfExpression) {
        visitExpression(psiInstanceOfExpression);
    }

    public void visitJavaToken(PsiJavaToken psiJavaToken) {
        visitElement(psiJavaToken);
    }

    public void visitKeyword(PsiKeyword psiKeyword) {
        visitJavaToken(psiKeyword);
    }

    public void visitLabeledStatement(PsiLabeledStatement psiLabeledStatement) {
        visitStatement(psiLabeledStatement);
    }

    public void visitLiteralExpression(PsiLiteralExpression psiLiteralExpression) {
        visitExpression(psiLiteralExpression);
    }

    public void visitLocalVariable(PsiLocalVariable psiLocalVariable) {
        visitVariable(psiLocalVariable);
    }

    public void visitMethod(PsiMethod psiMethod) {
        visitElement(psiMethod);
    }

    public void visitMethodCallExpression(PsiMethodCallExpression psiMethodCallExpression) {
        visitCallExpression(psiMethodCallExpression);
    }

    public void visitCallExpression(PsiCallExpression psiCallExpression) {
        visitExpression(psiCallExpression);
    }

    public void visitModifierList(PsiModifierList psiModifierList) {
        visitElement(psiModifierList);
    }

    public void visitNewExpression(PsiNewExpression psiNewExpression) {
        visitCallExpression(psiNewExpression);
    }

    public void visitPackage(PsiPackage psiPackage) {
        visitElement(psiPackage);
    }

    public void visitPackageStatement(PsiPackageStatement psiPackageStatement) {
        visitElement(psiPackageStatement);
    }

    public void visitParameter(PsiParameter psiParameter) {
        visitVariable(psiParameter);
    }

    public void visitRecordComponent(PsiRecordComponent psiRecordComponent) {
        visitVariable(psiRecordComponent);
    }

    public void visitReceiverParameter(PsiReceiverParameter psiReceiverParameter) {
        visitVariable(psiReceiverParameter);
    }

    public void visitParameterList(PsiParameterList psiParameterList) {
        visitElement(psiParameterList);
    }

    public void visitRecordHeader(PsiRecordHeader psiRecordHeader) {
        visitElement(psiRecordHeader);
    }

    public void visitParenthesizedExpression(PsiParenthesizedExpression psiParenthesizedExpression) {
        visitExpression(psiParenthesizedExpression);
    }

    public void visitUnaryExpression(PsiUnaryExpression psiUnaryExpression) {
        visitExpression(psiUnaryExpression);
    }

    public void visitPostfixExpression(PsiPostfixExpression psiPostfixExpression) {
        visitUnaryExpression(psiPostfixExpression);
    }

    public void visitPrefixExpression(PsiPrefixExpression psiPrefixExpression) {
        visitUnaryExpression(psiPrefixExpression);
    }

    public void visitReferenceElement(PsiJavaCodeReferenceElement psiJavaCodeReferenceElement) {
        visitElement(psiJavaCodeReferenceElement);
    }

    public void visitImportStaticReferenceElement(PsiImportStaticReferenceElement psiImportStaticReferenceElement) {
        visitElement(psiImportStaticReferenceElement);
    }

    public void visitMethodReferenceExpression(PsiMethodReferenceExpression psiMethodReferenceExpression) {
        visitReferenceExpression(psiMethodReferenceExpression);
    }

    public void visitReferenceList(PsiReferenceList psiReferenceList) {
        visitElement(psiReferenceList);
    }

    public void visitReferenceParameterList(PsiReferenceParameterList psiReferenceParameterList) {
        visitElement(psiReferenceParameterList);
    }

    public void visitTypeParameterList(PsiTypeParameterList psiTypeParameterList) {
        visitElement(psiTypeParameterList);
    }

    public void visitReturnStatement(PsiReturnStatement psiReturnStatement) {
        visitStatement(psiReturnStatement);
    }

    public void visitStatement(PsiStatement psiStatement) {
        visitElement(psiStatement);
    }

    public void visitSuperExpression(PsiSuperExpression psiSuperExpression) {
        visitExpression(psiSuperExpression);
    }

    public void visitSwitchLabelStatement(PsiSwitchLabelStatement psiSwitchLabelStatement) {
        visitStatement(psiSwitchLabelStatement);
    }

    public void visitSwitchLabeledRuleStatement(PsiSwitchLabeledRuleStatement psiSwitchLabeledRuleStatement) {
        visitStatement(psiSwitchLabeledRuleStatement);
    }

    public void visitSwitchStatement(PsiSwitchStatement psiSwitchStatement) {
        visitStatement(psiSwitchStatement);
    }

    public void visitSynchronizedStatement(PsiSynchronizedStatement psiSynchronizedStatement) {
        visitStatement(psiSynchronizedStatement);
    }

    public void visitThisExpression(PsiThisExpression psiThisExpression) {
        visitExpression(psiThisExpression);
    }

    public void visitThrowStatement(PsiThrowStatement psiThrowStatement) {
        visitStatement(psiThrowStatement);
    }

    public void visitTryStatement(PsiTryStatement psiTryStatement) {
        visitStatement(psiTryStatement);
    }

    public void visitCatchSection(PsiCatchSection psiCatchSection) {
        visitElement(psiCatchSection);
    }

    public void visitResourceList(PsiResourceList psiResourceList) {
        visitElement(psiResourceList);
    }

    public void visitResourceVariable(PsiResourceVariable psiResourceVariable) {
        visitLocalVariable(psiResourceVariable);
    }

    public void visitResourceExpression(PsiResourceExpression psiResourceExpression) {
        visitElement(psiResourceExpression);
    }

    public void visitTypeElement(PsiTypeElement psiTypeElement) {
        visitElement(psiTypeElement);
    }

    public void visitTypeCastExpression(PsiTypeCastExpression psiTypeCastExpression) {
        visitExpression(psiTypeCastExpression);
    }

    public void visitVariable(PsiVariable psiVariable) {
        visitElement(psiVariable);
    }

    public void visitWhileStatement(PsiWhileStatement psiWhileStatement) {
        visitStatement(psiWhileStatement);
    }

    public void visitJavaFile(PsiJavaFile psiJavaFile) {
        visitFile(psiJavaFile);
    }

    public void visitImplicitVariable(ImplicitVariable implicitVariable) {
        visitLocalVariable(implicitVariable);
    }

    public void visitDocToken(PsiDocToken psiDocToken) {
        visitElement(psiDocToken);
    }

    public void visitTypeParameter(PsiTypeParameter psiTypeParameter) {
        visitClass(psiTypeParameter);
    }

    public void visitAnnotation(PsiAnnotation psiAnnotation) {
        visitElement(psiAnnotation);
    }

    public void visitAnnotationParameterList(PsiAnnotationParameterList psiAnnotationParameterList) {
        visitElement(psiAnnotationParameterList);
    }

    public void visitAnnotationArrayInitializer(PsiArrayInitializerMemberValue psiArrayInitializerMemberValue) {
        visitElement(psiArrayInitializerMemberValue);
    }

    public void visitNameValuePair(PsiNameValuePair psiNameValuePair) {
        visitElement(psiNameValuePair);
    }

    public void visitAnnotationMethod(PsiAnnotationMethod psiAnnotationMethod) {
        visitMethod(psiAnnotationMethod);
    }

    public void visitEnumConstant(PsiEnumConstant psiEnumConstant) {
        visitField(psiEnumConstant);
    }

    public void visitEnumConstantInitializer(PsiEnumConstantInitializer psiEnumConstantInitializer) {
        visitAnonymousClass(psiEnumConstantInitializer);
    }

    public void visitCodeFragment(JavaCodeFragment javaCodeFragment) {
        visitFile(javaCodeFragment);
    }

    public void visitPolyadicExpression(PsiPolyadicExpression psiPolyadicExpression) {
        visitExpression(psiPolyadicExpression);
    }

    public void visitLambdaExpression(PsiLambdaExpression psiLambdaExpression) {
        visitExpression(psiLambdaExpression);
    }

    public void visitSwitchExpression(PsiSwitchExpression psiSwitchExpression) {
        visitExpression(psiSwitchExpression);
    }

    public void visitModule(PsiJavaModule psiJavaModule) {
        visitElement(psiJavaModule);
    }

    public void visitModuleReferenceElement(PsiJavaModuleReferenceElement psiJavaModuleReferenceElement) {
        visitElement(psiJavaModuleReferenceElement);
    }

    public void visitModuleStatement(PsiStatement psiStatement) {
        visitStatement(psiStatement);
    }

    public void visitRequiresStatement(PsiRequiresStatement psiRequiresStatement) {
        visitModuleStatement(psiRequiresStatement);
    }

    public void visitPackageAccessibilityStatement(PsiPackageAccessibilityStatement psiPackageAccessibilityStatement) {
        visitModuleStatement(psiPackageAccessibilityStatement);
    }

    public void visitUsesStatement(PsiUsesStatement psiUsesStatement) {
        visitModuleStatement(psiUsesStatement);
    }

    public void visitProvidesStatement(PsiProvidesStatement psiProvidesStatement) {
        visitModuleStatement(psiProvidesStatement);
    }

    public void visitPattern(PsiPattern psiPattern) {
        visitElement(psiPattern);
    }

    public void visitTypeTestPattern(PsiTypeTestPattern psiTypeTestPattern) {
        visitPattern(psiTypeTestPattern);
    }

    public void visitParenthesizedPattern(PsiParenthesizedPattern psiParenthesizedPattern) {
        visitPattern(psiParenthesizedPattern);
    }

    public void visitGuardedPattern(PsiGuardedPattern psiGuardedPattern) {
        visitPattern(psiGuardedPattern);
    }

    public void visitDefaultCaseLabelElement(PsiDefaultCaseLabelElement psiDefaultCaseLabelElement) {
        visitElement(psiDefaultCaseLabelElement);
    }

    public void visitCaseLabelElementList(PsiCaseLabelElementList psiCaseLabelElementList) {
        visitElement(psiCaseLabelElementList);
    }

    public void visitPatternVariable(PsiPatternVariable psiPatternVariable) {
        visitParameter(psiPatternVariable);
    }
}
