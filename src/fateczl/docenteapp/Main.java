package fateczl.docenteapp;

import fateczl.docenteapp.views.Window;
import java.awt.EventQueue;

public class Main {
  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      try {
        new Window().setVisible(true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }
}
