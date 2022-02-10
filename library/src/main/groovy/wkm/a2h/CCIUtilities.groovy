package wkm.a2h

import wkm.common.Utilities
import wkm.common.XmlParserFactory

class CCIUtilities {
    private static CCIUtilities instance = null

    private def cciMapping = [:] // key: cci_item/@id, value: reference/@index of ref with max version
    private final String CCI_RESOURCE_PATH = "/cci/U_CCI_List.xml"

    /**
     * Internal-only constructor
     */
    private CCIUtilities() {
        parse(
            XmlParserFactory.getParser()
                .parse(getClass().getResourceAsStream(CCI_RESOURCE_PATH)))
    }

    /**
     * Singleton instance getter
     */
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

    /**
     * Parse the CCI XML document to an easily searchable
     * map keyed by the CCI ID
     * @param cciNode
     * @return void
     */
    def parse(Node cciNode) {
        def cciItems = cciNode."**".findAll { n ->
            n instanceof Node && Utilities.instance.getElementBasename(n.name()) == "cci_item"
        }
        cciItems.each { cciItem ->
            def cciItemId = cciItem.@id.toString()
            // references
            def references = cciItem.children()."**".findAll { r ->
                r instanceof Node && Utilities.instance.getElementBasename(r.name()) == "reference"
            }
            def referenceMap = [:]
            references.each { reference ->
                def version = Integer.parseInt(reference.@version.toString())
                referenceMap[version] = reference.@index.toString()
            }
            cciMapping[cciItemId] = referenceMap.max { it.key }.value
        }
    }

    /**
     * map a list of CCI IDs to their NIST equivalent
     * @param ccis
     * @return
     */
    def ccis2nist(def ccis = []) {
        def nists = []
        ccis.each { cci ->
            def nist = cci2nist(cci)
            if (nist) { nists << nist }
        }
        return nists
    }

    /**
     * Map a single CCI ID to it's NIST equivalent
     * @param cci
     * @return
     */
    def cci2nist(def cci) {
        def nist = null

        if (cciMapping.containsKey(cci)) {
            nist = cciMapping[cci]
        }
        return nist
    }
}
