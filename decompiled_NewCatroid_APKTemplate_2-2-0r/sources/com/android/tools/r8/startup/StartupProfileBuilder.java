package com.android.tools.r8.startup;

import com.android.tools.r8.TextInputStream;
import com.android.tools.r8.profile.art.HumanReadableArtProfileParserBuilder;
import java.util.function.Consumer;

/* JADX INFO: compiled from: R8_8.1.56_756d1f50f618dd1c39c000f11defb367a21e9e866e3401b884be16c0950f6f79 */
/* JADX INFO: loaded from: classes7.dex */
public interface StartupProfileBuilder {
    StartupProfileBuilder addHumanReadableArtProfile(TextInputStream textInputStream, Consumer<HumanReadableArtProfileParserBuilder> consumer);

    StartupProfileBuilder addStartupClass(Consumer<StartupClassBuilder> consumer);

    StartupProfileBuilder addStartupMethod(Consumer<StartupMethodBuilder> consumer);
}
