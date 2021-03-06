package com.imie.morpion.view;

import com.imie.morpion.controller.BoardListener;
import com.imie.morpion.model.Game;

import javax.swing.*;
import java.awt.*;

/**
 * @author Marc-Antoine Perennou<Marc-Antoine@Perennou.com>
 */
public class Window extends JFrame implements GameListener {

   private Game game;
   private ScoresPanel scores;
   private Board board;

   public Window(Game game, BoardListener boardListener, String title) throws Exception {
      super("Morpion " + title);

      String[] lookAndFeels = {
            "com.sun.java.swing.plaf.gtk.GTKLookAndFeel",
            "com.sun.java.swing.plaf.windows.WindowsLookAndFeel",
            "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel",
            "com.sun.java.swing.plaf.motif.MotifLookAndFeel",
            "javax.swing.plaf.metal.MetalLookAndFeel"
      };

      integrate:
      {
         for (String name : lookAndFeels) {
            if (this.checkLookAndFeel(name))
               break integrate;
         }
         throw new Exception("No LookAndFeel");
      }

      this.game = game;
      game.subscribe(this);

      this.setLayout(new BorderLayout());
      this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      this.setSize(new Dimension(1220, 820));

      this.scores = new ScoresPanel();
      this.add(scores, BorderLayout.PAGE_START);

      this.board = new Board(500, 500);
      this.board.addListener(boardListener);
      this.add(board);

      this.scores.refresh(this.game.getScore1(), this.game.getScore2());
      this.board.refresh(this.game.getSquares());
   }

   private Boolean checkLookAndFeel(String name) {
      try {
         UIManager.setLookAndFeel(name);
         return true;
      } catch (RuntimeException ex) {
         return false;
      } catch (Exception ex) {
         return false;
      }
   }

   public void refresh() {

   }

   @Override
   public void onSquaresUpdate() {
      this.board.refresh(this.game.getSquares());
   }

   @Override
   public void onStateUpdate() {
      this.scores.refresh(this.game.getScore1(), this.game.getScore2());
   }
}
