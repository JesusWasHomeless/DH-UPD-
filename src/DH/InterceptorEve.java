package DH;


public class InterceptorEve {
        private int p;
        private int g;
        private int KeyA;
        private int KeyB;

        public void interceptPG(int [] PG){
            p = PG[0];
            g = PG[1];
        }

        public int[] getInterceptedData(){
            return new int[]{p,g};
        }

        public void interceptAliceKey(int K){
            KeyA = K;
        }

        public int getAliceKey(){
            return KeyA;
        }

        public void interceptBobKey(int K){
            KeyB = K;
        }

        public int getBobKey(){
            return KeyB;
        }
}
