package org.jetbrains.kotlin.fir.lazy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.properties.ReadWriteProperty;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.fir.FirAnnotationContainer;
import org.jetbrains.kotlin.fir.backend.Fir2IrComponents;
import org.jetbrains.kotlin.fir.expressions.FirAnnotation;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase;
import org.jetbrains.kotlin.ir.declarations.lazy.LazyUtilKt;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.expressions.IrExpression;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.types.KotlinType;
import org.jetbrains.kotlin.utils.addToStdlib.AddToStdlibKt;

/* JADX INFO: compiled from: AbstractFir2IrLazyDeclaration.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0001\n\u0000\bf\u0018\u0000*\b\b\u0000\u0010\u0001*\u00020\u00022\u00020\u00032\u00020\u00042\u00020\u0005J\u001c\u0010\u0015\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0017\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00190\u00180\u0016H\u0016J\b\u0010\u001a\u001a\u00020\u001bH\u0016J\b\u0010\u001c\u001a\u00020\u001dH\u0016R\u0014\u0010\u0006\u001a\u00020\u00078VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00028\u0000X¦\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0014\u0010\r\u001a\u00020\u000e8VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R\u0014\u0010\u0011\u001a\u00020\u00128VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u001eÀ\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/fir/lazy/AbstractFir2IrLazyDeclaration;", "F", "Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyDeclarationBase;", "Lorg/jetbrains/kotlin/fir/backend/Fir2IrComponents;", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "fir", "getFir", "()Lorg/jetbrains/kotlin/fir/FirAnnotationContainer;", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "getStubGenerator", "()Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "createLazyAnnotations", "Lkotlin/properties/ReadWriteProperty;", "", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "lazyParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "mutationNotSupported", "", "fir2ir"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface AbstractFir2IrLazyDeclaration<F extends FirAnnotationContainer> extends Fir2IrComponents, IrDeclaration, IrLazyDeclarationBase {
    F getFir();

    /* JADX INFO: compiled from: AbstractFir2IrLazyDeclaration.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static <F extends FirAnnotationContainer> ReadWriteProperty<Object, IrDeclarationParent> createLazyParent(AbstractFir2IrLazyDeclaration<F> abstractFir2IrLazyDeclaration) {
            return IrLazyDeclarationBase.DefaultImpls.createLazyParent(abstractFir2IrLazyDeclaration);
        }

        public static <F extends FirAnnotationContainer> IrValueParameter generateReceiverParameterStub(AbstractFir2IrLazyDeclaration<F> abstractFir2IrLazyDeclaration, ReceiverParameterDescriptor receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return IrLazyDeclarationBase.DefaultImpls.generateReceiverParameterStub(abstractFir2IrLazyDeclaration, receiver);
        }

        public static <F extends FirAnnotationContainer> IrType toIrType(AbstractFir2IrLazyDeclaration<F> abstractFir2IrLazyDeclaration, KotlinType receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return IrLazyDeclarationBase.DefaultImpls.toIrType(abstractFir2IrLazyDeclaration, receiver);
        }
    }

    @Override // org.jetbrains.kotlin.ir.declarations.IrDeclaration, org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    default IrFactory getFactory() {
        return getIrFactory();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    default ReadWriteProperty<Object, List<IrConstructorCall>> createLazyAnnotations() {
        return LazyUtilKt.lazyVar(getLock(), new Function0<List<? extends IrConstructorCall>>(this) { // from class: org.jetbrains.kotlin.fir.lazy.AbstractFir2IrLazyDeclaration.createLazyAnnotations.1
            final /* synthetic */ AbstractFir2IrLazyDeclaration<F> this$0;

            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
                this.this$0 = this;
            }

            @Override // kotlin.jvm.functions.Function0
            public final List<? extends IrConstructorCall> invoke() {
                List<FirAnnotation> annotations = this.this$0.getFir().getAnnotations();
                AbstractFir2IrLazyDeclaration<F> abstractFir2IrLazyDeclaration = this.this$0;
                ArrayList arrayList = new ArrayList();
                Iterator<T> it = annotations.iterator();
                while (it.hasNext()) {
                    IrExpression irExpressionConvertToIrConstructorCall = abstractFir2IrLazyDeclaration.getCallGenerator().convertToIrConstructorCall((FirAnnotation) it.next());
                    IrConstructorCall irConstructorCall = irExpressionConvertToIrConstructorCall instanceof IrConstructorCall ? (IrConstructorCall) irExpressionConvertToIrConstructorCall : null;
                    if (irConstructorCall != null) {
                        arrayList.add(irConstructorCall);
                    }
                }
                return arrayList;
            }
        });
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    default IrDeclarationParent lazyParent() {
        return getParent();
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    default DeclarationStubGenerator getStubGenerator() {
        AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
        throw null;
    }

    @Override // org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase
    default TypeTranslator getTypeTranslator() {
        AddToStdlibKt.shouldNotBeCalled$default(null, 1, null);
        throw null;
    }

    default Void mutationNotSupported() {
        throw new IllegalStateException("Mutation of Fir2Ir lazy elements is not possible".toString());
    }
}
