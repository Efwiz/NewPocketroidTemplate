package org.jetbrains.kotlin.resolve;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.impl.source.DummyHolder;
import org.jetbrains.kotlin.com.intellij.util.SmartList;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.CommonConfigurationKeysKt;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility;
import org.jetbrains.kotlin.descriptors.DescriptorWithDeprecation;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PackageViewDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.progress.ProgressIndicatorAndCompilationCanceledStatus;
import org.jetbrains.kotlin.psi.KtCallExpression;
import org.jetbrains.kotlin.psi.KtDotQualifiedExpression;
import org.jetbrains.kotlin.psi.KtDoubleColonExpression;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtImportDirective;
import org.jetbrains.kotlin.psi.KtImportInfo;
import org.jetbrains.kotlin.psi.KtPackageDirective;
import org.jetbrains.kotlin.psi.KtQualifiedExpression;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeArgumentList;
import org.jetbrains.kotlin.psi.KtTypeProjection;
import org.jetbrains.kotlin.psi.KtUserType;
import org.jetbrains.kotlin.psi.codeFragmentUtil.CodeFragmentUtilKt;
import org.jetbrains.kotlin.psi.psiUtil.KtPsiUtilKt;
import org.jetbrains.kotlin.resolve.calls.CallExpressionElement;
import org.jetbrains.kotlin.resolve.calls.CallExpressionUnrollerKt;
import org.jetbrains.kotlin.resolve.calls.checkers.UnderscoreUsageChecker;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.CompositePrioritizedImportingScope;
import org.jetbrains.kotlin.resolve.scopes.ImportingScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ClassQualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.PackageQualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.Qualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.QualifierKt;
import org.jetbrains.kotlin.resolve.scopes.receivers.Receiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.resolve.scopes.receivers.TypeAliasQualifier;
import org.jetbrains.kotlin.resolve.scopes.receivers.TypeParameterQualifier;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;
import org.jetbrains.kotlin.types.expressions.DoubleColonExpressionResolverKt;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;
import org.jetbrains.kotlin.utils.CallOnceFunction;

/* JADX INFO: compiled from: QualifiedExpressionResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0080\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u0000 h2\u00020\u0001:\u0005hijklB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J$\u0010\u0007\u001a\u00020\b2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0002J\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u00102\u0006\u0010\u0011\u001a\u00020\u00122\b\u0010\u0013\u001a\u0004\u0018\u00010\u0010H\u0002J:\u0010\u0014\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0010H\u0002J$\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u001e2\u0006\u0010\"\u001a\u00020#H\u0002J8\u0010$\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000b\u001a\u00020\f2\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u001c0\u001b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0010JB\u0010%\u001a\u0004\u0018\u00010\u00152\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010'\u001a\u00020\u001f2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0010H\u0002J.\u0010(\u001a\u00020\b2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010)\u001a\u00020*2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010+\u001a\u00020,H\u0002J\u001e\u0010-\u001a\u00020.2\u0006\u0010\r\u001a\u00020!2\u0006\u0010/\u001a\u0002002\u0006\u00101\u001a\u000202J&\u00103\u001a\u0002042\u0006\u0010\r\u001a\u0002052\u0006\u0010/\u001a\u0002002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u00106\u001a\u000207J&\u00108\u001a\u0002042\u0006\u00109\u001a\u00020:2\u0006\u0010/\u001a\u0002002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u00106\u001a\u000207J\u0016\u0010;\u001a\u0002072\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0002J\"\u0010<\u001a\u0004\u0018\u00010=2\u0006\u0010\r\u001a\u00020\u000e2\b\u0010>\u001a\u0004\u0018\u00010?2\u0006\u00101\u001a\u00020@J\u001e\u0010A\u001a\u00020\b2\u0006\u0010B\u001a\u00020C2\u0006\u0010D\u001a\u00020\u00192\u0006\u0010\u000b\u001a\u00020\fJ0\u0010E\u001a\b\u0012\u0004\u0012\u00020F0\u001e2\u0006\u0010\r\u001a\u00020!2\u0006\u00101\u001a\u00020@2\u0012\u0010G\u001a\u000e\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u0002070HJ8\u0010I\u001a\u0002042\f\u0010J\u001a\b\u0012\u0004\u0012\u00020K0\u001e2\b\u0010L\u001a\u0004\u0018\u00010\n2\u0006\u0010/\u001a\u0002002\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010M\u001a\u000207H\u0002JD\u0010N\u001a\u0004\u0018\u00010\n2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000b\u001a\u00020\f2\b\u0010O\u001a\u0004\u0018\u00010\n2\b\u0010P\u001a\u0004\u0018\u0001002\u0006\u0010+\u001a\u00020,H\u0002Jh\u0010Q\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\n\u0012\u0004\u0012\u00020#0R2\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u000b\u001a\u00020\f2\b\u0010O\u001a\u0004\u0018\u00010\n2\b\u0010P\u001a\u0004\u0018\u0001002\u0006\u0010+\u001a\u00020,2\u0016\b\u0002\u0010G\u001a\u0010\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u000207\u0018\u00010HH\u0002J\"\u0010S\u001a\u0004\u0018\u00010=2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010T\u001a\u00020\u000e2\u0006\u0010\t\u001a\u00020\nH\u0002JD\u0010U\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010T\u001a\u0004\u0018\u00010\u000e2\f\u0010V\u001a\b\u0012\u0004\u0012\u00020\n0\u001b2\b\u0010O\u001a\u0004\u0018\u00010\n2\u0006\u0010+\u001a\u00020,2\b\b\u0002\u0010M\u001a\u000207H\u0002JB\u0010U\u001a\u0004\u0018\u00010=2\u0006\u0010\u000b\u001a\u00020\f2\b\u0010T\u001a\u0004\u0018\u00010\u000e2\b\u0010\t\u001a\u0004\u0018\u00010\n2\b\u0010O\u001a\u0004\u0018\u00010\n2\u0006\u0010+\u001a\u00020,2\b\b\u0002\u0010M\u001a\u000207H\u0002J(\u0010W\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010X\u001a\u00020\n2\u0006\u0010'\u001a\u00020\u001fH\u0002J\u001c\u0010Y\u001a\b\u0012\u0004\u0012\u00020K0\u001e*\u0002052\b\b\u0002\u0010Z\u001a\u000207H\u0002J\u0012\u0010Y\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e*\u00020[H\u0002J\u001e\u0010Y\u001a\u0014\u0012\n\u0012\b\u0012\u0004\u0012\u00020K0\u001e\u0012\u0004\u0012\u0002070R*\u00020:H\u0002J\u0010\u0010\\\u001a\u0004\u0018\u00010\n*\u0004\u0018\u00010\nH\u0002J.\u0010]\u001a\u0004\u0018\u00010^*\u0002002\u0006\u0010_\u001a\u00020`2\u0006\u0010a\u001a\u00020b2\b\u0010c\u001a\u0004\u0018\u0001052\u0006\u0010\u000b\u001a\u00020\fJ\u0014\u0010d\u001a\u0004\u0018\u00010^*\u00020e2\u0006\u0010f\u001a\u00020\u001fJ6\u0010g\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020#0R*\u00020\u00192\f\u0010&\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001e2\u0006\u0010\u000b\u001a\u00020\f2\u0006\u0010+\u001a\u00020,H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006m"}, d2 = {"Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver;", "", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "(Lorg/jetbrains/kotlin/config/LanguageVersionSettings;)V", "getLanguageVersionSettings", "()Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "checkNotEnumEntry", "", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "expression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "computePackageFragmentToCheck", "Lorg/jetbrains/kotlin/descriptors/PackageFragmentDescriptor;", "containingFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "packageFragmentForVisibilityCheck", "doProcessImportReference", "Lorg/jetbrains/kotlin/resolve/scopes/ImportingScope;", "importDirective", "Lorg/jetbrains/kotlin/psi/KtImportInfo;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "excludedImportNames", "", "Lorg/jetbrains/kotlin/name/FqName;", "mapToQualifierParts", "", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$QualifierPart;", "qualifiedExpressions", "Lorg/jetbrains/kotlin/psi/KtQualifiedExpression;", "skipLast", "", "processImportReference", "processSingleImport", "path", "lastPart", "recordPackageViews", "packageView", "Lorg/jetbrains/kotlin/descriptors/PackageViewDescriptor;", "position", "Lorg/jetbrains/kotlin/resolve/QualifierPosition;", "resolveClassOrPackageInQualifiedExpression", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$QualifiedExpressionResolveResult;", "scope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "context", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "resolveDescriptorForDoubleColonLHS", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$TypeQualifierResolutionResult;", "Lorg/jetbrains/kotlin/psi/KtExpression;", "isDebuggerContext", "", "resolveDescriptorForType", "userType", "Lorg/jetbrains/kotlin/psi/KtUserType;", "resolveInIDEMode", "resolveNameExpressionAsQualifierForDiagnostics", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/Qualifier;", "receiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/Receiver;", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "resolvePackageHeader", "packageDirective", "Lorg/jetbrains/kotlin/psi/KtPackageDirective;", "module", "resolveQualifierInExpressionAndUnroll", "Lorg/jetbrains/kotlin/resolve/calls/CallExpressionElement;", "isValue", "Lkotlin/Function1;", "resolveQualifierPartListForType", "qualifierPartList", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$ExpressionQualifierPart;", "ownerDescriptor", "isQualifier", "resolveToPackageOrClass", "shouldBeVisibleFrom", "scopeForFirstPart", "resolveToPackageOrClassPrefix", "Lkotlin/Pair;", "storeQualifier", "referenceExpression", "storeResult", "descriptors", "tryResolveDescriptorsWhichCannotBeImported", "packageOrClassDescriptor", "asQualifierPartList", "doubleColonLHS", "Lorg/jetbrains/kotlin/psi/KtImportInfo$ImportContent;", "classDescriptorFromTypeAlias", "findClassifierAndReportDeprecationIfNeeded", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "lookupLocation", "Lorg/jetbrains/kotlin/incremental/KotlinLookupLocation;", "reportOn", "getContributedClassifier", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "qualifierPart", "quickResolveToPackage", "Companion", "ExpressionQualifierPart", "QualifiedExpressionResolveResult", "QualifierPart", "TypeQualifierResolutionResult", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class QualifiedExpressionResolver {
    public static final String ROOT_PREFIX_FOR_IDE_RESOLUTION_MODE = "_root_ide_package_";
    public static final String ROOT_PREFIX_FOR_IDE_RESOLUTION_MODE_WITH_DOT = "_root_ide_package_.";
    private final LanguageVersionSettings languageVersionSettings;

    public QualifiedExpressionResolver(LanguageVersionSettings languageVersionSettings) {
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        this.languageVersionSettings = languageVersionSettings;
    }

    public final LanguageVersionSettings getLanguageVersionSettings() {
        return this.languageVersionSettings;
    }

    public final void resolvePackageHeader(KtPackageDirective packageDirective, ModuleDescriptor module, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(packageDirective, "packageDirective");
        Intrinsics.checkNotNullParameter(module, "module");
        Intrinsics.checkNotNullParameter(trace, "trace");
        List<KtSimpleNameExpression> packageNames = packageDirective.getPackageNames();
        Intrinsics.checkNotNullExpressionValue(packageNames, "getPackageNames(...)");
        int i = 0;
        for (KtSimpleNameExpression ktSimpleNameExpression : packageNames) {
            int i2 = i + 1;
            FqName fqName = packageDirective.getFqName(ktSimpleNameExpression);
            Intrinsics.checkNotNullExpressionValue(fqName, "getFqName(...)");
            storeResult(trace, ktSimpleNameExpression, module.getPackage(fqName), (DeclarationDescriptor) null, QualifierPosition.PACKAGE_HEADER, i != CollectionsKt.getLastIndex(packageNames));
            i = i2;
        }
    }

    /* JADX INFO: compiled from: QualifiedExpressionResolver.kt */
    @Metadata(d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0086\b\u0018\u00002\u00020\u0001B\u001f\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\u0002\u0010\u0007J\u000f\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000b\u0010\u0010\u001a\u0004\u0018\u00010\u0006HÆ\u0003J%\u0010\u0011\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0015\u001a\u00020\u0016HÖ\u0001J\t\u0010\u0017\u001a\u00020\u0018HÖ\u0001R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\t0\u00038F¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$TypeQualifierResolutionResult;", "", "qualifierParts", "", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$ExpressionQualifierPart;", "classifierDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "(Ljava/util/List;Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;)V", "allProjections", "Lorg/jetbrains/kotlin/psi/KtTypeProjection;", "getAllProjections", "()Ljava/util/List;", "getClassifierDescriptor", "()Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getQualifierParts", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class TypeQualifierResolutionResult {
        private final ClassifierDescriptor classifierDescriptor;
        private final List<ExpressionQualifierPart> qualifierParts;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ TypeQualifierResolutionResult copy$default(TypeQualifierResolutionResult typeQualifierResolutionResult, List list, ClassifierDescriptor classifierDescriptor, int i, Object obj) {
            if ((i & 1) != 0) {
                list = typeQualifierResolutionResult.qualifierParts;
            }
            if ((i & 2) != 0) {
                classifierDescriptor = typeQualifierResolutionResult.classifierDescriptor;
            }
            return typeQualifierResolutionResult.copy(list, classifierDescriptor);
        }

        public final List<ExpressionQualifierPart> component1() {
            return this.qualifierParts;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final ClassifierDescriptor getClassifierDescriptor() {
            return this.classifierDescriptor;
        }

        public final TypeQualifierResolutionResult copy(List<ExpressionQualifierPart> qualifierParts, ClassifierDescriptor classifierDescriptor) {
            Intrinsics.checkNotNullParameter(qualifierParts, "qualifierParts");
            return new TypeQualifierResolutionResult(qualifierParts, classifierDescriptor);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof TypeQualifierResolutionResult)) {
                return false;
            }
            TypeQualifierResolutionResult typeQualifierResolutionResult = (TypeQualifierResolutionResult) other;
            return Intrinsics.areEqual(this.qualifierParts, typeQualifierResolutionResult.qualifierParts) && Intrinsics.areEqual(this.classifierDescriptor, typeQualifierResolutionResult.classifierDescriptor);
        }

        public int hashCode() {
            int iHashCode = this.qualifierParts.hashCode() * 31;
            ClassifierDescriptor classifierDescriptor = this.classifierDescriptor;
            return iHashCode + (classifierDescriptor == null ? 0 : classifierDescriptor.hashCode());
        }

        public String toString() {
            return "TypeQualifierResolutionResult(qualifierParts=" + this.qualifierParts + ", classifierDescriptor=" + this.classifierDescriptor + ')';
        }

        public TypeQualifierResolutionResult(List<ExpressionQualifierPart> qualifierParts, ClassifierDescriptor classifierDescriptor) {
            Intrinsics.checkNotNullParameter(qualifierParts, "qualifierParts");
            this.qualifierParts = qualifierParts;
            this.classifierDescriptor = classifierDescriptor;
        }

        public /* synthetic */ TypeQualifierResolutionResult(List list, ClassifierDescriptor classifierDescriptor, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(list, (i & 2) != 0 ? null : classifierDescriptor);
        }

        public final List<ExpressionQualifierPart> getQualifierParts() {
            return this.qualifierParts;
        }

        public final ClassifierDescriptor getClassifierDescriptor() {
            return this.classifierDescriptor;
        }

        public final List<KtTypeProjection> getAllProjections() {
            List<ExpressionQualifierPart> list = this.qualifierParts;
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                KtTypeArgumentList typeArguments = ((ExpressionQualifierPart) it.next()).getTypeArguments();
                List<KtTypeProjection> arguments = typeArguments != null ? typeArguments.getArguments() : null;
                if (arguments == null) {
                    arguments = CollectionsKt.emptyList();
                }
                CollectionsKt.addAll(arrayList, arguments);
            }
            return arrayList;
        }
    }

    public final ClassifierDescriptor findClassifierAndReportDeprecationIfNeeded(LexicalScope lexicalScope, Name name2, KotlinLookupLocation lookupLocation, KtExpression ktExpression, BindingTrace trace) {
        Intrinsics.checkNotNullParameter(lexicalScope, "<this>");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(lookupLocation, "lookupLocation");
        Intrinsics.checkNotNullParameter(trace, "trace");
        LexicalScope lexicalScope2 = lexicalScope;
        KotlinLookupLocation kotlinLookupLocation = lookupLocation;
        DescriptorWithDeprecation<ClassifierDescriptor> descriptorWithDeprecationFindFirstClassifierWithDeprecationStatus = ScopeUtilsKt.findFirstClassifierWithDeprecationStatus(lexicalScope2, name2, kotlinLookupLocation);
        if (descriptorWithDeprecationFindFirstClassifierWithDeprecationStatus == null) {
            return null;
        }
        ClassifierDescriptor classifierDescriptor = (ClassifierDescriptor) descriptorWithDeprecationFindFirstClassifierWithDeprecationStatus.component1();
        if (descriptorWithDeprecationFindFirstClassifierWithDeprecationStatus.getIsDeprecated() && ktExpression != null) {
            trace.record(BindingContext.DEPRECATED_SHORT_NAME_ACCESS, ktExpression);
            if (!ScopeUtilsKt.canBeResolvedWithoutDeprecation(classifierDescriptor, lexicalScope2, kotlinLookupLocation)) {
                trace.report(Errors.DEPRECATED_ACCESS_BY_SHORT_NAME.on(ktExpression, classifierDescriptor));
            }
        }
        return classifierDescriptor;
    }

    public final TypeQualifierResolutionResult resolveDescriptorForType(KtUserType userType, LexicalScope scope, BindingTrace trace, boolean isDebuggerContext) {
        Intrinsics.checkNotNullParameter(userType, "userType");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(trace, "trace");
        ClassifierDescriptor classifierDescriptorFindClassifierAndReportDeprecationIfNeeded = null;
        DeclarationDescriptor ownerDescriptor = !isDebuggerContext ? scope.getOwnerDescriptor() : null;
        if (userType.getQualifier() == null) {
            KtSimpleNameExpression referenceExpression = userType.getReferenceExpression();
            if (referenceExpression != null) {
                classifierDescriptorFindClassifierAndReportDeprecationIfNeeded = findClassifierAndReportDeprecationIfNeeded(scope, referenceExpression.getReferencedNameAsName(), new KotlinLookupLocation(referenceExpression), referenceExpression, trace);
                ClassifierDescriptor classifierDescriptor = classifierDescriptorFindClassifierAndReportDeprecationIfNeeded;
                checkNotEnumEntry(classifierDescriptor, trace, referenceExpression);
                storeResult(trace, referenceExpression, (DeclarationDescriptor) classifierDescriptor, ownerDescriptor, QualifierPosition.TYPE, false);
            }
            return new TypeQualifierResolutionResult(asQualifierPartList(userType).getFirst(), classifierDescriptorFindClassifierAndReportDeprecationIfNeeded);
        }
        Pair<List<ExpressionQualifierPart>, Boolean> pairAsQualifierPartList = asQualifierPartList(userType);
        List<ExpressionQualifierPart> listComponent1 = pairAsQualifierPartList.component1();
        if (pairAsQualifierPartList.component2().booleanValue()) {
            DeclarationDescriptor declarationDescriptorResolveToPackageOrClass = resolveToPackageOrClass(listComponent1, DescriptorUtilsKt.getModule(scope.getOwnerDescriptor()), trace, ownerDescriptor, scope, QualifierPosition.TYPE);
            return new TypeQualifierResolutionResult(listComponent1, declarationDescriptorResolveToPackageOrClass instanceof ClassifierDescriptor ? (ClassifierDescriptor) declarationDescriptorResolveToPackageOrClass : null);
        }
        return resolveQualifierPartListForType(listComponent1, ownerDescriptor, scope, trace, false);
    }

    private final TypeQualifierResolutionResult resolveQualifierPartListForType(List<ExpressionQualifierPart> qualifierPartList, DeclarationDescriptor ownerDescriptor, LexicalScope scope, BindingTrace trace, boolean isQualifier) {
        qualifierPartList.isEmpty();
        DeclarationDescriptor declarationDescriptorResolveToPackageOrClass = resolveToPackageOrClass(qualifierPartList.subList(0, qualifierPartList.size() - 1), DescriptorUtilsKt.getModule(scope.getOwnerDescriptor()), trace, ownerDescriptor, scope, QualifierPosition.TYPE);
        ClassifierDescriptor contributedClassifier = null;
        if (declarationDescriptorResolveToPackageOrClass == null) {
            return new TypeQualifierResolutionResult(qualifierPartList, null);
        }
        ExpressionQualifierPart expressionQualifierPart = (ExpressionQualifierPart) CollectionsKt.last((List) qualifierPartList);
        if (declarationDescriptorResolveToPackageOrClass instanceof PackageViewDescriptor) {
            contributedClassifier = ((PackageViewDescriptor) declarationDescriptorResolveToPackageOrClass).getMemberScope().mo7465getContributedClassifier(expressionQualifierPart.getName(), expressionQualifierPart.getLocation());
        } else if (declarationDescriptorResolveToPackageOrClass instanceof ClassDescriptor) {
            contributedClassifier = ((ClassDescriptor) declarationDescriptorResolveToPackageOrClass).getUnsubstitutedInnerClassesScope().mo7465getContributedClassifier(expressionQualifierPart.getName(), expressionQualifierPart.getLocation());
            checkNotEnumEntry(contributedClassifier, trace, expressionQualifierPart.getExpression());
        }
        storeResult(trace, expressionQualifierPart.getExpression(), contributedClassifier, ownerDescriptor, QualifierPosition.TYPE, isQualifier);
        return new TypeQualifierResolutionResult(qualifierPartList, contributedClassifier);
    }

    private final void checkNotEnumEntry(DeclarationDescriptor descriptor, BindingTrace trace, KtSimpleNameExpression expression) {
        if (expression == null || descriptor == null || !DescriptorUtils.isEnumEntry(descriptor)) {
            return;
        }
        KtQualifiedExpression topmostParentQualifiedExpressionForSelector = KtPsiUtilKt.getTopmostParentQualifiedExpressionForSelector(expression);
        if (topmostParentQualifiedExpressionForSelector == null || !(topmostParentQualifiedExpressionForSelector.getLightParent() instanceof KtDoubleColonExpression)) {
            trace.report(Errors.ENUM_ENTRY_AS_TYPE.on(expression));
        }
    }

    public final TypeQualifierResolutionResult resolveDescriptorForDoubleColonLHS(KtExpression expression, LexicalScope scope, BindingTrace trace, boolean isDebuggerContext) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(trace, "trace");
        DeclarationDescriptor ownerDescriptor = !isDebuggerContext ? scope.getOwnerDescriptor() : null;
        List<ExpressionQualifierPart> listAsQualifierPartList = asQualifierPartList(expression, true);
        if (listAsQualifierPartList.isEmpty()) {
            return new TypeQualifierResolutionResult(listAsQualifierPartList, null);
        }
        if (listAsQualifierPartList.size() == 1) {
            ExpressionQualifierPart expressionQualifierPart = (ExpressionQualifierPart) CollectionsKt.single((List) listAsQualifierPartList);
            Name nameComponent1 = expressionQualifierPart.getName();
            KtSimpleNameExpression ktSimpleNameExpressionComponent2 = expressionQualifierPart.component2();
            ClassifierDescriptor classifierDescriptorFindClassifierAndReportDeprecationIfNeeded = findClassifierAndReportDeprecationIfNeeded(scope, nameComponent1, new KotlinLookupLocation(ktSimpleNameExpressionComponent2), ktSimpleNameExpressionComponent2, trace);
            storeResult(trace, ktSimpleNameExpressionComponent2, (DeclarationDescriptor) classifierDescriptorFindClassifierAndReportDeprecationIfNeeded, ownerDescriptor, QualifierPosition.TYPE, true);
            return new TypeQualifierResolutionResult(listAsQualifierPartList, classifierDescriptorFindClassifierAndReportDeprecationIfNeeded);
        }
        return resolveQualifierPartListForType(listAsQualifierPartList, ownerDescriptor, scope, trace, true);
    }

    private final Pair<List<ExpressionQualifierPart>, Boolean> asQualifierPartList(KtUserType ktUserType) {
        SmartList smartList = new SmartList();
        boolean z = false;
        while (ktUserType != null) {
            KtSimpleNameExpression referenceExpression = ktUserType.getReferenceExpression();
            if (referenceExpression != null) {
                smartList.add(new ExpressionQualifierPart(referenceExpression.getReferencedNameAsName(), referenceExpression, ktUserType.getTypeArgumentList()));
            } else {
                z = true;
            }
            ktUserType = ktUserType.getQualifier();
        }
        return TuplesKt.to(CollectionsKt.asReversedMutable(smartList), Boolean.valueOf(z));
    }

    private static final ImportingScope processImportReference$processReferenceInContextOf(QualifiedExpressionResolver qualifiedExpressionResolver, KtImportInfo ktImportInfo, BindingTrace bindingTrace, Collection<FqName> collection, PackageFragmentDescriptor packageFragmentDescriptor, ModuleDescriptor moduleDescriptor) {
        return qualifiedExpressionResolver.doProcessImportReference(ktImportInfo, moduleDescriptor, bindingTrace, collection, packageFragmentDescriptor);
    }

    public final ImportingScope processImportReference(KtImportInfo importDirective, ModuleDescriptor moduleDescriptor, BindingTrace trace, Collection<FqName> excludedImportNames, PackageFragmentDescriptor packageFragmentForVisibilityCheck) {
        ModuleDescriptor resolutionAnchorIfAny;
        ImportingScope importingScopeProcessImportReference$processReferenceInContextOf;
        Intrinsics.checkNotNullParameter(importDirective, "importDirective");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(excludedImportNames, "excludedImportNames");
        ImportingScope importingScopeProcessImportReference$processReferenceInContextOf2 = processImportReference$processReferenceInContextOf(this, importDirective, trace, excludedImportNames, packageFragmentForVisibilityCheck, moduleDescriptor);
        if (!CommonConfigurationKeysKt.isLibraryToSourceAnalysisEnabled(this.languageVersionSettings) || (resolutionAnchorIfAny = ResolutionAnchorProviderKt.getResolutionAnchorIfAny(moduleDescriptor)) == null || (importingScopeProcessImportReference$processReferenceInContextOf = processImportReference$processReferenceInContextOf(this, importDirective, trace, excludedImportNames, packageFragmentForVisibilityCheck, resolutionAnchorIfAny)) == null) {
            return importingScopeProcessImportReference$processReferenceInContextOf2;
        }
        return importingScopeProcessImportReference$processReferenceInContextOf2 == null ? importingScopeProcessImportReference$processReferenceInContextOf : new CompositePrioritizedImportingScope(importingScopeProcessImportReference$processReferenceInContextOf, importingScopeProcessImportReference$processReferenceInContextOf2);
    }

    private final ImportingScope doProcessImportReference(KtImportInfo importDirective, ModuleDescriptor moduleDescriptor, BindingTrace trace, Collection<FqName> excludedImportNames, PackageFragmentDescriptor packageFragmentForVisibilityCheck) {
        PackageFragmentDescriptor packageFragmentDescriptorComputePackageFragmentToCheck;
        ProgressIndicatorAndCompilationCanceledStatus.checkCanceled();
        KtImportInfo.ImportContent importContent = importDirective.getImportContent();
        if (importContent == null) {
            return null;
        }
        List<QualifierPart> listAsQualifierPartList = asQualifierPartList(importContent);
        QualifierPart qualifierPart = (QualifierPart) CollectionsKt.lastOrNull((List) listAsQualifierPartList);
        if (qualifierPart == null) {
            return null;
        }
        if (importDirective instanceof KtImportDirective) {
            KtFile containingKtFile = ((KtImportDirective) importDirective).getContainingKtFile();
            Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
            packageFragmentDescriptorComputePackageFragmentToCheck = computePackageFragmentToCheck(containingKtFile, packageFragmentForVisibilityCheck);
        } else {
            packageFragmentDescriptorComputePackageFragmentToCheck = null;
        }
        if (importDirective.isAllUnder()) {
            DeclarationDescriptor declarationDescriptorClassDescriptorFromTypeAlias = classDescriptorFromTypeAlias(resolveToPackageOrClass(listAsQualifierPartList, moduleDescriptor, trace, packageFragmentDescriptorComputePackageFragmentToCheck, null, QualifierPosition.IMPORT));
            if (declarationDescriptorClassDescriptorFromTypeAlias == null) {
                return null;
            }
            if ((declarationDescriptorClassDescriptorFromTypeAlias instanceof ClassDescriptor) && ((ClassDescriptor) declarationDescriptorClassDescriptorFromTypeAlias).getKind().isSingleton() && qualifierPart.getExpression() != null) {
                DiagnosticFactory1<KtSimpleNameExpression, ClassDescriptor> diagnosticFactory1 = Errors.CANNOT_ALL_UNDER_IMPORT_FROM_SINGLETON;
                KtSimpleNameExpression expression = qualifierPart.getExpression();
                Intrinsics.checkNotNull(expression);
                trace.report(diagnosticFactory1.on(expression, (ClassDescriptor) declarationDescriptorClassDescriptorFromTypeAlias));
                return null;
            }
            return AllUnderImportScope.INSTANCE.create(declarationDescriptorClassDescriptorFromTypeAlias, excludedImportNames);
        }
        return processSingleImport(moduleDescriptor, trace, importDirective, listAsQualifierPartList, qualifierPart, packageFragmentDescriptorComputePackageFragmentToCheck);
    }

    private final DeclarationDescriptor classDescriptorFromTypeAlias(DeclarationDescriptor declarationDescriptor) {
        return declarationDescriptor instanceof TypeAliasDescriptor ? ((TypeAliasDescriptor) declarationDescriptor).getClassDescriptor() : declarationDescriptor;
    }

    private final PackageFragmentDescriptor computePackageFragmentToCheck(KtFile containingFile, PackageFragmentDescriptor packageFragmentForVisibilityCheck) {
        KtFile ktFile = containingFile;
        if (CodeFragmentUtilKt.suppressDiagnosticsInDebugMode(ktFile)) {
            return null;
        }
        return ((packageFragmentForVisibilityCheck instanceof DeclarationDescriptorWithSource) && Intrinsics.areEqual(packageFragmentForVisibilityCheck.getSource(), SourceElement.NO_SOURCE)) ? new PackageFragmentWithCustomSource(packageFragmentForVisibilityCheck, new KotlinSourceElement(ktFile)) : packageFragmentForVisibilityCheck;
    }

    /* JADX WARN: Code duplicated, block: B:19:0x0053  */
    private final ImportingScope processSingleImport(final ModuleDescriptor moduleDescriptor, final BindingTrace trace, KtImportInfo importDirective, List<? extends QualifierPart> path, final QualifierPart lastPart, final PackageFragmentDescriptor packageFragmentForVisibilityCheck) {
        DeclarationDescriptor declarationDescriptor;
        Name importedName = importDirective.getImportedName();
        if (importedName == null) {
            resolveToPackageOrClass(path, moduleDescriptor, trace, packageFragmentForVisibilityCheck, null, QualifierPosition.IMPORT);
            return null;
        }
        DeclarationDescriptor declarationDescriptorResolveToPackageOrClass = resolveToPackageOrClass(path.subList(0, path.size() - 1), moduleDescriptor, trace, packageFragmentForVisibilityCheck, null, QualifierPosition.IMPORT);
        if (declarationDescriptorResolveToPackageOrClass == null) {
            return null;
        }
        TypeAliasDescriptor typeAliasDescriptor = declarationDescriptorResolveToPackageOrClass instanceof TypeAliasDescriptor ? (TypeAliasDescriptor) declarationDescriptorResolveToPackageOrClass : null;
        if (typeAliasDescriptor == null) {
            declarationDescriptor = declarationDescriptorResolveToPackageOrClass;
        } else {
            ClassDescriptor classDescriptor = typeAliasDescriptor.getClassDescriptor();
            if (classDescriptor == null) {
                return null;
            }
            if (classDescriptor != null) {
                declarationDescriptor = classDescriptor;
            } else {
                declarationDescriptor = declarationDescriptorResolveToPackageOrClass;
            }
        }
        final DeclarationDescriptor declarationDescriptor2 = declarationDescriptor;
        return new LazyExplicitImportScope(this.languageVersionSettings, declarationDescriptor, packageFragmentForVisibilityCheck, lastPart.getName(), importedName, new CallOnceFunction(Unit.INSTANCE, new Function1<Collection<? extends DeclarationDescriptor>, Unit>() { // from class: org.jetbrains.kotlin.resolve.QualifiedExpressionResolver.processSingleImport.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Collection<? extends DeclarationDescriptor> collection) {
                invoke2(collection);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(Collection<? extends DeclarationDescriptor> candidates) {
                Intrinsics.checkNotNullParameter(candidates, "candidates");
                if (!candidates.isEmpty()) {
                    QualifiedExpressionResolver.this.storeResult(trace, lastPart.getExpression(), candidates, (DeclarationDescriptor) packageFragmentForVisibilityCheck, QualifierPosition.IMPORT, false);
                } else {
                    QualifiedExpressionResolver.this.tryResolveDescriptorsWhichCannotBeImported(trace, moduleDescriptor, declarationDescriptor2, lastPart);
                }
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void tryResolveDescriptorsWhichCannotBeImported(BindingTrace trace, ModuleDescriptor moduleDescriptor, DeclarationDescriptor packageOrClassDescriptor, QualifierPart lastPart) {
        KtSimpleNameExpression expression = lastPart.getExpression();
        if (expression == null) {
            return;
        }
        SmartList smartList = new SmartList();
        Name name2 = lastPart.getName();
        if (packageOrClassDescriptor instanceof PackageViewDescriptor) {
            FqName fqNameChild = ((PackageViewDescriptor) packageOrClassDescriptor).getFqName().child(name2);
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            if (!moduleDescriptor.getPackage(fqNameChild).isEmpty()) {
                trace.report(Errors.PACKAGE_CANNOT_BE_IMPORTED.on(expression));
                smartList.add(packageOrClassDescriptor);
            }
        } else if (packageOrClassDescriptor instanceof ClassDescriptor) {
            MemberScope unsubstitutedMemberScope = ((ClassDescriptor) packageOrClassDescriptor).getUnsubstitutedMemberScope();
            Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
            smartList.addAll(unsubstitutedMemberScope.getContributedFunctions(name2, lastPart.getLocation()));
            smartList.addAll(unsubstitutedMemberScope.getContributedVariables(name2, lastPart.getLocation()));
            if (!smartList.isEmpty()) {
                trace.report(Errors.CANNOT_BE_IMPORTED.on(expression, name2));
            }
        } else {
            throw new IllegalStateException("Should be class or package: " + packageOrClassDescriptor);
        }
        storeResult(trace, lastPart.getExpression(), (Collection<? extends DeclarationDescriptor>) smartList, (DeclarationDescriptor) null, QualifierPosition.IMPORT, false);
    }

    private final List<QualifierPart> asQualifierPartList(KtImportInfo.ImportContent importContent) {
        if (importContent instanceof KtImportInfo.ImportContent.ExpressionBased) {
            return asQualifierPartList$default(this, ((KtImportInfo.ImportContent.ExpressionBased) importContent).getExpression(), false, 1, null);
        }
        if (importContent instanceof KtImportInfo.ImportContent.FqNameBased) {
            List<Name> listPathSegments = ((KtImportInfo.ImportContent.FqNameBased) importContent).getFqName().pathSegments();
            Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
            List<Name> list = listPathSegments;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            for (Name name2 : list) {
                Intrinsics.checkNotNull(name2);
                arrayList.add(new QualifierPart(name2, null, null, 6, null));
            }
            return arrayList;
        }
        throw new NoWhenBranchMatchedException();
    }

    static /* synthetic */ List asQualifierPartList$default(QualifiedExpressionResolver qualifiedExpressionResolver, KtExpression ktExpression, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            z = false;
        }
        return qualifiedExpressionResolver.asQualifierPartList(ktExpression, z);
    }

    private final List<ExpressionQualifierPart> asQualifierPartList(KtExpression ktExpression, boolean z) {
        SmartList smartList = new SmartList();
        while (!asQualifierPartList$addQualifierPart(smartList, z, ktExpression) && (ktExpression instanceof KtQualifiedExpression)) {
            KtQualifiedExpression ktQualifiedExpression = (KtQualifiedExpression) ktExpression;
            asQualifierPartList$addQualifierPart(smartList, z, ktQualifiedExpression.getSelectorExpression());
            ktExpression = ktQualifiedExpression.getReceiverExpression();
        }
        return CollectionsKt.asReversedMutable(smartList);
    }

    private static final boolean asQualifierPartList$addQualifierPart(SmartList<ExpressionQualifierPart> smartList, boolean z, KtExpression ktExpression) {
        if (ktExpression instanceof KtSimpleNameExpression) {
            smartList.add(new ExpressionQualifierPart((KtSimpleNameExpression) ktExpression));
            return true;
        }
        if (!z || !(ktExpression instanceof KtCallExpression)) {
            return false;
        }
        KtCallExpression ktCallExpression = (KtCallExpression) ktExpression;
        if (!DoubleColonExpressionResolverKt.isWithoutValueArguments(ktCallExpression)) {
            return false;
        }
        KtExpression calleeExpression = ktCallExpression.getCalleeExpression();
        if (!(calleeExpression instanceof KtSimpleNameExpression)) {
            return false;
        }
        KtSimpleNameExpression ktSimpleNameExpression = (KtSimpleNameExpression) calleeExpression;
        smartList.add(new ExpressionQualifierPart(ktSimpleNameExpression.getReferencedNameAsName(), ktSimpleNameExpression, ktCallExpression.getTypeArgumentList()));
        return true;
    }

    /* JADX INFO: compiled from: QualifiedExpressionResolver.kt */
    @Metadata(d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\b\u0016\u0018\u00002\u00020\u0001B#\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\t\u0010\u0013\u001a\u00020\u0003H\u0086\u0002J\u000b\u0010\u0014\u001a\u0004\u0018\u00010\nH\u0096\u0002J\u000b\u0010\u0015\u001a\u0004\u0018\u00010\u0005H\u0086\u0002R\u0016\u0010\t\u001a\u0004\u0018\u00010\n8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$QualifierPart;", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "typeArguments", "Lorg/jetbrains/kotlin/psi/KtTypeArgumentList;", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/psi/KtTypeArgumentList;Lorg/jetbrains/kotlin/incremental/components/LookupLocation;)V", "expression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "getLocation", "()Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "getName", "()Lorg/jetbrains/kotlin/name/Name;", "getTypeArguments", "()Lorg/jetbrains/kotlin/psi/KtTypeArgumentList;", "component1", "component2", "component3", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static class QualifierPart {
        private final LookupLocation location;
        private final Name name;
        private final KtTypeArgumentList typeArguments;

        public KtSimpleNameExpression getExpression() {
            return null;
        }

        public QualifierPart(Name name2, KtTypeArgumentList ktTypeArgumentList, LookupLocation location) {
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(location, "location");
            this.name = name2;
            this.typeArguments = ktTypeArgumentList;
            this.location = location;
        }

        public final Name getName() {
            return this.name;
        }

        public final KtTypeArgumentList getTypeArguments() {
            return this.typeArguments;
        }

        public /* synthetic */ QualifierPart(Name name2, KtTypeArgumentList ktTypeArgumentList, NoLookupLocation noLookupLocation, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(name2, (i & 2) != 0 ? null : ktTypeArgumentList, (i & 4) != 0 ? NoLookupLocation.FOR_DEFAULT_IMPORTS : noLookupLocation);
        }

        public final LookupLocation getLocation() {
            return this.location;
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final Name getName() {
            return this.name;
        }

        public KtSimpleNameExpression component2() {
            return getExpression();
        }

        /* JADX INFO: renamed from: component3, reason: from getter */
        public final KtTypeArgumentList getTypeArguments() {
            return this.typeArguments;
        }
    }

    /* JADX INFO: compiled from: QualifiedExpressionResolver.kt */
    @Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B!\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0002\u0010\tJ\t\u0010\f\u001a\u00020\u0003H\u0096\u0002R\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$ExpressionQualifierPart;", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$QualifierPart;", "expression", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "(Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;)V", "name", "Lorg/jetbrains/kotlin/name/Name;", "typeArguments", "Lorg/jetbrains/kotlin/psi/KtTypeArgumentList;", "(Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;Lorg/jetbrains/kotlin/psi/KtTypeArgumentList;)V", "getExpression", "()Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "component2", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ExpressionQualifierPart extends QualifierPart {
        private final KtSimpleNameExpression expression;

        public /* synthetic */ ExpressionQualifierPart(Name name2, KtSimpleNameExpression ktSimpleNameExpression, KtTypeArgumentList ktTypeArgumentList, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(name2, ktSimpleNameExpression, (i & 4) != 0 ? null : ktTypeArgumentList);
        }

        @Override // org.jetbrains.kotlin.resolve.QualifiedExpressionResolver.QualifierPart
        public KtSimpleNameExpression getExpression() {
            return this.expression;
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public ExpressionQualifierPart(Name name2, KtSimpleNameExpression expression, KtTypeArgumentList ktTypeArgumentList) {
            super(name2, ktTypeArgumentList, new KotlinLookupLocation(expression));
            Intrinsics.checkNotNullParameter(name2, "name");
            Intrinsics.checkNotNullParameter(expression, "expression");
            this.expression = expression;
        }

        /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
        public ExpressionQualifierPart(KtSimpleNameExpression expression) {
            this(expression.getReferencedNameAsName(), expression, null, 4, null);
            Intrinsics.checkNotNullParameter(expression, "expression");
        }

        @Override // org.jetbrains.kotlin.resolve.QualifiedExpressionResolver.QualifierPart
        public KtSimpleNameExpression component2() {
            return getExpression();
        }
    }

    private final DeclarationDescriptor resolveToPackageOrClass(List<? extends QualifierPart> path, ModuleDescriptor moduleDescriptor, BindingTrace trace, DeclarationDescriptor shouldBeVisibleFrom, LexicalScope scopeForFirstPart, QualifierPosition position) {
        Pair pairResolveToPackageOrClassPrefix$default = resolveToPackageOrClassPrefix$default(this, path, moduleDescriptor, trace, shouldBeVisibleFrom, scopeForFirstPart, position, null, 64, null);
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) pairResolveToPackageOrClassPrefix$default.component1();
        if (((Number) pairResolveToPackageOrClassPrefix$default.component2()).intValue() != path.size()) {
            return null;
        }
        return declarationDescriptor;
    }

    private final boolean resolveInIDEMode(List<? extends QualifierPart> path) {
        return ((Boolean) this.languageVersionSettings.getFlag(AnalysisFlags.getIdeMode())).booleanValue() && path.size() > 1 && Intrinsics.areEqual(((QualifierPart) CollectionsKt.first((List) path)).getName().asString(), "_root_ide_package_");
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ Pair resolveToPackageOrClassPrefix$default(QualifiedExpressionResolver qualifiedExpressionResolver, List list, ModuleDescriptor moduleDescriptor, BindingTrace bindingTrace, DeclarationDescriptor declarationDescriptor, LexicalScope lexicalScope, QualifierPosition qualifierPosition, Function1 function1, int i, Object obj) {
        return qualifiedExpressionResolver.resolveToPackageOrClassPrefix(list, moduleDescriptor, bindingTrace, declarationDescriptor, lexicalScope, qualifierPosition, (i & 64) != 0 ? null : function1);
    }

    /* JADX WARN: Code duplicated, block: B:32:0x00f2  */
    private final Pair<DeclarationDescriptor, Integer> resolveToPackageOrClassPrefix(List<? extends QualifierPart> path, ModuleDescriptor moduleDescriptor, BindingTrace trace, DeclarationDescriptor shouldBeVisibleFrom, LexicalScope scopeForFirstPart, QualifierPosition position, Function1<? super KtSimpleNameExpression, Boolean> isValue) {
        Pair<PackageViewDescriptor, Integer> pairQuickResolveToPackage;
        PackageViewDescriptor packageViewDescriptor;
        ClassifierDescriptor contributedClassifier;
        DeclarationDescriptor declarationDescriptor;
        if (resolveInIDEMode(path)) {
            Pair<DeclarationDescriptor, Integer> pairResolveToPackageOrClassPrefix = resolveToPackageOrClassPrefix(path.subList(1, path.size()), moduleDescriptor, trace, shouldBeVisibleFrom, null, position, null);
            return TuplesKt.to(pairResolveToPackageOrClassPrefix.getFirst(), Integer.valueOf(pairResolveToPackageOrClassPrefix.getSecond().intValue() + 1));
        }
        if (path.isEmpty()) {
            FqName ROOT = FqName.ROOT;
            Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
            return new Pair<>(moduleDescriptor.getPackage(ROOT), 0);
        }
        QualifierPart qualifierPart = (QualifierPart) CollectionsKt.first((List) path);
        PackageViewDescriptor packageViewDescriptor2 = null;
        if (position == QualifierPosition.EXPRESSION && scopeForFirstPart != null && isValue != null && qualifierPart.getExpression() != null) {
            KtSimpleNameExpression expression = qualifierPart.getExpression();
            Intrinsics.checkNotNull(expression);
            if (isValue.invoke(expression).booleanValue()) {
                return new Pair<>(null, 0);
            }
        }
        ClassifierDescriptor classifierDescriptorFindClassifier = scopeForFirstPart != null ? ScopeUtilsKt.findClassifier(scopeForFirstPart, qualifierPart.getName(), qualifierPart.getLocation()) : null;
        if (classifierDescriptorFindClassifier != null) {
            storeResult$default(this, trace, qualifierPart.getExpression(), classifierDescriptorFindClassifier, shouldBeVisibleFrom, position, false, 32, null);
        }
        if (classifierDescriptorFindClassifier != null) {
            pairQuickResolveToPackage = new Pair<>(classifierDescriptorFindClassifier, 1);
        } else {
            pairQuickResolveToPackage = quickResolveToPackage(moduleDescriptor, path, trace, position);
        }
        PackageViewDescriptor packageViewDescriptorComponent1 = pairQuickResolveToPackage.component1();
        int iIntValue = pairQuickResolveToPackage.component2().intValue();
        int size = path.size();
        int i = iIntValue;
        DeclarationDescriptor declarationDescriptor2 = packageViewDescriptorComponent1;
        while (i < size) {
            QualifierPart qualifierPart2 = path.get(i);
            if (declarationDescriptor2 instanceof TypeAliasDescriptor) {
                declarationDescriptor = packageViewDescriptor2;
            } else {
                if (declarationDescriptor2 instanceof ClassDescriptor) {
                    contributedClassifier = getContributedClassifier((ClassDescriptor) declarationDescriptor2, qualifierPart2);
                } else if (declarationDescriptor2 instanceof PackageViewDescriptor) {
                    if (qualifierPart2.getTypeArguments() == null) {
                        FqName fqNameChild = ((PackageViewDescriptor) declarationDescriptor2).getFqName().child(qualifierPart2.getName());
                        Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
                        packageViewDescriptor = moduleDescriptor.getPackage(fqNameChild);
                    } else {
                        packageViewDescriptor = packageViewDescriptor2;
                    }
                    if (packageViewDescriptor != null && !packageViewDescriptor.isEmpty()) {
                        contributedClassifier = packageViewDescriptor;
                    } else {
                        contributedClassifier = ((PackageViewDescriptor) declarationDescriptor2).getMemberScope().mo7465getContributedClassifier(qualifierPart2.getName(), qualifierPart2.getLocation());
                    }
                } else {
                    declarationDescriptor = packageViewDescriptor2;
                }
                declarationDescriptor = contributedClassifier;
            }
            if (position != QualifierPosition.EXPRESSION || declarationDescriptor != null) {
                storeResult$default(this, trace, qualifierPart2.getExpression(), declarationDescriptor, shouldBeVisibleFrom, position, false, 32, null);
            }
            if (declarationDescriptor == null) {
                return new Pair<>(declarationDescriptor2, Integer.valueOf(i));
            }
            i++;
            declarationDescriptor2 = declarationDescriptor;
            packageViewDescriptor2 = null;
        }
        return new Pair<>(declarationDescriptor2, Integer.valueOf(path.size()));
    }

    public final ClassifierDescriptor getContributedClassifier(ClassDescriptor classDescriptor, QualifierPart qualifierPart) {
        Intrinsics.checkNotNullParameter(classDescriptor, "<this>");
        Intrinsics.checkNotNullParameter(qualifierPart, "qualifierPart");
        return classDescriptor.getUnsubstitutedInnerClassesScope().mo7465getContributedClassifier(qualifierPart.getName(), qualifierPart.getLocation());
    }

    public final Qualifier resolveNameExpressionAsQualifierForDiagnostics(KtSimpleNameExpression expression, Receiver receiver, ExpressionTypingContext context) {
        DeclarationDescriptor declarationDescriptor;
        ClassifierDescriptor classifierDescriptorFindClassifier;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Name referencedNameAsName = expression.getReferencedNameAsName();
        if (!expression.getIsPhysical() && !referencedNameAsName.isSpecial()) {
            String strAsString = referencedNameAsName.asString();
            Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
            if (StringsKt.endsWith$default(strAsString, "IntellijIdeaRulezzz", false, 2, (Object) null)) {
                return null;
            }
        }
        KotlinLookupLocation kotlinLookupLocation = new KotlinLookupLocation(expression);
        if (receiver instanceof PackageQualifier) {
            PackageQualifier packageQualifier = (PackageQualifier) receiver;
            FqName fqNameChild = packageQualifier.getDescriptor().getFqName().child(referencedNameAsName);
            Intrinsics.checkNotNullExpressionValue(fqNameChild, "child(...)");
            PackageViewDescriptor packageViewDescriptor = packageQualifier.getDescriptor().getModuleDescriptor().getPackage(fqNameChild);
            if (packageViewDescriptor.isEmpty()) {
                packageViewDescriptor = null;
            }
            if (packageViewDescriptor != null) {
                declarationDescriptor = packageViewDescriptor;
            } else {
                classifierDescriptorFindClassifier = packageQualifier.getDescriptor().getMemberScope().mo7465getContributedClassifier(referencedNameAsName, kotlinLookupLocation);
                declarationDescriptor = classifierDescriptorFindClassifier;
            }
        } else {
            if (receiver instanceof ClassQualifier) {
                classifierDescriptorFindClassifier = ((ClassQualifier) receiver).getStaticScope().mo7465getContributedClassifier(referencedNameAsName, kotlinLookupLocation);
            } else if (receiver == null) {
                LexicalScope scope = context.scope;
                Intrinsics.checkNotNullExpressionValue(scope, "scope");
                ClassifierDescriptor classifierDescriptorFindClassifier2 = ScopeUtilsKt.findClassifier(scope, referencedNameAsName, kotlinLookupLocation);
                if (classifierDescriptorFindClassifier2 != null) {
                    classifierDescriptorFindClassifier = classifierDescriptorFindClassifier2;
                } else {
                    ModuleDescriptor module = DescriptorUtilsKt.getModule(context.scope.getOwnerDescriptor());
                    FqName fqNameChild2 = FqName.ROOT.child(referencedNameAsName);
                    Intrinsics.checkNotNullExpressionValue(fqNameChild2, "child(...)");
                    PackageViewDescriptor packageViewDescriptor2 = module.getPackage(fqNameChild2);
                    if (packageViewDescriptor2.isEmpty()) {
                        packageViewDescriptor2 = null;
                    }
                    classifierDescriptorFindClassifier = packageViewDescriptor2;
                }
            } else if (receiver instanceof ReceiverValue) {
                classifierDescriptorFindClassifier = ScopeUtilsKt.findClassifier(ScopeUtilsKt.memberScopeAsImportingScope$default(((ReceiverValue) receiver).getType().getMemberScope(), null, 1, null), referencedNameAsName, kotlinLookupLocation);
            } else {
                declarationDescriptor = null;
            }
            declarationDescriptor = classifierDescriptorFindClassifier;
        }
        if (declarationDescriptor == null) {
            return null;
        }
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        return storeResult$default(this, trace, expression, declarationDescriptor, context.scope.getOwnerDescriptor(), QualifierPosition.EXPRESSION, false, 32, null);
    }

    /* JADX INFO: compiled from: QualifiedExpressionResolver.kt */
    @Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0019\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\u0002\u0010\u0006J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0005HÆ\u0003J!\u0010\r\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005HÆ\u0001J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0011\u001a\u00020\u0012HÖ\u0001J\t\u0010\u0013\u001a\u00020\u0014HÖ\u0001R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$QualifiedExpressionResolveResult;", "", "classOrPackage", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "memberName", "Lorg/jetbrains/kotlin/name/Name;", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/name/Name;)V", "getClassOrPackage", "()Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "getMemberName", "()Lorg/jetbrains/kotlin/name/Name;", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class QualifiedExpressionResolveResult {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final QualifiedExpressionResolveResult UNRESOLVED = new QualifiedExpressionResolveResult(null, null);
        private final DeclarationDescriptor classOrPackage;
        private final Name memberName;

        public static /* synthetic */ QualifiedExpressionResolveResult copy$default(QualifiedExpressionResolveResult qualifiedExpressionResolveResult, DeclarationDescriptor declarationDescriptor, Name name2, int i, Object obj) {
            if ((i & 1) != 0) {
                declarationDescriptor = qualifiedExpressionResolveResult.classOrPackage;
            }
            if ((i & 2) != 0) {
                name2 = qualifiedExpressionResolveResult.memberName;
            }
            return qualifiedExpressionResolveResult.copy(declarationDescriptor, name2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final DeclarationDescriptor getClassOrPackage() {
            return this.classOrPackage;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final Name getMemberName() {
            return this.memberName;
        }

        public final QualifiedExpressionResolveResult copy(DeclarationDescriptor classOrPackage, Name memberName) {
            return new QualifiedExpressionResolveResult(classOrPackage, memberName);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof QualifiedExpressionResolveResult)) {
                return false;
            }
            QualifiedExpressionResolveResult qualifiedExpressionResolveResult = (QualifiedExpressionResolveResult) other;
            return Intrinsics.areEqual(this.classOrPackage, qualifiedExpressionResolveResult.classOrPackage) && Intrinsics.areEqual(this.memberName, qualifiedExpressionResolveResult.memberName);
        }

        public int hashCode() {
            DeclarationDescriptor declarationDescriptor = this.classOrPackage;
            int iHashCode = (declarationDescriptor == null ? 0 : declarationDescriptor.hashCode()) * 31;
            Name name2 = this.memberName;
            return iHashCode + (name2 != null ? name2.hashCode() : 0);
        }

        public String toString() {
            return "QualifiedExpressionResolveResult(classOrPackage=" + this.classOrPackage + ", memberName=" + this.memberName + ')';
        }

        public QualifiedExpressionResolveResult(DeclarationDescriptor declarationDescriptor, Name name2) {
            this.classOrPackage = declarationDescriptor;
            this.memberName = name2;
        }

        public final DeclarationDescriptor getClassOrPackage() {
            return this.classOrPackage;
        }

        public final Name getMemberName() {
            return this.memberName;
        }

        /* JADX INFO: compiled from: QualifiedExpressionResolver.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$QualifiedExpressionResolveResult$Companion;", "", "()V", "UNRESOLVED", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$QualifiedExpressionResolveResult;", "getUNRESOLVED", "()Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver$QualifiedExpressionResolveResult;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final QualifiedExpressionResolveResult getUNRESOLVED() {
                return QualifiedExpressionResolveResult.UNRESOLVED;
            }
        }
    }

    public final QualifiedExpressionResolveResult resolveClassOrPackageInQualifiedExpression(KtQualifiedExpression expression, LexicalScope scope, BindingContext context) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(scope, "scope");
        Intrinsics.checkNotNullParameter(context, "context");
        List<QualifierPart> listMapToQualifierParts = mapToQualifierParts(CallExpressionUnrollerKt.unrollToLeftMostQualifiedExpression(expression), 0);
        Pair pairResolveToPackageOrClassPrefix$default = resolveToPackageOrClassPrefix$default(this, listMapToQualifierParts, DescriptorUtilsKt.getModule(scope.getOwnerDescriptor()), new DelegatingBindingTrace(context, "Temp trace for resolving qualified expression", false, null, false, null, 60, null), scope.getOwnerDescriptor(), scope, QualifierPosition.EXPRESSION, null, 64, null);
        DeclarationDescriptor declarationDescriptor = (DeclarationDescriptor) pairResolveToPackageOrClassPrefix$default.component1();
        int iIntValue = ((Number) pairResolveToPackageOrClassPrefix$default.component2()).intValue();
        if (declarationDescriptor == null) {
            return QualifiedExpressionResolveResult.INSTANCE.getUNRESOLVED();
        }
        if (iIntValue == listMapToQualifierParts.size()) {
            return new QualifiedExpressionResolveResult(declarationDescriptor, null);
        }
        return iIntValue == listMapToQualifierParts.size() + (-1) ? new QualifiedExpressionResolveResult(declarationDescriptor, listMapToQualifierParts.get(iIntValue).getName()) : QualifiedExpressionResolveResult.INSTANCE.getUNRESOLVED();
    }

    public final List<CallExpressionElement> resolveQualifierInExpressionAndUnroll(KtQualifiedExpression expression, ExpressionTypingContext context, Function1<? super KtSimpleNameExpression, Boolean> isValue) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(isValue, "isValue");
        List<KtQualifiedExpression> listUnrollToLeftMostQualifiedExpression = CallExpressionUnrollerKt.unrollToLeftMostQualifiedExpression(expression);
        List<QualifierPart> listMapToQualifierParts = mapToQualifierParts(listUnrollToLeftMostQualifiedExpression, 1);
        ModuleDescriptor module = DescriptorUtilsKt.getModule(context.scope.getOwnerDescriptor());
        BindingTrace trace = context.trace;
        Intrinsics.checkNotNullExpressionValue(trace, "trace");
        int iIntValue = resolveToPackageOrClassPrefix(listMapToQualifierParts, module, trace, context.scope.getOwnerDescriptor(), context.scope, QualifierPosition.EXPRESSION, isValue).getSecond().intValue();
        List<KtQualifiedExpression> listSubList = listUnrollToLeftMostQualifiedExpression.subList(iIntValue == 0 ? 0 : iIntValue - 1, listUnrollToLeftMostQualifiedExpression.size());
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSubList, 10));
        Iterator<T> it = listSubList.iterator();
        while (it.hasNext()) {
            arrayList.add(new CallExpressionElement((KtQualifiedExpression) it.next()));
        }
        return arrayList;
    }

    private final List<QualifierPart> mapToQualifierParts(List<? extends KtQualifiedExpression> qualifiedExpressions, int skipLast) {
        if (qualifiedExpressions.isEmpty()) {
            return CollectionsKt.emptyList();
        }
        KtQualifiedExpression ktQualifiedExpression = (KtQualifiedExpression) CollectionsKt.first((List) qualifiedExpressions);
        if (!(ktQualifiedExpression instanceof KtDotQualifiedExpression)) {
            return CollectionsKt.emptyList();
        }
        KtExpression receiverExpression = ktQualifiedExpression.getReceiverExpression();
        if (!(receiverExpression instanceof KtSimpleNameExpression)) {
            return CollectionsKt.emptyList();
        }
        ArrayList arrayList = new ArrayList();
        arrayList.add(new ExpressionQualifierPart((KtSimpleNameExpression) receiverExpression));
        for (KtQualifiedExpression ktQualifiedExpression2 : CollectionsKt.dropLast(qualifiedExpressions, skipLast)) {
            if (!(ktQualifiedExpression2 instanceof KtDotQualifiedExpression)) {
                break;
            }
            KtExpression selectorExpression = ktQualifiedExpression2.getSelectorExpression();
            if (!(selectorExpression instanceof KtSimpleNameExpression)) {
                break;
            }
            arrayList.add(new ExpressionQualifierPart((KtSimpleNameExpression) selectorExpression));
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void storeResult(BindingTrace trace, KtSimpleNameExpression referenceExpression, Collection<? extends DeclarationDescriptor> descriptors, DeclarationDescriptor shouldBeVisibleFrom, QualifierPosition position, boolean isQualifier) {
        if (referenceExpression == null) {
            return;
        }
        if (descriptors.size() > 1) {
            Collection<? extends DeclarationDescriptor> collection = descriptors;
            ArrayList arrayList = new ArrayList();
            for (Object obj : collection) {
                if (QualifiedExpressionResolverKt.isVisible((DeclarationDescriptor) obj, shouldBeVisibleFrom, position, this.languageVersionSettings)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                Object objFirst = CollectionsKt.first(collection);
                Intrinsics.checkNotNull(objFirst, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithVisibility");
                DeclarationDescriptorWithVisibility declarationDescriptorWithVisibility = (DeclarationDescriptorWithVisibility) objFirst;
                trace.report(Errors.INVISIBLE_REFERENCE.on(referenceExpression, declarationDescriptorWithVisibility, declarationDescriptorWithVisibility.getVisibility(), declarationDescriptorWithVisibility));
                return;
            }
            if (arrayList2.size() > 1) {
                trace.record(BindingContext.AMBIGUOUS_REFERENCE_TARGET, referenceExpression, arrayList2);
                return;
            } else {
                storeResult(trace, referenceExpression, (DeclarationDescriptor) CollectionsKt.single((List) arrayList2), (DeclarationDescriptor) null, position, isQualifier);
                return;
            }
        }
        storeResult(trace, referenceExpression, (DeclarationDescriptor) CollectionsKt.singleOrNull(descriptors), shouldBeVisibleFrom, position, isQualifier);
    }

    static /* synthetic */ Qualifier storeResult$default(QualifiedExpressionResolver qualifiedExpressionResolver, BindingTrace bindingTrace, KtSimpleNameExpression ktSimpleNameExpression, DeclarationDescriptor declarationDescriptor, DeclarationDescriptor declarationDescriptor2, QualifierPosition qualifierPosition, boolean z, int i, Object obj) {
        if ((i & 32) != 0) {
            z = true;
        }
        return qualifiedExpressionResolver.storeResult(bindingTrace, ktSimpleNameExpression, declarationDescriptor, declarationDescriptor2, qualifierPosition, z);
    }

    private final Qualifier storeResult(BindingTrace trace, KtSimpleNameExpression referenceExpression, DeclarationDescriptor descriptor, DeclarationDescriptor shouldBeVisibleFrom, QualifierPosition position, boolean isQualifier) {
        if (referenceExpression == null) {
            return null;
        }
        if (descriptor == null) {
            trace.report(Errors.UNRESOLVED_REFERENCE.on(referenceExpression, referenceExpression));
            return null;
        }
        trace.record(BindingContext.REFERENCE_TARGET, referenceExpression, descriptor);
        UnderscoreUsageChecker.INSTANCE.checkSimpleNameUsage(descriptor, referenceExpression, trace);
        if (descriptor instanceof DeclarationDescriptorWithVisibility) {
            if (shouldBeVisibleFrom instanceof PackageFragmentDescriptor) {
                PackageFragmentDescriptor packageFragmentDescriptor = (PackageFragmentDescriptor) shouldBeVisibleFrom;
                if (Intrinsics.areEqual(packageFragmentDescriptor.getSource(), SourceElement.NO_SOURCE) && !(referenceExpression.getContainingFile() instanceof DummyHolder)) {
                    KtFile containingKtFile = referenceExpression.getContainingKtFile();
                    Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
                    shouldBeVisibleFrom = new PackageFragmentWithCustomSource(packageFragmentDescriptor, new KotlinSourceElement(containingKtFile));
                }
            }
            if (!QualifiedExpressionResolverKt.isVisible(descriptor, shouldBeVisibleFrom, position, this.languageVersionSettings)) {
                trace.report(Errors.INVISIBLE_REFERENCE.on(referenceExpression, descriptor, ((DeclarationDescriptorWithVisibility) descriptor).getVisibility(), descriptor));
            }
        }
        if (isQualifier) {
            return storeQualifier(trace, referenceExpression, descriptor);
        }
        return null;
    }

    private final Qualifier storeQualifier(BindingTrace trace, KtSimpleNameExpression referenceExpression, DeclarationDescriptor descriptor) {
        TypeAliasDescriptor typeAliasDescriptor;
        ClassDescriptor classDescriptor;
        TypeAliasQualifier typeAliasQualifier;
        if (descriptor instanceof PackageViewDescriptor) {
            typeAliasQualifier = new PackageQualifier(referenceExpression, (PackageViewDescriptor) descriptor);
        } else if (descriptor instanceof ClassDescriptor) {
            typeAliasQualifier = new ClassQualifier(referenceExpression, (ClassDescriptor) descriptor);
        } else if (descriptor instanceof TypeParameterDescriptor) {
            typeAliasQualifier = new TypeParameterQualifier(referenceExpression, (TypeParameterDescriptor) descriptor);
        } else {
            if (!(descriptor instanceof TypeAliasDescriptor) || (classDescriptor = (typeAliasDescriptor = (TypeAliasDescriptor) descriptor).getClassDescriptor()) == null) {
                return null;
            }
            typeAliasQualifier = new TypeAliasQualifier(referenceExpression, typeAliasDescriptor, classDescriptor);
        }
        trace.record(BindingContext.QUALIFIER, QualifierKt.getExpression(typeAliasQualifier), typeAliasQualifier);
        return typeAliasQualifier;
    }

    private final Pair<PackageViewDescriptor, Integer> quickResolveToPackage(ModuleDescriptor moduleDescriptor, List<? extends QualifierPart> list, BindingTrace bindingTrace, QualifierPosition qualifierPosition) {
        Iterator<? extends QualifierPart> it = list.iterator();
        int i = 0;
        while (true) {
            if (!it.hasNext()) {
                i = -1;
                break;
            }
            if (it.next().getTypeArguments() != null) {
                break;
            }
            i++;
        }
        int size = i == -1 ? list.size() : i + 1;
        List<? extends QualifierPart> listSubList = list.subList(0, size);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(listSubList, 10));
        Iterator<T> it2 = listSubList.iterator();
        while (it2.hasNext()) {
            arrayList.add(((QualifierPart) it2.next()).getName().asString());
        }
        FqName fqNameFromSegments = FqName.fromSegments(arrayList);
        Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "fromSegments(...)");
        while (!fqNameFromSegments.isRoot()) {
            PackageViewDescriptor packageViewDescriptor = moduleDescriptor.getPackage(fqNameFromSegments);
            if (!packageViewDescriptor.isEmpty()) {
                recordPackageViews(list.subList(0, size), packageViewDescriptor, bindingTrace, qualifierPosition);
                return new Pair<>(packageViewDescriptor, Integer.valueOf(size));
            }
            fqNameFromSegments = fqNameFromSegments.parent();
            Intrinsics.checkNotNullExpressionValue(fqNameFromSegments, "parent(...)");
            size--;
        }
        FqName ROOT = FqName.ROOT;
        Intrinsics.checkNotNullExpressionValue(ROOT, "ROOT");
        return new Pair<>(moduleDescriptor.getPackage(ROOT), 0);
    }

    private final void recordPackageViews(List<? extends QualifierPart> path, PackageViewDescriptor packageView, BindingTrace trace, QualifierPosition position) {
        if (path.isEmpty()) {
            return;
        }
        ListIterator<? extends QualifierPart> listIterator = path.listIterator(path.size());
        PackageViewDescriptor packageViewDescriptor = packageView;
        while (listIterator.hasPrevious()) {
            storeResult$default(this, trace, listIterator.previous().getExpression(), packageViewDescriptor, null, position, false, 32, null);
            PackageViewDescriptor containingDeclaration = packageViewDescriptor.getContainingDeclaration();
            if (containingDeclaration == null) {
                throw new IllegalStateException(("Containing Declaration must be not null for package with fqName: " + packageViewDescriptor.getFqName() + ", path: " + CollectionsKt.joinToString$default(path, null, null, null, 0, null, null, 63, null) + ", packageView fqName: " + packageView.getFqName()).toString());
            }
            packageViewDescriptor = containingDeclaration;
        }
    }
}
