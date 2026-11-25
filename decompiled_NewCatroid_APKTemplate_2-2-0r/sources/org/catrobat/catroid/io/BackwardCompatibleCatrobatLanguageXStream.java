package org.catrobat.catroid.io;

import android.util.Log;
import android.util.Pair;
import androidx.constraintlayout.core.motion.utils.TypedValues;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.ConversionException;
import com.thoughtworks.xstream.converters.reflection.PureJavaReflectionProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.catrobat.catroid.content.bricks.Brick;
import org.jetbrains.kotlin.cli.common.modules.ModuleXmlParser;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/* JADX WARN: Classes with same name are omitted, all sources:
  NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/BackwardCompatibleCatrobatLanguageXStream.class
  classes14.dex
 */
/* JADX INFO: loaded from: classes14.dex */
public class BackwardCompatibleCatrobatLanguageXStream extends XStream {
    private static final String TAG = "BackwardCompatibleCatrobatLanguageXStream";
    private HashMap<String, BrickInfo> brickInfoMap;
    private HashMap<String, String> scriptInfoMap;

    public BackwardCompatibleCatrobatLanguageXStream(PureJavaReflectionProvider reflectionProvider) {
        super(reflectionProvider);
    }

    public Object getProjectFromXML(File file) {
        try {
            return super.fromXML(file);
        } catch (ConversionException e) {
            Log.d(TAG, "Conversion error " + e.getLocalizedMessage());
            modifyXMLToSupportUnknownFields(file);
            return super.fromXML(file);
        }
    }

    private void initializeBrickInfoMap() {
        if (this.brickInfoMap != null) {
            return;
        }
        this.brickInfoMap = new HashMap<>();
        BrickInfo brickInfo = new BrickInfo("AskBrick");
        brickInfo.addBrickFieldToMap("askQuestion", Brick.BrickField.ASK_QUESTION);
        this.brickInfoMap.put("askBrick", brickInfo);
        BrickInfo brickInfo2 = new BrickInfo("AskSpeechBrick");
        brickInfo2.addBrickFieldToMap("askQuestionSpoken", Brick.BrickField.ASK_SPEECH_QUESTION);
        this.brickInfoMap.put("askSpeechBrick", brickInfo2);
        this.brickInfoMap.put("startListeningBrick", new BrickInfo("StartListeningBrick"));
        this.brickInfoMap.put("setSpeechLanguageBrick", new BrickInfo("SetListeningLanguageBrick"));
        this.brickInfoMap.put("broadcastBrick", new BrickInfo("BroadcastBrick"));
        this.brickInfoMap.put("broadcastReceiverBrick", new BrickInfo("BroadcastReceiverBrick"));
        this.brickInfoMap.put("broadcastWaitBrick", new BrickInfo("BroadcastWaitBrick"));
        BrickInfo brickInfo3 = new BrickInfo("ChangeBrightnessByNBrick");
        brickInfo3.addBrickFieldToMap("changeBrightness", Brick.BrickField.BRIGHTNESS_CHANGE);
        this.brickInfoMap.put("changeBrightnessByNBrick", brickInfo3);
        BrickInfo brickInfo4 = new BrickInfo("ChangeColorByNBrick");
        brickInfo4.addBrickFieldToMap("changeColor", Brick.BrickField.COLOR_CHANGE);
        this.brickInfoMap.put("changeColorByNBrick", brickInfo4);
        BrickInfo brickInfo5 = new BrickInfo("ChangeTransparencyByNBrick");
        brickInfo5.addBrickFieldToMap("changeTransparency", Brick.BrickField.TRANSPARENCY_CHANGE);
        this.brickInfoMap.put("changeTransparencyByNBrick", brickInfo5);
        BrickInfo brickInfo6 = new BrickInfo("ChangeSizeByNBrick");
        brickInfo6.addBrickFieldToMap("size", Brick.BrickField.SIZE_CHANGE);
        this.brickInfoMap.put("changeSizeByNBrick", brickInfo6);
        BrickInfo brickInfo7 = new BrickInfo("ChangeVariableBrick");
        brickInfo7.addBrickFieldToMap("variableFormula", Brick.BrickField.VARIABLE_CHANGE);
        this.brickInfoMap.put("changeVariableBrick", brickInfo7);
        BrickInfo brickInfo8 = new BrickInfo("ChangeVolumeByNBrick");
        brickInfo8.addBrickFieldToMap("volume", Brick.BrickField.VOLUME_CHANGE);
        this.brickInfoMap.put("changeVolumeByNBrick", brickInfo8);
        BrickInfo brickInfo9 = new BrickInfo("ChangeXByNBrick");
        brickInfo9.addBrickFieldToMap("xMovement", Brick.BrickField.X_POSITION_CHANGE);
        this.brickInfoMap.put("changeXByNBrick", brickInfo9);
        BrickInfo brickInfo10 = new BrickInfo("ChangeYByNBrick");
        brickInfo10.addBrickFieldToMap("yMovement", Brick.BrickField.Y_POSITION_CHANGE);
        this.brickInfoMap.put("changeYByNBrick", brickInfo10);
        this.brickInfoMap.put("resetTimerBrick", new BrickInfo("ResetTimerBrick"));
        this.brickInfoMap.put("OpenUrlBrick", new BrickInfo("OpenUrlBrick"));
        this.brickInfoMap.put("clearGraphicEffectBrick", new BrickInfo("ClearGraphicEffectBrick"));
        this.brickInfoMap.put("comeToFrontBrick", new BrickInfo("ComeToFrontBrick"));
        this.brickInfoMap.put("foreverBrick", new BrickInfo("ForeverBrick"));
        BrickInfo brickInfo11 = new BrickInfo("GlideToBrick");
        brickInfo11.addBrickFieldToMap("xDestination", Brick.BrickField.X_DESTINATION);
        brickInfo11.addBrickFieldToMap("yDestination", Brick.BrickField.Y_DESTINATION);
        brickInfo11.addBrickFieldToMap("durationInSeconds", Brick.BrickField.DURATION_IN_SECONDS);
        this.brickInfoMap.put("glideToBrick", brickInfo11);
        BrickInfo brickInfo12 = new BrickInfo("GoNStepsBackBrick");
        brickInfo12.addBrickFieldToMap("steps", Brick.BrickField.STEPS);
        this.brickInfoMap.put("goNStepsBackBrick", brickInfo12);
        this.brickInfoMap.put("hideBrick", new BrickInfo("HideBrick"));
        BrickInfo brickInfo13 = new BrickInfo("IfLogicBeginBrick");
        brickInfo13.addBrickFieldToMap("ifCondition", Brick.BrickField.IF_CONDITION);
        this.brickInfoMap.put("ifLogicBeginBrick", brickInfo13);
        this.brickInfoMap.put("ifLogicElseBrick", new BrickInfo("IfLogicElseBrick"));
        this.brickInfoMap.put("ifLogicEndBrick", new BrickInfo("IfLogicEndBrick"));
        BrickInfo brickInfo14 = new BrickInfo("IfThenLogicBeginBrick");
        brickInfo14.addBrickFieldToMap("ifCondition", Brick.BrickField.IF_CONDITION);
        this.brickInfoMap.put("ifThenLogicBeginBrick", brickInfo14);
        this.brickInfoMap.put("ifThenLogicEndBrick", new BrickInfo("IfThenLogicEndBrick"));
        this.brickInfoMap.put("ifOnEdgeBounceBrick", new BrickInfo("IfOnEdgeBounceBrick"));
        this.brickInfoMap.put("flashBrick", new BrickInfo("FlashBrick"));
        BrickInfo brickInfo15 = new BrickInfo("LegoNxtMotorMoveBrick");
        brickInfo15.addBrickFieldToMap("speed", Brick.BrickField.LEGO_NXT_SPEED);
        this.brickInfoMap.put("legoNxtMotorMoveBrick", brickInfo15);
        this.brickInfoMap.put("legoNxtMotorStopBrick", new BrickInfo("LegoNxtMotorStopBrick"));
        BrickInfo brickInfo16 = new BrickInfo("LegoNxtMotorTurnAngleBrick");
        brickInfo16.addBrickFieldToMap("degrees", Brick.BrickField.LEGO_NXT_DEGREES);
        this.brickInfoMap.put("legoNxtMotorTurnAngleBrick", brickInfo16);
        BrickInfo brickInfo17 = new BrickInfo("LegoNxtPlayToneBrick");
        brickInfo17.addBrickFieldToMap("frequency", Brick.BrickField.LEGO_NXT_FREQUENCY);
        brickInfo17.addBrickFieldToMap("durationInSeconds", Brick.BrickField.LEGO_NXT_DURATION_IN_SECONDS);
        this.brickInfoMap.put("legoNxtPlayToneBrick", brickInfo17);
        BrickInfo brickInfo18 = new BrickInfo("PhiroMotorMoveForwardBrick");
        brickInfo18.addBrickFieldToMap("speed", Brick.BrickField.PHIRO_SPEED);
        this.brickInfoMap.put("phiroMotorMoveForwardBrick", brickInfo18);
        BrickInfo brickInfo19 = new BrickInfo("PhiroMotorMoveBackwardBrick");
        brickInfo19.addBrickFieldToMap("speed", Brick.BrickField.PHIRO_SPEED);
        this.brickInfoMap.put("phiroMotorMoveBackwardBrick", brickInfo19);
        this.brickInfoMap.put("phiroMotorStopBrick", new BrickInfo("PhiroMotorStopBrick"));
        BrickInfo brickInfo20 = new BrickInfo("PhiroPlayToneBrick");
        brickInfo20.addBrickFieldToMap("durationInSeconds", Brick.BrickField.PHIRO_DURATION_IN_SECONDS);
        this.brickInfoMap.put("phiroPlayToneBrick", brickInfo20);
        BrickInfo brickInfo21 = new BrickInfo("PhiroRGBLightBrick");
        brickInfo21.addBrickFieldToMap("light", Brick.BrickField.PHIRO_LIGHT_RED);
        brickInfo21.addBrickFieldToMap("light", Brick.BrickField.PHIRO_LIGHT_GREEN);
        brickInfo21.addBrickFieldToMap("light", Brick.BrickField.PHIRO_LIGHT_BLUE);
        this.brickInfoMap.put("phiroRGBLightBrick", brickInfo21);
        this.brickInfoMap.put("phiroSensorBrick", new BrickInfo("PhiroIfLogicBeginBrick"));
        this.brickInfoMap.put("phiroSensorElseBrick", new BrickInfo("IfLogicElseBrick"));
        this.brickInfoMap.put("phiroSensorEndBrick", new BrickInfo("IfLogicEndBrick"));
        BrickInfo brickInfo22 = new BrickInfo("ArduinoSendDigitalValueBrick");
        brickInfo22.addBrickFieldToMap("digitalPinNumber", Brick.BrickField.ARDUINO_DIGITAL_PIN_NUMBER);
        brickInfo22.addBrickFieldToMap("digitalPinValue", Brick.BrickField.ARDUINO_DIGITAL_PIN_VALUE);
        this.brickInfoMap.put("arduinoSendDigitalValueBrick", brickInfo22);
        BrickInfo brickInfo23 = new BrickInfo("ArduinoSendPWMValueBrick");
        brickInfo23.addBrickFieldToMap("pwmPinNumber", Brick.BrickField.ARDUINO_ANALOG_PIN_NUMBER);
        brickInfo23.addBrickFieldToMap("pwmPinValue", Brick.BrickField.ARDUINO_ANALOG_PIN_VALUE);
        this.brickInfoMap.put("arduinoSendPWMValueBrick", brickInfo23);
        BrickInfo brickInfo24 = new BrickInfo("RaspiSendDigitalValueBrick");
        brickInfo24.addBrickFieldToMap("digitalPinNumber", Brick.BrickField.RASPI_DIGITAL_PIN_NUMBER);
        brickInfo24.addBrickFieldToMap("digitalPinValue", Brick.BrickField.RASPI_DIGITAL_PIN_VALUE);
        this.brickInfoMap.put("raspiSendDigitalValueBrick", brickInfo24);
        BrickInfo brickInfo25 = new BrickInfo("RaspiPwmBrick");
        brickInfo25.addBrickFieldToMap("digitalPinNumber", Brick.BrickField.RASPI_DIGITAL_PIN_NUMBER);
        brickInfo25.addBrickFieldToMap("pwmFrequency", Brick.BrickField.RASPI_PWM_FREQUENCY);
        brickInfo25.addBrickFieldToMap("pwmPercentage", Brick.BrickField.RASPI_PWM_PERCENTAGE);
        this.brickInfoMap.put("raspiPwmBrick", brickInfo25);
        BrickInfo brickInfo26 = new BrickInfo("RaspiIfLogicBeginBrick");
        brickInfo26.addBrickFieldToMap("digitalPinNumber", Brick.BrickField.IF_CONDITION);
        this.brickInfoMap.put("raspiIfLogicBeginBrick", brickInfo26);
        this.brickInfoMap.put("userDefinedBrick", new BrickInfo("UserDefinedBrick"));
        this.brickInfoMap.put("loopEndBrick", new BrickInfo("LoopEndBrick"));
        this.brickInfoMap.put("loopEndlessBrick", new BrickInfo("LoopEndlessBrick"));
        BrickInfo brickInfo27 = new BrickInfo("MoveNStepsBrick");
        brickInfo27.addBrickFieldToMap("steps", Brick.BrickField.STEPS);
        this.brickInfoMap.put("moveNStepsBrick", brickInfo27);
        this.brickInfoMap.put("nextLookBrick", new BrickInfo("NextLookBrick"));
        this.brickInfoMap.put("previousLookBrick", new BrickInfo("PreviousLookBrick"));
        BrickInfo brickInfo28 = new BrickInfo("NoteBrick");
        brickInfo28.addBrickFieldToMap("note", Brick.BrickField.NOTE);
        this.brickInfoMap.put("noteBrick", brickInfo28);
        BrickInfo brickInfo29 = new BrickInfo("PlaceAtBrick");
        brickInfo29.addBrickFieldToMap("xPosition", Brick.BrickField.X_POSITION);
        brickInfo29.addBrickFieldToMap("yPosition", Brick.BrickField.Y_POSITION);
        this.brickInfoMap.put("placeAtBrick", brickInfo29);
        this.brickInfoMap.put("goToBrick", new BrickInfo("GoToBrick"));
        this.brickInfoMap.put("playDrumForBeatsBrick", new BrickInfo("PlayDrumForBeatsBrick"));
        this.brickInfoMap.put("playSoundBrick", new BrickInfo("PlaySoundBrick"));
        this.brickInfoMap.put("playSoundAndWaitBrick", new BrickInfo("PlaySoundAndWaitBrick"));
        this.brickInfoMap.put("playSoundAtBrick", new BrickInfo("PlaySoundAtBrick"));
        this.brickInfoMap.put("setTempoBrick", new BrickInfo("SetTempoBrick"));
        this.brickInfoMap.put("changeTempoByNBrick", new BrickInfo("ChangeTempoByNBrick"));
        this.brickInfoMap.put("playNoteForBeatsBrick", new BrickInfo("PlayNoteForBeatsBrick"));
        BrickInfo brickInfo30 = new BrickInfo("PointInDirectionBrick");
        brickInfo30.addBrickFieldToMap("degrees", Brick.BrickField.DEGREES);
        this.brickInfoMap.put("pointInDirectionBrick", brickInfo30);
        this.brickInfoMap.put("pointToBrick", new BrickInfo("PointToBrick"));
        BrickInfo brickInfo31 = new BrickInfo("RepeatBrick");
        brickInfo31.addBrickFieldToMap("timesToRepeat", Brick.BrickField.TIMES_TO_REPEAT);
        this.brickInfoMap.put("repeatBrick", brickInfo31);
        this.brickInfoMap.put("sceneTransitionBrick", new BrickInfo("SceneTransitionBrick"));
        this.brickInfoMap.put("sceneStartBrick", new BrickInfo("SceneStartBrick"));
        BrickInfo brickInfo32 = new BrickInfo("SetBrightnessBrick");
        brickInfo32.addBrickFieldToMap("brightness", Brick.BrickField.BRIGHTNESS);
        this.brickInfoMap.put("setBrightnessBrick", brickInfo32);
        BrickInfo brickInfo33 = new BrickInfo("SetColorBrick");
        brickInfo33.addBrickFieldToMap(TypedValues.Custom.S_COLOR, Brick.BrickField.COLOR);
        this.brickInfoMap.put("setColorBrick", brickInfo33);
        BrickInfo brickInfo34 = new BrickInfo("SetTransparencyBrick");
        brickInfo34.addBrickFieldToMap("transparency", Brick.BrickField.TRANSPARENCY);
        this.brickInfoMap.put("setTransparencyBrick", brickInfo34);
        this.brickInfoMap.put("setBackgroundBrick", new BrickInfo("SetBackgroundBrick"));
        this.brickInfoMap.put("setBackgroundAndWaitBrick", new BrickInfo("SetBackgroundAndWaitBrick"));
        this.brickInfoMap.put("setLookBrick", new BrickInfo("SetLookBrick"));
        this.brickInfoMap.put("setRotationStyleBrick", new BrickInfo("SetRotationStyleBrick"));
        BrickInfo brickInfo35 = new BrickInfo("SetSizeToBrick");
        brickInfo35.addBrickFieldToMap("size", Brick.BrickField.SIZE);
        this.brickInfoMap.put("setSizeToBrick", brickInfo35);
        BrickInfo brickInfo36 = new BrickInfo("SetVariableBrick");
        brickInfo36.addBrickFieldToMap("variableFormula", Brick.BrickField.VARIABLE);
        this.brickInfoMap.put("setVariableBrick", brickInfo36);
        BrickInfo brickInfo37 = new BrickInfo("SetVolumeToBrick");
        brickInfo37.addBrickFieldToMap("volume", Brick.BrickField.VOLUME);
        this.brickInfoMap.put("setVolumeToBrick", brickInfo37);
        BrickInfo brickInfo38 = new BrickInfo("SetXBrick");
        brickInfo38.addBrickFieldToMap("xPosition", Brick.BrickField.X_POSITION);
        this.brickInfoMap.put("setXBrick", brickInfo38);
        BrickInfo brickInfo39 = new BrickInfo("SetYBrick");
        brickInfo39.addBrickFieldToMap("yPosition", Brick.BrickField.Y_POSITION);
        this.brickInfoMap.put("setYBrick", brickInfo39);
        this.brickInfoMap.put("showBrick", new BrickInfo("ShowBrick"));
        BrickInfo brickInfo40 = new BrickInfo("SpeakBrick");
        brickInfo40.addBrickFieldToMap("text", Brick.BrickField.SPEAK);
        this.brickInfoMap.put("speakBrick", brickInfo40);
        BrickInfo brickInfo41 = new BrickInfo("ThinkBubbleBrick");
        brickInfo41.addBrickFieldToMap("text", Brick.BrickField.STRING);
        this.brickInfoMap.put("thinkBubbleBrick", brickInfo41);
        BrickInfo brickInfo42 = new BrickInfo("SayBubbleBrick");
        brickInfo42.addBrickFieldToMap("text", Brick.BrickField.STRING);
        this.brickInfoMap.put("sayBubbleBrick", brickInfo42);
        BrickInfo brickInfo43 = new BrickInfo("ThinkForBubbleBrick");
        brickInfo43.addBrickFieldToMap("text", Brick.BrickField.STRING);
        brickInfo43.addBrickFieldToMap("durationInSeconds", Brick.BrickField.DURATION_IN_SECONDS);
        this.brickInfoMap.put("thinkForBubbleBrick", brickInfo43);
        BrickInfo brickInfo44 = new BrickInfo("SayForBubbleBrick");
        brickInfo44.addBrickFieldToMap("text", Brick.BrickField.STRING);
        brickInfo44.addBrickFieldToMap("durationInSeconds", Brick.BrickField.DURATION_IN_SECONDS);
        this.brickInfoMap.put("sayForBubbleBrick", brickInfo44);
        BrickInfo brickInfo45 = new BrickInfo("SpeakAndWaitBrick");
        brickInfo45.addBrickFieldToMap("text", Brick.BrickField.SPEAK);
        this.brickInfoMap.put("speakAndWaitBrick", brickInfo45);
        this.brickInfoMap.put("whenBrick", new BrickInfo("WhenBrick"));
        this.brickInfoMap.put("emptyEventBrick", new BrickInfo("EmptyEventBrick"));
        this.brickInfoMap.put("whenConditionBrick", new BrickInfo("WhenConditionBrick"));
        BrickInfo brickInfo46 = new BrickInfo("TurnLeftBrick");
        brickInfo46.addBrickFieldToMap("degrees", Brick.BrickField.TURN_LEFT_DEGREES);
        this.brickInfoMap.put("turnLeftBrick", brickInfo46);
        BrickInfo brickInfo47 = new BrickInfo("TurnRightBrick");
        brickInfo47.addBrickFieldToMap("degrees", Brick.BrickField.TURN_RIGHT_DEGREES);
        this.brickInfoMap.put("turnRightBrick", brickInfo47);
        this.brickInfoMap.put("vibrationBrick", new BrickInfo("VibrationBrick"));
        BrickInfo brickInfo48 = new BrickInfo("WaitBrick");
        brickInfo48.addBrickFieldToMap("timeToWaitInSeconds", Brick.BrickField.TIME_TO_WAIT_IN_SECONDS);
        this.brickInfoMap.put("waitBrick", brickInfo48);
        this.brickInfoMap.put("stopAllSoundsBrick", new BrickInfo("StopAllSoundsBrick"));
        this.brickInfoMap.put("whenStartedBrick", new BrickInfo("WhenStartedBrick"));
        this.brickInfoMap.put("stopScriptBrick", new BrickInfo("StopScriptBrick"));
        this.brickInfoMap.put("whenNfcBrick", new BrickInfo("WhenNfcBrick"));
        this.brickInfoMap.put("setNfcTagBrick", new BrickInfo("SetNfcTagBrick"));
        this.brickInfoMap.put("whenClonedBrick", new BrickInfo("WhenClonedBrick"));
        this.brickInfoMap.put("cloneBrick", new BrickInfo("CloneBrick"));
        this.brickInfoMap.put("deleteThisCloneBrick", new BrickInfo("DeleteThisCloneBrick"));
        this.brickInfoMap.put("dronePlayLedAnimationBrick", new BrickInfo("DronePlayLedAnimationBrick"));
        this.brickInfoMap.put("droneGoEmergencyBrick", new BrickInfo("DroneEmergencyBrick"));
        this.brickInfoMap.put("droneTakeOffBrick", new BrickInfo("DroneTakeOffLandBrick"));
        BrickInfo brickInfo49 = new BrickInfo("DroneMoveForwardBrick");
        brickInfo49.addBrickFieldToMap("timeToFlyInSeconds", Brick.BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
        brickInfo49.addBrickFieldToMap("powerInPercent", Brick.BrickField.DRONE_POWER_IN_PERCENT);
        this.brickInfoMap.put("droneMoveForwardBrick", brickInfo49);
        BrickInfo brickInfo50 = new BrickInfo("DroneMoveBackwardBrick");
        brickInfo50.addBrickFieldToMap("timeToFlyInSeconds", Brick.BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
        brickInfo50.addBrickFieldToMap("powerInPercent", Brick.BrickField.DRONE_POWER_IN_PERCENT);
        this.brickInfoMap.put("droneMoveBackwardBrick", brickInfo50);
        BrickInfo brickInfo51 = new BrickInfo("DroneMoveUpBrick");
        brickInfo51.addBrickFieldToMap("timeToFlyInSeconds", Brick.BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
        brickInfo51.addBrickFieldToMap("powerInPercent", Brick.BrickField.DRONE_POWER_IN_PERCENT);
        this.brickInfoMap.put("droneMoveUpBrick", brickInfo51);
        BrickInfo brickInfo52 = new BrickInfo("DroneMoveDownBrick");
        brickInfo52.addBrickFieldToMap("timeToFlyInSeconds", Brick.BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
        brickInfo52.addBrickFieldToMap("powerInPercent", Brick.BrickField.DRONE_POWER_IN_PERCENT);
        this.brickInfoMap.put("droneMoveDownBrick", brickInfo52);
        BrickInfo brickInfo53 = new BrickInfo("DroneMoveLeftBrick");
        brickInfo53.addBrickFieldToMap("timeToFlyInSeconds", Brick.BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
        brickInfo53.addBrickFieldToMap("powerInPercent", Brick.BrickField.DRONE_POWER_IN_PERCENT);
        this.brickInfoMap.put("droneMoveLeftBrick", brickInfo53);
        BrickInfo brickInfo54 = new BrickInfo("DroneMoveRightBrick");
        brickInfo54.addBrickFieldToMap("timeToFlyInSeconds", Brick.BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
        brickInfo54.addBrickFieldToMap("powerInPercent", Brick.BrickField.DRONE_POWER_IN_PERCENT);
        this.brickInfoMap.put("droneMoveRightBrick", brickInfo54);
        BrickInfo brickInfo55 = new BrickInfo("DroneTurnLeftBrick");
        brickInfo55.addBrickFieldToMap("timeToFlyInSeconds", Brick.BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
        brickInfo55.addBrickFieldToMap("powerInPercent", Brick.BrickField.DRONE_POWER_IN_PERCENT);
        this.brickInfoMap.put("droneTurnLeftBrick", brickInfo55);
        BrickInfo brickInfo56 = new BrickInfo("DroneTurnRightBrick");
        brickInfo56.addBrickFieldToMap("timeToFlyInSeconds", Brick.BrickField.DRONE_TIME_TO_FLY_IN_SECONDS);
        brickInfo56.addBrickFieldToMap("powerInPercent", Brick.BrickField.DRONE_POWER_IN_PERCENT);
        this.brickInfoMap.put("droneTurnRightBrick", brickInfo56);
        this.brickInfoMap.put("droneSwitchCameraBrick", new BrickInfo("DroneSwitchCameraBrick"));
        this.brickInfoMap.put("droneEmergencyBrick", new BrickInfo("DroneEmergencyBrick"));
        this.brickInfoMap.put("droneFlipBrick", new BrickInfo("DroneFlipBrick"));
        BrickInfo brickInfo57 = new BrickInfo("SetTextBrick");
        brickInfo57.addBrickFieldToMap("xDestination", Brick.BrickField.X_DESTINATION);
        brickInfo57.addBrickFieldToMap("yDestination", Brick.BrickField.Y_DESTINATION);
        brickInfo57.addBrickFieldToMap("string", Brick.BrickField.STRING);
        this.brickInfoMap.put("setTextBrick", brickInfo57);
        this.brickInfoMap.put("showTextBrick", new BrickInfo("ShowTextBrick"));
        this.brickInfoMap.put("hideTextBrick", new BrickInfo("HideTextBrick"));
        this.brickInfoMap.put("videoBrick", new BrickInfo("CameraBrick"));
        this.brickInfoMap.put("chooseCameraBrick", new BrickInfo("ChooseCameraBrick"));
        this.brickInfoMap.put("collisionReceiverBrick", new BrickInfo("WhenBounceOffBrick"));
        BrickInfo brickInfo58 = new BrickInfo("SetBounceBrick");
        brickInfo58.addBrickFieldToMap("bounceFactor", Brick.BrickField.PHYSICS_BOUNCE_FACTOR);
        this.brickInfoMap.put("setBounceBrick", brickInfo58);
        BrickInfo brickInfo59 = new BrickInfo("SetFrictionBrick");
        brickInfo59.addBrickFieldToMap("friction", Brick.BrickField.PHYSICS_FRICTION);
        this.brickInfoMap.put("setFrictionBrick", brickInfo59);
        BrickInfo brickInfo60 = new BrickInfo("SetGravityBrick");
        brickInfo60.addBrickFieldToMap("gravityX", Brick.BrickField.PHYSICS_GRAVITY_X);
        brickInfo60.addBrickFieldToMap("gravityY", Brick.BrickField.PHYSICS_GRAVITY_Y);
        this.brickInfoMap.put("setGravityBrick", brickInfo60);
        BrickInfo brickInfo61 = new BrickInfo("SetMassBrick");
        brickInfo61.addBrickFieldToMap("mass", Brick.BrickField.PHYSICS_MASS);
        this.brickInfoMap.put("setMassBrick", brickInfo61);
        this.brickInfoMap.put("setPhysicsObjectTypeBrick", new BrickInfo("SetPhysicsObjectTypeBrick"));
        BrickInfo brickInfo62 = new BrickInfo("SetVelocityBrick");
        brickInfo62.addBrickFieldToMap("velocityX", Brick.BrickField.PHYSICS_VELOCITY_X);
        brickInfo62.addBrickFieldToMap("velocityY", Brick.BrickField.PHYSICS_VELOCITY_Y);
        this.brickInfoMap.put("setVelocityBrick", brickInfo62);
        BrickInfo brickInfo63 = new BrickInfo("TurnLeftSpeedBrick");
        brickInfo63.addBrickFieldToMap("turnLeftSpeed", Brick.BrickField.PHYSICS_TURN_LEFT_SPEED);
        this.brickInfoMap.put("turnLeftSpeedBrick", brickInfo63);
        BrickInfo brickInfo64 = new BrickInfo("TurnRightSpeedBrick");
        brickInfo64.addBrickFieldToMap("turnRightSpeed", Brick.BrickField.PHYSICS_TURN_RIGHT_SPEED);
        this.brickInfoMap.put("turnRightSpeedBrick", brickInfo64);
        this.brickInfoMap.put("penDownBrick", new BrickInfo("PenDownBrick"));
        this.brickInfoMap.put("penUpBrick", new BrickInfo("PenUpBrick"));
        this.brickInfoMap.put("stampBrick", new BrickInfo("StampBrick"));
        this.brickInfoMap.put("clearBackgroundBrick", new BrickInfo("ClearBackgroundBrick"));
        BrickInfo brickInfo65 = new BrickInfo("SetPenSizeBrick");
        brickInfo65.addBrickFieldToMap("penSize", Brick.BrickField.PEN_SIZE);
        this.brickInfoMap.put("setPenSizeBrick", brickInfo65);
        BrickInfo brickInfo66 = new BrickInfo("SetPenColorBrick");
        brickInfo66.addBrickFieldToMap("penColor", Brick.BrickField.PEN_COLOR_RED);
        brickInfo66.addBrickFieldToMap("penColor", Brick.BrickField.PEN_COLOR_GREEN);
        brickInfo66.addBrickFieldToMap("penColor", Brick.BrickField.PEN_COLOR_BLUE);
        this.brickInfoMap.put("setPenColorBrick", brickInfo66);
        this.brickInfoMap.put("whenGamepadButtonBrick", new BrickInfo("WhenGamepadButtonBrick"));
        this.brickInfoMap.put("sewUpBrick", new BrickInfo("SewUpBrick"));
        this.brickInfoMap.put("setThreadColorBrick", new BrickInfo("SetThreadColorBrick"));
        this.brickInfoMap.put("fadeParticleEffectBrick", new BrickInfo("FadeParticleEffectBrick"));
        this.brickInfoMap.put("particleEffectAdditiveBrick", new BrickInfo("ParticleEffectAdditivityBrick"));
    }

    private void initializeScriptInfoMap() {
        if (this.scriptInfoMap != null) {
            return;
        }
        HashMap<String, String> map = new HashMap<>();
        this.scriptInfoMap = map;
        map.put("startScript", "StartScript");
        this.scriptInfoMap.put("whenScript", "WhenScript");
        this.scriptInfoMap.put("whenConditionScript", "WhenConditionScript");
        this.scriptInfoMap.put("whenBackgroundChangesScript", "WhenBackgroundChangesScript");
        this.scriptInfoMap.put("broadcastScript", "BroadcastScript");
        this.scriptInfoMap.put("raspiInterruptScript", "RaspiInterruptScript");
        this.scriptInfoMap.put("whenNfcScript", "WhenNfcScript");
        this.scriptInfoMap.put("collisionScript", "WhenBounceOffScript");
        this.scriptInfoMap.put("whenTouchDownScript", "WhenTouchDownScript");
        this.scriptInfoMap.put("whenGamepadButtonScript", "WhenGamepadButtonScript");
        this.scriptInfoMap.put("userDefinedScript", "UserDefinedScript");
    }

    private void modifyXMLToSupportUnknownFields(File file) {
        initializeScriptInfoMap();
        initializeBrickInfoMap();
        Document document = getDocument(file);
        if (document != null) {
            updateLegoNXTFields(document);
            convertChildNodeToAttribute(document, "lookList", "name");
            convertChildNodeToAttribute(document, "object", "name");
            deleteChildNodeByName(document, "scriptList", "object");
            deleteChildNodeByName(document, "brickList", "object");
            deleteChildNodeByName(document.getElementsByTagName("header").item(0), "isPhiroProProject");
            deleteChildNodeByName(document, "brickList", "inUserBrick");
            renameScriptChildNodeByName(document, "CollisionScript", "receivedMessage", "spriteToBounceOffName");
            renameScriptChildNodeByName(document, "CollisionScript", "spriteToCollideWithName", "spriteToBounceOffName");
            renameScriptType(document, "CollisionScript", "WhenBounceOffScript");
            modifyScriptLists(document);
            modifyBrickLists(document);
            modifyVariables(document);
            modifyCameraBricks(document);
            checkReferences(document.getDocumentElement());
            saveDocument(document, file);
        }
    }

    private void renameScriptType(Document doc, String oldTypeName, String newTypeName) {
        for (Node node : getScriptsOfType(doc, oldTypeName)) {
            if (node instanceof Element) {
                ((Element) node).setAttribute(ModuleXmlParser.TYPE, newTypeName);
            }
        }
    }

    private void renameScriptChildNodeByName(Document originalDocument, String scriptName, String oldChildNodeName, String newChildNodeName) {
        Iterator<Node> it = getScriptsOfType(originalDocument, scriptName).iterator();
        while (it.hasNext()) {
            Element elementFindNodeByName = findNodeByName(it.next(), oldChildNodeName);
            if (elementFindNodeByName != null) {
                originalDocument.renameNode(elementFindNodeByName, null, newChildNodeName);
            }
        }
    }

    private List<Node> getScriptsOfType(Document doc, String type) {
        return getElementsFilteredByAttribute(doc.getElementsByTagName("script"), ModuleXmlParser.TYPE, type);
    }

    private List<Node> getBricksOfType(Document doc, String type) {
        return getElementsFilteredByAttribute(doc.getElementsByTagName("brick"), ModuleXmlParser.TYPE, type);
    }

    private List<Node> getElementsFilteredByAttribute(NodeList unfiltered, String attributeName, String filterAttributeValue) {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i < unfiltered.getLength(); i++) {
            Node nodeItem = unfiltered.item(i);
            String attributeOfNode = getAttributeOfNode(attributeName, nodeItem);
            if (attributeOfNode != null && attributeOfNode.equals(filterAttributeValue)) {
                arrayList.add(nodeItem);
            }
        }
        return arrayList;
    }

    private String getAttributeOfNode(String attributeName, Node node) {
        NamedNodeMap attributes = node.getAttributes();
        if (attributes != null) {
            return attributes.getNamedItem(attributeName).getNodeValue();
        }
        return null;
    }

    private void updateLegoNXTFields(Document originalDocument) {
        NodeList elementsByTagName = originalDocument.getElementsByTagName("motor");
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            Node nodeItem = elementsByTagName.item(i);
            if (nodeItem.getTextContent().equals("MOTOR_A_C")) {
                nodeItem.setTextContent("MOTOR_B_C");
            }
        }
        NodeList elementsByTagName2 = originalDocument.getElementsByTagName("legoNxtMotorActionBrick");
        for (int i2 = 0; i2 < elementsByTagName2.getLength(); i2++) {
            Node nodeItem2 = elementsByTagName2.item(i2);
            originalDocument.renameNode(nodeItem2, nodeItem2.getNamespaceURI(), "legoNxtMotorMoveBrick");
        }
    }

    private void modifyVariables(Document originalDocument) {
        Node nodeItem = originalDocument.getElementsByTagName("variables").item(0);
        if (nodeItem != null) {
            originalDocument.renameNode(nodeItem, nodeItem.getNamespaceURI(), "data");
        } else {
            Log.e(TAG, "XML-Update: No variables to modify.");
        }
    }

    private void modifyCameraBricks(Document originalDocument) {
        String textContent;
        for (Pair pair : Arrays.asList(new Pair("CameraBrick", "spinnerSelectionON"), new Pair("ChooseCameraBrick", "spinnerSelectionFRONT"))) {
            String str = (String) pair.first;
            String str2 = (String) pair.second;
            for (Node node : getBricksOfType(originalDocument, str)) {
                Element elementFindNodeByName = findNodeByName(node, "spinnerSelectionID");
                if (elementFindNodeByName != null && (textContent = elementFindNodeByName.getTextContent()) != null) {
                    String str3 = textContent.equals("0") ? "false" : "true";
                    Element elementCreateElement = originalDocument.createElement(str2);
                    elementCreateElement.setTextContent(str3);
                    node.removeChild(elementFindNodeByName);
                    node.appendChild(elementCreateElement);
                }
            }
        }
    }

    private Document getDocument(File file) {
        try {
            Transformer transformerNewTransformer = TransformerFactory.newInstance().newTransformer();
            transformerNewTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformerNewTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(file);
            normalizeWhiteSpaces(document);
            return document;
        } catch (Exception e) {
            Log.e(TAG, "Failed to parse file to a Document", e);
            return null;
        }
    }

    private void saveDocument(Document doc, File file) {
        try {
            Transformer transformerNewTransformer = TransformerFactory.newInstance().newTransformer();
            transformerNewTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformerNewTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformerNewTransformer.transform(new DOMSource(doc), new StreamResult(file.getPath()));
        } catch (Exception e) {
            Log.e(TAG, "Failed to save document to file", e);
        }
    }

    private void normalizeWhiteSpaces(Document document) throws XPathExpressionException {
        NodeList nodeList = (NodeList) XPathFactory.newInstance().newXPath().evaluate("//text()[normalize-space(.)='']", document, XPathConstants.NODESET);
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nodeItem = nodeList.item(i);
            nodeItem.getParentNode().removeChild(nodeItem);
        }
    }

    private Element findNodeByName(Node parentNode, String nodeName) {
        NodeList childNodes = parentNode.getChildNodes();
        if (childNodes == null) {
            return null;
        }
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeName().equals(nodeName)) {
                return (Element) childNodes.item(i);
            }
        }
        return null;
    }

    private void deleteChildNodeByName(Node parentNode, String childNodeName) {
        Element elementFindNodeByName = findNodeByName(parentNode, childNodeName);
        if (elementFindNodeByName != null) {
            parentNode.removeChild(elementFindNodeByName);
        }
    }

    private void deleteChildNodeByName(Document doc, String listNodeName, String childNodeName) {
        NodeList elementsByTagName = doc.getElementsByTagName(listNodeName);
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            Node nodeItem = elementsByTagName.item(i);
            if (nodeItem.hasChildNodes()) {
                NodeList childNodes = nodeItem.getChildNodes();
                for (int i2 = 0; i2 < childNodes.getLength(); i2++) {
                    deleteChildNodeByName(childNodes.item(i2), childNodeName);
                }
            }
        }
    }

    private void copyAttributesIfNeeded(Node sourceNode, Element destinationNode) {
        if (sourceNode.getNodeName().equals("loopEndlessBrick") || sourceNode.getNodeName().equals("loopEndBrick") || sourceNode.getNodeName().equals("ifLogicElseBrick") || sourceNode.getNodeName().equals("ifLogicEndBrick")) {
            return;
        }
        NamedNodeMap attributes = sourceNode.getAttributes();
        for (int i = 0; i < attributes.getLength(); i++) {
            Attr attr = (Attr) attributes.item(i);
            destinationNode.setAttributeNS(attr.getNamespaceURI(), attr.getName(), attr.getValue());
        }
    }

    private void convertChildNodeToAttribute(Document originalDocument, String parentNodeName, String childNodeName) {
        NodeList elementsByTagName = originalDocument.getElementsByTagName(parentNodeName);
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            Node nodeItem = elementsByTagName.item(i);
            Element elementFindNodeByName = findNodeByName(nodeItem, childNodeName);
            if (elementFindNodeByName != null && (nodeItem instanceof Element)) {
                ((Element) nodeItem).setAttribute(childNodeName, elementFindNodeByName.getTextContent());
                nodeItem.removeChild(elementFindNodeByName);
            }
        }
    }

    private void modifyScriptLists(Document originalDocument) {
        NodeList elementsByTagName = originalDocument.getElementsByTagName("scriptList");
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            Node nodeItem = elementsByTagName.item(i);
            if (nodeItem.hasChildNodes()) {
                NodeList childNodes = nodeItem.getChildNodes();
                for (int i2 = 0; i2 < childNodes.getLength(); i2++) {
                    Node nodeItem2 = childNodes.item(i2);
                    Element elementCreateElement = originalDocument.createElement("script");
                    String str = this.scriptInfoMap.get(nodeItem2.getNodeName());
                    if (str != null) {
                        elementCreateElement.setAttribute(ModuleXmlParser.TYPE, str);
                        copyAttributesIfNeeded(nodeItem2, elementCreateElement);
                        if (nodeItem2.hasChildNodes()) {
                            NodeList childNodes2 = nodeItem2.getChildNodes();
                            for (int i3 = 0; i3 < childNodes2.getLength(); i3++) {
                                elementCreateElement.appendChild(childNodes2.item(i3));
                            }
                        }
                        nodeItem.replaceChild(elementCreateElement, nodeItem2);
                    } else {
                        Log.e(TAG, nodeItem2.getNodeName() + ": Found no scripts to convert to new structure.\"");
                    }
                }
            }
        }
    }

    private void modifyBrickLists(Document originalDocument) {
        NodeList elementsByTagName = originalDocument.getElementsByTagName("brickList");
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            Node nodeItem = elementsByTagName.item(i);
            if (nodeItem.hasChildNodes()) {
                NodeList childNodes = nodeItem.getChildNodes();
                for (int i2 = 0; i2 < childNodes.getLength(); i2++) {
                    Node nodeItem2 = childNodes.item(i2);
                    Element elementCreateElement = originalDocument.createElement("brick");
                    if (nodeItem2.getNodeName().equals("setGhostEffectBrick")) {
                        originalDocument.renameNode(nodeItem2, nodeItem2.getNamespaceURI(), "setTransparencyBrick");
                    }
                    if (nodeItem2.getNodeName().equals("changeGhostEffectByNBrick")) {
                        originalDocument.renameNode(nodeItem2, nodeItem2.getNamespaceURI(), "changeTransparencyByNBrick");
                    }
                    BrickInfo brickInfo = this.brickInfoMap.get(nodeItem2.getNodeName());
                    if (brickInfo != null) {
                        elementCreateElement.setAttribute(ModuleXmlParser.TYPE, brickInfo.brickClassName);
                        copyAttributesIfNeeded(nodeItem2, elementCreateElement);
                        if (nodeItem2.hasChildNodes()) {
                            NodeList childNodes2 = nodeItem2.getChildNodes();
                            for (int i3 = 0; i3 < childNodes2.getLength(); i3++) {
                                Element element = (Element) childNodes2.item(i3);
                                if (element.getNodeName().equals("changeGhostEffect")) {
                                    originalDocument.renameNode(element, element.getNamespaceURI(), "changeTransparency");
                                }
                                if (brickInfo.getBrickFieldForOldFieldName(element.getNodeName()) != null) {
                                    handleFormulaNode(originalDocument, brickInfo, elementCreateElement, element);
                                } else if (element.getNodeName().equals("userVariable")) {
                                    handleUserVariableNode(elementCreateElement, element);
                                } else if (!element.getNodeName().equals("loopEndBrick") && !element.getNodeName().equals("ifElseBrick") && !element.getNodeName().equals("ifEndBrick")) {
                                    elementCreateElement.appendChild(element);
                                }
                            }
                        }
                        nodeItem.replaceChild(elementCreateElement, nodeItem2);
                    } else {
                        Log.e(TAG, nodeItem2.getNodeName() + ": Found no bricks to convert to new structure.");
                    }
                }
            }
        }
    }

    private void handleFormulaNode(Document doc, BrickInfo brickInfo, Element newParentNode, Element oldNode) {
        Element elementFindNodeByName = findNodeByName(newParentNode, "formulaList");
        if (elementFindNodeByName == null) {
            elementFindNodeByName = doc.createElement("formulaList");
            newParentNode.appendChild(elementFindNodeByName);
        }
        Element elementFindNodeByName2 = findNodeByName(oldNode, "formulaTree");
        if (elementFindNodeByName2 != null) {
            doc.renameNode(elementFindNodeByName2, elementFindNodeByName2.getNamespaceURI(), "formula");
        } else {
            elementFindNodeByName2 = doc.createElement("formula");
        }
        String string = brickInfo.getBrickFieldForOldFieldName(oldNode.getNodeName()).toString();
        elementFindNodeByName2.setAttribute("category", string);
        if (string.equals("SPEAK") || string.equals("NOTE")) {
            Element elementCreateElement = doc.createElement(ModuleXmlParser.TYPE);
            elementCreateElement.setTextContent("STRING");
            elementFindNodeByName2.appendChild(elementCreateElement);
            Element elementCreateElement2 = doc.createElement("value");
            elementCreateElement2.setTextContent(oldNode.getFirstChild().getTextContent());
            elementFindNodeByName2.appendChild(elementCreateElement2);
        }
        elementFindNodeByName.appendChild(elementFindNodeByName2);
    }

    private void handleUserVariableNode(Element parentNode, Element userVariableNode) {
        Element elementFindNodeByName;
        if (!userVariableNode.hasAttribute("reference") && (elementFindNodeByName = findNodeByName(userVariableNode, "name")) != null) {
            String textContent = elementFindNodeByName.getTextContent();
            userVariableNode.removeChild(elementFindNodeByName);
            userVariableNode.setTextContent(textContent);
        }
        parentNode.appendChild(userVariableNode);
    }

    private void checkReferences(Element node) {
        if (node.hasAttribute("reference")) {
            node.setAttribute("reference", getValidReference(node, node.getAttribute("reference")));
        }
        NodeList childNodes = node.getChildNodes();
        if (childNodes != null) {
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node nodeItem = childNodes.item(i);
                if (nodeItem instanceof Element) {
                    checkReferences((Element) nodeItem);
                }
            }
        }
    }

    private String getValidReference(Node brickNode, String reference) {
        int i;
        String[] strArrSplit = reference.split("/");
        for (int i2 = 0; i2 < strArrSplit.length; i2++) {
            if (strArrSplit[i2].equals("..")) {
                brickNode = brickNode.getParentNode();
            } else {
                String strSubstring = strArrSplit[i2];
                if (strSubstring.endsWith("]")) {
                    String str = strArrSplit[i2];
                    strSubstring = str.substring(0, str.indexOf(91));
                    String str2 = strArrSplit[i2];
                    i = Integer.parseInt(str2.substring(str2.indexOf(91) + 1, strArrSplit[i2].indexOf(93))) - 1;
                } else {
                    i = 0;
                }
                NodeList childNodes = brickNode.getChildNodes();
                int i3 = 0;
                for (int i4 = 0; i4 < childNodes.getLength(); i4++) {
                    Element element = (Element) childNodes.item(i4);
                    if (element.getNodeName().equals(strSubstring)) {
                        if (i3 == i) {
                            brickNode = element;
                            break;
                        }
                        i3++;
                    } else if (element.getNodeName().equals("script") && element.getAttribute(ModuleXmlParser.TYPE).equals(this.scriptInfoMap.get(strSubstring))) {
                        if (i3 == i) {
                            strArrSplit[i2] = "script[" + (i4 + 1) + "]";
                            brickNode = element;
                            break;
                            break;
                        }
                        i3++;
                    } else {
                        if (element.getNodeName().equals("brick") && element.getAttribute(ModuleXmlParser.TYPE).equals(this.brickInfoMap.get(strSubstring).getBrickClassName())) {
                            if (i3 == i) {
                                strArrSplit[i2] = "brick[" + (i4 + 1) + "]";
                                brickNode = element;
                                break;
                                break;
                            }
                            i3++;
                        }
                    }
                }
            }
        }
        return generateReference(strArrSplit);
    }

    private String generateReference(String[] referenceParts) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < referenceParts.length; i++) {
            sb.append(referenceParts[i]);
            if (i != referenceParts.length - 1) {
                sb.append('/');
            }
        }
        return sb.toString();
    }

    /* JADX WARN: Classes with same name are omitted, all sources:
      NewCatroid_APKTemplate_2-2-0r.apk:assets/app-classes.jar:org/catrobat/catroid/io/BackwardCompatibleCatrobatLanguageXStream$BrickInfo.class
      classes14.dex
     */
    private class BrickInfo {
        private String brickClassName;
        private HashMap<String, Brick.BrickField> brickFieldMap;

        BrickInfo(String brickClassName) {
            this.brickClassName = brickClassName;
        }

        void addBrickFieldToMap(String oldFiledName, Brick.BrickField brickField) {
            if (this.brickFieldMap == null) {
                this.brickFieldMap = new HashMap<>();
            }
            this.brickFieldMap.put(oldFiledName, brickField);
        }

        Brick.BrickField getBrickFieldForOldFieldName(String oldFiledName) {
            HashMap<String, Brick.BrickField> map = this.brickFieldMap;
            if (map != null) {
                return map.get(oldFiledName);
            }
            return null;
        }

        String getBrickClassName() {
            return this.brickClassName;
        }
    }
}
