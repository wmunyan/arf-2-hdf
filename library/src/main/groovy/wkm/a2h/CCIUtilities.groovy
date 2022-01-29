package wkm.a2h

class CCIUtilities {
    private static CCIUtilities instance = null

    private CCIUtilities() {
        // load the cci xml
    }

    static CCIUtilities getInstance() {
        if (instance == null) {
            synchronized (CCIUtilities.class) {
                if (instance == null) {
                    instance = new CCIUtilities()
                }
            }
        }
        return instance
    }

    // methods xpathing or manipulating the CCI xml
}
