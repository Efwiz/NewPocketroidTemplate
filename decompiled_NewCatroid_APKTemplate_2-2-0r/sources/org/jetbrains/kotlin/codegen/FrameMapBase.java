package org.jetbrains.kotlin.codegen;

import androidx.exifinterface.media.ExifInterface;
import com.reandroid.dex.sections.Marker;
import gnu.trove.TObjectIntHashMap;
import gnu.trove.TObjectIntIterator;
import java.util.ArrayList;
import java.util.Comparator;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import org.antlr.stringtemplate.language.ASTExpr;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.jetbrains.kotlin.com.google.common.collect.Lists;
import org.jetbrains.kotlin.com.intellij.openapi.util.Trinity;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: FrameMap.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0016\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u0002:\u0001\u001eB\u0005¢\u0006\u0002\u0010\u0003J\u001d\u0010\f\u001a\u00020\u00052\u0006\u0010\r\u001a\u00028\u00002\u0006\u0010\u000e\u001a\u00020\u000fH\u0016¢\u0006\u0002\u0010\u0010J\u000e\u0010\u0011\u001a\u00020\u00052\u0006\u0010\u000e\u001a\u00020\u000fJ\u0015\u0010\u0012\u001a\u00020\u00052\u0006\u0010\u0013\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0014J\u0015\u0010\u0015\u001a\u00020\u00052\u0006\u0010\r\u001a\u00028\u0000H\u0016¢\u0006\u0002\u0010\u0014J\u000e\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u000e\u001a\u00020\u000fJ\u0010\u0010\u0018\u001a\f0\u0019R\b\u0012\u0004\u0012\u00028\u00000\u0000J\u0018\u0010\u001a\u001a\f0\u0019R\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u001b\u001a\u00020\u0005J\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u001e\u0010\u0006\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005@BX\u0086\u000e¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00000\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00028\u00000\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/codegen/FrameMapBase;", ExifInterface.GPS_DIRECTION_TRUE, "", "()V", "<set-?>", "", "currentSize", "getCurrentSize", "()I", "myVarIndex", "Lgnu/trove/TObjectIntHashMap;", "myVarSizes", "enter", ASTExpr.DEFAULT_MAP_KEY_NAME, ModuleXmlParser.TYPE, "Lorg/jetbrains/org/objectweb/asm/Type;", "(Ljava/lang/Object;Lorg/jetbrains/org/objectweb/asm/Type;)I", "enterTemp", "getIndex", "descriptor", "(Ljava/lang/Object;)I", "leave", "leaveTemp", "", "mark", "Lorg/jetbrains/kotlin/codegen/FrameMapBase$Mark;", "skipTo", "target", "toString", "", "Mark", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public class FrameMapBase<T> {
    private int currentSize;
    private final TObjectIntHashMap<T> myVarIndex = new TObjectIntHashMap<>();
    private final TObjectIntHashMap<T> myVarSizes = new TObjectIntHashMap<>();

    public final int getCurrentSize() {
        return this.currentSize;
    }

    public int enter(T key, Type type) {
        Intrinsics.checkNotNullParameter(key, "key");
        Intrinsics.checkNotNullParameter(type, "type");
        int i = this.currentSize;
        this.myVarIndex.put(key, i);
        this.currentSize += type.getSize();
        this.myVarSizes.put(key, type.getSize());
        return i;
    }

    public int leave(T key) {
        Intrinsics.checkNotNullParameter(key, "key");
        this.currentSize -= this.myVarSizes.get(key);
        this.myVarSizes.remove(key);
        int iRemove = this.myVarIndex.remove(key);
        if (iRemove == this.currentSize) {
            return iRemove;
        }
        throw new IllegalStateException("Descriptor can be left only if it is last: " + key);
    }

    public final int enterTemp(Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        int i = this.currentSize;
        this.currentSize = type.getSize() + i;
        return i;
    }

    public final void leaveTemp(Type type) {
        Intrinsics.checkNotNullParameter(type, "type");
        this.currentSize -= type.getSize();
    }

    public int getIndex(T descriptor) {
        Intrinsics.checkNotNullParameter(descriptor, "descriptor");
        if (this.myVarIndex.contains(descriptor)) {
            return this.myVarIndex.get(descriptor);
        }
        return -1;
    }

    public final FrameMapBase<T>.Mark mark() {
        return new Mark(this.currentSize);
    }

    public final FrameMapBase<T>.Mark skipTo(int target) {
        FrameMapBase<T>.Mark mark = mark();
        if (this.currentSize < target) {
            this.currentSize = target;
        }
        return mark;
    }

    /* JADX INFO: compiled from: FrameMap.kt */
    @Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\b\u0086\u0004\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0006\u0010\u0005\u001a\u00020\u0006R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/codegen/FrameMapBase$Mark;", "", "myIndex", "", "(Lorg/jetbrains/kotlin/codegen/FrameMapBase;I)V", "dropTo", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class Mark {
        private final int myIndex;

        public Mark(int i) {
            this.myIndex = i;
        }

        public final void dropTo() {
            ArrayList arrayList = new ArrayList();
            TObjectIntIterator it = ((FrameMapBase) FrameMapBase.this).myVarIndex.iterator();
            while (it.hasNext()) {
                it.advance();
                if (it.value() >= this.myIndex) {
                    arrayList.add(it.key());
                }
            }
            for (Object obj : arrayList) {
                ((FrameMapBase) FrameMapBase.this).myVarIndex.remove(obj);
                ((FrameMapBase) FrameMapBase.this).myVarSizes.remove(obj);
            }
            ((FrameMapBase) FrameMapBase.this).currentSize = this.myIndex;
        }
    }

    /* JADX WARN: Type inference incomplete: some casts might be missing */
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.myVarIndex.size() != this.myVarSizes.size()) {
            return "inconsistent";
        }
        ArrayList<Trinity> arrayListNewArrayList = Lists.newArrayList();
        Object[] objArrKeys = this.myVarIndex.keys();
        Intrinsics.checkNotNullExpressionValue(objArrKeys, "keys(...)");
        for (Object obj : objArrKeys) {
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type T of org.jetbrains.kotlin.codegen.FrameMapBase");
            arrayListNewArrayList.add(Trinity.create(obj, Integer.valueOf(this.myVarIndex.get((T) obj)), Integer.valueOf(this.myVarSizes.get((T) obj))));
        }
        Intrinsics.checkNotNull(arrayListNewArrayList);
        ArrayList arrayList = arrayListNewArrayList;
        boolean z = true;
        if (arrayList.size() > 1) {
            CollectionsKt.sortWith(arrayList, new Comparator() { // from class: org.jetbrains.kotlin.codegen.FrameMapBase$toString$$inlined$sortBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    return ComparisonsKt.compareValues((Integer) ((Trinity) t).second, (Integer) ((Trinity) t2).second);
                }
            });
        }
        sb.append("size=").append(this.currentSize);
        for (Trinity trinity : arrayListNewArrayList) {
            if (!z) {
                sb.append(", ");
            }
            StringBuilder sbAppend = sb.append(trinity.first).append(",i=");
            B second = trinity.second;
            Intrinsics.checkNotNullExpressionValue(second, "second");
            StringBuilder sbAppend2 = sbAppend.append(((Number) second).intValue()).append(",s=");
            C third = trinity.third;
            Intrinsics.checkNotNullExpressionValue(third, "third");
            sbAppend2.append(((Number) third).intValue());
            z = false;
        }
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }
}
