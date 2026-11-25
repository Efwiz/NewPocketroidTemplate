package org.jetbrains.kotlin.types.expressions;

import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProcessCanceledException;
import org.jetbrains.kotlin.com.intellij.openapi.progress.ProgressManager;
import org.jetbrains.kotlin.com.intellij.openapi.project.IndexNotReadyException;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.diagnostics.PsiDiagnosticUtils;
import org.jetbrains.kotlin.incremental.components.LookupTracker;
import org.jetbrains.kotlin.psi.KtAnnotatedExpression;
import org.jetbrains.kotlin.psi.KtArrayAccessExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpression;
import org.jetbrains.kotlin.psi.KtBinaryExpressionWithTypeRHS;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtBreakExpression;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtCallableReferenceExpression;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtConstantExpression;
import org.jetbrains.kotlin.psi.KtContinueExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDoWhileExpression;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtForExpression;
import org.jetbrains.kotlin.psi.KtIfExpression;
import org.jetbrains.kotlin.psi.KtIsExpression;
import org.jetbrains.kotlin.psi.KtLabeledExpression;
import org.jetbrains.kotlin.psi.KtLambdaExpression;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectLiteralExpression;
import org.jetbrains.kotlin.psi.KtParenthesizedExpression;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtReturnExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtStringTemplateExpression;
import org.jetbrains.kotlin.psi.KtSuperExpression;
import org.jetbrains.kotlin.psi.KtThisExpression;
import org.jetbrains.kotlin.psi.KtThrowExpression;
import org.jetbrains.kotlin.psi.KtTryExpression;
import org.jetbrains.kotlin.psi.KtUnaryExpression;
import org.jetbrains.kotlin.psi.KtVisitor;
import org.jetbrains.kotlin.psi.KtWhenExpression;
import org.jetbrains.kotlin.psi.KtWhileExpression;
import org.jetbrains.kotlin.psi.ValueArgument;
import org.jetbrains.kotlin.psi.codeFragmentUtil.CodeFragmentUtilKt;
import org.jetbrains.kotlin.resolve.AnnotationChecker;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingContextUtils;
import org.jetbrains.kotlin.resolve.DeclarationsCheckerBuilder;
import org.jetbrains.kotlin.resolve.bindingContextUtil.BindingContextUtilsKt;
import org.jetbrains.kotlin.resolve.calls.context.CallPosition;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.LexicalWritableScope;
import org.jetbrains.kotlin.types.DeferredType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.error.ErrorTypeKind;
import org.jetbrains.kotlin.types.error.ErrorUtils;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.types.model.KotlinTypeMarker;
import org.jetbrains.kotlin.util.IncrementalTrackerUtilKt;
import org.jetbrains.kotlin.util.KotlinFrontEndException;
import org.jetbrains.kotlin.util.PerformanceCounter;
import org.jetbrains.kotlin.util.ReenteringLazyValueComputationException;
import org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments;

/* JADX INFO: loaded from: classes6.dex */
public abstract class ExpressionTypingVisitorDispatcher extends KtVisitor<KotlinTypeInfo, ExpressionTypingContext> implements ExpressionTypingInternals {
    private final AnnotationChecker annotationChecker;
    protected final BasicExpressionTypingVisitor basic;
    private final ExpressionTypingComponents components;
    protected final ControlStructureTypingVisitor controlStructures;
    protected final DeclarationsCheckerBuilder declarationsCheckerBuilder;
    protected final FunctionsTypingVisitor functions;
    protected final PatternMatchingTypingVisitor patterns;
    public static final PerformanceCounter typeInfoPerfCounter = PerformanceCounter.INSTANCE.create("Type info", true);
    private static final Logger LOG = Logger.getInstance((Class<?>) ExpressionTypingVisitor.class);

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        String str = (i == 2 || i == 7 || i == 12 || i == 16 || i == 9 || i == 10) ? "@NotNull method %s.%s must not return null" : "Argument for @NotNull parameter '%s' of %s.%s must not be null";
        Object[] objArr = new Object[(i == 2 || i == 7 || i == 12 || i == 16 || i == 9 || i == 10) ? 2 : 3];
        switch (i) {
            case 1:
                objArr[0] = "annotationChecker";
                break;
            case 2:
            case 7:
            case 9:
            case 10:
            case 12:
            case 16:
                objArr[0] = "org/jetbrains/kotlin/types/expressions/ExpressionTypingVisitorDispatcher";
                break;
            case 3:
                objArr[0] = "callElement";
                break;
            case 4:
                objArr[0] = "operationSign";
                break;
            case 5:
                objArr[0] = "leftArgument";
                break;
            case 6:
                objArr[0] = "context";
                break;
            case 8:
            case 11:
            case 13:
            case 14:
            case 15:
            case 17:
            case 19:
            case 20:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 39:
            case 40:
            case 41:
            case 42:
            case 43:
            case 44:
            case 45:
            case 46:
            case 47:
            case 48:
            case 52:
            case 53:
                objArr[0] = "expression";
                break;
            case 18:
                objArr[0] = "typeInfo";
                break;
            case 21:
                objArr[0] = "function";
                break;
            case 49:
                objArr[0] = "dcl";
                break;
            case 50:
                objArr[0] = "klass";
                break;
            case 51:
                objArr[0] = "property";
                break;
            case 54:
                objArr[0] = CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD;
                break;
            default:
                objArr[0] = "components";
                break;
        }
        if (i == 2) {
            objArr[1] = "getComponents";
        } else if (i == 7) {
            objArr[1] = "checkInExpression";
        } else if (i == 12 || i == 16) {
            objArr[1] = "getTypeInfo";
        } else if (i == 9 || i == 10) {
            objArr[1] = "safeGetTypeInfo";
        } else {
            objArr[1] = "org/jetbrains/kotlin/types/expressions/ExpressionTypingVisitorDispatcher";
        }
        switch (i) {
            case 2:
            case 7:
            case 9:
            case 10:
            case 12:
            case 16:
                break;
            case 3:
            case 4:
            case 5:
            case 6:
                objArr[2] = "checkInExpression";
                break;
            case 8:
                objArr[2] = "safeGetTypeInfo";
                break;
            case 11:
            case 13:
            case 15:
                objArr[2] = "getTypeInfo";
                break;
            case 14:
                objArr[2] = "checkStatementType";
                break;
            case 17:
            case 18:
                objArr[2] = "recordTypeInfo";
                break;
            case 19:
                objArr[2] = "logOrThrowException";
                break;
            case 20:
                objArr[2] = "visitLambdaExpression";
                break;
            case 21:
                objArr[2] = "visitNamedFunction";
                break;
            case 22:
                objArr[2] = "visitThrowExpression";
                break;
            case 23:
                objArr[2] = "visitReturnExpression";
                break;
            case 24:
                objArr[2] = "visitContinueExpression";
                break;
            case 25:
                objArr[2] = "visitIfExpression";
                break;
            case 26:
                objArr[2] = "visitTryExpression";
                break;
            case 27:
                objArr[2] = "visitForExpression";
                break;
            case 28:
                objArr[2] = "visitWhileExpression";
                break;
            case 29:
                objArr[2] = "visitDoWhileExpression";
                break;
            case 30:
                objArr[2] = "visitBreakExpression";
                break;
            case 31:
                objArr[2] = "visitIsExpression";
                break;
            case 32:
                objArr[2] = "visitWhenExpression";
                break;
            case 33:
                objArr[2] = "visitSimpleNameExpression";
                break;
            case 34:
                objArr[2] = "visitParenthesizedExpression";
                break;
            case 35:
                objArr[2] = "visitConstantExpression";
                break;
            case 36:
                objArr[2] = "visitBinaryWithTypeRHSExpression";
                break;
            case 37:
                objArr[2] = "visitThisExpression";
                break;
            case 38:
                objArr[2] = "visitSuperExpression";
                break;
            case 39:
                objArr[2] = "visitBlockExpression";
                break;
            case 40:
                objArr[2] = "visitClassLiteralExpression";
                break;
            case 41:
                objArr[2] = "visitCallableReferenceExpression";
                break;
            case 42:
                objArr[2] = "visitObjectLiteralExpression";
                break;
            case 43:
                objArr[2] = "visitQualifiedExpression";
                break;
            case 44:
                objArr[2] = "visitCallExpression";
                break;
            case 45:
                objArr[2] = "visitUnaryExpression";
                break;
            case 46:
                objArr[2] = "visitLabeledExpression";
                break;
            case 47:
                objArr[2] = "visitBinaryExpression";
                break;
            case 48:
                objArr[2] = "visitArrayAccessExpression";
                break;
            case 49:
                objArr[2] = "visitDeclaration";
                break;
            case 50:
                objArr[2] = "visitClass";
                break;
            case 51:
                objArr[2] = "visitProperty";
                break;
            case 52:
                objArr[2] = "visitStringTemplateExpression";
                break;
            case 53:
                objArr[2] = "visitAnnotatedExpression";
                break;
            case 54:
                objArr[2] = "visitKtElement";
                break;
            default:
                objArr[2] = "<init>";
                break;
        }
        String str2 = String.format(str, objArr);
        if (i != 2 && i != 7 && i != 12 && i != 16 && i != 9 && i != 10) {
            throw new IllegalArgumentException(str2);
        }
        throw new IllegalStateException(str2);
    }

    protected abstract ExpressionTypingVisitorForStatements getStatementVisitor(ExpressionTypingContext expressionTypingContext);

    public static class ForDeclarations extends ExpressionTypingVisitorDispatcher {
        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "annotationChecker";
            } else if (i != 2) {
                objArr[0] = "components";
            } else {
                objArr[0] = "context";
            }
            objArr[1] = "org/jetbrains/kotlin/types/expressions/ExpressionTypingVisitorDispatcher$ForDeclarations";
            if (i != 2) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "getStatementVisitor";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitAnnotatedExpression(KtAnnotatedExpression ktAnnotatedExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitAnnotatedExpression(ktAnnotatedExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitArrayAccessExpression(KtArrayAccessExpression ktArrayAccessExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitArrayAccessExpression(ktArrayAccessExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitBinaryExpression(KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitBinaryExpression(ktBinaryExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS, ExpressionTypingContext expressionTypingContext) {
            return super.visitBinaryWithTypeRHSExpression(ktBinaryExpressionWithTypeRHS, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitBlockExpression(KtBlockExpression ktBlockExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitBlockExpression(ktBlockExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitBreakExpression(KtBreakExpression ktBreakExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitBreakExpression(ktBreakExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitCallExpression(KtCallExpression ktCallExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitCallExpression(ktCallExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitCallableReferenceExpression(KtCallableReferenceExpression ktCallableReferenceExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitCallableReferenceExpression(ktCallableReferenceExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitClass(KtClass ktClass, ExpressionTypingContext expressionTypingContext) {
            return super.visitClass(ktClass, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitClassLiteralExpression(KtClassLiteralExpression ktClassLiteralExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitClassLiteralExpression(ktClassLiteralExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitConstantExpression(KtConstantExpression ktConstantExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitConstantExpression(ktConstantExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitContinueExpression(KtContinueExpression ktContinueExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitContinueExpression(ktContinueExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitDeclaration(KtDeclaration ktDeclaration, ExpressionTypingContext expressionTypingContext) {
            return super.visitDeclaration(ktDeclaration, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitDoWhileExpression(KtDoWhileExpression ktDoWhileExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitDoWhileExpression(ktDoWhileExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitForExpression(KtForExpression ktForExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitForExpression(ktForExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitIfExpression(KtIfExpression ktIfExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitIfExpression(ktIfExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitIsExpression(KtIsExpression ktIsExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitIsExpression(ktIsExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitKtElement(KtElement ktElement, ExpressionTypingContext expressionTypingContext) {
            return super.visitKtElement(ktElement, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitLabeledExpression(KtLabeledExpression ktLabeledExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitLabeledExpression(ktLabeledExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitLambdaExpression(KtLambdaExpression ktLambdaExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitLambdaExpression(ktLambdaExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitNamedFunction(KtNamedFunction ktNamedFunction, ExpressionTypingContext expressionTypingContext) {
            return super.visitNamedFunction(ktNamedFunction, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitObjectLiteralExpression(KtObjectLiteralExpression ktObjectLiteralExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitObjectLiteralExpression(ktObjectLiteralExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitParenthesizedExpression(KtParenthesizedExpression ktParenthesizedExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitParenthesizedExpression(ktParenthesizedExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitProperty(KtProperty ktProperty, ExpressionTypingContext expressionTypingContext) {
            return super.visitProperty(ktProperty, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitQualifiedExpression(KtQualifiedExpression ktQualifiedExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitQualifiedExpression(ktQualifiedExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitReturnExpression(KtReturnExpression ktReturnExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitReturnExpression(ktReturnExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitSimpleNameExpression(KtSimpleNameExpression ktSimpleNameExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitSimpleNameExpression(ktSimpleNameExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitStringTemplateExpression(KtStringTemplateExpression ktStringTemplateExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitStringTemplateExpression(ktStringTemplateExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitSuperExpression(KtSuperExpression ktSuperExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitSuperExpression(ktSuperExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitThisExpression(KtThisExpression ktThisExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitThisExpression(ktThisExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitThrowExpression(KtThrowExpression ktThrowExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitThrowExpression(ktThrowExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitTryExpression(KtTryExpression ktTryExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitTryExpression(ktTryExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitUnaryExpression(KtUnaryExpression ktUnaryExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitUnaryExpression(ktUnaryExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitWhenExpression(KtWhenExpression ktWhenExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitWhenExpression(ktWhenExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitWhileExpression(KtWhileExpression ktWhileExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitWhileExpression(ktWhileExpression, expressionTypingContext);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ForDeclarations(ExpressionTypingComponents expressionTypingComponents, AnnotationChecker annotationChecker) {
            super(expressionTypingComponents, annotationChecker);
            if (expressionTypingComponents == null) {
                $$$reportNull$$$0(0);
            }
            if (annotationChecker == null) {
                $$$reportNull$$$0(1);
            }
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher
        protected ExpressionTypingVisitorForStatements getStatementVisitor(ExpressionTypingContext expressionTypingContext) {
            if (expressionTypingContext == null) {
                $$$reportNull$$$0(2);
            }
            return createStatementVisitor(expressionTypingContext);
        }
    }

    public static class ForBlock extends ExpressionTypingVisitorDispatcher {
        private final ExpressionTypingVisitorForStatements visitorForBlock;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            Object[] objArr = new Object[3];
            if (i == 1) {
                objArr[0] = "annotationChecker";
            } else if (i == 2) {
                objArr[0] = "writableScope";
            } else if (i != 3) {
                objArr[0] = "components";
            } else {
                objArr[0] = "context";
            }
            objArr[1] = "org/jetbrains/kotlin/types/expressions/ExpressionTypingVisitorDispatcher$ForBlock";
            if (i != 3) {
                objArr[2] = "<init>";
            } else {
                objArr[2] = "getStatementVisitor";
            }
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", objArr));
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitAnnotatedExpression(KtAnnotatedExpression ktAnnotatedExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitAnnotatedExpression(ktAnnotatedExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitArrayAccessExpression(KtArrayAccessExpression ktArrayAccessExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitArrayAccessExpression(ktArrayAccessExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitBinaryExpression(KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitBinaryExpression(ktBinaryExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS, ExpressionTypingContext expressionTypingContext) {
            return super.visitBinaryWithTypeRHSExpression(ktBinaryExpressionWithTypeRHS, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitBlockExpression(KtBlockExpression ktBlockExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitBlockExpression(ktBlockExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitBreakExpression(KtBreakExpression ktBreakExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitBreakExpression(ktBreakExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitCallExpression(KtCallExpression ktCallExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitCallExpression(ktCallExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitCallableReferenceExpression(KtCallableReferenceExpression ktCallableReferenceExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitCallableReferenceExpression(ktCallableReferenceExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitClass(KtClass ktClass, ExpressionTypingContext expressionTypingContext) {
            return super.visitClass(ktClass, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitClassLiteralExpression(KtClassLiteralExpression ktClassLiteralExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitClassLiteralExpression(ktClassLiteralExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitConstantExpression(KtConstantExpression ktConstantExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitConstantExpression(ktConstantExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitContinueExpression(KtContinueExpression ktContinueExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitContinueExpression(ktContinueExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitDeclaration(KtDeclaration ktDeclaration, ExpressionTypingContext expressionTypingContext) {
            return super.visitDeclaration(ktDeclaration, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitDoWhileExpression(KtDoWhileExpression ktDoWhileExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitDoWhileExpression(ktDoWhileExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitForExpression(KtForExpression ktForExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitForExpression(ktForExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitIfExpression(KtIfExpression ktIfExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitIfExpression(ktIfExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitIsExpression(KtIsExpression ktIsExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitIsExpression(ktIsExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitKtElement(KtElement ktElement, ExpressionTypingContext expressionTypingContext) {
            return super.visitKtElement(ktElement, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitLabeledExpression(KtLabeledExpression ktLabeledExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitLabeledExpression(ktLabeledExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitLambdaExpression(KtLambdaExpression ktLambdaExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitLambdaExpression(ktLambdaExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitNamedFunction(KtNamedFunction ktNamedFunction, ExpressionTypingContext expressionTypingContext) {
            return super.visitNamedFunction(ktNamedFunction, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitObjectLiteralExpression(KtObjectLiteralExpression ktObjectLiteralExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitObjectLiteralExpression(ktObjectLiteralExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitParenthesizedExpression(KtParenthesizedExpression ktParenthesizedExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitParenthesizedExpression(ktParenthesizedExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitProperty(KtProperty ktProperty, ExpressionTypingContext expressionTypingContext) {
            return super.visitProperty(ktProperty, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitQualifiedExpression(KtQualifiedExpression ktQualifiedExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitQualifiedExpression(ktQualifiedExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitReturnExpression(KtReturnExpression ktReturnExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitReturnExpression(ktReturnExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitSimpleNameExpression(KtSimpleNameExpression ktSimpleNameExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitSimpleNameExpression(ktSimpleNameExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitStringTemplateExpression(KtStringTemplateExpression ktStringTemplateExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitStringTemplateExpression(ktStringTemplateExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitSuperExpression(KtSuperExpression ktSuperExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitSuperExpression(ktSuperExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitThisExpression(KtThisExpression ktThisExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitThisExpression(ktThisExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitThrowExpression(KtThrowExpression ktThrowExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitThrowExpression(ktThrowExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitTryExpression(KtTryExpression ktTryExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitTryExpression(ktTryExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitUnaryExpression(KtUnaryExpression ktUnaryExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitUnaryExpression(ktUnaryExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitWhenExpression(KtWhenExpression ktWhenExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitWhenExpression(ktWhenExpression, expressionTypingContext);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher, org.jetbrains.kotlin.psi.KtVisitor
        public /* bridge */ /* synthetic */ KotlinTypeInfo visitWhileExpression(KtWhileExpression ktWhileExpression, ExpressionTypingContext expressionTypingContext) {
            return super.visitWhileExpression(ktWhileExpression, expressionTypingContext);
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ForBlock(ExpressionTypingComponents expressionTypingComponents, AnnotationChecker annotationChecker, LexicalWritableScope lexicalWritableScope) {
            super(expressionTypingComponents, annotationChecker);
            if (expressionTypingComponents == null) {
                $$$reportNull$$$0(0);
            }
            if (annotationChecker == null) {
                $$$reportNull$$$0(1);
            }
            if (lexicalWritableScope == null) {
                $$$reportNull$$$0(2);
            }
            this.visitorForBlock = new ExpressionTypingVisitorForStatements(this, lexicalWritableScope, this.basic, this.controlStructures, this.patterns, this.functions);
        }

        @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher
        protected ExpressionTypingVisitorForStatements getStatementVisitor(ExpressionTypingContext expressionTypingContext) {
            if (expressionTypingContext == null) {
                $$$reportNull$$$0(3);
            }
            return this.visitorForBlock;
        }
    }

    private ExpressionTypingVisitorDispatcher(ExpressionTypingComponents expressionTypingComponents, AnnotationChecker annotationChecker) {
        if (expressionTypingComponents == null) {
            $$$reportNull$$$0(0);
        }
        if (annotationChecker == null) {
            $$$reportNull$$$0(1);
        }
        this.components = expressionTypingComponents;
        this.annotationChecker = annotationChecker;
        this.basic = new BasicExpressionTypingVisitor(this);
        this.controlStructures = new ControlStructureTypingVisitor(this);
        this.patterns = new PatternMatchingTypingVisitor(this);
        this.functions = new FunctionsTypingVisitor(this);
        this.declarationsCheckerBuilder = expressionTypingComponents.declarationsCheckerBuilder;
    }

    @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingInternals
    public ExpressionTypingComponents getComponents() {
        ExpressionTypingComponents expressionTypingComponents = this.components;
        if (expressionTypingComponents == null) {
            $$$reportNull$$$0(2);
        }
        return expressionTypingComponents;
    }

    @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingInternals
    public KotlinTypeInfo checkInExpression(KtElement ktElement, KtSimpleNameExpression ktSimpleNameExpression, ValueArgument valueArgument, KtExpression ktExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktElement == null) {
            $$$reportNull$$$0(3);
        }
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(4);
        }
        if (valueArgument == null) {
            $$$reportNull$$$0(5);
        }
        if (expressionTypingContext == null) {
            $$$reportNull$$$0(6);
        }
        KotlinTypeInfo kotlinTypeInfoCheckInExpression = this.basic.checkInExpression(ktElement, ktSimpleNameExpression, valueArgument, ktExpression, expressionTypingContext);
        if (kotlinTypeInfoCheckInExpression == null) {
            $$$reportNull$$$0(7);
        }
        return kotlinTypeInfoCheckInExpression;
    }

    @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingFacade
    public final KotlinTypeInfo safeGetTypeInfo(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(8);
        }
        KotlinTypeInfo typeInfo = getTypeInfo(ktExpression, expressionTypingContext);
        if (typeInfo.getType() != null) {
            if (typeInfo == null) {
                $$$reportNull$$$0(9);
            }
            return typeInfo;
        }
        KotlinTypeInfo kotlinTypeInfoReplaceDataFlowInfo = typeInfo.replaceType(ErrorUtils.createErrorType(ErrorTypeKind.NO_RECORDED_TYPE, ktExpression.getText())).replaceDataFlowInfo(expressionTypingContext.dataFlowInfo);
        if (kotlinTypeInfoReplaceDataFlowInfo == null) {
            $$$reportNull$$$0(10);
        }
        return kotlinTypeInfoReplaceDataFlowInfo;
    }

    @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingFacade
    public final KotlinTypeInfo getTypeInfo(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(11);
        }
        KotlinTypeInfo typeInfo = getTypeInfo(ktExpression, expressionTypingContext, this);
        this.annotationChecker.checkExpression(ktExpression, expressionTypingContext.trace);
        if (typeInfo == null) {
            $$$reportNull$$$0(12);
        }
        return typeInfo;
    }

    @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingFacade
    public final KotlinTypeInfo getTypeInfo(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext, boolean z) {
        if (ktExpression == null) {
            $$$reportNull$$$0(13);
        }
        if (CodeFragmentUtilKt.suppressDiagnosticsInDebugMode(ktExpression)) {
            expressionTypingContext = ExpressionTypingContext.newContext(expressionTypingContext, true);
        }
        if (!z) {
            return getTypeInfo(ktExpression, expressionTypingContext);
        }
        return getTypeInfo(ktExpression, expressionTypingContext, getStatementVisitor(expressionTypingContext));
    }

    protected ExpressionTypingVisitorForStatements createStatementVisitor(ExpressionTypingContext expressionTypingContext) {
        return new ExpressionTypingVisitorForStatements(this, ExpressionTypingUtils.newWritableScopeImpl(expressionTypingContext, LexicalScopeKind.CODE_BLOCK, this.components.overloadChecker), this.basic, this.controlStructures, this.patterns, this.functions);
    }

    @Override // org.jetbrains.kotlin.types.expressions.ExpressionTypingInternals
    public void checkStatementType(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktExpression == null) {
            $$$reportNull$$$0(14);
        }
        ktExpression.accept(createStatementVisitor(expressionTypingContext), expressionTypingContext);
    }

    private KotlinTypeInfo getTypeInfo(final KtExpression ktExpression, final ExpressionTypingContext expressionTypingContext, final KtVisitor<KotlinTypeInfo, ExpressionTypingContext> ktVisitor) {
        if (ktExpression == null) {
            $$$reportNull$$$0(15);
        }
        ProgressManager.checkCanceled();
        KotlinTypeInfo kotlinTypeInfo = (KotlinTypeInfo) typeInfoPerfCounter.time(new Function0() { // from class: org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return this.f$0.m7481x7ac75a4b(ktExpression, expressionTypingContext, ktVisitor);
            }
        });
        if (kotlinTypeInfo == null) {
            $$$reportNull$$$0(16);
        }
        return kotlinTypeInfo;
    }

    /* JADX INFO: renamed from: lambda$getTypeInfo$0$org-jetbrains-kotlin-types-expressions-ExpressionTypingVisitorDispatcher, reason: not valid java name */
    /* synthetic */ KotlinTypeInfo m7481x7ac75a4b(KtExpression ktExpression, ExpressionTypingContext expressionTypingContext, KtVisitor ktVisitor) {
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo;
        try {
            KotlinTypeInfo recordedTypeInfo = BindingContextUtils.getRecordedTypeInfo(ktExpression, expressionTypingContext.trace.getContext());
            if (recordedTypeInfo != null) {
                return recordedTypeInfo;
            }
            expressionTypingContext.trace.record(BindingContext.DATA_FLOW_INFO_BEFORE, ktExpression, expressionTypingContext.dataFlowInfo);
            try {
                kotlinTypeInfoNoTypeInfo = (KotlinTypeInfo) ktExpression.accept(ktVisitor, expressionTypingContext);
                if (expressionTypingContext.trace.get(BindingContext.PROCESSED, ktExpression) == Boolean.TRUE) {
                    return kotlinTypeInfoNoTypeInfo.replaceType(expressionTypingContext.trace.getContext().getType(ktExpression));
                }
                if (kotlinTypeInfoNoTypeInfo.getType() instanceof DeferredType) {
                    kotlinTypeInfoNoTypeInfo = kotlinTypeInfoNoTypeInfo.replaceType(((DeferredType) kotlinTypeInfoNoTypeInfo.getType()).getDelegate());
                }
                KotlinType kotlinTypeRefineType = kotlinTypeInfoNoTypeInfo.getType() != null ? this.components.kotlinTypeChecker.getKotlinTypeRefiner().refineType((KotlinTypeMarker) kotlinTypeInfoNoTypeInfo.getType()) : null;
                if (kotlinTypeRefineType != kotlinTypeInfoNoTypeInfo.getType()) {
                    kotlinTypeInfoNoTypeInfo = kotlinTypeInfoNoTypeInfo.replaceType(kotlinTypeRefineType);
                }
                expressionTypingContext.trace.record(BindingContext.EXPRESSION_TYPE_INFO, ktExpression, kotlinTypeInfoNoTypeInfo);
                expressionTypingContext.trace.record(BindingContext.PROCESSED, ktExpression);
                BindingContextUtilsKt.recordScope(expressionTypingContext.trace, expressionTypingContext.scope, ktExpression);
                BindingContextUtilsKt.recordDataFlowInfo(expressionTypingContext.replaceDataFlowInfo(kotlinTypeInfoNoTypeInfo.getDataFlowInfo()), ktExpression);
                try {
                    recordTypeInfo(ktExpression, kotlinTypeInfoNoTypeInfo);
                    return kotlinTypeInfoNoTypeInfo;
                } catch (ReenteringLazyValueComputationException unused) {
                    expressionTypingContext.trace.report(Errors.TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM.onError(ktExpression));
                    return TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
                }
            } catch (ReenteringLazyValueComputationException unused2) {
                expressionTypingContext.trace.report(Errors.TYPECHECKER_HAS_RUN_INTO_RECURSIVE_PROBLEM.onError(ktExpression));
                kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(expressionTypingContext);
            }
        } catch (ProcessCanceledException e) {
            throw e;
        } catch (IndexNotReadyException e2) {
            throw e2;
        } catch (KotlinFrontEndException e3) {
            throw e3;
        } catch (Throwable th) {
            expressionTypingContext.trace.report(Errors.EXCEPTION_FROM_ANALYZER.on(ktExpression, th));
            logOrThrowException(ktExpression, th);
            return TypeInfoFactoryKt.createTypeInfo(ErrorUtils.createErrorType(ErrorTypeKind.TYPE_FOR_COMPILER_EXCEPTION, th.getClass().getSimpleName()), expressionTypingContext);
        }
    }

    private void recordTypeInfo(KtExpression ktExpression, KotlinTypeInfo kotlinTypeInfo) {
        if (ktExpression == null) {
            $$$reportNull$$$0(17);
        }
        if (kotlinTypeInfo == null) {
            $$$reportNull$$$0(18);
        }
        LookupTracker lookupTracker = getComponents().lookupTracker;
        KotlinType type = kotlinTypeInfo.getType();
        if (type != null) {
            IncrementalTrackerUtilKt.record(lookupTracker, ktExpression, type);
        }
    }

    private static void logOrThrowException(KtExpression ktExpression, Throwable th) {
        String str;
        if (ktExpression == null) {
            $$$reportNull$$$0(19);
        }
        try {
            try {
                str = " in " + PsiDiagnosticUtils.atLocation(ktExpression);
            } catch (Exception unused) {
                str = "";
            }
            LOG.error((Throwable) new KotlinExceptionWithAttachments("Exception while analyzing expression" + str, th).withPsiAttachment("expression.kt", ktExpression));
        } catch (AssertionError e) {
            throw new KotlinFrontEndException(e.getMessage(), th);
        }
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitLambdaExpression(KtLambdaExpression ktLambdaExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktLambdaExpression == null) {
            $$$reportNull$$$0(20);
        }
        return this.functions.visitLambdaExpression(ktLambdaExpression, expressionTypingContext.replaceCallPosition(CallPosition.Unknown.INSTANCE));
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitNamedFunction(KtNamedFunction ktNamedFunction, ExpressionTypingContext expressionTypingContext) {
        if (ktNamedFunction == null) {
            $$$reportNull$$$0(21);
        }
        return this.functions.visitNamedFunction(ktNamedFunction, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitThrowExpression(KtThrowExpression ktThrowExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktThrowExpression == null) {
            $$$reportNull$$$0(22);
        }
        return this.controlStructures.visitThrowExpression(ktThrowExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitReturnExpression(KtReturnExpression ktReturnExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktReturnExpression == null) {
            $$$reportNull$$$0(23);
        }
        return this.controlStructures.visitReturnExpression(ktReturnExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitContinueExpression(KtContinueExpression ktContinueExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktContinueExpression == null) {
            $$$reportNull$$$0(24);
        }
        return this.controlStructures.visitContinueExpression(ktContinueExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitIfExpression(KtIfExpression ktIfExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktIfExpression == null) {
            $$$reportNull$$$0(25);
        }
        return this.controlStructures.visitIfExpression(ktIfExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitTryExpression(KtTryExpression ktTryExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktTryExpression == null) {
            $$$reportNull$$$0(26);
        }
        return this.controlStructures.visitTryExpression(ktTryExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitForExpression(KtForExpression ktForExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktForExpression == null) {
            $$$reportNull$$$0(27);
        }
        return this.controlStructures.visitForExpression(ktForExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitWhileExpression(KtWhileExpression ktWhileExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktWhileExpression == null) {
            $$$reportNull$$$0(28);
        }
        return this.controlStructures.visitWhileExpression(ktWhileExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitDoWhileExpression(KtDoWhileExpression ktDoWhileExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktDoWhileExpression == null) {
            $$$reportNull$$$0(29);
        }
        return this.controlStructures.visitDoWhileExpression(ktDoWhileExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitBreakExpression(KtBreakExpression ktBreakExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktBreakExpression == null) {
            $$$reportNull$$$0(30);
        }
        return this.controlStructures.visitBreakExpression(ktBreakExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitIsExpression(KtIsExpression ktIsExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktIsExpression == null) {
            $$$reportNull$$$0(31);
        }
        return this.patterns.visitIsExpression(ktIsExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitWhenExpression(KtWhenExpression ktWhenExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktWhenExpression == null) {
            $$$reportNull$$$0(32);
        }
        return this.patterns.visitWhenExpression(ktWhenExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitSimpleNameExpression(KtSimpleNameExpression ktSimpleNameExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktSimpleNameExpression == null) {
            $$$reportNull$$$0(33);
        }
        return this.basic.visitSimpleNameExpression(ktSimpleNameExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitParenthesizedExpression(KtParenthesizedExpression ktParenthesizedExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktParenthesizedExpression == null) {
            $$$reportNull$$$0(34);
        }
        return this.basic.visitParenthesizedExpression(ktParenthesizedExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitConstantExpression(KtConstantExpression ktConstantExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktConstantExpression == null) {
            $$$reportNull$$$0(35);
        }
        return this.basic.visitConstantExpression(ktConstantExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitBinaryWithTypeRHSExpression(KtBinaryExpressionWithTypeRHS ktBinaryExpressionWithTypeRHS, ExpressionTypingContext expressionTypingContext) {
        if (ktBinaryExpressionWithTypeRHS == null) {
            $$$reportNull$$$0(36);
        }
        return this.basic.visitBinaryWithTypeRHSExpression(ktBinaryExpressionWithTypeRHS, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitThisExpression(KtThisExpression ktThisExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktThisExpression == null) {
            $$$reportNull$$$0(37);
        }
        return this.basic.visitThisExpression(ktThisExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitSuperExpression(KtSuperExpression ktSuperExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktSuperExpression == null) {
            $$$reportNull$$$0(38);
        }
        return this.basic.visitSuperExpression(ktSuperExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitBlockExpression(KtBlockExpression ktBlockExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktBlockExpression == null) {
            $$$reportNull$$$0(39);
        }
        return this.basic.visitBlockExpression(ktBlockExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitClassLiteralExpression(KtClassLiteralExpression ktClassLiteralExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktClassLiteralExpression == null) {
            $$$reportNull$$$0(40);
        }
        return this.basic.visitClassLiteralExpression(ktClassLiteralExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitCallableReferenceExpression(KtCallableReferenceExpression ktCallableReferenceExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktCallableReferenceExpression == null) {
            $$$reportNull$$$0(41);
        }
        return this.basic.visitCallableReferenceExpression(ktCallableReferenceExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitObjectLiteralExpression(KtObjectLiteralExpression ktObjectLiteralExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktObjectLiteralExpression == null) {
            $$$reportNull$$$0(42);
        }
        return this.basic.visitObjectLiteralExpression(ktObjectLiteralExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitQualifiedExpression(KtQualifiedExpression ktQualifiedExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktQualifiedExpression == null) {
            $$$reportNull$$$0(43);
        }
        return this.basic.visitQualifiedExpression(ktQualifiedExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitCallExpression(KtCallExpression ktCallExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktCallExpression == null) {
            $$$reportNull$$$0(44);
        }
        return this.basic.visitCallExpression(ktCallExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitUnaryExpression(KtUnaryExpression ktUnaryExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktUnaryExpression == null) {
            $$$reportNull$$$0(45);
        }
        return this.basic.visitUnaryExpression(ktUnaryExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitLabeledExpression(KtLabeledExpression ktLabeledExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktLabeledExpression == null) {
            $$$reportNull$$$0(46);
        }
        return this.basic.visitLabeledExpression(ktLabeledExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitBinaryExpression(KtBinaryExpression ktBinaryExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktBinaryExpression == null) {
            $$$reportNull$$$0(47);
        }
        return this.basic.visitBinaryExpression(ktBinaryExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitArrayAccessExpression(KtArrayAccessExpression ktArrayAccessExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktArrayAccessExpression == null) {
            $$$reportNull$$$0(48);
        }
        return this.basic.visitArrayAccessExpression(ktArrayAccessExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitDeclaration(KtDeclaration ktDeclaration, ExpressionTypingContext expressionTypingContext) {
        if (ktDeclaration == null) {
            $$$reportNull$$$0(49);
        }
        return this.basic.visitDeclaration(ktDeclaration, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitClass(KtClass ktClass, ExpressionTypingContext expressionTypingContext) {
        if (ktClass == null) {
            $$$reportNull$$$0(50);
        }
        return this.basic.visitClass(ktClass, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitProperty(KtProperty ktProperty, ExpressionTypingContext expressionTypingContext) {
        if (ktProperty == null) {
            $$$reportNull$$$0(51);
        }
        return this.basic.visitProperty(ktProperty, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitStringTemplateExpression(KtStringTemplateExpression ktStringTemplateExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktStringTemplateExpression == null) {
            $$$reportNull$$$0(52);
        }
        return this.basic.visitStringTemplateExpression(ktStringTemplateExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitAnnotatedExpression(KtAnnotatedExpression ktAnnotatedExpression, ExpressionTypingContext expressionTypingContext) {
        if (ktAnnotatedExpression == null) {
            $$$reportNull$$$0(53);
        }
        return this.basic.visitAnnotatedExpression(ktAnnotatedExpression, expressionTypingContext);
    }

    @Override // org.jetbrains.kotlin.psi.KtVisitor
    public KotlinTypeInfo visitKtElement(KtElement ktElement, ExpressionTypingContext expressionTypingContext) {
        if (ktElement == null) {
            $$$reportNull$$$0(54);
        }
        return (KotlinTypeInfo) ktElement.accept(this.basic, expressionTypingContext);
    }
}
