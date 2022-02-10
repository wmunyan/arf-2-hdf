package wkm.a2h.intf

interface IHdfGenerator {
    /**
     * generationOptions:
     * - source-node
     * - support-node
     * @param generationOptions
     * @return
     */
    def generate(def generationOptions)
}