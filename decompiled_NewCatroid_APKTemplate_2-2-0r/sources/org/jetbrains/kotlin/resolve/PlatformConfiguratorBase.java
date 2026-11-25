package org.jetbrains.kotlin.resolve;

import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.PlatformSpecificCastChecker;
import org.jetbrains.kotlin.builtins.PlatformToKotlinClassMapper;
import org.jetbrains.kotlin.container.ContainerKt;
import org.jetbrains.kotlin.container.DslKt;
import org.jetbrains.kotlin.container.PlatformExtensionsClashResolver;
import org.jetbrains.kotlin.container.StorageComponentContainer;
import org.jetbrains.kotlin.resolve.calls.checkers.AdditionalTypeChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.AssignmentChecker;
import org.jetbrains.kotlin.resolve.calls.checkers.CallChecker;
import org.jetbrains.kotlin.resolve.checkers.ClassifierUsageChecker;
import org.jetbrains.kotlin.resolve.checkers.DeclarationChecker;
import org.jetbrains.kotlin.resolve.checkers.OptInMarkerDeclarationAnnotationChecker;
import org.jetbrains.kotlin.resolve.lazy.DelegationFilter;
import org.jetbrains.kotlin.types.DynamicTypesSettings;

/* JADX INFO: compiled from: PlatformConfiguratorBase.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\b&\u0018\u00002\u00020\u0001BÙ\u0001\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\u000e\b\u0002\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0005\u0012\u000e\b\u0002\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0005\u0012\u0012\b\u0002\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00120\u0005\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0014\u0012\n\b\u0002\u0010\u0015\u001a\u0004\u0018\u00010\u0016\u0012\n\b\u0002\u0010\u0017\u001a\u0004\u0018\u00010\u0018\u0012\n\b\u0002\u0010\u0019\u001a\u0004\u0018\u00010\u001a\u0012\n\b\u0002\u0010\u001b\u001a\u0004\u0018\u00010\u001c\u0012\n\b\u0002\u0010\u001d\u001a\u0004\u0018\u00010\u001e\u0012\n\b\u0002\u0010\u001f\u001a\u0004\u0018\u00010 ¢\u0006\u0002\u0010!J\u000e\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020#J\u0010\u0010)\u001a\u00020'2\u0006\u0010(\u001a\u00020#H\u0016R\u0014\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00100\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0018\u0010\u0011\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00120\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\f0\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001f\u001a\u0004\u0018\u00010 X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001b\u001a\u0004\u0018\u00010\u001cX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u0014X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u0004\u0018\u00010\u0016X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u001d\u001a\u0004\u0018\u00010\u001eX\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0019\u001a\u0004\u0018\u00010\u001aX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\"\u001a\u00020#X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b$\u0010%R\u0010\u0010\u0017\u001a\u0004\u0018\u00010\u0018X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/resolve/PlatformConfiguratorBase;", "Lorg/jetbrains/kotlin/resolve/PlatformConfigurator;", "dynamicTypesSettings", "Lorg/jetbrains/kotlin/types/DynamicTypesSettings;", "additionalDeclarationCheckers", "", "Lorg/jetbrains/kotlin/resolve/checkers/DeclarationChecker;", "additionalCallCheckers", "Lorg/jetbrains/kotlin/resolve/calls/checkers/CallChecker;", "additionalAssignmentCheckers", "Lorg/jetbrains/kotlin/resolve/calls/checkers/AssignmentChecker;", "additionalTypeCheckers", "Lorg/jetbrains/kotlin/resolve/calls/checkers/AdditionalTypeChecker;", "additionalClassifierUsageCheckers", "Lorg/jetbrains/kotlin/resolve/checkers/ClassifierUsageChecker;", "additionalAnnotationCheckers", "Lorg/jetbrains/kotlin/resolve/AdditionalAnnotationChecker;", "additionalClashResolvers", "Lorg/jetbrains/kotlin/container/PlatformExtensionsClashResolver;", "identifierChecker", "Lorg/jetbrains/kotlin/resolve/IdentifierChecker;", "overloadFilter", "Lorg/jetbrains/kotlin/resolve/OverloadFilter;", "platformToKotlinClassMapper", "Lorg/jetbrains/kotlin/builtins/PlatformToKotlinClassMapper;", "platformSpecificCastChecker", "Lorg/jetbrains/kotlin/builtins/PlatformSpecificCastChecker;", "delegationFilter", "Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;", "overridesBackwardCompatibilityHelper", "Lorg/jetbrains/kotlin/resolve/OverridesBackwardCompatibilityHelper;", "declarationReturnTypeSanitizer", "Lorg/jetbrains/kotlin/resolve/DeclarationReturnTypeSanitizer;", "(Lorg/jetbrains/kotlin/types/DynamicTypesSettings;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/util/List;Lorg/jetbrains/kotlin/resolve/IdentifierChecker;Lorg/jetbrains/kotlin/resolve/OverloadFilter;Lorg/jetbrains/kotlin/builtins/PlatformToKotlinClassMapper;Lorg/jetbrains/kotlin/builtins/PlatformSpecificCastChecker;Lorg/jetbrains/kotlin/resolve/lazy/DelegationFilter;Lorg/jetbrains/kotlin/resolve/OverridesBackwardCompatibilityHelper;Lorg/jetbrains/kotlin/resolve/DeclarationReturnTypeSanitizer;)V", "platformSpecificContainer", "Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "getPlatformSpecificContainer", "()Lorg/jetbrains/kotlin/container/StorageComponentContainer;", "configureExtensionsAndCheckers", "", "container", "configureModuleDependentCheckers", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class PlatformConfiguratorBase implements PlatformConfigurator {
    private final List<AdditionalAnnotationChecker> additionalAnnotationCheckers;
    private final List<AssignmentChecker> additionalAssignmentCheckers;
    private final List<CallChecker> additionalCallCheckers;
    private final List<PlatformExtensionsClashResolver<?>> additionalClashResolvers;
    private final List<ClassifierUsageChecker> additionalClassifierUsageCheckers;
    private final List<DeclarationChecker> additionalDeclarationCheckers;
    private final List<AdditionalTypeChecker> additionalTypeCheckers;
    private final DeclarationReturnTypeSanitizer declarationReturnTypeSanitizer;
    private final DelegationFilter delegationFilter;
    private final DynamicTypesSettings dynamicTypesSettings;
    private final IdentifierChecker identifierChecker;
    private final OverloadFilter overloadFilter;
    private final OverridesBackwardCompatibilityHelper overridesBackwardCompatibilityHelper;
    private final PlatformSpecificCastChecker platformSpecificCastChecker;
    private final StorageComponentContainer platformSpecificContainer;
    private final PlatformToKotlinClassMapper platformToKotlinClassMapper;

    public PlatformConfiguratorBase() {
        this(null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 32767, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public PlatformConfiguratorBase(DynamicTypesSettings dynamicTypesSettings, List<? extends DeclarationChecker> additionalDeclarationCheckers, List<? extends CallChecker> additionalCallCheckers, List<? extends AssignmentChecker> additionalAssignmentCheckers, List<? extends AdditionalTypeChecker> additionalTypeCheckers, List<? extends ClassifierUsageChecker> additionalClassifierUsageCheckers, List<? extends AdditionalAnnotationChecker> additionalAnnotationCheckers, List<? extends PlatformExtensionsClashResolver<?>> additionalClashResolvers, IdentifierChecker identifierChecker, OverloadFilter overloadFilter, PlatformToKotlinClassMapper platformToKotlinClassMapper, PlatformSpecificCastChecker platformSpecificCastChecker, DelegationFilter delegationFilter, OverridesBackwardCompatibilityHelper overridesBackwardCompatibilityHelper, DeclarationReturnTypeSanitizer declarationReturnTypeSanitizer) {
        Intrinsics.checkNotNullParameter(additionalDeclarationCheckers, "additionalDeclarationCheckers");
        Intrinsics.checkNotNullParameter(additionalCallCheckers, "additionalCallCheckers");
        Intrinsics.checkNotNullParameter(additionalAssignmentCheckers, "additionalAssignmentCheckers");
        Intrinsics.checkNotNullParameter(additionalTypeCheckers, "additionalTypeCheckers");
        Intrinsics.checkNotNullParameter(additionalClassifierUsageCheckers, "additionalClassifierUsageCheckers");
        Intrinsics.checkNotNullParameter(additionalAnnotationCheckers, "additionalAnnotationCheckers");
        Intrinsics.checkNotNullParameter(additionalClashResolvers, "additionalClashResolvers");
        this.dynamicTypesSettings = dynamicTypesSettings;
        this.additionalDeclarationCheckers = additionalDeclarationCheckers;
        this.additionalCallCheckers = additionalCallCheckers;
        this.additionalAssignmentCheckers = additionalAssignmentCheckers;
        this.additionalTypeCheckers = additionalTypeCheckers;
        this.additionalClassifierUsageCheckers = additionalClassifierUsageCheckers;
        this.additionalAnnotationCheckers = additionalAnnotationCheckers;
        this.additionalClashResolvers = additionalClashResolvers;
        this.identifierChecker = identifierChecker;
        this.overloadFilter = overloadFilter;
        this.platformToKotlinClassMapper = platformToKotlinClassMapper;
        this.platformSpecificCastChecker = platformSpecificCastChecker;
        this.delegationFilter = delegationFilter;
        this.overridesBackwardCompatibilityHelper = overridesBackwardCompatibilityHelper;
        this.declarationReturnTypeSanitizer = declarationReturnTypeSanitizer;
        String simpleName = getClass().getSimpleName();
        Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
        this.platformSpecificContainer = DslKt.composeContainer$default(simpleName, null, new Function1<StorageComponentContainer, Unit>() { // from class: org.jetbrains.kotlin.resolve.PlatformConfiguratorBase$platformSpecificContainer$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(StorageComponentContainer storageComponentContainer) {
                invoke2(storageComponentContainer);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(StorageComponentContainer composeContainer) {
                Intrinsics.checkNotNullParameter(composeContainer, "$this$composeContainer");
                PlatformConfiguratorBaseKt.configureDefaultCheckers(composeContainer);
                this.this$0.configureExtensionsAndCheckers(composeContainer);
            }
        }, 2, null);
    }

    public /* synthetic */ PlatformConfiguratorBase(DynamicTypesSettings dynamicTypesSettings, List list, List list2, List list3, List list4, List list5, List list6, List list7, IdentifierChecker identifierChecker, OverloadFilter overloadFilter, PlatformToKotlinClassMapper platformToKotlinClassMapper, PlatformSpecificCastChecker platformSpecificCastChecker, DelegationFilter delegationFilter, OverridesBackwardCompatibilityHelper overridesBackwardCompatibilityHelper, DeclarationReturnTypeSanitizer declarationReturnTypeSanitizer, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : dynamicTypesSettings, (i & 2) != 0 ? CollectionsKt.emptyList() : list, (i & 4) != 0 ? CollectionsKt.emptyList() : list2, (i & 8) != 0 ? CollectionsKt.emptyList() : list3, (i & 16) != 0 ? CollectionsKt.emptyList() : list4, (i & 32) != 0 ? CollectionsKt.emptyList() : list5, (i & 64) != 0 ? CollectionsKt.emptyList() : list6, (i & 128) != 0 ? CollectionsKt.emptyList() : list7, (i & 256) != 0 ? null : identifierChecker, (i & 512) != 0 ? null : overloadFilter, (i & 1024) != 0 ? null : platformToKotlinClassMapper, (i & 2048) != 0 ? null : platformSpecificCastChecker, (i & 4096) != 0 ? null : delegationFilter, (i & 8192) != 0 ? null : overridesBackwardCompatibilityHelper, (i & 16384) == 0 ? declarationReturnTypeSanitizer : null);
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformConfigurator
    public StorageComponentContainer getPlatformSpecificContainer() {
        return this.platformSpecificContainer;
    }

    public final void configureExtensionsAndCheckers(StorageComponentContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        DslKt.useInstanceIfNotNull(container, this.dynamicTypesSettings);
        Iterator<T> it = this.additionalDeclarationCheckers.iterator();
        while (it.hasNext()) {
            DslKt.useInstance(container, (DeclarationChecker) it.next());
        }
        Iterator<T> it2 = this.additionalCallCheckers.iterator();
        while (it2.hasNext()) {
            DslKt.useInstance(container, (CallChecker) it2.next());
        }
        Iterator<T> it3 = this.additionalAssignmentCheckers.iterator();
        while (it3.hasNext()) {
            DslKt.useInstance(container, (AssignmentChecker) it3.next());
        }
        Iterator<T> it4 = this.additionalTypeCheckers.iterator();
        while (it4.hasNext()) {
            DslKt.useInstance(container, (AdditionalTypeChecker) it4.next());
        }
        Iterator<T> it5 = this.additionalClassifierUsageCheckers.iterator();
        while (it5.hasNext()) {
            DslKt.useInstance(container, (ClassifierUsageChecker) it5.next());
        }
        Iterator<T> it6 = this.additionalAnnotationCheckers.iterator();
        while (it6.hasNext()) {
            DslKt.useInstance(container, (AdditionalAnnotationChecker) it6.next());
        }
        Iterator<T> it7 = this.additionalClashResolvers.iterator();
        while (it7.hasNext()) {
            DslKt.useClashResolver(container, (PlatformExtensionsClashResolver) it7.next());
        }
        DslKt.useInstanceIfNotNull(container, this.identifierChecker);
        DslKt.useInstanceIfNotNull(container, this.overloadFilter);
        DslKt.useInstanceIfNotNull(container, this.platformToKotlinClassMapper);
        DslKt.useInstanceIfNotNull(container, this.platformSpecificCastChecker);
        DslKt.useInstanceIfNotNull(container, this.delegationFilter);
        DslKt.useInstanceIfNotNull(container, this.overridesBackwardCompatibilityHelper);
        DslKt.useInstanceIfNotNull(container, this.declarationReturnTypeSanitizer);
    }

    @Override // org.jetbrains.kotlin.resolve.PlatformConfigurator
    public void configureModuleDependentCheckers(StorageComponentContainer container) {
        Intrinsics.checkNotNullParameter(container, "container");
        ContainerKt.registerSingleton(container, OptInMarkerDeclarationAnnotationChecker.class);
    }
}
