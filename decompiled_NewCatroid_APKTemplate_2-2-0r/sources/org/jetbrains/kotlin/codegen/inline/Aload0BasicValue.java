package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.Set;
import kotlin.Metadata;
import kotlin.collections.SetsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.resolve.jvm.AsmTypes;
import org.jetbrains.org.objectweb.asm.tree.analysis.BasicValue;

/* JADX INFO: compiled from: MethodInlinerUtil.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\b\u0006\b\u0000\u0018\u00002\u00020\u0001B\u000f\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004B\u0015\b\u0002\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\u0002\u0010\u0007J\u0011\u0010\n\u001a\u00020\u00002\u0006\u0010\u000b\u001a\u00020\u0000H\u0086\u0002R\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00030\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/Aload0BasicValue;", "Lorg/jetbrains/org/objectweb/asm/tree/analysis/BasicValue;", ASTExpr.DEFAULT_INDEX_VARIABLE_NAME, "", "(I)V", "indices", "", "(Ljava/util/Set;)V", "getIndices", "()Ljava/util/Set;", "plus", "other", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Aload0BasicValue extends BasicValue {
    private final Set<Integer> indices;

    private Aload0BasicValue(Set<Integer> set) {
        super(AsmTypes.OBJECT_TYPE);
        this.indices = set;
    }

    public final Set<Integer> getIndices() {
        return this.indices;
    }

    public Aload0BasicValue(int i) {
        this((Set<Integer>) SetsKt.setOf(Integer.valueOf(i)));
    }

    public final Aload0BasicValue plus(Aload0BasicValue other) {
        Intrinsics.checkNotNullParameter(other, "other");
        return new Aload0BasicValue((Set<Integer>) SetsKt.plus((Set) this.indices, (Iterable) other.indices));
    }
}
