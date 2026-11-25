package org.jetbrains.kotlin.psi;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: VisitorWrappers.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u008e\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a\u001a\u0010\u0000\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\u0006\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\b\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\n\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\u000b\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\r\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\u000f\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\u0010\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\u0012\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\u0014\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0013\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\u0015\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\u0017\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\u0018\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\u001a\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u0019\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\u001b\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\u001d\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\u001f\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010 \u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010\"\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020!\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010#\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010%\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020$\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010&\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010(\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010)\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010+\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010,\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010.\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020-\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010/\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u00101\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u000200\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u00102\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u00104\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u000203\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u00105\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u00107\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u000206\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u00108\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010:\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u000209\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010;\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010=\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020<\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010>\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020?\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010@\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020A\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010B\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020A\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010C\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020D\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010E\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020D\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010F\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010H\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010I\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020J\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010K\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020J\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010L\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020M\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010N\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020M\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010O\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020P\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010Q\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020P\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010R\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020S\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010T\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010V\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020U\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010W\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020X\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010Y\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020Z\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010[\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\\\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010]\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020\\\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010^\u001a\u00020\u00012\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020_\u0012\u0004\u0012\u00020\u00050\u0003\u001a\u001a\u0010`\u001a\u00020\u00072\u0012\u0010\u0002\u001a\u000e\u0012\u0004\u0012\u00020_\u0012\u0004\u0012\u00020\u00050\u0003¨\u0006a"}, d2 = {"annotationEntryRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtTreeVisitorVoid;", "block", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/psi/KtAnnotationEntry;", "", "annotationEntryVisitor", "Lorg/jetbrains/kotlin/psi/KtVisitorVoid;", "binaryExpressionRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtBinaryExpression;", "binaryExpressionVisitor", "binaryWithTypeRHSExpressionVisitor", "Lorg/jetbrains/kotlin/psi/KtBinaryExpressionWithTypeRHS;", "callExpressionRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtCallExpression;", "callExpressionVisitor", "classInitializerVisitor", "Lorg/jetbrains/kotlin/psi/KtClassInitializer;", "classOrObjectRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtClassOrObject;", "classOrObjectVisitor", "classRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtClass;", "classVisitor", "declarationRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "declarationVisitor", "delegatedSuperTypeEntry", "Lorg/jetbrains/kotlin/psi/KtDelegatedSuperTypeEntry;", "destructuringDeclarationRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "destructuringDeclarationVisitor", "dotQualifiedExpressionRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtDotQualifiedExpression;", "dotQualifiedExpressionVisitor", "enumEntryRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtEnumEntry;", "enumEntryVisitor", "expressionRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtExpression;", "expressionVisitor", "ifExpressionRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtIfExpression;", "ifExpressionVisitor", "lambdaExpressionRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtLambdaExpression;", "lambdaExpressionVisitor", "modifierListRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtModifierList;", "modifierListVisitor", "namedDeclarationRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "namedDeclarationVisitor", "namedFunctionRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "namedFunctionVisitor", "packageDirectiveRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtPackageDirective;", "packageDirectiveVisitor", "parameterRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtParameter;", "parameterVisitor", "postfixExpressionVisitor", "Lorg/jetbrains/kotlin/psi/KtPostfixExpression;", "prefixExpressionRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtPrefixExpression;", "prefixExpressionVisitor", "primaryConstructorRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtPrimaryConstructor;", "primaryConstructorVisitor", "propertyAccessorRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtPropertyAccessor;", "propertyAccessorVisitor", "propertyRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtProperty;", "propertyVisitor", "qualifiedExpressionRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "qualifiedExpressionVisitor", "referenceExpressionRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtReferenceExpression;", "referenceExpressionVisitor", "returnExpressionVisitor", "Lorg/jetbrains/kotlin/psi/KtReturnExpression;", "simpleNameExpressionRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "simpleNameExpressionVisitor", "typeReferenceRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtTypeReference;", "valueArgumentListVisitor", "Lorg/jetbrains/kotlin/psi/KtValueArgumentList;", "valueArgumentRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtValueArgument;", "valueArgumentVisitor", "whenExpressionRecursiveVisitor", "Lorg/jetbrains/kotlin/psi/KtWhenExpression;", "whenExpressionVisitor", "psi"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class VisitorWrappersKt {
    public static final KtVisitorVoid classOrObjectVisitor(final Function1<? super KtClassOrObject, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.classOrObjectVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitClassOrObject(KtClassOrObject classOrObject) {
                Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
                block.invoke(classOrObject);
            }
        };
    }

    public static final KtTreeVisitorVoid classOrObjectRecursiveVisitor(final Function1<? super KtClassOrObject, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.classOrObjectRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitClassOrObject(KtClassOrObject classOrObject) {
                Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
                super.visitClassOrObject(classOrObject);
                block.invoke(classOrObject);
            }
        };
    }

    public static final KtVisitorVoid classVisitor(final Function1<? super KtClass, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.classVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitClass(KtClass klass) {
                Intrinsics.checkNotNullParameter(klass, "klass");
                block.invoke(klass);
            }
        };
    }

    public static final KtTreeVisitorVoid classRecursiveVisitor(final Function1<? super KtClass, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.classRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitClass(KtClass klass) {
                Intrinsics.checkNotNullParameter(klass, "klass");
                super.visitClass(klass);
                block.invoke(klass);
            }
        };
    }

    public static final KtVisitorVoid expressionVisitor(final Function1<? super KtExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.expressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitExpression(KtExpression expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                block.invoke(expression);
            }
        };
    }

    public static final KtTreeVisitorVoid expressionRecursiveVisitor(final Function1<? super KtExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.expressionRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitExpression(KtExpression expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                super.visitExpression(expression);
                block.invoke(expression);
            }
        };
    }

    public static final KtVisitorVoid parameterVisitor(final Function1<? super KtParameter, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.parameterVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitParameter(KtParameter parameter) {
                Intrinsics.checkNotNullParameter(parameter, "parameter");
                block.invoke(parameter);
            }
        };
    }

    public static final KtTreeVisitorVoid parameterRecursiveVisitor(final Function1<? super KtParameter, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.parameterRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitParameter(KtParameter parameter) {
                Intrinsics.checkNotNullParameter(parameter, "parameter");
                super.visitParameter(parameter);
                block.invoke(parameter);
            }
        };
    }

    public static final KtVisitorVoid propertyVisitor(final Function1<? super KtProperty, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.propertyVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitProperty(KtProperty property) {
                Intrinsics.checkNotNullParameter(property, "property");
                block.invoke(property);
            }
        };
    }

    public static final KtTreeVisitorVoid propertyRecursiveVisitor(final Function1<? super KtProperty, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.propertyRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitProperty(KtProperty property) {
                Intrinsics.checkNotNullParameter(property, "property");
                super.visitProperty(property);
                block.invoke(property);
            }
        };
    }

    public static final KtVisitorVoid ifExpressionVisitor(final Function1<? super KtIfExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.ifExpressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitIfExpression(KtIfExpression ifExpression) {
                Intrinsics.checkNotNullParameter(ifExpression, "ifExpression");
                block.invoke(ifExpression);
            }
        };
    }

    public static final KtTreeVisitorVoid ifExpressionRecursiveVisitor(final Function1<? super KtIfExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.ifExpressionRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitIfExpression(KtIfExpression ifExpression) {
                Intrinsics.checkNotNullParameter(ifExpression, "ifExpression");
                super.visitIfExpression(ifExpression);
                block.invoke(ifExpression);
            }
        };
    }

    public static final KtVisitorVoid callExpressionVisitor(final Function1<? super KtCallExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.callExpressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitCallExpression(KtCallExpression callExpression) {
                Intrinsics.checkNotNullParameter(callExpression, "callExpression");
                block.invoke(callExpression);
            }
        };
    }

    public static final KtTreeVisitorVoid callExpressionRecursiveVisitor(final Function1<? super KtCallExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.callExpressionRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitCallExpression(KtCallExpression callExpression) {
                Intrinsics.checkNotNullParameter(callExpression, "callExpression");
                super.visitCallExpression(callExpression);
                block.invoke(callExpression);
            }
        };
    }

    public static final KtVisitorVoid primaryConstructorVisitor(final Function1<? super KtPrimaryConstructor, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.primaryConstructorVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitPrimaryConstructor(KtPrimaryConstructor primaryConstructor) {
                Intrinsics.checkNotNullParameter(primaryConstructor, "primaryConstructor");
                block.invoke(primaryConstructor);
            }
        };
    }

    public static final KtTreeVisitorVoid primaryConstructorRecursiveVisitor(final Function1<? super KtPrimaryConstructor, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.primaryConstructorRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitPrimaryConstructor(KtPrimaryConstructor primaryConstructor) {
                Intrinsics.checkNotNullParameter(primaryConstructor, "primaryConstructor");
                super.visitPrimaryConstructor(primaryConstructor);
                block.invoke(primaryConstructor);
            }
        };
    }

    public static final KtVisitorVoid destructuringDeclarationVisitor(final Function1<? super KtDestructuringDeclaration, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.destructuringDeclarationVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitDestructuringDeclaration(KtDestructuringDeclaration destructuringDeclaration) {
                Intrinsics.checkNotNullParameter(destructuringDeclaration, "destructuringDeclaration");
                block.invoke(destructuringDeclaration);
            }
        };
    }

    public static final KtTreeVisitorVoid destructuringDeclarationRecursiveVisitor(final Function1<? super KtDestructuringDeclaration, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.destructuringDeclarationRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitDestructuringDeclaration(KtDestructuringDeclaration destructuringDeclaration) {
                Intrinsics.checkNotNullParameter(destructuringDeclaration, "destructuringDeclaration");
                super.visitDestructuringDeclaration(destructuringDeclaration);
                block.invoke(destructuringDeclaration);
            }
        };
    }

    public static final KtVisitorVoid dotQualifiedExpressionVisitor(final Function1<? super KtDotQualifiedExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.dotQualifiedExpressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitDotQualifiedExpression(KtDotQualifiedExpression dotQualifiedExpression) {
                Intrinsics.checkNotNullParameter(dotQualifiedExpression, "dotQualifiedExpression");
                block.invoke(dotQualifiedExpression);
            }
        };
    }

    public static final KtTreeVisitorVoid dotQualifiedExpressionRecursiveVisitor(final Function1<? super KtDotQualifiedExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.dotQualifiedExpressionRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitDotQualifiedExpression(KtDotQualifiedExpression dotQualifiedExpression) {
                Intrinsics.checkNotNullParameter(dotQualifiedExpression, "dotQualifiedExpression");
                super.visitDotQualifiedExpression(dotQualifiedExpression);
                block.invoke(dotQualifiedExpression);
            }
        };
    }

    public static final KtVisitorVoid prefixExpressionVisitor(final Function1<? super KtPrefixExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.prefixExpressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitPrefixExpression(KtPrefixExpression prefixExpression) {
                Intrinsics.checkNotNullParameter(prefixExpression, "prefixExpression");
                block.invoke(prefixExpression);
            }
        };
    }

    public static final KtTreeVisitorVoid prefixExpressionRecursiveVisitor(final Function1<? super KtPrefixExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.prefixExpressionRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitPrefixExpression(KtPrefixExpression prefixExpression) {
                Intrinsics.checkNotNullParameter(prefixExpression, "prefixExpression");
                super.visitPrefixExpression(prefixExpression);
                block.invoke(prefixExpression);
            }
        };
    }

    public static final KtTreeVisitorVoid typeReferenceRecursiveVisitor(final Function1<? super KtTypeReference, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.typeReferenceRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitTypeReference(KtTypeReference typeReference) {
                Intrinsics.checkNotNullParameter(typeReference, "typeReference");
                super.visitTypeReference(typeReference);
                block.invoke(typeReference);
            }
        };
    }

    public static final KtVisitorVoid namedFunctionVisitor(final Function1<? super KtNamedFunction, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.namedFunctionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitNamedFunction(KtNamedFunction namedFunction) {
                Intrinsics.checkNotNullParameter(namedFunction, "namedFunction");
                block.invoke(namedFunction);
            }
        };
    }

    public static final KtTreeVisitorVoid namedFunctionRecursiveVisitor(final Function1<? super KtNamedFunction, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.namedFunctionRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitNamedFunction(KtNamedFunction namedFunction) {
                Intrinsics.checkNotNullParameter(namedFunction, "namedFunction");
                super.visitNamedFunction(namedFunction);
                block.invoke(namedFunction);
            }
        };
    }

    public static final KtVisitorVoid annotationEntryVisitor(final Function1<? super KtAnnotationEntry, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.annotationEntryVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitAnnotationEntry(KtAnnotationEntry annotationEntry) {
                Intrinsics.checkNotNullParameter(annotationEntry, "annotationEntry");
                block.invoke(annotationEntry);
            }
        };
    }

    public static final KtTreeVisitorVoid annotationEntryRecursiveVisitor(final Function1<? super KtAnnotationEntry, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.annotationEntryRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitAnnotationEntry(KtAnnotationEntry annotationEntry) {
                Intrinsics.checkNotNullParameter(annotationEntry, "annotationEntry");
                super.visitAnnotationEntry(annotationEntry);
                block.invoke(annotationEntry);
            }
        };
    }

    public static final KtVisitorVoid lambdaExpressionVisitor(final Function1<? super KtLambdaExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.lambdaExpressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitLambdaExpression(KtLambdaExpression lambdaExpression) {
                Intrinsics.checkNotNullParameter(lambdaExpression, "lambdaExpression");
                block.invoke(lambdaExpression);
            }
        };
    }

    public static final KtTreeVisitorVoid lambdaExpressionRecursiveVisitor(final Function1<? super KtLambdaExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.lambdaExpressionRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitLambdaExpression(KtLambdaExpression lambdaExpression) {
                Intrinsics.checkNotNullParameter(lambdaExpression, "lambdaExpression");
                super.visitLambdaExpression(lambdaExpression);
                block.invoke(lambdaExpression);
            }
        };
    }

    public static final KtVisitorVoid enumEntryVisitor(final Function1<? super KtEnumEntry, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.enumEntryVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitEnumEntry(KtEnumEntry enumEntry) {
                Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
                block.invoke(enumEntry);
            }
        };
    }

    public static final KtTreeVisitorVoid enumEntryRecursiveVisitor(final Function1<? super KtEnumEntry, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.enumEntryRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitEnumEntry(KtEnumEntry enumEntry) {
                Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
                super.visitEnumEntry(enumEntry);
                block.invoke(enumEntry);
            }
        };
    }

    public static final KtVisitorVoid packageDirectiveVisitor(final Function1<? super KtPackageDirective, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.packageDirectiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitPackageDirective(KtPackageDirective packageDirective) {
                Intrinsics.checkNotNullParameter(packageDirective, "packageDirective");
                block.invoke(packageDirective);
            }
        };
    }

    public static final KtTreeVisitorVoid packageDirectiveRecursiveVisitor(final Function1<? super KtPackageDirective, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.packageDirectiveRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitPackageDirective(KtPackageDirective packageDirective) {
                Intrinsics.checkNotNullParameter(packageDirective, "packageDirective");
                super.visitPackageDirective(packageDirective);
                block.invoke(packageDirective);
            }
        };
    }

    public static final KtVisitorVoid binaryExpressionVisitor(final Function1<? super KtBinaryExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.binaryExpressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitBinaryExpression(KtBinaryExpression binaryExpression) {
                Intrinsics.checkNotNullParameter(binaryExpression, "binaryExpression");
                block.invoke(binaryExpression);
            }
        };
    }

    public static final KtVisitorVoid binaryWithTypeRHSExpressionVisitor(final Function1<? super KtBinaryExpressionWithTypeRHS, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.binaryWithTypeRHSExpressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                block.invoke(expression);
            }
        };
    }

    public static final KtTreeVisitorVoid binaryExpressionRecursiveVisitor(final Function1<? super KtBinaryExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.binaryExpressionRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitBinaryExpression(KtBinaryExpression binaryExpression) {
                Intrinsics.checkNotNullParameter(binaryExpression, "binaryExpression");
                super.visitBinaryExpression(binaryExpression);
                block.invoke(binaryExpression);
            }
        };
    }

    public static final KtVisitorVoid declarationVisitor(final Function1<? super KtDeclaration, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.declarationVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitDeclaration(KtDeclaration declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                block.invoke(declaration);
            }
        };
    }

    public static final KtTreeVisitorVoid declarationRecursiveVisitor(final Function1<? super KtDeclaration, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.declarationRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitDeclaration(KtDeclaration declaration) {
                Intrinsics.checkNotNullParameter(declaration, "declaration");
                super.visitDeclaration(declaration);
                block.invoke(declaration);
            }
        };
    }

    public static final KtVisitorVoid simpleNameExpressionVisitor(final Function1<? super KtSimpleNameExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.simpleNameExpressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitSimpleNameExpression(KtSimpleNameExpression simpleNameExpression) {
                Intrinsics.checkNotNullParameter(simpleNameExpression, "simpleNameExpression");
                block.invoke(simpleNameExpression);
            }
        };
    }

    public static final KtTreeVisitorVoid simpleNameExpressionRecursiveVisitor(final Function1<? super KtSimpleNameExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.simpleNameExpressionRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitSimpleNameExpression(KtSimpleNameExpression simpleNameExpression) {
                Intrinsics.checkNotNullParameter(simpleNameExpression, "simpleNameExpression");
                super.visitSimpleNameExpression(simpleNameExpression);
                block.invoke(simpleNameExpression);
            }
        };
    }

    public static final KtVisitorVoid propertyAccessorVisitor(final Function1<? super KtPropertyAccessor, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.propertyAccessorVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitPropertyAccessor(KtPropertyAccessor propertyAccessor) {
                Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
                block.invoke(propertyAccessor);
            }
        };
    }

    public static final KtTreeVisitorVoid propertyAccessorRecursiveVisitor(final Function1<? super KtPropertyAccessor, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.propertyAccessorRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitPropertyAccessor(KtPropertyAccessor propertyAccessor) {
                Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
                super.visitPropertyAccessor(propertyAccessor);
                block.invoke(propertyAccessor);
            }
        };
    }

    public static final KtVisitorVoid referenceExpressionVisitor(final Function1<? super KtReferenceExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.referenceExpressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitReferenceExpression(KtReferenceExpression referenceExpression) {
                Intrinsics.checkNotNullParameter(referenceExpression, "referenceExpression");
                block.invoke(referenceExpression);
            }
        };
    }

    public static final KtTreeVisitorVoid referenceExpressionRecursiveVisitor(final Function1<? super KtReferenceExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.referenceExpressionRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitReferenceExpression(KtReferenceExpression referenceExpression) {
                Intrinsics.checkNotNullParameter(referenceExpression, "referenceExpression");
                super.visitReferenceExpression(referenceExpression);
                block.invoke(referenceExpression);
            }
        };
    }

    public static final KtVisitorVoid valueArgumentListVisitor(final Function1<? super KtValueArgumentList, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.valueArgumentListVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitValueArgumentList(KtValueArgumentList list) {
                Intrinsics.checkNotNullParameter(list, "list");
                block.invoke(list);
            }
        };
    }

    public static final KtVisitorVoid valueArgumentVisitor(final Function1<? super KtValueArgument, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.valueArgumentVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitArgument(KtValueArgument valueArgument) {
                Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
                block.invoke(valueArgument);
            }
        };
    }

    public static final KtTreeVisitorVoid valueArgumentRecursiveVisitor(final Function1<? super KtValueArgument, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.valueArgumentRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitArgument(KtValueArgument valueArgument) {
                Intrinsics.checkNotNullParameter(valueArgument, "valueArgument");
                super.visitArgument(valueArgument);
                block.invoke(valueArgument);
            }
        };
    }

    public static final KtVisitorVoid whenExpressionVisitor(final Function1<? super KtWhenExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.whenExpressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitWhenExpression(KtWhenExpression whenExpression) {
                Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
                block.invoke(whenExpression);
            }
        };
    }

    public static final KtTreeVisitorVoid whenExpressionRecursiveVisitor(final Function1<? super KtWhenExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.whenExpressionRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitWhenExpression(KtWhenExpression whenExpression) {
                Intrinsics.checkNotNullParameter(whenExpression, "whenExpression");
                super.visitWhenExpression(whenExpression);
                block.invoke(whenExpression);
            }
        };
    }

    public static final KtVisitorVoid modifierListVisitor(final Function1<? super KtModifierList, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.modifierListVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitModifierList(KtModifierList modifierList) {
                Intrinsics.checkNotNullParameter(modifierList, "modifierList");
                block.invoke(modifierList);
            }
        };
    }

    public static final KtTreeVisitorVoid modifierListRecursiveVisitor(final Function1<? super KtModifierList, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.modifierListRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitModifierList(KtModifierList modifierList) {
                Intrinsics.checkNotNullParameter(modifierList, "modifierList");
                super.visitModifierList(modifierList);
                block.invoke(modifierList);
            }
        };
    }

    public static final KtVisitorVoid namedDeclarationVisitor(final Function1<? super KtNamedDeclaration, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.namedDeclarationVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitNamedDeclaration(KtNamedDeclaration namedDeclaration) {
                Intrinsics.checkNotNullParameter(namedDeclaration, "namedDeclaration");
                block.invoke(namedDeclaration);
            }
        };
    }

    public static final KtTreeVisitorVoid namedDeclarationRecursiveVisitor(final Function1<? super KtNamedDeclaration, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.namedDeclarationRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitNamedDeclaration(KtNamedDeclaration namedDeclaration) {
                Intrinsics.checkNotNullParameter(namedDeclaration, "namedDeclaration");
                super.visitNamedDeclaration(namedDeclaration);
                block.invoke(namedDeclaration);
            }
        };
    }

    public static final KtVisitorVoid qualifiedExpressionVisitor(final Function1<? super KtQualifiedExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.qualifiedExpressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitQualifiedExpression(KtQualifiedExpression qualifiedExpression) {
                Intrinsics.checkNotNullParameter(qualifiedExpression, "qualifiedExpression");
                block.invoke(qualifiedExpression);
            }
        };
    }

    public static final KtTreeVisitorVoid qualifiedExpressionRecursiveVisitor(final Function1<? super KtQualifiedExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtTreeVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.qualifiedExpressionRecursiveVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitQualifiedExpression(KtQualifiedExpression qualifiedExpression) {
                Intrinsics.checkNotNullParameter(qualifiedExpression, "qualifiedExpression");
                super.visitQualifiedExpression(qualifiedExpression);
                block.invoke(qualifiedExpression);
            }
        };
    }

    public static final KtVisitorVoid returnExpressionVisitor(final Function1<? super KtReturnExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.returnExpressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitReturnExpression(KtReturnExpression returnExpression) {
                Intrinsics.checkNotNullParameter(returnExpression, "returnExpression");
                super.visitReturnExpression(returnExpression);
                block.invoke(returnExpression);
            }
        };
    }

    public static final KtVisitorVoid delegatedSuperTypeEntry(final Function1<? super KtDelegatedSuperTypeEntry, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.delegatedSuperTypeEntry.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitDelegatedSuperTypeEntry(KtDelegatedSuperTypeEntry delegatedSuperTypeEntry) {
                Intrinsics.checkNotNullParameter(delegatedSuperTypeEntry, "delegatedSuperTypeEntry");
                super.visitDelegatedSuperTypeEntry(delegatedSuperTypeEntry);
                block.invoke(delegatedSuperTypeEntry);
            }
        };
    }

    public static final KtVisitorVoid postfixExpressionVisitor(final Function1<? super KtPostfixExpression, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.postfixExpressionVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitPostfixExpression(KtPostfixExpression expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                super.visitPostfixExpression(expression);
                block.invoke(expression);
            }
        };
    }

    public static final KtVisitorVoid classInitializerVisitor(final Function1<? super KtClassInitializer, Unit> block) {
        Intrinsics.checkNotNullParameter(block, "block");
        return new KtVisitorVoid() { // from class: org.jetbrains.kotlin.psi.VisitorWrappersKt.classInitializerVisitor.1
            @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
            public void visitClassInitializer(KtClassInitializer expression) {
                Intrinsics.checkNotNullParameter(expression, "expression");
                super.visitClassInitializer(expression);
                block.invoke(expression);
            }
        };
    }
}
