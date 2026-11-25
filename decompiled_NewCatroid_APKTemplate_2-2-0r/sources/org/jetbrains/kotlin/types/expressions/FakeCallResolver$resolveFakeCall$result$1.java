package org.jetbrains.kotlin.types.expressions;

import java.util.Collection;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.diagnostics.Diagnostic;
import org.jetbrains.kotlin.diagnostics.Severity;
import org.jetbrains.kotlin.name.SpecialNames;
import org.jetbrains.kotlin.psi.KtSimpleNameExpression;
import org.jetbrains.kotlin.resolve.TemporaryBindingTrace;
import org.jetbrains.kotlin.resolve.TraceEntryFilter;
import org.jetbrains.kotlin.util.slicedMap.WritableSlice;

/* JADX INFO: compiled from: FakeCallResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n¢\u0006\u0002\b\u0004"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", "fake", "Lorg/jetbrains/kotlin/psi/KtSimpleNameExpression;", "invoke"}, k = 3, mv = {1, 9, 0}, xi = 48)
final class FakeCallResolver$resolveFakeCall$result$1 extends Lambda implements Function1<KtSimpleNameExpression, Unit> {
    final /* synthetic */ TemporaryBindingTrace $fakeTrace;
    final /* synthetic */ Ref.BooleanRef $reportIsMissingError;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    FakeCallResolver$resolveFakeCall$result$1(Ref.BooleanRef booleanRef, TemporaryBindingTrace temporaryBindingTrace) {
        super(1);
        this.$reportIsMissingError = booleanRef;
        this.$fakeTrace = temporaryBindingTrace;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(KtSimpleNameExpression ktSimpleNameExpression) {
        invoke2(ktSimpleNameExpression);
        return Unit.INSTANCE;
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final void invoke2(final KtSimpleNameExpression fake) {
        Intrinsics.checkNotNullParameter(fake, "fake");
        Ref.BooleanRef booleanRef = this.$reportIsMissingError;
        Collection<Diagnostic> collectionForElement = this.$fakeTrace.getContext().getDiagnostics().noSuppression().forElement(fake);
        boolean z = false;
        if (!(collectionForElement instanceof Collection) || !collectionForElement.isEmpty()) {
            Iterator<T> it = collectionForElement.iterator();
            while (it.hasNext()) {
                if (((Diagnostic) it.next()).getSeverity() == Severity.ERROR) {
                    z = true;
                    break;
                }
            }
        }
        booleanRef.element = z;
        this.$fakeTrace.commit(new TraceEntryFilter() { // from class: org.jetbrains.kotlin.types.expressions.FakeCallResolver$resolveFakeCall$result$1$$ExternalSyntheticLambda0
            @Override // org.jetbrains.kotlin.resolve.TraceEntryFilter
            public final boolean accept(WritableSlice writableSlice, Object obj) {
                return FakeCallResolver$resolveFakeCall$result$1.invoke$lambda$1(fake, writableSlice, obj);
            }
        }, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final boolean invoke$lambda$1(KtSimpleNameExpression fake, WritableSlice writableSlice, Object obj) {
        Intrinsics.checkNotNullParameter(fake, "$fake");
        return !Intrinsics.areEqual(obj, fake);
    }
}
