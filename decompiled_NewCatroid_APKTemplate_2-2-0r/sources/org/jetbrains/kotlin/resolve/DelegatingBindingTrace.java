package org.jetbrains.kotlin.resolve;

import androidx.exifinterface.media.ExifInterface;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableMap;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.DiagnosticSink;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.jetbrains.kotlin.psi.KtExpression;
import org.jetbrains.kotlin.resolve.diagnostics.BindingContextSuppressCache;
import org.jetbrains.kotlin.resolve.diagnostics.Diagnostics;
import org.jetbrains.kotlin.resolve.diagnostics.KotlinSuppressCache;
import org.jetbrains.kotlin.resolve.diagnostics.MutableDiagnosticsWithSuppression;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.types.expressions.KotlinTypeInfo;
import org.jetbrains.kotlin.types.expressions.typeInfoFactory.TypeInfoFactoryKt;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;
import org.jetbrains.kotlin.util.slicedMap.SetSlice;
import org.jetbrains.kotlin.util.slicedMap.SlicedMapImpl;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: DelegatingBindingTrace.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u0086\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\b\b\u0016\u0018\u00002\u00020\u0001:\u0001GB5\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\b\u0010\u0006\u001a\u0004\u0018\u00010\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0002\u0010\fB?\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\r\u001a\u00020\u0005\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000b\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010¢\u0006\u0002\u0010\u0011J&\u0010\"\u001a\u00020#2\u0006\u0010$\u001a\u00020\u00012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010%2\b\b\u0002\u0010&\u001a\u00020\u000bH\u0007J\b\u0010'\u001a\u00020#H\u0016J8\u0010(\u001a\u0004\u0018\u0001H)\"\u0004\b\u0000\u0010*\"\u0004\b\u0001\u0010)2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u0002H*\u0012\u0004\u0012\u0002H)0,2\u0006\u0010-\u001a\u0002H*H\u0096\u0002¢\u0006\u0002\u0010.J\b\u0010/\u001a\u00020\u0003H\u0016J.\u00100\u001a\b\u0012\u0004\u0012\u0002H*01\"\u0004\b\u0000\u0010*\"\u0004\b\u0001\u0010)2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u0002H*\u0012\u0004\u0012\u0002H)02H\u0016J\u0012\u00103\u001a\u0004\u0018\u0001042\u0006\u00105\u001a\u000206H\u0016J\u000e\u00107\u001a\u00020#2\u0006\u0010$\u001a\u00020\u0001J=\u00108\u001a\u00020#\"\u0004\b\u0000\u0010*\"\u0004\b\u0001\u0010)2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u0002H*\u0012\u0004\u0012\u0002H)022\u0006\u0010-\u001a\u0002H*2\u0006\u00109\u001a\u0002H)H\u0016¢\u0006\u0002\u0010:J/\u00108\u001a\u00020#\"\u0004\b\u0000\u0010*2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u0002H*\u0012\u0004\u0012\u00020\u000b022\u0006\u0010-\u001a\u0002H*H\u0016¢\u0006\u0002\u0010;J\u001a\u0010<\u001a\u00020#2\u0006\u00105\u001a\u0002062\b\u0010=\u001a\u0004\u0018\u000104H\u0016J\u0010\u0010>\u001a\u00020#2\u0006\u0010?\u001a\u00020@H\u0016J\b\u0010A\u001a\u00020#H\u0016J7\u0010B\u001a\u0004\u0018\u0001H)\"\u0004\b\u0000\u0010*\"\u0004\b\u0001\u0010)2\u0012\u0010+\u001a\u000e\u0012\u0004\u0012\u0002H*\u0012\u0004\u0012\u0002H)0,2\u0006\u0010-\u001a\u0002H*H\u0004¢\u0006\u0002\u0010.J\u0010\u0010C\u001a\u00020\u000b2\u0006\u0010D\u001a\u00020\u0015H\u0016J\b\u0010E\u001a\u00020\u0005H\u0016J\b\u0010F\u001a\u00020\u000bH\u0016R\u0012\u0010\u0012\u001a\u00060\u0013R\u00020\u0000X\u0082\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\u0014\u001a\u0004\u0018\u00010\u0015X\u0084\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R\u000e\u0010\b\u001a\u00020\tX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u001a\u001a\u00020\u001bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u001dR\u0016\u0010\u001e\u001a\u0004\u0018\u00010\u001fX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b \u0010!R\u000e\u0010\r\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006H"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DelegatingBindingTrace;", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "parentContext", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "debugName", "", "resolutionSubjectForMessage", "", "filter", "Lorg/jetbrains/kotlin/resolve/BindingTraceFilter;", "allowSliceRewrite", "", "(Lorg/jetbrains/kotlin/resolve/BindingContext;Ljava/lang/String;Ljava/lang/Object;Lorg/jetbrains/kotlin/resolve/BindingTraceFilter;Z)V", "name", "withParentDiagnostics", "customSuppressCache", "Lorg/jetbrains/kotlin/resolve/diagnostics/KotlinSuppressCache;", "(Lorg/jetbrains/kotlin/resolve/BindingContext;Ljava/lang/String;ZLorg/jetbrains/kotlin/resolve/BindingTraceFilter;ZLorg/jetbrains/kotlin/resolve/diagnostics/KotlinSuppressCache;)V", "bindingContext", "Lorg/jetbrains/kotlin/resolve/DelegatingBindingTrace$MyBindingContext;", "diagnosticsCallback", "Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink$DiagnosticsCallback;", "getDiagnosticsCallback", "()Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink$DiagnosticsCallback;", "setDiagnosticsCallback", "(Lorg/jetbrains/kotlin/diagnostics/DiagnosticSink$DiagnosticsCallback;)V", "map", "Lorg/jetbrains/kotlin/util/slicedMap/SlicedMapImpl;", "getMap", "()Lorg/jetbrains/kotlin/util/slicedMap/SlicedMapImpl;", "mutableDiagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/MutableDiagnosticsWithSuppression;", "getMutableDiagnostics", "()Lorg/jetbrains/kotlin/resolve/diagnostics/MutableDiagnosticsWithSuppression;", "addOwnDataTo", "", "trace", "Lorg/jetbrains/kotlin/resolve/TraceEntryFilter;", "commitDiagnostics", LineReader.CLEAR, "get", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "slice", "Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;Ljava/lang/Object;)Ljava/lang/Object;", "getBindingContext", "getKeys", "", "Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", "getType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "moveAllMyDataTo", PsiKeyword.RECORD, "value", "(Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;Ljava/lang/Object;Ljava/lang/Object;)V", "(Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;Ljava/lang/Object;)V", "recordType", ModuleXmlParser.TYPE, "report", "diagnostic", "Lorg/jetbrains/kotlin/diagnostics/Diagnostic;", "resetCallback", "selfGet", "setCallbackIfNotSet", "callback", "toString", "wantsDiagnostics", "MyBindingContext", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class DelegatingBindingTrace implements BindingTrace {
    private final MyBindingContext bindingContext;
    private volatile DiagnosticSink.DiagnosticsCallback diagnosticsCallback;
    private final BindingTraceFilter filter;
    private final SlicedMapImpl map;
    private final MutableDiagnosticsWithSuppression mutableDiagnostics;
    private final String name;
    private final BindingContext parentContext;

    public final void addOwnDataTo(BindingTrace trace) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        addOwnDataTo$default(this, trace, null, false, 6, null);
    }

    public final void addOwnDataTo(BindingTrace trace, TraceEntryFilter traceEntryFilter) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        addOwnDataTo$default(this, trace, traceEntryFilter, false, 4, null);
    }

    public DelegatingBindingTrace(BindingContext parentContext, String name2, boolean z, BindingTraceFilter filter, boolean z2, KotlinSuppressCache kotlinSuppressCache) {
        MutableDiagnosticsWithSuppression mutableDiagnosticsWithSuppression;
        Intrinsics.checkNotNullParameter(parentContext, "parentContext");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(filter, "filter");
        this.parentContext = parentContext;
        this.name = name2;
        this.filter = filter;
        this.map = new SlicedMapImpl(z2);
        MyBindingContext myBindingContext = new MyBindingContext();
        this.bindingContext = myBindingContext;
        if (filter.getIgnoreDiagnostics()) {
            mutableDiagnosticsWithSuppression = null;
        } else {
            kotlinSuppressCache = kotlinSuppressCache == null ? new BindingContextSuppressCache(myBindingContext) : kotlinSuppressCache;
            Diagnostics diagnostics = z ? parentContext.getDiagnostics() : Diagnostics.INSTANCE.getEMPTY();
            Intrinsics.checkNotNull(diagnostics);
            mutableDiagnosticsWithSuppression = new MutableDiagnosticsWithSuppression(kotlinSuppressCache, diagnostics);
        }
        this.mutableDiagnostics = mutableDiagnosticsWithSuppression;
    }

    public /* synthetic */ DelegatingBindingTrace(BindingContext bindingContext, String str, boolean z, BindingTraceFilter bindingTraceFilter, boolean z2, KotlinSuppressCache kotlinSuppressCache, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bindingContext, str, (i & 4) != 0 ? true : z, (i & 8) != 0 ? BindingTraceFilter.INSTANCE.getACCEPT_ALL() : bindingTraceFilter, (i & 16) != 0 ? false : z2, (i & 32) != 0 ? null : kotlinSuppressCache);
    }

    protected final SlicedMapImpl getMap() {
        return this.map;
    }

    /* JADX INFO: compiled from: DelegatingBindingTrace.kt */
    @Metadata(d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0082\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J8\u0010\t\u001a\u0004\u0018\u0001H\n\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\n0\r2\u0006\u0010\u000e\u001a\u0002H\u000bH\u0096\u0002¢\u0006\u0002\u0010\u000fJ\b\u0010\u0010\u001a\u00020\u0011H\u0016J.\u0010\u0012\u001a\b\u0012\u0004\u0012\u0002H\u000b0\u0013\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\n0\u0014H\u0016J4\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\n0\u0016\"\u0004\b\u0000\u0010\u000b\"\u0004\b\u0001\u0010\n2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u0002H\u000b\u0012\u0004\u0012\u0002H\n0\rH\u0016J\u0012\u0010\u0017\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016¨\u0006\u001b"}, d2 = {"Lorg/jetbrains/kotlin/resolve/DelegatingBindingTrace$MyBindingContext;", "Lorg/jetbrains/kotlin/resolve/BindingContext;", "(Lorg/jetbrains/kotlin/resolve/DelegatingBindingTrace;)V", "addOwnDataTo", "", "trace", "Lorg/jetbrains/kotlin/resolve/BindingTrace;", "commitDiagnostics", "", "get", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "slice", "Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;Ljava/lang/Object;)Ljava/lang/Object;", "getDiagnostics", "Lorg/jetbrains/kotlin/resolve/diagnostics/Diagnostics;", "getKeys", "", "Lorg/jetbrains/kotlin/util/slicedMap/WritableSlice;", "getSliceContents", "Lorg/jetbrains/kotlin/com/google/common/collect/ImmutableMap;", "getType", "Lorg/jetbrains/kotlin/types/KotlinType;", "expression", "Lorg/jetbrains/kotlin/psi/KtExpression;", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private final class MyBindingContext implements BindingContext {
        public MyBindingContext() {
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public Diagnostics getDiagnostics() {
            MutableDiagnosticsWithSuppression mutableDiagnostics = DelegatingBindingTrace.this.getMutableDiagnostics();
            return mutableDiagnostics != null ? mutableDiagnostics : Diagnostics.INSTANCE.getEMPTY();
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public <K, V> V get(ReadOnlySlice<K, V> slice, K key) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            return (V) DelegatingBindingTrace.this.get(slice, key);
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public KotlinType getType(KtExpression expression) {
            Intrinsics.checkNotNullParameter(expression, "expression");
            return DelegatingBindingTrace.this.getType(expression);
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public <K, V> Collection<K> getKeys(WritableSlice<K, V> slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            return DelegatingBindingTrace.this.getKeys(slice);
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public void addOwnDataTo(BindingTrace trace, boolean commitDiagnostics) {
            Intrinsics.checkNotNullParameter(trace, "trace");
            BindingContextUtils.addOwnDataTo(trace, null, commitDiagnostics, DelegatingBindingTrace.this.getMap(), DelegatingBindingTrace.this.getMutableDiagnostics());
        }

        @Override // org.jetbrains.kotlin.resolve.BindingContext
        public <K, V> ImmutableMap<K, V> getSliceContents(ReadOnlySlice<K, V> slice) {
            Intrinsics.checkNotNullParameter(slice, "slice");
            ImmutableMap<K, V> sliceContents = DelegatingBindingTrace.this.parentContext.getSliceContents(slice);
            Intrinsics.checkNotNullExpressionValue(sliceContents, "getSliceContents(...)");
            ImmutableMap<K, V> sliceContents2 = DelegatingBindingTrace.this.getMap().getSliceContents(slice);
            Intrinsics.checkNotNullExpressionValue(sliceContents2, "getSliceContents(...)");
            ImmutableMap<K, V> immutableMapCopyOf = ImmutableMap.copyOf(MapsKt.plus(sliceContents, sliceContents2));
            Intrinsics.checkNotNullExpressionValue(immutableMapCopyOf, "copyOf(...)");
            return immutableMapCopyOf;
        }
    }

    protected final MutableDiagnosticsWithSuppression getMutableDiagnostics() {
        return this.mutableDiagnostics;
    }

    public /* synthetic */ DelegatingBindingTrace(BindingContext bindingContext, String str, Object obj, BindingTraceFilter bindingTraceFilter, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(bindingContext, str, obj, (i & 8) != 0 ? BindingTraceFilter.INSTANCE.getACCEPT_ALL() : bindingTraceFilter, (i & 16) != 0 ? false : z);
    }

    /* JADX WARN: 'this' call moved to the top of the method (can break code semantics) */
    public DelegatingBindingTrace(BindingContext parentContext, String debugName, Object obj, BindingTraceFilter filter, boolean z) {
        this(parentContext, AnalyzingUtils.formDebugNameForBindingTrace(debugName, obj), false, filter, z, null, 36, null);
        Intrinsics.checkNotNullParameter(parentContext, "parentContext");
        Intrinsics.checkNotNullParameter(debugName, "debugName");
        Intrinsics.checkNotNullParameter(filter, "filter");
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public BindingContext getBindingContext() {
        return this.bindingContext;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> void record(WritableSlice<K, V> slice, K key, V value) {
        Intrinsics.checkNotNullParameter(slice, "slice");
        this.map.put(slice, key, value);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K> void record(WritableSlice<K, Boolean> slice, K key) {
        Intrinsics.checkNotNullParameter(slice, "slice");
        record(slice, key, true);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> V get(ReadOnlySlice<K, V> slice, K key) {
        Intrinsics.checkNotNullParameter(slice, "slice");
        V v = (V) selfGet(slice, key);
        return v == null ? (V) this.parentContext.get(slice, key) : v;
    }

    protected final <K, V> V selfGet(ReadOnlySlice<K, V> slice, K key) {
        Intrinsics.checkNotNullParameter(slice, "slice");
        V v = (V) this.map.get(slice, key);
        if ((slice instanceof SetSlice) && Intrinsics.areEqual(v, Boolean.valueOf(SetSlice.DEFAULT))) {
            return null;
        }
        return v;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> Collection<K> getKeys(WritableSlice<K, V> slice) {
        Intrinsics.checkNotNullParameter(slice, "slice");
        Collection<K> keys = this.map.getKeys(slice);
        Collection<K> keys2 = this.parentContext.getKeys(slice);
        Intrinsics.checkNotNullExpressionValue(keys2, "getKeys(...)");
        if (keys.isEmpty()) {
            return keys2;
        }
        if (keys2.isEmpty()) {
            Intrinsics.checkNotNull(keys);
            return keys;
        }
        Intrinsics.checkNotNull(keys);
        return CollectionsKt.plus((Collection) keys, (Iterable) keys2);
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public KotlinType getType(KtExpression expression) {
        Intrinsics.checkNotNullParameter(expression, "expression");
        WritableSlice<KtExpression, KotlinTypeInfo> EXPRESSION_TYPE_INFO = BindingContext.EXPRESSION_TYPE_INFO;
        Intrinsics.checkNotNullExpressionValue(EXPRESSION_TYPE_INFO, "EXPRESSION_TYPE_INFO");
        KotlinTypeInfo kotlinTypeInfo = (KotlinTypeInfo) get(EXPRESSION_TYPE_INFO, expression);
        if (kotlinTypeInfo != null) {
            return kotlinTypeInfo.getType();
        }
        return null;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTrace
    public void recordType(KtExpression expression, KotlinType type) {
        KotlinTypeInfo kotlinTypeInfoReplaceType;
        Intrinsics.checkNotNullParameter(expression, "expression");
        WritableSlice<KtExpression, KotlinTypeInfo> EXPRESSION_TYPE_INFO = BindingContext.EXPRESSION_TYPE_INFO;
        Intrinsics.checkNotNullExpressionValue(EXPRESSION_TYPE_INFO, "EXPRESSION_TYPE_INFO");
        KotlinTypeInfo kotlinTypeInfo = (KotlinTypeInfo) get(EXPRESSION_TYPE_INFO, expression);
        if (kotlinTypeInfo == null) {
            kotlinTypeInfoReplaceType = TypeInfoFactoryKt.createTypeInfo(type);
        } else {
            kotlinTypeInfoReplaceType = kotlinTypeInfo.replaceType(type);
        }
        WritableSlice<KtExpression, KotlinTypeInfo> EXPRESSION_TYPE_INFO2 = BindingContext.EXPRESSION_TYPE_INFO;
        Intrinsics.checkNotNullExpressionValue(EXPRESSION_TYPE_INFO2, "EXPRESSION_TYPE_INFO");
        record(EXPRESSION_TYPE_INFO2, expression, kotlinTypeInfoReplaceType);
    }

    public final void moveAllMyDataTo(BindingTrace trace) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        addOwnDataTo(trace, null, true);
        clear();
    }

    public static /* synthetic */ void addOwnDataTo$default(DelegatingBindingTrace delegatingBindingTrace, BindingTrace bindingTrace, TraceEntryFilter traceEntryFilter, boolean z, int i, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: addOwnDataTo");
        }
        if ((i & 2) != 0) {
            traceEntryFilter = null;
        }
        if ((i & 4) != 0) {
            z = true;
        }
        delegatingBindingTrace.addOwnDataTo(bindingTrace, traceEntryFilter, z);
    }

    public final void addOwnDataTo(BindingTrace trace, TraceEntryFilter filter, boolean commitDiagnostics) {
        Intrinsics.checkNotNullParameter(trace, "trace");
        BindingContextUtils.addOwnDataTo(trace, filter, commitDiagnostics, this.map, this.mutableDiagnostics);
    }

    public void clear() {
        this.map.clear();
        MutableDiagnosticsWithSuppression mutableDiagnosticsWithSuppression = this.mutableDiagnostics;
        if (mutableDiagnosticsWithSuppression != null) {
            mutableDiagnosticsWithSuppression.clear();
        }
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
    public void report(Diagnostic diagnostic) {
        Intrinsics.checkNotNullParameter(diagnostic, "diagnostic");
        MutableDiagnosticsWithSuppression mutableDiagnosticsWithSuppression = this.mutableDiagnostics;
        if (mutableDiagnosticsWithSuppression == null) {
            return;
        }
        mutableDiagnosticsWithSuppression.report(diagnostic);
    }

    protected final DiagnosticSink.DiagnosticsCallback getDiagnosticsCallback() {
        return this.diagnosticsCallback;
    }

    protected final void setDiagnosticsCallback(DiagnosticSink.DiagnosticsCallback diagnosticsCallback) {
        this.diagnosticsCallback = diagnosticsCallback;
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
    public boolean setCallbackIfNotSet(DiagnosticSink.DiagnosticsCallback callback) {
        Intrinsics.checkNotNullParameter(callback, "callback");
        MutableDiagnosticsWithSuppression mutableDiagnosticsWithSuppression = this.mutableDiagnostics;
        boolean callbackIfNotSet = mutableDiagnosticsWithSuppression != null ? mutableDiagnosticsWithSuppression.setCallbackIfNotSet(callback) : false;
        if (callbackIfNotSet) {
            this.diagnosticsCallback = callback;
        }
        return callbackIfNotSet;
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
    public void resetCallback() {
        this.diagnosticsCallback = null;
        MutableDiagnosticsWithSuppression mutableDiagnosticsWithSuppression = this.mutableDiagnostics;
        if (mutableDiagnosticsWithSuppression != null) {
            mutableDiagnosticsWithSuppression.resetCallback();
        }
    }

    @Override // org.jetbrains.kotlin.diagnostics.DiagnosticSink
    public boolean wantsDiagnostics() {
        return this.mutableDiagnostics != null;
    }

    /* JADX INFO: renamed from: toString, reason: from getter */
    public String getName() {
        return this.name;
    }
}
