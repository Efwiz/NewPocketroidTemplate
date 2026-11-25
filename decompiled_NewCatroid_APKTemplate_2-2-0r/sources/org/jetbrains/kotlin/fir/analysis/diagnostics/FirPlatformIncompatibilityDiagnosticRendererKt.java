package org.jetbrains.kotlin.fir.analysis.diagnostics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.transform.OutputKeys;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.collections.IntIterator;
import kotlin.jvm.functions.Function0;
import kotlin.ranges.IntRange;
import org.jetbrains.kotlin.fir.symbols.FirBasedSymbol;
import org.jetbrains.kotlin.resolve.multiplatform.ExpectActualCompatibility;

/* JADX INFO: compiled from: FirPlatformIncompatibilityDiagnosticRenderer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000<\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u001e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001aJ\u0010\u0002\u001a\u00020\u0003*\u00060\u0004j\u0002`\u00052(\u0010\u0006\u001a$\u0012\u0010\b\u0001\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\b\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\n0\u00072\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0002\u001a`\u0010\u000e\u001a\u00020\u0003*\u00060\u0004j\u0002`\u00052>\u0010\u000f\u001a:\u00126\u00124\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t\u0012&\u0012$\u0012\u0010\b\u0001\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\b\u0012\u000e\u0012\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\t0\n0\u00070\u00110\u00102\u0006\u0010\u000b\u001a\u00020\u00012\u0006\u0010\f\u001a\u00020\rH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0012"}, d2 = {"INDENTATION_UNIT", "", "renderIncompatibilityInformation", "", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "map", "", "Lorg/jetbrains/kotlin/resolve/multiplatform/ExpectActualCompatibility;", "Lorg/jetbrains/kotlin/fir/symbols/FirBasedSymbol;", "", OutputKeys.INDENT, "mode", "Lorg/jetbrains/kotlin/fir/analysis/diagnostics/MultiplatformDiagnosticRenderingMode;", "renderIncompatibleClassScopes", "unfulfilled", "", "Lkotlin/Pair;", "checkers"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirPlatformIncompatibilityDiagnosticRendererKt {
    /* JADX INFO: Access modifiers changed from: private */
    public static final void renderIncompatibilityInformation(final StringBuilder sb, Map<? extends ExpectActualCompatibility<? extends FirBasedSymbol<?>>, ? extends Collection<? extends FirBasedSymbol<?>>> map, final String str, final MultiplatformDiagnosticRenderingMode multiplatformDiagnosticRenderingMode) {
        String reason;
        for (Map.Entry<? extends ExpectActualCompatibility<? extends FirBasedSymbol<?>>, ? extends Collection<? extends FirBasedSymbol<?>>> entry : map.entrySet()) {
            ExpectActualCompatibility<? extends FirBasedSymbol<?>> key = entry.getKey();
            Collection<? extends FirBasedSymbol<?>> value = entry.getValue();
            sb.append(str);
            sb.append("The following declaration");
            sb.append(value.size() == 1 ? " is" : "s are");
            sb.append(" incompatible");
            ExpectActualCompatibility.Incompatible incompatible = key instanceof ExpectActualCompatibility.Incompatible ? (ExpectActualCompatibility.Incompatible) key : null;
            if (incompatible != null && (reason = incompatible.getReason()) != null) {
                sb.append(" because " + reason);
            }
            sb.append(":");
            Collection<? extends FirBasedSymbol<?>> collection = value;
            ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(collection, 10));
            Iterator<T> it = collection.iterator();
            while (it.hasNext()) {
                final FirBasedSymbol firBasedSymbol = (FirBasedSymbol) it.next();
                arrayList.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirPlatformIncompatibilityDiagnosticRendererKt$renderIncompatibilityInformation$2$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public /* bridge */ /* synthetic */ Unit invoke() {
                        invoke2();
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2() {
                        multiplatformDiagnosticRenderingMode.renderSymbol(sb, firBasedSymbol, str);
                    }
                });
            }
            multiplatformDiagnosticRenderingMode.renderList(sb, arrayList);
            if (key instanceof ExpectActualCompatibility.Incompatible.ClassScopes) {
                sb.append(str);
                sb.append("No actual members are found for expected members listed below:");
                multiplatformDiagnosticRenderingMode.newLine(sb);
                renderIncompatibleClassScopes(sb, ((ExpectActualCompatibility.Incompatible.ClassScopes) key).getUnfulfilled(), str, multiplatformDiagnosticRenderingMode);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final void renderIncompatibleClassScopes(final StringBuilder sb, final List<? extends Pair<? extends FirBasedSymbol<?>, ? extends Map<? extends ExpectActualCompatibility<? extends FirBasedSymbol<?>>, ? extends Collection<? extends FirBasedSymbol<?>>>>> list, final String str, final MultiplatformDiagnosticRenderingMode multiplatformDiagnosticRenderingMode) {
        IntRange indices = CollectionsKt.getIndices(list);
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(indices, 10));
        Iterator<Integer> it = indices.iterator();
        while (it.hasNext()) {
            final int iNextInt = ((IntIterator) it).nextInt();
            arrayList.add(new Function0<Unit>() { // from class: org.jetbrains.kotlin.fir.analysis.diagnostics.FirPlatformIncompatibilityDiagnosticRendererKt$renderIncompatibleClassScopes$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public /* bridge */ /* synthetic */ Unit invoke() {
                    invoke2();
                    return Unit.INSTANCE;
                }

                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2() {
                    Pair<FirBasedSymbol<?>, Map<? extends ExpectActualCompatibility<FirBasedSymbol<?>>, Collection<FirBasedSymbol<?>>>> pair = list.get(iNextInt);
                    FirBasedSymbol<?> firBasedSymbolComponent1 = pair.component1();
                    Map<? extends ExpectActualCompatibility<FirBasedSymbol<?>>, Collection<FirBasedSymbol<?>>> mapComponent2 = pair.component2();
                    multiplatformDiagnosticRenderingMode.renderSymbol(sb, firBasedSymbolComponent1, str);
                    if (!mapComponent2.isEmpty()) {
                        multiplatformDiagnosticRenderingMode.newLine(sb);
                        FirPlatformIncompatibilityDiagnosticRendererKt.renderIncompatibilityInformation(sb, mapComponent2, str + "    ", multiplatformDiagnosticRenderingMode);
                    }
                    if (iNextInt != CollectionsKt.getLastIndex(list)) {
                        multiplatformDiagnosticRenderingMode.newLine(sb);
                    }
                }
            });
        }
        multiplatformDiagnosticRenderingMode.renderList(sb, arrayList);
    }
}
