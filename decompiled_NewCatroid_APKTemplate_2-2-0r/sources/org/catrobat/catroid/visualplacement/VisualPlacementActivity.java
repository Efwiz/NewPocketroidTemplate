package org.catrobat.catroid.visualplacement;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import java.util.Locale;
import org.DanVexTeam.NewCatroidApkTemplate.R;
import org.catrobat.catroid.ProjectManager;
import org.catrobat.catroid.common.ScreenModes;
import org.catrobat.catroid.content.Project;
import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.ui.BaseCastActivity;
import org.catrobat.catroid.ui.SpriteActivity;
import org.catrobat.catroid.utils.ProjectManagerExtensionsKt;
import org.catrobat.catroid.utils.Resolution;
import org.catrobat.catroid.utils.ShowTextUtils;
import org.catrobat.catroid.utils.ToastUtil;
import org.jetbrains.kotlin.com.intellij.psi.PsiKeyword;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/visualplacement/VisualPlacementActivity.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class VisualPlacementActivity extends BaseCastActivity implements View.OnTouchListener, DialogInterface.OnClickListener, CoordinateInterface {
    public static final String CHANGED_COORDINATES = "changedCoordinates";
    public static final String EXTRA_IS_VARIABLE = "isVariable";
    public static final String EXTRA_ROTATION_CHANGED = "rotationChanged";
    public static final String EXTRA_SIZE_CHANGED = "sizeChanged";
    public static final String HEIGHT_BUNDLE_ARGUMENT = "heightPercentage";
    public static final String ROTATION_ANGLE_BUNDLE_ARGUMENT = "rotationAngle";
    public static final String SIZE_PERCENT_BUNDLE_ARGUMENT = "sizePercentage";
    public static final String TAG = "VisualPlacementActivity";
    public static final String WIDTH_BUNDLE_ARGUMENT = "widthPercentage";
    public static final String X_COORDINATE_BUNDLE_ARGUMENT = "xCoordinate";
    public static final String Y_COORDINATE_BUNDLE_ARGUMENT = "yCoordinate";
    private BitmapFactory.Options bitmapOptions;
    private FrameLayout frameLayout;
    private ImageView imageView;
    private float initialX;
    private float initialY;
    private boolean isText;
    private float layoutHeightRatio;
    private Resolution layoutResolution;
    private float layoutWidthRatio;
    private ProjectManager projectManager;
    private float relativeTextSize;
    private float rotation;
    private int rotationMode;
    private ScaleGestureDetector scaleGestureDetector;
    private float scaleX;
    private float scaleY;
    private String text;
    private int textAlignment;
    private String textColor;
    private float translateX;
    private float translateY;
    private VisualPlacementTouchListener visualPlacementTouchListener;
    private float xCoord;
    private float xOffsetText;
    private float yCoord;
    private float yOffsetText;
    private float rotationAngle = 0.0f;
    private boolean isVariable = false;
    private float currentScale = 1.0f;
    private float currentRotation = 0.0f;
    private float initialRotation = 0.0f;
    private float initialScale = 1.0f;

    @Override // org.catrobat.catroid.ui.BaseCastActivity, org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_confirm, menu);
        return true;
    }

    @Override // org.catrobat.catroid.ui.BaseCastActivity, org.catrobat.catroid.ui.BaseActivity, android.app.Activity
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == 16908332) {
            onBackPressed();
            return true;
        }
        if (itemId != R.id.confirm) {
            return true;
        }
        finishWithResult();
        return true;
    }

    @Override // org.catrobat.catroid.ui.BaseActivity, androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(null);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        if (isFinishing()) {
            return;
        }
        ProjectManager projectManager = ProjectManager.getInstance();
        this.projectManager = projectManager;
        Project currentProject = projectManager.getCurrentProject();
        setContentView(R.layout.visual_placement_layout);
        Bundle extras = getIntent().getExtras();
        this.translateX = extras.getInt(SpriteActivity.EXTRA_X_TRANSFORM);
        this.translateY = extras.getInt(SpriteActivity.EXTRA_Y_TRANSFORM);
        this.rotationAngle = extras.getFloat(SpriteActivity.EXTRA_ROTATION);
        this.isVariable = extras.getBoolean(EXTRA_IS_VARIABLE, false);
        if (extras.containsKey(SpriteActivity.EXTRA_TEXT)) {
            this.isText = true;
            this.text = extras.getString(SpriteActivity.EXTRA_TEXT);
            this.textAlignment = 1;
            this.relativeTextSize = 1.0f;
            if (extras.containsKey(SpriteActivity.EXTRA_TEXT_COLOR)) {
                this.textColor = extras.getString(SpriteActivity.EXTRA_TEXT_COLOR);
                this.textAlignment = extras.getInt(SpriteActivity.EXTRA_TEXT_ALIGNMENT);
                this.relativeTextSize = extras.getFloat(SpriteActivity.EXTRA_TEXT_SIZE);
            }
            this.xOffsetText = -3.0f;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.transparent_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.brick_option_place_visually);
        if (this.projectManager.isCurrentProjectLandscapeMode()) {
            setRequestedOrientation(0);
        } else {
            setRequestedOrientation(1);
        }
        this.visualPlacementTouchListener = new VisualPlacementTouchListener();
        this.frameLayout = (FrameLayout) findViewById(R.id.frame_container);
        Resolution resolution = new Resolution(currentProject.getXmlHeader().getVirtualScreenWidth(), currentProject.getXmlHeader().getVirtualScreenHeight());
        int i = displayMetrics.widthPixels;
        int i2 = displayMetrics.heightPixels;
        boolean zIsCurrentProjectLandscapeMode = this.projectManager.isCurrentProjectLandscapeMode();
        Resolution resolution2 = new Resolution(zIsCurrentProjectLandscapeMode ? Math.max(i, i2) : Math.min(i, i2), zIsCurrentProjectLandscapeMode ? Math.min(i, i2) : Math.max(i, i2));
        int i3 = AnonymousClass1.$SwitchMap$org$catrobat$catroid$common$ScreenModes[currentProject.getScreenMode().ordinal()];
        if (i3 == 1) {
            this.layoutResolution = resolution.resizeToFit(resolution2);
        } else if (i3 == 2) {
            this.layoutResolution = resolution2;
        }
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-2, -2);
        layoutParams.gravity = 17;
        layoutParams.width = this.layoutResolution.getWidth();
        layoutParams.height = this.layoutResolution.getHeight();
        this.frameLayout.setLayoutParams(layoutParams);
        this.layoutHeightRatio = this.layoutResolution.getHeight() / resolution.getHeight();
        float width = this.layoutResolution.getWidth() / resolution.getWidth();
        this.layoutWidthRatio = width;
        if (width <= 0.0f || Float.isNaN(width) || Float.isInfinite(this.layoutWidthRatio)) {
            this.layoutWidthRatio = 1.0f;
        }
        float f = this.layoutHeightRatio;
        if (f <= 0.0f || Float.isNaN(f) || Float.isInfinite(this.layoutHeightRatio)) {
            this.layoutHeightRatio = 1.0f;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        this.bitmapOptions = options;
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        setBackground();
        showMovableImageView();
        toolbar.bringToFront();
        this.frameLayout.setOnTouchListener(this);
        this.scaleGestureDetector = new ScaleGestureDetector(this, new MyScaleGestureListener());
        this.initialRotation = extras.getFloat(SpriteActivity.EXTRA_ROTATION, 0.0f);
        this.initialX = this.translateX;
        this.initialY = this.translateY;
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/visualplacement/VisualPlacementActivity$1.class
      classes14.dex
     */
    /* JADX INFO: renamed from: org.catrobat.catroid.visualplacement.VisualPlacementActivity$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$org$catrobat$catroid$common$ScreenModes;

        static {
            int[] iArr = new int[ScreenModes.values().length];
            $SwitchMap$org$catrobat$catroid$common$ScreenModes = iArr;
            try {
                iArr[ScreenModes.MAXIMIZE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$org$catrobat$catroid$common$ScreenModes[ScreenModes.STRETCH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }

    private void setBackground() {
        try {
            Bitmap projectBitmap = ProjectManagerExtensionsKt.getProjectBitmap(this.projectManager);
            BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(projectBitmap, (int) (projectBitmap.getWidth() * this.layoutWidthRatio), (int) (projectBitmap.getHeight() * this.layoutHeightRatio), true));
            bitmapDrawable.setColorFilter(Color.parseColor("#6F000000"), PorterDuff.Mode.SRC_ATOP);
            this.frameLayout.setBackground(bitmapDrawable);
        } catch (Exception unused) {
            this.frameLayout.setBackgroundColor(-1);
        }
    }

    /* JADX WARN: Code duplicated, block: B:24:0x011d A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:25:0x011f  */
    /* JADX WARN: Code duplicated, block: B:27:0x0122  */
    /* JADX WARN: Code duplicated, block: B:28:0x0123 A[Catch: Exception -> 0x01d6, TryCatch #0 {Exception -> 0x01d6, blocks: (B:3:0x0004, B:5:0x0011, B:6:0x0017, B:8:0x0024, B:22:0x0110, B:35:0x014b, B:40:0x0172, B:42:0x018b, B:44:0x01ad, B:43:0x01a3, B:28:0x0123, B:29:0x0127, B:31:0x012f, B:32:0x0134, B:34:0x013a, B:12:0x003a, B:14:0x0046, B:21:0x00e7, B:16:0x007a, B:18:0x0084, B:19:0x00b6), top: B:49:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:29:0x0127 A[Catch: Exception -> 0x01d6, TryCatch #0 {Exception -> 0x01d6, blocks: (B:3:0x0004, B:5:0x0011, B:6:0x0017, B:8:0x0024, B:22:0x0110, B:35:0x014b, B:40:0x0172, B:42:0x018b, B:44:0x01ad, B:43:0x01a3, B:28:0x0123, B:29:0x0127, B:31:0x012f, B:32:0x0134, B:34:0x013a, B:12:0x003a, B:14:0x0046, B:21:0x00e7, B:16:0x007a, B:18:0x0084, B:19:0x00b6), top: B:49:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:31:0x012f A[Catch: Exception -> 0x01d6, TryCatch #0 {Exception -> 0x01d6, blocks: (B:3:0x0004, B:5:0x0011, B:6:0x0017, B:8:0x0024, B:22:0x0110, B:35:0x014b, B:40:0x0172, B:42:0x018b, B:44:0x01ad, B:43:0x01a3, B:28:0x0123, B:29:0x0127, B:31:0x012f, B:32:0x0134, B:34:0x013a, B:12:0x003a, B:14:0x0046, B:21:0x00e7, B:16:0x007a, B:18:0x0084, B:19:0x00b6), top: B:49:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x0134 A[Catch: Exception -> 0x01d6, TryCatch #0 {Exception -> 0x01d6, blocks: (B:3:0x0004, B:5:0x0011, B:6:0x0017, B:8:0x0024, B:22:0x0110, B:35:0x014b, B:40:0x0172, B:42:0x018b, B:44:0x01ad, B:43:0x01a3, B:28:0x0123, B:29:0x0127, B:31:0x012f, B:32:0x0134, B:34:0x013a, B:12:0x003a, B:14:0x0046, B:21:0x00e7, B:16:0x007a, B:18:0x0084, B:19:0x00b6), top: B:49:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:34:0x013a A[Catch: Exception -> 0x01d6, TryCatch #0 {Exception -> 0x01d6, blocks: (B:3:0x0004, B:5:0x0011, B:6:0x0017, B:8:0x0024, B:22:0x0110, B:35:0x014b, B:40:0x0172, B:42:0x018b, B:44:0x01ad, B:43:0x01a3, B:28:0x0123, B:29:0x0127, B:31:0x012f, B:32:0x0134, B:34:0x013a, B:12:0x003a, B:14:0x0046, B:21:0x00e7, B:16:0x007a, B:18:0x0084, B:19:0x00b6), top: B:49:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:37:0x016e  */
    /* JADX WARN: Code duplicated, block: B:39:0x0171  */
    /* JADX WARN: Code duplicated, block: B:42:0x018b A[Catch: Exception -> 0x01d6, TryCatch #0 {Exception -> 0x01d6, blocks: (B:3:0x0004, B:5:0x0011, B:6:0x0017, B:8:0x0024, B:22:0x0110, B:35:0x014b, B:40:0x0172, B:42:0x018b, B:44:0x01ad, B:43:0x01a3, B:28:0x0123, B:29:0x0127, B:31:0x012f, B:32:0x0134, B:34:0x013a, B:12:0x003a, B:14:0x0046, B:21:0x00e7, B:16:0x007a, B:18:0x0084, B:19:0x00b6), top: B:49:0x0004 }] */
    /* JADX WARN: Code duplicated, block: B:43:0x01a3 A[Catch: Exception -> 0x01d6, TryCatch #0 {Exception -> 0x01d6, blocks: (B:3:0x0004, B:5:0x0011, B:6:0x0017, B:8:0x0024, B:22:0x0110, B:35:0x014b, B:40:0x0172, B:42:0x018b, B:44:0x01ad, B:43:0x01a3, B:28:0x0123, B:29:0x0127, B:31:0x012f, B:32:0x0134, B:34:0x013a, B:12:0x003a, B:14:0x0046, B:21:0x00e7, B:16:0x007a, B:18:0x0084, B:19:0x00b6), top: B:49:0x0004 }] */
    public void showMovableImageView() {
        Bitmap bitmapDecodeFile;
        Bitmap bitmapCreateBitmap;
        Matrix matrix;
        int i;
        Bitmap bitmapCreateBitmap2;
        int width;
        int height;
        float f;
        float f2;
        float f3;
        try {
            Sprite currentSprite = this.projectManager.getCurrentSprite();
            Log.d("VisualPlacement", "Started...");
            if (currentSprite == null) {
                Log.e("VisualPlacement", "sprite is null");
            }
            this.imageView = new ImageView(this);
            if (!this.isText) {
                if (currentSprite != null && !currentSprite.look.getImagePath().isEmpty()) {
                    Log.d("VisualPlacement", "!currentSprite.look.getImagePath().isEmpty()");
                    String imagePath = currentSprite.look.getImagePath();
                    this.scaleX = currentSprite.look.getScaleX();
                    this.scaleY = currentSprite.look.getScaleY();
                    this.rotationMode = currentSprite.look.getRotationMode();
                    this.rotation = currentSprite.look.getMotionDirectionInUserInterfaceDimensionUnit();
                    bitmapDecodeFile = BitmapFactory.decodeFile(imagePath, this.bitmapOptions);
                } else if (currentSprite == null || currentSprite.getLookList().size() == 0) {
                    Log.d("VisualPlacement", PsiKeyword.ELSE);
                    Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.pc_toolbar_icon);
                    Bitmap bitmapCreateBitmap3 = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas = new Canvas(bitmapCreateBitmap3);
                    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
                    drawable.draw(canvas);
                    bitmapDecodeFile = bitmapCreateBitmap3;
                } else {
                    Log.d("VisualPlacement", "currentSprite.getLookList().size() != 0");
                    String absolutePath = currentSprite.getLookList().get(0).getFile().getAbsolutePath();
                    Log.d("VisualPlacement", "objectLookPath: " + absolutePath);
                    bitmapDecodeFile = BitmapFactory.decodeFile(absolutePath, this.bitmapOptions);
                    this.scaleX = 1.0f;
                    this.scaleY = 1.0f;
                }
                if (bitmapDecodeFile == null) {
                    Drawable drawable2 = ContextCompat.getDrawable(getApplicationContext(), R.drawable.pc_toolbar_icon);
                    bitmapCreateBitmap = Bitmap.createBitmap(drawable2.getIntrinsicWidth(), drawable2.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
                    Canvas canvas2 = new Canvas(bitmapCreateBitmap);
                    drawable2.setBounds(0, 0, canvas2.getWidth(), canvas2.getHeight());
                    drawable2.draw(canvas2);
                }
                matrix = new Matrix();
                i = this.rotationMode;
                if (i != 0) {
                    if (i != 1) {
                        f3 = this.rotation;
                        if (f3 != 90.0f) {
                            matrix.postRotate(f3 - 90.0f);
                        }
                    } else if (i != 2) {
                        matrix.postRotate(0.0f);
                    }
                } else if (this.rotation < 0.0f) {
                    matrix.postScale(-1.0f, 1.0f, bitmapCreateBitmap.getWidth() / 2.0f, bitmapCreateBitmap.getHeight() / 2.0f);
                }
                bitmapCreateBitmap2 = Bitmap.createBitmap(bitmapCreateBitmap, 0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight(), matrix, true);
                width = (int) (bitmapCreateBitmap2.getWidth() * this.layoutWidthRatio);
                height = (int) (bitmapCreateBitmap2.getHeight() * this.layoutHeightRatio);
                if (width <= 0) {
                    width = 1;
                }
                if (height <= 0) {
                    height = 1;
                }
                this.imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmapCreateBitmap2, width, height, true));
                this.imageView.setScaleType(ImageView.ScaleType.CENTER);
                f = this.translateX;
                f2 = -this.translateY;
                if (this.isText) {
                    this.imageView.setTranslationX(f + this.xOffsetText);
                    this.imageView.setTranslationY((f2 + this.yOffsetText) - (bitmapCreateBitmap2.getHeight() / 2.0f));
                } else {
                    this.imageView.setTranslationX(f);
                    this.imageView.setTranslationY(f2);
                }
                this.xCoord = this.translateX;
                this.yCoord = this.translateY;
                this.imageView.setScaleX(this.scaleX);
                this.imageView.setScaleY(this.scaleY);
                this.imageView.setRotation(this.initialRotation);
                this.initialScale = this.scaleX;
                this.frameLayout.addView(this.imageView);
            }
            Log.d("VisualPlacement", "isText");
            this.scaleX = 1.0f;
            this.scaleY = 1.0f;
            bitmapDecodeFile = convertTextToBitmap();
            bitmapCreateBitmap = bitmapDecodeFile;
            matrix = new Matrix();
            i = this.rotationMode;
            if (i != 0) {
                if (i != 1) {
                    f3 = this.rotation;
                    if (f3 != 90.0f) {
                        matrix.postRotate(f3 - 90.0f);
                    }
                } else if (i != 2) {
                    matrix.postRotate(0.0f);
                }
            } else if (this.rotation < 0.0f) {
                matrix.postScale(-1.0f, 1.0f, bitmapCreateBitmap.getWidth() / 2.0f, bitmapCreateBitmap.getHeight() / 2.0f);
            }
            bitmapCreateBitmap2 = Bitmap.createBitmap(bitmapCreateBitmap, 0, 0, bitmapCreateBitmap.getWidth(), bitmapCreateBitmap.getHeight(), matrix, true);
            width = (int) (bitmapCreateBitmap2.getWidth() * this.layoutWidthRatio);
            height = (int) (bitmapCreateBitmap2.getHeight() * this.layoutHeightRatio);
            if (width <= 0) {
                width = 1;
            }
            if (height <= 0) {
                height = 1;
            }
            this.imageView.setImageBitmap(Bitmap.createScaledBitmap(bitmapCreateBitmap2, width, height, true));
            this.imageView.setScaleType(ImageView.ScaleType.CENTER);
            f = this.translateX;
            f2 = -this.translateY;
            if (this.isText) {
                this.imageView.setTranslationX(f + this.xOffsetText);
                this.imageView.setTranslationY((f2 + this.yOffsetText) - (bitmapCreateBitmap2.getHeight() / 2.0f));
            } else {
                this.imageView.setTranslationX(f);
                this.imageView.setTranslationY(f2);
            }
            this.xCoord = this.translateX;
            this.yCoord = this.translateY;
            this.imageView.setScaleX(this.scaleX);
            this.imageView.setScaleY(this.scaleY);
            this.imageView.setRotation(this.initialRotation);
            this.initialScale = this.scaleX;
            this.frameLayout.addView(this.imageView);
        } catch (Exception e) {
            Log.e("VisualPlacement", "Error displaying movable image", e);
            ToastUtil.showError(this, R.string.error_unknown_error);
            finish();
        }
    }

    private Bitmap convertTextToBitmap() {
        Paint paint = new Paint(1);
        float fSanitizeTextSize = ShowTextUtils.sanitizeTextSize(this.relativeTextSize * 45.0f);
        paint.setTextSize(fSanitizeTextSize);
        if (ShowTextUtils.isValidColorString(this.textColor)) {
            String upperCase = this.textColor.toUpperCase(Locale.getDefault());
            this.textColor = upperCase;
            int[] iArrCalculateColorRGBs = ShowTextUtils.calculateColorRGBs(upperCase);
            paint.setColor((iArrCalculateColorRGBs[1] << 8) | (-16777216) | (iArrCalculateColorRGBs[0] << 16) | iArrCalculateColorRGBs[2]);
        } else {
            paint.setColor(ViewCompat.MEASURED_STATE_MASK);
        }
        float f = -paint.ascent();
        int iMeasureText = (int) paint.measureText(this.text);
        int iCalculateAlignmentValuesForText = ShowTextUtils.calculateAlignmentValuesForText(paint, iMeasureText, this.textAlignment);
        int iDescent = (int) (paint.descent() + f);
        Bitmap bitmapCreateBitmap = Bitmap.createBitmap(iMeasureText, iDescent, Bitmap.Config.ARGB_8888);
        new Canvas(bitmapCreateBitmap).drawText(this.text, iCalculateAlignmentValuesForText, f, paint);
        this.yOffsetText = fSanitizeTextSize - (iDescent / 2);
        int i = this.textAlignment;
        if (i == 0) {
            this.xOffsetText += bitmapCreateBitmap.getWidth() / 2;
        } else if (i == 2) {
            this.xOffsetText -= bitmapCreateBitmap.getWidth() / 2;
        }
        return bitmapCreateBitmap;
    }

    @Override // android.view.View.OnTouchListener
    public boolean onTouch(View view, MotionEvent event) {
        this.scaleGestureDetector.onTouchEvent(event);
        if (this.scaleGestureDetector.isInProgress()) {
            return true;
        }
        this.visualPlacementTouchListener.onTouch(this.imageView, event, this);
        return true;
    }

    @Override // androidx.appcompat.app.AppCompatActivity, androidx.fragment.app.FragmentActivity, android.app.Activity
    protected void onStart() {
        super.onStart();
        getWindow().getDecorView().setSystemUiVisibility(5894);
    }

    @Override // androidx.activity.ComponentActivity, android.app.Activity
    public void onBackPressed() {
        int iRound = Math.round(this.xCoord / this.layoutWidthRatio);
        int iRound2 = Math.round(this.yCoord / this.layoutHeightRatio);
        if (this.translateX != iRound || this.translateY != iRound2) {
            showSaveChangesDialog(this);
        } else {
            super.onBackPressed();
        }
    }

    @Override // android.content.DialogInterface.OnClickListener
    public void onClick(DialogInterface dialog, int which) {
        if (which == -2) {
            ToastUtil.showError(this, R.string.formula_editor_changes_discarded);
            finish();
        } else {
            if (which != -1) {
                return;
            }
            finishWithResult();
        }
    }

    private void finishWithResult() {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putInt(SpriteActivity.EXTRA_BRICK_HASH, getIntent().getIntExtra(SpriteActivity.EXTRA_BRICK_HASH, -1));
        int iRound = Math.round(this.xCoord / this.layoutWidthRatio);
        int iRound2 = Math.round(this.yCoord / this.layoutHeightRatio);
        float scaleX = (this.imageView.getScaleX() / this.scaleX) * 100.0f;
        boolean z = true;
        boolean z2 = Math.abs(this.initialRotation - this.imageView.getRotation()) > 0.1f;
        boolean z3 = Math.abs(this.initialScale - this.imageView.getScaleX()) > 0.01f;
        if (this.initialX == iRound && this.initialY == iRound2 && !z2 && !z3) {
            z = false;
        }
        bundle.putInt(X_COORDINATE_BUNDLE_ARGUMENT, iRound);
        bundle.putInt(Y_COORDINATE_BUNDLE_ARGUMENT, iRound2);
        bundle.putFloat(ROTATION_ANGLE_BUNDLE_ARGUMENT, this.imageView.getRotation());
        bundle.putFloat(SIZE_PERCENT_BUNDLE_ARGUMENT, scaleX);
        bundle.putBoolean(CHANGED_COORDINATES, z);
        bundle.putBoolean(EXTRA_IS_VARIABLE, this.isVariable);
        bundle.putBoolean(EXTRA_ROTATION_CHANGED, z2);
        bundle.putBoolean(EXTRA_SIZE_CHANGED, z3);
        intent.putExtras(bundle);
        setResult(-1, intent);
        finish();
    }

    private void showSaveChangesDialog(Context context) {
        new AlertDialog.Builder(new ContextThemeWrapper(context, 2131952355)).setTitle(R.string.formula_editor_discard_changes_dialog_title).setMessage(R.string.formula_editor_discard_changes_dialog_message).setPositiveButton(R.string.save, this).setNegativeButton(R.string.discard, this).setCancelable(true).show();
    }

    @Override // org.catrobat.catroid.visualplacement.CoordinateInterface
    public void setXCoordinate(float xCoordinate) {
        if (this.isText) {
            this.xCoord = xCoordinate - this.xOffsetText;
        } else {
            this.xCoord = xCoordinate;
        }
    }

    @Override // org.catrobat.catroid.visualplacement.CoordinateInterface
    public void setYCoordinate(float yCoordinate) {
        if (this.isText) {
            this.yCoord = (yCoordinate + this.yOffsetText) - (this.imageView.getDrawable().getIntrinsicHeight() / 2.0f);
        } else {
            this.yCoord = yCoordinate;
        }
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/visualplacement/VisualPlacementActivity$MyScaleGestureListener.class
      classes14.dex
     */
    private class MyScaleGestureListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        private float lastAngle;

        private MyScaleGestureListener() {
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            this.lastAngle = getAngle(detector.getCurrentSpanX(), detector.getCurrentSpanY());
            return true;
        }

        @Override // android.view.ScaleGestureDetector.SimpleOnScaleGestureListener, android.view.ScaleGestureDetector.OnScaleGestureListener
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            if (!VisualPlacementActivity.this.isVariable) {
                VisualPlacementActivity.this.imageView.setScaleX(VisualPlacementActivity.this.imageView.getScaleX() * scaleFactor);
                VisualPlacementActivity.this.imageView.setScaleY(VisualPlacementActivity.this.imageView.getScaleY() * scaleFactor);
            }
            if (detector.getCurrentSpan() <= 0.0f) {
                return true;
            }
            float angle = getAngle(detector.getCurrentSpanX(), detector.getCurrentSpanY());
            float f = angle - this.lastAngle;
            if (Math.abs(f) < 10.0f && !VisualPlacementActivity.this.isText) {
                VisualPlacementActivity.this.imageView.setRotation(VisualPlacementActivity.this.imageView.getRotation() + f);
            }
            this.lastAngle = angle;
            return true;
        }

        private float getAngle(float dx, float dy) {
            return (float) Math.toDegrees(Math.atan2(dy, dx));
        }
    }
}
