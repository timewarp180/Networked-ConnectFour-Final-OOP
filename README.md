# Networked-ConnectFour-Final-OOP

If downloaded from github:
I am using eclipse IDE and in order to run the program
-right click the package, go to properties 
-go to java build path
-select libraries , click classpath, then add class folder
-click on res, then okay, and apply and close

this allows to add the image from res folder that will be used in the titlescreen.java


-----------------------GUIDE-------------------


LOCAL:

--HOST/ PLAYER 1--
1. Run the connectfourserver.java, type in port.
2. Host should open cmd then type ipconfig, copy the ipv4 address
3. Run connectfourclient.java , type in ur name, paste the ipv4 address, and input the port of the server

-- PLAYER2 --
1. Run connectfourclient.java, type in name, type in the ipv4 address of the host/player1, 
and then input the port of the server



MULTIPLAYER:


-DOWNLOAD HAMACHI FROM vpn.net, install vpn then run.

------player 1-----
1. TURN ON THE HAMACHI, GO TO NETWORK TAB, SELECT CREATE A NETWORK, (Sign up an account if u dont have one),
2. Run the connectfourserver.java, type in port.
3. right click beside the turn on button at the hamachi then copy ipv4 address
4. Run connectfourclient.java , type in ur name, paste the ipv4 address, and input the port of the server


---player 2-----

1. Turn on the hamachi and go to NETWORK TAB, SELECT JOIN AN EXISITING NETWORK, INPUT THE NETWORK ID AND THE PASSWORD THAT THE HOST CREATED.
2. Run connectfourclient.java, type in name, type in the ipv4 address of the host/player1 (From the hamachi server), 
and then input the port of the server.


