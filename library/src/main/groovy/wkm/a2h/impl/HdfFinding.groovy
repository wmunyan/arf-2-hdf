package wkm.a2h.impl

/**
 * HDF mapping for XCCDF rule-result
 */
class HdfFinding {
    String status // pass->passed, fail->failed
    String codeDesc
    def runTime
    def startTime
    String message
    String resourceClass
}
