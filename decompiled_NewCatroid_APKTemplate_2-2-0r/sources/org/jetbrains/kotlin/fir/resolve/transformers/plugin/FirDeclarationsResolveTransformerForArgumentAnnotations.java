package org.jetbrains.kotlin.fir.resolve.transformers.plugin;

import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.fir.declarations.FirAnonymousInitializer;
import org.jetbrains.kotlin.fir.declarations.FirConstructor;
import org.jetbrains.kotlin.fir.declarations.FirDeclarationStatus;
import org.jetbrains.kotlin.fir.declarations.FirEnumEntry;
import org.jetbrains.kotlin.fir.declarations.FirField;
import org.jetbrains.kotlin.fir.declarations.FirProperty;
import org.jetbrains.kotlin.fir.declarations.FirPropertyAccessor;
import org.jetbrains.kotlin.fir.declarations.FirReceiverParameter;
import org.jetbrains.kotlin.fir.declarations.FirRegularClass;
import org.jetbrains.kotlin.fir.declarations.FirScript;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirTypeAlias;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.expressions.FirStatement;
import org.jetbrains.kotlin.fir.resolve.FirTowerDataMode;
import org.jetbrains.kotlin.fir.resolve.ResolutionMode;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.BodyResolveContext;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirAbstractBodyResolveTransformerDispatcher;
import org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformer;
import org.jetbrains.kotlin.fir.visitors.FirTransformerUtilKt;
import org.jetbrains.kotlin.load.java.JvmAbi;

/* JADX INFO: compiled from: FirAnnotationArgumentsResolveTransformer.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000\u008a\u0001\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00112\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00142\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u00172\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00020\u001a2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001d2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010\u001f\u001a\u00020 2\u0006\u0010!\u001a\u00020\"2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010#\u001a\u00020$2\u0006\u0010%\u001a\u00020$2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010&\u001a\u00020'2\u0006\u0010(\u001a\u00020'2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010)\u001a\u00020*2\u0006\u0010+\u001a\u00020*2\u0006\u0010\b\u001a\u00020\tH\u0016J\u0018\u0010,\u001a\u00020 2\u0006\u0010-\u001a\u00020.2\u0006\u0010\b\u001a\u00020\tH\u0016J\u001e\u0010/\u001a\u00020\"2\u0006\u0010!\u001a\u00020\"2\f\u00100\u001a\b\u0012\u0004\u0012\u00020\"01H\u0016¨\u00062"}, d2 = {"Lorg/jetbrains/kotlin/fir/resolve/transformers/plugin/FirDeclarationsResolveTransformerForArgumentAnnotations;", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirDeclarationsResolveTransformer;", "transformer", "Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;", "(Lorg/jetbrains/kotlin/fir/resolve/transformers/body/resolve/FirAbstractBodyResolveTransformerDispatcher;)V", "transformAnonymousInitializer", "Lorg/jetbrains/kotlin/fir/declarations/FirAnonymousInitializer;", "anonymousInitializer", "data", "Lorg/jetbrains/kotlin/fir/resolve/ResolutionMode;", "transformConstructor", "Lorg/jetbrains/kotlin/fir/declarations/FirConstructor;", JvmAbi.ERASED_INLINE_CONSTRUCTOR_NAME, "transformDeclarationStatus", "Lorg/jetbrains/kotlin/fir/declarations/FirDeclarationStatus;", "declarationStatus", "transformEnumEntry", "Lorg/jetbrains/kotlin/fir/declarations/FirEnumEntry;", "enumEntry", "transformField", "Lorg/jetbrains/kotlin/fir/declarations/FirField;", "field", "transformProperty", "Lorg/jetbrains/kotlin/fir/declarations/FirProperty;", "property", "transformPropertyAccessor", "Lorg/jetbrains/kotlin/fir/declarations/FirPropertyAccessor;", "propertyAccessor", "transformReceiverParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirReceiverParameter;", "receiverParameter", "transformRegularClass", "Lorg/jetbrains/kotlin/fir/expressions/FirStatement;", "regularClass", "Lorg/jetbrains/kotlin/fir/declarations/FirRegularClass;", "transformScript", "Lorg/jetbrains/kotlin/fir/declarations/FirScript;", "script", "transformSimpleFunction", "Lorg/jetbrains/kotlin/fir/declarations/FirSimpleFunction;", "simpleFunction", "transformTypeAlias", "Lorg/jetbrains/kotlin/fir/declarations/FirTypeAlias;", "typeAlias", "transformValueParameter", "valueParameter", "Lorg/jetbrains/kotlin/fir/declarations/FirValueParameter;", "withRegularClass", "action", "Lkotlin/Function0;", "resolve"}, k = 1, mv = {1, 9, 0}, xi = 48)
final class FirDeclarationsResolveTransformerForArgumentAnnotations extends FirDeclarationsResolveTransformer {
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
    public FirDeclarationsResolveTransformerForArgumentAnnotations(FirAbstractBodyResolveTransformerDispatcher transformer) {
        super(transformer);
        Intrinsics.checkNotNullParameter(transformer, "transformer");
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformRegularClass(final FirRegularClass regularClass, final ResolutionMode data) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(data, "data");
        regularClass.transformAnnotations((FirTransformer<? super ResolutionMode>) this, data);
        withRegularClass(regularClass, new Function0<FirRegularClass>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.plugin.FirDeclarationsResolveTransformerForArgumentAnnotations.transformRegularClass.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FirRegularClass invoke() {
                return regularClass.transformTypeParameters((FirTransformer<? super ResolutionMode>) this.getTransformer(), data).transformSuperTypeRefs((FirTransformer<? super ResolutionMode>) this.getTransformer(), data).transformDeclarations((FirTransformer<? super ResolutionMode>) this.getTransformer(), data);
            }
        });
        return regularClass;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer
    public FirRegularClass withRegularClass(FirRegularClass regularClass, final Function0<? extends FirRegularClass> action) {
        Intrinsics.checkNotNullParameter(regularClass, "regularClass");
        Intrinsics.checkNotNullParameter(action, "action");
        BodyResolveContext context = getTransformer().getContext();
        context.getContainingClassDeclarations().add(regularClass);
        try {
            return (FirRegularClass) getTransformer().getContext().withRegularClass(regularClass, getTransformer().getComponents(), new Function0<FirRegularClass>() { // from class: org.jetbrains.kotlin.fir.resolve.transformers.plugin.FirDeclarationsResolveTransformerForArgumentAnnotations$withRegularClass$1$1
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
        simpleFunction.transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReceiverParameter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformValueParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
        return simpleFunction;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirConstructor transformConstructor(FirConstructor constructor, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(constructor, "constructor");
        Intrinsics.checkNotNullParameter(data, "data");
        constructor.transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReceiverParameter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformValueParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
        return constructor;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirStatement transformValueParameter(FirValueParameter valueParameter, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(valueParameter, "valueParameter");
        Intrinsics.checkNotNullParameter(data, "data");
        valueParameter.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data);
        return valueParameter;
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirProperty transformProperty(FirProperty property, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(property, "property");
        Intrinsics.checkNotNullParameter(data, "data");
        property.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReceiverParameter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformReturnTypeRef((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformGetter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformSetter((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformTypeParameters((FirTransformer<? super ResolutionMode>) getTransformer(), data);
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

    @Override // org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirReceiverParameter transformReceiverParameter(FirReceiverParameter receiverParameter, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(receiverParameter, "receiverParameter");
        Intrinsics.checkNotNullParameter(data, "data");
        return receiverParameter.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data).transformTypeRef(getTransformer(), data);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirField transformField(FirField field, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(field, "field");
        Intrinsics.checkNotNullParameter(data, "data");
        return field.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
    }

    @Override // org.jetbrains.kotlin.fir.resolve.transformers.body.resolve.FirDeclarationsResolveTransformer, org.jetbrains.kotlin.fir.visitors.FirTransformer
    public FirTypeAlias transformTypeAlias(FirTypeAlias typeAlias, ResolutionMode data) {
        Intrinsics.checkNotNullParameter(typeAlias, "typeAlias");
        Intrinsics.checkNotNullParameter(data, "data");
        typeAlias.transformAnnotations((FirTransformer<? super ResolutionMode>) getTransformer(), data);
        FirTransformerUtilKt.transformSingle(typeAlias.getExpandedTypeRef(), getTransformer(), data);
        return typeAlias;
    }
}
