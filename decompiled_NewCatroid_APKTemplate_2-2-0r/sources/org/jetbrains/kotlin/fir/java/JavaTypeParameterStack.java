package org.jetbrains.kotlin.fir.java;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.symbols.impl.FirTypeParameterSymbol;
import org.jetbrains.kotlin.load.java.structure.JavaTypeParameter;
import org.mozilla.javascript.NativeSymbol;

/* JADX INFO: compiled from: JavaTypeParameterStack.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0010&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0006\n\u0002\u0010(\n\u0002\b\u0002\u0018\u0000 \u00112\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u0001:\u0001\u0011B\u0005¢\u0006\u0002\u0010\u0005J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u00032\u0006\u0010\u000b\u001a\u00020\u0004J\u000e\u0010\f\u001a\u00020\t2\u0006\u0010\r\u001a\u00020\u0000J\u0011\u0010\u000e\u001a\u00020\u00042\u0006\u0010\n\u001a\u00020\u0003H\u0086\u0002J\u001b\u0010\u000f\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u00020\u0010H\u0096\u0002R\u001a\u0010\u0006\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00040\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", "", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaTypeParameter;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirTypeParameterSymbol;", "()V", "typeParameterMap", "", "addParameter", "", "javaTypeParameter", NativeSymbol.TYPE_NAME, "addStack", "javaTypeParameterStack", "get", "iterator", "", "Companion", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaTypeParameterStack implements Iterable<Map.Entry<? extends JavaTypeParameter, ? extends FirTypeParameterSymbol>>, KMappedMarker {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final JavaTypeParameterStack EMPTY = new JavaTypeParameterStack();
    private final Map<JavaTypeParameter, FirTypeParameterSymbol> typeParameterMap = new LinkedHashMap();

    public final void addParameter(JavaTypeParameter javaTypeParameter, FirTypeParameterSymbol symbol) {
        Intrinsics.checkNotNullParameter(javaTypeParameter, "javaTypeParameter");
        Intrinsics.checkNotNullParameter(symbol, "symbol");
        this.typeParameterMap.put(javaTypeParameter, symbol);
    }

    public final void addStack(JavaTypeParameterStack javaTypeParameterStack) {
        Intrinsics.checkNotNullParameter(javaTypeParameterStack, "javaTypeParameterStack");
        this.typeParameterMap.putAll(javaTypeParameterStack.typeParameterMap);
    }

    public final FirTypeParameterSymbol get(JavaTypeParameter javaTypeParameter) {
        Intrinsics.checkNotNullParameter(javaTypeParameter, "javaTypeParameter");
        FirTypeParameterSymbol firTypeParameterSymbol = this.typeParameterMap.get(javaTypeParameter);
        if (firTypeParameterSymbol != null) {
            return firTypeParameterSymbol;
        }
        throw new IllegalArgumentException("Cannot find Java type parameter " + javaTypeParameter + " in stack");
    }

    @Override // java.lang.Iterable
    public Iterator<Map.Entry<? extends JavaTypeParameter, ? extends FirTypeParameterSymbol>> iterator() {
        return this.typeParameterMap.entrySet().iterator();
    }

    /* JADX INFO: compiled from: JavaTypeParameterStack.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack$Companion;", "", "()V", "EMPTY", "Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", "getEMPTY", "()Lorg/jetbrains/kotlin/fir/java/JavaTypeParameterStack;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }

        public final JavaTypeParameterStack getEMPTY() {
            return JavaTypeParameterStack.EMPTY;
        }
    }
}
