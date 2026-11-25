package org.jetbrains.kotlin.container;

import java.lang.reflect.Type;
import kotlin.Metadata;

/* JADX INFO: compiled from: Resolve.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0012\u0010\u0002\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H&ø\u0001\u0000\u0082\u0002\u0006\n\u0004\b!0\u0001¨\u0006\u0006À\u0006\u0001"}, d2 = {"Lorg/jetbrains/kotlin/container/ValueResolveContext;", "", "resolve", "Lorg/jetbrains/kotlin/container/ValueDescriptor;", "registration", "Ljava/lang/reflect/Type;", "container"}, k = 1, mv = {1, 9, 0}, xi = 48)
public interface ValueResolveContext {
    ValueDescriptor resolve(Type registration);
}
