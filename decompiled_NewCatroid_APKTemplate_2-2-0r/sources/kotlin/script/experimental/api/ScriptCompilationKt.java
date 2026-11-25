package kotlin.script.experimental.api;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Unit;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.util.PropertiesCollection;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptCompilation.kt */
/* JADX INFO: loaded from: classes12.dex */
@Metadata(d1 = {"\u0000¬\u0001\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a$\u0010P\u001a\b\u0012\u0004\u0012\u00020R0Q*\u00020R2\u0006\u0010S\u001a\u00020(2\n\b\u0002\u0010T\u001a\u0004\u0018\u00010U\u001a$\u0010V\u001a\b\u0012\u0004\u0012\u00020R0Q*\u00020R2\u0006\u0010S\u001a\u00020(2\n\b\u0002\u0010T\u001a\u0004\u0018\u00010U\u001a \u0010W\u001a\b\u0012\u0004\u0012\u00020R0Q*\u00020R2\u0006\u0010S\u001a\u00020(2\u0006\u0010T\u001a\u00020U\u001a_\u0010X\u001a\b\u0012\u0004\u0012\u0002HY0Q\"\b\b\u0000\u0010Y*\u00020Z\"\u0004\b\u0001\u0010[*\u0002HY2\u0012\u0010\\\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H[0\t0\u00012\u001e\u0010]\u001a\u001a\u0012\u0004\u0012\u0002HY\u0012\u0004\u0012\u0002H[\u0012\n\u0012\b\u0012\u0004\u0012\u0002HY0Q0^H\u0080\bø\u0001\u0000¢\u0006\u0002\u0010_\u001a%\u0010`\u001a\u00020R*\u0004\u0018\u00010R2\u0017\u0010a\u001a\u0013\u0012\u0004\u0012\u000204\u0012\u0004\u0012\u00020c0b¢\u0006\u0002\bd\"%\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0006\u0010\u0007\u001a\u0004\b\u0004\u0010\u0005\"+\u0010\b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\u0007\u001a\u0004\b\u000b\u0010\u0005\"%\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u000f\u0010\u0007\u001a\u0004\b\u000e\u0010\u0005\"+\u0010\u0010\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\n0\t0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0007\u001a\u0004\b\u0011\u0010\u0005\"+\u0010\u0013\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\t0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0007\u001a\u0004\b\u0015\u0010\u0005\"%\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\n0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0019\u0010\u0007\u001a\u0004\b\u0018\u0010\u0005\"%\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\n0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001c\u0010\u0007\u001a\u0004\b\u001b\u0010\u0005\"%\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\n0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u0007\u001a\u0004\b\u001e\u0010\u0005\"%\u0010 \u001a\b\u0012\u0004\u0012\u00020!0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b#\u0010\u0007\u001a\u0004\b\"\u0010\u0005\"+\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00020\t0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b&\u0010\u0007\u001a\u0004\b%\u0010\u0005\"+\u0010'\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020(0\t0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b*\u0010\u0007\u001a\u0004\b)\u0010\u0005\"%\u0010+\u001a\b\u0012\u0004\u0012\u00020,0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b-\u0010\u0007\u001a\u0004\b+\u0010\u0005\"1\u0010.\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00020/0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b1\u0010\u0007\u001a\u0004\b0\u0010\u0005\"\u0015\u00102\u001a\u000203*\u0002048F¢\u0006\u0006\u001a\u0004\b5\u00106\"+\u00107\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002080\t0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b:\u0010\u0007\u001a\u0004\b9\u0010\u0005\"+\u0010;\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002080\t0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b=\u0010\u0007\u001a\u0004\b<\u0010\u0005\"+\u0010>\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020?0\t0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bA\u0010\u0007\u001a\u0004\b@\u0010\u0005\"%\u0010B\u001a\b\u0012\u0004\u0012\u00020\n0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bD\u0010\u0007\u001a\u0004\bC\u0010\u0005\"%\u0010E\u001a\b\u0012\u0004\u0012\u00020F0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bH\u0010\u0007\u001a\u0004\bG\u0010\u0005\"%\u0010I\u001a\b\u0012\u0004\u0012\u00020\n0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bK\u0010\u0007\u001a\u0004\bJ\u0010\u0005\"+\u0010L\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020M0\t0\u0001*\u00020\u00038FX\u0086\u0084\u0002¢\u0006\f\n\u0004\bO\u0010\u0007\u001a\u0004\bN\u0010\u0005*.\u0010e\"\u0014\u0012\u0004\u0012\u00020f\u0012\n\u0012\b\u0012\u0004\u0012\u00020R0Q0b2\u0014\u0012\u0004\u0012\u00020f\u0012\n\u0012\b\u0012\u0004\u0012\u00020R0Q0b*\"\u0010g\"\u000e\u0012\u0004\u0012\u00020f\u0012\u0004\u0012\u00020R0b2\u000e\u0012\u0004\u0012\u00020f\u0012\u0004\u0012\u00020R0b\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006h"}, d2 = {"baseClass", "Lkotlin/script/experimental/util/PropertiesCollection$Key;", "Lkotlin/script/experimental/api/KotlinType;", "Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;", "getBaseClass", "(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;", "baseClass$delegate", "Lkotlin/script/experimental/util/PropertiesCollection$PropertyKeyDelegate;", "compilerOptions", "", "", "getCompilerOptions", "compilerOptions$delegate", "defaultIdentifier", "getDefaultIdentifier", "defaultIdentifier$delegate", "defaultImports", "getDefaultImports", "defaultImports$delegate", "dependencies", "Lkotlin/script/experimental/api/ScriptDependency;", "getDependencies", "dependencies$delegate", "displayName", "getDisplayName", "displayName$delegate", "fileExtension", "getFileExtension", "fileExtension$delegate", "filePathPattern", "getFilePathPattern", "filePathPattern$delegate", "hostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "getHostConfiguration", "hostConfiguration$delegate", "implicitReceivers", "getImplicitReceivers", "implicitReceivers$delegate", "importScripts", "Lkotlin/script/experimental/api/SourceCode;", "getImportScripts", "importScripts$delegate", "isStandalone", "", "isStandalone$delegate", "providedProperties", "", "getProvidedProperties", "providedProperties$delegate", "refineConfiguration", "Lkotlin/script/experimental/api/RefineConfigurationBuilder;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration$Builder;", "getRefineConfiguration", "(Lkotlin/script/experimental/api/ScriptCompilationConfiguration$Builder;)Lkotlin/script/experimental/api/RefineConfigurationBuilder;", "refineConfigurationBeforeCompiling", "Lkotlin/script/experimental/api/RefineConfigurationUnconditionallyData;", "getRefineConfigurationBeforeCompiling", "refineConfigurationBeforeCompiling$delegate", "refineConfigurationBeforeParsing", "getRefineConfigurationBeforeParsing", "refineConfigurationBeforeParsing$delegate", "refineConfigurationOnAnnotations", "Lkotlin/script/experimental/api/RefineConfigurationOnAnnotationsData;", "getRefineConfigurationOnAnnotations", "refineConfigurationOnAnnotations$delegate", "resultField", "getResultField", "resultField$delegate", "scriptFileLocation", "Ljava/io/File;", "getScriptFileLocation", "scriptFileLocation$delegate", "scriptFileLocationVariable", "getScriptFileLocationVariable", "scriptFileLocationVariable$delegate", "sourceFragments", "Lkotlin/script/experimental/api/ScriptSourceNamedFragment;", "getSourceFragments", "sourceFragments$delegate", "refineBeforeCompiling", "Lkotlin/script/experimental/api/ResultWithDiagnostics;", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "script", "collectedData", "Lkotlin/script/experimental/api/ScriptCollectedData;", "refineBeforeParsing", "refineOnAnnotations", "simpleRefineImpl", "Configuration", "Lkotlin/script/experimental/util/PropertiesCollection;", "RefineData", ASTExpr.DEFAULT_MAP_KEY_NAME, "refineFn", "Lkotlin/Function2;", "(Lkotlin/script/experimental/util/PropertiesCollection;Lkotlin/script/experimental/util/PropertiesCollection$Key;Lkotlin/jvm/functions/Function2;)Lkotlin/script/experimental/api/ResultWithDiagnostics;", "with", "body", "Lkotlin/Function1;", "", "Lkotlin/ExtensionFunctionType;", "RefineScriptCompilationConfigurationHandler", "Lkotlin/script/experimental/api/ScriptConfigurationRefinementContext;", "SimpleRefineScriptCompilationConfigurationHandler", PathUtil.KOTLIN_SCRIPTING_COMMON_NAME}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class ScriptCompilationKt {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "displayName", "getDisplayName(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "defaultIdentifier", "getDefaultIdentifier(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "fileExtension", "getFileExtension(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "filePathPattern", "getFilePathPattern(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "baseClass", "getBaseClass(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "implicitReceivers", "getImplicitReceivers(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "providedProperties", "getProvidedProperties(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "scriptFileLocationVariable", "getScriptFileLocationVariable(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "scriptFileLocation", "getScriptFileLocation(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "defaultImports", "getDefaultImports(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "importScripts", "getImportScripts(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "resultField", "getResultField(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "dependencies", "getDependencies(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "compilerOptions", "getCompilerOptions(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "refineConfigurationBeforeParsing", "getRefineConfigurationBeforeParsing(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "refineConfigurationOnAnnotations", "getRefineConfigurationOnAnnotations(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "refineConfigurationBeforeCompiling", "getRefineConfigurationBeforeCompiling(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "sourceFragments", "getSourceFragments(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "hostConfiguration", "getHostConfiguration(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinPackage(ScriptCompilationKt.class, PathUtil.KOTLIN_SCRIPTING_COMMON_NAME), "isStandalone", "isStandalone(Lkotlin/script/experimental/api/ScriptCompilationConfigurationKeys;)Lkotlin/script/experimental/util/PropertiesCollection$Key;"))};
    private static final PropertiesCollection.PropertyKeyDelegate displayName$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate defaultIdentifier$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) "Script", false, 2, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate fileExtension$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) "kts", false, 2, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate filePathPattern$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate baseClass$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) new KotlinType(Reflection.getOrCreateKotlinClass(Object.class), false, 2, (DefaultConstructorMarker) null), false, 2, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate implicitReceivers$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate providedProperties$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate scriptFileLocationVariable$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate scriptFileLocation$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate defaultImports$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate importScripts$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate resultField$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) "$$result", false, 2, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate dependencies$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate compilerOptions$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate refineConfigurationBeforeParsing$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate refineConfigurationOnAnnotations$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate refineConfigurationBeforeCompiling$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate sourceFragments$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, false, 3, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate hostConfiguration$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) null, true, 1, (Object) null);
    private static final PropertiesCollection.PropertyKeyDelegate isStandalone$delegate = PropertiesCollection.Companion.key$default(PropertiesCollection.INSTANCE, (Object) true, false, 2, (Object) null);

    public static final ScriptCompilationConfiguration with(ScriptCompilationConfiguration scriptCompilationConfiguration, Function1<? super ScriptCompilationConfiguration.Builder, Unit> body) {
        ScriptCompilationConfiguration scriptCompilationConfiguration2;
        Intrinsics.checkNotNullParameter(body, "body");
        if (scriptCompilationConfiguration == null) {
            scriptCompilationConfiguration2 = new ScriptCompilationConfiguration(body);
        } else {
            scriptCompilationConfiguration2 = new ScriptCompilationConfiguration(new ScriptCompilationConfiguration[]{scriptCompilationConfiguration}, body);
        }
        return Intrinsics.areEqual(scriptCompilationConfiguration2, scriptCompilationConfiguration) ? scriptCompilationConfiguration : scriptCompilationConfiguration2;
    }

    public static final PropertiesCollection.Key<String> getDisplayName(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return displayName$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[0]);
    }

    public static final PropertiesCollection.Key<String> getDefaultIdentifier(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return defaultIdentifier$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[1]);
    }

    public static final PropertiesCollection.Key<String> getFileExtension(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return fileExtension$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[2]);
    }

    public static final PropertiesCollection.Key<String> getFilePathPattern(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return filePathPattern$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[3]);
    }

    public static final PropertiesCollection.Key<KotlinType> getBaseClass(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return baseClass$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[4]);
    }

    public static final PropertiesCollection.Key<List<KotlinType>> getImplicitReceivers(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return implicitReceivers$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[5]);
    }

    public static final PropertiesCollection.Key<Map<String, KotlinType>> getProvidedProperties(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return providedProperties$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[6]);
    }

    public static final PropertiesCollection.Key<String> getScriptFileLocationVariable(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return scriptFileLocationVariable$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[7]);
    }

    public static final PropertiesCollection.Key<File> getScriptFileLocation(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return scriptFileLocation$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[8]);
    }

    public static final PropertiesCollection.Key<List<String>> getDefaultImports(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return defaultImports$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[9]);
    }

    public static final PropertiesCollection.Key<List<SourceCode>> getImportScripts(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return importScripts$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[10]);
    }

    public static final PropertiesCollection.Key<String> getResultField(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return resultField$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[11]);
    }

    public static final PropertiesCollection.Key<List<ScriptDependency>> getDependencies(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return dependencies$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[12]);
    }

    public static final PropertiesCollection.Key<List<String>> getCompilerOptions(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return compilerOptions$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[13]);
    }

    public static final PropertiesCollection.Key<List<RefineConfigurationUnconditionallyData>> getRefineConfigurationBeforeParsing(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return refineConfigurationBeforeParsing$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[14]);
    }

    public static final PropertiesCollection.Key<List<RefineConfigurationOnAnnotationsData>> getRefineConfigurationOnAnnotations(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return refineConfigurationOnAnnotations$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[15]);
    }

    public static final PropertiesCollection.Key<List<RefineConfigurationUnconditionallyData>> getRefineConfigurationBeforeCompiling(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return refineConfigurationBeforeCompiling$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[16]);
    }

    public static final PropertiesCollection.Key<List<ScriptSourceNamedFragment>> getSourceFragments(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return sourceFragments$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[17]);
    }

    public static final PropertiesCollection.Key<ScriptingHostConfiguration> getHostConfiguration(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return hostConfiguration$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[18]);
    }

    public static final PropertiesCollection.Key<Boolean> isStandalone(ScriptCompilationConfigurationKeys scriptCompilationConfigurationKeys) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfigurationKeys, "<this>");
        return isStandalone$delegate.getValue(scriptCompilationConfigurationKeys, $$delegatedProperties[19]);
    }

    public static final RefineConfigurationBuilder getRefineConfiguration(ScriptCompilationConfiguration.Builder builder) {
        Intrinsics.checkNotNullParameter(builder, "<this>");
        return new RefineConfigurationBuilder();
    }

    public static /* synthetic */ ResultWithDiagnostics refineBeforeParsing$default(ScriptCompilationConfiguration scriptCompilationConfiguration, SourceCode sourceCode, ScriptCollectedData scriptCollectedData, int i, Object obj) {
        if ((i & 2) != 0) {
            scriptCollectedData = null;
        }
        return refineBeforeParsing(scriptCompilationConfiguration, sourceCode, scriptCollectedData);
    }

    public static final ResultWithDiagnostics<ScriptCompilationConfiguration> refineBeforeParsing(ScriptCompilationConfiguration scriptCompilationConfiguration, SourceCode script, ScriptCollectedData scriptCollectedData) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(script, "script");
        ScriptCompilationConfiguration scriptCompilationConfiguration2 = scriptCompilationConfiguration;
        List list = (List) scriptCompilationConfiguration2.get(getRefineConfigurationBeforeParsing(ScriptCompilationConfiguration.INSTANCE));
        if (list != null) {
            Iterator it = list.iterator();
            PropertiesCollection propertiesCollection = scriptCompilationConfiguration2;
            while (it.hasNext()) {
                ResultWithDiagnostics<ScriptCompilationConfiguration> resultWithDiagnosticsInvoke = ((RefineConfigurationUnconditionallyData) it.next()).getHandler().invoke(new ScriptConfigurationRefinementContext(script, (ScriptCompilationConfiguration) propertiesCollection, scriptCollectedData));
                if (!(resultWithDiagnosticsInvoke instanceof ResultWithDiagnostics.Success)) {
                    if (resultWithDiagnosticsInvoke instanceof ResultWithDiagnostics.Failure) {
                        return (ResultWithDiagnostics.Failure) resultWithDiagnosticsInvoke;
                    }
                    throw new NoWhenBranchMatchedException();
                }
                propertiesCollection = (PropertiesCollection) ((ResultWithDiagnostics.Success) resultWithDiagnosticsInvoke).getValue();
            }
            if (propertiesCollection != null) {
                scriptCompilationConfiguration2 = propertiesCollection;
            }
        }
        return ErrorHandlingKt.asSuccess$default(scriptCompilationConfiguration2, null, 1, null);
    }

    public static final ResultWithDiagnostics<ScriptCompilationConfiguration> refineOnAnnotations(ScriptCompilationConfiguration scriptCompilationConfiguration, SourceCode script, ScriptCollectedData collectedData) {
        HashSet hashSet;
        ResultWithDiagnostics<ScriptCompilationConfiguration> resultWithDiagnosticsInvoke;
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(collectedData, "collectedData");
        List list = (List) collectedData.get(ScriptDataKt.getFoundAnnotations(ScriptCollectedData.INSTANCE));
        if (list != null) {
            HashSet hashSet2 = new HashSet();
            Iterator it = list.iterator();
            while (it.hasNext()) {
                hashSet2.add(JvmClassMappingKt.getJavaClass(JvmClassMappingKt.getAnnotationClass((Annotation) it.next())).getName());
            }
            hashSet = hashSet2;
        } else {
            hashSet = null;
        }
        HashSet hashSet3 = hashSet;
        if (hashSet3 == null || hashSet3.isEmpty()) {
            return ErrorHandlingKt.asSuccess$default(scriptCompilationConfiguration, null, 1, null);
        }
        ResultWithDiagnostics.Success successAsSuccess$default = ErrorHandlingKt.asSuccess$default(scriptCompilationConfiguration, null, 1, null);
        List<RefineConfigurationOnAnnotationsData> list2 = (List) scriptCompilationConfiguration.get(getRefineConfigurationOnAnnotations(ScriptCompilationConfiguration.INSTANCE));
        if (list2 == null) {
            return successAsSuccess$default;
        }
        ResultWithDiagnostics<ScriptCompilationConfiguration> resultWithDiagnosticsPlus = successAsSuccess$default;
        for (RefineConfigurationOnAnnotationsData refineConfigurationOnAnnotationsData : list2) {
            List<KotlinType> listComponent1 = refineConfigurationOnAnnotationsData.component1();
            Function1<ScriptConfigurationRefinementContext, ResultWithDiagnostics<ScriptCompilationConfiguration>> function1Component2 = refineConfigurationOnAnnotationsData.component2();
            if (resultWithDiagnosticsPlus instanceof ResultWithDiagnostics.Success) {
                List<ScriptDiagnostic> reports = resultWithDiagnosticsPlus.getReports();
                ScriptCompilationConfiguration scriptCompilationConfiguration2 = (ScriptCompilationConfiguration) ((ResultWithDiagnostics.Success) resultWithDiagnosticsPlus).getValue();
                List<KotlinType> list3 = listComponent1;
                if ((list3 instanceof Collection) && list3.isEmpty()) {
                    resultWithDiagnosticsInvoke = ErrorHandlingKt.asSuccess$default(scriptCompilationConfiguration2, null, 1, null);
                    break;
                }
                Iterator<T> it2 = list3.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        if (hashSet.contains(((KotlinType) it2.next()).getTypeName())) {
                            resultWithDiagnosticsInvoke = function1Component2.invoke(new ScriptConfigurationRefinementContext(script, scriptCompilationConfiguration2, collectedData));
                            break;
                        }
                    } else {
                        resultWithDiagnosticsInvoke = ErrorHandlingKt.asSuccess$default(scriptCompilationConfiguration2, null, 1, null);
                        break;
                    }
                }
                resultWithDiagnosticsPlus = ErrorHandlingKt.plus(reports, resultWithDiagnosticsInvoke);
            } else if (!(resultWithDiagnosticsPlus instanceof ResultWithDiagnostics.Failure)) {
                throw new NoWhenBranchMatchedException();
            }
        }
        return resultWithDiagnosticsPlus != null ? resultWithDiagnosticsPlus : successAsSuccess$default;
    }

    public static /* synthetic */ ResultWithDiagnostics refineBeforeCompiling$default(ScriptCompilationConfiguration scriptCompilationConfiguration, SourceCode sourceCode, ScriptCollectedData scriptCollectedData, int i, Object obj) {
        if ((i & 2) != 0) {
            scriptCollectedData = null;
        }
        return refineBeforeCompiling(scriptCompilationConfiguration, sourceCode, scriptCollectedData);
    }

    public static final ResultWithDiagnostics<ScriptCompilationConfiguration> refineBeforeCompiling(ScriptCompilationConfiguration scriptCompilationConfiguration, SourceCode script, ScriptCollectedData scriptCollectedData) {
        Intrinsics.checkNotNullParameter(scriptCompilationConfiguration, "<this>");
        Intrinsics.checkNotNullParameter(script, "script");
        ScriptCompilationConfiguration scriptCompilationConfiguration2 = scriptCompilationConfiguration;
        List list = (List) scriptCompilationConfiguration2.get(getRefineConfigurationBeforeCompiling(ScriptCompilationConfiguration.INSTANCE));
        if (list != null) {
            Iterator it = list.iterator();
            PropertiesCollection propertiesCollection = scriptCompilationConfiguration2;
            while (it.hasNext()) {
                ResultWithDiagnostics<ScriptCompilationConfiguration> resultWithDiagnosticsInvoke = ((RefineConfigurationUnconditionallyData) it.next()).getHandler().invoke(new ScriptConfigurationRefinementContext(script, (ScriptCompilationConfiguration) propertiesCollection, scriptCollectedData));
                if (!(resultWithDiagnosticsInvoke instanceof ResultWithDiagnostics.Success)) {
                    if (resultWithDiagnosticsInvoke instanceof ResultWithDiagnostics.Failure) {
                        return (ResultWithDiagnostics.Failure) resultWithDiagnosticsInvoke;
                    }
                    throw new NoWhenBranchMatchedException();
                }
                propertiesCollection = (PropertiesCollection) ((ResultWithDiagnostics.Success) resultWithDiagnosticsInvoke).getValue();
            }
            if (propertiesCollection != null) {
                scriptCompilationConfiguration2 = propertiesCollection;
            }
        }
        return ErrorHandlingKt.asSuccess$default(scriptCompilationConfiguration2, null, 1, null);
    }

    public static final <Configuration extends PropertiesCollection, RefineData> ResultWithDiagnostics<Configuration> simpleRefineImpl(Configuration configuration, PropertiesCollection.Key<List<RefineData>> key, Function2<? super Configuration, ? super RefineData, ? extends ResultWithDiagnostics<? extends Configuration>> refineFn) {
        Intrinsics.checkNotNullParameter(configuration, "<this>");
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(refineFn, "refineFn");
        List list = (List) configuration.get(key);
        if (list != null) {
            Iterator it = list.iterator();
            PropertiesCollection propertiesCollection = (Object) configuration;
            while (it.hasNext()) {
                ResultWithDiagnostics<? extends Configuration> resultWithDiagnosticsInvoke = refineFn.invoke(propertiesCollection, (Object) it.next());
                if (!(resultWithDiagnosticsInvoke instanceof ResultWithDiagnostics.Success)) {
                    if (resultWithDiagnosticsInvoke instanceof ResultWithDiagnostics.Failure) {
                        return (ResultWithDiagnostics.Failure) resultWithDiagnosticsInvoke;
                    }
                    throw new NoWhenBranchMatchedException();
                }
                propertiesCollection = (PropertiesCollection) ((ResultWithDiagnostics.Success) resultWithDiagnosticsInvoke).getValue();
            }
            if (propertiesCollection != null) {
                configuration = propertiesCollection;
            }
        }
        return ErrorHandlingKt.asSuccess$default(configuration, null, 1, null);
    }
}
