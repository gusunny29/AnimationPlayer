README file HW6:

Changes to the Last HW:
-removed methods from the model that paused the game, resumed the game, and restarted 
the game (put these in the controller/view instead)
- created multiple view interfaces that were specific to each type of view 
instead of creating one view and throwing unsupportedOperationExceptions


Design of this HW: 
- EditView class contains the existing visual view as a component within it and 
adds the abilities to edit the animation(start, pause, resume, looping, speed, etc). 

-Buttons appear at the top of the GUI that show user its options

-Message pops up at the beginning of the game that tells user it can edit animation
by clicking on any shape. 

-Model can use either keyFrames or motions to update the animation.

-View provides a button that allows the user to add a shape without having to click on an existing
shape. They would provide the shape itself with no motions and then can add keyframes by clicking 
on it going forwards. 

-Added new functionalities to the controller that allowed it to obtain information
from the view/model and pass it to the other as needed to promote loose coupling. 

