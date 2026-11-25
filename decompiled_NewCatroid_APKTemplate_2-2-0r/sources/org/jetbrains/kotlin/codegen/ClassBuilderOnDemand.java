package org.jetbrains.kotlin.codegen;

import com.reandroid.dex.sections.Marker;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.storage.LockBasedStorageManager;
import org.jetbrains.kotlin.storage.NotNullLazyValue;
import org.mozilla.javascript.ES6Iterator;

/* JADX INFO: compiled from: ClassBuilderOnDemand.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u0018\u00002\u00020\u0001B\u0013\u0012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\u0002\u0010\u0005J\u0010\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\tH\u0016J\u0006\u0010\u000e\u001a\u00020\fJ\b\u0010\u000f\u001a\u00020\u0004H\u0014R\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00040\u0007X\u0082\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\b\u001a\u00020\t8F¢\u0006\u0006\u001a\u0004\b\b\u0010\n¨\u0006\u0010"}, d2 = {"Lorg/jetbrains/kotlin/codegen/ClassBuilderOnDemand;", "Lorg/jetbrains/kotlin/codegen/DelegatingClassBuilder;", "createClassBuilder", "Lkotlin/Function0;", "Lorg/jetbrains/kotlin/codegen/ClassBuilder;", "(Lkotlin/jvm/functions/Function0;)V", "classBuilder", "Lorg/jetbrains/kotlin/storage/NotNullLazyValue;", "isComputed", "", "()Z", ES6Iterator.DONE_PROPERTY, "", "generateSmapCopyToAnnotation", "ensureGenerated", "getDelegate", Marker.BACKEND}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ClassBuilderOnDemand extends DelegatingClassBuilder {
    private final NotNullLazyValue<ClassBuilder> classBuilder;

    public ClassBuilderOnDemand(Function0<? extends ClassBuilder> createClassBuilder) {
        Intrinsics.checkNotNullParameter(createClassBuilder, "createClassBuilder");
        this.classBuilder = LockBasedStorageManager.NO_LOCKS.createLazyValue(createClassBuilder);
    }

    public final boolean isComputed() {
        return this.classBuilder.isComputed();
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder
    protected ClassBuilder getDelegate() {
        return this.classBuilder.invoke();
    }

    public final void ensureGenerated() {
        this.classBuilder.invoke();
    }

    @Override // org.jetbrains.kotlin.codegen.DelegatingClassBuilder, org.jetbrains.kotlin.codegen.ClassBuilder
    public void done(boolean generateSmapCopyToAnnotation) {
        if (isComputed()) {
            this.classBuilder.invoke().done(generateSmapCopyToAnnotation);
        }
    }
}
