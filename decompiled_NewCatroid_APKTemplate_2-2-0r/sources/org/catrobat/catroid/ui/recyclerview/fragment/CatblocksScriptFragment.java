package org.catrobat.catroid.ui.recyclerview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.webkit.WebViewAssetLoader;
import com.beust.jcommander.Parameters;
import com.google.gson.Gson;
import com.hcaptcha.sdk.HCaptchaJSInterface;
import com.huawei.hms.ml.language.common.utils.Constant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.content.Scene;
import org.catrobat.catroid.content.Script;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.bricks.Brick;
import org.catrobat.catroid.content.bricks.EmptyEventBrick;
import org.catrobat.catroid.content.bricks.ScriptBrick;
import org.catrobat.catroid.content.bricks.UserDefinedBrick;
import org.catrobat.catroid.content.bricks.UserDefinedReceiverBrick;
import org.catrobat.catroid.io.XstreamSerializer;
import org.catrobat.catroid.ui.BottomBar;
import org.catrobat.catroid.ui.controller.RecentBrickListManager;
import org.catrobat.catroid.ui.fragment.BrickCategoryListBuilder;
import org.catrobat.catroid.ui.fragment.CategoryBricksFactory;
import org.catrobat.catroid.ui.settingsfragments.SettingsFragment;
import org.eclipse.jgit.lib.ConfigConstants;
import org.jetbrains.kotlin.js.translate.context.Namer;
import org.jetbrains.kotlin.org.jline.reader.LineReader;
import org.json.JSONArray;
import org.koin.java.KoinJavaComponent;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment.class
  classes14.dex
 */
/* JADX INFO: compiled from: CatblocksScriptFragment.kt */
/* JADX INFO: loaded from: classes14.dex */
@Metadata(d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u0018\u0000 !2\u00020\u0001:\u0005\u001f !\"#B\u000f\u0012\b\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\u0002\u0010\u0004J\u000e\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nH\u0002J\u0006\u0010\f\u001a\u00020\rJ\u0010\u0010\u000e\u001a\u00020\r2\u0006\u0010\u000f\u001a\u00020\bH\u0003J&\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\u00132\b\u0010\u0014\u001a\u0004\u0018\u00010\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0017H\u0016J\u0010\u0010\u0018\u001a\u00020\u00192\u0006\u0010\u001a\u001a\u00020\u001bH\u0016J\u0010\u0010\u001c\u001a\u00020\r2\u0006\u0010\u001d\u001a\u00020\u001eH\u0016R\u0010\u0010\u0002\u001a\u0004\u0018\u00010\u0003X\u0082\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e¢\u0006\u0002\n\u0000¨\u0006$"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment;", "Landroidx/fragment/app/Fragment;", "brickAtTopID", "Ljava/util/UUID;", "(Ljava/util/UUID;)V", "projectManager", "Lorg/catrobat/catroid/ProjectManager;", "webview", "Landroid/webkit/WebView;", "getAvailableBrickCategories", "", "Lorg/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment$BrickCategoryInfoHolder;", "handleAddButton", "", "initWebView", "catblocksWebView", "onCreateView", "Landroid/view/View;", "inflater", "Landroid/view/LayoutInflater;", "container", "Landroid/view/ViewGroup;", "savedInstanceState", "Landroid/os/Bundle;", "onOptionsItemSelected", "", "item", "Landroid/view/MenuItem;", "onPrepareOptionsMenu", LineReader.MENU, "Landroid/view/Menu;", "BrickCategoryInfoHolder", "BrickInfoHolder", "Companion", HCaptchaJSInterface.JS_INTERFACE_TAG, "SwitchTo1DHelper", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
public final class CatblocksScriptFragment extends Fragment {

    /* JADX INFO: renamed from: Companion, reason: from kotlin metadata */
    public static final Companion INSTANCE = new Companion(null);
    private static final String TAG;
    private static boolean testingMode;
    private final UUID brickAtTopID;
    private final ProjectManager projectManager = (ProjectManager) KoinJavaComponent.inject$default(ProjectManager.class, null, null, 6, null).getValue();
    private WebView webview;

    public CatblocksScriptFragment(UUID uuid) {
        this.brickAtTopID = uuid;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment$Companion.class
      classes14.dex
     */
    /* JADX INFO: compiled from: CatblocksScriptFragment.kt */
    @Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R$\u0010\u0007\u001a\u00020\b8\u0006@\u0006X\u0087\u000e¢\u0006\u0014\n\u0000\u0012\u0004\b\t\u0010\u0002\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u000e"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment$Companion;", "", "()V", "TAG", "", "getTAG", "()Ljava/lang/String;", "testingMode", "", "getTestingMode$annotations", "getTestingMode", "()Z", "setTestingMode", "(Z)V", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static /* synthetic */ void getTestingMode$annotations() {
        }

        private Companion() {
        }

        public final String getTAG() {
            return CatblocksScriptFragment.TAG;
        }

        public final boolean getTestingMode() {
            return CatblocksScriptFragment.testingMode;
        }

        public final void setTestingMode(boolean z) {
            CatblocksScriptFragment.testingMode = z;
        }
    }

    static {
        Intrinsics.checkNotNullExpressionValue("CatblocksScriptFragment", "getSimpleName(...)");
        TAG = "CatblocksScriptFragment";
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        Intrinsics.checkNotNullParameter(menu, "menu");
        super.onPrepareOptionsMenu(menu);
        menu.findItem(R.id.backpack).setVisible(false);
        menu.findItem(R.id.copy).setVisible(false);
        menu.findItem(R.id.delete).setVisible(false);
        menu.findItem(R.id.rename).setVisible(false);
        menu.findItem(R.id.show_details).setVisible(false);
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        Intrinsics.checkNotNullParameter(item, "item");
        if (item.getItemId() == R.id.catblocks_reorder_scripts) {
            WebView webView = this.webview;
            Intrinsics.checkNotNull(webView);
            webView.evaluateJavascript("javascript:CatBlocks.reorderCurrentScripts();", null);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Intrinsics.checkNotNullParameter(inflater, "inflater");
        BottomBar.showBottomBar(getActivity());
        BottomBar.showAiAssistButton(getActivity());
        setHasOptionsMenu(true);
        View viewInflate = View.inflate(getActivity(), R.layout.fragment_catblocks, null);
        WebView webView = (WebView) viewInflate.findViewById(R.id.catblocksWebView);
        Intrinsics.checkNotNull(webView);
        initWebView(webView);
        this.webview = webView;
        return viewInflate;
    }

    private final void initWebView(WebView catblocksWebView) {
        catblocksWebView.getSettings().setJavaScriptEnabled(true);
        final WebViewAssetLoader webViewAssetLoaderBuild = new WebViewAssetLoader.Builder().addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(requireActivity())).addPathHandler("/res/", new WebViewAssetLoader.ResourcesPathHandler(requireActivity())).build();
        Intrinsics.checkNotNullExpressionValue(webViewAssetLoaderBuild, "build(...)");
        catblocksWebView.addJavascriptInterface(new JSInterface(), "Android");
        catblocksWebView.setWebViewClient(new WebViewClient() { // from class: org.catrobat.catroid.ui.recyclerview.fragment.CatblocksScriptFragment.initWebView.1
            @Override // android.webkit.WebViewClient
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                Intrinsics.checkNotNullParameter(view, "view");
                Intrinsics.checkNotNullParameter(request, "request");
                return webViewAssetLoaderBuild.shouldInterceptRequest(request.getUrl());
            }

            @Override // android.webkit.WebViewClient
            public void onPageFinished(WebView view, String url) {
                String str;
                super.onPageFinished(view, url);
                if (CatblocksScriptFragment.INSTANCE.getTestingMode() && view != null) {
                    view.evaluateJavascript("javascript:(function(){\n    console.log(\"Load webViewUtilsFunctions.js\");\n    const scriptElement = document.createElement(\"script\");\n    scriptElement.src = \"https://appassets.androidplatform.net/assets/catblocks/webViewUtilsFunctions.js\";\n    document.head.appendChild(scriptElement);\n})()", null);
                }
                if (view != null) {
                    if (CatblocksScriptFragment.INSTANCE.getTestingMode()) {
                        str = "if (window.webViewUtils) window.webViewUtils.onPageLoaded();";
                    } else {
                        str = "";
                    }
                    view.evaluateJavascript(StringsKt.trimMargin$default("javascript:(async function(){\n                        await CatBlocks.init({\n                           container: 'catroid-catblocks-container',\n                           renderSize: 0.75,\n                           language: Android.getCurrentLanguage(),\n                           rtl: Android.isRTL(),\n                           shareRoot: 'https://appassets.androidplatform.net/assets/catblocks',\n                           media: 'https://appassets.androidplatform.net/assets/catblocks/media',\n                           i18n: 'https://appassets.androidplatform.net/assets/catblocks/i18n',\n                           noImageFound: 'No_Image_Available.jpg',\n                           renderLooks: false,\n                           renderSounds: false,\n                           readOnly: false\n                        });\n                        \n                        const programXML = Android.getCurrentProject();\n                        const scene = Android.getSceneNameToDisplay();\n                        const object = Android.getSpriteNameToDisplay();\n                        CatBlocks.render(programXML, scene, object);\n                        " + str + "\n                    })()\n                ", null, 1, null), null);
                }
            }
        });
        if (testingMode) {
            return;
        }
        catblocksWebView.loadUrl("https://appassets.androidplatform.net/assets/catblocks/index.html");
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment$SwitchTo1DHelper.class
      classes14.dex
     */
    /* JADX INFO: compiled from: CatblocksScriptFragment.kt */
    @Metadata(d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0003\b\u0086\u0004\u0018\u00002\u00020\u00012\b\u0012\u0004\u0012\u00020\u00030\u0002B\u0005¢\u0006\u0002\u0010\u0004J\u0012\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u0003H\u0016J\b\u0010\u000e\u001a\u00020\fH\u0016R\u001c\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0007\u0010\b\"\u0004\b\t\u0010\n¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment$SwitchTo1DHelper;", "Ljava/lang/Runnable;", "Landroid/webkit/ValueCallback;", "", "(Lorg/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment;)V", "brickToFocus", "Lorg/catrobat/catroid/content/bricks/Brick;", "getBrickToFocus", "()Lorg/catrobat/catroid/content/bricks/Brick;", "setBrickToFocus", "(Lorg/catrobat/catroid/content/bricks/Brick;)V", "onReceiveValue", "", "strBrickToFocusId", "run", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class SwitchTo1DHelper implements Runnable, ValueCallback<String> {
        private Brick brickToFocus;

        public SwitchTo1DHelper() {
        }

        public final Brick getBrickToFocus() {
            return this.brickToFocus;
        }

        public final void setBrickToFocus(Brick brick) {
            this.brickToFocus = brick;
        }

        @Override // java.lang.Runnable
        public void run() {
            ScriptFragment scriptFragmentNewInstance;
            SettingsFragment.setUseCatBlocks(CatblocksScriptFragment.this.getContext(), false);
            Brick brick = this.brickToFocus;
            if (brick == null) {
                scriptFragmentNewInstance = new ScriptFragment();
            } else if (brick instanceof ScriptBrick) {
                Intrinsics.checkNotNull(brick, "null cannot be cast to non-null type org.catrobat.catroid.content.bricks.ScriptBrick");
                scriptFragmentNewInstance = ScriptFragment.newInstance(((ScriptBrick) brick).getScript());
                Intrinsics.checkNotNull(scriptFragmentNewInstance);
            } else {
                scriptFragmentNewInstance = ScriptFragment.newInstance(brick);
                Intrinsics.checkNotNull(scriptFragmentNewInstance);
            }
            FragmentTransaction fragmentTransactionBeginTransaction = CatblocksScriptFragment.this.getParentFragmentManager().beginTransaction();
            Intrinsics.checkNotNullExpressionValue(fragmentTransactionBeginTransaction, "beginTransaction(...)");
            fragmentTransactionBeginTransaction.replace(R.id.fragment_container, scriptFragmentNewInstance, ScriptFragment.TAG);
            fragmentTransactionBeginTransaction.commit();
        }

        @Override // android.webkit.ValueCallback
        public void onReceiveValue(String strBrickToFocusId) {
            if (strBrickToFocusId != null) {
                String strTrim = StringsKt.trim(strBrickToFocusId, '\"');
                if (strTrim.length() > 0) {
                    try {
                        this.brickToFocus = CatblocksScriptFragment.this.projectManager.getCurrentSprite().findBrickInSprite(UUID.fromString(strTrim));
                    } catch (IllegalArgumentException e) {
                        System.out.println((Object) e.getMessage());
                    }
                }
            }
            FragmentActivity activity = CatblocksScriptFragment.this.getActivity();
            if (activity != null) {
                activity.runOnUiThread(this);
            }
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment$JSInterface.class
      classes14.dex
     */
    /* JADX INFO: compiled from: CatblocksScriptFragment.kt */
    @Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0002\b\n\b\u0086\u0004\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u0004H\u0007J\u0012\u0010\u0007\u001a\u0004\u0018\u00010\u00042\u0006\u0010\b\u001a\u00020\u0004H\u0007J\b\u0010\t\u001a\u00020\u0004H\u0007J\n\u0010\n\u001a\u0004\u0018\u00010\u0004H\u0007J\u0010\u0010\u000b\u001a\u00020\u00042\u0006\u0010\f\u001a\u00020\u0004H\u0007J\b\u0010\r\u001a\u00020\u0004H\u0007J\b\u0010\u000e\u001a\u00020\u0004H\u0007J\n\u0010\u000f\u001a\u0004\u0018\u00010\u0004H\u0007J\n\u0010\u0010\u001a\u0004\u0018\u00010\u0004H\u0007J\b\u0010\u0011\u001a\u00020\u0012H\u0007J+\u0010\u0013\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u00042\u0006\u0010\u0015\u001a\u00020\u00162\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u0018H\u0007¢\u0006\u0002\u0010\u0019J\u001b\u0010\u001a\u001a\u00020\u00042\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00020\u00040\u0018H\u0007¢\u0006\u0002\u0010\u001bJ\u001b\u0010\u001c\u001a\u00020\u001d2\f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\u00040\u0018H\u0007¢\u0006\u0002\u0010\u001fJ\b\u0010 \u001a\u00020\u0004H\u0007J\u0010\u0010!\u001a\u00020\u001d2\u0006\u0010\"\u001a\u00020\u0004H\u0007J \u0010#\u001a\u00020\u001d2\u0006\u0010$\u001a\u00020\u00042\u0006\u0010%\u001a\u00020\u00042\u0006\u0010&\u001a\u00020\u0004H\u0007¨\u0006'"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment$JSInterface;", "", "(Lorg/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment;)V", "addBrickByName", "", "categoryName", "brickName", "duplicateBrick", "brickStrIdToClone", "getBrickCategoryInfos", "getBrickIDToFocus", "getBricksForCategory", "category", "getCurrentLanguage", "getCurrentProject", "getSceneNameToDisplay", "getSpriteNameToDisplay", "isRTL", "", "moveBricks", "newParentStrId", "parentSubStackIndex", "", "brickStrIdsToMove", "", "(Ljava/lang/String;I[Ljava/lang/String;)Z", "moveBricksToEmptyScriptBrick", "([Ljava/lang/String;)Ljava/lang/String;", "removeBricks", "", "brickStrIdsToRemove", "([Ljava/lang/String;)V", "removeEmptyScriptBricks", "switchTo1D", "strClickedBrickId", "updateScriptPosition", "strScriptId", "x", "y", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public final class JSInterface {
        public JSInterface() {
        }

        @JavascriptInterface
        public final String getCurrentProject() {
            return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" + XstreamSerializer.getInstance().getXmlAsStringFromProject(CatblocksScriptFragment.this.projectManager.getCurrentProject());
        }

        @JavascriptInterface
        public final String getCurrentLanguage() {
            String string = Locale.getDefault().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return StringsKt.replace$default(string, "_", Parameters.DEFAULT_OPTION_PREFIXES, false, 4, (Object) null);
        }

        @JavascriptInterface
        public final boolean isRTL() {
            byte directionality = Character.getDirectionality(Locale.getDefault().getDisplayName().charAt(0));
            return directionality == 1 || directionality == 2;
        }

        @JavascriptInterface
        public final String getSceneNameToDisplay() {
            String name2;
            Scene currentlyEditedScene = CatblocksScriptFragment.this.projectManager.getCurrentlyEditedScene();
            if (currentlyEditedScene == null || (name2 = currentlyEditedScene.getName()) == null) {
                return null;
            }
            return StringsKt.trim((CharSequence) name2).toString();
        }

        @JavascriptInterface
        public final String getSpriteNameToDisplay() {
            String name2;
            Sprite currentSprite = CatblocksScriptFragment.this.projectManager.getCurrentSprite();
            if (currentSprite == null || (name2 = currentSprite.getName()) == null) {
                return null;
            }
            return StringsKt.trim((CharSequence) name2).toString();
        }

        @JavascriptInterface
        public final String getBrickIDToFocus() {
            if (CatblocksScriptFragment.this.brickAtTopID != null) {
                return CatblocksScriptFragment.this.brickAtTopID.toString();
            }
            Sprite currentSprite = CatblocksScriptFragment.this.projectManager.getCurrentSprite();
            if ((currentSprite != null ? currentSprite.getScriptList() : null) != null) {
                List<Script> scriptList = CatblocksScriptFragment.this.projectManager.getCurrentSprite().getScriptList();
                Intrinsics.checkNotNullExpressionValue(scriptList, "getScriptList(...)");
                if (CollectionsKt.any(scriptList)) {
                    return CatblocksScriptFragment.this.projectManager.getCurrentSprite().getScriptList().get(0).getScriptId().toString();
                }
            }
            return null;
        }

        @JavascriptInterface
        public final void updateScriptPosition(String strScriptId, String x, String y) {
            Intrinsics.checkNotNullParameter(strScriptId, "strScriptId");
            Intrinsics.checkNotNullParameter(x, "x");
            Intrinsics.checkNotNullParameter(y, "y");
            if (CatblocksScriptFragment.this.projectManager.getCurrentSprite() == null) {
                return;
            }
            UUID uuidFromString = UUID.fromString(strScriptId);
            float f = Float.parseFloat(x);
            float f2 = Float.parseFloat(y);
            for (Script script : CatblocksScriptFragment.this.projectManager.getCurrentSprite().getScriptList()) {
                if (Intrinsics.areEqual(script.getScriptId(), uuidFromString)) {
                    script.setPosX(f);
                    script.setPosY(f2);
                    return;
                }
            }
        }

        @JavascriptInterface
        public final String moveBricksToEmptyScriptBrick(String[] brickStrIdsToMove) {
            Intrinsics.checkNotNullParameter(brickStrIdsToMove, "brickStrIdsToMove");
            EmptyEventBrick emptyEventBrick = new EmptyEventBrick();
            ArrayList arrayList = new ArrayList();
            for (String str : brickStrIdsToMove) {
                UUID uuidFromString = UUID.fromString(str);
                Intrinsics.checkNotNullExpressionValue(uuidFromString, "fromString(...)");
                arrayList.add(uuidFromString);
            }
            Iterator<Script> it = CatblocksScriptFragment.this.projectManager.getCurrentSprite().getScriptList().iterator();
            while (it.hasNext()) {
                List<Brick> listRemoveBricksFromScript = it.next().removeBricksFromScript(arrayList);
                if (listRemoveBricksFromScript != null) {
                    emptyEventBrick.getScript().getBrickList().addAll(listRemoveBricksFromScript);
                    break;
                }
            }
            CatblocksScriptFragment.this.projectManager.getCurrentSprite().getScriptList().add(emptyEventBrick.getScript());
            String string = emptyEventBrick.getScript().getScriptId().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }

        @JavascriptInterface
        public final boolean moveBricks(String newParentStrId, int parentSubStackIndex, String[] brickStrIdsToMove) {
            Intrinsics.checkNotNullParameter(newParentStrId, "newParentStrId");
            Intrinsics.checkNotNullParameter(brickStrIdsToMove, "brickStrIdsToMove");
            ArrayList arrayList = new ArrayList();
            for (String str : brickStrIdsToMove) {
                UUID uuidFromString = UUID.fromString(str);
                Intrinsics.checkNotNullExpressionValue(uuidFromString, "fromString(...)");
                arrayList.add(uuidFromString);
            }
            UUID uuidFromString2 = UUID.fromString(newParentStrId);
            Iterator<Script> it = CatblocksScriptFragment.this.projectManager.getCurrentSprite().getScriptList().iterator();
            List<Brick> listRemoveBricksFromScript = null;
            while (it.hasNext() && (listRemoveBricksFromScript = it.next().removeBricksFromScript(arrayList)) == null) {
            }
            if (listRemoveBricksFromScript == null) {
                return false;
            }
            Iterator<Script> it2 = CatblocksScriptFragment.this.projectManager.getCurrentSprite().getScriptList().iterator();
            while (it2.hasNext()) {
                if (it2.next().insertBrickAfter(uuidFromString2, parentSubStackIndex, listRemoveBricksFromScript)) {
                    return true;
                }
            }
            return false;
        }

        @JavascriptInterface
        public final String removeEmptyScriptBricks() {
            String string = new JSONArray((Collection) CatblocksScriptFragment.this.projectManager.getCurrentSprite().removeAllEmptyScriptBricks()).toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            return string;
        }

        @JavascriptInterface
        public final void switchTo1D(String strClickedBrickId) {
            Intrinsics.checkNotNullParameter(strClickedBrickId, "strClickedBrickId");
            Brick brickFindBrickInSprite = CatblocksScriptFragment.this.projectManager.getCurrentSprite().findBrickInSprite(UUID.fromString(strClickedBrickId));
            if (brickFindBrickInSprite != null) {
                SwitchTo1DHelper switchTo1DHelper = CatblocksScriptFragment.this.new SwitchTo1DHelper();
                switchTo1DHelper.setBrickToFocus(brickFindBrickInSprite);
                FragmentActivity activity = CatblocksScriptFragment.this.getActivity();
                if (activity != null) {
                    activity.runOnUiThread(switchTo1DHelper);
                }
            }
        }

        /* JADX WARN: Code duplicated, block: B:19:0x0063 A[SYNTHETIC] */
        /* JADX WARN: Code duplicated, block: B:22:? A[LOOP:1: B:6:0x002d->B:22:?, LOOP_END, SYNTHETIC] */
        @JavascriptInterface
        public final void removeBricks(String[] brickStrIdsToRemove) {
            Intrinsics.checkNotNullParameter(brickStrIdsToRemove, "brickStrIdsToRemove");
            ArrayList arrayList = new ArrayList();
            boolean z = false;
            for (String str : brickStrIdsToRemove) {
                arrayList.add(UUID.fromString(str));
            }
            for (Script script : CatblocksScriptFragment.this.projectManager.getCurrentSprite().getScriptList()) {
                if (arrayList.contains(script.getScriptId())) {
                    CatblocksScriptFragment.this.projectManager.getCurrentSprite().getScriptList().remove(script);
                } else {
                    if (script.removeBricksFromScript(arrayList) != null) {
                    }
                    if (z) {
                        return;
                    }
                }
                z = true;
                if (z) {
                    return;
                }
            }
        }

        @JavascriptInterface
        public final String duplicateBrick(String brickStrIdToClone) throws CloneNotSupportedException {
            Script scriptClone;
            Intrinsics.checkNotNullParameter(brickStrIdToClone, "brickStrIdToClone");
            Brick brickFindBrickInSprite = CatblocksScriptFragment.this.projectManager.getCurrentSprite().findBrickInSprite(UUID.fromString(brickStrIdToClone));
            if (brickFindBrickInSprite == null) {
                return "";
            }
            if (brickFindBrickInSprite instanceof ScriptBrick) {
                Script script = ((ScriptBrick) brickFindBrickInSprite).getScript();
                if (script == null || (scriptClone = script.clone()) == null) {
                    return null;
                }
                CatblocksScriptFragment.this.projectManager.getCurrentSprite().getScriptList().add(scriptClone);
                return scriptClone.getScriptId().toString();
            }
            Brick brickClone = brickFindBrickInSprite.clone();
            Intrinsics.checkNotNullExpressionValue(brickClone, "clone(...)");
            EmptyEventBrick emptyEventBrick = new EmptyEventBrick();
            emptyEventBrick.getScript().getBrickList().add(brickClone);
            CatblocksScriptFragment.this.projectManager.getCurrentSprite().getScriptList().add(emptyEventBrick.getScript());
            return emptyEventBrick.getScript().getScriptId().toString();
        }

        @JavascriptInterface
        public final String getBricksForCategory(String category) {
            Intrinsics.checkNotNullParameter(category, "category");
            CategoryBricksFactory categoryBricksFactory = new CategoryBricksFactory();
            boolean zIsBackgroundSprite = CatblocksScriptFragment.this.projectManager.getCurrentSprite().isBackgroundSprite();
            Context contextRequireContext = CatblocksScriptFragment.this.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            List<Brick> bricks = categoryBricksFactory.getBricks(category, zIsBackgroundSprite, contextRequireContext);
            ArrayList arrayList = new ArrayList();
            Iterator<Brick> it = bricks.iterator();
            while (it.hasNext()) {
                String simpleName = it.next().getClass().getSimpleName();
                Intrinsics.checkNotNull(simpleName);
                arrayList.add(new BrickInfoHolder(simpleName, simpleName));
            }
            String json = new Gson().toJson(arrayList);
            Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
            return json;
        }

        @JavascriptInterface
        public final String addBrickByName(String categoryName, String brickName) throws CloneNotSupportedException {
            Intrinsics.checkNotNullParameter(categoryName, "categoryName");
            Intrinsics.checkNotNullParameter(brickName, "brickName");
            CategoryBricksFactory categoryBricksFactory = new CategoryBricksFactory();
            boolean zIsBackgroundSprite = CatblocksScriptFragment.this.projectManager.getCurrentSprite().isBackgroundSprite();
            Context contextRequireContext = CatblocksScriptFragment.this.requireContext();
            Intrinsics.checkNotNullExpressionValue(contextRequireContext, "requireContext(...)");
            List<Brick> bricks = categoryBricksFactory.getBricks(categoryName, zIsBackgroundSprite, contextRequireContext);
            ArrayList arrayList = new ArrayList();
            for (Object obj : bricks) {
                if (((Brick) obj).getClass().getSimpleName().equals(brickName)) {
                    arrayList.add(obj);
                }
            }
            ArrayList arrayList2 = arrayList;
            ArrayList arrayList3 = new ArrayList();
            if (arrayList2.size() == 1) {
                Brick brickClone = ((Brick) arrayList2.get(0)).clone();
                Intrinsics.checkNotNullExpressionValue(brickClone, "clone(...)");
                if (brickClone instanceof ScriptBrick) {
                    ScriptBrick scriptBrick = (ScriptBrick) brickClone;
                    CatblocksScriptFragment.this.projectManager.getCurrentSprite().getScriptList().add(scriptBrick.getScript());
                    String string = scriptBrick.getScript().getScriptId().toString();
                    Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
                    String simpleName = scriptBrick.getScript().getClass().getSimpleName();
                    Intrinsics.checkNotNullExpressionValue(simpleName, "getSimpleName(...)");
                    arrayList3.add(new BrickInfoHolder(string, simpleName));
                } else {
                    EmptyEventBrick emptyEventBrick = new EmptyEventBrick();
                    emptyEventBrick.getScript().getBrickList().add(brickClone);
                    CatblocksScriptFragment.this.projectManager.getCurrentSprite().getScriptList().add(emptyEventBrick.getScript());
                    String string2 = emptyEventBrick.getScript().getScriptId().toString();
                    Intrinsics.checkNotNullExpressionValue(string2, "toString(...)");
                    String simpleName2 = emptyEventBrick.getScript().getClass().getSimpleName();
                    Intrinsics.checkNotNullExpressionValue(simpleName2, "getSimpleName(...)");
                    arrayList3.add(new BrickInfoHolder(string2, simpleName2));
                    String string3 = brickClone.getBrickID().toString();
                    Intrinsics.checkNotNullExpressionValue(string3, "toString(...)");
                    String simpleName3 = brickClone.getClass().getSimpleName();
                    Intrinsics.checkNotNullExpressionValue(simpleName3, "getSimpleName(...)");
                    arrayList3.add(new BrickInfoHolder(string3, simpleName3));
                }
                try {
                    if (!Intrinsics.areEqual(brickClone.getClass(), UserDefinedReceiverBrick.class) && !Intrinsics.areEqual(brickClone.getClass(), UserDefinedBrick.class)) {
                        RecentBrickListManager.getInstance().addBrick(brickClone.clone());
                    }
                } catch (CloneNotSupportedException e) {
                    Log.e(ScriptFragment.TAG, Log.getStackTraceString(e));
                }
            }
            String json = new Gson().toJson(arrayList3);
            Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
            return json;
        }

        @JavascriptInterface
        public final String getBrickCategoryInfos() {
            String json = new Gson().toJson(CatblocksScriptFragment.this.getAvailableBrickCategories());
            Intrinsics.checkNotNullExpressionValue(json, "toJson(...)");
            return json;
        }
    }

    public final void handleAddButton() {
        String json = new Gson().toJson(getAvailableBrickCategories());
        WebView webView = this.webview;
        Intrinsics.checkNotNull(webView);
        webView.evaluateJavascript("javascript:CatBlocks.showBrickCategories(" + json + ");", null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final List<BrickCategoryInfoHolder> getAvailableBrickCategories() {
        FragmentActivity fragmentActivityRequireActivity = requireActivity();
        Intrinsics.checkNotNullExpressionValue(fragmentActivityRequireActivity, "requireActivity(...)");
        List<String> categoryNames = new BrickCategoryListBuilder(fragmentActivityRequireActivity).getCategoryNames();
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = categoryNames.iterator();
        while (it.hasNext()) {
            arrayList.add(new BrickCategoryInfoHolder(it.next()));
        }
        return arrayList;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment$BrickCategoryInfoHolder.class
      classes14.dex
     */
    /* JADX INFO: compiled from: CatblocksScriptFragment.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0082\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\u0007\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\f\u001a\u00020\rHÖ\u0001J\t\u0010\u000e\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006¨\u0006\u000f"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment$BrickCategoryInfoHolder;", "", "name", "", "(Ljava/lang/String;)V", "getName", "()Ljava/lang/String;", "component1", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    private static final /* data */ class BrickCategoryInfoHolder {
        private final String name;

        public static /* synthetic */ BrickCategoryInfoHolder copy$default(BrickCategoryInfoHolder brickCategoryInfoHolder, String str, int i, Object obj) {
            if ((i & 1) != 0) {
                str = brickCategoryInfoHolder.name;
            }
            return brickCategoryInfoHolder.copy(str);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getName() {
            return this.name;
        }

        public final BrickCategoryInfoHolder copy(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            return new BrickCategoryInfoHolder(name2);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            return (other instanceof BrickCategoryInfoHolder) && Intrinsics.areEqual(this.name, ((BrickCategoryInfoHolder) other).name);
        }

        public int hashCode() {
            return this.name.hashCode();
        }

        public String toString() {
            return "BrickCategoryInfoHolder(name=" + this.name + Constant.AFTER_QUTO;
        }

        public BrickCategoryInfoHolder(String name2) {
            Intrinsics.checkNotNullParameter(name2, "name");
            this.name = name2;
        }

        public final String getName() {
            return this.name;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment$BrickInfoHolder.class
      classes14.dex
     */
    /* JADX INFO: compiled from: CatblocksScriptFragment.kt */
    @Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0002\u0010\u0005J\t\u0010\t\u001a\u00020\u0003HÆ\u0003J\t\u0010\n\u001a\u00020\u0003HÆ\u0003J\u001d\u0010\u000b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\f\u001a\u00020\r2\b\u0010\u000e\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\u000f\u001a\u00020\u0010HÖ\u0001J\t\u0010\u0011\u001a\u00020\u0003HÖ\u0001R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0007¨\u0006\u0012"}, d2 = {"Lorg/catrobat/catroid/ui/recyclerview/fragment/CatblocksScriptFragment$BrickInfoHolder;", "", "brickId", "", "brickType", "(Ljava/lang/String;Ljava/lang/String;)V", "getBrickId", "()Ljava/lang/String;", "getBrickType", "component1", "component2", ConfigConstants.CONFIG_RENAMELIMIT_COPY, Namer.EQUALS_METHOD_NAME, "", "other", "hashCode", "", "toString", "catroid_apktemplateTemplateRelease"}, k = 1, mv = {1, 9, 0}, xi = 48)
    public static final /* data */ class BrickInfoHolder {
        private final String brickId;
        private final String brickType;

        public static /* synthetic */ BrickInfoHolder copy$default(BrickInfoHolder brickInfoHolder, String str, String str2, int i, Object obj) {
            if ((i & 1) != 0) {
                str = brickInfoHolder.brickId;
            }
            if ((i & 2) != 0) {
                str2 = brickInfoHolder.brickType;
            }
            return brickInfoHolder.copy(str, str2);
        }

        /* JADX INFO: renamed from: component1, reason: from getter */
        public final String getBrickId() {
            return this.brickId;
        }

        /* JADX INFO: renamed from: component2, reason: from getter */
        public final String getBrickType() {
            return this.brickType;
        }

        public final BrickInfoHolder copy(String brickId, String brickType) {
            Intrinsics.checkNotNullParameter(brickId, "brickId");
            Intrinsics.checkNotNullParameter(brickType, "brickType");
            return new BrickInfoHolder(brickId, brickType);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof BrickInfoHolder)) {
                return false;
            }
            BrickInfoHolder brickInfoHolder = (BrickInfoHolder) other;
            return Intrinsics.areEqual(this.brickId, brickInfoHolder.brickId) && Intrinsics.areEqual(this.brickType, brickInfoHolder.brickType);
        }

        public int hashCode() {
            return (this.brickId.hashCode() * 31) + this.brickType.hashCode();
        }

        public String toString() {
            return "BrickInfoHolder(brickId=" + this.brickId + ", brickType=" + this.brickType + Constant.AFTER_QUTO;
        }

        public BrickInfoHolder(String brickId, String brickType) {
            Intrinsics.checkNotNullParameter(brickId, "brickId");
            Intrinsics.checkNotNullParameter(brickType, "brickType");
            this.brickId = brickId;
            this.brickType = brickType;
        }

        public final String getBrickId() {
            return this.brickId;
        }

        public final String getBrickType() {
            return this.brickType;
        }
    }
}
