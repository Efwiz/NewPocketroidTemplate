package org.jetbrains.kotlin.ir.backend.js.lower;

import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;
import org.jetbrains.kotlin.backend.common.DeclarationTransformer;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.ir.backend.js.JsCommonBackendContext;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrConstructor;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrFactoryHelpersKt;
import org.jetbrains.kotlin.ir.expressions.IrBlockBody;
import org.jetbrains.kotlin.ir.expressions.impl.IrDelegatingConstructorCallImpl;
import org.jetbrains.kotlin.ir.expressions.impl.IrInstanceInitializerCallImpl;
import org.jetbrains.kotlin.ir.symbols.IrConstructorSymbol;
import org.jetbrains.kotlin.ir.types.IrType;
import org.jetbrains.kotlin.ir.util.IrUtilsKt;

/* JADX INFO: compiled from: AnnotationConstructorLowering.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\u0018\u0010\u000b\u001a\n\u0012\u0004\u0012\u00020\r\u0018\u00010\f2\u0006\u0010\u000e\u001a\u00020\rH\u0016R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u000e\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000f"}, d2 = {"Lorg/jetbrains/kotlin/ir/backend/js/lower/AnnotationConstructorLowering;", "Lorg/jetbrains/kotlin/backend/common/DeclarationTransformer;", "context", "Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "(Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;)V", "anyConstructor", "Lorg/jetbrains/kotlin/ir/symbols/IrConstructorSymbol;", "getContext", "()Lorg/jetbrains/kotlin/ir/backend/js/JsCommonBackendContext;", "unitType", "Lorg/jetbrains/kotlin/ir/types/IrType;", "transformFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "declaration", "backend.js"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class AnnotationConstructorLowering implements DeclarationTransformer {
    private final IrConstructorSymbol anyConstructor;
    private final JsCommonBackendContext context;
    private final IrType unitType;

    public AnnotationConstructorLowering(JsCommonBackendContext context) {
        Intrinsics.checkNotNullParameter(context, "context");
        this.context = context;
        this.unitType = context.getIrBuiltIns().getUnitType();
        this.anyConstructor = (IrConstructorSymbol) SequencesKt.first(IrUtilsKt.getConstructors(context.getIrBuiltIns().getAnyClass()));
    }

    public final JsCommonBackendContext getContext() {
        return this.context;
    }

    @Override // org.jetbrains.kotlin.backend.common.DeclarationTransformer
    public List<IrDeclaration> transformFlat(IrDeclaration declaration) {
        Intrinsics.checkNotNullParameter(declaration, "declaration");
        if (declaration instanceof IrConstructor) {
            IrConstructor irConstructor = (IrConstructor) declaration;
            if (irConstructor.getIsPrimary()) {
                final IrClass parentAsClass = IrUtilsKt.getParentAsClass(declaration);
                if (parentAsClass.getKind() != ClassKind.ANNOTATION_CLASS) {
                    return null;
                }
                irConstructor.setBody(IrFactoryHelpersKt.createBlockBody(declaration.getFactory(), declaration.getStartOffset(), declaration.getEndOffset(), new Function1<IrBlockBody, Unit>() { // from class: org.jetbrains.kotlin.ir.backend.js.lower.AnnotationConstructorLowering.transformFlat.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    public /* bridge */ /* synthetic */ Unit invoke(IrBlockBody irBlockBody) {
                        invoke2(irBlockBody);
                        return Unit.INSTANCE;
                    }

                    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
                    public final void invoke2(IrBlockBody createBlockBody) {
                        Intrinsics.checkNotNullParameter(createBlockBody, "$this$createBlockBody");
                        if (AnnotationConstructorLowering.this.getContext().getEs6mode()) {
                            createBlockBody.getStatements().add(new IrDelegatingConstructorCallImpl(createBlockBody.getStartOffset(), createBlockBody.getEndOffset(), AnnotationConstructorLowering.this.getContext().getIrBuiltIns().getAnyType(), AnnotationConstructorLowering.this.anyConstructor, 0, 0));
                        }
                        createBlockBody.getStatements().add(new IrInstanceInitializerCallImpl(createBlockBody.getStartOffset(), createBlockBody.getEndOffset(), parentAsClass.getSymbol(), AnnotationConstructorLowering.this.unitType));
                    }
                }));
            }
        }
        return null;
    }
}
