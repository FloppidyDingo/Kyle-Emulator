/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Hardware.Screen;
import java.awt.KeyboardFocusManager;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import kyle.emulator.KyleEmulator;

/**
 *
 * @author James
 */
public class MainFrame extends javax.swing.JFrame {

    private KyleEmulator emulator;
    private Options optionFrame;
    private Screen screen;
    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        open = new javax.swing.JMenuItem();
        runBios = new javax.swing.JMenuItem();
        exit = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        pause = new javax.swing.JCheckBoxMenuItem();
        Stop = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        options = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        memory = new javax.swing.JMenuItem();
        status = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 851, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 605, Short.MAX_VALUE)
        );

        jMenu1.setText("File");

        open.setText("Open...");
        open.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openActionPerformed(evt);
            }
        });
        jMenu1.add(open);

        runBios.setText("Run BIOS");
        runBios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                runBiosActionPerformed(evt);
            }
        });
        jMenu1.add(runBios);

        exit.setText("Exit");
        exit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitActionPerformed(evt);
            }
        });
        jMenu1.add(exit);

        jMenuBar1.add(jMenu1);

        jMenu3.setText("Emulation");

        pause.setText("Pause");
        pause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pauseActionPerformed(evt);
            }
        });
        jMenu3.add(pause);

        Stop.setText("Stop");
        Stop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StopActionPerformed(evt);
            }
        });
        jMenu3.add(Stop);

        jMenuItem2.setText("Reset");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        options.setText("Options...");
        options.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                optionsActionPerformed(evt);
            }
        });
        jMenu3.add(options);

        jMenuBar1.add(jMenu3);

        jMenu2.setText("Tools");

        memory.setText("Memory...");
        jMenu2.add(memory);

        status.setText("CPU status...");
        status.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusActionPerformed(evt);
            }
        });
        jMenu2.add(status);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void optionsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_optionsActionPerformed
        // TODO add your handling code here:
        optionFrame.setVisible(true);
    }//GEN-LAST:event_optionsActionPerformed

    private void exitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_exitActionPerformed

    private void runBiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_runBiosActionPerformed
        // TODO add your handling code here:
        emulator.startEmulation();
        screen.init();
        runBios.setEnabled(false);
        open.setEnabled(false);
    }//GEN-LAST:event_runBiosActionPerformed

    private void statusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusActionPerformed
        // TODO add your handling code here:
        KyleEmulator.getEmulator().getStats().setVisible(true);
    }//GEN-LAST:event_statusActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        emulator.getCpu().reset();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void pauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseActionPerformed
        // TODO add your handling code here:
        emulator.getLoop().setPlaying(!pause.isSelected());
        if(pause.isSelected()){
            screen.stop();
            emulator.getDsp().stop();
        }else{
            screen.init();
            emulator.getDsp().init();
        }
    }//GEN-LAST:event_pauseActionPerformed

    private void StopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StopActionPerformed
        // TODO add your handling code here:
        emulator.getLoop().setPlaying(false);
        emulator.getCpu().reset();
        emulator.getDsp().stop();
        screen.stop();
        runBios.setEnabled(true);
        open.setEnabled(true);
    }//GEN-LAST:event_StopActionPerformed

    private void openActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openActionPerformed
        // TODO add your handling code here:
        JFileChooser FC = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("ROM Files","rom");
        FC.addChoosableFileFilter(filter);
        if (FC.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            emulator.getMemory().loadROM(FC.getSelectedFile());
            emulator.startEmulation();
            screen.init();
            runBios.setEnabled(false);
            open.setEnabled(false);
        }
    }//GEN-LAST:event_openActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem Stop;
    private javax.swing.JMenuItem exit;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuItem memory;
    private javax.swing.JMenuItem open;
    private javax.swing.JMenuItem options;
    private javax.swing.JCheckBoxMenuItem pause;
    private javax.swing.JMenuItem runBios;
    private javax.swing.JMenuItem status;
    // End of variables declaration//GEN-END:variables

    public void begin() {
        emulator = KyleEmulator.getEmulator();
        screen = new Screen();
        screen.setSize(jPanel1.getSize());
        jPanel1.add(screen);
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher((KeyEvent e) -> {
            if (e.getID() == KeyEvent.KEY_PRESSED) {
                emulator.getControls().keyPressed(e);
            } else if(e.getID() == KeyEvent.KEY_RELEASED) {
                emulator.getControls().keyReleased(e);
            }
            return false;
        });
        optionFrame = new Options();
        this.setVisible(true);
        this.addComponentListener(new ComponentAdapter() {
            
            @Override
            public void componentResized(ComponentEvent e) {
                screen.setSize(jPanel1.getSize());
            }
        });
   }
}
