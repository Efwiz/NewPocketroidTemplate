package org.jetbrains.kotlin.resolve;

import io.netty.util.internal.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.com.google.common.collect.HashMultimap;
import org.jetbrains.kotlin.com.google.common.collect.Multimap;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.com.intellij.psi.PsiElementVisitor;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.KotlinLookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtAnonymousInitializer;
import org.jetbrains.kotlin.psi.KtClass;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclaration;
import org.jetbrains.kotlin.psi.KtDestructuringDeclarationEntry;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtEnumEntry;
import org.jetbrains.kotlin.psi.KtFile;
import org.jetbrains.kotlin.psi.KtImportDirective;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtNamedFunction;
import org.jetbrains.kotlin.psi.KtObjectDeclaration;
import org.jetbrains.kotlin.psi.KtPackageDirective;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtProperty;
import org.jetbrains.kotlin.psi.KtScript;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.KtVisitorVoid;
import org.jetbrains.kotlin.resolve.calls.smartcasts.DataFlowInfo;
import org.jetbrains.kotlin.resolve.checkers.ClassifierUsageChecker;
import org.jetbrains.kotlin.resolve.checkers.ClassifierUsageCheckerContext;
import org.jetbrains.kotlin.resolve.checkers.ClassifierUsageCheckerKt;
import org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver;
import org.jetbrains.kotlin.resolve.lazy.DeclarationScopeProvider;
import org.jetbrains.kotlin.resolve.lazy.FileScopeProvider;
import org.jetbrains.kotlin.resolve.lazy.ForceResolveUtil;
import org.jetbrains.kotlin.resolve.lazy.LazyDeclarationResolver;
import org.jetbrains.kotlin.resolve.lazy.TopLevelDescriptorProvider;
import org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor;
import org.jetbrains.kotlin.types.expressions.ExpressionTypingContext;

/* JADX INFO: compiled from: LazyTopDownAnalyzer.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000æ\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u0093\u0001\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u000b\u0012\u0006\u0010\f\u001a\u00020\r\u0012\u0006\u0010\u000e\u001a\u00020\u000f\u0012\u0006\u0010\u0010\u001a\u00020\u0011\u0012\u0006\u0010\u0012\u001a\u00020\u0013\u0012\u0006\u0010\u0014\u001a\u00020\u0015\u0012\u0006\u0010\u0016\u001a\u00020\u0017\u0012\u0006\u0010\u0018\u001a\u00020\u0019\u0012\u0006\u0010\u001a\u001a\u00020\u001b\u0012\u0006\u0010\u001c\u001a\u00020\u001d\u0012\u0006\u0010\u001e\u001a\u00020\u001f\u0012\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!\u0012\u0006\u0010#\u001a\u00020$¢\u0006\u0002\u0010%J2\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020)2\f\u0010*\u001a\b\u0012\u0004\u0012\u00020,0+2\b\b\u0002\u0010-\u001a\u00020.2\n\b\u0002\u0010/\u001a\u0004\u0018\u000100J\u001e\u00101\u001a\u0002022\u0006\u00103\u001a\u00020'2\f\u00104\u001a\b\u0012\u0004\u0012\u00020605H\u0002J2\u00107\u001a\u0002022\u0006\u00103\u001a\u00020'2\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020;092\f\u0010<\u001a\b\u0012\u0004\u0012\u00020=05H\u0002J2\u0010>\u001a\u0002022\u0006\u00103\u001a\u00020'2\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020;092\f\u0010?\u001a\b\u0012\u0004\u0012\u00020@05H\u0002J2\u0010A\u001a\u0002022\u0006\u00103\u001a\u00020'2\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020;092\f\u0010B\u001a\b\u0012\u0004\u0012\u00020C05H\u0002J,\u0010D\u001a\u0002022\u0012\u00108\u001a\u000e\u0012\u0004\u0012\u00020:\u0012\u0004\u0012\u00020;092\u0006\u0010E\u001a\u00020F2\u0006\u0010G\u001a\u00020HH\u0002J\u0010\u0010I\u001a\u0002022\u0006\u00103\u001a\u00020'H\u0002J\u0010\u0010J\u001a\u0002022\u0006\u00103\u001a\u00020'H\u0002J\u000e\u0010K\u001a\u0002022\u0006\u0010L\u001a\u00020MR\u000e\u0010\u0010\u001a\u00020\u0011X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010 \u001a\b\u0012\u0004\u0012\u00020\"0!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0016\u001a\u00020\u0017X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010#\u001a\u00020$X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001c\u001a\u00020\u001dX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\rX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0018\u001a\u00020\u0019X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0012\u001a\u00020\u0013X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006N"}, d2 = {"Lorg/jetbrains/kotlin/resolve/LazyTopDownAnalyzer;", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "declarationResolver", "Lorg/jetbrains/kotlin/resolve/DeclarationResolver;", "overrideResolver", "Lorg/jetbrains/kotlin/resolve/OverrideResolver;", "overloadResolver", "Lorg/jetbrains/kotlin/resolve/OverloadResolver;", "varianceChecker", "Lorg/jetbrains/kotlin/resolve/VarianceChecker;", "moduleDescriptor", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "lazyDeclarationResolver", "Lorg/jetbrains/kotlin/resolve/lazy/LazyDeclarationResolver;", "bodyResolver", "Lorg/jetbrains/kotlin/resolve/BodyResolver;", "topLevelDescriptorProvider", "Lorg/jetbrains/kotlin/resolve/lazy/TopLevelDescriptorProvider;", "fileScopeProvider", "Lorg/jetbrains/kotlin/resolve/lazy/FileScopeProvider;", "declarationScopeProvider", "Lorg/jetbrains/kotlin/resolve/lazy/DeclarationScopeProvider;", "qualifiedExpressionResolver", "Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver;", "identifierChecker", "Lorg/jetbrains/kotlin/resolve/IdentifierChecker;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "deprecationResolver", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "classifierUsageCheckers", "", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageChecker;", "filePreprocessor", "Lorg/jetbrains/kotlin/resolve/FilePreprocessor;", "(Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/resolve/DeclarationResolver;Lorg/jetbrains/kotlin/resolve/OverrideResolver;Lorg/jetbrains/kotlin/resolve/OverloadResolver;Lorg/jetbrains/kotlin/resolve/VarianceChecker;Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;Lorg/jetbrains/kotlin/resolve/lazy/LazyDeclarationResolver;Lorg/jetbrains/kotlin/resolve/BodyResolver;Lorg/jetbrains/kotlin/resolve/lazy/TopLevelDescriptorProvider;Lorg/jetbrains/kotlin/resolve/lazy/FileScopeProvider;Lorg/jetbrains/kotlin/resolve/lazy/DeclarationScopeProvider;Lorg/jetbrains/kotlin/resolve/QualifiedExpressionResolver;Lorg/jetbrains/kotlin/resolve/IdentifierChecker;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;Ljava/lang/Iterable;Lorg/jetbrains/kotlin/resolve/FilePreprocessor;)V", "analyzeDeclarations", "Lorg/jetbrains/kotlin/resolve/TopDownAnalysisContext;", "topDownAnalysisMode", "Lorg/jetbrains/kotlin/resolve/TopDownAnalysisMode;", "declarations", "", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "outerDataFlowInfo", "Lorg/jetbrains/kotlin/resolve/calls/smartcasts/DataFlowInfo;", "localContext", "Lorg/jetbrains/kotlin/types/expressions/ExpressionTypingContext;", "createFunctionDescriptors", "", "c", "functions", "", "Lorg/jetbrains/kotlin/psi/KtNamedFunction;", "createPropertiesFromDestructuringDeclarations", "topLevelFqNames", "Lorg/jetbrains/kotlin/com/google/common/collect/Multimap;", "Lorg/jetbrains/kotlin/name/FqName;", "Lorg/jetbrains/kotlin/psi/KtElement;", "destructuringDeclarations", "Lorg/jetbrains/kotlin/psi/KtDestructuringDeclaration;", "createPropertyDescriptors", "properties", "Lorg/jetbrains/kotlin/psi/KtProperty;", "createTypeAliasDescriptors", "typeAliases", "Lorg/jetbrains/kotlin/psi/KtTypeAlias;", "registerTopLevelFqName", "declaration", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "resolveAllHeadersInClasses", "resolveImportsInAllFiles", "resolveImportsInFile", "file", "Lorg/jetbrains/kotlin/psi/KtFile;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LazyTopDownAnalyzer {
    private final BodyResolver bodyResolver;
    private final Iterable<ClassifierUsageChecker> classifierUsageCheckers;
    private final DeclarationResolver declarationResolver;
    private final DeclarationScopeProvider declarationScopeProvider;
    private final DeprecationResolver deprecationResolver;
    private final FilePreprocessor filePreprocessor;
    private final FileScopeProvider fileScopeProvider;
    private final IdentifierChecker identifierChecker;
    private final LanguageVersionSettings languageVersionSettings;
    private final LazyDeclarationResolver lazyDeclarationResolver;
    private final ModuleDescriptor moduleDescriptor;
    private final OverloadResolver overloadResolver;
    private final OverrideResolver overrideResolver;
    private final QualifiedExpressionResolver qualifiedExpressionResolver;
    private final TopLevelDescriptorProvider topLevelDescriptorProvider;
    private final BindingTrace trace;
    private final VarianceChecker varianceChecker;

    /* JADX WARN: Multi-variable type inference failed */
    public LazyTopDownAnalyzer(BindingTrace trace, DeclarationResolver declarationResolver, OverrideResolver overrideResolver, OverloadResolver overloadResolver, VarianceChecker varianceChecker, ModuleDescriptor moduleDescriptor, LazyDeclarationResolver lazyDeclarationResolver, BodyResolver bodyResolver, TopLevelDescriptorProvider topLevelDescriptorProvider, FileScopeProvider fileScopeProvider, DeclarationScopeProvider declarationScopeProvider, QualifiedExpressionResolver qualifiedExpressionResolver, IdentifierChecker identifierChecker, LanguageVersionSettings languageVersionSettings, DeprecationResolver deprecationResolver, Iterable<? extends ClassifierUsageChecker> classifierUsageCheckers, FilePreprocessor filePreprocessor) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(declarationResolver, "declarationResolver");
        Intrinsics.checkNotNullParameter(overrideResolver, "overrideResolver");
        Intrinsics.checkNotNullParameter(overloadResolver, "overloadResolver");
        Intrinsics.checkNotNullParameter(varianceChecker, "varianceChecker");
        Intrinsics.checkNotNullParameter(moduleDescriptor, "moduleDescriptor");
        Intrinsics.checkNotNullParameter(lazyDeclarationResolver, "lazyDeclarationResolver");
        Intrinsics.checkNotNullParameter(bodyResolver, "bodyResolver");
        Intrinsics.checkNotNullParameter(topLevelDescriptorProvider, "topLevelDescriptorProvider");
        Intrinsics.checkNotNullParameter(fileScopeProvider, "fileScopeProvider");
        Intrinsics.checkNotNullParameter(declarationScopeProvider, "declarationScopeProvider");
        Intrinsics.checkNotNullParameter(qualifiedExpressionResolver, "qualifiedExpressionResolver");
        Intrinsics.checkNotNullParameter(identifierChecker, "identifierChecker");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(deprecationResolver, "deprecationResolver");
        Intrinsics.checkNotNullParameter(classifierUsageCheckers, "classifierUsageCheckers");
        Intrinsics.checkNotNullParameter(filePreprocessor, "filePreprocessor");
        this.trace = trace;
        this.declarationResolver = declarationResolver;
        this.overrideResolver = overrideResolver;
        this.overloadResolver = overloadResolver;
        this.varianceChecker = varianceChecker;
        this.moduleDescriptor = moduleDescriptor;
        this.lazyDeclarationResolver = lazyDeclarationResolver;
        this.bodyResolver = bodyResolver;
        this.topLevelDescriptorProvider = topLevelDescriptorProvider;
        this.fileScopeProvider = fileScopeProvider;
        this.declarationScopeProvider = declarationScopeProvider;
        this.qualifiedExpressionResolver = qualifiedExpressionResolver;
        this.identifierChecker = identifierChecker;
        this.languageVersionSettings = languageVersionSettings;
        this.deprecationResolver = deprecationResolver;
        this.classifierUsageCheckers = classifierUsageCheckers;
        this.filePreprocessor = filePreprocessor;
    }

    public static /* synthetic */ TopDownAnalysisContext analyzeDeclarations$default(LazyTopDownAnalyzer lazyTopDownAnalyzer, TopDownAnalysisMode topDownAnalysisMode, Collection collection, DataFlowInfo dataFlowInfo, ExpressionTypingContext expressionTypingContext, int i, Object obj) {
        if ((i & 4) != 0) {
            dataFlowInfo = DataFlowInfo.INSTANCE.getEMPTY();
        }
        if ((i & 8) != 0) {
            expressionTypingContext = null;
        }
        return lazyTopDownAnalyzer.analyzeDeclarations(topDownAnalysisMode, collection, dataFlowInfo, expressionTypingContext);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v0, types: [T, org.jetbrains.kotlin.resolve.ExceptionWrappingKtVisitorVoid] */
    public final TopDownAnalysisContext analyzeDeclarations(TopDownAnalysisMode topDownAnalysisMode, Collection<? extends PsiElement> declarations, DataFlowInfo outerDataFlowInfo, ExpressionTypingContext localContext) {
        Intrinsics.checkNotNullParameter(topDownAnalysisMode, "topDownAnalysisMode");
        Intrinsics.checkNotNullParameter(declarations, "declarations");
        Intrinsics.checkNotNullParameter(outerDataFlowInfo, "outerDataFlowInfo");
        final TopDownAnalysisContext topDownAnalysisContext = new TopDownAnalysisContext(topDownAnalysisMode, outerDataFlowInfo, this.declarationScopeProvider, localContext);
        HashMultimap hashMultimapCreate = HashMultimap.create();
        ArrayList arrayList = new ArrayList();
        final ArrayList arrayList2 = new ArrayList();
        final ArrayList arrayList3 = new ArrayList();
        final ArrayList arrayList4 = new ArrayList();
        for (PsiElement psiElement : declarations) {
            final Ref.ObjectRef objectRef = new Ref.ObjectRef();
            final HashMultimap hashMultimap = hashMultimapCreate;
            final ArrayList arrayList5 = arrayList;
            objectRef.element = new ExceptionWrappingKtVisitorVoid(new KtVisitorVoid() { // from class: org.jetbrains.kotlin.resolve.LazyTopDownAnalyzer.analyzeDeclarations.1
                private final void registerDeclarations(List<? extends KtDeclaration> declarations2) {
                    for (KtDeclaration ktDeclaration : declarations2) {
                        KtVisitorVoid ktVisitorVoid = objectRef.element;
                        Intrinsics.checkNotNull(ktVisitorVoid);
                        ktDeclaration.accept(ktVisitorVoid);
                    }
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitDeclaration(KtDeclaration dcl) {
                    Intrinsics.checkNotNullParameter(dcl, "dcl");
                    throw new IllegalArgumentException("Unsupported declaration: " + dcl + StringUtil.SPACE + dcl.getText());
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitScript(KtScript script) {
                    Intrinsics.checkNotNullParameter(script, "script");
                    topDownAnalysisContext.getScripts().put(script, this.lazyDeclarationResolver.getScriptDescriptor(script, new KotlinLookupLocation(script)));
                    List<KtDeclaration> declarations2 = script.getDeclarations();
                    Intrinsics.checkNotNullExpressionValue(declarations2, "getDeclarations(...)");
                    registerDeclarations(declarations2);
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitKtFile(KtFile file) {
                    Intrinsics.checkNotNullParameter(file, "file");
                    this.filePreprocessor.preprocessFile(file);
                    registerDeclarations(file.getDeclarations());
                    KtPackageDirective packageDirective = file.getPackageDirective();
                    file.isScript();
                    if (packageDirective != null) {
                        packageDirective.accept(this);
                    }
                    topDownAnalysisContext.addFile(file);
                    hashMultimap.put(file.getPackageFqName(), packageDirective);
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitPackageDirective(KtPackageDirective directive) {
                    Intrinsics.checkNotNullParameter(directive, "directive");
                    List<KtSimpleNameExpression> packageNames = directive.getPackageNames();
                    Intrinsics.checkNotNullExpressionValue(packageNames, "getPackageNames(...)");
                    LazyTopDownAnalyzer lazyTopDownAnalyzer = this;
                    for (KtSimpleNameExpression ktSimpleNameExpression : packageNames) {
                        IdentifierChecker identifierChecker = lazyTopDownAnalyzer.identifierChecker;
                        Intrinsics.checkNotNull(ktSimpleNameExpression);
                        identifierChecker.checkIdentifier(ktSimpleNameExpression, lazyTopDownAnalyzer.trace);
                    }
                    this.qualifiedExpressionResolver.resolvePackageHeader(directive, this.moduleDescriptor, this.trace);
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitImportDirective(KtImportDirective importDirective) {
                    Intrinsics.checkNotNullParameter(importDirective, "importDirective");
                    FileScopeProvider fileScopeProvider = this.fileScopeProvider;
                    KtFile containingKtFile = importDirective.getContainingKtFile();
                    Intrinsics.checkNotNullExpressionValue(containingKtFile, "getContainingKtFile(...)");
                    fileScopeProvider.getImportResolver(containingKtFile).forceResolveImport(importDirective);
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitClassOrObject(KtClassOrObject classOrObject) {
                    Intrinsics.checkNotNullParameter(classOrObject, "classOrObject");
                    ClassDescriptor classDescriptor = this.lazyDeclarationResolver.getClassDescriptor(classOrObject, classOrObject.isTopLevel() ? new KotlinLookupLocation(classOrObject) : NoLookupLocation.WHEN_RESOLVE_DECLARATION);
                    Intrinsics.checkNotNull(classDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes");
                    ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes = (ClassDescriptorWithResolutionScopes) classDescriptor;
                    topDownAnalysisContext.getDeclaredClasses().put(classOrObject, classDescriptorWithResolutionScopes);
                    registerDeclarations(classOrObject.getDeclarations());
                    LazyTopDownAnalyzer lazyTopDownAnalyzer = this;
                    HashMultimap<FqName, KtElement> topLevelFqNames = hashMultimap;
                    Intrinsics.checkNotNullExpressionValue(topLevelFqNames, "$topLevelFqNames");
                    lazyTopDownAnalyzer.registerTopLevelFqName(topLevelFqNames, classOrObject, classDescriptorWithResolutionScopes);
                    checkClassOrObjectDeclarations(classOrObject, classDescriptorWithResolutionScopes);
                }

                private final void checkClassOrObjectDeclarations(KtClassOrObject classOrObject, ClassDescriptor classDescriptor) {
                    boolean z = false;
                    for (KtDeclaration ktDeclaration : classOrObject.getDeclarations()) {
                        if ((ktDeclaration instanceof KtObjectDeclaration) && ((KtObjectDeclaration) ktDeclaration).isCompanion()) {
                            if (z) {
                                this.trace.report(Errors.MANY_COMPANION_OBJECTS.on(ktDeclaration));
                            }
                            z = true;
                        } else if (ktDeclaration instanceof KtSecondaryConstructor) {
                            if (DescriptorUtils.isSingletonOrAnonymousObject(classDescriptor)) {
                                this.trace.report(Errors.CONSTRUCTOR_IN_OBJECT.on(ktDeclaration));
                            } else if (classDescriptor.getKind() == ClassKind.INTERFACE) {
                                this.trace.report(Errors.CONSTRUCTOR_IN_INTERFACE.on(ktDeclaration));
                            }
                        }
                    }
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitClass(KtClass klass) {
                    Intrinsics.checkNotNullParameter(klass, "klass");
                    visitClassOrObject(klass);
                    registerPrimaryConstructorParameters(klass);
                }

                private final void registerPrimaryConstructorParameters(KtClass klass) {
                    for (KtParameter ktParameter : klass.getPrimaryConstructorParameters()) {
                        if (ktParameter.hasValOrVar()) {
                            Map<KtParameter, PropertyDescriptor> primaryConstructorParameterProperties = topDownAnalysisContext.getPrimaryConstructorParameterProperties();
                            DeclarationDescriptor declarationDescriptorResolveToDescriptor = this.lazyDeclarationResolver.resolveToDescriptor(ktParameter);
                            Intrinsics.checkNotNull(declarationDescriptorResolveToDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
                            primaryConstructorParameterProperties.put(ktParameter, (PropertyDescriptor) declarationDescriptorResolveToDescriptor);
                        }
                    }
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitSecondaryConstructor(KtSecondaryConstructor constructor) {
                    Intrinsics.checkNotNullParameter(constructor, "constructor");
                    Map<KtSecondaryConstructor, ClassConstructorDescriptor> secondaryConstructors = topDownAnalysisContext.getSecondaryConstructors();
                    DeclarationDescriptor declarationDescriptorResolveToDescriptor = this.lazyDeclarationResolver.resolveToDescriptor(constructor);
                    Intrinsics.checkNotNull(declarationDescriptorResolveToDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor");
                    secondaryConstructors.put(constructor, (ClassConstructorDescriptor) declarationDescriptorResolveToDescriptor);
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitEnumEntry(KtEnumEntry enumEntry) {
                    Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
                    visitClassOrObject(enumEntry);
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitObjectDeclaration(KtObjectDeclaration declaration) {
                    Intrinsics.checkNotNullParameter(declaration, "declaration");
                    visitClassOrObject(declaration);
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitAnonymousInitializer(KtAnonymousInitializer initializer) {
                    Intrinsics.checkNotNullParameter(initializer, "initializer");
                    DeclarationDescriptor declarationDescriptorResolveToDescriptor = this.lazyDeclarationResolver.resolveToDescriptor(initializer.getContainingDeclaration());
                    Intrinsics.checkNotNull(declarationDescriptorResolveToDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes");
                    topDownAnalysisContext.getAnonymousInitializers().put(initializer, (ClassDescriptorWithResolutionScopes) declarationDescriptorResolveToDescriptor);
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitDestructuringDeclaration(KtDestructuringDeclaration destructuringDeclaration) {
                    Intrinsics.checkNotNullParameter(destructuringDeclaration, "destructuringDeclaration");
                    if (destructuringDeclaration.getContainingKtFile().isScript()) {
                        arrayList4.add(destructuringDeclaration);
                    }
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitNamedFunction(KtNamedFunction function) {
                    Intrinsics.checkNotNullParameter(function, "function");
                    arrayList2.add(function);
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitProperty(KtProperty property) {
                    Intrinsics.checkNotNullParameter(property, "property");
                    arrayList5.add(property);
                }

                @Override // org.jetbrains.kotlin.psi.KtVisitorVoid
                public void visitTypeAlias(KtTypeAlias typeAlias) {
                    Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
                    arrayList3.add(typeAlias);
                }
            });
            psiElement.accept((PsiElementVisitor) objectRef.element);
            hashMultimapCreate = hashMultimapCreate;
            arrayList = arrayList;
        }
        HashMultimap hashMultimap2 = hashMultimapCreate;
        createFunctionDescriptors(topDownAnalysisContext, arrayList2);
        Intrinsics.checkNotNull(hashMultimap2);
        HashMultimap hashMultimap3 = hashMultimap2;
        createPropertyDescriptors(topDownAnalysisContext, hashMultimap3, arrayList);
        createPropertiesFromDestructuringDeclarations(topDownAnalysisContext, hashMultimap3, arrayList4);
        createTypeAliasDescriptors(topDownAnalysisContext, hashMultimap3, arrayList3);
        resolveAllHeadersInClasses(topDownAnalysisContext);
        this.declarationResolver.checkRedeclarationsInPackages(this.topLevelDescriptorProvider, hashMultimap3);
        this.declarationResolver.checkRedeclarations(topDownAnalysisContext);
        this.declarationResolver.resolveAnnotationsOnFiles(topDownAnalysisContext, this.fileScopeProvider);
        this.overrideResolver.check(topDownAnalysisContext);
        this.varianceChecker.check(topDownAnalysisContext);
        TopDownAnalysisContext topDownAnalysisContext2 = topDownAnalysisContext;
        this.overloadResolver.checkOverloads(topDownAnalysisContext2);
        this.bodyResolver.resolveBodies(topDownAnalysisContext2);
        resolveImportsInAllFiles(topDownAnalysisContext);
        ClassifierUsageCheckerKt.checkClassifierUsages(declarations, this.classifierUsageCheckers, new ClassifierUsageCheckerContext(this.trace, this.languageVersionSettings, this.deprecationResolver, this.moduleDescriptor));
        return topDownAnalysisContext;
    }

    private final void resolveAllHeadersInClasses(TopDownAnalysisContext c) {
        for (ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes : c.getAllClasses()) {
            Intrinsics.checkNotNull(classDescriptorWithResolutionScopes, "null cannot be cast to non-null type org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassDescriptor");
            ((LazyClassDescriptor) classDescriptorWithResolutionScopes).resolveMemberHeaders();
        }
    }

    private final void resolveImportsInAllFiles(TopDownAnalysisContext c) {
        Collection<KtFile> files = c.getFiles();
        Intrinsics.checkNotNullExpressionValue(files, "getFiles(...)");
        Set<KtScript> setKeySet = c.getScripts().keySet();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(setKeySet, 10));
        Iterator<T> it = setKeySet.iterator();
        while (it.hasNext()) {
            arrayList.add(((KtScript) it.next()).getContainingKtFile());
        }
        for (KtFile ktFile : CollectionsKt.plus((Collection) files, (Iterable) arrayList)) {
            Intrinsics.checkNotNull(ktFile);
            resolveImportsInFile(ktFile);
        }
    }

    public final void resolveImportsInFile(KtFile file) {
        Intrinsics.checkNotNullParameter(file, "file");
        this.fileScopeProvider.getImportResolver(file).forceResolveNonDefaultImports();
    }

    private final void createTypeAliasDescriptors(TopDownAnalysisContext c, Multimap<FqName, KtElement> topLevelFqNames, List<KtTypeAlias> typeAliases) {
        for (KtTypeAlias ktTypeAlias : typeAliases) {
            DeclarationDescriptor declarationDescriptorResolveToDescriptor = this.lazyDeclarationResolver.resolveToDescriptor(ktTypeAlias);
            Intrinsics.checkNotNull(declarationDescriptorResolveToDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.TypeAliasDescriptor");
            TypeAliasDescriptor typeAliasDescriptor = (TypeAliasDescriptor) declarationDescriptorResolveToDescriptor;
            Map<KtTypeAlias, TypeAliasDescriptor> typeAliases2 = c.getTypeAliases();
            Intrinsics.checkNotNullExpressionValue(typeAliases2, "getTypeAliases(...)");
            typeAliases2.put(ktTypeAlias, typeAliasDescriptor);
            ForceResolveUtil.forceResolveAllContents(typeAliasDescriptor.getAnnotations());
            registerTopLevelFqName(topLevelFqNames, ktTypeAlias, typeAliasDescriptor);
        }
    }

    private final void createPropertyDescriptors(TopDownAnalysisContext c, Multimap<FqName, KtElement> topLevelFqNames, List<? extends KtProperty> properties) {
        for (KtProperty ktProperty : properties) {
            DeclarationDescriptor declarationDescriptorResolveToDescriptor = this.lazyDeclarationResolver.resolveToDescriptor(ktProperty);
            Intrinsics.checkNotNull(declarationDescriptorResolveToDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) declarationDescriptorResolveToDescriptor;
            c.getProperties().put(ktProperty, propertyDescriptor);
            registerTopLevelFqName(topLevelFqNames, ktProperty, propertyDescriptor);
        }
    }

    private final void createFunctionDescriptors(TopDownAnalysisContext c, List<? extends KtNamedFunction> functions) {
        for (KtNamedFunction ktNamedFunction : functions) {
            DeclarationDescriptor declarationDescriptorResolveToDescriptor = this.lazyDeclarationResolver.resolveToDescriptor(ktNamedFunction);
            Intrinsics.checkNotNull(declarationDescriptorResolveToDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor");
            SimpleFunctionDescriptor simpleFunctionDescriptor = (SimpleFunctionDescriptor) declarationDescriptorResolveToDescriptor;
            c.getFunctions().put(ktNamedFunction, simpleFunctionDescriptor);
            ForceResolveUtil.forceResolveAllContents(simpleFunctionDescriptor.getAnnotations());
            Iterator<ValueParameterDescriptor> it = simpleFunctionDescriptor.getValueParameters().iterator();
            while (it.hasNext()) {
                ForceResolveUtil.forceResolveAllContents(it.next().getAnnotations());
            }
        }
    }

    private final void createPropertiesFromDestructuringDeclarations(TopDownAnalysisContext c, Multimap<FqName, KtElement> topLevelFqNames, List<? extends KtDestructuringDeclaration> destructuringDeclarations) {
        Iterator<? extends KtDestructuringDeclaration> it = destructuringDeclarations.iterator();
        while (it.hasNext()) {
            for (KtDestructuringDeclarationEntry ktDestructuringDeclarationEntry : it.next().getEntries()) {
                LazyDeclarationResolver lazyDeclarationResolver = this.lazyDeclarationResolver;
                Intrinsics.checkNotNull(ktDestructuringDeclarationEntry);
                DeclarationDescriptor declarationDescriptorResolveToDescriptor = lazyDeclarationResolver.resolveToDescriptor(ktDestructuringDeclarationEntry);
                Intrinsics.checkNotNull(declarationDescriptorResolveToDescriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.PropertyDescriptor");
                PropertyDescriptor propertyDescriptor = (PropertyDescriptor) declarationDescriptorResolveToDescriptor;
                Map<KtDestructuringDeclarationEntry, PropertyDescriptor> destructuringDeclarationEntries = c.getDestructuringDeclarationEntries();
                Intrinsics.checkNotNullExpressionValue(destructuringDeclarationEntries, "getDestructuringDeclarationEntries(...)");
                destructuringDeclarationEntries.put(ktDestructuringDeclarationEntry, propertyDescriptor);
                ForceResolveUtil.forceResolveAllContents(propertyDescriptor.getAnnotations());
                registerTopLevelFqName(topLevelFqNames, ktDestructuringDeclarationEntry, propertyDescriptor);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void registerTopLevelFqName(Multimap<FqName, KtElement> topLevelFqNames, KtNamedDeclaration declaration, DeclarationDescriptor descriptor) {
        FqName fqNameMo7391getFqName;
        if (!DescriptorUtils.isTopLevelDeclaration(descriptor) || (fqNameMo7391getFqName = declaration.mo7391getFqName()) == null) {
            return;
        }
        topLevelFqNames.put(fqNameMo7391getFqName, declaration);
    }
}
