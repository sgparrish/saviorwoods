package com.sgparrish.woods.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.Controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameInput extends ControllerAdapter {

    // Map Commands to keys, so game can query on commands, then the queries for
    // commands get turned into queries for keys in this singleton
    public static final Map<Commands, List<Integer>> keyMap = new HashMap<Commands, List<Integer>>();
    private static final float DEADZONE = 0.1f;

    // Controller maps
    public static Controller controller1;
    public static final Map<Commands, List<ControllerAxis>> axisMap = new HashMap<Commands, List<ControllerAxis>>();
    public static final Map<Commands, List<ControllerButton>> buttonMap = new HashMap<Commands, List<ControllerButton>>();

    static {
        // Instantiate every possible command
        for (Commands command : Commands.values()) {
            List<Integer> keys = new ArrayList<Integer>();
            keyMap.put(command, keys);

            List<ControllerAxis> axes = new ArrayList<ControllerAxis>();
            axisMap.put(command, axes);

            List<ControllerButton> buttons = new ArrayList<ControllerButton>();
            buttonMap.put(command, buttons);
        }


        loadDefaultKeys();

        if (Controllers.getControllers().first() != null)
            loadDefaultControllerInputs(Controllers.getControllers().first());
        Controllers.addListener(new GameInput());
    }

    // Float used over true/false because controllers use ranges
    public static float getCommandValue(Commands command) {
        // Poll the keys that trigger this command
        for (int key : keyMap.get(command)) {
            if (Gdx.input.isKeyPressed(key)) {
                return 1;
            }
        }

        // Poll button map for press that should trigger this command
        for (ControllerButton button : buttonMap.get(command)) {
            if (button.controller.getButton(button.id)) {
                return 1;
            }
        }

        // Poll axis map for presses that should trigger this command
        boolean firstValue = true;
        float bestValue = 0.0f;
        for (ControllerAxis axis : axisMap.get(command)) {
            float value = axis.controller.getAxis(axis.id);
            if (value > 0.0f == axis.positive && Math.abs(value) > DEADZONE) {
                if (Math.abs(value) > Math.abs(bestValue) || firstValue) {
                    bestValue = value;
                    firstValue = false;
                }
            }
        }
        return bestValue;
    }

    @Override
    public void connected(Controller controller) {
        if (controller1 == null) {
            // Connect all bindings!
            controller1 = controller;
        }
    }

    @Override
    public void disconnected(Controller controller) {
        if (controller1 == controller) {
            // Remove all bindings!

            controller1 = null;
        }
    }

    private class ControllerAxis {
        private Controller controller;
        private int id;
        private boolean positive;

        private ControllerAxis(Controller controller, int id, boolean positive) {
            this.controller = controller;
            this.id = id;
            this.positive = positive;
        }
    }

    private class ControllerButton {
        private Controller controller;
        private int id;

        private ControllerButton(Controller controller, int id) {
            this.controller = controller;
            this.id = id;
        }
    }


    public enum Commands {
        LEFT,
        RIGHT,
        UP,
        DOWN,

        JUMP,
        LIFT
    }

    public static void loadDefaultKeys() {
        // Default keys
        // Left
        keyMap.get(Commands.LEFT).add(Input.Keys.DPAD_LEFT);
        keyMap.get(Commands.LEFT).add(Input.Keys.A);

        // Right
        keyMap.get(Commands.RIGHT).add(Input.Keys.DPAD_RIGHT);
        keyMap.get(Commands.RIGHT).add(Input.Keys.D);

        // Up
        keyMap.get(Commands.UP).add(Input.Keys.DPAD_UP);
        keyMap.get(Commands.UP).add(Input.Keys.W);

        // Down
        keyMap.get(Commands.DOWN).add(Input.Keys.DPAD_DOWN);
        keyMap.get(Commands.DOWN).add(Input.Keys.S);

        // Jump
        keyMap.get(Commands.JUMP).add(Input.Keys.SPACE);

        // Lift
        keyMap.get(Commands.LIFT).add(Input.Keys.SHIFT_LEFT);
        keyMap.get(Commands.LIFT).add(Input.Keys.SHIFT_RIGHT);
    }

    public static void loadDefaultControllerInputs(Controller controller) {

        // This is necessary to instantiate inner class objects
        GameInput gameInput = new GameInput();

        // Left
        axisMap.get(Commands.LEFT).add(gameInput.new ControllerAxis(controller, 1, false));

        // Right
        axisMap.get(Commands.RIGHT).add(gameInput.new ControllerAxis(controller, 1, true));

        // Left
        axisMap.get(Commands.UP).add(gameInput.new ControllerAxis(controller, 0, true));

        // Right
        axisMap.get(Commands.DOWN).add(gameInput.new ControllerAxis(controller, 0, false));

        // Jump
        buttonMap.get(Commands.JUMP).add(gameInput.new ControllerButton(controller, 0));

        // Lift
        buttonMap.get(Commands.LIFT).add(gameInput.new ControllerButton(controller, 1));
    }

}
