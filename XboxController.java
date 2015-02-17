package org.usfirst.frc.team4624.robot.input;   // Change to whatever package you would like



/* Imports */
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;



/**
 * [class] XboxController
 * @author AJ Granowski & 4624 Owatonna Robotics
 * @version 2015
 * 
 * This class wraps around the Joystick class in order to make
 *     working with Xbox360 controllers less of a pain.
 * 
 * The values from this class can be used in two ways. One could
 *     either check each Button every cycle with .get(), or they
 *     could call commands directly from the Buttons with .whenPressed()
 * 
 * USAGE:
 *     // Initialization
 *     myXboxController = new XboxController( <port the controller is on (starts at 0)> );
 *     myXboxController.leftStick.setThumbstickDeadZone( .2 );  // Optional. See code below for defaults.
 *     
 *     // Using buttons
 *     myXboxController.a.whenPressed( new MyCommand() );
 *     myXboxController.lb.toggleWhenPressed( new MyCommand() );
 *     myXboxController.rightStick.whenPressed( new MyCommand() );
 *     
 *     // Getting values directly
 *     if( myXboxController.leftStick.getY() > .4 ) ...
 *     
 *     // Support of legacy methods (NOTE: These values are straight from the Joystick class. No deadzone stuff or anything)
 *     if( xboxController.getX() > .4 ) ...
 * 
 * NOTES:
 *     Although I have confidence that this will work, not everything has been tested.
 *     This should work for the 2015 WPILib. The mappings of axis's and buttons may change in later years.
 *     I am not a good Java programmer.
 */
public class XboxController extends Joystick {
    
    /* Default Values */
    private static final double DEFAULT_THUMBSTICK_DEADZONE = 0.1;  // Jiggle room for the thumbsticks
    private static final double DEFAULT_TRIGGER_DEADZONE    = 0.01; // Jiggle room for the triggers
    private static final double DEFAULT_TRIGGER_SENSITIVITY = 0.6;  // If the trigger is beyond this limit, say it has been pressed
    
    /* Button Mappings */
    private static final int    A_BUTTON_ID                 = 1;
    private static final int    B_BUTTON_ID                 = 2;
    private static final int    X_BUTTON_ID                 = 3;
    private static final int    Y_BUTTON_ID                 = 4;
    private static final int    LB_BUTTON_ID                = 5;
    private static final int    RB_BUTTON_ID                = 6;
    private static final int    BACK_BUTTON_ID              = 7;
    private static final int    START_BUTTON_ID             = 8;
    private static final int    LEFT_THUMBSTIKC_BUTTON_ID   = 9;
    private static final int    RIGHT_THUMBSTICK_BUTTON_ID  = 10;
    
    /* Axis Mappings */
    private static final int    LEFT_THUMBSTICK_X_AXIS_ID   = 0;
    private static final int    LEFT_THUMBSTICK_Y_AXIS_ID   = 1;
    private static final int    LEFT_TRIGGER_AXIS_ID        = 2;
    private static final int    RIGHT_TRIGGER_AXIS_ID       = 3;
    private static final int    RIGHT_THUMBSTICK_X_AXIS_ID  = 4;
    private static final int    RIGHT_THUMBSTICK_Y_AXIS_ID  = 5;
    
    
    
    /* Instance Values */
    private final   int             port;
    private final   Joystick        controller;
    
    public final    Thumbstick      leftStick;
    public final    Thumbstick      rightStick;
    public final    Trigger         lt;
    public final    Trigger         rt;
    public final    DirectionalPad  dPad;
    public final    Button          a;
    public final    Button          b;
    public final    Button          x;
    public final    Button          y;
    public final    Button          lb;
    public final    Button          rb;
    public final    Button          back;
    public final    Button          start;
    
    
    
    /**
     * (Constructor #1)
     * There are two ways to make an XboxController. With this constructor,
     * you can specify which port you expect the controller to be on.
     * @param port
     */
    public XboxController(final int port) {
        super(port);  // Extends Joystick...
        
        /* Initialize */
        this.port       = port;
        this.controller = new Joystick(this.port);    // Joystick referenced by everything
        
        this.leftStick  = new Thumbstick    (this.controller, HAND.LEFT);
        this.rightStick = new Thumbstick    (this.controller, HAND.RIGHT);
        this.dPad       = new DirectionalPad(this.controller);
        this.lt         = new Trigger       (this.controller, HAND.LEFT);
        this.rt         = new Trigger       (this.controller, HAND.RIGHT);
        this.a          = new JoystickButton(this.controller, A_BUTTON_ID);
        this.b          = new JoystickButton(this.controller, B_BUTTON_ID);
        this.x          = new JoystickButton(this.controller, X_BUTTON_ID);
        this.y          = new JoystickButton(this.controller, Y_BUTTON_ID);
        this.lb         = new JoystickButton(this.controller, LB_BUTTON_ID);
        this.rb         = new JoystickButton(this.controller, RB_BUTTON_ID);
        this.back       = new JoystickButton(this.controller, BACK_BUTTON_ID);
        this.start      = new JoystickButton(this.controller, START_BUTTON_ID);
    }
    
    
    
    /**
     * (Constructor #2) This is the other constructor. I would recommend using this one instead
     * as it is unlikely that anything else but the XboxController will be
     * connected.
     */
    public XboxController() {
        this(0);
    }
    
    
    
    /**
     * Rather than use an integer (which might not be what we expect)
     * we use an enum which has a set amount of possibilities.
     */
    public static enum HAND {
        LEFT, RIGHT
    }
    
    
    
    /**
     * This is the relation of direction and number for .getPOV() used
     * in the DirectionalPad class.
     */
    public static enum DPAD {
        UP          (0),
        UP_RIGHT    (45),
        RIGHT       (90),
        DOWN_RIGHT  (135),
        DOWN        (180),
        DOWN_LEFT   (225),
        LEFT        (270),
        UP_LEFT     (315);
        
        
        
        /* Instance Value */
        private int value;
        
        
        
        /**
         * Constructor
         * @param value
         */
        DPAD(final int value) {
            this.value = value;
        }
        
        /**
         * Convert integers to DPAD values
         * @param value
         * @return DPAD with matching angle
         */
        public static DPAD getEnum(int angle) {
            angle = Math.abs(angle);
            angle %= 360;
            angle = Math.round(angle / 45) * 45;    // May have rounding errors. Due to rounding errors.
            
            DPAD[] all = DPAD.values();
            
            for(int i = 0; i < all.length; i++) {
                if (all[i].value == angle) {
                    return all[i] ;
                }
            }
            // I don't know what to do here
            //throw new UnsupportedOperationException("Integer supplied (" + angle + ") is not a possible value of this enum.");
            System.out.println("[XboxController.DPAD.getEnum()] Angle supplied (" + angle + ") has no related DPad direction");
            return DPAD.UP;
        }
    }
    
    
    
    /**
     * This class is used to represent the thumbsticks on the
     * Xbox360 controller.
     */
    public static class Thumbstick extends Button {
        
        /* Instance Values */
        private final   Joystick    parent;
        private final   HAND        hand;
        private final   int         xAxisID;
        private final   int         yAxisID;
        private final   int         pressedID;
        private         double      xDeadZone;
        private         double      yDeadZone;
        
        
        
        /**
         * Constructor
         * @param parent
         * @param hand
         */
        Thumbstick(final Joystick parent, final HAND hand) {
            
            /* Initialize */
            this.parent     = parent;
            this.hand       = hand;
            this.xDeadZone  = DEFAULT_THUMBSTICK_DEADZONE;
            this.yDeadZone  = DEFAULT_THUMBSTICK_DEADZONE;
            
            if (hand == HAND.LEFT) {
                this.xAxisID    = LEFT_THUMBSTICK_X_AXIS_ID;
                this.yAxisID    = LEFT_THUMBSTICK_Y_AXIS_ID;
                this.pressedID  = LEFT_THUMBSTIKC_BUTTON_ID;
            } else {                                            // If right hand
                this.xAxisID    = RIGHT_THUMBSTICK_X_AXIS_ID;
                this.yAxisID    = RIGHT_THUMBSTICK_Y_AXIS_ID;
                this.pressedID  = RIGHT_THUMBSTICK_BUTTON_ID;
            }
        }
        
        
        
        /**
         * + = right
         * - = left
         * @return X but with a deadzone
         */
        private double rawX() {
            final double rawInput = parent.getRawAxis(xAxisID);
            
            return createDeadZone(rawInput, xDeadZone);
        }
        
        /**
         * + = up
         * - = down
         * @return Y but with a deadzone
         */
        private double rawY() {
            final double rawInput = -parent.getRawAxis(yAxisID);    // -Y was up on our thumbsticks. Consider this a fix?
            
            return createDeadZone(rawInput, yDeadZone);
        }
        
        /**
         * magnitude
         * @param x
         * @param y
         * @return Magnitude of thing
         */
        private double magnitude(double x, double y) {
            final double xSquared   = Math.pow(x, 2);
            final double ySquared   = Math.pow(y, 2);
            
            return Math.sqrt(xSquared + ySquared);
        }
        
        /**
         * angleToSquareSpace
         * @param angle
         * @return Number between 0 and PI/4
         */
        private double angleToSquareSpace(double angle) {
            final double absAngle   = Math.abs(angle);
            final double halfPi     = Math.PI / 2;
            final double quarterPi  = Math.PI / 4;
            final double modulus    = absAngle % halfPi;
            
            return -Math.abs(modulus - quarterPi) + quarterPi;
        }
        
        /**
         * scaleMagnitude
         * @param x
         * @param y
         * @return 
         */
        private double scaleMagnitude(double x, double y) {
            final double magnitude      = magnitude(x, y);
            final double angle          = Math.atan2(x, y);
            final double newAngle       = angleToSquareSpace(angle);
            final double scaleFactor    = Math.cos(newAngle);
            
            return magnitude * scaleFactor;
        }
        
        
        
        /* Extended Methods */
        @Override
        public boolean get() {
            return parent.getRawButton(pressedID);
        }
        
        
        
        /* Get Methods */
        /**
         * Used to see which side of the controller this thumbstick is
         * @return Thumbstick hand
         */
        public HAND getHand() {
            return hand;
        }
        
        /**
         * getRawX
         * @return X with a deadzone
         */
        public double getX() {
            return rawX();
        }
        
        /**
         * getRawY
         * @return Y with a deadzone
         */
        public double getY() {
            return rawY();
        }
        
        /**
         * 0    = Up;
         * 90   = Right;
         * ...
         * @return Angle the thumbstick is pointing
         */
        public double getAngle() {
            final double angle = Math.atan2(rawX(), rawY());
            
            return Math.toDegrees(angle);
        }
        
        /**
         * getMagnitude
         * @return A number between 0 and 1
         */
        public double getMagnitude() {
            double magnitude = scaleMagnitude(rawX(), rawY());
            
            if (magnitude > 1) {
                magnitude = 1;  // Prevent any errors that might arise
            }
            
            return magnitude;
        }
        
        /**
         * Get the adjusted thumbstick position (Magnitude <= 1)
         * @return True thumbstick position
         */
        public double getTrueX() {
            final double x      = rawX();
            final double y      = rawY();
            final double angle  = Math.atan2(x, y);
            
            return scaleMagnitude(x, y) * Math.sin(angle);
        }
        
        /**
         * Get the adjusted thumbstick position (Magnitude <= 1)
         * @return True thumbstick position
         */
        public double getTrueY() {
            final double x      = rawX();
            final double y      = rawY();
            final double angle  = Math.atan2(x, y);
            
            return scaleMagnitude(x, y) * Math.cos(angle);
        }
        
        
        
        /* Set Methods */
        /**
         * Set the X axis deadzone of this thumbstick
         * @param number
         */
        public void setXDeadZone(double number) {
            xDeadZone = number;
        }
        
        /**
         * Set the Y axis deadzone of this thumbstick
         * @param number
         */
        public void setYDeadZone(double number) {
            yDeadZone = number;
        }

        /**
         * Set both axis deadzones of this thumbstick
         * @param number
         */
        public void setDeadZone(double number) {
            xDeadZone = number;
            yDeadZone = number;
        }
    }
    
    
    
    /**
     * This class is used to represent one of the two
     * Triggers on an Xbox360 controller.
     */
    public static class Trigger extends Button {
        
        /* Instance Values */
        private final   Joystick    parent;
        private final   HAND        hand;
        
        private         double      deadZone;
        private         double      sensitivity;
        
        
        
        /**
         * Constructor
         * @param joystick
         * @param hand
         */
        Trigger(final Joystick joystick, final HAND hand) {
            
            /* Initialize */
            this.parent         = joystick;
            this.hand           = hand;
            this.deadZone       = DEFAULT_TRIGGER_DEADZONE;
            this.sensitivity    = DEFAULT_TRIGGER_SENSITIVITY;
        }
        
        
        
        /* Extended Methods */
        @Override
        public boolean get() {
            return getX() > sensitivity;
        }
        
        
        
        /* Get Methods */
        /**
         * getHand
         * @return Trigger hand
         * 
         * See which side of the controller this trigger is
         */
        public HAND getHand() {
            return hand;
        }
        
        /**
         * 0 = Not pressed
         * 1 = Completely pressed
         * @return How far its pressed
         */
        public double getX() {
            final double rawInput;
            
            if (hand == HAND.LEFT) {
                rawInput = parent.getRawAxis(LEFT_TRIGGER_AXIS_ID);
            } else {
                rawInput = parent.getRawAxis(RIGHT_TRIGGER_AXIS_ID);
            }
            
            return createDeadZone(rawInput, deadZone);
        }
        
        public double getY() {
            return getX();	// Triggers have one dimensional movement. Use getX() instead
        }
        
        
        
        /* Set Methods */
        /**
         * Set the deadzone of this trigger
         * @param number
         */
        public void setTriggerDeadZone(double number) {
            this.deadZone = number;
        }
        
        /**
         * How far you need to press this trigger to activate a button press
         * @param number
         */
        public void setTriggerSensitivity(double number) {
            this.sensitivity = number;
        }
    }
    
    
    
    /**
     * This is a weird object which is essentially just 8 buttons.
     */
    public static class DirectionalPad extends Button {
        
        /* Instance Values */
        private final   Joystick    parent;
        
        public final    Button      up;
        public final    Button      upRight;
        public final    Button      right;
        public final    Button      downRight;
        public final    Button      down;
        public final    Button      downLeft;
        public final    Button      left;
        public final    Button      upLeft;
        
        
        
        /**
         * Constructor
         * @param parent
         */
        DirectionalPad(final Joystick parent) {
            
            /* Initialize */
            this.parent	    = parent;
            this.up         = new DPadButton(this, DPAD.UP);
            this.upRight    = new DPadButton(this, DPAD.UP_RIGHT);
            this.right      = new DPadButton(this, DPAD.RIGHT);
            this.downRight  = new DPadButton(this, DPAD.DOWN_RIGHT);
            this.down       = new DPadButton(this, DPAD.DOWN);
            this.downLeft   = new DPadButton(this, DPAD.DOWN_LEFT);
            this.left       = new DPadButton(this, DPAD.LEFT);
            this.upLeft     = new DPadButton(this, DPAD.UP_LEFT);
        }
        
        
        
        /**
         * This class is used to represent each of the 8 values a
         * dPad has as a button.
         */
        public static class DPadButton extends Button {
            
            /* Instance Values */
            private final DPAD              direction;
            private final DirectionalPad    parent;
            
            
            
            /**
             * Constructor
             * @param parent
             * @param dPad
             */
            DPadButton(final DirectionalPad parent, final DPAD dPadDirection) {
                
                /* Initialize */
                this.direction  = dPadDirection;
                this.parent     = parent;
            }
            
            
            
            /* Extended Methods */
            @Override
            public boolean get() {
                return parent.getAngle() == direction.value;
            }
        }
        
        
        
        private int angle() {
            return parent.getPOV();
        }
        
        
        /* Extended Methods */
        @Override
        public boolean get() {
            return angle() != -1;
        }
        
        
        
        /* Get Methods */
        /**
         * UP          0;
         * UP_RIGHT    45;
         * RIGHT       90;
         * DOWN_RIGHT  135;
         * DOWN        180;
         * DOWN_LEFT   225;
         * LEFT        270;
         * UP_LEFT     315;
         * @return A number between 0 and 315 indicating direction
         */
        public int getAngle() {
            return angle();
        }
        
        /**
         * Just like getAngle, but returns a direction instead of an angle
         * @return A DPAD direction
         */
        public DPAD getDirection() {
            return DPAD.getEnum(angle());
        }
    }
    
    
    
    /**
     * Creates a deadzone, but without clipping the lower values.
     * turns this
     * |--1--2--3--4--5--|
     * into this
     * ______|-1-2-3-4-5-|
     * @param input
     * @param deadZoneSize
     * @return adjusted_input
     */
    private static double createDeadZone(double input, double deadZoneSize) {
        final   double  negative;
                double  deadZoneSizeClamp = deadZoneSize;
                double  adjusted;
        
        if (deadZoneSizeClamp < 0 || deadZoneSizeClamp >= 1) {
            deadZoneSizeClamp = 0;  // Prevent any weird errors
        }
        
        negative    = input < 0 ? -1 : 1;
        
        adjusted    = Math.abs(input) - deadZoneSizeClamp;  // Subtract the deadzone from the magnitude
        adjusted    = adjusted < 0 ? 0 : adjusted;          // if the new input is negative, make it zero
        adjusted    = adjusted / (1 - deadZoneSizeClamp);   // Adjust the adjustment so it can max at 1
        
        return negative * adjusted;
    }
    
    
    
    /* Get Methods */
    /**
     * @return The port of this XboxController
     */
    public int getPort() {
        return port;
    }
    
    /**
     * @return The Joystick of this XboxController
     */
    public Joystick getJoystick() {
        return controller;
    }
    
    
    
    /* Set Methods */
    /**
     * Make the controller vibrate
     * @param hand The side of the controller to rumble
     * @param intensity How strong the rumble is
     */
    public void setRumble(HAND hand, double intensity) {
        final float amount = new Float(intensity);
        
        if (hand == HAND.LEFT) {
            controller.setRumble(RumbleType.kLeftRumble, amount);
        } else {
            controller.setRumble(RumbleType.kRightRumble, amount);
        }
    }
    
    /**
     * Make the controller vibrate
     * @param intensity How strong the rumble is
     */
    public void setRumble(double intensity) {
        final float amount = new Float(intensity);
        
        controller.setRumble(RumbleType.kLeftRumble, amount);
        controller.setRumble(RumbleType.kRightRumble, amount);
    }

    /*
     * Set both axis deadzones of both thumbsticks
     * @param number
     */
    public void setDeadZone(double number) {
        leftStick.setDeadZone(number);
        rightStick.setDeadZone(number);
    }
}
