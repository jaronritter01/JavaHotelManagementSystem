package Room;

public class Room {
    private int room_number, floor_level;
    private String layout;
    private float price;
    private boolean available;

    // constructor for room class
    public Room(int room_num, String type, int floor, float price_per_night) {
        this.room_number = room_num;
        this.layout = type;
        this.floor_level = floor;
        this.price = price_per_night;
        this.available = true;
    }

    // getters
    public int getRoomNumber() {
        return this.room_number;
    }

    public String getLayout() {
        return this.layout;
    }

    public int getFloorLevel() {
        return this.floor_level;
    }

    public float getPrice() {
        return this.price;
    }

    public boolean is_available() {
        return this.available;
    }

    // setters
    public void setRoomNumber(int newNum) {
        this.room_number = newNum;
    }

    public void setLayout(String newLayout) {
        this.layout = newLayout;
    }

    public void setFloorLevel(int newLevel) {
        this.floor_level = newLevel;
    }

    public void setPrice(float new_price) {
        this.price = new_price;
    }

    public void setAvailable() {
        this.available = true;
    }

    public void setUnavailable() {
        this.available = false;
    }
}
