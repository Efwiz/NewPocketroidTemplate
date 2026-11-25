package org.jetbrains.kotlin.cli.jvm.compiler;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.jetbrains.kotlin.fir.FirElement;
import org.jetbrains.kotlin.fir.UtilsKt;
import org.jetbrains.kotlin.fir.declarations.FirFile;
import org.jetbrains.kotlin.fir.declarations.FirSimpleFunction;
import org.jetbrains.kotlin.fir.declarations.FirValueParameter;
import org.jetbrains.kotlin.fir.types.ArrayUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeBuiltinTypeUtilsKt;
import org.jetbrains.kotlin.fir.types.ConeKotlinType;
import org.jetbrains.kotlin.fir.types.FirTypeUtilsKt;
import org.jetbrains.kotlin.fir.visitors.FirVisitorVoid;
import org.jetbrains.kotlin.idea.KotlinFileType;
import org.jetbrains.kotlin.name.FqName;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirKotlinToJvmBytecodeCompiler.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\u001a\u0016\u0010\u0000\u001a\u0004\u0018\u00010\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¨\u0006\u0005"}, d2 = {"findMainClass", "Lorg/jetbrains/kotlin/name/FqName;", "fir", "", "Lorg/jetbrains/kotlin/fir/declarations/FirFile;", "cli"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirKotlinToJvmBytecodeCompilerKt {
    public static final FqName findMainClass(List<? extends FirFile> fir) {
        Intrinsics.checkNotNullParameter(fir, "fir");
        final ArrayList arrayList = new ArrayList();
        FirVisitorVoid firVisitorVoid = new FirVisitorVoid() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.FirKotlinToJvmBytecodeCompilerKt$findMainClass$visitor$1
            public FirFile file;

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            /* JADX INFO: renamed from: visitElement */
            public void mo6787visitElement(FirElement element) {
                Intrinsics.checkNotNullParameter(element, "element");
            }

            public final FirFile getFile() {
                FirFile firFile = this.file;
                if (firFile != null) {
                    return firFile;
                }
                Intrinsics.throwUninitializedPropertyAccessException("file");
                return null;
            }

            public final void setFile(FirFile firFile) {
                Intrinsics.checkNotNullParameter(firFile, "<set-?>");
                this.file = firFile;
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            public void visitFile(FirFile file) {
                Intrinsics.checkNotNullParameter(file, "file");
                setFile(file);
                file.acceptChildren(this);
            }

            @Override // org.jetbrains.kotlin.fir.visitors.FirVisitorVoid
            public void visitSimpleFunction(FirSimpleFunction simpleFunction) {
                ConeKotlinType coneKotlinTypeArrayElementType;
                Intrinsics.checkNotNullParameter(simpleFunction, "simpleFunction");
                if (Intrinsics.areEqual(simpleFunction.getName().asString(), "main") && simpleFunction.getTypeParameters().isEmpty()) {
                    int size = simpleFunction.getValueParameters().size();
                    if (size != 0) {
                        if (size != 1) {
                            return;
                        }
                        ConeKotlinType coneType = FirTypeUtilsKt.getConeType(((FirValueParameter) CollectionsKt.single((List) simpleFunction.getValueParameters())).getReturnTypeRef());
                        if (!ConeBuiltinTypeUtilsKt.isArrayType(coneType) || (coneKotlinTypeArrayElementType = ArrayUtilsKt.arrayElementType(coneType)) == null || !ConeBuiltinTypeUtilsKt.isString(coneKotlinTypeArrayElementType)) {
                            return;
                        }
                    }
                    List<FqName> list = arrayList;
                    List<Name> listPathSegments = UtilsKt.getPackageFqName(getFile()).pathSegments();
                    Intrinsics.checkNotNullExpressionValue(listPathSegments, "pathSegments(...)");
                    List<Name> list2 = listPathSegments;
                    ArrayList arrayList2 = new ArrayList(CollectionsKt.collectionSizeOrDefault(list2, 10));
                    Iterator<T> it = list2.iterator();
                    while (it.hasNext()) {
                        arrayList2.add(((Name) it.next()).asString());
                    }
                    list.add(FqName.fromSegments(CollectionsKt.plus((Collection<? extends String>) arrayList2, StringsKt.capitalize(StringsKt.removeSuffix(getFile().getName(), (CharSequence) KotlinFileType.DOT_DEFAULT_EXTENSION)) + "Kt")));
                }
            }
        };
        Iterator<T> it = fir.iterator();
        while (it.hasNext()) {
            ((FirFile) it.next()).accept(firVisitorVoid);
        }
        return (FqName) CollectionsKt.singleOrNull((List) arrayList);
    }
}
