package wkm.a2h.impl

class HdfProfile {
    String profileName // benchmark @id
    String version // Benchmark @style
    String title // Benchmark title
    String maintainer // Benchmark/reference/publisher
    String summary // Benchmark/description
    String license // Benchmark/notice/@id
    String copyright // Benchmark/metadata/creator
    String copyrightEmail // benchmarks@cisecurity.org
    def supports = []
    def depends = []
    def groups = []
    String status = "loaded"
    def controls = []
    String targetId
    def statistics = [:]
}
