package org.jetbrains.kotlin.checkers.utils;

import android.Manifest;
import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.collections.SetsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import org.jetbrains.kotlin.checkers.AbstractDiagnosticDescriptor;
import org.jetbrains.kotlin.checkers.ActualDiagnosticDescriptor;
import org.jetbrains.kotlin.checkers.CheckerDebugInfoReporter;
import org.jetbrains.kotlin.checkers.DiagnosedRange;
import org.jetbrains.kotlin.checkers.DiagnosticDiffCallbacks;
import org.jetbrains.kotlin.checkers.TextDiagnosticDescriptor;
import org.jetbrains.kotlin.checkers.diagnostics.AbstractTestDiagnostic;
import org.jetbrains.kotlin.checkers.diagnostics.ActualDiagnostic;
import org.jetbrains.kotlin.checkers.diagnostics.PositionalTextDiagnostic;
import org.jetbrains.kotlin.checkers.diagnostics.SyntaxErrorDiagnostic;
import org.jetbrains.kotlin.checkers.diagnostics.TextDiagnostic;
import org.jetbrains.kotlin.checkers.diagnostics.factories.DebugInfoDiagnosticFactory;
import org.jetbrains.kotlin.checkers.diagnostics.factories.DebugInfoDiagnosticFactory0;
import org.jetbrains.kotlin.checkers.diagnostics.factories.DebugInfoDiagnosticFactory1;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableMap;
import org.jetbrains.kotlin.com.google.common.collect.LinkedListMultimap;
import org.jetbrains.kotlin.com.intellij.openapi.util.TextRange;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiErrorElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.com.intellij.util.containers.Stack;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ModuleDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;
import org.jetbrains.kotlin.name.FqNameUnsafe;
import org.jetbrains.kotlin.platform.PlatformUtilKt;
import org.jetbrains.kotlin.platform.SimplePlatform;
import org.jetbrains.kotlin.platform.TargetPlatform;
import org.jetbrains.kotlin.platform.TargetPlatformKt;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtCallableDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtReferenceExpression;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.AnalyzingUtils;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.calls.model.ResolvedCall;
import org.jetbrains.kotlin.resolve.calls.model.VariableAsFunctionResolvedCall;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValue;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowValueFactory;
import org.jetbrains.kotlin.resolve.calls.smartcasts.ExplicitSmartCasts;
import org.jetbrains.kotlin.resolve.calls.util.CallUtilKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: CheckerTestUtil.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0080\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010#\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\"\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010(\n\u0002\b\u0004\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001c\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014Jb\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u00172\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0012\u0012\u0004\u0012\u00020\u00040\u001b2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u00142\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fJ\u0016\u0010!\u001a\u00020\"2\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J\u0018\u0010$\u001a\n \r*\u0004\u0018\u00010\u00100\u00102\u0006\u0010%\u001a\u00020\u0010H\u0002J4\u0010&\u001a\u00020\"2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020,2\u0012\u0010-\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190.H\u0002J\u0018\u0010/\u001a\u00020\u001f2\u0006\u00100\u001a\u00020\u00192\u0006\u0010#\u001a\u00020\u0019H\u0002J6\u00101\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u00172\f\u00100\u001a\b\u0012\u0004\u0012\u00020*022\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\u0006\u0010'\u001a\u00020(J\u001c\u00103\u001a\b\u0012\u0004\u0012\u00020,022\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u0014H\u0002J$\u00104\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u000106\u0012\u0004\u0012\u00020\u0004052\u0006\u00107\u001a\u0002082\u0006\u00109\u001a\u00020:Jj\u0010;\u001a\b\u0012\u0004\u0012\u00020\u0015022\u0006\u0010<\u001a\u0002082\u0006\u00109\u001a\u00020:2\u0006\u0010=\u001a\u00020\u001f2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020@0?2\u0006\u0010A\u001a\u00020B2\b\u0010C\u001a\u0004\u0018\u00010D2\b\u0010E\u001a\u0004\u0018\u00010F2\u001a\u0010G\u001a\u0016\u0012\u0004\u0012\u00020H\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040I\u0018\u00010\u0017Jl\u0010J\u001a\b\u0012\u0004\u0012\u00020\u00150?2\u0006\u00109\u001a\u00020:2\u0006\u0010<\u001a\u0002082\u0006\u0010=\u001a\u00020\u001f2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020@0?2\u0006\u0010A\u001a\u00020B2\b\u0010C\u001a\u0004\u0018\u00010D2\b\u0010E\u001a\u0004\u0018\u00010F2\u001c\b\u0002\u0010G\u001a\u0016\u0012\u0004\u0012\u00020H\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040I\u0018\u00010.J\u0086\u0001\u0010J\u001a\b\u0012\u0004\u0012\u00020\u0015022\u0006\u00109\u001a\u00020:2\u0018\u0010K\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020L\u0012\u0004\u0012\u00020:05022\u0006\u0010<\u001a\u0002082\u0006\u0010=\u001a\u00020\u001f2\f\u0010>\u001a\b\u0012\u0004\u0012\u00020@0?2\u0006\u0010A\u001a\u00020B2\b\u0010C\u001a\u0004\u0018\u00010D2\b\u0010E\u001a\u0004\u0018\u00010F2\u001c\b\u0002\u0010G\u001a\u0016\u0012\u0004\u0012\u00020H\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040I\u0018\u00010.J*\u0010M\u001a\b\u0012\u0004\u0012\u00020N022\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0014H\u0002JJ\u0010O\u001a\u0018\u0012\u0006\u0012\u0004\u0018\u00010P\u0012\f\u0012\n\u0012\u0004\u0012\u00020P\u0018\u00010Q052\u0006\u0010R\u001a\u0002082\u0006\u00109\u001a\u00020:2\b\u0010C\u001a\u0004\u0018\u00010D2\b\u0010S\u001a\u0004\u0018\u00010T2\b\u0010E\u001a\u0004\u0018\u00010FJ\u0018\u0010U\u001a\u00020\u00042\u0006\u0010R\u001a\u00020V2\u0006\u00109\u001a\u00020:H\u0002J*\u0010W\u001a\b\u0012\u0004\u0012\u00020N0?2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00150\u00142\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u001d0\u0014H\u0002J\u0010\u0010X\u001a\u00020\u001f2\u0006\u0010Y\u001a\u00020\u0018H\u0002J\u0018\u0010Z\u001a\u00020\"2\u0006\u0010'\u001a\u00020(2\u0006\u0010)\u001a\u00020*H\u0002J<\u0010[\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020\u00102\u0006\u0010\\\u001a\u00020N2\u0012\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0018\u0012\u0004\u0012\u00020\u00190\u00172\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u001fH\u0002J:\u0010]\u001a\u00020\u00042\u0006\u0010^\u001a\u00020\u00042\f\u0010_\u001a\b\u0012\u0004\u0012\u00020*0?2\u001c\b\u0002\u0010`\u001a\u0016\u0012\u0004\u0012\u00020H\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040I\u0018\u00010.Jn\u0010a\u001a\u00020\"2\u0006\u0010b\u001a\u00020c2\u0006\u00107\u001a\u00020d2\u0006\u0010<\u001a\u0002082\u0006\u00109\u001a\u00020:2\u0006\u0010A\u001a\u00020B2\b\u0010C\u001a\u0004\u0018\u00010D2\b\u0010E\u001a\u0004\u0018\u00010F2\u001a\u0010G\u001a\u0016\u0012\u0004\u0012\u00020H\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040I\u0018\u00010\u00172\f\u0010e\u001a\b\u0012\u0004\u0012\u00020\u00150?H\u0002J²\u0001\u0010f\u001a\u00020\"\"\u0004\b\u0000\u0010g\"\u0004\b\u0001\u0010h2.\u0010i\u001a*\u0012&\u0012$\u0012\u0014\u0012\u0012\u0012\u0006\b\u0001\u0012\u0002Hg\u0012\u0006\b\u0001\u0012\u0002Hh0j\u0012\n\u0012\b\u0012\u0004\u0012\u00020c0205022\u0006\u0010<\u001a\u0002082\u0006\u00109\u001a\u00020:2\u0006\u0010A\u001a\u00020B2\b\u0010C\u001a\u0004\u0018\u00010D2\b\u0010E\u001a\u0004\u0018\u00010F2\u001a\u0010G\u001a\u0016\u0012\u0004\u0012\u00020H\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040I\u0018\u00010\u00172\f\u0010e\u001a\b\u0012\u0004\u0012\u00020\u00150?2\u0016\b\u0002\u0010k\u001a\u0010\u0012\u0004\u0012\u0002Hg\u0012\u0006\u0012\u0004\u0018\u00010d0\u001bH\u0002J#\u0010l\u001a\u0004\u0018\u0001Hg\"\u0004\b\u0000\u0010g2\f\u0010m\u001a\b\u0012\u0004\u0012\u0002Hg0nH\u0002¢\u0006\u0002\u0010oJ\u0018\u0010p\u001a\u00020\"2\u0006\u0010q\u001a\u00020,2\u0006\u0010'\u001a\u00020(H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0019\u0010\f\u001a\n \r*\u0004\u0018\u00010\t0\t¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000b¨\u0006r"}, d2 = {"Lorg/jetbrains/kotlin/checkers/utils/CheckerTestUtil;", "", "()V", "IGNORE_DIAGNOSTIC_PARAMETER", "", "INDIVIDUAL_DIAGNOSTIC", "NEW_INFERENCE_PREFIX", "OLD_INFERENCE_PREFIX", "individualDiagnosticPattern", "Ljava/util/regex/Pattern;", "getIndividualDiagnosticPattern", "()Ljava/util/regex/Pattern;", "rangeStartOrEndPattern", JvmProtoBufUtil.PLATFORM_TYPE_ID, "getRangeStartOrEndPattern", "addDiagnosticMarkersToText", "Ljava/lang/StringBuffer;", "psiFile", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiFile;", "diagnostics", "", "Lorg/jetbrains/kotlin/checkers/diagnostics/ActualDiagnostic;", "diagnosticToExpectedDiagnostic", "", "Lorg/jetbrains/kotlin/checkers/diagnostics/AbstractTestDiagnostic;", "Lorg/jetbrains/kotlin/checkers/diagnostics/TextDiagnostic;", "getFileText", "Lkotlin/Function1;", "uncheckedDiagnostics", "Lorg/jetbrains/kotlin/checkers/diagnostics/PositionalTextDiagnostic;", "withNewInferenceDirective", "", "renderDiagnosticMessages", "assertSameFile", "", "actual", "closeDiagnosticString", "result", "compareDiagnostics", "callbacks", "Lorg/jetbrains/kotlin/checkers/DiagnosticDiffCallbacks;", "currentExpected", "Lorg/jetbrains/kotlin/checkers/DiagnosedRange;", "currentActual", "Lorg/jetbrains/kotlin/checkers/ActualDiagnosticDescriptor;", "diagnosticToInput", "", "compareTextDiagnostic", "expected", "diagnosticsDiff", "", "getActualSortedDiagnosticDescriptors", "getCallDebugInfo", "Lkotlin/Pair;", "Lorg/jetbrains/kotlin/name/FqNameUnsafe;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "getDebugInfoDiagnostics", "root", "markDynamicCalls", "dynamicCallDescriptors", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "configuration", "Lorg/jetbrains/kotlin/checkers/utils/DiagnosticsRenderingConfiguration;", "dataFlowValueFactory", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowValueFactory;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/impl/ModuleDescriptorImpl;", "diagnosedRanges", "Lkotlin/ranges/IntRange;", "", "getDiagnosticsIncludingSyntaxErrors", "implementingModulesBindings", "Lorg/jetbrains/kotlin/platform/TargetPlatform;", "getSortedDiagnosticDescriptors", "Lorg/jetbrains/kotlin/checkers/AbstractDiagnosticDescriptor;", "getTypeInfo", "Lorg/jetbrains/kotlin/types/KotlinType;", "", "expression", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "getTypeOfCall", "Lorg/jetbrains/kotlin/psi/KtExpression;", "groupDiagnosticsByTextRange", "hasExplicitDefinitionOnlyOption", "diagnostic", "missingDiagnostics", "openDiagnosticsString", "currentDescriptor", "parseDiagnosedRanges", "text", "ranges", "rangesToDiagnosticNames", "renderDiagnostics", "factory", "Lorg/jetbrains/kotlin/checkers/diagnostics/factories/DebugInfoDiagnosticFactory;", "Lorg/jetbrains/kotlin/psi/KtElement;", "debugAnnotations", "renderDiagnosticsByFactoryList", ExifInterface.GPS_DIRECTION_TRUE, "K", "factoryList", "Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", "elementProvider", "safeAdvance", "iterator", "", "(Ljava/util/Iterator;)Ljava/lang/Object;", "unexpectedDiagnostics", "descriptor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CheckerTestUtil {
    public static final String NEW_INFERENCE_PREFIX = "NI";
    public static final String OLD_INFERENCE_PREFIX = "OI";
    private static final Pattern individualDiagnosticPattern;
    public static final CheckerTestUtil INSTANCE = new CheckerTestUtil();
    private static final Pattern rangeStartOrEndPattern = Pattern.compile("(<!(\\w+;)?(\\w+:)?(\\w+)(\\{[\\w;]+})?(?:\\(((?:\".*?\")(?:,\\s*\".*?\")*)\\))?(,\\s*(\\w+;)?(\\w+:)?(\\w+)(\\{[\\w;]+})?(?:\\(((?:\".*?\")(?:,\\s*\".*?\")*)\\))?)*!>)|(<!>)");

    private CheckerTestUtil() {
    }

    static {
        Pattern patternCompile = Pattern.compile("(\\w+;)?(\\w+:)?(\\w+)(\\{[\\w;]+})?(?:\\(((?:\".*?\")(?:,\\s*\".*?\")*)\\))?");
        Intrinsics.checkNotNullExpressionValue(patternCompile, "compile(...)");
        individualDiagnosticPattern = patternCompile;
    }

    public final Pattern getRangeStartOrEndPattern() {
        return rangeStartOrEndPattern;
    }

    public final Pattern getIndividualDiagnosticPattern() {
        return individualDiagnosticPattern;
    }

    public final List<ActualDiagnostic> getDiagnosticsIncludingSyntaxErrors(BindingContext bindingContext, List<? extends Pair<? extends TargetPlatform, ? extends BindingContext>> implementingModulesBindings, PsiElement root, boolean markDynamicCalls, List<DeclarationDescriptor> dynamicCallDescriptors, DiagnosticsRenderingConfiguration configuration, DataFlowValueFactory dataFlowValueFactory, ModuleDescriptorImpl moduleDescriptor, Map<IntRange, Set<String>> diagnosedRanges) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(implementingModulesBindings, "implementingModulesBindings");
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(dynamicCallDescriptors, "dynamicCallDescriptors");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        List<ActualDiagnostic> diagnosticsIncludingSyntaxErrors = getDiagnosticsIncludingSyntaxErrors(bindingContext, root, markDynamicCalls, dynamicCallDescriptors, configuration, dataFlowValueFactory, moduleDescriptor, diagnosedRanges);
        for (Pair pair : CollectionsKt.sortedWith(implementingModulesBindings, new Comparator() { // from class: org.jetbrains.kotlin.checkers.utils.CheckerTestUtil$getDiagnosticsIncludingSyntaxErrors$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(PlatformUtilKt.getOldFashionedDescription((TargetPlatform) ((Pair) t).getFirst()), PlatformUtilKt.getOldFashionedDescription((TargetPlatform) ((Pair) t2).getFirst()));
            }
        })) {
            TargetPlatform targetPlatform = (TargetPlatform) pair.component1();
            BindingContext bindingContext2 = (BindingContext) pair.component2();
            TargetPlatformKt.isCommon(targetPlatform);
            diagnosticsIncludingSyntaxErrors.addAll(getDiagnosticsIncludingSyntaxErrors(bindingContext2, root, markDynamicCalls, dynamicCallDescriptors, DiagnosticsRenderingConfiguration.copy$default(configuration, ((SimplePlatform) CollectionsKt.single(targetPlatform)).getPlatformName(), false, null, false, 14, null), dataFlowValueFactory, moduleDescriptor, diagnosedRanges));
        }
        return diagnosticsIncludingSyntaxErrors;
    }

    public final List<ActualDiagnostic> getDiagnosticsIncludingSyntaxErrors(BindingContext bindingContext, PsiElement root, boolean markDynamicCalls, List<DeclarationDescriptor> dynamicCallDescriptors, DiagnosticsRenderingConfiguration configuration, DataFlowValueFactory dataFlowValueFactory, ModuleDescriptorImpl moduleDescriptor, Map<IntRange, Set<String>> diagnosedRanges) {
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(dynamicCallDescriptors, "dynamicCallDescriptors");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        ArrayList arrayList = new ArrayList();
        Diagnostics<Diagnostic> diagnostics = bindingContext.getDiagnostics();
        Intrinsics.checkNotNullExpressionValue(diagnostics, "getDiagnostics(...)");
        for (Diagnostic diagnostic : diagnostics) {
            if (PsiTreeUtil.isAncestor(root, diagnostic.getPsiElement(), false)) {
                arrayList.add(new ActualDiagnostic(diagnostic, configuration.getPlatform(), configuration.getWithNewInference()));
            }
        }
        Iterator<PsiErrorElement> it = AnalyzingUtils.getSyntaxErrorRanges(root).iterator();
        while (it.hasNext()) {
            arrayList.add(new ActualDiagnostic(new SyntaxErrorDiagnostic(it.next()), configuration.getPlatform(), configuration.getWithNewInference()));
        }
        if (!configuration.getSkipDebugInfoDiagnostics()) {
            arrayList.addAll(getDebugInfoDiagnostics(root, bindingContext, markDynamicCalls, dynamicCallDescriptors, configuration, dataFlowValueFactory, moduleDescriptor, diagnosedRanges));
        }
        return arrayList;
    }

    public final List<ActualDiagnostic> getDebugInfoDiagnostics(PsiElement root, BindingContext bindingContext, boolean markDynamicCalls, List<DeclarationDescriptor> dynamicCallDescriptors, DiagnosticsRenderingConfiguration configuration, DataFlowValueFactory dataFlowValueFactory, ModuleDescriptorImpl moduleDescriptor, Map<IntRange, ? extends Set<String>> diagnosedRanges) {
        Intrinsics.checkNotNullParameter(root, "root");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        Intrinsics.checkNotNullParameter(dynamicCallDescriptors, "dynamicCallDescriptors");
        Intrinsics.checkNotNullParameter(configuration, "configuration");
        ArrayList arrayList = new ArrayList();
        DebugInfoUtil.INSTANCE.markDebugAnnotations(root, bindingContext, new CheckerDebugInfoReporter(dynamicCallDescriptors, markDynamicCalls, arrayList, configuration.getWithNewInference(), configuration.getPlatform()));
        List listListOf = CollectionsKt.listOf((Object[]) new Pair[]{TuplesKt.to(BindingContext.EXPRESSION_TYPE_INFO, CollectionsKt.listOf(DebugInfoDiagnosticFactory1.INSTANCE.getEXPRESSION_TYPE())), TuplesKt.to(BindingContext.SMARTCAST, CollectionsKt.listOf(DebugInfoDiagnosticFactory0.INSTANCE.getSMARTCAST())), TuplesKt.to(BindingContext.IMPLICIT_RECEIVER_SMARTCAST, CollectionsKt.listOf(DebugInfoDiagnosticFactory0.INSTANCE.getIMPLICIT_RECEIVER_SMARTCAST())), TuplesKt.to(BindingContext.SMARTCAST_NULL, CollectionsKt.listOf(DebugInfoDiagnosticFactory0.INSTANCE.getCONSTANT())), TuplesKt.to(BindingContext.LEAKING_THIS, CollectionsKt.listOf(DebugInfoDiagnosticFactory0.INSTANCE.getLEAKING_THIS())), TuplesKt.to(BindingContext.IMPLICIT_EXHAUSTIVE_WHEN, CollectionsKt.listOf(DebugInfoDiagnosticFactory0.INSTANCE.getIMPLICIT_EXHAUSTIVE()))});
        List listListOf2 = CollectionsKt.listOf(TuplesKt.to(BindingContext.RESOLVED_CALL, CollectionsKt.listOf((Object[]) new DebugInfoDiagnosticFactory1[]{DebugInfoDiagnosticFactory1.INSTANCE.getCALL(), DebugInfoDiagnosticFactory1.INSTANCE.getCALLABLE_OWNER()})));
        renderDiagnosticsByFactoryList$default(this, listListOf, root, bindingContext, configuration, dataFlowValueFactory, moduleDescriptor, diagnosedRanges, arrayList, null, 256, null);
        renderDiagnosticsByFactoryList(listListOf2, root, bindingContext, configuration, dataFlowValueFactory, moduleDescriptor, diagnosedRanges, arrayList, new Function1<Call, KtElement>() { // from class: org.jetbrains.kotlin.checkers.utils.CheckerTestUtil.getDebugInfoDiagnostics.1
            @Override // kotlin.jvm.functions.Function1
            public final KtElement invoke(Call call) {
                return call.getCallElement();
            }
        });
        return arrayList;
    }

    static /* synthetic */ void renderDiagnosticsByFactoryList$default(CheckerTestUtil checkerTestUtil, List list, PsiElement psiElement, BindingContext bindingContext, DiagnosticsRenderingConfiguration diagnosticsRenderingConfiguration, DataFlowValueFactory dataFlowValueFactory, ModuleDescriptorImpl moduleDescriptorImpl, Map map, List list2, Function1 function1, int i, Object obj) {
        checkerTestUtil.renderDiagnosticsByFactoryList(list, psiElement, bindingContext, diagnosticsRenderingConfiguration, dataFlowValueFactory, moduleDescriptorImpl, map, list2, (i & 256) != 0 ? new Function1<T, KtElement>() { // from class: org.jetbrains.kotlin.checkers.utils.CheckerTestUtil.renderDiagnosticsByFactoryList.1
            @Override // kotlin.jvm.functions.Function1
            public final KtElement invoke(T t) {
                if (t instanceof KtElement) {
                    return (KtElement) t;
                }
                return null;
            }
        } : function1);
    }

    private final <T, K> void renderDiagnosticsByFactoryList(List<? extends Pair<? extends WritableSlice<? extends T, ? extends K>, ? extends List<? extends DebugInfoDiagnosticFactory>>> factoryList, PsiElement root, BindingContext bindingContext, DiagnosticsRenderingConfiguration configuration, DataFlowValueFactory dataFlowValueFactory, ModuleDescriptorImpl moduleDescriptor, Map<IntRange, ? extends Set<String>> diagnosedRanges, List<ActualDiagnostic> debugAnnotations, Function1<? super T, ? extends KtElement> elementProvider) {
        for (Pair<? extends WritableSlice<? extends T, ? extends K>, ? extends List<? extends DebugInfoDiagnosticFactory>> pair : factoryList) {
            WritableSlice<? extends T, ? extends K> writableSliceComponent1 = pair.component1();
            List<? extends DebugInfoDiagnosticFactory> listComponent2 = pair.component2();
            ImmutableMap sliceContents = bindingContext.getSliceContents(writableSliceComponent1);
            Intrinsics.checkNotNullExpressionValue(sliceContents, "getSliceContents(...)");
            Iterator it = sliceContents.entrySet().iterator();
            while (it.hasNext()) {
                Manifest.permission permissionVar = (Object) ((Map.Entry) it.next()).getKey();
                for (DebugInfoDiagnosticFactory debugInfoDiagnosticFactory : listComponent2) {
                    KtElement ktElementInvoke = elementProvider.invoke(permissionVar);
                    if (ktElementInvoke != null) {
                        renderDiagnostics(debugInfoDiagnosticFactory, ktElementInvoke, root, bindingContext, configuration, dataFlowValueFactory, moduleDescriptor, diagnosedRanges, debugAnnotations);
                    }
                }
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:13:0x0037  */
    /* JADX WARN: Multi-variable type inference failed */
    private final void renderDiagnostics(DebugInfoDiagnosticFactory factory, KtElement element, PsiElement root, BindingContext bindingContext, DiagnosticsRenderingConfiguration configuration, DataFlowValueFactory dataFlowValueFactory, ModuleDescriptorImpl moduleDescriptor, Map<IntRange, ? extends Set<String>> diagnosedRanges, List<ActualDiagnostic> debugAnnotations) {
        if (factory instanceof DiagnosticFactory) {
            boolean z = true;
            if (factory.getWithExplicitDefinitionOnly()) {
                if (diagnosedRanges != null) {
                    KtElement ktElement = element;
                    Set<String> set = diagnosedRanges.get(new IntRange(PsiUtilsKt.getStartOffset(ktElement), PsiUtilsKt.getEndOffset(ktElement)));
                    if (set == null || !set.contains(((DiagnosticFactory) factory).getName())) {
                        z = false;
                    }
                } else {
                    z = false;
                }
            }
            if (PsiTreeUtil.isAncestor(root, element, false) && z) {
                debugAnnotations.add(new ActualDiagnostic(factory.createDiagnostic(element, bindingContext, dataFlowValueFactory, configuration.getLanguageVersionSettings(), moduleDescriptor), configuration.getPlatform(), configuration.getWithNewInference()));
            }
        }
    }

    public final Map<AbstractTestDiagnostic, TextDiagnostic> diagnosticsDiff(List<DiagnosedRange> expected, Collection<ActualDiagnostic> actual, DiagnosticDiffCallbacks callbacks) {
        Intrinsics.checkNotNullParameter(expected, "expected");
        Intrinsics.checkNotNullParameter(actual, "actual");
        Intrinsics.checkNotNullParameter(callbacks, "callbacks");
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        assertSameFile(actual);
        Iterator<DiagnosedRange> it = expected.iterator();
        Iterator<ActualDiagnosticDescriptor> it2 = getActualSortedDiagnosticDescriptors(actual).iterator();
        DiagnosedRange diagnosedRange = (DiagnosedRange) safeAdvance(it);
        ActualDiagnosticDescriptor actualDiagnosticDescriptor = (ActualDiagnosticDescriptor) safeAdvance(it2);
        while (true) {
            if (diagnosedRange == null && actualDiagnosticDescriptor == null) {
                return linkedHashMap;
            }
            if (diagnosedRange == null) {
                Intrinsics.checkNotNull(actualDiagnosticDescriptor);
                unexpectedDiagnostics(actualDiagnosticDescriptor, callbacks);
                actualDiagnosticDescriptor = (ActualDiagnosticDescriptor) safeAdvance(it2);
            } else if (actualDiagnosticDescriptor == null) {
                missingDiagnostics(callbacks, diagnosedRange);
                diagnosedRange = (DiagnosedRange) safeAdvance(it);
            } else {
                int start = diagnosedRange.getStart();
                int start2 = actualDiagnosticDescriptor.getStart();
                int end = diagnosedRange.getEnd();
                int end2 = actualDiagnosticDescriptor.getEnd();
                if (start < start2) {
                    missingDiagnostics(callbacks, diagnosedRange);
                    diagnosedRange = (DiagnosedRange) safeAdvance(it);
                } else if (start > start2) {
                    unexpectedDiagnostics(actualDiagnosticDescriptor, callbacks);
                    actualDiagnosticDescriptor = (ActualDiagnosticDescriptor) safeAdvance(it2);
                } else if (end > end2) {
                    missingDiagnostics(callbacks, diagnosedRange);
                    diagnosedRange = (DiagnosedRange) safeAdvance(it);
                } else if (end < end2) {
                    unexpectedDiagnostics(actualDiagnosticDescriptor, callbacks);
                    actualDiagnosticDescriptor = (ActualDiagnosticDescriptor) safeAdvance(it2);
                } else {
                    compareDiagnostics(callbacks, diagnosedRange, actualDiagnosticDescriptor, linkedHashMap);
                    diagnosedRange = (DiagnosedRange) safeAdvance(it);
                    actualDiagnosticDescriptor = (ActualDiagnosticDescriptor) safeAdvance(it2);
                }
            }
        }
    }

    private final void compareDiagnostics(DiagnosticDiffCallbacks callbacks, DiagnosedRange currentExpected, ActualDiagnosticDescriptor currentActual, Map<AbstractTestDiagnostic, TextDiagnostic> diagnosticToInput) {
        Object obj;
        Object next;
        int start = currentExpected.getStart();
        int end = currentExpected.getEnd();
        int start2 = currentActual.getStart();
        int end2 = currentActual.getEnd();
        Map<AbstractTestDiagnostic, TextDiagnostic> textDiagnosticsMap = currentActual.getTextDiagnosticsMap();
        List<TextDiagnostic> diagnostics = currentExpected.getDiagnostics();
        HashSet hashSet = new HashSet();
        for (TextDiagnostic textDiagnostic : diagnostics) {
            Iterator<T> it = textDiagnosticsMap.entrySet().iterator();
            while (true) {
                obj = null;
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                TextDiagnostic textDiagnostic2 = (TextDiagnostic) ((Map.Entry) next).getValue();
                if (Intrinsics.areEqual(textDiagnostic.getDescription(), textDiagnostic2.getDescription()) && textDiagnostic.getInferenceCompatibility().isCompatible(textDiagnostic2.getInferenceCompatibility()) && Intrinsics.areEqual(textDiagnostic.getParameters(), textDiagnostic2.getParameters())) {
                    break;
                }
            }
            Map.Entry entry = (Map.Entry) next;
            if (entry == null) {
                for (Object obj2 : textDiagnosticsMap.entrySet()) {
                    TextDiagnostic textDiagnostic3 = (TextDiagnostic) ((Map.Entry) obj2).getValue();
                    if (Intrinsics.areEqual(textDiagnostic.getDescription(), textDiagnostic3.getDescription()) && textDiagnostic.getInferenceCompatibility().isCompatible(textDiagnostic3.getInferenceCompatibility())) {
                        obj = obj2;
                        break;
                    }
                }
                entry = (Map.Entry) obj;
            }
            if (entry == null) {
                callbacks.missingDiagnostic(textDiagnostic, start, end);
            } else {
                AbstractTestDiagnostic abstractTestDiagnostic = (AbstractTestDiagnostic) entry.getKey();
                TextDiagnostic textDiagnostic4 = (TextDiagnostic) entry.getValue();
                if (!compareTextDiagnostic(textDiagnostic, textDiagnostic4)) {
                    callbacks.wrongParametersDiagnostic(textDiagnostic, textDiagnostic4, start, end);
                }
                textDiagnosticsMap.remove(abstractTestDiagnostic);
                hashSet.add(abstractTestDiagnostic.getName());
                abstractTestDiagnostic.enhanceInferenceCompatibility(textDiagnostic.getInferenceCompatibility());
                diagnosticToInput.put(abstractTestDiagnostic, textDiagnostic);
            }
        }
        for (AbstractTestDiagnostic abstractTestDiagnostic2 : textDiagnosticsMap.keySet()) {
            TextDiagnostic textDiagnostic5 = textDiagnosticsMap.get(abstractTestDiagnostic2);
            if (!hasExplicitDefinitionOnlyOption(abstractTestDiagnostic2) || hashSet.contains(abstractTestDiagnostic2.getName())) {
                Intrinsics.checkNotNull(textDiagnostic5);
                callbacks.unexpectedDiagnostic(textDiagnostic5, start2, end2);
            }
        }
    }

    private final boolean compareTextDiagnostic(TextDiagnostic expected, TextDiagnostic actual) {
        if (!Intrinsics.areEqual(expected.getDescription(), actual.getDescription())) {
            return false;
        }
        if (expected.getParameters() == null) {
            return true;
        }
        if (actual.getParameters() == null || expected.getParameters().size() != actual.getParameters().size()) {
            return false;
        }
        int i = 0;
        for (Object obj : expected.getParameters()) {
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt.throwIndexOverflow();
            }
            String str = (String) obj;
            if (!Intrinsics.areEqual(str, "IGNORE") && !Intrinsics.areEqual(str, actual.getParameters().get(i))) {
                return false;
            }
            i = i2;
        }
        return true;
    }

    private final void assertSameFile(Collection<ActualDiagnostic> actual) {
        if (actual.isEmpty()) {
            return;
        }
        PsiFile file = ((ActualDiagnostic) CollectionsKt.first(actual)).getFile();
        Iterator<ActualDiagnostic> it = actual.iterator();
        while (it.hasNext()) {
            Intrinsics.areEqual(it.next().getFile(), file);
        }
    }

    private final void unexpectedDiagnostics(ActualDiagnosticDescriptor descriptor, DiagnosticDiffCallbacks callbacks) {
        for (AbstractTestDiagnostic abstractTestDiagnostic : descriptor.getDiagnostics()) {
            if (!hasExplicitDefinitionOnlyOption(abstractTestDiagnostic)) {
                callbacks.unexpectedDiagnostic(TextDiagnostic.INSTANCE.asTextDiagnostic(abstractTestDiagnostic), descriptor.getStart(), descriptor.getEnd());
            }
        }
    }

    private final void missingDiagnostics(DiagnosticDiffCallbacks callbacks, DiagnosedRange currentExpected) {
        Iterator<TextDiagnostic> it = currentExpected.getDiagnostics().iterator();
        while (it.hasNext()) {
            callbacks.missingDiagnostic(it.next(), currentExpected.getStart(), currentExpected.getEnd());
        }
    }

    private final <T> T safeAdvance(Iterator<? extends T> iterator) {
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ String parseDiagnosedRanges$default(CheckerTestUtil checkerTestUtil, String str, List list, Map map, int i, Object obj) {
        if ((i & 4) != 0) {
            map = null;
        }
        return checkerTestUtil.parseDiagnosedRanges(str, list, map);
    }

    public final String parseDiagnosedRanges(String text, List<DiagnosedRange> ranges, Map<IntRange, Set<String>> rangesToDiagnosticNames) {
        Intrinsics.checkNotNullParameter(text, "text");
        Intrinsics.checkNotNullParameter(ranges, "ranges");
        Matcher matcher = rangeStartOrEndPattern.matcher(text);
        Stack stack = new Stack();
        int length = 0;
        while (matcher.find()) {
            int iStart = matcher.start() - length;
            String strGroup = matcher.group();
            if (Intrinsics.areEqual(strGroup, "<!>")) {
                ((DiagnosedRange) stack.pop()).setEnd(iStart);
            } else {
                Matcher matcher2 = individualDiagnosticPattern.matcher(strGroup);
                DiagnosedRange diagnosedRange = new DiagnosedRange(iStart);
                while (matcher2.find()) {
                    String strGroup2 = matcher2.group();
                    Intrinsics.checkNotNullExpressionValue(strGroup2, "group(...)");
                    diagnosedRange.addDiagnostic(strGroup2);
                }
                stack.push(diagnosedRange);
                ranges.add(diagnosedRange);
            }
            length += strGroup.length();
        }
        stack.isEmpty();
        matcher.reset();
        if (rangesToDiagnosticNames != null) {
            for (DiagnosedRange diagnosedRange2 : ranges) {
                IntRange intRange = new IntRange(diagnosedRange2.getStart(), diagnosedRange2.getEnd());
                rangesToDiagnosticNames.putIfAbsent(intRange, new LinkedHashSet());
                Set<String> set = rangesToDiagnosticNames.get(intRange);
                Intrinsics.checkNotNull(set);
                Set<String> set2 = set;
                List<TextDiagnostic> diagnostics = diagnosedRange2.getDiagnostics();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(diagnostics, 10));
                Iterator<T> it = diagnostics.iterator();
                while (it.hasNext()) {
                    arrayList.add(((TextDiagnostic) it.next()).getName());
                }
                CollectionsKt.addAll(set2, arrayList);
            }
        }
        String strReplaceAll = matcher.replaceAll("");
        Intrinsics.checkNotNullExpressionValue(strReplaceAll, "replaceAll(...)");
        return strReplaceAll;
    }

    private final boolean hasExplicitDefinitionOnlyOption(AbstractTestDiagnostic diagnostic) {
        if (!(diagnostic instanceof ActualDiagnostic)) {
            return false;
        }
        Object factory = ((ActualDiagnostic) diagnostic).getDiagnostic().getFactory();
        return (factory instanceof DebugInfoDiagnosticFactory) && ((DebugInfoDiagnosticFactory) factory).getWithExplicitDefinitionOnly();
    }

    public final StringBuffer addDiagnosticMarkersToText(PsiFile psiFile, Collection<ActualDiagnostic> diagnostics) {
        Intrinsics.checkNotNullParameter(psiFile, "psiFile");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        return addDiagnosticMarkersToText(psiFile, diagnostics, MapsKt.emptyMap(), new Function1<PsiFile, String>() { // from class: org.jetbrains.kotlin.checkers.utils.CheckerTestUtil.addDiagnosticMarkersToText.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(PsiFile it) {
                Intrinsics.checkNotNullParameter(it, "it");
                String text = it.getText();
                Intrinsics.checkNotNullExpressionValue(text, "getText(...)");
                return text;
            }
        }, CollectionsKt.emptyList(), false, false);
    }

    public final StringBuffer addDiagnosticMarkersToText(PsiFile psiFile, Collection<ActualDiagnostic> diagnostics, Map<AbstractTestDiagnostic, TextDiagnostic> diagnosticToExpectedDiagnostic, Function1<? super PsiFile, String> getFileText, Collection<PositionalTextDiagnostic> uncheckedDiagnostics, boolean withNewInferenceDirective, boolean renderDiagnosticMessages) {
        Intrinsics.checkNotNullParameter(psiFile, "psiFile");
        Intrinsics.checkNotNullParameter(diagnostics, "diagnostics");
        Intrinsics.checkNotNullParameter(diagnosticToExpectedDiagnostic, "diagnosticToExpectedDiagnostic");
        Intrinsics.checkNotNullParameter(getFileText, "getFileText");
        Intrinsics.checkNotNullParameter(uncheckedDiagnostics, "uncheckedDiagnostics");
        String strInvoke = getFileText.invoke(psiFile);
        StringBuffer stringBuffer = new StringBuffer();
        ArrayList arrayList = new ArrayList();
        for (Object obj : diagnostics) {
            if (Intrinsics.areEqual(psiFile, ((ActualDiagnostic) obj).getFile())) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        if (arrayList2.isEmpty() && uncheckedDiagnostics.isEmpty()) {
            stringBuffer.append(strInvoke);
            return stringBuffer;
        }
        List<AbstractDiagnosticDescriptor> sortedDiagnosticDescriptors = getSortedDiagnosticDescriptors(arrayList2, uncheckedDiagnostics);
        if (sortedDiagnosticDescriptors.isEmpty()) {
            return stringBuffer;
        }
        Stack stack = new Stack();
        ListIterator<AbstractDiagnosticDescriptor> listIterator = sortedDiagnosticDescriptors.listIterator();
        AbstractDiagnosticDescriptor next = listIterator.next();
        int length = strInvoke.length();
        AbstractDiagnosticDescriptor next2 = next;
        for (int i = 0; i < length; i++) {
            char cCharAt = strInvoke.charAt(i);
            while (!stack.isEmpty() && i == ((AbstractDiagnosticDescriptor) stack.peek()).getEnd()) {
                closeDiagnosticString(stringBuffer);
                stack.pop();
            }
            while (next2 != null && i == next2.getStart()) {
                boolean zOpenDiagnosticsString = openDiagnosticsString(stringBuffer, next2, diagnosticToExpectedDiagnostic, withNewInferenceDirective, renderDiagnosticMessages);
                if (next2.getEnd() == i && !zOpenDiagnosticsString) {
                    closeDiagnosticString(stringBuffer);
                } else if (!zOpenDiagnosticsString) {
                    stack.push(next2);
                }
                next2 = listIterator.hasNext() ? listIterator.next() : null;
            }
            stringBuffer.append(cCharAt);
        }
        if (next2 != null) {
            next2.getStart();
            strInvoke.length();
            next2.getEnd();
            strInvoke.length();
            if (!openDiagnosticsString(stringBuffer, next2, diagnosticToExpectedDiagnostic, withNewInferenceDirective, renderDiagnosticMessages)) {
                stack.push(next2);
            }
        }
        while (!stack.isEmpty() && strInvoke.length() == ((AbstractDiagnosticDescriptor) stack.peek()).getEnd()) {
            closeDiagnosticString(stringBuffer);
            stack.pop();
        }
        stack.isEmpty();
        return stringBuffer;
    }

    /* JADX WARN: Code duplicated, block: B:22:0x0064  */
    private final boolean openDiagnosticsString(StringBuffer result, AbstractDiagnosticDescriptor currentDescriptor, Map<AbstractTestDiagnostic, TextDiagnostic> diagnosticToExpectedDiagnostic, boolean withNewInferenceDirective, boolean renderDiagnosticMessages) {
        boolean z;
        ArrayList arrayList = new ArrayList();
        if (currentDescriptor instanceof TextDiagnosticDescriptor) {
            arrayList.add(TextDiagnostic.asString$default(((TextDiagnosticDescriptor) currentDescriptor).getTextDiagnostic(), false, false, 3, null));
        } else if (currentDescriptor instanceof ActualDiagnosticDescriptor) {
            for (AbstractTestDiagnostic abstractTestDiagnostic : ((ActualDiagnosticDescriptor) currentDescriptor).getDiagnostics()) {
                TextDiagnostic textDiagnostic = diagnosticToExpectedDiagnostic.get(abstractTestDiagnostic);
                TextDiagnostic textDiagnosticAsTextDiagnostic = TextDiagnostic.INSTANCE.asTextDiagnostic(abstractTestDiagnostic);
                if (textDiagnostic != null || !hasExplicitDefinitionOnlyOption(abstractTestDiagnostic)) {
                    if (renderDiagnosticMessages) {
                        z = true;
                    } else if ((textDiagnostic != null ? textDiagnostic.getParameters() : null) != null) {
                        z = true;
                    } else {
                        z = false;
                    }
                    arrayList.add(textDiagnosticAsTextDiagnostic.asString(withNewInferenceDirective, z));
                }
            }
        } else {
            throw new IllegalStateException("Unknown diagnostic descriptor: " + currentDescriptor);
        }
        if (arrayList.size() == 0) {
            return true;
        }
        CollectionsKt.sort(arrayList);
        result.append("<!" + CollectionsKt.joinToString$default(arrayList, ", ", null, null, 0, null, null, 62, null) + "!>");
        return false;
    }

    private final StringBuffer closeDiagnosticString(StringBuffer result) {
        return result.append("<!>");
    }

    private final List<ActualDiagnosticDescriptor> getActualSortedDiagnosticDescriptors(Collection<ActualDiagnostic> diagnostics) {
        return CollectionsKt.filterIsInstance(getSortedDiagnosticDescriptors(diagnostics, CollectionsKt.emptyList()), ActualDiagnosticDescriptor.class);
    }

    private final List<AbstractDiagnosticDescriptor> getSortedDiagnosticDescriptors(Collection<ActualDiagnostic> diagnostics, Collection<PositionalTextDiagnostic> uncheckedDiagnostics) {
        ArrayList arrayList = new ArrayList();
        for (Object obj : diagnostics) {
            if (((ActualDiagnostic) obj).getDiagnostic().getIsValid()) {
                arrayList.add(obj);
            }
        }
        List<AbstractDiagnosticDescriptor> listGroupDiagnosticsByTextRange = groupDiagnosticsByTextRange(arrayList, uncheckedDiagnostics);
        CollectionsKt.sortWith(listGroupDiagnosticsByTextRange, new Comparator() { // from class: org.jetbrains.kotlin.checkers.utils.CheckerTestUtil$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj2, Object obj3) {
                return CheckerTestUtil.getSortedDiagnosticDescriptors$lambda$13((AbstractDiagnosticDescriptor) obj2, (AbstractDiagnosticDescriptor) obj3);
            }
        });
        return listGroupDiagnosticsByTextRange;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int getSortedDiagnosticDescriptors$lambda$13(AbstractDiagnosticDescriptor d1, AbstractDiagnosticDescriptor d2) {
        Intrinsics.checkNotNullParameter(d1, "d1");
        Intrinsics.checkNotNullParameter(d2, "d2");
        return d1.getStart() != d2.getStart() ? d1.getStart() - d2.getStart() : d2.getEnd() - d1.getEnd();
    }

    /* JADX WARN: Code duplicated, block: B:31:0x00c5  */
    /* JADX WARN: Code duplicated, block: B:42:0x00cf A[SYNTHETIC] */
    private final List<AbstractDiagnosticDescriptor> groupDiagnosticsByTextRange(Collection<ActualDiagnostic> diagnostics, Collection<PositionalTextDiagnostic> uncheckedDiagnostics) {
        LinkedListMultimap linkedListMultimapCreate = LinkedListMultimap.create();
        for (ActualDiagnostic actualDiagnostic : diagnostics) {
            Iterator<TextRange> it = actualDiagnostic.getDiagnostic().getTextRanges().iterator();
            while (it.hasNext()) {
                linkedListMultimapCreate.put(it.next(), actualDiagnostic);
            }
        }
        for (PositionalTextDiagnostic positionalTextDiagnostic : uncheckedDiagnostics) {
            linkedListMultimapCreate.put(new TextRange(positionalTextDiagnostic.getStart(), positionalTextDiagnostic.getEnd()), positionalTextDiagnostic.getDiagnostic());
        }
        Set setKeySet = linkedListMultimapCreate.keySet();
        Intrinsics.checkNotNullExpressionValue(setKeySet, "keySet(...)");
        Set<TextRange> set = setKeySet;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(set, 10));
        for (TextRange textRange : set) {
            List list = linkedListMultimapCreate.get(textRange);
            Intrinsics.checkNotNull(list);
            List list2 = list;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator it2 = list2.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (((AbstractTestDiagnostic) it2.next()).getInferenceCompatibility() != TextDiagnostic.InferenceCompatibility.ALL) {
                            if (list.size() > 1) {
                                CollectionsKt.sortWith(list, new Comparator() { // from class: org.jetbrains.kotlin.checkers.utils.CheckerTestUtil$groupDiagnosticsByTextRange$lambda$17$$inlined$sortBy$1
                                    @Override // java.util.Comparator
                                    public final int compare(T t, T t2) {
                                        return ComparisonsKt.compareValues(((AbstractTestDiagnostic) t).getName(), ((AbstractTestDiagnostic) t2).getName());
                                    }
                                });
                            }
                        }
                    } else if (list.size() > 1) {
                        CollectionsKt.sortWith(list, new Comparator() { // from class: org.jetbrains.kotlin.checkers.utils.CheckerTestUtil$groupDiagnosticsByTextRange$lambda$17$$inlined$sortBy$2
                            @Override // java.util.Comparator
                            public final int compare(T t, T t2) {
                                return ComparisonsKt.compareValues((AbstractTestDiagnostic) t, (AbstractTestDiagnostic) t2);
                            }
                        });
                    }
                }
            } else if (list.size() > 1) {
                CollectionsKt.sortWith(list, new Comparator() { // from class: org.jetbrains.kotlin.checkers.utils.CheckerTestUtil$groupDiagnosticsByTextRange$lambda$17$$inlined$sortBy$2
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt.compareValues((AbstractTestDiagnostic) t, (AbstractTestDiagnostic) t2);
                    }
                });
            }
            arrayList.add(new ActualDiagnosticDescriptor(textRange.getStartOffset(), textRange.getEndOffset(), list));
        }
        return CollectionsKt.toMutableList((Collection) arrayList);
    }

    public final Pair<KotlinType, Set<KotlinType>> getTypeInfo(PsiElement expression, BindingContext bindingContext, DataFlowValueFactory dataFlowValueFactory, LanguageVersionSettings languageVersionSettings, ModuleDescriptorImpl moduleDescriptor) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        if (expression instanceof KtCallableDeclaration) {
            Object obj = bindingContext.get(BindingContext.DECLARATION_TO_DESCRIPTOR, expression);
            CallableDescriptor callableDescriptor = obj instanceof CallableDescriptor ? (CallableDescriptor) obj : null;
            if (callableDescriptor != null) {
                return new Pair<>(callableDescriptor.getReturnType(), null);
            }
        }
        KtExpression ktExpression = (KtExpression) expression;
        KotlinTypeInfo kotlinTypeInfoNoTypeInfo = (KotlinTypeInfo) bindingContext.get(BindingContext.EXPRESSION_TYPE_INFO, ktExpression);
        if (kotlinTypeInfoNoTypeInfo == null) {
            kotlinTypeInfoNoTypeInfo = TypeInfoFactoryKt.noTypeInfo(DataFlowInfo.INSTANCE.getEMPTY());
        }
        Intrinsics.checkNotNull(kotlinTypeInfoNoTypeInfo);
        KotlinType type = CallUtilKt.getType(ktExpression, bindingContext);
        if (type == null) {
            return new Pair<>(null, null);
        }
        if (dataFlowValueFactory == null || moduleDescriptor == null) {
            return new Pair<>(type, null);
        }
        DataFlowValue dataFlowValueCreateDataFlowValue = dataFlowValueFactory.createDataFlowValue(ktExpression, type, bindingContext, moduleDescriptor);
        DataFlowInfo dataFlowInfo = kotlinTypeInfoNoTypeInfo.getDataFlowInfo();
        Intrinsics.checkNotNull(languageVersionSettings);
        Set<KotlinType> stableTypes = dataFlowInfo.getStableTypes(dataFlowValueCreateDataFlowValue, languageVersionSettings);
        Set<KotlinType> set = stableTypes;
        if (set != null && !set.isEmpty()) {
            return new Pair<>(type, stableTypes);
        }
        if (((ExplicitSmartCasts) bindingContext.get(BindingContext.SMARTCAST, expression)) != null && (expression instanceof KtReferenceExpression)) {
            Object obj2 = bindingContext.get(BindingContext.REFERENCE_TARGET, expression);
            CallableDescriptor callableDescriptor2 = obj2 instanceof CallableDescriptor ? (CallableDescriptor) obj2 : null;
            KotlinType returnType = callableDescriptor2 != null ? callableDescriptor2.getReturnType() : null;
            if (returnType != null) {
                return new Pair<>(type, SetsKt.setOf(returnType));
            }
        }
        return new Pair<>(type, null);
    }

    public final Pair<FqNameUnsafe, String> getCallDebugInfo(PsiElement element, BindingContext bindingContext) {
        CallableDescriptor callableDescriptor;
        Intrinsics.checkNotNullParameter(element, "element");
        Intrinsics.checkNotNullParameter(bindingContext, "bindingContext");
        FqNameUnsafe fqNameUnsafe = null;
        if (!(element instanceof KtExpression)) {
            return TuplesKt.to(null, TypeOfCall.OTHER.getNameToRender());
        }
        Call call = CallUtilKt.getCall((KtElement) element, bindingContext);
        String typeOfCall = getTypeOfCall((KtExpression) element, bindingContext);
        ResolvedCall resolvedCall = (ResolvedCall) bindingContext.get(BindingContext.RESOLVED_CALL, call);
        if (resolvedCall != null && (callableDescriptor = resolvedCall.get$suspendPropertyDescriptor()) != null) {
            fqNameUnsafe = DescriptorUtilsKt.getFqNameUnsafe(callableDescriptor);
        }
        return TuplesKt.to(fqNameUnsafe, typeOfCall);
    }

    private final String getTypeOfCall(KtExpression expression, BindingContext bindingContext) {
        ResolvedCall<? extends CallableDescriptor> resolvedCall = CallUtilKt.getResolvedCall(expression, bindingContext);
        if (resolvedCall == null) {
            return TypeOfCall.UNRESOLVED.getNameToRender();
        }
        if (resolvedCall instanceof VariableAsFunctionResolvedCall) {
            return TypeOfCall.VARIABLE_THROUGH_INVOKE.getNameToRender();
        }
        CallableDescriptor callableDescriptor = resolvedCall.get$suspendPropertyDescriptor();
        if (callableDescriptor instanceof PropertyDescriptor) {
            return TypeOfCall.PROPERTY_GETTER.getNameToRender();
        }
        if (callableDescriptor instanceof FunctionDescriptor) {
            StringBuilder sb = new StringBuilder();
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) callableDescriptor;
            if (functionDescriptor.isInline()) {
                sb.append("inline ");
            }
            if (functionDescriptor.isInfix()) {
                sb.append("infix ");
            }
            if (functionDescriptor.isOperator()) {
                sb.append("operator ");
            }
            if (DescriptorUtilsKt.isExtension(callableDescriptor)) {
                sb.append("extension ");
            }
            sb.append(TypeOfCall.FUNCTION.getNameToRender());
            String string = sb.toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }
        return TypeOfCall.OTHER.getNameToRender();
    }
}
