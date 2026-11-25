package org.jetbrains.kotlin.util.slicedMap;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import kotlin.jvm.functions.Function3;
import net.bytebuddy.implementation.MethodDelegation;
import org.jetbrains.kotlin.utils.Printer;

/* JADX INFO: loaded from: classes6.dex */
public class TrackingSlicedMap extends SlicedMapImpl {
    private final Map<ReadOnlySlice<?, ?>, SliceWithStackTrace<?, ?>> sliceTranslationMap;
    private final boolean trackWithStackTraces;

    private static /* synthetic */ void $$$reportNull$$$0(int i) {
        throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", "f", "org/jetbrains/kotlin/util/slicedMap/TrackingSlicedMap", "forEach"));
    }

    public TrackingSlicedMap(boolean z) {
        super(false);
        this.sliceTranslationMap = new HashMap();
        this.trackWithStackTraces = z;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public <K, V> SliceWithStackTrace<K, V> wrapSlice(final ReadOnlySlice<K, V> readOnlySlice) {
        return (SliceWithStackTrace) this.sliceTranslationMap.computeIfAbsent(readOnlySlice, new Function() { // from class: org.jetbrains.kotlin.util.slicedMap.TrackingSlicedMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m7485x1c3b8404(readOnlySlice, (ReadOnlySlice) obj);
            }
        });
    }

    /* JADX INFO: renamed from: lambda$wrapSlice$0$org-jetbrains-kotlin-util-slicedMap-TrackingSlicedMap, reason: not valid java name */
    /* synthetic */ SliceWithStackTrace m7485x1c3b8404(ReadOnlySlice readOnlySlice, ReadOnlySlice readOnlySlice2) {
        return new SliceWithStackTrace(readOnlySlice);
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.SlicedMapImpl, org.jetbrains.kotlin.util.slicedMap.SlicedMap
    public <K, V> V get(ReadOnlySlice<K, V> readOnlySlice, K k) {
        return (V) ((TrackableValue) super.get(wrapSlice(readOnlySlice), k)).value;
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.SlicedMapImpl, org.jetbrains.kotlin.util.slicedMap.SlicedMap
    public <K, V> Collection<K> getKeys(WritableSlice<K, V> writableSlice) {
        return super.getKeys(wrapSlice(writableSlice));
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.SlicedMapImpl, org.jetbrains.kotlin.util.slicedMap.SlicedMap
    public void forEach(final Function3<WritableSlice, Object, Object, Void> function3) {
        if (function3 == null) {
            $$$reportNull$$$0(0);
        }
        super.forEach(new Function3() { // from class: org.jetbrains.kotlin.util.slicedMap.TrackingSlicedMap$$ExternalSyntheticLambda1
            @Override // kotlin.jvm.functions.Function3
            public final Object invoke(Object obj, Object obj2, Object obj3) {
                return TrackingSlicedMap.lambda$forEach$1(function3, (WritableSlice) obj, obj2, obj3);
            }
        });
    }

    static /* synthetic */ Void lambda$forEach$1(Function3 function3, WritableSlice writableSlice, Object obj, Object obj2) {
        function3.invoke(((SliceWithStackTrace) writableSlice).getWritableDelegate(), obj, ((TrackableValue) obj2).value);
        return null;
    }

    @Override // org.jetbrains.kotlin.util.slicedMap.SlicedMapImpl, org.jetbrains.kotlin.util.slicedMap.MutableSlicedMap
    public <K, V> void put(WritableSlice<K, V> writableSlice, K k, V v) {
        super.put(wrapSlice(writableSlice), k, new TrackableValue(v, this.trackWithStackTraces));
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class TrackableValue<V> {
        private static final StackTraceElement[] EMPTY_STACK_TRACE = new StackTraceElement[0];
        private final StackTraceElement[] stackTrace;
        private final String threadName;
        private final V value;

        private TrackableValue(V v, boolean z) {
            this.value = v;
            this.stackTrace = z ? Thread.currentThread().getStackTrace() : EMPTY_STACK_TRACE;
            this.threadName = Thread.currentThread().getName();
        }

        private Appendable printStackTrace(Appendable appendable) {
            Printer printer = new Printer(appendable);
            printer.println(this.value);
            printer.println("Thread: " + this.threadName);
            printer.println("Written at ");
            for (StackTraceElement stackTraceElement : this.stackTrace) {
                printer.println("\tat " + stackTraceElement);
            }
            printer.println("---------");
            return appendable;
        }

        public String toString() {
            return printStackTrace(new StringBuilder()).toString();
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            V v = this.value;
            V v2 = ((TrackableValue) obj).value;
            return v == null ? v2 == null : v.equals(v2);
        }

        public int hashCode() {
            V v = this.value;
            if (v != null) {
                return v.hashCode();
            }
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    class SliceWithStackTrace<K, V> extends AbstractWritableSlice<K, TrackableValue<V>> implements WritableSlice<K, TrackableValue<V>> {
        private final ReadOnlySlice<K, V> delegate;
        final /* synthetic */ TrackingSlicedMap this$0;

        private static /* synthetic */ void $$$reportNull$$$0(int i) {
            throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", MethodDelegation.ImplementationDelegate.FIELD_NAME_PREFIX, "org/jetbrains/kotlin/util/slicedMap/TrackingSlicedMap$SliceWithStackTrace", "<init>"));
        }

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        private SliceWithStackTrace(TrackingSlicedMap trackingSlicedMap, ReadOnlySlice<K, V> readOnlySlice) {
            super(readOnlySlice.toString());
            if (readOnlySlice == null) {
                $$$reportNull$$$0(0);
            }
            this.this$0 = trackingSlicedMap;
            this.delegate = readOnlySlice;
        }

        /* JADX WARN: Type inference fix 'apply assigned field type' failed
        java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$PrimitiveArg
        	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
        	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
        	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
         */
        @Override // org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
        public TrackableValue<V> computeValue(SlicedMap slicedMap, K k, TrackableValue<V> trackableValue, boolean z) {
            return new TrackableValue<>(this.delegate.computeValue(slicedMap, k, (V) (trackableValue == null ? null : ((TrackableValue) trackableValue).value), z), this.this$0.trackWithStackTraces);
        }

        @Override // org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice
        public ReadOnlySlice<K, TrackableValue<V>> makeRawValueVersion() {
            return this.this$0.wrapSlice(this.delegate.makeRawValueVersion());
        }

        /* JADX INFO: Access modifiers changed from: private */
        public WritableSlice<K, V> getWritableDelegate() {
            return (WritableSlice) this.delegate;
        }

        @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice
        public boolean isCollective() {
            return getWritableDelegate().isCollective();
        }

        @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice
        public RewritePolicy getRewritePolicy() {
            return getWritableDelegate().getRewritePolicy();
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice
        public void afterPut(MutableSlicedMap mutableSlicedMap, K k, TrackableValue<V> trackableValue) {
            getWritableDelegate().afterPut(mutableSlicedMap, k, ((TrackableValue) trackableValue).value);
        }

        /* JADX WARN: Multi-variable type inference failed */
        @Override // org.jetbrains.kotlin.util.slicedMap.WritableSlice
        public boolean check(K k, TrackableValue<V> trackableValue) {
            return getWritableDelegate().check(k, ((TrackableValue) trackableValue).value);
        }
    }
}
