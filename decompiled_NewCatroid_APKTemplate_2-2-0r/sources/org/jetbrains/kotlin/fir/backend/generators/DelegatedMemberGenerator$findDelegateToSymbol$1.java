package org.jetbrains.kotlin.fir.backend.generators;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.Ref;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: Add missing generic type declarations: [S] */
/* JADX INFO: compiled from: DelegatedMemberGenerator.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u000e\b\u0000\u0010\u0002\u0018\u0001*\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010\u0004\u001a\u0002H\u0002H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, d2 = {SpecialNames.ANONYMOUS_STRING, "", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "candidateSymbol", "invoke", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;)V"}, k = 3, mv = {1, 9, 0}, xi = 48)
public final class DelegatedMemberGenerator$findDelegateToSymbol$1<S> extends Lambda implements Function1<S, Unit> {
    final /* synthetic */ Function2<S, Function1<? super S, ? extends ProcessorAction>, ProcessorAction> $processOverridden;
    final /* synthetic */ Ref.ObjectRef<S> $result;

    /* JADX INFO: Incorrect field signature: TS; */
    final /* synthetic */ FirCallableSymbol $unwrappedSymbol;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Incorrect types in method signature: (Lkotlin/jvm/internal/Ref$ObjectRef<TS;>;TS;Lkotlin/jvm/functions/Function2<-TS;-Lkotlin/jvm/functions/Function1<-TS;+Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;>;+Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;>;)V */
    public DelegatedMemberGenerator$findDelegateToSymbol$1(Ref.ObjectRef objectRef, FirCallableSymbol firCallableSymbol, Function2 function2) {
        super(1);
        this.$result = objectRef;
        this.$unwrappedSymbol = firCallableSymbol;
        this.$processOverridden = function2;
    }

    @Override // kotlin.jvm.functions.Function1
    public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
        invoke((FirCallableSymbol) obj);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Incorrect types in method signature: (TS;)V */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    public final void invoke(final FirCallableSymbol candidateSymbol) {
        Intrinsics.checkNotNullParameter(candidateSymbol, "candidateSymbol");
        if (this.$result.element != null) {
            return;
        }
        if (candidateSymbol == this.$unwrappedSymbol) {
            this.$result.element = candidateSymbol;
            return;
        }
        Function2<S, Function1<? super S, ? extends ProcessorAction>, ProcessorAction> function2 = this.$processOverridden;
        Intrinsics.needClassReification();
        final FirCallableSymbol firCallableSymbol = this.$unwrappedSymbol;
        final Ref.ObjectRef<S> objectRef = this.$result;
        function2.invoke(candidateSymbol, new Function1<S, ProcessorAction>() { // from class: org.jetbrains.kotlin.fir.backend.generators.DelegatedMemberGenerator$findDelegateToSymbol$1.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Incorrect types in method signature: (TS;Lkotlin/jvm/internal/Ref$ObjectRef<TS;>;TS;)V */
            {
                super(1);
            }

            /* JADX WARN: Incorrect types in method signature: (TS;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction; */
            /* JADX WARN: Type inference failed for: r0v2, types: [T, org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol] */
            @Override // kotlin.jvm.functions.Function1
            public final ProcessorAction invoke(FirCallableSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                if (it == firCallableSymbol) {
                    objectRef.element = candidateSymbol;
                    return ProcessorAction.STOP;
                }
                return ProcessorAction.NEXT;
            }
        });
    }
}
