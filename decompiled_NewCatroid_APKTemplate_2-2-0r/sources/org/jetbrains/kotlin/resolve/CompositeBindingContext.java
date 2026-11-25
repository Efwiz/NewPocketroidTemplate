package org.jetbrains.kotlin.resolve;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.arguments.DevModeOverwritingStrategies;
import org.jetbrains.kotlin.codegen.coroutines.CoroutineCodegenUtilKt;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableMap;
import org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker;
import org.jetbrains.kotlin.com.intellij.psi.PsiElement;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: CompositeBindingContext.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000R\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 \u001d2\u00020\u0001:\u0002\u001d\u001eB\u0015\b\u0002\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0016J<\u0010\u000b\u001a\u0004\u0018\u0001H\f\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\f2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\f\u0018\u00010\u000f2\b\u0010\u0010\u001a\u0004\u0018\u0001H\rH\u0096\u0002¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0013H\u0016J0\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\r0\u0015\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\f2\u0014\u0010\u000e\u001a\u0010\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\f\u0018\u00010\u0016H\u0016J4\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\f0\u0018\"\u0004\b\u0000\u0010\r\"\u0004\b\u0001\u0010\f2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\r\u0012\u0004\u0012\u0002H\f0\u000fH\u0016J\u0012\u0010\u0019\u001a\u0004\u0018\u00010\u001a2\u0006\u0010\u001b\u001a\u00020\u001cH\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/resolve/CompositeBindingContext;", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "delegates", "Ljava/util/LinkedHashSet;", "(Ljava/util/LinkedHashSet;)V", "addOwnDataTo", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "commitDiagnostics", "", "get", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "slice", "Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;Ljava/lang/Object;)Ljava/lang/Object;", "getDiagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "getKeys", "", "Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", "getSliceContents", "Lorg/jetbrains/kotlin/com/google/common/collect/ImmutableMap;", "getType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "Companion", "CompositeDiagnostics", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CompositeBindingContext implements BindingContext {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final LinkedHashSet<BindingContext> delegates;

    public /* synthetic */ CompositeBindingContext(LinkedHashSet linkedHashSet, DefaultConstructorMarker defaultConstructorMarker) {
        this(linkedHashSet);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingContext
    public void addOwnDataTo(BindingTrace trace, boolean commitDiagnostics) {
        Intrinsics.checkNotNullParameter(trace, "trace");
    }

    private CompositeBindingContext(LinkedHashSet<BindingContext> linkedHashSet) {
        this.delegates = linkedHashSet;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingContext
    public KotlinType getType(final KtExpression expression) {
        Object next;
        Intrinsics.checkNotNullParameter(expression, "expression");
        Iterator it = SequencesKt.map(CollectionsKt.asSequence(this.delegates), new Function1<BindingContext, KotlinType>() { // from class: org.jetbrains.kotlin.resolve.CompositeBindingContext.getType.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final KotlinType invoke(BindingContext it2) {
                Intrinsics.checkNotNullParameter(it2, "it");
                return it2.getType(expression);
            }
        }).iterator();
        while (it.hasNext()) {
            next = it.next();
            if (((KotlinType) next) != null) {
                return (KotlinType) next;
            }
        }
        next = null;
        return (KotlinType) next;
    }

    /* JADX INFO: compiled from: CompositeBindingContext.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u0014\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00040\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/resolve/CompositeBindingContext$Companion;", "", "()V", CoroutineCodegenUtilKt.SUSPEND_FUNCTION_CREATE_METHOD_NAME, "Lorg/jetbrains/kotlin/resolve/BindingContext;", "delegates", "", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final BindingContext create(List<? extends BindingContext> delegates) {
            Intrinsics.checkNotNullParameter(delegates, "delegates");
            if (!delegates.isEmpty()) {
                LinkedHashSet linkedHashSet = new LinkedHashSet(delegates);
                return linkedHashSet.size() == 1 ? (BindingContext) CollectionsKt.first((List) delegates) : new CompositeBindingContext(linkedHashSet, null);
            }
            BindingContext EMPTY = BindingContext.EMPTY;
            Intrinsics.checkNotNullExpressionValue(EMPTY, "EMPTY");
            return EMPTY;
        }
    }

    @Override // org.jetbrains.kotlin.resolve.BindingContext
    public <K, V> V get(final ReadOnlySlice<K, V> slice, final K key) {
        for (V v : SequencesKt.map(CollectionsKt.asSequence(this.delegates), new Function1<BindingContext, V>() { // from class: org.jetbrains.kotlin.resolve.CompositeBindingContext.get.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final V invoke(BindingContext it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return (V) it.get(slice, key);
            }
        })) {
            if (v != null) {
                return v;
            }
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingContext
    public <K, V> Collection<K> getKeys(WritableSlice<K, V> slice) {
        LinkedHashSet<BindingContext> linkedHashSet = this.delegates;
        ArrayList arrayList = new ArrayList();
        Iterator<T> it = linkedHashSet.iterator();
        while (it.hasNext()) {
            Collection<K> keys = ((BindingContext) it.next()).getKeys(slice);
            Intrinsics.checkNotNullExpressionValue(keys, "getKeys(...)");
            CollectionsKt.addAll(arrayList, keys);
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingContext
    public <K, V> ImmutableMap<K, V> getSliceContents(ReadOnlySlice<K, V> slice) {
        Intrinsics.checkNotNullParameter(slice, "slice");
        HashMap map = new HashMap();
        Iterator<T> it = this.delegates.iterator();
        while (it.hasNext()) {
            map.putAll(((BindingContext) it.next()).getSliceContents(slice));
        }
        ImmutableMap<K, V> immutableMapBuild = ImmutableMap.builder().putAll(map).build();
        Intrinsics.checkNotNullExpressionValue(immutableMapBuild, "build(...)");
        return immutableMapBuild;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingContext
    public Diagnostics getDiagnostics() {
        LinkedHashSet<BindingContext> linkedHashSet = this.delegates;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(linkedHashSet, 10));
        Iterator<T> it = linkedHashSet.iterator();
        while (it.hasNext()) {
            Diagnostics diagnostics = ((BindingContext) it.next()).getDiagnostics();
            Intrinsics.checkNotNullExpressionValue(diagnostics, "getDiagnostics(...)");
            arrayList.add(diagnostics);
        }
        return new CompositeDiagnostics(arrayList);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX INFO: compiled from: CompositeBindingContext.kt */
    @Metadata(d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010(\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0016J\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000b0\n2\u0006\u0010\r\u001a\u00020\u000eH\u0016J\b\u0010\u000f\u001a\u00020\u0010H\u0016J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0012H\u0096\u0002J\b\u0010\u0013\u001a\u00020\u0001H\u0016R\u0014\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\b¨\u0006\u0014"}, d2 = {"Lorg/jetbrains/kotlin/resolve/CompositeBindingContext$CompositeDiagnostics;", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "delegates", "", "(Ljava/util/List;)V", "modificationTracker", "Lorg/jetbrains/kotlin/com/intellij/openapi/util/ModificationTracker;", "getModificationTracker", "()Lcom/intellij/openapi/util/ModificationTracker;", DevModeOverwritingStrategies.ALL, "", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "forElement", "psiElement", "Lorg/jetbrains/kotlin/com/intellij/psi/PsiElement;", "isEmpty", "", "iterator", "", "noSuppression", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    static final class CompositeDiagnostics implements Diagnostics {
        private final List<Diagnostics> delegates;
        private final ModificationTracker modificationTracker;

        /* JADX WARN: Multi-variable type inference failed */
        public CompositeDiagnostics(List<? extends Diagnostics> delegates) {
            Intrinsics.checkNotNullParameter(delegates, "delegates");
            this.delegates = delegates;
            this.modificationTracker = new ModificationTracker() { // from class: org.jetbrains.kotlin.resolve.CompositeBindingContext$CompositeDiagnostics$$ExternalSyntheticLambda0
                @Override // org.jetbrains.kotlin.com.intellij.openapi.util.ModificationTracker
                public final long getModificationCount() {
                    return CompositeBindingContext.CompositeDiagnostics.modificationTracker$lambda$2(this.f$0);
                }
            };
        }

        @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics, java.lang.Iterable
        public Iterator<Diagnostic> iterator() {
            List<Diagnostics> list = this.delegates;
            Sequence sequenceEmptySequence = SequencesKt.emptySequence();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                sequenceEmptySequence = SequencesKt.plus(sequenceEmptySequence, CollectionsKt.asSequence((Diagnostics) it.next()));
            }
            return sequenceEmptySequence.iterator();
        }

        @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
        public ModificationTracker getModificationTracker() {
            return this.modificationTracker;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public static final long modificationTracker$lambda$2(CompositeDiagnostics this$0) {
            Intrinsics.checkNotNullParameter(this$0, "this$0");
            Iterator<T> it = this$0.delegates.iterator();
            long modificationCount = 0;
            while (it.hasNext()) {
                modificationCount += ((Diagnostics) it.next()).getModificationTracker().getModificationCount();
            }
            return modificationCount;
        }

        @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics
        public Collection<Diagnostic> all() {
            List<Diagnostics> list = this.delegates;
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, ((Diagnostics) it.next()).all());
            }
            return arrayList;
        }

        @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
        public Collection<Diagnostic> forElement(PsiElement psiElement) {
            Intrinsics.checkNotNullParameter(psiElement, "psiElement");
            List<Diagnostics> list = this.delegates;
            ArrayList arrayList = new ArrayList();
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                CollectionsKt.addAll(arrayList, ((Diagnostics) it.next()).forElement(psiElement));
            }
            return arrayList;
        }

        @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics, org.jetbrains.kotlin.diagnostics.GenericDiagnostics
        public boolean isEmpty() {
            List<Diagnostics> list = this.delegates;
            if ((list instanceof Collection) && list.isEmpty()) {
                return true;
            }
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                if (!((Diagnostics) it.next()).isEmpty()) {
                    return false;
                }
            }
            return true;
        }

        @Override // org.jetbrains.kotlin.resolve.diagnostics.Diagnostics
        public Diagnostics noSuppression() {
            List<Diagnostics> list = this.delegates;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
            Iterator<T> it = list.iterator();
            while (it.hasNext()) {
                arrayList.add(((Diagnostics) it.next()).noSuppression());
            }
            return new CompositeDiagnostics(arrayList);
        }
    }
}
