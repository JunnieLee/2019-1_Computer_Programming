import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.security.KeyPair;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.*;
//======================================================Don't modify below===============================================================//

enum PieceType {king, queen, bishop, knight, rook, pawn, none}
enum PlayerColor {black, white, none}

public class ChessBoard_2015_15356 {
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	private JPanel chessBoard;
	private JButton[][] chessBoardSquares = new JButton[8][8];
	private Piece[][] chessBoardStatus = new Piece[8][8];
	private ImageIcon[] pieceImage_b = new ImageIcon[7];
	private ImageIcon[] pieceImage_w = new ImageIcon[7];
	private JLabel message = new JLabel("Enter Reset to Start");

	ChessBoard_2015_15356(){
		initPieceImages();
		initBoardStatus();
		initializeGui();
	}

	public final void initBoardStatus(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++) chessBoardStatus[j][i] = new Piece();
		}
	}

	public final void initPieceImages(){
		pieceImage_b[0] = new ImageIcon(new ImageIcon("./img/king_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[1] = new ImageIcon(new ImageIcon("./img/queen_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[2] = new ImageIcon(new ImageIcon("./img/bishop_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[3] = new ImageIcon(new ImageIcon("./img/knight_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[4] = new ImageIcon(new ImageIcon("./img/rook_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[5] = new ImageIcon(new ImageIcon("./img/pawn_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));

		pieceImage_w[0] = new ImageIcon(new ImageIcon("./img/king_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[1] = new ImageIcon(new ImageIcon("./img/queen_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[2] = new ImageIcon(new ImageIcon("./img/bishop_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[3] = new ImageIcon(new ImageIcon("./img/knight_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[4] = new ImageIcon(new ImageIcon("./img/rook_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[5] = new ImageIcon(new ImageIcon("./img/pawn_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
	}

	public ImageIcon getImageIcon(Piece piece){
		if(piece.color.equals(PlayerColor.black)){
			if(piece.type.equals(PieceType.king)) return pieceImage_b[0];
			else if(piece.type.equals(PieceType.queen)) return pieceImage_b[1];
			else if(piece.type.equals(PieceType.bishop)) return pieceImage_b[2];
			else if(piece.type.equals(PieceType.knight)) return pieceImage_b[3];
			else if(piece.type.equals(PieceType.rook)) return pieceImage_b[4];
			else if(piece.type.equals(PieceType.pawn)) return pieceImage_b[5];
			else return pieceImage_b[6];
		}
		else if(piece.color.equals(PlayerColor.white)){
			if(piece.type.equals(PieceType.king)) return pieceImage_w[0];
			else if(piece.type.equals(PieceType.queen)) return pieceImage_w[1];
			else if(piece.type.equals(PieceType.bishop)) return pieceImage_w[2];
			else if(piece.type.equals(PieceType.knight)) return pieceImage_w[3];
			else if(piece.type.equals(PieceType.rook)) return pieceImage_w[4];
			else if(piece.type.equals(PieceType.pawn)) return pieceImage_w[5];
			else return pieceImage_w[6];
		}
		else return pieceImage_w[6];
	}

	public final void initializeGui(){
		gui.setBorder(new EmptyBorder(5, 5, 5, 5));
		JToolBar tools = new JToolBar();
		tools.setFloatable(false);
		gui.add(tools, BorderLayout.PAGE_START);
		JButton startButton = new JButton("Reset");
		startButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				initiateBoard();
			}
		});

		tools.add(startButton);
		tools.addSeparator();
		tools.add(message);

		chessBoard = new JPanel(new GridLayout(0, 8));
		chessBoard.setBorder(new LineBorder(Color.BLACK));
		gui.add(chessBoard);
		ImageIcon defaultIcon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
		Insets buttonMargin = new Insets(0,0,0,0);
		for(int i=0; i<chessBoardSquares.length; i++) {
			for (int j = 0; j < chessBoardSquares[i].length; j++) {
				JButton b = new JButton();
				b.addActionListener(new ButtonListener(i, j));
				b.setMargin(buttonMargin);
				b.setIcon(defaultIcon);
				if((j % 2 == 1 && i % 2 == 1)|| (j % 2 == 0 && i % 2 == 0)) b.setBackground(Color.WHITE);
				else b.setBackground(Color.gray);
				b.setOpaque(true);
				b.setBorderPainted(false);
				chessBoardSquares[j][i] = b;
			}
		}

		for (int i=0; i < 8; i++) {
			for (int j=0; j < 8; j++) chessBoard.add(chessBoardSquares[j][i]);

		}
	}

	public final JComponent getGui() {
		return gui;
	}

	public static void main(String[] args) {
		Runnable r = new Runnable() {
			@Override
			public void run() {
				ChessBoard_2015_15356 cb = new ChessBoard_2015_15356();
				JFrame f = new JFrame("Chess");
				f.add(cb.getGui());
				f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				f.setLocationByPlatform(true);
				f.setResizable(false);
				f.pack();
				f.setMinimumSize(f.getSize());
				f.setVisible(true);
			}
		};
		SwingUtilities.invokeLater(r);
	}

	//================================Utilize these functions========================================//

	class Piece{
		PlayerColor color;
		PieceType type;
		boolean pawn_initial=true; // 내가 새로 추가한 부분

		Piece(){
			color = PlayerColor.none;
			type = PieceType.none;
		}
		Piece(PlayerColor color, PieceType type){
			this.color = color;
			this.type = type;
		}

	}

	public void setIcon(int x, int y, Piece piece){
		chessBoardSquares[y][x].setIcon(getImageIcon(piece));
		chessBoardStatus[y][x] = piece;
	}

	public Piece getIcon(int x, int y){
		return chessBoardStatus[y][x];
	}

	public void markPosition(int x, int y){
		chessBoardSquares[y][x].setBackground(Color.pink);
	}

	public void unmarkPosition(int x, int y){
		if((y % 2 == 1 && x % 2 == 1)|| (y % 2 == 0 && x % 2 == 0)) chessBoardSquares[y][x].setBackground(Color.WHITE);
		else chessBoardSquares[y][x].setBackground(Color.gray);
	}

	public void setStatus(String inpt){
		message.setText(inpt);
	}

	public void initiateBoard(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++) setIcon(i, j, new Piece());
		}
		setIcon(0, 0, new Piece(PlayerColor.black, PieceType.rook));
		setIcon(0, 1, new Piece(PlayerColor.black, PieceType.knight));
		setIcon(0, 2, new Piece(PlayerColor.black, PieceType.bishop));
		setIcon(0, 3, new Piece(PlayerColor.black, PieceType.queen));
		setIcon(0, 4, new Piece(PlayerColor.black, PieceType.king));
		setIcon(0, 5, new Piece(PlayerColor.black, PieceType.bishop));
		setIcon(0, 6, new Piece(PlayerColor.black, PieceType.knight));
		setIcon(0, 7, new Piece(PlayerColor.black, PieceType.rook));
		for(int i=0;i<8;i++){
			setIcon(1, i, new Piece(PlayerColor.black, PieceType.pawn));
			setIcon(6, i, new Piece(PlayerColor.white, PieceType.pawn));
		}
		setIcon(7, 0, new Piece(PlayerColor.white, PieceType.rook));
		setIcon(7, 1, new Piece(PlayerColor.white, PieceType.knight));
		setIcon(7, 2, new Piece(PlayerColor.white, PieceType.bishop));
		setIcon(7, 3, new Piece(PlayerColor.white, PieceType.queen));
		setIcon(7, 4, new Piece(PlayerColor.white, PieceType.king));
		setIcon(7, 5, new Piece(PlayerColor.white, PieceType.bishop));
		setIcon(7, 6, new Piece(PlayerColor.white, PieceType.knight));
		setIcon(7, 7, new Piece(PlayerColor.white, PieceType.rook));
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++) unmarkPosition(i, j);
		}
		onInitiateBoard();
	}
//======================================================Don't modify above==============================================================//

	//======================================================Implement below=================================================================//
	enum MagicType {MARK, CHECK, CHECKMATE};
	private int selX, selY;
	private boolean check, checkmate;
	public boolean end = false;
	// 이 아래는 내가 임의로 만들어준 애들!
	private int state=0; // state 0, 1, 2, 3(game over)존재
	private PlayerColor turn= PlayerColor.black; // 현재 누구 turn이냐
	private Piece chosen_piece; // 현재 peice 뭐냐
	private ArrayList<Pair> available_positions= new ArrayList<Pair>();
	private Pair chosen_piece_coordinate;


	class Pair{ // 좌표값
		int x;
		int y;
		Pair(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	PlayerColor enemyColor(){
		switch (turn){
			case none:
				return PlayerColor.none;
			case white:
				return PlayerColor.black;
			case black:
				return PlayerColor.white;
			default:
				return PlayerColor.none;
		}
	}

	class ButtonListener implements ActionListener {
		int x;
		int y;

		ButtonListener(int x, int y) {
			this.x = x;
			this.y = y;
		}

		// (x, y)좌표를 클릭했을 때 발생하는 callback 함수
		public void actionPerformed(ActionEvent e) {    // Only modify here
			// (x, y) is where the click event occured

			if (end==true) {
				return;
			}

			// ****[State 0]********************************************************
			if (state == 0) {
				// 아래는 내가 새로 추가한 부분!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				switch (CheckStatus()){
					case 0: checkmate = true; check = false; break;
					case 1: check = true; checkmate = false; break;
					default: check = false; checkmate = false; break;
				}
				PrintStatus();

				chosen_piece_coordinate = new Pair(this.x, this.y);
				chosen_piece = getIcon(this.x, this.y);
				//- (1) 내 말이 맞느냐?
				if (chosen_piece.color != turn) return; // 다른 쪽 말이라면 그냥 무시
				//- (2) style 따라서 갈 수 있는 <mark>저장 - array
				available_positions.clear(); // 갈수 있는 mark 저장할 array 일단 비우고
				FindoutPlaceToGo(available_positions, chosen_piece, this.x, this.y); // piece type에 따라 갈 수 있는 위치 array에 담기!
				display_available_positions(); // 갈 수 있는곳 정했으면 핑크색으로 표시해줌
				state = 1; // 할 일 다 끝내면 다음 turn에 state1로 넘어가기!
			}

			// ****[State 1]********************************************************
			else if (state == 1) {
				// 아래는 내가 새로 추가한 부분!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				switch (CheckStatus()){
					case 0: checkmate = true; check = false; break;
					case 1: check = true; checkmate = false; break;
					default: check = false; checkmate = false; break;
				}

				remove_position_display();
				//-  X unmark
				boolean not_available_position = true;
				for (Pair pairs : available_positions){
					if ((pairs.x==this.x)&& (pairs.y==this.y)){
						not_available_position = false;
					}
				}
				if (not_available_position) {
					state = 0;
					return;
				}

				// 이 아래부터는 갈 수 있는 곳을 클릭한 경우임!

				if (getIcon(this.x, this.y).color == enemyColor()) { //- (1) 공격이 가능한 경우. (공격 O. 상대편 말이 있는 자리 선택)
					if (getIcon(this.x, this.y).type == PieceType.king) {
						state = 3; // (1)-1. 왕을 공격한 상황이면 - game over state
						end = true;
					}
					else {state = 2;} // (1)-2. 왕 말고 다른 말을 공격한 경우
				} else {state =2;} //- (2) 이동만 하는 경우.


				// (1) 기존에 있던 말은 그 자리에서 제외시켜줌
				setIcon(chosen_piece_coordinate.x, chosen_piece_coordinate.y, new Piece(PlayerColor.none, PieceType.none));
				// (2) 새로운 자리로 옮겨줌
				setIcon(this.x, this.y, chosen_piece); // 일단 선택한 자리로 말을 옮김
			}

			// ****[State 2]********************************************************
			if (state == 2) {
				turn = enemyColor();  // turn change

				switch (CheckStatus()){
					case 0: checkmate = true; check = false; break;
					case 1: check = true; checkmate = false; break;
					default: check = false; checkmate = false; break;
				}

				PrintStatus();
				state = 0;
			}

			// GAME OVER STATE
			// ****[State 3]********************************************************
			if (state == 3){ // state 3은 game over인 상황
				// end = true;
				check = false;
				checkmate = false;
				turn = enemyColor();
				PrintStatus();
				// return;
			}
		}
	} // end of ButtonListener class

	void FindoutPlaceToGo(ArrayList<Pair> arr, Piece chosen_piece, int x, int y) {
		switch (chosen_piece.type) {
			case pawn:
				Pawn_move_position(arr, x, y);
				break;
			case rook:
				Rook_move_position(arr, x, y);
				break;
			case knight:
				Knight_move_position(arr, x, y);
				break;
			case bishop:
				Bishop_move_position(arr, x, y);
				break;
			case queen:
				Queen_move_position(arr, x, y);
				break;
			case king:
				King_move_position(arr, x, y);
				break;
		}
	}


	void Pawn_move_position(ArrayList<Pair> arr, int x, int y) {
		int up_or_down = 0;
		if (turn == PlayerColor.black) {
			up_or_down = 1;
		} // 흑 - 아래쪽으로
		else if (turn == PlayerColor.white) {
			up_or_down = -1;
		} // 백 - 위쪽으로

		// [1] 원래 갈 수 있게 룰에서 정해놓은 곳
		for (int i = 1; i <= 2; i++) {
			Pair coordinate = new Pair(x + (i * up_or_down), y);
			if (valid_coordinate(coordinate.x, coordinate.y) && getIcon(coordinate.x, coordinate.y).type == PieceType.none) {
				// (좌표값이 -가 되지 않고, 그 자리에 다른 말이 없으면 갈 수 있도록 핑크 표시)
				arr.add(coordinate);
			} else {
				break;
			} // 이동 경로에 있는 말(아군/상대)을 뛰어넘는 것은 불가능
			if (!getIcon(x, y).pawn_initial) break; // 처음 움직이는게 아닐 경우 앞으로 1만 움직일 수 있음!
		}
		getIcon(x, y).pawn_initial = false;

		// [2] 공격할 수 있는 곳 (대각선쪽에 말이 있어야만 가능)
		Pair coordinate1 = new Pair(x + up_or_down, y + 1); // 말이 black일때 - 오른쪽 아래 대각선  // 말이 white일때 - 오른쪽 위 대각선
		Pair coordinate2 = new Pair(x + up_or_down, y - 1); // 말이 black일때 - 왼쪽 아래 대각선  // 말이 white일때 - 왼쪽 위 대각선

		// 좌표값이 -가 되지 않고, 그 자리에 다른 쪽 말이 있다면 - 그쪽으로 이동해서 공격가능
		if (valid_coordinate(coordinate1.x, coordinate1.y) && getIcon(coordinate1.x, coordinate1.y).color == enemyColor()) {
			arr.add(coordinate1);
		}
		if (valid_coordinate(coordinate2.x, coordinate2.y) && getIcon(coordinate2.x, coordinate2.y).color == enemyColor()) {
			arr.add(coordinate2);
		}
	}


	void Rook_move_position(ArrayList<Pair> arr, int x, int y) { // 십자가 모양으로 움직일 수 있음

		// 1. 오른쪽
		int r = 1;
		while (y + r < 8) {
			Pair coordinate = new Pair(x, y + r);
			if (getIcon(coordinate.x, coordinate.y).color != turn) {
				// (그 자리에 다른 말이 없으면 갈 수 있도록 핑크 표시)
				arr.add(coordinate);
				// 이 바로 아래는 새로 추가한 부분!!
				if (getIcon(coordinate.x, coordinate.y).type!=PieceType.none) break; // 경로상의 말을 뛰어넘을 수 없음
			} else {
				break;
			} // 경로상의 말을 뛰어넘을 수 없음
			r++;
		}

		// 2. 왼쪽
		int l = 1;
		while (y - l >= 0) {
			Pair coordinate = new Pair(x, y - l);
			if (getIcon(coordinate.x, coordinate.y).color != turn) {
				// (그 자리에 다른 말이 없으면 갈 수 있도록 핑크 표시)
				arr.add(coordinate);
				// 이 바로 아래는 새로 추가한 부분!!
				if (getIcon(coordinate.x, coordinate.y).type!=PieceType.none) break; // 경로상의 말을 뛰어넘을 수 없음
			} else {
				break;
			} // 경로상의 말을 뛰어넘을 수 없음
			l++;
		}

		// 3. 위쪽
		int u = 1;
		while (x - u >= 0) {
			Pair coordinate = new Pair(x - u, y);
			if (getIcon(coordinate.x, coordinate.y).color != turn) {
				// (그 자리에 다른 말이 없으면 갈 수 있도록 핑크 표시)
				arr.add(coordinate);
				// 이 바로 아래는 새로 추가한 부분!!
				if (getIcon(coordinate.x, coordinate.y).type!=PieceType.none) break; // 경로상의 말을 뛰어넘을 수 없음
			} else {
				break;
			} // 경로상의 말을 뛰어넘을 수 없음
			u++;
		}

		// 4. 아래쪽
		int d = 1;
		while (x + d < 8) {
			Pair coordinate = new Pair(x + d, y);
			if (getIcon(coordinate.x, coordinate.y).color != turn) {
				// (그 자리에 다른 말이 없으면 갈 수 있도록 핑크 표시)
				arr.add(coordinate);
				// 이 바로 아래는 새로 추가한 부분!!
				if (getIcon(coordinate.x, coordinate.y).type!=PieceType.none) break; // 경로상의 말을 뛰어넘을 수 없음
			} else {
				break;
			} // 경로상의 말을 뛰어넘을 수 없음
			d++;
		}
	}


	void Knight_move_position(ArrayList<Pair> arr, int x, int y) { // 원래 갈 수 있게 룰에서 정해놓은 곳 - 공격 가능한 곳과 일치
		int[] helper_arr = {-1, -2, 1, 2};
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (helper_arr[i]+helper_arr[j]==0 || i==j) continue; // 절댓값 같은애들끼리는 X
				int row = x + helper_arr[i];
				int column = y + helper_arr[j];
				if (valid_coordinate(row, column) && getIcon(row, column).color != turn) {
					arr.add(new Pair(row, column));
				}
			}
		}
	}


	void Bishop_move_position(ArrayList<Pair> arr, int x, int y) { // 대각선 위치 추출
		// [1] 오른쪽 위
		for (int i = 1; i < 8; i++) {
			int row = x + i;
			int column = y + i;
			if (!valid_coordinate(row, column)) break; // 수용 가능 범위를 넘어가면 for loop 탈출
			if (getIcon(row, column).color != turn) {
				arr.add(new Pair(row, column));
				// 이 바로 아래는 새로 추가한 부분!!
				if (getIcon(row, column).type!=PieceType.none) break; // 경로상의 말을 뛰어넘을 수 없음
			} else {
				break;
			} // 경로상의 말을 뛰어넘을 수 없음.
		}
		// [2] 왼쪽 위
		for (int i = 1; i < 8; i++) {
			int row = x - i;
			int column = y - i;
			if (!valid_coordinate(row, column)) break; // 수용 가능 범위를 넘어가면 for loop 탈출
			if (getIcon(row, column).color != turn) {
				arr.add(new Pair(row, column));
				// 이 바로 아래는 새로 추가한 부분!!
				if (getIcon(row, column).type!=PieceType.none) break; // 경로상의 말을 뛰어넘을 수 없음
			} else {
				break;
			} // 경로상의 말을 뛰어넘을 수 없음.
		}
		// [3] 왼쪽 아래
		for (int i = 1; i < 8; i++) {
			int row = x + i;
			int column = y - i;
			if (!valid_coordinate(row, column)) break; // 수용 가능 범위를 넘어가면 for loop 탈출
			if (getIcon(row, column).color != turn) {
				arr.add(new Pair(row, column));
				// 이 바로 아래는 새로 추가한 부분!!
				if (getIcon(row, column).type!=PieceType.none) break; // 경로상의 말을 뛰어넘을 수 없음
			} else {
				break;
			} // 경로상의 말을 뛰어넘을 수 없음.
		}
		// [4] 오른쪽 아래
		for (int i = 1; i < 8; i++) {
			int row = x - i;
			int column = y + i;
			if (!valid_coordinate(row, column)) break; // 수용 가능 범위를 넘어가면 for loop 탈출
			if (getIcon(row, column).color != turn) {
				arr.add(new Pair(row, column));
				// 이 바로 아래는 새로 추가한 부분!!
				if (getIcon(row, column).type!=PieceType.none) break; // 경로상의 말을 뛰어넘을 수 없음
			} else {
				break;
			} // 경로상의 말을 뛰어넘을 수 없음.
		}
	}

	void Queen_move_position(ArrayList<Pair> arr, int x, int y) { // 대각, 십자방향 모두 가능
		Rook_move_position(arr, x, y); // 십자방향
		Bishop_move_position(arr, x, y); // 대각
	}

	void King_move_position(ArrayList<Pair> arr, int x, int y) {
		int[] helper_arr = {0, -1, 1};
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (j == 0 && i == 0) continue; // j하고 i둘다 0일때는 제외!! (루프가 총 3*3-1 = 9-1= 8번 돈다!)
				int row = x + helper_arr[i];
				int column = y + helper_arr[j];
				if (valid_coordinate(row, column) && getIcon(row, column).color != turn) {
					arr.add(new Pair(row, column));
				}
			}
		}
	} // end of King_move_position


	int CheckStatus(){ // normal이면 -1, check면 1, checkmate면 0
		AttackChecker my_kings_safety = new AttackChecker(Kings_Poisition(turn));
		if (my_kings_safety.isUnderAttack()){ // 일단 check 혹은 checkmate인 상태
			return (my_kings_safety.isCheckMate()? 0 : 1);
		}
		else {return -1;}
	}

	boolean valid_coordinate(int row, int column){
		if (row < 0 || column < 0) return false;
		if (row >= 8 || column >= 8) return false;
		return true;
	}

	void display_available_positions(){
		for (Pair coordinates : available_positions){
			markPosition(coordinates.x, coordinates.y);
		}
	}

	void remove_position_display(){
		for (Pair coordinates : available_positions){
			unmarkPosition(coordinates.x, coordinates.y);
		}
	}


	void PrintStatus(){
		String str="";
		if (turn==PlayerColor.black){
			str+="BLACK's TURN ";
		} else if (turn==PlayerColor.white){
			str+="WHITE's TURN ";
		}

		if (check){
			str+="/ CHECK";
		} else if (checkmate){
			str+="/ CHECKMATE";
			end = true;
			state = 3;
		}
		setStatus(str);
	}

	Pair Kings_Poisition(PlayerColor color) {
		for (int i=0; i<8;i++){
			for (int j=0; j<8; j++){
				if (getIcon(i,j).color==color && getIcon(i,j).type==PieceType.king){
					return new Pair(i, j);
				}
			}
		}
		return new Pair(-1,-1); // error - King not found
	}

	// Reset 버튼을 클릭한 뒤 발생하는 callback 함수
	void onInitiateBoard(){
		available_positions.clear();
		turn = PlayerColor.black;
		checkmate = false;
		check = false;
		end = false;
		state = 0;
		// Status Print 하는 메세지 어떻게 되나 확인해야!
		PrintStatus();
	}


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// 메인 업무를 하는 AttackChecker 클래스 //
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	class AttackChecker{
		// properties ----------------------------------------------------------------
		public Pair target_piece_coordinate;
		public Piece target_piece;
		private ArrayList<Pair> positions_of_danger = new ArrayList<Pair>();
		// 그곳으로 자리를 옮기더라도 다른 공격수로부터 죽임당할 위험이 있는 좌표들
		// --> 여기로 King이 옮겨갔을때는 안전하지 않음! 다시 Check해줘야함... ㅠ
		private ArrayList<Pair> invader_list = new ArrayList<Pair>();
		private ArrayList<Pair> invading_route = new ArrayList<Pair>();
		// constructor ----------------------------------------------------------------
		AttackChecker(Pair coordinate){
			target_piece_coordinate = coordinate;
			target_piece = getIcon(coordinate.x, coordinate.y);
			invader_list = GetInvaderList();
			invading_route = GetInvadingRoute();
		}
		// class methods ----------------------------------------------------------------
		boolean isUnderAttack(){ return (!invader_list.isEmpty());}

		ArrayList<Pair> GetInvaderList() {
			ArrayList<Pair> enemy_pieces = new ArrayList<>();
			ArrayList<Pair> InvaderList = new ArrayList<>();
			// [1] 반대편 말들의 좌표를 enemy_pieces에 저장
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (getIcon(i, j).color == ((target_piece.color == PlayerColor.black) ? PlayerColor.white : PlayerColor.black)) {
						enemy_pieces.add(new Pair(i, j));
					}
				}
			}
			// [2] enemy_pieces 하나하나씩 (상대편 king을 공격가능한지 확인)
			for (Pair coordinates : enemy_pieces) {
				ArrayList<Pair> possible_moves = new ArrayList<>(); // 각 반대편 말들에 대해 그들이 갈 수있는 곳 좌표값 저장
				Piece enemy_piece = getIcon(coordinates.x, coordinates.y);
				// 여기선 simulation만 돌리는 것임. 따라서 처음 움직이는 pawn의 경우 시뮬레이션 때문에 초창기 2칸 움직이기 기능이 마비되지 않도록
				// 특별한 처리를 해주어야 함.
				boolean fix = false;
				if (enemy_piece.pawn_initial == true) fix = true;
				// 특별처리 2 -- 현재 turn과 구하고자 하는 target이 달라서 문제발생
				turn = enemy_piece.color; // 추가한 코드
				// 요 아래 부분이 현재 enemy piece가 공격할 수 있는 곳을 좌표로 possible_moves에 넣어줌
				if (enemy_piece.type == PieceType.pawn) { // pawn만 갈 수 있는곳과 공격할 수 있는 곳이 달라서 따로 처리해줌!!
					// 공격할 수 있는 곳 (대각선쪽에 말이 있어야만 가능)
					Pair coordinate1 = new Pair(coordinates.x + ((turn == PlayerColor.black) ? 1 : -1), coordinates.y + 1); // 말이 black일때 - 오른쪽 아래 대각선  // 말이 white일때 - 오른쪽 위 대각선
					Pair coordinate2 = new Pair(coordinates.x + ((turn == PlayerColor.black) ? 1 : -1), coordinates.y - 1); // 말이 black일때 - 왼쪽 아래 대각선  // 말이 white일때 - 왼쪽 위 대각선
					// 좌표값이 -가 되지 않고, 그 자리에 다른 쪽 말이 있다면 - 그쪽으로 이동해서 공격가능
					if (valid_coordinate(coordinate1.x, coordinate1.y) && getIcon(coordinate1.x, coordinate1.y).color == enemyColor()) {
						possible_moves.add(coordinate1);
						positions_of_danger.add(coordinate1);
					}
					if (valid_coordinate(coordinate1.x, coordinate1.y) && getIcon(coordinate1.x, coordinate1.y).color == turn) {
						positions_of_danger.add(coordinate1);
					}
					if (valid_coordinate(coordinate2.x, coordinate2.y) && getIcon(coordinate2.x, coordinate2.y).color == enemyColor()) {
						possible_moves.add(coordinate2);
						positions_of_danger.add(coordinate2);
					}
					if (valid_coordinate(coordinate2.x, coordinate2.y) && getIcon(coordinate2.x, coordinate2.y).color == turn) {
						positions_of_danger.add(coordinate2);
					}
				} else { // pawn 말고는 갈 수 있는곳과 공격할 수 있는 곳이 동일!!
					FindoutPlaceToGo(possible_moves, enemy_piece, coordinates.x, coordinates.y);
					turn = enemyColor();
					FindoutPlaceToGo(positions_of_danger, enemy_piece, coordinates.x, coordinates.y);
					turn = enemyColor();
				}
				turn = target_piece.color;
				if (fix) enemy_piece.pawn_initial = true;
				// 특별처리 작업 끝!

				for (Pair available_position : possible_moves) {
					if (target_piece_coordinate.x == available_position.x && target_piece_coordinate.y == available_position.y)
						InvaderList.add(coordinates);
				}
			}
			return InvaderList;
		}


		ArrayList<Pair> MiniInvadingRoute(Pair invader_coordinate){
			ArrayList<Pair> route_per_piece = new ArrayList<Pair>();
			Piece invader_piece = getIcon(invader_coordinate.x, invader_coordinate.y);
			// Rook, Bishop, Queen의 경우만 고려하면 됨. (다른 애들의 경우 다른 애들을 쭉 거쳐 가지 않음)
			if (invader_piece.type==(PieceType.rook) || (invader_piece.type==PieceType.bishop) || (invader_piece.type==PieceType.queen)){
				// (1) king이 같은 행에 있을때
				if (invader_coordinate.x == target_piece_coordinate.x){
					int bigger = (invader_coordinate.y > target_piece_coordinate.y)? invader_coordinate.y:target_piece_coordinate.y;
					int smaller = (invader_coordinate.y < target_piece_coordinate.y)? invader_coordinate.y:target_piece_coordinate.y;
					for (int i=smaller+1; i<bigger; i++){
						route_per_piece.add(new Pair(invader_coordinate.x, i));
					}
				}
				// (2) king이 같은 열에 있을때
				else if (invader_coordinate.y == target_piece_coordinate.y){
					int bigger = (invader_coordinate.x > target_piece_coordinate.x)? invader_coordinate.x : target_piece_coordinate.x;
					int smaller = (invader_coordinate.x < target_piece_coordinate.x)? invader_coordinate.x : target_piece_coordinate.x;
					for (int i=smaller+1; i<bigger; i++){
						route_per_piece.add(new Pair(i, invader_coordinate.y));
					}
				}
				// (3) king이 (왼 high) 대각선에 있을때
				else if (target_piece_coordinate.x < invader_coordinate.x && target_piece_coordinate.y < invader_coordinate.y){
					for (int i=1; invader_coordinate.x-i > target_piece_coordinate.x; i++){
						route_per_piece.add(new Pair(invader_coordinate.x-i, invader_coordinate.y-i));
					}
				}
				// (4) king이 (오른 high) 대각선에 있을때
				else if (target_piece_coordinate.x < invader_coordinate.x && target_piece_coordinate.y > invader_coordinate.y){
					for (int i=1; (invader_coordinate.x-i > target_piece_coordinate.x) && (target_piece_coordinate.y > invader_coordinate.y+i); i++){
						route_per_piece.add(new Pair(invader_coordinate.x-i, invader_coordinate.y+i));
					}
				}
				// (5) king이 (왼 low) 대각선에 있을때
				else if (target_piece_coordinate.x > invader_coordinate.x && target_piece_coordinate.y < invader_coordinate.y){
					for (int i=1; (target_piece_coordinate.x > invader_coordinate.x+i) && (target_piece_coordinate.y < invader_coordinate.y+i); i++){
						route_per_piece.add(new Pair(invader_coordinate.x+i, invader_coordinate.y-i));
					}
				}
				// (6) king이 (오른 low) 대각선에 있을때
				else if (target_piece_coordinate.x > invader_coordinate.x && target_piece_coordinate.y > invader_coordinate.y){
					for (int i=1; (target_piece_coordinate.x > invader_coordinate.x+i) && (target_piece_coordinate.y > invader_coordinate.y+i); i++){
						route_per_piece.add(new Pair(invader_coordinate.x+i, invader_coordinate.y+i));
					}
				}
			}
			return route_per_piece;
		}


		ArrayList<Pair> GetInvadingRoute(){
			ArrayList<Pair> enemys_path_to_my_king = new ArrayList<Pair>();
			// coordinates in betwwen the invading route
			for (Pair invader_coordinate : invader_list){
				for (Pair mini_coordinate : MiniInvadingRoute(invader_coordinate)){
					enemys_path_to_my_king.add(mini_coordinate);
				}
			}
			return enemys_path_to_my_king;
		}

		boolean CanBlockAttack(){
			// 모든 attack을 다 막을 수 있어야 함
			ArrayList<Pair> our_team_path = my_side_path();
			int block_num=0;
			for (Pair invader_coordinate : invader_list){
				for (Pair mini_coordinate : MiniInvadingRoute(invader_coordinate)){ // 하나의 invader와 걔가 갈 수 있는 invading route에 대하여
					boolean blocking_available=false;
					for (Pair my_path : our_team_path){
						if (mini_coordinate.x==my_path.x && mini_coordinate.y==my_path.y){ // 막을 수 있는 루트가 존재!
							blocking_available=true;
							break;
						}
					}
					if (blocking_available) block_num++;
				}
			}

			System.out.println("number of invaders :" + invader_list.size());
			System.out.println("number of available blocking events :" + block_num);

			// 모든 invader를 다 막았다면 true, 아니면 false
			return (block_num==invader_list.size());
		}


		// 수정요망
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 지금 문제있는 부분!///////////////////////////////////////////////////////////////////////////////////////
		boolean CanKillInvader(){
			// 근데 걔를 죽일 수 있는게 King이면...
			// King이 그자리에 갔을때 또 check가 될수도 있음...
			// 그럼 그 경우에 어짜피 또 죽을위기니까 checkmate여야 하는것임....

			PlayerColor this_turn = turn;
			// 이건 invader가 두명 이상일때는 그냥 안되는거... (한번의 turn으로 몽땅 죽일수가 없으므로)
			if (invader_list.size() >= 2) return false;
			// invader가 한명 이하일때만.. 경우의 수를 따져볼 수 있음...
			if (!invader_list.isEmpty()){
				AttackChecker check_invader = new AttackChecker(invader_list.get(0));
				if (check_invader.isUnderAttack()) {
					turn = this_turn; // 일단 얘는 반드시 해줘야하구
					Pair our_attacker_coordinate = check_invader.invader_list.get(0);
					// 만약에 현재 공격수를 죽일 수 있는게 우리의 King이면
					if (our_attacker_coordinate.x==target_piece_coordinate.x && our_attacker_coordinate.y==target_piece_coordinate.y){
						// 걔를 죽이러 갔을때 또 새로운 다른 공격수에게 죽임의 위협을 당할 수도 있음. 그럴때는 checkmate임.
						// 따라서, 그런 경우엔 완벽히 공격수를 몽땅 제거할 수 있는게 아니라고 가정함.
						for (Pair danger_zone : positions_of_danger){
							if (invader_list.get(0).x== danger_zone.x && invader_list.get(0).y== danger_zone.y){
								return false;
							}
						}
					} else{ // 만약에 현재 공격수를 죽일 수 있는게 우리편 King이 아니라 우리편 다른 말이라면
						return true;
					}
				}
				else {
					turn = this_turn;
					return false;
				} // 유일하게 있는 invader가 위험에 빠져있지 않은 상태라면, 걔를 죽일 수 없다는 뜻이겠지
			}
			// 죽일 수 있는 invader자체가 애초에 없으면 false를 return
			return false;
		}



		// 수정요망
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// 지금 문제있는 부분!///////////////////////////////////////////////////////////////////////////////////////
		boolean isStuck(){ // 조건 [1] 왕이 공격을 피할 수 있는 다른 자리로 갈 수 없어야 함... 즉, isStuck가 true여야 함
			// 피하러 갔을때 또 새로운 다른 공격수에게 죽임의 위협을 당할 수도 있음.
			// 따라서, 그런 경우엔 완벽히 공격수를 피할 수 있는게 아니라고 가정함.
			ArrayList<Pair> place_to_defend = new ArrayList<Pair>();

			boolean fix = false;
			if (target_piece.pawn_initial == true) fix = true;
			FindoutPlaceToGo(place_to_defend, target_piece, target_piece_coordinate.x, target_piece_coordinate.y);
			if (fix) target_piece.pawn_initial = true;

			int useless_moves=0; // king이 갈 수 있지만 가도 어짜피 잡히는 곳의 숫자
			for (Pair targets_move : place_to_defend){
				boolean useless = false;
				for (Pair enemys_move:positions_of_danger){ // 이거 원래는... invasion route들만 체크하는거였는데...positions_of_danger로 바꿈..
					if (enemys_move.x==targets_move.x && enemys_move.y==targets_move.y) {
						useless = true;
					}
				}
				if (useless) useless_moves++;
			}
			return (place_to_defend.size()==useless_moves); // 갈 수 있는곳이 다 useless하면 isStuck가 맞음...
		}

		boolean isCheckMate(){
			// 디버깅용 메세지!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
			System.out.println("is Stuck? " + isStuck());
			System.out.println("Can't Block Attack? " + !CanBlockAttack());
			System.out.println("Can't Kill Invader? " + !CanKillInvader());
			// 디버깅용 메세지!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1
			if (target_piece.type!=PieceType.king){
				// System.out.println("Well, this piece is not a King.");
				return false;
			}
			else {
				if (isStuck() && !CanBlockAttack() && !CanKillInvader()) return true;
			}
			return false;
		}

		ArrayList<Pair> my_side_path(){
			// target piece가 갈 수 있는 자리는 고려하지 않음.
			ArrayList<Pair> result = new ArrayList<Pair>();
			ArrayList<Pair> my_side = new ArrayList<Pair>();
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if ((getIcon(i, j).color == (target_piece.color))&&(getIcon(i, j)!=target_piece)){ // 우리 target path가 갈 수 있는곳은 일단 고려 X
						my_side.add(new Pair(i, j));
					}
				}
			}
			for (Pair my_piece : my_side){
				Piece mine = getIcon(my_piece.x, my_piece.y);
				boolean fix = false;
				if (mine.pawn_initial == true) fix = true;
				FindoutPlaceToGo(result, mine, my_piece.x, my_piece.y);
				if (fix) mine.pawn_initial = true;
			}
			return result;
		}
	} // end of Class AttackChecker


} // end of ChessBoard class