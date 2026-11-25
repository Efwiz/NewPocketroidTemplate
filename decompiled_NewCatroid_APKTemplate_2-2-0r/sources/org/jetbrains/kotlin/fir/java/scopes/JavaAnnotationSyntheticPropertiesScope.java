package org.jetbrains.kotlin.fir.java.scopes;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.fir.FirModuleData;
import org.jetbrains.kotlin.fir.FirModuleDataKt;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirResolveStateKt;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticProperty;
import org.jetbrains.kotlin.fir.declarations.synthetic.FirSyntheticPropertyAccessor;
import org.jetbrains.kotlin.fir.java.symbols.FirJavaOverriddenSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.resolve.substitution.ConeSubstitutor;
import org.jetbrains.kotlin.fir.scopes.FirTypeScope;
import org.jetbrains.kotlin.fir.scopes.ProcessorAction;
import org.jetbrains.kotlin.fir.symbols.impl.FirClassifierSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirConstructorSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirNamedFunctionSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirRegularClassSymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirSyntheticPropertySymbol;
import org.jetbrains.kotlin.fir.symbols.impl.FirVariableSymbol;
import org.jetbrains.kotlin.name.CallableId;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JavaAnnotationSyntheticPropertiesScope.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000t\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010%\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u000e\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J\u000e\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\r0\fH\u0016J.\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\r2\u001c\u0010\u0017\u001a\u0018\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0019\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u00020\u00150\u0018H\u0016J\u001c\u0010\u001b\u001a\u00020\u00152\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u001d\u0012\u0004\u0012\u00020\u00150\u001cH\u0016J*\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020\u00102\u0018\u0010\u0017\u001a\u0014\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u001f0\u0018H\u0016J*\u0010!\u001a\u00020\u001f2\u0006\u0010\"\u001a\u00020#2\u0018\u0010\u0017\u001a\u0014\u0012\u0004\u0012\u00020#\u0012\u0004\u0012\u00020\u0001\u0012\u0004\u0012\u00020\u001f0\u0018H\u0016J$\u0010$\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\r2\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00020\u0010\u0012\u0004\u0012\u00020\u00150\u001cH\u0016J(\u0010%\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\r2\u0016\u0010\u0017\u001a\u0012\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u0011\u0012\u0004\u0012\u00020\u00150\u001cH\u0016J\b\u0010&\u001a\u00020'H\u0016R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\r0\fX\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u001e\u0010\u000e\u001a\u0012\u0012\u0004\u0012\u00020\u0010\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00110\u000fX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006("}, d2 = {"Lorg/jetbrains/kotlin/fir/java/scopes/JavaAnnotationSyntheticPropertiesScope;", "Lorg/jetbrains/kotlin/fir/scopes/FirTypeScope;", "session", "Lorg/jetbrains/kotlin/fir/FirSession;", "owner", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;", "delegateScope", "Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassMembersEnhancementScope;", "(Lorg/jetbrains/kotlin/fir/FirSession;Lorg/jetbrains/kotlin/fir/symbols/impl/FirRegularClassSymbol;Lorg/jetbrains/kotlin/fir/java/scopes/JavaClassMembersEnhancementScope;)V", "classId", "Lorg/jetbrains/kotlin/name/ClassId;", "names", "", "Lorg/jetbrains/kotlin/name/Name;", "syntheticPropertiesCache", "", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirNamedFunctionSymbol;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirVariableSymbol;", "getCallableNames", "getClassifierNames", "processClassifiersByNameWithSubstitution", "", "name", "processor", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirClassifierSymbol;", "Lorg/jetbrains/kotlin/fir/resolve/substitution/ConeSubstitutor;", "processDeclaredConstructors", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirConstructorSymbol;", "processDirectOverriddenFunctionsWithBaseScope", "Lorg/jetbrains/kotlin/fir/scopes/ProcessorAction;", "functionSymbol", "processDirectOverriddenPropertiesWithBaseScope", "propertySymbol", "Lorg/jetbrains/kotlin/fir/symbols/impl/FirPropertySymbol;", "processFunctionsByName", "processPropertiesByName", "toString", "", SuffixConstants.EXTENSION_java}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JavaAnnotationSyntheticPropertiesScope extends FirTypeScope {
    private final ClassId classId;
    private final JavaClassMembersEnhancementScope delegateScope;
    private final Set<Name> names;
    private final FirSession session;
    private final Map<FirNamedFunctionSymbol, FirVariableSymbol<?>> syntheticPropertiesCache;

    /* JADX WARN: Multi-variable type inference failed */
    public JavaAnnotationSyntheticPropertiesScope(FirSession session, FirRegularClassSymbol owner, JavaClassMembersEnhancementScope delegateScope) {
        Intrinsics.checkNotNullParameter(session, "session");
        Intrinsics.checkNotNullParameter(owner, "owner");
        Intrinsics.checkNotNullParameter(delegateScope, "delegateScope");
        this.session = session;
        this.delegateScope = delegateScope;
        this.classId = owner.getClassId();
        List<FirDeclaration> declarations = ((FirRegularClass) owner.getFir()).getDeclarations();
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        for (FirDeclaration firDeclaration : declarations) {
            FirSimpleFunction firSimpleFunction = firDeclaration instanceof FirSimpleFunction ? (FirSimpleFunction) firDeclaration : null;
            Name name2 = firSimpleFunction != null ? firSimpleFunction.getName() : null;
            if (name2 != null) {
                linkedHashSet.add(name2);
            }
        }
        this.names = linkedHashSet;
        this.syntheticPropertiesCache = new LinkedHashMap();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processDeclaredConstructors(Function1<? super FirConstructorSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.delegateScope.processDeclaredConstructors(processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processFunctionsByName(Name name2, Function1<? super FirNamedFunctionSymbol, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (this.names.contains(name2)) {
            return;
        }
        this.delegateScope.processFunctionsByName(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processPropertiesByName(final Name name2, final Function1<? super FirVariableSymbol<?>, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        if (this.names.contains(name2)) {
            this.delegateScope.processFunctionsByName(name2, new Function1<FirNamedFunctionSymbol, Unit>() { // from class: org.jetbrains.kotlin.fir.java.scopes.JavaAnnotationSyntheticPropertiesScope.processPropertiesByName.1
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

                /* JADX WARN: Multi-variable type inference failed */
                /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                public final void invoke2(FirNamedFunctionSymbol functionSymbol) {
                    Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
                    FirSimpleFunction firSimpleFunction = (FirSimpleFunction) functionSymbol.getFir();
                    Map map = JavaAnnotationSyntheticPropertiesScope.this.syntheticPropertiesCache;
                    JavaAnnotationSyntheticPropertiesScope javaAnnotationSyntheticPropertiesScope = JavaAnnotationSyntheticPropertiesScope.this;
                    Name name3 = name2;
                    Object obj = map.get(functionSymbol);
                    if (obj == null) {
                        CallableId callableId = new CallableId(javaAnnotationSyntheticPropertiesScope.classId, name3);
                        Object firJavaOverriddenSyntheticPropertySymbol = new FirJavaOverriddenSyntheticPropertySymbol(callableId, callableId);
                        FirSyntheticPropertyAccessor firSyntheticPropertyAccessor = new FirSyntheticPropertyAccessor(firSimpleFunction, true, (FirPropertySymbol) firJavaOverriddenSyntheticPropertySymbol);
                        FirModuleData nullableModuleData = FirModuleDataKt.getNullableModuleData(javaAnnotationSyntheticPropertiesScope.session);
                        if (nullableModuleData == null) {
                            nullableModuleData = firSimpleFunction.getModuleData();
                        }
                        FirDeclarationStatus status = firSimpleFunction.getStatus();
                        new FirSyntheticProperty(nullableModuleData, name3, false, (FirSyntheticPropertySymbol) firJavaOverriddenSyntheticPropertySymbol, UtilsKt.copy(status, (2097147 & 1) != 0 ? status.getVisibility() : null, (2097147 & 2) != 0 ? status.getModality() : Modality.FINAL, (2097147 & 4) != 0 ? status.isExpect() : false, (2097147 & 8) != 0 ? status.isActual() : false, (2097147 & 16) != 0 ? status.isOverride() : false, (2097147 & 32) != 0 ? status.isOperator() : false, (2097147 & 64) != 0 ? status.isInfix() : false, (2097147 & 128) != 0 ? status.isInline() : false, (2097147 & 256) != 0 ? status.isTailRec() : false, (2097147 & 512) != 0 ? status.isExternal() : false, (2097147 & 1024) != 0 ? status.isConst() : false, (2097147 & 2048) != 0 ? status.isLateInit() : false, (2097147 & 4096) != 0 ? status.isInner() : false, (2097147 & 8192) != 0 ? status.isCompanion() : false, (2097147 & 16384) != 0 ? status.isData() : false, (2097147 & 32768) != 0 ? status.isSuspend() : false, (2097147 & 65536) != 0 ? status.isStatic() : false, (2097147 & 131072) != 0 ? status.isFromSealedClass() : false, (2097147 & 262144) != 0 ? status.isFromEnumClass() : false, (2097147 & 524288) != 0 ? status.isFun() : false, (2097147 & 1048576) != 0 ? status.getHasStableParameterNames() : false), FirResolveStateKt.getResolvePhase(firSimpleFunction), firSyntheticPropertyAccessor, null, null, null, 896, null);
                        obj = (FirVariableSymbol) firJavaOverriddenSyntheticPropertySymbol;
                        map.put(functionSymbol, obj);
                    }
                    processor.invoke((FirVariableSymbol) obj);
                }
            });
        }
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirScope
    public void processClassifiersByNameWithSubstitution(Name name2, Function2<? super FirClassifierSymbol<?>, ? super ConeSubstitutor, Unit> processor) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(processor, "processor");
        this.delegateScope.processClassifiersByNameWithSubstitution(name2, processor);
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenFunctionsWithBaseScope(FirNamedFunctionSymbol functionSymbol, Function2<? super FirNamedFunctionSymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(functionSymbol, "functionSymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return ProcessorAction.NONE;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirTypeScope
    public ProcessorAction processDirectOverriddenPropertiesWithBaseScope(FirPropertySymbol propertySymbol, Function2<? super FirPropertySymbol, ? super FirTypeScope, ? extends ProcessorAction> processor) {
        Intrinsics.checkNotNullParameter(propertySymbol, "propertySymbol");
        Intrinsics.checkNotNullParameter(processor, "processor");
        return ProcessorAction.NONE;
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getCallableNames() {
        return this.delegateScope.getCallableNames();
    }

    @Override // org.jetbrains.kotlin.fir.scopes.FirContainingNamesAwareScope
    public Set<Name> getClassifierNames() {
        return this.delegateScope.getClassifierNames();
    }

    public String toString() {
        return "Java annotation synthetic properties scope for " + this.classId;
    }
}
