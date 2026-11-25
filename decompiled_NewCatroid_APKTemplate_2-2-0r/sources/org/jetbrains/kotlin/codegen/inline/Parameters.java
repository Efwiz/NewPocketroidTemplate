package org.jetbrains.kotlin.codegen.inline;

import com.reandroid.dex.sections.Marker;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Lazy;
import kotlin.LazyKt;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IndexedValue;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import org.eclipse.jdt.internal.compiler.impl.CompilerOptions;
import org.jetbrains.org.objectweb.asm.Type;

/* JADX INFO: compiled from: Parameters.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010(\n\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004¢\u0006\u0002\u0010\u0005J\u0010\u0010\u001f\u001a\u00020\u00022\u0006\u0010 \u001a\u00020\nH\u0002J\u000e\u0010!\u001a\u00020\n2\u0006\u0010\"\u001a\u00020\u0002J\u000e\u0010#\u001a\u00020\u00022\u0006\u0010 \u001a\u00020\nJ\u000f\u0010$\u001a\b\u0012\u0004\u0012\u00020\u00020%H\u0096\u0002R\u0018\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0007X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\bR\u0011\u0010\t\u001a\u00020\n¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR&\u0010\r\u001a\r\u0012\t\u0012\u00070\u000e¢\u0006\u0002\b\u000f0\u00048FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0012\u0010\u0013\u001a\u0004\b\u0010\u0010\u0011R\u001b\u0010\u0014\u001a\u00020\n8FX\u0086\u0084\u0002¢\u0006\f\n\u0004\b\u0016\u0010\u0013\u001a\u0004\b\u0015\u0010\fR\u0017\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00180\u00048F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0011R\u001a\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\n0\u001bX\u0082\u0004¢\u0006\u0002\n\u0000R\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u001c\u0010\u0011R\u0011\u0010\u001d\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u001e\u0010\f¨\u0006&"}, d2 = {"Lorg/jetbrains/kotlin/codegen/inline/Parameters;", "", "Lorg/jetbrains/kotlin/codegen/inline/ParameterInfo;", "parameters", "", "(Ljava/util/List;)V", "actualDeclShifts", "", "[Lorg/jetbrains/kotlin/codegen/inline/ParameterInfo;", "argsSizeOnStack", "", "getArgsSizeOnStack", "()I", "captured", "Lorg/jetbrains/kotlin/codegen/inline/CapturedParamInfo;", "Lkotlin/internal/NoInfer;", "getCaptured", "()Ljava/util/List;", "captured$delegate", "Lkotlin/Lazy;", "capturedParametersSizeOnStack", "getCapturedParametersSizeOnStack", "capturedParametersSizeOnStack$delegate", "capturedTypes", "Lorg/jetbrains/org/objectweb/asm/Type;", "getCapturedTypes", "paramToDeclByteCodeIndex", "Ljava/util/HashMap;", "getParameters", "realParametersSizeOnStack", "getRealParametersSizeOnStack", "get", "index", "getDeclarationSlot", CompilerOptions.INFO, "getParameterByDeclarationSlot", "iterator", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class Parameters implements Iterable<ParameterInfo>, KMappedMarker {
    private final ParameterInfo[] actualDeclShifts;
    private final int argsSizeOnStack;

    /* JADX INFO: renamed from: captured$delegate, reason: from kotlin metadata */
    private final Lazy captured;

    /* JADX INFO: renamed from: capturedParametersSizeOnStack$delegate, reason: from kotlin metadata */
    private final Lazy capturedParametersSizeOnStack;
    private final HashMap<ParameterInfo, Integer> paramToDeclByteCodeIndex;
    private final List<ParameterInfo> parameters;

    /* JADX WARN: Multi-variable type inference failed */
    public Parameters(List<? extends ParameterInfo> parameters) {
        Intrinsics.checkNotNullParameter(parameters, "parameters");
        this.parameters = parameters;
        this.paramToDeclByteCodeIndex = new HashMap<>();
        Iterator it = parameters.iterator();
        int size = 0;
        while (it.hasNext()) {
            size += ((ParameterInfo) it.next()).getType().getSize();
        }
        this.argsSizeOnStack = size;
        this.capturedParametersSizeOnStack = LazyKt.lazy(new Function0<Integer>() { // from class: org.jetbrains.kotlin.codegen.inline.Parameters$capturedParametersSizeOnStack$2
            {
                super(0);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // kotlin.jvm.functions.Function0
            public final Integer invoke() {
                Iterator<T> it2 = this.this$0.getCaptured().iterator();
                int size2 = 0;
                while (it2.hasNext()) {
                    size2 += ((CapturedParamInfo) it2.next()).getType().getSize();
                }
                return Integer.valueOf(size2);
            }
        });
        this.captured = LazyKt.lazy(new Function0<List<? extends CapturedParamInfo>>() { // from class: org.jetbrains.kotlin.codegen.inline.Parameters$captured$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends CapturedParamInfo> invoke() {
                List<ParameterInfo> parameters2 = this.this$0.getParameters();
                ArrayList arrayList = new ArrayList();
                for (Object obj : parameters2) {
                    if (obj instanceof CapturedParamInfo) {
                        arrayList.add(obj);
                    }
                }
                return arrayList;
            }
        });
        Integer[] numArr = new Integer[size];
        for (IndexedValue indexedValue : CollectionsKt.withIndex(this)) {
            numArr[((ParameterInfo) indexedValue.getValue()).getDeclarationIndex()] = Integer.valueOf(indexedValue.getIndex());
        }
        this.actualDeclShifts = new ParameterInfo[this.argsSizeOnStack];
        int size2 = 0;
        for (int i = 0; i < size; i++) {
            Integer num = numArr[i];
            if (num != null) {
                ParameterInfo parameterInfo = get(num.intValue());
                this.actualDeclShifts[size2] = parameterInfo;
                this.paramToDeclByteCodeIndex.put(parameterInfo, Integer.valueOf(size2));
                size2 += parameterInfo.getType().getSize();
            }
        }
    }

    public final List<ParameterInfo> getParameters() {
        return this.parameters;
    }

    public final int getArgsSizeOnStack() {
        return this.argsSizeOnStack;
    }

    public final int getRealParametersSizeOnStack() {
        return this.argsSizeOnStack - getCapturedParametersSizeOnStack();
    }

    public final int getCapturedParametersSizeOnStack() {
        return ((Number) this.capturedParametersSizeOnStack.getValue()).intValue();
    }

    public final List<CapturedParamInfo> getCaptured() {
        return (List) this.captured.getValue();
    }

    public final int getDeclarationSlot(ParameterInfo info) {
        Intrinsics.checkNotNullParameter(info, "info");
        Integer num = this.paramToDeclByteCodeIndex.get(info);
        Intrinsics.checkNotNull(num);
        return num.intValue();
    }

    public final ParameterInfo getParameterByDeclarationSlot(int index) {
        ParameterInfo parameterInfo = this.actualDeclShifts[index];
        Intrinsics.checkNotNull(parameterInfo);
        return parameterInfo;
    }

    private final ParameterInfo get(int index) {
        return this.parameters.get(index);
    }

    @Override // java.lang.Iterable
    public Iterator<ParameterInfo> iterator() {
        return this.parameters.iterator();
    }

    public final List<Type> getCapturedTypes() {
        List<CapturedParamInfo> captured = getCaptured();
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(captured, 10));
        Iterator<T> it = captured.iterator();
        while (it.hasNext()) {
            arrayList.add(((CapturedParamInfo) it.next()).getType());
        }
        return arrayList;
    }
}
