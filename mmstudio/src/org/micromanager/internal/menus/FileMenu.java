package org.micromanager.internal.menus;

import java.io.IOException;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

import org.micromanager.internal.MMStudio;
import org.micromanager.internal.utils.GUIUtils;
import org.micromanager.internal.utils.ReportingUtils;



/**
 * Handles setting up the File menu and its actions.
 */
public class FileMenu {
   private MMStudio studio_;

   public FileMenu(MMStudio studio) {
      studio_ = studio;
   }

   public void initializeFileMenu(JMenuBar menuBar) {
      JMenu fileMenu = GUIUtils.createMenuInMenuBar(menuBar, "File");

      GUIUtils.addMenuItem(fileMenu, "Open (Virtual)...", null,
         new Runnable() {
            @Override
            public void run() {
               new Thread() {
                  @Override
                  public void run() {
                     try {
                        studio_.data().promptForDataToLoad(true);
                     }
                     catch (IOException e) {
                        ReportingUtils.showError(e, "There was an error when opening data");
                     }
                  }
               }.start();
            }
         }
      );

      GUIUtils.addMenuItem(fileMenu, "Open (RAM)...", null,
         new Runnable() {
            @Override
            public void run() {
               new Thread() {
                  @Override
                  public void run() {
                     try {
                        studio_.data().promptForDataToLoad(false);
                     }
                     catch (IOException e) {
                        ReportingUtils.showError(e, "There was an error when opening data");
                     }
                  }
               }.start();
            }
         }
      );

      fileMenu.addSeparator();

      GUIUtils.addMenuItem(fileMenu, "Exit", null,
         new Runnable() {
            public void run() {
               studio_.closeSequence(false);
            }
         }
      );
   }
}
