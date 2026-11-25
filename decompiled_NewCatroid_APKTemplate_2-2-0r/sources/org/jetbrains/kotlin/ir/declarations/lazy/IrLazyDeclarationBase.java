package org.jetbrains.kotlin.ir.declarations.lazy;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.properties.ReadWriteProperty;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.descriptors.ClassDescriptor;
import org.jetbrains.kotlin.descriptors.DeclarationDescriptor;
import org.jetbrains.kotlin.descriptors.FunctionDescriptor;
import org.jetbrains.kotlin.descriptors.PackageFragmentDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyAccessorDescriptor;
import org.jetbrains.kotlin.descriptors.PropertyDescriptor;
import org.jetbrains.kotlin.descriptors.ReceiverParameterDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.AnnotationDescriptor;
import org.jetbrains.kotlin.descriptors.annotations.Annotations;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.declarations.IrExternalPackageFragment;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrValueParameter;
import org.jetbrains.kotlin.ir.expressions.IrConstructorCall;
import org.jetbrains.kotlin.ir.symbols.IrValueParameterSymbol;
import org.jetbrains.kotlin.ir.symbols.impl.IrValueParameterSymbolImpl;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.ConstantValueGenerator;
import org.jetbrains.kotlin.ir.util.DeclarationStubGenerator;
import org.jetbrains.kotlin.ir.util.TypeTranslator;
import org.jetbrains.kotlin.name.Name;
import org.jetbrains.kotlin.types.KotlinType;

/* JADX INFO: compiled from: IrLazyDeclarationBase.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u001c\u0010\u000e\u001a\u0016\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00120\u00110\u000fH\u0016J\u0016\u0010\u0013\u001a\u0010\u0012\u0006\u0012\u0004\u0018\u00010\u0010\u0012\u0004\u0012\u00020\u00140\u000fH\u0016J\b\u0010\u0015\u001a\u00020\u0014H\u0016J\f\u0010\u0016\u001a\u00020\u0017*\u00020\u0018H\u0016J\f\u0010\u0019\u001a\u00020\u001a*\u00020\u001bH\u0016R\u0014\u0010\u0002\u001a\u00020\u00038VX\u0096\u0004¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005R\u0012\u0010\u0006\u001a\u00020\u0007X¦\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\tR\u0012\u0010\n\u001a\u00020\u000bX¦\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\r¨\u0006\u001c"}, d2 = {"Lorg/jetbrains/kotlin/ir/declarations/lazy/IrLazyDeclarationBase;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "factory", "Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "getFactory", "()Lorg/jetbrains/kotlin/ir/declarations/IrFactory;", "stubGenerator", "Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "getStubGenerator", "()Lorg/jetbrains/kotlin/ir/util/DeclarationStubGenerator;", "typeTranslator", "Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "getTypeTranslator", "()Lorg/jetbrains/kotlin/ir/util/TypeTranslator;", "createLazyAnnotations", "Lkotlin/properties/ReadWriteProperty;", "", "", "Lorg/jetbrains/kotlin/ir/expressions/IrConstructorCall;", "createLazyParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "lazyParent", "generateReceiverParameterStub", "Lorg/jetbrains/kotlin/ir/declarations/IrValueParameter;", "Lorg/jetbrains/kotlin/descriptors/ReceiverParameterDescriptor;", "toIrType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "Lorg/jetbrains/kotlin/types/KotlinType;", "ir.tree"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface IrLazyDeclarationBase extends IrDeclaration {
    ReadWriteProperty<Object, List<IrConstructorCall>> createLazyAnnotations();

    ReadWriteProperty<Object, IrDeclarationParent> createLazyParent();

    IrValueParameter generateReceiverParameterStub(ReceiverParameterDescriptor receiverParameterDescriptor);

    IrFactory getFactory();

    DeclarationStubGenerator getStubGenerator();

    TypeTranslator getTypeTranslator();

    IrDeclarationParent lazyParent();

    IrType toIrType(KotlinType kotlinType);

    /* JADX INFO: compiled from: IrLazyDeclarationBase.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    public static final class DefaultImpls {
        public static IrFactory getFactory(IrLazyDeclarationBase irLazyDeclarationBase) {
            return irLazyDeclarationBase.getStubGenerator().getSymbolTable().getIrFactory();
        }

        public static IrType toIrType(IrLazyDeclarationBase irLazyDeclarationBase, KotlinType receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            return irLazyDeclarationBase.getTypeTranslator().translateType(receiver);
        }

        public static IrValueParameter generateReceiverParameterStub(IrLazyDeclarationBase irLazyDeclarationBase, ReceiverParameterDescriptor receiver) {
            Intrinsics.checkNotNullParameter(receiver, "$receiver");
            IrFactory factory = irLazyDeclarationBase.getFactory();
            IrDeclarationOrigin origin = irLazyDeclarationBase.getOrigin();
            Name name2 = receiver.getShortName();
            Intrinsics.checkNotNullExpressionValue(name2, "getName(...)");
            KotlinType type = receiver.getType();
            Intrinsics.checkNotNullExpressionValue(type, "getType(...)");
            return factory.createValueParameter(-1, -1, origin, name2, irLazyDeclarationBase.toIrType(type), false, (IrValueParameterSymbol) new IrValueParameterSymbolImpl(receiver), -1, (IrType) null, false, false, false);
        }

        public static ReadWriteProperty<Object, List<IrConstructorCall>> createLazyAnnotations(final IrLazyDeclarationBase irLazyDeclarationBase) {
            return LazyUtilKt.lazyVar(irLazyDeclarationBase.getStubGenerator().getLock(), new Function0<List<? extends IrConstructorCall>>() { // from class: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase.createLazyAnnotations.1
                {
                    super(0);
                }

                @Override // kotlin.jvm.functions.Function0
                public final List<? extends IrConstructorCall> invoke() {
                    Annotations annotations = IrLazyDeclarationBase.this.getDescriptor().getAnnotations();
                    ConstantValueGenerator constantValueGenerator = IrLazyDeclarationBase.this.getTypeTranslator().getConstantValueGenerator();
                    ArrayList arrayList = new ArrayList();
                    Iterator<AnnotationDescriptor> it = annotations.iterator();
                    while (it.hasNext()) {
                        IrConstructorCall irConstructorCallGenerateAnnotationConstructorCall$default = ConstantValueGenerator.generateAnnotationConstructorCall$default(constantValueGenerator, it.next(), null, 2, null);
                        if (irConstructorCallGenerateAnnotationConstructorCall$default != null) {
                            arrayList.add(irConstructorCallGenerateAnnotationConstructorCall$default);
                        }
                    }
                    return CollectionsKt.toMutableList((Collection) arrayList);
                }
            });
        }

        public static ReadWriteProperty<Object, IrDeclarationParent> createLazyParent(IrLazyDeclarationBase irLazyDeclarationBase) {
            return LazyUtilKt.lazyVar(irLazyDeclarationBase.getStubGenerator().getLock(), new C47061(irLazyDeclarationBase));
        }

        public static IrDeclarationParent lazyParent(IrLazyDeclarationBase irLazyDeclarationBase) {
            IrExternalPackageFragment irExternalPackageFragmentGenerateOrGetEmptyExternalPackageFragmentStub;
            IrClass irClassGenerateOrGetFacadeClass;
            PropertyDescriptor correspondingProperty;
            DeclarationDescriptor descriptor = irLazyDeclarationBase.getDescriptor();
            PropertyAccessorDescriptor propertyAccessorDescriptor = descriptor instanceof PropertyAccessorDescriptor ? (PropertyAccessorDescriptor) descriptor : null;
            DeclarationDescriptor containingDeclaration = ((propertyAccessorDescriptor == null || (correspondingProperty = propertyAccessorDescriptor.getCorrespondingProperty()) == null) ? descriptor : correspondingProperty).getDeclarationDescriptor();
            if (containingDeclaration instanceof PackageFragmentDescriptor) {
                if ((irLazyDeclarationBase instanceof IrClass ? null : irLazyDeclarationBase) != null && (irClassGenerateOrGetFacadeClass = irLazyDeclarationBase.getStubGenerator().generateOrGetFacadeClass(irLazyDeclarationBase.getDescriptor())) != null) {
                    irExternalPackageFragmentGenerateOrGetEmptyExternalPackageFragmentStub = irClassGenerateOrGetFacadeClass;
                } else {
                    irExternalPackageFragmentGenerateOrGetEmptyExternalPackageFragmentStub = irLazyDeclarationBase.getStubGenerator().generateOrGetEmptyExternalPackageFragmentStub((PackageFragmentDescriptor) containingDeclaration);
                }
                irExternalPackageFragmentGenerateOrGetEmptyExternalPackageFragmentStub.getDeclarations().add(irLazyDeclarationBase);
                return irExternalPackageFragmentGenerateOrGetEmptyExternalPackageFragmentStub;
            }
            if (containingDeclaration instanceof ClassDescriptor) {
                return irLazyDeclarationBase.getStubGenerator().generateClassStub((ClassDescriptor) containingDeclaration);
            }
            if (containingDeclaration instanceof FunctionDescriptor) {
                return DeclarationStubGenerator.generateFunctionStub$default(irLazyDeclarationBase.getStubGenerator(), (FunctionDescriptor) containingDeclaration, false, 2, null);
            }
            if (!(containingDeclaration instanceof PropertyDescriptor)) {
                throw new AssertionError("Package or class expected: " + containingDeclaration + "; for " + descriptor);
            }
            DeclarationStubGenerator stubGenerator = irLazyDeclarationBase.getStubGenerator();
            PropertyDescriptor propertyDescriptor = (PropertyDescriptor) containingDeclaration;
            PropertyAccessorDescriptor getter = propertyDescriptor.getGetter();
            if (getter == null) {
                getter = propertyDescriptor.getSetter();
            }
            Intrinsics.checkNotNull(getter);
            return DeclarationStubGenerator.generateFunctionStub$default(stubGenerator, getter, false, 2, null);
        }
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.declarations.lazy.IrLazyDeclarationBase$createLazyParent$1, reason: invalid class name and case insensitive filesystem */
    /* JADX INFO: compiled from: IrLazyDeclarationBase.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 48)
    /* synthetic */ class C47061 extends FunctionReference implements Function0<IrDeclarationParent> {
        C47061(Object obj) {
            super(0, obj);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "lazyParent()Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "lazyParent";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(IrLazyDeclarationBase.class);
        }

        @Override // kotlin.jvm.functions.Function0
        public final IrDeclarationParent invoke() {
            return ((IrLazyDeclarationBase) this.receiver).lazyParent();
        }
    }
}
