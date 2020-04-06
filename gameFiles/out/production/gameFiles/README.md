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
a)  Removed Commendted Out Code for Better Readability<br>

4)<b>Lane</b> : <br>
a)  Removed string comparison using "==" becuase it causes errors sometimes and removed unnecessary type casting<br>
b) Removed Commendted Out Code for Better Readability<br>
c) Added @Override annotation to the function<br>
d) Added Braces to avoid Dangling else

5)<b>BowlerFile</b> : <br>
a) Implemented BufferRead with try catch block<br>
b) Implemented RandomAccessFile with try catch block<br>
c) 
