
import java.util.Arrays;

public class StableRoommate {
    private int N, preferencesCount;
    private String[][] stud1Pref;
    private String[] stud1;
    private String[] rezultat;
    private boolean[] stud1Pair;

    /**
     * Constructor
     **/
    public StableRoommate(String[] m,String[][] mp) {
        N = mp.length;
        preferencesCount = 0;
        stud1 = m;
        stud1Pref = mp;
        stud1Pair = new boolean[N];
        rezultat = new String[N];
        calcMatches();
    }

    /**
     * function to calculate all matches
     **/



    private void calcMatches() {
        while (preferencesCount < N) {
            int free ;
            for (free = 0; free < N; free++)
                if (!stud1Pair[free])
                    break;

            for (int i = 0; i < N && !stud1Pair[free]; i++) {
                int index = stud1IndexOf(stud1Pref[free][i]);
                if (rezultat[index] == null) {
                    rezultat[index] = stud1[free];
                    stud1Pair[free] = true;
                    preferencesCount++;
                } else {
                    String currentPartner = rezultat[index];
                    if (morePreference(currentPartner, stud1[free], index)) {
                        rezultat[index] = stud1[free];
                        stud1Pair[free] = true;
                        stud1Pair[stud1IndexOf(currentPartner)] = false;
                    }
                }
            }
        }
        printRoomates();

    }

    /**
     * function to check if stud2 prefers new roomate over old assigned roomate
     **/
    private boolean morePreference(String curPartner, String newPartner, int index) {
        for (int i = 0; i < N; i++) {
            if (stud1Pref[index][i].equals(newPartner))
                return true;
            if (stud1Pref[index][i].equals(curPartner))
                return false;
        }
        return false;
    }

    /**
     * get stud1 index
     **/
    private int stud1IndexOf(String str) {
        for (int i = 0; i < N; i++)
            if (stud1[i].equals(str))
                return i;
        return -1;
    }


    public void printRoomates(){
        System.out.println("Roomates are : ");
        for (int i = 0; i < N; i++) {
            System.out.println(stud1[i] + " "+ rezultat[i]);
        }

    }

    /**
     * main function
     **/
    public static void main(String[] args) {
        System.out.println("Stable Roomate Algorithm\n");
        /** list of stud1 **/
        String[] m = {"A","B","C","D","E","F"};

        /** list of stud2 **/

        /** stud1 preference **/
        String[][] mp = {{ "B", "D", "F", "C","E"},
                { "D", "E", "F", "A", "C"},
                { "D", "E", "F", "A", "B"},
                { "F", "C", "A", "E", "B"},
                { "F", "C", "D", "B", "A"},
                { "A", "B", "D", "C", "E"}
        };

        /** stud2 preference **/

        StableRoommate gs = new StableRoommate(m, mp);
    }
}
