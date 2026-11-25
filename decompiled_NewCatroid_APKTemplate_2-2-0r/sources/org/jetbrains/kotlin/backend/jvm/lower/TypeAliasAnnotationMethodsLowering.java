package org.jetbrains.kotlin.backend.jvm.lower;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.backend.common.ClassLoweringPass;
import org.jetbrains.kotlin.backend.common.CommonBackendContext;
import org.jetbrains.kotlin.backend.jvm.JvmLoweredDeclarationOrigin;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.ir.builders.declarations.DeclarationBuildersKt;
import org.jetbrains.kotlin.ir.builders.declarations.IrFunctionBuilder;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactory;
import org.jetbrains.kotlin.ir.declarations.IrSimpleFunction;
import org.jetbrains.kotlin.ir.declarations.IrTypeAlias;
import org.jetbrains.kotlin.ir.expressions.impl.IrBlockBodyImpl;
import org.jetbrains.kotlin.load.java.JvmAbi;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: TypeAliasAnnotationMethodsLowering.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0016J\f\u0010\u0010\u001a\u00020\r*\u00020\u000fH\u0002R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u0018\u0010\u0007\u001a\u00020\b*\u00020\t8BX\u0082\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\u000b¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/lower/TypeAliasAnnotationMethodsLowering;", "Lorg/jetbrains/kotlin/backend/common/ClassLoweringPass;", "context", "Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "(Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;)V", "getContext", "()Lorg/jetbrains/kotlin/backend/common/CommonBackendContext;", "syntheticAnnotationMethodName", "Lorg/jetbrains/kotlin/name/Name;", "Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;", "getSyntheticAnnotationMethodName", "(Lorg/jetbrains/kotlin/ir/declarations/IrTypeAlias;)Lorg/jetbrains/kotlin/name/Name;", "lower", "", "irClass", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "visitTypeAliases", "backend.jvm.lower"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class TypeAliasAnnotationMethodsLowering implements ClassLoweringPass {
    private final CommonBackendContext context;

    public TypeAliasAnnotationMethodsLowering(CommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
    }

    public final CommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.ClassLoweringPass
    public void lower(IrClass irClass) {
        Intrinsics.checkNotNullParameter(irClass, "irClass");
        visitTypeAliases(irClass);
    }

    private final Name getSyntheticAnnotationMethodName(IrTypeAlias irTypeAlias) {
        Name nameIdentifier = Name.identifier(JvmAbi.getSyntheticMethodNameForAnnotatedTypeAlias(irTypeAlias.getName()));
        Intrinsics.checkNotNullExpressionValue(nameIdentifier, "identifier(...)");
        return nameIdentifier;
    }

    private final void visitTypeAliases(IrClass irClass) {
        List<IrDeclaration> declarations = irClass.getDeclarations();
        ArrayList arrayList = new ArrayList();
        for (Object obj : declarations) {
            if (obj instanceof IrTypeAlias) {
                arrayList.add(obj);
            }
        }
        ArrayList<IrTypeAlias> arrayList2 = new ArrayList();
        for (Object obj2 : arrayList) {
            if (!((IrTypeAlias) obj2).getAnnotations().isEmpty()) {
                arrayList2.add(obj2);
            }
        }
        for (IrTypeAlias irTypeAlias : arrayList2) {
            IrFactory factory = irClass.getFactory();
            IrFunctionBuilder irFunctionBuilder = new IrFunctionBuilder();
            irFunctionBuilder.setName(getSyntheticAnnotationMethodName(irTypeAlias));
            irFunctionBuilder.setVisibility(irTypeAlias.getVisibility());
            irFunctionBuilder.setReturnType(this.context.getIrBuiltIns().getUnitType());
            irFunctionBuilder.setModality(Modality.OPEN);
            irFunctionBuilder.setOrigin(JvmLoweredDeclarationOrigin.SYNTHETIC_METHOD_FOR_PROPERTY_OR_TYPEALIAS_ANNOTATIONS.INSTANCE);
            IrSimpleFunction irSimpleFunctionBuildFunction = DeclarationBuildersKt.buildFunction(factory, irFunctionBuilder);
            IrClass irClass2 = irClass;
            irClass2.getDeclarations().add(irSimpleFunctionBuildFunction);
            irSimpleFunctionBuildFunction.setParent(irClass2);
            irSimpleFunctionBuildFunction.setBody(new IrBlockBodyImpl(-1, -1));
            irSimpleFunctionBuildFunction.setAnnotations(CollectionsKt.plus((Collection) irSimpleFunctionBuildFunction.getAnnotations(), (Iterable) irTypeAlias.getAnnotations()));
        }
    }
}
