package org.jetbrains.kotlin.ir.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntRange;
import kotlin.ranges.RangesKt;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrFileEntry;
import org.jetbrains.kotlin.ir.declarations.IrAnonymousInitializer;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFile;
import org.jetbrains.kotlin.ir.declarations.IrFunction;
import org.jetbrains.kotlin.ir.declarations.IrProperty;
import org.jetbrains.kotlin.ir.declarations.IrSymbolOwner;
import org.jetbrains.kotlin.ir.expressions.IrMemberAccessExpression;

/* JADX INFO: compiled from: DumpIrTree.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u00008\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0004H\u0000\u001a\u0014\u0010\u0005\u001a\u00020\u0002*\u00020\u00062\b\b\u0002\u0010\u0007\u001a\u00020\b\u001a\u001c\u0010\t\u001a\u00020\u0002*\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\b\u001a\u0016\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u0006\u0012\u0002\b\u00030\rH\u0000\u001a\f\u0010\u000e\u001a\u00020\u000f*\u00020\nH\u0002\u001a\u0018\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\u0001*\b\u0012\u0004\u0012\u00020\u00110\u0001H\u0000¨\u0006\u0012"}, d2 = {"getPlaceholderParameterNames", "", "", "expectedCount", "", ArchiveStreamFactory.DUMP, "Lorg/jetbrains/kotlin/ir/IrElement;", "options", "Lorg/jetbrains/kotlin/ir/util/DumpIrTreeOptions;", "dumpTreesFromLineNumber", "Lorg/jetbrains/kotlin/ir/declarations/IrFile;", "lineNumber", "getValueParameterNamesForDebug", "Lorg/jetbrains/kotlin/ir/expressions/IrMemberAccessExpression;", "shouldSkipDump", "", "stableOrdered", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DumpIrTreeKt {
    public static /* synthetic */ String dump$default(IrElement irElement, DumpIrTreeOptions dumpIrTreeOptions, int i, Object obj) {
        if ((i & 1) != 0) {
            dumpIrTreeOptions = new DumpIrTreeOptions(false, false, false, false, false, false, 63, null);
        }
        return dump(irElement, dumpIrTreeOptions);
    }

    public static final String dump(IrElement irElement, DumpIrTreeOptions options) {
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        try {
            StringBuilder sb = new StringBuilder();
            irElement.accept(new DumpIrTreeVisitor(sb, options), "");
            String string = sb.toString();
            Intrinsics.checkNotNull(string);
            return string;
        } catch (Exception e) {
            return "(Full dump is not available: " + e.getMessage() + ")\n" + RenderIrElementKt.render(irElement, options);
        }
    }

    public static /* synthetic */ String dumpTreesFromLineNumber$default(IrFile irFile, int i, DumpIrTreeOptions dumpIrTreeOptions, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            dumpIrTreeOptions = new DumpIrTreeOptions(false, false, false, false, false, false, 63, null);
        }
        return dumpTreesFromLineNumber(irFile, i, dumpIrTreeOptions);
    }

    public static final String dumpTreesFromLineNumber(IrFile irFile, int i, DumpIrTreeOptions options) {
        Intrinsics.checkNotNullParameter(irFile, "<this>");
        Intrinsics.checkNotNullParameter(options, "options");
        if (shouldSkipDump(irFile)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        irFile.accept(new DumpTreeFromSourceLineVisitor(irFile.getFileEntry(), i, sb, options), null);
        String string = sb.toString();
        Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
        return string;
    }

    private static final boolean shouldSkipDump(IrFile irFile) {
        IrFileEntry fileEntry = irFile.getFileEntry();
        NaiveSourceBasedFileEntryImpl naiveSourceBasedFileEntryImpl = fileEntry instanceof NaiveSourceBasedFileEntryImpl ? (NaiveSourceBasedFileEntryImpl) fileEntry : null;
        if (naiveSourceBasedFileEntryImpl == null) {
            return false;
        }
        return naiveSourceBasedFileEntryImpl.getLineStartOffsetsAreEmpty();
    }

    public static final List<IrDeclaration> stableOrdered(List<? extends IrDeclaration> list) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        final HashMap map = new HashMap();
        List<? extends IrDeclaration> list2 = list;
        int i = 0;
        for (IrDeclaration irDeclaration : list2) {
            if (irDeclaration instanceof IrProperty) {
                IrProperty irProperty = (IrProperty) irDeclaration;
                if (irProperty.getBackingField() != null && !irProperty.getIsConst()) {
                    map.put(irDeclaration, Integer.valueOf(i));
                    i++;
                }
            }
            if (irDeclaration instanceof IrAnonymousInitializer) {
                map.put(irDeclaration, Integer.valueOf(i));
                i++;
            }
        }
        final Function2<IrDeclaration, IrDeclaration, Integer> function2 = new Function2<IrDeclaration, IrDeclaration, Integer>() { // from class: org.jetbrains.kotlin.ir.util.DumpIrTreeKt.stableOrdered.2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Integer invoke(IrDeclaration irDeclaration2, IrDeclaration irDeclaration3) {
                int iCompareTo;
                Integer num = map.get(irDeclaration2);
                Integer num2 = Integer.MAX_VALUE;
                if (num == null) {
                    num = num2;
                }
                int iIntValue = num.intValue();
                Integer num3 = map.get(irDeclaration3);
                int iIntValue2 = (num3 != null ? num3 : Integer.MAX_VALUE).intValue();
                if (iIntValue == iIntValue2) {
                    Intrinsics.checkNotNull(irDeclaration2);
                    String strRender$default = RenderIrElementKt.render$default(irDeclaration2, (DumpIrTreeOptions) null, 1, (Object) null);
                    Intrinsics.checkNotNull(irDeclaration3);
                    iCompareTo = strRender$default.compareTo(RenderIrElementKt.render$default(irDeclaration3, (DumpIrTreeOptions) null, 1, (Object) null));
                } else {
                    iCompareTo = iIntValue - iIntValue2;
                }
                return Integer.valueOf(iCompareTo);
            }
        };
        return CollectionsKt.sortedWith(list2, new Comparator() { // from class: org.jetbrains.kotlin.ir.util.DumpIrTreeKt$$ExternalSyntheticLambda0
            @Override // java.util.Comparator
            public final int compare(Object obj, Object obj2) {
                return DumpIrTreeKt.stableOrdered$lambda$2(function2, obj, obj2);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final int stableOrdered$lambda$2(Function2 tmp0, Object obj, Object obj2) {
        Intrinsics.checkNotNullParameter(tmp0, "$tmp0");
        return ((Number) tmp0.invoke(obj, obj2)).intValue();
    }

    public static final List<String> getValueParameterNamesForDebug(IrMemberAccessExpression<?> irMemberAccessExpression) {
        String strValueOf;
        Intrinsics.checkNotNullParameter(irMemberAccessExpression, "<this>");
        int valueArgumentsCount = irMemberAccessExpression.getValueArgumentsCount();
        if (irMemberAccessExpression.getSymbol().isBound()) {
            IrSymbolOwner owner = irMemberAccessExpression.getSymbol().getOwner();
            if (owner instanceof IrFunction) {
                IntRange intRangeUntil = RangesKt.until(0, valueArgumentsCount);
                ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRangeUntil, 10));
                Iterator<Integer> it = intRangeUntil.iterator();
                while (it.hasNext()) {
                    int iNextInt = ((IntIterator) it).nextInt();
                    IrFunction irFunction = (IrFunction) owner;
                    if (iNextInt < irFunction.getValueParameters().size()) {
                        strValueOf = irFunction.getValueParameters().get(iNextInt).getName().asString();
                    } else {
                        strValueOf = String.valueOf(iNextInt + 1);
                    }
                    arrayList.add(strValueOf);
                }
                return arrayList;
            }
        }
        return getPlaceholderParameterNames(valueArgumentsCount);
    }

    public static final List<String> getPlaceholderParameterNames(int i) {
        IntRange intRange = new IntRange(1, i);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(intRange, 10));
        Iterator<Integer> it = intRange.iterator();
        while (it.hasNext()) {
            arrayList.add(String.valueOf(((IntIterator) it).nextInt()));
        }
        return arrayList;
    }
}
