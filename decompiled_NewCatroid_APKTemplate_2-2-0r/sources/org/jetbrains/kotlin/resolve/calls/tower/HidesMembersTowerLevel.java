package org.jetbrains.kotlin.resolve.calls.tower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.descriptors.CallableDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.VariableDescriptor;
import org.jetbrains.kotlin.incremental.components.LookupLocation;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveKt;
import org.jetbrains.kotlin.resolve.descriptorUtil.AnnotationsForResolveUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValueWithSmartCastInfo;
import org.jetbrains.kotlin.resolve.scopes.utils.ScopeUtilsKt;

/* JADX INFO: compiled from: TowerLevels.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004JK\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000b2)\u0010\f\u001a%\u0012\u0004\u0012\u00020\u000e\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u000f\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00100\u00060\r¢\u0006\u0002\b\u0011H\u0002J \u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J \u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00070\u00142\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J \u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00070\u00062\u0006\u0010\b\u001a\u00020\t2\b\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0016J\u0010\u0010\u0016\u001a\u00020\u00172\u0006\u0010\b\u001a\u00020\tH\u0016¨\u0006\u0018"}, d2 = {"Lorg/jetbrains/kotlin/resolve/calls/tower/HidesMembersTowerLevel;", "Lorg/jetbrains/kotlin/resolve/calls/tower/AbstractScopeTowerLevel;", "scopeTower", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;", "(Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitScopeTower;)V", "getCandidates", "", "Lorg/jetbrains/kotlin/resolve/calls/tower/CandidateWithBoundDispatchReceiver;", "name", "Lorg/jetbrains/kotlin/name/Name;", "extensionReceiver", "Lorg/jetbrains/kotlin/resolve/scopes/receivers/ReceiverValueWithSmartCastInfo;", "collectCandidates", "Lkotlin/Function3;", "Lorg/jetbrains/kotlin/resolve/scopes/LexicalScope;", "Lorg/jetbrains/kotlin/incremental/components/LookupLocation;", "Lorg/jetbrains/kotlin/descriptors/CallableDescriptor;", "Lkotlin/ExtensionFunctionType;", "getFunctions", "getObjects", "", "getVariables", "recordLookup", "", "resolution"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class HidesMembersTowerLevel extends AbstractScopeTowerLevel {
    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public void recordLookup(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HidesMembersTowerLevel(ImplicitScopeTower scopeTower) {
        super(scopeTower);
        Intrinsics.checkNotNullParameter(scopeTower, "scopeTower");
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.tower.HidesMembersTowerLevel$getVariables$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: TowerLevels.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C49831 extends FunctionReference implements Function3<LexicalScope, Name, LookupLocation, Collection<? extends VariableDescriptor>> {
        public static final C49831 INSTANCE = new C49831();

        C49831() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "collectVariables(Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/incremental/components/LookupLocation;)Ljava/util/Collection;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "collectVariables";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(ScopeUtilsKt.class, "resolution");
        }

        @Override // kotlin.jvm.functions.Function3
        public final Collection<VariableDescriptor> invoke(LexicalScope p0, Name p1, LookupLocation p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            return ScopeUtilsKt.collectVariables(p0, p1, p2);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public Collection<CandidateWithBoundDispatchReceiver> getVariables(Name name2, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getCandidates(name2, extensionReceiver, C49831.INSTANCE);
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public List<CandidateWithBoundDispatchReceiver> getObjects(Name name2, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return CollectionsKt.emptyList();
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.resolve.calls.tower.HidesMembersTowerLevel$getFunctions$1, reason: invalid class name */
    /* JADX INFO: compiled from: TowerLevels.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function3<LexicalScope, Name, LookupLocation, Collection<? extends FunctionDescriptor>> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        AnonymousClass1() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "collectFunctions(Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/incremental/components/LookupLocation;)Ljava/util/Collection;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "collectFunctions";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinPackage(ScopeUtilsKt.class, "resolution");
        }

        @Override // kotlin.jvm.functions.Function3
        public final Collection<FunctionDescriptor> invoke(LexicalScope p0, Name p1, LookupLocation p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            return ScopeUtilsKt.collectFunctions(p0, p1, p2);
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ScopeTowerLevel
    public Collection<CandidateWithBoundDispatchReceiver> getFunctions(Name name2, ReceiverValueWithSmartCastInfo extensionReceiver) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return getCandidates(name2, extensionReceiver, AnonymousClass1.INSTANCE);
    }

    private final Collection<CandidateWithBoundDispatchReceiver> getCandidates(Name name2, ReceiverValueWithSmartCastInfo extensionReceiver, Function3<? super LexicalScope, ? super Name, ? super LookupLocation, ? extends Collection<? extends CallableDescriptor>> collectCandidates) {
        if (extensionReceiver == null) {
            return CollectionsKt.emptyList();
        }
        if (!AnnotationsForResolveKt.getHIDES_MEMBERS_NAME_LIST().contains(name2) && !CollectionsKt.contains(AnnotationsForResolveKt.getHIDES_MEMBERS_NAME_LIST(), getScopeTower().getNameForGivenImportAlias(name2))) {
            return CollectionsKt.emptyList();
        }
        Collection<? extends CallableDescriptor> collectionInvoke = collectCandidates.invoke(getScopeTower().getLexicalScope(), name2, getLocation());
        ArrayList arrayList = new ArrayList();
        for (Object obj : collectionInvoke) {
            CallableDescriptor callableDescriptor = (CallableDescriptor) obj;
            if (callableDescriptor.getExtensionReceiverParameter() != null && AnnotationsForResolveUtilsKt.hasHidesMembersAnnotation(callableDescriptor)) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList(CollectionsKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it = arrayList2.iterator();
        while (it.hasNext()) {
            arrayList3.add(AbstractScopeTowerLevel.createCandidateDescriptor$default(this, (CallableDescriptor) it.next(), null, null, null, 12, null));
        }
        return arrayList3;
    }
}
