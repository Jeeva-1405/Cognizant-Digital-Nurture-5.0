public class Computer {

    private String cpu;
    private String ram;
    private String storage;
    private String graphicsCard;
    private String operatingSystem;

    private Computer(Builder builder) {
        this.cpu = builder.cpu;
        this.ram = builder.ram;
        this.storage = builder.storage;
        this.graphicsCard = builder.graphicsCard;
        this.operatingSystem = builder.operatingSystem;
    }

    public void displayConfig() {
        System.out.println("Computer Configuration:");
        System.out.println("  CPU             : " + cpu);
        System.out.println("  RAM             : " + ram);
        System.out.println("  Storage         : " + storage);
        System.out.println("  Graphics Card   : " + (graphicsCard != null ? graphicsCard : "Integrated"));
        System.out.println("  Operating System: " + (operatingSystem != null ? operatingSystem : "None"));
    }

    public static class Builder {
        private String cpu;
        private String ram;
        private String storage;
        private String graphicsCard;
        private String operatingSystem;

        public Builder setCpu(String cpu) {
            this.cpu = cpu;
            return this;
        }

        public Builder setRam(String ram) {
            this.ram = ram;
            return this;
        }

        public Builder setStorage(String storage) {
            this.storage = storage;
            return this;
        }

        public Builder setGraphicsCard(String graphicsCard) {
            this.graphicsCard = graphicsCard;
            return this;
        }

        public Builder setOperatingSystem(String operatingSystem) {
            this.operatingSystem = operatingSystem;
            return this;
        }

        public Computer build() {
            return new Computer(this);
        }
    }

    public static void main(String[] args) {
        Computer gamingPC = new Computer.Builder()
                .setCpu("Intel Core i9")
                .setRam("32GB DDR5")
                .setStorage("2TB NVMe SSD")
                .setGraphicsCard("NVIDIA RTX 4090")
                .setOperatingSystem("Windows 11")
                .build();

        Computer officePC = new Computer.Builder()
                .setCpu("Intel Core i5")
                .setRam("8GB DDR4")
                .setStorage("512GB SSD")
                .build();

        gamingPC.displayConfig();
        System.out.println();
        officePC.displayConfig();
    }
}
