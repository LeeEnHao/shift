import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Shift s = new Shift();
        Scanner sc = new Scanner(System.in);

        for (;;) {

            String command;

            try {

                command = sc.next();

            } catch (Exception e) {

                System.err.println(e.getMessage());

                continue;

            }

            if (command.equals("encode")) {

                String text = sc.nextLine().trim();

                String encodedText = s.encode(text);

                System.out.println("Encoded text for " + text + " is " + encodedText);

                continue;

            }

            if (command.equals("decode")) {

                String encodedText = sc.nextLine().trim();

                try {

                    String text = s.decode(encodedText);

                    System.out.println("Decoded text is " + text);

                } catch (Exception e) {

                    System.err.println("Encoded text cannot be decoded due " + e.getMessage());

                }

                continue;

            }

            if (command.equals("exit")) {

                System.exit(0);

            }
        }
    }
}