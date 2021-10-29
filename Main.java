import java.util.Scanner;
import java.util.LinkedList;
import java.io.File;
import java.io.FileWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
//need to uncomment this to populate the rooms file 
//import java.lang.Math;
import Room.Room;
import Guest.Guest;

public class Main {
    public static void main(String[] args) {
        // start of main

        // frequently used variables
        boolean open = true;
        Scanner input = new Scanner(System.in);
        int selection = 0;
        LinkedList<Room> rooms = new LinkedList<>();
        LinkedList<Guest> guestRegister = new LinkedList<>();

        // reading the input file data
        try {
            File guestFile = new File("data/guests.txt");
            Scanner guestFileScanner = new Scanner(guestFile);

            while (guestFileScanner.hasNextLine()) {
                String dataLine = guestFileScanner.nextLine();
                String[] dataArr = dataLine.split(" ");
                Guest newGuest = new Guest(Integer.parseInt(dataArr[0]), dataArr[1]);
                newGuest.setRoom(Integer.parseInt(dataArr[2]));
                guestRegister.add(newGuest);
            }

            guestFileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Guest file not found");
            e.printStackTrace();
        }

        // reading the rooms file
        try {
            File roomFile = new File("data/rooms.txt");
            Scanner roomsFileScanner = new Scanner(roomFile);

            while (roomsFileScanner.hasNextLine()) {
                String dataLine = roomsFileScanner.nextLine();
                String[] dataArr = dataLine.split(" ");
                Room newRoom = new Room(Integer.parseInt(dataArr[0]), dataArr[1], Integer.parseInt(dataArr[2]),
                        Float.parseFloat(dataArr[3]));
                if (dataArr[4].equals("true")) {
                    newRoom.setAvailable();
                } else {
                    newRoom.setUnavailable();
                }
                rooms.add(newRoom);
            }

            roomsFileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Rooms File Not Found");
        }

        System.out.println("Hello, Welcome to Hotel.");

        // this is used to populate the rooms list

        /*
         * for (int i = 1; i <= 99; i++) { Room newRoom = new Room(i, "Single", (int)
         * Math.ceil(i / 10), 100.0f); if (i % 3 == 0) { newRoom.setUnavailable(); }
         * rooms.add(newRoom); }
         */

        do {
            try {
                showMenu();
                selection = input.nextInt();
                input.nextLine(); // this call is because nextInt does not consume the \n

                switch (selection) {
                    case 1: {
                        for (int i = 0; i < rooms.size(); i++) {
                            Room currentRoom = rooms.get(i);
                            if (currentRoom.is_available()) {
                                System.out.println(
                                        "Room: " + currentRoom.getRoomNumber() + " at " + currentRoom.getPrice());
                            }
                        }
                        break;
                    }
                    case 2: {
                        printGuestList(guestRegister);
                        break;
                    }
                    case 3: {
                        showPrices();
                        break;
                    }
                    case 4: {
                        System.out.println("Check In Menu:");
                        System.out.print("Please enter the guest's name: ");
                        String guestNameIn = input.nextLine();
                        System.out.print("Please enter the desired room number: ");
                        int roomNumberIn = input.nextInt();
                        input.nextLine();
                        checkIn(guestNameIn, roomNumberIn, guestRegister, rooms);
                        break;
                    }
                    case 5: {
                        System.out.println("Check out Menu:");
                        System.out.print("Please enter the guest's name: ");
                        String guestNameOut = input.nextLine();
                        checkOut(guestNameOut, guestRegister, rooms);
                        break;
                    }
                    case 6: {
                        System.out.println("Guest Creation Menu: ");
                        System.out.print("Please enter the id of the new Guest: ");
                        try {
                            int new_id = input.nextInt();
                            input.nextLine();
                            System.out.print("Please enter the name of the new Guest: ");
                            String new_name = input.nextLine();
                            Guest newGuest = new Guest(new_id, new_name);
                            guestRegister.add(newGuest);
                            printGuestList(guestRegister);
                        } catch (InputMismatchException e) {
                            System.out.println("Incorrect Input");
                        }
                        break;
                    }
                    case 7: {
                        System.out.println("Thank you for choosing Hotel Management System!\n");
                        open = false;
                        break;
                    }
                    default: {
                        System.out.println("The selection you made is invalid, please try again");
                    }
                }
            } catch (InputMismatchException e) {
                System.out.println("The selection you made was invalid. Please Try again");
                input.nextLine();
            }

        } while (open);

        // saving the guest data
        try {
            FileWriter guestWriteFile = new FileWriter("data/guests.txt");
            for (int i = 0; i < guestRegister.size(); i++) {
                if (i == guestRegister.size() - 1) {
                    Guest currentGuest = guestRegister.get(i);
                    String guestString = currentGuest.getId() + " " + currentGuest.getName() + " "
                            + currentGuest.getRoomNum();
                    guestWriteFile.write(guestString);
                } else {
                    Guest currentGuest = guestRegister.get(i);
                    String guestString = currentGuest.getId() + " " + currentGuest.getName() + " "
                            + currentGuest.getRoomNum() + "\n";
                    guestWriteFile.write(guestString);
                }
            }

            guestWriteFile.close();
            System.out.println("Guest data saved");
        } catch (IOException e) {
            System.out.println("An Error occured while writing guest data");
        }

        // writing the room data
        try {
            FileWriter roomWriteFile = new FileWriter("data/rooms.txt");
            for (int i = 0; i < rooms.size(); i++) {
                if (i == rooms.size() - 1) {
                    Room currentRoom = rooms.get(i);
                    String currentRoomString = currentRoom.getRoomNumber() + " " + currentRoom.getLayout() + " "
                            + currentRoom.getFloorLevel() + " " + currentRoom.getPrice() + " "
                            + currentRoom.is_available();
                    roomWriteFile.write(currentRoomString);
                } else {
                    Room currentRoom = rooms.get(i);
                    String currentRoomString = currentRoom.getRoomNumber() + " " + currentRoom.getLayout() + " "
                            + currentRoom.getFloorLevel() + " " + currentRoom.getPrice() + " "
                            + currentRoom.is_available() + "\n";
                    roomWriteFile.write(currentRoomString);
                }
            }

            roomWriteFile.close();
            System.out.println("Room data saved");
        } catch (IOException e) {
            System.out.println("An Error occured while writing room data");
        }
        input.close();
    }

    public static void showMenu() {
        System.out.println("\nMenu: ");
        System.out.println("[1] Rooms Availiable");
        System.out.println("[2] View Current Guests");
        System.out.println("[3] Prices");
        System.out.println("[4] Check In");
        System.out.println("[5] Check Out");
        System.out.println("[6] Add new guest");
        System.out.println("[7] Exit");
        System.out.print("Selection: ");
    }

    public static void showPrices() {
        System.out.println("\nAll prices are on a per night basis.");
        System.out.println("All rooms come standard with full bed/s");
        System.out.println("Single: $100.00");
        System.out.println("Double: $175.00");
        System.out.println("Triple: $225.00");
        System.out.println("Quad: $325.00");
        System.out.println("Doubles-Double: $200.00");
        System.out.println(
                "All room sizes come with a bed option: \n\t twin: -$25.00\n\t queen: +$25.00\n\t king: +$45.00");
    }

    public static boolean checkIn(String newGuestName, int roomNum, LinkedList<Guest> guestList,
            LinkedList<Room> rooms) {
        boolean isCurrentGuest = false;
        Guest checkInGuest = new Guest(0, "abc"); // this is just a dummy guest
        for (int i = 0; i < guestList.size(); i++) {
            Guest currentGuest = guestList.get(i);
            // this will see if the guest is a part of our system
            if (currentGuest.getName().equals(newGuestName)) {
                isCurrentGuest = true;
                checkInGuest = currentGuest;
            }

        }

        boolean checkedIn = false;
        if (isCurrentGuest && (!checkInGuest.isCheckedIn())) {
            for (int j = 0; j < rooms.size(); j++) {
                Room currentRoom = rooms.get(j);
                // will not check in if the room number is not found
                if (roomNum == currentRoom.getRoomNumber() && currentRoom.is_available()) {
                    checkInGuest.setRoom(roomNum); // adds the room to the guest record
                    currentRoom.setUnavailable(); // makes the room unavailable
                    checkedIn = true;
                }
            }
        }

        if (isCurrentGuest == false) {
            System.out.println("\nError: Guest is not a registered guest.");
            return false;
        } else if (checkedIn == false) {
            System.out.println("\nError: Guest Could not be checked in.");
            return false;
        } else {
            System.out.println("\nSuccess: Guest was checked in!");
            return true;
        }
    }

    public static boolean checkOut(String newGuestName, LinkedList<Guest> guestList, LinkedList<Room> rooms) {
        boolean isCurrentGuest = false;
        Guest checkInGuest = new Guest(0, "abc"); // this is just a dummy guest
        for (int i = 0; i < guestList.size(); i++) {
            Guest currentGuest = guestList.get(i);
            // this will see if the guest is a part of our system
            if (currentGuest.getName().equals(newGuestName)) {
                isCurrentGuest = true;
                checkInGuest = currentGuest;
            }

        }

        boolean checkedOut = false;
        if (isCurrentGuest) {
            for (int j = 0; j < rooms.size(); j++) {
                int roomNum = checkInGuest.getRoomNum(); // gets the room the current guest is in
                Room currentRoom = rooms.get(j);
                if (roomNum == currentRoom.getRoomNumber() && (!currentRoom.is_available())) {
                    checkInGuest.setRoom(0);
                    currentRoom.setAvailable(); // makes the room available
                    checkedOut = true;
                }
            }
        }

        if (isCurrentGuest == false) {
            System.out.println("\nError: Guest is not a registered guest.");
            return false;
        } else if (checkedOut == false) {
            System.out.println("\nError: Guest Could not be checked out.");
            return false;
        } else {
            System.out.println("\nSuccess: Guest was checked Out!");
            return true;
        }
    }

    public static void printGuestList(LinkedList<Guest> guestList) {
        System.out.println("\nCurrent Guest List: ");
        for (int i = 0; i < guestList.size(); i++) {
            Guest currentGuest = guestList.get(i);
            System.out.printf("Name: %s Id: %d\n", currentGuest.getName(), currentGuest.getId());
        }
    }
}