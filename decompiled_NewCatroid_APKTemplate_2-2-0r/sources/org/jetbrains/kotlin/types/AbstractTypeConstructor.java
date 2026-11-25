package org.jetbrains.kotlin.types;

import java.util.Collection;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.LazyThreadSafetyMode;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.builtins.KotlinBuiltIns;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.descriptors.ClassifierDescriptor;
import org.jetbrains.kotlin.descriptors.SupertypeLoopChecker;
import org.jetbrains.kotlin.descriptors.TypeParameterDescriptor;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.jetbrains.kotlin.storage.StorageManager;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefiner;
import org.jetbrains.kotlin.types.checker.KotlinTypeRefinerKt;

/* JADX INFO: compiled from: AbstractTypeConstructor.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\b&\u0018\u00002\u00020\u0001:\u0002%&B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011H$J\n\u0010\u0013\u001a\u0004\u0018\u00010\u0012H\u0014J\u0016\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00120\u00112\u0006\u0010\u0015\u001a\u00020\u0006H\u0014J\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00120\u0017H\u0016J!\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00120\u00172\u0011\u0010\r\u001a\r\u0012\t\u0012\u00070\u0012¢\u0006\u0002\b\u00190\u0017H\u0014J\u0010\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dH\u0017J\u0006\u0010\u001e\u001a\u00020\u001fJ\u0010\u0010 \u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0012H\u0014J\u0010\u0010#\u001a\u00020!2\u0006\u0010\"\u001a\u00020\u0012H\u0014J\u001a\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00120\u0011*\u00020\u001b2\u0006\u0010\u0015\u001a\u00020\u0006H\u0002R\u0014\u0010\u0005\u001a\u00020\u0006X\u0094D¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\nX¤\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000eX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006'"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractTypeConstructor;", "Lorg/jetbrains/kotlin/types/ClassifierBasedTypeConstructor;", "storageManager", "Lorg/jetbrains/kotlin/storage/StorageManager;", "(Lorg/jetbrains/kotlin/storage/StorageManager;)V", "shouldReportCyclicScopeWithCompanionWarning", "", "getShouldReportCyclicScopeWithCompanionWarning", "()Z", "supertypeLoopChecker", "Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "getSupertypeLoopChecker", "()Lorg/jetbrains/kotlin/descriptors/SupertypeLoopChecker;", "supertypes", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "Lorg/jetbrains/kotlin/types/AbstractTypeConstructor$Supertypes;", "computeSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "defaultSupertypeIfEmpty", "getAdditionalNeighboursInSupertypeGraph", "useCompanions", "getSupertypes", "", "processSupertypesWithoutCycles", "Lkotlin/jvm/JvmSuppressWildcards;", "refine", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "renderAdditionalDebugInformation", "", "reportScopesLoopError", "", ModuleXmlParser.TYPE, "reportSupertypeLoopError", "computeNeighbours", "ModuleViewTypeConstructor", "Supertypes", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractTypeConstructor extends ClassifierBasedTypeConstructor {
    private final boolean shouldReportCyclicScopeWithCompanionWarning;
    private final NotNullLazyValue<Supertypes> supertypes;

    protected abstract Collection<KotlinType> computeSupertypes();

    protected KotlinType defaultSupertypeIfEmpty() {
        return null;
    }

    protected abstract SupertypeLoopChecker getSupertypeLoopChecker();

    protected List<KotlinType> processSupertypesWithoutCycles(List<KotlinType> supertypes) {
        Intrinsics.checkNotNullParameter(supertypes, "supertypes");
        return supertypes;
    }

    protected void reportScopesLoopError(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
    }

    protected void reportSupertypeLoopError(KotlinType type) {
        Intrinsics.checkNotNullParameter(type, "type");
    }

    public AbstractTypeConstructor(StorageManager storageManager) {
        Intrinsics.checkNotNullParameter(storageManager, "storageManager");
        this.supertypes = storageManager.createLazyValueWithPostCompute(new Function0<Supertypes>() { // from class: org.jetbrains.kotlin.types.AbstractTypeConstructor$supertypes$1
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final AbstractTypeConstructor.Supertypes invoke() {
                return new AbstractTypeConstructor.Supertypes(this.this$0.computeSupertypes());
            }
        }, new Function1<Boolean, Supertypes>() { // from class: org.jetbrains.kotlin.types.AbstractTypeConstructor$supertypes$2
            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ AbstractTypeConstructor.Supertypes invoke(Boolean bool) {
                return invoke(bool.booleanValue());
            }

            public final AbstractTypeConstructor.Supertypes invoke(boolean z) {
                return new AbstractTypeConstructor.Supertypes(CollectionsKt.listOf(org.jetbrains.kotlin.types.error.ErrorUtils.INSTANCE.getErrorTypeForLoopInSupertypes()));
            }
        }, new Function1<Supertypes, Unit>() { // from class: org.jetbrains.kotlin.types.AbstractTypeConstructor$supertypes$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(AbstractTypeConstructor.Supertypes supertypes) {
                invoke2(supertypes);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(AbstractTypeConstructor.Supertypes supertypes) {
                Intrinsics.checkNotNullParameter(supertypes, "supertypes");
                SupertypeLoopChecker supertypeLoopChecker = this.this$0.getSupertypeLoopChecker();
                AbstractTypeConstructor abstractTypeConstructor = this.this$0;
                Collection<KotlinType> allSupertypes = supertypes.getAllSupertypes();
                final AbstractTypeConstructor abstractTypeConstructor2 = this.this$0;
                Function1<TypeConstructor, Iterable<? extends KotlinType>> function1 = new Function1<TypeConstructor, Iterable<? extends KotlinType>>() { // from class: org.jetbrains.kotlin.types.AbstractTypeConstructor$supertypes$3$resultWithoutCycles$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public final Iterable<KotlinType> invoke(TypeConstructor it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        return abstractTypeConstructor2.computeNeighbours(it, false);
                    }
                };
                final AbstractTypeConstructor abstractTypeConstructor3 = this.this$0;
                List listFindLoopsInSupertypesAndDisconnect = supertypeLoopChecker.findLoopsInSupertypesAndDisconnect(abstractTypeConstructor, allSupertypes, function1, new Function1<KotlinType, Unit>() { // from class: org.jetbrains.kotlin.types.AbstractTypeConstructor$supertypes$3$resultWithoutCycles$2
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(KotlinType kotlinType) {
                        invoke2(kotlinType);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(KotlinType it) {
                        Intrinsics.checkNotNullParameter(it, "it");
                        abstractTypeConstructor3.reportSupertypeLoopError(it);
                    }
                });
                if (listFindLoopsInSupertypesAndDisconnect.isEmpty()) {
                    KotlinType kotlinTypeDefaultSupertypeIfEmpty = this.this$0.defaultSupertypeIfEmpty();
                    List listListOf = kotlinTypeDefaultSupertypeIfEmpty != null ? CollectionsKt.listOf(kotlinTypeDefaultSupertypeIfEmpty) : null;
                    if (listListOf == null) {
                        listListOf = CollectionsKt.emptyList();
                    }
                    listFindLoopsInSupertypesAndDisconnect = listListOf;
                }
                if (this.this$0.getShouldReportCyclicScopeWithCompanionWarning()) {
                    SupertypeLoopChecker supertypeLoopChecker2 = this.this$0.getSupertypeLoopChecker();
                    AbstractTypeConstructor abstractTypeConstructor4 = this.this$0;
                    final AbstractTypeConstructor abstractTypeConstructor5 = this.this$0;
                    Function1<TypeConstructor, Iterable<? extends KotlinType>> function2 = new Function1<TypeConstructor, Iterable<? extends KotlinType>>() { // from class: org.jetbrains.kotlin.types.AbstractTypeConstructor$supertypes$3.2
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Iterable<KotlinType> invoke(TypeConstructor it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            return abstractTypeConstructor5.computeNeighbours(it, true);
                        }
                    };
                    final AbstractTypeConstructor abstractTypeConstructor6 = this.this$0;
                    supertypeLoopChecker2.findLoopsInSupertypesAndDisconnect(abstractTypeConstructor4, listFindLoopsInSupertypesAndDisconnect, function2, new Function1<KotlinType, Unit>() { // from class: org.jetbrains.kotlin.types.AbstractTypeConstructor$supertypes$3.3
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public /* bridge */ /* synthetic */ Unit invoke(KotlinType kotlinType) {
                            invoke2(kotlinType);
                            return Unit.INSTANCE;
                        }

                        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                        public final void invoke2(KotlinType it) {
                            Intrinsics.checkNotNullParameter(it, "it");
                            abstractTypeConstructor6.reportScopesLoopError(it);
                        }
                    });
                }
                AbstractTypeConstructor abstractTypeConstructor7 = this.this$0;
                List<KotlinType> list = listFindLoopsInSupertypesAndDisconnect instanceof List ? (List) listFindLoopsInSupertypesAndDisconnect : null;
                if (list == null) {
                    list = CollectionsKt.toList(listFindLoopsInSupertypesAndDisconnect);
                }
                supertypes.setSupertypesWithoutCycles(abstractTypeConstructor7.processSupertypesWithoutCycles(list));
            }
        });
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    /* JADX INFO: renamed from: getSupertypes */
    public List<KotlinType> mo7438getSupertypes() {
        return this.supertypes.invoke().getSupertypesWithoutCycles();
    }

    @Override // org.jetbrains.kotlin.types.TypeConstructor
    public TypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner) {
        Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
        return new ModuleViewTypeConstructor(this, kotlinTypeRefiner);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AbstractTypeConstructor.kt */
    @Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0000\b\u0083\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0096\u0002J\b\u0010\u0010\u001a\u00020\u0011H\u0016J\b\u0010\u0012\u001a\u00020\u0013H\u0016J\u000e\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00150\u0006H\u0016J\u000e\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0016J\b\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010\u0019\u001a\u00020\rH\u0016J\b\u0010\u001a\u001a\u00020\rH\u0016J\u0010\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R!\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\n\u0010\u000b\u001a\u0004\b\b\u0010\t¨\u0006\u001e"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractTypeConstructor$ModuleViewTypeConstructor;", "Lorg/jetbrains/kotlin/types/TypeConstructor;", "kotlinTypeRefiner", "Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;", "(Lorg/jetbrains/kotlin/types/AbstractTypeConstructor;Lorg/jetbrains/kotlin/types/checker/KotlinTypeRefiner;)V", "refinedSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "getRefinedSupertypes", "()Ljava/util/List;", "refinedSupertypes$delegate", "Lkotlin/Lazy;", Namer.EQUALS_METHOD_NAME, "", "other", "", "getBuiltIns", "Lorg/jetbrains/kotlin/builtins/KotlinBuiltIns;", "getDeclarationDescriptor", "Lorg/jetbrains/kotlin/descriptors/ClassifierDescriptor;", "getParameters", "Lorg/jetbrains/kotlin/descriptors/TypeParameterDescriptor;", "getSupertypes", "hashCode", "", "isDenotable", "isFinal", "refine", "toString", "", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    final class ModuleViewTypeConstructor implements TypeConstructor {
        private final KotlinTypeRefiner kotlinTypeRefiner;

        /* JADX INFO: renamed from: refinedSupertypes$delegate, reason: from kotlin metadata */
        private final Lazy refinedSupertypes;
        final /* synthetic */ AbstractTypeConstructor this$0;

        public ModuleViewTypeConstructor(final AbstractTypeConstructor abstractTypeConstructor, KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            this.this$0 = abstractTypeConstructor;
            this.kotlinTypeRefiner = kotlinTypeRefiner;
            this.refinedSupertypes = LazyKt.lazy(LazyThreadSafetyMode.PUBLICATION, (Function0) new Function0<List<? extends KotlinType>>() { // from class: org.jetbrains.kotlin.types.AbstractTypeConstructor$ModuleViewTypeConstructor$refinedSupertypes$2
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends KotlinType> invoke() {
                    return KotlinTypeRefinerKt.refineTypes(this.this$0.kotlinTypeRefiner, abstractTypeConstructor.mo7438getSupertypes());
                }
            });
        }

        private final List<KotlinType> getRefinedSupertypes() {
            return (List) this.refinedSupertypes.getValue();
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public List<TypeParameterDescriptor> getParameters() {
            List<TypeParameterDescriptor> parameters = this.this$0.getParameters();
            Intrinsics.checkNotNullExpressionValue(parameters, "getParameters(...)");
            return parameters;
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        /* JADX INFO: renamed from: getSupertypes */
        public List<KotlinType> mo7438getSupertypes() {
            return getRefinedSupertypes();
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public boolean isFinal() {
            return this.this$0.isFinal();
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public boolean isDenotable() {
            return this.this$0.isDenotable();
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        /* JADX INFO: renamed from: getDeclarationDescriptor */
        public ClassifierDescriptor getThis$0() {
            return this.this$0.getThis$0();
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public KotlinBuiltIns getBuiltIns() {
            KotlinBuiltIns builtIns = this.this$0.getBuiltIns();
            Intrinsics.checkNotNullExpressionValue(builtIns, "getBuiltIns(...)");
            return builtIns;
        }

        @Override // org.jetbrains.kotlin.types.TypeConstructor
        public TypeConstructor refine(KotlinTypeRefiner kotlinTypeRefiner) {
            Intrinsics.checkNotNullParameter(kotlinTypeRefiner, "kotlinTypeRefiner");
            return this.this$0.refine(kotlinTypeRefiner);
        }

        public boolean equals(Object other) {
            return this.this$0.equals(other);
        }

        public int hashCode() {
            return this.this$0.hashCode();
        }

        public String toString() {
            return this.this$0.toString();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: AbstractTypeConstructor.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0005\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R \u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00040\tX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/types/AbstractTypeConstructor$Supertypes;", "", "allSupertypes", "", "Lorg/jetbrains/kotlin/types/KotlinType;", "(Ljava/util/Collection;)V", "getAllSupertypes", "()Ljava/util/Collection;", "supertypesWithoutCycles", "", "getSupertypesWithoutCycles", "()Ljava/util/List;", "setSupertypesWithoutCycles", "(Ljava/util/List;)V", "descriptors"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class Supertypes {
        private final Collection<KotlinType> allSupertypes;
        private List<? extends KotlinType> supertypesWithoutCycles;

        /* JADX WARN: Multi-variable type inference failed */
        public Supertypes(Collection<? extends KotlinType> allSupertypes) {
            Intrinsics.checkNotNullParameter(allSupertypes, "allSupertypes");
            this.allSupertypes = allSupertypes;
            this.supertypesWithoutCycles = CollectionsKt.listOf(org.jetbrains.kotlin.types.error.ErrorUtils.INSTANCE.getErrorTypeForLoopInSupertypes());
        }

        public final Collection<KotlinType> getAllSupertypes() {
            return this.allSupertypes;
        }

        public final List<KotlinType> getSupertypesWithoutCycles() {
            return this.supertypesWithoutCycles;
        }

        public final void setSupertypesWithoutCycles(List<? extends KotlinType> list) {
            Intrinsics.checkNotNullParameter(list, "<set-?>");
            this.supertypesWithoutCycles = list;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final Collection<KotlinType> computeNeighbours(TypeConstructor typeConstructor, boolean z) {
        List listPlus;
        AbstractTypeConstructor abstractTypeConstructor = typeConstructor instanceof AbstractTypeConstructor ? (AbstractTypeConstructor) typeConstructor : null;
        if (abstractTypeConstructor != null && (listPlus = CollectionsKt.plus((Collection) abstractTypeConstructor.supertypes.invoke().getAllSupertypes(), (Iterable) abstractTypeConstructor.getAdditionalNeighboursInSupertypeGraph(z))) != null) {
            return listPlus;
        }
        Collection<KotlinType> collectionMo7438getSupertypes = typeConstructor.mo7438getSupertypes();
        Intrinsics.checkNotNullExpressionValue(collectionMo7438getSupertypes, "getSupertypes(...)");
        return collectionMo7438getSupertypes;
    }

    protected boolean getShouldReportCyclicScopeWithCompanionWarning() {
        return this.shouldReportCyclicScopeWithCompanionWarning;
    }

    protected Collection<KotlinType> getAdditionalNeighboursInSupertypeGraph(boolean useCompanions) {
        return CollectionsKt.emptyList();
    }

    public final String renderAdditionalDebugInformation() {
        return "supertypes=" + this.supertypes.renderDebugInformation();
    }
}
