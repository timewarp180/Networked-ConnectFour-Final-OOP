import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

// thread para 2 players
public class HandleASession implements Runnable, connectfourconstraints {
	private Socket player1;
	  private Socket player2;

	  // creating 42 cells
	  private char[][] cell =  new char[6][7];

	  private DataInputStream fromPlayer1;
	  private DataOutputStream toPlayer1;
	  private DataInputStream fromPlayer2;
	  private DataOutputStream toPlayer2;

	  // determine if mo play pa or di na
	  private boolean continueToPlay = true;

	  /* thread */
	  public HandleASession(Socket player1, Socket player2) {
	    this.player1 = player1;
	    this.player2 = player2;

	    // Initialize cells
	    for (int i = 0; i < 5; i++)
	      for (int j = 0; j < 6; j++)
	        cell[i][j] = ' ';
	  }

	  /** Implement the run() method for the thread */
	  public void run() {
	    try {
	      // input = kai pagawas . output kai pa read
	      DataInputStream fromPlayer1 = new DataInputStream(player1.getInputStream());
	      DataOutputStream toPlayer1 = new DataOutputStream(player1.getOutputStream());
	      DataInputStream fromPlayer2 = new DataInputStream(player2.getInputStream());
	      DataOutputStream toPlayer2 = new DataOutputStream(player2.getOutputStream());

	      // Write anything to notify player 1 to start
	      
	      toPlayer1.writeInt(1);

	      while (true) {
	    	  //recieve movce from player 1 
	        int row = fromPlayer1.readInt();
	        int column = fromPlayer1.readInt();
	      //  char token = 'r';

	        cell[row][column] = 'r';

	        //  player 1 win
	        if (isWon('r')) {
	          toPlayer1.writeInt(PLAYER1_WON);
	          toPlayer2.writeInt(PLAYER1_WON);
	          sendMove(toPlayer2, row, column);
	          break; 
	        }
	        
	        else if (isFull()) { 
	          toPlayer1.writeInt(DRAW);
	          toPlayer2.writeInt(DRAW);
	          sendMove(toPlayer2, row, column);
	          break;
	        }
	        else {
	          // Notify player 2 to take the turn
	          toPlayer2.writeInt(CONTINUE);

	          sendMove(toPlayer2, row, column);
	       }

	        // Receive a move from Player 2
	        row = fromPlayer2.readInt();
	        column = fromPlayer2.readInt();
	        cell[row][column] = 'y';

	        // Check if Player 2 wins
	        if (isWon('y')) {
	          toPlayer1.writeInt(PLAYER2_WON);
	          toPlayer2.writeInt(PLAYER2_WON);
	          sendMove(toPlayer1, row, column);
	          break;
	        }
	        else {
	          // Notify player 1 to take the turn
	          toPlayer1.writeInt(CONTINUE);

	          // Send player 2's selected row and column to player 1
	          sendMove(toPlayer1, row, column);
	        }
	      }
	    }
	    catch(IOException ex) {
	      System.err.println(ex);
	    }
	  }

	  // Send the move to other player 
	  private void sendMove(DataOutputStream out, int row, int column)
	      throws IOException {
	    out.writeInt(row); 
	    out.writeInt(column); 
	  }

	  // Determine if the cells are all occupied 
	  private boolean isFull() {
	    for (int i = 0; i < 5; i++)
	      for (int j = 0; j < 6; j++)
	        if (cell[i][j] == ' ')return false; // pwede pa ka butang

	    // All cells are filled
	    return true;
	  }
	  
	  
	  // determine who wonm
	  private boolean isWon(char token) {

	  	// TEST BOARD VALUES
//	  	for (int x = 0; x < 5; x++) {
//			for (int y = 0; y < 6; y++) {
//				System.out.print(cell[x][y]);
//			}
//			System.out.println();
//		}


		//Horizontal wins

		for (int x = 0; x < 6; x++) {
			for (int y = 0; y < 3; y++) {
				if (cell[x][y] == token && cell[x][y+1] == token && cell[x][y+2] == token && cell[x][y+3] == token) {
					return true;
				}
			}
		}
		///Vert

		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 7; y++) {
				if (cell[x][y] == token && cell[x+1][y] == token && cell[x+2][y] == token && cell[x+3][y] == token) {
					return true;
				}
			}
		}

		//Diagonal wins
		//0 to 1
		//0 to 3
		for (int x = 0; x < 3; x++) {
			for (int y = 0; y < 6; y++) {
				if (cell[x][y] == token && cell[x+1][y+1] == token && cell[x+2][y+2] == token && cell[x+3][y+3] == token) {
					return true;
				}
			}
		}

		//Other diagonal wins
		for (int x = 0; x < 3; x++) {
			for (int y = 3; y < 7; y++) {
				if (cell[x][y] == token && cell[x+1][y-1] == token && cell[x+2][y-2] == token && cell[x+3][y-3] == token) {
					return true;
				}
			}
		}
		//Check all rows
		
		for (int i = 0; i < 5; i++)
		      if 	((cell[i][0] == token)
		          && (cell[i][1] == token)
		          && (cell[i][2] == token)
		          && (cell[i][3] == token))
		      {
		        return true;
		      }
		    for (int i = 0; i < 5; i++)
		      if 	((cell[i][1] == token)
		          && (cell[i][2] == token)
		          && (cell[i][3] == token)
		          && (cell[i][4] == token))
		      {
		        return true;
		      }
		    for (int i = 0; i < 5; i++)
		      if 	((cell[i][2] == token)
		          && (cell[i][3] == token)
		          && (cell[i][4] == token)
		          && (cell[i][5] == token))
		      {
		        return true;
		      }
			for (int i = 0; i < 5; i++)
		      if 	((cell[i][3] == token)
		          && (cell[i][4] == token)
		          && (cell[i][5] == token)
		          && (cell[i][6] == token))
		      {
		        return true;
		      }
		 
		    // Check all columns 
		 
		    for (int j = 0; j < 6; j++)
		      if 	((cell[0][j] == token)
		          && (cell[1][j] == token)
		          && (cell[2][j] == token)
		          && (cell[3][j] == token))
		      {
		        return true;
		      }
		    for (int j = 0; j < 6; j++)
		      if	((cell[1][j] == token)
		          && (cell[2][j] == token)
		          && (cell[3][j] == token)
		          && (cell[4][j] == token))
		      {
		        return true;
		      }
		    for (int j = 0; j < 6; j++)
		      if	((cell[2][j] == token)
		          && (cell[3][j] == token)
		          && (cell[4][j] == token)
		          && (cell[5][j] == token))
		      {
		        return true;
		      }
		 
		    // Check major diagonal 
		      if  ((cell[3][0] == token)
		        && (cell[2][1] == token)
		        && (cell[1][2] == token)
		        && (cell[0][3] == token))
		    {
		      return true;
		    }
		     if   ((cell[4][0] == token)
		        && (cell[3][1] == token)
		        && (cell[2][2] == token)
		        && (cell[1][3] == token))
		    {
		      return true;
		    }
		     if   ((cell[3][1] == token)
		        && (cell[2][2] == token)
		        && (cell[1][3] == token)
		        && (cell[0][4] == token))
		    {
		      return true;
		    }
		     if   ((cell[5][0] == token)
		        && (cell[4][1] == token)
		        && (cell[3][2] == token)
		        && (cell[2][3] == token))
		    {
		      return true;
		    }
		     if   ((cell[4][1] == token)
		        && (cell[3][2] == token)
		        && (cell[2][3] == token)
		        && (cell[1][4] == token))
		    {
		      return true;
		    }
		     if   ((cell[3][2] == token)
		        && (cell[2][3] == token)
		        && (cell[1][4] == token)
		        && (cell[0][5] == token))
		    {
		      return true;
		    }
		     if   ((cell[5][1] == token)
		        && (cell[4][2] == token)
		        && (cell[3][3] == token)
		        && (cell[2][4] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[4][2] == token)
		        && (cell[3][3] == token)
		        && (cell[2][4] == token)
		        && (cell[1][5] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[3][3] == token)
		        && (cell[2][4] == token)
		        && (cell[1][5] == token)
		        && (cell[0][6] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[5][2] == token)
		        && (cell[4][3] == token)
		        && (cell[3][4] == token)
		        && (cell[2][5] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[4][3] == token)
		        && (cell[3][4] == token)
		        && (cell[2][5] == token)
		        && (cell[1][6] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[5][3] == token)
		        && (cell[4][4] == token)
		        && (cell[3][5] == token)
		        && (cell[2][6] == token))
		    {
		      return true;
		    }
		 
		    // Check subdiagonal 
		 
		    if 	  ((cell[2][0] == token)
		        && (cell[3][1] == token)
		        && (cell[4][2] == token)
		        && (cell[5][3] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[1][0] == token)
		        && (cell[2][1] == token)
		        && (cell[3][2] == token)
		        && (cell[4][3] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[2][1] == token)
		        && (cell[3][2] == token)
		        && (cell[4][3] == token)
		        && (cell[5][4] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[0][0] == token)
		        && (cell[1][1] == token)
		        && (cell[2][2] == token)
		        && (cell[3][3] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[1][1] == token)
		        && (cell[2][2] == token)
		        && (cell[3][3] == token)
		        && (cell[4][4] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[2][2] == token)
		        && (cell[3][3] == token)
		        && (cell[4][4] == token)
		        && (cell[5][5] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[0][1] == token)
		        && (cell[1][2] == token)
		        && (cell[2][3] == token)
		        && (cell[3][4] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[1][2] == token)
		        && (cell[2][3] == token)
		        && (cell[3][4] == token)
		        && (cell[4][5] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[2][3] == token)
		        && (cell[3][4] == token)
		        && (cell[4][5] == token)
		        && (cell[5][6] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[0][2] == token)
		        && (cell[1][3] == token)
		        && (cell[2][4] == token)
		        && (cell[3][5] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[1][3] == token)
		        && (cell[2][4] == token)
		        && (cell[3][5] == token)
		        && (cell[4][6] == token))
		    {
		      return true;
		    }
		    if 	  ((cell[0][3] == token)
		        && (cell[1][4] == token)
		        && (cell[2][5] == token)
		        && (cell[3][6] == token))
		    {
		      return true;
		    }
		
		
		return false;
	  }




 }

