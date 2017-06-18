package com.example.shilin.ActorMovieFinder;

/**
 * Created by shilinlu on 8/20/2016.
 */
public class MovieDbUrl {
    //Volatile keyword ensures that multiple threads handle the unique/instance correctly
    private volatile static MovieDbUrl uniqueInstance;

    private final String url = "http://api.themoviedb.org/3/";
    private final String API_KEY = "deea9711e0770caae3fc592b028bb17e";

    private MovieDbUrl() {
    }

    //Check for an instance and if there isn't one enter the synchronized method
    public static MovieDbUrl getInstance() {
        if (uniqueInstance == null) {
            synchronized (MovieDbUrl.class) {
                if (uniqueInstance == null) {
                    uniqueInstance = new MovieDbUrl(); //Once in the block, check again and if still null, create the instance
                }
            }
        }
        return uniqueInstance;
    }

    public String getActorQuery(String nameToSearch){
        return url + "search/person?api_key=" + API_KEY + "&query=" + nameToSearch + "&sort_by=popularity";
    }

    public String getFilmographyQuery(int actorId){
        return url + "person/" + actorId + "?api_key=" + API_KEY + "&append_to_response=credits";
    }
    public String getMovies(String query){
        return url + "search/movie?api_key=" + API_KEY + "&language=en-US&query=" + query;
    }
    public String getNowPlaying(){
        return url+ "movie/now_playing?api_key=" + API_KEY + "&language=en-US";
    }
    public String getCredits(int id){
        return url + "movie/" + id + "/credits?api_key=" + API_KEY;
    }




}
