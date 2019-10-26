package DH;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Users {
    private Random random = new Random();
    private int p; //prime number
    private int g; //primitive root of p
   //  private int OpenKey;
    private BigInteger P;
    private BigInteger G;
    private BigInteger Sec;
    private BigInteger OpenKey;


    public void initPG(){
        p = getP();
        g = getG(p);
        P = BigInteger.valueOf(p);
        G = BigInteger.valueOf(g);
    }

    public int [] getPG(){
        return new int [] {p,g};
    }

    public void setParameters(int[] parameters) {
        p = parameters[0];
        P = BigInteger.valueOf(p);
        g = parameters[1];
        G = BigInteger.valueOf(g);
    }

    public void inSec(){
        Sec = BigInteger.valueOf(random.nextInt(p));
    }

    public int getSec(){
        return Sec.intValue();
    }

    public int openKeyCalc(){
        return G.modPow(Sec, P).intValue();
    }
    public void openKeySet(int key){
        OpenKey = BigInteger.valueOf(key);
    }

    public int getOK(){
        return OpenKey.intValue();
    }
    public int getSharedSecretKey(){
        return OpenKey.modPow(Sec,P).intValue();
    }



    //prime numbers randomizer
    private Integer getP(){
        List<Integer>PrimeList = new ArrayList<>();
        int n = 1000;
        boolean [] isPrime = new boolean[n];
        isPrime[0]=isPrime[1]=false; //0 i 1 ne prime

        for(int i = 2; i < n; i++){
            isPrime[i] = true; // ����� ��� ���������� �������
        }
//���� ����� �������, �� ����� ������������ � ��� ��� �� ������� �����, ����� �����������
        for(int i = 2; i < isPrime.length; i++){
            if (isPrime[i]){
                for (int j = 2; j*i<isPrime.length; j++){
                    isPrime[i*j] = false;
                }
            }
        }


        for (int i = 2; i < n; i++) {
            if (isPrime[i]) {
                PrimeList.add(i);
            }
        }
        for(int i = 0; i < PrimeList.size(); i++)
        if (MillerRabinTest(BigInteger.valueOf(i),5 ))
        return PrimeList.get(random.nextInt(PrimeList.size()));
        return null;
    }


    // ���� ������� � ������ �� �������� �����
// ������������ k ������� �������� ����� n �� ��������
    public boolean MillerRabinTest(BigInteger n, int k)
    {
        // ���� n == 2 ��� n == 3 - ��� ����� �������, ���������� true
        if (n.equals(BigInteger.valueOf(2)) || n.equals(BigInteger.valueOf(3)))
            return true;

        // ���� n < 2 ��� n ������ - ���������� false
        if (n.compareTo(BigInteger.valueOf(2))<0 || n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
            return false;

        // ���������� n ? 1 � ���� (2^s)�t, ��� t �������, ��� ����� ������� ���������������� �������� n - 1 �� 2
        BigInteger t = n.subtract(BigInteger.ONE);

        int s = 0;

            while (t.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))

        {
            t = t.divide(BigInteger.valueOf(2));
            s += 1;
        }

        // ��������� k ���
        for (int i = 0; i < k; i++)
        {
            // ������� ��������� ����� ����� a � ������� [2, n ? 2]
            SecureRandom rng = new SecureRandom();

            byte[] _a = new byte[n.toByteArray().length];

            BigInteger a;

            do
            {
                rng.nextBytes(_a);
                a = new BigInteger(_a);
            }
            while (a.compareTo(BigInteger.valueOf(2)) <0 || a.compareTo(n.subtract(BigInteger.valueOf(2))) >= 0);

            // x ? a^t mod n, �������� � ������� ���������� � ������� �� ������
            BigInteger x = a.modPow(t, n);

            // ���� x == 1 ��� x == n ? 1, �� ������� �� ��������� �������� �����
            if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
                continue;

            // ��������� s ? 1 ���
            for (int r = 1; r < s; r++)
            {
                // x ? x^2 mod n
                x = x.modPow(BigInteger.valueOf(2), n);

                // ���� x == 1, �� ������� "���������"
                if (x.equals(BigInteger.ONE))
                    return false;

                // ���� x == n ? 1, �� ������� �� ��������� �������� �������� �����
                if (x.equals(n.subtract(BigInteger.ONE)))
                    break;
            }

            if (!Objects.equals(x, n.subtract(BigInteger.ONE)))
                return false;
        }

        // ������� "�������� �������"
        return true;
    }
    private int getG(int p){
        List<Integer> ROOT = new ArrayList<>();
        for(int gInit = 2; gInit < 1000; gInit++)
            if (isRoot(gInit, p))
                ROOT.add(gInit);

            return ROOT.get(random.nextInt(ROOT.size()));
    }

    private boolean isRoot(int gInit, int p1){
        int  phi = p1-1;
        BigInteger PHI = BigInteger.valueOf(phi);
        //������������ �����
        List<Integer>F = new ArrayList<>(); //���� ������� ����������
        for(int i = 2; i< phi; i++){
            while (phi%i==0){
                F.add(i);
                phi/=i;
            }
        }
        if(phi>2)
            F.add(phi);

        BigInteger G = BigInteger.valueOf(gInit);
        BigInteger P1 = BigInteger.valueOf(p1);
        for (Integer a : F) {
            BigInteger Factor = BigInteger.valueOf(a);
            BigInteger Div = PHI.divide(Factor);
            // ���� g^(phi/factor(i)) mod p = 1, �� ��� �� ������������� ������
            if (G.modPow(Div, P1).equals(BigInteger.ONE)) return false;
        }

        return true;
    }

}
