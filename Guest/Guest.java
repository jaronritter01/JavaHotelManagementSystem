package Guest;

public class Guest {
    private int id;
    private String name;
    private int room_num;

    public Guest(int input_id, String input_name) {
        id = input_id;
        name = input_name;
        room_num = 0;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public int getRoomOfOccupation() {
        return this.room_num;
    }

    public int getRoomNum() {
        return this.room_num;
    }

    public void setId(int new_id) {
        this.id = new_id;
    }

    public void setRoom(int new_room) {
        this.room_num = new_room;
    }

    public boolean isCheckedIn() {
        if (room_num != 0) {
            return true;
        } else {
            return false;
        }
    }
}
