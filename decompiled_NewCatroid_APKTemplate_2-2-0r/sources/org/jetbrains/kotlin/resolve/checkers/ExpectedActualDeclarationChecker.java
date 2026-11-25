package org.jetbrains.kotlin.resolve.checkers;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.com.intellij.openapi.vfs.VfsUtilCore;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.AnalysisFlags;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.ConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.MemberDescriptor;
import org.jetbrains.kotlin.descriptors.ModuleDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.descriptors.SourceFile;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.diagnostics.DiagnosticFactory2;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.components.ExpectActualTracker;
import org.jetbrains.kotlin.mpp.DeclarationSymbolMarker;
import org.jetbrains.kotlin.mpp.MppJavaImplicitActualizatorMarker;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.KtConstructor;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.psi.KtFunction;
import org.jetbrains.kotlin.psi.KtNamedDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtTypeAlias;
import org.jetbrains.kotlin.psi.psiUtil.PsiUtilsKt;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.ModulePath;
import org.jetbrains.kotlin.resolve.ModuleStructureOracle;
import org.jetbrains.kotlin.resolve.calls.mpp.AbstractExpectActualAnnotationMatchChecker;
import org.jetbrains.kotlin.resolve.calls.mpp.ExpectActualMatchingContext;
import org.jetbrains.kotlin.resolve.constants.CompileTimeConstant;
import org.jetbrains.kotlin.resolve.constants.ConstantValue;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.ClassicExpectActualMatchingContext;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectedActualResolver;
import org.jetbrains.kotlin.resolve.multiplatform.ModuleFilterUtilsKt;
import org.jetbrains.kotlin.resolve.multiplatform.OptionalAnnotationUtil;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.resolve.source.KotlinSourceElement;
import org.jetbrains.kotlin.resolve.source.PsiSourceFile;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.TypeConstructor;

/* JADX INFO: compiled from: ExpectedActualDeclarationChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000È\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 U2\u00020\u0001:\u0002TUB\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0016J@\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0012\u001a\u00020\u00132\u0016\u0010\u001a\u001a\u0012\u0012\u0004\u0012\u00020\u001c\u0012\u0004\u0012\u00020\u00190\u001bj\u0002`\u001dH\u0002J@\u0010\u001e\u001a\u00020\r2\u001e\u0010\u001f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\"0 2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u0017H\u0002J(\u0010%\u001a\u00020\r2\u0006\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020'2\u0006\u0010#\u001a\u00020$2\u0006\u0010\u0015\u001a\u00020)H\u0002J(\u0010*\u001a\u00020\r2\u0006\u0010+\u001a\u00020\u00172\u0006\u0010,\u001a\u00020\u00172\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\\\u0010-\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010+\u001a\u00020\u00172\"\u0010.\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\"0 j\u0002`/2\u0006\u0010#\u001a\u00020$2\u0006\u00100\u001a\u00020\u001c2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u00101\u001a\u000202H\u0002JR\u00103\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010+\u001a\u00020\u00172\"\u0010.\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\"0 j\u0002`/2\f\u00104\u001a\b\u0012\u0004\u0012\u0002050\"2\u0006\u0010#\u001a\u00020$H\u0002J0\u00106\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u00172\u0006\u0010#\u001a\u00020$2\u0006\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J \u00107\u001a\u00020\r2\u0006\u0010+\u001a\u00020\u00172\u0006\u00108\u001a\u00020\u00162\u0006\u0010\u0012\u001a\u00020\u0013H\u0002JL\u00109\u001a\u00020\r2\u0006\u0010:\u001a\u00020\u00162\u0006\u0010;\u001a\u00020\u00172\"\u0010.\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\"0 j\u0002`/2\u0006\u00100\u001a\u00020\u001c2\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J(\u0010<\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0010\u001a\u00020\u00172\u0006\u0010+\u001a\u00020\u00172\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J&\u0010=\u001a\b\u0012\u0002\b\u0003\u0018\u00010>2\u0006\u0010?\u001a\u00020@2\u0006\u0010A\u001a\u00020B2\u0006\u0010C\u001a\u00020DH\u0002J\u0016\u0010E\u001a\b\u0012\u0004\u0012\u00020F0\"2\u0006\u0010G\u001a\u00020HH\u0002J\u0010\u0010I\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u0017H\u0002J\"\u0010J\u001a\u00020\r2\u0006\u0010(\u001a\u00020\u00172\b\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010#\u001a\u00020$H\u0002J\u0010\u0010K\u001a\u00020\u00192\u0006\u0010\u0010\u001a\u00020\u0017H\u0002J\u0012\u0010L\u001a\u0004\u0018\u00010M2\u0006\u0010\u0010\u001a\u00020\u0017H\u0002J,\u0010N\u001a\u00020\u0019*\u00020\u00172\u001e\u0010\u001f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170!\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00170\"0 H\u0002J\f\u0010O\u001a\u00020\u0019*\u00020\u0017H\u0002J\f\u0010P\u001a\u00020\u0019*\u00020\u0017H\u0002J\"\u0010Q\u001a\u00020\r*\u0002022\u0006\u0010&\u001a\u00020\u00172\f\u0010R\u001a\b\u0012\u0004\u0012\u00020\u00170SH\u0002R\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b¨\u0006V"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ExpectedActualDeclarationChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "moduleStructureOracle", "Lorg/jetbrains/kotlin/resolve/ModuleStructureOracle;", "argumentExtractors", "", "Lorg/jetbrains/kotlin/resolve/checkers/ExpectedActualDeclarationChecker$ActualAnnotationArgumentExtractor;", "(Lorg/jetbrains/kotlin/resolve/ModuleStructureOracle;Ljava/lang/Iterable;)V", "getArgumentExtractors", "()Ljava/lang/Iterable;", "getModuleStructureOracle", "()Lorg/jetbrains/kotlin/resolve/ModuleStructureOracle;", "check", "", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "descriptor", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "context", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationCheckerContext;", "checkActualDeclarationHasExpected", "reportOn", "Lorg/jetbrains/kotlin/psi/KtNamedDeclaration;", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "checkActualModifier", "", "moduleVisibilityFilter", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/descriptors/ModuleDescriptor;", "Lorg/jetbrains/kotlin/resolve/multiplatform/ModuleFilter;", "checkAmbiguousExpects", "compatibility", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "checkAnnotationConstructors", "expected", "Lorg/jetbrains/kotlin/descriptors/ConstructorDescriptor;", "actual", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "checkAnnotationsMatch", "expectDescriptor", "actualDescriptor", "checkExpectedDeclarationHasAtLeastOneActual", "actuals", "Lorg/jetbrains/kotlin/resolve/checkers/ActualsMap;", "module", "expectActualTracker", "Lorg/jetbrains/kotlin/incremental/components/ExpectActualTracker;", "checkExpectedDeclarationHasAtMostOneActual", "modulePaths", "Lorg/jetbrains/kotlin/resolve/ModulePath;", "checkExpectedDeclarationHasProperActuals", "checkIfExpectHasDefaultArgumentsAndActualizedWithTypealias", "actualDeclaration", "checkImplicitJavaActualization", "expectPsi", "expect", "checkOptInAnnotation", "getActualAnnotationParameterValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "actualParameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "getMembersWithDefaultValueParametersUnlessAnnotation", "Lorg/jetbrains/kotlin/descriptors/FunctionDescriptor;", "classDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "isUnderlyingPropertyOfInlineClass", "reportMissingActualModifier", "requireActualModifier", "sourceFile", "Ljava/io/File;", "hasNoActualWithDiagnostic", "isActualOrSomeContainerIsActual", "isExplicitActualDeclaration", "reportExpectActual", "actualMembers", "Lkotlin/sequences/Sequence;", "ActualAnnotationArgumentExtractor", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExpectedActualDeclarationChecker implements DeclarationChecker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final Iterable<ActualAnnotationArgumentExtractor> argumentExtractors;
    private final ModuleStructureOracle moduleStructureOracle;

    /* JADX INFO: compiled from: ExpectedActualDeclarationChecker.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001e\u0010\u0002\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u00052\u0006\u0010\u0006\u001a\u00020\u0007H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\bÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ExpectedActualDeclarationChecker$ActualAnnotationArgumentExtractor;", "", "extractDefaultValue", "Lorg/jetbrains/kotlin/resolve/constants/ConstantValue;", "parameter", "Lorg/jetbrains/kotlin/descriptors/ValueParameterDescriptor;", "expectedType", "Lorg/jetbrains/kotlin/types/KotlinType;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface ActualAnnotationArgumentExtractor {
        ConstantValue<?> extractDefaultValue(ValueParameterDescriptor parameter, KotlinType expectedType);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ExpectedActualDeclarationChecker(ModuleStructureOracle moduleStructureOracle, Iterable<? extends ActualAnnotationArgumentExtractor> argumentExtractors) {
        Intrinsics.checkNotNullParameter(moduleStructureOracle, "moduleStructureOracle");
        Intrinsics.checkNotNullParameter(argumentExtractors, "argumentExtractors");
        this.moduleStructureOracle = moduleStructureOracle;
        this.argumentExtractors = argumentExtractors;
    }

    public final ModuleStructureOracle getModuleStructureOracle() {
        return this.moduleStructureOracle;
    }

    public final Iterable<ActualAnnotationArgumentExtractor> getArgumentExtractors() {
        return this.argumentExtractors;
    }

    @Override // org.jetbrains.kotlin.resolve.checkers.DeclarationChecker
    public void check(KtDeclaration declaration, DeclarationDescriptor descriptor, DeclarationCheckerContext context) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        Intrinsics.checkNotNullParameter(context, "context");
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.MultiPlatformProjects) && !((Boolean) context.getLanguageVersionSettings().getFlag(AnalysisFlags.getExpectActualLinker())).booleanValue() && !(descriptor instanceof PropertyAccessorDescriptor) && (declaration instanceof KtNamedDeclaration) && (descriptor instanceof MemberDescriptor) && !DescriptorUtils.isEnumEntry(descriptor)) {
            boolean z = !((Boolean) context.getLanguageVersionSettings().getFlag(AnalysisFlags.getMultiPlatformDoNotCheckActual())).booleanValue();
            MemberDescriptor memberDescriptor = (MemberDescriptor) descriptor;
            if (memberDescriptor.get$isExpect()) {
                KtNamedDeclaration ktNamedDeclaration = (KtNamedDeclaration) declaration;
                checkExpectedDeclarationHasProperActuals(ktNamedDeclaration, memberDescriptor, context.getTrace(), z, context);
                checkOptInAnnotation(ktNamedDeclaration, memberDescriptor, memberDescriptor, context);
            }
            if (isActualOrSomeContainerIsActual(memberDescriptor)) {
                List<ModulePath> listFindAllDependsOnPaths = this.moduleStructureOracle.findAllDependsOnPaths(DescriptorUtilsKt.getModule(descriptor));
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = listFindAllDependsOnPaths.iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(arrayList, ((ModulePath) it.next()).getNodes());
                }
                final HashSet hashSet = CollectionsKt.toHashSet(arrayList);
                checkActualDeclarationHasExpected((KtNamedDeclaration) declaration, memberDescriptor, z, context, new Function1<ModuleDescriptor, Boolean>() { // from class: org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker.check.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Boolean invoke(ModuleDescriptor it2) {
                        Intrinsics.checkNotNullParameter(it2, "it");
                        return Boolean.valueOf(hashSet.contains(it2));
                    }
                });
            }
        }
    }

    private final boolean isActualOrSomeContainerIsActual(MemberDescriptor memberDescriptor) {
        while (!memberDescriptor.isActual()) {
            DeclarationDescriptor containingDeclaration = memberDescriptor.getDeclarationDescriptor();
            memberDescriptor = containingDeclaration instanceof MemberDescriptor ? (MemberDescriptor) containingDeclaration : null;
            if (memberDescriptor == null) {
                return false;
            }
        }
        return true;
    }

    private final void checkExpectedDeclarationHasProperActuals(KtNamedDeclaration reportOn, MemberDescriptor descriptor, BindingTrace trace, boolean checkActualModifier, DeclarationCheckerContext context) {
        List<ModulePath> listFindAllReversedDependsOnPaths = this.moduleStructureOracle.findAllReversedDependsOnPaths(DescriptorUtilsKt.getModule(descriptor));
        List<ModulePath> list = listFindAllReversedDependsOnPaths;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add((ModuleDescriptor) CollectionsKt.last((List) ((ModulePath) it.next()).getNodes()));
        }
        for (ModuleDescriptor moduleDescriptor : CollectionsKt.toSet(arrayList)) {
            Map<ExpectActualCompatibility<MemberDescriptor>, ? extends List<? extends MemberDescriptor>> mapFindActualForExpected$default = ExpectedActualResolver.findActualForExpected$default(ExpectedActualResolver.INSTANCE, descriptor, moduleDescriptor, null, 4, null);
            if (mapFindActualForExpected$default != null) {
                checkExpectedDeclarationHasAtLeastOneActual(reportOn, descriptor, mapFindActualForExpected$default, trace, moduleDescriptor, checkActualModifier, context.getExpectActualTracker());
                checkImplicitJavaActualization(reportOn, descriptor, mapFindActualForExpected$default, moduleDescriptor, context);
                checkExpectedDeclarationHasAtMostOneActual(reportOn, descriptor, mapFindActualForExpected$default, listFindAllReversedDependsOnPaths, trace);
            }
        }
    }

    private final void checkImplicitJavaActualization(KtNamedDeclaration expectPsi, MemberDescriptor expect, Map<ExpectActualCompatibility<MemberDescriptor>, ? extends List<? extends MemberDescriptor>> actuals, ModuleDescriptor module, DeclarationCheckerContext context) {
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.MultiplatformRestrictions)) {
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            for (Map.Entry<ExpectActualCompatibility<MemberDescriptor>, ? extends List<? extends MemberDescriptor>> entry : actuals.entrySet()) {
                if (INSTANCE.isCompatibleOrWeakCompatible$frontend(entry.getKey())) {
                    linkedHashMap.put(entry.getKey(), entry.getValue());
                }
            }
            ArrayList arrayList = new ArrayList();
            Iterator it = linkedHashMap.entrySet().iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, (List) ((Map.Entry) it.next()).getValue());
            }
            ArrayList arrayList2 = arrayList;
            if (arrayList2.isEmpty()) {
                arrayList2 = null;
            }
            if (arrayList2 == null) {
                return;
            }
            ArrayList arrayList3 = arrayList2;
            if ((arrayList3 instanceof Collection) && arrayList3.isEmpty()) {
                return;
            }
            Iterator it2 = arrayList3.iterator();
            while (it2.hasNext()) {
                if (((MemberDescriptor) it2.next()) instanceof MppJavaImplicitActualizatorMarker) {
                    FqName fqName = ExpectedActualDeclarationCheckerKt.implicitlyActualizedAnnotationFqn;
                    BindingContext context2 = context.getTrace().getContext();
                    Intrinsics.checkNotNullExpressionValue(context2, "getBindingContext(...)");
                    if (!OptInUsageChecker.INSTANCE.isDeclarationAnnotatedWith$frontend(expectPsi, fqName, context2)) {
                        context.getTrace().report(Errors.IMPLICIT_JVM_ACTUALIZATION.on(expectPsi, expect, module));
                        return;
                    }
                }
            }
        }
    }

    private final void checkExpectedDeclarationHasAtLeastOneActual(KtNamedDeclaration reportOn, MemberDescriptor expectDescriptor, Map<ExpectActualCompatibility<MemberDescriptor>, ? extends List<? extends MemberDescriptor>> actuals, BindingTrace trace, ModuleDescriptor module, boolean checkActualModifier, ExpectActualTracker expectActualTracker) {
        if (expectDescriptor.getDeclarationDescriptor() instanceof PackageFragmentDescriptor) {
            Companion companion = INSTANCE;
            if (companion.allStrongIncompatibilities(actuals) && OptionalAnnotationUtil.isOptionalAnnotationClass(expectDescriptor)) {
                return;
            }
            if (companion.allStrongIncompatibilities(actuals) || (!actuals.containsKey(ExpectActualCompatibility.Compatible.INSTANCE) && hasNoActualWithDiagnostic(expectDescriptor, actuals))) {
                Set<ExpectActualCompatibility<MemberDescriptor>> setKeySet = actuals.keySet();
                if (!(setKeySet instanceof Collection) || !setKeySet.isEmpty()) {
                    Iterator<T> it = setKeySet.iterator();
                    while (it.hasNext() && (((ExpectActualCompatibility) it.next()) instanceof ExpectActualCompatibility.Incompatible)) {
                    }
                }
                Intrinsics.checkNotNull(actuals, "null cannot be cast to non-null type kotlin.collections.Map<org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility.Incompatible<org.jetbrains.kotlin.descriptors.MemberDescriptor>, kotlin.collections.Collection<org.jetbrains.kotlin.descriptors.MemberDescriptor>>");
                trace.report(Errors.NO_ACTUAL_FOR_EXPECT.on(reportOn, expectDescriptor, module, actuals));
                return;
            }
            Sequence<? extends MemberDescriptor> sequenceFlatMap = SequencesKt.flatMap(SequencesKt.filter(MapsKt.asSequence(actuals), new Function1<Map.Entry<? extends ExpectActualCompatibility<? extends MemberDescriptor>, ? extends List<? extends MemberDescriptor>>, Boolean>() { // from class: org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker$checkExpectedDeclarationHasAtLeastOneActual$actualMembers$1
                @Override // kotlin.jvm.functions.Function1
                public final Boolean invoke(Map.Entry<? extends ExpectActualCompatibility<? extends MemberDescriptor>, ? extends List<? extends MemberDescriptor>> it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return Boolean.valueOf(ExpectedActualDeclarationChecker.INSTANCE.isCompatibleOrWeakCompatible$frontend(it2.getKey()));
                }
            }), new Function1<Map.Entry<? extends ExpectActualCompatibility<? extends MemberDescriptor>, ? extends List<? extends MemberDescriptor>>, Sequence<? extends MemberDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker$checkExpectedDeclarationHasAtLeastOneActual$actualMembers$2
                @Override // kotlin.jvm.functions.Function1
                public final Sequence<MemberDescriptor> invoke(Map.Entry<? extends ExpectActualCompatibility<? extends MemberDescriptor>, ? extends List<? extends MemberDescriptor>> it2) {
                    Intrinsics.checkNotNullParameter(it2, "it");
                    return CollectionsKt.asSequence(it2.getValue());
                }
            });
            if (checkActualModifier) {
                Iterator<? extends MemberDescriptor> it2 = sequenceFlatMap.iterator();
                while (it2.hasNext()) {
                    reportMissingActualModifier(it2.next(), null, trace);
                }
            }
            reportExpectActual(expectActualTracker, expectDescriptor, sequenceFlatMap);
        }
    }

    private final void reportMissingActualModifier(MemberDescriptor actual, KtNamedDeclaration reportOn, BindingTrace trace) {
        if (actual.isActual()) {
            return;
        }
        if (reportOn == null) {
            SourceElement source = actual.getSourceElement();
            KotlinSourceElement kotlinSourceElement = source instanceof KotlinSourceElement ? (KotlinSourceElement) source : null;
            KtElement psi = kotlinSourceElement != null ? kotlinSourceElement.getPsi() : null;
            reportOn = psi instanceof KtNamedDeclaration ? (KtNamedDeclaration) psi : null;
            if (reportOn == null) {
                return;
            }
        }
        if (requireActualModifier(actual)) {
            trace.report(Errors.ACTUAL_MISSING.on(reportOn));
        }
    }

    private final void checkIfExpectHasDefaultArgumentsAndActualizedWithTypealias(MemberDescriptor expectDescriptor, KtNamedDeclaration actualDeclaration, DeclarationCheckerContext context) {
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.MultiplatformRestrictions) && (expectDescriptor instanceof ClassDescriptor) && (actualDeclaration instanceof KtTypeAlias)) {
            List<FunctionDescriptor> membersWithDefaultValueParametersUnlessAnnotation = getMembersWithDefaultValueParametersUnlessAnnotation((ClassDescriptor) expectDescriptor);
            if (membersWithDefaultValueParametersUnlessAnnotation.isEmpty()) {
                return;
            }
            context.getTrace().report(Errors.DEFAULT_ARGUMENTS_IN_EXPECT_WITH_ACTUAL_TYPEALIAS.on(actualDeclaration, expectDescriptor, membersWithDefaultValueParametersUnlessAnnotation));
        }
    }

    private final List<FunctionDescriptor> getMembersWithDefaultValueParametersUnlessAnnotation(ClassDescriptor classDescriptor) {
        ArrayList arrayList = new ArrayList();
        getMembersWithDefaultValueParametersUnlessAnnotation$collectFunctions(arrayList, classDescriptor);
        return arrayList;
    }

    private static final void getMembersWithDefaultValueParametersUnlessAnnotation$collectFunctions(List<FunctionDescriptor> list, ClassDescriptor classDescriptor) {
        if (classDescriptor.getKind() == ClassKind.ANNOTATION_CLASS) {
            return;
        }
        Collection<ClassConstructorDescriptor> constructors = classDescriptor.getConstructors();
        Intrinsics.checkNotNullExpressionValue(constructors, "getConstructors(...)");
        MemberScope unsubstitutedMemberScope = classDescriptor.getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope, "getUnsubstitutedMemberScope(...)");
        Collection contributedDescriptors$default = ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope, DescriptorKindFilter.FUNCTIONS, null, 2, null);
        ArrayList arrayList = new ArrayList();
        for (Object obj : contributedDescriptors$default) {
            if (obj instanceof FunctionDescriptor) {
                arrayList.add(obj);
            }
        }
        for (Object obj2 : CollectionsKt.plus((Collection) constructors, (Iterable) arrayList)) {
            List<ValueParameterDescriptor> valueParameters = ((FunctionDescriptor) obj2).getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            List<ValueParameterDescriptor> list2 = valueParameters;
            if (!(list2 instanceof Collection) || !list2.isEmpty()) {
                Iterator<T> it = list2.iterator();
                while (it.hasNext()) {
                    if (((ValueParameterDescriptor) it.next()).declaresDefaultValue()) {
                        list.add(obj2);
                        break;
                    }
                }
            }
        }
        MemberScope unsubstitutedMemberScope2 = classDescriptor.getUnsubstitutedMemberScope();
        Intrinsics.checkNotNullExpressionValue(unsubstitutedMemberScope2, "getUnsubstitutedMemberScope(...)");
        Collection contributedDescriptors$default2 = ResolutionScope.DefaultImpls.getContributedDescriptors$default(unsubstitutedMemberScope2, DescriptorKindFilter.CLASSIFIERS, null, 2, null);
        ArrayList arrayList2 = new ArrayList();
        for (Object obj3 : contributedDescriptors$default2) {
            if (obj3 instanceof ClassDescriptor) {
                arrayList2.add(obj3);
            }
        }
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            getMembersWithDefaultValueParametersUnlessAnnotation$collectFunctions(list, (ClassDescriptor) it2.next());
        }
    }

    private final boolean hasNoActualWithDiagnostic(MemberDescriptor memberDescriptor, Map<ExpectActualCompatibility<MemberDescriptor>, ? extends List<? extends MemberDescriptor>> map) {
        Collection<? extends List<? extends MemberDescriptor>> collectionValues = map.values();
        HashSet hashSet = new HashSet();
        Iterator<T> it = collectionValues.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(hashSet, (List) it.next());
        }
        HashSet hashSet2 = hashSet;
        if ((hashSet2 instanceof Collection) && hashSet2.isEmpty()) {
            return true;
        }
        Iterator it2 = hashSet2.iterator();
        while (it2.hasNext()) {
            Map mapFindExpectedForActual$default = ExpectedActualResolver.findExpectedForActual$default(ExpectedActualResolver.INSTANCE, (MemberDescriptor) it2.next(), ModuleFilterUtilsKt.onlyFromThisModule(DescriptorUtilsKt.getModule(memberDescriptor)), false, 4, null);
            if (mapFindExpectedForActual$default == null || !mapFindExpectedForActual$default.keySet().contains(ExpectActualCompatibility.Compatible.INSTANCE)) {
                return false;
            }
        }
        return true;
    }

    private final void reportExpectActual(ExpectActualTracker expectActualTracker, MemberDescriptor memberDescriptor, Sequence<? extends MemberDescriptor> sequence) {
        File fileSourceFile;
        if ((expectActualTracker instanceof ExpectActualTracker.DoNothing) || (fileSourceFile = sourceFile(memberDescriptor)) == null) {
            return;
        }
        Iterator<? extends MemberDescriptor> it = sequence.iterator();
        while (it.hasNext()) {
            File fileSourceFile2 = sourceFile(it.next());
            if (fileSourceFile2 != null) {
                expectActualTracker.report(fileSourceFile, fileSourceFile2);
            }
        }
    }

    private final File sourceFile(MemberDescriptor descriptor) {
        SourceFile containingFile = descriptor.getSourceElement().getContainingFile();
        PsiSourceFile psiSourceFile = containingFile instanceof PsiSourceFile ? (PsiSourceFile) containingFile : null;
        if (psiSourceFile == null) {
            return null;
        }
        return VfsUtilCore.virtualToIoFile(psiSourceFile.getPsiFile().getVirtualFile());
    }

    /* JADX WARN: Code duplicated, block: B:79:0x0150 A[EDGE_INSN: B:79:0x0150->B:109:0x01de BREAK  A[LOOP:2: B:74:0x0130->B:127:?]] */
    private final void checkActualDeclarationHasExpected(KtNamedDeclaration reportOn, MemberDescriptor descriptor, boolean checkActualModifier, DeclarationCheckerContext context, Function1<? super ModuleDescriptor, Boolean> moduleVisibilityFilter) {
        ClassConstructorDescriptor underlyingConstructorDescriptor;
        Collection<TypeAliasConstructorDescriptor> constructors;
        TypeAliasConstructorDescriptor typeAliasConstructorDescriptor;
        Collection<ClassConstructorDescriptor> constructors2;
        SimpleType expandedType;
        TypeConstructor constructor;
        BindingTrace trace = context.getTrace();
        Map<ExpectActualCompatibility<MemberDescriptor>, List<MemberDescriptor>> mapFindExpectedForActual = ExpectedActualResolver.INSTANCE.findExpectedForActual(descriptor, moduleVisibilityFilter, true);
        if (mapFindExpectedForActual == null) {
            return;
        }
        checkAmbiguousExpects(mapFindExpectedForActual, trace, reportOn, descriptor);
        if (checkActualModifier && !(descriptor.getDeclarationDescriptor() instanceof PackageFragmentDescriptor) && !mapFindExpectedForActual.isEmpty()) {
            Iterator<Map.Entry<ExpectActualCompatibility<MemberDescriptor>, List<MemberDescriptor>>> it = mapFindExpectedForActual.entrySet().iterator();
            while (it.hasNext()) {
                if (INSTANCE.isCompatibleOrWeakCompatible$frontend(it.next().getKey())) {
                    reportMissingActualModifier(descriptor, reportOn, trace);
                    break;
                }
            }
        }
        if (PsiUtilsKt.hasActualModifier(reportOn) || !INSTANCE.allStrongIncompatibilities(mapFindExpectedForActual)) {
            ExpectActualCompatibility expectActualCompatibility = (ExpectActualCompatibility) CollectionsKt.firstOrNull(mapFindExpectedForActual.keySet());
            if (expectActualCompatibility instanceof ExpectActualCompatibility.Incompatible.ClassScopes) {
                if (!(descriptor instanceof ClassDescriptor)) {
                    boolean z = descriptor instanceof TypeAliasDescriptor;
                }
                List unfulfilled = ((ExpectActualCompatibility.Incompatible.ClassScopes) expectActualCompatibility).getUnfulfilled();
                ArrayList arrayList = new ArrayList();
                for (Object obj : unfulfilled) {
                    if (!checkActualDeclarationHasExpected$hasSingleActualSuspect(this, (Pair) obj)) {
                        arrayList.add(obj);
                    }
                }
                ArrayList arrayList2 = arrayList;
                if (!arrayList2.isEmpty()) {
                    TypeAliasDescriptor typeAliasDescriptor = descriptor instanceof TypeAliasDescriptor ? (TypeAliasDescriptor) descriptor : null;
                    ClassifierDescriptor this$0 = (typeAliasDescriptor == null || (expandedType = typeAliasDescriptor.getExpandedType()) == null || (constructor = expandedType.getConstructor()) == null) ? null : constructor.getThis$0();
                    ClassDescriptor classDescriptor = this$0 instanceof ClassDescriptor ? (ClassDescriptor) this$0 : null;
                    if (classDescriptor == null) {
                        Intrinsics.checkNotNull(descriptor, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.ClassDescriptor");
                        classDescriptor = (ClassDescriptor) descriptor;
                    }
                    trace.report(Errors.NO_ACTUAL_CLASS_MEMBER_FOR_EXPECTED_CLASS.on(reportOn, classDescriptor, arrayList2));
                }
            } else if (!mapFindExpectedForActual.containsKey(ExpectActualCompatibility.Compatible.INSTANCE)) {
                Set<ExpectActualCompatibility<MemberDescriptor>> setKeySet = mapFindExpectedForActual.keySet();
                if (!(setKeySet instanceof Collection) || !setKeySet.isEmpty()) {
                    Iterator<T> it2 = setKeySet.iterator();
                    while (it2.hasNext() && (((ExpectActualCompatibility) it2.next()) instanceof ExpectActualCompatibility.Incompatible)) {
                    }
                }
                if (!(reportOn instanceof KtFunction)) {
                    trace.report(Errors.ACTUAL_WITHOUT_EXPECT.on(reportOn, descriptor, (Map<ExpectActualCompatibility.Incompatible<MemberDescriptor>, Collection<MemberDescriptor>>) mapFindExpectedForActual));
                    break;
                }
                Set<ExpectActualCompatibility<MemberDescriptor>> setKeySet2 = mapFindExpectedForActual.keySet();
                if ((setKeySet2 instanceof Collection) && setKeySet2.isEmpty()) {
                    trace.report(Errors.ACTUAL_WITHOUT_EXPECT.on(reportOn, descriptor, (Map<ExpectActualCompatibility.Incompatible<MemberDescriptor>, Collection<MemberDescriptor>>) mapFindExpectedForActual));
                    break;
                }
                Iterator<T> it3 = setKeySet2.iterator();
                while (true) {
                    if (it3.hasNext()) {
                        if (((ExpectActualCompatibility.Incompatible) it3.next()) instanceof ExpectActualCompatibility.Incompatible.ActualFunctionWithDefaultParameters) {
                            trace.report(Errors.ACTUAL_FUNCTION_WITH_DEFAULT_ARGUMENTS.on(reportOn));
                            break;
                        }
                    } else {
                        trace.report(Errors.ACTUAL_WITHOUT_EXPECT.on(reportOn, descriptor, (Map<ExpectActualCompatibility.Incompatible<MemberDescriptor>, Collection<MemberDescriptor>>) mapFindExpectedForActual));
                        break;
                    }
                }
            } else {
                List<MemberDescriptor> list = mapFindExpectedForActual.get(ExpectActualCompatibility.Compatible.INSTANCE);
                Intrinsics.checkNotNull(list);
                MemberDescriptor memberDescriptor = (MemberDescriptor) CollectionsKt.first((List) list);
                if (memberDescriptor instanceof ClassDescriptor) {
                    ClassDescriptor classDescriptor2 = (ClassDescriptor) memberDescriptor;
                    if (classDescriptor2.getKind() == ClassKind.ANNOTATION_CLASS) {
                        ClassDescriptor classDescriptor3 = descriptor instanceof ClassDescriptor ? (ClassDescriptor) descriptor : null;
                        if (classDescriptor3 == null || (constructors2 = classDescriptor3.getConstructors()) == null || (underlyingConstructorDescriptor = (ClassConstructorDescriptor) CollectionsKt.singleOrNull(constructors2)) == null) {
                            TypeAliasDescriptor typeAliasDescriptor2 = descriptor instanceof TypeAliasDescriptor ? (TypeAliasDescriptor) descriptor : null;
                            underlyingConstructorDescriptor = (typeAliasDescriptor2 == null || (constructors = typeAliasDescriptor2.getConstructors()) == null || (typeAliasConstructorDescriptor = (TypeAliasConstructorDescriptor) CollectionsKt.singleOrNull(constructors)) == null) ? null : typeAliasConstructorDescriptor.getUnderlyingConstructorDescriptor();
                        }
                        Collection<ClassConstructorDescriptor> constructors3 = classDescriptor2.getConstructors();
                        Intrinsics.checkNotNullExpressionValue(constructors3, "getConstructors(...)");
                        ClassConstructorDescriptor classConstructorDescriptor = (ClassConstructorDescriptor) CollectionsKt.singleOrNull(constructors3);
                        if (classConstructorDescriptor != null && underlyingConstructorDescriptor != null) {
                            checkAnnotationConstructors(classConstructorDescriptor, underlyingConstructorDescriptor, trace, reportOn);
                        }
                        checkOptInAnnotation(reportOn, descriptor, memberDescriptor, context);
                    }
                }
            }
            List<MemberDescriptor> list2 = mapFindExpectedForActual.get(ExpectActualCompatibility.Compatible.INSTANCE);
            if (list2 == null) {
                list2 = (List) CollectionsKt.singleOrNull(mapFindExpectedForActual.values());
            }
            MemberDescriptor memberDescriptor2 = list2 != null ? (MemberDescriptor) CollectionsKt.singleOrNull((List) list2) : null;
            if (memberDescriptor2 != null) {
                checkIfExpectHasDefaultArgumentsAndActualizedWithTypealias(memberDescriptor2, reportOn, context);
                checkAnnotationsMatch(memberDescriptor2, descriptor, reportOn, context);
            }
        }
    }

    private static final boolean checkActualDeclarationHasExpected$hasSingleActualSuspect(ExpectedActualDeclarationChecker expectedActualDeclarationChecker, Pair<? extends MemberDescriptor, ? extends Map<ExpectActualCompatibility.Incompatible<MemberDescriptor>, ? extends Collection<? extends MemberDescriptor>>> pair) {
        Collection collectionValues;
        List list;
        MemberDescriptor memberDescriptorComponent1 = pair.component1();
        Map<ExpectActualCompatibility.Incompatible<MemberDescriptor>, ? extends Collection<? extends MemberDescriptor>> mapComponent2 = pair.component2();
        Collection collection = (Collection) CollectionsKt.singleOrNull(mapComponent2.values());
        MemberDescriptor memberDescriptor = null;
        MemberDescriptor memberDescriptor2 = collection != null ? (MemberDescriptor) CollectionsKt.singleOrNull(collection) : null;
        if (memberDescriptor2 != null && expectedActualDeclarationChecker.isExplicitActualDeclaration(memberDescriptor2) && !INSTANCE.allStrongIncompatibilities(mapComponent2)) {
            Map mapFindExpectedForActual$default = ExpectedActualResolver.findExpectedForActual$default(ExpectedActualResolver.INSTANCE, memberDescriptor2, ModuleFilterUtilsKt.onlyFromThisModule(DescriptorUtilsKt.getModule(memberDescriptorComponent1)), false, 4, null);
            if (mapFindExpectedForActual$default != null && (collectionValues = mapFindExpectedForActual$default.values()) != null && (list = (List) CollectionsKt.singleOrNull(collectionValues)) != null) {
                memberDescriptor = (MemberDescriptor) CollectionsKt.singleOrNull(list);
            }
            if (Intrinsics.areEqual(memberDescriptor, memberDescriptorComponent1)) {
                return true;
            }
        }
        return false;
    }

    private final void checkAmbiguousExpects(Map<ExpectActualCompatibility<MemberDescriptor>, ? extends List<? extends MemberDescriptor>> compatibility, BindingTrace trace, KtNamedDeclaration reportOn, MemberDescriptor descriptor) {
        List list = SequencesKt.toList(SequencesKt.sortedWith(SequencesKt.map(SequencesKt.flattenSequenceOfIterable(SequencesKt.map(SequencesKt.filter(MapsKt.asSequence(compatibility), new Function1<Map.Entry<? extends ExpectActualCompatibility<? extends MemberDescriptor>, ? extends List<? extends MemberDescriptor>>, Boolean>() { // from class: org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker$checkAmbiguousExpects$filesWithAtLeastWeaklyCompatibleExpects$1
            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(Map.Entry<? extends ExpectActualCompatibility<? extends MemberDescriptor>, ? extends List<? extends MemberDescriptor>> entry) {
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                return Boolean.valueOf(ExpectedActualDeclarationChecker.INSTANCE.isCompatibleOrWeakCompatible$frontend(entry.getKey()));
            }
        }), new Function1<Map.Entry<? extends ExpectActualCompatibility<? extends MemberDescriptor>, ? extends List<? extends MemberDescriptor>>, List<? extends MemberDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker$checkAmbiguousExpects$filesWithAtLeastWeaklyCompatibleExpects$2
            @Override // kotlin.jvm.functions.Function1
            public final List<MemberDescriptor> invoke(Map.Entry<? extends ExpectActualCompatibility<? extends MemberDescriptor>, ? extends List<? extends MemberDescriptor>> entry) {
                Intrinsics.checkNotNullParameter(entry, "<name for destructuring parameter 0>");
                return (List) entry.getValue();
            }
        })), new Function1<MemberDescriptor, ModuleDescriptor>() { // from class: org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker$checkAmbiguousExpects$filesWithAtLeastWeaklyCompatibleExpects$3
            @Override // kotlin.jvm.functions.Function1
            public final ModuleDescriptor invoke(MemberDescriptor it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return DescriptorUtilsKt.getModule(it);
            }
        }), new Comparator() { // from class: org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker$checkAmbiguousExpects$$inlined$sortedBy$1
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((ModuleDescriptor) t).getShortName().asString(), ((ModuleDescriptor) t2).getShortName().asString());
            }
        }));
        if (list.size() > 1) {
            trace.report(Errors.AMBIGUOUS_EXPECTS.on(reportOn, descriptor, list));
        }
    }

    private final boolean requireActualModifier(MemberDescriptor descriptor) {
        MemberDescriptor memberDescriptor = descriptor;
        return (DescriptorUtilsKt.isAnnotationConstructor(memberDescriptor) || DescriptorUtilsKt.isPrimaryConstructorOfInlineClass(memberDescriptor) || isUnderlyingPropertyOfInlineClass(descriptor)) ? false : true;
    }

    private final boolean isUnderlyingPropertyOfInlineClass(MemberDescriptor descriptor) {
        return (descriptor instanceof PropertyDescriptor) && InlineClassesUtilsKt.isUnderlyingPropertyOfInlineClass((VariableDescriptor) descriptor);
    }

    private final boolean isExplicitActualDeclaration(MemberDescriptor memberDescriptor) {
        if (memberDescriptor instanceof ConstructorDescriptor) {
            return DescriptorToSourceUtils.getSourceFromDescriptor(memberDescriptor) instanceof KtConstructor;
        }
        return !(memberDescriptor instanceof CallableMemberDescriptor) || ((CallableMemberDescriptor) memberDescriptor).getKind() == CallableMemberDescriptor.Kind.DECLARATION;
    }

    private final void checkAnnotationConstructors(ConstructorDescriptor expected, ConstructorDescriptor actual, BindingTrace trace, PsiElement reportOn) {
        ValueParameterDescriptor valueParameterDescriptor;
        ConstantValue constantValue;
        KtExpression ktExpression;
        KtExpression defaultValue;
        for (ValueParameterDescriptor valueParameterDescriptor2 : expected.getValueParameters()) {
            List<ValueParameterDescriptor> valueParameters = actual.getValueParameters();
            Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
            Iterator<T> it = valueParameters.iterator();
            do {
                if (!it.hasNext()) {
                    throw new NoSuchElementException("Collection contains no element matching the predicate.");
                }
                valueParameterDescriptor = (ValueParameterDescriptor) it.next();
            } while (!Intrinsics.areEqual(valueParameterDescriptor.getShortName(), valueParameterDescriptor2.getShortName()));
            if (valueParameterDescriptor2.declaresDefaultValue() && valueParameterDescriptor.declaresDefaultValue()) {
                Intrinsics.checkNotNull(valueParameterDescriptor2);
                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(valueParameterDescriptor2);
                KtParameter ktParameter = psiElementDescriptorToDeclaration instanceof KtParameter ? (KtParameter) psiElementDescriptorToDeclaration : null;
                if (ktParameter != null) {
                    CompileTimeConstant compileTimeConstant = (CompileTimeConstant) trace.getContext().get(BindingContext.COMPILE_TIME_VALUE, ktParameter.getDefaultValue());
                    if (compileTimeConstant != null) {
                        KotlinType type = valueParameterDescriptor2.getType();
                        Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
                        constantValue = compileTimeConstant.toConstantValue(type);
                    } else {
                        constantValue = null;
                    }
                    Intrinsics.checkNotNull(valueParameterDescriptor);
                    BindingContext context = trace.getContext();
                    Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
                    KotlinType type2 = valueParameterDescriptor2.getType();
                    Intrinsics.checkNotNullExpressionValue(type2, "getType(...)");
                    if (!Intrinsics.areEqual(constantValue, getActualAnnotationParameterValue(valueParameterDescriptor, context, type2))) {
                        PsiElement psiElementDescriptorToDeclaration2 = DescriptorToSourceUtils.descriptorToDeclaration(valueParameterDescriptor);
                        KtParameter ktParameter2 = psiElementDescriptorToDeclaration2 instanceof KtParameter ? (KtParameter) psiElementDescriptorToDeclaration2 : null;
                        if (ktParameter2 == null || (defaultValue = ktParameter2.getDefaultValue()) == null) {
                            KtTypeAlias ktTypeAlias = reportOn instanceof KtTypeAlias ? (KtTypeAlias) reportOn : null;
                            PsiElement nameIdentifier = ktTypeAlias != null ? ktTypeAlias.mo7392getNameIdentifier() : null;
                            ktExpression = nameIdentifier == null ? reportOn : nameIdentifier;
                        } else {
                            ktExpression = defaultValue;
                        }
                        Intrinsics.checkNotNull(ktExpression);
                        trace.report(Errors.ACTUAL_ANNOTATION_CONFLICTING_DEFAULT_ARGUMENT_VALUE.on(ktExpression, valueParameterDescriptor));
                    }
                }
            }
        }
    }

    private final ConstantValue<?> getActualAnnotationParameterValue(ValueParameterDescriptor actualParameter, BindingContext bindingContext, KotlinType expectedType) {
        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(actualParameter);
        if (psiElementDescriptorToDeclaration instanceof KtParameter) {
            CompileTimeConstant compileTimeConstant = (CompileTimeConstant) bindingContext.get(BindingContext.COMPILE_TIME_VALUE, ((KtParameter) psiElementDescriptorToDeclaration).getDefaultValue());
            if (compileTimeConstant != null) {
                return compileTimeConstant.toConstantValue(expectedType);
            }
            return null;
        }
        Iterator<ActualAnnotationArgumentExtractor> it = this.argumentExtractors.iterator();
        while (it.hasNext()) {
            ConstantValue<?> constantValueExtractDefaultValue = it.next().extractDefaultValue(actualParameter, expectedType);
            if (constantValueExtractDefaultValue != null) {
                return constantValueExtractDefaultValue;
            }
        }
        return null;
    }

    private final void checkOptInAnnotation(KtNamedDeclaration reportOn, MemberDescriptor descriptor, MemberDescriptor expectDescriptor, DeclarationCheckerContext context) {
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.MultiplatformRestrictions) && (descriptor instanceof ClassDescriptor) && ((ClassDescriptor) descriptor).getKind() == ClassKind.ANNOTATION_CLASS && descriptor.getAnnotations().hasAnnotation(OptInNames.INSTANCE.getREQUIRES_OPT_IN_FQ_NAME()) && !expectDescriptor.getAnnotations().hasAnnotation(OptionalAnnotationUtil.INSTANCE.getOPTIONAL_EXPECTATION_FQ_NAME())) {
            context.getTrace().report(Errors.EXPECT_ACTUAL_OPT_IN_ANNOTATION.on(reportOn));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private final void checkAnnotationsMatch(MemberDescriptor expectDescriptor, MemberDescriptor actualDescriptor, KtNamedDeclaration reportOn, DeclarationCheckerContext context) {
        if (context.getLanguageVersionSettings().supportsFeature(LanguageFeature.MultiplatformRestrictions)) {
            AbstractExpectActualAnnotationMatchChecker.Incompatibility incompatibilityAreAnnotationsCompatible = AbstractExpectActualAnnotationMatchChecker.INSTANCE.areAnnotationsCompatible(expectDescriptor, actualDescriptor, new ClassicExpectActualMatchingContext(DescriptorUtilsKt.getModule(actualDescriptor), false, 2, null));
            if (incompatibilityAreAnnotationsCompatible == null) {
                return;
            }
            BindingTrace trace = context.getTrace();
            DeclarationSymbolMarker expectSymbol = incompatibilityAreAnnotationsCompatible.getExpectSymbol();
            Intrinsics.checkNotNull(expectSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.DeclarationDescriptor");
            DeclarationSymbolMarker actualSymbol = incompatibilityAreAnnotationsCompatible.getActualSymbol();
            Intrinsics.checkNotNull(actualSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.DeclarationDescriptor");
            trace.report(Errors.ACTUAL_ANNOTATIONS_NOT_MATCH_EXPECT.on(reportOn, (DeclarationDescriptor) expectSymbol, (DeclarationDescriptor) actualSymbol, incompatibilityAreAnnotationsCompatible.getType().mapAnnotationType(new Function1<ExpectActualMatchingContext.AnnotationCallInfo, AnnotationDescriptor>() { // from class: org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker.checkAnnotationsMatch.1
                @Override // kotlin.jvm.functions.Function1
                public final AnnotationDescriptor invoke(ExpectActualMatchingContext.AnnotationCallInfo it) {
                    Intrinsics.checkNotNullParameter(it, "it");
                    Object annotationSymbol = it.getAnnotationSymbol();
                    Intrinsics.checkNotNull(annotationSymbol, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor");
                    return (AnnotationDescriptor) annotationSymbol;
                }
            })));
        }
    }

    /* JADX INFO: compiled from: ExpectedActualDeclarationChecker.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J$\u0010\u0003\u001a\u00020\u0004*\u001c\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u00020\u00070\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00070\b0\u0005J\u0017\u0010\t\u001a\u00020\u0004*\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0000¢\u0006\u0002\b\n¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/ExpectedActualDeclarationChecker$Companion;", "", "()V", "allStrongIncompatibilities", "", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "Lorg/jetbrains/kotlin/descriptors/MemberDescriptor;", "", "isCompatibleOrWeakCompatible", "isCompatibleOrWeakCompatible$frontend", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final boolean allStrongIncompatibilities(Map<? extends ExpectActualCompatibility<? extends MemberDescriptor>, ? extends Collection<? extends MemberDescriptor>> map) {
            Intrinsics.checkNotNullParameter(map, "<this>");
            Set<? extends ExpectActualCompatibility<? extends MemberDescriptor>> setKeySet = map.keySet();
            if ((setKeySet instanceof Collection) && setKeySet.isEmpty()) {
                return true;
            }
            Iterator<T> it = setKeySet.iterator();
            while (it.hasNext()) {
                if (!(((ExpectActualCompatibility) it.next()) instanceof ExpectActualCompatibility.Incompatible.StrongIncompatible)) {
                    return false;
                }
            }
            return true;
        }

        public final boolean isCompatibleOrWeakCompatible$frontend(ExpectActualCompatibility<? extends MemberDescriptor> expectActualCompatibility) {
            Intrinsics.checkNotNullParameter(expectActualCompatibility, "<this>");
            return (expectActualCompatibility instanceof ExpectActualCompatibility.Compatible) || (expectActualCompatibility instanceof ExpectActualCompatibility.Incompatible.WeakIncompatible);
        }
    }

    private final void checkExpectedDeclarationHasAtMostOneActual(KtNamedDeclaration reportOn, MemberDescriptor expectDescriptor, Map<ExpectActualCompatibility<MemberDescriptor>, ? extends List<? extends MemberDescriptor>> actuals, List<ModulePath> modulePaths, BindingTrace trace) {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        for (Map.Entry<ExpectActualCompatibility<MemberDescriptor>, ? extends List<? extends MemberDescriptor>> entry : actuals.entrySet()) {
            if (INSTANCE.isCompatibleOrWeakCompatible$frontend(entry.getKey())) {
                linkedHashMap.put(entry.getKey(), entry.getValue());
            }
        }
        List listFlatten = CollectionsKt.flatten(linkedHashMap.values());
        if (listFlatten.size() <= 1) {
            return;
        }
        List<ModulePath> list = modulePaths;
        LinkedHashMap linkedHashMap2 = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(CollectionsKt.collectionSizeOrDefault(list, 10)), 16));
        for (Object obj : list) {
            LinkedHashMap linkedHashMap3 = linkedHashMap2;
            ModulePath modulePath = (ModulePath) obj;
            ArrayList arrayList = new ArrayList();
            for (Object obj2 : listFlatten) {
                if (modulePath.getNodes().contains(DescriptorUtilsKt.getModule((MemberDescriptor) obj2))) {
                    arrayList.add(obj2);
                }
            }
            linkedHashMap3.put(obj, arrayList);
        }
        Iterator it = linkedHashMap2.entrySet().iterator();
        while (it.hasNext()) {
            List list2 = (List) ((Map.Entry) it.next()).getValue();
            if (list2.size() > 1) {
                DiagnosticFactory2<KtNamedDeclaration, DeclarationDescriptor, Collection<ModuleDescriptor>> diagnosticFactory2 = Errors.AMBIGUOUS_ACTUALS;
                KtNamedDeclaration ktNamedDeclaration = reportOn;
                List list3 = list2;
                ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list3, 10));
                Iterator it2 = list3.iterator();
                while (it2.hasNext()) {
                    arrayList2.add(DescriptorUtilsKt.getModule((MemberDescriptor) it2.next()));
                }
                trace.report(diagnosticFactory2.on(ktNamedDeclaration, expectDescriptor, CollectionsKt.sortedWith(arrayList2, new Comparator() { // from class: org.jetbrains.kotlin.resolve.checkers.ExpectedActualDeclarationChecker$checkExpectedDeclarationHasAtMostOneActual$lambda$12$$inlined$sortedBy$1
                    @Override // java.util.Comparator
                    public final int compare(T t, T t2) {
                        return ComparisonsKt.compareValues(((ModuleDescriptor) t).getShortName().asString(), ((ModuleDescriptor) t2).getShortName().asString());
                    }
                })));
            }
        }
    }
}
