package org.jetbrains.kotlin.checkers.diagnostics.factories;

import java.util.Set;
import kotlin.Metadata;
import kotlin.NotImplementedError;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.checkers.utils.CheckerTestUtil;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.ParametrizedDiagnostic;
import org.jetbrains.kotlin.diagnostics.PositioningStrategies;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.diagnostics.rendering.Renderers;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: DebugInfoDiagnosticFactory1.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \u001e2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u00012\u00020\u0004:\u0001\u001eB\u0017\b\u0012\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bB\u001f\b\u0012\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJD\u0010\u0011\u001a\u0010\u0012\f\u0012\n \u0013*\u0004\u0018\u00010\u00020\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016R\u0014\u0010\u0005\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\nX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/factories/DebugInfoDiagnosticFactory1;", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "", "Lorg/jetbrains/kotlin/checkers/diagnostics/factories/DebugInfoDiagnosticFactory;", "name", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/Severity;)V", "withExplicitDefinitionOnly", "", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/Severity;Z)V", "getName", "()Ljava/lang/String;", "privateName", "getWithExplicitDefinitionOnly", "()Z", "createDiagnostic", "Lorg/jetbrains/kotlin/diagnostics/ParametrizedDiagnostic;", JvmProtoBufUtil.PLATFORM_TYPE_ID, CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/psi/KtElement;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DebugInfoDiagnosticFactory1 extends DiagnosticFactory1<PsiElement, String> implements DebugInfoDiagnosticFactory {
    private static final DebugInfoDiagnosticFactory1 CALL;
    private static final DebugInfoDiagnosticFactory1 CALLABLE_OWNER;

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    private static final DebugInfoDiagnosticFactory1 EXPRESSION_TYPE;
    private final String privateName;
    private final boolean withExplicitDefinitionOnly;

    public /* synthetic */ DebugInfoDiagnosticFactory1(String str, Severity severity, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, severity);
    }

    public /* synthetic */ DebugInfoDiagnosticFactory1(String str, Severity severity, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, severity, z);
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticFactory
    public String getName() {
        return "DEBUG_INFO_" + this.privateName;
    }

    @Override // org.jetbrains.kotlin.checkers.diagnostics.factories.DebugInfoDiagnosticFactory
    public boolean getWithExplicitDefinitionOnly() {
        return this.withExplicitDefinitionOnly;
    }

    @Override // org.jetbrains.kotlin.checkers.diagnostics.factories.DebugInfoDiagnosticFactory
    public ParametrizedDiagnostic<PsiElement> createDiagnostic(KtElement element, BindingContext bindingContext, DataFlowValueFactory dataFlowValueFactory, LanguageVersionSettings languageVersionSettings, ModuleDescriptorImpl moduleDescriptor) {
        ParametrizedDiagnostic<PsiElement> parametrizedDiagnosticOn;
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        String str = this.privateName;
        if (Intrinsics.areEqual(str, EXPRESSION_TYPE.privateName)) {
            KtElement ktElement = element;
            Pair<KotlinType, Set<KotlinType>> typeInfo = CheckerTestUtil.INSTANCE.getTypeInfo(ktElement, bindingContext, dataFlowValueFactory, languageVersionSettings, moduleDescriptor);
            parametrizedDiagnosticOn = on(ktElement, Renderers.INSTANCE.renderExpressionType(typeInfo.component1(), typeInfo.component2()));
        } else if (Intrinsics.areEqual(str, CALL.privateName)) {
            KtElement ktElement2 = element;
            Pair<FqNameUnsafe, String> callDebugInfo = CheckerTestUtil.INSTANCE.getCallDebugInfo(ktElement2, bindingContext);
            parametrizedDiagnosticOn = on(ktElement2, Renderers.INSTANCE.renderCallInfo(callDebugInfo.component1(), callDebugInfo.component2()));
        } else if (Intrinsics.areEqual(str, CALLABLE_OWNER.privateName)) {
            Call call = CallUtilKt.getCall(element, bindingContext);
            ResolvedCall<? extends CallableDescriptor> resolvedCall = call != null ? CallUtilKt.getResolvedCall(call, bindingContext) : null;
            if (resolvedCall != null) {
                CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
                Companion companion = INSTANCE;
                FqName fqNameSafe = DescriptorUtilsKt.getFqNameSafe(resultingDescriptor);
                DeclarationDescriptor containingDeclaration = resultingDescriptor.getContainingModule();
                Intrinsics.checkNotNullExpressionValue(containingDeclaration, "getContainingDeclaration(...)");
                parametrizedDiagnosticOn = on(element, companion.renderCallableOwner(fqNameSafe, DescriptorUtilsKt.fqNameOrNull(containingDeclaration), false));
            } else {
                parametrizedDiagnosticOn = on(element, "");
            }
        } else {
            throw new NotImplementedError("Creation diagnostic '" + getName() + "' isn't supported.");
        }
        Intrinsics.checkNotNull(parametrizedDiagnosticOn);
        return parametrizedDiagnosticOn;
    }

    private DebugInfoDiagnosticFactory1(String str, Severity severity) {
        super(severity, PositioningStrategies.DEFAULT);
        this.privateName = str;
        this.withExplicitDefinitionOnly = false;
    }

    private DebugInfoDiagnosticFactory1(String str, Severity severity, boolean z) {
        super(severity, PositioningStrategies.DEFAULT);
        this.privateName = str;
        this.withExplicitDefinitionOnly = z;
    }

    /* JADX INFO: compiled from: DebugInfoDiagnosticFactory1.kt */
    @Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0016\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u001e\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u0011J \u0010\u0012\u001a\u00020\r2\u0006\u0010\u0013\u001a\u00020\u00142\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0017\u001a\u00020\u0011J\"\u0010\u0012\u001a\u00020\r2\u0006\u0010\u0018\u001a\u00020\u00192\b\u0010\u001a\u001a\u0004\u0018\u00010\u00192\u0006\u0010\u001b\u001a\u00020\u0011H\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0007\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0006R\u0011\u0010\t\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0006¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/checkers/diagnostics/factories/DebugInfoDiagnosticFactory1$Companion;", "", "()V", "CALL", "Lorg/jetbrains/kotlin/checkers/diagnostics/factories/DebugInfoDiagnosticFactory1;", "getCALL", "()Lorg/jetbrains/kotlin/checkers/diagnostics/factories/DebugInfoDiagnosticFactory1;", "CALLABLE_OWNER", "getCALLABLE_OWNER", "EXPRESSION_TYPE", "getEXPRESSION_TYPE", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "name", "", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "withExplicitDefinitionOnly", "", "renderCallableOwner", "callableId", "Lorg/jetbrains/kotlin/name/CallableId;", "ownerId", "Lorg/jetbrains/kotlin/name/ClassId;", "isExplicit", "callableFqName", "Lorg/jetbrains/kotlin/name/FqName;", "ownerFqName", "isImplicit", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final DebugInfoDiagnosticFactory1 getEXPRESSION_TYPE() {
            return DebugInfoDiagnosticFactory1.EXPRESSION_TYPE;
        }

        public final DebugInfoDiagnosticFactory1 getCALLABLE_OWNER() {
            return DebugInfoDiagnosticFactory1.CALLABLE_OWNER;
        }

        public final DebugInfoDiagnosticFactory1 getCALL() {
            return DebugInfoDiagnosticFactory1.CALL;
        }

        public final DebugInfoDiagnosticFactory1 create(String name2, Severity severity) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(severity, "severity");
            return new DebugInfoDiagnosticFactory1(name2, severity, (DefaultConstructorMarker) null);
        }

        public final DebugInfoDiagnosticFactory1 create(String name2, Severity severity, boolean withExplicitDefinitionOnly) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(severity, "severity");
            return new DebugInfoDiagnosticFactory1(name2, severity, withExplicitDefinitionOnly, null);
        }

        public final String renderCallableOwner(CallableId callableId, ClassId ownerId, boolean isExplicit) {
            Intrinsics.checkNotNullParameter(callableId, "callableId");
            return renderCallableOwner(callableId.asFqNameForDebugInfo(), ownerId != null ? ownerId.asSingleFqName() : null, isExplicit);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final String renderCallableOwner(FqName callableFqName, FqName ownerFqName, boolean isImplicit) {
            String strAsString;
            StringBuilder sb = new StringBuilder();
            sb.append(callableFqName.asString());
            sb.append(" in ");
            if (isImplicit) {
                sb.append("implicit ");
            }
            if (ownerFqName == null || (strAsString = ownerFqName.asString()) == null) {
                strAsString = "<unknown>";
            }
            sb.append(strAsString);
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        EXPRESSION_TYPE = companion.create("EXPRESSION_TYPE", Severity.INFO, true);
        CALLABLE_OWNER = companion.create("CALLABLE_OWNER", Severity.INFO, true);
        CALL = companion.create("CALL", Severity.INFO, true);
    }
}
