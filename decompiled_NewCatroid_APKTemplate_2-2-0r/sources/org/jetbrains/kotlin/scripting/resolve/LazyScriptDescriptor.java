package org.jetbrains.kotlin.scripting.resolve;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.collections.ArraysKt;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;
import kotlin.reflect.KType;
import kotlin.script.experimental.api.ReplDataKt;
import kotlin.script.experimental.api.ScriptCompilationConfiguration;
import kotlin.script.experimental.api.ScriptCompilationKt;
import kotlin.script.experimental.api.SourceCode;
import kotlin.script.experimental.host.GetScriptingClass;
import kotlin.script.experimental.host.HostConfigurationKt;
import kotlin.script.experimental.host.ScriptingHostConfiguration;
import kotlin.script.experimental.jvm.JvmScriptingHostConfigurationKt;
import kotlin.script.experimental.jvm.util.IdentifiersKt;
import kotlin.text.StringsKt;
import net.bytebuddy.description.method.ParameterDescription;
import org.eclipse.jdt.internal.compiler.classfmt.ExternalAnnotationProvider;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiFile;
import org.jetbrains.kotlin.com.intellij.psi.PsiManager;
import org.jetbrains.kotlin.com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptorVisitor;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.FindClassInModuleKt;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ScopesHolderForClass;
import org.jetbrains.kotlin.descriptors.ScriptDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.descriptors.annotations.FilteredAnnotations;
import org.jetbrains.kotlin.descriptors.impl.ClassConstructorDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.ValueParameterDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory1;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtBlockExpression;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtScriptInitializer;
import org.jetbrains.kotlin.resolve.AllUnderImportScope;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.lazy.LazyClassContext;
import org.jetbrains.kotlin.resolve.lazy.ResolveSession;
import org.jetbrains.kotlin.resolve.lazy.data.KtScriptInfo;
import org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeImpl;
import org.jetbrains.kotlin.resolve.scopes.LexicalScopeKind;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElementKt;
import org.jetbrains.kotlin.scripting.definitions.DefinitionsKt;
import org.jetbrains.kotlin.scripting.definitions.ScriptDefinition;
import org.jetbrains.kotlin.scripting.definitions.ScriptDependenciesProvider;
import org.jetbrains.kotlin.scripting.definitions.ScriptPriorities;
import org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor.ImportedScriptDescriptorsFinder;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeSubstitutor;
import org.jetbrains.kotlin.types.Variance;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.typeUtil.TypeUtilsKt;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: LazyScriptDescriptor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0080\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u0002:\u0002fgB%\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0006\u0010\t\u001a\u00020\n¢\u0006\u0002\u0010\u000bJ5\u00102\u001a\u0002H3\"\u0004\b\u0000\u00103\"\u0004\b\u0001\u001042\u0012\u00105\u001a\u000e\u0012\u0004\u0012\u0002H3\u0012\u0004\u0012\u0002H4062\u0006\u00107\u001a\u0002H4H\u0016¢\u0006\u0002\u00108J\u000e\u00109\u001a\b\u0012\u0004\u0012\u00020:0\"H\u0014J\u001e\u0010;\u001a\b\u0012\u0004\u0012\u00020=0<2\u0006\u0010>\u001a\u00020?2\u0006\u0010@\u001a\u00020AH\u0014J1\u0010B\u001a\u0004\u0018\u00010\u00142\n\u0010C\u001a\u0006\u0012\u0002\b\u00030D2\u0014\u0010E\u001a\u0010\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020H\u0018\u00010FH\u0000¢\u0006\u0002\bIJ-\u0010B\u001a\u0004\u0018\u00010\u00142\u0006\u0010J\u001a\u00020K2\u0014\u0010E\u001a\u0010\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020H\u0018\u00010FH\u0000¢\u0006\u0002\bIJ7\u0010B\u001a\u0004\u0018\u00010\u00142\b\u0010L\u001a\u0004\u0018\u00010M2\u0006\u0010N\u001a\u00020H2\u0014\u0010E\u001a\u0010\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020H\u0018\u00010FH\u0000¢\u0006\u0002\bIJ\n\u0010O\u001a\u0004\u0018\u00010PH\u0016J\u000e\u0010Q\u001a\b\u0012\u0004\u0012\u00020P0\"H\u0016J\u000e\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00140\"H\u0016J\u000e\u0010S\u001a\b\u0012\u0004\u0012\u00020P0\"H\u0016J\b\u0010T\u001a\u00020&H\u0014J\b\u0010U\u001a\u00020\u001aH\u0016J\n\u0010V\u001a\u0004\u0018\u00010\u000eH\u0016J\u000e\u0010W\u001a\b\u0012\u0004\u0012\u00020X0\"H\u0016J\u000e\u0010Y\u001a\b\u0012\u0004\u0012\u00020P0\"H\u0016J\u0012\u0010Z\u001a\u0006\u0012\u0002\b\u00030D2\u0006\u0010J\u001a\u00020[J\b\u0010\\\u001a\u000201H\u0016J\b\u0010]\u001a\u00020^H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J&\u0010_\u001a\u00020`2\u0014\u0010E\u001a\u0010\u0012\u0004\u0012\u00020G\u0012\u0004\u0012\u00020H\u0018\u00010F2\u0006\u0010a\u001a\u00020HH\u0002J\b\u0010b\u001a\u0004\u0018\u00010\bJ\u0010\u0010c\u001a\u00020\u00002\u0006\u0010d\u001a\u00020eH\u0016R\u0016\u0010\f\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u000e0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000f\u001a\u00020\u00108VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012R\u001c\u0010\u0013\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00140\rX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u000e\u0010\u0017\u001a\u00020\u0018X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0019\u001a\u00020\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001cR\u0014\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00100\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u001f0\r¢\u0006\b\n\u0000\u001a\u0004\b \u0010\u0016R\u001a\u0010!\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00140\"0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\u00020\nX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b#\u0010$R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020&0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010'\u001a\b\u0012\u0004\u0012\u00020(0\rX\u0080\u0004¢\u0006\b\n\u0000\u001a\u0004\b)\u0010\u0016R\u001a\u0010*\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020+0\"0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010,\u001a\b\u0012\u0004\u0012\u00020-0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010.\u001a\b\u0012\u0004\u0012\u00020/0\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u00100\u001a\u000201X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006h"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/LazyScriptDescriptor;", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassDescriptor;", "resolveSession", "Lorg/jetbrains/kotlin/resolve/lazy/ResolveSession;", "containingDeclaration", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "name", "Lorg/jetbrains/kotlin/name/Name;", "scriptInfo", "Lorg/jetbrains/kotlin/resolve/lazy/data/KtScriptInfo;", "(Lorg/jetbrains/kotlin/resolve/lazy/ResolveSession;Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/resolve/lazy/data/KtScriptInfo;)V", "_resultValue", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/scripting/resolve/ReplResultPropertyDescriptor;", "annotations", "Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "getAnnotations", "()Lorg/jetbrains/kotlin/descriptors/annotations/Annotations;", "baseClassDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getBaseClassDescriptor$kotlin_scripting_compiler_impl", "()Lkotlin/jvm/functions/Function0;", "isReplScript", "", "priority", "", "getResolveSession", "()Lorg/jetbrains/kotlin/resolve/lazy/ResolveSession;", "scriptClassAnnotations", "scriptCompilationConfiguration", "Lkotlin/script/experimental/api/ScriptCompilationConfiguration;", "getScriptCompilationConfiguration", "scriptImplicitReceivers", "", "getScriptInfo$kotlin_scripting_compiler_impl", "()Lorg/jetbrains/kotlin/resolve/lazy/data/KtScriptInfo;", "scriptOuterScope", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "scriptPrimaryConstructorWithParams", "Lorg/jetbrains/kotlin/scripting/resolve/LazyScriptDescriptor$ConstructorWithParams;", "getScriptPrimaryConstructorWithParams$kotlin_scripting_compiler_impl", "scriptProvidedProperties", "Lorg/jetbrains/kotlin/scripting/resolve/ScriptProvidedPropertyDescriptor;", "scriptingClassGetter", "Lkotlin/script/experimental/host/GetScriptingClass;", "scriptingHostConfiguration", "Lkotlin/script/experimental/host/ScriptingHostConfiguration;", "sourceElement", "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "accept", "R", "D", "visitor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;", "data", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptorVisitor;Ljava/lang/Object;)Ljava/lang/Object;", "computeSupertypes", "Lorg/jetbrains/kotlin/types/SimpleType;", "createScopesHolderForClass", "Lorg/jetbrains/kotlin/descriptors/ScopesHolderForClass;", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassMemberScope;", "c", "Lorg/jetbrains/kotlin/resolve/lazy/LazyClassContext;", "declarationProvider", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/ClassMemberDeclarationProvider;", "findTypeDescriptor", "kClass", "Lkotlin/reflect/KClass;", "errorDiagnostic", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticFactory1;", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "", "findTypeDescriptor$kotlin_scripting_compiler_impl", ModuleXmlParser.TYPE, "Lkotlin/reflect/KType;", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "typeName", "getEarlierScriptsConstructorParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getExplicitConstructorParameters", "getImplicitReceivers", "getImplicitReceiversParameters", "getOuterScope", "getPriority", "getResultValue", "getScriptProvidedProperties", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "getScriptProvidedPropertiesParameters", "getScriptingClass", "Lkotlin/script/experimental/api/KotlinType;", "getSource", "getUnsubstitutedPrimaryConstructor", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "reportErrorString1", "", ParameterDescription.NAME_PREFIX, "resultFieldName", "substitute", "substitutor", "Lorg/jetbrains/kotlin/types/TypeSubstitutor;", "ConstructorWithParams", "ImportedScriptDescriptorsFinder", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyScriptDescriptor extends LazyClassDescriptor implements ScriptDescriptor {
    private final Function0<ReplResultPropertyDescriptor> _resultValue;
    private final Function0<ClassDescriptor> baseClassDescriptor;
    private final boolean isReplScript;
    private final int priority;
    private final ResolveSession resolveSession;
    private final Function0<Annotations> scriptClassAnnotations;
    private final Function0<ScriptCompilationConfiguration> scriptCompilationConfiguration;
    private final Function0<List<ClassDescriptor>> scriptImplicitReceivers;
    private final KtScriptInfo scriptInfo;
    private final Function0<LexicalScope> scriptOuterScope;
    private final Function0<ConstructorWithParams> scriptPrimaryConstructorWithParams;
    private final Function0<List<ScriptProvidedPropertyDescriptor>> scriptProvidedProperties;
    private final Function0<GetScriptingClass> scriptingClassGetter;
    private final Function0<ScriptingHostConfiguration> scriptingHostConfiguration;
    private final SourceElement sourceElement;

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.Substitutable
    public LazyScriptDescriptor substitute(TypeSubstitutor substitutor) {
        Intrinsics.checkNotNullParameter(substitutor, "substitutor");
        return this;
    }

    public final ResolveSession getResolveSession() {
        return this.resolveSession;
    }

    /* JADX INFO: renamed from: getScriptInfo$kotlin_scripting_compiler_impl, reason: from getter */
    public final KtScriptInfo getScriptInfo() {
        return this.scriptInfo;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyScriptDescriptor(ResolveSession resolveSession, DeclarationDescriptor containingDeclaration, Name name2, KtScriptInfo scriptInfo) {
        super(resolveSession, containingDeclaration, name2, scriptInfo, false);
        Intrinsics.checkNotNullParameter(resolveSession, "resolveSession");
        Intrinsics.checkNotNullParameter(containingDeclaration, "containingDeclaration");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(scriptInfo, "scriptInfo");
        this.resolveSession = resolveSession;
        this.scriptInfo = scriptInfo;
        resolveSession.getTrace().record(BindingContext.SCRIPT, scriptInfo.getScript(), this);
        this._resultValue = resolveSession.getStorageManager().createNullableLazyValue(new Function0<ReplResultPropertyDescriptor>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor$_resultValue$1
            {
                super(0);
            }

            /* JADX WARN: Code duplicated, block: B:12:0x003d  */
            @Override // kotlin.jvm.functions.Function0
            public final ReplResultPropertyDescriptor invoke() {
                KtExpression ktExpression;
                Name nameResultFieldName;
                KtScriptInitializer ktScriptInitializer;
                KtBlockExpression ktBlockExpression = (KtBlockExpression) PsiTreeUtil.getChildOfType(this.this$0.getScriptInfo().getScript(), KtBlockExpression.class);
                if (ktBlockExpression == null) {
                    ktExpression = null;
                } else {
                    KtScriptInitializer[] childrenOfType = PsiTreeUtil.getChildrenOfType(ktBlockExpression, KtScriptInitializer.class);
                    if (childrenOfType == null) {
                        childrenOfType = new KtScriptInitializer[0];
                    }
                    KtScriptInitializer[] ktScriptInitializerArr = (KtScriptInitializer[]) childrenOfType;
                    if (ktScriptInitializerArr == null || (ktScriptInitializer = (KtScriptInitializer) ArraysKt.lastOrNull(ktScriptInitializerArr)) == null) {
                        ktExpression = null;
                    } else {
                        ktExpression = (KtExpression) PsiTreeUtil.getChildOfType(ktScriptInitializer, KtExpression.class);
                    }
                }
                KotlinType type = ktExpression != null ? this.this$0.getResolveSession().getTrace().getContext().getType(ktExpression) : null;
                if (type == null || TypeUtilsKt.isUnit(type) || TypeUtilsKt.isNothing(type) || (nameResultFieldName = this.this$0.resultFieldName()) == null) {
                    return null;
                }
                LazyScriptDescriptor lazyScriptDescriptor = this.this$0;
                return new ReplResultPropertyDescriptor(nameResultFieldName, type, lazyScriptDescriptor.getThisAsReceiverParameter(), lazyScriptDescriptor, KotlinSourceElementKt.toSourceElement(ktExpression));
            }
        });
        this.sourceElement = KotlinSourceElementKt.toSourceElement(scriptInfo.getScript());
        this.priority = ScriptPriorities.getScriptPriority(scriptInfo.getScript());
        this.isReplScript = ScriptPriorities.isReplScript(scriptInfo.getScript());
        this.scriptCompilationConfiguration = resolveSession.getStorageManager().createLazyValue(new Function0<ScriptCompilationConfiguration>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor$scriptCompilationConfiguration$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ScriptCompilationConfiguration invoke() {
                ScriptCompilationConfiguration compilationConfiguration;
                ScriptCompilationConfigurationWrapper scriptConfiguration;
                KtFile containingKtFile = this.this$0.getScriptInfo().getScript().getContainingKtFile();
                Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
                ScriptDependenciesProvider.Companion companion = ScriptDependenciesProvider.INSTANCE;
                Project project = containingKtFile.getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                ScriptDependenciesProvider companion2 = companion.getInstance(project);
                if (companion2 == null || (scriptConfiguration = companion2.getScriptConfiguration(containingKtFile)) == null || (compilationConfiguration = scriptConfiguration.getConfiguration()) == null) {
                    ScriptDefinition scriptDefinitionFindScriptDefinition = DefinitionsKt.findScriptDefinition(containingKtFile);
                    compilationConfiguration = scriptDefinitionFindScriptDefinition != null ? scriptDefinitionFindScriptDefinition.getCompilationConfiguration() : null;
                }
                if (compilationConfiguration != null) {
                    return compilationConfiguration;
                }
                throw new IllegalArgumentException("Unable to find script compilation configuration for the script " + this.this$0.getScriptInfo().getScript().getContainingFile());
            }
        });
        this.scriptingHostConfiguration = resolveSession.getStorageManager().createLazyValue(new Function0<ScriptingHostConfiguration>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor$scriptingHostConfiguration$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ScriptingHostConfiguration invoke() {
                ScriptingHostConfiguration scriptingHostConfiguration = (ScriptingHostConfiguration) this.this$0.getScriptCompilationConfiguration().invoke().get(ScriptCompilationKt.getHostConfiguration(ScriptCompilationConfiguration.INSTANCE));
                return scriptingHostConfiguration == null ? JvmScriptingHostConfigurationKt.getDefaultJvmScriptingHostConfiguration() : scriptingHostConfiguration;
            }
        });
        this.scriptingClassGetter = resolveSession.getStorageManager().createLazyValue(new Function0<GetScriptingClass>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor$scriptingClassGetter$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final GetScriptingClass invoke() {
                GetScriptingClass getScriptingClass = (GetScriptingClass) ((ScriptingHostConfiguration) this.this$0.scriptingHostConfiguration.invoke()).get(HostConfigurationKt.getGetScriptingClass(ScriptingHostConfiguration.INSTANCE));
                if (getScriptingClass != null) {
                    return getScriptingClass;
                }
                throw new IllegalArgumentException("Expecting 'getScriptingClass' property in the scripting host configuration for the script " + this.this$0.getScriptInfo().getScript().getContainingFile());
            }
        });
        this.baseClassDescriptor = resolveSession.getStorageManager().createNullableLazyValue(new Function0<ClassDescriptor>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor$baseClassDescriptor$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassDescriptor invoke() {
                String typeName;
                String string;
                kotlin.script.experimental.api.KotlinType kotlinType = (kotlin.script.experimental.api.KotlinType) this.this$0.getScriptCompilationConfiguration().invoke().get(ScriptCompilationKt.getBaseClass(ScriptCompilationConfiguration.INSTANCE));
                if (kotlinType == null) {
                    throw new IllegalStateException(("Base class is not configured for the script " + this.this$0.getScriptInfo().getScript().getContainingFile()).toString());
                }
                KClass<?> fromClass = kotlinType.getFromClass();
                if (fromClass == null || (string = fromClass.toString()) == null || (typeName = StringsKt.replace$default(string, ExternalAnnotationProvider.CLASS_PREFIX, "", false, 4, (Object) null)) == null) {
                    typeName = kotlinType.getTypeName();
                }
                FqName fqName = new FqName(typeName);
                ClassId classId = ClassId.topLevel(fqName);
                Intrinsics.checkNotNullExpressionValue(classId, "topLevel(...)");
                LazyScriptDescriptor lazyScriptDescriptor = this.this$0;
                String strAsString = fqName.parent().asString();
                Intrinsics.checkNotNullExpressionValue(strAsString, "asString(...)");
                return lazyScriptDescriptor.findTypeDescriptor$kotlin_scripting_compiler_impl(classId, typeName, StringsKt.startsWith$default(strAsString, "kotlin.script.templates.standard", false, 2, (Object) null) ? Errors.MISSING_SCRIPT_STANDARD_TEMPLATE : Errors.MISSING_SCRIPT_BASE_CLASS);
            }
        });
        this.scriptImplicitReceivers = resolveSession.getStorageManager().createLazyValue(new Function0<ArrayList<ClassDescriptor>>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor$scriptImplicitReceivers$1
            {
                super(0);
            }

            /* JADX WARN: Code duplicated, block: B:7:0x003e  */
            @Override // kotlin.jvm.functions.Function0
            public final ArrayList<ClassDescriptor> invoke() {
                List<SourceCode> importedScripts;
                ArrayList<ClassDescriptor> arrayList = new ArrayList<>();
                ScriptDependenciesProvider.Companion companion = ScriptDependenciesProvider.INSTANCE;
                Project project = this.this$0.getScriptInfo().getScript().getProject();
                Intrinsics.checkNotNullExpressionValue(project, "getProject(...)");
                ScriptDependenciesProvider companion2 = companion.getInstance(project);
                if (companion2 != null) {
                    KtFile containingKtFile = this.this$0.getScriptInfo().getScript().getContainingKtFile();
                    Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
                    ScriptCompilationConfigurationWrapper scriptConfiguration = companion2.getScriptConfiguration(containingKtFile);
                    if (scriptConfiguration != null) {
                        importedScripts = scriptConfiguration.getImportedScripts();
                    } else {
                        importedScripts = null;
                    }
                } else {
                    importedScripts = null;
                }
                if (importedScripts != null) {
                    LazyScriptDescriptor.ImportedScriptDescriptorsFinder importedScriptDescriptorsFinder = this.this$0.new ImportedScriptDescriptorsFinder();
                    Iterator<T> it = importedScripts.iterator();
                    while (it.hasNext()) {
                        ScriptDescriptor scriptDescriptorInvoke = importedScriptDescriptorsFinder.invoke((SourceCode) it.next());
                        if (scriptDescriptorInvoke != null) {
                            arrayList.add(scriptDescriptorInvoke);
                        }
                    }
                }
                List list = (List) this.this$0.getScriptCompilationConfiguration().invoke().get(ScriptCompilationKt.getImplicitReceivers(ScriptCompilationConfiguration.INSTANCE));
                if (list != null) {
                    LazyScriptDescriptor lazyScriptDescriptor = this.this$0;
                    Iterator it2 = list.iterator();
                    while (it2.hasNext()) {
                        ClassDescriptor classDescriptorFindTypeDescriptor$kotlin_scripting_compiler_impl = lazyScriptDescriptor.findTypeDescriptor$kotlin_scripting_compiler_impl(lazyScriptDescriptor.getScriptingClass((kotlin.script.experimental.api.KotlinType) it2.next()), Errors.MISSING_SCRIPT_RECEIVER_CLASS);
                        if (classDescriptorFindTypeDescriptor$kotlin_scripting_compiler_impl != null) {
                            arrayList.add(classDescriptorFindTypeDescriptor$kotlin_scripting_compiler_impl);
                        }
                    }
                }
                return arrayList;
            }
        });
        this.scriptProvidedProperties = resolveSession.getStorageManager().createLazyValue(new Function0<List<? extends ScriptProvidedPropertyDescriptor>>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor$scriptProvidedProperties$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends ScriptProvidedPropertyDescriptor> invoke() {
                Map mapEmptyMap = (Map) this.this$0.getScriptCompilationConfiguration().invoke().get(ScriptCompilationKt.getProvidedProperties(ScriptCompilationConfiguration.INSTANCE));
                if (mapEmptyMap == null) {
                    mapEmptyMap = MapsKt.emptyMap();
                }
                LazyScriptDescriptor lazyScriptDescriptor = this.this$0;
                ArrayList arrayList = new ArrayList();
                for (Map.Entry entry : mapEmptyMap.entrySet()) {
                    String str = (String) entry.getKey();
                    kotlin.script.experimental.api.KotlinType kotlinType = (kotlin.script.experimental.api.KotlinType) entry.getValue();
                    ClassDescriptor classDescriptorFindTypeDescriptor$kotlin_scripting_compiler_impl = lazyScriptDescriptor.findTypeDescriptor$kotlin_scripting_compiler_impl(lazyScriptDescriptor.getScriptingClass(kotlinType), Errors.MISSING_SCRIPT_PROVIDED_PROPERTY_CLASS);
                    Pair pair = classDescriptorFindTypeDescriptor$kotlin_scripting_compiler_impl != null ? TuplesKt.to(IdentifiersKt.toValidJvmIdentifier(str), TypeUtilsKt.replaceArgumentsWithStarProjections(classDescriptorFindTypeDescriptor$kotlin_scripting_compiler_impl.getDefaultType().makeNullableAsSpecified(kotlinType.getIsNullable()))) : null;
                    if (pair != null) {
                        arrayList.add(pair);
                    }
                }
                ArrayList<Pair> arrayList2 = arrayList;
                LazyScriptDescriptor lazyScriptDescriptor2 = this.this$0;
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
                for (Pair pair2 : arrayList2) {
                    String str2 = (String) pair2.component1();
                    KotlinType kotlinType2 = (KotlinType) pair2.component2();
                    Name nameIdentifier = Name.identifier(str2);
                    Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                    arrayList3.add(new ScriptProvidedPropertyDescriptor(nameIdentifier, kotlinType2, lazyScriptDescriptor2.getThisAsReceiverParameter(), true, lazyScriptDescriptor2));
                }
                return arrayList3;
            }
        });
        this.scriptPrimaryConstructorWithParams = resolveSession.getStorageManager().createLazyValue(new Function0<ConstructorWithParams>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor$scriptPrimaryConstructorWithParams$1
            {
                super(0);
            }

            /* JADX WARN: Multi-variable type inference failed */
            @Override // kotlin.jvm.functions.Function0
            public final LazyScriptDescriptor.ConstructorWithParams invoke() {
                Annotations empty;
                ClassDescriptor classDescriptorInvoke = this.this$0.getBaseClassDescriptor$kotlin_scripting_compiler_impl().invoke();
                ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = classDescriptorInvoke != null ? classDescriptorInvoke.mo6347getUnsubstitutedPrimaryConstructor() : null;
                if (classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor == null || (empty = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getAnnotations()) == null) {
                    empty = Annotations.INSTANCE.getEMPTY();
                }
                List<ValueParameterDescriptor> valueParameters = classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor != null ? classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor.getValueParameters() : null;
                if (valueParameters == null) {
                    valueParameters = CollectionsKt.emptyList();
                }
                List<ClassDescriptor> implicitReceivers = this.this$0.getImplicitReceivers();
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(implicitReceivers, 10));
                int i = 0;
                for (Object obj : implicitReceivers) {
                    int i2 = i + 1;
                    if (i < 0) {
                        CollectionsKt.throwIndexOverflow();
                    }
                    ClassDescriptor classDescriptor = (ClassDescriptor) obj;
                    arrayList.add(TuplesKt.to(Name.identifier(classDescriptor instanceof ScriptDescriptor ? LazyScriptClassMemberScope.IMPORTED_SCRIPT_PARAM_NAME_PREFIX + ((ScriptDescriptor) classDescriptor).getShortName() : LazyScriptClassMemberScope.IMPLICIT_RECEIVER_PARAM_NAME_PREFIX + i), classDescriptor.getDefaultType()));
                    i = i2;
                }
                ArrayList arrayList2 = arrayList;
                Iterable<ScriptProvidedPropertyDescriptor> iterable = (Iterable) this.this$0.scriptProvidedProperties.invoke();
                ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(iterable, 10));
                for (ScriptProvidedPropertyDescriptor scriptProvidedPropertyDescriptor : iterable) {
                    arrayList3.add(TuplesKt.to(scriptProvidedPropertyDescriptor.getShortName(), scriptProvidedPropertyDescriptor.getType()));
                }
                ArrayList arrayList4 = arrayList3;
                LazyScriptDescriptor lazyScriptDescriptor = this.this$0;
                ClassConstructorDescriptorImpl classConstructorDescriptorImplCreate = ClassConstructorDescriptorImpl.create(lazyScriptDescriptor, empty, true, lazyScriptDescriptor.getSourceElement());
                Intrinsics.checkNotNullExpressionValue(classConstructorDescriptorImplCreate, "create(...)");
                Ref.IntRef intRef = new Ref.IntRef();
                ValueParameterDescriptorImpl valueParameterDescriptorImplInvoke$createValueParameter = this.this$0.isReplScript ? invoke$createValueParameter(classConstructorDescriptorImplCreate, intRef, TuplesKt.to(Name.special("<earlierScripts>"), DescriptorUtilsKt.getBuiltIns(this.this$0).getArrayType(Variance.INVARIANT, DescriptorUtilsKt.getBuiltIns(this.this$0).getAnyType()))) : null;
                List<ValueParameterDescriptor> list = valueParameters;
                ArrayList arrayList5 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                for (ValueParameterDescriptor valueParameterDescriptor : list) {
                    Name name3 = valueParameterDescriptor.getShortName();
                    Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                    int i3 = intRef.element;
                    intRef.element = i3 + 1;
                    arrayList5.add(valueParameterDescriptor.mo7204copy(classConstructorDescriptorImplCreate, name3, i3));
                }
                ArrayList arrayList6 = arrayList5;
                ArrayList arrayList7 = arrayList2;
                ArrayList arrayList8 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList7, 10));
                Iterator it = arrayList7.iterator();
                while (it.hasNext()) {
                    arrayList8.add(invoke$createValueParameter(classConstructorDescriptorImplCreate, intRef, (Pair) it.next()));
                }
                ArrayList arrayList9 = arrayList8;
                ArrayList arrayList10 = arrayList4;
                ArrayList arrayList11 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList10, 10));
                Iterator it2 = arrayList10.iterator();
                while (it2.hasNext()) {
                    arrayList11.add(invoke$createValueParameter(classConstructorDescriptorImplCreate, intRef, (Pair) it2.next()));
                }
                ArrayList arrayList12 = arrayList11;
                List listCreateListBuilder = CollectionsKt.createListBuilder();
                if (valueParameterDescriptorImplInvoke$createValueParameter != null) {
                    listCreateListBuilder.add(valueParameterDescriptorImplInvoke$createValueParameter);
                }
                listCreateListBuilder.addAll(arrayList6);
                listCreateListBuilder.addAll(arrayList9);
                listCreateListBuilder.addAll(arrayList12);
                classConstructorDescriptorImplCreate.initialize(CollectionsKt.build(listCreateListBuilder), DescriptorVisibilities.PUBLIC);
                classConstructorDescriptorImplCreate.setReturnType((KotlinType) this.this$0.defaultType.invoke());
                return new LazyScriptDescriptor.ConstructorWithParams(classConstructorDescriptorImplCreate, valueParameterDescriptorImplInvoke$createValueParameter, arrayList6, arrayList9, arrayList12);
            }

            private static final ValueParameterDescriptorImpl invoke$createValueParameter(ClassConstructorDescriptorImpl classConstructorDescriptorImpl, Ref.IntRef intRef, Pair<Name, ? extends KotlinType> pair) {
                ClassConstructorDescriptorImpl classConstructorDescriptorImpl2 = classConstructorDescriptorImpl;
                int i = intRef.element;
                intRef.element = i + 1;
                Annotations empty = Annotations.INSTANCE.getEMPTY();
                Name first = pair.getFirst();
                KotlinType second = pair.getSecond();
                SourceElement NO_SOURCE = SourceElement.NO_SOURCE;
                Intrinsics.checkNotNullExpressionValue(NO_SOURCE, "NO_SOURCE");
                return new ValueParameterDescriptorImpl(classConstructorDescriptorImpl2, null, i, empty, first, second, false, false, false, null, NO_SOURCE);
            }
        });
        this.scriptOuterScope = resolveSession.getStorageManager().createLazyValue(new Function0<LexicalScope>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor$scriptOuterScope$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final LexicalScope invoke() {
                LexicalScope outerScope = super/*org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor*/.getOuterScope();
                Intrinsics.checkNotNullExpressionValue(outerScope, "access$getOuterScope$s1747253459(...)");
                for (ClassDescriptor classDescriptor : CollectionsKt.asReversed(this.this$0.getImplicitReceivers())) {
                    LexicalScope lexicalScope = outerScope;
                    ClassDescriptor classDescriptor2 = classDescriptor;
                    outerScope = ScopeUtilsKt.addImportingScope(new LexicalScopeImpl(lexicalScope, classDescriptor2, true, classDescriptor.getThisAsReceiverParameter(), CollectionsKt.emptyList(), LexicalScopeKind.CLASS_MEMBER_SCOPE, null, null, 192, null), AllUnderImportScope.INSTANCE.create(classDescriptor2, CollectionsKt.emptyList()));
                }
                return outerScope;
            }
        });
        this.scriptClassAnnotations = resolveSession.getStorageManager().createLazyValue(new Function0<Annotations>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor$scriptClassAnnotations$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Annotations invoke() {
                Annotations annotations;
                ClassDescriptor classDescriptorInvoke = this.this$0.getBaseClassDescriptor$kotlin_scripting_compiler_impl().invoke();
                if (classDescriptorInvoke == null || (annotations = classDescriptorInvoke.getAnnotations()) == null) {
                    return super/*org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor*/.getAnnotations();
                }
                return new FilteredAnnotations(annotations, new Function1<FqName, Boolean>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor$scriptClassAnnotations$1$1$1
                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(FqName fqname) {
                        Intrinsics.checkNotNullParameter(fqname, "fqname");
                        String identifier = fqname.shortName().getIdentifier();
                        Intrinsics.checkNotNullExpressionValue(identifier, "getIdentifier(...)");
                        boolean z = false;
                        if (!StringsKt.startsWith$default(identifier, "KotlinScript", false, 2, (Object) null) && !StringsKt.startsWith$default(identifier, "ScriptTemplate", false, 2, (Object) null)) {
                            z = true;
                        }
                        return Boolean.valueOf(z);
                    }
                });
            }
        });
    }

    @Override // org.jetbrains.kotlin.descriptors.ScriptDescriptor
    public ReplResultPropertyDescriptor getResultValue() {
        return this._resultValue.invoke();
    }

    /* JADX WARN: Code duplicated, block: B:20:0x0078  */
    public final Name resultFieldName() {
        String str;
        Integer num = (Integer) this.scriptInfo.getScript().getUserData(ScriptPriorities.PRIORITY_KEY);
        String strValueOf = num != null ? String.valueOf(num) : null;
        if (strValueOf != null) {
            String str2 = (String) this.scriptCompilationConfiguration.invoke().get(ReplDataKt.getResultFieldPrefix(ReplDataKt.getRepl(ScriptCompilationConfiguration.INSTANCE)));
            if (str2 == null) {
                str = null;
            } else {
                if (StringsKt.isBlank(str2)) {
                    str2 = null;
                }
                if (str2 != null) {
                    str = str2 + strValueOf;
                } else {
                    str = null;
                }
            }
        } else {
            str = (String) this.scriptCompilationConfiguration.invoke().get(ScriptCompilationKt.getResultField(ScriptCompilationConfiguration.INSTANCE));
            if (str == null || StringsKt.isBlank(str)) {
                str = null;
            }
        }
        if (str != null) {
            return Name.identifier(str);
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.ClassDescriptorBase, org.jetbrains.kotlin.descriptors.DeclarationDescriptorWithSource
    /* JADX INFO: renamed from: getSource, reason: from getter */
    public SourceElement getSourceElement() {
        return this.sourceElement;
    }

    @Override // org.jetbrains.kotlin.descriptors.ScriptDescriptor
    public int getPriority() {
        return this.priority;
    }

    public final Function0<ScriptCompilationConfiguration> getScriptCompilationConfiguration() {
        return this.scriptCompilationConfiguration;
    }

    public final KClass<?> getScriptingClass(kotlin.script.experimental.api.KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
        return this.scriptingClassGetter.invoke().invoke(type, Reflection.getOrCreateKotlinClass(ScriptDefinition.class), this.scriptingHostConfiguration.invoke());
    }

    @Override // org.jetbrains.kotlin.descriptors.impl.AbstractClassDescriptor, org.jetbrains.kotlin.descriptors.DeclarationDescriptor
    public <R, D> R accept(DeclarationDescriptorVisitor<R, D> visitor, D data) {
        Intrinsics.checkNotNullParameter(visitor, "visitor");
        return visitor.visitScriptDescriptor(this, data);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor
    protected ScopesHolderForClass<LazyClassMemberScope> createScopesHolderForClass(final LazyClassContext c, final ClassMemberDeclarationProvider declarationProvider) {
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(declarationProvider, "declarationProvider");
        return ScopesHolderForClass.INSTANCE.create(this, c.getStorageManager(), c.getKotlinTypeCheckerOfOwnerModule().getKotlinTypeRefiner(), new Function1<KotlinTypeRefiner, LazyClassMemberScope>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor.createScopesHolderForClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final LazyClassMemberScope invoke(KotlinTypeRefiner it) {
                Intrinsics.checkNotNullParameter(it, "it");
                LazyClassContext lazyClassContext = c;
                Intrinsics.checkNotNull(lazyClassContext, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.lazy.ResolveSession");
                return new LazyScriptClassMemberScope((ResolveSession) lazyClassContext, declarationProvider, this, c.getTrace());
            }
        });
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor, org.jetbrains.kotlin.descriptors.ClassDescriptor
    /* JADX INFO: renamed from: getUnsubstitutedPrimaryConstructor */
    public ClassConstructorDescriptor mo6347getUnsubstitutedPrimaryConstructor() {
        ClassConstructorDescriptor classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor = super.mo6347getUnsubstitutedPrimaryConstructor();
        Intrinsics.checkNotNull(classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor);
        return classConstructorDescriptorMo6347getUnsubstitutedPrimaryConstructor;
    }

    public final Function0<ClassDescriptor> getBaseClassDescriptor$kotlin_scripting_compiler_impl() {
        return this.baseClassDescriptor;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor
    public List<SimpleType> computeSupertypes() {
        SimpleType anyType;
        ClassDescriptor classDescriptorInvoke = this.baseClassDescriptor.invoke();
        if (classDescriptorInvoke == null || (anyType = classDescriptorInvoke.getDefaultType()) == null) {
            anyType = DescriptorUtilsKt.getBuiltIns(this).getAnyType();
        }
        return CollectionsKt.listOf(anyType);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LazyScriptDescriptor.kt */
    @Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0012\u0010\n\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\rH\u0002J\u0013\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\u0006\u0010\u0010\u001a\u00020\rH\u0086\u0002R \u0010\u0003\u001a\u00070\u0004¢\u0006\u0002\b\u00058FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\b\u0010\t\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/LazyScriptDescriptor$ImportedScriptDescriptorsFinder;", "", "(Lorg/jetbrains/kotlin/scripting/resolve/LazyScriptDescriptor;)V", "psiManager", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiManager;", "Lorg/jetbrains/annotations/NotNull;", "getPsiManager", "()Lcom/intellij/psi/PsiManager;", "psiManager$delegate", "Lkotlin/Lazy;", "getKtFile", "Lorg/jetbrains/kotlin/psi/KtFile;", "script", "Lkotlin/script/experimental/api/SourceCode;", "invoke", "Lorg/jetbrains/kotlin/descriptors/ScriptDescriptor;", "importedScript", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class ImportedScriptDescriptorsFinder {

        /* JADX INFO: renamed from: psiManager$delegate, reason: from kotlin metadata */
        private final Lazy psiManager;

        public ImportedScriptDescriptorsFinder() {
            this.psiManager = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<PsiManager>() { // from class: org.jetbrains.kotlin.scripting.resolve.LazyScriptDescriptor$ImportedScriptDescriptorsFinder$psiManager$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final PsiManager invoke() {
                    return PsiManager.getInstance(lazyScriptDescriptor.getScriptInfo().getScript().getProject());
                }
            });
        }

        public final PsiManager getPsiManager() {
            return (PsiManager) this.psiManager.getValue();
        }

        public final ScriptDescriptor invoke(SourceCode importedScript) {
            List<KtDeclaration> declarations;
            Object next;
            Intrinsics.checkNotNullParameter(importedScript, "importedScript");
            KtFile ktFile = getKtFile(importedScript);
            if (ktFile != null && (declarations = ktFile.getDeclarations()) != null) {
                Iterator<T> it = declarations.iterator();
                do {
                    if (!it.hasNext()) {
                        next = null;
                        break;
                    }
                    next = it.next();
                } while (!(next instanceof KtScript));
                KtScript ktScript = (KtScript) next;
                if (ktScript != null) {
                    ClassDescriptorWithResolutionScopes scriptDescriptor = LazyScriptDescriptor.this.getResolveSession().getScriptDescriptor(ktScript);
                    Intrinsics.checkNotNull(scriptDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ScriptDescriptor");
                    return (ScriptDescriptor) scriptDescriptor;
                }
            }
            return null;
        }

        private final KtFile getKtFile(SourceCode script) {
            PsiFile psiFileFindFile;
            if (script instanceof KtFileScriptSource) {
                return ((KtFileScriptSource) script).getKtFile();
            }
            VirtualFileScriptSource virtualFileScriptSource = script instanceof VirtualFileScriptSource ? (VirtualFileScriptSource) script : null;
            if (virtualFileScriptSource == null || (psiFileFindFile = getPsiManager().findFile(virtualFileScriptSource.getVirtualFile())) == null) {
                return getKtFile$errorKtFile(LazyScriptDescriptor.this, script, Errors.MISSING_IMPORTED_SCRIPT_PSI);
            }
            if (psiFileFindFile instanceof KtFile) {
                return (KtFile) psiFileFindFile;
            }
            return null;
        }

        private static final KtFile getKtFile$errorKtFile(LazyScriptDescriptor lazyScriptDescriptor, SourceCode sourceCode, DiagnosticFactory1<PsiElement, String> diagnosticFactory1) {
            String locationId = sourceCode.getLocationId();
            if (locationId == null && (locationId = sourceCode.getName()) == null) {
                locationId = "unknown script";
            }
            lazyScriptDescriptor.reportErrorString1(diagnosticFactory1, locationId);
            return null;
        }
    }

    public final ClassDescriptor findTypeDescriptor$kotlin_scripting_compiler_impl(KClass<?> kClass, DiagnosticFactory1<PsiElement, String> errorDiagnostic) {
        Intrinsics.checkNotNullParameter(kClass, "kClass");
        return findTypeDescriptor$kotlin_scripting_compiler_impl(ScriptDescriptorUtilKt.getClassId(kClass), kClass.toString(), errorDiagnostic);
    }

    public final ClassDescriptor findTypeDescriptor$kotlin_scripting_compiler_impl(KType type, DiagnosticFactory1<PsiElement, String> errorDiagnostic) {
        Intrinsics.checkNotNullParameter(type, "type");
        return findTypeDescriptor$kotlin_scripting_compiler_impl(ScriptDescriptorUtilKt.getClassId(type), type.toString(), errorDiagnostic);
    }

    public final ClassDescriptor findTypeDescriptor$kotlin_scripting_compiler_impl(ClassId classId, String typeName, DiagnosticFactory1<PsiElement, String> errorDiagnostic) {
        FqName fqNameAsSingleFqName;
        String string;
        Intrinsics.checkNotNullParameter(typeName, "typeName");
        ClassDescriptor classDescriptorFindClassAcrossModuleDependencies = classId != null ? FindClassInModuleKt.findClassAcrossModuleDependencies(DescriptorUtilsKt.getModule(this), classId) : null;
        if (classDescriptorFindClassAcrossModuleDependencies == null) {
            if (classId != null && (fqNameAsSingleFqName = classId.asSingleFqName()) != null && (string = fqNameAsSingleFqName.toString()) != null) {
                typeName = string;
            }
            reportErrorString1(errorDiagnostic, typeName);
        }
        return classDescriptorFindClassAcrossModuleDependencies;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void reportErrorString1(DiagnosticFactory1<PsiElement, String> errorDiagnostic, String arg) {
        if (errorDiagnostic != null) {
            this.resolveSession.getTrace().report(errorDiagnostic.on(this.scriptInfo.getScript().getContainingFile(), arg));
        }
    }

    @Override // org.jetbrains.kotlin.descriptors.ScriptDescriptor
    public List<ClassDescriptor> getImplicitReceivers() {
        return this.scriptImplicitReceivers.invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.ScriptDescriptor
    public List<PropertyDescriptor> getScriptProvidedProperties() {
        return this.scriptProvidedProperties.invoke();
    }

    /* JADX INFO: compiled from: LazyScriptDescriptor.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\f\b\u0000\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\b\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\u0002\u0010\nR\u0017\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\fR\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00050\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\f¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/LazyScriptDescriptor$ConstructorWithParams;", "", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "Lorg/jetbrains/kotlin/descriptors/impl/ClassConstructorDescriptorImpl;", "earlierScriptsParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "baseClassConstructorParameters", "", "implicitReceiversParameters", "scriptProvidedPropertiesParameters", "(Lorg/jetbrains/kotlin/descriptors/impl/ClassConstructorDescriptorImpl;Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;Ljava/util/List;Ljava/util/List;Ljava/util/List;)V", "getBaseClassConstructorParameters", "()Ljava/util/List;", "getConstructor", "()Lorg/jetbrains/kotlin/descriptors/impl/ClassConstructorDescriptorImpl;", "getEarlierScriptsParameter", "()Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "getImplicitReceiversParameters", "getScriptProvidedPropertiesParameters", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class ConstructorWithParams {
        private final List<ValueParameterDescriptor> baseClassConstructorParameters;
        private final ClassConstructorDescriptorImpl constructor;
        private final ValueParameterDescriptor earlierScriptsParameter;
        private final List<ValueParameterDescriptor> implicitReceiversParameters;
        private final List<ValueParameterDescriptor> scriptProvidedPropertiesParameters;

        /* JADX WARN: Multi-variable type inference failed */
        public ConstructorWithParams(ClassConstructorDescriptorImpl constructor, ValueParameterDescriptor valueParameterDescriptor, List<? extends ValueParameterDescriptor> baseClassConstructorParameters, List<? extends ValueParameterDescriptor> implicitReceiversParameters, List<? extends ValueParameterDescriptor> scriptProvidedPropertiesParameters) {
            Intrinsics.checkNotNullParameter(constructor, "constructor");
            Intrinsics.checkNotNullParameter(baseClassConstructorParameters, "baseClassConstructorParameters");
            Intrinsics.checkNotNullParameter(implicitReceiversParameters, "implicitReceiversParameters");
            Intrinsics.checkNotNullParameter(scriptProvidedPropertiesParameters, "scriptProvidedPropertiesParameters");
            this.constructor = constructor;
            this.earlierScriptsParameter = valueParameterDescriptor;
            this.baseClassConstructorParameters = baseClassConstructorParameters;
            this.implicitReceiversParameters = implicitReceiversParameters;
            this.scriptProvidedPropertiesParameters = scriptProvidedPropertiesParameters;
        }

        public final ClassConstructorDescriptorImpl getConstructor() {
            return this.constructor;
        }

        public final ValueParameterDescriptor getEarlierScriptsParameter() {
            return this.earlierScriptsParameter;
        }

        public final List<ValueParameterDescriptor> getBaseClassConstructorParameters() {
            return this.baseClassConstructorParameters;
        }

        public final List<ValueParameterDescriptor> getImplicitReceiversParameters() {
            return this.implicitReceiversParameters;
        }

        public final List<ValueParameterDescriptor> getScriptProvidedPropertiesParameters() {
            return this.scriptProvidedPropertiesParameters;
        }
    }

    public final Function0<ConstructorWithParams> getScriptPrimaryConstructorWithParams$kotlin_scripting_compiler_impl() {
        return this.scriptPrimaryConstructorWithParams;
    }

    @Override // org.jetbrains.kotlin.descriptors.ScriptDescriptor
    public ValueParameterDescriptor getEarlierScriptsConstructorParameter() {
        return this.scriptPrimaryConstructorWithParams.invoke().getEarlierScriptsParameter();
    }

    @Override // org.jetbrains.kotlin.descriptors.ScriptDescriptor
    public List<ValueParameterDescriptor> getExplicitConstructorParameters() {
        return this.scriptPrimaryConstructorWithParams.invoke().getBaseClassConstructorParameters();
    }

    @Override // org.jetbrains.kotlin.descriptors.ScriptDescriptor
    public List<ValueParameterDescriptor> getImplicitReceiversParameters() {
        return this.scriptPrimaryConstructorWithParams.invoke().getImplicitReceiversParameters();
    }

    @Override // org.jetbrains.kotlin.descriptors.ScriptDescriptor
    public List<ValueParameterDescriptor> getScriptProvidedPropertiesParameters() {
        return this.scriptPrimaryConstructorWithParams.invoke().getScriptProvidedPropertiesParameters();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor
    protected LexicalScope getOuterScope() {
        return this.scriptOuterScope.invoke();
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor, org.jetbrains.kotlin.descriptors.annotations.Annotated
    public Annotations getAnnotations() {
        return this.scriptClassAnnotations.invoke();
    }

    @Override // org.jetbrains.kotlin.descriptors.ScriptDescriptor
    /* JADX INFO: renamed from: isReplScript, reason: from getter */
    public boolean getIsReplScript() {
        return this.isReplScript;
    }
}
