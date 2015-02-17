# XboxController #
This class wraps around the Joystick class in order to make working with Xbox360 controllers less of a pain. The values from this class can be used in two ways.
You could either check each Button every cycle with .get(), or you could call commands directly from the Buttons with .whenPressed()

### USAGE: ###
Initialization
````
myXboxController = new XboxController( <port the controller is on (starts at 0)> );
myXboxController.leftStick.setThumbstickDeadZone( .2 ); // Optional. See code below for defaults.
````

Using buttons
````
myXboxController.a.whenPressed( new MyCommand() );
myXboxController.lb.toggleWhenPressed( new MyCommand() );
myXboxController.rightStick.whenPressed( new MyCommand() );
````

Getting values directly
````
if( myXboxController.leftStick.getY() > .4 ) ...
````
Support of legacy methods (NOTE: These values are straight from the Joystick class. No deadzone stuff or anything)
````
if( xboxController.getX() > .4 ) ...
````

### NOTES: ###
Although we have confidence that this will work, not everything has been tested.
This should work for the 2015 WPILib. The mappings of axis's and buttons may change in later years.
