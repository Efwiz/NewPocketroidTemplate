package org.jetbrains.kotlin.cli.jvm.compiler;

import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import org.jetbrains.kotlin.name.ClassId;
import org.jetbrains.kotlin.name.FqName;

/* JADX INFO: compiled from: KotlinCliJavaFileManagerImpl.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a'\u0010\u0000\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00010\u0004H\u0002¢\u0006\u0002\u0010\u0005\u001a\u000e\u0010\u0006\u001a\u0004\u0018\u00010\u0007*\u00020\bH\u0002\u001a\u000e\u0010\t\u001a\u0004\u0018\u00010\n*\u00020\bH\u0002¨\u0006\u000b"}, d2 = {"safely", ExifInterface.GPS_DIRECTION_TRUE, "", "compute", "Lkotlin/Function0;", "(Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "toSafeFqName", "Lorg/jetbrains/kotlin/name/FqName;", "", "toSafeTopLevelClassId", "Lorg/jetbrains/kotlin/name/ClassId;", "cli-base"}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class KotlinCliJavaFileManagerImplKt {
    private static final <T> T safely(Function0<? extends T> function0) {
        try {
            return function0.invoke();
        } catch (AssertionError | IllegalArgumentException unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final FqName toSafeFqName(final String str) {
        return (FqName) safely(new Function0<FqName>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImplKt.toSafeFqName.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final FqName invoke() {
                return new FqName(str);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final ClassId toSafeTopLevelClassId(final String str) {
        return (ClassId) safely(new Function0<ClassId>() { // from class: org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImplKt.toSafeTopLevelClassId.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final ClassId invoke() {
                return ClassId.topLevel(new FqName(str));
            }
        });
    }
}
