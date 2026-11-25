package org.jetbrains.kotlin.resolve.checkers;

import kotlin.Metadata;
import org.jetbrains.kotlin.resolve.RequireKotlinConstants;

/* JADX INFO: compiled from: KotlinVersionStringAnnotationValueChecker.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"Lorg/jetbrains/kotlin/resolve/checkers/RequireKotlinAnnotationValueChecker;", "Lorg/jetbrains/kotlin/resolve/checkers/KotlinVersionStringAnnotationValueChecker;", "()V", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class RequireKotlinAnnotationValueChecker extends KotlinVersionStringAnnotationValueChecker {
    public static final RequireKotlinAnnotationValueChecker INSTANCE = new RequireKotlinAnnotationValueChecker();

    private RequireKotlinAnnotationValueChecker() {
        super(RequireKotlinConstants.INSTANCE.getFQ_NAME());
    }
}
