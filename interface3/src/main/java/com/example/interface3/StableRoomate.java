package com.example.interface3;

public class StableRoomate {
    private int N, preferencesCount;
    private String[][] stud1Pref;
    private String[][] stud2Pref;
    private String[] stud1;
    private String[] stud2;
    private String[] stud2Partner;
    private boolean[] stud1Pair;

    /**
     * Constructor
     **/
    public StableRoomate(String[] m, String[] w, String[][] mp, String[][] wp) {
        N = mp.length;
        preferencesCount = 0;
        stud1 = m;
        stud2 = w;
        stud1Pref = mp;
        stud2Pref = wp;
        stud1Pair = new boolean[N];
        stud2Partner = new String[N];
        calcMatches();
    }

    /**
     * function to calculate all matches
     **/
    private void calcMatches() {
        while (preferencesCount < N) {
            int free;
            for (free = 0; free < N; free++)
                if (!stud1Pair[free])
                    break;

            for (int i = 0; i < N && !stud1Pair[free]; i++) {
                int index = stud2IndexOf(stud1Pref[free][i]);
                if (stud2Partner[index] == null) {
                    stud2Partner[index] = stud1[free];
                    stud1Pair[free] = true;
                    preferencesCount++;
                } else {
                    String currentPartner = stud2Partner[index];
                    if (morePreference(currentPartner, stud1[free], index)) {
                        stud2Partner[index] = stud1[free];
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
            if (stud2Pref[index][i].equals(newPartner))
                return true;
            if (stud2Pref[index][i].equals(curPartner))
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

    /**
     * get stud2 index
     **/
    private int stud2IndexOf(String str) {
        for (int i = 0; i < N; i++)
            if (stud2[i].equals(str))
                return i;
        return -1;
    }

    /**3
     * print roomates
     **/
    public void printRoomates() {
        System.out.println("Roomates are : ");
        for (int i = 0; i < N; i++) {
            System.out.println(stud2Partner[i] + " " + stud2[i]);
        }
    }

    /**
     * main function
     **/
    public static void main(String[] args) {
        System.out.println("Stable Roomate Algorithm\n");
        /** list of stud1 **/
        String[] m = {"M1", "M2", "M3", "M4", "M5","M6"};
        /** list of stud2 **/
        String[] w = {"W1", "W2", "W3", "W4", "W5","W6"};

        /** stud1 preference **/
        String[][] mp = {{"W5", "W2", "W3", "W4", "W1","W6"},
                {"W2", "W5","W6", "W1", "W3", "W4"},
                {"W4", "W3", "W6","W2", "W1", "W5"},
                {"W1","W6", "W2", "W3", "W4", "W5"},
                {"W5", "W2","W6", "W3", "W4", "W1"},
                {"W6", "W1", "W3", "W2","W6", "W5"}
        };
        /** stud2 preference **/
        String[][] wp = {{"M5", "M3", "M4", "M1", "M2", "M6"},
                {"M6","M1", "M2", "M3", "M5", "M4"},
                {"M4", "M5", "M3", "M2","M6", "M1"},
                {"M5", "M2","M6", "M1", "M4", "M3"},
                {"M2", "M1", "M4", "M3", "M5","M6"},
                {"M1", "M2", "M5", "M4", "M5","M6"}
        };

        StableRoomate gs = new StableRoomate(m, w, mp, wp);
    }
}
