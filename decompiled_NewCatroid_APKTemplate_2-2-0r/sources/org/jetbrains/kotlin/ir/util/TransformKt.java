package org.jetbrains.kotlin.ir.util;

import androidx.exifinterface.media.ExifInterface;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.ir.IrElement;
import org.jetbrains.kotlin.ir.IrElementBase;
import org.jetbrains.kotlin.ir.declarations.IrDeclaration;
import org.jetbrains.kotlin.ir.declarations.IrDeclarationContainer;
import org.jetbrains.kotlin.ir.visitors.IrElementTransformer;

/* JADX INFO: compiled from: transform.kt */
/* JADX INFO: loaded from: classes5.dex */
@Metadata(d1 = {"\u0000D\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010!\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0005\u001a0\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u00052\u0006\u0010\u0006\u001a\u00020\u0001H\u0001\u001a&\u0010\u0007\u001a\u00020\b*\u00020\t2\u001a\u0010\n\u001a\u0016\u0012\u0004\u0012\u00020\f\u0012\f\u0012\n\u0012\u0004\u0012\u00020\f\u0018\u00010\u00050\u000b\u001a8\u0010\r\u001a\u00020\b\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u001a\u0010\n\u001a\u0016\u0012\u0004\u0012\u0002H\u0002\u0012\f\u0012\n\u0012\u0004\u0012\u0002H\u0002\u0018\u00010\u00050\u000bH\u0086\bø\u0001\u0000\u001aA\u0010\u000e\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0005\"\b\b\u0000\u0010\u0002*\u00020\u000f\"\u0004\b\u0001\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00020\u00052\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00100\u00122\u0006\u0010\u0013\u001a\u0002H\u0010¢\u0006\u0002\u0010\u0014\u001a=\u0010\u0015\u001a\u00020\b\"\b\b\u0000\u0010\u0002*\u00020\u000f\"\u0004\b\u0001\u0010\u0010*\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u00162\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00100\u00122\u0006\u0010\u0013\u001a\u0002H\u0010¢\u0006\u0002\u0010\u0017\u001a6\u0010\u0015\u001a\u00020\b\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u000f*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0012\u0010\n\u001a\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u00020\u000f0\u000bH\u0086\bø\u0001\u0000\u001a;\u0010\u0015\u001a\u00020\b\"\b\b\u0000\u0010\u0002*\u00020\u000f\"\u0004\b\u0001\u0010\u0010*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u0002H\u00100\u00122\u0006\u0010\u0013\u001a\u0002H\u0010¢\u0006\u0002\u0010\u0018\u001aD\u0010\u0019\u001a\u00020\b\"\u0004\b\u0000\u0010\u0002\"\n\b\u0001\u0010\u001a\u0018\u0001*\u0002H\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u001a\u0010\n\u001a\u0016\u0012\u0004\u0012\u0002H\u001a\u0012\f\u0012\n\u0012\u0004\u0012\u0002H\u001a\u0018\u00010\u00050\u000bH\u0086\bø\u0001\u0000\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\u001b"}, d2 = {"replaceInPlace", "", ExifInterface.GPS_DIRECTION_TRUE, "", "transformed", "", "atIndex", "transformDeclarationsFlat", "", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclarationContainer;", "transformation", "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/ir/declarations/IrDeclaration;", "transformFlat", "transformIfNeeded", "Lorg/jetbrains/kotlin/ir/IrElement;", "D", "transformer", "Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;", "data", "(Ljava/util/List;Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)Ljava/util/List;", "transformInPlace", "", "([Lorg/jetbrains/kotlin/ir/IrElement;Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "(Ljava/util/List;Lorg/jetbrains/kotlin/ir/visitors/IrElementTransformer;Ljava/lang/Object;)V", "transformSubsetFlat", ExifInterface.LATITUDE_SOUTH, "ir.tree"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class TransformKt {
    /* JADX WARN: Multi-variable type inference failed */
    public static final /* synthetic */ <T extends IrElement> void transformInPlace(List<T> list, Function1<? super T, ? extends IrElement> transformation) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(transformation, "transformation");
        int size = list.size();
        for (int i = 0; i < size; i++) {
            IrElement irElementInvoke = transformation.invoke((Object) list.get(i));
            Intrinsics.reifiedOperationMarker(1, ExifInterface.GPS_DIRECTION_TRUE);
            list.set(i, irElementInvoke);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T extends IrElement, D> void transformInPlace(List<T> list, IrElementTransformer<? super D> transformer, D d) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Object obj = list.get(i);
            Intrinsics.checkNotNull(obj, "null cannot be cast to non-null type org.jetbrains.kotlin.ir.IrElementBase");
            IrElement irElementTransform = ((IrElementBase) obj).transform(transformer, d);
            Intrinsics.checkNotNull(irElementTransform, "null cannot be cast to non-null type T of org.jetbrains.kotlin.ir.util.TransformKt.transformInPlace");
            list.set(i, irElementTransform);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T extends IrElement, D> void transformInPlace(T[] tArr, IrElementTransformer<? super D> transformer, D d) {
        Intrinsics.checkNotNullParameter(tArr, "<this>");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        int length = tArr.length;
        for (int i = 0; i < length; i++) {
            IrElementBase irElementBase = (IrElementBase) tArr[i];
            if (irElementBase != null) {
                IrElement irElementTransform = irElementBase.transform(transformer, d);
                Intrinsics.checkNotNull(irElementTransform, "null cannot be cast to non-null type T of org.jetbrains.kotlin.ir.util.TransformKt.transformInPlace");
                tArr[i] = irElementTransform;
            }
        }
    }

    public static final <T> void transformFlat(List<T> list, Function1<? super T, ? extends List<? extends T>> transformation) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(transformation, "transformation");
        int iReplaceInPlace = 0;
        while (iReplaceInPlace < list.size()) {
            iReplaceInPlace = replaceInPlace(list, transformation.invoke(list.get(iReplaceInPlace)), iReplaceInPlace);
        }
    }

    public static final /* synthetic */ <T, S extends T> void transformSubsetFlat(List<T> list, Function1<? super S, ? extends List<? extends S>> transformation) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(transformation, "transformation");
        int iReplaceInPlace = 0;
        while (iReplaceInPlace < list.size()) {
            T t = list.get(iReplaceInPlace);
            Intrinsics.reifiedOperationMarker(3, ExifInterface.LATITUDE_SOUTH);
            iReplaceInPlace = !(t instanceof Object) ? iReplaceInPlace + 1 : replaceInPlace(list, transformation.invoke(t), iReplaceInPlace);
        }
    }

    public static final <T> int replaceInPlace(List<T> list, List<? extends T> list2, int i) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Integer numValueOf = list2 != null ? Integer.valueOf(list2.size()) : null;
        if (numValueOf == null) {
            return i + 1;
        }
        if (numValueOf.intValue() == 0) {
            list.remove(i);
            return i;
        }
        if (numValueOf.intValue() == 1) {
            int i2 = i + 1;
            list.set(i, list2.get(0));
            return i2;
        }
        list.addAll(i, list2);
        int size = i + list2.size();
        list.remove(size);
        return size;
    }

    public static final void transformDeclarationsFlat(IrDeclarationContainer irDeclarationContainer, Function1<? super IrDeclaration, ? extends List<? extends IrDeclaration>> transformation) {
        Intrinsics.checkNotNullParameter(irDeclarationContainer, "<this>");
        Intrinsics.checkNotNullParameter(transformation, "transformation");
        List<IrDeclaration> declarations = irDeclarationContainer.getDeclarations();
        int iReplaceInPlace = 0;
        while (iReplaceInPlace < declarations.size()) {
            List<? extends IrDeclaration> listInvoke = transformation.invoke(declarations.get(iReplaceInPlace));
            if (listInvoke != null) {
                Iterator<T> it = listInvoke.iterator();
                while (it.hasNext()) {
                    ((IrDeclaration) it.next()).setParent(irDeclarationContainer);
                }
            }
            iReplaceInPlace = replaceInPlace(declarations, listInvoke, iReplaceInPlace);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final <T extends IrElement, D> List<T> transformIfNeeded(List<? extends T> list, IrElementTransformer<? super D> transformer, D d) {
        Intrinsics.checkNotNullParameter(list, "<this>");
        Intrinsics.checkNotNullParameter(transformer, "transformer");
        Iterator it = list.iterator();
        ArrayList arrayList = null;
        int i = 0;
        while (it.hasNext()) {
            int i2 = i + 1;
            IrElement irElement = (IrElement) it.next();
            IrElement irElementTransform = irElement.transform(transformer, d);
            Intrinsics.checkNotNull(irElementTransform, "null cannot be cast to non-null type T of org.jetbrains.kotlin.ir.util.TransformKt.transformIfNeeded");
            if (irElementTransform != irElement && arrayList == null) {
                arrayList = new ArrayList(list);
            }
            if (arrayList != null) {
            }
            i = i2;
        }
        return arrayList != null ? arrayList : list;
    }
}
