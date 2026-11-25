package org.jetbrains.kotlin.ir.linkage.partial;

import java.util.Set;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.declarations.IrOverridableDeclaration;
import org.jetbrains.kotlin.ir.expressions.IrCall;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;
import org.jetbrains.kotlin.ir.expressions.IrReturn;
import org.jetbrains.kotlin.ir.expressions.IrTypeOperatorCall;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.symbols.IrFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrPropertySymbol;
import org.jetbrains.kotlin.ir.symbols.IrReturnTargetSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSymbol;

/* JADX INFO: compiled from: PartialLinkageCase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bv\u0018\u00002\u00020\u0001:\u0011\u0002\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0082\u0001\u0011\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#¨\u0006$"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "", "AbstractClassInstantiation", "AmbiguousNonOverriddenCallable", "DeclarationWithUnusableClassifier", "ExpressionHasDeclarationWithUnusableClassifier", "ExpressionHasInaccessibleDeclaration", "ExpressionHasWrongTypeOfDeclaration", "ExpressionWithMissingDeclaration", "ExpressionWithUnusableClassifier", "IllegalNonLocalReturn", "InvalidConstructorDelegation", "InvalidSamConversion", "MemberAccessExpressionArgumentsMismatch", "MissingDeclaration", "SuspendableFunctionCallWithoutCoroutineContext", "UnimplementedAbstractCallable", "UnusableAnnotation", "UnusableClassifier", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$AbstractClassInstantiation;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$AmbiguousNonOverriddenCallable;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$DeclarationWithUnusableClassifier;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$ExpressionHasDeclarationWithUnusableClassifier;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$ExpressionHasInaccessibleDeclaration;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$ExpressionHasWrongTypeOfDeclaration;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$ExpressionWithMissingDeclaration;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$ExpressionWithUnusableClassifier;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$IllegalNonLocalReturn;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$InvalidConstructorDelegation;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$InvalidSamConversion;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$MemberAccessExpressionArgumentsMismatch;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$MissingDeclaration;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$SuspendableFunctionCallWithoutCoroutineContext;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$UnimplementedAbstractCallable;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$UnusableAnnotation;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$UnusableClassifier;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface PartialLinkageCase {

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$UnusableClassifier;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "cause", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$CanBeRootCause;", "(Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$CanBeRootCause;)V", "getCause", "()Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable$CanBeRootCause;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnusableClassifier implements PartialLinkageCase {
        private final ExploredClassifier.Unusable.CanBeRootCause cause;

        public UnusableClassifier(ExploredClassifier.Unusable.CanBeRootCause cause) {
            Intrinsics.checkNotNullParameter(cause, "cause");
            this.cause = cause;
        }

        public final ExploredClassifier.Unusable.CanBeRootCause getCause() {
            return this.cause;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$MissingDeclaration;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "missingDeclarationSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)V", "getMissingDeclarationSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MissingDeclaration implements PartialLinkageCase {
        private final IrSymbol missingDeclarationSymbol;

        public MissingDeclaration(IrSymbol missingDeclarationSymbol) {
            Intrinsics.checkNotNullParameter(missingDeclarationSymbol, "missingDeclarationSymbol");
            this.missingDeclarationSymbol = missingDeclarationSymbol;
        }

        public final IrSymbol getMissingDeclarationSymbol() {
            return this.missingDeclarationSymbol;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$DeclarationWithUnusableClassifier;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "declarationSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "cause", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "(Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;)V", "getCause", "()Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "getDeclarationSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DeclarationWithUnusableClassifier implements PartialLinkageCase {
        private final ExploredClassifier.Unusable cause;
        private final IrSymbol declarationSymbol;

        public DeclarationWithUnusableClassifier(IrSymbol declarationSymbol, ExploredClassifier.Unusable cause) {
            Intrinsics.checkNotNullParameter(declarationSymbol, "declarationSymbol");
            Intrinsics.checkNotNullParameter(cause, "cause");
            this.declarationSymbol = declarationSymbol;
            this.cause = cause;
        }

        public final IrSymbol getDeclarationSymbol() {
            return this.declarationSymbol;
        }

        public final ExploredClassifier.Unusable getCause() {
            return this.cause;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$ExpressionWithUnusableClassifier;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "cause", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;)V", "getCause", "()Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExpressionWithUnusableClassifier implements PartialLinkageCase {
        private final ExploredClassifier.Unusable cause;
        private final IrExpression expression;

        public ExpressionWithUnusableClassifier(IrExpression expression, ExploredClassifier.Unusable cause) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(cause, "cause");
            this.expression = expression;
            this.cause = cause;
        }

        public final IrExpression getExpression() {
            return this.expression;
        }

        public final ExploredClassifier.Unusable getCause() {
            return this.cause;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$ExpressionWithMissingDeclaration;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "missingDeclarationSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)V", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getMissingDeclarationSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExpressionWithMissingDeclaration implements PartialLinkageCase {
        private final IrExpression expression;
        private final IrSymbol missingDeclarationSymbol;

        public ExpressionWithMissingDeclaration(IrExpression expression, IrSymbol missingDeclarationSymbol) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(missingDeclarationSymbol, "missingDeclarationSymbol");
            this.expression = expression;
            this.missingDeclarationSymbol = missingDeclarationSymbol;
        }

        public final IrExpression getExpression() {
            return this.expression;
        }

        public final IrSymbol getMissingDeclarationSymbol() {
            return this.missingDeclarationSymbol;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$ExpressionHasDeclarationWithUnusableClassifier;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "referencedDeclarationSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "cause", "Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;)V", "getCause", "()Lorg/jetbrains/kotlin/ir/linkage/partial/ExploredClassifier$Unusable;", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getReferencedDeclarationSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExpressionHasDeclarationWithUnusableClassifier implements PartialLinkageCase {
        private final ExploredClassifier.Unusable cause;
        private final IrExpression expression;
        private final IrSymbol referencedDeclarationSymbol;

        public ExpressionHasDeclarationWithUnusableClassifier(IrExpression expression, IrSymbol referencedDeclarationSymbol, ExploredClassifier.Unusable cause) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(referencedDeclarationSymbol, "referencedDeclarationSymbol");
            Intrinsics.checkNotNullParameter(cause, "cause");
            this.expression = expression;
            this.referencedDeclarationSymbol = referencedDeclarationSymbol;
            this.cause = cause;
        }

        public final IrExpression getExpression() {
            return this.expression;
        }

        public final IrSymbol getReferencedDeclarationSymbol() {
            return this.referencedDeclarationSymbol;
        }

        public final ExploredClassifier.Unusable getCause() {
            return this.cause;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$ExpressionHasWrongTypeOfDeclaration;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "actualDeclarationSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "expectedDeclarationDescription", "", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;Ljava/lang/String;)V", "getActualDeclarationSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getExpectedDeclarationDescription", "()Ljava/lang/String;", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExpressionHasWrongTypeOfDeclaration implements PartialLinkageCase {
        private final IrSymbol actualDeclarationSymbol;
        private final String expectedDeclarationDescription;
        private final IrExpression expression;

        public ExpressionHasWrongTypeOfDeclaration(IrExpression expression, IrSymbol actualDeclarationSymbol, String expectedDeclarationDescription) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(actualDeclarationSymbol, "actualDeclarationSymbol");
            Intrinsics.checkNotNullParameter(expectedDeclarationDescription, "expectedDeclarationDescription");
            this.expression = expression;
            this.actualDeclarationSymbol = actualDeclarationSymbol;
            this.expectedDeclarationDescription = expectedDeclarationDescription;
        }

        public final IrExpression getExpression() {
            return this.expression;
        }

        public final IrSymbol getActualDeclarationSymbol() {
            return this.actualDeclarationSymbol;
        }

        public final String getExpectedDeclarationDescription() {
            return this.expectedDeclarationDescription;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u000b\u0018\u00002\u00020\u0001B3\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\t¢\u0006\u0002\u0010\u000bR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$MemberAccessExpressionArgumentsMismatch;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "Lorg/jetbrains/kotlin/ir/symbols/IrFunctionSymbol;", "expressionHasDispatchReceiver", "", "functionHasDispatchReceiver", "expressionValueArgumentCount", "", "functionValueParameterCount", "(Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;ZZII)V", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "getExpressionHasDispatchReceiver", "()Z", "getExpressionValueArgumentCount", "()I", "getFunctionHasDispatchReceiver", "getFunctionValueParameterCount", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class MemberAccessExpressionArgumentsMismatch implements PartialLinkageCase {
        private final IrMemberAccessExpression<IrFunctionSymbol> expression;
        private final boolean expressionHasDispatchReceiver;
        private final int expressionValueArgumentCount;
        private final boolean functionHasDispatchReceiver;
        private final int functionValueParameterCount;

        public MemberAccessExpressionArgumentsMismatch(IrMemberAccessExpression<IrFunctionSymbol> expression, boolean z, boolean z2, int i, int i2) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            this.expression = expression;
            this.expressionHasDispatchReceiver = z;
            this.functionHasDispatchReceiver = z2;
            this.expressionValueArgumentCount = i;
            this.functionValueParameterCount = i2;
        }

        public final IrMemberAccessExpression<IrFunctionSymbol> getExpression() {
            return this.expression;
        }

        public final boolean getExpressionHasDispatchReceiver() {
            return this.expressionHasDispatchReceiver;
        }

        public final boolean getFunctionHasDispatchReceiver() {
            return this.functionHasDispatchReceiver;
        }

        public final int getExpressionValueArgumentCount() {
            return this.expressionValueArgumentCount;
        }

        public final int getFunctionValueParameterCount() {
            return this.functionValueParameterCount;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\b\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$InvalidSamConversion;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "abstractFunctionSymbols", "", "Lorg/jetbrains/kotlin/ir/symbols/IrSimpleFunctionSymbol;", "abstractPropertySymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "(Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;Ljava/util/Set;Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;)V", "getAbstractFunctionSymbols", "()Ljava/util/Set;", "getAbstractPropertySymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrPropertySymbol;", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrTypeOperatorCall;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class InvalidSamConversion implements PartialLinkageCase {
        private final Set<IrSimpleFunctionSymbol> abstractFunctionSymbols;
        private final IrPropertySymbol abstractPropertySymbol;
        private final IrTypeOperatorCall expression;

        /* JADX WARN: Multi-variable type inference failed */
        public InvalidSamConversion(IrTypeOperatorCall expression, Set<? extends IrSimpleFunctionSymbol> abstractFunctionSymbols, IrPropertySymbol irPropertySymbol) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(abstractFunctionSymbols, "abstractFunctionSymbols");
            this.expression = expression;
            this.abstractFunctionSymbols = abstractFunctionSymbols;
            this.abstractPropertySymbol = irPropertySymbol;
        }

        public final IrTypeOperatorCall getExpression() {
            return this.expression;
        }

        public final Set<IrSimpleFunctionSymbol> getAbstractFunctionSymbols() {
            return this.abstractFunctionSymbols;
        }

        public final IrPropertySymbol getAbstractPropertySymbol() {
            return this.abstractPropertySymbol;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$SuspendableFunctionCallWithoutCoroutineContext;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "(Lorg/jetbrains/kotlin/ir/expressions/IrCall;)V", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrCall;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SuspendableFunctionCallWithoutCoroutineContext implements PartialLinkageCase {
        private final IrCall expression;

        public SuspendableFunctionCallWithoutCoroutineContext(IrCall expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            this.expression = expression;
        }

        public final IrCall getExpression() {
            return this.expression;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$IllegalNonLocalReturn;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "validReturnTargets", "", "Lorg/jetbrains/kotlin/ir/symbols/IrReturnTargetSymbol;", "(Lorg/jetbrains/kotlin/ir/expressions/IrReturn;Ljava/util/Set;)V", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrReturn;", "getValidReturnTargets", "()Ljava/util/Set;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IllegalNonLocalReturn implements PartialLinkageCase {
        private final IrReturn expression;
        private final Set<IrReturnTargetSymbol> validReturnTargets;

        /* JADX WARN: Multi-variable type inference failed */
        public IllegalNonLocalReturn(IrReturn expression, Set<? extends IrReturnTargetSymbol> validReturnTargets) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(validReturnTargets, "validReturnTargets");
            this.expression = expression;
            this.validReturnTargets = validReturnTargets;
        }

        public final IrReturn getExpression() {
            return this.expression;
        }

        public final Set<IrReturnTargetSymbol> getValidReturnTargets() {
            return this.validReturnTargets;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0007¢\u0006\u0002\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\b\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$ExpressionHasInaccessibleDeclaration;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "expression", "Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "referencedDeclarationSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "declaringModule", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "useSiteModule", "(Lorg/jetbrains/kotlin/ir/expressions/IrExpression;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;)V", "getDeclaringModule", "()Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageUtils$Module;", "getExpression", "()Lorg/jetbrains/kotlin/ir/expressions/IrExpression;", "getReferencedDeclarationSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "getUseSiteModule", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExpressionHasInaccessibleDeclaration implements PartialLinkageCase {
        private final PartialLinkageUtils.Module declaringModule;
        private final IrExpression expression;
        private final IrSymbol referencedDeclarationSymbol;
        private final PartialLinkageUtils.Module useSiteModule;

        public ExpressionHasInaccessibleDeclaration(IrExpression expression, IrSymbol referencedDeclarationSymbol, PartialLinkageUtils.Module declaringModule, PartialLinkageUtils.Module useSiteModule) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            Intrinsics.checkNotNullParameter(referencedDeclarationSymbol, "referencedDeclarationSymbol");
            Intrinsics.checkNotNullParameter(declaringModule, "declaringModule");
            Intrinsics.checkNotNullParameter(useSiteModule, "useSiteModule");
            this.expression = expression;
            this.referencedDeclarationSymbol = referencedDeclarationSymbol;
            this.declaringModule = declaringModule;
            this.useSiteModule = useSiteModule;
        }

        public final IrExpression getExpression() {
            return this.expression;
        }

        public final IrSymbol getReferencedDeclarationSymbol() {
            return this.referencedDeclarationSymbol;
        }

        public final PartialLinkageUtils.Module getDeclaringModule() {
            return this.declaringModule;
        }

        public final PartialLinkageUtils.Module getUseSiteModule() {
            return this.useSiteModule;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003¢\u0006\u0002\u0010\u0007R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\t¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$InvalidConstructorDelegation;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "constructorSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "superClassSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "unexpectedSuperClassConstructorSymbol", "(Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;)V", "getConstructorSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getSuperClassSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getUnexpectedSuperClassConstructorSymbol", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class InvalidConstructorDelegation implements PartialLinkageCase {
        private final IrConstructorSymbol constructorSymbol;
        private final IrClassSymbol superClassSymbol;
        private final IrConstructorSymbol unexpectedSuperClassConstructorSymbol;

        public InvalidConstructorDelegation(IrConstructorSymbol constructorSymbol, IrClassSymbol superClassSymbol, IrConstructorSymbol unexpectedSuperClassConstructorSymbol) {
            Intrinsics.checkNotNullParameter(constructorSymbol, "constructorSymbol");
            Intrinsics.checkNotNullParameter(superClassSymbol, "superClassSymbol");
            Intrinsics.checkNotNullParameter(unexpectedSuperClassConstructorSymbol, "unexpectedSuperClassConstructorSymbol");
            this.constructorSymbol = constructorSymbol;
            this.superClassSymbol = superClassSymbol;
            this.unexpectedSuperClassConstructorSymbol = unexpectedSuperClassConstructorSymbol;
        }

        public final IrConstructorSymbol getConstructorSymbol() {
            return this.constructorSymbol;
        }

        public final IrClassSymbol getSuperClassSymbol() {
            return this.superClassSymbol;
        }

        public final IrConstructorSymbol getUnexpectedSuperClassConstructorSymbol() {
            return this.unexpectedSuperClassConstructorSymbol;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$AbstractClassInstantiation;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "constructorCall", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "classSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "(Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "getClassSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getConstructorCall", "()Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AbstractClassInstantiation implements PartialLinkageCase {
        private final IrClassSymbol classSymbol;
        private final IrConstructorCall constructorCall;

        public AbstractClassInstantiation(IrConstructorCall constructorCall, IrClassSymbol classSymbol) {
            Intrinsics.checkNotNullParameter(constructorCall, "constructorCall");
            Intrinsics.checkNotNullParameter(classSymbol, "classSymbol");
            this.constructorCall = constructorCall;
            this.classSymbol = classSymbol;
        }

        public final IrClassSymbol getClassSymbol() {
            return this.classSymbol;
        }

        public final IrConstructorCall getConstructorCall() {
            return this.constructorCall;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$UnimplementedAbstractCallable;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "callable", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;)V", "getCallable", "()Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnimplementedAbstractCallable implements PartialLinkageCase {
        private final IrOverridableDeclaration<?> callable;

        public UnimplementedAbstractCallable(IrOverridableDeclaration<?> callable) {
            Intrinsics.checkNotNullParameter(callable, "callable");
            this.callable = callable;
        }

        public final IrOverridableDeclaration<?> getCallable() {
            return this.callable;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$UnusableAnnotation;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "annotationConstructorSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "holderDeclarationSymbol", "Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "(Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;)V", "getAnnotationConstructorSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getHolderDeclarationSymbol", "()Lorg/jetbrains/kotlin/ir/symbols/IrSymbol;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnusableAnnotation implements PartialLinkageCase {
        private final IrConstructorSymbol annotationConstructorSymbol;
        private final IrSymbol holderDeclarationSymbol;

        public UnusableAnnotation(IrConstructorSymbol annotationConstructorSymbol, IrSymbol holderDeclarationSymbol) {
            Intrinsics.checkNotNullParameter(annotationConstructorSymbol, "annotationConstructorSymbol");
            Intrinsics.checkNotNullParameter(holderDeclarationSymbol, "holderDeclarationSymbol");
            this.annotationConstructorSymbol = annotationConstructorSymbol;
            this.holderDeclarationSymbol = holderDeclarationSymbol;
        }

        public final IrConstructorSymbol getAnnotationConstructorSymbol() {
            return this.annotationConstructorSymbol;
        }

        public final IrSymbol getHolderDeclarationSymbol() {
            return this.holderDeclarationSymbol;
        }
    }

    /* JADX INFO: compiled from: PartialLinkageCase.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase$AmbiguousNonOverriddenCallable;", "Lorg/jetbrains/kotlin/ir/linkage/partial/PartialLinkageCase;", "callable", "Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "(Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;)V", "getCallable", "()Lorg/jetbrains/kotlin/ir/declarations/IrOverridableDeclaration;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AmbiguousNonOverriddenCallable implements PartialLinkageCase {
        private final IrOverridableDeclaration<?> callable;

        public AmbiguousNonOverriddenCallable(IrOverridableDeclaration<?> callable) {
            Intrinsics.checkNotNullParameter(callable, "callable");
            this.callable = callable;
        }

        public final IrOverridableDeclaration<?> getCallable() {
            return this.callable;
        }
    }
}
