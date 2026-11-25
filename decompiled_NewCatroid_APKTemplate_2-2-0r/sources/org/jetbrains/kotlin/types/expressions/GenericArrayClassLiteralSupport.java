package org.jetbrains.kotlin.types.expressions;

import kotlin.Metadata;
import org.jetbrains.kotlin.container.DefaultImplementation;

/* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
/* JADX INFO: loaded from: classes6.dex */
@DefaultImplementation(impl = Disabled.class)
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001:\u0002\u0005\u0006R\u0012\u0010\u0002\u001a\u00020\u0003X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0002\u0010\u0004ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0007À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/GenericArrayClassLiteralSupport;", "", "isEnabled", "", "()Z", "Disabled", "Enabled", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface GenericArrayClassLiteralSupport {
    boolean isEnabled();

    /* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/GenericArrayClassLiteralSupport$Enabled;", "Lorg/jetbrains/kotlin/types/expressions/GenericArrayClassLiteralSupport;", "()V", "isEnabled", "", "()Z", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Enabled implements GenericArrayClassLiteralSupport {
        public static final Enabled INSTANCE = new Enabled();
        private static final boolean isEnabled = true;

        private Enabled() {
        }

        @Override // org.jetbrains.kotlin.types.expressions.GenericArrayClassLiteralSupport
        public boolean isEnabled() {
            return isEnabled;
        }
    }

    /* JADX INFO: compiled from: DoubleColonExpressionResolver.kt */
    @Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\bÆ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0014\u0010\u0003\u001a\u00020\u0004X\u0096D¢\u0006\b\n\u0000\u001a\u0004\b\u0003\u0010\u0005¨\u0006\u0006"}, d2 = {"Lorg/jetbrains/kotlin/types/expressions/GenericArrayClassLiteralSupport$Disabled;", "Lorg/jetbrains/kotlin/types/expressions/GenericArrayClassLiteralSupport;", "()V", "isEnabled", "", "()Z", "frontend"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Disabled implements GenericArrayClassLiteralSupport {
        public static final Disabled INSTANCE = new Disabled();
        private static final boolean isEnabled = false;

        private Disabled() {
        }

        @Override // org.jetbrains.kotlin.types.expressions.GenericArrayClassLiteralSupport
        public boolean isEnabled() {
            return isEnabled;
        }
    }
}
