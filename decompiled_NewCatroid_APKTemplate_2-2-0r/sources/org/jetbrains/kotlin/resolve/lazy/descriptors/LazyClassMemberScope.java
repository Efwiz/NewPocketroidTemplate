package org.jetbrains.kotlin.resolve.lazy.descriptors;

import androidx.exifinterface.media.ExifInterface;
import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.PropertyReference1Impl;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KProperty;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassConstructorDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptorWithResolutionScopes;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.DeserializedDeclarationsFromSupertypeConflictDataKey;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.SimpleFunctionDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.impl.ClassConstructorDescriptorImpl;
import org.jetbrains.kotlin.descriptors.impl.FunctionDescriptorImpl;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.diagnostics.Errors;
import org.jetbrains.kotlin.incremental.UtilsKt;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.incremental.components.NoLookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.psi.KtClassOrObject;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtParameter;
import org.jetbrains.kotlin.psi.KtPureClassOrObject;
import org.jetbrains.kotlin.psi.KtSecondaryConstructor;
import org.jetbrains.kotlin.psi.KtTypeReference;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTrace;
import org.jetbrains.kotlin.resolve.DataClassDescriptorResolver;
import org.jetbrains.kotlin.resolve.DelegationResolver;
import org.jetbrains.kotlin.resolve.DescriptorResolver;
import org.jetbrains.kotlin.resolve.DescriptorToSourceUtils;
import org.jetbrains.kotlin.resolve.DescriptorUtils;
import org.jetbrains.kotlin.resolve.FunctionDescriptorResolver;
import org.jetbrains.kotlin.resolve.FunctionsFromAny;
import org.jetbrains.kotlin.resolve.InlineClassesUtilsKt;
import org.jetbrains.kotlin.resolve.OverrideResolver;
import org.jetbrains.kotlin.resolve.OverridingStrategy;
import org.jetbrains.kotlin.resolve.OverridingUtil;
import org.jetbrains.kotlin.resolve.TypeResolver;
import org.jetbrains.kotlin.resolve.VarianceCheckerCore;
import org.jetbrains.kotlin.resolve.extensions.SyntheticResolveExtension;
import org.jetbrains.kotlin.resolve.lazy.LazyClassContext;
import org.jetbrains.kotlin.resolve.lazy.declarations.ClassMemberDeclarationProvider;
import org.jetbrains.kotlin.resolve.scopes.DescriptorKindFilter;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.MemberScope;
import org.jetbrains.kotlin.resolve.scopes.ResolutionScope;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.NullableLazyValue;
import org.jetbrains.kotlin.storage.StorageKt;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.SimpleType;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.NewKotlinTypeCheckerImpl;

/* JADX INFO: compiled from: LazyClassMemberScope.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000Þ\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010#\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u001f\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0016\u0018\u0000 s2\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001:\u0002stB;\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0002\u0012\u0006\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0000¢\u0006\u0002\u0010\rJ\u001e\u0010/\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u00020#022\u0006\u00103\u001a\u000204H\u0002J\u001e\u00105\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u00020#022\u0006\u00103\u001a\u000204H\u0002J\u001e\u00106\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u00020#022\u0006\u00103\u001a\u000204H\u0002J\u001e\u00107\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u00020#022\u0006\u00103\u001a\u000204H\u0002J\u0016\u00108\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u00020'02H\u0002J\u001e\u00109\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u00020#022\u0006\u00103\u001a\u000204H\u0002J\u001e\u0010:\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u00020#022\u0006\u00103\u001a\u000204H\u0014J\u001e\u0010;\u001a\u0002002\u0006\u0010<\u001a\u00020\u00102\f\u00101\u001a\b\u0012\u0004\u0012\u00020=0\u0019H\u0014J\u0010\u0010>\u001a\u00020?2\u0006\u0010<\u001a\u00020\u0010H\u0016J\"\u0010@\u001a\b\u0012\u0004\u0012\u00020#0\"2\u0012\u0010A\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020?0BH\u0002J\"\u0010C\u001a\b\u0012\u0004\u0012\u00020#0\"2\u0012\u0010A\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020?0BH\u0002J\u000e\u0010D\u001a\b\u0012\u0004\u0012\u00020'0)H\u0002J4\u0010E\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u00020F022\u0006\u0010<\u001a\u00020\u00102\u0006\u00103\u001a\u0002042\f\u0010G\u001a\b\u0012\u0004\u0012\u00020F0\"H\u0002J<\u0010H\u001a\b\u0012\u0004\u0012\u0002HI0)\"\b\b\u0000\u0010I*\u00020J2\u0006\u0010<\u001a\u00020\u00102\f\u0010K\u001a\b\u0012\u0004\u0012\u0002HI0L2\f\u0010M\u001a\b\u0012\u0004\u0012\u00020N0)H\u0002JF\u0010O\u001a\u000200\"\b\b\u0000\u0010P*\u00020J2\u0006\u0010<\u001a\u00020\u00102\f\u0010G\u001a\b\u0012\u0004\u0012\u0002HP0)2\f\u00101\u001a\b\u0012\u0004\u0012\u0002HP022\u000e\u0010Q\u001a\n\u0012\u0006\b\u0001\u0012\u0002HP0RH\u0002J,\u0010S\u001a\u0002002\f\u00101\u001a\b\u0012\u0004\u0012\u00020F022\u0006\u0010<\u001a\u00020\u00102\f\u0010G\u001a\b\u0012\u0004\u0012\u00020F0\"H\u0002J\u001e\u0010T\u001a\u0002002\u0006\u0010<\u001a\u00020\u00102\f\u00101\u001a\b\u0012\u0004\u0012\u00020U0\u0019H\u0002J\u0010\u0010V\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000fH\u0016J\f\u0010W\u001a\b\u0012\u0004\u0012\u00020'0)J*\u0010X\u001a\b\u0012\u0004\u0012\u00020#0)2\u0006\u0010Y\u001a\u00020Z2\u0012\u0010A\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020?0BH\u0016J\u001e\u0010[\u001a\b\u0012\u0004\u0012\u00020F0)2\u0006\u0010<\u001a\u00020\u00102\u0006\u00103\u001a\u000204H\u0016J\u001e\u0010\\\u001a\b\u0012\u0004\u0012\u00020=0)2\u0006\u0010<\u001a\u00020\u00102\u0006\u00103\u001a\u000204H\u0016J\u000e\u0010]\u001a\b\u0012\u0004\u0012\u00020\u00100)H\u0002J\u000e\u0010^\u001a\b\u0012\u0004\u0012\u00020\u00100\u0019H\u0016J\u001e\u0010_\u001a\u0002002\u0006\u0010<\u001a\u00020\u00102\f\u00101\u001a\b\u0012\u0004\u0012\u00020U0\u0019H\u0014J\u001e\u0010`\u001a\u0002002\u0006\u0010<\u001a\u00020\u00102\f\u00101\u001a\b\u0012\u0004\u0012\u00020F0\u0019H\u0014J\u001e\u0010a\u001a\u0002002\u0006\u0010<\u001a\u00020\u00102\f\u00101\u001a\b\u0012\u0004\u0012\u00020=0\u0019H\u0014J\b\u0010b\u001a\u0004\u0018\u00010'J\u0010\u0010c\u001a\u00020d2\u0006\u0010e\u001a\u00020fH\u0014J\u0010\u0010g\u001a\u00020d2\u0006\u0010e\u001a\u00020fH\u0014J\u000e\u0010h\u001a\b\u0012\u0004\u0012\u00020\u00100\u0019H\u0016J\u0018\u0010i\u001a\u0002002\u0006\u0010<\u001a\u00020\u00102\u0006\u00103\u001a\u000204H\u0016J\n\u0010j\u001a\u0004\u0018\u00010'H\u0014J\u000e\u0010k\u001a\b\u0012\u0004\u0012\u00020'0)H\u0002J\u0016\u0010l\u001a\u0002002\f\u0010m\u001a\b\u0012\u0004\u0012\u00020J0)H\u0002J\u0010\u0010n\u001a\u0002002\u0006\u0010o\u001a\u00020pH\u0004J\b\u0010q\u001a\u00020rH\u0016R#\u0010\u000e\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0013\u0010\u0014\u001a\u0004\b\u0011\u0010\u0012R#\u0010\u0015\u001a\n\u0012\u0004\u0012\u00020\u0010\u0018\u00010\u000f8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u0017\u0010\u0014\u001a\u0004\b\u0016\u0010\u0012R!\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00100\u00198BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001b\u0010\u001c\u001a\u0004\b\u001a\u0010\u0012R!\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00100\u00198BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\u001f\u0010\u001c\u001a\u0004\b\u001e\u0010\u0012R\u001a\u0010 \u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\"0!X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010$\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020#0\"0!X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u000bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020'0&X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010(\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020'0)0!X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010*\u001a\b\u0012\u0004\u0012\u00020+0)8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b.\u0010\u001c\u001a\u0004\b,\u0010-¨\u0006u"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassMemberScope;", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/AbstractLazyMemberScope;", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;", "Lorg/jetbrains/kotlin/resolve/lazy/declarations/ClassMemberDeclarationProvider;", "c", "Lorg/jetbrains/kotlin/resolve/lazy/LazyClassContext;", "declarationProvider", "thisClass", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "scopeForDeclaredMembers", "(Lorg/jetbrains/kotlin/resolve/lazy/LazyClassContext;Lorg/jetbrains/kotlin/resolve/lazy/declarations/ClassMemberDeclarationProvider;Lorg/jetbrains/kotlin/descriptors/ClassDescriptorWithResolutionScopes;Lorg/jetbrains/kotlin/resolve/BindingTrace;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassMemberScope;)V", "_allNames", "", "Lorg/jetbrains/kotlin/name/Name;", "get_allNames", "()Ljava/util/Set;", "_allNames$delegate", "Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "_classifierNames", "get_classifierNames", "_classifierNames$delegate", "_functionNames", "", "get_functionNames", "_functionNames$delegate", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "_variableNames", "get_variableNames", "_variableNames$delegate", "allClassifierDescriptors", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "allDescriptors", "primaryConstructor", "Lorg/jetbrains/kotlin/storage/NullableLazyValue;", "Lorg/jetbrains/kotlin/descriptors/ClassConstructorDescriptor;", "secondaryConstructors", "", "supertypes", "Lorg/jetbrains/kotlin/types/KotlinType;", "getSupertypes", "()Ljava/util/Collection;", "supertypes$delegate", "addDataClassMethods", "", "result", "", FirebaseAnalytics.Param.LOCATION, "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "addSyntheticCompanionObject", "addSyntheticFunctions", "addSyntheticNestedClasses", "addSyntheticSecondaryConstructors", "addSyntheticVariables", "computeExtraDescriptors", "createPropertiesFromPrimaryConstructorParameters", "name", "Lorg/jetbrains/kotlin/descriptors/PropertyDescriptor;", "definitelyDoesNotContainName", "", "doClassifierDescriptors", "nameFilter", "Lkotlin/Function1;", "doDescriptors", "doGetConstructors", "generateDataClassMethods", "Lorg/jetbrains/kotlin/descriptors/SimpleFunctionDescriptor;", "fromSupertypes", "generateDelegatingDescriptors", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "extractor", "Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassMemberScope$MemberExtractor;", "existingDescriptors", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "generateFakeOverrides", "D", "exactDescriptorClass", "Ljava/lang/Class;", "generateFunctionsFromAnyForValueClass", "generateSyntheticCompanionObject", "Lorg/jetbrains/kotlin/descriptors/ClassDescriptor;", "getClassifierNames", "getConstructors", "getContributedDescriptors", "kindFilter", "Lorg/jetbrains/kotlin/resolve/scopes/DescriptorKindFilter;", "getContributedFunctions", "getContributedVariables", "getDataClassRelatedFunctionNames", "getFunctionNames", "getNonDeclaredClasses", "getNonDeclaredFunctions", "getNonDeclaredProperties", "getPrimaryConstructor", "getScopeForInitializerResolution", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "declaration", "Lorg/jetbrains/kotlin/psi/KtDeclaration;", "getScopeForMemberDeclarationResolution", "getVariableNames", "recordLookup", "resolvePrimaryConstructor", "resolveSecondaryConstructors", "resolveUnknownVisibilitiesForMembers", "descriptors", "setDeferredReturnType", "descriptor", "Lorg/jetbrains/kotlin/descriptors/impl/ClassConstructorDescriptorImpl;", "toString", "", "Companion", "MemberExtractor", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class LazyClassMemberScope extends AbstractLazyMemberScope<ClassDescriptorWithResolutionScopes, ClassMemberDeclarationProvider> {
    static final /* synthetic */ KProperty<Object>[] $$delegatedProperties = {Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyClassMemberScope.class), "supertypes", "getSupertypes()Ljava/util/Collection;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyClassMemberScope.class), "_variableNames", "get_variableNames()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyClassMemberScope.class), "_functionNames", "get_functionNames()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyClassMemberScope.class), "_classifierNames", "get_classifierNames()Ljava/util/Set;")), Reflection.property1(new PropertyReference1Impl(Reflection.getOrCreateKotlinClass(LazyClassMemberScope.class), "_allNames", "get_allNames()Ljava/util/Set;"))};
    private static final MemberExtractor<SimpleFunctionDescriptor> EXTRACT_FUNCTIONS = new MemberExtractor<SimpleFunctionDescriptor>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope$Companion$EXTRACT_FUNCTIONS$1
        @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope.MemberExtractor
        public Collection<SimpleFunctionDescriptor> extract(KotlinType extractFrom, Name name2) {
            Intrinsics.checkNotNullParameter(extractFrom, "extractFrom");
            Intrinsics.checkNotNullParameter(name2, "name");
            return extractFrom.getMemberScope().getContributedFunctions(name2, NoLookupLocation.FOR_ALREADY_TRACKED);
        }
    };
    private static final MemberExtractor<PropertyDescriptor> EXTRACT_PROPERTIES = new MemberExtractor<PropertyDescriptor>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope$Companion$EXTRACT_PROPERTIES$1
        @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope.MemberExtractor
        public Collection<PropertyDescriptor> extract(KotlinType extractFrom, Name name2) {
            Intrinsics.checkNotNullParameter(extractFrom, "extractFrom");
            Intrinsics.checkNotNullParameter(name2, "name");
            return extractFrom.getMemberScope().getContributedVariables(name2, NoLookupLocation.FOR_ALREADY_TRACKED);
        }
    };

    /* JADX INFO: renamed from: _allNames$delegate, reason: from kotlin metadata */
    private final NullableLazyValue _allNames;

    /* JADX INFO: renamed from: _classifierNames$delegate, reason: from kotlin metadata */
    private final NullableLazyValue _classifierNames;

    /* JADX INFO: renamed from: _functionNames$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue _functionNames;

    /* JADX INFO: renamed from: _variableNames$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue _variableNames;
    private final NotNullLazyValue<List<DeclarationDescriptor>> allClassifierDescriptors;
    private final NotNullLazyValue<List<DeclarationDescriptor>> allDescriptors;
    private final KotlinTypeRefiner kotlinTypeRefiner;
    private final NullableLazyValue<ClassConstructorDescriptor> primaryConstructor;
    private final NotNullLazyValue<Collection<ClassConstructorDescriptor>> secondaryConstructors;

    /* JADX INFO: renamed from: supertypes$delegate, reason: from kotlin metadata */
    private final NotNullLazyValue supertypes;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: LazyClassMemberScope.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bb\u0018\u0000*\n\b\u0000\u0010\u0001 \u0001*\u00020\u00022\u00020\u0003J\u001e\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u00052\u0006\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\tH&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\nÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/resolve/lazy/descriptors/LazyClassMemberScope$MemberExtractor;", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "", "extract", "", "extractFrom", "Lorg/jetbrains/kotlin/types/KotlinType;", "name", "Lorg/jetbrains/kotlin/name/Name;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    interface MemberExtractor<T extends CallableMemberDescriptor> {
        Collection<T> extract(KotlinType extractFrom, Name name2);
    }

    public /* synthetic */ LazyClassMemberScope(LazyClassContext lazyClassContext, ClassMemberDeclarationProvider classMemberDeclarationProvider, ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopes, BindingTrace bindingTrace, KotlinTypeRefiner kotlinTypeRefiner, LazyClassMemberScope lazyClassMemberScope, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(lazyClassContext, classMemberDeclarationProvider, classDescriptorWithResolutionScopes, bindingTrace, (i & 16) != 0 ? lazyClassContext.getKotlinTypeCheckerOfOwnerModule().getKotlinTypeRefiner() : kotlinTypeRefiner, (i & 32) != 0 ? null : lazyClassMemberScope);
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public LazyClassMemberScope(final LazyClassContext c, final ClassMemberDeclarationProvider declarationProvider, ClassDescriptorWithResolutionScopes thisClass, BindingTrace trace, KotlinTypeRefiner kotlinTypeRefiner, LazyClassMemberScope lazyClassMemberScope) {
        super(c, declarationProvider, thisClass, trace, lazyClassMemberScope);
        Intrinsics.checkNotNullParameter(c, "c");
        Intrinsics.checkNotNullParameter(declarationProvider, "declarationProvider");
        Intrinsics.checkNotNullParameter(thisClass, "thisClass");
        Intrinsics.checkNotNullParameter(trace, "trace");
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        this.kotlinTypeRefiner = kotlinTypeRefiner;
        this.allDescriptors = getStorageManager().createLazyValue(new Function0<List<? extends DeclarationDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope$allDescriptors$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends DeclarationDescriptor> invoke() {
                return this.this$0.doDescriptors(MemberScope.INSTANCE.getALL_NAME_FILTER());
            }
        });
        this.allClassifierDescriptors = getStorageManager().createLazyValue(new Function0<List<? extends DeclarationDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope$allClassifierDescriptors$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends DeclarationDescriptor> invoke() {
                return this.this$0.doClassifierDescriptors(MemberScope.INSTANCE.getALL_NAME_FILTER());
            }
        });
        this.supertypes = getStorageManager().createLazyValue(new Function0<Collection<? extends KotlinType>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope$supertypes$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends KotlinType> invoke() {
                return this.this$0.kotlinTypeRefiner.refineSupertypes(this.this$0.getThisDescriptor());
            }
        });
        this._variableNames = getStorageManager().createLazyValue(new Function0<Set<Name>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope$_variableNames$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<Name> invoke() {
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                ClassMemberDeclarationProvider classMemberDeclarationProvider = declarationProvider;
                LazyClassContext lazyClassContext = c;
                LazyClassMemberScope lazyClassMemberScope2 = this;
                linkedHashSet.addAll(classMemberDeclarationProvider.getDeclarationNames());
                linkedHashSet.addAll(lazyClassContext.getSyntheticResolveExtension().getSyntheticPropertiesNames(lazyClassMemberScope2.getThisDescriptor()));
                Iterator<T> it = lazyClassMemberScope2.getSupertypes().iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(linkedHashSet, ((KotlinType) it.next()).getMemberScope().getVariableNames());
                }
                return linkedHashSet;
            }
        });
        this._functionNames = getStorageManager().createLazyValue(new Function0<Set<Name>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope$_functionNames$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<Name> invoke() {
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                ClassMemberDeclarationProvider classMemberDeclarationProvider = declarationProvider;
                LazyClassContext lazyClassContext = c;
                LazyClassMemberScope lazyClassMemberScope2 = this;
                linkedHashSet.addAll(classMemberDeclarationProvider.getDeclarationNames());
                linkedHashSet.addAll(lazyClassContext.getSyntheticResolveExtension().getSyntheticFunctionNames(lazyClassMemberScope2.getThisDescriptor()));
                Iterator<T> it = lazyClassMemberScope2.getSupertypes().iterator();
                while (it.hasNext()) {
                    CollectionsKt.addAll(linkedHashSet, ((KotlinType) it.next()).getMemberScope().getFunctionNames());
                }
                linkedHashSet.addAll(lazyClassMemberScope2.getDataClassRelatedFunctionNames());
                return linkedHashSet;
            }
        });
        this._classifierNames = getStorageManager().createNullableLazyValue(new Function0<Set<Name>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope$_classifierNames$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<Name> invoke() {
                LinkedHashSet linkedHashSet;
                LinkedHashSet linkedHashSet2 = new LinkedHashSet();
                LazyClassMemberScope lazyClassMemberScope2 = this.this$0;
                ClassMemberDeclarationProvider classMemberDeclarationProvider = declarationProvider;
                LazyClassContext lazyClassContext = c;
                Iterator<T> it = lazyClassMemberScope2.getSupertypes().iterator();
                while (true) {
                    if (it.hasNext()) {
                        Set<Name> classifierNames = ((KotlinType) it.next()).getMemberScope().getClassifierNames();
                        if (classifierNames == null) {
                            linkedHashSet = null;
                            break;
                        }
                        CollectionsKt.addAll(linkedHashSet2, classifierNames);
                    } else {
                        linkedHashSet = linkedHashSet2;
                        break;
                    }
                }
                if (linkedHashSet == null) {
                    return null;
                }
                linkedHashSet2.addAll(classMemberDeclarationProvider.getDeclarationNames());
                SyntheticResolveExtension syntheticResolveExtension = lazyClassContext.getSyntheticResolveExtension();
                List<Name> possibleSyntheticNestedClassNames = syntheticResolveExtension.getPossibleSyntheticNestedClassNames(lazyClassMemberScope2.getThisDescriptor());
                if (possibleSyntheticNestedClassNames == null) {
                    return null;
                }
                linkedHashSet2.addAll(possibleSyntheticNestedClassNames);
                Name syntheticCompanionObjectNameIfNeeded = syntheticResolveExtension.getSyntheticCompanionObjectNameIfNeeded(lazyClassMemberScope2.getThisDescriptor());
                if (syntheticCompanionObjectNameIfNeeded != null) {
                    linkedHashSet2.add(syntheticCompanionObjectNameIfNeeded);
                }
                return linkedHashSet2;
            }
        });
        this._allNames = getStorageManager().createNullableLazyValue(new Function0<Set<Name>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope$_allNames$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Set<Name> invoke() {
                Set<Name> classifierNames = this.this$0.getClassifierNames();
                if (classifierNames == null) {
                    return null;
                }
                LinkedHashSet linkedHashSet = new LinkedHashSet();
                LazyClassMemberScope lazyClassMemberScope2 = this.this$0;
                linkedHashSet.addAll(lazyClassMemberScope2.getVariableNames());
                linkedHashSet.addAll(lazyClassMemberScope2.getFunctionNames());
                linkedHashSet.addAll(classifierNames);
                return linkedHashSet;
            }
        });
        this.primaryConstructor = c.getStorageManager().createNullableLazyValue(new Function0<ClassConstructorDescriptor>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope$primaryConstructor$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassConstructorDescriptor invoke() {
                return this.this$0.resolvePrimaryConstructor();
            }
        });
        this.secondaryConstructors = c.getStorageManager().createLazyValue(new Function0<Collection<? extends ClassConstructorDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope$secondaryConstructors$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Collection<? extends ClassConstructorDescriptor> invoke() {
                return this.this$0.doGetConstructors();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<DeclarationDescriptor> doDescriptors(Function1<? super Name, Boolean> nameFilter) {
        Set<DeclarationDescriptor> setComputeDescriptorsFromDeclaredElements = computeDescriptorsFromDeclaredElements(DescriptorKindFilter.ALL, nameFilter, NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS);
        computeExtraDescriptors(setComputeDescriptorsFromDeclaredElements, NoLookupLocation.FOR_ALREADY_TRACKED);
        return CollectionsKt.toList(setComputeDescriptorsFromDeclaredElements);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<DeclarationDescriptor> doClassifierDescriptors(Function1<? super Name, Boolean> nameFilter) {
        Set<DeclarationDescriptor> setComputeDescriptorsFromDeclaredElements = computeDescriptorsFromDeclaredElements(DescriptorKindFilter.CLASSIFIERS, nameFilter, NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS);
        Set<DeclarationDescriptor> set = setComputeDescriptorsFromDeclaredElements;
        addSyntheticCompanionObject(set, NoLookupLocation.FOR_ALREADY_TRACKED);
        addSyntheticNestedClasses(set, NoLookupLocation.FOR_ALREADY_TRACKED);
        return CollectionsKt.toList(setComputeDescriptorsFromDeclaredElements);
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<DeclarationDescriptor> getContributedDescriptors(DescriptorKindFilter kindFilter, final Function1<? super Name, Boolean> nameFilter) {
        List<DeclarationDescriptor> listInvoke;
        List<DeclarationDescriptor> listInvoke2;
        Intrinsics.checkNotNullParameter(kindFilter, "kindFilter");
        Intrinsics.checkNotNullParameter(nameFilter, "nameFilter");
        if (Intrinsics.areEqual(kindFilter, DescriptorKindFilter.CLASSIFIERS)) {
            if (Intrinsics.areEqual(nameFilter, MemberScope.INSTANCE.getALL_NAME_FILTER()) || this.allClassifierDescriptors.isComputed() || this.allClassifierDescriptors.isComputing()) {
                listInvoke2 = this.allClassifierDescriptors.invoke();
            } else {
                listInvoke2 = (List) getStorageManager().compute(new Function0<List<? extends DeclarationDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope.getContributedDescriptors.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    /* JADX WARN: Multi-variable type inference failed */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final List<? extends DeclarationDescriptor> invoke() {
                        return LazyClassMemberScope.this.doClassifierDescriptors(nameFilter);
                    }
                });
            }
            return listInvoke2;
        }
        if (Intrinsics.areEqual(nameFilter, MemberScope.INSTANCE.getALL_NAME_FILTER()) || this.allDescriptors.isComputed() || this.allDescriptors.isComputing()) {
            listInvoke = this.allDescriptors.invoke();
        } else {
            listInvoke = (List) getStorageManager().compute(new Function0<List<? extends DeclarationDescriptor>>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope.getContributedDescriptors.2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends DeclarationDescriptor> invoke() {
                    return LazyClassMemberScope.this.doDescriptors(nameFilter);
                }
            });
        }
        return listInvoke;
    }

    protected void computeExtraDescriptors(Collection<DeclarationDescriptor> result, LookupLocation location) {
        Intrinsics.checkNotNullParameter(result, "result");
        Intrinsics.checkNotNullParameter(location, "location");
        Iterator<KotlinType> it = getSupertypes().iterator();
        while (it.hasNext()) {
            for (DeclarationDescriptor declarationDescriptor : ResolutionScope.DefaultImpls.getContributedDescriptors$default(it.next().getMemberScope(), null, null, 3, null)) {
                if (declarationDescriptor instanceof FunctionDescriptor) {
                    Name name2 = ((FunctionDescriptor) declarationDescriptor).getShortName();
                    Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
                    result.addAll(getContributedFunctions(name2, location));
                } else if (declarationDescriptor instanceof PropertyDescriptor) {
                    Name name3 = ((PropertyDescriptor) declarationDescriptor).getShortName();
                    Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                    result.addAll(getContributedVariables(name3, location));
                }
            }
        }
        addDataClassMethods(result, location);
        addSyntheticFunctions(result, location);
        addSyntheticVariables(result, location);
        addSyntheticCompanionObject(result, location);
        addSyntheticNestedClasses(result, location);
    }

    public final Collection<KotlinType> getSupertypes() {
        return (Collection) StorageKt.getValue(this.supertypes, this, (KProperty<?>) $$delegatedProperties[0]);
    }

    private final Set<Name> get_variableNames() {
        return (Set) StorageKt.getValue(this._variableNames, this, (KProperty<?>) $$delegatedProperties[1]);
    }

    private final Set<Name> get_functionNames() {
        return (Set) StorageKt.getValue(this._functionNames, this, (KProperty<?>) $$delegatedProperties[2]);
    }

    private final Set<Name> get_classifierNames() {
        return (Set) StorageKt.getValue(this._classifierNames, this, (KProperty<?>) $$delegatedProperties[3]);
    }

    private final Set<Name> get_allNames() {
        return (Set) StorageKt.getValue(this._allNames, this, (KProperty<?>) $$delegatedProperties[4]);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<Name> getDataClassRelatedFunctionNames() {
        ArrayList arrayList = new ArrayList();
        addDataClassMethods(arrayList, NoLookupLocation.WHEN_GET_ALL_DESCRIPTORS);
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(((DeclarationDescriptor) it.next()).getShortName());
        }
        return arrayList3;
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getVariableNames() {
        return get_variableNames();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getFunctionNames() {
        return get_functionNames();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope
    public Set<Name> getClassifierNames() {
        return get_classifierNames();
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public boolean definitelyDoesNotContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Set<Name> set = get_allNames();
        if (set != null) {
            return !set.contains(name2);
        }
        return false;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    protected LexicalScope getScopeForMemberDeclarationResolution(KtDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        LexicalScope scopeForMemberDeclarationResolution = getThisDescriptor().getScopeForMemberDeclarationResolution();
        Intrinsics.checkNotNullExpressionValue(scopeForMemberDeclarationResolution, "getScopeForMemberDeclarationResolution(...)");
        return scopeForMemberDeclarationResolution;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    protected LexicalScope getScopeForInitializerResolution(KtDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        LexicalScope scopeForInitializerResolution = getThisDescriptor().getScopeForInitializerResolution();
        Intrinsics.checkNotNullExpressionValue(scopeForInitializerResolution, "getScopeForInitializerResolution(...)");
        return scopeForInitializerResolution;
    }

    private final <D extends CallableMemberDescriptor> void generateFakeOverrides(Name name2, Collection<? extends D> fromSupertypes, final Collection<D> result, final Class<? extends D> exactDescriptorClass) {
        CallableMemberDescriptor callableMemberDescriptor;
        Unit unit;
        Unit unit2;
        new NewKotlinTypeCheckerImpl(this.kotlinTypeRefiner, null, 2, null).getOverridingUtil().generateOverridesInFunctionGroup(name2, fromSupertypes, new ArrayList(result), getThisDescriptor(), new OverridingStrategy() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope.generateFakeOverrides.1
            /* JADX WARN: Type inference incomplete: some casts might be missing */
            @Override // org.jetbrains.kotlin.resolve.OverridingStrategy
            public void addFakeOverride(CallableMemberDescriptor fakeOverride) {
                Intrinsics.checkNotNullParameter(fakeOverride, "fakeOverride");
                exactDescriptorClass.isInstance(fakeOverride);
                result.add((D) fakeOverride);
            }

            @Override // org.jetbrains.kotlin.resolve.OverridingStrategy
            public void overrideConflict(CallableMemberDescriptor fromSuper, CallableMemberDescriptor fromCurrent) {
                Unit unit3;
                Intrinsics.checkNotNullParameter(fromSuper, "fromSuper");
                Intrinsics.checkNotNullParameter(fromCurrent, "fromCurrent");
                BindingTrace trace = this.getTrace();
                CallableMemberDescriptor callableMemberDescriptor2 = fromCurrent;
                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(callableMemberDescriptor2);
                if (psiElementDescriptorToDeclaration != null) {
                    Diagnostic diagnosticOn = Errors.CONFLICTING_OVERLOADS.on(psiElementDescriptorToDeclaration, CollectionsKt.listOf((Object[]) new CallableMemberDescriptor[]{fromCurrent, fromSuper}));
                    Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                    trace.report(diagnosticOn);
                    unit3 = Unit.INSTANCE;
                } else {
                    unit3 = null;
                }
                if (unit3 == null) {
                    throw new AssertionError("No declaration for " + callableMemberDescriptor2);
                }
            }

            @Override // org.jetbrains.kotlin.resolve.OverridingStrategy
            public void inheritanceConflict(CallableMemberDescriptor first, CallableMemberDescriptor second) {
                Intrinsics.checkNotNullParameter(first, "first");
                Intrinsics.checkNotNullParameter(second, "second");
                BindingTrace trace = this.getTrace();
                ClassDescriptorWithResolutionScopes thisDescriptor = this.getThisDescriptor();
                LazyClassMemberScope lazyClassMemberScope = this;
                PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(thisDescriptor);
                Unit unit3 = null;
                if (psiElementDescriptorToDeclaration != null) {
                    KtClassOrObject ktClassOrObject = (KtClassOrObject) (!(psiElementDescriptorToDeclaration instanceof KtClassOrObject) ? null : psiElementDescriptorToDeclaration);
                    if (ktClassOrObject != null) {
                        Diagnostic diagnosticOn = Errors.CONFLICTING_INHERITED_MEMBERS.on(ktClassOrObject, lazyClassMemberScope.getThisDescriptor(), CollectionsKt.listOf((Object[]) new CallableMemberDescriptor[]{first, second}));
                        Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                        trace.report(diagnosticOn);
                        unit3 = Unit.INSTANCE;
                    }
                    if (unit3 != null) {
                        unit3 = Unit.INSTANCE;
                    } else {
                        throw new AssertionError("Declaration for " + thisDescriptor + " is expected to be " + Reflection.getOrCreateKotlinClass(KtClassOrObject.class).getSimpleName() + ", actual declaration: " + psiElementDescriptorToDeclaration);
                    }
                }
                if (unit3 == null) {
                    throw new AssertionError("No declaration for " + thisDescriptor);
                }
            }
        });
        for (D d : result) {
            if (d instanceof FunctionDescriptorImpl) {
                for (FunctionDescriptor functionDescriptor : ((FunctionDescriptorImpl) d).getOverriddenDescriptors()) {
                    if ((functionDescriptor instanceof FunctionDescriptorImpl) && (callableMemberDescriptor = (CallableMemberDescriptor) functionDescriptor.getUserData(DeserializedDeclarationsFromSupertypeConflictDataKey.INSTANCE)) != null) {
                        BindingTrace trace = getTrace();
                        ClassDescriptorWithResolutionScopes thisDescriptor = getThisDescriptor();
                        PsiElement psiElementDescriptorToDeclaration = DescriptorToSourceUtils.descriptorToDeclaration(thisDescriptor);
                        if (psiElementDescriptorToDeclaration != null) {
                            KtClassOrObject ktClassOrObject = (KtClassOrObject) (!(psiElementDescriptorToDeclaration instanceof KtClassOrObject) ? null : psiElementDescriptorToDeclaration);
                            if (ktClassOrObject != null) {
                                Diagnostic diagnosticOn = Errors.CONFLICTING_INHERITED_MEMBERS_WARNING.on(ktClassOrObject, getThisDescriptor(), CollectionsKt.listOf((Object[]) new CallableMemberDescriptor[]{functionDescriptor, callableMemberDescriptor}));
                                Intrinsics.checkNotNullExpressionValue(diagnosticOn, "on(...)");
                                trace.report(diagnosticOn);
                                unit2 = Unit.INSTANCE;
                            } else {
                                unit2 = null;
                            }
                            if (unit2 != null) {
                                unit = Unit.INSTANCE;
                            } else {
                                throw new AssertionError("Declaration for " + thisDescriptor + " is expected to be " + Reflection.getOrCreateKotlinClass(KtClassOrObject.class).getSimpleName() + ", actual declaration: " + psiElementDescriptorToDeclaration);
                            }
                        } else {
                            unit = null;
                        }
                        if (unit == null) {
                            throw new AssertionError("No declaration for " + thisDescriptor);
                        }
                    }
                }
            }
        }
        OverrideResolver.INSTANCE.resolveUnknownVisibilities(result, getTrace());
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope, org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<SimpleFunctionDescriptor> getContributedFunctions(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Collection<SimpleFunctionDescriptor> contributedFunctions = super.getContributedFunctions(name2, location);
        resolveUnknownVisibilitiesForMembers(contributedFunctions);
        return contributedFunctions;
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    protected void getNonDeclaredClasses(Name name2, Set<ClassDescriptor> result) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
        generateSyntheticCompanionObject(name2, result);
        getC().getSyntheticResolveExtension().generateSyntheticClasses(getThisDescriptor(), name2, getC(), getDeclarationProvider(), result);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    protected void getNonDeclaredFunctions(Name name2, Set<SimpleFunctionDescriptor> result) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
        NoLookupLocation noLookupLocation = NoLookupLocation.FOR_ALREADY_TRACKED;
        ArrayList arrayList = new ArrayList();
        Iterator<KotlinType> it = getSupertypes().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().getMemberScope().getContributedFunctions(name2, noLookupLocation));
        }
        Set<SimpleFunctionDescriptor> set = result;
        result.addAll(generateDelegatingDescriptors(name2, EXTRACT_FUNCTIONS, set));
        NoLookupLocation noLookupLocation2 = noLookupLocation;
        ArrayList arrayList2 = arrayList;
        generateDataClassMethods(set, name2, noLookupLocation2, arrayList2);
        generateFunctionsFromAnyForValueClass(set, name2, arrayList2);
        SyntheticResolveExtension syntheticResolveExtension = getC().getSyntheticResolveExtension();
        ClassDescriptorWithResolutionScopes thisDescriptor = getThisDescriptor();
        BindingContext context = getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        syntheticResolveExtension.generateSyntheticMethods(thisDescriptor, name2, context, arrayList2, set);
        ArrayList arrayList3 = arrayList;
        getC().getAdditionalClassPartsProvider().generateAdditionalMethods(getThisDescriptor(), set, name2, noLookupLocation2, arrayList3);
        generateFakeOverrides(name2, arrayList3, set, SimpleFunctionDescriptor.class);
    }

    private final void generateFunctionsFromAnyForValueClass(Collection<SimpleFunctionDescriptor> result, Name name2, List<? extends SimpleFunctionDescriptor> fromSupertypes) {
        if (InlineClassesUtilsKt.isValueClass(getThisDescriptor())) {
            FunctionsFromAny.INSTANCE.addFunctionFromAnyIfNeeded(getThisDescriptor(), result, name2, fromSupertypes);
        }
    }

    private final void generateDataClassMethods(Collection<SimpleFunctionDescriptor> result, Name name2, LookupLocation location, List<? extends SimpleFunctionDescriptor> fromSupertypes) {
        ClassConstructorDescriptor primaryConstructor;
        Object next;
        if (getThisDescriptor().isData() && (primaryConstructor = getPrimaryConstructor()) != null) {
            List<KtParameter> primaryConstructorParameters = getDeclarationProvider().getPrimaryConstructorParameters();
            primaryConstructor.getValueParameters().size();
            primaryConstructorParameters.size();
            if (DataClassDescriptorResolver.INSTANCE.isComponentLike(name2)) {
                int i = 0;
                for (ValueParameterDescriptor valueParameterDescriptor : primaryConstructor.getValueParameters()) {
                    if (primaryConstructorParameters.get(valueParameterDescriptor.getIndex()).hasValOrVar()) {
                        Name name3 = valueParameterDescriptor.getShortName();
                        Intrinsics.checkNotNullExpressionValue(name3, "getName(...)");
                        Iterator<T> it = getContributedVariables(name3, location).iterator();
                        do {
                            if (!it.hasNext()) {
                                next = null;
                                break;
                            }
                            next = it.next();
                        } while (((PropertyDescriptor) next).getExtensionReceiverParameter() != null);
                        PropertyDescriptor propertyDescriptor = (PropertyDescriptor) next;
                        if (propertyDescriptor == null) {
                            continue;
                        } else {
                            int i2 = i + 1;
                            if (Intrinsics.areEqual(name2, DataClassDescriptorResolver.INSTANCE.createComponentName(i2))) {
                                DataClassDescriptorResolver dataClassDescriptorResolver = DataClassDescriptorResolver.INSTANCE;
                                Intrinsics.checkNotNull(valueParameterDescriptor);
                                result.add(dataClassDescriptorResolver.createComponentFunctionDescriptor(i2, propertyDescriptor, valueParameterDescriptor, getThisDescriptor(), getTrace()));
                                break;
                            }
                            i = i2;
                        }
                    }
                }
            }
            if (Intrinsics.areEqual(name2, DataClassDescriptorResolver.INSTANCE.getCOPY_METHOD_NAME())) {
                Iterator<ValueParameterDescriptor> it2 = primaryConstructor.getValueParameters().iterator();
                while (it2.hasNext()) {
                    Name name4 = it2.next().getShortName();
                    Intrinsics.checkNotNullExpressionValue(name4, "getName(...)");
                    getContributedVariables(name4, location);
                }
                DataClassDescriptorResolver dataClassDescriptorResolver2 = DataClassDescriptorResolver.INSTANCE;
                List<ValueParameterDescriptor> valueParameters = primaryConstructor.getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                result.add(dataClassDescriptorResolver2.createCopyFunctionDescriptor(valueParameters, getThisDescriptor(), getTrace()));
            }
            if (getC().getLanguageVersionSettings().supportsFeature(LanguageFeature.DataClassInheritance)) {
                FunctionsFromAny.INSTANCE.addFunctionFromAnyIfNeeded(getThisDescriptor(), result, name2, fromSupertypes);
            }
        }
    }

    private final void addSyntheticCompanionObject(Collection<DeclarationDescriptor> result, LookupLocation location) {
        ClassifierDescriptor contributedClassifier;
        Name syntheticCompanionObjectNameIfNeeded = getC().getSyntheticResolveExtension().getSyntheticCompanionObjectNameIfNeeded(getThisDescriptor());
        if (syntheticCompanionObjectNameIfNeeded == null || (contributedClassifier = mo7465getContributedClassifier(syntheticCompanionObjectNameIfNeeded, location)) == null) {
            return;
        }
        result.add(contributedClassifier);
    }

    private final void addSyntheticFunctions(Collection<DeclarationDescriptor> result, LookupLocation location) {
        List<Name> syntheticFunctionNames = getC().getSyntheticResolveExtension().getSyntheticFunctionNames(getThisDescriptor());
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = syntheticFunctionNames.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, getContributedFunctions((Name) it.next(), location));
        }
        result.addAll(CollectionsKt.toList(arrayList));
    }

    private final void addSyntheticVariables(Collection<DeclarationDescriptor> result, LookupLocation location) {
        List<Name> syntheticPropertiesNames = getC().getSyntheticResolveExtension().getSyntheticPropertiesNames(getThisDescriptor());
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = syntheticPropertiesNames.iterator();
        while (it.hasNext()) {
            CollectionsKt.addAll(arrayList, getContributedVariables((Name) it.next(), location));
        }
        result.addAll(CollectionsKt.toList(arrayList));
    }

    private final void addSyntheticNestedClasses(Collection<DeclarationDescriptor> result, LookupLocation location) {
        List<Name> syntheticNestedClassNames = getC().getSyntheticResolveExtension().getSyntheticNestedClassNames(getThisDescriptor());
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = syntheticNestedClassNames.iterator();
        while (it.hasNext()) {
            ClassifierDescriptor contributedClassifier = mo7465getContributedClassifier((Name) it.next(), location);
            if (contributedClassifier != null) {
                arrayList.add(contributedClassifier);
            }
        }
        result.addAll(CollectionsKt.toList(arrayList));
    }

    private final void generateSyntheticCompanionObject(Name name2, Set<ClassDescriptor> result) {
        Name syntheticCompanionObjectNameIfNeeded = getC().getSyntheticResolveExtension().getSyntheticCompanionObjectNameIfNeeded(getThisDescriptor());
        if (syntheticCompanionObjectNameIfNeeded != null && Intrinsics.areEqual(name2, syntheticCompanionObjectNameIfNeeded)) {
            Set<ClassDescriptor> set = result;
            if (!(set instanceof Collection) || !set.isEmpty()) {
                Iterator<T> it = set.iterator();
                while (it.hasNext()) {
                    if (((ClassDescriptor) it.next()).getIsCompanionObject()) {
                        return;
                    }
                }
            }
            ClassDescriptorWithResolutionScopes classDescriptorWithResolutionScopesMo6346getCompanionObjectDescriptor = getThisDescriptor().mo6346getCompanionObjectDescriptor();
            if (classDescriptorWithResolutionScopesMo6346getCompanionObjectDescriptor == null) {
                return;
            }
            result.add(classDescriptorWithResolutionScopesMo6346getCompanionObjectDescriptor);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope, org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.MemberScope, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    public Collection<PropertyDescriptor> getContributedVariables(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        Collection<PropertyDescriptor> contributedVariables = super.getContributedVariables(name2, location);
        Intrinsics.checkNotNull(contributedVariables, "null cannot be cast to non-null type kotlin.collections.Collection<org.jetbrains.kotlin.descriptors.CallableMemberDescriptor>");
        resolveUnknownVisibilitiesForMembers(contributedVariables);
        return contributedVariables;
    }

    private final void resolveUnknownVisibilitiesForMembers(Collection<? extends CallableMemberDescriptor> descriptors) {
        for (CallableMemberDescriptor callableMemberDescriptor : descriptors) {
            if (callableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.FAKE_OVERRIDE && callableMemberDescriptor.getKind() != CallableMemberDescriptor.Kind.DELEGATION) {
                OverridingUtil.resolveUnknownVisibilityForMember(callableMemberDescriptor, OverrideResolver.INSTANCE.createCannotInferVisibilityReporter(getTrace()));
            }
            BindingContext context = getTrace().getContext();
            Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
            DiagnosticSink DO_NOTHING = DiagnosticSink.DO_NOTHING;
            Intrinsics.checkNotNullExpressionValue(DO_NOTHING, "DO_NOTHING");
            new VarianceCheckerCore(context, DO_NOTHING, null, null, 12, null).recordPrivateToThisIfNeeded(callableMemberDescriptor);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    protected void getNonDeclaredProperties(Name name2, Set<PropertyDescriptor> result) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
        createPropertiesFromPrimaryConstructorParameters(name2, result);
        ArrayList<PropertyDescriptor> arrayList = new ArrayList<>();
        Iterator<KotlinType> it = getSupertypes().iterator();
        while (it.hasNext()) {
            arrayList.addAll(it.next().getMemberScope().getContributedVariables(name2, NoLookupLocation.FOR_ALREADY_TRACKED));
        }
        Set<PropertyDescriptor> set = result;
        result.addAll(generateDelegatingDescriptors(name2, EXTRACT_PROPERTIES, set));
        SyntheticResolveExtension syntheticResolveExtension = getC().getSyntheticResolveExtension();
        ClassDescriptorWithResolutionScopes thisDescriptor = getThisDescriptor();
        BindingContext context = getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        syntheticResolveExtension.generateSyntheticProperties(thisDescriptor, name2, context, arrayList, result);
        generateFakeOverrides(name2, arrayList, set, PropertyDescriptor.class);
    }

    protected void createPropertiesFromPrimaryConstructorParameters(Name name2, Set<PropertyDescriptor> result) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(result, "result");
        ClassConstructorDescriptor primaryConstructor = getPrimaryConstructor();
        if (primaryConstructor == null) {
            return;
        }
        List<ValueParameterDescriptor> valueParameters = primaryConstructor.getValueParameters();
        Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
        List<KtParameter> primaryConstructorParameters = getDeclarationProvider().getPrimaryConstructorParameters();
        valueParameters.size();
        primaryConstructorParameters.size();
        for (ValueParameterDescriptor valueParameterDescriptor : valueParameters) {
            if (Intrinsics.areEqual(name2, valueParameterDescriptor.getShortName())) {
                KtParameter ktParameter = primaryConstructorParameters.get(valueParameterDescriptor.getIndex());
                if (ktParameter.hasValOrVar()) {
                    PropertyDescriptor propertyDescriptorResolvePrimaryConstructorParameterToAProperty = (PropertyDescriptor) getTrace().get(BindingContext.PRIMARY_CONSTRUCTOR_PARAMETER, ktParameter);
                    if (propertyDescriptorResolvePrimaryConstructorParameterToAProperty == null) {
                        propertyDescriptorResolvePrimaryConstructorParameterToAProperty = getC().getDescriptorResolver().resolvePrimaryConstructorParameterToAProperty(getThisDescriptor(), valueParameterDescriptor, getThisDescriptor().getScopeForConstructorHeaderResolution(), ktParameter, getTrace());
                    }
                    Intrinsics.checkNotNull(propertyDescriptorResolvePrimaryConstructorParameterToAProperty);
                    result.add(propertyDescriptorResolvePrimaryConstructorParameterToAProperty);
                }
            }
        }
    }

    private final <T extends CallableMemberDescriptor> Collection<T> generateDelegatingDescriptors(final Name name2, final MemberExtractor<? extends T> extractor, Collection<? extends CallableDescriptor> existingDescriptors) {
        KtPureClassOrObject correspondingClassOrObject = getDeclarationProvider().getCorrespondingClassOrObject();
        if (correspondingClassOrObject == null) {
            return SetsKt.emptySet();
        }
        return DelegationResolver.INSTANCE.generateDelegatedMembers(correspondingClassOrObject, getThisDescriptor(), existingDescriptors, getTrace(), (DelegationResolver.MemberExtractor) new DelegationResolver.MemberExtractor<T>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope$generateDelegatingDescriptors$lazyMemberExtractor$1
            @Override // org.jetbrains.kotlin.resolve.DelegationResolver.MemberExtractor
            public Collection<T> getMembersByType(KotlinType type) {
                Intrinsics.checkNotNullParameter(type, "type");
                return extractor.extract(type, name2);
            }
        }, new DelegationResolver.TypeResolver() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope$generateDelegatingDescriptors$lazyTypeResolver$1
            @Override // org.jetbrains.kotlin.resolve.DelegationResolver.TypeResolver
            public KotlinType resolve(KtTypeReference reference) {
                Intrinsics.checkNotNullParameter(reference, "reference");
                TypeResolver typeResolver = this.this$0.getC().getTypeResolver();
                LexicalScope scopeForClassHeaderResolution = this.this$0.getThisDescriptor().getScopeForClassHeaderResolution();
                Intrinsics.checkNotNullExpressionValue(scopeForClassHeaderResolution, "getScopeForClassHeaderResolution(...)");
                return typeResolver.resolveType(scopeForClassHeaderResolution, reference, this.this$0.getTrace(), false);
            }
        }, getC().getDelegationFilter(), getC().getLanguageVersionSettings());
    }

    private final void addDataClassMethods(Collection<DeclarationDescriptor> result, LookupLocation location) {
        if (!getThisDescriptor().isData() || getThisDescriptor().getKind() != ClassKind.CLASS || getPrimaryConstructor() == null) {
            return;
        }
        int i = 1;
        while (true) {
            Collection<SimpleFunctionDescriptor> contributedFunctions = getContributedFunctions(DataClassDescriptorResolver.INSTANCE.createComponentName(i), location);
            if (!contributedFunctions.isEmpty()) {
                result.addAll(contributedFunctions);
                i++;
            } else {
                Name nameIdentifier = Name.identifier(ConfigConstants.CONFIG_RENAMELIMIT_COPY);
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                result.addAll(getContributedFunctions(nameIdentifier, location));
                return;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<ClassConstructorDescriptor> doGetConstructors() {
        ArrayList arrayList = new ArrayList();
        arrayList.addAll(resolveSecondaryConstructors());
        ArrayList arrayList2 = arrayList;
        addSyntheticSecondaryConstructors(arrayList2);
        return arrayList2;
    }

    private final void addSyntheticSecondaryConstructors(Collection<ClassConstructorDescriptor> result) {
        SyntheticResolveExtension syntheticResolveExtension = getC().getSyntheticResolveExtension();
        ClassDescriptorWithResolutionScopes thisDescriptor = getThisDescriptor();
        BindingContext context = getTrace().getContext();
        Intrinsics.checkNotNullExpressionValue(context, "getBindingContext(...)");
        syntheticResolveExtension.generateSyntheticSecondaryConstructors(thisDescriptor, context, result);
    }

    public final Collection<ClassConstructorDescriptor> getConstructors() {
        Collection<ClassConstructorDescriptor> collectionInvoke;
        NotNullLazyValue<Collection<ClassConstructorDescriptor>> notNullLazyValue;
        LazyClassMemberScope lazyClassMemberScope = (LazyClassMemberScope) getMainScope();
        if (lazyClassMemberScope == null || (notNullLazyValue = lazyClassMemberScope.secondaryConstructors) == null || (collectionInvoke = notNullLazyValue.invoke()) == null) {
            collectionInvoke = this.secondaryConstructors.invoke();
        }
        ClassConstructorDescriptor primaryConstructor = getPrimaryConstructor();
        return primaryConstructor == null ? collectionInvoke : CollectionsKt.plus((Collection<? extends ClassConstructorDescriptor>) collectionInvoke, primaryConstructor);
    }

    public final ClassConstructorDescriptor getPrimaryConstructor() {
        NullableLazyValue<ClassConstructorDescriptor> nullableLazyValue;
        ClassConstructorDescriptor classConstructorDescriptorInvoke;
        LazyClassMemberScope lazyClassMemberScope = (LazyClassMemberScope) getMainScope();
        return (lazyClassMemberScope == null || (nullableLazyValue = lazyClassMemberScope.primaryConstructor) == null || (classConstructorDescriptorInvoke = nullableLazyValue.invoke()) == null) ? this.primaryConstructor.invoke() : classConstructorDescriptorInvoke;
    }

    protected ClassConstructorDescriptor resolvePrimaryConstructor() {
        KtPureClassOrObject correspondingClassOrObject = getDeclarationProvider().getCorrespondingClassOrObject();
        if (correspondingClassOrObject == null) {
            return null;
        }
        boolean zHasExplicitPrimaryConstructor = correspondingClassOrObject.hasExplicitPrimaryConstructor();
        if (!zHasExplicitPrimaryConstructor && ((getThisDescriptor().get$isExpect() && !DescriptorUtils.isEnumEntry(getThisDescriptor())) || DescriptorUtils.isInterface(getThisDescriptor()))) {
            return null;
        }
        if (DescriptorUtils.canHaveDeclaredConstructors(getThisDescriptor()) || zHasExplicitPrimaryConstructor) {
            FunctionDescriptorResolver functionDescriptorResolver = getC().getFunctionDescriptorResolver();
            LexicalScope scopeForConstructorHeaderResolution = getThisDescriptor().getScopeForConstructorHeaderResolution();
            Intrinsics.checkNotNullExpressionValue(scopeForConstructorHeaderResolution, "getScopeForConstructorHeaderResolution(...)");
            ClassConstructorDescriptorImpl classConstructorDescriptorImplResolvePrimaryConstructorDescriptor = functionDescriptorResolver.resolvePrimaryConstructorDescriptor(scopeForConstructorHeaderResolution, getThisDescriptor(), correspondingClassOrObject, getTrace(), getC().getLanguageVersionSettings(), getC().getInferenceSession());
            if (classConstructorDescriptorImplResolvePrimaryConstructorDescriptor == null) {
                return null;
            }
            setDeferredReturnType(classConstructorDescriptorImplResolvePrimaryConstructorDescriptor);
            return classConstructorDescriptorImplResolvePrimaryConstructorDescriptor;
        }
        ClassConstructorDescriptorImpl classConstructorDescriptorImplCreateAndRecordPrimaryConstructorForObject = DescriptorResolver.createAndRecordPrimaryConstructorForObject(correspondingClassOrObject, getThisDescriptor(), getTrace());
        Intrinsics.checkNotNullExpressionValue(classConstructorDescriptorImplCreateAndRecordPrimaryConstructorForObject, "createAndRecordPrimaryConstructorForObject(...)");
        setDeferredReturnType(classConstructorDescriptorImplCreateAndRecordPrimaryConstructorForObject);
        return classConstructorDescriptorImplCreateAndRecordPrimaryConstructorForObject;
    }

    private final Collection<ClassConstructorDescriptor> resolveSecondaryConstructors() {
        KtPureClassOrObject correspondingClassOrObject = getDeclarationProvider().getCorrespondingClassOrObject();
        if (correspondingClassOrObject == null) {
            return CollectionsKt.emptyList();
        }
        List<KtSecondaryConstructor> secondaryConstructors = correspondingClassOrObject.getSecondaryConstructors();
        Intrinsics.checkNotNullExpressionValue(secondaryConstructors, "getSecondaryConstructors(...)");
        List<KtSecondaryConstructor> list = secondaryConstructors;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        for (KtSecondaryConstructor ktSecondaryConstructor : list) {
            FunctionDescriptorResolver functionDescriptorResolver = getC().getFunctionDescriptorResolver();
            LexicalScope scopeForConstructorHeaderResolution = getThisDescriptor().getScopeForConstructorHeaderResolution();
            Intrinsics.checkNotNullExpressionValue(scopeForConstructorHeaderResolution, "getScopeForConstructorHeaderResolution(...)");
            ClassDescriptorWithResolutionScopes thisDescriptor = getThisDescriptor();
            Intrinsics.checkNotNull(ktSecondaryConstructor);
            ClassConstructorDescriptorImpl classConstructorDescriptorImplResolveSecondaryConstructorDescriptor = functionDescriptorResolver.resolveSecondaryConstructorDescriptor(scopeForConstructorHeaderResolution, thisDescriptor, ktSecondaryConstructor, getTrace(), getC().getLanguageVersionSettings(), getC().getInferenceSession());
            setDeferredReturnType(classConstructorDescriptorImplResolveSecondaryConstructorDescriptor);
            arrayList.add(classConstructorDescriptorImplResolveSecondaryConstructorDescriptor);
        }
        return arrayList;
    }

    protected final void setDeferredReturnType(ClassConstructorDescriptorImpl descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        descriptor.setReturnType(getC().getWrappedTypeFactory().createDeferredType(getTrace(), new Function0<KotlinType>() { // from class: org.jetbrains.kotlin.resolve.lazy.descriptors.LazyClassMemberScope.setDeferredReturnType.1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final KotlinType invoke() {
                SimpleType defaultType = LazyClassMemberScope.this.getThisDescriptor().getDefaultType();
                Intrinsics.checkNotNullExpressionValue(defaultType, "getDefaultType(...)");
                return defaultType;
            }
        }));
    }

    @Override // org.jetbrains.kotlin.resolve.scopes.MemberScopeImpl, org.jetbrains.kotlin.resolve.scopes.ResolutionScope
    /* JADX INFO: renamed from: recordLookup */
    public void mo7479recordLookup(Name name2, LookupLocation location) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(location, "location");
        UtilsKt.record(getC().getLookupTracker(), location, getThisDescriptor(), name2);
    }

    @Override // org.jetbrains.kotlin.resolve.lazy.descriptors.AbstractLazyMemberScope
    public String toString() {
        return "lazy scope for class " + getThisDescriptor().getShortName();
    }
}
