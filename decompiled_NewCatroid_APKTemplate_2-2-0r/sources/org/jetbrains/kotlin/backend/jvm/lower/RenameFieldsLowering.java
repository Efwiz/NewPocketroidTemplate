package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrField;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: RenameFieldsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016¨\u0006\u0007"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/RenameFieldsLowering;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "()V", "lower", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class RenameFieldsLowering implements ClassLoweringPass {
    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        List<IrField> mutableList = SequencesKt.toMutableList(IrUtilsKt.getFields(irClass));
        if (mutableList.size() > 1) {
            CollectionsKt.sortWith(mutableList, new Comparator() { // from class: org.jetbrains.kotlin.backend.jvm.lower.RenameFieldsLowering$lower$$inlined$sortBy$1
                /* JADX WARN: Multi-variable type inference failed */
                @Override // java.util.Comparator
                public final int compare(T t, T t2) {
                    int i;
                    IrField irField = (IrField) t;
                    int i2 = 1;
                    if (irField.getVisibility().isPublicAPI()) {
                        i = 0;
                    } else {
                        i = irField.getIsStatic() ? 1 : 2;
                    }
                    Integer numValueOf = Integer.valueOf(i);
                    IrField irField2 = (IrField) t2;
                    if (irField2.getVisibility().isPublicAPI()) {
                        i2 = 0;
                    } else if (!irField2.getIsStatic()) {
                        i2 = 2;
                    }
                    return ComparisonsKt.compareValues(numValueOf, Integer.valueOf(i2));
                }
            });
        }
        HashMap map = new HashMap();
        for (IrField irField : mutableList) {
            Name name2 = irField.getName();
            int i = (Integer) map.get(name2);
            if (i == null) {
                i = 0;
            }
            int iIntValue = i.intValue();
            if (iIntValue != 0 && !irField.getVisibility().isPublicAPI()) {
                Name nameIdentifier = Name.identifier(new StringBuilder().append(name2).append('$').append(iIntValue).toString());
                Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
                irField.setName(nameIdentifier);
            }
            map.put(name2, Integer.valueOf(iIntValue + 1));
        }
    }
}
