interface Command {
    void execute();
}

class Light {
    private String location;

    public Light(String location) {
        this.location = location;
    }

    public void turnOn() {
        System.out.println(location + " light is ON.");
    }

    public void turnOff() {
        System.out.println(location + " light is OFF.");
    }
}

class LightOnCommand implements Command {
    private Light light;

    public LightOnCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.turnOn();
    }
}

class LightOffCommand implements Command {
    private Light light;

    public LightOffCommand(Light light) {
        this.light = light;
    }

    public void execute() {
        light.turnOff();
    }
}

class RemoteControl {
    private Command command;

    public void setCommand(Command command) {
        this.command = command;
    }

    public void pressButton() {
        if (command != null) {
            command.execute();
        } else {
            System.out.println("No command assigned to remote.");
        }
    }
}

public class HomeAutomationTest {
    public static void main(String[] args) {
        Light livingRoomLight = new Light("Living Room");
        Light bedroomLight = new Light("Bedroom");

        Command livingRoomOn = new LightOnCommand(livingRoomLight);
        Command livingRoomOff = new LightOffCommand(livingRoomLight);
        Command bedroomOn = new LightOnCommand(bedroomLight);
        Command bedroomOff = new LightOffCommand(bedroomLight);

        RemoteControl remote = new RemoteControl();

        remote.setCommand(livingRoomOn);
        remote.pressButton();

        remote.setCommand(bedroomOn);
        remote.pressButton();

        remote.setCommand(livingRoomOff);
        remote.pressButton();

        remote.setCommand(bedroomOff);
        remote.pressButton();
    }
}
