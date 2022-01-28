package wkm.common

import groovy.xml.XmlParser
import groovy.xml.XmlSlurper

class XmlParserFactory {

	/**
	 * Construct and return a new XML Parser
	 * @return XmlParser
	 */
	static def getParser() {
		def xmlParser = new XmlParser(false, false)
		xmlParser.setFeature("http://xml.org/sax/features/external-general-entities", false)
		xmlParser.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
		xmlParser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
		return xmlParser
	}

	/**
	 * Construct and return a new XML Parser
	 * @return XmlParser
	 */
	static def getNamespaceAwareParser() {
		def xmlParser = new XmlParser(false, true)
		xmlParser.setFeature("http://xml.org/sax/features/external-general-entities", false)
		xmlParser.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
		xmlParser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
		return xmlParser
	}

	static def getNamespaceAwareParserAllowingDoctypeDeclaration() {
		def xmlParser = new XmlParser(false, true, true)
		xmlParser.setFeature("http://xml.org/sax/features/external-general-entities", false)
		xmlParser.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
		xmlParser.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
		return xmlParser
	}

	/**
	 * Construct and return a new XML Slurper
	 * @return XmlSlurper
	 */
	static def getSlurper() {
		def xmlSlurper = new XmlSlurper(false, false)
		xmlSlurper.setFeature("http://xml.org/sax/features/external-general-entities", false)
		xmlSlurper.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
		xmlSlurper.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false)
		return xmlSlurper
	}
}
