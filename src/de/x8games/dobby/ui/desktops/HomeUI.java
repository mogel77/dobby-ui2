package de.x8games.dobby.ui.desktops;

import de.x8games.dobby.ui.ObjectUI;

import javax.swing.*;



public class HomeUI implements ObjectUI {

    private JPanel rootPanel;
    private JProgressBar pbarProgress;
    private JButton cmdStopp;
    private JButton cmdPrint;
    private JButton cmdHotend;
    private JSlider sldFeedrate;
    private JButton cmdFeedrate;
    private JSlider sldFlowrate;
    private JButton cmdFlowrate;
    private JLabel lblStatus;
    private JLabel lblFilename;
    private JLabel lblFilesize;
    private JLabel lblPrintTime;
    private JLabel lblFilament;
    private JLabel lblEstimatedPrintTime;

    @Override
    public JComponent getUIPane() {
        return rootPanel;
    }

    public JProgressBar getPbarProgress() {
        return pbarProgress;
    }

    public JButton getCmdStopp() {
        return cmdStopp;
    }

    public JButton getCmdPrint() {
        return cmdPrint;
    }

    public JButton getCmdHotend() {
        return cmdHotend;
    }

    public JSlider getSldFeedrate() {
        return sldFeedrate;
    }

    public JButton getCmdFeedrate() {
        return cmdFeedrate;
    }

    public JSlider getSldFlowrate() {
        return sldFlowrate;
    }

    public JButton getCmdFlowrate() {
        return cmdFlowrate;
    }

    public JLabel getLblStatus() {
        return lblStatus;
    }

    public JLabel getLblFilename() {
        return lblFilename;
    }

    public JLabel getLblFilesize() {
        return lblFilesize;
    }

    public JLabel getLblEstimatedPrintTime() {
        return lblEstimatedPrintTime;
    }

    public JLabel getLblFilament() {
        return lblFilament;
    }

    public JLabel getLblPrintTime() {
        return lblPrintTime;
    }
}
