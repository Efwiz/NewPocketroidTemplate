package org.jetbrains.kotlin.fir.scopes.impl;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirScope;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirDefaultStarImportingScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0004¢\u0006\u0002\u0010\u0006J.\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u001c\u0010\u000e\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0010\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u000b0\u000fH\u0016J\u001c\u0010\u0012\u001a\u00020\u000b2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0014\u0012\u0004\u0012\u00020\u000b0\u0013H\u0016J$\u0010\u0015\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0016\u0012\u0004\u0012\u00020\u000b0\u0013H\u0016J(\u0010\u0017\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0016\u0010\u000e\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0018\u0012\u0004\u0012\u00020\u000b0\u0013H\u0016Jc\u0010\u0019\u001a\u00020\u000b\"\f\b\u0000\u0010\u001a*\u0006\u0012\u0002\b\u00030\u001b2\u0006\u0010\f\u001a\u00020\r2/\u0010\u001c\u001a+\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\r\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u00020\u000b0\u0013\u0012\u0004\u0012\u00020\u000b0\u001d¢\u0006\u0002\b\u001e2\u0012\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u0002H\u001a\u0012\u0004\u0012\u00020\u000b0\u0013H\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0005\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\b¨\u0006\u001f"}, d2 = {"Lorg/jetbrains/kotlin/fir/scopes/impl/FirDefaultStarImportingScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirScope;", "Lorg/jetbrains/kotlin/fir/scopes/impl/DefaultStarImportingScopeMarker;", "first", "Lorg/jetbrains/kotlin/fir/scopes/impl/FirSingleLevelDefaultStarImportingScope;", "second", "(Lorg/jetbrains/kotlin/fir/scopes/impl/FirSingleLevelDefaultStarImportingScope;Lorg/jetbrains/kotlin/fir/scopes/impl/FirSingleLevelDefaultStarImportingScope;)V", "getFirst", "()Lorg/jetbrains/kotlin/fir/scopes/impl/FirSingleLevelDefaultStarImportingScope;", "getSecond", "processClassifiersByNameWithSubstitution", "", "name", "Lorg/jetbrains/kotlin/name/Name;", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processDeclaredConstructors", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "processFunctionsByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "processSymbolsByName", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "processingFactory", "Lkotlin/Function3;", "Lkotlin/ExtensionFunctionType;", "providers"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class FirDefaultStarImportingScope extends FirScope implements DefaultStarImportingScopeMarker {
    private final FirSingleLevelDefaultStarImportingScope first;
    private final FirSingleLevelDefaultStarImportingScope second;

    public final FirSingleLevelDefaultStarImportingScope getFirst() {
        return this.first;
    }

    public final FirSingleLevelDefaultStarImportingScope getSecond() {
        return this.second;
    }

    public FirDefaultStarImportingScope(FirSingleLevelDefaultStarImportingScope first, FirSingleLevelDefaultStarImportingScope second) {
        Intrinsics.checkNotNullParameter(first, "first");
        Intrinsics.checkNotNullParameter(second, "second");
        this.first = first;
        this.second = second;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, final Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        this.first.processClassifiersByNameWithSubstitution(name2, new Function2<FirClassifierSymbol<?>, ConeSubstitutor, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDefaultStarImportingScope.processClassifiersByNameWithSubstitution.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public /* bridge */ /* synthetic */ Unit invoke(FirClassifierSymbol<?> firClassifierSymbol, ConeSubstitutor coneSubstitutor) {
                invoke2(firClassifierSymbol, coneSubstitutor);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirClassifierSymbol<?> symbol, ConeSubstitutor substitutor) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                Intrinsics.checkNotNullParameter(substitutor, "substitutor");
                booleanRef.element = true;
                processor.invoke(symbol, substitutor);
            }
        });
        if (booleanRef.element) {
            return;
        }
        this.second.processClassifiersByNameWithSubstitution(name2, processor);
    }

    private final <S extends FirCallableSymbol<?>> void processSymbolsByName(Name name2, Function3<? super FirScope, ? super Name, ? super Function1<? super S, Unit>, Unit> processingFactory, final Function1<? super S, Unit> processor) {
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        processingFactory.invoke(this.first, name2, new Function1<S, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDefaultStarImportingScope.processSymbolsByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(Object obj) {
                invoke((FirCallableSymbol) obj);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Incorrect types in method signature: (TS;)V */
            public final void invoke(FirCallableSymbol it) {
                Intrinsics.checkNotNullParameter(it, "it");
                booleanRef.element = true;
                processor.invoke(it);
            }
        });
        if (booleanRef.element) {
            return;
        }
        processingFactory.invoke(this.second, name2, processor);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.impl.FirDefaultStarImportingScope$processFunctionsByName$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirDefaultStarImportingScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C46041 extends FunctionReference implements Function3<FirScope, Name, Function1<? super FirNamedFunctionSymbol, ? extends Unit>, Unit> {
        public static final C46041 INSTANCE = new C46041();

        C46041() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "processFunctionsByName(Lorg/jetbrains/kotlin/name/Name;Lkotlin/jvm/functions/Function1;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "processFunctionsByName";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FirScope.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(FirScope firScope, Name name2, Function1<? super FirNamedFunctionSymbol, ? extends Unit> function1) {
            invoke2(firScope, name2, (Function1<? super FirNamedFunctionSymbol, Unit>) function1);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(FirScope p0, Name p1, Function1<? super FirNamedFunctionSymbol, Unit> p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            p0.processFunctionsByName(p1, p2);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        processSymbolsByName(name2, C46041.INSTANCE, processor);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.scopes.impl.FirDefaultStarImportingScope$processPropertiesByName$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: FirDefaultStarImportingScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C46051 extends FunctionReference implements Function3<FirScope, Name, Function1<? super FirVariableSymbol<?>, ? extends Unit>, Unit> {
        public static final C46051 INSTANCE = new C46051();

        C46051() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "processPropertiesByName(Lorg/jetbrains/kotlin/name/Name;Lkotlin/jvm/functions/Function1;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "processPropertiesByName";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FirScope.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public /* bridge */ /* synthetic */ Unit invoke(FirScope firScope, Name name2, Function1<? super FirVariableSymbol<?>, ? extends Unit> function1) {
            invoke2(firScope, name2, (Function1<? super FirVariableSymbol<?>, Unit>) function1);
            return Unit.INSTANCE;
        }

        /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
        public final void invoke2(FirScope p0, Name p1, Function1<? super FirVariableSymbol<?>, Unit> p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            p0.processPropertiesByName(p1, p2);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(Name name2, Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        processSymbolsByName(name2, C46051.INSTANCE, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(final Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        final Ref.BooleanRef booleanRef = new Ref.BooleanRef();
        this.first.processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.scopes.impl.FirDefaultStarImportingScope.processDeclaredConstructors.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirConstructorSymbol firConstructorSymbol) {
                invoke2(firConstructorSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirConstructorSymbol symbol) {
                Intrinsics.checkNotNullParameter(symbol, "symbol");
                booleanRef.element = true;
                processor.invoke(symbol);
            }
        });
        if (booleanRef.element) {
            return;
        }
        this.second.processDeclaredConstructors(processor);
    }
}
