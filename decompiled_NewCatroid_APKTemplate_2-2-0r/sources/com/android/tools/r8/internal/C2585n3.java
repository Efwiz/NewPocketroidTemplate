package com.android.tools.r8.internal;

import com.android.tools.r8.TextInputStream;
import com.android.tools.r8.profile.art.ArtProfileBuilder;
import com.android.tools.r8.startup.StartupClassBuilder;
import com.android.tools.r8.startup.StartupMethodBuilder;
import com.android.tools.r8.startup.StartupProfileBuilder;
import java.util.function.Consumer;

/* JADX INFO: renamed from: com.android.tools.r8.internal.n3, reason: case insensitive filesystem */
/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
final class C2585n3 implements ArtProfileBuilder {
    final /* synthetic */ StartupProfileBuilder a;

    C2585n3(StartupProfileBuilder startupProfileBuilder) {
        this.a = startupProfileBuilder;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C2669o3 c2669o3, StartupClassBuilder startupClassBuilder) {
        startupClassBuilder.setClassReference(c2669o3.a);
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileBuilder
    public final ArtProfileBuilder addClassRule(Consumer consumer) {
        final C2669o3 c2669o3 = new C2669o3();
        consumer.accept(c2669o3);
        this.a.addStartupClass(new Consumer() { // from class: com.android.tools.r8.internal.n3$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2585n3.a(c2669o3, (StartupClassBuilder) obj);
            }
        });
        return this;
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileBuilder
    public final ArtProfileBuilder addHumanReadableArtProfile(TextInputStream textInputStream, Consumer consumer) {
        throw new C3405y10();
    }

    @Override // com.android.tools.r8.profile.art.ArtProfileBuilder
    public final ArtProfileBuilder addMethodRule(Consumer consumer) {
        final C2751p3 c2751p3 = new C2751p3();
        consumer.accept(c2751p3);
        this.a.addStartupMethod(new Consumer() { // from class: com.android.tools.r8.internal.n3$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                C2585n3.a(c2751p3, (StartupMethodBuilder) obj);
            }
        });
        return this;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(C2751p3 c2751p3, StartupMethodBuilder startupMethodBuilder) {
        startupMethodBuilder.setMethodReference(c2751p3.a);
    }
}
