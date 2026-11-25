package com.hcaptcha.sdk;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.BadParcelableException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.InflateException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.Window;
import android.widget.LinearLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

/* JADX INFO: compiled from: r8-map-id-071b77f92f575d77028d4852bce4d6e5f9d65cb60a14a6342bd357f80a49e8cf */
/* JADX INFO: loaded from: classes10.dex */
public final class HCaptchaDialogFragment extends DialogFragment implements IHCaptchaVerifier {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    static final String KEY_CONFIG = "hCaptchaConfig";
    static final String KEY_INTERNAL_CONFIG = "hCaptchaInternalConfig";
    static final String KEY_LISTENER = "hCaptchaDialogListener";
    private static final String TAG = "HCaptchaDialogFragment";
    private static HCaptchaWebView sPreloadWebView;
    private float defaultDimAmount;
    private HCaptchaStateListener listener;
    private LinearLayout loadingContainer;
    private boolean readyForInteraction;
    private boolean replayLoadedOnStart;
    private HCaptchaVerifyParams verifyParams;
    private HCaptchaWebViewHelper webViewHelper;
    private boolean webViewLoaded;

    public HCaptchaDialogFragment() {
        this.defaultDimAmount = 0.6f;
        this.readyForInteraction = false;
        this.webViewLoaded = false;
        this.replayLoadedOnStart = false;
    }

    private void hideLoadingContainer() {
        HCaptchaWebViewHelper hCaptchaWebViewHelper;
        if (this.loadingContainer != null && (hCaptchaWebViewHelper = this.webViewHelper) != null && Boolean.TRUE.equals(hCaptchaWebViewHelper.getConfig().getLoading())) {
            this.loadingContainer.animate().alpha(0.0f).setDuration(200L).setListener(new AnimatorListenerAdapter() { // from class: com.hcaptcha.sdk.HCaptchaDialogFragment.1
                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public void onAnimationEnd(Animator animator) {
                    if (HCaptchaDialogFragment.this.loadingContainer != null) {
                        HCaptchaDialogFragment.this.loadingContainer.setVisibility(8);
                    }
                }
            });
            return;
        }
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().addFlags(2);
            dialog.getWindow().setDimAmount(this.defaultDimAmount);
        }
    }

    public static HCaptchaDialogFragment newInstance(Context context, HCaptchaConfig hCaptchaConfig, HCaptchaInternalConfig hCaptchaInternalConfig, HCaptchaStateListener hCaptchaStateListener) {
        HCaptchaLog.d("DialogFragment.newInstance");
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_CONFIG, hCaptchaConfig);
        bundle.putSerializable(KEY_INTERNAL_CONFIG, hCaptchaInternalConfig);
        bundle.putParcelable(KEY_LISTENER, hCaptchaStateListener);
        HCaptchaDialogFragment hCaptchaDialogFragment = new HCaptchaDialogFragment(context, hCaptchaConfig, hCaptchaInternalConfig);
        hCaptchaDialogFragment.setArguments(bundle);
        hCaptchaDialogFragment.listener = hCaptchaStateListener;
        return hCaptchaDialogFragment;
    }

    private View prepareRootView(LayoutInflater layoutInflater, ViewGroup viewGroup, final HCaptchaConfig hCaptchaConfig) {
        View viewInflate = layoutInflater.inflate(R.layout.hcaptcha_fragment, viewGroup, false);
        viewInflate.setFocusableInTouchMode(true);
        viewInflate.requestFocus();
        viewInflate.setOnKeyListener(new View.OnKeyListener() { // from class: com.hcaptcha.sdk.HCaptchaDialogFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnKeyListener
            public final boolean onKey(View view, int i, KeyEvent keyEvent) {
                return this.f$0.m2666lambda$prepareRootView$0$comhcaptchasdkHCaptchaDialogFragment(hCaptchaConfig, view, i, keyEvent);
            }
        });
        return viewInflate;
    }

    private void prepareWebView(View view, HCaptchaConfig hCaptchaConfig) {
        View viewFindViewById = view.findViewById(R.id.webView);
        if (!(viewFindViewById instanceof ViewStub)) {
            HCaptchaLog.w("DialogFragment.prepareWebView invalid state " + viewFindViewById.getClass());
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int iIndexOfChild = viewGroup.indexOfChild(viewFindViewById);
        viewGroup.removeView(viewFindViewById);
        if (sPreloadWebView == null) {
            HCaptchaLog.w("DialogFragment.prepareWebView: sPreloadWebView cold init");
            sPreloadWebView = new HCaptchaWebView(requireContext());
        }
        if (sPreloadWebView.getParent() != null) {
            ((ViewGroup) sPreloadWebView.getParent()).removeView(sPreloadWebView);
        }
        viewGroup.addView(sPreloadWebView, iIndexOfChild);
        if (Boolean.FALSE.equals(hCaptchaConfig.getLoading())) {
            sPreloadWebView.setOnTouchListener(new View.OnTouchListener() { // from class: com.hcaptcha.sdk.HCaptchaDialogFragment$$ExternalSyntheticLambda0
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view2, MotionEvent motionEvent) {
                    return this.f$0.m2667lambda$prepareWebView$1$comhcaptchasdkHCaptchaDialogFragment(view2, motionEvent);
                }
            });
        }
    }

    @Override // com.hcaptcha.sdk.IHCaptchaVerifier
    public void destroy() {
        HCaptchaWebViewHelper hCaptchaWebViewHelper = this.webViewHelper;
        if (hCaptchaWebViewHelper != null) {
            hCaptchaWebViewHelper.destroy();
        }
        if (isAdded()) {
            dismissAllowingStateLoss();
        }
        sPreloadWebView = null;
    }

    /* JADX INFO: renamed from: lambda$prepareRootView$0$com-hcaptcha-sdk-HCaptchaDialogFragment, reason: not valid java name */
    /* synthetic */ boolean m2666lambda$prepareRootView$0$comhcaptchasdkHCaptchaDialogFragment(HCaptchaConfig hCaptchaConfig, View view, int i, KeyEvent keyEvent) {
        if (i == 4 && keyEvent.getAction() == 0) {
            if (!this.readyForInteraction && Boolean.FALSE.equals(hCaptchaConfig.getLoading())) {
                return true;
            }
            HCaptchaWebViewHelper hCaptchaWebViewHelper = this.webViewHelper;
            if (hCaptchaWebViewHelper != null && hCaptchaWebViewHelper.shouldRetry(new HCaptchaException(HCaptchaError.CHALLENGE_CLOSED))) {
                return true;
            }
        }
        return false;
    }

    /* JADX INFO: renamed from: lambda$prepareWebView$1$com-hcaptcha-sdk-HCaptchaDialogFragment, reason: not valid java name */
    /* synthetic */ boolean m2667lambda$prepareWebView$1$comhcaptchasdkHCaptchaDialogFragment(View view, MotionEvent motionEvent) {
        FragmentActivity activity;
        if (this.readyForInteraction || !isAdded() || (activity = getActivity()) == null) {
            return view.performClick();
        }
        activity.dispatchTouchEvent(motionEvent);
        return true;
    }

    @Override // androidx.fragment.app.DialogFragment, android.content.DialogInterface.OnCancelListener
    public void onCancel(DialogInterface dialogInterface) {
        HCaptchaLog.d("DialogFragment.onCancel");
        super.onCancel(dialogInterface);
        onFailure(new HCaptchaException(HCaptchaError.CHALLENGE_CLOSED));
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setStyle(2, R.style.HCaptchaDialogTheme);
        try {
            Bundle arguments = getArguments();
            this.listener = (HCaptchaStateListener) HCaptchaCompat.getParcelable(arguments, KEY_LISTENER, HCaptchaStateListener.class);
            HCaptchaConfig hCaptchaConfig = (HCaptchaConfig) HCaptchaCompat.getSerializable(arguments, KEY_CONFIG, HCaptchaConfig.class);
            HCaptchaInternalConfig hCaptchaInternalConfig = (HCaptchaInternalConfig) HCaptchaCompat.getSerializable(arguments, KEY_INTERNAL_CONFIG, HCaptchaInternalConfig.class);
            if (this.listener != null && hCaptchaConfig != null && hCaptchaInternalConfig != null) {
                if (sPreloadWebView == null) {
                    HCaptchaLog.w("DialogFragment.onCreate: sPreloadWebView cold init");
                    sPreloadWebView = new HCaptchaWebView(requireContext());
                }
                HCaptchaWebView hCaptchaWebView = sPreloadWebView;
                if (this.webViewHelper == null) {
                    this.webViewHelper = new HCaptchaWebViewHelper(new Handler(Looper.getMainLooper()), requireContext(), hCaptchaConfig, hCaptchaInternalConfig, this, hCaptchaWebView);
                    return;
                }
                return;
            }
            HCaptchaLog.w("DialogFragment.onCreate getArguments is incomplete");
            HCaptchaStateListener hCaptchaStateListener = this.listener;
            if (hCaptchaStateListener != null) {
                hCaptchaStateListener.onFailure(new HCaptchaException(HCaptchaError.ERROR));
            }
        } catch (BadParcelableException | AssertionError | ClassCastException unused) {
            HCaptchaLog.w("DialogFragment.onCreate: cannot create fragment");
            HCaptchaStateListener hCaptchaStateListener2 = this.listener;
            if (hCaptchaStateListener2 != null) {
                hCaptchaStateListener2.onFailure(new HCaptchaException(HCaptchaError.ERROR));
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        HCaptchaLog.d("DialogFragment.onCreateView");
        try {
            HCaptchaConfig hCaptchaConfig = (HCaptchaConfig) HCaptchaCompat.getSerializable(getArguments(), KEY_CONFIG, HCaptchaConfig.class);
            if (layoutInflater == null) {
                throw new InflateException("inflater is null");
            }
            View viewPrepareRootView = prepareRootView(layoutInflater, viewGroup, hCaptchaConfig);
            HCaptchaLog.d("DialogFragment.onCreateView inflated");
            prepareWebView(viewPrepareRootView, hCaptchaConfig);
            LinearLayout linearLayout = (LinearLayout) viewPrepareRootView.findViewById(R.id.loadingContainer);
            this.loadingContainer = linearLayout;
            if (linearLayout != null) {
                this.loadingContainer.setBackgroundColor(hCaptchaConfig.getTheme() == HCaptchaTheme.DARK ? ViewCompat.MEASURED_STATE_MASK : -1);
                this.loadingContainer.setVisibility((!Boolean.TRUE.equals(hCaptchaConfig.getLoading()) || this.readyForInteraction) ? 8 : 0);
            }
            return viewPrepareRootView;
        } catch (BadParcelableException | InflateException | AssertionError | ClassCastException unused) {
            HCaptchaLog.w("Cannot create view. Dismissing dialog...");
            dismiss();
            HCaptchaStateListener hCaptchaStateListener = this.listener;
            if (hCaptchaStateListener == null) {
                return null;
            }
            hCaptchaStateListener.onFailure(new HCaptchaException(HCaptchaError.ERROR));
            return null;
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        HCaptchaLog.d("DialogFragment.onDestroy");
        super.onDestroy();
        this.readyForInteraction = false;
        this.replayLoadedOnStart = this.webViewLoaded;
        HCaptchaWebViewHelper hCaptchaWebViewHelper = this.webViewHelper;
        if (hCaptchaWebViewHelper != null) {
            hCaptchaWebViewHelper.reset();
        }
    }

    @Override // com.hcaptcha.sdk.tasks.OnFailureListener
    public void onFailure(HCaptchaException hCaptchaException) {
        HCaptchaWebViewHelper hCaptchaWebViewHelper = this.webViewHelper;
        boolean z = hCaptchaWebViewHelper != null && hCaptchaWebViewHelper.shouldRetry(hCaptchaException);
        if (isAdded() && !z) {
            dismissAllowingStateLoss();
        }
        HCaptchaWebViewHelper hCaptchaWebViewHelper2 = this.webViewHelper;
        if (hCaptchaWebViewHelper2 != null) {
            if (z) {
                hCaptchaWebViewHelper2.resetAndExecute(this.verifyParams);
                return;
            }
            HCaptchaStateListener hCaptchaStateListener = this.listener;
            if (hCaptchaStateListener != null) {
                hCaptchaStateListener.onFailure(hCaptchaException);
            } else {
                HCaptchaLog.w("Dialog wasn't created, listener == null");
            }
        }
    }

    @Override // com.hcaptcha.sdk.tasks.OnLoadedListener
    public void onLoaded() {
        if (this.webViewHelper == null) {
            HCaptchaLog.w("DialogFragment.onLoaded webViewHelper == null, likely about to destroy");
            return;
        }
        this.webViewLoaded = true;
        HCaptchaStateListener hCaptchaStateListener = this.listener;
        if (hCaptchaStateListener != null) {
            hCaptchaStateListener.onLoaded();
        }
        HCaptchaConfig config = this.webViewHelper.getConfig();
        boolean z = config.getSize() == HCaptchaSize.INVISIBLE;
        if (!z) {
            this.readyForInteraction = true;
            hideLoadingContainer();
        }
        if (this.webViewHelper != null) {
            if (!z || config.isHeadlessMode()) {
                this.webViewHelper.setVerifyParams(this.verifyParams);
            } else {
                this.webViewHelper.resetAndExecute(this.verifyParams);
            }
        }
    }

    @Override // com.hcaptcha.sdk.tasks.OnOpenListener
    public void onOpen() {
        HCaptchaWebViewHelper hCaptchaWebViewHelper = this.webViewHelper;
        if (hCaptchaWebViewHelper == null) {
            HCaptchaLog.w("DialogFragment.onOpen webViewHelper == null, likely about to destroy");
            return;
        }
        if (hCaptchaWebViewHelper.getConfig().getSize() == HCaptchaSize.INVISIBLE) {
            hideLoadingContainer();
        }
        this.readyForInteraction = true;
        HCaptchaStateListener hCaptchaStateListener = this.listener;
        if (hCaptchaStateListener != null) {
            hCaptchaStateListener.onOpen();
        } else {
            HCaptchaLog.w("DialogFragment.hideLoadingContainer: dialog was not created, listener == null");
        }
    }

    @Override // androidx.fragment.app.DialogFragment, androidx.fragment.app.Fragment
    public void onStart() {
        HCaptchaLog.d("DialogFragment.onStart");
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && this.webViewHelper != null) {
            Window window = dialog.getWindow();
            window.setBackgroundDrawable(new ColorDrawable(0));
            this.defaultDimAmount = window.getAttributes().dimAmount;
            if (!this.readyForInteraction && Boolean.FALSE.equals(this.webViewHelper.getConfig().getLoading())) {
                window.clearFlags(2);
                window.setDimAmount(0.0f);
            }
        }
        if (this.readyForInteraction || this.webViewHelper == null || !this.replayLoadedOnStart) {
            return;
        }
        this.replayLoadedOnStart = false;
        HCaptchaLog.d("DialogFragment.onStart: re-triggering onLoaded after reset");
        onLoaded();
    }

    @Override // com.hcaptcha.sdk.IHCaptchaVerifier
    public void reset() {
        HCaptchaWebViewHelper hCaptchaWebViewHelper = this.webViewHelper;
        if (hCaptchaWebViewHelper != null) {
            hCaptchaWebViewHelper.reset();
        }
        this.webViewLoaded = false;
        this.replayLoadedOnStart = false;
        if (isAdded()) {
            dismissAllowingStateLoss();
        }
        sPreloadWebView = null;
    }

    @Override // com.hcaptcha.sdk.IHCaptchaVerifier
    public void startVerification(Activity activity, HCaptchaVerifyParams hCaptchaVerifyParams) {
        HCaptchaWebViewHelper hCaptchaWebViewHelper;
        this.verifyParams = hCaptchaVerifyParams;
        if (this.readyForInteraction && (hCaptchaWebViewHelper = this.webViewHelper) != null) {
            hCaptchaWebViewHelper.setVerifyParams(hCaptchaVerifyParams);
        }
        FragmentManager supportFragmentManager = ((FragmentActivity) activity).getSupportFragmentManager();
        String str = TAG;
        Fragment fragmentFindFragmentByTag = supportFragmentManager.findFragmentByTag(str);
        if (fragmentFindFragmentByTag != null && fragmentFindFragmentByTag.isAdded()) {
            HCaptchaLog.w("DialogFragment was already added.");
            return;
        }
        if (supportFragmentManager.isStateSaved()) {
            HCaptchaLog.w("FragmentActivity is in state saved");
            return;
        }
        try {
            show(supportFragmentManager, str);
        } catch (IllegalStateException e) {
            HCaptchaLog.w("DialogFragment.startVerification " + e.getMessage());
            HCaptchaStateListener hCaptchaStateListener = this.listener;
            if (hCaptchaStateListener != null) {
                hCaptchaStateListener.onFailure(new HCaptchaException(HCaptchaError.ERROR));
            }
        }
    }

    @Override // com.hcaptcha.sdk.tasks.OnSuccessListener
    public void onSuccess(String str) {
        if (isAdded()) {
            dismissAllowingStateLoss();
        }
        HCaptchaStateListener hCaptchaStateListener = this.listener;
        if (hCaptchaStateListener != null) {
            hCaptchaStateListener.onSuccess(str);
        } else {
            HCaptchaLog.w("Dialog wasn't created, listener == null");
        }
    }

    public HCaptchaDialogFragment(Context context, HCaptchaConfig hCaptchaConfig, HCaptchaInternalConfig hCaptchaInternalConfig) {
        this();
        HCaptchaWebView hCaptchaWebView = new HCaptchaWebView(context);
        this.webViewHelper = new HCaptchaWebViewHelper(new Handler(Looper.getMainLooper()), context, hCaptchaConfig, hCaptchaInternalConfig, this, hCaptchaWebView);
        sPreloadWebView = hCaptchaWebView;
    }
}
