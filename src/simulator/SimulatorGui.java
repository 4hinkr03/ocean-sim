package simulator;

import model.Agent;
import model.Environment;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.*;

/**
 *
 * @author Prins
 */
public class SimulatorGui extends JFrame {

    public SimulatorGui(Environment e) {
        setEnvironment(e);
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

       startButton.addActionListener(a -> {
            running = true;
            stepping = false;
        });
        stopButton.addActionListener(a -> {
            running = false;
            stepping = false;
        });
        resetButton.addActionListener(a -> {
            environment.reset();
            running = true;
            stepping = false;
        });
        stepButton.addActionListener(a -> {
            running = true;
            stepping = true;
        });

        speedSlider.addChangeListener( c -> environment.setSpeed(speedSlider.getValue()));
    }

    private void initWorld() {
        DefaultTableModel model = new DefaultTableModel(SimulatorConstants.WORLD_WIDTH, SimulatorConstants.WORLD_HEIGHT);
        world = new JTable(model);
        world.setEnabled(false);

        AgentCellRenderer renderer = new AgentCellRenderer(environment);

        for (int col = 0; col < SimulatorConstants.WORLD_WIDTH; col++) {
            TableColumn column = world.getColumnModel().getColumn(col);
            column.setMinWidth(SimulatorConstants.WORLD_CELL_SIZE);
            column.setMaxWidth(SimulatorConstants.WORLD_CELL_SIZE);
            world.setDefaultRenderer(world.getColumnClass(col), renderer);
        }

        JPanel worldPanel = new JPanel();
        worldPanel.add(world);
        this.add(worldPanel, BorderLayout.CENTER);

    }

    public void cycleEnvironment() {
        while(true) {
            if(running) {
                environment.addNewAgents();
                environment.clearDeadAgents();
                environment.actAgents();
                repaint();
                environment.sleep();

                if(stepping) {
                    running = false;
                }
            }
        }
    }

    private JButton startButton;
    private JButton stepButton;
    private JButton stopButton;
    private JButton resetButton;
    private JSlider speedSlider;
    private JTable world;
    private Environment environment;
    private boolean running = true, stepping = false;

    public void setEnvironment(Environment e) {
        this.environment = e;
    }

   public class AgentCellRenderer extends DefaultTableCellRenderer {

        private Agent agent = null;
        private Environment e;

        public AgentCellRenderer(Environment e) {
            this.e = e;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            Agent a = e.getAgent(row, column);

            if(a != null && a.isAlive() && a.getLocation().equals(row, column)) {
                label.setBackground(a.getColor());
            } else {
                label.setBackground(Color.BLUE);
            }
            return label;
        }

        public void setAgent(Agent agent) {
            this.agent = agent;
        }
    }
}