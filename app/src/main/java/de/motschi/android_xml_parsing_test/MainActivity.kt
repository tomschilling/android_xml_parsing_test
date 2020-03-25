package de.motschi.android_xml_parsing_test

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import de.motschi.android_xml_parsing_test.model.Season
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserException
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream


class MainActivity : AppCompatActivity() {

    private var txt: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        txt = findViewById(R.id.txt)
        parseXML()
    }

    private fun parseXML() {
        val parserFactory: XmlPullParserFactory
        try {
            parserFactory = XmlPullParserFactory.newInstance()
            val parser = parserFactory.newPullParser()
            val `is`: InputStream = assets.open("data.xml")
            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false)
            parser.setInput(`is`, null)
            processParsing(parser)
        } catch (e: XmlPullParserException) {
        } catch (e: IOException) {
        }
    }

    @Throws(IOException::class, XmlPullParserException::class)
    private fun processParsing(parser: XmlPullParser) {
        var eventType = parser.eventType
        var season: Season? = null
        while (eventType != XmlPullParser.END_DOCUMENT) {
            var eltName: String?
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    eltName = parser.name
                    if ("season" == eltName) {
                        val name = parser.getAttributeValue(0)
                        val startYear = parser.getAttributeValue(1)
                        val endYear = parser.getAttributeValue(2)
                        season = Season(name, startYear, endYear)
                    }
                }
            }
            eventType = parser.next()
        }
        printSeason(season)
    }

    private fun printSeason(season: Season?) {
        val builder = StringBuilder()
            builder.append(season?.name).append("\n").append(season?.startYear).append("\n")
                .append(season?.endYear).append("\n\n")
        txt!!.text = builder.toString()
    }
}
