package com.android.tools.r8;

import com.android.tools.r8.dex.C0408c;
import com.android.tools.r8.graph.C0729q2;
import com.android.tools.r8.references.ClassReference;
import com.android.tools.r8.references.MethodReference;
import com.android.tools.r8.utils.A1;
import com.android.tools.r8.utils.C3945p;
import com.android.tools.r8.utils.y3;
import java.io.IOException;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public final class ResourceShrinker {

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class Builder extends BaseCommand.Builder<Command, Builder> {
        @Override // com.android.tools.r8.BaseCommand.Builder
        final BaseCommand c() {
            return new Command(a().a());
        }

        @Override // com.android.tools.r8.BaseCommand.Builder
        final BaseCommand.Builder d() {
            return this;
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public static final class Command extends BaseCommand {
        Command(C3945p c3945p) {
            super(c3945p);
        }

        @Override // com.android.tools.r8.BaseCommand
        final A1 b() {
            return new A1();
        }
    }

    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    public interface ReferenceChecker {
        default void endClassVisit(ClassReference classReference) {
        }

        default void endMethodVisit(MethodReference methodReference) {
        }

        void referencedInt(int i);

        void referencedMethod(String str, String str2, String str3);

        void referencedStaticField(String str, String str2);

        void referencedString(String str);

        boolean shouldProcess(String str);

        default void startClassVisit(ClassReference classReference) {
        }

        default void startMethodVisit(MethodReference methodReference) {
        }
    }

    public static void run(Command command, ReferenceChecker referenceChecker) throws ExecutionException, IOException {
        Iterator<C0729q2> it = new C0408c(command.a(), new A1(), new y3("resource shrinker analyzer")).a().d().iterator();
        while (it.hasNext()) {
            new b0(it.next(), referenceChecker).a();
        }
    }
}
