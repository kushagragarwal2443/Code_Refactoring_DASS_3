<b>MODIFICATION</b>

1)<b>AddPartyView</b> : <br>
a) Removed unused Local Variable<br>
b) Removed Commendted Out Code for Better Readability<br>
c) GetNames is similar to GetParty function and getNames is redundant so removed this function.

2)<b>Bowler</b> : <br>
a) Removed getNick function it was similar to getNickName and fixed the corresponding places with appropriate function<br>
b) Renamed equal function to prevent confusion <br>
c) Renamed variable to make in complaince with Regex <br>

3)<b>ScoreReport</b> : <br>
a)  Removed Commented Out Code for Better Readability<br>

4)<b>Lane</b> : <br>
a)  Removed string comparison using "==" because it causes errors sometimes and removed unnecessary type casting<br>
b) Removed Commented Out Code for Better Readability<br>
c) Added @Override annotation to the function<br>
d) Added Braces to avoid Dangling else<br>
e) Reduced complexity of receivePinsetterEvent by merging condition having same objective<br>

5)<b>BowlerFile</b> : <br>
a) Implemented BufferRead with try catch block<br>
b) Implemented RandomAccessFile with try catch block<br>

6)<b>ControlDesk</b> : <br>
a) Added Override annotation to the run function to prevent confusion<br>
b) Moved registerPatron to BowlerFile to improve cohesion <br>
7)<b>EndGame </b> : <br>
a) Restarted thread in catch block if interrupt was received by thread<br>

8)<b>ScoreHistory </b> : <br>
a) Implemented all file reading in catch block to prevent the interrupts caused to pipe reading issues.<br>
b) Add private Instructor to hide public one (SingleTon Design Pattern).

9)<b>Lane Status View </b> : <br>
a) Removed some irrelavant branching and comments for better readability<br>
b) 
