package wkm.a2h.impl

/**
 * HDF mapping for XCCDF Rule
 */
class HdfControl {
    String id
    String title
    String desc
    def descriptions = [] // array of maps
    String impact
    def refs = []
    def tags = [:] // an HdfTag
    String code
    String sourceLocation = [:]
    HdfFinding finding // an HdfFinding (rule-result)
}
