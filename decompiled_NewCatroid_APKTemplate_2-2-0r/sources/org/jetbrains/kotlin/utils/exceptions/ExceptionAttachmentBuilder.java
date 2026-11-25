package org.jetbrains.kotlin.utils.exceptions;

import androidx.exifinterface.media.ExifInterface;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.kotlin.utils.SmartPrinter;

/* JADX INFO: compiled from: ExceptionAttachmentBuilder.kt */
/* JADX INFO: loaded from: classes6.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\u0018\u0000 \u00142\u00020\u0001:\u0001\u0014B\u0005¢\u0006\u0002\u0010\u0002J\u0006\u0010\u0005\u001a\u00020\u0006J:\u0010\u0007\u001a\u00020\b\"\u0004\b\u0000\u0010\t2\u0006\u0010\n\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u0002H\t2\u0014\u0010\f\u001a\u0010\u0012\u0006\u0012\u0004\b\u0002H\t\u0012\u0004\u0012\u00020\u00060\rø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\b\u0010\u000b\u001a\u0004\u0018\u00010\u0006J'\u0010\u0007\u001a\u00020\b2\u0006\u0010\n\u001a\u00020\u00062\u0017\u0010\u000f\u001a\u0013\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\r¢\u0006\u0002\b\u0010J'\u0010\u0011\u001a\u00020\b2\u0006\u0010\u0012\u001a\u00020\u00062\u0017\u0010\u0013\u001a\u0013\u0012\u0004\u0012\u00020\u0000\u0012\u0004\u0012\u00020\b0\r¢\u0006\u0002\b\u0010R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u0004¢\u0006\u0002\n\u0000\u0082\u0002\u0004\n\u0002\b9¨\u0006\u0015"}, d2 = {"Lorg/jetbrains/kotlin/utils/exceptions/ExceptionAttachmentBuilder;", "", "()V", "printer", "Lorg/jetbrains/kotlin/utils/SmartPrinter;", "buildString", "", "withEntry", "", ExifInterface.GPS_DIRECTION_TRUE, "name", "value", "render", "Lkotlin/Function1;", "(Ljava/lang/String;Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "buildValue", "Lkotlin/ExtensionFunctionType;", "withEntryGroup", "groupName", JsonPOJOBuilder.DEFAULT_BUILD_METHOD, "Companion", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class ExceptionAttachmentBuilder {
    private static final Companion Companion = new Companion(null);
    private final SmartPrinter printer = new SmartPrinter(new StringBuilder(), null, 2, null);

    public final <T> void withEntry(String name2, final T value, final Function1<? super T, String> render) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(render, "render");
        withEntry(name2, new Function1<SmartPrinter, Unit>() { // from class: org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder.withEntry.1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            /* JADX WARN: Multi-variable type inference failed */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            public /* bridge */ /* synthetic */ Unit invoke(SmartPrinter smartPrinter) {
                invoke2(smartPrinter);
                return Unit.INSTANCE;
            }

            /* JADX WARN: Type inference fix 'apply assigned field type' failed
            java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
            	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
            	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
            	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
            	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
             */
            /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
            public final void invoke2(SmartPrinter withEntry) {
                String strInvoke;
                Intrinsics.checkNotNullParameter(withEntry, "$this$withEntry");
                StringBuilder sb = new StringBuilder("Class: ");
                T t = value;
                String name3 = t != 0 ? t.getClass().getName() : null;
                String str = "<null>";
                if (name3 == null) {
                    name3 = "<null>";
                }
                withEntry.println(sb.append(name3).toString());
                withEntry.println("Value:");
                T t2 = value;
                Function1<T, String> function1 = render;
                withEntry.pushIndent();
                if (t2 != 0 && (strInvoke = function1.invoke(t2)) != null) {
                    str = strInvoke;
                }
                withEntry.println(str);
                withEntry.popIndent();
            }
        });
    }

    public final void withEntry(String name2, String value) {
        Intrinsics.checkNotNullParameter(name2, "name");
        SmartPrinter smartPrinter = this.printer;
        smartPrinter.println("- " + name2 + ':');
        smartPrinter.pushIndent();
        if (value == null) {
            value = "<null>";
        }
        smartPrinter.println(value);
        smartPrinter.popIndent();
        smartPrinter.println("========");
    }

    public final void withEntry(String name2, Function1<? super SmartPrinter, Unit> buildValue) {
        Intrinsics.checkNotNullParameter(name2, "name");
        Intrinsics.checkNotNullParameter(buildValue, "buildValue");
        SmartPrinter smartPrinter = new SmartPrinter(new StringBuilder(), null, 2, null);
        buildValue.invoke(smartPrinter);
        withEntry(name2, smartPrinter.toString());
    }

    public final void withEntryGroup(String groupName, Function1<? super ExceptionAttachmentBuilder, Unit> build) {
        Intrinsics.checkNotNullParameter(groupName, "groupName");
        Intrinsics.checkNotNullParameter(build, "build");
        ExceptionAttachmentBuilder exceptionAttachmentBuilder = new ExceptionAttachmentBuilder();
        build.invoke(exceptionAttachmentBuilder);
        withEntry(groupName, exceptionAttachmentBuilder, new Function1<ExceptionAttachmentBuilder, String>() { // from class: org.jetbrains.kotlin.utils.exceptions.ExceptionAttachmentBuilder.withEntryGroup.1
            @Override // kotlin.jvm.functions.Function1
            public final String invoke(ExceptionAttachmentBuilder it) {
                Intrinsics.checkNotNullParameter(it, "it");
                return it.buildString();
            }
        });
    }

    public final String buildString() {
        return this.printer.toString();
    }

    /* JADX INFO: compiled from: ExceptionAttachmentBuilder.kt */
    @Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0082\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T¢\u0006\u0002\n\u0000¨\u0006\u0005"}, d2 = {"Lorg/jetbrains/kotlin/utils/exceptions/ExceptionAttachmentBuilder$Companion;", "", "()V", "separator", "", "util"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }
}
