/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode.autonomous;

// RR-specific imports

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvPipeline;
import org.openftc.easyopencv.OpenCvWebcam;

@Config
@Autonomous(name = "CameraAzulDentro", group = "Autonomous")
public class CameraAzulDentro extends LinearOpMode {

    OpenCvWebcam webcam = null;
    String pos;

    public static final double DELAY = 0.5;



    @Override
    public void runOpMode() {

        WebcamName webcamName = hardwareMap.get(WebcamName.class, "Webcam 1");
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(webcamName, cameraMonitorViewId);

        webcam.setPipeline(new Pipeline());

        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                webcam.startStreaming(1280, 720, OpenCvCameraRotation.UPSIDE_DOWN);
            }

            @Override
            public void onError(int errorCode) {

            }
        });


        MecanumDrive drive = new MecanumDrive(hardwareMap, new Pose2d(5, -60, Math.toRadians(270)));

        Action trajectoryActionMeioBlue = drive.actionBuilder(drive.pose)
                //Meio
                //////////////////////////////////////////////
                //Indo para pontuar na marcação
                .strafeTo(new Vector2d(13, -30))
                .waitSeconds(DELAY)
                //Pontuando
                //Indo para baixo da treliça
                .strafeTo(new Vector2d(5, -32))
                .waitSeconds(DELAY)
                //Indo para o meio
                .strafeTo(new Vector2d(5, 0))
                .waitSeconds(DELAY)
                //Indo para o backdrop
                .strafeTo(new Vector2d(-70, 0))
                .waitSeconds(DELAY)
                .splineTo(new Vector2d(-100, -46), Math.toRadians(180))
                .waitSeconds(DELAY)
                //Pontuando
                //Estacionando
                .strafeTo(new Vector2d(-100, -15))
                .waitSeconds(DELAY)
                .strafeTo(new Vector2d(-105, -15))
                .build();

        Action trajectoryActionEsquerdaBlue = drive.actionBuilder(drive.pose)
                //Esquerda Azul
                //////////////////////////////////////////////////
                //Ir e virar para a marcação:
                .strafeTo(new Vector2d(13, -60))
                .waitSeconds(DELAY)
                .lineToYSplineHeading(30, Math.toRadians(180))
                .waitSeconds(DELAY)
                .strafeTo(new Vector2d(11.5, -30))
                .waitSeconds(DELAY)
                // Ir para o meio
                .strafeTo(new Vector2d(11.5, 0))
                .waitSeconds(DELAY)
                // Ir para o backdrop
                .strafeTo(new Vector2d(-70, 0))
                .waitSeconds(DELAY)
                .splineTo(new Vector2d(-100, -53), Math.toRadians(180))
                .waitSeconds(DELAY)
                //Pontuar
                //Estacionando
                .strafeTo(new Vector2d(-100, -15))
                .waitSeconds(DELAY)
                .strafeTo(new Vector2d(-105, -15))
                .build();

        Action trajectoryActionDireitaBlue = drive.actionBuilder(drive.pose)
                //Direita Azul
                //////////////////////////////////////////////////
                //Ir e virar para a marcação
                .strafeTo(new Vector2d(13, -60))
                .waitSeconds(DELAY)
                .lineToYSplineHeading(30, Math.toRadians(1))
                .waitSeconds(DELAY)
                .strafeTo(new Vector2d(11.5, -30))
                .waitSeconds(DELAY)
                //Pontuar
                //Indo para baixo da treliça
                .strafeTo(new Vector2d(5, -32))
                .waitSeconds(DELAY)
                //Indo para o meio
                .strafeTo(new Vector2d(5, 0))
                .waitSeconds(DELAY)
                //Indo para o backdrop
                .strafeTo(new Vector2d(-70, 0))
                .waitSeconds(DELAY)
                .splineTo(new Vector2d(-100, -42), Math.toRadians(180))
                .waitSeconds(DELAY)
                //Pontuando
                //Estacionando
                .strafeTo(new Vector2d(-100, -15))
                .waitSeconds(DELAY)
                .strafeTo(new Vector2d(-105, -15))
                .build();

        while (!isStopRequested() && !opModeIsActive()) {

        }

        waitForStart();

        if (isStopRequested()) return;
        Action trajectoryActionChosen;
        if (pos == "Meio") {
            trajectoryActionChosen = trajectoryActionMeioBlue;
        } else if (pos == "Esquerda") {
            trajectoryActionChosen = trajectoryActionEsquerdaBlue;}
        else {
            trajectoryActionChosen = trajectoryActionDireitaBlue;
        }

        Actions.runBlocking(trajectoryActionChosen);
    }
    class Pipeline extends OpenCvPipeline {
        Mat YCbCr = new Mat();
        Mat leftCrop;
        Mat rightCrop;
        Mat midCrop;
        double leftavgfin;
        double rightavgfin;
        double midavgfin;
        Mat output = new Mat();
        Scalar rectColor = new Scalar(255.0, 255.0, 255.0);
        public Mat processFrame(Mat input){
            Imgproc.cvtColor(input, YCbCr, Imgproc.COLOR_RGB2YCrCb);
            telemetry.addLine("Pipeline rodando");

            Rect leftRect = new Rect(70, 400, 250, 250);
            Rect rightRect = new Rect(1000, 400, 250, 250);
            Rect midRect = new Rect(570, 350, 150, 150);

            input.copyTo(output);
            Imgproc.rectangle(output, leftRect, rectColor, 2);
            Imgproc.rectangle(output, rightRect, rectColor, 2);
            Imgproc.rectangle(output, midRect, rectColor, 2);

            leftCrop = YCbCr.submat(leftRect);
            rightCrop = YCbCr.submat(rightRect);
            midCrop = YCbCr.submat(midRect);

            Core.extractChannel(leftCrop, leftCrop, 1);
            Core.extractChannel(rightCrop, rightCrop, 1);
            Core.extractChannel(midCrop, midCrop, 1);

            Scalar leftavg = Core.mean(leftCrop);
            Scalar rightavg = Core.mean(rightCrop);
            Scalar midavg = Core.mean(midCrop);

            leftavgfin = leftavg.val[0];
            rightavgfin = rightavg.val[0];
            midavgfin = midavg.val[0];

            telemetry.addData("Direita", leftavgfin);
            telemetry.addData("Meio", midavgfin);
            telemetry.addData("Esquerda", rightavgfin);
            telemetry.update();

            if (leftavgfin < rightavgfin && leftavgfin < midavgfin){
                telemetry.addLine("Direita");
                pos = "Direita";

            }else if (rightavgfin < midavgfin){
                telemetry.addLine("Esquerda");
                pos = "Esquerda";
            }else{
                telemetry.addLine("Meio");
                pos = "Meio";
            }

            return (output);
        }
    }
}