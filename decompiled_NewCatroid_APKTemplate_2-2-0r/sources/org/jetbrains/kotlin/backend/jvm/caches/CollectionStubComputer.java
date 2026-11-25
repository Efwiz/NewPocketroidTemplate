package org.jetbrains.kotlin.backend.jvm.caches;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.jvm.JvmBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmSymbols;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.symbols.IrClassSymbol;
import org.jetbrains.kotlin.ir.symbols.IrSimpleFunctionSymbol;
import org.jetbrains.kotlin.ir.types.IrTypeUtilsKt;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: CollectionStubComputer.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\b\u0005\u0018\u00002\u00020\u0001:\u0001\u0015B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\t0\u00112\u0006\u0010\u0013\u001a\u00020\u0010H\u0002J\u0014\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\t0\u00112\u0006\u0010\u0013\u001a\u00020\u0010R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R!\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b8BX\u0082\u0084\u0002¢\u0006\f\n\u0004\b\f\u0010\r\u001a\u0004\b\n\u0010\u000bR \u0010\u000e\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\t0\u00110\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/caches/CollectionStubComputer;", "", "context", "Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "(Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/jvm/JvmBackendContext;", "preComputedStubs", "", "Lorg/jetbrains/kotlin/backend/jvm/caches/StubsForCollectionClass;", "getPreComputedStubs", "()Ljava/util/Collection;", "preComputedStubs$delegate", "Lkotlin/Lazy;", "stubsCache", "Ljava/util/concurrent/ConcurrentHashMap;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "", "computeStubsForCollectionClasses", "irClass", "stubsForCollectionClasses", "LazyStubsForCollectionClass", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CollectionStubComputer {
    private final JvmBackendContext context;

    /* JADX INFO: renamed from: preComputedStubs$delegate, reason: from kotlin metadata */
    private final Lazy preComputedStubs;
    private final ConcurrentHashMap<IrClass, List<StubsForCollectionClass>> stubsCache;

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: CollectionStubComputer.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005R!\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00078VX\u0096\u0084\u0002¢\u0006\f\n\u0004\b\u000b\u0010\f\u001a\u0004\b\t\u0010\nR\u0014\u0010\u0004\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0014\u0010\u0002\u001a\u00020\u0003X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000e¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/caches/CollectionStubComputer$LazyStubsForCollectionClass;", "Lorg/jetbrains/kotlin/backend/jvm/caches/StubsForCollectionClass;", "readOnlyClass", "Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "mutableClass", "(Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;)V", "candidatesForStubs", "", "Lorg/jetbrains/kotlin/ir/declarations/IrSimpleFunction;", "getCandidatesForStubs", "()Ljava/util/Collection;", "candidatesForStubs$delegate", "Lkotlin/Lazy;", "getMutableClass", "()Lorg/jetbrains/kotlin/ir/symbols/IrClassSymbol;", "getReadOnlyClass", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class LazyStubsForCollectionClass implements StubsForCollectionClass {

        /* JADX INFO: renamed from: candidatesForStubs$delegate, reason: from kotlin metadata */
        private final Lazy candidatesForStubs;
        private final IrClassSymbol mutableClass;
        private final IrClassSymbol readOnlyClass;

        public LazyStubsForCollectionClass(IrClassSymbol readOnlyClass, IrClassSymbol mutableClass) {
            Intrinsics.checkNotNullParameter(readOnlyClass, "readOnlyClass");
            Intrinsics.checkNotNullParameter(mutableClass, "mutableClass");
            this.readOnlyClass = readOnlyClass;
            this.mutableClass = mutableClass;
            this.candidatesForStubs = LazyKt.lazy(new Function0<List<? extends IrSimpleFunction>>() { // from class: org.jetbrains.kotlin.backend.jvm.caches.CollectionStubComputer$LazyStubsForCollectionClass$candidatesForStubs$2
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends IrSimpleFunction> invoke() {
                    Sequence<IrSimpleFunction> functions = IrUtilsKt.getFunctions(this.this$0.getMutableClass().getOwner());
                    final CollectionStubComputer.LazyStubsForCollectionClass lazyStubsForCollectionClass = this.this$0;
                    return SequencesKt.toList(SequencesKt.filter(functions, new Function1<IrSimpleFunction, Boolean>() { // from class: org.jetbrains.kotlin.backend.jvm.caches.CollectionStubComputer$LazyStubsForCollectionClass$candidatesForStubs$2.1
                        {
                            super(1);
                        }

                        @Override // kotlin.jvm.functions.Function1
                        public final Boolean invoke(IrSimpleFunction memberFun) {
                            boolean z;
                            Intrinsics.checkNotNullParameter(memberFun, "memberFun");
                            if (memberFun.getIsFakeOverride()) {
                                if (memberFun.getModality() == Modality.ABSTRACT) {
                                    List<IrSimpleFunctionSymbol> overriddenSymbols = memberFun.getOverriddenSymbols();
                                    CollectionStubComputer.LazyStubsForCollectionClass lazyStubsForCollectionClass2 = lazyStubsForCollectionClass;
                                    if (!(overriddenSymbols instanceof Collection) || !overriddenSymbols.isEmpty()) {
                                        Iterator<T> it = overriddenSymbols.iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                if (Intrinsics.areEqual(IrUtilsKt.getParentAsClass(((IrSimpleFunctionSymbol) it.next()).getOwner()).getSymbol(), lazyStubsForCollectionClass2.getReadOnlyClass())) {
                                                }
                                            }
                                        }
                                    }
                                    z = true;
                                }
                                z = false;
                            } else {
                                z = true;
                            }
                            return Boolean.valueOf(z);
                        }
                    }));
                }
            });
        }

        @Override // org.jetbrains.kotlin.backend.jvm.caches.StubsForCollectionClass
        public IrClassSymbol getReadOnlyClass() {
            return this.readOnlyClass;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.caches.StubsForCollectionClass
        public IrClassSymbol getMutableClass() {
            return this.mutableClass;
        }

        @Override // org.jetbrains.kotlin.backend.jvm.caches.StubsForCollectionClass
        public Collection<IrSimpleFunction> getCandidatesForStubs() {
            return (Collection) this.candidatesForStubs.getValue();
        }
    }

    public CollectionStubComputer(JvmBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.preComputedStubs = LazyKt.lazy(new Function0<List<? extends LazyStubsForCollectionClass>>() { // from class: org.jetbrains.kotlin.backend.jvm.caches.CollectionStubComputer$preComputedStubs$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends CollectionStubComputer.LazyStubsForCollectionClass> invoke() {
                JvmSymbols symbols = this.this$0.getContext().getIr().getSymbols();
                return CollectionsKt.listOf((Object[]) new CollectionStubComputer.LazyStubsForCollectionClass[]{new CollectionStubComputer.LazyStubsForCollectionClass(symbols.getCollection(), symbols.getMutableCollection()), new CollectionStubComputer.LazyStubsForCollectionClass(symbols.getSet(), symbols.getMutableSet()), new CollectionStubComputer.LazyStubsForCollectionClass(symbols.getList(), symbols.getMutableList()), new CollectionStubComputer.LazyStubsForCollectionClass(symbols.getMap(), symbols.getMutableMap()), new CollectionStubComputer.LazyStubsForCollectionClass(symbols.getMapEntry(), symbols.getMutableMapEntry()), new CollectionStubComputer.LazyStubsForCollectionClass(symbols.getIterable(), symbols.getMutableIterable()), new CollectionStubComputer.LazyStubsForCollectionClass(symbols.getIterator(), symbols.getMutableIterator()), new CollectionStubComputer.LazyStubsForCollectionClass(symbols.getListIterator(), symbols.getMutableListIterator())});
            }
        });
        this.stubsCache = new ConcurrentHashMap<>();
    }

    public final JvmBackendContext getContext() {
        return this.context;
    }

    private final Collection<StubsForCollectionClass> getPreComputedStubs() {
        return (Collection) this.preComputedStubs.getValue();
    }

    public final List<StubsForCollectionClass> stubsForCollectionClasses(IrClass irClass) {
        List<StubsForCollectionClass> listPutIfAbsent;
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        ConcurrentHashMap<IrClass, List<StubsForCollectionClass>> concurrentHashMap = this.stubsCache;
        List<StubsForCollectionClass> listComputeStubsForCollectionClasses = concurrentHashMap.get(irClass);
        if (listComputeStubsForCollectionClasses == null && (listPutIfAbsent = concurrentHashMap.putIfAbsent(irClass, (listComputeStubsForCollectionClasses = computeStubsForCollectionClasses(irClass)))) != null) {
            listComputeStubsForCollectionClasses = listPutIfAbsent;
        }
        Intrinsics.checkNotNullExpressionValue(listComputeStubsForCollectionClasses, "getOrPut(...)");
        return listComputeStubsForCollectionClasses;
    }

    private final List<StubsForCollectionClass> computeStubsForCollectionClasses(IrClass irClass) {
        if (IrUtilsKt.isFromJava(irClass)) {
            return CollectionsKt.emptyList();
        }
        Collection<StubsForCollectionClass> preComputedStubs = getPreComputedStubs();
        ArrayList arrayList = new ArrayList();
        for (Object obj : preComputedStubs) {
            StubsForCollectionClass stubsForCollectionClass = (StubsForCollectionClass) obj;
            if (IrTypeUtilsKt.isStrictSubtypeOfClass(irClass.getSymbol(), stubsForCollectionClass.getReadOnlyClass()) && !IrTypeUtilsKt.isSubtypeOfClass(irClass.getSymbol(), stubsForCollectionClass.getMutableClass())) {
                arrayList.add(obj);
            }
        }
        ArrayList arrayList2 = arrayList;
        ArrayList arrayList3 = new ArrayList();
        for (Object obj2 : arrayList2) {
            StubsForCollectionClass stubsForCollectionClass2 = (StubsForCollectionClass) obj2;
            if (!(arrayList2 instanceof Collection) || !arrayList2.isEmpty()) {
                Iterator it = arrayList2.iterator();
                while (true) {
                    if (it.hasNext()) {
                        StubsForCollectionClass stubsForCollectionClass3 = (StubsForCollectionClass) it.next();
                        if (!Intrinsics.areEqual(stubsForCollectionClass2.getReadOnlyClass(), stubsForCollectionClass3.getReadOnlyClass()) && IrTypeUtilsKt.isSubtypeOfClass(stubsForCollectionClass3.getReadOnlyClass(), stubsForCollectionClass2.getReadOnlyClass())) {
                            break;
                        }
                    }
                }
            }
            arrayList3.add(obj2);
        }
        return arrayList3;
    }
}
