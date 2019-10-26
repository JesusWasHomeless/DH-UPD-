package DH;

public class Transfer {
    private static int p;
    private static int g;
    private static int OpenKey;

    public Transfer() {

    }
    public static void transferPG(Object object) {
        if (object instanceof Users) {
            Users user = (Users) object;
            p = user.getPG()[0];
            g = user.getPG()[1];
        } else
            System.out.println("Idi prolog lomai!");
    }

    public static int[] readPG() {
        return new int[]{p, g};
    }

    public static void transferOK(Object object) {
        if (object instanceof Users) {
            Users user = (Users) object;
            OpenKey = user.openKeyCalc();
        } else
            System.out.println("Idi prolog lomai");
    }

    public static int readOK() {
        return OpenKey;
    }
}
