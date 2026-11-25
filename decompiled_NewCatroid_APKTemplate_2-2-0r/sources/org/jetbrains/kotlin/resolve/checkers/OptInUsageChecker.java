package org.jetbrains.kotlin.resolve.checkers;

import com.google.android.gms.cast.MediaError;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.huawei.hms.framework.common.hianalytics.CrashHianalyticsData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DescriptorUtilKt;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.DiagnosticUtilsKt;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtAnnotated;
import org.jetbrains.kotlin.psi.KtAnnotationEntry;
import org.jetbrains.kotlin.psi.KtClassLiteralExpression;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtImportDirective;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtSuperTypeListEntry;
import org.jetbrains.kotlin.psi.KtValueArgument;
import org.jetbrains.kotlin.psi.KtValueArgumentList;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.ModuleAnnotationsResolver;
import org.jetbrains.kotlin.resolve.SinceKotlinAccessibility;
import org.jetbrains.kotlin.resolve.SinceKotlinUtilKt;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallCheckerContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.constants.ArrayValue;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.constants.EnumValue;
import org.jetbrains.kotlin.resolve.constants.KClassValue;
import org.jetbrains.kotlin.resolve.constants.StringValue;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationLevelValue;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationSettings;
import org.jetbrains.kotlin.resolve.deprecation.DescriptorBasedDeprecationInfo;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.sam.SamConstructorDescriptor;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.types.AbbreviatedType;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.KotlinTypeKt;
import org.jetbrains.kotlin.types.TypeProjection;
import org.jetbrains.kotlin.utils.SmartSet;

/* JADX INFO: compiled from: OptInUsageChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00132\u00020\u0001:\u0006\u0012\u0013\u0014\u0015\u0016\u0017B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u0006\u0012\u0002\b\u00030\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0016\u0010\u000f\u001a\u0004\u0018\u00010\u0010*\u00020\u00112\u0006\u0010\r\u001a\u00020\u000eH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker;", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "moduleAnnotationsResolver", "Lorg/jetbrains/kotlin/resolve/ModuleAnnotationsResolver;", "check", "", "resolvedCall", "Lorg/jetbrains/kotlin/resolve/calls/model/ResolvedCall;", "reportOn", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallCheckerContext;", "findRelevantDataClassPropertyIfAny", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "ClassifierUsage", "Companion", "OptInDiagnosticReporter", "OptInFactoryBasedReporter", "OptInReporterMultiplexer", "Overrides", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class OptInUsageChecker implements CallChecker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE;
    private static final Name ERROR_LEVEL;
    private static final Name LEVEL;
    private static final Name MESSAGE;
    private static final OptInReporterMultiplexer USAGE_DIAGNOSTICS;
    private static final Name WARNING_LEVEL;
    private final ModuleAnnotationsResolver moduleAnnotationsResolver;

    /* JADX INFO: compiled from: OptInUsageChecker.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\bf\u0018\u00002\u00020\u0001J*\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\fÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$OptInDiagnosticReporter;", "", "report", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "fqName", "Lorg/jetbrains/kotlin/name/FqName;", CrashHianalyticsData.MESSAGE, "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface OptInDiagnosticReporter {
        void report(BindingTrace trace, PsiElement element, FqName fqName, String message);
    }

    public OptInUsageChecker(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.moduleAnnotationsResolver = ModuleAnnotationsResolver.INSTANCE.getInstance(project);
    }

    /* JADX INFO: compiled from: OptInUsageChecker.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B3\u0012\u0018\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0003\u0012\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\b¢\u0006\u0002\u0010\tJ*\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00042\u0006\u0010\u0013\u001a\u00020\u00052\b\u0010\u0014\u001a\u0004\u0018\u00010\u0006H\u0016R\u001d\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\b¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR#\u0010\u0002\u001a\u0014\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$OptInFactoryBasedReporter;", "Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$OptInDiagnosticReporter;", "factory", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory2;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "Lorg/jetbrains/kotlin/name/FqName;", "", "defaultMessage", "Lkotlin/Function1;", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory2;Lkotlin/jvm/functions/Function1;)V", "getDefaultMessage", "()Lkotlin/jvm/functions/Function1;", "getFactory", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory2;", "report", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "fqName", CrashHianalyticsData.MESSAGE, "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class OptInFactoryBasedReporter implements OptInDiagnosticReporter {
        private final Function1<FqName, String> defaultMessage;
        private final DiagnosticFactory2<PsiElement, FqName, String> factory;

        /* JADX WARN: Multi-variable type inference failed */
        public OptInFactoryBasedReporter(DiagnosticFactory2<PsiElement, FqName, String> factory, Function1<? super FqName, String> defaultMessage) {
            Intrinsics.checkNotNullParameter(factory, "factory");
            Intrinsics.checkNotNullParameter(defaultMessage, "defaultMessage");
            this.factory = factory;
            this.defaultMessage = defaultMessage;
        }

        public final DiagnosticFactory2<PsiElement, FqName, String> getFactory() {
            return this.factory;
        }

        public final Function1<FqName, String> getDefaultMessage() {
            return this.defaultMessage;
        }

        /* JADX WARN: Code duplicated, block: B:9:0x0020  */
        @Override // org.jetbrains.kotlin.resolve.checkers.OptInUsageChecker.OptInDiagnosticReporter
        public void report(BindingTrace trace, PsiElement element, FqName fqName, String message) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(fqName, "fqName");
            DiagnosticFactory2<PsiElement, FqName, String> diagnosticFactory2 = this.factory;
            if (message == null) {
                message = this.defaultMessage.invoke(fqName);
            } else {
                if (StringsKt.isBlank(message)) {
                    message = null;
                }
                if (message == null) {
                    message = this.defaultMessage.invoke(fqName);
                }
            }
            Diagnostic diagnosticOn = diagnosticFactory2.on(element, fqName, message);
            Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
            DiagnosticUtilsKt.reportDiagnosticOnce(trace, diagnosticOn);
        }
    }

    /* JADX INFO: compiled from: OptInUsageChecker.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003¢\u0006\u0002\u0010\u0006J\t\u0010\u000b\u001a\u00020\u0003HÆ\u0003J\t\u0010\f\u001a\u00020\u0003HÆ\u0003J\t\u0010\r\u001a\u00020\u0003HÆ\u0003J'\u0010\u000e\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0012\u001a\u00020\u0013HÖ\u0001J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\b¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$OptInReporterMultiplexer;", "", CompilerOptions.WARNING, "Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$OptInDiagnosticReporter;", "error", "futureError", "(Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$OptInDiagnosticReporter;Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$OptInDiagnosticReporter;Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$OptInDiagnosticReporter;)V", "getError", "()Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$OptInDiagnosticReporter;", "getFutureError", "getWarning", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class OptInReporterMultiplexer {
        private final OptInDiagnosticReporter error;
        private final OptInDiagnosticReporter futureError;
        private final OptInDiagnosticReporter warning;

        public static /* synthetic */ OptInReporterMultiplexer copy$default(OptInReporterMultiplexer optInReporterMultiplexer, OptInDiagnosticReporter optInDiagnosticReporter, OptInDiagnosticReporter optInDiagnosticReporter2, OptInDiagnosticReporter optInDiagnosticReporter3, int i, Object obj) {
            if ((i & 1) != 0) {
                optInDiagnosticReporter = optInReporterMultiplexer.warning;
            }
            if ((i & 2) != 0) {
                optInDiagnosticReporter2 = optInReporterMultiplexer.error;
            }
            if ((i & 4) != 0) {
                optInDiagnosticReporter3 = optInReporterMultiplexer.futureError;
            }
            return optInReporterMultiplexer.copy(optInDiagnosticReporter, optInDiagnosticReporter2, optInDiagnosticReporter3);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final OptInDiagnosticReporter getWarning() {
            return this.warning;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final OptInDiagnosticReporter getError() {
            return this.error;
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final OptInDiagnosticReporter getFutureError() {
            return this.futureError;
        }

        public final OptInReporterMultiplexer copy(OptInDiagnosticReporter warning, OptInDiagnosticReporter error, OptInDiagnosticReporter futureError) {
            Intrinsics.checkNotNullParameter(warning, "warning");
            Intrinsics.checkNotNullParameter(error, "error");
            Intrinsics.checkNotNullParameter(futureError, "futureError");
            return new OptInReporterMultiplexer(warning, error, futureError);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof OptInReporterMultiplexer)) {
                return false;
            }
            OptInReporterMultiplexer optInReporterMultiplexer = (OptInReporterMultiplexer) other;
            return Intrinsics.areEqual(this.warning, optInReporterMultiplexer.warning) && Intrinsics.areEqual(this.error, optInReporterMultiplexer.error) && Intrinsics.areEqual(this.futureError, optInReporterMultiplexer.futureError);
        }

        public int hashCode() {
            return (((this.warning.hashCode() * 31) + this.error.hashCode()) * 31) + this.futureError.hashCode();
        }

        public String toString() {
            return "OptInReporterMultiplexer(warning=" + this.warning + ", error=" + this.error + ", futureError=" + this.futureError + ')';
        }

        public OptInReporterMultiplexer(OptInDiagnosticReporter warning, OptInDiagnosticReporter error, OptInDiagnosticReporter futureError) {
            Intrinsics.checkNotNullParameter(warning, "warning");
            Intrinsics.checkNotNullParameter(error, "error");
            Intrinsics.checkNotNullParameter(futureError, "futureError");
            this.warning = warning;
            this.error = error;
            this.futureError = futureError;
        }

        public final OptInDiagnosticReporter getWarning() {
            return this.warning;
        }

        public final OptInDiagnosticReporter getError() {
            return this.error;
        }

        public final OptInDiagnosticReporter getFutureError() {
            return this.futureError;
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.checkers.CallChecker
    public void check(ResolvedCall<?> resolvedCall, PsiElement reportOn, CallCheckerContext context) {
        PropertyDescriptor propertyDescriptorFindRelevantDataClassPropertyIfAny;
        Intrinsics.checkNotNullParameter(resolvedCall, "resolvedCall");
        Intrinsics.checkNotNullParameter(reportOn, "reportOn");
        Intrinsics.checkNotNullParameter(context, "context");
        CallableDescriptor resultingDescriptor = resolvedCall.getResultingDescriptor();
        BindingContext bindingContext = context.getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
        LanguageVersionSettings languageVersionSettings = context.getLanguageVersionSettings();
        Companion companion = INSTANCE;
        Intrinsics.checkNotNull(resultingDescriptor);
        Set setLoadOptIns$default = Companion.loadOptIns$default(companion, resultingDescriptor, this.moduleAnnotationsResolver, bindingContext, languageVersionSettings, null, false, false, false, 120, null);
        if (resultingDescriptor instanceof FunctionDescriptor) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) resultingDescriptor;
            if (functionDescriptor.getKind() == CallableMemberDescriptor.Kind.SYNTHESIZED && (propertyDescriptorFindRelevantDataClassPropertyIfAny = findRelevantDataClassPropertyIfAny(functionDescriptor, context)) != null) {
                companion.reportNotAllowedOptIns(SetsKt.plus(setLoadOptIns$default, (Iterable) Companion.loadOptIns$default(companion, propertyDescriptorFindRelevantDataClassPropertyIfAny, this.moduleAnnotationsResolver, bindingContext, languageVersionSettings, null, false, false, false, 120, null)), reportOn, context);
                return;
            }
        }
        if (resultingDescriptor instanceof SamConstructorDescriptor) {
            Set<OptInDescription> setLoadOptIns$default2 = Companion.loadOptIns$default(companion, ((SamConstructorDescriptor) resultingDescriptor).getSingleAbstractMethod(), this.moduleAnnotationsResolver, bindingContext, languageVersionSettings, null, false, false, false, 120, null);
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setLoadOptIns$default2, 10));
            for (OptInDescription optInDescription : setLoadOptIns$default2) {
                arrayList.add(new OptInDescription(optInDescription.getAnnotationFqName(), OptInDescription.Severity.WARNING, optInDescription.getMessage(), false));
            }
            INSTANCE.reportNotAllowedOptIns(arrayList, reportOn, context);
        }
        INSTANCE.reportNotAllowedOptIns(setLoadOptIns$default, reportOn, context);
    }

    private final PropertyDescriptor findRelevantDataClassPropertyIfAny(FunctionDescriptor functionDescriptor, CallCheckerContext callCheckerContext) {
        List<ValueParameterDescriptor> valueParameters;
        String strAsString = functionDescriptor.getShortName().asString();
        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
        Integer intOrNull = StringsKt.toIntOrNull(StringsKt.removePrefix(strAsString, (CharSequence) "component"));
        DeclarationDescriptor containingModule = functionDescriptor.getContainingModule();
        Intrinsics.checkNotNullExpressionValue(containingModule, "getContainingDeclaration(...)");
        if (containingModule instanceof ClassDescriptor) {
            ClassDescriptor classDescriptor = (ClassDescriptor) containingModule;
            if (classDescriptor.isData() && intOrNull != null) {
                ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = classDescriptor.mo6347getUnsubstitutedPrimaryConstructor();
                ValueParameterDescriptor valueParameterDescriptor = (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null || (valueParameters = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters()) == null) ? null : (ValueParameterDescriptor) CollectionsKt.getOrNull(valueParameters, intOrNull.intValue() - 1);
                if (valueParameterDescriptor != null) {
                    return (PropertyDescriptor) callCheckerContext.getTrace().getContext().get(BindingContext.VALUE_PARAMETER_AS_PROPERTY, valueParameterDescriptor);
                }
            }
        }
        return null;
    }

    /* JADX INFO: compiled from: OptInUsageChecker.kt */
    @Metadata(d1 = {"\u0000¤\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J>\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0012\u0010\u0010\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000b0\u00112\u0012\u0010\u0013\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u000b0\u0011J!\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0015\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0016\u001a\u00020\u0012H\u0000¢\u0006\u0002\b\u0017J4\u0010\u0018\u001a\u00020\u000b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\bJ$\u0010\u0018\u001a\u00020\u000b2\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u001d2\u0006\u0010!\u001a\u00020\"J0\u0010#\u001a\u00020$*\u00020\u001d2!\u0010%\u001a\u001d\u0012\u0013\u0012\u00110\u001d¢\u0006\f\b&\u0012\b\b'\u0012\u0004\b\b(\u001c\u0012\u0004\u0012\u00020$0\u0011H\u0082\bJ!\u0010(\u001a\u00020$*\u00020\u001d2\u0006\u0010)\u001a\u00020\u00152\u0006\u0010*\u001a\u00020+H\u0000¢\u0006\u0002\b,J\u001c\u0010-\u001a\u00020$*\u00020\u001d2\u0006\u0010)\u001a\u00020\u00152\u0006\u0010*\u001a\u00020+H\u0002J\u001c\u0010.\u001a\u00020$*\u00020\u001d2\u0006\u0010)\u001a\u00020\u00152\u0006\u0010*\u001a\u00020+H\u0002J\"\u0010/\u001a\u00020$*\u00020\u001d2\u0006\u0010)\u001a\u00020\u00152\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010*\u001a\u00020+J,\u0010/\u001a\u00020$*\u00020\u001d2\u0006\u0010)\u001a\u00020\u00152\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010*\u001a\u00020+2\u0006\u00100\u001a\u00020$H\u0002J$\u0010/\u001a\u00020$*\u00020\u001d2\u0006\u0010)\u001a\u00020\u00152\u0006\u0010!\u001a\u00020\"2\u0006\u00100\u001a\u00020$H\u0002J'\u00101\u001a\u0004\u0018\u00010\u001b*\u0002022\b\b\u0002\u00103\u001a\u00020$2\b\b\u0002\u00100\u001a\u00020$H\u0000¢\u0006\u0002\b4JV\u00105\u001a\b\u0012\u0004\u0012\u00020\u001b06*\u0002072\u0006\u00108\u001a\u0002092\u0006\u0010!\u001a\u00020+2\u0006\u0010\u000e\u001a\u00020\u000f2\u000e\b\u0002\u0010:\u001a\b\u0012\u0004\u0012\u0002070;2\b\b\u0002\u00103\u001a\u00020$2\b\b\u0002\u0010<\u001a\u00020$2\b\b\u0002\u0010=\u001a\u00020$JD\u00105\u001a\b\u0012\u0004\u0012\u00020\u001b06*\u0004\u0018\u00010>2\u0006\u00108\u001a\u0002092\u0006\u0010!\u001a\u00020+2\u0006\u0010\u000e\u001a\u00020\u000f2\f\u0010?\u001a\b\u0012\u0004\u0012\u0002070;2\b\b\u0002\u0010@\u001a\u00020$H\u0002J\u0018\u0010A\u001a\u0004\u0018\u00010\u001b*\u00020B2\b\u0010!\u001a\u0004\u0018\u00010+H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006C"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$Companion;", "", "()V", "ERROR_LEVEL", "Lorg/jetbrains/kotlin/name/Name;", "LEVEL", "MESSAGE", "USAGE_DIAGNOSTICS", "Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$OptInReporterMultiplexer;", "WARNING_LEVEL", "checkCompilerArguments", "", "module", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "reportError", "Lkotlin/Function1;", "", "reportWarning", "getDefaultDiagnosticMessage", "Lorg/jetbrains/kotlin/name/FqName;", "prefix", "getDefaultDiagnosticMessage$frontend", "reportNotAllowedOptIns", "descriptions", "", "Lorg/jetbrains/kotlin/resolve/checkers/OptInDescription;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "diagnostics", "context", "Lorg/jetbrains/kotlin/resolve/checkers/CheckerContext;", "anyParentMatches", "", "predicate", "Lkotlin/ParameterName;", "name", "isDeclarationAnnotatedWith", "annotationFqName", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "isDeclarationAnnotatedWith$frontend", "isElementAnnotatedWithOptIn", "isElementAnnotatedWithSubclassOptInRequired", "isOptInAllowed", "subclassesOnly", "loadOptInForMarkerAnnotation", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "useFutureError", "loadOptInForMarkerAnnotation$frontend", "loadOptIns", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "moduleAnnotationsResolver", "Lorg/jetbrains/kotlin/resolve/ModuleAnnotationsResolver;", "visited", "", "useMarkersFromContainer", "fromSupertype", "Lorg/jetbrains/kotlin/types/KotlinType;", "visitedClassifiers", "warningsOnly", "loadSubclassOptInRequired", "Lorg/jetbrains/kotlin/descriptors/annotations/AnnotationDescriptor;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {

        /* JADX INFO: compiled from: OptInUsageChecker.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;
            public static final /* synthetic */ int[] $EnumSwitchMapping$1;

            static {
                int[] iArr = new int[OptInDescription.Severity.values().length];
                try {
                    iArr[OptInDescription.Severity.WARNING.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[OptInDescription.Severity.ERROR.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[OptInDescription.Severity.FUTURE_ERROR.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
                int[] iArr2 = new int[DeprecationLevelValue.values().length];
                try {
                    iArr2[DeprecationLevelValue.WARNING.ordinal()] = 1;
                } catch (NoSuchFieldError unused4) {
                }
                try {
                    iArr2[DeprecationLevelValue.ERROR.ordinal()] = 2;
                } catch (NoSuchFieldError unused5) {
                }
                try {
                    iArr2[DeprecationLevelValue.HIDDEN.ordinal()] = 3;
                } catch (NoSuchFieldError unused6) {
                }
                $EnumSwitchMapping$1 = iArr2;
            }
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final Function1<FqName, String> getDefaultDiagnosticMessage$frontend(final String prefix) {
            Intrinsics.checkNotNullParameter(prefix, "prefix");
            return new Function1<FqName, String>() { // from class: org.jetbrains.kotlin.resolve.checkers.OptInUsageChecker$Companion$getDefaultDiagnosticMessage$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                public final String invoke(FqName fqName) {
                    Intrinsics.checkNotNullParameter(fqName, "fqName");
                    OptInNames optInNames = OptInNames.INSTANCE;
                    String str = prefix;
                    String strAsString = fqName.asString();
                    Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                    return optInNames.buildDefaultDiagnosticMessage(str, strAsString);
                }
            };
        }

        public final void reportNotAllowedOptIns(Collection<OptInDescription> descriptions, PsiElement element, CheckerContext context) {
            Intrinsics.checkNotNullParameter(descriptions, "descriptions");
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(context, "context");
            reportNotAllowedOptIns(descriptions, element, context.getLanguageVersionSettings(), context.getTrace(), OptInUsageChecker.USAGE_DIAGNOSTICS);
        }

        public final void reportNotAllowedOptIns(Collection<OptInDescription> descriptions, PsiElement element, LanguageVersionSettings languageVersionSettings, BindingTrace trace, OptInReporterMultiplexer diagnostics) {
            OptInDiagnosticReporter warning;
            Intrinsics.checkNotNullParameter(descriptions, "descriptions");
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            Intrinsics.checkNotNullParameter(trace, "trace");
            Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
            for (OptInDescription optInDescription : descriptions) {
                FqName annotationFqName = optInDescription.getAnnotationFqName();
                OptInDescription.Severity severity = optInDescription.getSeverity();
                String message = optInDescription.getMessage();
                boolean subclassesOnly = optInDescription.getSubclassesOnly();
                BindingContext bindingContext = trace.getContext();
                Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
                if (!isOptInAllowed(element, annotationFqName, languageVersionSettings, bindingContext, subclassesOnly)) {
                    int i = WhenMappings.$EnumSwitchMapping$0[severity.ordinal()];
                    if (i == 1) {
                        warning = diagnostics.getWarning();
                    } else if (i == 2) {
                        warning = diagnostics.getError();
                    } else {
                        if (i != 3) {
                            throw new NoWhenBranchMatchedException();
                        }
                        warning = diagnostics.getFutureError();
                    }
                    warning.report(trace, element, annotationFqName, message);
                }
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ Set loadOptIns$default(Companion companion, DeclarationDescriptor declarationDescriptor, ModuleAnnotationsResolver moduleAnnotationsResolver, BindingContext bindingContext, LanguageVersionSettings languageVersionSettings, Set set, boolean z, boolean z2, boolean z3, int i, Object obj) {
            return companion.loadOptIns(declarationDescriptor, moduleAnnotationsResolver, bindingContext, languageVersionSettings, (i & 8) != 0 ? new LinkedHashSet() : set, (i & 16) != 0 ? false : z, (i & 32) != 0 ? true : z2, (i & 64) != 0 ? false : z3);
        }

        public final Set<OptInDescription> loadOptIns(DeclarationDescriptor declarationDescriptor, ModuleAnnotationsResolver moduleAnnotationsResolver, BindingContext context, LanguageVersionSettings languageVersionSettings, Set<DeclarationDescriptor> visited, boolean z, boolean z2, boolean z3) {
            OptInDescription optInDescriptionLoadSubclassOptInRequired;
            Intrinsics.checkNotNullParameter(declarationDescriptor, "<this>");
            Intrinsics.checkNotNullParameter(moduleAnnotationsResolver, "moduleAnnotationsResolver");
            Intrinsics.checkNotNullParameter(context, "context");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            Intrinsics.checkNotNullParameter(visited, "visited");
            if (!visited.add(declarationDescriptor)) {
                return SetsKt.emptySet();
            }
            SmartSet smartSetCreate = SmartSet.INSTANCE.create();
            if (declarationDescriptor instanceof CallableMemberDescriptor) {
                CallableMemberDescriptor callableMemberDescriptor = (CallableMemberDescriptor) declarationDescriptor;
                if (callableMemberDescriptor.getKind() == CallableMemberDescriptor.Kind.FAKE_OVERRIDE) {
                    for (CallableMemberDescriptor callableMemberDescriptor2 : callableMemberDescriptor.getOverriddenDescriptors()) {
                        Intrinsics.checkNotNull(callableMemberDescriptor2);
                        smartSetCreate.addAll(loadOptIns$default(this, callableMemberDescriptor2, moduleAnnotationsResolver, context, languageVersionSettings, visited, !languageVersionSettings.supportsFeature(LanguageFeature.OptInContagiousSignatures), false, false, 64, null));
                        if (z2) {
                            DeclarationDescriptor containingDeclaration = callableMemberDescriptor.getContainingModule();
                            ClassDescriptor classDescriptor = containingDeclaration instanceof ClassDescriptor ? (ClassDescriptor) containingDeclaration : null;
                            if (classDescriptor != null) {
                                smartSetCreate.addAll(loadOptIns$default(OptInUsageChecker.INSTANCE, classDescriptor, moduleAnnotationsResolver, context, languageVersionSettings, visited, z, false, false, 96, null));
                            }
                        }
                    }
                    return smartSetCreate;
                }
            }
            for (AnnotationDescriptor annotationDescriptor : declarationDescriptor.getAnnotations()) {
                SmartSet smartSet = smartSetCreate;
                ClassDescriptor annotationClass = DescriptorUtilsKt.getAnnotationClass(annotationDescriptor);
                if (annotationClass == null || (optInDescriptionLoadSubclassOptInRequired = loadOptInForMarkerAnnotation$frontend$default(this, annotationClass, z, false, 2, null)) == null) {
                    optInDescriptionLoadSubclassOptInRequired = z3 ? loadSubclassOptInRequired(annotationDescriptor, context) : null;
                }
                org.jetbrains.kotlin.utils.CollectionsKt.addIfNotNull(smartSet, optInDescriptionLoadSubclassOptInRequired);
            }
            if ((declarationDescriptor instanceof CallableDescriptor) && !(declarationDescriptor instanceof ClassConstructorDescriptor)) {
                CallableDescriptor callableDescriptor = (CallableDescriptor) declarationDescriptor;
                smartSetCreate.addAll(loadOptIns$default(this, callableDescriptor.getReturnType(), moduleAnnotationsResolver, context, languageVersionSettings, visited, false, 16, null));
                ReceiverParameterDescriptor extensionReceiverParameter = callableDescriptor.getExtensionReceiverParameter();
                smartSetCreate.addAll(loadOptIns$default(this, extensionReceiverParameter != null ? extensionReceiverParameter.getType() : null, moduleAnnotationsResolver, context, languageVersionSettings, visited, false, 16, null));
            }
            if (declarationDescriptor instanceof FunctionDescriptor) {
                List<ValueParameterDescriptor> valueParameters = ((FunctionDescriptor) declarationDescriptor).getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                Iterator<T> it = valueParameters.iterator();
                while (it.hasNext()) {
                    smartSetCreate.addAll(OptInUsageChecker.INSTANCE.loadOptIns(((ValueParameterDescriptor) it.next()).getType(), moduleAnnotationsResolver, context, languageVersionSettings, visited, declarationDescriptor instanceof ConstructorDescriptor));
                }
            }
            if (declarationDescriptor instanceof TypeAliasDescriptor) {
                smartSetCreate.addAll(loadOptIns$default(this, ((TypeAliasDescriptor) declarationDescriptor).getExpandedType(), moduleAnnotationsResolver, context, languageVersionSettings, visited, false, 16, null));
            }
            Annotations annotations = declarationDescriptor.getAnnotations();
            if (!(annotations instanceof Collection) || !((Collection) annotations).isEmpty()) {
                Iterator<AnnotationDescriptor> it2 = annotations.iterator();
                while (it2.hasNext()) {
                    if (Intrinsics.areEqual(it2.next().getFqName(), OptInNames.INSTANCE.getWAS_EXPERIMENTAL_FQ_NAME())) {
                        SinceKotlinAccessibility sinceKotlinAccessibilityCheckSinceKotlinVersionAccessibility = SinceKotlinUtilKt.checkSinceKotlinVersionAccessibility(declarationDescriptor, languageVersionSettings);
                        if (!(sinceKotlinAccessibilityCheckSinceKotlinVersionAccessibility instanceof SinceKotlinAccessibility.NotAccessibleButWasExperimental)) {
                            break;
                        }
                        List<ClassDescriptor> markerClasses = ((SinceKotlinAccessibility.NotAccessibleButWasExperimental) sinceKotlinAccessibilityCheckSinceKotlinVersionAccessibility).getMarkerClasses();
                        ArrayList arrayList = new ArrayList();
                        Iterator<T> it3 = markerClasses.iterator();
                        while (it3.hasNext()) {
                            OptInDescription optInDescriptionLoadOptInForMarkerAnnotation$frontend$default = loadOptInForMarkerAnnotation$frontend$default(OptInUsageChecker.INSTANCE, (ClassDescriptor) it3.next(), z, false, 2, null);
                            if (optInDescriptionLoadOptInForMarkerAnnotation$frontend$default != null) {
                                arrayList.add(optInDescriptionLoadOptInForMarkerAnnotation$frontend$default);
                            }
                        }
                        smartSetCreate.addAll(arrayList);
                        break;
                    }
                }
            }
            DeclarationDescriptor containingModule = declarationDescriptor.getContainingModule();
            if (z2 && (containingModule instanceof ClassDescriptor) && !(declarationDescriptor instanceof ConstructorDescriptor)) {
                smartSetCreate.addAll(loadOptIns$default(this, containingModule, moduleAnnotationsResolver, context, languageVersionSettings, visited, z, false, false, 96, null));
            }
            return smartSetCreate;
        }

        static /* synthetic */ Set loadOptIns$default(Companion companion, KotlinType kotlinType, ModuleAnnotationsResolver moduleAnnotationsResolver, BindingContext bindingContext, LanguageVersionSettings languageVersionSettings, Set set, boolean z, int i, Object obj) {
            if ((i & 16) != 0) {
                z = false;
            }
            return companion.loadOptIns(kotlinType, moduleAnnotationsResolver, bindingContext, languageVersionSettings, set, z);
        }

        private final Set<OptInDescription> loadOptIns(KotlinType kotlinType, ModuleAnnotationsResolver moduleAnnotationsResolver, BindingContext bindingContext, LanguageVersionSettings languageVersionSettings, Set<DeclarationDescriptor> set, boolean z) {
            if (kotlinType == null || KotlinTypeKt.isError(kotlinType)) {
                return SetsKt.emptySet();
            }
            Set setEmptySet = null;
            if (kotlinType instanceof AbbreviatedType) {
                AbbreviatedType abbreviatedType = (AbbreviatedType) kotlinType;
                ClassifierDescriptor this$0 = abbreviatedType.getAbbreviation().getConstructor().getThis$0();
                if (this$0 != null) {
                    setEmptySet = loadOptIns$default(this, this$0, moduleAnnotationsResolver, bindingContext, languageVersionSettings, set, z || !languageVersionSettings.supportsFeature(LanguageFeature.OptInContagiousSignatures), false, false, 96, null);
                }
                if (setEmptySet == null) {
                    setEmptySet = SetsKt.emptySet();
                }
                return SetsKt.plus(setEmptySet, (Iterable) loadOptIns$default(this, abbreviatedType.getExpandedType(), moduleAnnotationsResolver, bindingContext, languageVersionSettings, set, false, 16, null));
            }
            ClassifierDescriptor this$1 = kotlinType.getConstructor().getThis$0();
            if (this$1 != null) {
                setEmptySet = loadOptIns$default(this, this$1, moduleAnnotationsResolver, bindingContext, languageVersionSettings, set, z || !languageVersionSettings.supportsFeature(LanguageFeature.OptInContagiousSignatures), false, false, 96, null);
            }
            if (setEmptySet == null) {
                setEmptySet = SetsKt.emptySet();
            }
            Set set2 = setEmptySet;
            List<TypeProjection> arguments = kotlinType.getArguments();
            ArrayList arrayList = new ArrayList();
            for (TypeProjection typeProjection : arguments) {
                CollectionsKt.addAll(arrayList, typeProjection.isStarProjection() ? SetsKt.emptySet() : loadOptIns$default(OptInUsageChecker.INSTANCE, typeProjection.getNullableAnyType(), moduleAnnotationsResolver, bindingContext, languageVersionSettings, set, false, 16, null));
            }
            return SetsKt.plus(set2, (Iterable) arrayList);
        }

        public static /* synthetic */ OptInDescription loadOptInForMarkerAnnotation$frontend$default(Companion companion, ClassDescriptor classDescriptor, boolean z, boolean z2, int i, Object obj) {
            if ((i & 1) != 0) {
                z = false;
            }
            if ((i & 2) != 0) {
                z2 = false;
            }
            return companion.loadOptInForMarkerAnnotation$frontend(classDescriptor, z, z2);
        }

        public final OptInDescription loadOptInForMarkerAnnotation$frontend(ClassDescriptor classDescriptor, boolean z, boolean z2) {
            OptInDescription.Severity default_severity;
            Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
            AnnotationDescriptor annotationDescriptorMo6752findAnnotation = classDescriptor.getAnnotations().mo6752findAnnotation(OptInNames.INSTANCE.getREQUIRES_OPT_IN_FQ_NAME());
            if (annotationDescriptorMo6752findAnnotation == null) {
                return null;
            }
            Map<Name, ConstantValue<?>> allValueArguments = annotationDescriptorMo6752findAnnotation.getAllValueArguments();
            ConstantValue<?> constantValue = allValueArguments.get(OptInUsageChecker.LEVEL);
            EnumValue enumValue = constantValue instanceof EnumValue ? (EnumValue) constantValue : null;
            Name enumEntryName = enumValue != null ? enumValue.getEnumEntryName() : null;
            if (Intrinsics.areEqual(enumEntryName, OptInUsageChecker.WARNING_LEVEL)) {
                default_severity = OptInDescription.Severity.WARNING;
            } else if (Intrinsics.areEqual(enumEntryName, OptInUsageChecker.ERROR_LEVEL)) {
                default_severity = z ? OptInDescription.Severity.FUTURE_ERROR : OptInDescription.Severity.ERROR;
            } else if (OptInDescription.INSTANCE.getDEFAULT_SEVERITY() == OptInDescription.Severity.ERROR && z) {
                default_severity = OptInDescription.Severity.FUTURE_ERROR;
            } else {
                default_severity = OptInDescription.INSTANCE.getDEFAULT_SEVERITY();
            }
            ConstantValue<?> constantValue2 = allValueArguments.get(OptInUsageChecker.MESSAGE);
            StringValue stringValue = constantValue2 instanceof StringValue ? (StringValue) constantValue2 : null;
            return new OptInDescription(DescriptorUtilsKt.getFqNameSafe(classDescriptor), default_severity, stringValue != null ? stringValue.getValue() : null, z2);
        }

        private final OptInDescription loadSubclassOptInRequired(AnnotationDescriptor annotationDescriptor, BindingContext bindingContext) {
            if (!Intrinsics.areEqual(annotationDescriptor.getFqName(), OptInNames.INSTANCE.getSUBCLASS_OPT_IN_REQUIRED_FQ_NAME())) {
                return null;
            }
            ConstantValue<?> constantValue = annotationDescriptor.getAllValueArguments().get(OptInNames.INSTANCE.getOPT_IN_ANNOTATION_CLASS());
            if (!(constantValue instanceof KClassValue)) {
                return null;
            }
            KClassValue.Value value = ((KClassValue) constantValue).getValue();
            if (!(value instanceof KClassValue.Value.NormalClass)) {
                return null;
            }
            ClassDescriptor classDescriptor = bindingContext != null ? (ClassDescriptor) bindingContext.get(BindingContext.FQNAME_TO_CLASS_DESCRIPTOR, ((KClassValue.Value.NormalClass) value).getClassId().asSingleFqName().toUnsafe()) : null;
            if (classDescriptor != null) {
                return loadOptInForMarkerAnnotation$frontend$default(this, classDescriptor, false, true, 1, null);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isOptInAllowed(PsiElement psiElement, FqName fqName, CheckerContext checkerContext, boolean z) {
            LanguageVersionSettings languageVersionSettings = checkerContext.getLanguageVersionSettings();
            BindingContext bindingContext = checkerContext.getTrace().getContext();
            Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
            return isOptInAllowed(psiElement, fqName, languageVersionSettings, bindingContext, z);
        }

        public final boolean isOptInAllowed(PsiElement psiElement, FqName annotationFqName, LanguageVersionSettings languageVersionSettings, BindingContext bindingContext) {
            Intrinsics.checkNotNullParameter(psiElement, "<this>");
            Intrinsics.checkNotNullParameter(annotationFqName, "annotationFqName");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            return isOptInAllowed(psiElement, annotationFqName, languageVersionSettings, bindingContext, false);
        }

        private final boolean isOptInAllowed(PsiElement psiElement, FqName fqName, LanguageVersionSettings languageVersionSettings, BindingContext bindingContext, boolean z) {
            if (((List) languageVersionSettings.getFlag(AnalysisFlags.getOptIn())).contains(fqName.asString())) {
                return true;
            }
            boolean z2 = z && PsiTreeUtil.getParentOfType(psiElement, KtSuperTypeListEntry.class, true) != null;
            while (!OptInUsageChecker.INSTANCE.isDeclarationAnnotatedWith$frontend(psiElement, fqName, bindingContext) && !OptInUsageChecker.INSTANCE.isElementAnnotatedWithOptIn(psiElement, fqName, bindingContext)) {
                if (z2 && OptInUsageChecker.INSTANCE.isElementAnnotatedWithSubclassOptInRequired(psiElement, fqName, bindingContext)) {
                    return true;
                }
                psiElement = psiElement.getLightParent();
                if (psiElement == null) {
                    return false;
                }
                Intrinsics.checkNotNull(psiElement);
            }
            return true;
        }

        public final boolean isDeclarationAnnotatedWith$frontend(PsiElement psiElement, FqName annotationFqName, BindingContext bindingContext) {
            DeclarationDescriptor declarationDescriptor;
            Intrinsics.checkNotNullParameter(psiElement, "<this>");
            Intrinsics.checkNotNullParameter(annotationFqName, "annotationFqName");
            Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
            return (psiElement instanceof KtDeclaration) && (declarationDescriptor = (DeclarationDescriptor) bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, psiElement)) != null && declarationDescriptor.getAnnotations().hasAnnotation(annotationFqName);
        }

        private final boolean isElementAnnotatedWithOptIn(PsiElement psiElement, FqName fqName, BindingContext bindingContext) {
            if (psiElement instanceof KtAnnotated) {
                List<KtAnnotationEntry> annotationEntries = ((KtAnnotated) psiElement).getAnnotationEntries();
                Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
                List<KtAnnotationEntry> list = annotationEntries;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) bindingContext.get(BindingContext.ANNOTATION, (KtAnnotationEntry) it.next());
                        if (annotationDescriptor != null && Intrinsics.areEqual(annotationDescriptor.getFqName(), OptInNames.INSTANCE.getOPT_IN_FQ_NAME())) {
                            ConstantValue<?> constantValue = annotationDescriptor.getAllValueArguments().get(OptInNames.INSTANCE.getOPT_IN_ANNOTATION_CLASS());
                            if (constantValue instanceof ArrayValue) {
                                List<? extends ConstantValue<?>> value = ((ArrayValue) constantValue).getValue();
                                if (!(value instanceof Collection) || !value.isEmpty()) {
                                    Iterator<T> it2 = value.iterator();
                                    while (it2.hasNext()) {
                                        ConstantValue constantValue2 = (ConstantValue) it2.next();
                                        if (constantValue2 instanceof KClassValue) {
                                            KClassValue.Value value2 = ((KClassValue) constantValue2).getValue();
                                            if (value2 instanceof KClassValue.Value.NormalClass) {
                                                KClassValue.Value.NormalClass normalClass = (KClassValue.Value.NormalClass) value2;
                                                if (Intrinsics.areEqual(normalClass.getClassId().asSingleFqName(), fqName) && normalClass.getArrayDimensions() == 0) {
                                                    return true;
                                                }
                                            } else {
                                                continue;
                                            }
                                        }
                                    }
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
            return false;
        }

        private final boolean isElementAnnotatedWithSubclassOptInRequired(PsiElement psiElement, FqName fqName, BindingContext bindingContext) {
            if (psiElement instanceof KtAnnotated) {
                List<KtAnnotationEntry> annotationEntries = ((KtAnnotated) psiElement).getAnnotationEntries();
                Intrinsics.checkNotNullExpressionValue(annotationEntries, "getAnnotationEntries(...)");
                List<KtAnnotationEntry> list = annotationEntries;
                if (!(list instanceof Collection) || !list.isEmpty()) {
                    Iterator<T> it = list.iterator();
                    while (it.hasNext()) {
                        AnnotationDescriptor annotationDescriptor = (AnnotationDescriptor) bindingContext.get(BindingContext.ANNOTATION, (KtAnnotationEntry) it.next());
                        if (annotationDescriptor != null && Intrinsics.areEqual(annotationDescriptor.getFqName(), OptInNames.INSTANCE.getSUBCLASS_OPT_IN_REQUIRED_FQ_NAME())) {
                            ConstantValue<?> constantValue = annotationDescriptor.getAllValueArguments().get(OptInNames.INSTANCE.getOPT_IN_ANNOTATION_CLASS());
                            if (constantValue instanceof KClassValue) {
                                KClassValue.Value value = ((KClassValue) constantValue).getValue();
                                if (value instanceof KClassValue.Value.NormalClass) {
                                    KClassValue.Value.NormalClass normalClass = (KClassValue.Value.NormalClass) value;
                                    if (Intrinsics.areEqual(normalClass.getClassId().asSingleFqName(), fqName) && normalClass.getArrayDimensions() == 0) {
                                        return true;
                                    }
                                } else {
                                    continue;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
            return false;
        }

        public final void checkCompilerArguments(ModuleDescriptor module, LanguageVersionSettings languageVersionSettings, Function1<? super String, Unit> reportError, Function1<? super String, Unit> reportWarning) {
            Intrinsics.checkNotNullParameter(module, "module");
            Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
            Intrinsics.checkNotNullParameter(reportError, "reportError");
            Intrinsics.checkNotNullParameter(reportWarning, "reportWarning");
            DeprecationResolver deprecationResolver = new DeprecationResolver(new LockBasedStorageManager("OptInUsageChecker"), languageVersionSettings, DeprecationSettings.Default.INSTANCE);
            for (String str : (Iterable) languageVersionSettings.getFlag(AnalysisFlags.getOptIn())) {
                if (!Intrinsics.areEqual(str, OptInNames.INSTANCE.getREQUIRES_OPT_IN_FQ_NAME().asString())) {
                    checkCompilerArguments$checkAnnotation(module, reportWarning, deprecationResolver, reportError, str);
                }
            }
        }

        private static final boolean checkCompilerArguments$checkAnnotation(ModuleDescriptor moduleDescriptor, Function1<? super String, Unit> function1, DeprecationResolver deprecationResolver, Function1<? super String, Unit> function2, String str) {
            Function1<? super String, Unit> function3;
            ClassDescriptor classDescriptorResolveClassByFqName = DescriptorUtilKt.resolveClassByFqName(moduleDescriptor, new FqName(str), NoLookupLocation.FOR_NON_TRACKED_SCOPE);
            if (classDescriptorResolveClassByFqName == null) {
                function1.invoke("Opt-in requirement marker " + str + " is unresolved. Please make sure it's present in the module dependencies");
                return false;
            }
            if (loadOptInForMarkerAnnotation$frontend$default(OptInUsageChecker.INSTANCE, classDescriptorResolveClassByFqName, false, false, 3, null) == null) {
                function1.invoke("Class " + str + " is not an opt-in requirement marker");
                return false;
            }
            for (DescriptorBasedDeprecationInfo descriptorBasedDeprecationInfo : deprecationResolver.getDeprecations(classDescriptorResolveClassByFqName)) {
                int i = WhenMappings.$EnumSwitchMapping$1[descriptorBasedDeprecationInfo.getDeprecationLevel().ordinal()];
                if (i == 1) {
                    function3 = function1;
                } else {
                    if (i != 2 && i != 3) {
                        throw new NoWhenBranchMatchedException();
                    }
                    function3 = function2;
                }
                StringBuilder sbAppend = new StringBuilder("Opt-in requirement marker ").append(str).append(" is deprecated");
                String message = descriptorBasedDeprecationInfo.getMessage();
                String str2 = message != null ? ". " + message : null;
                if (str2 == null) {
                    str2 = "";
                }
                function3.invoke(sbAppend.append(str2).toString());
            }
            return true;
        }
    }

    static {
        Companion companion = new Companion(null);
        INSTANCE = companion;
        Name nameIdentifier = Name.identifier(FirebaseAnalytics.Param.LEVEL);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        LEVEL = nameIdentifier;
        Name nameIdentifier2 = Name.identifier(CrashHianalyticsData.MESSAGE);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier2, "identifier(...)");
        MESSAGE = nameIdentifier2;
        Name nameIdentifier3 = Name.identifier("WARNING");
        Intrinsics.checkNotNullExpressionValue(nameIdentifier3, "identifier(...)");
        WARNING_LEVEL = nameIdentifier3;
        Name nameIdentifier4 = Name.identifier(MediaError.ERROR_TYPE_ERROR);
        Intrinsics.checkNotNullExpressionValue(nameIdentifier4, "identifier(...)");
        ERROR_LEVEL = nameIdentifier4;
        DiagnosticFactory2<PsiElement, FqName, String> OPT_IN_USAGE = Errors.OPT_IN_USAGE;
        Intrinsics.checkNotNullExpressionValue(OPT_IN_USAGE, "OPT_IN_USAGE");
        OptInFactoryBasedReporter optInFactoryBasedReporter = new OptInFactoryBasedReporter(OPT_IN_USAGE, companion.getDefaultDiagnosticMessage$frontend(OptInNames.INSTANCE.buildMessagePrefix("should")));
        DiagnosticFactory2<PsiElement, FqName, String> OPT_IN_USAGE_ERROR = Errors.OPT_IN_USAGE_ERROR;
        Intrinsics.checkNotNullExpressionValue(OPT_IN_USAGE_ERROR, "OPT_IN_USAGE_ERROR");
        OptInFactoryBasedReporter optInFactoryBasedReporter2 = new OptInFactoryBasedReporter(OPT_IN_USAGE_ERROR, companion.getDefaultDiagnosticMessage$frontend(OptInNames.INSTANCE.buildMessagePrefix("must")));
        DiagnosticFactory2<PsiElement, FqName, String> OPT_IN_USAGE_FUTURE_ERROR = Errors.OPT_IN_USAGE_FUTURE_ERROR;
        Intrinsics.checkNotNullExpressionValue(OPT_IN_USAGE_FUTURE_ERROR, "OPT_IN_USAGE_FUTURE_ERROR");
        USAGE_DIAGNOSTICS = new OptInReporterMultiplexer(optInFactoryBasedReporter, optInFactoryBasedReporter2, new OptInFactoryBasedReporter(OPT_IN_USAGE_FUTURE_ERROR, companion.getDefaultDiagnosticMessage$frontend("This declaration is experimental due to signature types and its usage must be marked (will become an error in future releases)")));
    }

    /* JADX INFO: compiled from: OptInUsageChecker.kt */
    @Metadata(d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\u0018\u0010\u000f\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0010H\u0002J\u0014\u0010\u0011\u001a\u00020\u0012*\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\f\u0010\u0015\u001a\u00020\u0012*\u00020\fH\u0002R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$ClassifierUsage;", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageChecker;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "moduleAnnotationsResolver", "Lorg/jetbrains/kotlin/resolve/ModuleAnnotationsResolver;", "check", "", "targetDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageCheckerContext;", "checkUsageOfKotlinRequiresOptInOrOptIn", "Lorg/jetbrains/kotlin/resolve/checkers/CheckerContext;", "isUsageAsOptInArgument", "", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "isUsageAsQualifier", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ClassifierUsage implements ClassifierUsageChecker {
        private final ModuleAnnotationsResolver moduleAnnotationsResolver;

        public ClassifierUsage(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            this.moduleAnnotationsResolver = ModuleAnnotationsResolver.INSTANCE.getInstance(project);
        }

        /* JADX WARN: Code duplicated, block: B:43:0x0100  */
        @Override // org.jetbrains.kotlin.resolve.checkers.ClassifierUsageChecker
        public void check(ClassifierDescriptor targetDescriptor, PsiElement element, ClassifierUsageCheckerContext context) {
            ClassDescriptor classDescriptor;
            boolean z;
            Intrinsics.checkNotNullParameter(targetDescriptor, "targetDescriptor");
            Intrinsics.checkNotNullParameter(element, "element");
            Intrinsics.checkNotNullParameter(context, "context");
            Name name2 = targetDescriptor.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            if (Intrinsics.areEqual(name2, OptInNames.INSTANCE.getREQUIRES_OPT_IN_FQ_NAME().shortName()) || Intrinsics.areEqual(name2, OptInNames.INSTANCE.getOPT_IN_FQ_NAME().shortName())) {
                FqName fqNameSafe = DescriptorUtilsKt.getFqNameSafe(targetDescriptor);
                if (Intrinsics.areEqual(fqNameSafe, OptInNames.INSTANCE.getREQUIRES_OPT_IN_FQ_NAME()) || Intrinsics.areEqual(fqNameSafe, OptInNames.INSTANCE.getOPT_IN_FQ_NAME())) {
                    checkUsageOfKotlinRequiresOptInOrOptIn(element, context);
                    return;
                }
            }
            if (targetDescriptor instanceof ClassDescriptor) {
                classDescriptor = (ClassDescriptor) targetDescriptor;
            } else {
                classDescriptor = targetDescriptor instanceof TypeAliasDescriptor ? ((TypeAliasDescriptor) targetDescriptor).getClassDescriptor() : null;
            }
            ClassDescriptor classDescriptor2 = classDescriptor;
            BindingContext bindingContext = context.getTrace().getContext();
            Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
            if (classDescriptor2 != null && Companion.loadOptInForMarkerAnnotation$frontend$default(OptInUsageChecker.INSTANCE, classDescriptor2, false, false, 3, null) != null && !UsageCheckerUtilsKt.isUsageAsAnnotationOrImport(element) && !isUsageAsOptInArgument(element, bindingContext)) {
                context.getTrace().report(Errors.OPT_IN_MARKER_CAN_ONLY_BE_USED_AS_ANNOTATION_OR_ARGUMENT_IN_OPT_IN.on(element));
            }
            if (PsiTreeUtil.getParentOfType(element, KtImportDirective.class, false) == null) {
                KtClassOrObject ktClassOrObject = (KtClassOrObject) PsiTreeUtil.getParentOfType(element, KtClassOrObject.class, true);
                Companion companion = OptInUsageChecker.INSTANCE;
                ClassifierDescriptor classifierDescriptor = targetDescriptor;
                ModuleAnnotationsResolver moduleAnnotationsResolver = this.moduleAnnotationsResolver;
                LanguageVersionSettings languageVersionSettings = context.getLanguageVersionSettings();
                if (ktClassOrObject != null) {
                    List<KtSuperTypeListEntry> superTypeListEntries = ktClassOrObject.getSuperTypeListEntries();
                    if ((superTypeListEntries instanceof Collection) && superTypeListEntries.isEmpty()) {
                        z = false;
                    } else {
                        Iterator<T> it = superTypeListEntries.iterator();
                        while (it.hasNext()) {
                            if (Intrinsics.areEqual(((KtSuperTypeListEntry) it.next()).getTypeAsUserType(), element.getLightParent())) {
                                z = true;
                            }
                        }
                        z = false;
                    }
                } else {
                    z = false;
                }
                OptInUsageChecker.INSTANCE.reportNotAllowedOptIns(Companion.loadOptIns$default(companion, classifierDescriptor, moduleAnnotationsResolver, bindingContext, languageVersionSettings, null, false, false, z, 56, null), element, context);
            }
        }

        private final void checkUsageOfKotlinRequiresOptInOrOptIn(PsiElement element, CheckerContext context) {
            List list = (List) context.getLanguageVersionSettings().getFlag(AnalysisFlags.getOptIn());
            if (!context.getLanguageVersionSettings().supportsFeature(LanguageFeature.OptInRelease) && !list.contains(OptInNames.INSTANCE.getREQUIRES_OPT_IN_FQ_NAME().asString())) {
                context.getTrace().report(Errors.OPT_IN_IS_NOT_ENABLED.on(element));
            }
            if (UsageCheckerUtilsKt.isUsageAsAnnotationOrImport(element) || isUsageAsQualifier(element)) {
                return;
            }
            context.getTrace().report(Errors.OPT_IN_CAN_ONLY_BE_USED_AS_ANNOTATION.on(element));
        }

        private final boolean isUsageAsQualifier(PsiElement psiElement) {
            if (!(psiElement instanceof KtSimpleNameExpression)) {
                return false;
            }
            KtQualifiedExpression topmostParentQualifiedExpressionForSelector = KtPsiUtilKt.getTopmostParentQualifiedExpressionForSelector((KtSimpleNameExpression) psiElement);
            KtQualifiedExpression ktQualifiedExpression = topmostParentQualifiedExpressionForSelector != null ? topmostParentQualifiedExpressionForSelector : (KtExpression) psiElement;
            PsiElement parent = ktQualifiedExpression.getLightParent();
            KtDotQualifiedExpression ktDotQualifiedExpression = parent instanceof KtDotQualifiedExpression ? (KtDotQualifiedExpression) parent : null;
            return Intrinsics.areEqual(ktDotQualifiedExpression != null ? ktDotQualifiedExpression.getReceiverExpression() : null, ktQualifiedExpression);
        }

        private final boolean isUsageAsOptInArgument(PsiElement psiElement, BindingContext bindingContext) {
            AnnotationDescriptor annotationDescriptor;
            KtQualifiedExpression topmostParentQualifiedExpressionForSelector;
            KtSimpleNameExpression ktSimpleNameExpression = psiElement instanceof KtSimpleNameExpression ? (KtSimpleNameExpression) psiElement : null;
            if (ktSimpleNameExpression != null && (topmostParentQualifiedExpressionForSelector = KtPsiUtilKt.getTopmostParentQualifiedExpressionForSelector(ktSimpleNameExpression)) != null) {
                psiElement = topmostParentQualifiedExpressionForSelector;
            }
            PsiElement lightParent = psiElement.getLightParent();
            if (lightParent instanceof KtClassLiteralExpression) {
                KtClassLiteralExpression ktClassLiteralExpression = (KtClassLiteralExpression) lightParent;
                if ((ktClassLiteralExpression.getLightParent() instanceof KtValueArgument) && (ktClassLiteralExpression.getLightParent().getLightParent() instanceof KtValueArgumentList)) {
                    PsiElement lightParent2 = ktClassLiteralExpression.getLightParent().getLightParent().getLightParent();
                    if ((lightParent2 instanceof KtAnnotationEntry) && (annotationDescriptor = (AnnotationDescriptor) bindingContext.get(BindingContext.ANNOTATION, lightParent2)) != null && (Intrinsics.areEqual(annotationDescriptor.getFqName(), OptInNames.INSTANCE.getOPT_IN_FQ_NAME()) || Intrinsics.areEqual(annotationDescriptor.getFqName(), OptInNames.INSTANCE.getWAS_EXPERIMENTAL_FQ_NAME()) || Intrinsics.areEqual(annotationDescriptor.getFqName(), OptInNames.INSTANCE.getSUBCLASS_OPT_IN_REQUIRED_FQ_NAME()))) {
                        return true;
                    }
                }
            }
            return false;
        }
    }

    /* JADX INFO: compiled from: OptInUsageChecker.kt */
    @Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J \u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/OptInUsageChecker$Overrides;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "moduleAnnotationsResolver", "Lorg/jetbrains/kotlin/resolve/ModuleAnnotationsResolver;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Overrides implements DeclarationChecker {
        private final ModuleAnnotationsResolver moduleAnnotationsResolver;

        /* JADX INFO: compiled from: OptInUsageChecker.kt */
        @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
        public /* synthetic */ class WhenMappings {
            public static final /* synthetic */ int[] $EnumSwitchMapping$0;

            static {
                int[] iArr = new int[OptInDescription.Severity.values().length];
                try {
                    iArr[OptInDescription.Severity.WARNING.ordinal()] = 1;
                } catch (NoSuchFieldError unused) {
                }
                try {
                    iArr[OptInDescription.Severity.ERROR.ordinal()] = 2;
                } catch (NoSuchFieldError unused2) {
                }
                try {
                    iArr[OptInDescription.Severity.FUTURE_ERROR.ordinal()] = 3;
                } catch (NoSuchFieldError unused3) {
                }
                $EnumSwitchMapping$0 = iArr;
            }
        }

        public Overrides(Project project) {
            Intrinsics.checkNotNullParameter(project, "project");
            this.moduleAnnotationsResolver = ModuleAnnotationsResolver.INSTANCE.getInstance(project);
        }

        @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
        public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
            Pair pair;
            PsiElement nameIdentifier;
            Intrinsics.checkNotNullParameter(declaration, "declaration");
            Intrinsics.checkNotNullParameter(descriptor, "descriptor");
            Intrinsics.checkNotNullParameter(context, "context");
            if (descriptor instanceof CallableMemberDescriptor) {
                Collection<? extends CallableMemberDescriptor> overriddenDescriptors = ((CallableMemberDescriptor) descriptor).getOverriddenDescriptors();
                Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
                ArrayList arrayList = new ArrayList();
                for (CallableMemberDescriptor callableMemberDescriptor : overriddenDescriptors) {
                    Companion companion = OptInUsageChecker.INSTANCE;
                    Intrinsics.checkNotNull(callableMemberDescriptor);
                    ModuleAnnotationsResolver moduleAnnotationsResolver = this.moduleAnnotationsResolver;
                    BindingContext bindingContext = context.getTrace().getContext();
                    Intrinsics.checkNotNullExpressionValue(bindingContext, "getBindingContext(...)");
                    Set setLoadOptIns$default = Companion.loadOptIns$default(companion, callableMemberDescriptor, moduleAnnotationsResolver, bindingContext, context.getLanguageVersionSettings(), null, false, false, false, 120, null);
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(setLoadOptIns$default, 10));
                    Iterator it = setLoadOptIns$default.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(TuplesKt.to((OptInDescription) it.next(), callableMemberDescriptor));
                    }
                    CollectionsKt.addAll(arrayList, arrayList2);
                }
                for (Map.Entry entry : MapsKt.toMap(arrayList).entrySet()) {
                    OptInDescription optInDescription = (OptInDescription) entry.getKey();
                    CallableMemberDescriptor callableMemberDescriptor2 = (CallableMemberDescriptor) entry.getValue();
                    KtDeclaration ktDeclaration = declaration;
                    if (!OptInUsageChecker.INSTANCE.isOptInAllowed(ktDeclaration, optInDescription.getAnnotationFqName(), context, optInDescription.getSubclassesOnly())) {
                        KtNamedDeclaration ktNamedDeclaration = declaration instanceof KtNamedDeclaration ? (KtNamedDeclaration) declaration : null;
                        if (ktNamedDeclaration != null && (nameIdentifier = ktNamedDeclaration.mo7392getNameIdentifier()) != null) {
                            ktDeclaration = nameIdentifier;
                        }
                        int i = WhenMappings.$EnumSwitchMapping$0[optInDescription.getSeverity().ordinal()];
                        if (i == 1) {
                            pair = TuplesKt.to(Errors.OPT_IN_OVERRIDE, "should");
                        } else {
                            if (i != 2 && i != 3) {
                                throw new NoWhenBranchMatchedException();
                            }
                            pair = TuplesKt.to(Errors.OPT_IN_OVERRIDE_ERROR, "must");
                        }
                        DiagnosticFactory2 diagnosticFactory2 = (DiagnosticFactory2) pair.component1();
                        String str = (String) pair.component2();
                        OptInNames optInNames = OptInNames.INSTANCE;
                        String strAsString = callableMemberDescriptor2.getContainingModule().getShortName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                        String message = optInDescription.getMessage();
                        String strAsString2 = optInDescription.getAnnotationFqName().asString();
                        Intrinsics.checkNotNullExpressionValue(strAsString2, "asString(...)");
                        context.getTrace().report(diagnosticFactory2.on(ktDeclaration, optInDescription.getAnnotationFqName(), optInNames.buildOverrideMessage(strAsString, message, str, strAsString2)));
                    }
                }
            }
        }
    }
}
