import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    static Scanner input = new Scanner(System.in);
    static String[][] Morning;
    static String[][] Afternoon;
    static String[][] Night;
    static int row;
    static int col;
    static String[] History;
    static int n = 0;

    private static void Menu() {
        System.out.println("\n");

        System.out.println(">>>>>>--------Menu for application---------<<<<<<<<");
        System.out.println("\t <A>Booking ");
        System.out.println("\t <B>Hall");
        System.out.println("\t <C>Showtime");
        System.out.println("\t <D>rebootShowtime");
        System.out.println("\t <E>History");
        System.out.println("\t <F>Exit");
        System.out.println("<<<<<<------------>>>>>>>>>>>>>>>>");
    }

    public static String validate(String message, String regex) {
        while (true) {
            System.out.print(message);
            String userInput = input.nextLine().trim();
            Pattern pattern = Pattern.compile(regex);
            if (pattern.matcher(userInput).matches()) {
                return userInput;
            } else {
                System.out.println(" Error invalid !");
            }
        }
    }

    private static void booking() {
        System.out.println("Booking process");
        showTime();
        String option = validate("Choose your showtime", "[a-cA-C]");
        String[][] currentHall;
        if (option.equalsIgnoreCase("a")) currentHall = Morning;
        else if (option.equalsIgnoreCase("b")) currentHall = Afternoon;
        else currentHall = Night;
        String header;
        if (option.equalsIgnoreCase("a")) header = "Hall A";
        else if (option.equalsIgnoreCase("b")) header = "Hall B";
        else header = "Hall C";

        System.out.println(header);
        char s = 'A';
        String seat = "";
        String available;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                seat = s + "-" + (j + 1);
                if (currentHall[i][j] == null) available = "AV";
                else available = "BO";
                System.out.print("|" + seat + "::" + available + "|\t");
            }
            s += 1;
            System.out.println();
        }
        System.out.println("<<<<<<<<<<------------>>>>>>>>>>>>>>>>");
        System.out.println("# Instruction");
        System.out.println("# Single A-1");
        System.out.println("Multiple (seperate by comma): A-1,A-2");
        System.out.println("<<<<<<<<<<------------>>>>>>>>>>>>>>>>");

        Mainloop:
        while (true) {
            System.out.println("select avaliable seat:");
            String allBooking = input.nextLine();
            allBooking = allBooking.replaceAll("\\s", "");
            String[] userBooking = allBooking.split(",");
            Pattern pattern = Pattern.compile("^\\s*[A-Za-z]\\s*-\\s*(?!0)\\d+\\s*$");
            for (String booking : userBooking) {
                if (pattern.matcher(booking).matches()) {
                    boolean avaliableCheck = true;
                    s = 'A';
                    seat = "";
                    available = "";
                    subloop:
                    for (int i = 0; i < row; i++) {
                        for (int j = 0; j < col; j++) {
                            seat = s + "-" + (j + 1);
                            if (booking.equalsIgnoreCase(seat)) {
                                if (currentHall[i][j] != null) {
                                    System.out.println(seat + "This seat is already booked ");
                                    continue Mainloop;
                                } else {
                                    avaliableCheck = false;
                                    break subloop;
                                }

                            }
                        }
                        s += 1;
                    }
                    if (avaliableCheck) {
                        System.out.println("This is unavaliable");
                        continue Mainloop;

                    }
                } else {
                    System.out.println("Invalid format");
                    continue Mainloop;
                }
            }
            String checkYourbooking = validate("Are you sure you want to booking[y/n]", "[yYnN]");
            if (checkYourbooking.equalsIgnoreCase("Y")) {
                int id = Integer.parseInt(validate("Enter your ID: ", "[0-9]{1,5}"));
                LocalDateTime datetime = LocalDateTime.now();
                DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
                String dt = datetime.format(format);
                StringBuilder allBookingSeat = new StringBuilder("[");
                for (String booking : userBooking) {
                    s = 'A';
                    seat = "";
                    available = "";
                    for (int i = 0; i < row; i++) {
                        //System.out.println("row");
                        for (int j = 0; j < col; j++) {
                            seat = s + "-" + (j + 1);
                            if (booking.equalsIgnoreCase(seat)) {
                                currentHall[i][j] = "BO";
                                allBookingSeat.append(seat).append(",");
                            }
                        }
                        s += 1;
                    }
                }
                allBookingSeat.append("\b]");
                String historyFormat = """
                        #Seat: %s
                        #Hall                        #Student ID:                           #Credited At
                        %s                           %d                                 %s
                        """;
                String historyElement = String.format(historyFormat, allBookingSeat, header, id, dt);
                History[n++] = historyElement;
                System.out.println("Booking success");
            }
            break;
        }
    }


    private static void hall() {
        System.out.println("<<<<<<<<<<------------>>>>>>>>>>>>>>>>");
        System.out.println("Hall Morning");
        char s = 'A';
        String seat = "";
        String available = "";
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                seat = s + "-" + (j + 1);
                if (Morning[i][j] == null) available = "AV";
                else available = "BO";
                System.out.print("|" + seat + "::" + available + "|\t");
            }
            s += 1;
            System.out.println();
        }
        System.out.println("<<<<<<<<<<------------>>>>>>>>>>>>>>>>");
        System.out.println("Hall Afternoon");
        s = 'A';
        seat = "";
        available = "";
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                seat = s + "-" + (j + 1);
                if (Afternoon[i][j] == null) available = "AV";
                else available = "BO";
                System.out.print("|" + seat + "::" + available + "|\t");
            }
            s += 1;
            System.out.println();
        }
        System.out.println("<<<<<<<<<<------------>>>>>>>>>>>>>>>>");
        System.out.println("Hall Night");
        s = 'A';
        seat = "";
        available = "";
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                seat = s + "-" + (j + 1);
                if (Night[i][j] == null) available = "AV";
                else available = "BO";
                System.out.print("|" + seat + "::" + available + "|\t");
            }
            s += 1;
            System.out.println();
        }
    }

    private static void showTime() {
        System.out.println("Daily Showtime of my cinema");
        System.out.println("A).Morning (8:00AM-12:30PM");
        System.out.println("B).Afternoon (1:00PM-5:30PM");
        System.out.println("C).Night (6:00PM-9:30PM");
    }

    private static void rebootShowtime() {
        String Yes = validate("Do you want to reboot[y/n]", "[ynYN]");
        int r = 0;
        if (Yes.equalsIgnoreCase("y")) {
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    Morning[i][j] = null;
                    Afternoon[i][j] = null;
                    Night[i][j] = null;
                    History[r++] = null;
                }
            }
        }
        System.out.println("Reboot successfully!");
        n = 0;
    }

    private static void history() {
        System.out.println("History information record");
        if (n > 0) {
            for (int i = 0; i < n; i++) {
                System.out.println("#NO: " + (i + 1));
                System.out.println(History[i]);
                System.out.println("<<<<<<<<<<<------------>>>>>>>>>>>>>>>>");
            }
        } else {
            System.out.println("<<<<<<<<<<<------------>>>>>>>>>>>>>>>>");
            System.out.println("No history");
            System.out.println("<<<<<<<<<<<------------>>>>>>>>>>>>>>>>");
        }

    }

    private static void Exit() {
        String Yes = validate("Do you want to exit!", "[ynYN]");
        if (Yes.equalsIgnoreCase("y")) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        row = Integer.parseInt(validate("Config your row in hall: ", "[1-9]\\d*"));
        col = Integer.parseInt(validate("Config your colunm in hall: ", "[1-9]\\d*"));
        Scanner input = new Scanner(System.in);
        Morning = new String[row][col];
        Afternoon = new String[row][col];
        Night = new String[row][col];
        History = new String[row * col];
        while (true) {
            Menu();
            String option = validate("Enter option: ", "[a-fA-F]").toUpperCase();
            switch (option) {
                case "A":
                    booking();
                    break;
                case "B":
                    hall();
                    break;
                case "C":
                    showTime();
                    break;
                case "D":
                    rebootShowtime();
                    break;
                case "E":
                    history();
                    break;
                case "F":
                    Exit();
                    break;
            }
        }
    }
}