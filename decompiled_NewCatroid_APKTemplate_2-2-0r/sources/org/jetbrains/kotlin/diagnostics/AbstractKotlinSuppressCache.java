package org.jetbrains.kotlin.diagnostics;

import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import net.bytebuddy.implementation.auxiliary.TypeProxy;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.codegen.inline.ReifiedTypeInliner;
import org.jetbrains.kotlin.codegen.optimization.CapturedVarsOptimizationMethodTransformerKt;
import org.jetbrains.kotlin.com.google.common.collect.ImmutableSet;
import org.jetbrains.kotlin.com.intellij.util.containers.ContainerUtil;
import org.jetbrains.kotlin.fir.analysis.collectors.AbstractDiagnosticCollector;
import org.jetbrains.kotlin.metadata.jvm.deserialization.JvmProtoBufUtil;

/* JADX INFO: compiled from: AbstractKotlinSuppressCache.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\t\b&\u0018\u0000 #*\u0004\b\u0000\u0010\u00012\u00020\u0002:\u0007#$%&'()B\u0005¢\u0006\u0002\u0010\u0003J'\u0010\u000b\u001a\u0004\u0018\u00018\u00002\u0006\u0010\f\u001a\u00028\u00002\u0006\u0010\r\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u000fH$¢\u0006\u0002\u0010\u0010J\u001b\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00000\u00072\u0006\u0010\u0012\u001a\u00028\u0000H\u0004¢\u0006\u0002\u0010\u0013J\u001b\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00160\u00152\u0006\u0010\u0012\u001a\u00028\u0000H$¢\u0006\u0002\u0010\u0017J+\u0010\u0018\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00028\u00002\u0006\u0010\r\u001a\u00028\u00002\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001b¢\u0006\u0002\u0010\u001cJ\u0016\u0010\u0018\u001a\u00020\u000f2\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00028\u00000\u001eH\u0014J5\u0010\u001f\u001a\u00020\u000f2\u0006\u0010\u0019\u001a\u00020\u00162\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u0012\u001a\u00028\u00002\u0006\u0010\r\u001a\u00028\u00002\u0006\u0010 \u001a\u00020!H\u0014¢\u0006\u0002\u0010\"RA\u0010\u0004\u001a/\u0012\f\u0012\n \u0006*\u0004\u0018\u00018\u00008\u0000\u0012\u0018\u0012\u0016\u0012\u0004\u0012\u00028\u0000 \u0006*\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u00070\u00070\u0005¢\u0006\u0002\b\bX\u0084\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\n¨\u0006*"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache;", "Element", "", "()V", "suppressors", "Ljava/util/concurrent/ConcurrentMap;", JvmProtoBufUtil.PLATFORM_TYPE_ID, "Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$Suppressor;", "Lorg/jetbrains/annotations/NotNull;", "getSuppressors", "()Ljava/util/concurrent/ConcurrentMap;", "getClosestAnnotatedAncestorElement", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "rootElement", "excludeSelf", "", "(Ljava/lang/Object;Ljava/lang/Object;Z)Ljava/lang/Object;", "getOrCreateSuppressor", "annotated", "(Ljava/lang/Object;)Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$Suppressor;", "getSuppressingStrings", "", "", "(Ljava/lang/Object;)Ljava/util/Set;", "isSuppressed", "suppressionKey", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/Severity;)Z", "request", "Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$SuppressRequest;", "isSuppressedByAnnotated", "debugDepth", "", "(Ljava/lang/String;Lorg/jetbrains/kotlin/diagnostics/Severity;Ljava/lang/Object;Ljava/lang/Object;I)Z", "Companion", "EmptySuppressor", "MultiSuppressor", "SingularSuppressor", "StringSuppressRequest", "SuppressRequest", "Suppressor", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
public abstract class AbstractKotlinSuppressCache<Element> {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private final ConcurrentMap<Element, Suppressor<Element>> suppressors;

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX INFO: compiled from: AbstractKotlinSuppressCache.kt */
    @Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\bd\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002R\u0012\u0010\u0003\u001a\u00028\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00028\u0001X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u0012\u0010\b\u001a\u00020\tX¦\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000bR\u0012\u0010\f\u001a\u00020\rX¦\u0004¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0010À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$SuppressRequest;", "Element", "", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "getElement", "()Ljava/lang/Object;", "rootElement", "getRootElement", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "suppressKey", "", "getSuppressKey", "()Ljava/lang/String;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public interface SuppressRequest<Element> {
        Element getElement();

        Element getRootElement();

        Severity getSeverity();

        String getSuppressKey();
    }

    protected abstract Element getClosestAnnotatedAncestorElement(Element element, Element rootElement, boolean excludeSelf);

    protected abstract Set<String> getSuppressingStrings(Element annotated);

    public AbstractKotlinSuppressCache() {
        ConcurrentMap<Element, Suppressor<Element>> concurrentMapCreateConcurrentWeakValueMap = ContainerUtil.createConcurrentWeakValueMap();
        Intrinsics.checkNotNullExpressionValue(concurrentMapCreateConcurrentWeakValueMap, "createConcurrentWeakValueMap(...)");
        this.suppressors = concurrentMapCreateConcurrentWeakValueMap;
    }

    protected final ConcurrentMap<Element, Suppressor<Element>> getSuppressors() {
        return this.suppressors;
    }

    public final boolean isSuppressed(Element element, Element rootElement, String suppressionKey, Severity severity) {
        Intrinsics.checkNotNullParameter(suppressionKey, "suppressionKey");
        Intrinsics.checkNotNullParameter(severity, "severity");
        String lowerCase = suppressionKey.toLowerCase(Locale.ROOT);
        Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
        return isSuppressed(new StringSuppressRequest(element, rootElement, severity, lowerCase));
    }

    protected boolean isSuppressed(SuppressRequest<Element> request) {
        Intrinsics.checkNotNullParameter(request, "request");
        Element closestAnnotatedAncestorElement = getClosestAnnotatedAncestorElement(request.getElement(), request.getRootElement(), false);
        if (closestAnnotatedAncestorElement == null) {
            return false;
        }
        return isSuppressedByAnnotated(request.getSuppressKey(), request.getSeverity(), closestAnnotatedAncestorElement, request.getRootElement(), 0);
    }

    protected boolean isSuppressedByAnnotated(String suppressionKey, Severity severity, Element annotated, Element rootElement, int debugDepth) {
        Intrinsics.checkNotNullParameter(suppressionKey, "suppressionKey");
        Intrinsics.checkNotNullParameter(severity, "severity");
        Suppressor<Element> orCreateSuppressor = getOrCreateSuppressor(annotated);
        if (orCreateSuppressor.isSuppressed(suppressionKey, severity)) {
            return true;
        }
        Element closestAnnotatedAncestorElement = getClosestAnnotatedAncestorElement(orCreateSuppressor.getAnnotatedElement(), rootElement, true);
        if (closestAnnotatedAncestorElement == null) {
            return false;
        }
        boolean zIsSuppressedByAnnotated = isSuppressedByAnnotated(suppressionKey, severity, closestAnnotatedAncestorElement, rootElement, debugDepth + 1);
        Suppressor<Element> suppressor = this.suppressors.get(closestAnnotatedAncestorElement);
        if (suppressor != null && suppressor.dominates(orCreateSuppressor)) {
            this.suppressors.put(annotated, suppressor);
        }
        return zIsSuppressedByAnnotated;
    }

    protected final Suppressor<Element> getOrCreateSuppressor(Element annotated) {
        EmptySuppressor emptySuppressor;
        ConcurrentMap<Element, Suppressor<Element>> concurrentMap = this.suppressors;
        Suppressor<Element> suppressor = concurrentMap.get(annotated);
        if (suppressor == null) {
            Set<String> suppressingStrings = getSuppressingStrings(annotated);
            int size = suppressingStrings.size();
            if (size == 0) {
                emptySuppressor = new EmptySuppressor(annotated);
            } else if (size == 1) {
                emptySuppressor = new SingularSuppressor(annotated, (String) CollectionsKt.first(suppressingStrings));
            } else {
                emptySuppressor = new MultiSuppressor(annotated, suppressingStrings);
            }
            Suppressor<Element> suppressorPutIfAbsent = concurrentMap.putIfAbsent(annotated, emptySuppressor);
            suppressor = suppressorPutIfAbsent == null ? emptySuppressor : suppressorPutIfAbsent;
        }
        Intrinsics.checkNotNullExpressionValue(suppressor, "getOrPut(...)");
        return suppressor;
    }

    /* JADX INFO: compiled from: AbstractKotlinSuppressCache.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00060\b2\u0006\u0010\t\u001a\u00020\nH\u0002¨\u0006\u000b"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$Companion;", "", "()V", "isSuppressedByStrings", "", ASTExpr.DEFAULT_MAP_KEY_NAME, "", "strings", "", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        /* JADX INFO: Access modifiers changed from: private */
        public final boolean isSuppressedByStrings(String key, Set<String> strings, Severity severity) {
            if (severity != Severity.WARNING || !strings.contains(AbstractDiagnosticCollector.SUPPRESS_ALL_WARNINGS)) {
                String lowerCase = key.toLowerCase(Locale.ROOT);
                Intrinsics.checkNotNullExpressionValue(lowerCase, "toLowerCase(...)");
                if (!strings.contains(lowerCase)) {
                    return false;
                }
            }
            return true;
        }
    }

    /* JADX INFO: compiled from: AbstractKotlinSuppressCache.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b$\u0018\u0000*\u0004\b\u0001\u0010\u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0002\u0010\u0004J\u0016\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u0000H&J\u0018\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH&R\u0013\u0010\u0003\u001a\u00028\u0001¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$Suppressor;", "Element", "", "annotatedElement", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "getAnnotatedElement", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "dominates", "", "other", "isSuppressed", "suppressionKey", "", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    protected static abstract class Suppressor<Element> {
        private final Element annotatedElement;

        public abstract boolean dominates(Suppressor<Element> other);

        public abstract boolean isSuppressed(String suppressionKey, Severity severity);

        public Suppressor(Element element) {
            this.annotatedElement = element;
        }

        public final Element getAnnotatedElement() {
            return this.annotatedElement;
        }
    }

    /* JADX INFO: compiled from: AbstractKotlinSuppressCache.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0002\u0010\u0004J\u0016\u0010\u0005\u001a\u00020\u00062\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002H\u0016J\u0018\u0010\b\u001a\u00020\u00062\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016¨\u0006\r"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$EmptySuppressor;", "Element", "Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$Suppressor;", "annotated", ReifiedTypeInliner.pluginIntrinsicsMarkerSignature, "dominates", "", "other", "isSuppressed", "suppressionKey", "", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class EmptySuppressor<Element> extends Suppressor<Element> {
        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.Suppressor
        public boolean isSuppressed(String suppressionKey, Severity severity) {
            Intrinsics.checkNotNullParameter(suppressionKey, "suppressionKey");
            Intrinsics.checkNotNullParameter(severity, "severity");
            return false;
        }

        public EmptySuppressor(Element element) {
            super(element);
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.Suppressor
        public boolean dominates(Suppressor<Element> other) {
            Intrinsics.checkNotNullParameter(other, "other");
            return other instanceof EmptySuppressor;
        }
    }

    /* JADX INFO: compiled from: AbstractKotlinSuppressCache.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00028\u0001\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0016\u0010\u0007\u001a\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002H\u0016J\u0018\u0010\n\u001a\u00020\b2\u0006\u0010\u000b\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\rH\u0016R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$SingularSuppressor;", "Element", "Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$Suppressor;", "annotated", "string", "", "(Ljava/lang/Object;Ljava/lang/String;)V", "dominates", "", "other", "isSuppressed", "suppressionKey", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class SingularSuppressor<Element> extends Suppressor<Element> {
        private final String string;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public SingularSuppressor(Element element, String string) {
            super(element);
            Intrinsics.checkNotNullParameter(string, "string");
            this.string = string;
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.Suppressor
        public boolean isSuppressed(String suppressionKey, Severity severity) {
            Intrinsics.checkNotNullParameter(suppressionKey, "suppressionKey");
            Intrinsics.checkNotNullParameter(severity, "severity");
            Companion companion = AbstractKotlinSuppressCache.INSTANCE;
            ImmutableSet immutableSetOf = ImmutableSet.of(this.string);
            Intrinsics.checkNotNullExpressionValue(immutableSetOf, "of(...)");
            return companion.isSuppressedByStrings(suppressionKey, immutableSetOf, severity);
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.Suppressor
        public boolean dominates(Suppressor<Element> other) {
            Intrinsics.checkNotNullParameter(other, "other");
            return (other instanceof EmptySuppressor) || ((other instanceof SingularSuppressor) && Intrinsics.areEqual(((SingularSuppressor) other).string, this.string));
        }
    }

    /* JADX INFO: compiled from: AbstractKotlinSuppressCache.kt */
    @Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001b\u0012\u0006\u0010\u0003\u001a\u00028\u0001\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\u0016\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u00028\u00010\u0002H\u0016J\u0018\u0010\u000b\u001a\u00020\t2\u0006\u0010\f\u001a\u00020\u00062\u0006\u0010\r\u001a\u00020\u000eH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$MultiSuppressor;", "Element", "Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$Suppressor;", "annotated", "strings", "", "", "(Ljava/lang/Object;Ljava/util/Set;)V", "dominates", "", "other", "isSuppressed", "suppressionKey", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class MultiSuppressor<Element> extends Suppressor<Element> {
        private final Set<String> strings;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public MultiSuppressor(Element element, Set<String> strings) {
            super(element);
            Intrinsics.checkNotNullParameter(strings, "strings");
            this.strings = strings;
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.Suppressor
        public boolean isSuppressed(String suppressionKey, Severity severity) {
            Intrinsics.checkNotNullParameter(suppressionKey, "suppressionKey");
            Intrinsics.checkNotNullParameter(severity, "severity");
            return AbstractKotlinSuppressCache.INSTANCE.isSuppressedByStrings(suppressionKey, this.strings, severity);
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.Suppressor
        public boolean dominates(Suppressor<Element> other) {
            Intrinsics.checkNotNullParameter(other, "other");
            return other instanceof EmptySuppressor;
        }
    }

    /* JADX INFO: compiled from: AbstractKotlinSuppressCache.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\n\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B%\u0012\u0006\u0010\u0003\u001a\u00028\u0001\u0012\u0006\u0010\u0004\u001a\u00028\u0001\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tR\u0016\u0010\u0003\u001a\u00028\u0001X\u0096\u0004¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\n\u0010\u000bR\u0016\u0010\u0004\u001a\u00028\u0001X\u0096\u0004¢\u0006\n\n\u0002\u0010\f\u001a\u0004\b\r\u0010\u000bR\u0014\u0010\u0005\u001a\u00020\u0006X\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0014\u0010\u0007\u001a\u00020\bX\u0096\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$StringSuppressRequest;", "Element", "Lorg/jetbrains/kotlin/diagnostics/AbstractKotlinSuppressCache$SuppressRequest;", CapturedVarsOptimizationMethodTransformerKt.REF_ELEMENT_FIELD, "rootElement", "severity", "Lorg/jetbrains/kotlin/diagnostics/Severity;", "suppressKey", "", "(Ljava/lang/Object;Ljava/lang/Object;Lorg/jetbrains/kotlin/diagnostics/Severity;Ljava/lang/String;)V", "getElement", "()Ljava/lang/Object;", TypeProxy.SilentConstruction.Appender.JAVA_LANG_OBJECT_DESCRIPTOR, "getRootElement", "getSeverity", "()Lorg/jetbrains/kotlin/diagnostics/Severity;", "getSuppressKey", "()Ljava/lang/String;", "frontend.common"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class StringSuppressRequest<Element> implements SuppressRequest<Element> {
        private final Element element;
        private final Element rootElement;
        private final Severity severity;
        private final String suppressKey;

        public StringSuppressRequest(Element element, Element element2, Severity severity, String suppressKey) {
            Intrinsics.checkNotNullParameter(severity, "severity");
            Intrinsics.checkNotNullParameter(suppressKey, "suppressKey");
            this.element = element;
            this.rootElement = element2;
            this.severity = severity;
            this.suppressKey = suppressKey;
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.SuppressRequest
        public Element getElement() {
            return this.element;
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.SuppressRequest
        public Element getRootElement() {
            return this.rootElement;
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.SuppressRequest
        public Severity getSeverity() {
            return this.severity;
        }

        @Override // org.jetbrains.kotlin.diagnostics.AbstractKotlinSuppressCache.SuppressRequest
        public String getSuppressKey() {
            return this.suppressKey;
        }
    }
}
