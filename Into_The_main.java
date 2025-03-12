package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/*
 * Demonstrates an empty iterative OpMode
 */
@TeleOp(name = "Into The Main", group = "Concept")
public class Into_The_main extends OpMode {

  private ElapsedTime runtime = new ElapsedTime();
  // Move code
  private DcMotor leftFrontDrive = null;
  private DcMotor leftBackDrive = null;
  private DcMotor rightFrontDrive = null;
  private DcMotor rightBackDrive = null;
  // Hands

  private DcMotor UpLeft = null;
  private DcMotor UpRight = null;
  // Arm code
  private Servo MainArm;
  private Servo FrontHand;
  private Servo Claw;
  private Servo Elbow;
  private Servo Spini;
  private Servo Arm2Body;
  private Servo Arm2Claw;
  private Servo Arm2Spini;
  private int armStage = 1;
  private boolean buttondown = false;
  private boolean buttondownA = false;
  private double bonusArm = 0.3;

  @Override
  public void init() {
    // Hands
    UpLeft = hardwareMap.get(DcMotor.class, "upleft");
    UpRight = hardwareMap.get(DcMotor.class, "upright");
    UpLeft.setDirection(DcMotor.Direction.FORWARD);
    UpRight.setDirection(DcMotor.Direction.FORWARD);
    UpLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    UpRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    // Body
    leftFrontDrive = hardwareMap.get(DcMotor.class, "leftfront");
    leftBackDrive = hardwareMap.get(DcMotor.class, "leftback");
    rightFrontDrive = hardwareMap.get(DcMotor.class, "rightfront");
    rightBackDrive = hardwareMap.get(DcMotor.class, "rightback");
    leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
    leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
    rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
    rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
    leftFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    leftBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    rightBackDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    rightFrontDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    // Arm code
    FrontHand = hardwareMap.get(Servo.class, "fronthand");
    MainArm = hardwareMap.get(Servo.class, "allarm");
    Claw = hardwareMap.get(Servo.class, "claw");
    Elbow = hardwareMap.get(Servo.class, "elbow");
    Spini = hardwareMap.get(Servo.class, "spini");
    Arm2Body = hardwareMap.get(Servo.class, "body2arm");
    Arm2Claw = hardwareMap.get(Servo.class, "twoclaw");
    Arm2Spini = hardwareMap.get(Servo.class, "twospini");
    Arm2Body.setDirection(Servo.Direction.REVERSE);
    MainArm.setDirection(Servo.Direction.REVERSE);
    telemetry.addData("Status", "Initialized");
  }

//  @Override
//  public void init_loop() {
//  }

  @Override
  public void start() {
    runtime.reset();
  }

  @Override
  public void loop() {
    looph();
    loopv();
    loopa();
    loopm();

    telemetry.addData("Status", "Run Time: " + runtime.toString());
    telemetry.addData("center", rightFrontDrive.getCurrentPosition());
    telemetry.update();
  }

  public void loopm() {
    double LeftFrontPower; // Power fo
    // r front left motor
    double LeftBackPower; // Power for back left motor
    double RightFrontPower; // Power for front right motor
    double RightBackPower; // Power for back right motor
    int Left = 0; // Left turning
    int Right = 0; // Right turning
    double ForwardBackPower = 0; // Forward/Backward
    double StrafePower = 0; // Strafe

    // Turning left
    if (gamepad1.left_bumper == true) {
      Left = 1;
    } else {
      Left = 0;
    }
    // Turning right
    if (gamepad1.right_bumper == true) {
      Right = 1;
    } else {
      Right = 0;
    }
    // Strafe/ForwardBack
    if (gamepad1.a == true) {
      StrafePower = (gamepad1.left_trigger - gamepad1.right_trigger) / 2;
    } else {
      ForwardBackPower = (gamepad1.right_trigger - gamepad1.left_trigger) / 2;
    }
    // Power for each motor
    LeftFrontPower = ForwardBackPower - Left - StrafePower + Right;
    LeftBackPower = ForwardBackPower - Left + StrafePower + Right;
    RightFrontPower = ForwardBackPower - Right + StrafePower + Left;
    RightBackPower = ForwardBackPower - Right - StrafePower + Left;
    // Gives the power for the motors
    leftFrontDrive.setPower(LeftFrontPower);
    rightFrontDrive.setPower(RightFrontPower);
    leftBackDrive.setPower(LeftBackPower);
    rightBackDrive.setPower(RightBackPower);
  }

  public void looph() {
    double speed = 0.6;
    if (gamepad2.dpad_right) {
      FrontHand.setPosition(0);
      // Add logic for hand control if needed
    } else if (gamepad2.dpad_left) {
      FrontHand.setPosition(1);
      // Add logic for hand control if needed
    } else {
      // Hand motors control logic goes here
    }
    telemetry.update();
  }

  public void loopv() {
    double speed = 0.9;
    if (gamepad2.dpad_up) {
      UpLeft.setPower(speed + 0.1);
      UpRight.setPower(speed);
    } else if (gamepad2.dpad_down) {
      UpLeft.setPower(-speed);
      UpRight.setPower(-speed);
    } else {
      UpLeft.setPower(0);
      UpRight.setPower(0);
    }
  }

  public void loopa() {
    if(!buttondownA&&gamepad2.a)
    {
      if(gamepad2.a &&armStage == 1&&Spini.getPosition()==0.5)
      {
        buttondownA = true;
        Spini.setPosition(1);
      }
      else if(gamepad2.a &&armStage == 1&&Spini.getPosition()==1)
      {
        buttondownA = true;
        Spini.setPosition(0.5);
      }

    }
    if(!gamepad2.a)
    {
      buttondownA = false;
    }

    if(gamepad2.left_bumper&&!buttondown)
    {
     // leftFrontDrive.setPower(0);
      //rightFrontDrive.setPower(0);
     // leftBackDrive.setPower(0);
      // rightBackDrive.setPower(0);
      armStage++;
      buttondown = true;
      if(armStage >= 6)
      {
        armStage = 1;
      }
    }
    else if(gamepad2.right_bumper&&!buttondown)
    {
    //  leftFrontDrive.setPower(0);
     // rightFrontDrive.setPower(0);
     // leftBackDrive.setPower(0);
     // rightBackDrive.setPower(0);
      armStage--;
      buttondown = true;
      if(armStage <= 0)
      {
        armStage = 1;
      }
    }

    if(armStage == 1&&buttondown)//Grab Position front
    {
    //  leftFrontDrive.setPower(0);
     // rightFrontDrive.setPower(0);
     // leftBackDrive.setPower(0);
     // rightBackDrive.setPower(0);
      Spini.setPosition(1);
      Arm2Spini.setPosition(1);
      MainArm.setPosition(1);
      waitFor(8);
      Elbow.setPosition(1);
      Claw.setPosition(0);



    }
    else if(armStage == 2&&buttondown)//grab and move middle
    {
     // leftFrontDrive.setPower(0);
     // rightFrontDrive.setPower(0);
     // leftBackDrive.setPower(0);
     // rightBackDrive.setPower(0);
      Arm2Body.setPosition(1);
      Claw.setPosition(1);
      waitFor(6);
      Spini.setPosition(0.5);
      waitFor(10);
      Arm2Claw.setPosition(0.5);
      MainArm.setPosition(0);
      waitFor(10);
      Elbow.setPosition(0);

      buttondown = true;

    }

    else if(armStage == 3&&buttondown)//take from middle and go up
    {
   //   leftFrontDrive.setPower(0);
     // rightFrontDrive.setPower(0);
     // leftBackDrive.setPower(0);
     // rightBackDrive.setPower(0);
      Arm2Body.setPosition(1);
      Elbow.setPosition(0.5);
      waitFor(4);
      Elbow.setPosition(0);
      waitFor(8);
      Arm2Claw.setPosition(1);
      waitFor(8);
      Claw.setPosition(0.5);
      waitFor(8);
      MainArm.setPosition(0.5);
      waitFor(6);
      Arm2Body.setPosition(0.5);





    }
    else if(armStage == 4&&buttondown)
    {
    //  leftFrontDrive.setPower(0);
     // rightFrontDrive.setPower(0);
     // leftBackDrive.setPower(0);
     // rightBackDrive.setPower(0);
      Arm2Body.setPosition(0);
      waitFor(8);



    }
    else if(armStage == 5&&buttondown)
    {

   //   leftFrontDrive.setPower(0);
     // rightFrontDrive.setPower(0);
     // leftBackDrive.setPower(0);
     // rightBackDrive.setPower(0);
      Arm2Claw.setPosition(0.5);
      waitFor(4);

    }
    if(!gamepad2.left_bumper&&!gamepad2.right_bumper)
    {
      buttondown = false;
    }
  }



  public void waitFor(int time) {
    for (int i = 0; i < 5000; i++) {
      for (int j = 0; j < time * 2500; j++) {
      }
    }
  }
}
