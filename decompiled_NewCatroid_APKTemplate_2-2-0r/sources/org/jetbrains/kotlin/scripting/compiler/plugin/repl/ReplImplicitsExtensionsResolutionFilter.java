package org.jetbrains.kotlin.scripting.compiler.plugin.repl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.SetsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.script.experimental.api.KotlinType;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.resolve.calls.tower.ImplicitsExtensionsResolutionFilter;
import org.jetbrains.kotlin.resolve.calls.tower.ScopeWithImplicitsExtensionsResolutionInfo;
import org.jetbrains.kotlin.resolve.descriptorUtil.DescriptorUtilsKt;
import org.jetbrains.kotlin.resolve.scopes.HierarchicalScope;
import org.jetbrains.kotlin.resolve.scopes.LexicalScope;
import org.jetbrains.kotlin.resolve.scopes.receivers.ImplicitClassReceiver;
import org.jetbrains.kotlin.resolve.scopes.receivers.ReceiverValue;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: ReplImplicitsExtensionsResolutionFilter.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\u0018\u00002\u00020\u0001B%\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0006J\u001c\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000e2\f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u000eH\u0016J&\u0010\u0012\u001a\u00020\u00132\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003R\u0014\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\n\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0082\u000e¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/scripting/compiler/plugin/repl/ReplImplicitsExtensionsResolutionFilter;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ImplicitsExtensionsResolutionFilter;", "classesToSkip", "", "Lkotlin/script/experimental/api/KotlinType;", "classesToSkipAfterFirstTime", "(Ljava/util/Collection;Ljava/util/Collection;)V", "classesToSkipFirstTimeNames", "", "", "classesToSkipNames", "lock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "getScopesWithInfo", "Lkotlin/sequences/Sequence;", "Lorg/jetbrains/kotlin/resolve/calls/tower/ScopeWithImplicitsExtensionsResolutionInfo;", "scopes", "Lorg/jetbrains/kotlin/resolve/scopes/HierarchicalScope;", ConfigConstants.CONFIG_KEY_UPDATE, "", PathUtil.KOTLIN_SCRIPTING_COMPILER_PLUGIN_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ReplImplicitsExtensionsResolutionFilter implements ImplicitsExtensionsResolutionFilter {
    private Set<String> classesToSkipFirstTimeNames;
    private Set<String> classesToSkipNames;
    private final ReentrantReadWriteLock lock;

    /* JADX WARN: Multi-variable type inference failed */
    public ReplImplicitsExtensionsResolutionFilter() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    public ReplImplicitsExtensionsResolutionFilter(Collection<KotlinType> classesToSkip, Collection<KotlinType> classesToSkipAfterFirstTime) {
        Intrinsics.checkNotNullParameter(classesToSkip, "classesToSkip");
        Intrinsics.checkNotNullParameter(classesToSkipAfterFirstTime, "classesToSkipAfterFirstTime");
        this.lock = new ReentrantReadWriteLock();
        this.classesToSkipNames = SetsKt.emptySet();
        this.classesToSkipFirstTimeNames = SetsKt.emptySet();
        update(classesToSkip, classesToSkipAfterFirstTime);
    }

    public /* synthetic */ ReplImplicitsExtensionsResolutionFilter(List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CollectionsKt.emptyList() : list, (i & 2) != 0 ? CollectionsKt.emptyList() : list2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ void update$default(ReplImplicitsExtensionsResolutionFilter replImplicitsExtensionsResolutionFilter, Collection collection, Collection collection2, int i, Object obj) {
        if ((i & 1) != 0) {
            collection = CollectionsKt.emptyList();
        }
        if ((i & 2) != 0) {
            collection2 = CollectionsKt.emptyList();
        }
        replImplicitsExtensionsResolutionFilter.update(collection, collection2);
    }

    public final void update(Collection<KotlinType> classesToSkip, Collection<KotlinType> classesToSkipAfterFirstTime) {
        Intrinsics.checkNotNullParameter(classesToSkip, "classesToSkip");
        Intrinsics.checkNotNullParameter(classesToSkipAfterFirstTime, "classesToSkipAfterFirstTime");
        ReentrantReadWriteLock reentrantReadWriteLock = this.lock;
        ReentrantReadWriteLock.ReadLock lock = reentrantReadWriteLock.readLock();
        int i = 0;
        int readHoldCount = reentrantReadWriteLock.getWriteHoldCount() == 0 ? reentrantReadWriteLock.getReadHoldCount() : 0;
        for (int i2 = 0; i2 < readHoldCount; i2++) {
            lock.unlock();
        }
        ReentrantReadWriteLock.WriteLock writeLock = reentrantReadWriteLock.writeLock();
        writeLock.lock();
        try {
            HashSet hashSet = new HashSet();
            Iterator<T> it = classesToSkip.iterator();
            while (it.hasNext()) {
                hashSet.add(((KotlinType) it.next()).getTypeName());
            }
            this.classesToSkipNames = hashSet;
            HashSet hashSet2 = new HashSet();
            Iterator<T> it2 = classesToSkipAfterFirstTime.iterator();
            while (it2.hasNext()) {
                hashSet2.add(((KotlinType) it2.next()).getTypeName());
            }
            this.classesToSkipFirstTimeNames = hashSet2;
            Unit unit = Unit.INSTANCE;
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
        } finally {
            while (i < readHoldCount) {
                lock.lock();
                i++;
            }
            writeLock.unlock();
        }
    }

    @Override // org.jetbrains.kotlin.resolve.calls.tower.ImplicitsExtensionsResolutionFilter
    public Sequence<ScopeWithImplicitsExtensionsResolutionInfo> getScopesWithInfo(Sequence<? extends HierarchicalScope> scopes) {
        Intrinsics.checkNotNullParameter(scopes, "scopes");
        final LinkedHashSet linkedHashSet = new LinkedHashSet();
        return SequencesKt.map(scopes, new Function1<HierarchicalScope, ScopeWithImplicitsExtensionsResolutionInfo>() { // from class: org.jetbrains.kotlin.scripting.compiler.plugin.repl.ReplImplicitsExtensionsResolutionFilter.getScopesWithInfo.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            /* JADX WARN: Code duplicated, block: B:37:0x00a4  */
            @Override // kotlin.jvm.functions.Function1
            public final ScopeWithImplicitsExtensionsResolutionInfo invoke(HierarchicalScope scope) {
                List listPlus;
                ArrayList arrayList;
                boolean zAdd;
                ClassDescriptor declarationDescriptor;
                FqName fqNameSafe;
                Intrinsics.checkNotNullParameter(scope, "scope");
                if (scope instanceof LexicalScope) {
                    LexicalScope lexicalScope = (LexicalScope) scope;
                    listPlus = CollectionsKt.plus((Collection) CollectionsKt.listOfNotNull(lexicalScope.getImplicitReceiver()), (Iterable) lexicalScope.getContextReceiversGroup());
                } else {
                    listPlus = null;
                }
                if (listPlus != null) {
                    List list = listPlus;
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(((ReceiverParameterDescriptor) it.next()).getValue());
                    }
                    arrayList = arrayList2;
                } else {
                    arrayList = null;
                }
                boolean z = true;
                if (arrayList != null) {
                    ArrayList<ReceiverValue> arrayList3 = arrayList;
                    ReplImplicitsExtensionsResolutionFilter replImplicitsExtensionsResolutionFilter = ReplImplicitsExtensionsResolutionFilter.this;
                    Set<String> set = linkedHashSet;
                    if (!(arrayList3 instanceof Collection) || !arrayList3.isEmpty()) {
                        for (ReceiverValue receiverValue : arrayList3) {
                            ReentrantReadWriteLock.ReadLock lock = replImplicitsExtensionsResolutionFilter.lock.readLock();
                            lock.lock();
                            try {
                                ImplicitClassReceiver implicitClassReceiver = receiverValue instanceof ImplicitClassReceiver ? (ImplicitClassReceiver) receiverValue : null;
                                String strAsString = (implicitClassReceiver == null || (declarationDescriptor = implicitClassReceiver.getDeclarationDescriptor()) == null || (fqNameSafe = DescriptorUtilsKt.getFqNameSafe(declarationDescriptor)) == null) ? null : fqNameSafe.asString();
                                if (strAsString == null) {
                                    zAdd = true;
                                } else if (replImplicitsExtensionsResolutionFilter.classesToSkipNames.contains(strAsString)) {
                                    zAdd = false;
                                } else if (replImplicitsExtensionsResolutionFilter.classesToSkipFirstTimeNames.contains(strAsString)) {
                                    zAdd = set.add(strAsString);
                                } else {
                                    zAdd = true;
                                }
                                lock.unlock();
                                if (!zAdd) {
                                    z = false;
                                    break;
                                }
                            } catch (Throwable th) {
                                lock.unlock();
                                throw th;
                            }
                        }
                    }
                }
                return new ScopeWithImplicitsExtensionsResolutionInfo(scope, z);
            }
        });
    }
}
