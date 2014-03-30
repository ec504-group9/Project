The GUI of the project: 

3/20/14:
So heres some things I found when trying to interact with the encoder:

1. Use the WindowBuilder if you are using eclipse for a GUI builder - just open file with WindowBuilder. You should be able to add the Panel.java class to the WindowBuilder structure, by just right clicking and adding component. 

2. We need to change the Panel.java into a Jpanel instead of Jframe so we can put buttons/other components to interact
with the encoder. You cannot put a jframe on another jframe i.e. put the encoder Jframe onto the GUI jframe. This requires some tweaking in Panel.java. 

Note: I did not commit major changes as I am so far only testing things.

