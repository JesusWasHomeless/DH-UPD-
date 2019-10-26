
package Main;

import DH.InterceptorEve;
import DH.Users;
import DH.Transfer;

import java.util.Arrays;

public class Main {
    public static void main(String[] args) {

        Users Alice = new Users();
        Users Bob = new Users();
        InterceptorEve Eve = new InterceptorEve();

        Alice.initPG();
        System.out.println("Alice has generated p and g: " + Arrays.toString(Alice.getPG()));
        Transfer.transferPG(Alice); //Transfer to Bob

        System.out.println();

        Eve.interceptPG(Transfer.readPG());
        System.out.println("Eve knows p and g: " + Arrays.toString(Eve.getInterceptedData()));

        System.out.println();

        Bob.setParameters(Transfer.readPG());
        System.out.println("Bob has received parameters: " + Arrays.toString(Bob.getPG()));

        System.out.println();

        Alice.inSec();
        Bob.inSec();

        System.out.println("Alice has created secret number a = " + Alice.getSec());
        System.out.println("Bob has created secret number b = " + Bob.getSec());

        System.out.println();

        Alice.openKeySet(Alice.openKeyCalc());
        Bob.openKeySet(Bob.openKeyCalc());

        System.out.println("Alice has counted her OpenKey: " + Alice.getOK());
        System.out.println("Bob has counted his OpenKey: " + Bob.getOK());

        System.out.println();

        Transfer.transferOK(Alice); //OK to Bob

        Eve.interceptAliceKey(Transfer.readOK());

        Bob.openKeySet(Transfer.readOK());

        System.out.println("Eve has intercepted Alice's OK: " + Eve.getAliceKey());

        System.out.println();

        System.out.println("Bob has received an OK from Alice: " + Bob.getOK());

        System.out.println();

        Transfer.transferOK(Bob);

        Eve.interceptBobKey(Transfer.readOK());

        Alice.openKeySet(Transfer.readOK());

        System.out.println("Eve has intercepted Bob's OK: " + Eve.getBobKey());

        System.out.println();

        System.out.println("Alice has received an OK from Bob: " + Alice.getOK());

        System.out.println();

        int A_S = Alice.getSharedSecretKey();
        int B_S = Bob.getSharedSecretKey();

        System.out.println("Alice's K: " + A_S);
        System.out.println("Bob's K: " + B_S);
        if(A_S == B_S)
            System.out.println("MATCH!");
        else
            System.out.println("MISMATCH!");


    }
}
