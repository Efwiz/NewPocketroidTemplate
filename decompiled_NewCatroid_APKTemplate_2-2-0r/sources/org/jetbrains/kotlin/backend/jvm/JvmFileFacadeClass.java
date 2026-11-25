package org.jetbrains.kotlin.backend.jvm;

import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.descriptors.ClassKind;
import org.jetbrains.kotlin.descriptors.DescriptorVisibilities;
import org.jetbrains.kotlin.descriptors.DescriptorVisibility;
import org.jetbrains.kotlin.descriptors.Modality;
import org.jetbrains.kotlin.descriptors.SourceElement;
import org.jetbrains.kotlin.ir.declarations.IrClass;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationOrigin;
import org.jetbrains.kotlin.ir.declarations.impl.IrClassImpl;
import org.jetbrains.kotlin.ir.symbols.impl.IrClassSymbolImpl;
import org.jetbrains.kotlin.ir.util.DeserializableClass;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: JvmFileFacadeClass.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\u0018\u00002\u00020\u00012\u00020\u0002B1\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b\u0012\u0012\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\n¢\u0006\u0002\u0010\rJ\b\u0010\u0010\u001a\u00020\fH\u0016R\u001a\u0010\t\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\f0\nX\u0082\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u000e\u001a\u0004\u0018\u00010\fX\u0082\u000e¢\u0006\u0004\n\u0002\u0010\u000f¨\u0006\u0011"}, d2 = {"Lorg/jetbrains/kotlin/backend/jvm/JvmFileFacadeClass;", "Lorg/jetbrains/kotlin/ir/declarations/impl/IrClassImpl;", "Lorg/jetbrains/kotlin/ir/util/DeserializableClass;", "origin", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;", "name", "Lorg/jetbrains/kotlin/name/Name;", FirebaseAnalytics.Param.SOURCE, "Lorg/jetbrains/kotlin/descriptors/SourceElement;", "deserializeIr", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrClass;", "", "(Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationOrigin;Lorg/jetbrains/kotlin/name/Name;Lorg/jetbrains/kotlin/descriptors/SourceElement;Lkotlin/jvm/functions/Function1;)V", "irLoaded", "Ljava/lang/Boolean;", "loadIr", "backend.jvm"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class JvmFileFacadeClass extends IrClassImpl implements DeserializableClass {
    private final Function1<IrClass, Boolean> deserializeIr;
    private Boolean irLoaded;

    /* JADX WARN: Multi-variable type inference failed */
    public JvmFileFacadeClass(IrDeclarationOrigin origin, Name name2, SourceElement source, Function1<? super IrClass, Boolean> deserializeIr) {
        Intrinsics.checkNotNullParameter(origin, "origin");
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(source, "source");
        Intrinsics.checkNotNullParameter(deserializeIr, "deserializeIr");
        IrClassSymbolImpl irClassSymbolImpl = new IrClassSymbolImpl(null, 1, null);
        ClassKind classKind = ClassKind.CLASS;
        DescriptorVisibility PUBLIC = DescriptorVisibilities.PUBLIC;
        Intrinsics.checkNotNullExpressionValue(PUBLIC, "PUBLIC");
        super(-1, -1, origin, irClassSymbolImpl, name2, classKind, PUBLIC, Modality.FINAL, false, false, false, false, false, false, false, source, null, 98048, null);
        this.deserializeIr = deserializeIr;
    }

    @Override // org.jetbrains.kotlin.ir.util.DeserializableClass
    public boolean loadIr() {
        Boolean boolInvoke = this.irLoaded;
        if (boolInvoke == null) {
            boolInvoke = this.deserializeIr.invoke(this);
            this.irLoaded = Boolean.valueOf(boolInvoke.booleanValue());
        }
        return boolInvoke.booleanValue();
    }
}
