package ru.kinotest
import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
class KinoRuTestProvider:MainAPI(){
 override var mainUrl="https://download.blender.org"; override var name="Кино RU Test"; override var lang="ru"; override val supportedTypes=setOf(TvType.Movie); override val hasMainPage=true
 private data class Film(val id:String,val title:String,val year:Int,val description:String,val poster:String,val video:String)
 private val films=listOf(
  Film("bbb","Большой Бак Банни",2008,"Открытый короткометражный фильм Blender Foundation. Тест встроенного воспроизведения CloudStream.","https://peach.blender.org/wp-content/uploads/title_anouncement.jpg?x11217","https://download.blender.org/peach/bigbuckbunny_movies/BigBuckBunny_320x180.mp4"),
  Film("sintel","Синтел",2010,"Открытый фильм Blender Foundation. Используется только для проверки каталога и плеера.","https://durian.blender.org/wp-content/uploads/2010/06/sintel-poster.jpg","https://download.blender.org/durian/trailer/sintel_trailer-480p.mp4")
 )
 override suspend fun getMainPage(page:Int,request:MainPageRequest):HomePageResponse{ val cards=films.map{f->newMovieSearchResponse(f.title,"kinorutest://"+f.id,TvType.Movie){posterUrl=f.poster}}; return newHomePageResponse(listOf(HomePageList("Открытые фильмы — тест",cards,true))) }
 override suspend fun search(query:String):List<SearchResponse> = films.filter{it.title.contains(query,ignoreCase=true)}.map{f->newMovieSearchResponse(f.title,"kinorutest://"+f.id,TvType.Movie){posterUrl=f.poster}}
 override suspend fun load(url:String):LoadResponse{ val id=url.substringAfter("kinorutest://"); val f=films.firstOrNull{it.id==id}?:throw ErrorLoadingException("Фильм не найден"); return newMovieLoadResponse(f.title,url,TvType.Movie,f.video){posterUrl=f.poster;year=f.year;plot=f.description} }
 override suspend fun loadLinks(data:String,isCasting:Boolean,subtitleCallback:(SubtitleFile)->Unit,callback:(ExtractorLink)->Unit):Boolean{ callback(newExtractorLink(name,"Открытое видео",data){quality=Qualities.P480.value}); return true }
}