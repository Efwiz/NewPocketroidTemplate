package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import java.util.Iterator;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.FirSession;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirBackingField;
import org.jetbrains.kotlin.fir.declarations.FirClass;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclaration;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTowerDataContext;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.declarations.ImplicitReceiverUtilsKt;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.FirTowerDataMode;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirResolveContextCollector;
import org.jetbrains.kotlin.fir.scopes.impl.FirLocalScope;
import org.jetbrains.kotlin.fir.scopes.impl.FirMemberTypeParameterScope;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.SpecialNames;

/* JADX INFO: compiled from: FirAnnotationArgumentsMappingTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u0088\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000f2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u00122\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u0014\u001a\u00020\u00152\u0006\u0010\u0016\u001a\u00020\u00152\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u00182\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001b2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u001d\u001a\u00020\u001e2\u0006\u0010\u001f\u001a\u00020\u001e2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010 \u001a\u00020\u000b2\u0006\u0010!\u001a\u00020\"2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020'2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010,\u001a\u00020\u000b2\u0006\u0010-\u001a\u00020.2\u0006\u0010\b\u001a\u00020\tH\u0016J\u001e\u0010/\u001a\u00020\"2\u0006\u0010!\u001a\u00020\"2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\"01H\u0016¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirDeclarationsResolveTransformerForAnnotationArgumentsMapping;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;)V", "transformAnonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "anonymousInitializer", "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "transformBackingField", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "backingField", "Lorg/jetbrains/kotlin/fir/declarations/FirBackingField;", "transformConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "transformDeclarationStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "declarationStatus", "transformEnumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "enumEntry", "transformField", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "field", "transformProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "property", "transformPropertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "propertyAccessor", "transformRegularClass", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "transformScript", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "script", "transformSimpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "simpleFunction", "transformTypeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "typeAlias", "transformValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "withRegularClass", "action", "Lkotlin/Function0;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FirDeclarationsResolveTransformerForAnnotationArgumentsMapping extends FirDeclarationsResolveTransformer {
    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirAnonymousInitializer transformAnonymousInitializer(FirAnonymousInitializer anonymousInitializer, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(anonymousInitializer, "anonymousInitializer");
        Intrinsics.checkNotNullParameter(data, "data");
        return anonymousInitializer;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirDeclarationStatus transformDeclarationStatus(FirDeclarationStatus declarationStatus, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(declarationStatus, "declarationStatus");
        Intrinsics.checkNotNullParameter(data, "data");
        return declarationStatus;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirScript transformScript(FirScript script, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(script, "script");
        Intrinsics.checkNotNullParameter(data, "data");
        return script;
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public FirDeclarationsResolveTransformerForAnnotationArgumentsMapping(FirAbstractBodyResolveTransformerDispatcher transformer) {
        super(transformer);
        Intrinsics.checkNotNullParameter(transformer, "transformer");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformRegularClass(FirRegularClass regularClass, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformerForAnnotationArgumentsMapping firDeclarationsResolveTransformerForAnnotationArgumentsMapping = this;
        regularClass.transformAnnotations((FirTransformer<? super ResolutionMode>) firDeclarationsResolveTransformerForAnnotationArgumentsMapping, data);
        doTransformTypeParameters(regularClass);
        regularClass.transformSuperTypeRefs((FirTransformer<? super ResolutionMode>) firDeclarationsResolveTransformerForAnnotationArgumentsMapping, data);
        doTransformRegularClass(regularClass, data);
        return regularClass;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer
    public FirRegularClass withRegularClass(FirRegularClass regularClass, final Function0<? extends FirRegularClass> action) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(action, "action");
        BodyResolveContext context = getTransformer().getContext();
        context.getContainingClassDeclarations().add(regularClass);
        try {
            return (FirRegularClass) getTransformer().getContext().withRegularClass(regularClass, getTransformer().getComponents(), new Function0<FirRegularClass>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.plugin.FirDeclarationsResolveTransformerForAnnotationArgumentsMapping$withRegularClass$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                /* JADX WARN: Multi-variable type inference failed */
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final FirRegularClass invoke() {
                    return action.invoke();
                }
            });
        } finally {
            context.getContainingClassDeclarations().removeLast();
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirSimpleFunction transformSimpleFunction(FirSimpleFunction simpleFunction, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
        Intrinsics.checkNotNullParameter(data, "data");
        if (getBodyResolved(simpleFunction)) {
            return simpleFunction;
        }
        FirSimpleFunction firSimpleFunction = simpleFunction;
        doTransformTypeParameters(firSimpleFunction);
        BodyResolveContext context = getTransformer().getContext();
        FirSession session = getSession();
        if (!(context.getContainerIfAny() instanceof FirClass)) {
            context.storeFunction(simpleFunction, session);
        }
        if (firSimpleFunction.getTypeParameters().isEmpty()) {
            context.getContainers().add(simpleFunction);
            try {
                simpleFunction.transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReceiverParameter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformValueParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
            } finally {
                context.getContainers().removeLast();
            }
        } else {
            FirMemberTypeParameterScope firMemberTypeParameterScope = new FirMemberTypeParameterScope(firSimpleFunction);
            FirTowerDataContext towerDataContext = context.getTowerDataContext();
            try {
                context.addNonLocalTowerDataElement(ImplicitReceiverUtilsKt.asTowerDataElement(firMemberTypeParameterScope, false));
                context.getContainers().add(simpleFunction);
                try {
                    simpleFunction.transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReceiverParameter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformValueParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
                    context.getContainers().removeLast();
                    context.replaceTowerDataContext(towerDataContext);
                } catch (Throwable th) {
                    context.getContainers().removeLast();
                    throw th;
                }
            } catch (Throwable th2) {
                context.replaceTowerDataContext(towerDataContext);
                throw th2;
            }
        }
        return simpleFunction;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirConstructor transformConstructor(FirConstructor constructor, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(data, "data");
        FirDeclarationsResolveTransformerForAnnotationArgumentsMapping firDeclarationsResolveTransformerForAnnotationArgumentsMapping = this;
        FirDeclaration containerIfAny = firDeclarationsResolveTransformerForAnnotationArgumentsMapping.getTransformer().getContext().getContainerIfAny();
        FirRegularClass firRegularClass = containerIfAny instanceof FirRegularClass ? (FirRegularClass) containerIfAny : null;
        doTransformTypeParameters(constructor);
        BodyResolveContext context = firDeclarationsResolveTransformerForAnnotationArgumentsMapping.getTransformer().getContext();
        context.getContainers().add(constructor);
        try {
            constructor.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReceiverParameter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data);
            BodyResolveContext context2 = getTransformer().getContext();
            FirAbstractBodyResolveTransformer.BodyResolveTransformerComponents components = getTransformer().getComponents();
            FirTowerDataMode firTowerDataMode = FirTowerDataMode.CONSTRUCTOR_HEADER;
            FirTowerDataMode towerDataMode = context2.getTowerDataMode();
            try {
                context2.setTowerDataMode(firTowerDataMode);
                if (constructor.getIsPrimary()) {
                    FirLocalScope primaryConstructorAllParametersScope = context2.getPrimaryConstructorAllParametersScope();
                    if (primaryConstructorAllParametersScope == null) {
                        constructor.transformValueParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data);
                    } else {
                        FirTowerDataContext towerDataContext = context2.getTowerDataContext();
                        try {
                            context2.addLocalScope(primaryConstructorAllParametersScope);
                            FirConstructor firConstructorTransformValueParameters = constructor.transformValueParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data);
                            context2.replaceTowerDataContext(towerDataContext);
                            if (firConstructorTransformValueParameters == null) {
                                constructor.transformValueParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data);
                            }
                        } catch (Throwable th) {
                            context2.replaceTowerDataContext(towerDataContext);
                            throw th;
                        }
                    }
                } else {
                    context2.addInaccessibleImplicitReceiverValue(firRegularClass, components);
                    FirTowerDataContext towerDataContext2 = context2.getTowerDataContext();
                    try {
                        context2.addLocalScope(context2.buildSecondaryConstructorParametersScope(constructor, components.getSession()));
                        Iterator<T> it = constructor.getValueParameters().iterator();
                        while (it.hasNext()) {
                            context2.storeVariable((FirValueParameter) it.next(), components.getSession());
                        }
                        constructor.transformValueParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data);
                        context2.replaceTowerDataContext(towerDataContext2);
                    } catch (Throwable th2) {
                        context2.replaceTowerDataContext(towerDataContext2);
                        throw th2;
                    }
                }
                context2.setTowerDataMode(towerDataMode);
                context.getContainers().removeLast();
                return constructor;
            } catch (Throwable th3) {
                context2.setTowerDataMode(towerDataMode);
                throw th3;
            }
        } catch (Throwable th4) {
            context.getContainers().removeLast();
            throw th4;
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformValueParameter(FirValueParameter valueParameter, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Intrinsics.checkNotNullParameter(data, "data");
        BodyResolveContext context = getTransformer().getContext();
        FirSession session = getSession();
        if (!valueParameter.getName().isSpecial() || !Intrinsics.areEqual(valueParameter.getName(), SpecialNames.UNDERSCORE_FOR_UNUSED_VAR)) {
            context.storeVariable(valueParameter, session);
        }
        context.getContainers().add(valueParameter);
        try {
            valueParameter.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data);
            return valueParameter;
        } finally {
            context.getContainers().removeLast();
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirProperty transformProperty(FirProperty property, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(data, "data");
        property.transformReceiverParameter((FirTransformer<? super ResolutionMode.ContextIndependent>) getTransformer(), ResolutionMode.ContextIndependent.INSTANCE);
        FirProperty firProperty = property;
        doTransformTypeParameters(firProperty);
        BodyResolveContext context = getTransformer().getContext();
        if (firProperty.getTypeParameters().isEmpty()) {
            context.getContainers().add(property);
            try {
                property.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReceiverParameter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformGetter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformSetter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformBackingField((FirTransformer<? super ResolutionMode>) getTransformer(), data);
            } finally {
                context.getContainers().removeLast();
            }
        } else {
            FirMemberTypeParameterScope firMemberTypeParameterScope = new FirMemberTypeParameterScope(firProperty);
            FirTowerDataContext towerDataContext = context.getTowerDataContext();
            try {
                context.addNonLocalTowerDataElement(ImplicitReceiverUtilsKt.asTowerDataElement(firMemberTypeParameterScope, false));
                context.getContainers().add(property);
                try {
                    property.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReceiverParameter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformGetter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformSetter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformBackingField((FirTransformer<? super ResolutionMode>) getTransformer(), data);
                    context.getContainers().removeLast();
                    context.replaceTowerDataContext(towerDataContext);
                } catch (Throwable th) {
                    context.getContainers().removeLast();
                    throw th;
                }
            } catch (Throwable th2) {
                context.replaceTowerDataContext(towerDataContext);
                throw th2;
            }
        }
        return property;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer
    public FirPropertyAccessor transformPropertyAccessor(FirPropertyAccessor propertyAccessor, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(propertyAccessor, "propertyAccessor");
        Intrinsics.checkNotNullParameter(data, "data");
        propertyAccessor.transformValueParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReceiverParameter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
        return propertyAccessor;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirEnumEntry transformEnumEntry(FirEnumEntry enumEntry, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(enumEntry, "enumEntry");
        Intrinsics.checkNotNullParameter(data, "data");
        BodyResolveContext context = getTransformer().getContext();
        FirTowerDataMode firTowerDataMode = FirTowerDataMode.ENUM_ENTRY;
        FirTowerDataMode towerDataMode = context.getTowerDataMode();
        try {
            context.setTowerDataMode(firTowerDataMode);
            enumEntry.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReceiverParameter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformTypeParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data);
            return enumEntry;
        } finally {
            context.setTowerDataMode(towerDataMode);
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirField transformField(FirField field, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(data, "data");
        BodyResolveContext context = getTransformer().getContext();
        FirTowerDataMode firTowerDataMode = FirTowerDataMode.CONSTRUCTOR_HEADER;
        FirTowerDataMode towerDataMode = context.getTowerDataMode();
        try {
            context.setTowerDataMode(firTowerDataMode);
            context.getContainers().add(field);
            try {
                FirTowerDataContext towerDataContext = context.getTowerDataContext();
                try {
                    FirLocalScope primaryConstructorAllParametersScope = context.getPrimaryConstructorAllParametersScope();
                    if (primaryConstructorAllParametersScope != null) {
                        context.addLocalScope(primaryConstructorAllParametersScope);
                    }
                    field.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
                    context.replaceTowerDataContext(towerDataContext);
                    context.getContainers().removeLast();
                    context.setTowerDataMode(towerDataMode);
                    return field;
                } catch (Throwable th) {
                    context.replaceTowerDataContext(towerDataContext);
                    throw th;
                }
            } catch (Throwable th2) {
                context.getContainers().removeLast();
                throw th2;
            }
        } catch (Throwable th3) {
            context.setTowerDataMode(towerDataMode);
            throw th3;
        }
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformBackingField(FirBackingField backingField, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(backingField, "backingField");
        Intrinsics.checkNotNullParameter(data, "data");
        backingField.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
        return backingField;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeAlias transformTypeAlias(FirTypeAlias typeAlias, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        Intrinsics.checkNotNullParameter(data, "data");
        doTransformTypeParameters(typeAlias);
        typeAlias.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
        FirResolveContextCollector firResolveContextCollector = getTransformer().getFirResolveContextCollector();
        if (firResolveContextCollector != null) {
            firResolveContextCollector.addDeclarationContext(typeAlias, getTransformer().getContext());
        }
        FirTransformerUtilKt.transformSingle(typeAlias.getExpandedTypeRef(), getTransformer(), data);
        return typeAlias;
    }
}
