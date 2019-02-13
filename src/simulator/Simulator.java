/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulator;

import model.Environment;

/**
 *
 * @author Prins
 */
public class Simulator {
    
    public static void main(String[] args) {
        SimulatorGui gui = new SimulatorGui(new Environment());
        gui.showGui();
        gui.cycleEnvironment();
    }
}