package org.jetbrains.kotlin.fir.java.scopes;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.fir.ClassMembersKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirCallableDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationOrigin;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.java.enhancement.FirSignatureEnhancement;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.FirTypeScopeKt;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirCallableSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JavaClassMembersEnhancementScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u009e\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0088\u0001\u0010\u0010\u001a\u00020\u0011\"\f\b\u0000\u0010\u0012*\u0006\u0012\u0002\b\u00030\u00132\u0006\u0010\u0014\u001a\u0002H\u00122\u0018\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00110\u00162\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u0002H\u00120\u001825\u0010\u0019\u001a1\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u0002H\u0012\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u0002H\u0012\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00110\u0016\u0012\u0004\u0012\u00020\u00110\u001a¢\u0006\u0002\b\u001bH\u0002¢\u0006\u0002\u0010\u001cJ\u000e\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0016J\u000e\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001f0\u001eH\u0016J\u0010\u0010!\u001a\u00020\"2\u0006\u0010#\u001a\u00020\u001fH\u0016J.\u0010$\u001a\u00020%2\u0006\u0010#\u001a\u00020\u001f2\u001c\u0010\u0015\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030&\u0012\u0004\u0012\u00020'\u0012\u0004\u0012\u00020%0\u0016H\u0016J\u001c\u0010(\u001a\u00020%2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020*\u0012\u0004\u0012\u00020%0)H\u0016J*\u0010+\u001a\u00020\u00112\u0006\u0010,\u001a\u00020\u000b2\u0018\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00110\u0016H\u0016J*\u0010-\u001a\u00020\u00112\u0006\u0010.\u001a\u00020\r2\u0018\u0010\u0015\u001a\u0014\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u00110\u0016H\u0016J$\u0010/\u001a\u00020%2\u0006\u0010#\u001a\u00020\u001f2\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020%0)H\u0016J(\u00100\u001a\u00020%2\u0006\u0010#\u001a\u00020\u001f2\u0016\u0010\u0015\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u000301\u0012\u0004\u0012\u00020%0)H\u0016J\b\u00102\u001a\u000203H\u0016J\u0012\u00104\u001a\b\u0012\u0004\u0012\u00020605*\u000206H\u0002R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u000b0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\r\u0012\u0004\u0012\u00020\r0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u000e\u001a\u00020\u000fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000¨\u00067"}, d2 = {"Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassMembersEnhancementScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "useSiteMemberScope", "Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassUseSiteMemberScope;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassUseSiteMemberScope;)V", "enhancedToOriginalFunctions", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "enhancedToOriginalProperties", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "signatureEnhancement", "Lorg/jetbrains/kotlin/fir/java/enhancement/FirSignatureEnhancement;", "doProcessDirectOverriddenCallables", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", ExifInterface.LATITUDE_SOUTH, "Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;", "callableSymbol", "processor", "Lkotlin/Function2;", "enhancedToOriginalMap", "", "processDirectOverriddenCallables", "Lkotlin/Function3;", "Lkotlin/ExtensionFunctionType;", "(Lorg/jetbrains/kotlin/fir/symbols/impl/FirCallableSymbol;Lkotlin/jvm/functions/Function2;Ljava/util/Map;Lkotlin/jvm/functions/Function3;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "getCallableNames", "", "Lorg/jetbrains/kotlin/name/Name;", "getClassifierNames", "mayContainName", "", "name", "processClassifiersByNameWithSubstitution", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processDeclaredConstructors", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "processDirectOverriddenFunctionsWithBaseScope", "functionSymbol", "processDirectOverriddenPropertiesWithBaseScope", "propertySymbol", "processFunctionsByName", "processPropertiesByName", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "toString", "", "overriddenMembers", "", "Lorg/jetbrains/kotlin/fir/declarations/FirCallableDeclaration;", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaClassMembersEnhancementScope extends FirTypeScope {
    private final Map<FirNamedFunctionSymbol, FirNamedFunctionSymbol> enhancedToOriginalFunctions;
    private final Map<FirPropertySymbol, FirPropertySymbol> enhancedToOriginalProperties;
    private final FirRegularClassSymbol owner;
    private final FirSignatureEnhancement signatureEnhancement;
    private final JavaClassUseSiteMemberScope useSiteMemberScope;

    /* JADX WARN: Multi-variable type inference failed */
    public JavaClassMembersEnhancementScope(FirSession session, FirRegularClassSymbol owner, JavaClassUseSiteMemberScope useSiteMemberScope) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(useSiteMemberScope, "useSiteMemberScope");
        this.owner = owner;
        this.useSiteMemberScope = useSiteMemberScope;
        this.enhancedToOriginalFunctions = new LinkedHashMap();
        this.enhancedToOriginalProperties = new LinkedHashMap();
        this.signatureEnhancement = new FirSignatureEnhancement((FirRegularClass) owner.getFir(), session, new Function1<FirSimpleFunction, List<? extends FirCallableDeclaration>>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassMembersEnhancementScope$signatureEnhancement$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public final List<FirCallableDeclaration> invoke(FirSimpleFunction $receiver) {
                Intrinsics.checkNotNullParameter($receiver, "$this$$receiver");
                return this.this$0.overriddenMembers($receiver);
            }
        });
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(final Name name2, final Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.useSiteMemberScope.processPropertiesByName(name2, new Function1<FirVariableSymbol<?>, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassMembersEnhancementScope.processPropertiesByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirVariableSymbol<?> firVariableSymbol) {
                invoke2(firVariableSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirVariableSymbol<?> original) {
                Intrinsics.checkNotNullParameter(original, "original");
                FirVariableSymbol<?> firVariableSymbolEnhancedProperty = JavaClassMembersEnhancementScope.this.signatureEnhancement.enhancedProperty(original, name2);
                if ((original instanceof FirPropertySymbol) && (firVariableSymbolEnhancedProperty instanceof FirPropertySymbol)) {
                    JavaClassMembersEnhancementScope.this.enhancedToOriginalProperties.put(firVariableSymbolEnhancedProperty, original);
                }
                processor.invoke(firVariableSymbolEnhancedProperty);
            }
        });
        super.processPropertiesByName(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(final Name name2, final Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.useSiteMemberScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassMembersEnhancementScope.processFunctionsByName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(FirNamedFunctionSymbol firNamedFunctionSymbol) {
                invoke2(firNamedFunctionSymbol);
                return Unit.INSTANCE;
            }

            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(FirNamedFunctionSymbol original) {
                FirNamedFunctionSymbol symbol;
                Intrinsics.checkNotNullParameter(original, "original");
                FirNamedFunctionSymbol firNamedFunctionSymbolEnhancedFunction = JavaClassMembersEnhancementScope.this.signatureEnhancement.enhancedFunction(original, name2);
                Object fir = firNamedFunctionSymbolEnhancedFunction.getFir();
                FirSimpleFunction firSimpleFunction = fir instanceof FirSimpleFunction ? (FirSimpleFunction) fir : null;
                if (firSimpleFunction != null && (symbol = firSimpleFunction.getSymbol()) != null) {
                    firNamedFunctionSymbolEnhancedFunction = symbol;
                }
                if (firNamedFunctionSymbolEnhancedFunction instanceof FirNamedFunctionSymbol) {
                    JavaClassMembersEnhancementScope.this.enhancedToOriginalFunctions.put(firNamedFunctionSymbolEnhancedFunction, original);
                    processor.invoke((FirNamedFunctionSymbol) firNamedFunctionSymbolEnhancedFunction);
                }
            }
        });
        super.processFunctionsByName(name2, processor);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Multi-variable type inference failed */
    public final List<FirCallableDeclaration> overriddenMembers(FirCallableDeclaration firCallableDeclaration) {
        List directOverriddenProperties$default;
        FirCallableSymbol<? extends FirCallableDeclaration> symbol = firCallableDeclaration.getSymbol();
        if (symbol instanceof FirNamedFunctionSymbol) {
            directOverriddenProperties$default = FirTypeScopeKt.getDirectOverriddenMembers$default(this.useSiteMemberScope, symbol, false, 2, null);
        } else {
            directOverriddenProperties$default = symbol instanceof FirPropertySymbol ? FirTypeScopeKt.getDirectOverriddenProperties$default(this.useSiteMemberScope, (FirPropertySymbol) symbol, false, 2, null) : CollectionsKt.emptyList();
        }
        List list = directOverriddenProperties$default;
        ArrayList arrayList = new ArrayList(CollectionsKt.collectionSizeOrDefault(list, 10));
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add((FirCallableDeclaration) ((FirCallableSymbol) it.next()).getFir());
        }
        return arrayList;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.useSiteMemberScope.processClassifiersByNameWithSubstitution(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(final Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.useSiteMemberScope.processDeclaredConstructors(new Function1<FirConstructorSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaClassMembersEnhancementScope.processDeclaredConstructors.1
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
            public final void invoke2(FirConstructorSymbol original) {
                Intrinsics.checkNotNullParameter(original, "original");
                FirFunctionSymbol<?> firFunctionSymbolEnhancedFunction = JavaClassMembersEnhancementScope.this.signatureEnhancement.enhancedFunction(original, null);
                Function1<FirConstructorSymbol, Unit> function1 = processor;
                Intrinsics.checkNotNull(firFunctionSymbolEnhancedFunction, "null cannot be cast to non-null type org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol");
                function1.invoke((FirConstructorSymbol) firFunctionSymbolEnhancedFunction);
            }
        });
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.java.scopes.JavaClassMembersEnhancementScope$processDirectOverriddenFunctionsWithBaseScope$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: JavaClassMembersEnhancementScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C44941 extends FunctionReference implements Function3<FirTypeScope, FirNamedFunctionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction>, ProcessorAction> {
        public static final C44941 INSTANCE = new C44941();

        C44941() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "processDirectOverriddenFunctionsWithBaseScope(Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "processDirectOverriddenFunctionsWithBaseScope";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FirTypeScope.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public final ProcessorAction invoke(FirTypeScope p0, FirNamedFunctionSymbol p1, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            return p0.processDirectOverriddenFunctionsWithBaseScope(p1, p2);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenFunctionsWithBaseScope(FirNamedFunctionSymbol functionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return doProcessDirectOverriddenCallables(functionSymbol, processor, this.enhancedToOriginalFunctions, C44941.INSTANCE);
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.fir.java.scopes.JavaClassMembersEnhancementScope$processDirectOverriddenPropertiesWithBaseScope$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: JavaClassMembersEnhancementScope.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C44951 extends FunctionReference implements Function3<FirTypeScope, FirPropertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction>, ProcessorAction> {
        public static final C44951 INSTANCE = new C44951();

        C44951() {
            super(3);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "processDirectOverriddenPropertiesWithBaseScope(Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "processDirectOverriddenPropertiesWithBaseScope";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(FirTypeScope.class);
        }

        @Override // kotlin.jvm.functions.Function3
        public final ProcessorAction invoke(FirTypeScope p0, FirPropertySymbol p1, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> p2) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            Intrinsics.checkNotNullParameter(p2, "p2");
            return p0.processDirectOverriddenPropertiesWithBaseScope(p1, p2);
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenPropertiesWithBaseScope(FirPropertySymbol propertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return doProcessDirectOverriddenCallables(propertySymbol, processor, this.enhancedToOriginalProperties, C44951.INSTANCE);
    }

    private final <S extends FirCallableSymbol<?>> ProcessorAction doProcessDirectOverriddenCallables(S callableSymbol, Function2<? super S, ? super FirTypeScope, ? extends ProcessorAction> processor, Map<S, ? extends S> enhancedToOriginalMap, Function3<? super FirTypeScope, ? super S, ? super Function2<? super S, ? super FirTypeScope, ? extends ProcessorAction>, ? extends ProcessorAction> processDirectOverriddenCallables) {
        if (Intrinsics.areEqual(callableSymbol.getOrigin(), FirDeclarationOrigin.RenamedForOverride.INSTANCE)) {
            FirCallableDeclaration initialSignatureAttr = ClassMembersKt.getInitialSignatureAttr((FirCallableDeclaration) callableSymbol.getFir());
            FirCallableSymbol<? extends FirCallableDeclaration> symbol = initialSignatureAttr != null ? initialSignatureAttr.getSymbol() : null;
            FirCallableSymbol<? extends FirCallableDeclaration> firCallableSymbol = symbol instanceof FirCallableSymbol ? symbol : null;
            if (firCallableSymbol != null) {
                callableSymbol = firCallableSymbol;
            }
        }
        S s = enhancedToOriginalMap.get(callableSymbol);
        return s == null ? ProcessorAction.NONE : processDirectOverriddenCallables.invoke(this.useSiteMemberScope, s, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return this.useSiteMemberScope.getCallableNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return this.useSiteMemberScope.getClassifierNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public boolean mayContainName(Name name2) {
        Intrinsics.checkNotNullParameter(name2, "name");
        return this.useSiteMemberScope.mayContainName(name2);
    }

    public String toString() {
        return "Java enhancement scope for " + this.owner.getClassId();
    }
}
