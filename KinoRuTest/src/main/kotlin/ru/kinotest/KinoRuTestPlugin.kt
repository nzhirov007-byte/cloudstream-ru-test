package ru.kinotest
import com.lagradost.cloudstream3.plugins.BasePlugin
import com.lagradost.cloudstream3.plugins.CloudstreamPlugin
@CloudstreamPlugin class KinoRuTestPlugin:BasePlugin(){ override fun load(){ registerMainAPI(KinoRuTestProvider()) } }