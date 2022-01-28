package wkm.common

import groovy.xml.XmlParser;

@Singleton
class Utilities {
    def getElementBasename(String elementName) {
        def basename = elementName
        def pos = elementName.indexOf(":")
        if (pos >= 0) {
            basename = elementName.substring(pos + 1)
        }
        return basename
    }
}
