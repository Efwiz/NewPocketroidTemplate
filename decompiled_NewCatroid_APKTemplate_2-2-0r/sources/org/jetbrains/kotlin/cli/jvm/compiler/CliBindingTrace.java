package org.jetbrains.kotlin.cli.jvm.compiler;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.psi.KtDeclaration;
import org.jetbrains.kotlin.psi.KtPsiUtil;
import org.jetbrains.kotlin.resolve.BindingContext;
import org.jetbrains.kotlin.resolve.BindingTraceContext;
import org.jetbrains.kotlin.resolve.lazy.KotlinCodeAnalyzer;
import org.jetbrains.kotlin.util.slicedMap.ReadOnlySlice;

/* JADX INFO: compiled from: CliTrace.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0016\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J8\u0010\u0005\u001a\u0004\u0018\u0001H\u0006\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\u00062\u0012\u0010\b\u001a\u000e\u0012\u0004\u0012\u0002H\u0007\u0012\u0004\u0012\u0002H\u00060\t2\u0006\u0010\n\u001a\u0002H\u0007H\u0096\u0002¢\u0006\u0002\u0010\u000bJ\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u0003\u001a\u00020\u0004J\b\u0010\u000e\u001a\u00020\u000fH\u0016R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/cli/jvm/compiler/CliBindingTrace;", "Lorg/jetbrains/kotlin/resolve/BindingTraceContext;", "()V", "kotlinCodeAnalyzer", "Lorg/jetbrains/kotlin/resolve/lazy/KotlinCodeAnalyzer;", "get", ExifInterface.GPS_MEASUREMENT_INTERRUPTED, "K", "slice", "Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;", ASTExpr.DEFAULT_MAP_KEY_NAME, "(Lorg/jetbrains/kotlin/util/slicedMap/ReadOnlySlice;Ljava/lang/Object;)Ljava/lang/Object;", "setKotlinCodeAnalyzer", "", "toString", "", "cli-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public class CliBindingTrace extends BindingTraceContext {
    private KotlinCodeAnalyzer kotlinCodeAnalyzer;

    public String toString() {
        String name2 = CliBindingTrace.class.getName();
        Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
        return name2;
    }

    public final void setKotlinCodeAnalyzer(KotlinCodeAnalyzer kotlinCodeAnalyzer) {
        Intrinsics.checkNotNullParameter(kotlinCodeAnalyzer, "kotlinCodeAnalyzer");
        this.kotlinCodeAnalyzer = kotlinCodeAnalyzer;
    }

    @Override // org.jetbrains.kotlin.resolve.BindingTraceContext, org.jetbrains.kotlin.resolve.BindingTrace
    public <K, V> V get(ReadOnlySlice<K, V> slice, K key) {
        Intrinsics.checkNotNullParameter(slice, "slice");
        V v = (V) super.get(slice, key);
        if (v == null && (key instanceof KtDeclaration)) {
            if (BindingContext.FUNCTION == slice) {
                KtDeclaration ktDeclaration = (KtDeclaration) key;
                if (!KtPsiUtil.isLocal(ktDeclaration)) {
                    KotlinCodeAnalyzer kotlinCodeAnalyzer = this.kotlinCodeAnalyzer;
                    Intrinsics.checkNotNull(kotlinCodeAnalyzer);
                    kotlinCodeAnalyzer.resolveToDescriptor(ktDeclaration);
                    return (V) super.get(slice, key);
                }
            }
            if (BindingContext.VARIABLE == slice) {
                KtDeclaration ktDeclaration2 = (KtDeclaration) key;
                if (!KtPsiUtil.isLocal(ktDeclaration2)) {
                    KotlinCodeAnalyzer kotlinCodeAnalyzer2 = this.kotlinCodeAnalyzer;
                    Intrinsics.checkNotNull(kotlinCodeAnalyzer2);
                    kotlinCodeAnalyzer2.resolveToDescriptor(ktDeclaration2);
                    return (V) super.get(slice, key);
                }
            }
        }
        return v;
    }
}
