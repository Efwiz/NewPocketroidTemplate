package org.jetbrains.kotlin.fir.types.jvm;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.HttpUrl;
import org.eclipse.jdt.internal.compiler.util.SuffixConstants;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.builtins.PrimitiveType;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;
import org.jetbrains.kotlin.load.java.structure.JavaArrayType;
import org.jetbrains.kotlin.load.java.structure.JavaClassifierType;
import org.jetbrains.kotlin.load.java.structure.JavaPrimitiveType;
import org.jetbrains.kotlin.load.java.structure.JavaType;
import org.jetbrains.kotlin.name.Name;

/* JADX INFO: compiled from: FirJavaTypeRef.kt */
/* JADX INFO: loaded from: classes4.dex */
@Metadata(d1 = {"\u0000$\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\u0018\u0002\n\u0000\u001a%\u0010\u0000\u001a\u00020\u00012\u0017\u0010\u0002\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\u00050\u0003¢\u0006\u0002\b\u0006H\u0086\bø\u0001\u0000\u001a\u000e\u0010\u0007\u001a\u00020\b*\u0004\u0018\u00010\tH\u0002\u0082\u0002\u0007\n\u0005\b\u009920\u0001¨\u0006\n"}, d2 = {"buildJavaTypeRef", "Lorg/jetbrains/kotlin/fir/types/jvm/FirJavaTypeRef;", ConfigConstants.CONFIG_INIT_SECTION, "Lkotlin/Function1;", "Lorg/jetbrains/kotlin/fir/types/jvm/FirJavaTypeRefBuilder;", "", "Lkotlin/ExtensionFunctionType;", "render", "", "Lorg/jetbrains/kotlin/load/java/structure/JavaType;", SuffixConstants.EXTENSION_java}, k = 2, mv = {1, 9, 0}, xi = 48)
public final class FirJavaTypeRefKt {
    public static final FirJavaTypeRef buildJavaTypeRef(Function1<? super FirJavaTypeRefBuilder, Unit> init) {
        Intrinsics.checkNotNullParameter(init, "init");
        FirJavaTypeRefBuilder firJavaTypeRefBuilder = new FirJavaTypeRefBuilder();
        init.invoke(firJavaTypeRefBuilder);
        return firJavaTypeRefBuilder.build();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final String render(JavaType javaType) {
        Name typeName;
        String identifier;
        if (javaType instanceof JavaArrayType) {
            return render(((JavaArrayType) javaType).getComponentType()) + HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        if (javaType instanceof JavaClassifierType) {
            JavaClassifierType javaClassifierType = (JavaClassifierType) javaType;
            if (javaClassifierType.mo7270getTypeArguments().isEmpty()) {
                return javaClassifierType.getClassifierQualifiedName();
            }
            return javaClassifierType.getClassifierQualifiedName() + CollectionsKt.joinToString$default(javaClassifierType.mo7270getTypeArguments(), ", ", "<", ">", 0, null, new Function1<JavaType, CharSequence>() { // from class: org.jetbrains.kotlin.fir.types.jvm.FirJavaTypeRefKt.render.1
                @Override // kotlin.jvm.functions.Function1
                public final CharSequence invoke(JavaType javaType2) {
                    return FirJavaTypeRefKt.render(javaType2);
                }
            }, 24, null);
        }
        if (!(javaType instanceof JavaPrimitiveType)) {
            return String.valueOf(javaType);
        }
        PrimitiveType type = ((JavaPrimitiveType) javaType).getType();
        return (type == null || (typeName = type.getTypeName()) == null || (identifier = typeName.getIdentifier()) == null) ? PsiKeyword.VOID : identifier;
    }
}
