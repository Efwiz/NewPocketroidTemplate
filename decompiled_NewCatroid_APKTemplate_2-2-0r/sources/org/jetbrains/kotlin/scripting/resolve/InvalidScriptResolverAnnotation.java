package org.jetbrains.kotlin.scripting.resolve;

import java.lang.annotation.Annotation;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.PathUtil;

/* JADX INFO: compiled from: scriptAnnotationsPreprocessing.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\u0018\u00002\u00020\u00012\u00020\u0002B?\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u001e\u0010\u0005\u001a\u001a\u0012\u0014\u0012\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007\u0018\u00010\u0006\u0012\u0010\b\u0002\u0010\t\u001a\n\u0018\u00010\nj\u0004\u0018\u0001`\u000b¢\u0006\u0002\u0010\fJ\u0010\u0010\u0013\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00010\u0014H\u0016R)\u0010\u0005\u001a\u001a\u0012\u0014\u0012\u0012\u0012\u0006\u0012\u0004\u0018\u00010\u0004\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0019\u0010\t\u001a\n\u0018\u00010\nj\u0004\u0018\u0001`\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/scripting/resolve/InvalidScriptResolverAnnotation;", "", "Ljava/lang/annotation/Annotation;", "name", "", "annParams", "", "Lkotlin/Pair;", "", "error", "Ljava/lang/Exception;", "Lkotlin/Exception;", "(Ljava/lang/String;Ljava/util/List;Ljava/lang/Exception;)V", "getAnnParams", "()Ljava/util/List;", "getError", "()Ljava/lang/Exception;", "getName", "()Ljava/lang/String;", "annotationType", "Ljava/lang/Class;", PathUtil.KOTLIN_SCRIPTING_COMPILER_IMPL_NAME}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class InvalidScriptResolverAnnotation implements Annotation {
    private final List<Pair<String, Object>> annParams;
    private final Exception error;
    private final String name;

    @Override // java.lang.annotation.Annotation
    public Class<? extends Annotation> annotationType() {
        return InvalidScriptResolverAnnotation.class;
    }

    /* JADX WARN: Multi-variable type inference failed */
    public InvalidScriptResolverAnnotation(String name2, List<? extends Pair<String, ? extends Object>> list, Exception exc) {
        Intrinsics.checkNotNullParameter(name2, "name");
        this.name = name2;
        this.annParams = list;
        this.error = exc;
    }

    public /* synthetic */ InvalidScriptResolverAnnotation(String str, List list, Exception exc, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, list, (i & 4) != 0 ? null : exc);
    }

    public final List<Pair<String, Object>> getAnnParams() {
        return this.annParams;
    }

    public final Exception getError() {
        return this.error;
    }

    public final String getName() {
        return this.name;
    }
}
