package org.jetbrains.kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX INFO: compiled from: KtSourceElement.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000\u0084\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b?\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:<\u0007\b\t\n\u000b\f\r\u000e\u000f\u0010\u0011\u0012\u0013\u0014\u0015\u0016\u0017\u0018\u0019\u001a\u001b\u001c\u001d\u001e\u001f !\"#$%&'()*+,-./0123456789:;<=>?@ABB\u0011\b\u0004\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006\u0082\u0001<CDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~¨\u0006\u007f"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "Lorg/jetbrains/kotlin/KtSourceElementKind;", "shouldSkipErrorTypeReporting", "", "(Z)V", "getShouldSkipErrorTypeReporting", "()Z", "ArrayAccessNameReference", "ArrayIndexExpressionReference", "ArrayTypeFromVarargParameter", "AssignmentLValueError", "CheckedSafeCallSubject", "ClassDelegationField", "ClassSelfTypeRef", "ConstructorTypeParameter", "ContractBlock", "DanglingModifierList", "DataClassGeneratedMembers", "DefaultAccessor", "DelegatedPropertyAccessor", "DelegatingConstructorCall", "DesugaredComponentFunctionCall", "DesugaredCompoundAssignment", "DesugaredForLoop", "DesugaredIncrementOrDecrement", "DesugaredInvertedContains", "DesugaredPostfixNameReference", "DesugaredPrefixNameReference", "DesugaredPrefixSecondGetReference", "DesugaredSafeCallExpression", "EnumGeneratedDeclaration", "EnumInitializer", "ErrorTypeRef", "FromUseSiteTarget", "GeneratedComparisonExpression", "GeneratedLambdaLabel", "ImplicitAnnotationAnnotationConstructorParameter", "ImplicitConstructor", "ImplicitImport", "ImplicitInvokeCall", "ImplicitJavaAnnotationConstructor", "ImplicitJavaRecordConstructor", "ImplicitReceiver", "ImplicitRecordConstructorParameter", "ImplicitReturn", "ImplicitReturnTypeOfLambdaValueParameter", "ImplicitTypeRef", "ImplicitUnit", "IntToLongConversion", "ItLambdaParameter", "JavaRecordComponentField", "JavaRecordComponentFunction", "ParameterNameAnnotationCall", "PropertyFromParameter", "PropertyTypeFromGetterReturnType", "ReceiverFromType", "ReferenceInAtomicQualifiedAccess", "SingleExpressionBlock", "SmartCastExpression", "SmartCastedTypeRef", "SuperCallExplicitType", "SuperCallImplicitType", "SyntheticCall", "VarargArgument", "WhenCondition", "WhenGeneratedSubject", "WrappedDelegate", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ArrayAccessNameReference;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ArrayIndexExpressionReference;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ArrayTypeFromVarargParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$AssignmentLValueError;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$CheckedSafeCallSubject;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ClassDelegationField;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ClassSelfTypeRef;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ConstructorTypeParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ContractBlock;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DanglingModifierList;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DataClassGeneratedMembers;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DefaultAccessor;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DelegatedPropertyAccessor;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DelegatingConstructorCall;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredComponentFunctionCall;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredCompoundAssignment;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredForLoop;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredIncrementOrDecrement;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredInvertedContains;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredPostfixNameReference;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredPrefixNameReference;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredPrefixSecondGetReference;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredSafeCallExpression;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$EnumGeneratedDeclaration;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$EnumInitializer;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ErrorTypeRef;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$FromUseSiteTarget;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$GeneratedComparisonExpression;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$GeneratedLambdaLabel;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitAnnotationAnnotationConstructorParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitConstructor;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitImport;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitInvokeCall;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitJavaAnnotationConstructor;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitJavaRecordConstructor;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitReceiver;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitRecordConstructorParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitReturn;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitReturnTypeOfLambdaValueParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitTypeRef;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitUnit;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$IntToLongConversion;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ItLambdaParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$JavaRecordComponentField;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$JavaRecordComponentFunction;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ParameterNameAnnotationCall;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$PropertyFromParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$PropertyTypeFromGetterReturnType;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ReceiverFromType;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ReferenceInAtomicQualifiedAccess;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$SingleExpressionBlock;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$SmartCastExpression;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$SmartCastedTypeRef;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$SuperCallExplicitType;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$SuperCallImplicitType;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$SyntheticCall;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$VarargArgument;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$WhenCondition;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$WhenGeneratedSubject;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$WrappedDelegate;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class KtFakeSourceElementKind extends KtSourceElementKind {
    private final boolean shouldSkipErrorTypeReporting;

    public /* synthetic */ KtFakeSourceElementKind(boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(z);
    }

    private KtFakeSourceElementKind(boolean z) {
        super(null);
        this.shouldSkipErrorTypeReporting = z;
    }

    public /* synthetic */ KtFakeSourceElementKind(boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, null);
    }

    @Override // org.jetbrains.kotlin.KtSourceElementKind
    public final boolean getShouldSkipErrorTypeReporting() {
        return this.shouldSkipErrorTypeReporting;
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitTypeRef;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ImplicitTypeRef extends KtFakeSourceElementKind {
        public static final ImplicitTypeRef INSTANCE = new ImplicitTypeRef();

        private ImplicitTypeRef() {
            super(true, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ClassSelfTypeRef;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ClassSelfTypeRef extends KtFakeSourceElementKind {
        public static final ClassSelfTypeRef INSTANCE = new ClassSelfTypeRef();

        private ClassSelfTypeRef() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ErrorTypeRef;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ErrorTypeRef extends KtFakeSourceElementKind {
        public static final ErrorTypeRef INSTANCE = new ErrorTypeRef();

        private ErrorTypeRef() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DefaultAccessor;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultAccessor extends KtFakeSourceElementKind {
        public static final DefaultAccessor INSTANCE = new DefaultAccessor();

        private DefaultAccessor() {
            super(true, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DelegatedPropertyAccessor;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DelegatedPropertyAccessor extends KtFakeSourceElementKind {
        public static final DelegatedPropertyAccessor INSTANCE = new DelegatedPropertyAccessor();

        private DelegatedPropertyAccessor() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitConstructor;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ImplicitConstructor extends KtFakeSourceElementKind {
        public static final ImplicitConstructor INSTANCE = new ImplicitConstructor();

        private ImplicitConstructor() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ConstructorTypeParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ConstructorTypeParameter extends KtFakeSourceElementKind {
        public static final ConstructorTypeParameter INSTANCE = new ConstructorTypeParameter();

        private ConstructorTypeParameter() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DelegatingConstructorCall;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DelegatingConstructorCall extends KtFakeSourceElementKind {
        public static final DelegatingConstructorCall INSTANCE = new DelegatingConstructorCall();

        private DelegatingConstructorCall() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$EnumInitializer;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EnumInitializer extends KtFakeSourceElementKind {
        public static final EnumInitializer INSTANCE = new EnumInitializer();

        private EnumInitializer() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$GeneratedLambdaLabel;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GeneratedLambdaLabel extends KtFakeSourceElementKind {
        public static final GeneratedLambdaLabel INSTANCE = new GeneratedLambdaLabel();

        private GeneratedLambdaLabel() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DanglingModifierList;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DanglingModifierList extends KtFakeSourceElementKind {
        public static final DanglingModifierList INSTANCE = new DanglingModifierList();

        private DanglingModifierList() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0002\u0003\u0004B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0002\u0005\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitReturn;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "FromExpressionBody", "FromLastStatement", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitReturn$FromExpressionBody;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitReturn$FromLastStatement;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class ImplicitReturn extends KtFakeSourceElementKind {
        public /* synthetic */ ImplicitReturn(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /* JADX INFO: compiled from: KtSourceElement.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitReturn$FromExpressionBody;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitReturn;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FromExpressionBody extends ImplicitReturn {
            public static final FromExpressionBody INSTANCE = new FromExpressionBody();

            private FromExpressionBody() {
                super(null);
            }
        }

        private ImplicitReturn() {
            super(false, 1, null);
        }

        /* JADX INFO: compiled from: KtSourceElement.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitReturn$FromLastStatement;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitReturn;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class FromLastStatement extends ImplicitReturn {
            public static final FromLastStatement INSTANCE = new FromLastStatement();

            private FromLastStatement() {
                super(null);
            }
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b6\u0018\u00002\u00020\u0001:\u0003\u0003\u0004\u0005B\u0007\b\u0004¢\u0006\u0002\u0010\u0002\u0082\u0001\u0003\u0006\u0007\b¨\u0006\t"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitUnit;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "IndexedAssignmentCoercion", "LambdaCoercion", "Return", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitUnit$IndexedAssignmentCoercion;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitUnit$LambdaCoercion;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitUnit$Return;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static abstract class ImplicitUnit extends KtFakeSourceElementKind {
        public /* synthetic */ ImplicitUnit(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private ImplicitUnit() {
            super(false, 1, null);
        }

        /* JADX INFO: compiled from: KtSourceElement.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitUnit$LambdaCoercion;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitUnit;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class LambdaCoercion extends ImplicitUnit {
            public static final LambdaCoercion INSTANCE = new LambdaCoercion();

            private LambdaCoercion() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: KtSourceElement.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitUnit$Return;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitUnit;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Return extends ImplicitUnit {
            public static final Return INSTANCE = new Return();

            private Return() {
                super(null);
            }
        }

        /* JADX INFO: compiled from: KtSourceElement.kt */
        @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitUnit$IndexedAssignmentCoercion;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitUnit;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class IndexedAssignmentCoercion extends ImplicitUnit {
            public static final IndexedAssignmentCoercion INSTANCE = new IndexedAssignmentCoercion();

            private IndexedAssignmentCoercion() {
                super(null);
            }
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$WrappedDelegate;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class WrappedDelegate extends KtFakeSourceElementKind {
        public static final WrappedDelegate INSTANCE = new WrappedDelegate();

        private WrappedDelegate() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredForLoop;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DesugaredForLoop extends KtFakeSourceElementKind {
        public static final DesugaredForLoop INSTANCE = new DesugaredForLoop();

        private DesugaredForLoop() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitInvokeCall;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ImplicitInvokeCall extends KtFakeSourceElementKind {
        public static final ImplicitInvokeCall INSTANCE = new ImplicitInvokeCall();

        private ImplicitInvokeCall() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ReferenceInAtomicQualifiedAccess;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ReferenceInAtomicQualifiedAccess extends KtFakeSourceElementKind {
        public static final ReferenceInAtomicQualifiedAccess INSTANCE = new ReferenceInAtomicQualifiedAccess();

        private ReferenceInAtomicQualifiedAccess() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$EnumGeneratedDeclaration;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class EnumGeneratedDeclaration extends KtFakeSourceElementKind {
        public static final EnumGeneratedDeclaration INSTANCE = new EnumGeneratedDeclaration();

        private EnumGeneratedDeclaration() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$WhenCondition;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class WhenCondition extends KtFakeSourceElementKind {
        public static final WhenCondition INSTANCE = new WhenCondition();

        private WhenCondition() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$PropertyFromParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PropertyFromParameter extends KtFakeSourceElementKind {
        public static final PropertyFromParameter INSTANCE = new PropertyFromParameter();

        private PropertyFromParameter() {
            super(true, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$SingleExpressionBlock;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SingleExpressionBlock extends KtFakeSourceElementKind {
        public static final SingleExpressionBlock INSTANCE = new SingleExpressionBlock();

        private SingleExpressionBlock() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ContractBlock;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ContractBlock extends KtFakeSourceElementKind {
        public static final ContractBlock INSTANCE = new ContractBlock();

        private ContractBlock() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredIncrementOrDecrement;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DesugaredIncrementOrDecrement extends KtFakeSourceElementKind {
        public static final DesugaredIncrementOrDecrement INSTANCE = new DesugaredIncrementOrDecrement();

        private DesugaredIncrementOrDecrement() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredPrefixSecondGetReference;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DesugaredPrefixSecondGetReference extends KtFakeSourceElementKind {
        public static final DesugaredPrefixSecondGetReference INSTANCE = new DesugaredPrefixSecondGetReference();

        private DesugaredPrefixSecondGetReference() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredPrefixNameReference;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DesugaredPrefixNameReference extends KtFakeSourceElementKind {
        public static final DesugaredPrefixNameReference INSTANCE = new DesugaredPrefixNameReference();

        private DesugaredPrefixNameReference() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredPostfixNameReference;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DesugaredPostfixNameReference extends KtFakeSourceElementKind {
        public static final DesugaredPostfixNameReference INSTANCE = new DesugaredPostfixNameReference();

        private DesugaredPostfixNameReference() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredInvertedContains;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DesugaredInvertedContains extends KtFakeSourceElementKind {
        public static final DesugaredInvertedContains INSTANCE = new DesugaredInvertedContains();

        private DesugaredInvertedContains() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DataClassGeneratedMembers;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DataClassGeneratedMembers extends KtFakeSourceElementKind {
        public static final DataClassGeneratedMembers INSTANCE = new DataClassGeneratedMembers();

        private DataClassGeneratedMembers() {
            super(true, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ArrayTypeFromVarargParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ArrayTypeFromVarargParameter extends KtFakeSourceElementKind {
        public static final ArrayTypeFromVarargParameter INSTANCE = new ArrayTypeFromVarargParameter();

        private ArrayTypeFromVarargParameter() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredComponentFunctionCall;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DesugaredComponentFunctionCall extends KtFakeSourceElementKind {
        public static final DesugaredComponentFunctionCall INSTANCE = new DesugaredComponentFunctionCall();

        private DesugaredComponentFunctionCall() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$SmartCastedTypeRef;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SmartCastedTypeRef extends KtFakeSourceElementKind {
        public static final SmartCastedTypeRef INSTANCE = new SmartCastedTypeRef();

        private SmartCastedTypeRef() {
            super(true, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$SmartCastExpression;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SmartCastExpression extends KtFakeSourceElementKind {
        public static final SmartCastExpression INSTANCE = new SmartCastExpression();

        private SmartCastExpression() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredSafeCallExpression;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DesugaredSafeCallExpression extends KtFakeSourceElementKind {
        public static final DesugaredSafeCallExpression INSTANCE = new DesugaredSafeCallExpression();

        private DesugaredSafeCallExpression() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$DesugaredCompoundAssignment;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class DesugaredCompoundAssignment extends KtFakeSourceElementKind {
        public static final DesugaredCompoundAssignment INSTANCE = new DesugaredCompoundAssignment();

        private DesugaredCompoundAssignment() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$GeneratedComparisonExpression;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class GeneratedComparisonExpression extends KtFakeSourceElementKind {
        public static final GeneratedComparisonExpression INSTANCE = new GeneratedComparisonExpression();

        private GeneratedComparisonExpression() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$WhenGeneratedSubject;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class WhenGeneratedSubject extends KtFakeSourceElementKind {
        public static final WhenGeneratedSubject INSTANCE = new WhenGeneratedSubject();

        private WhenGeneratedSubject() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ArrayAccessNameReference;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ArrayAccessNameReference extends KtFakeSourceElementKind {
        public static final ArrayAccessNameReference INSTANCE = new ArrayAccessNameReference();

        private ArrayAccessNameReference() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ArrayIndexExpressionReference;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ArrayIndexExpressionReference extends KtFakeSourceElementKind {
        public static final ArrayIndexExpressionReference INSTANCE = new ArrayIndexExpressionReference();

        private ArrayIndexExpressionReference() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$SuperCallImplicitType;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SuperCallImplicitType extends KtFakeSourceElementKind {
        public static final SuperCallImplicitType INSTANCE = new SuperCallImplicitType();

        private SuperCallImplicitType() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$SuperCallExplicitType;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SuperCallExplicitType extends KtFakeSourceElementKind {
        public static final SuperCallExplicitType INSTANCE = new SuperCallExplicitType();

        private SuperCallExplicitType() {
            super(true, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$VarargArgument;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class VarargArgument extends KtFakeSourceElementKind {
        public static final VarargArgument INSTANCE = new VarargArgument();

        private VarargArgument() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$CheckedSafeCallSubject;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class CheckedSafeCallSubject extends KtFakeSourceElementKind {
        public static final CheckedSafeCallSubject INSTANCE = new CheckedSafeCallSubject();

        private CheckedSafeCallSubject() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ItLambdaParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ItLambdaParameter extends KtFakeSourceElementKind {
        public static final ItLambdaParameter INSTANCE = new ItLambdaParameter();

        private ItLambdaParameter() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitJavaAnnotationConstructor;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ImplicitJavaAnnotationConstructor extends KtFakeSourceElementKind {
        public static final ImplicitJavaAnnotationConstructor INSTANCE = new ImplicitJavaAnnotationConstructor();

        private ImplicitJavaAnnotationConstructor() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitAnnotationAnnotationConstructorParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ImplicitAnnotationAnnotationConstructorParameter extends KtFakeSourceElementKind {
        public static final ImplicitAnnotationAnnotationConstructorParameter INSTANCE = new ImplicitAnnotationAnnotationConstructorParameter();

        private ImplicitAnnotationAnnotationConstructorParameter() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitJavaRecordConstructor;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ImplicitJavaRecordConstructor extends KtFakeSourceElementKind {
        public static final ImplicitJavaRecordConstructor INSTANCE = new ImplicitJavaRecordConstructor();

        private ImplicitJavaRecordConstructor() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitRecordConstructorParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ImplicitRecordConstructorParameter extends KtFakeSourceElementKind {
        public static final ImplicitRecordConstructorParameter INSTANCE = new ImplicitRecordConstructorParameter();

        private ImplicitRecordConstructorParameter() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$JavaRecordComponentFunction;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class JavaRecordComponentFunction extends KtFakeSourceElementKind {
        public static final JavaRecordComponentFunction INSTANCE = new JavaRecordComponentFunction();

        private JavaRecordComponentFunction() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$JavaRecordComponentField;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class JavaRecordComponentField extends KtFakeSourceElementKind {
        public static final JavaRecordComponentField INSTANCE = new JavaRecordComponentField();

        private JavaRecordComponentField() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ClassDelegationField;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ClassDelegationField extends KtFakeSourceElementKind {
        public static final ClassDelegationField INSTANCE = new ClassDelegationField();

        private ClassDelegationField() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$FromUseSiteTarget;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class FromUseSiteTarget extends KtFakeSourceElementKind {
        public static final FromUseSiteTarget INSTANCE = new FromUseSiteTarget();

        private FromUseSiteTarget() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ParameterNameAnnotationCall;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ParameterNameAnnotationCall extends KtFakeSourceElementKind {
        public static final ParameterNameAnnotationCall INSTANCE = new ParameterNameAnnotationCall();

        private ParameterNameAnnotationCall() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$IntToLongConversion;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class IntToLongConversion extends KtFakeSourceElementKind {
        public static final IntToLongConversion INSTANCE = new IntToLongConversion();

        private IntToLongConversion() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ReceiverFromType;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ReceiverFromType extends KtFakeSourceElementKind {
        public static final ReceiverFromType INSTANCE = new ReceiverFromType();

        private ReceiverFromType() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitReceiver;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ImplicitReceiver extends KtFakeSourceElementKind {
        public static final ImplicitReceiver INSTANCE = new ImplicitReceiver();

        private ImplicitReceiver() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$AssignmentLValueError;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class AssignmentLValueError extends KtFakeSourceElementKind {
        public static final AssignmentLValueError INSTANCE = new AssignmentLValueError();

        private AssignmentLValueError() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitReturnTypeOfLambdaValueParameter;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ImplicitReturnTypeOfLambdaValueParameter extends KtFakeSourceElementKind {
        public static final ImplicitReturnTypeOfLambdaValueParameter INSTANCE = new ImplicitReturnTypeOfLambdaValueParameter();

        private ImplicitReturnTypeOfLambdaValueParameter() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$SyntheticCall;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class SyntheticCall extends KtFakeSourceElementKind {
        public static final SyntheticCall INSTANCE = new SyntheticCall();

        private SyntheticCall() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$PropertyTypeFromGetterReturnType;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class PropertyTypeFromGetterReturnType extends KtFakeSourceElementKind {
        public static final PropertyTypeFromGetterReturnType INSTANCE = new PropertyTypeFromGetterReturnType();

        private PropertyTypeFromGetterReturnType() {
            super(false, 1, null);
        }
    }

    /* JADX INFO: compiled from: KtSourceElement.kt */
    @Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/KtFakeSourceElementKind$ImplicitImport;", "Lorg/jetbrains/kotlin/KtFakeSourceElementKind;", "()V", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ImplicitImport extends KtFakeSourceElementKind {
        public static final ImplicitImport INSTANCE = new ImplicitImport();

        private ImplicitImport() {
            super(false, 1, null);
        }
    }
}
