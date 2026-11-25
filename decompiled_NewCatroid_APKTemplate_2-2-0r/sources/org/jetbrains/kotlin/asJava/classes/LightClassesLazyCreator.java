package org.jetbrains.kotlin.asJava.classes;

import androidx.exifinterface.media.ExifInterface;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import kotlin.Lazy;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.com.intellij.openapi.project.Project;
import org.jetbrains.kotlin.com.intellij.psi.impl.PsiCachedValueImpl;

/* JADX INFO: compiled from: LightClassesLazyCreator.kt */
/* JADX INFO: loaded from: classes2.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\u0018\u0000 \r2\u00020\u0001:\u0001\rB\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J4\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\b\b\u0000\u0010\u0007*\u00020\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u0002H\u00070\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\fH\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004¢\u0006\u0002\n\u0000¨\u0006\u000e"}, d2 = {"Lorg/jetbrains/kotlin/asJava/classes/LightClassesLazyCreator;", "Lorg/jetbrains/kotlin/asJava/classes/KotlinClassInnerStuffCache$LazyCreator;", "project", "Lorg/jetbrains/kotlin/com/intellij/openapi/project/Project;", "(Lcom/intellij/openapi/project/Project;)V", "get", "Lkotlin/Lazy;", ExifInterface.GPS_DIRECTION_TRUE, "", "initializer", "Lkotlin/Function0;", "dependencies", "", "Companion", "light-classes-base"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class LightClassesLazyCreator extends KotlinClassInnerStuffCache.LazyCreator {
    private static final ThreadLocal<Boolean> initIsRunning;
    private final Project project;

    public LightClassesLazyCreator(Project project) {
        Intrinsics.checkNotNullParameter(project, "project");
        this.project = project;
    }

    @Override // org.jetbrains.kotlin.asJava.classes.KotlinClassInnerStuffCache.LazyCreator
    public <T> Lazy<T> get(Function0<? extends T> initializer, List<? extends Object> dependencies) {
        Intrinsics.checkNotNullParameter(initializer, "initializer");
        Intrinsics.checkNotNullParameter(dependencies, "dependencies");
        return new Lazy<T>(this, initializer, dependencies) { // from class: org.jetbrains.kotlin.asJava.classes.LightClassesLazyCreator.get.1
            private final Lazy<PsiCachedValueImpl<T>> holder;
            private final ReentrantLock lock = new ReentrantLock();

            private final T computeValue() {
                T value = this.holder.getValue().getValue();
                if (value != null) {
                    return value;
                }
                throw new IllegalStateException("holder has not null in initializer".toString());
            }

            {
                this.holder = ImplUtilsKt.lazyPub(new LightClassesLazyCreator$get$1$holder$1(this, initializer, dependencies));
            }

            @Override // kotlin.Lazy
            public T getValue() {
                if (!this.holder.getValue().hasUpToDateValue()) {
                    if (!((Boolean) LightClassesLazyCreator.initIsRunning.get()).booleanValue() && this.lock.tryLock(5L, TimeUnit.SECONDS)) {
                        try {
                            LightClassesLazyCreator.initIsRunning.set(true);
                            try {
                                T tComputeValue = computeValue();
                                LightClassesLazyCreator.initIsRunning.set(false);
                                this.lock.unlock();
                                return tComputeValue;
                            } catch (Throwable th) {
                                LightClassesLazyCreator.initIsRunning.set(false);
                                throw th;
                            }
                        } catch (Throwable th2) {
                            this.lock.unlock();
                            throw th2;
                        }
                    }
                    return computeValue();
                }
                return computeValue();
            }

            @Override // kotlin.Lazy
            public boolean isInitialized() {
                return this.holder.isInitialized();
            }
        };
    }

    static {
        ThreadLocal<Boolean> threadLocalWithInitial = ThreadLocal.withInitial(new Supplier() { // from class: org.jetbrains.kotlin.asJava.classes.LightClassesLazyCreator$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return LightClassesLazyCreator.initIsRunning$lambda$0();
            }
        });
        Intrinsics.checkNotNullExpressionValue(threadLocalWithInitial, "withInitial(...)");
        initIsRunning = threadLocalWithInitial;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static final Boolean initIsRunning$lambda$0() {
        return false;
    }
}
