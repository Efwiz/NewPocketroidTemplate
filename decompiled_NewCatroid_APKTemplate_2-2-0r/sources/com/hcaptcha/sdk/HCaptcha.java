package com.hcaptcha.sdk;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.util.AndroidRuntimeException;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentActivity;
import com.hcaptcha.sdk.journeylitics.InMemorySink;
import com.hcaptcha.sdk.journeylitics.JLConfig;
import com.hcaptcha.sdk.journeylitics.JLEvent;
import com.hcaptcha.sdk.journeylitics.Journeylitics;
import com.hcaptcha.sdk.tasks.Task;
import java.util.List;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public final class HCaptcha extends Task<HCaptchaTokenResponse> implements IHCaptcha {
    public static final String META_SITE_KEY = "com.hcaptcha.sdk.site-key";
    private final Activity activity;
    private IHCaptchaVerifier captchaVerifier;
    private HCaptchaConfig config;
    private ViewGroup embeddedContainer;
    private final HCaptchaInternalConfig internalConfig;
    private InMemorySink journeySink;

    private HCaptcha(Activity activity, HCaptchaInternalConfig hCaptchaInternalConfig) {
        this.activity = activity;
        this.internalConfig = hCaptchaInternalConfig;
    }

    public static HCaptcha getClient(Activity activity) {
        return new HCaptcha(activity, HCaptchaInternalConfig.builder().build());
    }

    private HCaptcha startVerification() {
        HCaptchaLog.d("HCaptcha.startVerification");
        return startVerification(null);
    }

    @Override // com.hcaptcha.sdk.IHCaptcha
    public void destroy() {
        IHCaptchaVerifier iHCaptchaVerifier = this.captchaVerifier;
        if (iHCaptchaVerifier != null) {
            iHCaptchaVerifier.destroy();
            this.captchaVerifier = null;
        }
        stopEvents();
    }

    @Override // com.hcaptcha.sdk.IHCaptcha
    public void reset() {
        IHCaptchaVerifier iHCaptchaVerifier = this.captchaVerifier;
        if (iHCaptchaVerifier != null) {
            iHCaptchaVerifier.reset();
            this.captchaVerifier = null;
        }
        stopEvents();
    }

    @Override // com.hcaptcha.sdk.IHCaptcha
    public void stopEvents() {
        if (this.journeySink != null) {
            if (Journeylitics.isStarted()) {
                Journeylitics.removeSink(this.journeySink);
            }
            this.journeySink.clearEvents();
            this.journeySink = null;
        }
    }

    public static HCaptcha getClient(Activity activity, HCaptchaInternalConfig hCaptchaInternalConfig) {
        return new HCaptcha(activity, hCaptchaInternalConfig);
    }

    @Override // com.hcaptcha.sdk.IHCaptcha
    public HCaptcha setEmbeddedContainer(ViewGroup viewGroup) {
        IHCaptchaVerifier iHCaptchaVerifier;
        if (this.embeddedContainer != viewGroup && (iHCaptchaVerifier = this.captchaVerifier) != null) {
            iHCaptchaVerifier.reset();
            this.captchaVerifier = null;
        }
        this.embeddedContainer = viewGroup;
        return this;
    }

    private HCaptcha startVerification(HCaptchaVerifyParams hCaptchaVerifyParams) {
        HCaptchaLog.d("HCaptcha.startVerification".concat(hCaptchaVerifyParams != null ? " with params" : ""));
        this.handler.removeCallbacksAndMessages(null);
        if (this.captchaVerifier == null) {
            setException(new HCaptchaException(HCaptchaError.ERROR));
            return this;
        }
        InMemorySink inMemorySink = this.journeySink;
        if (inMemorySink != null) {
            List<JLEvent> events = inMemorySink.getEvents();
            if (!events.isEmpty()) {
                if (hCaptchaVerifyParams == null) {
                    hCaptchaVerifyParams = HCaptchaVerifyParams.builder().userJourney(events).build();
                } else {
                    hCaptchaVerifyParams = hCaptchaVerifyParams.toBuilder().userJourney(events).build();
                }
            }
        }
        this.captchaVerifier.startVerification(this.activity, hCaptchaVerifyParams);
        return this;
    }

    @Override // com.hcaptcha.sdk.IHCaptcha
    public HCaptcha setup() {
        try {
            String string = HCaptchaCompat.getApplicationInfo(this.activity).metaData.getString(META_SITE_KEY);
            if (string != null) {
                return setup(string);
            }
            throw new IllegalStateException("The site-key is missing. You can pass it by adding com.hcaptcha.sdk.site-key as meta-data to AndroidManifest.xml or as an argument for setup/verifyWithHCaptcha methods.");
        } catch (PackageManager.NameNotFoundException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override // com.hcaptcha.sdk.IHCaptcha
    public HCaptcha verifyWithHCaptcha() {
        if (this.captchaVerifier == null) {
            setup();
        }
        return startVerification();
    }

    @Override // com.hcaptcha.sdk.IHCaptcha
    public HCaptcha verifyWithHCaptcha(String str) {
        HCaptchaConfig hCaptchaConfig;
        if (this.captchaVerifier == null || (hCaptchaConfig = this.config) == null || !str.equals(hCaptchaConfig.getSiteKey())) {
            setup(str);
        }
        return startVerification();
    }

    @Override // com.hcaptcha.sdk.IHCaptcha
    public HCaptcha setup(String str) {
        return setup(HCaptchaConfig.builder().siteKey(str).build());
    }

    @Override // com.hcaptcha.sdk.IHCaptcha
    public HCaptcha setup(final HCaptchaConfig hCaptchaConfig) {
        HCaptchaLog.sDiagnosticsLogEnabled = hCaptchaConfig.getDiagnosticLog().booleanValue();
        HCaptchaLog.d("HCaptcha.setup");
        HCaptchaStateListener hCaptchaStateListener = new HCaptchaStateListener() { // from class: com.hcaptcha.sdk.HCaptcha.1
            @Override // com.hcaptcha.sdk.HCaptchaStateListener
            public void onFailure(HCaptchaException hCaptchaException) {
                HCaptchaLog.d("HCaptcha.onFailure");
                HCaptcha.this.setException(hCaptchaException);
            }

            @Override // com.hcaptcha.sdk.HCaptchaStateListener
            public void onLoaded() {
                HCaptcha.this.captchaLoaded();
            }

            @Override // com.hcaptcha.sdk.HCaptchaStateListener
            public void onOpen() {
                HCaptcha.this.captchaOpened();
            }

            @Override // com.hcaptcha.sdk.HCaptchaStateListener
            public void onSuccess(String str) {
                HCaptchaLog.d("HCaptcha.onSuccess");
                if (HCaptcha.this.journeySink != null) {
                    HCaptcha.this.journeySink.clearEvents();
                }
                HCaptcha.this.scheduleCaptchaExpired(hCaptchaConfig.getTokenExpiration());
                HCaptcha hCaptcha = HCaptcha.this;
                hCaptcha.setResult(new HCaptchaTokenResponse(str, ((Task) hCaptcha).handler));
            }
        };
        try {
            Boolean bool = Boolean.TRUE;
            if (bool.equals(hCaptchaConfig.getUserJourney())) {
                if (this.journeySink == null) {
                    this.journeySink = new InMemorySink();
                }
                if (Journeylitics.isStarted()) {
                    Journeylitics.addSink(this.journeySink);
                } else {
                    Journeylitics.start(this.activity, new JLConfig(this.journeySink));
                }
            } else if (this.journeySink != null) {
                if (Journeylitics.isStarted()) {
                    Journeylitics.removeSink(this.journeySink);
                }
                this.journeySink = null;
            }
            boolean zIsHeadlessMode = hCaptchaConfig.isHeadlessMode();
            if (bool.equals(hCaptchaConfig.getHideDialog())) {
                HCaptchaLog.w("Config.hideDialog is deprecated. Use renderMode=HEADLESS instead.");
            }
            if (zIsHeadlessMode) {
                HCaptchaConfig hCaptchaConfigBuild = hCaptchaConfig.toBuilder().renderMode(HCaptchaRenderMode.HEADLESS).size(HCaptchaSize.INVISIBLE).loading(Boolean.FALSE).build();
                this.config = hCaptchaConfigBuild;
                this.captchaVerifier = new HCaptchaHeadlessWebView(this.activity, hCaptchaConfigBuild, this.internalConfig, hCaptchaStateListener);
                return this;
            }
            if (hCaptchaConfig.getRenderMode() == HCaptchaRenderMode.EMBEDDED) {
                ViewGroup viewGroup = this.embeddedContainer;
                if (viewGroup != null) {
                    this.captchaVerifier = new HCaptchaEmbeddedView(this.activity, hCaptchaConfig, this.internalConfig, hCaptchaStateListener, viewGroup);
                    this.config = hCaptchaConfig;
                    return this;
                }
                throw new IllegalStateException("renderMode=EMBEDDED requires an embedded container. Call setEmbeddedContainer(...) first.");
            }
            Activity activity = this.activity;
            if (activity instanceof FragmentActivity) {
                this.captchaVerifier = HCaptchaDialogFragment.newInstance(activity, hCaptchaConfig, this.internalConfig, hCaptchaStateListener);
                this.config = hCaptchaConfig;
                return this;
            }
            throw new IllegalStateException("Visual hCaptcha challenge verification requires FragmentActivity.");
        } catch (AndroidRuntimeException unused) {
            hCaptchaStateListener.onFailure(new HCaptchaException(HCaptchaError.ERROR));
            return this;
        }
    }

    @Override // com.hcaptcha.sdk.IHCaptcha
    public HCaptcha verifyWithHCaptcha(HCaptchaConfig hCaptchaConfig) {
        if (this.captchaVerifier == null || !hCaptchaConfig.equals(this.config)) {
            setup(hCaptchaConfig);
        }
        return startVerification();
    }

    @Override // com.hcaptcha.sdk.IHCaptcha
    public HCaptcha verifyWithHCaptcha(HCaptchaVerifyParams hCaptchaVerifyParams) {
        if (this.captchaVerifier == null) {
            setup();
        }
        return startVerification(hCaptchaVerifyParams);
    }

    @Override // com.hcaptcha.sdk.IHCaptcha
    public HCaptcha verifyWithHCaptcha(HCaptchaConfig hCaptchaConfig, HCaptchaVerifyParams hCaptchaVerifyParams) {
        if (this.captchaVerifier == null || !hCaptchaConfig.equals(this.config)) {
            setup(hCaptchaConfig);
        }
        return startVerification(hCaptchaVerifyParams);
    }
}
