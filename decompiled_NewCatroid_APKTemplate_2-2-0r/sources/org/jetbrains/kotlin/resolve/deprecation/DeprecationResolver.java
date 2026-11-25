package org.jetbrains.kotlin.resolve.deprecation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.NoWhenBranchMatchedException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.builtins.StandardNames;
import org.jetbrains.kotlin.com.intellij.psi.CommonClassNames;
import org.jetbrains.kotlin.config.ApiVersion;
import org.jetbrains.kotlin.config.LanguageFeature;
import org.jetbrains.kotlin.config.LanguageVersionSettings;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.CallableMemberDescriptor;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyGetterDescriptor;
import org.jetbrains.kotlin.descriptors.SyntheticPropertyDescriptor;
import org.jetbrains.kotlin.descriptors.TypeAliasDescriptor;
import org.jetbrains.kotlin.descriptors.ValueParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.impl.DescriptorDerivedFromTypeAlias;
import org.jetbrains.kotlin.descriptors.impl.TypeAliasConstructorDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.metadata.deserialization.VersionRequirement;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.psi.Call;
import org.jetbrains.kotlin.psi.KtElement;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.SinceKotlinAccessibility;
import org.jetbrains.kotlin.resolve.SinceKotlinUtilKt;
import org.jetbrains.kotlin.resolve.calls.checkers.OperatorCallCheckerKt;
import org.jetbrains.kotlin.resolve.checkers.OptInUsageChecker;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedClassDescriptor;
import org.jetbrains.kotlin.serialization.deserialization.descriptors.DeserializedMemberDescriptor;
import org.jetbrains.kotlin.storage.MemoizedFunctionToNotNull;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.TypeUtils;
import org.jetbrains.kotlin.types.UnwrappedType;
import org.jetbrains.kotlin.utils.SmartList;

/* JADX INFO: compiled from: DeprecationResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0080\u0001\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u0018\u0000 22\u00020\u0001:\u000223B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u001f\u0010\u0012\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u000b\u0082\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0000\u001a\u0004\u0010\u0001(\u0000J\u0010\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u000bH\u0002J\u0012\u0010\u0016\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u0018\u001a\u00020\u0014H\u0002J\u0016\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u001b0\u001a2\u0006\u0010\u001c\u001a\u00020\u000bH\u0002J\u0012\u0010\u001d\u001a\u0004\u0018\u00010\u00172\u0006\u0010\u001c\u001a\u00020\u000bH\u0002J\u0014\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00170\u001a2\u0006\u0010\u0013\u001a\u00020\u000bJ\u0014\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00170\u001a2\u0006\u0010\u0013\u001a\u00020\u000bJ\u000e\u0010 \u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u000bJ<\u0010!\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u000b2\n\b\u0002\u0010\"\u001a\u0004\u0018\u00010#2\n\b\u0002\u0010$\u001a\u0004\u0018\u00010%2\b\b\u0002\u0010&\u001a\u00020\u00102\b\b\u0002\u0010'\u001a\u00020\u0010H\u0007J2\u0010!\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u000b2\b\u0010(\u001a\u0004\u0018\u00010)2\b\u0010$\u001a\u0004\u0018\u00010%2\u0006\u0010&\u001a\u00020\u00102\u0006\u0010'\u001a\u00020\u0010J\u0018\u0010*\u001a\u00020\u00102\u0006\u0010\u0013\u001a\u00020\u000b2\u0006\u0010\u0004\u001a\u00020\u0005H\u0002J\u001a\u0010+\u001a\u00020,*\u00020\u000b2\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00170.H\u0002J\u0012\u0010/\u001a\b\u0012\u0004\u0012\u00020\u00170\u001a*\u000200H\u0002J\u0012\u00101\u001a\b\u0012\u0004\u0012\u00020\u00170\u001a*\u00020\u000bH\u0002R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000e0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u000f\u001a\u00020\u0010*\u00020\u000b8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0011ò\u0001\u0004\n\u00020\u0014¨\u00064"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver;", "", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "languageVersionSettings", "Lorg/jetbrains/kotlin/config/LanguageVersionSettings;", "deprecationSettings", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationSettings;", "(Lorg/jetbrains/kotlin/storage/StorageManager;Lorg/jetbrains/kotlin/config/LanguageVersionSettings;Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationSettings;)V", "deprecations", "Lorg/jetbrains/kotlin/storage/MemoizedFunctionToNotNull;", "Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver$DeprecationInfo;", "isHiddenBecauseOfKotlinVersionAccessibility", "Lorg/jetbrains/kotlin/resolve/SinceKotlinAccessibility;", "isBuiltInOperatorMod", "", "(Lorg/jetbrains/kotlin/descriptors/DeclarationDescriptor;)Z", "areDeprecationsInheritedFromOverriden", "descriptor", "Lorg/jetbrains/kotlin/descriptors/CallableMemberDescriptor;", "computeDeprecation", "deprecationByOverridden", "Lorg/jetbrains/kotlin/resolve/deprecation/DescriptorBasedDeprecationInfo;", "root", "getDeprecationByVersionRequirement", "", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecatedByVersionRequirement;", "target", "getDeprecationFromUserData", "getDeprecations", "getHiddenDeprecationsFromOverriden", "isDeprecatedHidden", "isHiddenInResolution", "call", "Lorg/jetbrains/kotlin/psi/Call;", "bindingContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "isSuperCall", "fromImportingScope", "callElement", "Lorg/jetbrains/kotlin/psi/KtElement;", "shouldSkipDeprecationOnKotlinIoReadBytes", "addDeprecationIfPresent", "", "result", "", "deprecationsByConstituentTypes", "Lorg/jetbrains/kotlin/types/KotlinType;", "getOwnDeprecations", "Companion", "DeprecationInfo", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class DeprecationResolver {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final FqName JAVA_DEPRECATED = new FqName(CommonClassNames.JAVA_LANG_DEPRECATED);
    private final DeprecationSettings deprecationSettings;
    private final MemoizedFunctionToNotNull<DeclarationDescriptor, DeprecationInfo> deprecations;
    private final MemoizedFunctionToNotNull<DeclarationDescriptor, SinceKotlinAccessibility> isHiddenBecauseOfKotlinVersionAccessibility;
    private final LanguageVersionSettings languageVersionSettings;

    public final boolean isHiddenInResolution(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return isHiddenInResolution$default(this, descriptor, null, null, false, false, 30, null);
    }

    public final boolean isHiddenInResolution(DeclarationDescriptor descriptor, Call call) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return isHiddenInResolution$default(this, descriptor, call, null, false, false, 28, null);
    }

    public final boolean isHiddenInResolution(DeclarationDescriptor descriptor, Call call, BindingContext bindingContext) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return isHiddenInResolution$default(this, descriptor, call, bindingContext, false, false, 24, null);
    }

    public final boolean isHiddenInResolution(DeclarationDescriptor descriptor, Call call, BindingContext bindingContext, boolean z) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return isHiddenInResolution$default(this, descriptor, call, bindingContext, z, false, 16, null);
    }

    public DeprecationResolver(StorageManager storageManager, LanguageVersionSettings languageVersionSettings, DeprecationSettings deprecationSettings) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        Intrinsics.checkNotNullParameter(languageVersionSettings, "languageVersionSettings");
        Intrinsics.checkNotNullParameter(deprecationSettings, "deprecationSettings");
        this.languageVersionSettings = languageVersionSettings;
        this.deprecationSettings = deprecationSettings;
        this.deprecations = storageManager.createMemoizedFunction(new Function1<DeclarationDescriptor, DeprecationInfo>() { // from class: org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver$deprecations$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final DeprecationResolver.DeprecationInfo invoke(DeclarationDescriptor descriptor) {
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                return this.this$0.computeDeprecation(descriptor);
            }
        });
        this.isHiddenBecauseOfKotlinVersionAccessibility = storageManager.createMemoizedFunction(new Function1<DeclarationDescriptor, SinceKotlinAccessibility>() { // from class: org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver$isHiddenBecauseOfKotlinVersionAccessibility$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final SinceKotlinAccessibility invoke(DeclarationDescriptor descriptor) {
                Intrinsics.checkNotNullParameter(descriptor, "descriptor");
                return SinceKotlinUtilKt.checkSinceKotlinVersionAccessibility(descriptor, this.this$0.languageVersionSettings);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final DeprecationInfo computeDeprecation(DeclarationDescriptor descriptor) {
        List<DescriptorBasedDeprecationInfo> ownDeprecations = getOwnDeprecations(descriptor);
        if (!ownDeprecations.isEmpty()) {
            return new DeprecationInfo(ownDeprecations, false, null, 4, null);
        }
        if (descriptor instanceof PropertyAccessorDescriptor) {
            PropertyAccessorDescriptor propertyAccessorDescriptor = (PropertyAccessorDescriptor) descriptor;
            if (propertyAccessorDescriptor.getCorrespondingProperty() instanceof SyntheticPropertyDescriptor) {
                PropertyDescriptor correspondingProperty = propertyAccessorDescriptor.getCorrespondingProperty();
                Intrinsics.checkNotNull(correspondingProperty, "null cannot be cast to non-null type org.jetbrains.kotlin.descriptors.SyntheticPropertyDescriptor");
                SyntheticPropertyDescriptor syntheticPropertyDescriptor = (SyntheticPropertyDescriptor) correspondingProperty;
                FunctionDescriptor getMethod = descriptor instanceof PropertyGetterDescriptor ? syntheticPropertyDescriptor.getGetMethod() : syntheticPropertyDescriptor.getSetMethod();
                if (getMethod == null) {
                    return DeprecationInfo.INSTANCE.getEMPTY();
                }
                DeprecationInfo deprecationInfoInvoke = this.deprecations.invoke(getMethod);
                List<DescriptorBasedDeprecationInfo> deprecations = deprecationInfoInvoke.getDeprecations();
                ArrayList arrayList = new ArrayList();
                for (Object obj : deprecations) {
                    DescriptorBasedDeprecationInfo descriptorBasedDeprecationInfo = (DescriptorBasedDeprecationInfo) obj;
                    if (descriptorBasedDeprecationInfo.getDeprecationLevel() == DeprecationLevelValue.WARNING && descriptorBasedDeprecationInfo.getForcePropagationToOverrides()) {
                        arrayList.add(obj);
                    }
                }
                return DeprecationInfo.copy$default(deprecationInfoInvoke, arrayList, false, null, 6, null);
            }
        }
        if (descriptor instanceof CallableMemberDescriptor) {
            List listListOfNotNull = CollectionsKt.listOfNotNull(deprecationByOverridden((CallableMemberDescriptor) descriptor));
            boolean zIsEmpty = listListOfNotNull.isEmpty();
            if (!(!zIsEmpty)) {
                if (zIsEmpty) {
                    return DeprecationInfo.INSTANCE.getEMPTY();
                }
                throw new NoWhenBranchMatchedException();
            }
            boolean zSupportsFeature = this.languageVersionSettings.supportsFeature(LanguageFeature.StopPropagatingDeprecationThroughOverrides);
            if (!zSupportsFeature) {
                if (zSupportsFeature) {
                    throw new NoWhenBranchMatchedException();
                }
                return new DeprecationInfo(listListOfNotNull, true, null, 4, null);
            }
            ArrayList arrayList2 = new ArrayList();
            for (Object obj2 : listListOfNotNull) {
                if (((DescriptorBasedDeprecationInfo) obj2).getForcePropagationToOverrides()) {
                    arrayList2.add(obj2);
                }
            }
            return new DeprecationInfo(arrayList2, true, listListOfNotNull);
        }
        return DeprecationInfo.INSTANCE.getEMPTY();
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: DeprecationResolver.kt */
    @Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0082\b\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B+\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\bJ\u000f\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\u000f\u001a\u00020\u0006HÆ\u0003J\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J3\u0010\u0011\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0001J\u0013\u0010\u0012\u001a\u00020\u00062\b\u0010\u0013\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u0014\u001a\u00020\u0015HÖ\u0001J\t\u0010\u0016\u001a\u00020\u0017HÖ\u0001R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\n¨\u0006\u0019"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver$DeprecationInfo;", "", "deprecations", "", "Lorg/jetbrains/kotlin/resolve/deprecation/DescriptorBasedDeprecationInfo;", "hasInheritedDeprecations", "", "hiddenInheritedDeprecations", "(Ljava/util/List;ZLjava/util/List;)V", "getDeprecations", "()Ljava/util/List;", "getHasInheritedDeprecations", "()Z", "getHiddenInheritedDeprecations", "component1", "component2", "component3", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "other", "hashCode", "", "toString", "", "Companion", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final /* data */ class DeprecationInfo {

        /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
        public static final Companion INSTANCE = new Companion(null);
        private static final DeprecationInfo EMPTY = new DeprecationInfo(CollectionsKt.emptyList(), false, CollectionsKt.emptyList());
        private final List<DescriptorBasedDeprecationInfo> deprecations;
        private final boolean hasInheritedDeprecations;
        private final List<DescriptorBasedDeprecationInfo> hiddenInheritedDeprecations;

        /* JADX WARN: Multi-variable type inference failed */
        public static /* synthetic */ DeprecationInfo copy$default(DeprecationInfo deprecationInfo, List list, boolean z, List list2, int i, Object obj) {
            if ((i & 1) != 0) {
                list = deprecationInfo.deprecations;
            }
            if ((i & 2) != 0) {
                z = deprecationInfo.hasInheritedDeprecations;
            }
            if ((i & 4) != 0) {
                list2 = deprecationInfo.hiddenInheritedDeprecations;
            }
            return deprecationInfo.copy(list, z, list2);
        }

        public final List<DescriptorBasedDeprecationInfo> component1() {
            return this.deprecations;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final boolean getHasInheritedDeprecations() {
            return this.hasInheritedDeprecations;
        }

        public final List<DescriptorBasedDeprecationInfo> component3() {
            return this.hiddenInheritedDeprecations;
        }

        public final DeprecationInfo copy(List<? extends DescriptorBasedDeprecationInfo> deprecations, boolean hasInheritedDeprecations, List<? extends DescriptorBasedDeprecationInfo> hiddenInheritedDeprecations) {
            Intrinsics.checkNotNullParameter(deprecations, "deprecations");
            Intrinsics.checkNotNullParameter(hiddenInheritedDeprecations, "hiddenInheritedDeprecations");
            return new DeprecationInfo(deprecations, hasInheritedDeprecations, hiddenInheritedDeprecations);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DeprecationInfo)) {
                return false;
            }
            DeprecationInfo deprecationInfo = (DeprecationInfo) other;
            return Intrinsics.areEqual(this.deprecations, deprecationInfo.deprecations) && this.hasInheritedDeprecations == deprecationInfo.hasInheritedDeprecations && Intrinsics.areEqual(this.hiddenInheritedDeprecations, deprecationInfo.hiddenInheritedDeprecations);
        }

        public int hashCode() {
            return (((this.deprecations.hashCode() * 31) + Boolean.hashCode(this.hasInheritedDeprecations)) * 31) + this.hiddenInheritedDeprecations.hashCode();
        }

        public String toString() {
            return "DeprecationInfo(deprecations=" + this.deprecations + ", hasInheritedDeprecations=" + this.hasInheritedDeprecations + ", hiddenInheritedDeprecations=" + this.hiddenInheritedDeprecations + ')';
        }

        /* JADX WARN: Multi-variable type inference failed */
        public DeprecationInfo(List<? extends DescriptorBasedDeprecationInfo> deprecations, boolean z, List<? extends DescriptorBasedDeprecationInfo> hiddenInheritedDeprecations) {
            Intrinsics.checkNotNullParameter(deprecations, "deprecations");
            Intrinsics.checkNotNullParameter(hiddenInheritedDeprecations, "hiddenInheritedDeprecations");
            this.deprecations = deprecations;
            this.hasInheritedDeprecations = z;
            this.hiddenInheritedDeprecations = hiddenInheritedDeprecations;
        }

        public final List<DescriptorBasedDeprecationInfo> getDeprecations() {
            return this.deprecations;
        }

        public final boolean getHasInheritedDeprecations() {
            return this.hasInheritedDeprecations;
        }

        public /* synthetic */ DeprecationInfo(List list, boolean z, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this(list, z, (i & 4) != 0 ? CollectionsKt.emptyList() : list2);
        }

        public final List<DescriptorBasedDeprecationInfo> getHiddenInheritedDeprecations() {
            return this.hiddenInheritedDeprecations;
        }

        /* JADX INFO: compiled from: DeprecationResolver.kt */
        @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver$DeprecationInfo$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver$DeprecationInfo;", "getEMPTY", "()Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver$DeprecationInfo;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
        public static final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }

            public final DeprecationInfo getEMPTY() {
                return DeprecationInfo.EMPTY;
            }
        }
    }

    public final List<DescriptorBasedDeprecationInfo> getDeprecations(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        MemoizedFunctionToNotNull<DeclarationDescriptor, DeprecationInfo> memoizedFunctionToNotNull = this.deprecations;
        DeclarationDescriptor original = descriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        return memoizedFunctionToNotNull.invoke(original).getDeprecations();
    }

    public final boolean areDeprecationsInheritedFromOverriden(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        MemoizedFunctionToNotNull<DeclarationDescriptor, DeprecationInfo> memoizedFunctionToNotNull = this.deprecations;
        DeclarationDescriptor original = descriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        return memoizedFunctionToNotNull.invoke(original).getHasInheritedDeprecations();
    }

    public final List<DescriptorBasedDeprecationInfo> getHiddenDeprecationsFromOverriden(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        MemoizedFunctionToNotNull<DeclarationDescriptor, DeprecationInfo> memoizedFunctionToNotNull = this.deprecations;
        DeclarationDescriptor original = descriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        return memoizedFunctionToNotNull.invoke(original).getHiddenInheritedDeprecations();
    }

    public final boolean isDeprecatedHidden(DeclarationDescriptor descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        List<DescriptorBasedDeprecationInfo> deprecations = getDeprecations(descriptor);
        if ((deprecations instanceof Collection) && deprecations.isEmpty()) {
            return false;
        }
        Iterator<T> it = deprecations.iterator();
        while (it.hasNext()) {
            if (((DescriptorBasedDeprecationInfo) it.next()).getDeprecationLevel() == DeprecationLevelValue.HIDDEN) {
                return true;
            }
        }
        return false;
    }

    public static /* synthetic */ boolean isHiddenInResolution$default(DeprecationResolver deprecationResolver, DeclarationDescriptor declarationDescriptor, Call call, BindingContext bindingContext, boolean z, boolean z2, int i, Object obj) {
        return deprecationResolver.isHiddenInResolution(declarationDescriptor, (i & 2) != 0 ? null : call, (i & 4) != 0 ? null : bindingContext, (i & 8) != 0 ? false : z, (i & 16) != 0 ? false : z2);
    }

    public final boolean isHiddenInResolution(DeclarationDescriptor descriptor, Call call, BindingContext bindingContext, boolean isSuperCall, boolean fromImportingScope) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        return isHiddenInResolution(descriptor, call != null ? call.getCallElement() : null, bindingContext, isSuperCall, fromImportingScope);
    }

    public final boolean isHiddenInResolution(DeclarationDescriptor descriptor, KtElement callElement, BindingContext bindingContext, boolean isSuperCall, boolean fromImportingScope) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (descriptor instanceof FunctionDescriptor) {
            FunctionDescriptor functionDescriptor = (FunctionDescriptor) descriptor;
            if (functionDescriptor.isHiddenToOvercomeSignatureClash()) {
                return true;
            }
            if (functionDescriptor.isHiddenForResolutionEverywhereBesideSupercalls() && !isSuperCall) {
                return true;
            }
        }
        MemoizedFunctionToNotNull<DeclarationDescriptor, SinceKotlinAccessibility> memoizedFunctionToNotNull = this.isHiddenBecauseOfKotlinVersionAccessibility;
        DeclarationDescriptor original = descriptor.getOriginal();
        Intrinsics.checkNotNullExpressionValue(original, "getOriginal(...)");
        SinceKotlinAccessibility sinceKotlinAccessibilityInvoke = memoizedFunctionToNotNull.invoke(original);
        if (sinceKotlinAccessibilityInvoke instanceof SinceKotlinAccessibility.NotAccessible) {
            return true;
        }
        if (!(sinceKotlinAccessibilityInvoke instanceof SinceKotlinAccessibility.NotAccessibleButWasExperimental)) {
            return isDeprecatedHidden(descriptor);
        }
        if (callElement != null && bindingContext != null) {
            OptInUsageChecker.Companion companion = OptInUsageChecker.INSTANCE;
            List<ClassDescriptor> markerClasses = ((SinceKotlinAccessibility.NotAccessibleButWasExperimental) sinceKotlinAccessibilityInvoke).getMarkerClasses();
            if (!(markerClasses instanceof Collection) || !markerClasses.isEmpty()) {
                Iterator<T> it = markerClasses.iterator();
                while (it.hasNext()) {
                    if (!companion.isOptInAllowed(callElement, DescriptorUtilsKt.getFqNameSafe((ClassDescriptor) it.next()), this.languageVersionSettings, bindingContext)) {
                        return true;
                    }
                }
            }
        } else if (!fromImportingScope) {
            return true;
        }
        return false;
    }

    private final List<DescriptorBasedDeprecationInfo> deprecationsByConstituentTypes(KotlinType kotlinType) {
        final SmartList smartList = new SmartList();
        TypeUtils.contains(kotlinType, new Function1<UnwrappedType, Boolean>() { // from class: org.jetbrains.kotlin.resolve.deprecation.DeprecationResolver$deprecationsByConstituentTypes$1$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final Boolean invoke(UnwrappedType unwrappedType) {
                ClassifierDescriptor this$0 = unwrappedType.getConstructor().getThis$0();
                if (this$0 != null) {
                    smartList.addAll(this.getDeprecations(this$0));
                }
                return false;
            }
        });
        return smartList;
    }

    private final DescriptorBasedDeprecationInfo deprecationByOverridden(CallableMemberDescriptor root) {
        HashSet hashSet = new HashSet();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        deprecationByOverridden$traverse(hashSet, this, linkedHashSet, booleanRef, root);
        if (linkedHashSet.isEmpty()) {
            return null;
        }
        if (booleanRef.element) {
            LinkedHashSet linkedHashSet2 = linkedHashSet;
            if (!(linkedHashSet2 instanceof Collection) || !linkedHashSet2.isEmpty()) {
                Iterator it = linkedHashSet2.iterator();
                while (it.hasNext()) {
                    if (((DescriptorBasedDeprecationInfo) it.next()).getForcePropagationToOverrides()) {
                    }
                }
            }
            return null;
        }
        if (root.getKind().isReal()) {
            LinkedHashSet linkedHashSet3 = linkedHashSet;
            if (!(linkedHashSet3 instanceof Collection) || !linkedHashSet3.isEmpty()) {
                Iterator it2 = linkedHashSet3.iterator();
                while (it2.hasNext()) {
                    if (((DescriptorBasedDeprecationInfo) it2.next()).getPropagatesToOverrides()) {
                    }
                }
            }
            return null;
        }
        return new DeprecatedByOverridden(linkedHashSet);
    }

    private static final void deprecationByOverridden$traverse(HashSet<CallableMemberDescriptor> hashSet, DeprecationResolver deprecationResolver, LinkedHashSet<DescriptorBasedDeprecationInfo> linkedHashSet, Ref.BooleanRef booleanRef, CallableMemberDescriptor callableMemberDescriptor) {
        if (hashSet.contains(callableMemberDescriptor)) {
            return;
        }
        hashSet.add(callableMemberDescriptor);
        List<DescriptorBasedDeprecationInfo> ownDeprecations = deprecationResolver.getOwnDeprecations(callableMemberDescriptor);
        Collection<? extends CallableMemberDescriptor> overriddenDescriptors = callableMemberDescriptor.getOriginal().getOverriddenDescriptors();
        Intrinsics.checkNotNullExpressionValue(overriddenDescriptors, "getOverriddenDescriptors(...)");
        List<DescriptorBasedDeprecationInfo> list = ownDeprecations;
        if (!list.isEmpty()) {
            linkedHashSet.addAll(list);
        } else {
            if (overriddenDescriptors.isEmpty()) {
                booleanRef.element = true;
                return;
            }
            Iterator<T> it = overriddenDescriptors.iterator();
            while (it.hasNext()) {
                deprecationByOverridden$traverse(hashSet, deprecationResolver, linkedHashSet, booleanRef, (CallableMemberDescriptor) it.next());
            }
        }
    }

    private final List<DescriptorBasedDeprecationInfo> getOwnDeprecations(DeclarationDescriptor declarationDescriptor) {
        if (isBuiltInOperatorMod(declarationDescriptor) && !OperatorCallCheckerKt.shouldWarnAboutDeprecatedModFromBuiltIns(this.languageVersionSettings)) {
            return CollectionsKt.emptyList();
        }
        if (shouldSkipDeprecationOnKotlinIoReadBytes(declarationDescriptor, this.languageVersionSettings)) {
            return CollectionsKt.emptyList();
        }
        SmartList smartList = new SmartList();
        SmartList smartList2 = smartList;
        addDeprecationIfPresent(declarationDescriptor, smartList2);
        if (!(declarationDescriptor instanceof TypeAliasDescriptor)) {
            if (declarationDescriptor instanceof DescriptorDerivedFromTypeAlias) {
                smartList.addAll(getOwnDeprecations(((DescriptorDerivedFromTypeAlias) declarationDescriptor).getTypeAliasDescriptor()));
            } else if (declarationDescriptor instanceof PropertyAccessorDescriptor) {
                PropertyDescriptor correspondingProperty = ((PropertyAccessorDescriptor) declarationDescriptor).getCorrespondingProperty();
                Intrinsics.checkNotNullExpressionValue(correspondingProperty, "getCorrespondingProperty(...)");
                addDeprecationIfPresent(correspondingProperty, smartList2);
            }
        } else {
            TypeAliasDescriptor typeAliasDescriptor = (TypeAliasDescriptor) declarationDescriptor;
            for (DeprecatedTypealiasByAnnotation deprecatedTypealiasByAnnotation : deprecationsByConstituentTypes(typeAliasDescriptor.getExpandedType())) {
                SmartList smartList3 = smartList;
                if (deprecatedTypealiasByAnnotation instanceof DeprecatedByAnnotation) {
                    deprecatedTypealiasByAnnotation = new DeprecatedTypealiasByAnnotation(typeAliasDescriptor, (DeprecatedByAnnotation) deprecatedTypealiasByAnnotation);
                }
                smartList3.add(deprecatedTypealiasByAnnotation);
            }
        }
        return CollectionsKt.distinct(smartList);
    }

    private final void addDeprecationIfPresent(DeclarationDescriptor declarationDescriptor, List<DescriptorBasedDeprecationInfo> list) {
        DeprecatedByAnnotation deprecatedByAnnotationCreate;
        DeprecatedByAnnotation deprecatedOperatorMod;
        AnnotationDescriptor annotationDescriptorMo6752findAnnotation = declarationDescriptor.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.deprecated);
        if (annotationDescriptorMo6752findAnnotation == null) {
            annotationDescriptorMo6752findAnnotation = declarationDescriptor.getAnnotations().mo6752findAnnotation(JAVA_DEPRECATED);
        }
        AnnotationDescriptor annotationDescriptor = annotationDescriptorMo6752findAnnotation;
        if (annotationDescriptor != null && (deprecatedByAnnotationCreate = DeprecatedByAnnotation.INSTANCE.create(annotationDescriptor, declarationDescriptor.getAnnotations().mo6752findAnnotation(StandardNames.FqNames.deprecatedSinceKotlin), declarationDescriptor, this.deprecationSettings.propagatedToOverrides(annotationDescriptor), this.languageVersionSettings.getApiVersion())) != null) {
            if (declarationDescriptor instanceof TypeAliasConstructorDescriptor) {
                deprecatedOperatorMod = new DeprecatedTypealiasByAnnotation(((TypeAliasConstructorDescriptor) declarationDescriptor).getTypeAliasDescriptor(), deprecatedByAnnotationCreate);
            } else if (isBuiltInOperatorMod(declarationDescriptor)) {
                deprecatedOperatorMod = new DeprecatedOperatorMod(this.languageVersionSettings, deprecatedByAnnotationCreate);
            } else {
                deprecatedOperatorMod = deprecatedByAnnotationCreate;
            }
            list.add(deprecatedOperatorMod);
        }
        Iterator<DeprecatedByVersionRequirement> it = getDeprecationByVersionRequirement(declarationDescriptor).iterator();
        while (it.hasNext()) {
            list.add(it.next());
        }
        DescriptorBasedDeprecationInfo deprecationFromUserData = getDeprecationFromUserData(declarationDescriptor);
        if (deprecationFromUserData != null) {
            list.add(deprecationFromUserData);
        }
    }

    private final boolean isBuiltInOperatorMod(DeclarationDescriptor declarationDescriptor) {
        return (declarationDescriptor instanceof FunctionDescriptor) && OperatorCallCheckerKt.isOperatorMod((FunctionDescriptor) declarationDescriptor) && KotlinBuiltIns.isUnderKotlinPackage(declarationDescriptor);
    }

    private final boolean shouldSkipDeprecationOnKotlinIoReadBytes(DeclarationDescriptor descriptor, LanguageVersionSettings languageVersionSettings) {
        KotlinType type;
        FqName fqName;
        if (Intrinsics.areEqual(descriptor.getShortName().asString(), "readBytes")) {
            DeclarationDescriptor containingDeclaration = descriptor.getPackageFragment();
            String strAsString = null;
            PackageFragmentDescriptor packageFragmentDescriptor = containingDeclaration instanceof PackageFragmentDescriptor ? (PackageFragmentDescriptor) containingDeclaration : null;
            if (packageFragmentDescriptor != null && (fqName = packageFragmentDescriptor.getFqName()) != null) {
                strAsString = fqName.asString();
            }
            if (Intrinsics.areEqual(strAsString, "kotlin.io") && (descriptor instanceof FunctionDescriptor)) {
                List<ValueParameterDescriptor> valueParameters = ((FunctionDescriptor) descriptor).getValueParameters();
                Intrinsics.checkNotNullExpressionValue(valueParameters, "getValueParameters(...)");
                ValueParameterDescriptor valueParameterDescriptor = (ValueParameterDescriptor) CollectionsKt.singleOrNull((List) valueParameters);
                if (valueParameterDescriptor != null && (type = valueParameterDescriptor.getType()) != null && KotlinBuiltIns.isInt(type) && languageVersionSettings.getApiVersion().compareTo(ApiVersion.KOTLIN_1_3) < 0) {
                    return true;
                }
            }
        }
        return false;
    }

    private final DescriptorBasedDeprecationInfo getDeprecationFromUserData(DeclarationDescriptor target) {
        CallableDescriptor callableDescriptor = target instanceof CallableDescriptor ? (CallableDescriptor) target : null;
        if (callableDescriptor != null) {
            return (DescriptorBasedDeprecationInfo) callableDescriptor.getUserData(DescriptorBasedDeprecationInfoKt.getDEPRECATED_FUNCTION_KEY());
        }
        return null;
    }

    private final List<DeprecatedByVersionRequirement> getDeprecationByVersionRequirement(DeclarationDescriptor target) {
        List<VersionRequirement> versionRequirements;
        DeserializedMemberDescriptor deserializedMemberDescriptor = target instanceof DeserializedMemberDescriptor ? (DeserializedMemberDescriptor) target : null;
        if (deserializedMemberDescriptor == null || (versionRequirements = deserializedMemberDescriptor.getVersionRequirements()) == null) {
            DeserializedClassDescriptor deserializedClassDescriptor = target instanceof DeserializedClassDescriptor ? (DeserializedClassDescriptor) target : null;
            if (deserializedClassDescriptor == null) {
                return CollectionsKt.emptyList();
            }
            versionRequirements = deserializedClassDescriptor.getVersionRequirements();
        }
        ArrayList arrayList = new ArrayList();
        for (VersionRequirement versionRequirement : versionRequirements) {
            DeprecatedByVersionRequirement deprecatedByVersionRequirement = !CommonDeprecationUtilsKt.isFulfilled(versionRequirement, this.languageVersionSettings) ? new DeprecatedByVersionRequirement(versionRequirement, target) : null;
            if (deprecatedByVersionRequirement != null) {
                arrayList.add(deprecatedByVersionRequirement);
            }
        }
        return arrayList;
    }

    /* JADX INFO: compiled from: DeprecationResolver.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/deprecation/DeprecationResolver$Companion;", "", "()V", "JAVA_DEPRECATED", "Lorg/jetbrains/kotlin/name/FqName;", "getJAVA_DEPRECATED", "()Lorg/jetbrains/kotlin/name/FqName;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final FqName getJAVA_DEPRECATED() {
            return DeprecationResolver.JAVA_DEPRECATED;
        }
    }
}
