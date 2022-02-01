package wkm.a2h.impl

import groovy.json.JsonOutput
import groovy.json.StreamingJsonBuilder
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import wkm.a2h.intf.IHdfGenerator
import wkm.common.Constants
import wkm.common.Utilities

class XccdfTestResult2HdfGenerator implements IHdfGenerator {
    private Logger log = LoggerFactory.getLogger(XccdfTestResult2HdfGenerator.class)

//    profile_name: @benchmarks['id'],
//    version: @benchmarks['style'],
//    duration: NA_FLOAT,
//    title: @benchmarks['title'],
//    maintainer: @benchmarks['reference']['publisher'],
//    summary: @benchmarks['description'],
//    license: @benchmarks['notice']['id'],
//    copyright: @benchmarks['metadata']['creator'],
//    copyright_email: 'disa.stig_spt@mail.mil',
//    controls: controls
//
//    controls = []
//    @groups.each_with_index do |group, i|
//    @item = {}
//    @item['id'] = group['Rule']['id'].split('.').last.split('_').drop(2).first.split('r').first.split('S')[1]
//    @item['title']               = group['Rule']['title'].to_s
//    @item['desc']                = group['Rule']['description'].to_s.split('Satisfies').first
//    @item['descriptions']		 = []
//    @item['descriptions']		 << desc_tags(group['Rule']['description'], 'default')
//    @item['descriptions']		 << desc_tags('NA', 'rationale')
//    @item['descriptions']		 << desc_tags(group['Rule']['check']['check-content-ref']['name'], 'check')
//    @item['descriptions']		 << desc_tags(group['Rule']['fixtext']['text'], 'fix')
//    @item['impact']				 = get_impact(group['Rule']['severity'])
//    @item['refs']				 = NA_ARRAY
//    @item['tags']				 = {}
//    @item['tags']['severity']    = nil
//    @item['tags']['gtitle']      = group['title']
//    @item['tags']['satisfies']   = satisfies_parse(group['Rule']['description'])
//    @item['tags']['gid']         = group['Rule']['id'].split('.').last.split('_').drop(2).first.split('r').first
//    @item['tags']['legacy_id']   = group['Rule']['ident'][2]['text']
//    @item['tags']['rid']         = group['Rule']['ident'][1]['text']
//    @item['tags']['stig_id']     = @benchmarks['id']
//    @item['tags']['fix_id']      = group['Rule']['fix']['id']
//    @item['tags']['cci']         = parse_refs(group['Rule']['ident'])
//    @item['tags']['nist']        = cci_nist_tag(@item['tags']['cci'])
//    @item['code']                = NA_STRING
//    @item['source_location'] = NA_HASH
//    # results were in another location and using the top block "Benchmark" as a starting point caused odd issues. This works for now for the results.
//    @item['results'] = finding(@results, i)
//    controls << @item
//            end

    /**
     * XCCDF TestResult to HDF generation
     * @return the array of maps to be converted to JSON. For
     * an XCCDF TestResult, that array will always be 1 item.
     */
    def generate(def sourceNode, def supportNode) {
        log.info "[START] XCCDF-to-HDF Generator"

        def hdfResults = []

        def sourceNodeRoot  = Utilities.instance.getElementBasename(sourceNode.name())
        def supportNodeRoot = Utilities.instance.getElementBasename(supportNode.name())

        if (sourceNodeRoot == Constants.XCCDF_TESTRESULT && supportNodeRoot == Constants.XCCDF_BENCHMARK) {
            log.info " - TestResult: ${sourceNode.@id.toString()}"
            log.info " -  Benchmark: ${supportNode.@id.toString()}"

            def authors = [
                new Author (name: "Guillaume", foo: "bar"),
                new Author (name: "Jochen", foo: "bar"),
                new Author (name: "Paul", foo: "bar")
            ]

            StringWriter         writer  = new StringWriter()
            StreamingJsonBuilder builder = new StreamingJsonBuilder(writer)
            builder {
                platform {
                    name "CCCC Heimdall Mapper"
                    release "0.1"
                    target_id ""
                }
                version "0.1"
                statistics {
                    duration "30s"
                }
                people authors, { Author author ->
                    name author.name
                    foo author.foo
                }
            }
            String json = JsonOutput.prettyPrint(writer.toString())
            log.info json
        } else {
            log.error "Invalid source (TestResult) or support (Benchmark) nodes provided"
        }

        log.info "[ END ] XCCDF-to-HDF Generator"
        return hdfResults
    }
}

class Author {
    String name
    String foo
}