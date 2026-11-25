package com.android.tools.r8;

import com.android.tools.r8.internal.AbstractC1639am;
import com.android.tools.r8.internal.C1507Xl;
import com.android.tools.r8.internal.OU;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.o, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
class C3674o implements ClassFileResourceProvider {
    final List<ClassFileResourceProvider> a;
    final HashSet b;

    /* JADX INFO: renamed from: com.android.tools.r8.o$a */
    /* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
    static class a {
        private final C1507Xl a = AbstractC1639am.g();
        boolean b = true;

        a() {
        }

        C3674o a() {
            return new C3674o(this.a.a());
        }

        final boolean b() {
            return this.b;
        }

        a a(ClassFileResourceProvider classFileResourceProvider) {
            this.a.a(classFileResourceProvider);
            this.b = false;
            return this;
        }
    }

    private C3674o(AbstractC1639am abstractC1639am) {
        this.b = OU.b();
        this.a = abstractC1639am;
        abstractC1639am.forEach(new Consumer() { // from class: com.android.tools.r8.o$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.a((ClassFileResourceProvider) obj);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void a(ClassFileResourceProvider classFileResourceProvider) {
        this.b.addAll(classFileResourceProvider.getClassDescriptors());
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public final Set getClassDescriptors() {
        return this.b;
    }

    @Override // com.android.tools.r8.ClassFileResourceProvider
    public final ProgramResource getProgramResource(String str) {
        for (ClassFileResourceProvider classFileResourceProvider : this.a) {
            if (classFileResourceProvider.getClassDescriptors().contains(str)) {
                return classFileResourceProvider.getProgramResource(str);
            }
        }
        return null;
    }

    static a a() {
        return new a();
    }
}
