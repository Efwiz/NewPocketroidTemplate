package org.catrobat.catroid.embroidery;

import androidx.constraintlayout.core.motion.utils.TypedValues;
import androidx.mediarouter.media.GlobalMediaRouter;
import com.android.apksig.internal.apk.AndroidBinXmlParser;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.net.HttpStatus;
import com.gaurav.avnc.vnc.XKeySym;
import com.google.android.gms.cast.MediaError;
import org.apache.commons.compress.archivers.tar.TarConstants;
import org.billthefarmer.mididriver.MidiConstants;
import org.eclipse.jdt.internal.compiler.parser.ParserBasicInformation;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/embroidery/DSTFileConstants.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public final class DSTFileConstants {
    public static final String DST_HEADER = "ST:%-6d\n\u001aCO:%-2d\n\u001a+X:%-4d\n\u001a-X:%-4d\n\u001a+Y:%-4d\n\u001a-Y:%-4d\n\u001aAX:%-5d\n\u001aAY:%-5d\n\u001aMX:%-5d\n\u001aMY:%-5d\n\u001aPD:%-5s\n\u001a";
    public static final String DST_HEADER_LABEL = "LA:%-15s\n\u001a";
    static final int MAX_DISTANCE = 121;
    private static final float STITCH_POINT_UNIT_FACTOR = 2.0f;
    private static final char SUBSTITUTE_CHAR = 26;
    public static final int[] CONVERSION_TABLE = {0, 1, 6, 4, 5, 26, 24, 25, 18, 16, 17, 22, 20, 21, 106, 104, 105, 98, 96, 97, 102, 100, 101, 74, 72, 73, 66, 64, 65, 70, 68, 69, 90, 88, 89, 82, 80, 81, 86, 84, 85, XKeySym.XK_Scedilla, 424, 425, XKeySym.XK_breve, 416, 417, 422, 420, 421, 394, 392, 393, 386, AndroidBinXmlParser.Chunk.RES_XML_TYPE_RESOURCE_MAP, 385, 390, 388, 389, HttpStatus.SC_GONE, HttpStatus.SC_REQUEST_TIMEOUT, HttpStatus.SC_CONFLICT, 402, 400, 401, HttpStatus.SC_NOT_ACCEPTABLE, 404, HttpStatus.SC_METHOD_NOT_ALLOWED, 298, 296, 297, ParserBasicInformation.SCOPE_UBOUND, 288, 289, 294, 292, 293, 266, GlobalMediaRouter.CallbackHandler.MSG_ROUTE_ANOTHER_SELECTED, 265, 258, 256, 257, GlobalMediaRouter.CallbackHandler.MSG_ROUTE_SELECTED, 260, GlobalMediaRouter.CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED, 282, 280, 281, 274, 272, 273, 278, 276, 277, 362, 360, 361, 354, 352, 353, 358, 356, 357, 330, 328, 329, MediaError.DetailedErrorCode.DASH_NO_INIT, 320, MediaError.DetailedErrorCode.DASH_NETWORK, 326, 324, 325, 346, 344, TarConstants.XSTAR_PREFIX_OFFSET, 338, 336, 337, 342, 340, 341, 2, 9, 8, 10, 37, 36, 38, 33, 32, 34, 41, 40, 42, 149, 148, 150, 145, 144, 146, 153, 152, 154, 133, 132, 134, 129, 128, 130, 137, 136, 138, 165, 164, 166, 161, 160, 162, 169, 168, 170, 597, 596, 598, 593, 592, 594, 601, 600, TypedValues.MotionType.TYPE_QUANTIZE_MOTION_PHASE, 581, 580, 582, 577, 576, 578, 585, 584, 586, 613, TypedValues.MotionType.TYPE_QUANTIZE_INTERPOLATOR_ID, 614, TypedValues.MotionType.TYPE_POLAR_RELATIVETO, TypedValues.MotionType.TYPE_DRAW_PATH, TypedValues.MotionType.TYPE_QUANTIZE_MOTIONSTEPS, 617, 616, 618, 533, 532, 534, 529, 528, 530, 537, 536, 538, GL20.GL_NOTEQUAL, 516, 518, 513, 512, 514, 521, 520, 522, 549, 548, 550, 545, 544, 546, 553, 552, 554, 661, 660, 662, 657, 656, 658, 665, 664, 666, 645, 644, 646, 641, 640, 642, 649, 648, 650, 677, 676, XKeySym.XK_Hcircumflex, XKeySym.XK_Hstroke, 672, 674, XKeySym.XK_Iabovedot, 680, 682};
    public static final String HEADER_FILL = String.format("%388s", " ");
    static final byte[] FILE_END = {0, 0, MidiConstants.SONG_SELECT};

    private DSTFileConstants() {
    }

    public static float getMaxDistanceBetweenPoints(float x1, float y1, float x2, float y2) {
        return Math.max(Math.abs(toEmbroideryUnit(x1 - x2)), Math.abs(toEmbroideryUnit(y1 - y2)));
    }

    public static int toEmbroideryUnit(float value) {
        return Math.round(value * 2.0f);
    }
}
