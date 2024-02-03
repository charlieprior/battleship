package edu.duke.cgp26.battleship;

import java.io.*;

/**
 * The main entry point for our Battleship application.
 */
public class App {
    /**
     * The factory for creating ships.
     */
    final AbstractShipFactory<Character> shipFactory;
    /**
     * The game board.
     */
    final Board<Character> theBoard;
    /**
     * Text view of the game board.
     */
    final BoardTextView view;
    /**
     * Where to read input from.
     */
    final BufferedReader inputReader;
    /**
     * Where to print output.
     */
    final PrintStream out;

    /**
     * Creates an app given a {@link Board}, input reader and output stream.
     *
     * @param theBoard    the Board.
     * @param inputSource the input Reader.
     * @param out         the output stream.
     */
    public App(Board<Character> theBoard, Reader inputSource, PrintStream out) {
        this.shipFactory = new V1ShipFactory();
        this.theBoard = theBoard;
        this.view = new BoardTextView(theBoard);
        this.inputReader = new BufferedReader(inputSource);
        this.out = out;
    }

    /**
     * The entry point of our program.
     *
     * @param args Command line arguments.
     * @throws IOException We will not handle this exception.
     */
    public static void main(String[] args) throws IOException {
        Board<Character> board = new BattleShipBoard<>(10, 20);
        App app = new App(board, new InputStreamReader(System.in), System.out);
        app.doOnePlacement();
    }

    /**
     * Reads a placement String and places it.
     *
     * @param prompt the placement String.
     * @return the Placement
     * @throws IOException We will not handle this exception.
     */
    public Placement readPlacement(String prompt) throws IOException {
        out.println(prompt);
        String s = inputReader.readLine();
        return new Placement(s);
    }

    /**
     * Reads a placement String from the input reader, places it, and displays the Board.
     *
     * @throws IOException We will not handle this exception.
     */
    public void doOnePlacement() throws IOException {
        Placement p = readPlacement("Where would you like to put your ship?");
        Ship<Character> s = shipFactory.makeDestroyer(p);
        theBoard.tryAddShip(s);
        out.print(view.displayMyOwnBoard());
    }
}
