package simulator;

import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 *
 * @author Prins
 */
public class SimulatorGui extends JFrame {

    public SimulatorGui() {
        initGui();
        initComponents();
    }

    public void showGui() {
        pack();
        setVisible(true);
    }

    private void initGui() {
        setTitle(SimulatorConstants.SIM_NAME);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void initComponents() {
        initWorld();
        initControls();
    }

    private void initControls() {
        JPanel controlsPanel = new JPanel();

        startButton = new JButton("Start");
        controlsPanel.add(startButton);

        stepButton = new JButton("Step");
        controlsPanel.add(stepButton);

        stopButton = new JButton("Stop");
        controlsPanel.add(stopButton);

        resetButton = new JButton("Reset");
        controlsPanel.add(resetButton);

        controlsPanel.add(new JLabel("Slow"));

        speedSlider = new JSlider(JSlider.HORIZONTAL,
                SimulatorConstants.MIN_SIM_SPEED,
                SimulatorConstants.MAX_SIM_SPEED,
                SimulatorConstants.INIT_SIM_SPEED);
        controlsPanel.add(speedSlider);

        controlsPanel.add(new JLabel("Fast"));

        this.add(controlsPanel, BorderLayout.SOUTH);
    }

    private void initWorld() {
        DefaultTableModel model = new DefaultTableModel(SimulatorConstants.WORLD_WIDTH, SimulatorConstants.WORLD_HEIGHT);
        world = new JTable(model);
        world.setEnabled(false);

        for (int col = 0; col < SimulatorConstants.WORLD_WIDTH; col++) {
            TableColumn column = world.getColumnModel().getColumn(col);
            column.setMinWidth(SimulatorConstants.WORLD_CELL_SIZE);
            column.setMaxWidth(SimulatorConstants.WORLD_CELL_SIZE);
        }

        JPanel worldPanel = new JPanel();
        worldPanel.add(world);
        this.add(worldPanel, BorderLayout.CENTER);
    }

    private JButton startButton;
    private JButton stepButton;
    private JButton stopButton;
    private JButton resetButton;
    private JSlider speedSlider;
    private JTable world;
}