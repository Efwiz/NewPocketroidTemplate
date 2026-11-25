package org.jetbrains.kotlin.fir.resolve.diagnostics;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.contracts.description.KtContractDescriptionElement;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic;
import org.jetbrains.kotlin.fir.expressions.FirConstExpression;
import org.jetbrains.kotlin.fir.expressions.FirOperation;
import org.jetbrains.kotlin.fir.expressions.FirThisReceiverExpression;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.Name;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: ConeDiagnostics.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0010\u0003\u0004\u0005\u0006\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0010\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"¨\u0006#"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "()V", "IllegalConst", "IllegalElement", "IllegalEqualityOperator", "IllegalParameter", "IllegalThis", "NoArgument", "NoReceiver", "NotABooleanExpression", "NotAConstant", "NotAParameterReference", "NotContractDsl", "NotReifiedTypeParameter", "NotSelfTypeParameter", "UnresolvedCall", "UnresolvedInvocationKind", "UnresolvedThis", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$IllegalConst;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$IllegalElement;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$IllegalEqualityOperator;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$IllegalParameter;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$IllegalThis;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NoArgument;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NoReceiver;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NotABooleanExpression;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NotAConstant;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NotAParameterReference;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NotContractDsl;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NotReifiedTypeParameter;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NotSelfTypeParameter;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$UnresolvedCall;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$UnresolvedInvocationKind;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$UnresolvedThis;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class ConeContractDescriptionError implements ConeDiagnostic {
    public /* synthetic */ ConeContractDescriptionError(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$IllegalElement;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "(Lorg/jetbrains/kotlin/fir/FirElement;)V", "getElement", "()Lorg/jetbrains/kotlin/fir/FirElement;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IllegalElement extends ConeContractDescriptionError {
        private final FirElement element;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IllegalElement(FirElement element) {
            super(null);
            Intrinsics.checkNotNullParameter(element, "element");
            this.element = element;
        }

        public final FirElement getElement() {
            return this.element;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return "Illegal element in contract description";
        }
    }

    private ConeContractDescriptionError() {
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$UnresolvedCall;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnresolvedCall extends ConeContractDescriptionError {
        private final Name name;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public UnresolvedCall(Name name2) {
            super(null);
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
        }

        public final Name getName() {
            return this.name;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return "Unresolved call in contract description: " + this.name.asString();
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NoReceiver;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NoReceiver extends ConeContractDescriptionError {
        private final Name name;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public NoReceiver(Name name2) {
            super(null);
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
        }

        public final Name getName() {
            return this.name;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return "No receiver for call " + this.name.asString() + " found";
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NoArgument;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", "name", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/name/Name;)V", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NoArgument extends ConeContractDescriptionError {
        private final Name name;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public NoArgument(Name name2) {
            super(null);
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
        }

        public final Name getName() {
            return this.name;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return "No argument for call " + this.name.asString() + " found";
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NotAConstant;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getElement", "()Ljava/lang/Object;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotAConstant extends ConeContractDescriptionError {
        private final Object element;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public NotAConstant(Object element) {
            super(null);
            Intrinsics.checkNotNullParameter(element, "element");
            this.element = element;
        }

        public final Object getElement() {
            return this.element;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return this.element + " is not a constant reference";
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0019\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000e¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$IllegalConst;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "onlyNullAllowed", "", "(Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;Z)V", "getElement", "()Lorg/jetbrains/kotlin/fir/expressions/FirConstExpression;", "getOnlyNullAllowed", "()Z", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IllegalConst extends ConeContractDescriptionError {
        private final FirConstExpression<?> element;
        private final boolean onlyNullAllowed;

        public final FirConstExpression<?> getElement() {
            return this.element;
        }

        public final boolean getOnlyNullAllowed() {
            return this.onlyNullAllowed;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IllegalConst(FirConstExpression<?> element, boolean z) {
            super(null);
            Intrinsics.checkNotNullParameter(element, "element");
            this.element = element;
            this.onlyNullAllowed = z;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            StringBuilder sb = new StringBuilder();
            sb.append(UtilsKt.render(this.element));
            sb.append("is not a null");
            if (!this.onlyNullAllowed) {
                sb.append(", true or false");
            }
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006¢\u0006\u0002\u0010\u0007R!\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NotAParameterReference;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeContractDescriptionElement;", "(Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;)V", "getElement", "()Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotAParameterReference extends ConeContractDescriptionError {
        private final KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> element;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public NotAParameterReference(KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> element) {
            super(null);
            Intrinsics.checkNotNullParameter(element, "element");
            this.element = element;
        }

        public final KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> getElement() {
            return this.element;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return this.element + " is not a parameter or receiver reference";
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\u0018\u00002\u00020\u0001B\u0019\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006R\u0014\u0010\u0004\u001a\u00020\u0005X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0015\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$IllegalParameter;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "reason", "", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Ljava/lang/String;)V", "getReason", "()Ljava/lang/String;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IllegalParameter extends ConeContractDescriptionError {
        private final String reason;
        private final FirCallableSymbol<?> symbol;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IllegalParameter(FirCallableSymbol<?> symbol, String reason) {
            super(null);
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            Intrinsics.checkNotNullParameter(reason, "reason");
            this.symbol = symbol;
            this.reason = reason;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return this.reason;
        }

        public final FirCallableSymbol<?> getSymbol() {
            return this.symbol;
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$UnresolvedThis;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;)V", "getExpression", "()Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnresolvedThis extends ConeContractDescriptionError {
        private final FirThisReceiverExpression expression;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public UnresolvedThis(FirThisReceiverExpression expression) {
            super(null);
            Intrinsics.checkNotNullParameter(expression, "expression");
            this.expression = expression;
        }

        public final FirThisReceiverExpression getExpression() {
            return this.expression;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return "Can't resolve this reference";
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$IllegalThis;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", "expression", "Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "(Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;)V", "getExpression", "()Lorg/jetbrains/kotlin/fir/expressions/FirThisReceiverExpression;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IllegalThis extends ConeContractDescriptionError {
        private final FirThisReceiverExpression expression;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IllegalThis(FirThisReceiverExpression expression) {
            super(null);
            Intrinsics.checkNotNullParameter(expression, "expression");
            this.expression = expression;
        }

        public final FirThisReceiverExpression getExpression() {
            return this.expression;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return "Only this reference to extension receiver of a function is allowed";
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$UnresolvedInvocationKind;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/fir/FirElement;", "(Lorg/jetbrains/kotlin/fir/FirElement;)V", "getElement", "()Lorg/jetbrains/kotlin/fir/FirElement;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class UnresolvedInvocationKind extends ConeContractDescriptionError {
        private final FirElement element;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public UnresolvedInvocationKind(FirElement element) {
            super(null);
            Intrinsics.checkNotNullParameter(element, "element");
            this.element = element;
        }

        public final FirElement getElement() {
            return this.element;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return UtilsKt.render(this.element) + " is not a valid invocation kind";
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u001d\u0012\u0016\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006¢\u0006\u0002\u0010\u0007R!\u0010\u0002\u001a\u0012\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003j\u0002`\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NotABooleanExpression;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;", "Lorg/jetbrains/kotlin/fir/types/ConeKotlinType;", "Lorg/jetbrains/kotlin/fir/diagnostics/ConeDiagnostic;", "Lorg/jetbrains/kotlin/fir/contracts/description/ConeContractDescriptionElement;", "(Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;)V", "getElement", "()Lorg/jetbrains/kotlin/contracts/description/KtContractDescriptionElement;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotABooleanExpression extends ConeContractDescriptionError {
        private final KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> element;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public NotABooleanExpression(KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> element) {
            super(null);
            Intrinsics.checkNotNullParameter(element, "element");
            this.element = element;
        }

        public final KtContractDescriptionElement<ConeKotlinType, ConeDiagnostic> getElement() {
            return this.element;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return this.element + " is not a boolean expression";
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NotContractDsl;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "(Lorg/jetbrains/kotlin/name/CallableId;)V", "getCallableId", "()Lorg/jetbrains/kotlin/name/CallableId;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotContractDsl extends ConeContractDescriptionError {
        private final CallableId callableId;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public NotContractDsl(CallableId callableId) {
            super(null);
            Intrinsics.checkNotNullParameter(callableId, "callableId");
            this.callableId = callableId;
        }

        public final CallableId getCallableId() {
            return this.callableId;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return this.callableId + " is not a part of contracts DSL";
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0014\u0010\u0007\u001a\u00020\b8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$IllegalEqualityOperator;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", "operation", "Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "(Lorg/jetbrains/kotlin/fir/expressions/FirOperation;)V", "getOperation", "()Lorg/jetbrains/kotlin/fir/expressions/FirOperation;", "reason", "", "getReason", "()Ljava/lang/String;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IllegalEqualityOperator extends ConeContractDescriptionError {
        private final FirOperation operation;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IllegalEqualityOperator(FirOperation operation) {
            super(null);
            Intrinsics.checkNotNullParameter(operation, "operation");
            this.operation = operation;
        }

        public final FirOperation getOperation() {
            return this.operation;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return this.operation + " operator call is illegal in contract description";
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NotSelfTypeParameter;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;)V", "reason", "", "getReason", "()Ljava/lang/String;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotSelfTypeParameter extends ConeContractDescriptionError {
        private final FirTypeParameterSymbol symbol;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public NotSelfTypeParameter(FirTypeParameterSymbol symbol) {
            super(null);
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            this.symbol = symbol;
        }

        public final FirTypeParameterSymbol getSymbol() {
            return this.symbol;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return "Type parameter " + this.symbol.getName() + " does not belong to owner of contract";
        }
    }

    /* JADX INFO: compiled from: ConeDiagnostics.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0014\u0010\u0005\u001a\u00020\u00068VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError$NotReifiedTypeParameter;", "Lorg/jetbrains/kotlin/fir/resolve/diagnostics/ConeContractDescriptionError;", NativeSymbol.TYPE_NAME, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;)V", "reason", "", "getReason", "()Ljava/lang/String;", "getSymbol", "()Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "semantics"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class NotReifiedTypeParameter extends ConeContractDescriptionError {
        private final FirTypeParameterSymbol symbol;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public NotReifiedTypeParameter(FirTypeParameterSymbol symbol) {
            super(null);
            Intrinsics.checkNotNullParameter(symbol, "symbol");
            this.symbol = symbol;
        }

        public final FirTypeParameterSymbol getSymbol() {
            return this.symbol;
        }

        @Override // org.jetbrains.kotlin.fir.diagnostics.ConeDiagnostic
        public String getReason() {
            return "Type parameter " + this.symbol.getName() + " is not reified";
        }
    }
}
