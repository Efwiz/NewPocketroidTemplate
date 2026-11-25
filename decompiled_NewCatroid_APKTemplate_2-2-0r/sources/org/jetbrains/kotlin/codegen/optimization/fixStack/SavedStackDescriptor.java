package org.jetbrains.kotlin.codegen.optimization.fixStack;

import com.reandroid.dex.sections.Marker;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.cli.common.arguments.PreprocessCommandLineArgumentsKt;

/* JADX INFO: compiled from: StackTransformationUtils.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\t\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001b\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0006\u0010\u000f\u001a\u00020\u0010J\b\u0010\u0011\u001a\u00020\u0012H\u0016R\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\n\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u000e\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u0013"}, d2 = {"Lorg/jetbrains/kotlin/codegen/optimization/fixStack/SavedStackDescriptor;", "", "savedValues", "", "Lorg/jetbrains/kotlin/codegen/optimization/fixStack/FixStackValue;", "firstLocalVarIndex", "", "(Ljava/util/List;I)V", "getFirstLocalVarIndex", "()I", "firstUnusedLocalVarIndex", "getFirstUnusedLocalVarIndex", "getSavedValues", "()Ljava/util/List;", "savedValuesSize", "isNotEmpty", "", "toString", "", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class SavedStackDescriptor {
    private final int firstLocalVarIndex;
    private final int firstUnusedLocalVarIndex;
    private final List<FixStackValue> savedValues;
    private final int savedValuesSize;

    /* JADX WARN: Multi-variable type inference failed */
    public SavedStackDescriptor(List<? extends FixStackValue> savedValues, int i) {
        Intrinsics.checkNotNullParameter(savedValues, "savedValues");
        this.savedValues = savedValues;
        this.firstLocalVarIndex = i;
        Iterator it = savedValues.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            i2 += ((FixStackValue) it.next()).get_size();
        }
        this.savedValuesSize = i2;
        this.firstUnusedLocalVarIndex = this.firstLocalVarIndex + i2;
    }

    public final List<FixStackValue> getSavedValues() {
        return this.savedValues;
    }

    public final int getFirstLocalVarIndex() {
        return this.firstLocalVarIndex;
    }

    public final int getFirstUnusedLocalVarIndex() {
        return this.firstUnusedLocalVarIndex;
    }

    public String toString() {
        return PreprocessCommandLineArgumentsKt.ARGFILE_ARGUMENT + this.firstLocalVarIndex + ": [" + this.savedValues + ']';
    }

    public final boolean isNotEmpty() {
        return !this.savedValues.isEmpty();
    }
}
