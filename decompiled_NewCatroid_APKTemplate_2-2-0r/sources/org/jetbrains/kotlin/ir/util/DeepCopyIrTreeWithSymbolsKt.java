package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.FunctionReference;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationParent;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;
import org.jetbrains.kotlin.ir.visitors.IrVisitorsKt;

/* JADX INFO: compiled from: DeepCopyIrTreeWithSymbols.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000,\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u001aI\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u0002H\u00012\n\b\u0002\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u001a\b\u0002\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0006H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\n\u001aO\u0010\u0000\u001a\u0002H\u0001\"\n\b\u0000\u0010\u0001\u0018\u0001*\u00020\u0002*\u0002H\u00012\b\u0010\u0003\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u000b\u001a\u00020\f2\u001a\b\u0002\u0010\u0005\u001a\u0014\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0006H\u0086\bø\u0001\u0000¢\u0006\u0002\u0010\r\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u000e"}, d2 = {"deepCopyWithSymbols", ExifInterface.GPS_DIRECTION_TRUE, "Lorg/jetbrains/kotlin/ir/IrElement;", "initialParent", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;", "createCopier", "Lkotlin/Function2;", "Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;", "Lorg/jetbrains/kotlin/ir/util/TypeRemapper;", "Lorg/jetbrains/kotlin/ir/util/DeepCopyIrTreeWithSymbols;", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/ir/IrElement;", "symbolRemapper", "Lorg/jetbrains/kotlin/ir/util/DeepCopySymbolRemapper;", "(Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationParent;Lorg/jetbrains/kotlin/ir/util/DeepCopySymbolRemapper;Lkotlin/jvm/functions/Function2;)Lorg/jetbrains/kotlin/ir/IrElement;", "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class DeepCopyIrTreeWithSymbolsKt {

    /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbolsKt$deepCopyWithSymbols$1, reason: invalid class name */
    /* JADX INFO: compiled from: DeepCopyIrTreeWithSymbols.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 176)
    public /* synthetic */ class AnonymousClass1 extends FunctionReference implements Function2<SymbolRemapper, TypeRemapper, DeepCopyIrTreeWithSymbols> {
        public static final AnonymousClass1 INSTANCE = new AnonymousClass1();

        public AnonymousClass1() {
            super(2);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;Lorg/jetbrains/kotlin/ir/util/TypeRemapper;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(DeepCopyIrTreeWithSymbols.class);
        }

        @Override // kotlin.jvm.functions.Function2
        public final DeepCopyIrTreeWithSymbols invoke(SymbolRemapper p0, TypeRemapper p1) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            return new DeepCopyIrTreeWithSymbols(p0, p1);
        }
    }

    public static /* synthetic */ IrElement deepCopyWithSymbols$default(IrElement irElement, IrDeclarationParent irDeclarationParent, Function2 createCopier, int i, Object obj) {
        if ((i & 1) != 0) {
            irDeclarationParent = null;
        }
        if ((i & 2) != 0) {
            createCopier = AnonymousClass1.INSTANCE;
        }
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        Intrinsics.checkNotNullParameter(createCopier, "createCopier");
        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
        IrVisitorsKt.acceptVoid(irElement, deepCopySymbolRemapper);
        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irElement.transform((IrElementTransformer) createCopier.invoke(deepCopySymbolRemapper, new DeepCopyTypeRemapper(deepCopySymbolRemapper)), null), irDeclarationParent);
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        return irElementPatchDeclarationParents;
    }

    public static final /* synthetic */ <T extends IrElement> T deepCopyWithSymbols(T t, IrDeclarationParent irDeclarationParent, Function2<? super SymbolRemapper, ? super TypeRemapper, ? extends DeepCopyIrTreeWithSymbols> createCopier) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(createCopier, "createCopier");
        DeepCopySymbolRemapper deepCopySymbolRemapper = new DeepCopySymbolRemapper(null, 1, null);
        IrVisitorsKt.acceptVoid(t, deepCopySymbolRemapper);
        T t2 = (T) PatchDeclarationParentsKt.patchDeclarationParents(t.transform(createCopier.invoke(deepCopySymbolRemapper, new DeepCopyTypeRemapper(deepCopySymbolRemapper)), null), irDeclarationParent);
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        return t2;
    }

    /* JADX INFO: renamed from: org.jetbrains.kotlin.ir.util.DeepCopyIrTreeWithSymbolsKt$deepCopyWithSymbols$2, reason: invalid class name */
    /* JADX INFO: compiled from: DeepCopyIrTreeWithSymbols.kt */
    @Metadata(k = 3, mv = {1, 9, 0}, xi = 176)
    public /* synthetic */ class AnonymousClass2 extends FunctionReference implements Function2<SymbolRemapper, TypeRemapper, DeepCopyIrTreeWithSymbols> {
        public static final AnonymousClass2 INSTANCE = new AnonymousClass2();

        public AnonymousClass2() {
            super(2);
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final String getSignature() {
            return "<init>(Lorg/jetbrains/kotlin/ir/util/SymbolRemapper;Lorg/jetbrains/kotlin/ir/util/TypeRemapper;)V";
        }

        @Override // kotlin.jvm.internal.CallableReference, kotlin.reflect.KCallable
        public final String getName() {
            return "<init>";
        }

        @Override // kotlin.jvm.internal.CallableReference
        public final KDeclarationContainer getOwner() {
            return Reflection.getOrCreateKotlinClass(DeepCopyIrTreeWithSymbols.class);
        }

        @Override // kotlin.jvm.functions.Function2
        public final DeepCopyIrTreeWithSymbols invoke(SymbolRemapper p0, TypeRemapper p1) {
            Intrinsics.checkNotNullParameter(p0, "p0");
            Intrinsics.checkNotNullParameter(p1, "p1");
            return new DeepCopyIrTreeWithSymbols(p0, p1);
        }
    }

    public static /* synthetic */ IrElement deepCopyWithSymbols$default(IrElement irElement, IrDeclarationParent irDeclarationParent, DeepCopySymbolRemapper symbolRemapper, Function2 createCopier, int i, Object obj) {
        if ((i & 4) != 0) {
            createCopier = AnonymousClass2.INSTANCE;
        }
        Intrinsics.checkNotNullParameter(irElement, "<this>");
        Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
        Intrinsics.checkNotNullParameter(createCopier, "createCopier");
        IrVisitorsKt.acceptVoid(irElement, symbolRemapper);
        IrElement irElementPatchDeclarationParents = PatchDeclarationParentsKt.patchDeclarationParents(irElement.transform((IrElementTransformer) createCopier.invoke(symbolRemapper, new DeepCopyTypeRemapper(symbolRemapper)), null), irDeclarationParent);
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        return irElementPatchDeclarationParents;
    }

    public static final /* synthetic */ <T extends IrElement> T deepCopyWithSymbols(T t, IrDeclarationParent irDeclarationParent, DeepCopySymbolRemapper symbolRemapper, Function2<? super SymbolRemapper, ? super TypeRemapper, ? extends DeepCopyIrTreeWithSymbols> createCopier) {
        Intrinsics.checkNotNullParameter(t, "<this>");
        Intrinsics.checkNotNullParameter(symbolRemapper, "symbolRemapper");
        Intrinsics.checkNotNullParameter(createCopier, "createCopier");
        IrVisitorsKt.acceptVoid(t, symbolRemapper);
        T t2 = (T) PatchDeclarationParentsKt.patchDeclarationParents(t.transform(createCopier.invoke(symbolRemapper, new DeepCopyTypeRemapper(symbolRemapper)), null), irDeclarationParent);
        Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
        return t2;
    }
}
